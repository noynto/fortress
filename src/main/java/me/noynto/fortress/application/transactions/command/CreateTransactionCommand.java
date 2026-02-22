package me.noynto.fortress.application.transactions.command;

import java.math.BigDecimal;

/**
* Commande pour créer une transaction.
*/
public record CreateTransactionCommand(
        BigDecimal amount,
        String type,
        String description
) {

    public void validate() {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant doit être supérieur à zéro");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Le type de transaction est obligatoire");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Le description de transaction est obligatoire");
        }
    }

}
