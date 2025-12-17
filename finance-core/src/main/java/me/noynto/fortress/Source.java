package me.noynto.fortress;

import java.util.Objects;

/**
 * Représentation de la source de données.
 * @param key la clé
 * @param name le nom
 */
public record Source(
        String key,
        String name
) {
    public Source {
        Objects.requireNonNull(key, "La clé de la source est requise.");
        Objects.requireNonNull(name, "Le nom de la source est requis.");
    }
}
