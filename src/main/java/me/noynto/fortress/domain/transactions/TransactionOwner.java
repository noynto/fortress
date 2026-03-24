package me.noynto.fortress.domain.transactions;

import me.noynto.fortress.domain.shared.UserId;

import java.util.Objects;

public record TransactionOwner(
        UserId userId
) {
    public TransactionOwner {
        Objects.requireNonNull(userId);
    }
}
