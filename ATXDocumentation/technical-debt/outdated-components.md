# Outdated Components

[← Back to README](../README.md) | [← Technical Debt Report](../technical-debt-report.md) | [Summary](summary.md) | [Remediation Plan](remediation-plan.md)

## Frameworks & Runtimes (High Severity)

### Spring Boot Versions

| Module | Current Version | Status | EOL Date | Recommended |
|--------|----------------|--------|----------|-------------|
| shop | 1.5.19.RELEASE | 🔴 EOL | August 2019 | Spring Boot 3.2.x+ |
| stock | 2.1.3.RELEASE | 🔴 EOL | Late 2019 | Spring Boot 3.2.x+ |
| instruments | 2.7.5 | 🔴 EOL | November 2023 | Spring Boot 3.2.x+ |
| products | 3.2.2 | ✅ Current | Active | No action needed |
| conductors | 3.2.2 | ✅ Current | Active | No action needed |

### Java Versions

| Module | Current Version | Status | Recommended |
|--------|----------------|--------|-------------|
| shop | 1.8 | 🔴 Limits upgrades | Java 17 or 21 |
| stock | 1.8 | 🔴 Limits upgrades | Java 17 or 21 |
| test | 11 | 🟡 Active but aging | Java 17 or 21 |
| products | 17 | ✅ Current LTS | No action needed |
| conductors | 17 | ✅ Current LTS | No action needed |
| instruments | 17 | ✅ Current | No action needed |
| annotator | 17 | ✅ Current | No action needed |

### Spring Cloud

| Module | Current Version | Status | Recommended |
|--------|----------------|--------|-------------|
| shop | Dalston.SR5 | 🔴 EOL (Dec 2018) | Spring Cloud 2023.0.x (requires Spring Boot 3.2.x) |

### Netflix Hystrix

| Module | Status | Recommended |
|--------|--------|-------------|
| shop | 🔴 Deprecated (2018) | Migrate to Resilience4j or Spring Cloud Circuit Breaker |

---

## Dependencies (Medium Severity)

### Logging

| Module | Library | Current | Status | Recommended |
|--------|---------|---------|--------|-------------|
| test | log4j-api | 2.6.1 | 🔴 **CVE-2021-44228** (Log4Shell, CVSS 10.0) | 2.24.x+ |
| test | log4j-core | 2.6.1 | 🔴 **CVE-2021-44228** | 2.24.x+ |
| shop | log4j-api | 2.24.3 | ✅ Patched | No action needed |
| shop | log4j-core | 2.24.3 | ✅ Patched | No action needed |

### Testing Frameworks

| Module | Library | Current | Status | Recommended |
|--------|---------|---------|--------|-------------|
| root | junit | 3.8.1 | 🔴 Extremely outdated | JUnit 5.10.x (junit-jupiter) |
| stock | cucumber-java (info.cukes) | 1.2.5 | 🔴 Deprecated groupId | io.cucumber:cucumber-java 7.x+ |
| stock | cucumber-junit (info.cukes) | 1.2.5 | 🔴 Deprecated groupId | io.cucumber:cucumber-junit 7.x+ |
| stock | cucumber-spring (info.cukes) | 1.2.5 | 🔴 Deprecated groupId | io.cucumber:cucumber-spring 7.x+ |

### HTTP Clients

| Module | Library | Current | Status | Recommended |
|--------|---------|---------|--------|-------------|
| test | commons-httpclient | 3.1 | 🔴 Deprecated | Apache HttpComponents HttpClient 5.x or java.net.http |

### OpenTelemetry

| Module | Library | Current | Status | Recommended |
|--------|---------|---------|--------|-------------|
| shop | opentelemetry-instrumentation-annotations | 1.19.2-alpha | 🟡 Alpha pre-release | 2.x stable GA |
| instruments | opentelemetry-instrumentation-annotations | 1.19.2-alpha | 🟡 Alpha pre-release | 2.x stable GA |
| annotator | opentelemetry-instrumentation-annotations | 1.19.1-alpha | 🟡 Alpha pre-release | 2.x stable GA |
| products | opentelemetry-instrumentation-annotations | 2.2.0 | ✅ Current | No action needed |

### Other

| Module | Library | Current | Status | Recommended |
|--------|---------|---------|--------|-------------|
| shop | nekohtml | 1.9.22 | 🟡 Outdated | Consider removal or update |
| annotator | javaparser-core | 3.23.1 | 🟡 Minor updates available | 3.26.x |
| shop | aspectjweaver | 1.9.19 | ✅ Recent | No action needed |
| stock | hamcrest-core | 2.1 | 🟡 Minor update available | 2.2 |

### Build Plugins

| Module | Plugin | Current | Status | Recommended |
|--------|--------|---------|--------|-------------|
| shop | maven-jar-plugin | 3.0.0 | 🟡 Outdated | 3.4.x |
| stock | versions-maven-plugin | 2.7 | 🟡 Outdated | 2.17.x |
| annotator | exec-maven-plugin | 3.1.0 | ✅ Recent | No action needed |

---

## Related Documents

- [Summary](summary.md) — Overview of all technical debt
- [Maintenance Burden](maintenance-burden.md) — Maintenance complexity
- [Remediation Plan](remediation-plan.md) — Prioritized upgrade paths
