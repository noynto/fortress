package me.noynto.fortress.infrastructure.persistence.sessions;

import at.favre.lib.crypto.bcrypt.BCrypt;
import me.noynto.fortress.domain.sessions.Session;
import me.noynto.fortress.domain.sessions.SessionProvider;
import me.noynto.fortress.domain.shared.SessionId;
import me.noynto.fortress.domain.shared.IdentityId;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public record InMemorySessions(
        Map<SessionId, Session> store,
        BCrypt.Hasher hasher
) implements SessionProvider {

    private static final int LEVEL_OF_HASH = 16;

    @Override
    public Session create(IdentityId identityId) {
        Session session = new Session();
        SessionId id = new SessionId(hasher.hashToString(LEVEL_OF_HASH, UUID.randomUUID().toString().toCharArray()));
        session.id(id);
        session.userId(identityId);
        this.store.put(id, session);
        return session;
    }

    @Override
    public Optional<Session> find(SessionId id) {
        return Optional.ofNullable(this.store.get(id));
    }

}
