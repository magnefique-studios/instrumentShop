# Component Details

## 1. Shop Module (Gateway/Frontend)

**Port**: 8010 | **Spring Boot**: 1.5.19 | **Java**: 8

The shop module serves as the frontend gateway and user-facing application.

### Key Classes

| Class | File | Role |
|-------|------|------|
| `JavaShopApp` | `shop/src/main/java/.../JavaShopApp.java:1-21` | Application entry point, enables Hystrix, defines RestTemplate bean |
| `HomeController` | `shop/src/main/java/.../controllers/HomeController.java:1-130` | Main request handler for `/`, `/score`, `/healthcheck` |
| `User` | `shop/src/main/java/.../controllers/User.java:1-26` | Simple POJO for user data (name, location) |
| `ProductService` | `shop/src/main/java/.../services/ProductService.java:1-47` | Merges ProductDTO + StockDTO into Product objects |
| `InstrumentService` | `shop/src/main/java/.../services/InstrumentService.java:1-42` | Retrieves instruments by location with locale validation |
| `ProductRepo` | `shop/src/main/java/.../repo/ProductRepo.java:1-56` | REST client for Products/Conductors service (location routing) |
| `InstrumentRepo` | `shop/src/main/java/.../repo/InstrumentRepo.java:1-57` | REST client for Instruments service (Hystrix fallback) |
| `StockRepo` | `shop/src/main/java/.../repo/StockRepo.java:1-55` | REST client for Stock service (Hystrix fallback) |
| `ProductResource` | `shop/src/main/java/.../resources/ProductResource.java:1-21` | REST endpoint `/products` for shop's own product listing |
| `Exercises` | `shop/src/main/java/.../Exercises.java:1-198` | Workshop exercise scoring system with 15 exercises |
| `PropertiesUpdater` | `shop/src/main/java/.../PropertiesUpdater.java:1-106` | File-based properties management for scores |

### Models and DTOs

| Class | File | Role |
|-------|------|------|
| `Product` | `shop/src/main/java/.../model/Product.java` | Product domain model (id, sku, name, description, price, amountAvailable) |
| `Instrument` | `shop/src/main/java/.../model/Instrument.java` | Instrument domain model with locale validation (isEnglish) |
| `ProductDTO` | `shop/src/main/java/.../services/dto/ProductDTO.java` | DTO for products from Products/Conductors service |
| `StockDTO` | `shop/src/main/java/.../services/dto/StockDTO.java` | DTO for stock data from Stock service |
| `InstrumentDTO` | `shop/src/main/java/.../services/dto/InstrumentDTO.java` | DTO for instruments from Instruments service |

---

## 2. Products Module

**Port**: 8020 | **Spring Boot**: 3.2.2 | **Java**: 17

Provides product catalog with hardcoded in-memory data.

### Key Classes

| Class | File | Role |
|-------|------|------|
| `ProductServiceApplication` | `products/src/main/java/.../ProductServiceApplication.java` | Spring Boot application entry point |
| `ProductController` | `products/src/main/java/.../controllers/ProductController.java:1-28` | REST endpoint `GET /products?location=` |
| `ProductService` | `products/src/main/java/.../services/ProductService.java:1-25` | In-memory product catalog (5 hardcoded products) |
| `ProductFilterService` | `products/src/main/java/.../services/ProductFilterService.java:1-450+` | **Intentional latency injection**: ~100+ sequential method calls with Thread.sleep() |
| `Product` | `products/src/main/java/.../model/Product.java` | Product model (id, name, description, price) |

---

## 3. Conductors Module

**Port**: 8050 | **Spring Boot**: 3.2.2 | **Java**: 17

Mirrors the Products module but serves Utah/Oregon locations. Hardcodes location to "Oregon".

### Key Classes

| Class | File | Role |
|-------|------|------|
| `ConductorsServiceApplication` | `conductors/src/main/java/.../ConductorsServiceApplication.java` | Spring Boot application entry point |
| `ConductorsController` | `conductors/src/main/java/.../controllers/ConductorsController.java:1-30` | REST endpoint `GET /conductors?location=`; **overrides location to "Oregon"** |
| `ConductorsService` | `conductors/src/main/java/.../services/ConductorsService.java:1-24` | In-memory product catalog (same 5 products as Products) |
| `ProductFilterService` | `conductors/src/main/java/.../services/ProductFilterService.java` | Mirrored from Products but with most methods commented out |
| `FilteredProducts` | `conductors/src/main/java/.../model/FilteredProducts.java` | Locale filter (throws exception for Oregon when disabled) |
| `Product` | `conductors/src/main/java/.../model/Product.java` | Product model (duplicated from Products) |

---

## 4. Instruments Module

**Port**: 8040 | **Spring Boot**: 2.7.5 | **Java**: 17

Musical instrument service backed by PostgreSQL.

### Key Classes

| Class | File | Role |
|-------|------|------|
| `InstrumentsApplication` | `instruments/src/main/java/.../InstrumentsApplication.java` | Spring Boot application entry point |
| `InstrumentResource` | `instruments/src/main/java/.../resources/InstrumentResource.java:1-48` | REST endpoints: `/instruments`, `/stocks`, `/healthcheck` |
| `InstrumentService` | `instruments/src/main/java/.../services/InstrumentService.java:1-51` | Business logic: location filtering, Chicago-specific query path |
| `InstrumentStocksService` | `instruments/src/main/java/.../services/InstrumentStocksService.java` | Stock retrieval via CrudRepository |
| `InstrumentRepository` | `instruments/src/main/java/.../repositories/InstrumentRepository.java` | CrudRepository interface for Instrument entity |
| `FindInstrumentRepository` | `instruments/src/main/java/.../repositories/FindInstrumentRepository.java` | Custom query interface |
| `FindInstrumentRepositoryImpl` | `instruments/src/main/java/.../repositories/FindInstrumentRepositoryImpl.java:1-37` | **Contains SQL injection vulnerability** and Cartesian join bug |
| `Instrument` | `instruments/src/main/java/.../model/Instrument.java` | JPA entity mapped to `instruments_for_sale` table |
| `Stock` | `instruments/src/main/java/.../model/Stock.java` | JPA entity mapped to `InstrumentStocks` table |
| `FilteredInstrument` | `instruments/src/main/java/.../model/FilteredInstrument.java` | Locale filter (throws exception for Oregon when disabled) |

---

## 5. Stock Module

**Port**: 8030 | **Spring Boot**: 2.1.3 | **Java**: 8

Stock management service with H2 in-memory database.

### Key Classes

| Class | File | Role |
|-------|------|------|
| `StockManagerApplication` | `stock/src/main/java/.../StockManagerApplication.java` | Spring Boot application entry point |
| `StockResource` | `stock/src/main/java/.../resources/StockResource.java:1-44` | REST endpoints: `/legacy`, `/insruments` (typo), `/healthcheck` |
| `StockService` | `stock/src/main/java/.../services/StockService.java:1-28` | Stock retrieval via CrudRepository |
| `InstrumentStocksService` | `stock/src/main/java/.../services/InstrumentStocksService.java` | Instrument stocks retrieval |
| `StockRepository` | `stock/src/main/java/.../repositories/StockRepository.java` | CrudRepository for Stock entity |
| `InstrumentStocksRepository` | `stock/src/main/java/.../repositories/InstrumentStocksRepository.java` | CrudRepository for instrument stocks |
| `DataGenerator` | `stock/src/main/java/.../config/DataGenerator.java:1-32` | Generates 5 synthetic stock records on startup |
| `Stock` | `stock/src/main/java/.../model/Stock.java` | JPA entity (productId, sku, amountAvailable) |

---

## 6. Annotator Module (Utility)

**Java**: 17 | No Spring Boot

Utility tool for automatically adding OpenTelemetry `@WithSpan` annotations to Java source files.

### Key Classes

| Class | File | Role |
|-------|------|------|
| `OpenTelemetryAnnotator` | `annotator/src/main/java/.../OpenTelemetryAnnotator.java` | Main entry point; uses JavaParser to add annotations |
| `DirExplorer` | `annotator/src/main/java/.../DirExplorer.java` | Directory traversal utility |
| `DirExplorerInstrumented` | `annotator/src/main/java/.../DirExplorerInstrumented.java` | Instrumented directory traversal variant |

---

## 7. Test Module (Utility)

**Java**: 11 | No Spring Boot

Traffic generator that sends HTTP requests to the shop service for testing.

### Key Classes

| Class | File | Role |
|-------|------|------|
| `GenerateTraffic` | `test/src/main/java/GenerateTraffic.java` | Sends synthetic traffic to shop endpoints |

## Cross-References

- [System Overview](system-overview.md) | [Dependencies](dependencies.md) | [Patterns](patterns.md)
- [Program Structure](../reference/program-structure.md)
