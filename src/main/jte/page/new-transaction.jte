@param String error = null

@template.layout.main(
title = "Nouvelle transaction",
content = @`
    <div class="max-w-xl mx-auto">
        <div class="mb-8">
            <a href="/transactions" class="text-[11px] font-mono text-ef-dim hover:text-ef-green transition-colors">← Retour</a>
            <h1 class="font-serif text-2xl text-ef-fg mt-4">Nouvelle transaction</h1>
            <p class="text-sm font-mono text-ef-dim mt-1">Renseignez les informations ci-dessous</p>
        </div>

        @if(error != null)
            <div class="mb-6 flex items-center gap-3 rounded-xl border border-[rgba(230,126,128,0.3)] bg-[rgba(230,126,128,0.1)] px-5 py-4">
                <span class="text-ef-red text-lg shrink-0">✕</span>
                <p class="text-sm font-mono text-ef-red">${error}</p>
            </div>
        @endif

        <form action="/transactions" method="post" class="space-y-6">

            <%-- TYPE --%>
            <div>
                <p class="text-[10px] font-mono font-medium tracking-[.14em] uppercase text-ef-dim mb-3">Type</p>
                <div class="grid grid-cols-2 gap-3">
                    <div class="relative">
                        <input type="radio" name="type" value="CREDIT" id="type-credit"
                               class="peer sr-only" required checked>
                        <label for="type-credit"
                               class="flex flex-col cursor-pointer rounded-2xl border border-[rgba(167,192,128,0.15)] bg-ef-bg1 p-5
                                      transition-all
                                      peer-checked:border-ef-green peer-checked:bg-[rgba(167,192,128,0.06)]
                                      hover:border-[rgba(167,192,128,0.3)]">
                            <span class="font-mono text-base font-semibold text-ef-green">↗ Crédit</span>
                            <span class="text-[11px] font-mono text-ef-dim mt-1">Ajout d'argent</span>
                        </label>
                    </div>
                    <div class="relative">
                        <input type="radio" name="type" value="DEBIT" id="type-debit"
                               class="peer sr-only" required>
                        <label for="type-debit"
                               class="flex flex-col cursor-pointer rounded-2xl border border-[rgba(167,192,128,0.15)] bg-ef-bg1 p-5
                                      transition-all
                                      peer-checked:border-ef-red peer-checked:bg-[rgba(230,126,128,0.06)]
                                      hover:border-[rgba(167,192,128,0.3)]">
                            <span class="font-mono text-base font-semibold text-ef-red">↙ Débit</span>
                            <span class="text-[11px] font-mono text-ef-dim mt-1">Retrait d'argent</span>
                        </label>
                    </div>
                </div>
            </div>

            <%-- MONTANT --%>
            <div>
                <label for="amount"
                       class="block text-[10px] font-mono font-medium tracking-[.14em] uppercase text-ef-dim mb-3">
                    Montant (€)
                </label>
                <div class="relative">
                    <input type="number" id="amount" name="amount"
                           step="0.01" min="0.01" required placeholder="0.00"
                           class="w-full bg-ef-bg2 border border-[rgba(167,192,128,0.15)] rounded-xl px-5 py-3.5 pr-10
                                  text-ef-fg font-mono text-lg font-semibold
                                  placeholder:text-ef-bg4
                                  focus:outline-none focus:border-ef-green focus:ring-1 focus:ring-[rgba(167,192,128,0.25)]
                                  transition-colors">
                    <span class="absolute right-4 top-1/2 -translate-y-1/2 text-ef-dim text-sm font-mono">€</span>
                </div>
            </div>

            <%-- DATE --%>
            <div>
                <label for="effective-date"
                       class="block text-[10px] font-mono font-medium tracking-[.14em] uppercase text-ef-dim mb-3">
                    Date effective
                </label>
                <input type="date"
                       id="effective-date"
                       name="effective-date"
                       required
                       class="w-full bg-ef-bg2 border border-[rgba(167,192,128,0.15)] rounded-xl px-5 py-3.5
                              text-ef-fg font-mono text-sm
                              focus:outline-none focus:border-ef-green focus:ring-1 focus:ring-[rgba(167,192,128,0.25)]
                              transition-colors
                              [&::-webkit-calendar-picker-indicator]:opacity-40
                              [&::-webkit-calendar-picker-indicator]:invert">
            </div>

            <%-- DESCRIPTION --%>
            <div>
                <label for="description"
                       class="block text-[10px] font-mono font-medium tracking-[.14em] uppercase text-ef-dim mb-3">
                    Description
                </label>
                <textarea id="description" name="description" rows="3"
                          placeholder="Ex : Salaire mensuel, Achat supermarché..."
                          class="w-full bg-ef-bg2 border border-[rgba(167,192,128,0.15)] rounded-xl px-5 py-3.5
                                 text-ef-fg text-sm font-mono placeholder:text-ef-bg4
                                 focus:outline-none focus:border-ef-green focus:ring-1 focus:ring-[rgba(167,192,128,0.25)]
                                 transition-colors resize-none"></textarea>
            </div>

            <%-- ACTIONS --%>
            <div class="flex gap-3 pt-2">
                <button type="submit"
                        class="flex-1 font-medium text-sm py-3.5 px-6 rounded-xl
                               bg-ef-green text-ef-bg0 hover:bg-[#b8d494] transition-colors">
                    Créer la transaction
                </button>
                <a href="/transactions"
                   class="flex-1 font-medium text-sm py-3.5 px-6 rounded-xl text-center
                          bg-ef-bg1 border border-[rgba(167,192,128,0.2)] text-ef-dim hover:border-[rgba(167,192,128,0.35)] hover:text-ef-fg transition-colors">
                    Annuler
                </a>
            </div>

        </form>
    </div>
`
)