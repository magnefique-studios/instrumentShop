# Validation Criteria

[← Back to README](../README.md) | [Component Order](component-order.md) | [Test Specifications](test-specifications.md)

## Criteria for Successful Migration

### 1. Build Validation
- [ ] All 7 modules compile without errors
- [ ] All modules produce valid JAR artifacts
- [ ] Docker images build successfully for all services
- [ ] No `javax.*` imports remain in modules targeting Spring Boot 3.x (should be `jakarta.*`)

### 2. Runtime Validation
- [ ] All services start without exceptions in Docker Compose
- [ ] Health check endpoints return HTTP 200 for all services
- [ ] PostgreSQL connection successful from instruments service
- [ ] H2 in-memory database initializes with synthetic data in stock service

### 3. Functional Validation
- [ ] Main page (`GET /`) returns HTML with products and instruments
- [ ] Products endpoint returns 5 products for any location
- [ ] Conductors endpoint returns 5 products (internal Oregon exception handled gracefully)
- [ ] Stock endpoint returns 5 stock records
- [ ] Instruments endpoint returns instruments from PostgreSQL
- [ ] Utah routing correctly delegates to conductors service
- [ ] Colorado latency injection still produces ~1 second delay
- [ ] Exercise scoring endpoint returns valid JSON

### 4. Circuit Breaker Validation (Post-Hystrix Migration)
- [ ] Fallback triggered when downstream service unavailable
- [ ] Fallback returns empty collection (not error)
- [ ] Circuit opens after threshold failures
- [ ] Circuit closes after recovery period

### 5. Data Integrity
- [ ] `instruments_for_sale` table accessible with all records
- [ ] `instruments_for_sale_chicago` table accessible with all records
- [ ] Stock synthetic data (5 records) generated on startup
- [ ] Product catalog (5 products) available via in-memory DAO

### 6. Security Validation
- [ ] Log4j upgraded to 2.24.x+ in test module (CVE-2021-44228 remediated)
- [ ] SQL injection in `FindInstrumentRepositoryImpl` replaced with parameterized query
- [ ] No new security vulnerabilities introduced during migration

### 7. Compatibility Validation
- [ ] All REST API contracts unchanged (same endpoints, parameters, response formats)
- [ ] Endpoint typos preserved if intentional (`/instrumemnts`, `/insruments`) or coordinated fix
- [ ] Thymeleaf templates render correctly with updated Spring Boot
- [ ] Docker Compose orchestration works with updated service images

---

## Related Documents

- [Test Specifications](test-specifications.md) — Specific test cases
- [Component Order](component-order.md) — Migration sequence
- [Remediation Plan](../technical-debt/remediation-plan.md) — Detailed upgrade steps
