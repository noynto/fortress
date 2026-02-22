package me.noynto.fortress.application.transactions.command.handler;

import me.noynto.fortress.application.transactions.command.CreateTransactionCommand;
import me.noynto.fortress.domain.transactions.TransactionProvider;
import me.noynto.fortress.domain.transactions.Transaction;

import java.util.Objects;
import java.util.UUID;

/**
 * Handler pour la création de transaction
 */
public record CreateTransactionHandler(
        TransactionProvider transactionProvider
) {

    public Transaction handle(CreateTransactionCommand command) {
        Objects.requireNonNull(command);
        command.validate();
        Transaction transaction = new Transaction.Default();
        transaction.id(new Transaction.Id(UUID.randomUUID().toString()));
        Transaction.Description description = new Transaction.Description(command.description());
        transaction.description(description);
        Transaction.Amount amount = new Transaction.Amount(command.amount());
        transaction.amount(amount);
        Transaction.Type type = Transaction.Type.valueOf(command.type());
        transaction.type(type);
        transaction.status(Transaction.Status.PENDING);
        return transactionProvider.create(transaction);
    }
}
