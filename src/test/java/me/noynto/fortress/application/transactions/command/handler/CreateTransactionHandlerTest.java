package me.noynto.fortress.application.transactions.command.handler;

import me.noynto.fortress.application.transactions.command.CreateTransactionCommand;
import me.noynto.fortress.domain.transactions.Transaction;
import me.noynto.fortress.domain.transactions.TransactionProvider;
import me.noynto.fortress.infrastructure.persistence.transactions.InMemoryTransactions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CreateTransactionHandlerTest {

    @Test
    void should_create_transaction() {
        Map<Transaction.Id,Transaction> store =  new HashMap<>();
        TransactionProvider transactionProvider = new InMemoryTransactions(store);
        CreateTransactionHandler handler = new CreateTransactionHandler(transactionProvider);
        BigDecimal amount = new BigDecimal("100");
        String type = "CREDIT";
        String description = "Description";
        CreateTransactionCommand command = new CreateTransactionCommand(amount, type, description);
        Transaction transaction = handler.handle(command);
        Assertions.assertNotNull(transaction);
        Assertions.assertEquals(Transaction.Status.PENDING,transaction.status());
    }

}