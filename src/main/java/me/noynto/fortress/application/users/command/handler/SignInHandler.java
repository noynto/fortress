package me.noynto.fortress.application.users.command.handler;

import at.favre.lib.crypto.bcrypt.BCrypt;
import me.noynto.fortress.application.users.command.SignInCommand;
import me.noynto.fortress.domain.identities.Identity;
import me.noynto.fortress.domain.sessions.Session;
import me.noynto.fortress.domain.sessions.SessionProvider;
import me.noynto.fortress.domain.shared.IdentityId;
import me.noynto.fortress.domain.identities.IdentityElectronicAddress;
import me.noynto.fortress.domain.identities.IdentityProvider;

import java.util.Objects;

public record SignInHandler(
        IdentityProvider identityProvider,
        BCrypt.Verifyer verifyer,
        SessionProvider sessionProvider
) {

    public Session handle(SignInCommand command) {
        Objects.requireNonNull(command);
        command.validate();
        IdentityElectronicAddress identityElectronicAddress = new IdentityElectronicAddress(command.electronicAddress());
        IdentityId identityId = identityProvider.stream(identityElectronicAddress)
                .filter(user -> verifyer.verify(command.password().toCharArray(), user.password().value().toCharArray()).verified)
                .map(Identity::id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Le mot de passe renseigné est invalide."));
        return sessionProvider.create(identityId);
    }

}
