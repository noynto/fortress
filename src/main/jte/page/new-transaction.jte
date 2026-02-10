@template.layout.main(
title = "Nouvelle transaction",
content = @`
    <div class="max-w-2xl mx-auto">
        <div class="bg-white rounded-lg shadow p-8">
            <h2 class="text-2xl font-bold mb-6">Créer une nouvelle transaction</h2>

            <form action="/transactions/create" method="post" class="space-y-6">
                <!-- Type -->
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">Type de transaction</label>
                    <div class="grid grid-cols-2 gap-4">
                        <label class="relative flex cursor-pointer rounded-lg border border-gray-300 bg-white p-4 hover:border-green-500 focus:outline-none">
                            <input type="radio" name="type" value="CREDIT" class="sr-only" required checked>
                            <span class="flex flex-1">
                                    <span class="flex flex-col">
                                        <span class="block text-lg font-semibold text-green-600">↗ CRÉDIT</span>
                                        <span class="mt-1 flex items-center text-sm text-gray-500">Ajout d'argent</span>
                                    </span>
                                </span>
                        </label>

                        <label class="relative flex cursor-pointer rounded-lg border border-gray-300 bg-white p-4 hover:border-red-500 focus:outline-none">
                            <input type="radio" name="type" value="DEBIT" class="sr-only" required>
                            <span class="flex flex-1">
                                    <span class="flex flex-col">
                                        <span class="block text-lg font-semibold text-red-600">↙ DÉBIT</span>
                                        <span class="mt-1 flex items-center text-sm text-gray-500">Retrait d'argent</span>
                                    </span>
                                </span>
                        </label>
                    </div>
                </div>

                <!-- Montant -->
                <div>
                    <label for="amount" class="block text-sm font-medium text-gray-700 mb-2">
                        Montant (€)
                    </label>
                    <input
                            type="number"
                            id="amount"
                            name="amount"
                            step="0.01"
                            min="0.01"
                            required
                            class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                            placeholder="100.00"
                    >
                </div>

                <!-- Description -->
                <div>
                    <label for="description" class="block text-sm font-medium text-gray-700 mb-2">
                        Description
                    </label>
                    <textarea
                            id="description"
                            name="description"
                            rows="3"
                            class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                            placeholder="Exemple: Salaire mensuel, Achat supermarché..."
                    ></textarea>
                </div>

                <!-- Boutons -->
                <div class="flex gap-4">
                    <button
                            type="submit"
                            class="flex-1 bg-blue-600 text-white py-3 px-4 rounded-lg hover:bg-blue-700 font-semibold"
                    >
                        Créer la transaction
                    </button>
                    <a
                            href="/"
                            class="flex-1 bg-gray-200 text-gray-700 py-3 px-4 rounded-lg hover:bg-gray-300 font-semibold text-center"
                    >
                        Annuler
                    </a>
                </div>
            </form>
        </div>
    </div>
`
)