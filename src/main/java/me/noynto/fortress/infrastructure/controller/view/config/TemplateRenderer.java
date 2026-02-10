package me.noynto.fortress.infrastructure.controller.view.config;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.TemplateOutput;
import gg.jte.output.StringOutput;
import gg.jte.resolve.DirectoryCodeResolver;

import java.nio.file.Path;
import java.util.Map;

public class TemplateRenderer {

    private final TemplateEngine engine;

    public TemplateRenderer() {
        Path templatePath = Path.of("src/main/jte");
        DirectoryCodeResolver codeResolver = new DirectoryCodeResolver(templatePath);
        this.engine = TemplateEngine.create(codeResolver, ContentType.Html);
        this.engine.setBinaryStaticContent(true);
    }

    /**
     * Rend un template JTE avec des paramètres
     */
    public String render(String templatePath, Map<String, Object> params) {
        TemplateOutput output = new StringOutput();
        engine.render(templatePath, params, output);
        return output.toString();
    }

    /**
     * Rend un template JTE avec un paramètre
     */
    public String render(String templatePath, Object param) {
        TemplateOutput output = new StringOutput();
        engine.render(templatePath, param, output);
        return output.toString();
    }

    /**
     * Rend un template JTE sans paramètres
     */
    public String render(String templatePath) {
        TemplateOutput output = new StringOutput();
        engine.render(templatePath, null, output);
        return output.toString();
    }
}
