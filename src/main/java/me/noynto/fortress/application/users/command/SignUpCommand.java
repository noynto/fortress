package me.noynto.fortress.application.users.command;

import java.util.Objects;

/**
 * Commande pour créer une transaction.
 */
public record SignUpCommand(
        String electronicAddress,
        String password,
        String confirmationPassword
) {
    public void validate() {
        Objects.requireNonNull(electronicAddress, "L'adresse électronique est requise.");
        Objects.requireNonNull(password, "Le mot de passe est requis.");
        Objects.requireNonNull(confirmationPassword, "La confirmation de mot de passe est requise.");
        if (!electronicAddress.contains("@") || !electronicAddress.contains(".")) {
            throw new IllegalArgumentException("L'adresse doit avoir un format correct.");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 8 caractères.");
        }
        if (!confirmationPassword.equals(password)) {
            throw new IllegalArgumentException("Les mots de passe sont différents.");
        }
    }

}
