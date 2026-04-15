# Security Patterns

## Critical Vulnerabilities

### 1. SQL Injection — FindInstrumentRepositoryImpl.findInstrumentByID()
- **Severity**: High
- **File**: `instruments/src/main/java/.../repositories/FindInstrumentRepositoryImpl.java` line 33
- **Code**: `"FROM instruments i WHERE i.ID = " + id.toString()`
- **Impact**: An attacker can inject arbitrary SQL/HQL through the `id` parameter
- **Remediation**: Use parameterized queries:
  ```java
  entityManager.createQuery("FROM Instrument i WHERE i.id = :id")
               .setParameter("id", id)
               .getSingleResult();
  ```

### 2. Log4j 2.6.1 Remote Code Execution (CVE-2021-44228)
- **Severity**: High
- **Files**: `shop/pom.xml` lines 36-41, `test/pom.xml` lines 10-17
- **Impact**: Log4Shell vulnerability allows remote code execution through crafted log messages containing JNDI lookups
- **Remediation**: Upgrade to Log4j 2.17.0+ or migrate to SLF4J/Logback

### 3. Cross-Table Cartesian Join
- **Severity**: Medium
- **File**: `instruments/src/main/java/.../repositories/FindInstrumentRepositoryImpl.java` line 27
- **Code**: `"SELECT * FROM instruments_for_sale, instruments_for_sale_chicago"`
- **Impact**: Returns Cartesian product of both tables (data corruption/incorrect results), potential performance issue with large datasets
- **Remediation**: Use `UNION ALL` or a proper `JOIN` with conditions

## Credential and Secret Handling

### 4. Hardcoded Database Credentials
- **Severity**: Medium
- **File**: `docker-compose.yml` lines 121-123
- **Credentials**: PostgreSQL user/password both set to "instruments"
- **Impact**: Credentials visible in version control
- **Remediation**: Use Docker secrets or environment variable references from a `.env` file (partially done with `${SHOP_USER}`)

### 5. Commented-Out External HTTP Calls with Tokens
- **Severity**: Low (currently commented out)
- **File**: `shop/src/main/java/.../controllers/HomeController.java` lines 98-128
- **Description**: Commented-out code contains a Lambda function URL and HTTP POST with user credentials
- **Impact**: If uncommented, exposes endpoint URLs; demonstrates insecure credential handling pattern

### 6. Observability Token Handling in Exercises
- **Severity**: Medium
- **File**: `shop/src/main/java/.../Exercises.java` lines 144-197
- **Description**: `checkExercise2()` reads Splunk access tokens from a properties file and sends them via HTTP to `ingest.{realm}.signalfx.com`
- **Impact**: Tokens stored in plaintext file at `/container/shop/data/.env`
- **Mitigation**: Contains checks for "dummy"/"test"/"placeholder" tokens

## Input Validation

### 7. No Input Validation on Location Parameter
- **Severity**: Low
- **Files**: Multiple controllers across all modules
- **Description**: The `location` parameter is used directly in REST calls and database queries without validation:
  - `HomeController` — passes to services
  - `ProductController` — passes to filter service
  - `InstrumentResource` — passes to InstrumentService
  - `ConductorsController` — hardcoded (mitigates risk)
- **Impact**: Potential for unexpected behavior with malicious input
- **Remediation**: Add input validation/sanitization for location parameter

### 8. Missing CSRF Protection
- **Severity**: Low
- **File**: `shop/src/main/java/.../JavaShopApp.java`
- **Description**: Spring Boot 1.5 with Spring MVC does not enable CSRF protection by default for Thymeleaf forms
- **Impact**: Potential cross-site request forgery attacks

## Authentication & Authorization

### 9. No Authentication Framework
- **Severity**: Medium (for a production application)
- **Description**: No Spring Security or equivalent authentication mechanism is configured. The `checkIfRestricted()` method in `HomeController` is the only permission check, and it's commented out (always returns false).
- **Impact**: All endpoints are publicly accessible

## Secure Coding Observations

| Pattern | Status | Notes |
|---------|--------|-------|
| Parameterized queries | ❌ Not used | `FindInstrumentRepositoryImpl` uses string concatenation |
| Input validation | ❌ Minimal | Location parameter not validated |
| HTTPS | ❌ Not configured | All inter-service communication over plain HTTP |
| Authentication | ❌ None | No Spring Security |
| Secret management | ⚠️ Partial | `.env` file used but credentials in docker-compose.yml |
| Dependency scanning | ❌ Not configured | No OWASP or similar dependency check |
| Error messages | ⚠️ Verbose | Stack traces printed to stdout in multiple places |

## Cross-References

- [Error Handling](../behavior/error-handling.md) | [Remediation Plan](../technical-debt/remediation-plan.md)
- [Technical Debt Report](../technical-debt-report.md)
