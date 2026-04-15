> ⚠️ **Early Access**: Behavior documentation is in early access. Please review critically.

# Business Logic

## Shop Module

### HomeController (`shop/src/main/java/.../controllers/HomeController.java`)
- **Main Page (`/`)**: Accepts `name`, `location`, and `userid` parameters. Defaults: name="Guest", location="California", userid="X0000"
- Increments trace counter via `Exercises.incrementTracesSent()`
- Validates user permissions via `allParameters()` → `checkIfRestricted()` (currently always returns false; HTTP permission check is commented out)
- Creates a `User` object with name and location
- Retrieves products via `productService.getProducts(location)` and tracks latency
- Tracks Colorado and Utah latency separately (`s_coloradoLatency`, `s_utahLatency` static fields)
- Retrieves instruments via `instrumentService.getInstruments(location)`
- Returns Thymeleaf "index" template

### Exercise Scoring (`/score`)
- Accepts `exercise` number and `data` string parameters
- If exercise=0, returns all scores via `PropertiesUpdater.getListOfScores()`
- For exercises 1-15, delegates to `Exercises.checkExercise()` which uses a switch statement
- Exercises validate workshop progress (e.g., trace sending, service discovery, annotation usage)

### ProductService (`shop/src/main/java/.../services/ProductService.java`)
- `getProducts(location)`: Merges `ProductDTO` (from Products/Conductors service) with `StockDTO` (from Stock service) into unified `Product` objects
- Uses Java Streams to map and join data by product ID
- Falls back to `DEFAULT_STOCK_DTO` (sku="default", amount=999) when stock data is missing

### InstrumentService (`shop/src/main/java/.../services/InstrumentService.java`)
- `getInstruments(location)`: Retrieves instruments by location from Instruments service
- Maps `InstrumentDTO` to `Instrument` using `buildForLocale()` which validates English locale
- On `InvalidLocaleException`, falls back to `buildIt()` (without title field)

## Products Module

### ProductController (`products/src/main/java/.../controllers/ProductController.java`)
- `getProductsByLocation(location)`: Defaults location to "California" if null
- Instantiates `ProductService` and `ProductFilterService` (not Spring-managed — direct instantiation)
- Delegates to `ProductFilterService.filterAllProducts(location, service)`

### ProductService (`products/src/main/java/.../services/ProductService.java`)
- Contains hardcoded product catalog with 5 items: Widget ($1.20), Sprocket ($4.10), Anvil ($45.50), Cogs ($1.80), Multitool ($154.10)
- `getAllProducts()`: Returns all products from in-memory HashMap
- `getProduct(id)`: Returns Optional product by ID

### ProductFilterService (`products/src/main/java/.../services/ProductFilterService.java`)
- `filterAllProducts(location, productService)`: **Workshop simulation method** — calls 100+ `myCoolFunction*()` variants before returning `productService.getAllProducts()`
- For "Colorado" location, `locationLookup11()` triggers `myCoolFunction234234234()` which introduces **966-1166ms random latency** via `Thread.sleep()`
- Most `myCoolFunction*()` calls perform 0-8ms Thread.sleep() with no business logic

## Conductors Module

### ConductorsController (`conductors/src/main/java/.../controllers/ConductorsController.java`)
- `getProductsByLocation(location)`: **Hardcodes location to "Oregon"** regardless of input parameter
- Instantiates `ConductorsService` and `ProductFilterService` directly
- Calls `FilteredProducts.filterProducts("Oregon")` which throws `InvalidLocaleException` (Oregon is disabled)
- Exception is silently caught; proceeds to return `serviceFilter.filterAllProducts(location, service)`
- Note: Since location is hardcoded to Oregon and Oregon locale is disabled, the FilteredProducts check always throws but is ignored

### ConductorsService
- Identical product catalog to `ProductService` (same 5 products)

## Instruments Module

### InstrumentService (`instruments/src/main/java/.../services/InstrumentService.java`)
- `getInstruments(location)`: Core business logic:
  1. Creates `FilteredInstrument` and calls `filterInstruments(location)` — returns false for Oregon (throws InvalidLocaleException)
  2. If filter returns false, returns empty list
  3. For "Chicago" location: calls `instrumentRepo.findInstruments()` which performs cross-table query, then still returns `findAll()` results if query succeeded
  4. For all other locations: returns all instruments via `instrumentRepo.findAll()`

### FilteredInstrument (`instruments/src/main/java/.../model/FilteredInstrument.java`)
- `filterInstruments(locale)`: Returns true for all locales except "Oregon" (throws `InvalidLocaleException` when Oregon locale is disabled — which is always, since `s_Localedisabled = true`)

## Stock Module

### StockService (`stock/src/main/java/.../services/StockService.java`)
- `getStocks()`: Returns all stocks via `stockRepository.findAll()` using Spring Data CrudRepository

### InstrumentStocksService (`stock/src/main/java/.../services/InstrumentStocksService.java`)
- `getStocks()`: Returns all instrument stocks
- `getStock(productId)`: Currently returns `null` (implementation commented out)

### DataGenerator (`stock/src/main/java/.../config/DataGenerator.java`)
- `@PostConstruct` method generates 5 synthetic stock records: products 1-5 with SKUs and quantities (5, 2, 999, 0, 1)

## Cross-References

- [Workflows](workflows.md) | [Decision Logic](decision-logic.md) | [Error Handling](error-handling.md)
- [Component Details](../architecture/components.md)
