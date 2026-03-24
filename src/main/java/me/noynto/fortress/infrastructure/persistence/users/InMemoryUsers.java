package me.noynto.fortress.infrastructure.persistence.users;

import me.noynto.fortress.domain.shared.UserId;
import me.noynto.fortress.domain.users.User;
import me.noynto.fortress.domain.users.UserElectronicAddress;
import me.noynto.fortress.domain.users.UserPassword;
import me.noynto.fortress.domain.users.UserProvider;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public record InMemoryUsers(
        Map<UserId, User> store
) implements UserProvider {

    @Override
    public User create(UserElectronicAddress electronicAddress, UserPassword password) {
        User user = new User();
        user.id(new UserId(UUID.randomUUID().toString()));
        user.electronicAddress(electronicAddress);
        user.password(password);
        this.store.put(user.id(), user);
        return user;
    }

    @Override
    public Stream<User> stream(UserElectronicAddress userElectronicAddress) {
        return this.store.values().stream().filter(user -> Objects.equals(userElectronicAddress, user.electronicAddress()));
    }

    @Override
    public boolean exist(UserElectronicAddress userElectronicAddress) {
        return this.store.values().stream().anyMatch(user -> Objects.equals(userElectronicAddress, user.electronicAddress()));
    }
}
