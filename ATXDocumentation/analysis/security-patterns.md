# Security Patterns

[← Back to README](../README.md) | [Code Metrics](code-metrics.md) | [Dependency Analysis](dependency-analysis.md) | [Tech Debt](tech-debt.md)

## Critical Security Findings

### 1. SQL Injection — FindInstrumentRepositoryImpl (High Impact)

**Location**: `instruments/src/main/java/.../repositories/FindInstrumentRepositoryImpl.java`
**Line**: `findInstrumentByID()` method

```java
// VULNERABLE: String concatenation in JPQL
Instrument result = (Instrument) entityManager.createQuery(
    "FROM instruments i WHERE i.ID = " + id.toString()
).getSingleResult();
```

**Risk**: An attacker can inject arbitrary JPQL/SQL via the `id` parameter.
**Remediation**: Use parameterized queries:
```java
entityManager.createQuery("FROM Instrument i WHERE i.id = :id")
    .setParameter("id", id)
    .getSingleResult();
```

**Note**: This method is not currently called by any REST endpoint (commented out in `InstrumentResource`), reducing immediate exposure but remaining a risk if re-enabled.

### 2. Log4j RCE — CVE-2021-44228 (High Impact)

**Location**: `test/pom.xml`
**Version**: Log4j 2.6.1 (log4j-api and log4j-core)
**CVE**: CVE-2021-44228 (Log4Shell), CVSS 10.0
**Risk**: Remote code execution via JNDI lookup injection in log messages
**Remediation**: Upgrade to Log4j 2.24.x

**Note**: The **shop module** has Log4j 2.24.3 — ✅ already patched and NOT vulnerable.

---

## Security Patterns in Code

### Authentication/Authorization
- `HomeController.checkIfRestricted()` — Permission check framework exists but is **entirely commented out** (the external Lambda URL call). Currently always returns `false`.
- `HomeController.allParameters()` throws `NoPermissionException` if restricted — but never triggers.
- No other authentication or authorization mechanisms found in any module.

### Input Validation
- `Instrument.isEnglish()` (shop module) — Regex-based locale validation for instrument titles. Checks against English character pattern.
- `Instrument.buildForLocale()` — Calls `isEnglish()` but the exception throw is **commented out**, making the validation ineffective.
- No input validation on REST endpoint parameters across any service.

### Credential Handling
- `Exercises.checkExercise2()` reads `SPLUNK_ACCESS_TOKEN` and `SPLUNK_REALM` from properties file
- Token is sent via HTTP header `X-SF-Token` to Splunk ingestion endpoint
- Token values are read from container-mounted file, not hardcoded in source
- Code checks for dummy/placeholder values before attempting API calls

### Error Information Disclosure
- Stack traces printed via `e.printStackTrace()` in multiple locations (Exercises, InstrumentService)
- `System.out.println("DATA IS FOUND: " + data)` in HomeController exposes request data to console
- Exercise data and scores are exposed via `/score` endpoint without authentication

---

## Empty Catch Blocks (Security Impact)

60+ empty catch blocks in ProductFilterService can mask security-relevant exceptions:
```java
try {
    Thread.sleep(sleepy);
} catch (Exception e) {
    // Exception silently swallowed — including InterruptedException
}
```

While these primarily catch `InterruptedException`, the overly broad `Exception` type could mask unexpected errors including security-relevant ones.

---

## Cartesian Product Query (Data Exposure)

**Location**: `FindInstrumentRepositoryImpl.findInstruments()`

```java
entityManager.createNativeQuery(
    "SELECT * FROM instruments_for_sale, instruments_for_sale_chicago"
).getResultList();
```

This unjoined cross-product returns `rows_table1 × rows_table2` results, potentially exposing more data than intended and causing performance degradation.

---

## Network Security

- All inter-service communication uses unencrypted HTTP (no TLS)
- PostgreSQL credentials are environment variables in `docker-compose.yml`: `instruments/instruments`
- No API authentication between microservices
- External network `instrument_shop` is declared but configured as external (must pre-exist)

---

## Related Documents

- [Technical Debt Report](../technical-debt-report.md) — Prioritized findings
- [Error Handling](../behavior/error-handling.md) — Exception patterns
- [Dependency Analysis](dependency-analysis.md) — Vulnerable dependency versions
