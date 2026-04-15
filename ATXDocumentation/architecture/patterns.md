# Architectural Patterns

## Design Patterns Identified

### 1. API Gateway Pattern
- **Where**: Shop module acts as the single entry point for all client requests
- **Implementation**: `HomeController` aggregates data from Products, Stock, and Instruments services
- **Source**: `shop/src/main/java/.../controllers/HomeController.java`

### 2. Circuit Breaker Pattern (Hystrix)
- **Where**: Shop module's repository classes
- **Implementation**: `@HystrixCommand(fallbackMethod = "...")` annotations on `InstrumentRepo.getinstrumentDTOs()`, `StockRepo.getStockDTOs()`, `StockRepo.getInstrumentStockDTOs()`
- **Fallback Strategy**: Returns empty collections on service failure
- **Source**: `shop/src/main/java/.../repo/InstrumentRepo.java:35`, `shop/src/main/java/.../repo/StockRepo.java:37,47`

### 3. Repository Pattern
- **Where**: All data access layers
- **Implementation**: Spring Data `CrudRepository` interfaces for Stock and Instrument entities
- **Variants**:
  - `StockRepository extends CrudRepository<Stock, String>` — Stock module
  - `InstrumentRepository extends CrudRepository<Instrument, Long>` — Instruments module
  - Custom `FindInstrumentRepository` with `FindInstrumentRepositoryImpl` for native queries

### 4. DTO (Data Transfer Object) Pattern
- **Where**: Shop module communicating with backend services
- **Implementation**: `ProductDTO`, `StockDTO`, `InstrumentDTO` used to transfer data between services
- **Source**: `shop/src/main/java/.../services/dto/`

### 5. Service Layer Pattern
- **Where**: All modules
- **Implementation**: Dedicated `@Service` classes encapsulate business logic
- **Examples**: `ProductService`, `InstrumentService`, `StockService`, `ConductorsService`

### 6. Singleton Pattern
- **Where**: Shop module utilities
- **Implementation**: `PropertiesUpdater.s_instance` and `Exercises.s_instance` are eager singletons
- **Source**: `shop/src/main/java/.../PropertiesUpdater.java:13`, `shop/src/main/java/.../Exercises.java:20`

### 7. Strategy/Routing Pattern
- **Where**: Location-based service routing
- **Implementation**: `ProductRepo.getProductDTOs()` routes Utah requests to Conductors service, others to Products
- **Source**: `shop/src/main/java/.../repo/ProductRepo.java:38-48`

## Anti-Patterns Identified

### 1. Code Duplication
- **Problem**: Conductors module is a near-complete copy of Products module (same Product model, same ProductFilterService structure, same ConductorsService data)
- **Impact**: Changes must be synchronized across both modules

### 2. God Method
- **Problem**: `ProductFilterService.filterAllProducts()` contains 100+ sequential method calls with no meaningful logic differentiation
- **Source**: `products/src/main/java/.../services/ProductFilterService.java:19-168`

### 3. Hardcoded Configuration
- **Problem**: Service URLs, database credentials, and location overrides are hardcoded
- **Examples**:
  - `ConductorsController` hardcodes `location = "Oregon"` (line 22)
  - Docker Compose has hardcoded PostgreSQL credentials

### 4. Inconsistent API Design
- **Problem**: Endpoint naming is inconsistent across services
- **Examples**:
  - Stock: `/legacy` and `/insruments` (typo for `/instruments`)
  - Instruments: `/instruments` and `/stocks`

### 5. Mixed Responsibility
- **Problem**: `Exercises.java` contains HTTP client code, file I/O, scoring logic, and trace management in a single class
- **Source**: `shop/src/main/java/.../Exercises.java`

## Cross-References

- [System Overview](system-overview.md) | [Components](components.md) | [Dependencies](dependencies.md)
- [Technical Debt - Maintenance Burden](../technical-debt/maintenance-burden.md)
