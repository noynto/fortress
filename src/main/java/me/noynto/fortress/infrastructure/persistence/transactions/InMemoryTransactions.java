package me.noynto.fortress.infrastructure.persistence.transactions;

import me.noynto.fortress.domain.transactions.TransactionProvider;
import me.noynto.fortress.domain.transactions.Transaction;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public record InMemoryTransactions(
        Map<Transaction.Id, Transaction> store
) implements TransactionProvider {

    @Override
    public Transaction create(Transaction transaction) {
        store.put(transaction.id(), transaction);
        return store.get(transaction.id());
    }

    @Override
    public List<Transaction> streamAll() {
        return store.values().stream().toList();
    }

    @Override
    public Optional<Transaction> findById(Transaction.Id id) {
        return store.values().stream().filter(transaction -> transaction.id().equals(id)).findFirst();
    }

    @Override
    public void deleteById(Transaction transaction) {
        store.remove(transaction.id());
    }

    @Override
    public List<Transaction> findByStatus(Transaction.Status status) {
        return store.values().stream().filter(transaction -> transaction.status().equals(status)).toList();
    }
}
