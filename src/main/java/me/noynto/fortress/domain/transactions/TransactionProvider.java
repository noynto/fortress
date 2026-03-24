package me.noynto.fortress.domain.transactions;

import me.noynto.fortress.domain.shared.TransactionId;

import java.util.Optional;
import java.util.stream.Stream;

public interface TransactionProvider {

    Transaction create(
            TransactionDescription description,
            TransactionAmount amount,
            TransactionType type,
            TransactionOwner owner,
            TransactionApplicationDate applicationDate
    );

    Stream<Transaction> stream();

    Optional<Transaction> find(TransactionId id);

    void delete(TransactionId id);

    Stream<Transaction> stream(TransactionState state);

    Transaction update(Transaction transaction);
}
