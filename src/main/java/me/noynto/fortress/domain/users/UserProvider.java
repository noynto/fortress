package me.noynto.fortress.domain.users;

import java.util.Optional;

public interface UserProvider {
    User create(User user);

    Optional<User> read(User.ElectronicAddress userElectronicAddress);
}
