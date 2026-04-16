# Interfaces (REST Endpoints)

[← Back to README](../README.md) | [Program Structure](program-structure.md) | [Data Models](data-models.md) | [API Reference](api-reference.md)

## Shop Service (port 8010)

| Method | Endpoint | Parameters | Returns | Controller |
|--------|----------|-----------|---------|------------|
| GET | `/` | `name` (optional), `location` (optional), `userid` (optional) | Thymeleaf "index" template | `HomeController` |
| GET | `/score` | `exercise` (optional), `data` (optional) | `HashMap<String, String>` JSON | `HomeController` |
| GET | `/healthcheck` | none | "HTTP Status OK (CODE 200)" | `HomeController` |
| GET | `/products` | `location` (optional) | `List<Product>` JSON | `ProductResource` |

## Products Service (port 8020)

| Method | Endpoint | Parameters | Returns | Controller |
|--------|----------|-----------|---------|------------|
| GET | `/products` | `location` (required) | `List<Product>` JSON | `ProductController` |
| GET | `/products/healthcheck` | none | "HTTP Status OK (CODE 200)" | `ProductController` |

## Conductors Service (port 8050)

| Method | Endpoint | Parameters | Returns | Controller |
|--------|----------|-----------|---------|------------|
| GET | `/conductors` | `location` (required) | `List<Product>` JSON | `ConductorsController` |
| GET | `/conductors/healthcheck` | none | "HTTP Status OK (CODE 200)" | `ConductorsController` |

## Instruments Service (port 8040)

| Method | Endpoint | Parameters | Returns | Controller |
|--------|----------|-----------|---------|------------|
| GET | `/instruments` | `location` (required, default "California") | `List<Instrument>` JSON | `InstrumentResource` |
| GET | `/stocks` | none | `List<Stock>` JSON | `InstrumentResource` |
| GET | `/healthcheck` | none | "HTTP Status OK (CODE 200)" | `InstrumentResource` |

## Stock Service (port 8030)

| Method | Endpoint | Parameters | Returns | Controller |
|--------|----------|-----------|---------|------------|
| GET | `/legacy` | none | `List<Stock>` JSON | `StockResource` |
| GET | `/insruments` | none | `List<Stock>` JSON | `StockResource` |
| GET | `/healthcheck` | none | "HTTP Status OK (CODE 200)" | `StockResource` |

---

## Internal Repository Interfaces

### FindInstrumentRepository
```java
public interface FindInstrumentRepository {
    Object findInstruments();                    // Cartesian product query
    Instrument findInstrumentByID(String id);    // SQL injection vulnerable
}
```

### InstrumentRepository
```java
public interface InstrumentRepository extends JpaRepository<Instrument, String>, FindInstrumentRepository {}
```

### StockRepository / InstrumentStocksRepository
```java
public interface StockRepository extends CrudRepository<Stock, String> {}
public interface InstrumentStocksRepository extends CrudRepository<Stock, String> {}
```

---

## Related Documents

- [API Reference](api-reference.md) — Full HTTP API specifications
- [Data Models](data-models.md) — Request/response data structures
- [Sequence Diagrams](../diagrams/behavioral/sequence-diagrams.md) — Request flow visualizations
