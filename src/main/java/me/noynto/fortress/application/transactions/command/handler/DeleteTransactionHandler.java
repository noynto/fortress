package me.noynto.fortress.application.transactions.command.handler;

import me.noynto.fortress.application.transactions.command.DeleteTransactionCommand;
import me.noynto.fortress.domain.transactions.Transaction;
import me.noynto.fortress.domain.transactions.TransactionProvider;

import java.util.Objects;

/**
 * Handler pour la création de transaction
 */
public record DeleteTransactionHandler(
        TransactionProvider repository
) {

    public void handle(DeleteTransactionCommand command) throws Transaction.NotFindable {
        Objects.requireNonNull(command);
        Transaction transaction = repository.findById(command.transactionId())
                .orElseThrow(() -> new Transaction.NotFindable("Transaction non trouvée : " + command.transactionId()));
        repository.deleteById(transaction);
    }
}
