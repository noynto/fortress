package me.noynto.fortress.application.transactions.command.handler;

import me.noynto.fortress.application.transactions.command.CreateTransactionCommand;
import me.noynto.fortress.domain.transactions.*;

import java.util.Objects;

/**
 * Handler pour la création de transaction
 */
public record CreateTransactionHandler(
        TransactionProvider provider
) {

    public Transaction handle(CreateTransactionCommand command) {
        Objects.requireNonNull(command);
        command.validate();
        TransactionDescription description = new TransactionDescription(command.description());
        TransactionAmount amount = new TransactionAmount(command.amount());
        TransactionType type = TransactionType.valueOf(command.type());
        TransactionEffectiveDate effectiveDate = new TransactionEffectiveDate(command.effectiveDate());
        return provider.create(description, amount, type, effectiveDate);
    }

}
