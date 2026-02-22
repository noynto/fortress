package me.noynto.fortress.domain.transactions;

import java.util.List;
import java.util.Optional;

public interface TransactionProvider {

    Transaction create(Transaction transaction);

    List<Transaction> streamAll();

    Optional<Transaction> findById(Transaction.Id id);

    void deleteById(Transaction transaction);

    List<Transaction> findByStatus(Transaction.Status status);
}
