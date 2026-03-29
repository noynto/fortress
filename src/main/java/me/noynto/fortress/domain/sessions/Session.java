package me.noynto.fortress.domain.sessions;

import me.noynto.fortress.domain.shared.SessionId;
import me.noynto.fortress.domain.shared.IdentityId;

public class Session {
    private SessionId id;
    private IdentityId identityId;

    public IdentityId userId() {
        return identityId;
    }

    public void userId(IdentityId identityId) {
        this.identityId = identityId;
    }

    public SessionId id() {
        return id;
    }

    public void id(SessionId id) {
        this.id = id;
    }

}
