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
            <a href="/dashboard" class="font-serif text-lg text-ef-green hover:opacity-80 transition-opacity">🌲 Fortress</a>
            <span class="text-[10px] font-mono font-medium px-2.5 py-1 rounded-full bg-[rgba(167,192,128,0.1)] text-ef-dim border border-[rgba(167,192,128,0.2)]">Transactions</span>
        </div>
        <a href="/transactions/new"
           class="text-[11px] font-medium px-4 py-2.5 rounded-xl bg-ef-green text-ef-bg0 hover:bg-[#b8d494] transition-colors">
            + Nouvelle transaction
        </a>
    </div>

    <%-- DESKTOP : tableau --%>
    <div class="hidden md:block bg-ef-bg1 border border-[rgba(167,192,128,0.15)] rounded-2xl overflow-hidden">
        <table class="min-w-full">
            <thead>
            <tr class="border-b border-[rgba(167,192,128,0.12)]">
                <th class="px-6 py-4 text-left text-[10px] font-mono font-medium tracking-[.12em] uppercase text-ef-dim">Date</th>
                <th class="px-6 py-4 text-left text-[10px] font-mono font-medium tracking-[.12em] uppercase text-ef-dim">Description</th>
                <th class="px-6 py-4 text-left text-[10px] font-mono font-medium tracking-[.12em] uppercase text-ef-dim">Type</th>
                <th class="px-6 py-4 text-right text-[10px] font-mono font-medium tracking-[.12em] uppercase text-ef-dim">Montant</th>
                <th class="px-6 py-4 text-left text-[10px] font-mono font-medium tracking-[.12em] uppercase text-ef-dim">Statut</th>
                <th class="px-6 py-4 text-right text-[10px] font-mono font-medium tracking-[.12em] uppercase text-ef-dim">Actions</th>
            </tr>
            </thead>
            <tbody>
            @if(transactions.isEmpty())
                <tr>
                    <td colspan="6" class="px-6 py-16 text-center text-ef-dim text-sm font-mono">
                        Aucune transaction trouvée
                    </td>
                </tr>
            @else
                @for(Transaction transaction : transactions)
                    <tr class="border-b border-[rgba(167,192,128,0.08)] last:border-0 hover:bg-[rgba(167,192,128,0.04)] transition-colors">
                        <td class="px-6 py-4 whitespace-nowrap">
                            <p class="text-[11px] font-mono text-ef-fg">${transaction.applicationDate().value().format(DateTimeFormatter.ISO_DATE)}</p>
                            <p class="text-[10px] font-mono text-ef-dim mt-0.5">
                                Créée le ${transaction.issueDate().value().format(DateTimeFormatter.ISO_DATE)}
                            </p>
                        </td>
                        <td class="px-6 py-4 text-sm text-ef-fg font-medium">
                            ${transaction.description().value()}
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap">
                            @if(transaction.type().name().equals("CREDIT"))
                                <span class="inline-flex items-center gap-1 text-[10px] font-mono px-2.5 py-1 rounded-full bg-[rgba(167,192,128,0.12)] text-ef-green">↗ Crédit</span>
                            @else
                                <span class="inline-flex items-center gap-1 text-[10px] font-mono px-2.5 py-1 rounded-full bg-[rgba(230,126,128,0.12)] text-ef-red">↙ Débit</span>
                            @endif
                        </td>
                        <td class="px-6 py-4 text-right whitespace-nowrap font-mono text-sm font-semibold ${transaction.type().name().equals("CREDIT") ? "text-ef-green" : "text-ef-red"}">
                            ${transaction.type().name().equals("CREDIT") ? "+" : "−"}${String.format("%.2f", transaction.amount().value())} €
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap">
                            @if(transaction.state() == TransactionState.PENDING)
                                <span class="inline-flex items-center gap-1 text-[10px] font-mono px-2.5 py-1 rounded-full bg-[rgba(219,188,127,0.12)] text-ef-yellow">⏳ En attente</span>
                            @elseif(transaction.state() == TransactionState.APPROVED)
                                <span class="inline-flex items-center gap-1 text-[10px] font-mono px-2.5 py-1 rounded-full bg-[rgba(167,192,128,0.12)] text-ef-green">✓ Approuvée</span>
                            @else
                                <span class="inline-flex items-center gap-1 text-[10px] font-mono px-2.5 py-1 rounded-full bg-[rgba(230,126,128,0.12)] text-ef-red">✗ Rejetée</span>
                            @endif
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap text-right">
                            <div class="flex items-center justify-end gap-2">
                                @if(transaction.state() == TransactionState.PENDING)
                                    <form action="/transactions/${transaction.id().value()}/approve" method="post" class="inline">
                                        <button type="submit"
                                                class="text-[10px] font-mono font-medium px-3 py-1.5 rounded-lg border border-[rgba(167,192,128,0.3)] text-ef-green hover:bg-[rgba(167,192,128,0.1)] transition-colors">
                                            Approuver
                                        </button>
                                    </form>
                                    <form action="/transactions/${transaction.id().value()}/reject" method="post" class="inline">
                                        <button type="submit"
                                                class="text-[10px] font-mono font-medium px-3 py-1.5 rounded-lg border border-[rgba(230,126,128,0.3)] text-ef-red hover:bg-[rgba(230,126,128,0.1)] transition-colors">
                                            Rejeter
                                        </button>
                                    </form>
                                @endif
                                <form action="/transactions/${transaction.id().value()}/delete" method="post" class="inline">
                                    <button type="submit"
                                            class="text-[10px] font-mono font-medium px-3 py-1.5 rounded-lg border border-[rgba(167,192,128,0.15)] text-ef-dim hover:bg-[rgba(167,192,128,0.07)] hover:text-ef-fg transition-colors">
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
            <div class="bg-ef-bg1 border border-[rgba(167,192,128,0.15)] rounded-2xl px-6 py-16 text-center text-ef-dim text-sm font-mono">
                Aucune transaction trouvée
            </div>
        @else
            @for(Transaction transaction : transactions)
                <div class="bg-ef-bg1 border border-[rgba(167,192,128,0.15)] rounded-2xl overflow-hidden">

                    <%-- Description + montant --%>
                    <div class="flex items-start justify-between gap-3 px-4 pt-4 pb-3">
                        <div>
                            <p class="text-sm font-medium text-ef-fg leading-snug">${transaction.description().value()}</p>
                            <p class="text-[10px] font-mono text-ef-dim mt-1">Effectif le ${transaction.applicationDate().value().format(DateTimeFormatter.ISO_DATE)}</p>
                        </div>
                        <p class="font-mono text-base font-semibold shrink-0 ${transaction.type().name().equals("CREDIT") ? "text-ef-green" : "text-ef-red"}">
                            ${transaction.type().name().equals("CREDIT") ? "+" : "−"}${String.format("%.2f", transaction.amount().value())} €
                        </p>
                    </div>

                    <%-- Badges + date de création --%>
                    <div class="flex items-center gap-1.5 px-4 pb-3 flex-wrap">
                        @if(transaction.type().name().equals("CREDIT"))
                            <span class="inline-flex items-center gap-1 text-[10px] font-mono px-2.5 py-1 rounded-full bg-[rgba(167,192,128,0.12)] text-ef-green">↗ Crédit</span>
                        @else
                            <span class="inline-flex items-center gap-1 text-[10px] font-mono px-2.5 py-1 rounded-full bg-[rgba(230,126,128,0.12)] text-ef-red">↙ Débit</span>
                        @endif
                        @if(transaction.state() == TransactionState.PENDING)
                            <span class="inline-flex items-center gap-1 text-[10px] font-mono px-2.5 py-1 rounded-full bg-[rgba(219,188,127,0.12)] text-ef-yellow">⏳ En attente</span>
                        @elseif(transaction.state() == TransactionState.APPROVED)
                            <span class="inline-flex items-center gap-1 text-[10px] font-mono px-2.5 py-1 rounded-full bg-[rgba(167,192,128,0.12)] text-ef-green">✓ Approuvée</span>
                        @else
                            <span class="inline-flex items-center gap-1 text-[10px] font-mono px-2.5 py-1 rounded-full bg-[rgba(230,126,128,0.12)] text-ef-red">✗ Rejetée</span>
                        @endif
                        <span class="text-[10px] font-mono text-ef-dim ml-auto">Créée le ${transaction.issueDate().value().format(DateTimeFormatter.ISO_DATE)}</span>
                    </div>

                    <%-- Actions --%>
                    <div class="flex items-center gap-2 px-4 py-2.5 border-t border-[rgba(167,192,128,0.1)] bg-[rgba(0,0,0,0.08)]">
                        @if(transaction.state() == TransactionState.PENDING)
                            <form action="/transactions/${transaction.id().value()}/approve" method="post" class="inline">
                                <button type="submit"
                                        class="text-[10px] font-mono font-medium px-3 py-1.5 rounded-lg border border-[rgba(167,192,128,0.3)] text-ef-green hover:bg-[rgba(167,192,128,0.1)] transition-colors">
                                    Approuver
                                </button>
                            </form>
                            <form action="/transactions/${transaction.id().value()}/reject" method="post" class="inline">
                                <button type="submit"
                                        class="text-[10px] font-mono font-medium px-3 py-1.5 rounded-lg border border-[rgba(230,126,128,0.3)] text-ef-red hover:bg-[rgba(230,126,128,0.1)] transition-colors">
                                    Rejeter
                                </button>
                            </form>
                        @endif
                        <form action="/transactions/${transaction.id().value()}/delete" method="post" class="inline ml-auto">
                            <button type="submit"
                                    class="text-[10px] font-mono font-medium px-3 py-1.5 rounded-lg border border-[rgba(167,192,128,0.15)] text-ef-dim hover:bg-[rgba(167,192,128,0.07)] hover:text-ef-fg transition-colors">
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