package me.noynto.fortress.application.transactions.query.handler;

import me.noynto.fortress.application.transactions.query.GetAllTransactionsQuery;
import me.noynto.fortress.domain.shared.UserId;
import me.noynto.fortress.domain.transactions.Transaction;
import me.noynto.fortress.domain.transactions.TransactionProvider;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public record GetAllTransactionsHandler(
        TransactionProvider provider
) {

    public List<Transaction> handle(GetAllTransactionsQuery query) {
        Objects.requireNonNull(query);
        return this.provider.stream().filter(creatorOfTransactionEqualsTo(query.userId())).toList();
    }

    private static Predicate<Transaction> creatorOfTransactionEqualsTo(UserId userId) {
        return transaction -> Objects.equals(transaction.owner().userId(), userId);
    }

}
