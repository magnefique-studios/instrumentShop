> ⚠️ **Early Access**: Behavior documentation is in early access. Please review critically.

# Application Workflows

## Workflow 1: Product Browsing

**Entry Point**: `GET /` on Shop (port 8010)

```
User Browser
     │
     │ GET /?name=&location=&userid=
     ▼
HomeController.getProductsAllLocations()
     │
     ├── Exercises.incrementTracesSent()
     │
     ├── allParameters() → checkIfRestricted(userid)
     │    [Currently always returns false]
     │
     ├── productService.getProducts(location)
     │    │
     │    ├── productRepo.getProductDTOs(location)
     │    │    │
     │    │    ├─ [If Utah] ──▶ GET conductors:8050/conductors?location=Utah
     │    │    │                    └── ConductorsController.getProductsByLocation()
     │    │    │                         ├── location = "Oregon" (hardcoded override)
     │    │    │                         ├── FilteredProducts.filterProducts("Oregon") → throws (caught)
     │    │    │                         └── ProductFilterService.filterAllProducts("Oregon", service)
     │    │    │                              └── ConductorsService.getAllProducts() → 5 products
     │    │    │
     │    │    └─ [Else] ────▶ GET products:8020/products?location={location}
     │    │                      └── ProductController.getProductsByLocation()
     │    │                           └── ProductFilterService.filterAllProducts(location, service)
     │    │                                ├── [100+ myCoolFunction*() calls with Thread.sleep()]
     │    │                                ├── [Colorado: 966-1166ms extra latency]
     │    │                                └── ProductService.getAllProducts() → 5 products
     │    │
     │    ├── stockRepo.getStockDTOs()
     │    │    └── @HystrixCommand ──▶ GET stock:8030/legacy
     │    │         └── StockResource.getStocks()
     │    │              └── StockService.getStocks() → 5 stock records
     │    │
     │    └── Merge ProductDTOs + StockDTOs → List<Product>
     │
     ├── [Track latency for Utah/Colorado]
     │
     ├── instrumentService.getInstruments(location)
     │    │
     │    └── instrumentRepo.getinstrumentsByLocation(location)
     │         └── GET instruments:8040/instruments?location={location}
     │              └── InstrumentResource.getInstruments()
     │                   └── InstrumentService.getInstruments(location)
     │                        ├── FilteredInstrument.filterInstruments(location)
     │                        ├── [Chicago] findInstruments() → cross-table query
     │                        └── findAll() → all instruments from DB
     │
     └── Return Thymeleaf "index" template
```

## Workflow 2: Exercise Scoring

**Entry Point**: `GET /score?exercise=&data=` on Shop (port 8010)

```
User/Client
     │
     │ GET /score?exercise={n}&data={data}
     ▼
HomeController.getScores()
     │
     ├── [exercise=0] → PropertiesUpdater.getListOfScores()
     │    └── Read /container/shop/data/shop.properties
     │         └── Return HashMap of all exercise scores
     │
     └── [exercise=1-15] → Exercises.checkExercise(exercise, data, controller)
          │
          ├── case 1: checkExercise2(controller, data)     → Send metric with user token
          ├── case 2: checkExercise2(controller, "")        → Send metric with file token
          ├── case 3: checkExercise3(controller)            → Check traces ≤ 180
          ├── case 4: checkExercise4(controller)            → Colorado latency > Utah×1.2
          ├── case 5: checkExercise5(controller)            → Check user restriction
          ├── case 6: data.contains("Authorization")
          ├── case 7: data contains "Shop"/"Products"
          ├── case 8: data.contains("Not")
          ├── case 9: data.contains("getAllProducts")
          ├── case 10: data contains "myCool" or "lookup" (not "getAllProducts")
          ├── case 11: checkExercise11 → check "Annotated" property
          ├── case 12: checkExercise3 (same as case 3)
          ├── case 13: data.contains("myCoolFunction234234234")
          ├── case 14: data.contains("@SpanAttribute")
          └── case 15: !checkExercise4 (inverse of case 4)
```

## Workflow 3: Direct Product Listing

**Entry Point**: `GET /products` on Shop (port 8010)

```
Client
     │
     │ GET /products?location={location}
     ▼
ProductResource.getProducts()
     │
     └── productService.getProducts(location)
          └── [Same as Product Browsing workflow from productService step]
```

## Workflow 4: Instrument Stock Retrieval

**Entry Point**: `GET /stocks` on Instruments (port 8040)

```
Client
     │
     │ GET /stocks
     ▼
InstrumentResource.getInstrumentStocks()
     │
     └── instrumentStocksService.getInstrumentStocks()
          └── stockRepository.findAll() → List<Stock>
```

## Cross-References

- [Business Logic](business-logic.md) | [Decision Logic](decision-logic.md) | [Error Handling](error-handling.md)
- [System Overview](../architecture/system-overview.md)
