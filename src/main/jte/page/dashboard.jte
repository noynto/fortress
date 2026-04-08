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
            <span class="font-serif text-lg text-ef-green">🌲 Fortress</span>
            <span class="text-[10px] font-mono font-medium px-2.5 py-1 rounded-full bg-[rgba(167,192,128,0.1)] text-ef-dim border border-[rgba(167,192,128,0.2)]">Dashboard</span>
        </div>
        <div class="flex flex-wrap items-center gap-2">
            <a href="/transactions"
               class="text-[11px] font-medium px-4 py-2.5 rounded-xl border border-[rgba(167,192,128,0.25)] text-ef-dim hover:border-ef-green hover:text-ef-green transition-colors">
                Voir les transactions →
            </a>
            <a href="/transactions/new"
               class="text-[11px] font-medium px-4 py-2.5 rounded-xl bg-ef-green text-ef-bg0 hover:bg-[#b8d494] transition-colors">
                + Nouvelle transaction
            </a>
        </div>
    </div>

    <%-- PRÉVISIONNEL --%>
    <p class="text-[10px] font-mono uppercase tracking-[.14em] text-ef-dim mb-4">Prévisionnel</p>

    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-3 mb-3">
        <div class="bg-ef-bg1 border border-[rgba(167,192,128,0.15)] rounded-2xl p-5">
            <p class="text-[10px] font-mono uppercase tracking-wider text-ef-dim mb-2">Solde actuel</p>
            <p class="font-mono text-2xl font-semibold ${balance.compareTo(BigDecimal.ZERO) >= 0 ? "text-ef-green" : "text-ef-red"}">
                ${balance.compareTo(BigDecimal.ZERO) >= 0 ? "+" : ""}${String.format("%.2f", balance)} €
            </p>
            <p class="text-[10px] font-mono text-ef-dim mt-2">Transactions approuvées</p>
        </div>
        <div class="bg-ef-bg1 border border-[rgba(167,192,128,0.35)] rounded-2xl p-5">
            <p class="text-[10px] font-mono uppercase tracking-wider text-ef-dim mb-2">Solde prévisionnel</p>
            <p class="font-mono text-2xl font-semibold text-ef-blue">
                ${forecastBalance.compareTo(BigDecimal.ZERO) >= 0 ? "+" : ""}${String.format("%.2f", forecastBalance)} €
            </p>
            <p class="text-[10px] font-mono text-ef-dim mt-2">${pendingCount} transaction(s) en attente</p>
        </div>
        <div class="bg-ef-bg1 border border-[rgba(167,192,128,0.15)] rounded-2xl p-5">
            <p class="text-[10px] font-mono uppercase tracking-wider text-ef-dim mb-2">Crédits à venir</p>
            <p class="font-mono text-2xl font-semibold text-ef-green">+${String.format("%.2f", pendingCredits)} €</p>
            <p class="text-[10px] font-mono text-ef-dim mt-2">En attente d'approbation</p>
        </div>
        <div class="bg-ef-bg1 border border-[rgba(167,192,128,0.15)] rounded-2xl p-5">
            <p class="text-[10px] font-mono uppercase tracking-wider text-ef-dim mb-2">Débits à venir</p>
            <p class="font-mono text-2xl font-semibold text-ef-red">−${String.format("%.2f", pendingDebits)} €</p>
            <p class="text-[10px] font-mono text-ef-dim mt-2">En attente d'approbation</p>
        </div>
    </div>

    <%-- RÉSUMÉ PAR STATUT --%>
    <p class="text-[10px] font-mono uppercase tracking-[.14em] text-ef-dim mb-4">Résumé</p>
    <div class="grid grid-cols-1 sm:grid-cols-3 gap-3">
        <div class="bg-ef-bg1 border border-[rgba(167,192,128,0.15)] rounded-2xl p-5 flex items-center gap-4">
            <div class="w-10 h-10 rounded-xl bg-[rgba(219,188,127,0.12)] flex items-center justify-center text-ef-yellow text-lg shrink-0">
                ⏳
            </div>
            <div>
                <p class="font-mono text-xl font-semibold text-ef-yellow">${pendingCount}</p>
                <p class="text-[11px] font-mono text-ef-dim mt-0.5">En attente</p>
            </div>
        </div>
        <div class="bg-ef-bg1 border border-[rgba(167,192,128,0.15)] rounded-2xl p-5 flex items-center gap-4">
            <div class="w-10 h-10 rounded-xl bg-[rgba(167,192,128,0.12)] flex items-center justify-center text-ef-green text-lg shrink-0">
                ✓
            </div>
            <div>
                <p class="font-mono text-xl font-semibold text-ef-green">${approvedCount}</p>
                <p class="text-[11px] font-mono text-ef-dim mt-0.5">Approuvées</p>
            </div>
        </div>
        <div class="bg-ef-bg1 border border-[rgba(167,192,128,0.15)] rounded-2xl p-5 flex items-center gap-4">
            <div class="w-10 h-10 rounded-xl bg-[rgba(230,126,128,0.12)] flex items-center justify-center text-ef-red text-lg shrink-0">
                ✗
            </div>
            <div>
                <p class="font-mono text-xl font-semibold text-ef-red">${rejectedCount}</p>
                <p class="text-[11px] font-mono text-ef-dim mt-0.5">Rejetées</p>
            </div>
        </div>
    </div>
`
)