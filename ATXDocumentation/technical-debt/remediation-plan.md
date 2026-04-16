# Remediation Plan

[← Back to README](../README.md) | [← Technical Debt Report](../technical-debt-report.md) | [Summary](summary.md) | [Outdated Components](outdated-components.md) | [Maintenance Burden](maintenance-burden.md)

## Overview

Prioritized remediation plan organized by severity. All items use qualitative effort indicators only.

---

## Phase 1: Critical Security Fixes (High Priority)

### 1.1 Upgrade Log4j in Test Module
- **Severity**: Medium (test/dev dependency, but critical CVE)
- **Current**: Log4j 2.6.1 (CVE-2021-44228, CVE-2021-45046, CVE-2017-5645)
- **Target**: Log4j 2.24.x
- **Scope**: `test/pom.xml` — update `log4j-api` and `log4j-core` version
- **Effort**: Low — version bump only
- **Risk**: Low — test utility module

### 1.2 Fix SQL Injection in FindInstrumentRepositoryImpl
- **Severity**: Low (code quality, but security impact is high if exposed)
- **Current**: String concatenation in JPQL: `"FROM instruments i WHERE i.ID = " + id.toString()`
- **Target**: Use parameterized query: `"FROM Instrument i WHERE i.id = :id"` with `.setParameter("id", id)`
- **Scope**: `instruments/src/main/java/.../repositories/FindInstrumentRepositoryImpl.java`
- **Effort**: Low — single method change

---

## Phase 2: Framework Upgrades (High Priority)

### 2.1 Upgrade Shop Module (Spring Boot 1.5.19 → 3.2.x)
- **Severity**: High
- **Scope**: Complete rewrite of shop module dependencies
- **Key Changes**:
  - Java 1.8 → Java 17+
  - `javax.*` → `jakarta.*` namespace
  - Spring Cloud Dalston → Spring Cloud 2023.0.x
  - Netflix Hystrix → Resilience4j or Spring Cloud Circuit Breaker
  - Thymeleaf template compatibility
  - RestTemplate patterns may need updating
- **Effort**: High — most complex module with Hystrix and Spring Cloud dependencies
- **Risk**: High — central orchestrator module

### 2.2 Upgrade Stock Module (Spring Boot 2.1.3 → 3.2.x)
- **Severity**: High
- **Scope**: Stock module upgrade
- **Key Changes**:
  - Java 1.8 → Java 17+
  - `javax.persistence` → `jakarta.persistence`
  - `javax.validation` → `jakarta.validation`
  - `javax.annotation` → `jakarta.annotation`
  - H2 database driver compatibility
  - Cucumber `info.cukes` → `io.cucumber` 7.x+
- **Effort**: Medium — simpler module with fewer dependencies
- **Risk**: Medium — standalone data service

### 2.3 Upgrade Instruments Module (Spring Boot 2.7.5 → 3.2.x)
- **Severity**: High
- **Scope**: Instruments module upgrade
- **Key Changes**:
  - `javax.persistence` → `jakarta.persistence`
  - `javax.annotation` → `jakarta.annotation`
  - PostgreSQL driver compatibility
  - Spring Data JPA API changes
  - OTel annotations alpha → stable 2.x
- **Effort**: Medium — JPA entities and repositories require namespace migration
- **Risk**: Medium — database-dependent service

---

## Phase 3: Dependency Updates (Medium Priority)

### 3.1 Replace Deprecated Dependencies
| Current | Target | Module | Effort |
|---------|--------|--------|--------|
| JUnit 3.8.1 | JUnit 5.10.x | root pom | Low |
| commons-httpclient 3.1 | java.net.http (built-in) | test | Medium — test module already uses java.net.http |
| Cucumber 1.2.5 (info.cukes) | io.cucumber 7.x+ | stock | Medium |
| nekohtml 1.9.22 | Updated version or removal | shop | Low |
| OTel annotations alpha | 2.x stable GA | shop, instruments, annotator | Low |

### 3.2 Standardize OTel Annotations
- **Current**: Three different alpha versions (1.19.1-alpha, 1.19.2-alpha, 2.2.0)
- **Target**: Single stable GA version (2.x) across all modules
- **Effort**: Low — version alignment

---

## Phase 4: Code Quality Improvements (Low Priority)

### 4.1 Address Empty Catch Blocks
- Replace empty catch blocks with proper exception handling or at minimum logging
- Focus on ProductFilterService in both products and conductors modules
- **Effort**: Medium — many occurrences but straightforward changes

### 4.2 Fix Endpoint Typos
- `/instrumemnts` → `/instruments` (StockRepo in shop)
- `/insruments` → `/instruments` (StockResource in stock)
- **Note**: Requires coordinated change across calling and serving modules
- **Effort**: Low but requires coordination

### 4.3 Remove Commented-Out Code
- Clean up extensive commented-out code throughout the codebase
- **Effort**: Low — purely cosmetic improvement

### 4.4 Enable Spring DI in Product/Conductor Controllers
- Replace `new ProductService()` with `@Autowired` bean injection
- Replace `new ProductFilterService()` with `@Autowired` bean injection
- Add `@Service` or `@Component` annotations to service classes
- **Effort**: Low — standard Spring pattern

### 4.5 Fix Cartesian Product Query
- `FindInstrumentRepositoryImpl.findInstruments()` uses `SELECT * FROM instruments_for_sale, instruments_for_sale_chicago` without a JOIN condition
- Replace with proper JOIN or separate queries
- **Effort**: Low — single query change

---

## Recommended Migration Order

Based on dependency analysis (see [Component Order](../migration/component-order.md)):

1. **products** and **conductors** — leaf services, already on Spring Boot 3.2.2 (no upgrade needed)
2. **stock** — standalone data service, upgrade to Spring Boot 3.2.x
3. **instruments** — database service, upgrade to Spring Boot 3.2.x
4. **shop** — central orchestrator, upgrade last (depends on all other services)
5. **test** — traffic generator, upgrade independently
6. **annotator** — standalone tool, already on Java 17

---

## Related Documents

- [Summary](summary.md) — Debt overview
- [Outdated Components](outdated-components.md) — Version details
- [Migration Component Order](../migration/component-order.md) — Dependency-based migration sequence
