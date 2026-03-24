package me.noynto.fortress.domain.users;

import java.util.Objects;

public record UserElectronicAddress(
        String value
) {
    public UserElectronicAddress {
        Objects.requireNonNull(value);
    }
}
