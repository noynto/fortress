package me.noynto.fortress.infrastructure.controller.filter;

import io.helidon.http.HeaderNames;
import io.helidon.http.Status;
import io.helidon.webserver.http.*;
import me.noynto.fortress.domain.sessions.Session;
import me.noynto.fortress.domain.sessions.SessionProvider;
import me.noynto.fortress.domain.shared.SessionId;

public record SessionFilter(
        SessionProvider provider
) implements Handler {

    public static final String SESSION_KEY_COOKIE_NAME = "session_id";

    @Override
    public void handle(ServerRequest routingRequest, ServerResponse routingResponse) {
        Session session = routingRequest
                .headers()
                .cookies()
                .first(SESSION_KEY_COOKIE_NAME)
                .map(SessionId::new)
                .flatMap(provider::find)
                .orElse(null);
        if (session == null) {
            routingResponse.status(Status.FOUND_302).headers().add(HeaderNames.LOCATION, "/sign-in");
            routingResponse.send();
            return;
        }
        routingRequest.context().register(session);
        routingResponse.next();
    }

}
