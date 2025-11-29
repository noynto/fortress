package me.noynto.fortress.finance.infrastructure;

import io.helidon.webserver.WebServer;
import jakarta.json.Json;
import jakarta.json.stream.JsonGenerator;
import me.noynto.fortress.AddExpense;
import me.noynto.fortress.Expense;
import me.noynto.fortress.ExpenseProvider;

/**
 * Knight est le serveur web de Fortress
 *
 * @param webServer
 * @param expenseProvider
 */
public record Knight(
        WebServer webServer,
        ExpenseProvider expenseProvider
) {

    public static Knight init(ExpenseProvider expenseProvider) {
        return new Knight(
                WebServer.builder()
                        .port(8080)
                        .routing(builder -> builder
                                .post("/expenses", (serverRequest, serverResponse) -> {
                                    Expense requestEntity = serverRequest.content().as(Expense.class);
                                    AddExpense addExpense = new AddExpense(expenseProvider, requestEntity.label(), requestEntity.amount());
                                    Expense expense = addExpense.execute();
                                    serverResponse.send(expense);
                                })
                                .get("/expenses", (serverRequest, serverResponse) -> {
                                    JsonGenerator jsonGenerator = Json.createGenerator(serverResponse.outputStream());
                                    jsonGenerator.writeStartArray();
                                    expenseProvider.read()
                                            .forEachOrdered(expense -> {
                                                jsonGenerator.writeStartObject();
                                                jsonGenerator.write("reference", expense.reference().value());
                                                jsonGenerator.writeEnd();
                                                jsonGenerator.flush();
                                            });
                                    jsonGenerator.writeEnd();
                                    jsonGenerator.flush();
                                })
                        )
                        .build(),
                expenseProvider
        );
    }

    public void start() {
        webServer.start();
    }

}
