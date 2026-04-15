# Structural Diagrams

## Component Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                     Docker Network: instrument_shop          │
│                                                              │
│  ┌──────────────────────────────────────────────────────┐   │
│  │               SHOP (Gateway) :8010                    │   │
│  │  ┌────────────┐ ┌──────────┐ ┌──────────────────┐   │   │
│  │  │HomeCtrl    │ │ProductSvc│ │InstrumentSvc     │   │   │
│  │  │ProductRes  │ │          │ │                   │   │   │
│  │  │Exercises   │ │          │ │                   │   │   │
│  │  └────────────┘ └──────────┘ └──────────────────┘   │   │
│  │  ┌────────────┐ ┌──────────┐ ┌──────────────────┐   │   │
│  │  │ProductRepo │ │StockRepo │ │InstrumentRepo    │   │   │
│  │  │ (REST+RT)  │ │(Hystrix) │ │(Hystrix)         │   │   │
│  │  └─────┬──────┘ └────┬─────┘ └────────┬─────────┘   │   │
│  └────────┼──────────────┼────────────────┼─────────────┘   │
│           │              │                │                  │
│    ┌──────▼──────┐ ┌────▼──────┐ ┌───────▼──────────┐      │
│    │  PRODUCTS   │ │  STOCK    │ │  INSTRUMENTS     │      │
│    │   :8020     │ │  :8030    │ │    :8040         │      │
│    │ SB 3.2.2    │ │ SB 2.1.3 │ │  SB 2.7.5       │      │
│    │ Java 17     │ │ Java 8   │ │  Java 17         │      │
│    │             │ │           │ │                   │      │
│    │ ProductCtrl │ │ StockRes  │ │ InstrumentRes    │      │
│    │ ProductSvc  │ │ StockSvc  │ │ InstrumentSvc   │      │
│    │ FilterSvc   │ │ DataGen   │ │ FindInstRepoImpl│      │
│    │ [in-memory] │ │ [H2 DB]  │ │ [JPA/Hibernate] │      │
│    └─────────────┘ └──────────┘ └────────┬──────────┘      │
│                                           │                  │
│    ┌─────────────┐                 ┌──────▼──────────┐      │
│    │ CONDUCTORS  │                 │  PostgreSQL     │      │
│    │   :8050     │                 │    :5432        │      │
│    │ SB 3.2.2    │                 │   13.1-alpine   │      │
│    │ Java 17     │                 └─────────────────┘      │
│    │ [in-memory] │                                          │
│    └─────────────┘       ┌───────────┐                      │
│                          │   Redis   │                      │
│                          │ (unused)  │                      │
│                          └───────────┘                      │
└─────────────────────────────────────────────────────────────┘
```

## Class Diagram — Shop Module (Key Classes)

```
┌────────────────────────┐
│    «@Controller»       │
│    HomeController      │
├────────────────────────┤
│ - productService       │──────▶ ProductService
│ - instrumentService    │──────▶ InstrumentService
├────────────────────────┤
│ + getProductsAllLocs() │
│ + getScores()          │
│ + healthCheck()        │
│ - allParameters()      │
│ - checkIfRestricted()  │
└────────────────────────┘

┌────────────────────────┐     ┌──────────────────┐
│    «@Service»          │     │   «@Component»   │
│    ProductService      │────▶│   ProductRepo    │
├────────────────────────┤     ├──────────────────┤
│ - stockRepo            │     │ + getProductDTOs()│
│ - productRepo          │     │ [Utah→Conductors] │
├────────────────────────┤     │ [else→Products]   │
│ + getProducts(loc)     │     └──────────────────┘
│ + productsNotFound()   │
└────────────────────────┘     ┌──────────────────┐
                               │   «@Component»   │
┌────────────────────────┐     │   StockRepo      │
│    «@Service»          │────▶├──────────────────┤
│    InstrumentService   │     │ @HystrixCommand  │
├────────────────────────┤     │ + getStockDTOs() │
│ - instrumentRepo       │     │ + stocksNotFound()│
├────────────────────────┤     └──────────────────┘
│ + getInstruments(loc)  │
└────────────────────────┘     ┌──────────────────┐
                               │   «@Component»   │
                               │   InstrumentRepo │
                               ├──────────────────┤
                               │ @HystrixCommand  │
                               │ + getinstrumentDTOs()│
                               │ + instrumentsNotFound()│
                               └──────────────────┘
```

## Package Dependency Graph

```
shop
 ├── controllers  ──▶  services  ──▶  repo
 │                     services  ──▶  model
 │                     repo      ──▶  services.dto
 └── Exercises    ──▶  controllers

products
 └── controllers  ──▶  services  ──▶  model

instruments
 └── resources    ──▶  services  ──▶  repositories  ──▶  model

stock
 └── resources    ──▶  services  ──▶  repositories  ──▶  model
     config       ──▶  repositories
```

## Cross-References

- [Behavioral Diagrams](../behavioral/sequence-diagrams.md)
- [Architecture Diagrams](../architecture/system-context.md)
- [System Overview](../../architecture/system-overview.md)
