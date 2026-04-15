# Complexity Analysis

## High Complexity Hotspots

### 1. ProductFilterService.filterAllProducts() — Products Module
- **File**: `products/src/main/java/.../services/ProductFilterService.java` lines 19-168
- **Cyclomatic Complexity**: High
- **Description**: Contains 100+ sequential method calls (`myCoolFunction*()` variants) before returning products. Each method performs `Thread.sleep()` with varying durations.
- **Key Concern**: `locationLookup11()` → `myCoolFunction234234234()` introduces 966-1166ms latency for Colorado location
- **Purpose**: Intentional workshop simulation for APM latency debugging

### 2. Exercises.checkExercise() — Shop Module
- **File**: `shop/src/main/java/.../Exercises.java` lines 52-95
- **Cyclomatic Complexity**: High (15-case switch statement)
- **Description**: Evaluates 15 different exercise conditions with varied logic:
  - External HTTP calls (exercises 1, 2)
  - File I/O (exercises 2, 3, 11, 12)
  - String matching (exercises 6, 7, 8, 9, 10, 13, 14)
  - Latency comparison (exercises 4, 15)
  - External permission check (exercise 5)

### 3. HomeController.getProductsAllLocations() — Shop Module
- **File**: `shop/src/main/java/.../controllers/HomeController.java` lines 32-77
- **Cyclomatic Complexity**: Medium
- **Description**: Handles null parameter defaults, permission checks, multiple service calls, and location-specific latency tracking

### 4. InstrumentService.getInstruments() — Instruments Module
- **File**: `instruments/src/main/java/.../services/InstrumentService.java` lines 29-51
- **Cyclomatic Complexity**: Medium
- **Description**: Branching logic for locale filtering, Chicago-specific query path, and null checking

### 5. ProductFilterService — Conductors Module
- **File**: `conductors/src/main/java/.../services/ProductFilterService.java`
- **Cyclomatic Complexity**: Medium (most methods commented out)
- **Description**: Mirrors Products module structure but with most latency simulation commented out

## Code Duplication Analysis

### High Duplication

| Pattern | Source | Duplicate | Similarity |
|---------|--------|-----------|------------|
| Product model | `products/.../model/Product.java` | `conductors/.../model/Product.java` | ~100% |
| ProductService/ConductorsService | `products/.../services/ProductService.java` | `conductors/.../services/ConductorsService.java` | ~100% |
| ProductFilterService | `products/.../services/ProductFilterService.java` | `conductors/.../services/ProductFilterService.java` | ~90% (conductors has most code commented out) |
| FilteredInstrument/FilteredProducts | `instruments/.../model/FilteredInstrument.java` | `conductors/.../model/FilteredProducts.java` | ~95% |
| InvalidLocaleException | 3 copies | shop, instruments, conductors | 100% |
| myCoolFunction() variants | ~30 methods in ProductFilterService | Each is a copy with slight sleep variation | ~95% |

### Method-Level Duplication
The `ProductFilterService` class in the products module contains approximately 30 methods that follow the identical pattern:
```java
private void myCoolFunctionN(String location) {
    int sleepy = N;
    try { Thread.sleep(sleepy); } catch (Exception e) { }
}
```

## Maintainability Concerns

| Concern | Severity | Module(s) |
|---------|----------|-----------|
| No dependency injection in controllers | Medium | products, conductors (use `new` instead of `@Autowired`) |
| Static mutable state | Medium | shop (`HomeController.s_coloradoLatency`, `s_utahLatency`) |
| Singleton with file I/O | Medium | shop (`Exercises`, `PropertiesUpdater`) |
| Mixed logging frameworks | Low | SLF4J + Log4j2 across modules |
| Inconsistent code style | Low | Tabs vs spaces, varying indentation |

## Cross-References

- [Code Metrics](code-metrics.md) | [Dependency Analysis](dependency-analysis.md) | [Security Patterns](security-patterns.md)
- [Maintenance Burden](../technical-debt/maintenance-burden.md)
