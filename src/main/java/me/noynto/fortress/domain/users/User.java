package me.noynto.fortress.domain.users;

import me.noynto.fortress.domain.shared.UserId;

public class User {

    private UserId id;
    private UserElectronicAddress electronicAddress;
    private UserPassword password;

    public UserId id() {
        return this.id;
    }

    public void id(UserId id) {
        this.id = id;
    }

    public UserElectronicAddress electronicAddress() {
        return this.electronicAddress;
    }

    public void electronicAddress(UserElectronicAddress electronicAddress) {
        this.electronicAddress = electronicAddress;
    }

    public UserPassword password() {
        return this.password;
    }

    public void password(UserPassword password) {
        this.password = password;
    }
}
