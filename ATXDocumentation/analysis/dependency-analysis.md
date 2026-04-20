# Dependency Analysis

## Internal Dependencies (Runtime HTTP)

```
shop ──HTTP──→ products     (GET /products?location=...)
shop ──HTTP──→ conductors   (GET /conductors?location=..., Utah only)
shop ──HTTP──→ stock        (GET /legacy, GET /instrumemnts)
shop ──HTTP──→ instruments  (GET /instruments?location=...)
instruments ──JDBC──→ PostgreSQL (port 5432)
```

## External Dependency Map

### Framework Dependencies
| Library | shop | stock | instruments | products | conductors | annotator |
|---------|------|-------|-------------|----------|------------|-----------|
| Spring Boot 1.5.x | ✅ | — | — | — | — | — |
| Spring Boot 2.1.x | — | ✅ | — | — | — | — |
| Spring Boot 2.7.x | — | — | ✅ | — | — | — |
| Spring Boot 3.2.x | — | — | — | ✅ | ✅ | — |
| Spring Cloud Dalston | ✅ | — | — | — | — | — |
| Spring Data JPA | — | ✅ | ✅ | — | — | — |

### Runtime Dependencies
| Library | shop | stock | instruments | products | conductors | annotator |
|---------|------|-------|-------------|----------|------------|-----------|
| Log4j 2.6.1→2.24.3 ¹ | ✅ | — | — | — | — | — |
| Log4j2 (via starter) | — | — | ✅ (~2.17.2) | — | — | — |
| Log4j API (undeclared) ² | — | — | — | ⚠️ | ⚠️ | — |
| OTel annotations | ✅ (1.19.2α) | — | ✅ (1.19.2α) | ✅ (2.2.0) | — | ✅ (1.19.1α) |
| AspectJ 1.9.19 | ✅ | — | — | — | — | — |
| Hystrix | ✅ | — | — | — | — | — |
| PostgreSQL driver | — | — | ✅ | — | — | — |
| H2 database | — | ✅ | — | — | — | — |
| NekoHTML 1.9.22 | ✅ | — | — | — | — | — |
| JavaParser 3.23.1 | — | — | — | — | — | ✅ |
| Thymeleaf | ✅ | — | — | — | — | — |

> ¹ **PR #25 Update**: Log4j upgraded from 2.6.1 to 2.24.3 in `shop/pom.xml` and `test/pom.xml` to fix CVE-2017-5645 (CVSS 9.8). **⚠️ Java version compatibility concern**: Log4j 2.24.3 requires Java 17+, but `shop` targets Java 8 and `test` targets Java 11. See [PR #25 Analysis](pr-25-cve-2017-5645-analysis.md).
>
> ² **Missing Dependency Finding**: `products` and `conductors` modules use `org.apache.logging.log4j.LogManager` and `Logger` in their `ProductFilterService.java` code but do not declare any Log4j dependency in their respective `pom.xml` files.

### Test Dependencies
| Library | shop | stock | instruments | products | conductors | annotator |
|---------|------|-------|-------------|----------|------------|-----------|
| JUnit 3.8.1 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Cucumber 1.2.5 | — | ✅ | — | — | — | — |
| Hamcrest | — | ✅ (2.1) | ✅ (managed) | — | — | — |
| spring-boot-starter-test | — | ✅ | ✅ | — | — | — |
| Log4j 2.6.1→2.24.3 ¹ | — | — | — | — | — | — |
| commons-httpclient 3.1 | — | — | — | — | — | — |

> ¹ `test/pom.xml` also upgraded from 2.6.1 to 2.24.3 per PR #25.

## Dependency Health Assessment

| Status | Count | Examples |
|--------|-------|---------|
| 🔴 EOL/Critical | 4 | Spring Boot 1.5.x, 2.1.x; Spring Cloud Dalston; Hystrix |
| 🟡 Outdated | 5 | Spring Boot 2.7.x, JUnit 3.8.1, OTel alpha, Cucumber 1.2.5, commons-httpclient 3.1 |
| 🟡 Compatibility Risk | 1 | Log4j 2.24.3 (requires Java 17+, modules target Java 8/11) |
| 🟢 Remediated | 1 | Log4j CVE-2017-5645 + related CVEs (via PR #25 upgrade, pending compatibility fix) |
| 🟢 Current/Acceptable | 5+ | Spring Boot 3.2.2, OTel 2.2.0, PostgreSQL driver (managed) |

## Log4j Version Compatibility Matrix

| Module | Current Log4j | Target (PR #25) | Java Target | Compatible? | Max Safe Version |
|--------|---------------|-----------------|-------------|-------------|------------------|
| shop | 2.6.1 | 2.24.3 | Java 8 | ❌ | 2.17.1 |
| test | 2.6.1 | 2.24.3 | Java 11 | ❌ | 2.19.0 |
| instruments | ~2.17.2 (managed) | N/A | Java 17 | ✅ | N/A |

## Related Documents

- [Architecture → Dependencies](../architecture/dependencies.md)
- [Technical Debt → Outdated Components](../technical-debt/outdated-components.md)
- [PR #25 CVE-2017-5645 Analysis](pr-25-cve-2017-5645-analysis.md)
- [Code Metrics](code-metrics.md) | [Security Patterns](security-patterns.md)

---

[← Back to README](../README.md)
