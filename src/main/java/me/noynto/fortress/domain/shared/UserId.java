package me.noynto.fortress.domain.shared;

import java.util.Objects;

public record UserId(
        String value
) {
    public UserId {
        Objects.requireNonNull(value, "La valeur de l'identifiant de l'utilisateur est requise.");
    }
}
