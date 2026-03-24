package me.noynto.fortress.domain.users;

import java.util.Objects;

public record UserPassword(
        String value
) {
    public UserPassword {
        Objects.requireNonNull(value);
    }
}
