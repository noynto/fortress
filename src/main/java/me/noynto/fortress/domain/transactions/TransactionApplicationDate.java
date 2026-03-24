package me.noynto.fortress.domain.transactions;

import java.time.LocalDate;
import java.util.Objects;

public record TransactionApplicationDate(
        LocalDate value
) {
    public TransactionApplicationDate {
        Objects.requireNonNull(value);
    }
}
