package me.noynto.fortress.domain.identities;

import java.util.Objects;

public record IdentityPassword(
        String value
) {
    public IdentityPassword {
        Objects.requireNonNull(value);
    }
}
