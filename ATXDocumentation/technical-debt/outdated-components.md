# Outdated Components

## 🔴 High Severity — EOL/Deprecated Runtimes & Frameworks

### 1. Spring Boot 1.5.19.RELEASE (shop module)
- **File**: `shop/pom.xml` line 15
- **Status**: End of Life since August 2019
- **Impact**: No security patches, no bug fixes, incompatible with modern Spring ecosystem
- **Upgrade Path**: Spring Boot 1.5 → 2.x → 3.x (requires Java 17+, Jakarta EE migration)

### 2. Spring Boot 2.1.3.RELEASE (stock module)
- **File**: `stock/pom.xml` line 14
- **Status**: End of Life (out of OSS support)
- **Impact**: No security patches
- **Upgrade Path**: Spring Boot 2.1 → 3.x (requires Java 17+, Jakarta EE migration)

### 3. Spring Boot 2.7.5 (instruments module)
- **File**: `instruments/pom.xml` line 15
- **Status**: End of Life since November 2023
- **Impact**: No security patches
- **Upgrade Path**: Spring Boot 2.7 → 3.x (requires Jakarta EE migration, javax → jakarta)

### 4. Spring Cloud Dalston.SR5 (shop module)
- **File**: `shop/pom.xml` line 27
- **Status**: End of Life
- **Impact**: Based on Spring Boot 1.5, incompatible with modern Spring Cloud
- **Upgrade Path**: Dalston → 2022.x+ (requires Spring Boot 3.x)

### 5. Netflix Hystrix (shop module)
- **File**: `shop/pom.xml` line 50 (`spring-cloud-starter-hystrix`)
- **Status**: Deprecated / Maintenance mode (Netflix stopped development)
- **Impact**: No new features or bug fixes
- **Upgrade Path**: Replace with Spring Cloud Circuit Breaker + Resilience4j

### 6. Java 8 Target (shop, stock modules)
- **Files**: `shop/pom.xml` line 21 (`<java.version>1.8</java.version>`), `stock/pom.xml` line 20
- **Status**: Oracle Java 8 extended support ending; open-source support available but ecosystem moving to 17+
- **Impact**: Cannot use modern Java features, Spring Boot 3.x requires Java 17+
- **Upgrade Path**: Java 8 → 17 or 21

### 7. javax.persistence (instruments, stock modules)
- **Files**: `instruments/src/main/java/.../model/Instrument.java` lines 4-8, `instruments/src/main/java/.../repositories/FindInstrumentRepositoryImpl.java` lines 6-7, `stock/src/main/java/.../model/Stock.java` lines 5-6
- **Status**: Superseded by Jakarta EE (`jakarta.persistence`)
- **Impact**: Incompatible with Spring Boot 3.x

### 8. javax.validation (stock module)
- **File**: `stock/src/main/java/.../services/StockService.java` line 14
- **Status**: Superseded by Jakarta EE (`jakarta.validation`)
- **Impact**: Incompatible with Spring Boot 3.x

### 9. JUnit 3.8.1 (root POM)
- **File**: `pom.xml` line 19
- **Status**: Extremely outdated (released ~2005). Current version: JUnit 5.10+
- **Impact**: Missing modern testing features (parameterized tests, extensions, assertions)
- **Upgrade Path**: JUnit 3 → JUnit 5 (Jupiter)

## 🟠 Medium Severity — Outdated Dependencies

### 10. Log4j 2.6.1 (shop, test modules)
- **Files**: `shop/pom.xml` lines 36-41, `test/pom.xml` lines 10-17
- **Status**: **Critical security vulnerability** — CVE-2021-44228 (Log4Shell) affects Log4j 2.x < 2.17.0
- **Impact**: Remote code execution vulnerability
- **Upgrade Path**: Log4j 2.6.1 → 2.23+ (or migrate to SLF4J/Logback)

### 11. commons-httpclient 3.1 (test module)
- **File**: `test/pom.xml` line 20
- **Status**: End of Life (project discontinued in 2007)
- **Impact**: No security updates, missing modern HTTP features
- **Upgrade Path**: commons-httpclient 3.1 → Apache HttpComponents HttpClient 5.x (or Java 11+ HttpClient)

### 12. nekohtml 1.9.22 (shop module)
- **File**: `shop/pom.xml` line 57
- **Status**: Outdated HTML parser, used for Thymeleaf LEGACYHTML5 mode
- **Impact**: Limited maintenance
- **Upgrade Path**: Remove by upgrading Thymeleaf (modern Thymeleaf doesn't need nekohtml)

### 13. OpenTelemetry Annotations (shop, instruments, annotator)
- **Files**: `shop/pom.xml` line 44 (1.19.2-alpha), `instruments/pom.xml` line 53 (1.19.2-alpha), `annotator/pom.xml` line 14 (1.19.1-alpha), `products/pom.xml` line 22 (2.2.0)
- **Status**: Alpha versions; inconsistent across modules
- **Impact**: API changes in stable releases
- **Upgrade Path**: Standardize all to 2.x stable release

### 14. Cucumber 1.2.5 (stock module)
- **File**: `stock/pom.xml` lines 36-48
- **Status**: Extremely outdated; `info.cukes` group is abandoned, replaced by `io.cucumber`
- **Impact**: Missing modern BDD testing features
- **Upgrade Path**: info.cukes:cucumber-* 1.2.5 → io.cucumber:cucumber-* 7.x

### 15. JavaParser 3.23.1 (annotator module)
- **File**: `annotator/pom.xml` line 12
- **Status**: Newer versions available (3.26+)
- **Impact**: Missing recent Java syntax support
- **Upgrade Path**: JavaParser 3.23.1 → 3.26+

## Cross-References

- [Technical Debt Summary](summary.md)
- [Remediation Plan](remediation-plan.md)
- [Dependencies](../architecture/dependencies.md)
