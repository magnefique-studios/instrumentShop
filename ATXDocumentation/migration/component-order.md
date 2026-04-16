# Component Migration Order

[← Back to README](../README.md) | [Test Specifications](test-specifications.md) | [Validation Criteria](validation-criteria.md)

## Dependency-Based Migration Sequence

Based on the service dependency graph, modules should be migrated from leaf services (no outbound dependencies) to the central orchestrator.

### Phase 1: Leaf Services (No outbound service dependencies)

**1a. products** (Spring Boot 3.2.2, Java 17) — ✅ Already on modern stack
- No migration needed for Spring Boot version
- Fix: Thread.sleep patterns, empty catch blocks, manual service instantiation
- Risk: Low

**1b. conductors** (Spring Boot 3.2.2, Java 17) — ✅ Already on modern stack
- No migration needed for Spring Boot version
- Fix: Hardcoded "Oregon" location, commented-out code, manual service instantiation
- Risk: Low

### Phase 2: Data Services

**2a. stock** (Spring Boot 2.1.3 → 3.2.x, Java 1.8 → 17)
- Upgrade path: Spring Boot 2.1.3 → 2.7.x → 3.2.x (staged recommended)
- Key changes: `javax.persistence` → `jakarta.persistence`, `javax.annotation` → `jakarta.annotation`, `javax.validation` → `jakarta.validation`
- H2 database auto-configuration updates
- Cucumber `info.cukes` → `io.cucumber`
- Risk: Medium — standalone service, H2 is embedded

**2b. instruments** (Spring Boot 2.7.5 → 3.2.x, Java 17 already)
- Upgrade path: Spring Boot 2.7.5 → 3.2.x (single jump feasible)
- Key changes: `javax.persistence` → `jakarta.persistence`, `javax.annotation` → `jakarta.annotation`
- PostgreSQL driver compatibility
- OTel annotations alpha → stable GA
- Fix SQL injection in FindInstrumentRepositoryImpl
- Risk: Medium — PostgreSQL dependency requires testing

### Phase 3: Central Orchestrator

**3. shop** (Spring Boot 1.5.19 → 3.2.x, Java 1.8 → 17)
- Most complex migration: 5+ Spring Boot major versions to traverse
- Upgrade path: Spring Boot 1.5.19 → 2.0.x → 2.7.x → 3.2.x (staged)
- Key changes:
  - Java 1.8 → 17
  - `javax.*` → `jakarta.*` namespace
  - Spring Cloud Dalston → 2023.0.x
  - Netflix Hystrix → Resilience4j
  - Thymeleaf compatibility updates
  - RestTemplate API changes
  - OTel annotations alpha → stable GA
  - Log4j 2.24.3 — already patched, no change needed
- Risk: High — central orchestrator, many dependencies

### Phase 4: Standalone Utilities

**4a. test** (Java 11 → 17)
- Upgrade Log4j 2.6.1 → 2.24.x (critical security fix)
- Remove commons-httpclient 3.1 (already uses java.net.http)
- Risk: Low — standalone traffic generator

**4b. annotator** (Java 17) — ✅ Already modern
- Upgrade OTel annotations alpha → stable GA
- Update javaparser-core to latest
- Risk: Low — offline utility

---

## Migration Dependency Diagram

```
Phase 1:   products ✅    conductors ✅
              │                │
Phase 2:   stock (upgrade)   instruments (upgrade)
              │                │
Phase 3:        shop (upgrade)
              │
Phase 4:   test (upgrade)    annotator ✅
```

---

## Related Documents

- [Remediation Plan](../technical-debt/remediation-plan.md) — Detailed upgrade steps
- [Validation Criteria](validation-criteria.md) — Success criteria
- [Test Specifications](test-specifications.md) — Test cases
