# Maintenance Burden

## Areas Requiring Significant Maintenance Attention

### 1. Framework Version Fragmentation
- **Impact**: High
- **Description**: The codebase runs 4 different Spring Boot versions (1.5.19, 2.1.3, 2.7.5, 3.2.2) and 3 different Java versions (8, 11, 17) across its modules. This fragmentation means:
  - Each module may require different build toolchains
  - Security patches must be tracked independently per module
  - Shared libraries/patterns cannot be standardized
  - Developer onboarding requires familiarity with multiple framework generations

### 2. Code Duplication Between Products and Conductors
- **Impact**: Medium
- **Description**: The `conductors` module is a near-complete copy of the `products` module:
  - `ConductorsService` has identical product data as `ProductService`
  - `ProductFilterService` exists in both modules with the same structure (though conductors has most methods commented out)
  - `Product` model is duplicated
  - `FilteredProducts` mirrors `FilteredInstrument` from instruments module
- **Risk**: Bug fixes or changes must be applied to both modules

### 3. Workshop/Training Code in Production Codebase
- **Impact**: Medium
- **Description**: The codebase contains intentional defects and training code that would be maintenance liabilities in a production setting:
  - `ProductFilterService` in products module contains ~100+ sequential `myCoolFunction*()` calls with `Thread.sleep()` — intentionally designed for latency debugging workshops
  - `Exercises.java` implements a 15-exercise scoring system with external HTTP calls
  - `PropertiesUpdater.java` manages file-based exercise scores
  - `getMyInt()` always returns 999, causing `myCoolFunction234234234()` to introduce 966-1166ms latency for Colorado location

### 4. Inconsistent Error Handling
- **Impact**: Medium
- **Description**: Error handling patterns vary significantly across modules:
  - Shop: Hystrix fallbacks return empty collections silently
  - Instruments: Some exceptions are caught and logged, others propagated
  - Products: All exceptions in ProductFilterService are silently swallowed with empty catch blocks
  - Stock: `@ExceptionHandler` annotation used for `StockNotFoundException`
  - `InstrumentStocksService.getStock()` returns `null` instead of throwing an exception

### 5. Dead/Commented Code
- **Impact**: Low
- **Description**: Significant amounts of commented-out code throughout:
  - `HomeController.checkIfRestricted()` — entire HTTP client logic commented out (lines 98-128)
  - `StockService.getStock()` — commented out (lines 27-30)
  - `InstrumentStocksService.getStock()` — body commented out, returns null
  - Datadog agent configuration in `docker-compose.yml` — fully commented out
  - Multiple Dockerfile entrypoint references in `Instrumented - Dockerfile Entrypoints.txt`
  - Conductors `ProductFilterService` — most method bodies are commented out

### 6. Hardcoded Values and Configuration
- **Impact**: Medium
- **Description**: Multiple hardcoded values that should be externalized:
  - PostgreSQL credentials in `docker-compose.yml`: `instruments/instruments`
  - Service discovery via hardcoded hostnames and ports in `application.properties`
  - `ConductorsController` hardcodes `location = "Oregon"` overriding the user-provided parameter
  - `ProductService` in products module uses hardcoded product data
  - `DataGenerator` in stock module uses hardcoded stock data

### 7. Missing Test Coverage
- **Impact**: Medium
- **Description**: Only one test file exists in the entire codebase:
  - `shop/src/test/java/.../ShopTest.java` — likely a basic Spring Boot context test
  - Stock module has Cucumber dependencies but test files were not found in source paths
  - Products, instruments, and conductors have no test files
  - The `test` module is a traffic generator, not a test suite

## Cross-References

- [Technical Debt Summary](summary.md)
- [Outdated Components](outdated-components.md)
- [Remediation Plan](remediation-plan.md)
- [Architectural Patterns](../architecture/patterns.md)
