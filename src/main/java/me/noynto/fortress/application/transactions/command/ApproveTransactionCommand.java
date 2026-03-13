package me.noynto.fortress.application.transactions.command;

/**
 * Commande pour approuver une transaction
 */
public record ApproveTransactionCommand(
        String transactionId
) {

}
