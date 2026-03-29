package me.noynto.fortress.infrastructure.persistence.users;

import me.noynto.fortress.domain.identities.Identity;
import me.noynto.fortress.domain.shared.IdentityId;
import me.noynto.fortress.domain.identities.IdentityElectronicAddress;
import me.noynto.fortress.domain.identities.IdentityPassword;
import me.noynto.fortress.domain.identities.IdentityProvider;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public record InMemoryUsers(
        Map<IdentityId, Identity> store
) implements IdentityProvider {

    @Override
    public Identity create(IdentityElectronicAddress electronicAddress, IdentityPassword password) {
        Identity identity = new Identity();
        identity.id(new IdentityId(UUID.randomUUID().toString()));
        identity.electronicAddress(electronicAddress);
        identity.password(password);
        this.store.put(identity.id(), identity);
        return identity;
    }

    @Override
    public Stream<Identity> stream(IdentityElectronicAddress identityElectronicAddress) {
        return this.store.values().stream().filter(user -> Objects.equals(identityElectronicAddress, user.electronicAddress()));
    }

    @Override
    public boolean exist(IdentityElectronicAddress identityElectronicAddress) {
        return this.store.values().stream().anyMatch(user -> Objects.equals(identityElectronicAddress, user.electronicAddress()));
    }
}
