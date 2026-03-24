package me.noynto.fortress.infrastructure.controller.view;

import io.helidon.http.HeaderNames;
import io.helidon.http.Status;
import io.helidon.webserver.http.HttpFeature;
import io.helidon.webserver.http.HttpRouting;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import me.noynto.fortress.application.transactions.command.ApproveTransactionCommand;
import me.noynto.fortress.application.transactions.command.CreateTransactionCommand;
import me.noynto.fortress.application.transactions.command.DeleteTransactionCommand;
import me.noynto.fortress.application.transactions.command.RejectTransactionCommand;
import me.noynto.fortress.application.transactions.command.handler.ApproveTransactionHandler;
import me.noynto.fortress.application.transactions.command.handler.CreateTransactionHandler;
import me.noynto.fortress.application.transactions.command.handler.DeleteTransactionHandler;
import me.noynto.fortress.application.transactions.command.handler.RejectTransactionHandler;
import me.noynto.fortress.application.transactions.query.GetAllTransactionsQuery;
import me.noynto.fortress.application.transactions.query.handler.GetAllTransactionsHandler;
import me.noynto.fortress.domain.sessions.Session;
import me.noynto.fortress.domain.transactions.Transaction;
import me.noynto.fortress.infrastructure.controller.filter.SessionFilter;
import me.noynto.fortress.infrastructure.controller.view.config.TemplateRenderer;
import me.noynto.fortress.infrastructure.controller.view.helper.FormDataParser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur pour le rendu HTML des transactions avec JTE
 */
public record TransactionViewController(
        TemplateRenderer templateRenderer,
        SessionFilter sessionFilter,
        CreateTransactionHandler createTransactionHandler,
        ApproveTransactionHandler approveTransactionHandler,
        RejectTransactionHandler rejectTransactionHandler,
        DeleteTransactionHandler deleteTransactionHandler,
        GetAllTransactionsHandler getAllTransactionsHandler
) implements HttpFeature {

    @Override
    public void setup(HttpRouting.Builder builder) {
        builder
                .get("/transactions", sessionFilter,this::showAllTransactions)
                .get("/transactions/new", sessionFilter,this::showNewTransactionForm)
                .post("/transactions", sessionFilter, this::createTransaction)
                .post("/transactions/{transactionId}/approve", sessionFilter, this::approveTransaction)
                .post("/transactions/{transactionId}/reject", sessionFilter, this::rejectTransaction)
                .post("/transactions/{transactionId}/delete", sessionFilter, this::deleteTransaction);
    }

    // ============= PAGES (Queries) =============

    private void showAllTransactions(ServerRequest req, ServerResponse res) {
        Session session = req.context().get(Session.class).orElseThrow();
        List<Transaction> transactions = getAllTransactionsHandler.handle(new GetAllTransactionsQuery(session.userId()));
        Map<String, Object> params = new HashMap<>();
        params.put("transactions", transactions);
        String html = templateRenderer.render("page/transactions.jte", params);
        res.header(HeaderNames.CONTENT_TYPE, "text/html");
        res.send(html);
    }

    private void showNewTransactionForm(ServerRequest req, ServerResponse res) {
        String html = templateRenderer.render("page/new-transaction.jte");
        res.header(HeaderNames.CONTENT_TYPE, "text/html");
        res.send(html);
    }

    // ============= ACTIONS (Commands) =============

    private void createTransaction(ServerRequest req, ServerResponse res) {
        Session session = req.context().get(Session.class).orElseThrow();
        try {
            // Parser les données du formulaire
            Map<String, String> formData = FormDataParser.execute(req.content().as(String.class));
            CreateTransactionCommand command = new CreateTransactionCommand(
                    new BigDecimal(formData.get("amount")),
                    formData.get("type"),
                    formData.getOrDefault("description", ""),
                    LocalDate.parse(formData.get("effective-date")),
                    session.userId()
            );
            createTransactionHandler.handle(command);
            // Redirection vers la liste
            res.status(Status.SEE_OTHER_303);
            res.header(HeaderNames.LOCATION, "/transactions");
            res.send();
        } catch (Exception e) {
            res.status(Status.BAD_REQUEST_400);
            res.send("Erreur: " + e.getMessage());
        }
    }

    private void approveTransaction(ServerRequest req, ServerResponse res) {
        String transactionId = req.path().pathParameters().get("transactionId");
        ApproveTransactionCommand command = new ApproveTransactionCommand(transactionId);
        try {
            approveTransactionHandler.handle(command);
            res.status(Status.SEE_OTHER_303);
            res.header(HeaderNames.LOCATION, "/transactions");
            res.send();
        } catch (IllegalArgumentException e) {
            res.status(Status.NOT_FOUND_404);
            res.send("Transaction non trouvée.");
        } catch (IllegalStateException e) {
            res.status(Status.BAD_REQUEST_400);
            res.send(e.getMessage());
        }
    }

    private void rejectTransaction(ServerRequest req, ServerResponse res) {
        String transactionId = req.path().pathParameters().get("transactionId");
        RejectTransactionCommand command = new RejectTransactionCommand(transactionId);
        try {
            rejectTransactionHandler.handle(command);
            res.status(Status.SEE_OTHER_303);
            res.header(HeaderNames.LOCATION, "/transactions");
            res.send();
        } catch (IllegalArgumentException e) {
            res.status(Status.NOT_FOUND_404);
            res.send("Transaction non trouvée.");
        } catch (IllegalStateException e) {
            res.status(Status.BAD_REQUEST_400);
            res.send(e.getMessage());
        }
    }

    private void deleteTransaction(ServerRequest req, ServerResponse res) {
        String transactionId = req.path().pathParameters().get("transactionId");
        DeleteTransactionCommand command = new DeleteTransactionCommand(transactionId);
        try {
            deleteTransactionHandler.handle(command);
            res.status(Status.SEE_OTHER_303);
            res.header(HeaderNames.LOCATION, "/transactions");
            res.send();
        } catch (IllegalArgumentException e) {
            res.status(Status.NOT_FOUND_404);
            res.send("Transaction non trouvée");
        }
    }

}