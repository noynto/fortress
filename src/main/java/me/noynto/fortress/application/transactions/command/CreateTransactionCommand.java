package me.noynto.fortress.application.transactions.command;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
* Commande pour créer une transaction.
*/
public record CreateTransactionCommand(
        BigDecimal amount,
        String type,
        String description,
        LocalDate effectiveDate
) {

    public void validate() {
        Objects.requireNonNull(amount, "Le montant de la transaction est requis.");
        Objects.requireNonNull(type, "Le type de la transaction est requis.");
        Objects.requireNonNull(description, "La description de la transaction est requise.");
        Objects.requireNonNull(effectiveDate, "La date effective de la transaction est requise.");
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant de la transaction doit être supérieur à zéro");
        }
        if (type.isBlank()) {
            throw new IllegalArgumentException("Le type de la transaction est obligatoire");
        }
        if (description.isBlank()) {
            throw new IllegalArgumentException("Le description de la transaction est obligatoire");
        }
        if (effectiveDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La date effective de la transaction doit être égale ou postérieur à la création.");

        }
    }

}
