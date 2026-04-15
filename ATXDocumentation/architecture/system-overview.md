# System Overview

## Architecture Style

The JavaShop application follows a **microservices architecture** pattern with a frontend gateway service (shop) that communicates with backend services via synchronous REST calls using `RestTemplate`. The system uses **Netflix Hystrix** circuit breakers for resilience in the shop module.

## High-Level Architecture

```
┌──────────────┐
│   Browser    │
└──────┬───────┘
       │ HTTP :8010
┌──────▼───────────────────────────────────────┐
│                SHOP (Gateway)                 │
│  Spring Boot 1.5.19 / Java 8                 │
│  Thymeleaf UI + Hystrix Circuit Breakers      │
│  Port: 8010                                   │
└──┬──────┬───────────┬────────────────────────┘
   │      │           │
   │ REST │ REST      │ REST
   │      │           │
┌──▼──┐ ┌─▼────────┐ ┌▼───────────┐  ┌────────────┐
│PROD-│ │CONDUCTORS│ │INSTRUMENTS │  │   STOCK    │
│UCTS │ │          │ │            │  │            │
│8020 │ │  8050    │ │   8040     │  │   8030     │
│SB   │ │  SB 3.2  │ │  SB 2.7   │  │  SB 2.1   │
│3.2.2│ │  Java 17 │ │  Java 17  │  │  Java 8   │
└─────┘ └──────────┘ └──┬────────┘  └────────────┘
                         │ JDBC
                    ┌────▼─────┐
                    │PostgreSQL│
                    │  13.1    │
                    └──────────┘
```

## Service Communication

All inter-service communication is **synchronous HTTP/REST**:
- **shop → products**: `GET /products?location={location}` (via RestTemplate)
- **shop → conductors**: `GET /conductors?location={location}` (for Utah location only)
- **shop → stock**: `GET /legacy` (via RestTemplate with Hystrix fallback)
- **shop → instruments**: `GET /instruments?location={location}` (via RestTemplate with Hystrix fallback)
- **instruments → PostgreSQL**: JDBC via Spring Data JPA / Hibernate

## Deployment Model

Docker Compose orchestrates all services on a shared Docker network (`instrument_shop`):
- 5 application services (shop, products, conductors, stock, instruments)
- 1 test traffic generator (shoptester)
- 1 PostgreSQL database (postgresDB)
- 1 Redis instance (currently unused by application code)

## Key Architectural Decisions

1. **Gateway Pattern**: Shop acts as the single entry point, aggregating data from products, stock, and instruments services
2. **Location-Based Routing**: Utah requests are routed to the Conductors service; all others go to Products
3. **Circuit Breaker Pattern**: Hystrix provides fallbacks for service failures in InstrumentRepo and StockRepo
4. **In-Memory Data**: Products and Conductors use hardcoded in-memory data stores (no database)
5. **Mixed Persistence**: Instruments uses PostgreSQL; Stock uses H2 in-memory database

## Cross-References

- [Components](components.md) | [Dependencies](dependencies.md) | [Patterns](patterns.md)
- [Project Overview](../project-overview.md)
