package me.noynto.fortress.domain.transactions;

import java.time.LocalDate;
import java.util.Objects;

public record TransactionCreationDate(
        LocalDate value
) {
    public TransactionCreationDate {
        Objects.requireNonNull(value);
    }
}
