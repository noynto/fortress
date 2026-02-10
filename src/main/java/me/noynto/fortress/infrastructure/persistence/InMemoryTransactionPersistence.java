package me.noynto.fortress.infrastructure.persistence;

import me.noynto.fortress.domain.Persistence;
import me.noynto.fortress.domain.Transaction;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public record InMemoryTransactionPersistence(
        Map<Transaction.Id, Transaction> store
) implements Persistence {

    @Override
    public Transaction save(Transaction transaction) {
        return store.put(transaction.id(), transaction);
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
