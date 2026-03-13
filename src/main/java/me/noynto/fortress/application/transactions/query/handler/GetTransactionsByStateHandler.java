package me.noynto.fortress.application.transactions.query.handler;

import me.noynto.fortress.application.transactions.query.GetTransactionsByStateQuery;
import me.noynto.fortress.domain.transactions.Transaction;
import me.noynto.fortress.domain.transactions.TransactionProvider;
import me.noynto.fortress.domain.transactions.TransactionState;

import java.util.List;
import java.util.Objects;

public record GetTransactionsByStateHandler(
        TransactionProvider provider
) {

    public List<Transaction> handle(GetTransactionsByStateQuery query) {
        Objects.requireNonNull(query);
        TransactionState state = resolveState(query.state());
        return provider.stream(state).toList();
    }

    private static TransactionState resolveState(String rawState) {
        try {
            return TransactionState.valueOf(rawState.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("L'état recherché n'est pas connu.", e);
        }
    }
}
