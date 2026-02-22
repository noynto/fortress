package me.noynto.fortress.application.transactions.command.handler;

import me.noynto.fortress.application.transactions.command.RejectTransactionCommand;
import me.noynto.fortress.domain.transactions.TransactionProvider;
import me.noynto.fortress.domain.transactions.Transaction;

import java.util.Objects;


/**
 * Handler pour le rejet de transaction
 */
public record RejectTransactionHandler(
        TransactionProvider repository
) {

    public Transaction handle(RejectTransactionCommand command) throws Transaction.NotFindable {
        Objects.requireNonNull(command);
        Transaction transaction = repository.findById(command.transactionId())
                .orElseThrow(() -> new Transaction.NotFindable("Transaction non trouvée : " + command.transactionId()));
        if (transaction.status() == Transaction.Status.REJECTED) {
            throw new IllegalStateException("La transaction est déjà rejetée");
        }
        if (transaction.status() == Transaction.Status.APPROVED) {
            throw new IllegalStateException("Impossible de rejeter une transaction approuvée");
        }
        transaction.status(Transaction.Status.REJECTED);
        return repository.create(transaction);
    }
}
