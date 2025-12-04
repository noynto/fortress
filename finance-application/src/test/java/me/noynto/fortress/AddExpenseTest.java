package me.noynto.fortress;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddExpenseTest {

    private ExpenseProvider expenseProvider;
    private Expense expense;
    private AddExpense addExpense;

    @BeforeEach
    void setUp() {
        this.expenseProvider = mock(ExpenseProvider.class);
        this.expense = mock(Expense.class);
    }

    @Test
    void execute_with_validation_failed() throws Action.Failed {

        // GIVEN
        Expense.Label label = mock(Expense.Label.class);
        Expense.Amount amount = mock(Expense.Amount.class);
        addExpense = spy(new AddExpense(this.expenseProvider, label, amount));
        doReturn("").when(label).value();
        doReturn(this.expense).when(this.expenseProvider).create(any(), any());

        // WHEN
        Assertions.assertThrows(Action.Failed.class, () -> addExpense.execute());

        // THEN
        verify(addExpense, times(1)).validate();
        verify(expenseProvider, never()).create(any(), any());
    }

}