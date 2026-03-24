package me.noynto.fortress.application.users.command;

import java.util.Objects;

public record SignInCommand(
        String electronicAddress,
        String password
) {
    public void validate() {
        Objects.requireNonNull(electronicAddress, "L'adresse électronique est requise.");
        Objects.requireNonNull(password, "Le mot de passe est requis.");
        if (!electronicAddress.contains("@") || !electronicAddress.contains(".")) {
            throw new IllegalArgumentException("L'adresse doit avoir un format correct.");
        }
    }
}
