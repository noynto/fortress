package me.noynto.fortress.domain.identities;

import java.util.Objects;

public record IdentityElectronicAddress(
        String value
) {
    public IdentityElectronicAddress {
        Objects.requireNonNull(value);
    }
}
