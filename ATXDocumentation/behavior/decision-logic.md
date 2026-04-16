> ⚠️ **Early Access**: Behavior documentation is in early access. Please review critically.

# Decision Logic

[← Back to README](../README.md) | [Related: Business Logic](business-logic.md) | [Workflows](workflows.md) | [Error Handling](error-handling.md)

## Overview

This document catalogs all decision points found in the application code.

---

## Shop Module Decision Points

### HomeController.getProductsAllLocations()
| # | Condition | True Branch | False Branch | Location |
|---|-----------|-------------|--------------|----------|
| 1 | `name == null` | name = "Guest" | use provided name | `HomeController.java:44` |
| 2 | `location == null` | location = "California" | use provided location | `HomeController.java:48` |
| 3 | `userid == null` | userid = "X0000" | use provided userid | `HomeController.java:52` |
| 4 | `checkIfRestricted(userid)` | throw NoPermissionException | continue | `HomeController.java:100` |
| 5 | `location == "Utah"` (case-insensitive) | update s_utahLatency if > current; reset s_coloradoLatency=0 | check Colorado | `HomeController.java:68` |
| 6 | `location == "Colorado"` (case-insensitive) | update s_coloradoLatency if > current | skip latency tracking | `HomeController.java:75` |

### HomeController.getScores()
| # | Condition | True Branch | False Branch | Location |
|---|-----------|-------------|--------------|----------|
| 1 | `exercise == null` | exercise = "0" | use provided | `HomeController.java:86` |
| 2 | `data == null` | data = "" | use provided | `HomeController.java:90` |
| 3 | `iExercise == 0` | return all scores | check individual exercise | `HomeController.java:94` |

### ProductRepo.getProductDTOs()
| # | Condition | True Branch | False Branch | Location |
|---|-----------|-------------|--------------|----------|
| 1 | `bConductorsEnabled && "Utah".equalsIgnoreCase(location)` | route to conductors service | route to products service | `ProductRepo.java:39` |

### Exercises.checkExercise() (switch on exercise 1–15)
| Exercise | Validation Logic |
|----------|-----------------|
| 1 | `checkExercise2(controller, data)` — test sending metric with user-entered access code |
| 2 | `checkExercise2(controller, "")` — test sending metric using .env access code |
| 3 | `checkExercise3(controller)` — traces sent ≤ MIN_TRACES_EXPECTED (180) |
| 4 | `checkExercise4(controller)` — Colorado latency > Utah latency × 1.2 |
| 5 | `checkExercise5(controller)` — `checkIfRestricted("C0000010")` |
| 6 | `data.contains("Authorization")` |
| 7 | `(data.contains("Shop") or "shop") AND data.contains("Products") or "products"` |
| 8 | `data.contains("Not")` |
| 9 | `data.contains("getAllProducts")` |
| 10 | NOT contains "getAllProducts" AND (contains "myCool" OR "lookup") |
| 11 | `checkExercise11(controller)` — properties "Annotated" == "true" |
| 12 | `checkExercise3(controller)` — same as exercise 3 |
| 13 | `data.contains("myCoolFunction234234234")` |
| 14 | `data.contains("@SpanAttribute")` |
| 15 | NOT `checkExercise4(controller)` — inverse of exercise 4 |

---

## Products Module Decision Points

### ProductController.getProductsByLocation()
| # | Condition | True Branch | False Branch | Location |
|---|-----------|-------------|--------------|----------|
| 1 | `location == null` | location = "California" | use provided | `ProductController.java:23` |

### ProductFilterService.locationLookup11()
| # | Condition | True Branch | False Branch | Location |
|---|-----------|-------------|--------------|----------|
| 1 | `location.equalsIgnoreCase("Colorado")` | call `myCoolFunction234234234(getMyInt(location))` — sleep 966–1166ms | return sleepy=1 | `ProductFilterService.java` |

### ProductFilterService.myCoolFunction234234234()
| # | Condition | True Branch | False Branch | Location |
|---|-----------|-------------|--------------|----------|
| 1 | `999 == myInt` | sleep random 966–1166ms | no sleep | `ProductFilterService.java` |

### ProductFilterService.lookupLocation4()
| # | Condition | True Branch | False Branch | Location |
|---|-----------|-------------|--------------|----------|
| 1 | `location.equalsIgnoreCase("California")` | sleepy=4 | sleepy=2 | `ProductFilterService.java` |

---

## Conductors Module Decision Points

### ConductorsController.getProductsByLocation()
| # | Condition | True Branch | False Branch | Location |
|---|-----------|-------------|--------------|----------|
| 1 | Always | location hardcoded to "Oregon" | N/A | `ConductorsController.java:24` |

### FilteredProducts.filterProducts()
| # | Condition | True Branch | False Branch | Location |
|---|-----------|-------------|--------------|----------|
| 1 | `"Oregon".equalsIgnoreCase(locale)` | check s_Localedisabled | return true | `FilteredProducts.java:20` |
| 2 | `s_Localedisabled` (always true) | throw InvalidLocaleException | log and return true | `FilteredProducts.java:22` |

---

## Instruments Module Decision Points

### InstrumentService.getInstruments()
| # | Condition | True Branch | False Branch | Location |
|---|-----------|-------------|--------------|----------|
| 1 | `!fInstrument.filterInstruments(location)` | return empty list | continue | `InstrumentService.java:38` |
| 2 | `location.equalsIgnoreCase("Chicago")` | execute cartesian product query | use standard findAll() | `InstrumentService.java:45` |
| 3 | `obj == null or !(obj instanceof List)` | return null | return findAll() results | `InstrumentService.java:48` |

### FilteredInstrument.filterInstruments()
| # | Condition | True Branch | False Branch | Location |
|---|-----------|-------------|--------------|----------|
| 1 | `"Oregon".equalsIgnoreCase(locale)` | check s_Localedisabled | return true | `FilteredInstrument.java:20` |
| 2 | `s_Localedisabled` (always true) | throw InvalidLocaleException | log and return true | `FilteredInstrument.java:24` |

---

## Stock Module Decision Points

The stock module has minimal decision logic — it primarily serves data from its H2 database via CRUD repositories.

---

## Test Module Decision Points

### GenerateTraffic.main()
| # | Condition | True Branch | False Branch | Location |
|---|-----------|-------------|--------------|----------|
| 1 | `firstTimeAutoBoot` | set FirstRun=False, write props, return | continue with traffic | `GenerateTraffic.java:30` |
| 2 | `args[0] == "-chicago"` | chicago=true | chicago=false | `GenerateTraffic.java:49` |
| 3 | `!chicago` | send 40 Colorado requests | send 2 Chicago requests | `GenerateTraffic.java:98` |

---

## Related Documents

- [Business Logic](business-logic.md) — Full business rules per module
- [Workflows](workflows.md) — Process flows
- [Error Handling](error-handling.md) — Exception patterns
- [Security Patterns](../analysis/security-patterns.md) — SQL injection in FindInstrumentRepositoryImpl
