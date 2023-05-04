package com.kusitms.wannafly;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private static final String ALLOWED_METHODS = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

    @Value("${cors.allowed.origins}")
    private String corsAllowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods(ALLOWED_METHODS.split(","))
                .allowedOrigins(corsAllowedOrigins.split(","))
                .allowCredentials(true)
                .exposedHeaders(HttpHeaders.LOCATION, HttpHeaders.SET_COOKIE);
    }
}
