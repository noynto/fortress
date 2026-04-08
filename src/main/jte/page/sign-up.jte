@param String error = null

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fortress — Inscription</title>

    <!-- Icône -->
    <link rel="icon" type="image/png" href="/icons/icon-192.png">
    <link rel="apple-touch-icon" href="/icons/icon-192.png">

    <!-- SEO -->
    <meta name="description" content="Créez votre compte Fortress gratuitement et prenez le contrôle de vos finances.">
    <meta name="robots" content="noindex, nofollow">

    <!-- Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display:ital@0;1&family=DM+Mono:wght@400;500&family=Sora:wght@300;400;500;600&display=swap" rel="stylesheet">

    <!-- Tailwind -->
    <script src="https://cdn.tailwindcss.com"></script>
    <script>
        tailwind.config = {
            theme: {
                screens: { sm:'640px', md:'768px', lg:'1024px', xl:'1280px' },
                extend: {
                    colors: {
                        ef: {
                            bg0:'#2D353B', bg1:'#343F44', bg2:'#3D484D',
                            bg3:'#475258', bg4:'#4F585E',
                            fg:'#D3C6AA', dim:'#9DA9A0',
                            green:'#A7C080', yellow:'#DBBC7F',
                            red:'#E67E80', blue:'#7FBBB3',
                        },
                    },
                    fontFamily: {
                        serif: ['"DM Serif Display"', 'serif'],
                        mono:  ['"DM Mono"', 'monospace'],
                        sans:  ['Sora', 'sans-serif'],
                    },
                },
            },
        }
    </script>

    <style>
        .bef-base   { border-color: rgba(167,192,128,0.15); }
        .bef-strong { border-color: rgba(167,192,128,0.30); }
        .bg-g10     { background-color: rgba(167,192,128,0.10); }
        .bg-r10     { background-color: rgba(230,126,128,0.10); }
        .border-r30 { border-color: rgba(230,126,128,0.30); }

        input:-webkit-autofill,
        input:-webkit-autofill:focus {
            -webkit-box-shadow: 0 0 0 1000px #3D484D inset !important;
            -webkit-text-fill-color: #D3C6AA !important;
            caret-color: #D3C6AA;
        }
    </style>
</head>

<body class="bg-ef-bg0 text-ef-fg font-sans antialiased min-h-screen flex flex-col items-center justify-center p-4 sm:p-6">

<div class="w-full max-w-[420px]">

    <!-- LOGO -->
    <div class="text-center mb-8">
        <a href="/" class="font-serif text-2xl text-ef-green hover:opacity-80 transition-opacity inline-block">
            🌲 Fortress
        </a>
        <p class="text-[12px] font-mono text-ef-dim mt-1.5 uppercase tracking-wider">Gestion financière personnelle</p>
    </div>

    <!-- CARTE -->
    <div class="bg-ef-bg1 border bef-base rounded-2xl p-7 sm:p-8">

        <h1 class="font-serif text-[22px] text-ef-fg mb-1.5 leading-tight">Créer un compte</h1>
        <p class="text-[12px] text-ef-dim mb-6">Gratuit, sans carte bancaire.</p>

        <!-- ERREUR -->
        @if(error != null)
            <div class="flex items-start gap-3 bg-r10 border border-r30 rounded-xl px-4 py-3 mb-6">
                <span class="text-ef-red text-sm shrink-0 mt-0.5">⚠</span>
                <p class="text-ef-red text-xs leading-relaxed">${error}</p>
            </div>
        @endif

        <form action="/sign-up" method="post" autocomplete="on" class="space-y-5">

            <!-- EMAIL -->
            <div>
                <label for="email" class="block text-[10px] font-mono font-medium tracking-[.12em] uppercase text-ef-dim mb-2">
                    Adresse email
                </label>
                <input type="email" id="email" name="email"
                       autocomplete="email" autocapitalize="none" spellcheck="false"
                       placeholder="jean.dupont@email.com" required
                       class="w-full bg-ef-bg2 border bef-base rounded-xl px-4 py-3
                                  text-ef-fg text-sm placeholder:text-ef-bg4
                                  focus:outline-none focus:border-ef-green focus:ring-1 focus:ring-[rgba(167,192,128,0.25)]
                                  transition-colors">
            </div>

            <!-- MOT DE PASSE -->
            <div>
                <label for="password" class="block text-[10px] font-mono font-medium tracking-[.12em] uppercase text-ef-dim mb-2">
                    Mot de passe
                </label>
                <input type="password" id="password" name="password"
                       autocomplete="new-password"
                       placeholder="••••••••" minlength="8" required
                       class="w-full bg-ef-bg2 border bef-base rounded-xl px-4 py-3
                                  text-ef-fg text-sm placeholder:text-ef-bg4
                                  focus:outline-none focus:border-ef-green focus:ring-1 focus:ring-[rgba(167,192,128,0.25)]
                                  transition-colors">
                <p class="text-[10px] font-mono text-ef-dim mt-1.5">Minimum 8 caractères</p>
            </div>

            <!-- CONFIRMER -->
            <div>
                <label for="confirm" class="block text-[10px] font-mono font-medium tracking-[.12em] uppercase text-ef-dim mb-2">
                    Confirmer le mot de passe
                </label>
                <input type="password" id="confirm" name="confirm"
                       autocomplete="new-password"
                       placeholder="••••••••" required
                       class="w-full bg-ef-bg2 border bef-base rounded-xl px-4 py-3
                                  text-ef-fg text-sm placeholder:text-ef-bg4
                                  focus:outline-none focus:border-ef-green focus:ring-1 focus:ring-[rgba(167,192,128,0.25)]
                                  transition-colors">
            </div>

            <!-- SUBMIT -->
            <button type="submit"
                    class="w-full bg-ef-green text-ef-bg0 font-semibold text-sm py-3.5 rounded-xl
                               hover:bg-[#b8d494] transition-colors mt-1">
                Créer mon compte
            </button>

        </form>

        <!-- BADGE SÉCURITÉ -->
        <div class="flex items-center justify-center gap-1.5 mt-5 text-[10px] font-mono text-ef-dim">
            <span>🔐</span>
            <span>Données chiffrées AES-256 · TLS 1.3</span>
        </div>

        <!-- SÉPARATEUR -->
        <div class="flex items-center gap-3 my-5">
            <div class="flex-1 h-px bg-[rgba(167,192,128,0.1)]"></div>
            <span class="text-[10px] font-mono text-ef-dim uppercase tracking-wider">déjà inscrit ?</span>
            <div class="flex-1 h-px bg-[rgba(167,192,128,0.1)]"></div>
        </div>

        <!-- LIEN CONNEXION -->
        <p class="text-center text-sm text-ef-dim">
            <a href="/sign-in" class="text-ef-green font-medium hover:opacity-80 transition-opacity">
                Se connecter →
            </a>
        </p>
    </div>

    <!-- FOOTER -->
    <p class="text-center text-[10px] font-mono text-ef-bg4 mt-6">© 2026 Fortress — Gestion financière</p>

</div>

</body>
</html>
