package com.example.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 對外提供 /uploads/product-images/** 的 URL
        registry.addResourceHandler("/uploads/product-images/**")
                .addResourceLocations("file:uploads/product-images/");
    }
}