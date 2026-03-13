package me.noynto.fortress.application.transactions.command.handler;

import me.noynto.fortress.application.transactions.command.RejectTransactionCommand;
import me.noynto.fortress.domain.shared.TransactionId;
import me.noynto.fortress.domain.transactions.TransactionProvider;
import me.noynto.fortress.domain.transactions.Transaction;
import me.noynto.fortress.domain.transactions.TransactionState;

import java.util.Objects;


/**
 * Handler pour le rejet de transaction
 */
public record RejectTransactionHandler(
        TransactionProvider provider
) {

    public Transaction handle(RejectTransactionCommand command) {
        Objects.requireNonNull(command);
        Transaction transaction = provider.find(new TransactionId(command.transactionId()))
                .orElseThrow(() -> new IllegalArgumentException("Transaction non trouvée : " + command.transactionId()));
        if (transaction.state() == TransactionState.REJECTED) {
            throw new IllegalStateException("La transaction est déjà rejetée.");
        }
        if (transaction.state() == TransactionState.APPROVED) {
            throw new IllegalStateException("Impossible de rejeter une transaction approuvée.");
        }
        transaction.state(TransactionState.REJECTED);
        return this.provider.update(transaction);
    }
}
