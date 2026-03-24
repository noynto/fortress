package me.noynto.fortress.domain.users;

import java.util.stream.Stream;

public interface UserProvider {
    User create(UserElectronicAddress electronicAddress, UserPassword userPassword);

    Stream<User> stream(UserElectronicAddress userElectronicAddress);

    boolean exist(UserElectronicAddress userElectronicAddress);
}
