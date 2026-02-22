package me.noynto.fortress.application.users.command;

public record LoginUserCommand(
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
    }
}
