package me.noynto.fortress.domain.identities;

import me.noynto.fortress.domain.shared.IdentityId;

public class Identity {

    private IdentityId id;
    private IdentityElectronicAddress electronicAddress;
    private IdentityPassword password;

    public IdentityId id() {
        return this.id;
    }

    public void id(IdentityId id) {
        this.id = id;
    }

    public IdentityElectronicAddress electronicAddress() {
        return this.electronicAddress;
    }

    public void electronicAddress(IdentityElectronicAddress electronicAddress) {
        this.electronicAddress = electronicAddress;
    }

    public IdentityPassword password() {
        return this.password;
    }

    public void password(IdentityPassword password) {
        this.password = password;
    }
}
