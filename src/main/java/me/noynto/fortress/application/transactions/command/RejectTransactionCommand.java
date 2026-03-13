package me.noynto.fortress.application.transactions.command;

/**
 * Commande pour rejeter une transaction
 */
public record RejectTransactionCommand(
        String transactionId
) {

}