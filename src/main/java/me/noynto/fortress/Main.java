package me.noynto.fortress;

import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.HttpRouting;
import me.noynto.fortress.application.command.handler.ApproveTransactionHandler;
import me.noynto.fortress.application.command.handler.CreateTransactionHandler;
import me.noynto.fortress.application.command.handler.DeleteTransactionHandler;
import me.noynto.fortress.application.command.handler.RejectTransactionHandler;
import me.noynto.fortress.application.query.handler.GetAllTransactionsHandler;
import me.noynto.fortress.application.query.handler.GetBalanceHandler;
import me.noynto.fortress.application.query.handler.GetTransactionsByStatusHandler;
import me.noynto.fortress.domain.Persistence;
import me.noynto.fortress.infrastructure.controller.view.TransactionViewController;
import me.noynto.fortress.infrastructure.controller.view.config.TemplateRenderer;
import me.noynto.fortress.infrastructure.persistence.InMemoryTransactionPersistence;

import java.util.concurrent.ConcurrentHashMap;

public class Main {

    public static void main(String[] args) {
        // Infrastructure Layer : Repository
        Persistence persistence = new InMemoryTransactionPersistence(new ConcurrentHashMap<>());

        // Application Layer : Command Handlers (écriture)
        CreateTransactionHandler createTransactionHandler = new CreateTransactionHandler(persistence);
        ApproveTransactionHandler approveTransactionHandler = new ApproveTransactionHandler(persistence);
        RejectTransactionHandler rejectTransactionHandler = new RejectTransactionHandler(persistence);
        DeleteTransactionHandler deleteTransactionHandler = new DeleteTransactionHandler(persistence);

        // Application Layer : Query Handlers (lecture)
        GetAllTransactionsHandler getAllTransactionsHandler =
                new GetAllTransactionsHandler(persistence);
        GetTransactionsByStatusHandler getTransactionsByStatusHandler =
                new GetTransactionsByStatusHandler(persistence);
        GetBalanceHandler getBalanceHandler =
                new GetBalanceHandler(persistence);

        // Infrastructure Layer : Template Renderer
        TemplateRenderer templateRenderer = new TemplateRenderer();

        // Infrastructure Layer : View Controller (rendu HTML)
        TransactionViewController viewController = new TransactionViewController(
                templateRenderer,
                createTransactionHandler,
                approveTransactionHandler,
                rejectTransactionHandler,
                deleteTransactionHandler,
                getAllTransactionsHandler,
                getTransactionsByStatusHandler,
                getBalanceHandler
        );

        // Configuration du routing HTTP
        HttpRouting.Builder routing = HttpRouting.builder()
                // Pages HTML (interface web)
                .register("/", viewController);

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