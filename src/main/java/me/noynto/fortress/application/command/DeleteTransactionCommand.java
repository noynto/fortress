package me.noynto.fortress.application.command;

import me.noynto.fortress.domain.Transaction;

public record DeleteTransactionCommand(
        Transaction.Id transactionId
) {

}
