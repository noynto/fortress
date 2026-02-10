package me.noynto.fortress.application.command;

import me.noynto.fortress.domain.Transaction;

/**
 * Commande pour rejeter une transaction
 */
public record RejectTransactionCommand(
        Transaction.Id transactionId
) {

}