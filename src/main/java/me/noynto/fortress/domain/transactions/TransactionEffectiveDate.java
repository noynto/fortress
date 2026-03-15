package me.noynto.fortress.domain.transactions;

import java.time.LocalDate;
import java.util.Objects;

public record TransactionEffectiveDate(
        LocalDate value
) {
    public TransactionEffectiveDate {
        Objects.requireNonNull(value);
    }
}
