package me.noynto.fortress.application.query.handler;

import me.noynto.fortress.application.query.GetAllTransactionsQuery;
import me.noynto.fortress.domain.Persistence;
import me.noynto.fortress.domain.Transaction;

import java.util.List;
import java.util.Objects;

public record GetAllTransactionsHandler(
        Persistence repository
) {

    public List<Transaction> handle(GetAllTransactionsQuery query) {
        Objects.requireNonNull(query);
        return repository.streamAll();
    }

}
