# Maintenance Burden

[← Back to README](../README.md) | [← Technical Debt Report](../technical-debt-report.md) | [Summary](summary.md) | [Outdated Components](outdated-components.md) | [Remediation Plan](remediation-plan.md)

## Overview

This document identifies areas of the codebase that create ongoing maintenance overhead.

---

## Mixed Framework Versions

The most significant maintenance burden is the **three different Spring Boot versions** across the project:

| Version | Modules | Java Requirement | API Style |
|---------|---------|-----------------|-----------|
| 1.5.19 | shop | Java 1.8 | `javax.*` namespace, Spring Web MVC 4 |
| 2.1.3 | stock | Java 1.8 | `javax.*` namespace, Spring Web MVC 5 |
| 2.7.5 | instruments | Java 17 | `javax.*` namespace, Spring Web MVC 5 |
| 3.2.2 | products, conductors | Java 17 | `jakarta.*` namespace, Spring Web MVC 6 |

This means:
- Different dependency resolution behavior per module
- Cannot use a single parent BOM for consistent dependency management
- `javax.persistence` (shop, stock, instruments) vs `jakarta.persistence` (products, conductors) creates incompatible entity models
- Testing and debugging requires knowledge of multiple Spring Boot generations

---

## Code Duplication

| Duplicated Code | Locations | Impact |
|----------------|-----------|--------|
| `ProductFilterService` | products module, conductors module | Nearly identical ~600-line classes with Thread.sleep chains. Conductors version has calls commented out. Any bug fix must be applied in both. |
| `ProductService` / `ConductorsService` | products, conductors | Identical in-memory DAO with same 5 products |
| `Product` model | products, conductors, shop | Three different Product classes with overlapping fields |
| `InvalidLocaleException` | shop, products, conductors, instruments | Four identical exception classes in different packages |
| `FilteredProducts` / `FilteredInstrument` | conductors, instruments | Nearly identical Oregon-locale filter logic |

---

## Anti-Patterns Requiring Ongoing Attention

### Thread.sleep Abuse
- **~60+ Thread.sleep calls** in ProductFilterService (products module) for artificial latency
- Same pattern duplicated (but commented out) in conductors module
- `myCoolFunction234234234()` contains the deliberate latency injection for Colorado
- Maintenance risk: Distinguishing intentional demo behavior from actual bugs

### Empty Catch Blocks
- **~60+ empty catch blocks** silently swallowing `InterruptedException`
- Spread across both ProductFilterService implementations
- Makes debugging extremely difficult — exceptions disappear without trace

### Manual Service Instantiation
- `ProductController` creates `new ProductService()` and `new ProductFilterService()` per request instead of using Spring DI
- Same pattern in `ConductorsController` with `new ConductorsService()` and `new ProductFilterService()`
- Prevents proper testing, monitoring, and lifecycle management

### Commented-Out Code
- Extensive commented-out code throughout:
  - `HomeController.checkIfRestricted()` — entire HTTP client call
  - `ProductService.getProductsNew()` — entire method
  - `StockService.getStock()` — entire method
  - `StockResource` — getStock endpoint
  - `InstrumentResource` — getInstrument endpoint
  - `conductors/ProductFilterService` — entire filter chain
  - Annotator file paths and imports

---

## Configuration Complexity

### Docker Compose
- Multiple `docker-compose*.yml` files: `docker-compose.yml`, `docker-compose copy.yml`, `docker-compose copy 2.yml`, `docker-compose.yml22`, `docker-compose-conductors.yml`
- Extensive commented-out Datadog agent configuration
- External network dependency (`instrument_shop`)

### Environment-Dependent Behavior
- Exercise system reads from container-mounted file `/container/shop/data/.env`
- Properties-based scoring reads from `/container/shop/data/shop.properties`
- Test module reads from `/container/test/data/tester.properties`
- All require specific Docker volume mounts to function

---

## Endpoint Naming Issues

| Endpoint | Module | Issue |
|----------|--------|-------|
| `/instrumemnts` | shop (StockRepo) | Typo: "instrumemnts" instead of "instruments" |
| `/insruments` | stock (StockResource) | Typo: "insruments" instead of "instruments" |
| Both endpoint typos may be intentional for the demo | | Cannot be fixed without coordinating clients |

---

## Related Documents

- [Summary](summary.md) — Debt overview
- [Outdated Components](outdated-components.md) — Version details
- [Remediation Plan](remediation-plan.md) — Prioritized fixes
- [Architecture Patterns](../architecture/patterns.md) — Design patterns in use
