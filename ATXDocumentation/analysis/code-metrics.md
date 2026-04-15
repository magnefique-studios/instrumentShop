# Code Metrics

## Lines of Code by Module

| Module | Java LOC | Classes/Interfaces | Spring Boot Version | Java Version |
|--------|----------|--------------------|---------------------|-------------|
| shop | 1,492 | 18 | 1.5.19 | 8 |
| conductors | 1,039 | 8 | 3.2.2 | 17 |
| products | 855 | 6 | 3.2.2 | 17 |
| instruments | 590 | 13 | 2.7.5 | 17 |
| annotator | 387 | 5 | N/A | 17 |
| stock | 256 | 9 | 2.1.3 | 8 |
| test | 186 | 1 | N/A | 11 |
| **Total** | **4,805** | **60** | | |

## Additional Source Files

| Type | Count | Total Lines |
|------|-------|-------------|
| XML (pom.xml, etc.) | ~10 | ~800 |
| Properties files | 5 | ~20 |
| SQL scripts | 2 | ~40,000 |
| Docker/YAML | ~6 | ~200 |
| Shell scripts | ~8 | ~200 |

## Method Counts by Key Classes

| Class | Module | Public Methods | Private Methods | Total |
|-------|--------|---------------|-----------------|-------|
| `ProductFilterService` | products | 1 | ~30 | ~31 |
| `ProductFilterService` | conductors | 1 | ~30 | ~31 |
| `HomeController` | shop | 4 | 1 | 5 |
| `Exercises` | shop | 8 | 0 | 8 |
| `InstrumentService` | instruments | 1 | 0 | 1 |
| `PropertiesUpdater` | shop | 5 | 2 | 7 |
| `Instrument` (shop) | shop | 12 | 1 | 13 |

## Code Quality Indicators

| Indicator | Status | Notes |
|-----------|--------|-------|
| Test Coverage | ⚠️ Very Low | Only 1 test file found (`ShopTest.java`) |
| Code Duplication | ⚠️ High | Products ↔ Conductors modules nearly identical |
| Dead Code | ⚠️ Significant | Extensive commented-out blocks across modules |
| Naming Consistency | ⚠️ Poor | `myCoolFunction*()` variants, typos in endpoints |
| Documentation | ⚠️ Minimal | Very few Javadoc comments; inline comments mostly commented-out code |
| Spring DI Usage | ⚠️ Mixed | Products/Conductors instantiate services with `new` instead of DI |

## Cross-References

- [Complexity Analysis](complexity-analysis.md) | [Dependency Analysis](dependency-analysis.md) | [Security Patterns](security-patterns.md)
