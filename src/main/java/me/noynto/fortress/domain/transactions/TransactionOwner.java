package me.noynto.fortress.domain.transactions;

import me.noynto.fortress.domain.shared.IdentityId;

import java.util.Objects;

public record TransactionOwner(
        IdentityId identityId
) {
    public TransactionOwner {
        Objects.requireNonNull(identityId);
    }
}
