package me.noynto.fortress;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.HttpRouting;
import io.helidon.webserver.staticcontent.StaticContentService;
import me.noynto.fortress.application.transactions.command.handler.ApproveTransactionHandler;
import me.noynto.fortress.application.transactions.command.handler.CreateTransactionHandler;
import me.noynto.fortress.application.transactions.command.handler.DeleteTransactionHandler;
import me.noynto.fortress.application.transactions.command.handler.RejectTransactionHandler;
import me.noynto.fortress.application.transactions.query.handler.*;
import me.noynto.fortress.domain.sessions.SessionProvider;
import me.noynto.fortress.domain.transactions.TransactionProvider;
import me.noynto.fortress.infrastructure.controller.view.DashboardViewController;
import me.noynto.fortress.infrastructure.controller.view.TransactionViewController;
import me.noynto.fortress.infrastructure.controller.view.config.TemplateRenderer;
import me.noynto.fortress.infrastructure.persistence.sessions.InMemorySessions;
import me.noynto.fortress.infrastructure.persistence.transactions.InMemoryTransactions;

import java.util.concurrent.ConcurrentHashMap;

public class Main {

    public static void main(String[] args) {
        // Infrastructure Layer : Repository
        TransactionProvider transactionProvider = new InMemoryTransactions(new ConcurrentHashMap<>());
        SessionProvider sessionProvider = new InMemorySessions(new ConcurrentHashMap<>(), BCrypt.withDefaults());

        // Application Layer : Command Handlers (écriture)
        CreateTransactionHandler createTransactionHandler = new CreateTransactionHandler(transactionProvider);
        ApproveTransactionHandler approveTransactionHandler = new ApproveTransactionHandler(transactionProvider);
        RejectTransactionHandler rejectTransactionHandler = new RejectTransactionHandler(transactionProvider);
        DeleteTransactionHandler deleteTransactionHandler = new DeleteTransactionHandler(transactionProvider);

        // Application Layer : Query Handlers (lecture)
        GetAllTransactionsHandler getAllTransactionsHandler =
                new GetAllTransactionsHandler(transactionProvider);

        // Infrastructure Layer : Template Renderer
        TemplateRenderer templateRenderer = new TemplateRenderer();

        // Infrastructure Layer : View Controller (rendu HTML)
        DashboardViewController dashboardViewController = new DashboardViewController(
                new CalculateBalance(transactionProvider),
                new CalculateForecastBalance(transactionProvider),
                new CalculatePendingCredits(transactionProvider),
                new CalculatePendingDebits(transactionProvider),
                new CountPending(transactionProvider),
                new CountApproved(transactionProvider),
                new CountRejected(transactionProvider),
                templateRenderer
        );
        TransactionViewController transactionViewController = new TransactionViewController(
                templateRenderer,
                createTransactionHandler,
                approveTransactionHandler,
                rejectTransactionHandler,
                deleteTransactionHandler,
                getAllTransactionsHandler
        );

        // Configuration du routing HTTP
        HttpRouting.Builder routing = HttpRouting.builder()
                .register("/", StaticContentService.builder("/web").welcomeFileName("index.html").build())
                // Pages HTML (interface web)
                .register("/dashboard", dashboardViewController)
                .register("/transactions", transactionViewController);

        // Création et démarrage du serveur
        WebServer server = WebServer.builder()
                .routing(routing)
                .port(8080)
                .build()
                .start();

        System.out.println("========================================");
        System.out.println("✅ Serveur démarré : http://localhost:" + server.port());
        System.out.println("========================================");
        System.out.println("🌐 Interface Web (HTML avec JTE) :");
        System.out.println("  http://localhost:8080/             - Page d'accueil");
        System.out.println("  http://localhost:8080/new          - Nouvelle transaction");
        System.out.println("========================================");
    }
}