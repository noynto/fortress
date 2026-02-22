package me.noynto.fortress.infrastructure.persistence.users;

import me.noynto.fortress.domain.users.User;
import me.noynto.fortress.domain.users.UserProvider;

import java.util.Map;
import java.util.Optional;

public record InMemoryUsers(
        Map<User.Id, User> store
) implements UserProvider {
    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public Optional<User> read(User.ElectronicAddress userElectronicAddress) {
        return Optional.empty();
    }
}
