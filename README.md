# Payment Processing Engine

A demo-grade, production-inspired **Payment Processing Engine** built with **Java 21 + Spring Boot 3**, showcasing:
- Idempotent payment APIs
- Event-driven architecture with Kafka
- Fraud rules engine
- Retry & DLQ handling
- JWT-secured APIs
- Admin dashboard for payment monitoring

## Architecture

- **Tech Stack**: Java 21, Spring Boot 3, Kafka, Redis, Postgres, Docker, Spring Security (JWT)
- **Patterns**: Event-driven, Idempotent APIs, Layered Architecture, Retry with DLQ, CQRS-lite

![Architecture Diagram](docs/architecture.png) <!-- placeholder; add once drawn -->

### Components (Modular Monolith)
- **API Layer**: REST controllers under `/api/payments`, `/api/auth`, `/api/admin`.
- **Application Layer**: `PaymentService`, `PaymentOrchestrator`, `FraudService`, `IdempotencyService`, `EventPublisher`.
- **Infrastructure**: `KafkaConfig`, `RedisConfig`, `SecurityConfig`, Postgres via Spring Data JPA.
- **Messaging**: Topics `payments.created`, `payments.authorized`, `payments.captured`, `payments.failed`, optional DLQ and fraud topics.

### Domain Model (JPA)
- `Payment`: id (UUID), externalId (for idempotency), amount, currency, status (INITIATED, AUTHORIZED, CAPTURED, FAILED, CANCELLED), methodType, customerId, timestamps, failureReason.
- `PaymentMethod`: FK to payment, type (CARD/BANK/WALLET), masked card/bank fields.
- `PaymentEvent`: eventType (CREATED, AUTHORIZED, CAPTURED, FAILED, FRAUD_FLAGGED), payloadJson, createdAt.
- `FraudCheck`: score, decision (APPROVE, REVIEW, DECLINE), reason, createdAt.
- `IdempotencyKey`: key, requestHash, responseBody, status, createdAt, expiresAt.

## Getting Started

```bash
# 1. Start infra via Docker (Postgres, Kafka, Redis)
docker-compose up -d

# 2. Run the Spring Boot application
./mvnw spring-boot:run

# 3. Test API
curl -X POST http://localhost:8080/api/payments \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <jwt>" \
  -H "Idempotency-Key: 123e4567-e89b-12d3-a456-426614174000" \
  -d '{
        "amount": 120.50,
        "currency": "USD",
        "paymentMethod": {
          "type": "CARD",
          "number": "4111111111111111",
          "expiryMonth": 12,
          "expiryYear": 2028,
          "holderName": "Test User"
        }
      }'
```

## API (MVP)

### POST `/api/payments`
- **Headers**: `Authorization: Bearer <jwt>`, `Idempotency-Key: <uuid>`
- **Request body**
```json
{
  "externalId": "merchant-12345",
  "amount": 120.50,
  "currency": "USD",
  "customerId": "cust-999",
  "paymentMethod": {
    "type": "CARD",
    "number": "4111111111111111",
    "expiryMonth": 12,
    "expiryYear": 2028,
    "holderName": "Test User"
  }
}
```
- **Responses**
  - `201 Created` with the persisted payment snapshot when processed the first time
  - `200 OK` with the cached response when the same `Idempotency-Key` is replayed and the payload hash matches
```json
{
  "id": "7b1f2d2d-fde4-4280-9b61-8f1c1afc35c5",
  "externalId": "merchant-12345",
  "amount": 120.5,
  "currency": "USD",
  "status": "CAPTURED",
  "methodType": "CARD",
  "customerId": "cust-999",
  "createdAt": "2024-01-01T12:00:00Z",
  "updatedAt": "2024-01-01T12:00:01Z",
  "failureReason": null
}
```

### GET `/api/payments/{id}`
- **Headers**: `Authorization: Bearer <jwt>`
- **Responses**
  - `200 OK` with `PaymentResponse`
  - `404 Not Found` if the payment does not exist

## Project Structure
```
com.paymentengine
  ├── PaymentProcessingEngineApplication.java
  ├── config
  │     ├── SecurityConfig.java
  │     ├── KafkaConfig.java
  │     ├── RedisConfig.java
  ├── controller
  │     ├── PaymentController.java
  │     └── AdminController.java
  ├── dto
  │     ├── PaymentRequest.java
  │     ├── PaymentResponse.java
  │     └── FraudDecisionResponse.java
  ├── domain
  │     ├── Payment.java
  │     ├── PaymentMethod.java
  │     ├── PaymentEvent.java
  │     ├── FraudCheck.java
  │     └── enums (PaymentStatus, PaymentMethodType, FraudDecisionType)
  ├── repository
  │     ├── PaymentRepository.java
  │     ├── PaymentEventRepository.java
  │     └── FraudCheckRepository.java
  ├── service
  │     ├── PaymentService.java
  │     ├── PaymentOrchestrator.java
  │     ├── FraudService.java
  │     ├── IdempotencyService.java
  │     └── EventPublisher.java
  ├── messaging
  │     ├── PaymentEventProducer.java
  │     └── PaymentEventConsumer.java
  ├── security
  │     ├── JwtTokenProvider.java
  │     └── JwtAuthenticationFilter.java
  └── util
        └── ...
```

## Idempotency & Security
- Clients must send `Idempotency-Key` on write APIs; responses are cached in Redis with TTL (24h) and optionally persisted in Postgres.
- JWT required for `/api/payments/**`; roles `ROLE_USER` for merchants and `ROLE_ADMIN` for dashboard/admin endpoints.
- Sensitive PAN data is not logged; only masked values are stored.

## Kafka Topics
- `payments.created`
- `payments.authorized`
- `payments.captured`
- `payments.failed`
- (optional) `payments.fraud-flagged`
- (optional) `payments.dlq`

## Retry & DLQ
- Internal retries for transient PSP failures via `@Retryable` with exponential backoff.
- Kafka consumer retries with recovery to `payments.dlq` after max attempts.

## Future Enhancements
- Split into separate microservices
- Add proper 3DS / SCA flows
- Add observability (metrics, tracing)
- UI dashboard (React)

## Case Study Outline (for docs)
1. **Context & Problem** – reliability, idempotency, fraud, regulatory needs
2. **Design Decisions** – why Java/Spring Boot/Kafka/Redis/Postgres; event-driven + idempotent APIs
3. **Implementation Details** – API contracts, idempotency, retry strategy, fraud rules
4. **Trade-offs & Future Work** – simplifications, scaling, observability

## Next Step
Wire the Kafka producers/consumers and add JWT filter + security configuration to protect the APIs.
