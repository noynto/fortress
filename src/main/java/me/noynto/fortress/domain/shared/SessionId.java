package me.noynto.fortress.domain.shared;

import java.util.Objects;

public record SessionId(
        String value
) {
    public SessionId {
        Objects.requireNonNull(value, "La valeur de l'identifiant de session est requise.");
    }
}
