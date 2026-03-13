package me.noynto.fortress.domain.transactions;

import java.util.Objects;

public record TransactionDescription(
        String value
) {
    public TransactionDescription {
        Objects.requireNonNull(value);
    }
}
