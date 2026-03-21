package me.noynto.fortress.domain.sessions;

import me.noynto.fortress.domain.shared.SessionId;
import me.noynto.fortress.domain.shared.UserId;

public class Session {
    private SessionId id;
    private UserId userId;

    public UserId userId() {
        return userId;
    }

    public void userId(UserId userId) {
        this.userId = userId;
    }

    public SessionId id() {
        return id;
    }

    public void id(SessionId id) {
        this.id = id;
    }
}
