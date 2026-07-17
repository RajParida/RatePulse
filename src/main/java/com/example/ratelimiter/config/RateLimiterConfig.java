package com.example.ratelimiter.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ratelimiter")
public class RateLimiterConfig {

    private String algorithm = "tokenBucket";
    private TokenBucket tokenBucket = new TokenBucket();
    private LeakyBucket leakyBucket = new LeakyBucket();
    private SlidingWindow slidingWindow = new SlidingWindow();
    private List<String> allowedApiKeys = List.of("internship-key");

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public TokenBucket getTokenBucket() {
        return tokenBucket;
    }

    public void setTokenBucket(TokenBucket tokenBucket) {
        this.tokenBucket = tokenBucket;
    }

    public LeakyBucket getLeakyBucket() {
        return leakyBucket;
    }

    public void setLeakyBucket(LeakyBucket leakyBucket) {
        this.leakyBucket = leakyBucket;
    }

    public SlidingWindow getSlidingWindow() {
        return slidingWindow;
    }

    public void setSlidingWindow(SlidingWindow slidingWindow) {
        this.slidingWindow = slidingWindow;
    }

    public List<String> getAllowedApiKeys() {
        return allowedApiKeys;
    }

    public void setAllowedApiKeys(List<String> allowedApiKeys) {
        this.allowedApiKeys = allowedApiKeys;
    }

    public static class TokenBucket {
        private int capacity = 20;
        private int refillTokens = 5;
        private int refillPeriodSeconds = 1;

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public int getRefillTokens() {
            return refillTokens;
        }

        public void setRefillTokens(int refillTokens) {
            this.refillTokens = refillTokens;
        }

        public int getRefillPeriodSeconds() {
            return refillPeriodSeconds;
        }

        public void setRefillPeriodSeconds(int refillPeriodSeconds) {
            this.refillPeriodSeconds = refillPeriodSeconds;
        }
    }

    public static class LeakyBucket {
        private int capacity = 20;
        private int leakRatePerSecond = 5;

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public int getLeakRatePerSecond() {
            return leakRatePerSecond;
        }

        public void setLeakRatePerSecond(int leakRatePerSecond) {
            this.leakRatePerSecond = leakRatePerSecond;
        }
    }

    public static class SlidingWindow {
        private int windowSeconds = 60;
        private int maxRequests = 100;

        public int getWindowSeconds() {
            return windowSeconds;
        }

        public void setWindowSeconds(int windowSeconds) {
            this.windowSeconds = windowSeconds;
        }

        public int getMaxRequests() {
            return maxRequests;
        }

        public void setMaxRequests(int maxRequests) {
            this.maxRequests = maxRequests;
        }
    }
}
