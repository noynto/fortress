package me.noynto.fortress.finance;

import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Bootstrap {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        Path path = FileSystems.getDefault().getPath("expenses");
        ExpenseProvider expenseProvider = new JsonExpense(mapper, path);
        for (int i = 1; i < 100; i++) {
            try {
                Expense expense = new AddExpense(expenseProvider, new Expense.Label("Label " + i), new Expense.Amount(BigDecimal.ZERO.subtract(BigDecimal.valueOf(i)))).execute();
                System.out.println(expense.toString());
            } catch (Action.Failed e) {
                throw new RuntimeException(e);
            }
        }
    }
}
