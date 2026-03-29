package me.noynto.fortress.application.users.command.handler;

import at.favre.lib.crypto.bcrypt.BCrypt;
import me.noynto.fortress.application.users.command.SignInCommand;
import me.noynto.fortress.domain.identities.Identity;
import me.noynto.fortress.domain.sessions.Session;
import me.noynto.fortress.domain.sessions.SessionProvider;
import me.noynto.fortress.domain.shared.SessionId;
import me.noynto.fortress.domain.shared.IdentityId;
import me.noynto.fortress.domain.identities.IdentityElectronicAddress;
import me.noynto.fortress.domain.identities.IdentityPassword;
import me.noynto.fortress.domain.identities.IdentityProvider;
import me.noynto.fortress.infrastructure.persistence.sessions.InMemorySessions;
import me.noynto.fortress.infrastructure.persistence.users.InMemoryUsers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class SignInHandlerTest {

    @Test
    void should_sign_in() {
        BCrypt.Hasher hasher = BCrypt.withDefaults();
        Map<IdentityId, Identity> userStore = new HashMap<>();
        IdentityProvider identityProvider = new InMemoryUsers(userStore);
        IdentityId identityId = identityProvider.create(new IdentityElectronicAddress("electronic@address.com"), new IdentityPassword(hasher.hashToString(4, "password".toCharArray()))).id();
        BCrypt.Verifyer verifyer = BCrypt.verifyer();
        Map<SessionId, Session> sessionStore = new HashMap<>();
        SessionProvider sessionProvider = new InMemorySessions(sessionStore, hasher);
        SignInHandler signInHandler = new SignInHandler(identityProvider, verifyer, sessionProvider);

        SignInCommand command = new SignInCommand(
                "electronic@address.com",
                "password"
        );
        Session session = signInHandler.handle(command);

        Assertions.assertNotNull(session);
        Assertions.assertEquals(identityId, session.userId());
    }

}