package com.bookstore.bookstore.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Override the method to configure CORS (Cross-Origin Resource Sharing)
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // This configures which HTTP requests from which domains are allowed
        registry.addMapping("/**") // Allow CORS for all endpoints in the application (/** means all URLs)
                .allowedOrigins("http://localhost:3000") // Allow requests only from the frontend running on http://localhost:3000
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow these HTTP methods
                .allowedHeaders("*") // Allow any type of headers in the request
                .allowCredentials(true); // Allow sending credentials (such as cookies or authentication data) in the request
    }
}