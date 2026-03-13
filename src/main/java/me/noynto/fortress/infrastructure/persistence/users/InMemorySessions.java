package me.noynto.fortress.infrastructure.persistence.users;

import me.noynto.fortress.domain.sessions.Session;
import me.noynto.fortress.domain.sessions.SessionProvider;

import java.util.Map;

public record InMemorySessions(
        Map<Session.Id, Session> store
) implements SessionProvider {
}
