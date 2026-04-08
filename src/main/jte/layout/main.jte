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
    <meta name="theme-color" content="#2D353B">
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

    <!-- Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display:ital@0;1&family=DM+Mono:wght@400;500&family=Sora:wght@300;400;500;600&display=swap" rel="stylesheet">

    <!-- Tailwind -->
    <script src="https://cdn.tailwindcss.com"></script>
    <script>
        tailwind.config = {
            theme: {
                screens: {
                    sm:  '640px',
                    md:  '768px',
                    lg:  '1024px',
                    xl:  '1280px',
                    '2xl': '1536px',
                },
                extend: {
                    colors: {
                        ef: {
                            bg0:    '#2D353B',
                            bg1:    '#343F44',
                            bg2:    '#3D484D',
                            bg3:    '#475258',
                            bg4:    '#4F585E',
                            fg:     '#D3C6AA',
                            dim:    '#9DA9A0',
                            green:  '#A7C080',
                            green2: '#83C092',
                            yellow: '#DBBC7F',
                            orange: '#E69875',
                            red:    '#E67E80',
                            blue:   '#7FBBB3',
                            purple: '#D699B6',
                        },
                    },
                    fontFamily: {
                        serif: ['"DM Serif Display"', 'serif'],
                        mono:  ['"DM Mono"', 'monospace'],
                        sans:  ['Sora', 'sans-serif'],
                    },
                    fontSize: {
                        display: ['clamp(26px,7vw,62px)',   { lineHeight: '1.08', letterSpacing: '-0.03em' }],
                        heading: ['clamp(20px,5.5vw,40px)', { lineHeight: '1.15', letterSpacing: '-0.02em' }],
                        price:   ['clamp(24px,6vw,42px)',   { lineHeight: '1',    letterSpacing: '-0.03em' }],
                    },
                },
            },
        }
    </script>

    <style>
        /* Couleurs semi-transparentes Everforest */
        .bef-base   { border-color: rgba(167,192,128,0.15); }
        .bef-strong { border-color: rgba(167,192,128,0.30); }
        .bef-accent { border-color: rgba(167,192,128,0.40); }
        .bg-g10     { background-color: rgba(167,192,128,0.10); }
        .bg-g05     { background-color: rgba(167,192,128,0.05); }
        .bg-g12     { background-color: rgba(167,192,128,0.12); }
        .bg-y12     { background-color: rgba(219,188,127,0.12); }
        .bg-b12     { background-color: rgba(127,187,179,0.12); }
        .bg-r12     { background-color: rgba(230,126,128,0.12); }
        .bg-p12     { background-color: rgba(214,153,182,0.12); }

        /* Autofill webkit */
        input:-webkit-autofill,
        input:-webkit-autofill:focus {
            -webkit-box-shadow: 0 0 0 1000px #3D484D inset !important;
            -webkit-text-fill-color: #D3C6AA !important;
            caret-color: #D3C6AA;
        }
    </style>
</head>

<body class="bg-ef-bg0 text-ef-fg min-h-screen font-sans antialiased">

<main class="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-6 sm:py-8">
    ${content}
</main>

<footer class="bg-ef-bg1 border-t bef-base mt-12 py-10">
    <div class="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8">

        <%-- HAUT DU FOOTER --%>
        <div class="flex flex-wrap justify-between gap-8 mb-8">

            <%-- BRANDING --%>
            <div>
                <p class="font-serif text-base text-ef-green mb-2">🌲 Fortress</p>
                <p class="text-[11px] font-mono text-ef-dim leading-relaxed max-w-[220px]">
                    Suivez, anticipez et planifiez vos finances en temps réel.
                </p>
                <div class="mt-3 inline-flex items-center gap-1.5 text-[10px] font-mono text-ef-dim">
                    <span>🔐</span> Données chiffrées AES-256
                </div>
            </div>

            <%-- LIENS --%>
            <div class="flex flex-wrap gap-10">
                <div>
                    <p class="text-[10px] font-mono font-medium tracking-[.12em] uppercase text-ef-dim mb-3">Navigation</p>
                    <div class="flex flex-col gap-2">
                        <a href="/dashboard"        class="text-[11px] font-mono text-ef-dim hover:text-ef-green transition-colors">Dashboard</a>
                        <a href="/transactions"     class="text-[11px] font-mono text-ef-dim hover:text-ef-green transition-colors">Transactions</a>
                        <a href="/transactions/new" class="text-[11px] font-mono text-ef-dim hover:text-ef-green transition-colors">Nouvelle transaction</a>
                    </div>
                </div>
                <div>
                    <p class="text-[10px] font-mono font-medium tracking-[.12em] uppercase text-ef-dim mb-3">Stack</p>
                    <div class="flex flex-col gap-2">
                        <span class="text-[11px] font-mono text-ef-dim">Helidon SE</span>
                        <span class="text-[11px] font-mono text-ef-dim">JTE Templates</span>
                        <span class="text-[11px] font-mono text-ef-dim">Tailwind CSS</span>
                    </div>
                </div>
            </div>

        </div>

        <%-- BAS DU FOOTER --%>
        <div class="flex flex-wrap items-center justify-between gap-3 pt-5 border-t bef-base">
            <span class="text-[10px] font-mono text-ef-dim">© 2026 Fortress — Gestion financière</span>
            <div class="flex items-center gap-2">
                <div class="w-1.5 h-1.5 rounded-full bg-ef-green"></div>
                <span class="text-[10px] font-mono text-ef-dim">Système opérationnel</span>
            </div>
        </div>

    </div>
</footer>

</body>
</html>
