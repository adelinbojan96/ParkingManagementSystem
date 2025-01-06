package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("Registering CORS configuration..."); // Debug message
        registry.addMapping("/**") // Apply CORS to all endpoints
                .allowedOrigins(
                        "http://localhost:3000", // Frontend dev server
                        "http://localhost:8081", // Alternate backend endpoint (if needed)
                        "http://localhost:8080"  // Primary backend endpoint
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // HTTP methods allowed
                .allowedHeaders("*")
                .exposedHeaders("Access-Control-Allow-Origin", "Authorization")
                .allowCredentials(true);
    }
}
