package me.noynto.fortress.domain.shared;

import java.util.Objects;

public record TransactionId(
        String value
) {
    public TransactionId {
        Objects.requireNonNull(value, "La valeur de l'identifiant de transaction est requise.");
    }
}
