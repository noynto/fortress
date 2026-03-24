package me.noynto.fortress.domain.sessions;

import me.noynto.fortress.domain.shared.SessionId;
import me.noynto.fortress.domain.shared.UserId;

import java.util.Optional;

public interface SessionProvider {
    Session create(UserId userId);

    Optional<Session> find(SessionId id);
}
