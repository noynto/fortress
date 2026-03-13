package me.noynto.fortress.domain.shared;

import java.util.Objects;

public record CategoryId(
        String value
) {
    public CategoryId {
        Objects.requireNonNull(value, "La valeur de l'identifiant de catégorie est requise.");
    }
}
