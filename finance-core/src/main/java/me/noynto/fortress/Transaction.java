package me.noynto.fortress;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public record Transaction(
        Source source,
        String description,
        Instant timestamp,
        BigDecimal amount
) {
    public Transaction {
        Objects.requireNonNull(source, "La source de la transaction est requise.");
        Objects.requireNonNull(description, "La description de la transaction est requise.");
        Objects.requireNonNull(timestamp, "L'horodatage de la transaction est requis.");
        Objects.requireNonNull(amount, "Le montant de la transaction est requis.");
    }
}
