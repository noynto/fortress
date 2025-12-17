package me.noynto.fortress;

import io.helidon.webserver.http.Handler;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;

public record CreateTransactionHandler(
        CreateTransaction createTransaction
) implements Handler {

    @Override
    public void handle(ServerRequest serverRequest, ServerResponse serverResponse) throws Exception {
        if (serverRequest.requestedUri().path().path().contains("/transactions")) {
            Request request = serverRequest.content().as(Request.class);
            Transaction transaction = createTransaction.execute(request.description, LocalDate.parse(request.timestamp).atStartOfDay().toInstant(ZoneOffset.UTC), new BigDecimal(request.amount));
            serverResponse.send(transaction.source());
        }
    }

    public record Request(
            String description,
            String timestamp,
            String amount
    ) {
    }
}
