@import me.noynto.fortress.domain.transactions.Transaction
@import me.noynto.fortress.domain.transactions.TransactionState
@import java.time.format.DateTimeFormatter
@import java.util.List

@param List<Transaction> transactions

@template.layout.main(
title = "Fortress — Transactions",
content = @`

<%-- TOPBAR --%>
    <div class="flex items-center justify-between mb-10">
        <div class="flex items-center gap-3">
            <a href="/" class="font-nunito text-lg font-bold text-ctp-mauve hover:opacity-80 transition-opacity">🐱
                Fortress</a>
            <span class="text-xs font-semibold px-2.5 py-1 rounded-full bg-ctp-surface0 text-ctp-overlay2">Transactions</span>
        </div>
        <a href="/transactions/new"
           class="font-nunito text-xs font-bold px-4 py-2.5 rounded-xl bg-ctp-mauve text-ctp-crust hover:opacity-85 transition-opacity">
            + Nouvelle transaction
        </a>
    </div>
    <%-- DESKTOP : tableau --%>
    <div class="hidden md:block bg-ctp-mantle border border-ctp-surface0 rounded-2xl overflow-hidden">
        <table class="min-w-full">
            <thead>
            <tr class="border-b border-ctp-surface0">
                <th class="px-6 py-4 text-left text-[0.63rem] font-bold tracking-[.12em] uppercase text-ctp-overlay0">
                    Date
                </th>
                <th class="px-6 py-4 text-left text-[0.63rem] font-bold tracking-[.12em] uppercase text-ctp-overlay0">
                    Description
                </th>
                <th class="px-6 py-4 text-left text-[0.63rem] font-bold tracking-[.12em] uppercase text-ctp-overlay0">
                    Type
                </th>
                <th class="px-6 py-4 text-right text-[0.63rem] font-bold tracking-[.12em] uppercase text-ctp-overlay0">
                    Montant
                </th>
                <th class="px-6 py-4 text-left text-[0.63rem] font-bold tracking-[.12em] uppercase text-ctp-overlay0">
                    Statut
                </th>
                <th class="px-6 py-4 text-right text-[0.63rem] font-bold tracking-[.12em] uppercase text-ctp-overlay0">
                    Actions
                </th>
            </tr>
            </thead>
            <tbody>
            @if(transactions.isEmpty())
                <tr>
                    <td colspan="6" class="px-6 py-16 text-center text-ctp-overlay0 text-sm">
                        Aucune transaction trouvée
                    </td>
                </tr>
            @else
                @for(Transaction transaction : transactions)
                    <tr class="border-b border-ctp-surface0 last:border-0 hover:bg-ctp-surface0 transition-colors">
                        <td class="px-6 py-4 whitespace-nowrap">
                            <p class="text-xs text-ctp-overlay1">${transaction.effectiveDate().value().format(DateTimeFormatter.ISO_DATE)}</p>
                            <p class="text-[0.65rem] text-ctp-overlay0 mt-0.5">
                                Créée le ${transaction.creationDate().value().format(DateTimeFormatter.ISO_DATE)}
                            </p>
                        </td>
                        <td class="px-6 py-4 text-sm text-ctp-text font-semibold">
                            ${transaction.description().value()}
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap">
                            @if(transaction.type().name().equals("CREDIT"))
                                <span class="inline-flex items-center gap-1 text-[0.67rem] font-bold px-2.5 py-1 rounded-full bg-ctp-green/15 text-ctp-green">↗ Crédit</span>
                            @else
                                <span class="inline-flex items-center gap-1 text-[0.67rem] font-bold px-2.5 py-1 rounded-full bg-ctp-red/15 text-ctp-red">↙ Débit</span>
                            @endif
                        </td>
                        <td class="px-6 py-4 text-right whitespace-nowrap font-nunito text-sm font-bold ${transaction.type().name().equals("CREDIT") ? "text-ctp-green" : "text-ctp-red"}">
                            ${transaction.type().name().equals("CREDIT") ? "+" : "−"}${String.format("%.2f", transaction.amount().value())}
                            €
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap">
                            @if(transaction.state() == TransactionState.PENDING)
                                <span class="inline-flex items-center gap-1 text-[0.67rem] font-bold px-2.5 py-1 rounded-full bg-ctp-yellow/15 text-ctp-yellow">⏳ En attente</span>
                            @elseif(transaction.state() == TransactionState.APPROVED)
                                <span class="inline-flex items-center gap-1 text-[0.67rem] font-bold px-2.5 py-1 rounded-full bg-ctp-green/15 text-ctp-green">✓ Approuvée</span>
                            @else
                                <span class="inline-flex items-center gap-1 text-[0.67rem] font-bold px-2.5 py-1 rounded-full bg-ctp-red/15 text-ctp-red">✗ Rejetée</span>
                            @endif
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap text-right">
                            <div class="flex items-center justify-end gap-2">
                                @if(transaction.state() == TransactionState.PENDING)
                                    <form action="/transactions/${transaction.id().value()}/approve" method="post"
                                          class="inline">
                                        <button type="submit"
                                                class="text-[0.7rem] font-semibold px-3 py-1.5 rounded-lg border border-ctp-green/35 text-ctp-green hover:bg-ctp-green/15 transition-colors">
                                            Approuver
                                        </button>
                                    </form>
                                    <form action="/transactions/${transaction.id().value()}/reject" method="post"
                                          class="inline">
                                        <button type="submit"
                                                class="text-[0.7rem] font-semibold px-3 py-1.5 rounded-lg border border-ctp-red/35 text-ctp-red hover:bg-ctp-red/15 transition-colors">
                                            Rejeter
                                        </button>
                                    </form>
                                @endif
                                <form action="/transactions/${transaction.id().value()}/delete" method="post"
                                      class="inline">
                                    <button type="submit"
                                            class="text-[0.7rem] font-semibold px-3 py-1.5 rounded-lg border border-ctp-surface1 text-ctp-overlay1 hover:bg-ctp-surface0 hover:text-ctp-subtext0 transition-colors">
                                        Supprimer
                                    </button>
                                </form>
                            </div>
                        </td>
                    </tr>
                @endfor
            @endif
            </tbody>
        </table>
    </div>

    <%-- MOBILE : cartes empilées --%>
    <div class="md:hidden space-y-2.5">
        @if(transactions.isEmpty())
            <div class="bg-ctp-mantle border border-ctp-surface0 rounded-2xl px-6 py-16 text-center text-ctp-overlay0 text-sm">
                Aucune transaction trouvée
            </div>
        @else
            @for(Transaction transaction : transactions)
                <div class="bg-ctp-mantle border border-ctp-surface0 rounded-2xl overflow-hidden">

                    <%-- Description + montant --%>
                    <div class="flex items-start justify-between gap-3 px-4 pt-4 pb-3">
                        <div>
                            <p class="text-sm font-bold text-ctp-text leading-snug">${transaction.description().value()}</p>
                            <p class="text-[0.65rem] text-ctp-overlay0 mt-1 font-medium">Effectif
                                le ${transaction.effectiveDate().value().format(DateTimeFormatter.ISO_DATE)}</p>
                        </div>
                        <p class="font-nunito text-base font-bold shrink-0 ${transaction.type().name().equals("CREDIT") ? "text-ctp-green" : "text-ctp-red"}">
                            ${transaction.type().name().equals("CREDIT") ? "+" : "−"}${String.format("%.2f", transaction.amount().value())}
                            €
                        </p>
                    </div>

                    <%-- Badges + date de création --%>
                    <div class="flex items-center gap-1.5 px-4 pb-3">
                        @if(transaction.type().name().equals("CREDIT"))
                            <span class="inline-flex items-center gap-1 text-[0.65rem] font-bold px-2.5 py-1 rounded-full bg-ctp-green/15 text-ctp-green">↗ Crédit</span>
                        @else
                            <span class="inline-flex items-center gap-1 text-[0.65rem] font-bold px-2.5 py-1 rounded-full bg-ctp-red/15 text-ctp-red">↙ Débit</span>
                        @endif
                        @if(transaction.state() == TransactionState.PENDING)
                            <span class="inline-flex items-center gap-1 text-[0.65rem] font-bold px-2.5 py-1 rounded-full bg-ctp-yellow/15 text-ctp-yellow">⏳ En attente</span>
                        @elseif(transaction.state() == TransactionState.APPROVED)
                            <span class="inline-flex items-center gap-1 text-[0.65rem] font-bold px-2.5 py-1 rounded-full bg-ctp-green/15 text-ctp-green">✓ Approuvée</span>
                        @else
                            <span class="inline-flex items-center gap-1 text-[0.65rem] font-bold px-2.5 py-1 rounded-full bg-ctp-red/15 text-ctp-red">✗ Rejetée</span>
                        @endif
                        <span class="text-[0.62rem] text-ctp-overlay0 ml-auto font-medium">Créée le ${transaction.creationDate().value().format(DateTimeFormatter.ISO_DATE)}</span>
                    </div>

                    <%-- Actions --%>
                    <div class="flex items-center gap-2 px-4 py-2.5 border-t border-ctp-surface0 bg-black/10">
                        @if(transaction.state() == TransactionState.PENDING)
                            <form action="/transactions/${transaction.id().value()}/approve" method="post"
                                  class="inline">
                                <button type="submit"
                                        class="text-[0.7rem] font-bold px-3 py-1.5 rounded-lg border border-ctp-green/35 text-ctp-green hover:bg-ctp-green/15 transition-colors">
                                    Approuver
                                </button>
                            </form>
                            <form action="/transactions/${transaction.id().value()}/reject" method="post"
                                  class="inline">
                                <button type="submit"
                                        class="text-[0.7rem] font-bold px-3 py-1.5 rounded-lg border border-ctp-red/35 text-ctp-red hover:bg-ctp-red/15 transition-colors">
                                    Rejeter
                                </button>
                            </form>
                        @endif
                        <form action="/transactions/${transaction.id().value()}/delete" method="post"
                              class="inline ml-auto">
                            <button type="submit"
                                    class="text-[0.7rem] font-bold px-3 py-1.5 rounded-lg border border-ctp-surface1 text-ctp-overlay1 hover:bg-ctp-surface0 hover:text-ctp-subtext0 transition-colors">
                                Supprimer
                            </button>
                        </form>
                    </div>

                </div>
            @endfor
        @endif
    </div>
`
)
