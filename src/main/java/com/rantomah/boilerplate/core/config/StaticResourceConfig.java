package com.rantomah.boilerplate.core.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Value("${app.upload.base-dir:uploads}")
    private String baseUploadDir;

    @Value("${app.upload.base-url:/files}")
    private String baseFilesUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String resourceHandler = ensureEndsWithSlash(baseFilesUrl) + "**";
        Path uploadPath = Paths.get(baseUploadDir).toAbsolutePath().normalize();
        String resourceLocation = uploadPath.toUri().toString(); // file:///...
        registry.addResourceHandler(resourceHandler)
                .addResourceLocations(resourceLocation)
                .setCachePeriod(3600);
    }

    private String ensureEndsWithSlash(String path) {
        if (path == null || path.isEmpty()) {
            return "/";
        }
        return path.endsWith("/") ? path : path + "/";
    }
}
