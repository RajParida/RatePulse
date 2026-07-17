package com.example.ratelimiter.config;

import com.example.ratelimiter.security.ApiKeyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    private final RateLimiterConfig config;

    public SecurityConfig(RateLimiterConfig config) {
        this.config = config;
    }

    @Bean
    public FilterRegistrationBean<ApiKeyFilter> apiKeyFilter() {
        FilterRegistrationBean<ApiKeyFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new ApiKeyFilter(config.getAllowedApiKeys()));
        registration.addUrlPatterns("/api/*");
        registration.setOrder(1);
        return registration;
    }
}
