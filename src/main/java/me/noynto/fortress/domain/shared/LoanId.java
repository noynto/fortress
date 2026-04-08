package me.noynto.fortress.domain.shared;

import java.util.Objects;

public record LoanId(
        String value
) {
    public LoanId {
        Objects.requireNonNull(value, "La valeur de l'identifiant de prêt est requise.");
    }
}
