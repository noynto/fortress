package me.noynto.fortress.infrastructure.controller.view;

import io.helidon.http.HeaderNames;
import io.helidon.http.Status;
import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import me.noynto.fortress.application.command.ApproveTransactionCommand;
import me.noynto.fortress.application.command.CreateTransactionCommand;
import me.noynto.fortress.application.command.DeleteTransactionCommand;
import me.noynto.fortress.application.command.RejectTransactionCommand;
import me.noynto.fortress.application.command.handler.ApproveTransactionHandler;
import me.noynto.fortress.application.command.handler.CreateTransactionHandler;
import me.noynto.fortress.application.command.handler.DeleteTransactionHandler;
import me.noynto.fortress.application.command.handler.RejectTransactionHandler;
import me.noynto.fortress.application.query.GetAllTransactionsQuery;
import me.noynto.fortress.application.query.GetBalanceQuery;
import me.noynto.fortress.application.query.GetTransactionsByStatusQuery;
import me.noynto.fortress.application.query.handler.GetAllTransactionsHandler;
import me.noynto.fortress.application.query.handler.GetBalanceHandler;
import me.noynto.fortress.application.query.handler.GetTransactionsByStatusHandler;
import me.noynto.fortress.domain.Balance;
import me.noynto.fortress.domain.Transaction;
import me.noynto.fortress.infrastructure.controller.view.config.TemplateRenderer;

import java.math.BigDecimal;
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
    private final GetTransactionsByStatusHandler getTransactionsByStatusHandler;
    private final GetBalanceHandler getBalanceHandler;

    public TransactionViewController(
            TemplateRenderer templateRenderer,
            CreateTransactionHandler createTransactionHandler,
            ApproveTransactionHandler approveTransactionHandler,
            RejectTransactionHandler rejectTransactionHandler,
            DeleteTransactionHandler deleteTransactionHandler,
            GetAllTransactionsHandler getAllTransactionsHandler,
            GetTransactionsByStatusHandler getTransactionsByStatusHandler,
            GetBalanceHandler getBalanceHandler) {
        this.templateRenderer = templateRenderer;
        this.createTransactionHandler = createTransactionHandler;
        this.approveTransactionHandler = approveTransactionHandler;
        this.rejectTransactionHandler = rejectTransactionHandler;
        this.deleteTransactionHandler = deleteTransactionHandler;
        this.getAllTransactionsHandler = getAllTransactionsHandler;
        this.getTransactionsByStatusHandler = getTransactionsByStatusHandler;
        this.getBalanceHandler = getBalanceHandler;
    }

    @Override
    public void routing(HttpRules rules) {
        rules
                // Pages
                .get("/", this::showAllTransactions)
                .get("/status/{status}", this::showTransactionsByStatus)
                .get("/new", this::showNewTransactionForm)
                // Actions
                .post("/transactions/create", this::createTransaction)
                .post("/transactions/{transactionId}/approve", this::approveTransaction)
                .post("/transactions/{transactionId}/reject", this::rejectTransaction)
                .post("/transactions/{transactionId}/delete", this::deleteTransaction);
    }

    // ============= PAGES (Queries) =============

    private void showAllTransactions(ServerRequest req, ServerResponse res) {
        GetAllTransactionsQuery query = new GetAllTransactionsQuery();
        List<Transaction> transactions = getAllTransactionsHandler.handle(query);
        GetBalanceQuery balanceQuery = new GetBalanceQuery();
        Balance balance = getBalanceHandler.handle(balanceQuery);
        Map<String, Object> params = new HashMap<>();
        params.put("transactions", transactions);
        params.put("balance", balance);
        String html = templateRenderer.render("page/transactions.jte", params);
        res.header(HeaderNames.CONTENT_TYPE, "text/html");
        res.send(html);
    }

    private void showTransactionsByStatus(ServerRequest req, ServerResponse res) {
        try {
            String statusParam = req.path().pathParameters().get("status");
            Transaction.Status status = Transaction.Status.valueOf(statusParam.toUpperCase());
            GetTransactionsByStatusQuery query = new GetTransactionsByStatusQuery(status);
            List<Transaction> transactions = getTransactionsByStatusHandler.handle(query);
            GetBalanceQuery balanceQuery = new GetBalanceQuery();
            Balance balance = getBalanceHandler.handle(balanceQuery);
            Map<String, Object> params = new HashMap<>();
            params.put("transactions", transactions);
            params.put("balance", balance);
            String html = templateRenderer.render("page/transactions.jte", params);
            res.header(HeaderNames.CONTENT_TYPE, "text/html");
            res.send(html);
        } catch (IllegalArgumentException e) {
            res.status(Status.BAD_REQUEST_400).send("Statut invalide");
        }
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
            Transaction.Amount amount = new Transaction.Amount(new BigDecimal(formData.get("amount")));
            Transaction.Type type = Transaction.Type.valueOf(formData.get("type"));
            Transaction.Description description = new Transaction.Description(formData.getOrDefault("description", ""));
            CreateTransactionCommand command = new CreateTransactionCommand(amount, type, description);
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
        Transaction.Id transactionId = new Transaction.Id(req.path().pathParameters().get("transactionId"));
        ApproveTransactionCommand command = new ApproveTransactionCommand(transactionId);
        try {
            approveTransactionHandler.handle(command);
            res.status(Status.SEE_OTHER_303);
            res.header(HeaderNames.LOCATION, "/");
            res.send();
        } catch (Transaction.NotFindable e) {
            res.status(Status.NOT_FOUND_404);
            res.send("Transaction non trouvée.");
        } catch (IllegalStateException e) {
            res.status(Status.BAD_REQUEST_400);
            res.send(e.getMessage());
        }
    }

    private void rejectTransaction(ServerRequest req, ServerResponse res) {
        Transaction.Id transactionId = new Transaction.Id(req.path().pathParameters().get("transactionId"));
        RejectTransactionCommand command = new RejectTransactionCommand(transactionId);
        try {
            rejectTransactionHandler.handle(command);
            res.status(Status.SEE_OTHER_303);
            res.header(HeaderNames.LOCATION, "/");
            res.send();
        } catch (Transaction.NotFindable e) {
            res.status(Status.NOT_FOUND_404);
            res.send("Transaction non trouvée.");
        } catch (IllegalStateException e) {
            res.status(Status.BAD_REQUEST_400);
            res.send(e.getMessage());
        }
    }

    private void deleteTransaction(ServerRequest req, ServerResponse res) {
        Transaction.Id transactionId = new Transaction.Id(req.path().pathParameters().get("transactionId"));
        DeleteTransactionCommand command = new DeleteTransactionCommand(transactionId);
        try {
            deleteTransactionHandler.handle(command);
            res.status(Status.SEE_OTHER_303);
            res.header(HeaderNames.LOCATION, "/");
            res.send();
        } catch (Transaction.NotFindable e) {
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