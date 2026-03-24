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
import me.noynto.fortress.application.users.command.handler.SignInHandler;
import me.noynto.fortress.application.users.command.handler.SignUpHandler;
import me.noynto.fortress.domain.sessions.SessionProvider;
import me.noynto.fortress.domain.transactions.TransactionProvider;
import me.noynto.fortress.domain.users.UserProvider;
import me.noynto.fortress.infrastructure.controller.filter.SessionFilter;
import me.noynto.fortress.infrastructure.controller.view.DashboardViewController;
import me.noynto.fortress.infrastructure.controller.view.SignInViewController;
import me.noynto.fortress.infrastructure.controller.view.SignUpViewController;
import me.noynto.fortress.infrastructure.controller.view.TransactionViewController;
import me.noynto.fortress.infrastructure.controller.view.config.TemplateRenderer;
import me.noynto.fortress.infrastructure.persistence.sessions.InMemorySessions;
import me.noynto.fortress.infrastructure.persistence.transactions.InMemoryTransactions;
import me.noynto.fortress.infrastructure.persistence.users.InMemoryUsers;

import java.time.Clock;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        // Infrastructure Layer : Repository
        BCrypt.Hasher hasher = BCrypt.withDefaults();
        BCrypt.Verifyer verifyer = BCrypt.verifyer();
        Clock clock = Clock.systemUTC();
        TransactionProvider transactionProvider = new InMemoryTransactions(new ConcurrentHashMap<>(), clock);
        SessionProvider sessionProvider = new InMemorySessions(new ConcurrentHashMap<>(), hasher);
        UserProvider userProvider = new InMemoryUsers(new ConcurrentHashMap<>());

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
                userProvider,
                hasher,
                sessionProvider
        );

        SignUpViewController signUpViewController = new SignUpViewController(
                templateRenderer,
                signUpHandler
        );

        SignInHandler signInHandler = new SignInHandler(
                userProvider,
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
        HttpRouting.Builder routing = HttpRouting.builder()
                .register("/", StaticContentService.builder("/web").welcomeFileName("index.html").build())
                .register("/sign-up", signUpViewController)
                .register("/sign-in", signInViewController)
                .addFeature(dashboardViewController)
                .addFeature(transactionViewController);

        // Création et démarrage du serveur
        WebServer server = WebServer.builder()
                .routing(routing)
                .port(8080)
                .build()
                .start();

        LOGGER.log(Level.INFO, "Le serveur est prêt à recevoir des requêtes à l'adresse http://localhost:" + server.port() + ".");
    }
}