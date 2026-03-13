package me.noynto.fortress.application.transactions.command.handler;

import me.noynto.fortress.application.transactions.command.CreateTransactionCommand;
import me.noynto.fortress.domain.shared.TransactionId;
import me.noynto.fortress.domain.transactions.Transaction;
import me.noynto.fortress.domain.transactions.TransactionProvider;
import me.noynto.fortress.domain.transactions.TransactionState;
import me.noynto.fortress.infrastructure.persistence.transactions.InMemoryTransactions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

class CreateTransactionHandlerTest {

    @Test
    void should_create_transaction() {
        Map<TransactionId, Transaction> store = new HashMap<>();
        TransactionProvider transactionProvider = new InMemoryTransactions(store);
        CreateTransactionHandler handler = new CreateTransactionHandler(transactionProvider);
        BigDecimal amount = new BigDecimal("100");
        String type = "CREDIT";
        String description = "Description";
        CreateTransactionCommand command = new CreateTransactionCommand(amount, type, description);
        Transaction transaction = handler.handle(command);
        Assertions.assertNotNull(transaction);
        Assertions.assertEquals(TransactionState.PENDING, transaction.state());
    }

}