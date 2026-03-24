package me.noynto.fortress.application.transactions.query;

import me.noynto.fortress.domain.shared.UserId;

/**
 * Query pour récupérer toutes les transactions
 * @param userId identifiant de l'utilisateur.
 */
public record GetAllTransactionsQuery(
        UserId userId
) {
}