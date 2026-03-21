package me.noynto.fortress.infrastructure.persistence.sessions;

import at.favre.lib.crypto.bcrypt.BCrypt;
import me.noynto.fortress.domain.sessions.Session;
import me.noynto.fortress.domain.sessions.SessionProvider;
import me.noynto.fortress.domain.shared.SessionId;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public record InMemorySessions(
        Map<SessionId, Session> store,
        BCrypt.Hasher hasher
) implements SessionProvider {
    @Override
    public Session create() {
        UUID rawUUID = UUID.randomUUID();
        String uniqueCypheredId = hasher.hashToString(10, rawUUID.toString().toCharArray());
        Session session = new Session();
        SessionId id = new SessionId(uniqueCypheredId);
        session.id(id);
        this.store.put(id, session);
        return session;
    }

    @Override
    public Session update(Session session) {
        this.store.replace(session.id(), session);
        return session;
    }

    @Override
    public Optional<Session> find(SessionId id) {
        return Optional.ofNullable(this.store.get(id));
    }
}
