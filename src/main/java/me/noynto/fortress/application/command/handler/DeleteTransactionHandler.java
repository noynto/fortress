package me.noynto.fortress.application.command.handler;

import me.noynto.fortress.application.command.DeleteTransactionCommand;
import me.noynto.fortress.domain.Transaction;
import me.noynto.fortress.domain.Persistence;

import java.util.Objects;

/**
 * Handler pour la création de transaction
 */
public record DeleteTransactionHandler(
        Persistence repository
) {

    public void handle(DeleteTransactionCommand command) throws Transaction.NotFindable {
        Objects.requireNonNull(command);
        Transaction transaction = repository.findById(command.transactionId())
                .orElseThrow(() -> new Transaction.NotFindable("Transaction non trouvée : " + command.transactionId()));
        repository.deleteById(transaction);
    }
}
