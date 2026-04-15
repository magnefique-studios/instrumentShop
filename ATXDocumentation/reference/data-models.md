# Data Models

## Shop Module Models

### Product (`shop/src/main/java/.../model/Product.java`)
| Field | Type | Description |
|-------|------|-------------|
| id | String | Product identifier |
| sku | String | Stock keeping unit |
| name | String | Product name |
| description | String | Product description |
| price | BigDecimal | Product price |
| amountAvailable | int | Quantity in stock |

### Instrument (`shop/src/main/java/.../model/Instrument.java`)
| Field | Type | Description |
|-------|------|-------------|
| id | long | Instrument identifier |
| title | String | Instrument title (validated for English characters) |
| price | String | Price string |
| instrument_type | String | Type of instrument |
| condition | String | Condition (New/Used) |
| seller_type | String | Seller type |
| location | String | Seller location |

### User (`shop/src/main/java/.../controllers/User.java`)
| Field | Type | Description |
|-------|------|-------------|
| name | String | User display name |
| location | String | User location |

### ProductDTO (`shop/src/main/java/.../services/dto/ProductDTO.java`)
| Field | Type | Description |
|-------|------|-------------|
| id | String | Product identifier |
| name | String | Product name |
| description | String | Product description |
| price | BigDecimal | Product price |

### StockDTO (`shop/src/main/java/.../services/dto/StockDTO.java`)
| Field | Type | Description |
|-------|------|-------------|
| productId | String | Associated product ID |
| sku | String | Stock keeping unit |
| amountAvailable | int | Available quantity |

Static: `DEFAULT_STOCK_DTO = new StockDTO("", "default", 999)`

### InstrumentDTO (`shop/src/main/java/.../services/dto/InstrumentDTO.java`)
| Field | Type | Description |
|-------|------|-------------|
| id | long | Instrument identifier |
| title | String | Instrument title |
| sub_title | String | Subtitle |
| price | String | Price string |
| instrument_type | String | Type of instrument |
| condition | String | Condition |
| post_url | String | Listing URL |
| seller_type | String | Seller type |
| location | String | Location |
| published_date | String | Publication date |

Static: `DEFAULT_INSTRUMENT_DTO` with placeholder values

## Products Module Models

### Product (`products/src/main/java/.../model/Product.java`)
| Field | Type | Description |
|-------|------|-------------|
| id | String | Product identifier |
| name | String | Product name |
| description | String | Product description |
| price | BigDecimal | Product price |

Uses `@JsonProperty` annotations for serialization.

## Conductors Module Models

### Product (`conductors/src/main/java/.../model/Product.java`)
Identical structure to Products module Product. Uses `@JsonProperty` annotations.

### FilteredProducts (`conductors/src/main/java/.../model/FilteredProducts.java`)
- Method: `filterProducts(String locale)` → returns boolean, throws `InvalidLocaleException` for Oregon

## Instruments Module Models (JPA Entities)

### Instrument (`instruments/src/main/java/.../model/Instrument.java`)
**Table**: `instruments_for_sale`

| Column | Type | Nullable | Description |
|--------|------|----------|-------------|
| id | long | NO | Primary key (auto-generated) |
| title | VARCHAR | NO | Instrument title |
| sub_title | VARCHAR | NO | Subtitle |
| price | VARCHAR | NO | Price string |
| instrument_type | VARCHAR | NO | Type of instrument |
| condition | VARCHAR | NO | Condition (New/Used) |
| location | VARCHAR | NO | Seller location |
| post_url | VARCHAR | NO | Listing URL |
| seller_type | VARCHAR | NO | Seller type |
| published_date | VARCHAR | NO | Publication date |

### Stock (`instruments/src/main/java/.../model/Stock.java`)
**Table**: `InstrumentStocks`

| Column | Type | Nullable | Description |
|--------|------|----------|-------------|
| ID | String | NO | Primary key |
| Quantity | String | NO | Stock quantity |

### FilteredInstrument (`instruments/src/main/java/.../model/FilteredInstrument.java`)
- Method: `filterInstruments(String locale)` → returns boolean, throws `InvalidLocaleException` for Oregon

## Stock Module Models (JPA Entity)

### Stock (`stock/src/main/java/.../model/Stock.java`)
**Table**: `Stock` (H2, auto-created)

| Column | Type | Description |
|--------|------|-------------|
| productId | String | Primary key (`@Id`) |
| sku | String | Stock keeping unit |
| amountAvailable | int | Available quantity |

## Entity Relationship Diagram

```
┌─────────────────────────────┐
│  instruments_for_sale       │
│  (PostgreSQL)               │
├─────────────────────────────┤
│  ID (PK)                    │
│  Title, Sub_title           │
│  Price, Instrument_Type     │
│  Condition, Location        │
│  Post_URL, Seller_type      │
│  Published_date             │
└─────────────────────────────┘

┌─────────────────────────────┐
│  instruments_for_sale_chicago│
│  (PostgreSQL)               │
├─────────────────────────────┤
│  [Same schema as above]     │
└─────────────────────────────┘

┌─────────────────────────────┐
│  Stock (H2)                 │
├─────────────────────────────┤
│  productId (PK)             │
│  sku                        │
│  amountAvailable            │
└─────────────────────────────┘
```

## Cross-References

- [Program Structure](program-structure.md) | [Interfaces](interfaces.md) | [API Reference](api-reference.md)
- [Database Schemas](../specialized/database-schemas.md)
