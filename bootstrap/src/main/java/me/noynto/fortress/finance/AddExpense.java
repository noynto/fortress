package me.noynto.fortress.finance;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * <b>Application Programming Interface</b>
 * <p>Cette classe permet d'ajouter une dépense.</p>
 */
public record AddExpense(
        ExpenseProvider expenseProvider,
        Expense.Label label,
        Expense.Amount amount
) implements Action<Expense> {

    public AddExpense {
        Objects.requireNonNull(expenseProvider, "Le fournisseur de dépense est requis.");
        Objects.requireNonNull(amount, "Le montant est requis.");
        Objects.requireNonNull(label, "Le libellé est requis.");
    }

    public Expense execute() throws Failed {
        this.validate();
        return this.expenseProvider.create(this.label, this.amount);
    }

    void validate() throws Failed {
        if (this.label().value().isBlank()) {
            throw new Failed("Le libellé doit être rempli.");
        }
        if (this.amount().value().compareTo(BigDecimal.ZERO) >= 0) {
            throw new Failed("Le montant est invalide.");
        }
    }

}
