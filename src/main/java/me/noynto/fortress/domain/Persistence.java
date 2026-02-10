package me.noynto.fortress.domain;

import java.util.List;
import java.util.Optional;

public interface Persistence {

    Transaction save(Transaction transaction);

    List<Transaction> streamAll();

    Optional<Transaction> findById(Transaction.Id id);

    void deleteById(Transaction transaction);

    List<Transaction> findByStatus(Transaction.Status status);
}
