package me.noynto.fortress.application.command.handler;

import me.noynto.fortress.application.command.CreateTransactionCommand;
import me.noynto.fortress.domain.Persistence;
import me.noynto.fortress.domain.Transaction;

import java.util.Objects;
import java.util.UUID;

/**
 * Handler pour la création de transaction
 */
public record CreateTransactionHandler(
        Persistence persistence
) {

    public Transaction handle(CreateTransactionCommand command) {
        Objects.requireNonNull(command);
        command.validate();
        Transaction transaction = new Transaction.Default();
        transaction.id(new Transaction.Id(UUID.randomUUID().toString()));
        transaction.description(command.description());
        transaction.amount(command.amount());
        transaction.type(command.type());
        transaction.status(Transaction.Status.PENDING);
        return persistence.save(transaction);
    }
}
