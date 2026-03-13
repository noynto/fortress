package me.noynto.fortress.application.transactions.command.handler;

import me.noynto.fortress.application.transactions.command.DeleteTransactionCommand;
import me.noynto.fortress.domain.shared.TransactionId;
import me.noynto.fortress.domain.transactions.Transaction;
import me.noynto.fortress.domain.transactions.TransactionProvider;

import java.util.Objects;

/**
 * Handler pour la création de transaction
 */
public record DeleteTransactionHandler(
        TransactionProvider provider
) {

    public void handle(DeleteTransactionCommand command) {
        Objects.requireNonNull(command);
        Transaction transaction = this.provider.find(new TransactionId(command.transactionId()))
                .orElseThrow(() -> new IllegalArgumentException("Transaction non trouvée : " + command.transactionId()));
        this.provider.delete(transaction.id());
    }
}
