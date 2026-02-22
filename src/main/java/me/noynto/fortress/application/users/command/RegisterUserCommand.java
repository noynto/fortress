package me.noynto.fortress.application.users.command;

/**
 * Commande pour créer une transaction.
 */
public record RegisterUserCommand(
        String electronicAddress,
        String password
) {
    public void validate() {
        if (electronicAddress == null || electronicAddress.isEmpty() || electronicAddress.isBlank()) {
            throw new IllegalArgumentException("L'adresse électronique est requise.");
        }
        if (!electronicAddress.contains("@") || !electronicAddress.contains(".")) {
            throw new IllegalArgumentException("L'adresse doit avoir un format correct.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe est requis.");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 8 caractères.");
        }
    }

}
