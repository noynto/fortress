package me.noynto.fortress.infrastructure.controller.view;

import io.helidon.http.HeaderNames;
import io.helidon.http.SetCookie;
import io.helidon.http.Status;
import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import me.noynto.fortress.application.users.command.SignInCommand;
import me.noynto.fortress.application.users.command.handler.SignInHandler;
import me.noynto.fortress.domain.sessions.Session;
import me.noynto.fortress.infrastructure.controller.view.config.TemplateRenderer;
import me.noynto.fortress.infrastructure.controller.view.helper.FormDataParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Contrôleur pour le rendu HTML des transactions avec JTE
 */
public record SignInViewController(
        TemplateRenderer renderer,
        SignInHandler signInHandler
) implements HttpService {

    @Override
    public void routing(HttpRules rules) {
        rules
                // Pages
                .get(this::showSignInForm)
                // Actions
                .post(this::submitSignInForm);
    }

    // ============= PAGES (Queries) =============

    private void showSignInForm(ServerRequest req, ServerResponse res) {
        Map<String, Object> params = new HashMap<>();
        String html = renderer.render("page/sign-in.jte", params);
        res.header(HeaderNames.CONTENT_TYPE, "text/html");
        res.send(html);
    }

    // ============= ACTIONS (Commands) =============

    private void submitSignInForm(ServerRequest req, ServerResponse res) {
        try {
            // Parser les données du formulaire
            Map<String, String> formData = FormDataParser.execute(req.content().as(String.class));
            SignInCommand command = new SignInCommand(
                    formData.get("email"),
                    formData.get("password")
            );
            Session session = signInHandler.handle(command);
            res.status(Status.SEE_OTHER_303);
            res.headers().addCookie(
                    SetCookie.builder("session_id", session.id().value())
                            .httpOnly(true)
                            .path("/")
                            .sameSite(SetCookie.SameSite.STRICT)
                            .secure(true)
                            .build()
            );
            res.header(HeaderNames.LOCATION, "/dashboard");
            res.send();
        } catch (Exception e) {
            res.status(Status.BAD_REQUEST_400);
            res.send("Erreur: " + e.getMessage());
        }
    }

}