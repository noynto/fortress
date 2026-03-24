package me.noynto.fortress.application.transactions.command.handler;

import me.noynto.fortress.application.transactions.command.CreateTransactionCommand;
import me.noynto.fortress.domain.shared.TransactionId;
import me.noynto.fortress.domain.shared.UserId;
import me.noynto.fortress.domain.transactions.Transaction;
import me.noynto.fortress.domain.transactions.TransactionProvider;
import me.noynto.fortress.domain.transactions.TransactionState;
import me.noynto.fortress.infrastructure.persistence.transactions.InMemoryTransactions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

class CreateTransactionHandlerTest {

    @Test
    void should_create_transaction() {
        Map<TransactionId, Transaction> store = new HashMap<>();
        Clock clock = Clock.systemUTC();
        TransactionProvider transactionProvider = new InMemoryTransactions(store, clock);
        CreateTransactionHandler handler = new CreateTransactionHandler(transactionProvider, clock);
        BigDecimal amount = new BigDecimal("100");
        String type = "CREDIT";
        String description = "Description";
        LocalDate effectiveDate = LocalDate.now().plusDays(3);
        UserId userId = new UserId("USER_ID");
        CreateTransactionCommand command = new CreateTransactionCommand(amount, type, description, effectiveDate, userId);
        Transaction transaction = handler.handle(command);
        Assertions.assertNotNull(transaction);
        Assertions.assertEquals(TransactionState.PENDING, transaction.state());
    }

}