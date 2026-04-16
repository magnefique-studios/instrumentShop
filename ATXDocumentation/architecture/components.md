# Components

[← Back to README](../README.md) | [System Overview](system-overview.md) | [Dependencies](dependencies.md) | [Patterns](patterns.md)

## Shop Module (17 classes)

| Class | Package | Responsibility |
|-------|---------|---------------|
| `JavaShopApp` | `shop` | Spring Boot app entry point, enables Hystrix, defines RestTemplate bean |
| `HomeController` | `shop.controllers` | Main web controller: `/` (products+instruments page), `/score` (exercises), `/healthcheck` |
| `User` | `shop.controllers` | POJO for user name and location |
| `Exercises` | `shop` | Singleton managing 15 exercise validations, traces counter, `.env` file I/O |
| `PropertiesUpdater` | `shop` | Singleton managing exercise scores via `shop.properties` |
| `InvalidLocaleException` | `shop.exceptions` | Custom checked exception for locale validation |
| `Instrument` | `shop.model` | Instrument model with builder methods and locale validation |
| `Product` | `shop.model` | Product model with id, sku, name, description, price, amountAvailable |
| `InstrumentRepo` | `shop.repo` | REST client for instruments service with Hystrix fallback |
| `ProductRepo` | `shop.repo` | REST client for products/conductors services (Utah routing) |
| `StockRepo` | `shop.repo` | REST client for stock service with Hystrix fallback |
| `ProductResource` | `shop.resources` | REST controller `GET /products` delegating to ProductService |
| `InstrumentService` | `shop.services` | Maps InstrumentDTOs to Instrument models with locale handling |
| `ProductService` | `shop.services` | Merges product and stock DTOs into Product models |
| `InstrumentDTO` | `shop.services.dto` | DTO for instrument data transfer from instruments service |
| `ProductDTO` | `shop.services.dto` | DTO for product data transfer from products service |
| `StockDTO` | `shop.services.dto` | DTO for stock data transfer with DEFAULT_STOCK_DTO |

## Products Module (6 classes)

| Class | Package | Responsibility |
|-------|---------|---------------|
| `ProductServiceApplication` | `products` | Spring Boot entry point |
| `ProductController` | `products.controllers` | `GET /products?location=` with filter pipeline |
| `InvalidLocaleException` | `products.exceptions` | Custom checked exception |
| `Product` | `products.model` | Product POJO with Jackson annotations |
| `ProductFilterService` | `products.services` | Filter chain with deliberate Colorado latency injection |
| `ProductService` | `products.services` | In-memory product DAO (5 hardcoded products) |

## Conductors Module (7 classes)

| Class | Package | Responsibility |
|-------|---------|---------------|
| `ConductorsServiceApplication` | `conductors` | Spring Boot entry point |
| `ConductorsController` | `conductors.controllers` | `GET /conductors?location=` (hardcodes Oregon) |
| `InvalidLocaleException` | `conductors.exceptions` | Custom checked exception |
| `FilteredProducts` | `conductors.model` | Oregon locale filter (always throws exception) |
| `Product` | `conductors.model` | Product POJO with Jackson annotations |
| `ConductorsService` | `conductors.services` | In-memory product DAO (5 hardcoded products) |
| `ProductFilterService` | `conductors.services` | Filter chain (all calls commented out) |

## Instruments Module (11 classes)

| Class | Package | Responsibility |
|-------|---------|---------------|
| `InstrumentsApplication` | `instruments` | Spring Boot entry point, counts instruments on startup |
| `InstrumentNotFoundException` | `instruments.exceptions` | Custom checked exception |
| `InvalidLocaleException` | `instruments.exceptions` | Custom checked exception |
| `FilteredInstrument` | `instruments.model` | Oregon locale filter (throws exception when Oregon) |
| `Instrument` | `instruments.model` | JPA entity mapped to `instruments_for_sale` table |
| `Stock` | `instruments.model` | JPA entity mapped to `InstrumentStocks` table |
| `FindInstrumentRepository` | `instruments.repositories` | Custom repository interface |
| `FindInstrumentRepositoryImpl` | `instruments.repositories` | Custom repo with native SQL + SQL injection vulnerability |
| `InstrumentRepository` | `instruments.repositories` | JPA repository extending JpaRepository + FindInstrumentRepository |
| `InstrumentStocksRepository` | `instruments.repositories` | CrudRepository for Stock entity |
| `InstrumentResource` | `instruments.resources` | REST controller: `/instruments`, `/stocks`, `/healthcheck` |
| `InstrumentService` | `instruments.services` | Location-based instrument retrieval with Chicago/Oregon special handling |
| `InstrumentStocksService` | `instruments.services` | CRUD service for instrument stocks |

## Stock Module (8 classes)

| Class | Package | Responsibility |
|-------|---------|---------------|
| `StockManagerApplication` | `stock` | Spring Boot entry point |
| `DataGenerator` | `stock.config` | Generates 5 synthetic stock records on startup |
| `StockNotFoundException` | `stock.exceptions` | Custom checked exception |
| `Stock` | `stock.model` | JPA entity (H2) with productId, sku, amountAvailable |
| `InstrumentStocksRepository` | `stock.repositories` | CrudRepository for Stock |
| `StockRepository` | `stock.repositories` | CrudRepository for Stock |
| `StockResource` | `stock.resources` | REST controller: `/legacy`, `/insruments`, `/healthcheck` |
| `InstrumentStocksService` | `stock.services` | CRUD service for instrument stocks |
| `StockService` | `stock.services` | CRUD service for product stocks |

## Annotator Module (4 classes)

| Class | Package | Responsibility |
|-------|---------|---------------|
| `DirExplorer` | `annotator` | Recursive directory traversal with filter |
| `DirExplorerInstrumented` | `annotator` | DirExplorer with OTel @WithSpan/@SpanAttribute annotations |
| `OpenTelemetryAnnotator` | `annotator` | Main tool: parses Java files and adds OTel annotations |
| `TestGeneratedInstrumentation` | `annotator` | Lists classes in a directory using JavaParser |

## Test Module (1 class)

| Class | Package | Responsibility |
|-------|---------|---------------|
| `GenerateTraffic` | (default) | Sends HTTP traffic to shop at various locations for demo |

---

## Related Documents

- [System Overview](system-overview.md) — Architecture overview
- [Dependencies](dependencies.md) — Dependency mapping
- [Program Structure](../reference/program-structure.md) — Full class hierarchy
