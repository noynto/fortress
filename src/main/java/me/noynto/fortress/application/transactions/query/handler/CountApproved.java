package me.noynto.fortress.application.transactions.query.handler;

import me.noynto.fortress.application.QueryHandler;
import me.noynto.fortress.domain.transactions.TransactionProvider;
import me.noynto.fortress.domain.transactions.TransactionState;

import java.util.Objects;

public record CountApproved(
        TransactionProvider provider
) implements QueryHandler<Long> {

    @Override
    public Long handle() {
        return this.provider
                .stream()
                .filter(transaction -> Objects.equals(transaction.state(), TransactionState.APPROVED))
                .count();
    }
}
