package me.noynto.fortress.application.command;

import me.noynto.fortress.domain.Transaction;

import java.math.BigDecimal;

/**
* Commande pour créer une transaction.
*/

public record CreateTransactionCommand(
        Transaction.Amount amount,
        Transaction.Type type,
        Transaction.Description description
) {

    public void validate() {
        if (amount == null || amount.value().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant doit être supérieur à zéro");
        }
        if (type == null) {
            throw new IllegalArgumentException("Le type de transaction est obligatoire");
        }
    }

}
