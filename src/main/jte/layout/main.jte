@param gg.jte.Content content
@param String title = "Fortress"

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
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

<footer class="mt-12 py-6 border-t border-ctp-surface0">
    <div class="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 text-center text-ctp-overlay0 text-xs">
        Architecture CQRS avec Helidon SE & JTE
    </div>
</footer>

</body>
</html>