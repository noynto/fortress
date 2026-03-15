package me.noynto.fortress.infrastructure.controller.view;

import io.helidon.http.HeaderNames;
import io.helidon.http.Status;
import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;
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
import me.noynto.fortress.domain.transactions.Transaction;
import me.noynto.fortress.infrastructure.controller.view.config.TemplateRenderer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur pour le rendu HTML des transactions avec JTE
 */
public class TransactionViewController implements HttpService {

    private final TemplateRenderer templateRenderer;

    // Command Handlers
    private final CreateTransactionHandler createTransactionHandler;
    private final ApproveTransactionHandler approveTransactionHandler;
    private final RejectTransactionHandler rejectTransactionHandler;
    private final DeleteTransactionHandler deleteTransactionHandler;

    // Query Handlers
    private final GetAllTransactionsHandler getAllTransactionsHandler;

    public TransactionViewController(
            TemplateRenderer templateRenderer,
            CreateTransactionHandler createTransactionHandler,
            ApproveTransactionHandler approveTransactionHandler,
            RejectTransactionHandler rejectTransactionHandler,
            DeleteTransactionHandler deleteTransactionHandler,
            GetAllTransactionsHandler getAllTransactionsHandler
    ) {
        this.templateRenderer = templateRenderer;
        this.createTransactionHandler = createTransactionHandler;
        this.approveTransactionHandler = approveTransactionHandler;
        this.rejectTransactionHandler = rejectTransactionHandler;
        this.deleteTransactionHandler = deleteTransactionHandler;
        this.getAllTransactionsHandler = getAllTransactionsHandler;
    }

    @Override
    public void routing(HttpRules rules) {
        rules
                // Pages
                .get("/", this::showAllTransactions)
                .get("/new", this::showNewTransactionForm)
                // Actions
                .post("/create", this::createTransaction)
                .post("/{transactionId}/approve", this::approveTransaction)
                .post("/{transactionId}/reject", this::rejectTransaction)
                .post("/{transactionId}/delete", this::deleteTransaction);
    }

    // ============= PAGES (Queries) =============

    private void showAllTransactions(ServerRequest req, ServerResponse res) {
        List<Transaction> transactions = getAllTransactionsHandler.handle(new GetAllTransactionsQuery());
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
        try {
            // Parser les données du formulaire
            Map<String, String> formData = parseFormData(req.content().as(String.class));
            CreateTransactionCommand command = new CreateTransactionCommand(
                    new BigDecimal(formData.get("amount")),
                    formData.get("type"),
                    formData.getOrDefault("description", ""),
                    LocalDate.parse(formData.get("effective-date"))
            );
            createTransactionHandler.handle(command);
            // Redirection vers la liste
            res.status(Status.SEE_OTHER_303);
            res.header(HeaderNames.LOCATION, "/");
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
            res.header(HeaderNames.LOCATION, "/");
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
            res.header(HeaderNames.LOCATION, "/");
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
            res.header(HeaderNames.LOCATION, "/");
            res.send();
        } catch (IllegalArgumentException e) {
            res.status(Status.NOT_FOUND_404);
            res.send("Transaction non trouvée");
        }
    }

    // ============= HELPERS =============

    private Map<String, String> parseFormData(String body) {
        Map<String, String> data = new java.util.HashMap<>();
        if (body == null || body.isEmpty()) {
            return data;
        }

        for (String pair : body.split("&")) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                data.put(
                        java.net.URLDecoder.decode(keyValue[0], java.nio.charset.StandardCharsets.UTF_8),
                        java.net.URLDecoder.decode(keyValue[1], java.nio.charset.StandardCharsets.UTF_8)
                );
            }
        }
        return data;
    }
}