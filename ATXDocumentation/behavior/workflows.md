> ⚠️ **Early Access**: Behavior documentation is in early access. Please review critically.

# Workflows

[← Back to README](../README.md) | [Related: Business Logic](business-logic.md) | [Decision Logic](decision-logic.md) | [Error Handling](error-handling.md)

## Overview

This document describes application-level workflows only. Build, test, CI/CD, and deployment workflows are excluded.

---

## Workflow 1: Product & Instrument Retrieval (Main Page Request)

**Entry Point**: `HomeController.getProductsAllLocations()` — `GET /?name={name}&location={location}&userid={userid}`

**Flow**:
1. Increment traces sent counter (`Exercises.incrementTracesSent()`)
2. Apply defaults: name → "Guest", location → "California", userid → "X0000"
3. Call `allParameters()` → `checkIfRestricted(userid)` (currently always returns false)
4. Create `User` object, set on model
5. Record start time
6. **Fetch products**: `productService.getProducts(location)` →
   - `ProductRepo.getProductDTOs(location)`:
     - If location = "Utah" AND conductors enabled → call conductors service
     - Else → call products service
   - `StockRepo.getStockDTOs()` → call stock service at `/legacy`
   - Merge product DTOs with stock DTOs (default stock if missing)
7. Record end time, compute duration
8. **Latency tracking**: Update per-location max latency (Utah or Colorado)
9. **Fetch instruments**: `instrumentService.getInstruments(location)` →
   - `InstrumentRepo.getinstrumentsByLocation(location)` → call instruments service
   - Map DTOs to Instrument models via `buildForLocale()` or `buildIt()` on locale failure
10. Set products and instruments on model, return "index" template

---

## Workflow 2: Product Retrieval via Products Service

**Entry Point**: `ProductController.getProductsByLocation()` — `GET /products?location={location}`

**Flow**:
1. Default location to "California" if null
2. Create new `ProductService` instance (in-memory DAO with 5 products)
3. Create new `ProductFilterService` instance
4. Call `filterAllProducts(location, productService)`:
   - Execute chain of `myCoolFunction*()` calls — most sleep 0–8ms
   - **Colorado path**: `myCoolFunction11()` → `locationLookup11()` detects "Colorado" → `myCoolFunction234234234(999)` → sleep 966–1166ms (latency injection)
   - Return `productService.getAllProducts()` (all 5 hardcoded products)

---

## Workflow 3: Product Retrieval via Conductors Service

**Entry Point**: `ConductorsController.getProductsByLocation()` — `GET /conductors?location={location}`

**Flow**:
1. **Hardcode location to "Oregon"** (ignores input parameter)
2. Create `FilteredProducts`, call `filterProducts("Oregon")`
   - Oregon is disabled locale → throws `InvalidLocaleException`
   - Exception caught in empty catch block (silently swallowed)
3. Call `ProductFilterService.filterAllProducts("Oregon", conductorsService)`
   - All filter function calls are **commented out** in conductors version
   - Returns `conductorsService.getAllProducts()` immediately
4. Return 5 hardcoded products

---

## Workflow 4: Instrument Retrieval with Location-Based Query

**Entry Point**: `InstrumentResource.getInstruments()` — `GET /instruments?location={location}`

**Flow**:
1. Log incoming location
2. Call `InstrumentService.getInstruments(location)`:
   a. Create `FilteredInstrument`, call `filterInstruments(location)`
      - If "Oregon" → throws `InvalidLocaleException` (caught, logged as error)
   b. **Chicago path**: Call `instrumentRepo.findInstruments()` →
      - Executes cartesian product query: `SELECT * FROM instruments_for_sale, instruments_for_sale_chicago`
      - If result is non-null List, returns `instrumentRepo.findAll()` (standard result)
   c. **Default path**: Returns `instrumentRepo.findAll()` via JPA/PostgreSQL

---

## Workflow 5: Stock Data Initialization

**Entry Point**: `DataGenerator.init()` — triggered by `@PostConstruct` on startup

**Flow**:
1. Log "Generating synthetic data..."
2. Save 5 stock records to H2 in-memory database:
   - Product 1: SKU 12345678, qty 5
   - Product 2: SKU 34567890, qty 2
   - Product 3: SKU 54326745, qty 999
   - Product 4: SKU 93847614, qty 0
   - Product 5: SKU 11856388, qty 1
3. Log "data generation complete"

---

## Workflow 6: Exercise Scoring

**Entry Point**: `HomeController.getScores()` — `GET /score?exercise={n}&data={data}`

**Flow**:
1. Default exercise to "0", data to ""
2. If exercise = 0 → return all scores from `PropertiesUpdater.getListOfScores()`
3. Else → call `Exercises.checkExercise(exercise, data, controller)`:
   - Switch on exercise number (1–15), each with custom validation logic
   - Return result as `{"exercise{N}": "true"/"false"}`

---

## Workflow 7: Traffic Generation

**Entry Point**: `GenerateTraffic.main()` — standalone Java application

**Flow**:
1. Read `tester.properties` file
2. If first run → set `FirstRun=False`, write properties, exit
3. Sleep 20 seconds (wait for services to start)
4. Send 40 requests to shop with location=Utah
5. Send 40 requests with location=California (timed)
6. Send 30 requests with location=Oregon
7. If not `-chicago` flag: Send 40 requests with location=Colorado (timed)
8. If `-chicago` flag: Send 2 requests with location=Chicago (timed)
9. Print duration comparisons

---

## Workflow 8: Code Annotation (Annotator)

**Entry Point**: `OpenTelemetryAnnotator.main()` — utility tool

**Flow**:
1. Walk products module source directory
2. For each `.java` file:
   - Parse using JavaParser
   - Visit all method declarations
   - Add `@WithSpan` to methods (excluding main, setters, health, most getters)
   - Add `@SpanAttribute` to all method parameters
   - Add import declarations for OTel annotations
   - Rename original file to `.javaOLD`, write modified file

---

## Related Documents

- [Business Logic](business-logic.md) — Detailed business rules per component
- [Decision Logic](decision-logic.md) — All conditional branches
- [Error Handling](error-handling.md) — Exception patterns
- [Sequence Diagrams](../diagrams/behavioral/sequence-diagrams.md) — Visual flow representations
