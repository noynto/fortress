package me.noynto.fortress.application.transactions.query.handler;

import me.noynto.fortress.application.transactions.query.GetAllTransactionsQuery;
import me.noynto.fortress.domain.shared.IdentityId;
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
        return this.provider.stream().filter(creatorOfTransactionEqualsTo(query.identityId())).toList();
    }

    private static Predicate<Transaction> creatorOfTransactionEqualsTo(IdentityId identityId) {
        return transaction -> Objects.equals(transaction.owner().identityId(), identityId);
    }

}
