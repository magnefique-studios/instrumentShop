# Test Specifications

## Service-Level Test Cases

### Stock Service Tests

| Test Case | Endpoint | Method | Expected Result |
|-----------|----------|--------|-----------------|
| T-STOCK-01 | `/legacy` | GET | HTTP 200, returns 5 stock items |
| T-STOCK-02 | `/legacy` | GET | Response contains productId, sku, amountAvailable fields |
| T-STOCK-03 | `/insruments` | GET | HTTP 200, returns instrument stock list |
| T-STOCK-04 | `/healthcheck` | GET | HTTP 200, "OK" |
| T-STOCK-05 | H2 Database | Startup | DataGenerator creates 5 records with correct data |

### Instruments Service Tests

| Test Case | Endpoint | Method | Expected Result |
|-----------|----------|--------|-----------------|
| T-INST-01 | `/instruments?location=California` | GET | HTTP 200, returns all instruments |
| T-INST-02 | `/instruments?location=Oregon` | GET | HTTP 200, returns empty list |
| T-INST-03 | `/instruments?location=Chicago` | GET | HTTP 200, returns instruments (cross-table query executes) |
| T-INST-04 | `/stocks` | GET | HTTP 200, returns instrument stocks |
| T-INST-05 | `/healthcheck` | GET | HTTP 200, "OK" |
| T-INST-06 | PostgreSQL | Startup | Connection successful, tables exist |

### Products Service Tests

| Test Case | Endpoint | Method | Expected Result |
|-----------|----------|--------|-----------------|
| T-PROD-01 | `/products?location=California` | GET | HTTP 200, returns 5 products |
| T-PROD-02 | `/products?location=Colorado` | GET | HTTP 200, returns 5 products (with latency) |
| T-PROD-03 | `/products/healthcheck` | GET | HTTP 200, "OK" |
| T-PROD-04 | Response Schema | GET | Products contain id, name, description, price |

### Conductors Service Tests

| Test Case | Endpoint | Method | Expected Result |
|-----------|----------|--------|-----------------|
| T-COND-01 | `/conductors?location=Utah` | GET | HTTP 200, returns 5 products |
| T-COND-02 | `/conductors/healthcheck` | GET | HTTP 200, "OK" |
| T-COND-03 | Response Schema | GET | Products contain id, name, description, price |

### Shop Service Tests

| Test Case | Endpoint | Method | Expected Result |
|-----------|----------|--------|-----------------|
| T-SHOP-01 | `/` | GET | HTTP 200, HTML page with products and instruments |
| T-SHOP-02 | `/?location=Utah` | GET | HTTP 200, products from Conductors service |
| T-SHOP-03 | `/?location=California` | GET | HTTP 200, products from Products service |
| T-SHOP-04 | `/products` | GET | HTTP 200, JSON product list |
| T-SHOP-05 | `/score?exercise=0` | GET | HTTP 200, returns exercise scores HashMap |
| T-SHOP-06 | `/healthcheck` | GET | HTTP 200, "OK" |

## Integration Test Cases

| Test Case | Flow | Expected Result |
|-----------|------|-----------------|
| T-INT-01 | Shop → Products | Product data returned and displayed |
| T-INT-02 | Shop → Conductors (Utah) | Utah routing returns Conductors product data |
| T-INT-03 | Shop → Stock | Stock data merged with product data |
| T-INT-04 | Shop → Instruments | Instrument data displayed |
| T-INT-05 | Instruments → PostgreSQL | Database queries execute correctly |
| T-INT-06 | All services health | All /healthcheck endpoints return 200 |

## Post-Migration Verification

| Test Case | Description | Expected Result |
|-----------|-------------|-----------------|
| T-MIG-01 | javax → jakarta migration | No javax.persistence or javax.validation imports remain |
| T-MIG-02 | Hystrix → Resilience4j | Circuit breaker fallbacks work correctly |
| T-MIG-03 | Log4j → SLF4J/Logback | Logging works, no Log4j 2.6.1 on classpath |
| T-MIG-04 | Java 17+ compatibility | All modules compile and run on Java 17+ |
| T-MIG-05 | Spring Boot 3.x | All modules use Spring Boot 3.x parent |

## Cross-References

- [Component Order](component-order.md) | [Validation Criteria](validation-criteria.md)
