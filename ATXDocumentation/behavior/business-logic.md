> ⚠️ **Early Access**: Behavior documentation is in early access. Please review critically.

# Business Logic

[← Back to README](../README.md) | [Related: Workflows](workflows.md) | [Decision Logic](decision-logic.md) | [Error Handling](error-handling.md)

## Overview

The InstrumenT-ation Shop is a multi-service e-commerce demo platform for musical instruments, composed of 7 Maven modules orchestrated via Docker Compose. Business logic centers on location-based product/instrument routing, exercise scoring, and observability training scenarios.

---

## Module-Level Business Rules

### 1. Shop Module (`shop/`)

**HomeController** (`shop/src/main/java/.../controllers/HomeController.java`)
- **Location defaulting**: If `location` is null, defaults to `"California"` (line ~48)
- **Name defaulting**: If `name` is null, defaults to `"Guest"` (line ~44)
- **UserID defaulting**: If `userid` is null, defaults to `"X0000"` (line ~52)
- **Latency tracking**: Measures `productService.getProducts()` duration in nanoseconds; tracks per-location maximum latencies for Utah and Colorado in static fields (`s_utahLatency`, `s_coloradoLatency`)
- **Utah latency**: When location is "Utah", if duration exceeds stored max, updates `s_utahLatency`; resets `s_coloradoLatency` to 0
- **Colorado latency**: When location is "Colorado", if duration exceeds stored max, updates `s_coloradoLatency`
- **Permission check**: `allParameters()` calls `checkIfRestricted(userid)` which currently always returns `false` (external call commented out)
- **Trace counting**: Each request to `/` increments `Exercises.incrementTracesSent()`

**ProductRepo** (`shop/src/main/java/.../repo/ProductRepo.java`)
- **Utah routing**: When `bConductorsEnabled` is true AND location equals "Utah" (case-insensitive), routes to the **conductors** service instead of **products** service
- **Default routing**: All other locations route to the **products** service

**InstrumentRepo** (`shop/src/main/java/.../repo/InstrumentRepo.java`)
- Fetches instruments from the **instruments** service via REST, either all or filtered by location
- Hystrix fallback: Returns empty map on failure (`instrumentsNotFound()`)

**StockRepo** (`shop/src/main/java/.../repo/StockRepo.java`)
- Fetches stock data from the **stock** service at `/legacy` endpoint
- Fetches instrument stocks from `/instrumemnts` endpoint (note: typo in endpoint name)
- Hystrix fallback: Returns empty map on failure (`stocksNotFound()`)

**ProductService** (`shop/src/main/java/.../services/ProductService.java`)
- Merges product DTOs with stock DTOs by matching `productId`
- When stock DTO is missing for a product, uses `StockDTO.DEFAULT_STOCK_DTO` (sku="default", amount=999)

**InstrumentService** (`shop/src/main/java/.../services/InstrumentService.java`)
- Maps `InstrumentDTO` to `Instrument` using `buildForLocale()` for locale validation
- On `InvalidLocaleException`, falls back to `buildIt()` (without locale validation)

**Exercises** (`shop/src/main/java/.../Exercises.java`)
- Singleton pattern managing exercise scoring for observability training
- 15 exercises with validation logic via `checkExercise()` switch statement
- Tracks traces sent count, reads/writes `.env` file for configuration
- Exercise validations range from credential checks to latency comparisons to annotation detection

**PropertiesUpdater** (`shop/src/main/java/.../PropertiesUpdater.java`)
- Singleton managing `shop.properties` file for exercise scores
- Provides get/set/reset score functionality for up to 10 exercises

### 2. Products Module (`products/`)

**ProductController** (`products/src/main/java/.../controllers/ProductController.java`)
- `GET /products?location={location}` — defaults location to "California" if null
- Creates new `ProductService` and `ProductFilterService` per request (not Spring-managed)

**ProductService** (`products/src/main/java/.../services/ProductService.java`)
- Fake in-memory product DAO with 5 hardcoded products (Widget, Sprocket, Anvil, Cogs, Multitool)
- `getAllProducts()` returns all products; `getProduct(id)` returns Optional

**ProductFilterService** (`products/src/main/java/.../services/ProductFilterService.java`)
- `filterAllProducts()` calls a long chain of `myCoolFunction*()` methods before returning all products
- **Colorado latency injection**: When location is "Colorado", `locationLookup11()` calls `myCoolFunction234234234()` which sleeps for a random duration between 966–1166ms via `sleepy.nextInt(200) + 966`
- Other `myCoolFunction*()` methods have Thread.sleep calls with small values (0–8ms) for noise
- This is the deliberate latency injection point for the observability training scenario

### 3. Conductors Module (`conductors/`)

**ConductorsController** (`conductors/src/main/java/.../controllers/ConductorsController.java`)
- `GET /conductors?location={location}` — **hardcodes location to "Oregon"** regardless of input (line ~24)
- Creates `FilteredProducts` and calls `filterProducts("Oregon")` which always throws `InvalidLocaleException` (since Oregon is a disabled locale)
- Exception is silently caught in empty catch block, then returns products from `ProductFilterService`

**FilteredProducts** (`conductors/src/main/java/.../model/FilteredProducts.java`)
- Oregon locale is hardcoded as disabled (`s_Localedisabled = true`)
- `filterProducts("Oregon")` always throws `InvalidLocaleException`

**ProductFilterService (conductors)** — Same structure as products module but with all `myCoolFunction*()` calls commented out; returns products directly from `ConductorsService.getAllProducts()`

**ConductorsService** — Same in-memory DAO as ProductService with identical 5 products

### 4. Instruments Module (`instruments/`)

**InstrumentResource** (`instruments/src/main/java/.../resources/InstrumentResource.java`)
- `GET /instruments?location={location}` — defaults to "California" via `@DefaultValue`
- `GET /stocks` — returns all instrument stocks
- `GET /healthcheck` — health check endpoint
- `@ExceptionHandler` for `InstrumentNotFoundException` returning 404

**InstrumentService** (`instruments/src/main/java/.../services/InstrumentService.java`)
- **Oregon locale filter**: Calls `FilteredInstrument.filterInstruments(location)`. If Oregon, exception is caught and logged but does not halt execution
- **Chicago cartesian query**: When location is "Chicago", calls `instrumentRepo.findInstruments()` which executes `SELECT * FROM instruments_for_sale, instruments_for_sale_chicago` — a cartesian product join producing massive result set
- **Default path**: All other locations use `instrumentRepo.findAll()` (standard JPA)

**FindInstrumentRepositoryImpl** (`instruments/src/main/java/.../repositories/FindInstrumentRepositoryImpl.java`)
- **SQL Injection vulnerability**: `findInstrumentByID()` concatenates `id` directly into JPQL query: `"FROM instruments i WHERE i.ID = " + id.toString()`
- `findInstruments()` executes a native SQL cartesian product query across two tables

**InstrumentStocksService** — Simple CRUD service for instrument stocks via `CrudRepository`

### 5. Stock Module (`stock/`)

**DataGenerator** (`stock/src/main/java/.../config/DataGenerator.java`)
- Generates 5 synthetic stock records on startup via `@PostConstruct`
- Products 1-5 with SKUs and varying availability (5, 2, 999, 0, 1)

**StockResource** (`stock/src/main/java/.../resources/StockResource.java`)
- `GET /legacy` — returns all stocks
- `GET /insruments` — returns instrument stocks (note: typo in endpoint — "insruments" not "instruments")
- `GET /healthcheck` — health check

**StockService / InstrumentStocksService** — Both use Spring Data `CrudRepository` to stream all records

### 6. Annotator Module (`annotator/`)

**OpenTelemetryAnnotator** — Automated tool using JavaParser to add `@WithSpan` and `@SpanAttribute` annotations to methods in the products module source code. Filters out `main`, `set*`, `health*`, and most `get*` methods.

**DirExplorer / DirExplorerInstrumented** — File system traversal utilities; `DirExplorerInstrumented` is the annotated version with `@WithSpan`/`@SpanAttribute`.

### 7. Test Module (`test/`)

**GenerateTraffic** — Traffic generator that sends HTTP requests to the shop service at various locations (Utah ×40, California ×40, Oregon ×30, then Colorado ×40 or Chicago ×2). Includes first-run detection via properties file.

---

## Related Documents

- [Workflows](workflows.md) — Process flows and user journeys
- [Decision Logic](decision-logic.md) — Decision trees and conditional branches
- [Error Handling](error-handling.md) — Exception patterns and recovery
- [Architecture Components](../architecture/components.md) — Component details
- [Technical Debt Report](../technical-debt-report.md) — Issues and recommendations
