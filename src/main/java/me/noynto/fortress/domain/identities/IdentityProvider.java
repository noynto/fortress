package me.noynto.fortress.domain.identities;

import java.util.stream.Stream;

public interface IdentityProvider {
    Identity create(IdentityElectronicAddress electronicAddress, IdentityPassword identityPassword);

    Stream<Identity> stream(IdentityElectronicAddress identityElectronicAddress);

    boolean exist(IdentityElectronicAddress identityElectronicAddress);
}
