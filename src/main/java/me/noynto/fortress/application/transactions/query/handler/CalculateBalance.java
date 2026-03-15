package me.noynto.fortress.application.transactions.query.handler;

import me.noynto.fortress.application.QueryHandler;
import me.noynto.fortress.domain.transactions.TransactionProvider;
import me.noynto.fortress.domain.transactions.TransactionState;
import me.noynto.fortress.domain.transactions.TransactionType;

import java.math.BigDecimal;
import java.util.Objects;

public record CalculateBalance(
        TransactionProvider provider
) implements QueryHandler<BigDecimal> {

    @Override
    public BigDecimal handle() {
        return this.provider
                .stream()
                .filter(transaction -> Objects.equals(transaction.state(), TransactionState.APPROVED))
                .reduce(
                        BigDecimal.ZERO,
                        (accumulator, item) -> item.type() == TransactionType.CREDIT ? accumulator.add(item.amount().value()) : accumulator.subtract(item.amount().value()),
                        BigDecimal::add
                );
    }
}
