package me.noynto.fortress.bootstrap;


import me.noynto.fortress.ExpenseProvider;
import me.noynto.fortress.finance.infrastructure.Knight;
import me.noynto.fortress.finance.infrastructure.MongoExpense;

public class Application {

    public static void main(String[] args) {
        ExpenseProvider expenseProvider = MongoExpense.init();
        Knight knight = Knight.init(expenseProvider);
        knight.start();
    }
}
