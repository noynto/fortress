package me.noynto.fortress.domain.transactions;

import java.time.LocalDate;
import java.util.Objects;

public record TransactionIssueDate(
        LocalDate value
) {
    public TransactionIssueDate {
        Objects.requireNonNull(value);
    }
}
