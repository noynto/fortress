package me.noynto.fortress.application.transactions.query.handler;

import me.noynto.fortress.application.QueryHandler;
import me.noynto.fortress.domain.transactions.TransactionProvider;
import me.noynto.fortress.domain.transactions.TransactionState;
import me.noynto.fortress.domain.transactions.TransactionType;

import java.math.BigDecimal;
import java.util.Set;

public record CalculateForecastBalance(
        TransactionProvider provider
) implements QueryHandler<BigDecimal> {

    @Override
    public BigDecimal handle() {
        Set<TransactionState> forecastStates = Set.of(TransactionState.PENDING, TransactionState.APPROVED);
        return this.provider
                .stream()
                .filter(transaction -> forecastStates.contains(transaction.state()))
                .reduce(
                        BigDecimal.ZERO,
                        (accumulator, item) -> item.type() == TransactionType.CREDIT ? accumulator.add(item.amount().value()) : accumulator.subtract(item.amount().value()),
                        BigDecimal::add
                );
    }
}
