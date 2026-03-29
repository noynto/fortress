package me.noynto.fortress.infrastructure.persistence.transactions;

import io.helidon.dbclient.DbClient;
import io.helidon.dbclient.DbRow;
import me.noynto.fortress.domain.shared.IdentityId;
import me.noynto.fortress.domain.shared.TransactionId;
import me.noynto.fortress.domain.transactions.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public record DatabaseTransactions(
        DbClient client,
        Clock clock
) implements TransactionProvider {
    @Override
    public Transaction create(TransactionDescription description, TransactionAmount amount, TransactionType type, TransactionOwner owner, TransactionApplicationDate applicationDate) {
        var id = new TransactionId(UUID.randomUUID().toString());
        var issueDate = new TransactionIssueDate(LocalDate.now(clock));
        client.execute()
                .createInsert(
                        """
                                INSERT INTO transactions
                                  (id, description, amount_cents, type, state, owner_identity_id, issue_date, application_date)
                                VALUES
                                  (:id, :description, :amount_cents, :type, :state, :owner_identity_id, :issue_date, :application_date)
                                """
                )
                .addParam("id", id.value())
                .addParam("description", description.value())
                .addParam("amount_cents", amount.value().multiply(BigDecimal.valueOf(100)))
                .addParam("type", type.name())
                .addParam("state", TransactionState.PENDING.name())
                .addParam("owner_identity_id", owner.identityId().value())
                .addParam("issue_date", issueDate.value().toString())
                .addParam("application_date", applicationDate.value().toString())
                .execute();
        var transaction = new Transaction();
        transaction.id(id);
        transaction.description(description);
        transaction.amount(amount);
        transaction.type(type);
        transaction.state(TransactionState.PENDING);
        transaction.owner(owner);
        transaction.issueDate(issueDate);
        transaction.applicationDate(applicationDate);
        return transaction;
    }

    @Override
    public Stream<Transaction> stream() {
        return client.execute()
                .createQuery("SELECT * FROM transactions")
                .execute()
                .map(DatabaseTransactions::toTransaction);
    }

    @Override
    public Optional<Transaction> find(TransactionId id) {
        return client.execute().createQuery("SELECT * FROM transactions WHERE id = :id")
                .addParam("id", id.value())
                .execute()
                .findFirst()
                .map(DatabaseTransactions::toTransaction);
    }

    @Override
    public void delete(TransactionId id) {
        client.execute()
                .createDelete("DELETE FROM transactions WHERE id = :id")
                .addParam("id", id.value())
                .execute();
    }

    @Override
    public Stream<Transaction> stream(TransactionState state) {
        return client.execute()
                .createQuery("SELECT * FROM transactions WHERE state = :state")
                .addParam("state", state.name())
                .execute()
                .map(DatabaseTransactions::toTransaction);
    }

    @Override
    public Transaction update(Transaction transaction) {
        client.execute().createUpdate("""
                        UPDATE transactions
                        SET description      = :description,
                            amount_cents     = :amount_cents,
                            type             = :type,
                            state            = :state,
                            application_date = :application_date
                        WHERE id = :id
                        """)
                .addParam("description", transaction.description().value())
                .addParam("amount_cents", transaction.amount().value().multiply(BigDecimal.valueOf(100)))
                .addParam("type", transaction.type().name())
                .addParam("state", transaction.state().name())
                .addParam("application_date", transaction.applicationDate().value().toString())
                .addParam("id", transaction.id().value())
                .execute();
        return transaction;
    }

    private static Transaction toTransaction(DbRow dbRow) {
        Transaction transaction = new Transaction();
        transaction.id(new TransactionId(dbRow.column("id").get(String.class)));
        transaction.description(new TransactionDescription(dbRow.column("description").get(String.class)));
        transaction.amount(new TransactionAmount(BigDecimal.valueOf(dbRow.column("amount_cents").get(Integer.class)).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)));
        transaction.type(TransactionType.valueOf(dbRow.column("type").get(String.class)));
        transaction.state(TransactionState.valueOf(dbRow.column("state").get(String.class)));
        transaction.owner(new TransactionOwner(new IdentityId(dbRow.column("owner_identity_id").get(String.class))));
        transaction.issueDate(new TransactionIssueDate(LocalDate.parse(dbRow.column("issue_date").get(String.class))));
        transaction.applicationDate(new TransactionApplicationDate(LocalDate.parse(dbRow.column("application_date").get(String.class))));
        return transaction;
    }
}
