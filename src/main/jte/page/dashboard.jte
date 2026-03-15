@import java.math.BigDecimal

@param BigDecimal balance
@param BigDecimal forecastBalance
@param BigDecimal pendingCredits
@param BigDecimal pendingDebits
@param Long pendingCount
@param Long approvedCount
@param Long rejectedCount

@template.layout.main(
title = "Fortress — Dashboard",
content = @`

<%-- TOPBAR --%>
    <div class="flex flex-wrap items-center justify-between gap-3 mb-10">
        <div class="flex flex-wrap items-center gap-2">
            <span class="font-nunito text-lg font-bold text-ctp-mauve">🐱 Fortress</span>
            <span class="text-xs font-semibold px-2.5 py-1 rounded-full bg-ctp-surface0 text-ctp-overlay2">Dashboard</span>
        </div>
        <div class="flex flex-wrap items-center gap-2">
            <a href="/transactions"
               class="font-nunito text-xs font-bold px-4 py-2.5 rounded-xl border border-ctp-surface1 text-ctp-overlay1 hover:bg-ctp-surface0 hover:text-ctp-text transition-colors">
                Voir les transactions →
            </a>
            <a href="/transactions/new"
               class="font-nunito text-xs font-bold px-4 py-2.5 rounded-xl bg-ctp-mauve text-ctp-crust hover:opacity-85 transition-opacity">
                + Nouvelle transaction
            </a>
        </div>
    </div>

    <%-- PRÉVISIONNEL --%>
    <p class="font-nunito text-[0.65rem] font-bold tracking-[.14em] uppercase text-ctp-overlay1 mb-4">Prévisionnel</p>

    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-3 mb-3">
        <div class="bg-ctp-mantle border border-ctp-surface0 rounded-2xl p-5">
            <p class="text-[0.68rem] font-bold uppercase tracking-wider text-ctp-overlay1 mb-2">Solde actuel</p>
            <p class="font-nunito text-2xl font-bold ${balance.compareTo(BigDecimal.ZERO) >= 0 ? "text-ctp-green" : "text-ctp-red"}">
                ${balance.compareTo(BigDecimal.ZERO) >= 0 ? "+" : ""}${String.format("%.2f", balance)} €
            </p>
            <p class="text-[0.7rem] text-ctp-overlay0 mt-2">Transactions approuvées</p>
        </div>
        <div class="bg-ctp-crust border border-ctp-mauve rounded-2xl p-5">
            <p class="text-[0.68rem] font-bold uppercase tracking-wider text-ctp-overlay1 mb-2">Solde prévisionnel</p>
            <p class="font-nunito text-2xl font-bold text-ctp-mauve">
                ${forecastBalance.compareTo(BigDecimal.ZERO) >= 0 ? "+" : ""}${String.format("%.2f", forecastBalance)} €
            </p>
            <p class="text-[0.7rem] text-ctp-overlay0 mt-2">${pendingCount} transaction(s) en attente</p>
        </div>
        <div class="bg-ctp-mantle border border-ctp-surface0 rounded-2xl p-5">
            <p class="text-[0.68rem] font-bold uppercase tracking-wider text-ctp-overlay1 mb-2">Crédits à venir</p>
            <p class="font-nunito text-2xl font-bold text-ctp-green">+${String.format("%.2f", pendingCredits)} €</p>
            <p class="text-[0.7rem] text-ctp-overlay0 mt-2">En attente d'approbation</p>
        </div>
        <div class="bg-ctp-mantle border border-ctp-surface0 rounded-2xl p-5">
            <p class="text-[0.68rem] font-bold uppercase tracking-wider text-ctp-overlay1 mb-2">Débits à venir</p>
            <p class="font-nunito text-2xl font-bold text-ctp-red">−${String.format("%.2f", pendingDebits)} €</p>
            <p class="text-[0.7rem] text-ctp-overlay0 mt-2">En attente d'approbation</p>
        </div>
    </div>

    <%-- RÉSUMÉ PAR STATUT --%>
    <p class="font-nunito text-[0.65rem] font-bold tracking-[.14em] uppercase text-ctp-overlay1 mb-4">Résumé</p>
    <div class="grid grid-cols-1 sm:grid-cols-3 gap-3">
        <div class="bg-ctp-mantle border border-ctp-surface0 rounded-2xl p-5 flex items-center gap-4">
            <div class="w-10 h-10 rounded-xl bg-ctp-yellow/15 flex items-center justify-center text-ctp-yellow text-lg">
                ⏳
            </div>
            <div>
                <p class="font-nunito text-xl font-bold text-ctp-yellow">${pendingCount}</p>
                <p class="text-[0.7rem] text-ctp-overlay1 font-semibold">En attente</p>
            </div>
        </div>
        <div class="bg-ctp-mantle border border-ctp-surface0 rounded-2xl p-5 flex items-center gap-4">
            <div class="w-10 h-10 rounded-xl bg-ctp-green/15 flex items-center justify-center text-ctp-green text-lg">
                ✓
            </div>
            <div>
                <p class="font-nunito text-xl font-bold text-ctp-green">${approvedCount}</p>
                <p class="text-[0.7rem] text-ctp-overlay1 font-semibold">Approuvées</p>
            </div>
        </div>
        <div class="bg-ctp-mantle border border-ctp-surface0 rounded-2xl p-5 flex items-center gap-4">
            <div class="w-10 h-10 rounded-xl bg-ctp-red/15 flex items-center justify-center text-ctp-red text-lg">✗
            </div>
            <div>
                <p class="font-nunito text-xl font-bold text-ctp-red">${rejectedCount}</p>
                <p class="text-[0.7rem] text-ctp-overlay1 font-semibold">Rejetées</p>
            </div>
        </div>
    </div>
`
)