package me.noynto.fortress.application.transactions.query.handler;

import me.noynto.fortress.application.transactions.query.GetAllTransactionsQuery;
import me.noynto.fortress.domain.transactions.TransactionProvider;
import me.noynto.fortress.domain.transactions.Transaction;

import java.util.List;
import java.util.Objects;

public record GetAllTransactionsHandler(
        TransactionProvider repository
) {

    public List<Transaction> handle(GetAllTransactionsQuery query) {
        Objects.requireNonNull(query);
        return repository.streamAll();
    }

}
