package me.noynto.fortress.application.users.command.handler;

import at.favre.lib.crypto.bcrypt.BCrypt;
import me.noynto.fortress.application.users.command.SignUpCommand;
import me.noynto.fortress.domain.sessions.Session;
import me.noynto.fortress.domain.sessions.SessionProvider;
import me.noynto.fortress.domain.users.User;
import me.noynto.fortress.domain.users.UserElectronicAddress;
import me.noynto.fortress.domain.users.UserPassword;
import me.noynto.fortress.domain.users.UserProvider;

import java.util.Objects;

public record SignUpHandler(
        UserProvider userProvider,
        BCrypt.Hasher hasher,
        SessionProvider sessionProvider
) {
    private static final int LEVEL_OF_HASH = 12;

    public Session handle(SignUpCommand command) {
        Objects.requireNonNull(command);
        command.validate();
        UserElectronicAddress userElectronicAddress = new UserElectronicAddress(command.electronicAddress());
        if (userProvider.exist(userElectronicAddress)) {
            throw new IllegalArgumentException("Un utilisateur est déjà lié à cette adresse électronique.");
        }
        UserPassword userPassword = new UserPassword(hasher.hashToString(LEVEL_OF_HASH, command.electronicAddress().toCharArray()));
        User user = userProvider.create(userElectronicAddress, userPassword);
        return sessionProvider.create(user.id());
    }

}
