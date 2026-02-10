package me.noynto.fortress.application.query;

import me.noynto.fortress.domain.Transaction;

public record GetTransactionsByStatusQuery(
        Transaction.Status status
) {
}
