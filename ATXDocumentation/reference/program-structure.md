# Program Structure

## Root Project
- **Group**: `com.splunk`
- **Artifact**: `javashop`
- **Packaging**: POM (multi-module)
- **Modules**: shop, stock, products, instruments, conductors, annotator, test

## Module Hierarchies

### Shop Module (`com.shabushabu.javashop.shop`)
```
com.shabushabu.javashop.shop
├── JavaShopApp.java                    [Spring Boot Application + @EnableHystrix]
├── Exercises.java                      [Workshop exercise scoring (singleton)]
├── PropertiesUpdater.java              [File-based properties management (singleton)]
├── controllers/
│   ├── HomeController.java             [@Controller: /, /score, /healthcheck]
│   └── User.java                       [POJO: name, location]
├── exceptions/
│   └── InvalidLocaleException.java     [Custom exception]
├── model/
│   ├── Product.java                    [Domain: id, sku, name, description, price, amountAvailable]
│   └── Instrument.java                 [Domain: id, title, price, type, condition, seller, location]
├── repo/
│   ├── ProductRepo.java                [REST client → Products/Conductors service]
│   ├── InstrumentRepo.java             [REST client → Instruments service (@HystrixCommand)]
│   └── StockRepo.java                  [REST client → Stock service (@HystrixCommand)]
├── resources/
│   └── ProductResource.java            [@RestController: /products]
└── services/
    ├── ProductService.java             [@Service: merges ProductDTO + StockDTO]
    ├── InstrumentService.java          [@Service: instrument retrieval with locale filter]
    └── dto/
        ├── ProductDTO.java             [DTO: id, name, description, price]
        ├── StockDTO.java               [DTO: productId, sku, amountAvailable]
        └── InstrumentDTO.java          [DTO: id, title, sub_title, price, type, condition, seller, location, date, url]
```

### Products Module (`com.shabushabu.javashop.products`)
```
com.shabushabu.javashop.products
├── ProductServiceApplication.java      [Spring Boot Application]
├── controllers/
│   └── ProductController.java          [@RestController: /products, /products/healthcheck]
├── exceptions/
│   └── InvalidLocaleException.java     [Custom exception (unused)]
├── model/
│   └── Product.java                    [Domain: id, name, description, price]
└── services/
    ├── ProductService.java             [In-memory product catalog (5 items)]
    └── ProductFilterService.java       [Latency simulation with 100+ Thread.sleep() calls]
```

### Stock Module (`com.shabushabu.javashop.stock`)
```
com.shabushabu.javashop.stock
├── StockManagerApplication.java        [Spring Boot Application]
├── config/
│   └── DataGenerator.java              [@Component: generates 5 stock records @PostConstruct]
├── exceptions/
│   └── StockNotFoundException.java     [Custom exception]
├── model/
│   └── Stock.java                      [@Entity: productId, sku, amountAvailable]
├── repositories/
│   ├── StockRepository.java            [CrudRepository<Stock, String>]
│   └── InstrumentStocksRepository.java [CrudRepository<Stock, String>]
├── resources/
│   └── StockResource.java             [@RestController: /legacy, /insruments, /healthcheck]
└── services/
    ├── StockService.java               [@Service: stock retrieval]
    └── InstrumentStocksService.java    [@Service: instrument stock retrieval]
```

### Instruments Module (`com.shabushabu.javashop.instruments`)
```
com.shabushabu.javashop.instruments
├── InstrumentsApplication.java         [Spring Boot Application]
├── exceptions/
│   ├── InstrumentNotFoundException.java [Custom exception]
│   └── InvalidLocaleException.java     [Custom exception]
├── model/
│   ├── Instrument.java                 [@Entity: instruments_for_sale table]
│   ├── Stock.java                      [@Entity: InstrumentStocks table]
│   └── FilteredInstrument.java         [Locale filter (Oregon disabled)]
├── repositories/
│   ├── InstrumentRepository.java       [CrudRepository<Instrument, Long> + FindInstrumentRepository]
│   ├── FindInstrumentRepository.java   [Custom query interface]
│   └── FindInstrumentRepositoryImpl.java [Native SQL queries (contains SQL injection)]
├── resources/
│   └── InstrumentResource.java         [@RestController: /instruments, /stocks, /healthcheck]
└── services/
    ├── InstrumentService.java          [@Service: location-based instrument retrieval]
    └── InstrumentStocksService.java    [@Service: instrument stock retrieval]
```

### Conductors Module (`com.shabushabu.javashop.conductors`)
```
com.shabushabu.javashop.conductors
├── ConductorsServiceApplication.java   [Spring Boot Application]
├── controllers/
│   └── ConductorsController.java       [@RestController: /conductors, /conductors/healthcheck]
├── exceptions/
│   └── InvalidLocaleException.java     [Custom exception]
├── model/
│   ├── Product.java                    [Domain: id, name, description, price]
│   └── FilteredProducts.java           [Locale filter (Oregon disabled)]
└── services/
    ├── ConductorsService.java          [In-memory product catalog (5 items)]
    └── ProductFilterService.java       [Mirrored from Products (most code commented out)]
```

### Annotator Module (`com.splunk.otel.annotator`)
```
com.splunk.otel.annotator
├── OpenTelemetryAnnotator.java         [Main class: adds @WithSpan annotations]
├── DirExplorer.java                    [Directory traversal utility]
├── DirExplorerInstrumented.java        [Instrumented directory traversal]
└── TestGeneratedInstrumentation.java   [Test for generated instrumentation]
```

### Test Module (default package)
```
GenerateTraffic.java                    [Traffic generator for shop service]
```

## Cross-References

- [Components](../architecture/components.md) | [Interfaces](interfaces.md) | [Data Models](data-models.md)
