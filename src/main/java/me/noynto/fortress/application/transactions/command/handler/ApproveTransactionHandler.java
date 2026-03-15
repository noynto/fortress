package me.noynto.fortress.application.transactions.command.handler;

import me.noynto.fortress.application.transactions.command.ApproveTransactionCommand;
import me.noynto.fortress.domain.shared.TransactionId;
import me.noynto.fortress.domain.transactions.Transaction;
import me.noynto.fortress.domain.transactions.TransactionEffectiveDate;
import me.noynto.fortress.domain.transactions.TransactionProvider;
import me.noynto.fortress.domain.transactions.TransactionState;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Handler pour l'approbation de transaction
 */
public record ApproveTransactionHandler(
        TransactionProvider provider
) {

    public Transaction handle(ApproveTransactionCommand command) {
        Objects.requireNonNull(command);
        TransactionId transactionId = new TransactionId(command.transactionId());
        Transaction transaction = provider.find(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction non trouvée : " + command.transactionId()));
        if (transaction.state() == TransactionState.APPROVED) {
            throw new IllegalStateException("La transaction est déjà approuvée.");
        }
        if (transaction.state() == TransactionState.REJECTED) {
            throw new IllegalStateException("Impossible d'approuver une transaction rejetée.");
        }
        transaction.state(TransactionState.APPROVED);
        if (LocalDate.now().isBefore(transaction.effectiveDate().value())) {
            transaction.effectiveDate(new TransactionEffectiveDate(LocalDate.now()));
        }
        return this.provider.update(transaction);
    }

}
