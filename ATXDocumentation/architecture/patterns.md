# Design Patterns

[← Back to README](../README.md) | [System Overview](system-overview.md) | [Components](components.md) | [Dependencies](dependencies.md)

## Identified Patterns

### 1. MVC (Model-View-Controller)
- **Where**: Shop module (Thymeleaf), Products, Conductors, Instruments, Stock
- **Controllers**: `HomeController` (@Controller + Thymeleaf), `ProductResource` (@RestController), `ProductController`, `ConductorsController`, `InstrumentResource`, `StockResource`
- **Models**: `Product`, `Instrument`, `Stock`, `User`
- **Views**: Thymeleaf templates (shop — `index` template)

### 2. Repository Pattern
- **Where**: Shop (REST client repos), Instruments (JPA repos), Stock (JPA repos)
- **Shop**: `InstrumentRepo`, `ProductRepo`, `StockRepo` — abstract REST calls to downstream services
- **Instruments**: `InstrumentRepository` (extends `JpaRepository`), `FindInstrumentRepository`/`FindInstrumentRepositoryImpl` (custom queries), `InstrumentStocksRepository` (CrudRepository)
- **Stock**: `StockRepository`, `InstrumentStocksRepository` (both CrudRepository)

### 3. Circuit Breaker (Hystrix)
- **Where**: Shop module only
- **Implementation**: `@HystrixCommand(fallbackMethod = "...")` on REST client methods
- **Locations**: `InstrumentRepo.getinstrumentDTOs()`, `StockRepo.getStockDTOs()`, `StockRepo.getInstrumentStockDTOs()`
- **Fallbacks**: Return `Collections.emptyMap()` for graceful degradation
- **Note**: Netflix Hystrix is deprecated; pattern should migrate to Resilience4j

### 4. DTO (Data Transfer Object)
- **Where**: Shop module
- **Classes**: `InstrumentDTO`, `ProductDTO`, `StockDTO`
- **Purpose**: Decouple internal domain models from external service responses
- **Default instance**: `StockDTO.DEFAULT_STOCK_DTO`, `InstrumentDTO.DEFAULT_INSTRUMENT_DTO`

### 5. Builder Pattern (Informal)
- **Where**: `shop/model/Instrument.java`
- **Methods**: `buildIt()`, `buildForLocale()` — return `this` for fluent-style construction
- **Note**: Not a formal Builder pattern (no separate Builder class), but provides builder-like API

### 6. Singleton Pattern
- **Where**: Shop module
- **Classes**: `Exercises` (`private static final Exercises s_instance = new Exercises()`), `PropertiesUpdater` (`public static final PropertiesUpdater s_instance = new PropertiesUpdater()`)
- **Purpose**: Manage shared state (exercise scores, trace counts) across requests
- **Note**: Eager initialization singletons, not thread-safe for property modifications

### 7. Service Layer Pattern
- **Where**: All Spring Boot modules
- **Shop**: `ProductService`, `InstrumentService` (with @Service annotation)
- **Instruments**: `InstrumentService`, `InstrumentStocksService`
- **Stock**: `StockService`, `InstrumentStocksService`
- **Note**: Products and Conductors modules do NOT use @Service — services are manually instantiated

### 8. Visitor Pattern
- **Where**: Annotator module
- **Implementation**: `OtelAnnotationVisitor extends VoidVisitorAdapter<Void>` — visits `MethodDeclaration` AST nodes to add annotations
- **Usage**: JavaParser's visitor API for AST traversal and modification

### 9. Observer/Event Listener Pattern
- **Where**: Instruments module
- **Implementation**: `@EventListener(ApplicationReadyEvent.class)` on `InstrumentsApplication.runAfterStartup()`
- **Purpose**: Count and log instruments after application startup

---

## Anti-Patterns Identified

| Anti-Pattern | Location | Description |
|-------------|----------|-------------|
| **God Class** | `ProductFilterService` (products) | ~600 lines with 30+ nearly identical methods |
| **Empty Catch Block** | ProductFilterService (both modules) | 60+ occurrences silently swallowing exceptions |
| **Service Locator** | `ProductController`, `ConductorsController` | Manual `new` instantiation instead of DI |
| **Magic Numbers** | `Exercises`, `ProductFilterService` | Hardcoded values (180, 999, 966) without named constants |
| **Cartesian Product** | `FindInstrumentRepositoryImpl` | Unjoined multi-table query |

---

## Related Documents

- [Components](components.md) — Class-level details
- [Business Logic](../behavior/business-logic.md) — Pattern implementations in business context
- [Maintenance Burden](../technical-debt/maintenance-burden.md) — Anti-pattern impacts
