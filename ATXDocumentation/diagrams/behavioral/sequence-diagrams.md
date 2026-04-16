# Sequence Diagrams

[← Back to README](../../README.md) | [Component Diagrams](../structural/component-diagrams.md) | [System Context](../architecture/system-context.md)

## Sequence 1: Default Product Request (California)

```
User          HomeController    ProductRepo    products:8020    StockRepo    stock:8030    InstrumentRepo    instruments:8040
 │                │                 │              │               │            │               │                │
 │ GET /?loc=CA   │                 │              │               │            │               │                │
 │───────────────>│                 │              │               │            │               │                │
 │                │ getProducts(CA) │              │               │            │               │                │
 │                │────────────────>│              │               │            │               │                │
 │                │                 │ GET /products│?location=CA   │            │               │                │
 │                │                 │─────────────>│              │            │               │                │
 │                │                 │  List<Prod>  │              │            │               │                │
 │                │                 │<─────────────│              │            │               │                │
 │                │                 │              │ getStockDTOs()│            │               │                │
 │                │                 │──────────────│──────────────>│            │               │                │
 │                │                 │              │               │ GET /legacy│               │                │
 │                │                 │              │               │───────────>│               │                │
 │                │                 │              │               │ List<Stock>│               │                │
 │                │                 │              │               │<───────────│               │                │
 │                │  List<Product>  │              │               │            │               │                │
 │                │<────────────────│              │               │            │               │                │
 │                │ getInstruments(CA)             │               │            │               │                │
 │                │───────────────────────────────────────────────────────────>│                │
 │                │                                                            │ GET /instruments?loc=CA         │
 │                │                                                            │──────────────>│                │
 │                │                                                            │ List<Instr>   │                │
 │                │                                                            │<──────────────│                │
 │                │  List<Instrument>                                           │                │
 │                │<──────────────────────────────────────────────────────────│                │
 │  HTML (index)  │                                                                            │
 │<───────────────│                                                                            │
```

## Sequence 2: Utah Request (Conductors Routing)

```
User          HomeController    ProductRepo    conductors:8050    StockRepo    stock:8030
 │                │                 │                │               │            │
 │ GET /?loc=Utah │                 │                │               │            │
 │───────────────>│                 │                │               │            │
 │                │ getProducts(UT) │                │               │            │
 │                │────────────────>│                │               │            │
 │                │                 │ [Utah→conductors]              │            │
 │                │                 │ GET /conductors?loc=Utah       │            │
 │                │                 │───────────────>│               │            │
 │                │                 │                │ (hardcode "Oregon")        │
 │                │                 │                │ filterProducts("Oregon")   │
 │                │                 │                │ → InvalidLocaleException   │
 │                │                 │                │   (caught, ignored)        │
 │                │                 │  List<Product> │               │            │
 │                │                 │<───────────────│               │            │
 │                │                 │                │  getStockDTOs()            │
 │                │                 │────────────────│──────────────>│            │
 │                │                 │                │               │ GET /legacy│
 │                │                 │                │               │───────────>│
 │                │  List<Product>  │                │               │            │
 │                │<────────────────│                │               │            │
```

## Sequence 3: Colorado Request (Latency Injection)

```
User          HomeController    ProductRepo    products:8020    ProductFilterService
 │                │                 │              │                │
 │ GET /?loc=CO   │                 │              │                │
 │───────────────>│                 │              │                │
 │                │ getProducts(CO) │              │                │
 │                │────────────────>│              │                │
 │                │                 │ GET /products?loc=Colorado    │
 │                │                 │─────────────>│               │
 │                │                 │              │ filterAll(CO)  │
 │                │                 │              │───────────────>│
 │                │                 │              │                │ myCoolFunction11(CO)
 │                │                 │              │                │ → locationLookup11(CO)
 │                │                 │              │                │   → myCoolFunction234234234(999)
 │                │                 │              │                │     → Thread.sleep(966-1166ms) ⚠️
 │                │                 │              │  List<Product> │
 │                │                 │              │<───────────────│
 │                │                 │ List<Product>│                │
 │                │                 │<─────────────│                │
```

## Sequence 4: Chicago Request (Cartesian Product)

```
User          HomeController    InstrumentRepo    instruments:8040    InstrumentService    PostgreSQL
 │                │                 │                   │                  │                   │
 │ GET /?loc=CHI  │                 │                   │                  │                   │
 │───────────────>│                 │                   │                  │                   │
 │                │ getInstr(CHI)   │                   │                  │                   │
 │                │────────────────>│                   │                  │                   │
 │                │                 │ GET /instruments?loc=Chicago         │                   │
 │                │                 │──────────────────>│                  │                   │
 │                │                 │                   │ getInstr(CHI)    │                   │
 │                │                 │                   │─────────────────>│                   │
 │                │                 │                   │                  │ [Chicago branch]   │
 │                │                 │                   │                  │ findInstruments()  │
 │                │                 │                   │                  │──────────────────>│
 │                │                 │                   │                  │ SELECT * FROM      │
 │                │                 │                   │                  │ instr, instr_chi   │
 │                │                 │                   │                  │ (cartesian!) ⚠️    │
 │                │                 │                   │                  │<──────────────────│
 │                │                 │                   │                  │ findAll()          │
 │                │                 │                   │                  │──────────────────>│
 │                │                 │                   │                  │ List<Instrument>   │
 │                │                 │                   │                  │<──────────────────│
 │                │                 │  List<Instrument> │                  │                   │
 │                │                 │<──────────────────│                  │                   │
```

---

## Related Documents

- [Workflows](../../behavior/workflows.md) — Detailed workflow descriptions
- [Component Diagrams](../structural/component-diagrams.md) — Structural views
- [Business Logic](../../behavior/business-logic.md) — Business rules driving these flows
