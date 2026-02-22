package me.noynto.fortress.application.users.command.handler;

import at.favre.lib.crypto.bcrypt.BCrypt;
import me.noynto.fortress.application.users.command.RegisterUserCommand;
import me.noynto.fortress.domain.users.User;
import me.noynto.fortress.domain.users.UserProvider;

import java.util.Objects;
import java.util.UUID;

public record RegisterUserHandler(
        UserProvider userProvider
) {

    public User.Id handle(RegisterUserCommand command) {
        Objects.requireNonNull(command);
        command.validate();
        User user = new User.Default();
        user.id(new User.Id(UUID.randomUUID().toString()));
        user.electronicAddress(new User.ElectronicAddress(command.electronicAddress()));
        user.password(new User.Password(BCrypt.withDefaults().hashToString(12, command.password().toCharArray())));
        return userProvider.create(user).id();
    }

}
