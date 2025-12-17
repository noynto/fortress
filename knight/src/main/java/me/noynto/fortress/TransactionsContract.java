package me.noynto.fortress;

import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;

public class TransactionsContract implements HttpService {

    public static final String PATH = "/transactions";
    private final CreateTransaction createTransaction;

    public TransactionsContract(CreateTransaction createTransaction) {
        this.createTransaction = createTransaction;
    }

    @Override
    public void routing(HttpRules httpRules) {
        httpRules.post(new CreateTransactionHandler(createTransaction));
    }
}
