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

    <!-- Tailwind -->
    <script src="https://cdn.tailwindcss.com"></script>
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        ctp: {
                            crust:    '#11111b',
                            mantle:   '#181825',
                            base:     '#1e1e2e',
                            surface0: '#313244',
                            surface1: '#45475a',
                            surface2: '#585b70',
                            overlay0: '#6c7086',
                            overlay1: '#7f849c',
                            overlay2: '#9399b2',
                            subtext0: '#a6adc8',
                            subtext1: '#bac2de',
                            text:     '#cdd6f4',
                            teal:     '#94e2d5',
                            green:    '#a6e3a1',
                            yellow:   '#f9e2af',
                            peach:    '#fab387',
                            red:      '#f38ba8',
                            mauve:    '#cba6f7',
                            pink:     '#f5c2e7',
                        }
                    },
                    fontFamily: {
                        nunito: ['Nunito', 'sans-serif'],
                    }
                }
            }
        }
    </script>

    <!-- Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@400;500;600;700;800&display=swap" rel="stylesheet">
</head>
<body class="bg-ctp-base text-ctp-text font-nunito min-h-screen flex items-center justify-center p-6">

    <div class="w-full max-w-md">

        <!-- LOGO -->
        <div class="text-center mb-8">
            <a href="/" class="font-nunito text-2xl font-extrabold text-ctp-mauve hover:opacity-80 transition-opacity">
                🐱 Fortress
            </a>
            <p class="text-sm text-ctp-overlay1 mt-1">Gestion financière personnelle</p>
        </div>

        <!-- CARTE -->
        <div class="bg-ctp-mantle border border-ctp-surface0 rounded-2xl p-8">
            <h1 class="font-nunito text-xl font-extrabold text-ctp-text mb-6">Créer un compte</h1>

            <!-- ERREUR -->
            @if(error != null)
                <div class="flex items-start gap-3 bg-ctp-red/10 border border-ctp-red/30 rounded-xl px-4 py-3 mb-6">
                    <span class="text-ctp-red text-sm shrink-0 mt-0.5">⚠</span>
                    <p class="text-ctp-red text-xs leading-relaxed">${error}</p>
                </div>
            @endif

            <form action="/sign-up" method="post" class="space-y-5">

                <!-- EMAIL -->
                <div>
                    <label for="email"
                           class="block text-[0.62rem] font-bold tracking-[.12em] uppercase text-ctp-overlay1 mb-2">
                        Adresse email
                    </label>
                    <input type="email" id="email" name="email"
                           placeholder="jean.dupont@email.com" required
                           class="w-full bg-ctp-base border border-ctp-surface1 rounded-xl px-4 py-3
                                  text-ctp-text text-sm font-semibold placeholder:text-ctp-surface2
                                  focus:outline-none focus:border-ctp-mauve focus:ring-1 focus:ring-ctp-mauve/30
                                  transition-colors">
                </div>

                <!-- MOT DE PASSE -->
                <div>
                    <label for="password"
                           class="block text-[0.62rem] font-bold tracking-[.12em] uppercase text-ctp-overlay1 mb-2">
                        Mot de passe
                    </label>
                    <input type="password" id="password" name="password"
                           placeholder="••••••••" minlength="8" required
                           class="w-full bg-ctp-base border border-ctp-surface1 rounded-xl px-4 py-3
                                  text-ctp-text text-sm font-semibold placeholder:text-ctp-surface2
                                  focus:outline-none focus:border-ctp-mauve focus:ring-1 focus:ring-ctp-mauve/30
                                  transition-colors">
                    <p class="text-[0.67rem] text-ctp-overlay0 mt-1.5">Minimum 8 caractères</p>
                </div>

                <!-- CONFIRMER -->
                <div>
                    <label for="confirm"
                           class="block text-[0.62rem] font-bold tracking-[.12em] uppercase text-ctp-overlay1 mb-2">
                        Confirmer le mot de passe
                    </label>
                    <input type="password" id="confirm" name="confirm"
                           placeholder="••••••••" required
                           class="w-full bg-ctp-base border border-ctp-surface1 rounded-xl px-4 py-3
                                  text-ctp-text text-sm font-semibold placeholder:text-ctp-surface2
                                  focus:outline-none focus:border-ctp-mauve focus:ring-1 focus:ring-ctp-mauve/30
                                  transition-colors">
                </div>

                <!-- SUBMIT -->
                <button type="submit"
                        class="w-full font-nunito font-bold text-sm py-3.5 rounded-xl
                               bg-ctp-mauve text-ctp-crust hover:opacity-85 transition-opacity mt-1">
                    Créer mon compte
                </button>

            </form>

            <!-- SÉPARATEUR -->
            <div class="flex items-center gap-3 my-5">
                <div class="flex-1 h-px bg-ctp-surface0"></div>
                <span class="text-[0.68rem] text-ctp-overlay0">déjà inscrit ?</span>
                <div class="flex-1 h-px bg-ctp-surface0"></div>
            </div>

            <!-- LIEN CONNEXION -->
            <p class="text-center text-sm text-ctp-overlay1">
                <a href="/sign-in" class="text-ctp-mauve font-bold hover:opacity-80 transition-opacity">
                    Se connecter →
                </a>
            </p>
        </div>

        <!-- FOOTER -->
        <p class="text-center text-[0.68rem] text-ctp-overlay0 mt-6">
            © 2026 Fortress — Gestion financière
        </p>

    </div>

</body>
</html>
