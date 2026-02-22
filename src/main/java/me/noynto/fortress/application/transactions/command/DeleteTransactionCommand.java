package me.noynto.fortress.application.transactions.command;

import me.noynto.fortress.domain.transactions.Transaction;

public record DeleteTransactionCommand(
        Transaction.Id transactionId
) {

}
