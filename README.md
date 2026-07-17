# Distributed Rate Limiter and Security Throttle

This Spring Boot project implements an API gateway component that protects downstream services by throttling incoming requests.

It supports interchangeable rate limiting algorithms:
- Token Bucket
- Leaky Bucket
- Sliding Window Log

The gateway also enforces a simple API key security throttle to validate incoming consumers.

## Build and run

1. Build the application:
   ```
   mvn clean package
   ```
2. Run the application:
   ```
   mvn spring-boot:run
   ```

## Configuration

The algorithm selection is controlled in `src/main/resources/application.yml` under `ratelimiter.algorithm`.

Example values:
- `tokenBucket`
- `leakyBucket`
- `slidingWindow`

## API endpoints

- `GET /api/data` - gateway-protected endpoint

## Security

Requests must include a valid `X-API-KEY` header. Invalid or missing keys return `401 Unauthorized`.

## Example request

```bash
curl -H "X-API-KEY: internship-key" http://localhost:8080/api/data
```
