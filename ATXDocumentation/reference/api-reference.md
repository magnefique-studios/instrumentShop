# API Reference

[← Back to README](../README.md) | [Interfaces](interfaces.md) | [Data Models](data-models.md)

## Shop Service — `http://shop:8010`

### GET /
**Description**: Main page — retrieves products and instruments for display in Thymeleaf template.

| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `name` | String | No | "Guest" | User display name |
| `location` | String | No | "California" | Location for filtering/routing |
| `userid` | String | No | "X0000" | User ID for permission check |

**Response**: Thymeleaf HTML template "index" with model attributes: `user`, `products`, `instruments`

### GET /score
**Description**: Exercise scoring endpoint for observability training.

| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `exercise` | String | No | "0" | Exercise number (0=list all, 1-15=check specific) |
| `data` | String | No | "" | Exercise-specific validation data |

**Response**: `HashMap<String, String>` as JSON — e.g., `{"exercise1": "true"}`

### GET /products
**Description**: REST API for products (separate from Thymeleaf page).

| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `location` | String | No | "California" | Location for product retrieval |

**Response**: `List<Product>` JSON array

### GET /healthcheck
**Response**: "HTTP Status OK (CODE 200)" with HTTP 200

---

## Products Service — `http://products:8020`

### GET /products
| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `location` | String | Yes | "California" (if null) | Location for filtering |

**Response**: `List<Product>` JSON — always returns 5 hardcoded products regardless of location; Colorado triggers ~1s latency.

### GET /products/healthcheck
**Response**: "HTTP Status OK (CODE 200)"

---

## Conductors Service — `http://conductors:8050`

### GET /conductors
| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `location` | String | Yes | N/A | Ignored — hardcoded to "Oregon" internally |

**Response**: `List<Product>` JSON — 5 products. Always triggers InvalidLocaleException internally (silently caught).

### GET /conductors/healthcheck
**Response**: "HTTP Status OK (CODE 200)"

---

## Instruments Service — `http://instruments:8040`

### GET /instruments
| Parameter | Type | Required | Default | Description |
|-----------|------|----------|---------|-------------|
| `location` | String | Yes | "California" | Location for instrument retrieval |

**Response**: `List<Instrument>` JSON from PostgreSQL `instruments_for_sale` table. Chicago triggers cartesian product query. Oregon triggers InvalidLocaleException (logged, continues).

### GET /stocks
**Response**: `List<Stock>` JSON — all instrument stocks

### GET /healthcheck
**Response**: "HTTP Status OK (CODE 200)"

---

## Stock Service — `http://stock:8030`

### GET /legacy
**Response**: `List<Stock>` JSON — 5 synthetic stock records (seeded on startup)

### GET /insruments
**Note**: Endpoint has typo ("insruments" not "instruments")
**Response**: `List<Stock>` JSON — instrument stocks

### GET /healthcheck
**Response**: "HTTP Status OK (CODE 200)"

---

## Related Documents

- [Interfaces](interfaces.md) — Interface contracts
- [Workflows](../behavior/workflows.md) — Request flows
- [Deployment Configuration](../specialized/deployment-configuration.md) — Service ports and networking
