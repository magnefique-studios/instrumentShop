# Security Patterns

## Critical Security Findings

### 1. SQL Injection — Severity: Medium

**File**: `instruments/src/main/java/.../repositories/FindInstrumentRepositoryImpl.java`, line ~32

```java
public Instrument findInstrumentByID(String id) {
    Instrument result = (Instrument) entityManager
        .createQuery("FROM instruments i WHERE i.ID = " + id.toString())
        .getSingleResult();
    return result;
}
```

**Risk**: The `id` parameter is concatenated directly into the HQL query without sanitization. An attacker could inject arbitrary HQL/SQL.

**Fix**: Use parameterized queries:
```java
entityManager.createQuery("FROM Instrument i WHERE i.id = :id")
    .setParameter("id", id)
    .getSingleResult();
```

---

### 2. Log4j 2.6.1 Vulnerability — Severity: Medium → ✅ REMEDIATION IN PROGRESS

**File**: `shop/pom.xml`, lines ~31-37; `test/pom.xml`, lines ~17-24

**Previous State**: Log4j 2.6.1 — vulnerable to CVE-2017-5645 (Deserialization RCE, CVSS 9.8), CVE-2021-44228 (Log4Shell, CVSS 10.0), CVE-2021-45046, CVE-2021-45105, CVE-2021-44832.

**Remediation**: [PR #25](https://github.com/magnefique-studios/instrumentShop/pull/25) upgrades `log4j-api` and `log4j-core` from 2.6.1 to 2.24.3 in both `shop/pom.xml` and `test/pom.xml`.

**⚠️ Compatibility Warning**: Log4j 2.24.3 requires Java 17+. The `shop` module targets Java 8 and the `test` module targets Java 11. This version may cause compilation/runtime failures. Consider using Log4j 2.17.1 (last Java 8 compatible release that patches all critical CVEs) instead. See [PR #25 Analysis](pr-25-cve-2017-5645-analysis.md) for full details.

**Additional Finding — Missing Log4j Dependencies**:
- `products/src/main/java/.../services/ProductFilterService.java` imports `org.apache.logging.log4j.LogManager` and `Logger` but `products/pom.xml` has no Log4j dependency declared.
- `conductors/src/main/java/.../services/ProductFilterService.java` imports `org.apache.logging.log4j.LogManager` and `Logger` but `conductors/pom.xml` has no Log4j dependency declared.

---

### 3. Cartesian Product Query — Severity: Low

**File**: `instruments/src/main/java/.../repositories/FindInstrumentRepositoryImpl.java`, line ~26

```java
entityManager.createNativeQuery("SELECT * FROM instruments_for_sale, instruments_for_sale_chicago").getResultList();
```

**Risk**: Cross-join without WHERE clause produces 131 × 86 = 11,266 rows. Denial of service vector if triggered repeatedly.

**Trigger**: Only executed when location is "Chicago".

---

### 4. Commented-Out External HTTP Call with Hardcoded URL — Severity: Low

**File**: `shop/src/main/java/.../controllers/HomeController.java`, line ~100 (commented out)

```java
// URL url = new URL("https://example.lambda-url.us-west-1.on.aws/?userId=" + userId);
```

**Risk**: If uncommented, this would make HTTP calls to a hardcoded external AWS Lambda URL with user IDs. The URL may no longer be controlled by the project maintainers.

---

### 5. Empty Catch Blocks Hiding Errors — Severity: Low

**Files**: `products/...ProductFilterService.java`, `conductors/...ProductFilterService.java`, `conductors/...ConductorsController.java`

**Risk**: Silently swallowing exceptions prevents detection of runtime errors, security incidents, or data corruption. Over 30 instances across the codebase.

---

### 6. Static Mutable State — Severity: Low

**File**: `shop/src/main/java/.../controllers/HomeController.java`, lines ~25-26

```java
public static long s_coloradoLatency;
public static long s_utahLatency;
```

**Risk**: Static mutable fields in a multi-threaded web server can lead to race conditions and data corruption.

---

## Security Patterns in Use

| Pattern | Implementation | Location |
|---------|---------------|----------|
| Circuit Breaker | Hystrix fallbacks prevent cascading failures | shop module repos |
| Health Checks | `/healthcheck` endpoints for container monitoring | All services |
| JPA Repository | Standard Spring Data patterns (excluding custom impl) | instruments, stock |
| Spring Security | Not implemented | — |
| Input Validation | Minimal (no `@Valid`, no request validation) | All services |
| CORS | Not configured | All services |
| Authentication | Not implemented | All services |

## Related Documents

- [Technical Debt Report](../technical-debt-report.md)
- [PR #25 CVE-2017-5645 Analysis](pr-25-cve-2017-5645-analysis.md)
- [Code Metrics](code-metrics.md) | [Dependency Analysis](dependency-analysis.md)

---

[← Back to README](../README.md)
