# Component Diagrams

[← Back to README](../../README.md) | [Sequence Diagrams](../behavioral/sequence-diagrams.md) | [System Context](../architecture/system-context.md)

## Service Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                     Docker Network: instrument_shop              │
│                                                                  │
│  ┌──────────┐                                                    │
│  │  redis   │  (declared, no app usage found)                    │
│  └──────────┘                                                    │
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐    │
│  │                    shop (port 8010)                       │    │
│  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐   │    │
│  │  │HomeController│  │ProductService│  │InstrumentSvc │   │    │
│  │  │  (Thymeleaf) │  │  (merge DTOs)│  │(DTO mapping) │   │    │
│  │  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘   │    │
│  │         │                 │                  │           │    │
│  │  ┌──────▼───────┐ ┌──────▼───────┐ ┌───────▼──────┐    │    │
│  │  │ ProductRepo  │ │  StockRepo   │ │InstrumentRepo│    │    │
│  │  │(@HystrixCmd) │ │(@HystrixCmd) │ │(@HystrixCmd) │    │    │
│  │  └──────┬───────┘ └──────┬───────┘ └───────┬──────┘    │    │
│  └─────────┼────────────────┼──────────────────┼───────────┘    │
│            │                │                  │                 │
│     ┌──────▼──────┐  ┌─────▼──────┐  ┌───────▼────────┐       │
│     │  products   │  │   stock    │  │  instruments   │       │
│     │  (8020)     │  │  (8030)    │  │   (8040)       │       │
│     │  SB 3.2.2   │  │  SB 2.1.3  │  │  SB 2.7.5     │       │
│     │  Java 17    │  │  Java 1.8   │  │  Java 17      │       │
│     └─────────────┘  └────────────┘  └───────┬────────┘       │
│                                               │ JDBC            │
│     ┌──────────────┐                  ┌───────▼────────┐       │
│     │ conductors   │                  │  postgresDB    │       │
│     │  (8050)      │                  │  PG 13.1       │       │
│     │  SB 3.2.2    │                  └────────────────┘       │
│     │  Java 17     │                                            │
│     └──────────────┘                                            │
│                                                                  │
│  ┌──────────┐                                                    │
│  │shoptester│ ──HTTP──> shop                                     │
│  │  Java 11 │                                                    │
│  └──────────┘                                                    │
└─────────────────────────────────────────────────────────────────┘

   ┌──────────┐ (offline utility, not in Docker)
   │ annotator│
   │  Java 17 │
   └──────────┘
```

## Class Dependency Graph (Shop Module)

```
JavaShopApp
    │
    ├── HomeController
    │    ├── ProductService ──> ProductRepo ──> (HTTP) products / conductors
    │    │                  ──> StockRepo   ──> (HTTP) stock
    │    ├── InstrumentService ──> InstrumentRepo ──> (HTTP) instruments
    │    ├── Exercises (singleton)
    │    │    └── PropertiesUpdater (singleton)
    │    └── User (POJO)
    │
    └── ProductResource
         └── ProductService (same as above)
```

## Package Structure Diagram

```
com.shabushabu.javashop
├── shop/
│   ├── controllers/     [HomeController, User]
│   ├── exceptions/      [InvalidLocaleException]
│   ├── model/           [Instrument, Product]
│   ├── repo/            [InstrumentRepo, ProductRepo, StockRepo]
│   ├── resources/       [ProductResource]
│   └── services/
│       ├── dto/         [InstrumentDTO, ProductDTO, StockDTO]
│       ├── InstrumentService
│       └── ProductService
├── products/
│   ├── controllers/     [ProductController]
│   ├── exceptions/      [InvalidLocaleException]
│   ├── model/           [Product]
│   └── services/        [ProductFilterService, ProductService]
├── conductors/
│   ├── controllers/     [ConductorsController]
│   ├── exceptions/      [InvalidLocaleException]
│   ├── model/           [FilteredProducts, Product]
│   └── services/        [ConductorsService, ProductFilterService]
├── instruments/
│   ├── exceptions/      [InstrumentNotFound, InvalidLocale]
│   ├── model/           [FilteredInstrument, Instrument, Stock]
│   ├── repositories/    [FindInstrumentRepo*, InstrumentRepo, StocksRepo]
│   ├── resources/       [InstrumentResource]
│   └── services/        [InstrumentService, InstrumentStocksService]
└── stock/
    ├── config/          [DataGenerator]
    ├── exceptions/      [StockNotFoundException]
    ├── model/           [Stock]
    ├── repositories/    [InstrumentStocksRepo, StockRepository]
    ├── resources/       [StockResource]
    └── services/        [InstrumentStocksService, StockService]

com.splunk.otel.annotator/
├── DirExplorer, DirExplorerInstrumented
├── OpenTelemetryAnnotator (+ inner OtelAnnotationVisitor)
└── TestGeneratedInstrumentation
```

---

## Related Documents

- [Sequence Diagrams](../behavioral/sequence-diagrams.md) — Behavioral flows
- [System Context](../architecture/system-context.md) — System boundaries
- [Modules](../../reference/modules.md) — Module details
