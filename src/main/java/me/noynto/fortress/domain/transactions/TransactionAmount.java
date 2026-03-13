package me.noynto.fortress.domain.transactions;

import java.math.BigDecimal;
import java.util.Objects;

public record TransactionAmount(
        BigDecimal value
) {
    public TransactionAmount {
        Objects.requireNonNull(value);
    }
}
