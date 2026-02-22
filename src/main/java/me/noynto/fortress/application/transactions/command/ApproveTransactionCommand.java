package me.noynto.fortress.application.transactions.command;

import me.noynto.fortress.domain.transactions.Transaction;

/**
 * Commande pour approuver une transaction
 */
public record ApproveTransactionCommand(
        Transaction.Id transactionId
) {

}
