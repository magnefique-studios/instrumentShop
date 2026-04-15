# Dependencies

## Internal Dependencies (Inter-Service Communication via REST)

```
shop ──REST──▶ products    (GET /products?location=)
shop ──REST──▶ conductors  (GET /conductors?location=)  [Utah only]
shop ──REST──▶ stock       (GET /legacy, GET /insruments)
shop ──REST──▶ instruments (GET /instruments?location=)
instruments ──JDBC──▶ PostgreSQL (instruments_for_sale, instruments_for_sale_chicago)
stock ──H2──▶ H2 In-Memory Database
```

## External Dependencies by Module

### Root POM
| Dependency | Version | Scope | Status |
|-----------|---------|-------|--------|
| junit:junit | 3.8.1 | test | ⚠️ **Extremely outdated** (current: JUnit 5.x) |

### Shop Module (Spring Boot 1.5.19)
| Dependency | Version | Scope | Status |
|-----------|---------|-------|--------|
| spring-boot-starter-parent | 1.5.19.RELEASE | parent | ⚠️ **EOL** (Aug 2019) |
| spring-cloud-dependencies | Dalston.SR5 | BOM | ⚠️ **EOL** |
| spring-cloud-starter-hystrix | (managed) | runtime | ⚠️ **Deprecated** (Netflix Hystrix in maintenance) |
| spring-cloud-starter-eureka | (managed) | runtime | ⚠️ **Deprecated** |
| spring-boot-starter-thymeleaf | (managed) | runtime | OK (version tied to SB 1.5) |
| spring-boot-starter-actuator | (managed) | runtime | OK (version tied to SB 1.5) |
| aspectjweaver | 1.9.19 | runtime | OK |
| log4j-api | 2.6.1 | runtime | 🔴 **CRITICAL SECURITY** (CVE-2021-44228) |
| log4j-core | 2.6.1 | runtime | 🔴 **CRITICAL SECURITY** (CVE-2021-44228) |
| opentelemetry-instrumentation-annotations | 1.19.2-alpha | runtime | ⚠️ Alpha version, newer available |
| nekohtml | 1.9.22 | runtime | ⚠️ Outdated HTML parser |
| maven-jar-plugin | 3.0.0 | build | ⚠️ Outdated |

### Products Module (Spring Boot 3.2.2)
| Dependency | Version | Scope | Status |
|-----------|---------|-------|--------|
| spring-boot-starter-parent | 3.2.2 | parent | ✅ Recent |
| spring-boot-starter-web | (managed) | runtime | ✅ |
| spring-boot-docker-compose | (managed) | runtime | ✅ |
| opentelemetry-instrumentation-annotations | 2.2.0 | runtime | ✅ Recent |

### Stock Module (Spring Boot 2.1.3)
| Dependency | Version | Scope | Status |
|-----------|---------|-------|--------|
| spring-boot-starter-parent | 2.1.3.RELEASE | parent | ⚠️ **EOL** |
| spring-boot-starter-web | (managed) | runtime | ⚠️ EOL version |
| spring-boot-starter-data-jpa | (managed) | runtime | ⚠️ EOL version |
| h2 | (managed) | runtime | OK |
| spring-boot-starter-actuator | (managed) | runtime | ⚠️ EOL version |
| hamcrest-core | 2.1 | test | OK |
| cucumber-java (info.cukes) | 1.2.5 | test | ⚠️ **Outdated** (current: io.cucumber 7.x) |
| cucumber-junit (info.cukes) | 1.2.5 | test | ⚠️ **Outdated** |
| cucumber-spring (info.cukes) | 1.2.5 | test | ⚠️ **Outdated** |
| versions-maven-plugin | 2.7 | build | OK |

### Instruments Module (Spring Boot 2.7.5)
| Dependency | Version | Scope | Status |
|-----------|---------|-------|--------|
| spring-boot-starter-parent | 2.7.5 | parent | ⚠️ **EOL** (Nov 2023) |
| spring-boot-starter-web | (managed) | runtime | ⚠️ EOL version |
| spring-boot-starter-data-jpa | (managed) | runtime | ⚠️ EOL version |
| spring-boot-starter-log4j2 | (managed) | runtime | ⚠️ EOL version |
| postgresql | (managed) | runtime | OK (managed by SB parent) |
| spring-boot-starter-actuator | (managed) | runtime | ⚠️ EOL version |
| opentelemetry-instrumentation-annotations | 1.19.2-alpha | runtime | ⚠️ Alpha, inconsistent with Products |
| hamcrest-core | (managed) | test | OK |

### Conductors Module (Spring Boot 3.2.2)
| Dependency | Version | Scope | Status |
|-----------|---------|-------|--------|
| spring-boot-starter-parent | 3.2.2 | parent | ✅ Recent |
| spring-boot-starter-web | (managed) | runtime | ✅ |
| spring-boot-docker-compose | (managed) | runtime | ✅ |

### Annotator Module (No Spring Boot)
| Dependency | Version | Scope | Status |
|-----------|---------|-------|--------|
| javaparser-core | 3.23.1 | runtime | ⚠️ Newer versions available |
| opentelemetry-instrumentation-annotations | 1.19.1-alpha | runtime | ⚠️ Alpha, inconsistent |

### Test Module (No Spring Boot)
| Dependency | Version | Scope | Status |
|-----------|---------|-------|--------|
| log4j-api | 2.6.1 | runtime | 🔴 **CRITICAL SECURITY** |
| log4j-core | 2.6.1 | runtime | 🔴 **CRITICAL SECURITY** |
| commons-httpclient | 3.1 | runtime | ⚠️ **EOL** (replaced by Apache HttpComponents) |

## Infrastructure Dependencies

| Component | Version | Status |
|-----------|---------|--------|
| PostgreSQL | 13.1-alpine | ⚠️ EOL (Nov 2025 approaching) |
| Redis | latest | ✅ (unused by application code) |
| Docker Compose | v3 format | OK |

## Dependency Graph

```
                    ┌──────────────┐
                    │  Root POM    │
                    │  junit 3.8.1 │
                    └──────┬───────┘
         ┌─────────┬──────┼──────┬──────────┬──────────┬─────────┐
         ▼         ▼      ▼      ▼          ▼          ▼         ▼
      ┌──────┐ ┌──────┐ ┌────┐ ┌─────────┐ ┌──────────┐ ┌─────┐ ┌────┐
      │ shop │ │stock │ │prod│ │instrmts │ │conductors│ │annot│ │test│
      │SB1.5 │ │SB2.1 │ │SB3 │ │SB2.7    │ │SB3.2     │ │Java │ │Java│
      │Java8 │ │Java8 │ │J17 │ │Java17   │ │Java17    │ │17   │ │11  │
      └──────┘ └──────┘ └────┘ └─────────┘ └──────────┘ └─────┘ └────┘
```

## Cross-References

- [System Overview](system-overview.md) | [Components](components.md) | [Patterns](patterns.md)
- [Technical Debt - Outdated Components](../technical-debt/outdated-components.md)
- [Dependency Analysis](../analysis/dependency-analysis.md)
