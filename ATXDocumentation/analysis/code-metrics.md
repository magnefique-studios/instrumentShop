# Code Metrics

[← Back to README](../README.md) | [Complexity Analysis](complexity-analysis.md) | [Dependency Analysis](dependency-analysis.md) | [Security Patterns](security-patterns.md)

## Lines of Code by Module

| Module | Java Files | Approx LOC | Main Classes | Test Classes |
|--------|-----------|------------|--------------|-------------|
| shop | 18 | ~1,800 | 17 | 1 (empty) |
| products | 6 | ~700 | 6 | 0 |
| conductors | 7 | ~700 | 7 | 0 |
| instruments | 13 | ~650 | 13 | 0 |
| stock | 9 | ~350 | 9 | 0 |
| annotator | 4 | ~400 | 4 | 0 |
| test | 1 | ~180 | 1 | 0 |
| **Total** | **58** | **~4,805** | **57** | **1** |

## Class Count by Type

| Type | Count | Examples |
|------|-------|---------|
| @SpringBootApplication | 5 | JavaShopApp, ProductServiceApplication, etc. |
| @Controller / @RestController | 6 | HomeController, ProductController, etc. |
| @Service | 6 | ProductService (shop), InstrumentService, StockService, etc. |
| @Component | 4 | InstrumentRepo, ProductRepo, StockRepo, DataGenerator |
| @Entity | 3 | Instrument, Stock (instruments), Stock (stock) |
| Interface | 4 | FindInstrumentRepository, InstrumentRepository, etc. |
| Exception classes | 6 | InvalidLocaleException (×4), InstrumentNotFoundException, StockNotFoundException |
| DTO classes | 3 | InstrumentDTO, ProductDTO, StockDTO |
| POJO/Model | 8 | Product (×3), User, FilteredProducts, FilteredInstrument, etc. |
| Utility/Tool | 5 | Exercises, PropertiesUpdater, DirExplorer, OpenTelemetryAnnotator, GenerateTraffic |

## Method Count by Module

| Module | Public Methods | Private Methods | Total |
|--------|---------------|----------------|-------|
| shop | ~65 | ~15 | ~80 |
| products | ~15 | ~35 | ~50 |
| conductors | ~15 | ~35 | ~50 |
| instruments | ~45 | ~5 | ~50 |
| stock | ~20 | ~2 | ~22 |
| annotator | ~12 | ~3 | ~15 |
| test | ~1 | ~0 | ~1 |

## Test Coverage

- **ShopTest.java**: Single test file with all test methods commented out — effectively **0% test coverage**
- **No other test classes exist** in any module
- Stock module has Cucumber dependencies but no feature files or step definitions found in source

---

## Related Documents

- [Complexity Analysis](complexity-analysis.md) — Module complexity rankings
- [Dependency Analysis](dependency-analysis.md) — Dependency mapping
