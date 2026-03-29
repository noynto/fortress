package me.noynto.fortress.domain.sessions;

import me.noynto.fortress.domain.shared.SessionId;
import me.noynto.fortress.domain.shared.IdentityId;

import java.util.Optional;

public interface SessionProvider {
    Session create(IdentityId identityId);

    Optional<Session> find(SessionId id);
}
