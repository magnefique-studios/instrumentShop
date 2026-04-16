# Test Specifications

[← Back to README](../README.md) | [Component Order](component-order.md) | [Validation Criteria](validation-criteria.md)

## Test Cases for Migration Validation

### TC-1: Shop Health Check
- **Endpoint**: `GET http://shop:8010/healthcheck`
- **Expected**: HTTP 200 with body containing "200"
- **Validates**: Shop service starts and responds

### TC-2: Products Service
- **Endpoint**: `GET http://products:8020/products?location=California`
- **Expected**: HTTP 200, JSON array with 5 product objects (Widget, Sprocket, Anvil, Cogs, Multitool)
- **Validates**: Products service returns hardcoded product catalog

### TC-3: Conductors Service
- **Endpoint**: `GET http://conductors:8050/conductors?location=Utah`
- **Expected**: HTTP 200, JSON array with 5 product objects
- **Validates**: Conductors returns products despite internal Oregon exception

### TC-4: Stock Service
- **Endpoint**: `GET http://stock:8030/legacy`
- **Expected**: HTTP 200, JSON array with 5 stock records (productIds 1-5)
- **Validates**: H2 data generation and stock retrieval

### TC-5: Instruments Service
- **Endpoint**: `GET http://instruments:8040/instruments?location=California`
- **Expected**: HTTP 200, JSON array of instruments from PostgreSQL
- **Validates**: JPA + PostgreSQL connectivity and query execution

### TC-6: Main Page Default
- **Endpoint**: `GET http://shop:8010/`
- **Expected**: HTTP 200, HTML page with products and instruments
- **Validates**: End-to-end flow through shop → products + stock + instruments

### TC-7: Utah Routing
- **Endpoint**: `GET http://shop:8010/?location=Utah`
- **Expected**: HTTP 200, HTML page with products (routed through conductors)
- **Validates**: ProductRepo Utah → conductors routing logic

### TC-8: Colorado Latency
- **Endpoint**: `GET http://products:8020/products?location=Colorado`
- **Expected**: HTTP 200 with response time > 900ms
- **Validates**: Deliberate latency injection in ProductFilterService

### TC-9: Oregon Exception Handling
- **Endpoint**: `GET http://instruments:8040/instruments?location=Oregon`
- **Expected**: HTTP 200 (exception caught, returns instruments)
- **Validates**: InvalidLocaleException handling in InstrumentService

### TC-10: Exercise Scoring
- **Endpoint**: `GET http://shop:8010/score?exercise=0`
- **Expected**: HTTP 200, JSON object with exercise scores
- **Validates**: Properties file reading and score retrieval

### TC-11: Hystrix Fallback
- **Scenario**: Stop instruments service, then `GET http://shop:8010/`
- **Expected**: HTTP 200 with empty instruments list (fallback triggered)
- **Validates**: Hystrix circuit breaker fallback to empty map

### TC-12: Instrument Stocks
- **Endpoint**: `GET http://instruments:8040/stocks`
- **Expected**: HTTP 200, JSON array of stock records
- **Validates**: InstrumentStocksService and repository

---

## Related Documents

- [Validation Criteria](validation-criteria.md) — Success criteria for migration
- [Component Order](component-order.md) — Migration sequence
- [API Reference](../reference/api-reference.md) — Full endpoint specifications
