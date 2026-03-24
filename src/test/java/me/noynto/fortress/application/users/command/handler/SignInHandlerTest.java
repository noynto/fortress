package me.noynto.fortress.application.users.command.handler;

import at.favre.lib.crypto.bcrypt.BCrypt;
import me.noynto.fortress.application.users.command.SignInCommand;
import me.noynto.fortress.domain.sessions.Session;
import me.noynto.fortress.domain.sessions.SessionProvider;
import me.noynto.fortress.domain.shared.SessionId;
import me.noynto.fortress.domain.shared.UserId;
import me.noynto.fortress.domain.users.User;
import me.noynto.fortress.domain.users.UserElectronicAddress;
import me.noynto.fortress.domain.users.UserPassword;
import me.noynto.fortress.domain.users.UserProvider;
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
        Map<UserId, User> userStore = new HashMap<>();
        UserProvider userProvider = new InMemoryUsers(userStore);
        UserId userId = userProvider.create(new UserElectronicAddress("electronic@address.com"), new UserPassword(hasher.hashToString(4, "password".toCharArray()))).id();
        BCrypt.Verifyer verifyer = BCrypt.verifyer();
        Map<SessionId, Session> sessionStore = new HashMap<>();
        SessionProvider sessionProvider = new InMemorySessions(sessionStore, hasher);
        SignInHandler signInHandler = new SignInHandler(userProvider, verifyer, sessionProvider);

        SignInCommand command = new SignInCommand(
                "electronic@address.com",
                "password"
        );
        Session session = signInHandler.handle(command);

        Assertions.assertNotNull(session);
        Assertions.assertEquals(userId, session.userId());
    }

}