package me.noynto.fortress.finance;

import java.util.stream.Stream;

/**
 * <b>Service Provider Interface</b>
 * <p>Cette interface correspond au fournisseur de dépense.</p>
 */
public interface ExpenseProvider extends Provider {

    /**
     * Permet de créer une dépense chez le fournisseur.
     *
     * @param label  le libellé de la dépense.
     * @param amount le montant de la dépense.
     * @return la dépense qui vient d'être créé.
     */
    Expense create(Expense.Label label, Expense.Amount amount);

    /**
     * Permet de lire les dépenses chez le fournisseur.
     * @return les dépenses.
     */
    Stream<Expense> read();

}
