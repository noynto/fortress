package me.noynto.fortress.application.transactions.query;

import me.noynto.fortress.domain.shared.IdentityId;

/**
 * Query pour récupérer toutes les transactions
 * @param identityId identifiant de l'utilisateur.
 */
public record GetAllTransactionsQuery(
        IdentityId identityId
) {
}