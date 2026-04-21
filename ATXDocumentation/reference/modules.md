# Module Organization and Dependencies

## Maven Module Structure

The project uses a multi-module Maven POM structure. The root POM at `pom.xml` defines all 7 modules but each module independently defines its own Spring Boot parent and dependencies.

| Module | Artifact ID | Packaging | Spring Boot | Java |
|--------|------------|-----------|-------------|------|
| shop | javashop.shop | jar | 1.5.19.RELEASE | 1.8 |
| stock | javashop.stock | jar | 2.1.3.RELEASE | 1.8 |
| products | javashop.products | jar | 3.2.2 | 17 |
| instruments | javashop.instruments | jar | 2.7.5 | 17 |
| conductors | javashop.conductors | jar | 3.2.2 | 17 |
| annotator | annotator | jar | N/A | 17 |
| test | javashop.tester | jar | N/A | 11 |

## Inter-Module Dependencies

There are **no compile-time Maven dependencies between modules**. All inter-service communication is performed at runtime via HTTP REST calls. This is a characteristic of a microservices architecture where services are independently deployable.

## Module Responsibilities

- **shop**: User-facing frontend, service orchestration, exercise system
- **products**: Product catalog (in-memory)
- **conductors**: Filtered product catalog for Utah (in-memory, hardcoded to Oregon)
- **stock**: Stock/inventory management (H2 in-memory DB)
- **instruments**: Musical instrument data (PostgreSQL)
- **annotator**: Developer tool for OTel annotation
- **test**: Traffic generation for load testing (Java 11, log4j 2.24.3, commons-httpclient 3.1)

## Related Documents

- [Program Structure](program-structure.md)
- [Architecture → Components](../architecture/components.md)
- [Architecture → Dependencies](../architecture/dependencies.md)

---

[← Back to README](../README.md)
