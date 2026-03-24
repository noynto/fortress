package me.noynto.fortress.application.transactions.command.handler;

import me.noynto.fortress.application.transactions.command.CreateTransactionCommand;
import me.noynto.fortress.domain.transactions.*;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Handler pour la création de transaction
 */
public record CreateTransactionHandler(
        TransactionProvider transactionProvider,
        Clock clock
) {

    public Transaction handle(CreateTransactionCommand command) {
        Objects.requireNonNull(command);
        command.validate();
        TransactionDescription description = new TransactionDescription(command.description());
        TransactionAmount amount = new TransactionAmount(command.amount());
        TransactionType type = TransactionType.valueOf(command.type());
        TransactionOwner creator = new TransactionOwner(command.userId());
        TransactionApplicationDate applicationDate = new TransactionApplicationDate(LocalDate.now(clock));
        return transactionProvider.create(description, amount, type, creator, applicationDate);
    }

}
