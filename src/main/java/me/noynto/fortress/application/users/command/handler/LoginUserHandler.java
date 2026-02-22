package me.noynto.fortress.application.users.command.handler;

import at.favre.lib.crypto.bcrypt.BCrypt;
import me.noynto.fortress.application.users.command.LoginUserCommand;
import me.noynto.fortress.domain.users.User;
import me.noynto.fortress.domain.users.UserProvider;

import java.util.Objects;

public record LoginUserHandler(
        UserProvider userProvider
) {

    static final String ERROR_MESSAGE = "Adresse électronique ou mot de passe invalide.";

    public User.Id handle(LoginUserCommand command) {
        Objects.requireNonNull(command);
        command.validate();
        User.ElectronicAddress userElectronicAddress = new User.ElectronicAddress(command.electronicAddress());
        User user = userProvider.read(userElectronicAddress)
                .orElseThrow(() -> new IllegalArgumentException(ERROR_MESSAGE));
        if (!BCrypt.verifyer().verify(command.password().toCharArray(), user.password().value().toCharArray()).verified) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        return user.id();
    }

}
