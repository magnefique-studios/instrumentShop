# Interfaces and APIs

## REST API Endpoints

### Shop Service (Port 8010)

| Method | Path | Parameters | Response | Handler |
|--------|------|-----------|----------|---------|
| GET | `/` | `name` (opt), `location` (opt), `userid` (opt) | Thymeleaf HTML page | `HomeController.getProductsAllLocations()` |
| GET | `/score` | `exercise` (opt), `data` (opt) | `HashMap<String, String>` JSON | `HomeController.getScores()` |
| GET | `/products` | `location` (opt) | `List<Product>` JSON | `ProductResource.getProducts()` |
| GET | `/healthcheck` | none | HTTP 200 OK | `HomeController.healthCheck()` |

### Products Service (Port 8020)

| Method | Path | Parameters | Response | Handler |
|--------|------|-----------|----------|---------|
| GET | `/products` | `location` (required) | `List<Product>` JSON | `ProductController.getProductsByLocation()` |
| GET | `/products/healthcheck` | none | HTTP 200 OK | `ProductController.healthCheck()` |

### Stock Service (Port 8030)

| Method | Path | Parameters | Response | Handler |
|--------|------|-----------|----------|---------|
| GET | `/legacy` | none | `List<Stock>` JSON | `StockResource.getStocks()` |
| GET | `/insruments` | none | `List<Stock>` JSON | `StockResource.getInstrumentStocks()` |
| GET | `/healthcheck` | none | HTTP 200 OK | `StockResource.healthCheck()` |

**Note**: `/insruments` is a typo — should be `/instruments`

### Instruments Service (Port 8040)

| Method | Path | Parameters | Response | Handler |
|--------|------|-----------|----------|---------|
| GET | `/instruments` | `location` (default: "California") | `List<Instrument>` JSON | `InstrumentResource.getInstruments()` |
| GET | `/stocks` | none | `List<Stock>` JSON | `InstrumentResource.getInstrumentStocks()` |
| GET | `/healthcheck` | none | HTTP 200 OK | `InstrumentResource.healthCheck()` |

### Conductors Service (Port 8050)

| Method | Path | Parameters | Response | Handler |
|--------|------|-----------|----------|---------|
| GET | `/conductors` | `location` (required) | `List<Product>` JSON | `ConductorsController.getProductsByLocation()` |
| GET | `/conductors/healthcheck` | none | HTTP 200 OK | `ConductorsController.healthCheck()` |

## Repository Interfaces

### Spring Data CrudRepository Implementations

| Interface | Entity | ID Type | Module |
|-----------|--------|---------|--------|
| `StockRepository` | `Stock` | `String` | stock |
| `InstrumentStocksRepository` | `Stock` | `String` | stock |
| `InstrumentRepository` | `Instrument` | `Long` | instruments |
| `InstrumentStocksRepository` | `Stock` | `String` | instruments |

### Custom Repository Interface

```java
// instruments/src/main/java/.../repositories/FindInstrumentRepository.java
public interface FindInstrumentRepository {
    Object findInstruments();               // Returns cross-table query results
    Instrument findInstrumentByID(String id); // SQL injection vulnerable
}
```

## Service Interfaces (Class-Based)

| Service Class | Module | Key Methods |
|--------------|--------|-------------|
| `ProductService` (shop) | shop | `getProducts(String location)`, `productsNotFound()` |
| `InstrumentService` (shop) | shop | `getInstruments(String location)`, `productsNotFound()` |
| `ProductService` (products) | products | `getAllProducts()`, `getProduct(String id)` |
| `ProductFilterService` (products) | products | `filterAllProducts(String, ProductService)` |
| `ConductorsService` | conductors | `getAllProducts()`, `getProduct(String id)` |
| `ProductFilterService` (conductors) | conductors | `filterAllProducts(String, ConductorsService)` |
| `InstrumentService` | instruments | `getInstruments(String location)` |
| `InstrumentStocksService` (instruments) | instruments | `getInstrumentStocks()` |
| `StockService` | stock | `getStocks()` |
| `InstrumentStocksService` (stock) | stock | `getStocks()`, `getStock(String productId)` |

## Cross-References

- [Program Structure](program-structure.md) | [Data Models](data-models.md) | [API Reference](api-reference.md)
