package com.rantomah.boilerplate.core.config;

import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MimeTypeConfig
        implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    private static final String JS_TYPE = "text/javascript;charset=UTF-8";
    private static final String CSS_TYPE = "text/css;charset=UTF-8";
    private static final String HTML_TYPE = "text/html;charset=UTF-8";
    private static final String JSON_TYPE = "application/json;charset=UTF-8";

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);

        // ES Modules specific mappings
        mappings.add("js", JS_TYPE);
        mappings.add("mjs", JS_TYPE);
        mappings.add("chunk.js", JS_TYPE);
        mappings.add("module.js", JS_TYPE);

        // Handle hashed files from Angular build
        mappings.add("*.js", JS_TYPE);
        mappings.add("*-*.js", JS_TYPE);

        // Other static assets
        mappings.add("css", CSS_TYPE);
        mappings.add("*.css", CSS_TYPE);
        mappings.add("html", HTML_TYPE);
        mappings.add("json", JSON_TYPE);

        factory.setMimeMappings(mappings);
    }
}
