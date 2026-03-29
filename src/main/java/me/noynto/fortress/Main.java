package me.noynto.fortress;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.helidon.config.Config;
import io.helidon.dbclient.DbClient;
import io.helidon.logging.common.LogConfig;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.HttpRouting;
import io.helidon.webserver.staticcontent.StaticContentFeature;
import me.noynto.fortress.application.transactions.command.handler.ApproveTransactionHandler;
import me.noynto.fortress.application.transactions.command.handler.CreateTransactionHandler;
import me.noynto.fortress.application.transactions.command.handler.DeleteTransactionHandler;
import me.noynto.fortress.application.transactions.command.handler.RejectTransactionHandler;
import me.noynto.fortress.application.transactions.query.handler.*;
import me.noynto.fortress.application.users.command.handler.SignInHandler;
import me.noynto.fortress.application.users.command.handler.SignUpHandler;
import me.noynto.fortress.domain.identities.IdentityProvider;
import me.noynto.fortress.domain.sessions.SessionProvider;
import me.noynto.fortress.domain.transactions.TransactionProvider;
import me.noynto.fortress.infrastructure.controller.filter.SessionFilter;
import me.noynto.fortress.infrastructure.controller.view.DashboardViewController;
import me.noynto.fortress.infrastructure.controller.view.SignInViewController;
import me.noynto.fortress.infrastructure.controller.view.SignUpViewController;
import me.noynto.fortress.infrastructure.controller.view.TransactionViewController;
import me.noynto.fortress.infrastructure.controller.view.config.TemplateRenderer;
import me.noynto.fortress.infrastructure.persistence.DatabaseConfiguration;
import me.noynto.fortress.infrastructure.persistence.sessions.InMemorySessions;
import me.noynto.fortress.infrastructure.persistence.transactions.DatabaseTransactions;
import me.noynto.fortress.infrastructure.persistence.users.InMemoryUsers;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.time.Clock;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LogConfig.configureRuntime();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        Config config = Config.create();
        // Infrastructure Layer : Repository
        BCrypt.Hasher hasher = BCrypt.withDefaults();
        BCrypt.Verifyer verifyer = BCrypt.verifyer();
        Clock clock = Clock.systemUTC();
        Config dbConfig = config.get("db");
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        DbClient dbClient = databaseConfiguration.client(dbConfig);
        TransactionProvider transactionProvider = new DatabaseTransactions(dbClient, clock);
        SessionProvider sessionProvider = new InMemorySessions(new ConcurrentHashMap<>(), hasher);
        IdentityProvider identityProvider = new InMemoryUsers(new ConcurrentHashMap<>());

        // Application Layer : Command Handlers (écriture)
        CreateTransactionHandler createTransactionHandler = new CreateTransactionHandler(transactionProvider, clock);
        ApproveTransactionHandler approveTransactionHandler = new ApproveTransactionHandler(transactionProvider);
        RejectTransactionHandler rejectTransactionHandler = new RejectTransactionHandler(transactionProvider);
        DeleteTransactionHandler deleteTransactionHandler = new DeleteTransactionHandler(transactionProvider);

        // Application Layer : Query Handlers (lecture)
        GetAllTransactionsHandler getAllTransactionsHandler =
                new GetAllTransactionsHandler(transactionProvider);

        // Infrastructure Layer : Template Renderer
        TemplateRenderer templateRenderer = new TemplateRenderer();


        SignUpHandler signUpHandler = new SignUpHandler(
                identityProvider,
                hasher,
                sessionProvider
        );

        SignUpViewController signUpViewController = new SignUpViewController(
                templateRenderer,
                signUpHandler
        );

        SignInHandler signInHandler = new SignInHandler(
                identityProvider,
                verifyer,
                sessionProvider
        );

        SignInViewController signInViewController = new SignInViewController(
                templateRenderer,
                signInHandler
        );

        SessionFilter sessionFilter = new SessionFilter(sessionProvider);

        // Infrastructure Layer : View Controller (rendu HTML)
        DashboardViewController dashboardViewController = new DashboardViewController(
                new CalculateBalance(transactionProvider),
                new CalculateForecastBalance(transactionProvider),
                new CalculatePendingCredits(transactionProvider),
                new CalculatePendingDebits(transactionProvider),
                new CountPending(transactionProvider),
                new CountApproved(transactionProvider),
                new CountRejected(transactionProvider),
                templateRenderer,
                sessionFilter
        );
        TransactionViewController transactionViewController = new TransactionViewController(
                templateRenderer,
                sessionFilter,
                createTransactionHandler,
                approveTransactionHandler,
                rejectTransactionHandler,
                deleteTransactionHandler,
                getAllTransactionsHandler
        );

        // Configuration du routing HTTP
        HttpRouting.Builder routing = HttpRouting.builder();
        routing.register("/sign-up", signUpViewController);
        routing.register("/sign-in", signInViewController);
        routing.addFeature(dashboardViewController);
        routing.addFeature(transactionViewController);

        // Création et démarrage du serveur
        WebServer server = WebServer.builder()
                .routing(routing)
                .addFeature(StaticContentFeature.builder().addClasspath(builder -> builder.location("/web").welcome("index.html").context("/")).build())
                .port(8080)
                .build()
                .start();

        LOGGER.log(Level.INFO, "Le serveur est prêt à recevoir des requêtes à l'adresse http://localhost:" + server.port() + ".");
    }
}