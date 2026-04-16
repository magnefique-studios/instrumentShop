# Project Overview

[← Back to README](README.md)

## Executive Summary

**InstrumenT-ation Shop** (JavaShop) is a multi-service Java e-commerce demo platform designed for APM (Application Performance Monitoring) and observability training. The application simulates a musical instrument marketplace with **deliberate performance issues and error conditions** that trainees must identify using observability tools.

## Key Facts

| Attribute | Value |
|-----------|-------|
| **Language** | Java |
| **Build System** | Maven (multi-module) |
| **Total LOC** | ~4,805 |
| **Java Source Files** | 58 |
| **Modules** | 7 (shop, products, conductors, instruments, stock, annotator, test) |
| **Deployment** | Docker Compose with 8 containers |
| **Databases** | PostgreSQL 13.1 (instruments), H2 in-memory (stock) |

## Technology Stack Summary

| Component | Versions Used |
|-----------|--------------|
| Spring Boot | 1.5.19, 2.1.3, 2.7.5, 3.2.2 (mixed!) |
| Java | 1.8, 11, 17 (mixed!) |
| Spring Cloud | Dalston.SR5 (EOL) |
| Circuit Breaker | Netflix Hystrix (deprecated) |
| ORM | Spring Data JPA + Hibernate |
| Template Engine | Thymeleaf |
| Containerization | Docker Compose v3 |

## Module Overview

| Module | Role | Spring Boot | Java |
|--------|------|------------|------|
| **shop** | Frontend + Orchestrator | 1.5.19 (EOL) | 1.8 |
| **products** | Product catalog | 3.2.2 ✅ | 17 ✅ |
| **conductors** | Alt product service | 3.2.2 ✅ | 17 ✅ |
| **instruments** | Instrument CRUD | 2.7.5 (EOL) | 17 ✅ |
| **stock** | Stock management | 2.1.3 (EOL) | 1.8 |
| **annotator** | OTel annotation tool | N/A | 17 ✅ |
| **test** | Traffic generator | N/A | 11 |

## Key Findings

### Critical Issues
1. **3 of 5 Spring Boot services** use end-of-life versions
2. **Log4j 2.6.1** in test module — CVE-2021-44228 (Log4Shell)
3. **SQL injection vulnerability** in instruments module
4. **Netflix Hystrix deprecated** — no longer maintained

### Architectural Observations
- Mixed framework versions create maintenance complexity
- No compile-time dependencies between modules (all HTTP/REST at runtime)
- Deliberate performance issues (Colorado latency injection, Chicago cartesian query)
- Exercise scoring system for observability training
- Shop module is the central orchestrator with highest coupling

### AWS Transformation Recommendation
**`AWS/java-version-upgrade`** — Recommended to systematically upgrade Java 1.8 modules to modern JDK versions with comprehensive dependency modernization.

See [Technical Debt Report](technical-debt-report.md) for full details.

---

## Related Documents

- [System Overview](architecture/system-overview.md) — Detailed architecture
- [Technical Debt Report](technical-debt-report.md) — Prioritized findings with AWS recommendation
- [Component Order](migration/component-order.md) — Migration sequence
