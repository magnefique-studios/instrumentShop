> ⚠️ **Early Access**: Behavior documentation is in early access. Please review critically.

# Decision Logic

## Decision Point 1: Location-Based Service Routing
- **Location**: `shop/src/main/java/.../repo/ProductRepo.java` line 38
- **Logic**: If `bConductorsEnabled` is true AND location equals "Utah" (case-insensitive) → route to Conductors service; otherwise → route to Products service
- **Current State**: `bConductorsEnabled` is hardcoded to `true`

```
location == "Utah"?
  ├── YES → GET conductors:8050/conductors?location=Utah
  └── NO  → GET products:8020/products?location={location}
```

## Decision Point 2: Oregon Locale Filter (Instruments)
- **Location**: `instruments/src/main/java/.../model/FilteredInstrument.java` lines 15-31
- **Logic**: If locale equals "Oregon" (case-insensitive) AND `s_Localedisabled` is true → throw `InvalidLocaleException`; otherwise → return true
- **Current State**: `s_Localedisabled` is hardcoded to `true`, so Oregon always throws

```
locale == "Oregon"?
  ├── YES → s_Localedisabled?
  │         ├── YES → throw InvalidLocaleException
  │         └── NO  → return true
  └── NO  → return true
```

## Decision Point 3: Oregon Locale Filter (Conductors)
- **Location**: `conductors/src/main/java/.../model/FilteredProducts.java` lines 15-31
- **Logic**: Identical to instruments' FilteredInstrument — throws for Oregon when disabled
- **Note**: ConductorsController hardcodes location to "Oregon", so this always throws (but is silently caught)

## Decision Point 4: Chicago-Specific Database Query
- **Location**: `instruments/src/main/java/.../services/InstrumentService.java` lines 41-52
- **Logic**: If location equals "Chicago" (case-insensitive) → execute `findInstruments()` (cross-table native query), then still return `findAll()` results; otherwise → return `findAll()` directly

```
location == "Chicago"?
  ├── YES → findInstruments() [cross-table query for side effect]
  │         → findAll() [return all instruments]
  └── NO  → findAll() [return all instruments]
```

## Decision Point 5: English Locale Validation (Shop Instrument Model)
- **Location**: `shop/src/main/java/.../model/Instrument.java` lines 18-22
- **Logic**: `buildForLocale()` checks if title matches English character regex
- **Current State**: The `InvalidLocaleException` throw is commented out, so non-English titles are accepted silently

## Decision Point 6: User Permission Check
- **Location**: `shop/src/main/java/.../controllers/HomeController.java` lines 92-128
- **Logic**: `allParameters()` calls `checkIfRestricted(userid)` → if true, throws `NoPermissionException`
- **Current State**: `checkIfRestricted()` always returns false (HTTP call is commented out)

## Decision Point 7: Default Parameter Values
- **Location**: `shop/src/main/java/.../controllers/HomeController.java` lines 37-48
- **Logic**:
  - `name` is null → default to "Guest"
  - `location` is null → default to "California"
  - `userid` is null → default to "X0000"

## Decision Point 8: Product-Stock Merge Fallback
- **Location**: `shop/src/main/java/.../services/ProductService.java` lines 27-33
- **Logic**: If no `StockDTO` found for a product → use `DEFAULT_STOCK_DTO` (sku="default", amount=999)

## Decision Point 9: Colorado Latency Injection
- **Location**: `products/src/main/java/.../services/ProductFilterService.java` → `locationLookup11()` → `myCoolFunction234234234()`
- **Logic**: If location equals "Colorado" → call `getMyInt()` (returns 999) → `myCoolFunction234234234(999)` → `Thread.sleep(random(200) + 966)` introducing 966-1166ms latency

```
location == "Colorado"?
  ├── YES → getMyInt() returns 999
  │         → myCoolFunction234234234(999)
  │         → Thread.sleep(966 + random(200))
  └── NO  → return sleepy=1 (minimal delay)
```

## Decision Point 10: Exercise Scoring Switch
- **Location**: `shop/src/main/java/.../Exercises.java` lines 52-95
- **Logic**: 15-case switch statement delegating to various check methods based on exercise number

## Cross-References

- [Business Logic](business-logic.md) | [Workflows](workflows.md) | [Error Handling](error-handling.md)
