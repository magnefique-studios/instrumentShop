> ⚠️ **Early Access**: Behavior documentation is in early access. Please review critically.

# Error Handling

[← Back to README](../README.md) | [Related: Business Logic](business-logic.md) | [Workflows](workflows.md) | [Decision Logic](decision-logic.md)

## Overview

This document catalogs all exception types, error handling patterns, and recovery mechanisms in the application.

---

## Custom Exception Classes

| Exception | Module | Package | Extends | Usage |
|-----------|--------|---------|---------|-------|
| `InvalidLocaleException` | shop | `com.shabushabu.javashop.shop.exceptions` | `Exception` | Locale validation failures |
| `InvalidLocaleException` | products | `com.shabushabu.javashop.products.exceptions` | `Exception` | Locale validation (unused) |
| `InvalidLocaleException` | conductors | `com.shabushabu.javashop.conductors.exceptions` | `Exception` | Oregon locale disabled |
| `InvalidLocaleException` | instruments | `com.shabushabu.javashop.instruments.exceptions` | `Exception` | Oregon locale disabled |
| `InstrumentNotFoundException` | instruments | `com.shabushabu.javashop.instruments.exceptions` | `Exception` | Instrument not found (404) |
| `StockNotFoundException` | stock | `com.shabushabu.javashop.stock.exceptions` | `Exception` | Stock not found (404) |

All custom exceptions follow the same pattern with constructors for: no-args, message, cause, message+cause. `InvalidLocaleException` in shop also has a 4-arg constructor with `enableSuppression` and `writableStackTrace`.

---

## Hystrix Circuit Breaker Fallbacks (Shop Module)

| Method | Fallback Method | Behavior | Location |
|--------|----------------|----------|----------|
| `InstrumentRepo.getinstrumentDTOs()` | `instrumentsNotFound()` | Returns `Collections.emptyMap()` | `InstrumentRepo.java:35` |
| `StockRepo.getStockDTOs()` | `stocksNotFound()` | Returns `Collections.emptyMap()` | `StockRepo.java:35` |
| `StockRepo.getInstrumentStockDTOs()` | `stocksNotFound()` | Returns `Collections.emptyMap()` | `StockRepo.java:46` |

Hystrix is enabled via `@EnableHystrix` on `JavaShopApp`.

---

## Exception Handler Annotations

| Controller | Exception Type | HTTP Status | Location |
|------------|---------------|-------------|----------|
| `InstrumentResource` | `InstrumentNotFoundException` | 404 NOT_FOUND | `InstrumentResource.java:56` |
| `StockResource` | `StockNotFoundException` | 404 NOT_FOUND | `StockResource.java:50` |

Both use `@ExceptionHandler` + `@ResponseStatus(HttpStatus.NOT_FOUND)` with void handler methods.

---

## Try-Catch Patterns

### Empty Catch Blocks (Anti-pattern)

The codebase contains numerous empty catch blocks, primarily in `ProductFilterService` (both products and conductors modules):

| Location | Pattern | Risk |
|----------|---------|------|
| `ProductFilterService` (products) — ~30+ occurrences | `try { Thread.sleep(x); } catch (Exception e) { }` | Silently swallows `InterruptedException`, loses thread interrupt status |
| `ProductFilterService` (conductors) — ~30+ occurrences | `try { Thread.sleep(x); } catch (Exception e) { }` | Same as above |
| `ConductorsController.getProductsByLocation()` | `try { products.filterProducts(location); } catch(Exception e) { }` | Silently swallows `InvalidLocaleException` — deliberately for Oregon locale |

### Logged/Handled Catch Blocks

| Location | Exception | Handling |
|----------|-----------|----------|
| `InstrumentService.getInstruments()` | `Exception` (from locale filter) | Logs error: "Locale Filter Failed on {location}" |
| `InstrumentService (shop)` — `getInstruments()` lambda | `InvalidLocaleException` | Prints stack trace, falls back to `buildIt()` |
| `Exercises` constructor | `IOException` | Logs "Failed to read properties file" |
| `Exercises.checkExercise2()` | `IOException` (HTTP call) | Prints stack trace or returns false for 403 |
| `PropertiesUpdater` constructor | `IOException` | Logs "Failed to read properties file" |
| `PropertiesUpdater.storeTheScores()` | `IOException` | Logs "Failed to write properties file" |
| `GenerateTraffic.main()` | `Exception` (HTTP calls) | Prints stack trace |

### Permission Check

| Location | Exception | Condition |
|----------|-----------|-----------|
| `HomeController.allParameters()` | `NoPermissionException` | When `checkIfRestricted(userid)` returns true (currently never) |

---

## Error Recovery Strategies

| Strategy | Implementation | Module |
|----------|---------------|--------|
| **Circuit Breaker** | Hystrix `@HystrixCommand` with fallback methods returning empty collections | shop |
| **Default Values** | `StockDTO.DEFAULT_STOCK_DTO` when stock not found for product | shop |
| **Fallback Builder** | `Instrument.buildIt()` when `buildForLocale()` throws InvalidLocaleException | shop |
| **Empty Collection** | Return empty list when locale filter returns false | instruments |
| **Silent Swallow** | Empty catch blocks throughout filter services | products, conductors |
| **Properties Fallback** | Return early/return true when properties file cannot be read | shop (Exercises) |

---

## Notable Error Handling Issues

1. **Empty catch blocks**: ~60+ instances across ProductFilterService in both products and conductors modules. These silently swallow `InterruptedException`, which can cause thread interrupt status to be lost.

2. **Overly broad exception catching**: Many catches use `Exception` instead of specific types (`InterruptedException`, `IOException`).

3. **Conductor's Oregon exception**: `ConductorsController` hardcodes location to "Oregon", which always triggers `InvalidLocaleException` from `FilteredProducts`. This exception is silently caught — the controller still returns products despite the "error".

4. **Null return in InstrumentService**: When Chicago query returns null or non-List, `getInstruments()` returns `null` instead of an empty list, which could cause `NullPointerException` downstream.

---

## Related Documents

- [Business Logic](business-logic.md) — Business rules that trigger exceptions
- [Decision Logic](decision-logic.md) — Conditional branches around error handling
- [Security Patterns](../analysis/security-patterns.md) — Security-related error handling
- [Technical Debt Report](../technical-debt-report.md) — Error handling anti-patterns as tech debt
