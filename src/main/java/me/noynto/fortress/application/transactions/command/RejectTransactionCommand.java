package me.noynto.fortress.application.transactions.command;

import me.noynto.fortress.domain.transactions.Transaction;

/**
 * Commande pour rejeter une transaction
 */
public record RejectTransactionCommand(
        Transaction.Id transactionId
) {

}