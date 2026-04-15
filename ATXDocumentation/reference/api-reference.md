# API Reference

## Shop Service (Port 8010)

### GET /
Renders the main product/instrument browsing page.

**Parameters**:
- `name` (optional, String) — User display name. Default: "Guest"
- `location` (optional, String) — Location filter. Default: "California"
- `userid` (optional, String) — User ID for permission check. Default: "X0000"

**Response**: Thymeleaf HTML page with `products` and `instruments` model attributes.

**Example**: `GET http://localhost:8010/?name=John&location=Utah`

### GET /score
Returns or checks exercise scores for the workshop.

**Parameters**:
- `exercise` (optional, String) — Exercise number (0-15). Default: "0"
- `data` (optional, String) — Data to validate. Default: ""

**Response** (exercise=0): `{"exercise1": "true", "exercise2": "false", ...}`
**Response** (exercise=1-15): `{"exercise{n}": "true"|"false"}`

### GET /products
Returns product list for a location.

**Parameters**:
- `location` (optional, String) — Location filter. Default: "California"

**Response**: `[{"id":"1","sku":"12345678","name":"Widget","description":"Premium ACME Widgets","price":1.20,"amountAvailable":5}, ...]`

### GET /healthcheck
**Response**: HTTP 200 OK

---

## Products Service (Port 8020)

### GET /products
Returns filtered product catalog.

**Parameters**:
- `location` (required, String) — Location filter

**Response**: `[{"id":"1","name":"Widget","description":"Premium ACME Widgets","price":1.20}, ...]`

**Note**: Colorado location incurs 966-1166ms additional latency (workshop simulation).

### GET /products/healthcheck
**Response**: HTTP 200 OK

---

## Stock Service (Port 8030)

### GET /legacy
Returns all stock records.

**Response**: `[{"productId":"1","sku":"12345678","amountAvailable":5}, ...]`

### GET /insruments
Returns instrument stock records. (Note: endpoint name contains typo)

**Response**: `[{"productId":"...","sku":"...","amountAvailable":...}, ...]`

### GET /healthcheck
**Response**: HTTP 200 OK

---

## Instruments Service (Port 8040)

### GET /instruments
Returns instruments filtered by location.

**Parameters**:
- `location` (required, String, default: "California") — Location filter

**Response**: Full instrument objects from PostgreSQL `instruments_for_sale` table.

**Special Cases**:
- Oregon → returns empty list (locale disabled)
- Chicago → executes cross-table query (side effect), returns all instruments

### GET /stocks
Returns instrument stock records.

**Response**: `[{"iD":"...","quantity":"..."}, ...]`

### GET /healthcheck
**Response**: HTTP 200 OK

---

## Conductors Service (Port 8050)

### GET /conductors
Returns product catalog. **Note: location parameter is ignored; hardcoded to "Oregon".**

**Parameters**:
- `location` (required, String) — Ignored; overridden to "Oregon"

**Response**: `[{"id":"1","name":"Widget","description":"Premium ACME Widgets","price":1.20}, ...]`

### GET /conductors/healthcheck
**Response**: HTTP 200 OK

## Cross-References

- [Interfaces](interfaces.md) | [Data Models](data-models.md) | [Program Structure](program-structure.md)
- [Workflows](../behavior/workflows.md)
