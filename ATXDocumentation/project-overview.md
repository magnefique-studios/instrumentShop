# Project Overview: InstrumenT-ation Shop (JavaShop)

## Summary

**JavaShop** is a microservices-based e-commerce demonstration application for browsing and managing musical instruments and related products. It is organized as a multi-module Maven project under the group `com.splunk` with the artifact `javashop`. The project was originally designed as a workshop/training application for observability tooling (Splunk/Datadog/OpenTelemetry), featuring intentional latency injections and exercise-based scoring.

## Technology Stack

| Layer | Technology | Notes |
|-------|-----------|-------|
| Language | Java 8, 11, 17 | Mixed versions across modules |
| Framework | Spring Boot 1.5.19, 2.1.3, 2.7.5, 3.2.2 | Multiple versions across modules |
| Cloud | Spring Cloud Dalston.SR5 | Shop module only (Hystrix, Eureka) |
| Database | PostgreSQL 13.1, H2 (in-memory) | Instruments uses PostgreSQL; Stock uses H2 |
| Templating | Thymeleaf | Shop module frontend |
| Observability | OpenTelemetry Annotations | Versions: 1.19.1-alpha, 1.19.2-alpha, 2.2.0 |
| Logging | Log4j2 2.6.1, SLF4J | Mixed across modules |
| Build | Maven | With spring-boot-maven-plugin |
| Deployment | Docker, Docker Compose | Multi-container orchestration |
| AOP | AspectJ 1.9.19 | Shop module |

## Module Overview

| Module | Port | Spring Boot | Java | Description |
|--------|------|-------------|------|-------------|
| **shop** | 8010 | 1.5.19 | 8 | Frontend gateway with Thymeleaf UI, Hystrix circuit breakers |
| **products** | 8020 | 3.2.2 | 17 | Product catalog service with in-memory data |
| **stock** | 8030 | 2.1.3 | 8 | Stock management with H2 database |
| **instruments** | 8040 | 2.7.5 | 17 | Musical instrument service with PostgreSQL |
| **conductors** | 8050 | 3.2.2 | 17 | Parallel product service for Utah/Oregon locations |
| **annotator** | N/A | N/A | 17 | OpenTelemetry annotation utility tool |
| **test** | N/A | N/A | 11 | Traffic generation tool for testing |

## Repository Structure

```
javashop/
├── pom.xml                     # Root POM (multi-module)
├── docker-compose.yml          # Service orchestration
├── shop/                       # Frontend gateway service
├── products/                   # Product catalog service
├── stock/                      # Stock management service
├── instruments/                # Instrument service (PostgreSQL)
├── conductors/                 # Conductors (parallel products) service
├── annotator/                  # OpenTelemetry annotation utility
├── test/                       # Traffic generation tool
├── db/sql/                     # Database initialization scripts
├── datadog/                    # Datadog configuration (legacy)
└── ATXDocumentation/           # This documentation
```

## Key Characteristics

1. **Workshop/Training Application**: Designed for APM/observability workshops with intentional performance issues and exercises
2. **Microservices Architecture**: 5 independently deployable services communicating via REST
3. **Mixed Technology Versions**: Intentionally spans multiple Java and Spring Boot versions to demonstrate upgrade scenarios
4. **Docker-Based Deployment**: All services orchestrated via Docker Compose with health checks
5. **Observability Focus**: Heavy instrumentation with OpenTelemetry annotations, Log4j2 logging, and Hystrix metrics

## Cross-References

- [Architecture Overview](architecture/system-overview.md)
- [Component Details](architecture/components.md)
- [Technical Debt Report](technical-debt-report.md)
- [Dependency Analysis](architecture/dependencies.md)
