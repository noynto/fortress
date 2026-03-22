@param gg.jte.Content content
@param String title = "Fortress"

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>

    <!-- Icône -->
    <link rel="icon" type="image/png" href="/icons/icon-192.png">
    <link rel="apple-touch-icon" href="/icons/icon-192.png">

    <!-- PWA -->
    <link rel="manifest" href="/manifest.json">
    <meta name="theme-color" content="#1e1e2e">
    <meta name="mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    <meta name="apple-mobile-web-app-title" content="Fortress">

    <!-- Service Worker -->
    <script>
        if ('serviceWorker' in navigator) {
            window.addEventListener('load', () => {
                navigator.serviceWorker.register('/service-worker.js');
            });
        }
    </script>
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
                            lavender: '#b4befe',
                            blue:     '#89b4fa',
                            sapphire: '#74c7ec',
                            teal:     '#94e2d5',
                            green:    '#a6e3a1',
                            yellow:   '#f9e2af',
                            peach:    '#fab387',
                            maroon:   '#eba0ac',
                            red:      '#f38ba8',
                            mauve:    '#cba6f7',
                            pink:     '#f5c2e7',
                            flamingo: '#f2cdcd',
                            rosewater:'#f5e0dc',
                        }
                    },
                    fontFamily: {
                        nunito: ['Nunito', 'sans-serif'],
                    }
                }
            }
        }
    </script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@400;500;600;700&display=swap" rel="stylesheet">
</head>
<body class="bg-ctp-base text-ctp-text min-h-screen font-nunito">

<main class="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-6 sm:py-8">
    ${content}
</main>

<footer class="bg-ctp-mantle border-t border-ctp-surface0 mt-12 py-10">
    <div class="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8">

        <%-- HAUT DU FOOTER --%>
        <div class="flex flex-wrap justify-between gap-8 mb-8">

            <%-- BRANDING --%>
            <div>
                <p class="font-nunito text-base font-bold text-ctp-mauve mb-2">🐱 Fortress</p>
                <p class="text-xs text-ctp-overlay0 leading-relaxed max-w-[200px]">
                    Suivez, approuvez et analysez vos flux financiers en temps réel.
                </p>
            </div>

            <%-- LIENS --%>
            <div class="flex flex-wrap gap-12">
                <div>
                    <p class="text-[0.62rem] font-bold tracking-[.12em] uppercase text-ctp-overlay0 mb-3">Navigation</p>
                    <div class="flex flex-col gap-2">
                        <a href="/dashboard" class="text-xs text-ctp-subtext0 hover:text-ctp-mauve transition-colors">Dashboard</a>
                        <a href="/transactions" class="text-xs text-ctp-subtext0 hover:text-ctp-mauve transition-colors">Transactions</a>
                        <a href="/transactions/new" class="text-xs text-ctp-subtext0 hover:text-ctp-mauve transition-colors">Nouvelle transaction</a>
                    </div>
                </div>
                <div>
                    <p class="text-[0.62rem] font-bold tracking-[.12em] uppercase text-ctp-overlay0 mb-3">Stack</p>
                    <div class="flex flex-col gap-2">
                        <span class="text-xs text-ctp-subtext0">Helidon SE</span>
                        <span class="text-xs text-ctp-subtext0">JTE Templates</span>
                        <span class="text-xs text-ctp-subtext0">Tailwind CSS</span>
                    </div>
                </div>
            </div>

        </div>

        <%-- BAS DU FOOTER --%>
        <div class="flex flex-wrap items-center justify-between gap-3 pt-5 border-t border-ctp-surface0">
            <span class="text-[0.7rem] text-ctp-overlay0">© 2026 Fortress — Gestion financière</span>
            <div class="flex items-center gap-2">
                <div class="w-1.5 h-1.5 rounded-full bg-ctp-green"></div>
                <span class="text-[0.7rem] text-ctp-overlay0">Système opérationnel</span>
            </div>
        </div>

    </div>
</footer>

</body>
</html>