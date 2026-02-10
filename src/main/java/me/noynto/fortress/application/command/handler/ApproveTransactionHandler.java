package me.noynto.fortress.application.command.handler;

import me.noynto.fortress.application.command.ApproveTransactionCommand;
import me.noynto.fortress.domain.Persistence;
import me.noynto.fortress.domain.Transaction;

import java.util.Objects;

/**
 * Handler pour l'approbation de transaction
 */
public record ApproveTransactionHandler(
        Persistence repository
) {

    public Transaction handle(ApproveTransactionCommand command) throws Transaction.NotFindable {
        Objects.requireNonNull(command);
        Transaction transaction = repository.findById(command.transactionId())
                .orElseThrow(() -> new Transaction.NotFindable("Transaction non trouvée : " + command.transactionId()));
        if (transaction.status() == Transaction.Status.APPROVED) {
            throw new IllegalStateException("La transaction est déjà approuvée");
        }
        if (transaction.status() == Transaction.Status.REJECTED) {
            throw new IllegalStateException("Impossible d'approuver une transaction rejetée");
        }
        transaction.status(Transaction.Status.APPROVED);
        return repository.save(transaction);
    }
}
