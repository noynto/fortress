package me.noynto.fortress.domain.shared;

import java.util.Objects;

public record IdentityId(
        String value
) {
    public IdentityId {
        Objects.requireNonNull(value, "La valeur de l'identifiant de l'utilisateur est requise.");
    }
}
