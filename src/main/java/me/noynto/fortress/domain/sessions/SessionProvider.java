package me.noynto.fortress.domain.sessions;

import me.noynto.fortress.domain.shared.SessionId;

import java.util.Optional;

public interface SessionProvider {
    Session create();
    Session update(Session session);
    Optional<Session> find(SessionId id);
}
