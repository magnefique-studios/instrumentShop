# Remediation Plan

## Priority 1: Critical Security (High Severity)

### 1.1 Upgrade Log4j 2.6.1 → 2.23+ (or migrate to SLF4J/Logback)
- **Modules**: shop, test
- **Rationale**: CVE-2021-44228 (Log4Shell) enables remote code execution
- **Action**: Update `log4j-api` and `log4j-core` versions in `shop/pom.xml` and `test/pom.xml` to 2.23+, or use `AWS/early-access-log4j-to-slf4j-migration` transformation to migrate to SLF4J with Logback
- **Complexity**: Low (version bump) to Medium (full migration to SLF4J)

### 1.2 Fix SQL Injection in FindInstrumentRepositoryImpl
- **Module**: instruments
- **File**: `instruments/src/main/java/.../repositories/FindInstrumentRepositoryImpl.java` line 33
- **Current**: `"FROM instruments i WHERE i.ID = " + id.toString()`
- **Action**: Use parameterized queries: `entityManager.createQuery("FROM Instrument i WHERE i.id = :id").setParameter("id", id)`
- **Complexity**: Low

## Priority 2: EOL Framework Upgrades (High Severity)

### 2.1 Upgrade Shop Module: Spring Boot 1.5.19 → 3.x
- **Complexity**: High
- **Steps**:
  1. Upgrade Java target from 8 to 17+
  2. Upgrade Spring Boot 1.5 → 2.x → 3.x
  3. Migrate Spring Cloud Dalston → 2022.x+
  4. Replace Hystrix with Resilience4j / Spring Cloud Circuit Breaker
  5. Migrate javax.* imports to jakarta.*
  6. Update Thymeleaf configuration (remove LEGACYHTML5 mode, drop nekohtml)
  7. Verify RestTemplate usage (consider WebClient for reactive)

### 2.2 Upgrade Stock Module: Spring Boot 2.1.3 → 3.x
- **Complexity**: Medium
- **Steps**:
  1. Upgrade Java target from 8 to 17+
  2. Upgrade Spring Boot 2.1 → 3.x
  3. Migrate javax.persistence → jakarta.persistence
  4. Migrate javax.validation → jakarta.validation
  5. Migrate javax.annotation → jakarta.annotation (PostConstruct)
  6. Update Cucumber from info.cukes 1.2.5 to io.cucumber 7.x
  7. Update H2 database driver if needed

### 2.3 Upgrade Instruments Module: Spring Boot 2.7.5 → 3.x
- **Complexity**: Medium
- **Steps**:
  1. Upgrade Spring Boot 2.7 → 3.x
  2. Migrate javax.persistence → jakarta.persistence in all entity classes and repositories
  3. Migrate javax.annotation.PostConstruct → jakarta.annotation.PostConstruct
  4. Update OpenTelemetry annotations from 1.19.2-alpha to 2.x stable
  5. Fix Cartesian join in `FindInstrumentRepositoryImpl.findInstruments()`

### 2.4 Upgrade JUnit 3.8.1 → JUnit 5
- **Module**: root POM
- **Complexity**: Low
- **Action**: Replace `junit:junit:3.8.1` with `org.junit.jupiter:junit-jupiter:5.10+`

## Priority 3: Dependency Updates (Medium Severity)

### 3.1 Replace commons-httpclient 3.1
- **Module**: test
- **Action**: Replace with Apache HttpComponents HttpClient 5.x or Java 11+ `java.net.http.HttpClient`
- **Complexity**: Medium

### 3.2 Standardize OpenTelemetry Annotations
- **Modules**: shop, instruments, annotator, products
- **Action**: Standardize all to `io.opentelemetry.instrumentation:opentelemetry-instrumentation-annotations:2.x` (stable)
- **Complexity**: Low

### 3.3 Upgrade JavaParser
- **Module**: annotator
- **Action**: Update from 3.23.1 to 3.26+
- **Complexity**: Low

### 3.4 Remove nekohtml dependency
- **Module**: shop
- **Action**: Remove nekohtml 1.9.22 when upgrading Thymeleaf (modern Thymeleaf doesn't need it)
- **Complexity**: Low (part of Spring Boot upgrade)

## Priority 4: Code Quality (Low Severity)

### 4.1 Remove deprecated finalize() method
- **File**: `shop/src/main/java/.../Exercises.java` line 35
- **Action**: Replace with `try-with-resources` or `Closeable` implementation
- **Complexity**: Low

### 4.2 Fix endpoint typo
- **File**: `stock/src/main/java/.../resources/StockResource.java` line 34
- **Action**: Rename `/insruments` to `/instruments` and update all callers
- **Complexity**: Low

### 4.3 Fix hardcoded location override
- **File**: `conductors/src/main/java/.../controllers/ConductorsController.java` line 22
- **Action**: Remove `location = "Oregon"` line, use the parameter value
- **Complexity**: Low

### 4.4 Fix Cartesian join
- **File**: `instruments/src/main/java/.../repositories/FindInstrumentRepositoryImpl.java` line 27
- **Current**: `SELECT * FROM instruments_for_sale, instruments_for_sale_chicago` (cross join)
- **Action**: Use `UNION ALL` or proper JOIN with a condition
- **Complexity**: Low

### 4.5 Clean up dead code
- **Action**: Remove all commented-out code blocks across the codebase
- **Complexity**: Low

### 4.6 Consolidate Products and Conductors modules
- **Action**: Consider merging the largely duplicated Product/Conductors logic into a single parameterized service
- **Complexity**: Medium

## Recommended AWS Transformations

1. **`AWS/java-version-upgrade`**: Upgrade Java 8 modules (shop, stock) to Java 17 or 21
2. **`AWS/early-access-log4j-to-slf4j-migration`**: Migrate Log4j 2.6.1 to SLF4J/Logback in shop and test modules

## Cross-References

- [Technical Debt Summary](summary.md)
- [Outdated Components](outdated-components.md)
- [Maintenance Burden](maintenance-burden.md)
- [Root-Level Report](../technical-debt-report.md)
