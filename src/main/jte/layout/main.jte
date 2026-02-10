@param gg.jte.Content content
@param String title = "Gestion de Transactions"

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen">
<!-- Navigation -->
<nav class="bg-white shadow-lg">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between h-16">
            <div class="flex items-center">
                <h1 class="text-xl font-bold text-gray-800">💰 Gestion de Transactions</h1>
            </div>
            <div class="flex items-center space-x-4">
                <a href="/" class="text-gray-700 hover:text-blue-600">Transactions</a>
                <a href="/new" class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700">
                    + Nouvelle transaction
                </a>
            </div>
        </div>
    </div>
</nav>

<!-- Contenu principal -->
<main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
    ${content}
</main>

<!-- Footer -->
<footer class="bg-white mt-12 py-6 border-t">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center text-gray-600">
        <p>Architecture CQRS avec Helidon SE & JTE</p>
    </div>
</footer>
</body>
</html>
