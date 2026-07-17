package com.example.ratelimiter.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ApiKeyFilter extends HttpFilter {

    private final List<String> allowedApiKeys;

    public ApiKeyFilter(List<String> allowedApiKeys) {
        this.allowedApiKeys = allowedApiKeys;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String apiKey = request.getHeader("X-API-KEY");
        if (apiKey == null || !allowedApiKeys.contains(apiKey)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API key");
            return;
        }
        chain.doFilter(request, response);
    }
}
