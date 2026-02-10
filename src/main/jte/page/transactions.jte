@import me.noynto.fortress.domain.Balance
@import me.noynto.fortress.domain.Transaction
@import me.noynto.fortress.domain.Transaction.Status
@import java.time.LocalDateTime
@import java.util.List
@import java.math.BigDecimal

@param List<Transaction> transactions
@param Balance balance

@template.layout.main(
title = "Liste des transactions",
content = @`
    <div class="mb-8">
        <div class="bg-white rounded-lg shadow p-6">
            <h2 class="text-3xl font-bold mb-2">Solde actuel</h2>
            <p class="text-5xl font-bold ${balance.value().compareTo(BigDecimal.ZERO) >= 0 ? "text-green-600" : "text-red-600"}">
                ${String.format("%.2f", balance.value())} €
            </p>
            <p class="text-gray-500 mt-2">Basé sur les transactions approuvées</p>
        </div>
    </div>

    <!-- Filtres -->
    <div class="bg-white rounded-lg shadow p-4 mb-6">
        <div class="flex flex-wrap gap-4">
            <a href="/" class="px-4 py-2 bg-gray-200 rounded-lg hover:bg-gray-300">Toutes</a>
            <a href="/status/PENDING" class="px-4 py-2 bg-yellow-100 text-yellow-800 rounded-lg hover:bg-yellow-200">En
                attente</a>
            <a href="/status/APPROVED" class="px-4 py-2 bg-green-100 text-green-800 rounded-lg hover:bg-green-200">Approuvées</a>
            <a href="/status/REJECTED"
               class="px-4 py-2 bg-red-100 text-red-800 rounded-lg hover:bg-red-200">Rejetées</a>
        </div>
    </div>

    <!-- Liste des transactions -->
    <div class="bg-white rounded-lg shadow overflow-hidden">
        <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50">
            <tr>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Date</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Description</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Type</th>
                <th class="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase">Montant</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Statut</th>
                <th class="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase">Actions</th>
            </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
            @if(transactions.isEmpty())
                <tr>
                    <td colspan="6" class="px-6 py-12 text-center text-gray-500">
                        Aucune transaction trouvée
                    </td>
                </tr>
            @else
                @for(Transaction transaction : transactions)
                    <tr class="hover:bg-gray-50">
                        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                            ${LocalDateTime.now().toString().substring(0, 16).replace("T", " ")}
                        </td>
                        <td class="px-6 py-4 text-sm text-gray-900">
                            ${transaction.description().value()}
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap">
                            @if(transaction.type().name().equals("CREDIT"))
                                <span class="px-2 py-1 text-xs font-semibold rounded-full bg-green-100 text-green-800">
                                            ↗ CRÉDIT
                                        </span>
                            @else
                                <span class="px-2 py-1 text-xs font-semibold rounded-full bg-red-100 text-red-800">
                                            ↙ DÉBIT
                                        </span>
                            @endif
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap text-right text-sm font-semibold ${transaction.type().name().equals("CREDIT") ? "text-green-600" : "text-red-600"}">
                            ${transaction.type().name().equals("CREDIT") ? "+" : "-"}${String.format("%.2f", transaction.amount().value())}
                            €
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap">
                            @if(transaction.status() == Status.PENDING)
                                <span class="px-2 py-1 text-xs font-semibold rounded-full bg-yellow-100 text-yellow-800">
                                            ⏳ En attente
                                        </span>
                            @elseif(transaction.status() == Status.APPROVED)
                                <span class="px-2 py-1 text-xs font-semibold rounded-full bg-green-100 text-green-800">
                                            ✓ Approuvée
                                        </span>
                            @else
                                <span class="px-2 py-1 text-xs font-semibold rounded-full bg-red-100 text-red-800">
                                            ✗ Rejetée
                                        </span>
                            @endif
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap text-right text-sm">
                            @if(transaction.status() == Status.PENDING)
                                <form action="/transactions/${transaction.id().value()}/approve" method="post"
                                      class="inline">
                                    <button type="submit" class="text-green-600 hover:text-green-900 mr-3">
                                        ✓ Approuver
                                    </button>
                                </form>
                                <form action="/transactions/${transaction.id().value()}/reject" method="post"
                                      class="inline">
                                    <button type="submit" class="text-red-600 hover:text-red-900 mr-3">
                                        ✗ Rejeter
                                    </button>
                                </form>
                            @endif
                            <form action="/transactions/${transaction.id().value()}/delete" method="post"
                                  class="inline">
                                <button type="submit" class="text-gray-600 hover:text-gray-900">
                                    🗑 Supprimer
                                </button>
                            </form>
                        </td>
                    </tr>
                @endfor
            @endif
            </tbody>
        </table>
    </div>
`
)