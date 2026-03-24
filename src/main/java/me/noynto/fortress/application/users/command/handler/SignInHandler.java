package me.noynto.fortress.application.users.command.handler;

import at.favre.lib.crypto.bcrypt.BCrypt;
import me.noynto.fortress.application.users.command.SignInCommand;
import me.noynto.fortress.domain.sessions.Session;
import me.noynto.fortress.domain.sessions.SessionProvider;
import me.noynto.fortress.domain.shared.UserId;
import me.noynto.fortress.domain.users.User;
import me.noynto.fortress.domain.users.UserElectronicAddress;
import me.noynto.fortress.domain.users.UserProvider;

import java.util.Objects;

public record SignInHandler(
        UserProvider userProvider,
        BCrypt.Verifyer verifyer,
        SessionProvider sessionProvider
) {

    public Session handle(SignInCommand command) {
        Objects.requireNonNull(command);
        command.validate();
        UserElectronicAddress userElectronicAddress = new UserElectronicAddress(command.electronicAddress());
        UserId userId = userProvider.stream(userElectronicAddress)
                .filter(user -> verifyer.verify(command.password().toCharArray(), user.password().value().toCharArray()).verified)
                .map(User::id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Le mot de passe renseigné est invalide."));
        return sessionProvider.create(userId);
    }

}
