package com.magicvault.config;

// Import necessary classes for Spring configuration
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Indicates that this class contains Spring configuration
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Override the addCorsMappings method to configure CORS
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Add CORS mapping for all paths
        registry.addMapping("/**")
                .allowedOrigins("*") // Allows all origins; in production, specify allowed domains
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allows specified HTTP methods
                .allowedHeaders("*"); // Allows all headers
    }
}
