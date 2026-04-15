# Validation Criteria

## Criteria for Successful Migration

### 1. Build Validation
- [ ] All modules compile without errors using `mvn clean package -DskipTests`
- [ ] No deprecated API warnings from removed javax.* packages
- [ ] All Spring Boot modules produce runnable fat JARs
- [ ] Docker images build successfully for all services

### 2. Runtime Validation
- [ ] All services start without errors in Docker Compose
- [ ] All health check endpoints return HTTP 200
- [ ] PostgreSQL connection established by Instruments service
- [ ] H2 database initialized by Stock service with 5 records
- [ ] No ClassNotFoundException or NoClassDefFoundError at runtime

### 3. Functional Validation

#### Shop Service
- [ ] Main page (`/`) renders with products and instruments
- [ ] Location=Utah routes to Conductors service
- [ ] Location=California routes to Products service
- [ ] Score endpoint (`/score`) returns exercise results
- [ ] Products endpoint (`/products`) returns merged product/stock data

#### Products Service
- [ ] `GET /products?location=California` returns 5 products
- [ ] Product data contains id, name, description, price fields
- [ ] Colorado location triggers latency simulation (if preserved)

#### Stock Service
- [ ] `GET /legacy` returns 5 stock records
- [ ] Stock data contains productId, sku, amountAvailable fields

#### Instruments Service
- [ ] `GET /instruments?location=California` returns instrument list
- [ ] `GET /instruments?location=Oregon` returns empty list
- [ ] `GET /stocks` returns instrument stock data

#### Conductors Service
- [ ] `GET /conductors?location=Utah` returns 5 products

### 4. Security Validation
- [ ] Log4j 2.6.1 no longer on any classpath (verified via dependency tree)
- [ ] No javax.persistence or javax.validation imports in source code
- [ ] SQL injection in FindInstrumentRepositoryImpl fixed (parameterized query)

### 5. Inter-Service Communication Validation
- [ ] Shop successfully calls Products service and receives response
- [ ] Shop successfully calls Stock service and receives response
- [ ] Shop successfully calls Instruments service and receives response
- [ ] Shop routes Utah requests to Conductors service
- [ ] Circuit breaker (Resilience4j) fallbacks work on service failure

### 6. Data Integrity Validation
- [ ] Product data matches original hardcoded catalog (5 items)
- [ ] Stock data matches original synthetic data (5 records)
- [ ] Instrument data loaded from SQL scripts into PostgreSQL
- [ ] No data loss or corruption during migration

### 7. Compatibility Matrix

| Module | Java Version | Spring Boot | Build | Startup | Endpoints |
|--------|-------------|-------------|-------|---------|-----------|
| shop | ≥17 | ≥3.2 | ✅ | ✅ | ✅ |
| products | ≥17 | ≥3.2 | ✅ | ✅ | ✅ |
| stock | ≥17 | ≥3.2 | ✅ | ✅ | ✅ |
| instruments | ≥17 | ≥3.2 | ✅ | ✅ | ✅ |
| conductors | ≥17 | ≥3.2 | ✅ | ✅ | ✅ |
| annotator | ≥17 | N/A | ✅ | ✅ | N/A |
| test | ≥17 | N/A | ✅ | ✅ | N/A |

## Cross-References

- [Component Order](component-order.md) | [Test Specifications](test-specifications.md)
- [Remediation Plan](../technical-debt/remediation-plan.md)
