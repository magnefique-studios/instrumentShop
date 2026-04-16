# System Context

[← Back to README](../../README.md) | [Component Diagrams](../structural/component-diagrams.md) | [Sequence Diagrams](../behavioral/sequence-diagrams.md)

## System Context Diagram

```
┌──────────────────────────────────────────────────────────────────────────┐
│                          External Boundary                                │
│                                                                           │
│   ┌────────────┐                                                         │
│   │   User /   │                                                         │
│   │  Browser   │──── HTTP (port 8010) ────┐                              │
│   └────────────┘                          │                              │
│                                           ▼                              │
│   ┌─────────────────────────────────────────────────────────────┐       │
│   │              Docker Compose: instrument_shop                 │       │
│   │                                                              │       │
│   │   ┌─────────────────────┐                                    │       │
│   │   │   shop (8010)       │                                    │       │
│   │   │   Spring Boot 1.5   │                                    │       │
│   │   │   + Thymeleaf UI    │                                    │       │
│   │   │   + Hystrix CB      │                                    │       │
│   │   └──┬──┬──┬──┬─────────┘                                    │       │
│   │      │  │  │  │                                              │       │
│   │      │  │  │  └──── HTTP ────> instruments (8040)            │       │
│   │      │  │  │                    Spring Boot 2.7              │       │
│   │      │  │  │                    + JPA/Hibernate              │       │
│   │      │  │  │                         │                       │       │
│   │      │  │  │                    JDBC (5432)                  │       │
│   │      │  │  │                         │                       │       │
│   │      │  │  │                    postgresDB                   │       │
│   │      │  │  │                    PG 13.1-alpine               │       │
│   │      │  │  │                                                 │       │
│   │      │  │  └────── HTTP ────> stock (8030)                   │       │
│   │      │  │                     Spring Boot 2.1                │       │
│   │      │  │                     + H2 (embedded)                │       │
│   │      │  │                                                    │       │
│   │      │  └──────── HTTP ────> products (8020)                 │       │
│   │      │                       Spring Boot 3.2                 │       │
│   │      │                                                       │       │
│   │      └──── HTTP (Utah) ──> conductors (8050)                 │       │
│   │                            Spring Boot 3.2                   │       │
│   │                                                              │       │
│   │   ┌────────────┐                                             │       │
│   │   │   redis    │  (no app code usage found)                  │       │
│   │   └────────────┘                                             │       │
│   │                                                              │       │
│   │   ┌────────────┐                                             │       │
│   │   │ shoptester │──── HTTP ────> shop                         │       │
│   │   │ (traffic)  │                                             │       │
│   │   └────────────┘                                             │       │
│   └──────────────────────────────────────────────────────────────┘       │
│                                                                           │
│   ┌────────────┐  (offline utility — not in Docker)                      │
│   │  annotator │  Modifies products/ source code with OTel annotations   │
│   └────────────┘                                                         │
│                                                                           │
└──────────────────────────────────────────────────────────────────────────┘
```

## Communication Patterns

| Pattern | Implementation | Modules |
|---------|---------------|---------|
| Synchronous REST | `RestTemplate` (shop) → downstream services | shop → products, conductors, stock, instruments |
| Circuit Breaker | `@HystrixCommand` with fallback methods | shop (InstrumentRepo, StockRepo) |
| JDBC/JPA | Spring Data JPA + PostgreSQL driver | instruments → postgresDB |
| Embedded DB | H2 in-memory via Spring Data JPA | stock |
| Health Check | HTTP `/healthcheck` endpoint per service | all services |

## Service Ports

| Service | Internal Port | External Port | Protocol |
|---------|--------------|---------------|----------|
| shop | 8010 | 8010 | HTTP |
| products | 8020 | 8020 | HTTP |
| stock | 8030 | 8030 | HTTP |
| instruments | 8040 | 8040 | HTTP |
| conductors | 8050 | 8050 | HTTP |
| postgresDB | 5432 | 5432 | TCP/JDBC |

## Security Boundaries

- **No TLS**: All inter-service communication is unencrypted HTTP
- **No authentication**: No API keys or tokens between services
- **Database credentials**: Passed via environment variables in docker-compose.yml (`instruments/instruments`)
- **Network isolation**: All services on shared `instrument_shop` Docker network (external)

---

## Related Documents

- [Component Diagrams](../structural/component-diagrams.md) — Structural views
- [Deployment Configuration](../../specialized/deployment-configuration.md) — Docker setup
- [Architecture Dependencies](../../architecture/dependencies.md) — Full dependency map
