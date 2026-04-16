# System Overview

[← Back to README](../README.md) | [Components](components.md) | [Dependencies](dependencies.md) | [Patterns](patterns.md)

## Project Identity

- **Name**: InstrumenT-ation Shop (JavaShop)
- **GroupId**: `com.splunk` (root), `com.shabushabu.javashop` (modules), `com.splunk.otel` (annotator)
- **Build System**: Maven (multi-module POM)
- **Language**: Java
- **Total LOC**: ~4,805 lines across 60 Java source files

## Purpose

A multi-service e-commerce demo platform designed for **APM/observability training**. The application simulates a musical instrument marketplace with deliberate latency injection points and error conditions that trainees must identify using observability tools (originally Splunk/Datadog, with OpenTelemetry instrumentation).

## Technology Stack

| Layer | Technology | Version(s) |
|-------|-----------|------------|
| Framework | Spring Boot | 1.5.19, 2.1.3, 2.7.5, 3.2.2 |
| Cloud | Spring Cloud | Dalston.SR5 (shop only) |
| Circuit Breaker | Netflix Hystrix | via Spring Cloud Dalston (shop only) |
| Template Engine | Thymeleaf | via Spring Boot (shop only) |
| ORM | Spring Data JPA / Hibernate | Per Spring Boot version |
| Database (instruments) | PostgreSQL | 13.1-alpine |
| Database (stock) | H2 (in-memory) | via Spring Boot |
| Cache | Redis | Latest (declared but usage not observed in code) |
| Build | Maven | 4.0.0 POM |
| Containerization | Docker / Docker Compose | v3 format |
| Observability | OpenTelemetry annotations | 1.19.x-alpha, 2.2.0 |
| Logging | Log4j 2, SLF4J/Logback | 2.24.3 (shop), 2.6.1 (test), managed (others) |
| Code Analysis | JavaParser | 3.23.1 (annotator) |

## Module Overview

| Module | Spring Boot | Java | Port | Description |
|--------|------------|------|------|-------------|
| shop | 1.5.19 | 1.8 | 8010 | Main frontend + orchestrator (Thymeleaf UI, Hystrix) |
| products | 3.2.2 | 17 | 8020 | Product catalog with in-memory data + latency simulation |
| conductors | 3.2.2 | 17 | 8050 | Alternative product service (Utah routing, Oregon exception) |
| stock | 2.1.3 | 1.8 | 8030 | Stock management with H2 + synthetic data |
| instruments | 2.7.5 | 17 | 8040 | Instrument CRUD with PostgreSQL |
| annotator | N/A | 17 | N/A | OTel annotation tool (offline utility) |
| test | N/A | 11 | N/A | Traffic generator for demo scenarios |

## Deployment Model

- **Docker Compose** orchestration with `instrument_shop` external network
- **PostgreSQL** container for instruments data (2 SQL init scripts)
- **Redis** container declared (usage not observed in application code)
- **Volume mounts** for properties files (shop/data, test/data)
- **Health checks** on all services via `/healthcheck` endpoints
- **Service links**: shop → products, stock, instruments; instruments → postgresDB; shoptester → shop

---

## Related Documents

- [Components](components.md) — Detailed component descriptions
- [Dependencies](dependencies.md) — Internal and external dependency mapping
- [Patterns](patterns.md) — Design patterns identified
- [Project Overview](../project-overview.md) — Executive summary
