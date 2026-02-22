package me.noynto.fortress.application.transactions.query;

import me.noynto.fortress.domain.transactions.Transaction;

public record GetTransactionsByStatusQuery(
        Transaction.Status status
) {
}
