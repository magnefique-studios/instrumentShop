# Behavioral Diagrams

## Sequence Diagram: Product Browsing Flow

```
Browser          Shop:8010       Products:8020    Stock:8030     Instruments:8040    PostgreSQL
  │                 │                │                │                │                │
  │  GET /?loc=CA   │                │                │                │                │
  │────────────────▶│                │                │                │                │
  │                 │                │                │                │                │
  │                 │ GET /products? │                │                │                │
  │                 │───────────────▶│                │                │                │
  │                 │                │ filterAll()    │                │                │
  │                 │                │ [100+ methods] │                │                │
  │                 │                │ getAllProducts()│                │                │
  │                 │  List<Product> │                │                │                │
  │                 │◀───────────────│                │                │                │
  │                 │                │                │                │                │
  │                 │ GET /legacy    │                │                │                │
  │                 │───────────────────────────────▶│                │                │
  │                 │                │  List<Stock>   │                │                │
  │                 │◀───────────────────────────────│                │                │
  │                 │                │                │                │                │
  │                 │ [Merge Products + Stock]        │                │                │
  │                 │                │                │                │                │
  │                 │ GET /instruments?loc=CA         │                │                │
  │                 │───────────────────────────────────────────────▶│                │
  │                 │                │                │  findAll()     │                │
  │                 │                │                │────────────────────────────────▶│
  │                 │                │                │  List<Instr>   │                │
  │                 │                │                │◀────────────────────────────────│
  │                 │  List<Instrument>               │                │                │
  │                 │◀───────────────────────────────────────────────│                │
  │                 │                │                │                │                │
  │  HTML Page      │                │                │                │                │
  │◀────────────────│                │                │                │                │
```

## Sequence Diagram: Utah Location Flow

```
Browser          Shop:8010      Conductors:8050     Stock:8030    Instruments:8040
  │                 │                │                  │                │
  │ GET /?loc=Utah  │                │                  │                │
  │────────────────▶│                │                  │                │
  │                 │                │                  │                │
  │                 │ GET /conductors│                  │                │
  │                 │───────────────▶│                  │                │
  │                 │                │ loc="Oregon"     │                │
  │                 │                │ FilteredProducts()│               │
  │                 │                │ [throws→caught]  │                │
  │                 │                │ filterAllProducts()               │
  │                 │ List<Product>  │                  │                │
  │                 │◀───────────────│                  │                │
  │                 │                │                  │                │
  │                 │ GET /legacy    │                  │                │
  │                 │──────────────────────────────────▶│                │
  │                 │ List<Stock>    │                  │                │
  │                 │◀──────────────────────────────────│                │
  │                 │                │                  │                │
  │                 │ GET /instruments?loc=Utah         │                │
  │                 │──────────────────────────────────────────────────▶│
  │                 │ List<Instrument>                  │                │
  │                 │◀──────────────────────────────────────────────────│
  │  HTML Page      │                │                  │                │
  │◀────────────────│                │                  │                │
```

## Activity Diagram: Location-Based Routing

```
                    ┌──────────────┐
                    │   Request    │
                    │  Received    │
                    └──────┬───────┘
                           │
                    ┌──────▼───────┐
                    │  location    │
                    │  == "Utah"?  │
                    └──┬───────┬───┘
                   YES │       │ NO
                       │       │
              ┌────────▼──┐ ┌──▼─────────┐
              │ Call       │ │ Call       │
              │ Conductors │ │ Products   │
              │ :8050      │ │ :8020      │
              └────────┬───┘ └──┬─────────┘
                       │        │
                    ┌──▼────────▼──┐
                    │ Call Stock   │
                    │ :8030/legacy │
                    └──────┬───────┘
                           │
                    ┌──────▼───────┐
                    │ Merge Product│
                    │ + Stock Data │
                    └──────┬───────┘
                           │
                    ┌──────▼───────┐
                    │ Call         │
                    │ Instruments  │
                    │ :8040        │
                    └──────┬───────┘
                           │
                    ┌──────▼───────┐
                    │   Render     │
                    │   Template   │
                    └──────────────┘
```

## Data Flow Diagram

```
┌──────────┐     Location        ┌──────────────┐
│  Browser │────────────────────▶│    Shop       │
│          │                     │  HomeCtrl     │
└──────────┘                     └──┬──┬──┬──────┘
                                    │  │  │
                    Products/DTOs   │  │  │  Instruments/DTOs
                    ┌───────────────┘  │  └───────────────┐
                    │                  │                    │
              ┌─────▼──────┐    ┌─────▼──────┐     ┌──────▼───────┐
              │  Products  │    │   Stock    │     │ Instruments  │
              │ /Conductors│    │            │     │              │
              └────────────┘    └────────────┘     └──────┬───────┘
                                                          │ JDBC
                                                   ┌──────▼───────┐
                                                   │  PostgreSQL  │
                                                   │  DB Tables   │
                                                   └──────────────┘
```

## Cross-References

- [Structural Diagrams](../structural/component-diagrams.md)
- [Architecture Diagrams](../architecture/system-context.md)
- [Workflows](../../behavior/workflows.md)
