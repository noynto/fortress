package me.noynto.fortress.application.transactions.query.handler;

import me.noynto.fortress.application.QueryHandler;
import me.noynto.fortress.domain.transactions.*;

import java.math.BigDecimal;
import java.util.Objects;

public record CalculatePendingDebits(
        TransactionProvider provider
) implements QueryHandler<BigDecimal> {

    @Override
    public BigDecimal handle() {
        return this.provider
                .stream()
                .filter(transaction -> Objects.equals(transaction.type(), TransactionType.DEBIT))
                .filter(transaction -> Objects.equals(transaction.state(),TransactionState.PENDING))
                .map(Transaction::amount)
                .map(TransactionAmount::value)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
