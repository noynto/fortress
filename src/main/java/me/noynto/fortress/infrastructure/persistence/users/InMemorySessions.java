package me.noynto.fortress.infrastructure.persistence.users;

import me.noynto.fortress.domain.users.Session;
import me.noynto.fortress.domain.users.SessionProvider;

import java.util.Map;

public record InMemorySessions(
        Map<Session.Id, Session> store
) implements SessionProvider {
}
