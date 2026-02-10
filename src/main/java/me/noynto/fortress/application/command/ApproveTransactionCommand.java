package me.noynto.fortress.application.command;

import me.noynto.fortress.domain.Transaction;

/**
 * Commande pour approuver une transaction
 */
public record ApproveTransactionCommand(
        Transaction.Id transactionId
) {

}
