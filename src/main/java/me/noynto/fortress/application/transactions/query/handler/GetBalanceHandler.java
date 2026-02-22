package me.noynto.fortress.application.transactions.query.handler;

import me.noynto.fortress.application.transactions.query.GetBalanceQuery;
import me.noynto.fortress.domain.transactions.Balance;
import me.noynto.fortress.domain.transactions.TransactionProvider;
import me.noynto.fortress.domain.transactions.Transaction;

import java.math.BigDecimal;
import java.util.List;

public record GetBalanceHandler(
        TransactionProvider transactionProvider
) {

    public Balance handle(GetBalanceQuery query) {
        List<Transaction> approvedTransactions = transactionProvider.findByStatus(Transaction.Status.APPROVED);

        BigDecimal value = BigDecimal.ZERO;
        for (Transaction transaction : approvedTransactions) {
            if (transaction.type() == Transaction.Type.CREDIT) {
                value = value.add(transaction.amount().value());
            } else {
                value = value.subtract(transaction.amount().value());
            }
        }
        return new Balance(value);
    }
}
