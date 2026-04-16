# Program Structure

[← Back to README](../README.md) | [Interfaces](interfaces.md) | [Data Models](data-models.md) | [API Reference](api-reference.md) | [Modules](modules.md)

## Complete Class Hierarchy

### Shop Module (`com.shabushabu.javashop.shop`)
```
shop/src/main/java/com/shabushabu/javashop/shop/
├── JavaShopApp.java                    — @SpringBootApplication, @EnableHystrix
├── Exercises.java                      — Singleton, exercise validation engine
├── PropertiesUpdater.java              — Singleton, score persistence
├── controllers/
│   ├── HomeController.java             — @Controller (/, /score, /healthcheck)
│   └── User.java                       — POJO (name, location)
├── exceptions/
│   └── InvalidLocaleException.java     — extends Exception
├── model/
│   ├── Instrument.java                 — Domain model with builder methods
│   └── Product.java                    — Domain model (id, sku, name, desc, price, amount)
├── repo/
│   ├── InstrumentRepo.java             — @Component, REST client + @HystrixCommand
│   ├── ProductRepo.java                — @Component, REST client (Utah routing)
│   └── StockRepo.java                  — @Component, REST client + @HystrixCommand
├── resources/
│   └── ProductResource.java            — @RestController (/products)
└── services/
    ├── InstrumentService.java          — @Service, DTO→Model mapping
    ├── ProductService.java             — @Service, product+stock merge
    └── dto/
        ├── InstrumentDTO.java          — DTO (10 fields)
        ├── ProductDTO.java             — DTO (4 fields)
        └── StockDTO.java              — DTO (3 fields, DEFAULT_STOCK_DTO)
shop/src/test/java/com/shabushabu/javashop/shop/
└── ShopTest.java                       — Empty test class (all tests commented out)
```

### Products Module (`com.shabushabu.javashop.products`)
```
products/src/main/java/com/shabushabu/javashop/products/
├── ProductServiceApplication.java      — @SpringBootApplication
├── controllers/
│   └── ProductController.java          — @RestController (GET /products)
├── exceptions/
│   └── InvalidLocaleException.java     — extends Exception
├── model/
│   └── Product.java                    — POJO with @JsonProperty
└── services/
    ├── ProductFilterService.java       — Filter chain with latency injection (~600 LOC)
    └── ProductService.java             — In-memory DAO (5 products)
```

### Conductors Module (`com.shabushabu.javashop.conductors`)
```
conductors/src/main/java/com/shabushabu/javashop/conductors/
├── ConductorsServiceApplication.java  — @SpringBootApplication
├── controllers/
│   └── ConductorsController.java      — @RestController (GET /conductors)
├── exceptions/
│   └── InvalidLocaleException.java    — extends Exception
├── model/
│   ├── FilteredProducts.java          — Oregon locale filter
│   └── Product.java                   — POJO with @JsonProperty
└── services/
    ├── ConductorsService.java         — In-memory DAO (5 products)
    └── ProductFilterService.java      — Filter chain (calls commented out, ~600 LOC)
```

### Instruments Module (`com.shabushabu.javashop.instruments`)
```
instruments/src/main/java/com/shabushabu/javashop/instruments/
├── InstrumentsApplication.java        — @SpringBootApplication, @EventListener
├── exceptions/
│   ├── InstrumentNotFoundException.java — extends Exception
│   └── InvalidLocaleException.java     — extends Exception
├── model/
│   ├── FilteredInstrument.java        — Oregon locale filter
│   ├── Instrument.java               — @Entity (instruments_for_sale table)
│   └── Stock.java                     — @Entity (InstrumentStocks table)
├── repositories/
│   ├── FindInstrumentRepository.java  — interface (custom queries)
│   ├── FindInstrumentRepositoryImpl.java — SQL injection vulnerability
│   ├── InstrumentRepository.java      — extends JpaRepository + FindInstrumentRepository
│   └── InstrumentStocksRepository.java — extends CrudRepository
├── resources/
│   └── InstrumentResource.java        — @RestController (/instruments, /stocks)
└── services/
    ├── InstrumentService.java         — @Service, location-based retrieval
    └── InstrumentStocksService.java   — @Service, CRUD for stocks
```

### Stock Module (`com.shabushabu.javashop.stock`)
```
stock/src/main/java/com/shabushabu/javashop/stock/
├── StockManagerApplication.java       — @SpringBootApplication
├── config/
│   └── DataGenerator.java            — @Component, @PostConstruct data seeding
├── exceptions/
│   └── StockNotFoundException.java   — extends Exception
├── model/
│   └── Stock.java                    — @Entity (H2)
├── repositories/
│   ├── InstrumentStocksRepository.java — extends CrudRepository
│   └── StockRepository.java          — extends CrudRepository
├── resources/
│   └── StockResource.java            — @RestController (/legacy, /insruments)
└── services/
    ├── InstrumentStocksService.java  — @Service
    └── StockService.java             — @Service
```

### Annotator Module (`com.splunk.otel.annotator`)
```
annotator/src/main/java/com/splunk/otel/annotator/
├── DirExplorer.java                  — Recursive file traversal
├── DirExplorerInstrumented.java      — Same with @WithSpan/@SpanAttribute
├── OpenTelemetryAnnotator.java       — Main annotator tool + OtelAnnotationVisitor (inner class)
└── TestGeneratedInstrumentation.java — Test utility for listing classes
```

### Test Module (default package)
```
test/src/main/java/
└── GenerateTraffic.java              — Traffic generator (no package)
```

---

## Related Documents

- [Interfaces](interfaces.md) — Public APIs and contracts
- [Data Models](data-models.md) — Entity and DTO definitions
- [Components](../architecture/components.md) — Component responsibilities
