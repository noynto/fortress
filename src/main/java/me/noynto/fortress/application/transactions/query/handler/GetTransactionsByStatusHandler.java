package me.noynto.fortress.application.transactions.query.handler;

import me.noynto.fortress.application.transactions.query.GetTransactionsByStatusQuery;
import me.noynto.fortress.domain.transactions.TransactionProvider;
import me.noynto.fortress.domain.transactions.Transaction;

import java.util.List;

public record GetTransactionsByStatusHandler(
        TransactionProvider transactionProvider
) {

    public List<Transaction> handle(GetTransactionsByStatusQuery query) {
        return transactionProvider.findByStatus(query.status());
    }
}
