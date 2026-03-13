package me.noynto.fortress.application.transactions.query.handler;

import me.noynto.fortress.application.transactions.query.GetBalanceQuery;
import me.noynto.fortress.domain.transactions.Balance;
import me.noynto.fortress.domain.transactions.TransactionProvider;
import me.noynto.fortress.domain.transactions.TransactionState;
import me.noynto.fortress.domain.transactions.TransactionType;

import java.math.BigDecimal;
import java.util.Objects;

public record GetBalanceHandler(
        TransactionProvider provider
) {

    public Balance handle(GetBalanceQuery query) {
        Objects.requireNonNull(query);
        return new Balance(
                this.provider
                        .stream()
                        .filter(transaction -> Objects.equals(transaction.state(), TransactionState.APPROVED))
                        .reduce(
                                BigDecimal.ZERO,
                                (accumulator, item) -> item.type() == TransactionType.CREDIT ? accumulator.add(item.amount().value()) : accumulator.subtract(item.amount().value()),
                                BigDecimal::add
                        )
        );
    }
}
