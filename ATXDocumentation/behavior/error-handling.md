> ⚠️ **Early Access**: Behavior documentation is in early access. Please review critically.

# Error Handling

## Circuit Breaker Fallbacks (Shop Module)

### InstrumentRepo Hystrix Fallback
- **Location**: `shop/src/main/java/.../repo/InstrumentRepo.java` line 35
- **Trigger**: Any exception when calling `GET instruments:8040/instruments`
- **Fallback**: `instrumentsNotFound()` → returns `Collections.emptyMap()`
- **Logging**: `LOGGER.info("Instruments Empty NOT FOUND *** FALLBACK ***")`

### StockRepo Hystrix Fallback
- **Location**: `shop/src/main/java/.../repo/StockRepo.java` lines 37, 47
- **Trigger**: Any exception when calling `GET stock:8030/legacy` or `/instrumemnts`
- **Fallback**: `stocksNotFound()` → returns `Collections.emptyMap()`
- **Logging**: `LOGGER.info("stocksNotFound *** FALLBACK ***")`

**Note**: ProductRepo does NOT have a Hystrix fallback, so product service failures will propagate as exceptions.

## Custom Exceptions

### InvalidLocaleException
- **Defined in**: `shop`, `instruments`, `conductors` (each module has its own copy)
- **Thrown by**:
  - `FilteredInstrument.filterInstruments("Oregon")` — instruments module
  - `FilteredProducts.filterProducts("Oregon")` — conductors module
  - `Instrument.buildForLocale()` — shop module (currently commented out)
- **Caught by**:
  - `InstrumentService.getInstruments()` (instruments) — catches and logs error, returns empty list
  - `ConductorsController.getProductsByLocation()` (conductors) — silently caught in empty catch block
  - `InstrumentService.getInstruments()` (shop) — catches and falls back to `buildIt()` (without title)

### InstrumentNotFoundException
- **Defined in**: instruments module
- **Handler**: `InstrumentResource` has `@ExceptionHandler` returning HTTP 404
- **Thrown by**: Not currently thrown by any active code (related methods are commented out)

### StockNotFoundException
- **Defined in**: stock module
- **Handler**: `StockResource` has `@ExceptionHandler` returning HTTP 404
- **Thrown by**: Not currently thrown (related `getStock()` method is commented out)

### NoPermissionException
- **Defined in**: `javax.naming.NoPermissionException` (standard Java)
- **Thrown by**: `HomeController.allParameters()` if `checkIfRestricted()` returns true
- **Current State**: Never thrown (`checkIfRestricted()` always returns false)

## Silent Exception Handling

### ProductFilterService (products module)
- **Location**: All `myCoolFunction*()` methods
- **Pattern**: `try { Thread.sleep(n); } catch (Exception e) { }`
- **Issue**: All exceptions are silently swallowed with empty catch blocks

### ConductorsController
- **Location**: `conductors/src/main/java/.../controllers/ConductorsController.java` line 27
- **Pattern**: `try { products.filterProducts(location); } catch(Exception e) { }`
- **Issue**: `InvalidLocaleException` from Oregon filter is silently caught

### InstrumentService (instruments module)
- **Location**: `instruments/src/main/java/.../services/InstrumentService.java` line 38
- **Pattern**: Catches exception from `filterInstruments()`, logs error, continues execution
- **Behavior**: `s_logger.error("Locale Filter Failed on " + location)`

## Error Recovery Patterns

| Pattern | Module | Behavior |
|---------|--------|----------|
| Hystrix Fallback | shop | Return empty collections on service failure |
| Silent Catch | products, conductors | Swallow all exceptions |
| Log and Continue | instruments | Log error, continue with default behavior |
| Exception Handler | stock, instruments | Return HTTP 404 for not-found exceptions |
| Default Values | shop | Use DEFAULT_STOCK_DTO when stock not found |
| Fallback Build | shop (InstrumentService) | Use `buildIt()` instead of `buildForLocale()` on locale error |

## Cross-References

- [Business Logic](business-logic.md) | [Workflows](workflows.md) | [Decision Logic](decision-logic.md)
- [Security Patterns](../analysis/security-patterns.md)
