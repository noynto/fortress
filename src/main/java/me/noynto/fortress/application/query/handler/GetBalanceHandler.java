package me.noynto.fortress.application.query.handler;

import me.noynto.fortress.application.query.GetBalanceQuery;
import me.noynto.fortress.domain.Balance;
import me.noynto.fortress.domain.Persistence;
import me.noynto.fortress.domain.Transaction;

import java.math.BigDecimal;
import java.util.List;

public record GetBalanceHandler(
        Persistence persistence
) {

    public Balance handle(GetBalanceQuery query) {
        List<Transaction> approvedTransactions = persistence.findByStatus(Transaction.Status.APPROVED);

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
