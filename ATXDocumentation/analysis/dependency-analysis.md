# Dependency Analysis

## Internal Dependency Map

### Service-Level Dependencies (Runtime REST)
```
shop ────────┬──▶ products     (GET /products?location=)
             ├──▶ conductors   (GET /conductors?location=)  [Utah only]
             ├──▶ stock        (GET /legacy)
             └──▶ instruments  (GET /instruments?location=)
instruments ──▶ PostgreSQL     (JDBC via Spring Data JPA)
stock ────────▶ H2             (In-memory via Spring Data JPA)
```

### Module Independence
- **products**: Fully independent (no external service calls, in-memory data)
- **conductors**: Fully independent (no external service calls, in-memory data)
- **stock**: Fully independent (H2 in-memory, no external calls)
- **instruments**: Depends on PostgreSQL (external database)
- **shop**: Depends on products, conductors, stock, instruments (REST)
- **annotator**: Fully independent (standalone utility)
- **test**: Depends on shop (traffic generator)

### Startup Order (for Docker Compose)
1. PostgreSQL → instruments (depends_on with health check)
2. products, stock, conductors (independent)
3. shop (links to products, stock, instruments)
4. shoptester (links to shop)

## External Dependency Summary

### Build Tool Dependencies
| Tool | Version | Module | Notes |
|------|---------|--------|-------|
| Maven | (system) | All | Build tool |
| spring-boot-maven-plugin | (managed) | shop, products, stock, instruments, conductors | Fat JAR packaging |
| exec-maven-plugin | 3.1.0 | annotator | Execution plugin |
| maven-jar-plugin | 3.0.0 | shop | ⚠️ Outdated |
| versions-maven-plugin | 2.7 | stock, instruments | Dependency version management |

### Runtime Dependency Version Matrix

| Dependency | shop | products | stock | instruments | conductors | annotator | test |
|-----------|------|----------|-------|-------------|------------|-----------|------|
| Spring Boot | 1.5.19 | 3.2.2 | 2.1.3 | 2.7.5 | 3.2.2 | — | — |
| Java | 8 | 17 | 8 | 17 | 17 | 17 | 11 |
| Log4j | 2.6.1 | — | — | (via SB) | — | — | 2.6.1 |
| OTel Annotations | 1.19.2α | 2.2.0 | — | 1.19.2α | — | 1.19.1α | — |
| Spring Data JPA | (SB 1.5) | — | (SB 2.1) | (SB 2.7) | — | — | — |
| Hystrix | yes | — | — | — | — | — | — |
| Thymeleaf | yes | — | — | — | — | — | — |

### Transitive Dependency Risks
1. **Spring Boot parent POM**: Each module inherits a different set of managed dependency versions, potentially causing classpath conflicts if modules were ever combined
2. **Log4j transitive**: Spring Boot 2.7.5 (instruments) manages its own Log4j version through `spring-boot-starter-log4j2`, which may differ from the explicitly declared 2.6.1 in shop
3. **Jackson versions**: Different Spring Boot versions bring different Jackson versions for JSON serialization

## Criticality Analysis

| Dependency | Criticality | Reason |
|-----------|-------------|--------|
| Spring Boot | Critical | Core framework for all services |
| PostgreSQL | Critical | Production data store for instruments |
| Spring Data JPA | High | Data access layer for instruments, stock |
| Hystrix | High | Resilience for shop service |
| Thymeleaf | High | UI rendering for shop |
| Log4j | High | Logging + CRITICAL SECURITY VULNERABILITY |
| OTel Annotations | Medium | Observability instrumentation |
| H2 | Medium | Stock data (demo/dev only) |
| nekohtml | Low | Thymeleaf legacy HTML mode support |
| commons-httpclient | Low | Test traffic generation only |
| JavaParser | Low | Annotator utility only |

## Cross-References

- [Code Metrics](code-metrics.md) | [Complexity Analysis](complexity-analysis.md) | [Security Patterns](security-patterns.md)
- [Architecture Dependencies](../architecture/dependencies.md)
- [Outdated Components](../technical-debt/outdated-components.md)
