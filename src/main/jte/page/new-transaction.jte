@param String error = null

@template.layout.main(
title = "Nouvelle transaction",
content = @`
    <div class="max-w-xl mx-auto">
        <div class="mb-8">
            <a href="/transactions" class="text-xs text-ctp-overlay1 hover:text-ctp-mauve transition-colors">← Retour</a>
            <h1 class="font-nunito text-2xl font-bold text-ctp-text mt-4">Nouvelle transaction</h1>
            <p class="text-sm text-ctp-overlay1 mt-1">Renseignez les informations ci-dessous</p>
        </div>

        @if(error != null)
            <div class="mb-6 flex items-center gap-3 rounded-xl border border-ctp-red/30 bg-ctp-red/10 px-5 py-4">
                <span class="text-ctp-red text-lg">✕</span>
                <p class="text-sm font-nunito font-semibold text-ctp-red">${error}</p>
            </div>
        @endif

        <form action="/transactions" method="post" class="space-y-6">

            <%-- TYPE --%>
            <div>
                <p class="text-[0.65rem] font-nunito font-bold tracking-[.14em] uppercase text-ctp-overlay1 mb-3">Type</p>
                <div class="grid grid-cols-2 gap-3">
                    <div class="relative">
                        <input type="radio" name="type" value="CREDIT" id="type-credit"
                               class="peer sr-only" required checked>
                        <label for="type-credit"
                               class="flex flex-col cursor-pointer rounded-2xl border border-ctp-surface1 bg-ctp-mantle p-5
                                      transition-all
                                      peer-checked:border-ctp-green peer-checked:bg-ctp-green/5
                                      hover:border-ctp-overlay1">
                            <span class="font-nunito text-base font-bold text-ctp-green">↗ Crédit</span>
                            <span class="text-xs text-ctp-overlay1 mt-1">Ajout d'argent</span>
                        </label>
                    </div>
                    <div class="relative">
                        <input type="radio" name="type" value="DEBIT" id="type-debit"
                               class="peer sr-only" required>
                        <label for="type-debit"
                               class="flex flex-col cursor-pointer rounded-2xl border border-ctp-surface1 bg-ctp-mantle p-5
                                      transition-all
                                      peer-checked:border-ctp-red peer-checked:bg-ctp-red/5
                                      hover:border-ctp-overlay1">
                            <span class="font-nunito text-base font-bold text-ctp-red">↙ Débit</span>
                            <span class="text-xs text-ctp-overlay1 mt-1">Retrait d'argent</span>
                        </label>
                    </div>
                </div>
            </div>

            <%-- MONTANT --%>
            <div>
                <label for="amount"
                       class="block text-[0.65rem] font-nunito font-bold tracking-[.14em] uppercase text-ctp-overlay1 mb-3">
                    Montant (€)
                </label>
                <div class="relative">
                    <input type="number" id="amount" name="amount"
                           step="0.01" min="0.01" required placeholder="0.00"
                           class="w-full bg-ctp-mantle border border-ctp-surface1 rounded-xl px-5 py-3.5 pr-10
                                  text-ctp-text font-nunito text-lg font-bold
                                  placeholder:text-ctp-surface2
                                  focus:outline-none focus:border-ctp-mauve focus:ring-1 focus:ring-ctp-mauve/30
                                  transition-colors">
                    <span class="absolute right-4 top-1/2 -translate-y-1/2 text-ctp-overlay1 text-sm font-semibold">€</span>
                </div>
            </div>

            <%-- DATE --%>
            <div>
                <label for="effective-date"
                       class="block text-[0.65rem] font-nunito font-bold tracking-[.14em] uppercase text-ctp-overlay1 mb-3">
                    Date effective
                </label>
                <input type="date"
                       id="effective-date"
                       name="effective-date"
                       required
                       class="w-full bg-ctp-mantle border border-ctp-surface1 rounded-xl px-5 py-3.5
                              text-ctp-text font-nunito text-sm font-semibold
                              focus:outline-none focus:border-ctp-mauve focus:ring-1 focus:ring-ctp-mauve/30
                              transition-colors
                              [&::-webkit-calendar-picker-indicator]:opacity-40
                              [&::-webkit-calendar-picker-indicator]:invert">
            </div>

            <%-- DESCRIPTION --%>
            <div>
                <label for="description"
                       class="block text-[0.65rem] font-nunito font-bold tracking-[.14em] uppercase text-ctp-overlay1 mb-3">
                    Description
                </label>
                <textarea id="description" name="description" rows="3"
                          placeholder="Ex : Salaire mensuel, Achat supermarché..."
                          class="w-full bg-ctp-mantle border border-ctp-surface1 rounded-xl px-5 py-3.5
                                 text-ctp-text text-sm placeholder:text-ctp-surface2
                                 focus:outline-none focus:border-ctp-mauve focus:ring-1 focus:ring-ctp-mauve/30
                                 transition-colors resize-none"></textarea>
            </div>

            <%-- ACTIONS --%>
            <div class="flex gap-3 pt-2">
                <button type="submit"
                        class="flex-1 font-nunito font-bold text-sm py-3.5 px-6 rounded-xl
                               bg-ctp-mauve text-ctp-crust hover:opacity-85 transition-opacity">
                    Créer la transaction
                </button>
                <a href="/transactions"
                   class="flex-1 font-nunito font-bold text-sm py-3.5 px-6 rounded-xl text-center
                          bg-ctp-mantle border border-ctp-surface1 text-ctp-overlay1 hover:bg-ctp-surface0 hover:text-ctp-text transition-colors">
                    Annuler
                </a>
            </div>

        </form>
    </div>
`
)
