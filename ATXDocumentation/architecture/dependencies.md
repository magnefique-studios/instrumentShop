# Dependencies

[← Back to README](../README.md) | [System Overview](system-overview.md) | [Components](components.md) | [Patterns](patterns.md)

## Internal Service Dependencies

```
shop (port 8010)
 ├── products (port 8020)     — GET /products?location={location}
 ├── conductors (port 8050)   — GET /conductors?location={location}  (only when location=Utah)
 ├── stock (port 8030)        — GET /legacy, GET /instrumemnts
 └── instruments (port 8040)  — GET /instruments?location={location}
      └── postgresDB (port 5432) — JDBC/JPA

shoptester → shop (port 8010) — HTTP traffic generation
```

## External Dependencies by Module

### Root POM
| GroupId | ArtifactId | Version | Scope |
|---------|-----------|---------|-------|
| junit | junit | 3.8.1 | test |

### Shop Module (Spring Boot 1.5.19, Java 1.8)
| GroupId | ArtifactId | Version | Scope |
|---------|-----------|---------|-------|
| org.springframework.boot | spring-boot-starter-parent | 1.5.19.RELEASE | parent |
| org.springframework.cloud | spring-cloud-dependencies | Dalston.SR5 | BOM |
| org.springframework.boot | spring-boot-starter-thymeleaf | (managed) | compile |
| org.springframework.boot | spring-boot-starter-actuator | (managed) | compile |
| org.springframework.cloud | spring-cloud-starter-hystrix | (managed) | compile |
| org.springframework.cloud | spring-cloud-starter-eureka | (managed) | compile |
| org.aspectj | aspectjweaver | 1.9.19 | compile |
| org.apache.logging.log4j | log4j-api | **2.24.3** ✅ | compile |
| org.apache.logging.log4j | log4j-core | **2.24.3** ✅ | compile |
| io.opentelemetry.instrumentation | opentelemetry-instrumentation-annotations | 1.19.2-alpha | compile |
| net.sourceforge.nekohtml | nekohtml | 1.9.22 | compile |

### Products Module (Spring Boot 3.2.2, Java 17)
| GroupId | ArtifactId | Version | Scope |
|---------|-----------|---------|-------|
| org.springframework.boot | spring-boot-starter-parent | 3.2.2 | parent |
| org.springframework.boot | spring-boot-starter-web | (managed) | compile |
| org.springframework.boot | spring-boot-docker-compose | (managed) | runtime/optional |
| io.opentelemetry.instrumentation | opentelemetry-instrumentation-annotations | 2.2.0 | compile |

### Conductors Module (Spring Boot 3.2.2, Java 17)
| GroupId | ArtifactId | Version | Scope |
|---------|-----------|---------|-------|
| org.springframework.boot | spring-boot-starter-parent | 3.2.2 | parent |
| org.springframework.boot | spring-boot-starter-web | (managed) | compile |
| org.springframework.boot | spring-boot-docker-compose | (managed) | runtime/optional |

### Instruments Module (Spring Boot 2.7.5, Java 17)
| GroupId | ArtifactId | Version | Scope |
|---------|-----------|---------|-------|
| org.springframework.boot | spring-boot-starter-parent | 2.7.5 | parent |
| org.springframework.boot | spring-boot-starter-web | (managed) | compile |
| org.springframework.boot | spring-boot-starter-log4j2 | (managed) | compile |
| org.springframework.boot | spring-boot-starter-data-jpa | (managed) | compile |
| org.springframework.boot | spring-boot-starter-actuator | (managed) | compile |
| org.springframework.boot | spring-boot-starter-test | (managed) | test |
| org.postgresql | postgresql | (managed) | runtime |
| org.hamcrest | hamcrest-core | (managed) | compile |
| io.opentelemetry.instrumentation | opentelemetry-instrumentation-annotations | 1.19.2-alpha | compile |

### Stock Module (Spring Boot 2.1.3, Java 1.8)
| GroupId | ArtifactId | Version | Scope |
|---------|-----------|---------|-------|
| org.springframework.boot | spring-boot-starter-parent | 2.1.3.RELEASE | parent |
| org.springframework.boot | spring-boot-starter-web | (managed) | compile |
| org.springframework.boot | spring-boot-starter-data-jpa | (managed) | compile |
| org.springframework.boot | spring-boot-starter-actuator | (managed) | compile |
| org.springframework.boot | spring-boot-starter-test | (managed) | test |
| com.h2database | h2 | (managed) | compile |
| org.hamcrest | hamcrest-core | 2.1 | compile |
| info.cukes | cucumber-java | 1.2.5 | compile |
| info.cukes | cucumber-junit | 1.2.5 | compile |
| info.cukes | cucumber-spring | 1.2.5 | compile |

### Annotator Module (Java 17, no Spring Boot)
| GroupId | ArtifactId | Version | Scope |
|---------|-----------|---------|-------|
| com.github.javaparser | javaparser-core | 3.23.1 | compile |
| io.opentelemetry.instrumentation | opentelemetry-instrumentation-annotations | 1.19.1-alpha | compile |

### Test Module (Java 11, no Spring Boot)
| GroupId | ArtifactId | Version | Scope |
|---------|-----------|---------|-------|
| org.apache.logging.log4j | log4j-api | **2.6.1** 🔴 CVE-2021-44228 | compile |
| org.apache.logging.log4j | log4j-core | **2.6.1** 🔴 CVE-2021-44228 | compile |
| commons-httpclient | commons-httpclient | 3.1 | compile |

---

## Related Documents

- [Outdated Components](../technical-debt/outdated-components.md) — Version EOL analysis
- [Dependency Analysis](../analysis/dependency-analysis.md) — Detailed dependency mapping
- [Security Patterns](../analysis/security-patterns.md) — Security concerns in dependencies
