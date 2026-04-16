# Complexity Analysis

[← Back to README](../README.md) | [Code Metrics](code-metrics.md) | [Dependency Analysis](dependency-analysis.md) | [Security Patterns](security-patterns.md)

## Module Complexity Rankings

| Rank | Module | Complexity | Key Factors |
|------|--------|-----------|-------------|
| 1 | **shop** | High | Central orchestrator, Hystrix circuit breakers, multi-service integration, exercise engine, property file I/O, latency tracking |
| 2 | **products** | Medium | ProductFilterService has ~30+ methods with Thread.sleep patterns and deliberate latency injection |
| 3 | **conductors** | Medium | Similar structure to products but with filter calls commented out; hardcoded Oregon locale |
| 4 | **instruments** | Medium | JPA + PostgreSQL, custom repository, location-based query routing, cartesian product issue |
| 5 | **annotator** | Medium | AST parsing/modification with JavaParser, file I/O, visitor pattern |
| 6 | **stock** | Low | Simple CRUD with in-memory H2, synthetic data generation |
| 7 | **test** | Low | Linear HTTP traffic generation script |

## Highest Complexity Classes

### ProductFilterService (products) — ~600 LOC
- **Cyclomatic Complexity**: High
- ~30 `myCoolFunction*()` methods, many with Thread.sleep
- `locationLookup11()` has nested conditional calling `myCoolFunction234234234()`
- `myCoolFunction234234234()` uses Random for variable-length sleep
- Main `filterAllProducts()` calls these sequentially with no clear business purpose beyond latency simulation

### Exercises (shop) — ~200 LOC
- **Cyclomatic Complexity**: High
- `checkExercise()` has 15-case switch statement
- Multiple helper methods with conditional HTTP calls, property reads, and latency comparisons
- Singleton with finalize() override (deprecated pattern)

### HomeController (shop) — ~120 LOC
- **Cyclomatic Complexity**: Medium
- Multiple request parameters with null checks
- Location-based latency tracking with nested conditions
- Permission check delegation

### InstrumentService (instruments) — ~60 LOC
- **Cyclomatic Complexity**: Medium
- Location-based branching: Oregon → exception, Chicago → cartesian query, default → standard query
- Nested null/type checks on query results

### OpenTelemetryAnnotator (annotator) — ~120 LOC
- **Cyclomatic Complexity**: Medium
- Visitor pattern with conditional annotation logic
- File I/O with rename operations
- Name-based method filtering (main, set*, health*, get*)

## Code Smell Summary

| Smell | Count | Severity | Location |
|-------|-------|----------|----------|
| Empty catch blocks | 60+ | Low | ProductFilterService (both modules) |
| Duplicate code | 5 classes | Low | products ↔ conductors duplication |
| God class | 1 | Low | ProductFilterService (~600 LOC) |
| Magic numbers | 10+ | Low | Exercises (180, 999, 966), ProductFilterService |
| Dead code (commented) | 15+ blocks | Low | Multiple modules |
| finalize() usage | 1 | Low | Exercises.java (deprecated since Java 9) |

---

## Related Documents

- [Code Metrics](code-metrics.md) — Quantitative metrics
- [Maintenance Burden](../technical-debt/maintenance-burden.md) — Impact of complexity
- [Patterns](../architecture/patterns.md) — Design patterns and anti-patterns
