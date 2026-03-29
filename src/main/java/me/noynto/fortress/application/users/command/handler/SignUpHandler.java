package me.noynto.fortress.application.users.command.handler;

import at.favre.lib.crypto.bcrypt.BCrypt;
import me.noynto.fortress.application.users.command.SignUpCommand;
import me.noynto.fortress.domain.identities.Identity;
import me.noynto.fortress.domain.sessions.Session;
import me.noynto.fortress.domain.sessions.SessionProvider;
import me.noynto.fortress.domain.identities.IdentityElectronicAddress;
import me.noynto.fortress.domain.identities.IdentityPassword;
import me.noynto.fortress.domain.identities.IdentityProvider;

import java.util.Objects;

public record SignUpHandler(
        IdentityProvider identityProvider,
        BCrypt.Hasher hasher,
        SessionProvider sessionProvider
) {
    private static final int LEVEL_OF_HASH = 4;

    public Session handle(SignUpCommand command) {
        Objects.requireNonNull(command);
        command.validate();
        IdentityElectronicAddress identityElectronicAddress = new IdentityElectronicAddress(command.electronicAddress());
        if (identityProvider.exist(identityElectronicAddress)) {
            throw new IllegalArgumentException("Un utilisateur est déjà lié à cette adresse électronique.");
        }
        IdentityPassword identityPassword = new IdentityPassword(hasher.hashToString(LEVEL_OF_HASH, command.electronicAddress().toCharArray()));
        Identity identity = identityProvider.create(identityElectronicAddress, identityPassword);
        return sessionProvider.create(identity.id());
    }

}
