package me.noynto.fortress.infrastructure.persistence.transactions;

import me.noynto.fortress.domain.shared.TransactionId;
import me.noynto.fortress.domain.transactions.*;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public record InMemoryTransactions(
        Map<TransactionId, Transaction> store,
        Clock clock
) implements TransactionProvider {

    @Override
    public Transaction create(TransactionDescription description, TransactionAmount amount, TransactionType type, TransactionOwner owner, TransactionApplicationDate applicationDate) {
        Transaction transaction = new Transaction();
        TransactionId id = new TransactionId(UUID.randomUUID().toString());
        transaction.id(id);
        transaction.description(description);
        transaction.amount(amount);
        transaction.type(type);
        transaction.state(TransactionState.PENDING);
        transaction.owner(owner);
        transaction.issueDate(new TransactionIssueDate(LocalDate.now(clock)));
        transaction.applicationDate(applicationDate);
        this.store.put(id, transaction);
        return transaction;
    }

    @Override
    public Stream<Transaction> stream() {
        return this.store.values().stream();
    }

    @Override
    public Optional<Transaction> find(TransactionId id) {
        return Optional.ofNullable(this.store.get(id));
    }

    @Override
    public void delete(TransactionId id) {
        this.store.remove(id);
    }

    @Override
    public Stream<Transaction> stream(TransactionState state) {
        return this.store.values().stream().filter(transaction -> Objects.equals(transaction.state(), state));
    }

    @Override
    public Transaction update(Transaction transaction) {
        this.store.replace(transaction.id(), transaction);
        return transaction;
    }
}
