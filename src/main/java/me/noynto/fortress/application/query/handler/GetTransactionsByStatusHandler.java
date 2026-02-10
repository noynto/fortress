package me.noynto.fortress.application.query.handler;

import me.noynto.fortress.application.query.GetTransactionsByStatusQuery;
import me.noynto.fortress.domain.Persistence;
import me.noynto.fortress.domain.Transaction;

import java.util.List;

public record GetTransactionsByStatusHandler(
        Persistence persistence
) {

    public List<Transaction> handle(GetTransactionsByStatusQuery query) {
        return persistence.findByStatus(query.status());
    }
}
