package com.example.ratelimiter.controller;

import com.example.ratelimiter.service.RateLimiterFactory;
import com.example.ratelimiter.limiter.RateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GatewayController {

    private final RateLimiter rateLimiter;

    public GatewayController(RateLimiterFactory factory) {
        this.rateLimiter = factory.getLimiter();
    }

    @GetMapping("/data")
    public ResponseEntity<String> getData(@RequestHeader("X-API-KEY") String apiKey) {
        if (!rateLimiter.tryAcquire(apiKey)) {
            return ResponseEntity.status(429).body("Rate limit exceeded");
        }
        return ResponseEntity.ok("Request processed successfully");
    }
}
