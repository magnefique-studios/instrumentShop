# Component Migration Order

## Recommended Migration Sequence

Based on dependency analysis and complexity assessment, the recommended order for upgrading modules to a modern, unified Spring Boot 3.x / Java 17+ stack is:

### Phase 1: Independent Backend Services (Lowest Risk)

#### 1. Stock Module
- **Current**: Spring Boot 2.1.3 / Java 8
- **Target**: Spring Boot 3.x / Java 17+
- **Rationale**: Simplest module (256 LOC, 9 classes), standalone with H2 database, no downstream dependents
- **Key Changes**:
  - Java 8 → 17
  - javax.persistence → jakarta.persistence
  - javax.validation → jakarta.validation
  - javax.annotation.PostConstruct → jakarta.annotation.PostConstruct
  - Update Cucumber from info.cukes 1.2.5 to io.cucumber 7.x
  - Fix endpoint typo: `/insruments` → `/instruments`
- **Complexity**: Low

#### 2. Instruments Module
- **Current**: Spring Boot 2.7.5 / Java 17
- **Target**: Spring Boot 3.x / Java 17+
- **Rationale**: Already on Java 17, closest to Spring Boot 3.x. Depends only on PostgreSQL (external).
- **Key Changes**:
  - Spring Boot 2.7 → 3.x
  - javax.persistence → jakarta.persistence
  - javax.annotation → jakarta.annotation
  - Fix SQL injection in `FindInstrumentRepositoryImpl`
  - Fix Cartesian join in `findInstruments()`
  - Update OpenTelemetry annotations to 2.x stable
- **Complexity**: Medium

### Phase 2: Already-Modern Services (Validation Only)

#### 3. Products Module
- **Current**: Spring Boot 3.2.2 / Java 17
- **Target**: Verify compatibility, update minor versions
- **Rationale**: Already on Spring Boot 3.x, minimal changes needed
- **Key Changes**:
  - Verify OpenTelemetry annotations 2.2.0 compatibility
  - Consider Spring DI for ProductService/ProductFilterService (currently `new`)
  - Remove or refactor intentional Thread.sleep() methods
- **Complexity**: Low

#### 4. Conductors Module
- **Current**: Spring Boot 3.2.2 / Java 17
- **Target**: Verify compatibility, fix bugs
- **Rationale**: Already on Spring Boot 3.x
- **Key Changes**:
  - Remove hardcoded `location = "Oregon"` override
  - Consider consolidating with Products module
- **Complexity**: Low

### Phase 3: Gateway Service (Highest Complexity)

#### 5. Shop Module
- **Current**: Spring Boot 1.5.19 / Java 8
- **Target**: Spring Boot 3.x / Java 17+
- **Rationale**: Most complex module with most dependencies. Must be migrated last because it depends on all other services.
- **Key Changes**:
  - Java 8 → 17
  - Spring Boot 1.5 → 3.x (largest version jump)
  - Spring Cloud Dalston → 2022.x+
  - Replace Hystrix with Resilience4j / Spring Cloud Circuit Breaker
  - Replace `@EnableHystrix` with `@EnableCircuitBreaker` (Resilience4j)
  - Replace `@HystrixCommand` with `@CircuitBreaker`
  - Upgrade Thymeleaf (remove LEGACYHTML5 mode, drop nekohtml)
  - Upgrade/replace Log4j 2.6.1 (critical security)
  - Remove `spring-cloud-starter-eureka` (not used at runtime)
  - Update OpenTelemetry annotations to 2.x stable
  - Migrate javax.naming.NoPermissionException usage
- **Complexity**: High

### Phase 4: Utility Modules (Independent)

#### 6. Annotator Module
- **Current**: Java 17, no Spring Boot
- **Target**: Update dependencies
- **Key Changes**:
  - Update JavaParser 3.23.1 → 3.26+
  - Update OpenTelemetry annotations to 2.x stable
- **Complexity**: Low

#### 7. Test Module
- **Current**: Java 11, no Spring Boot
- **Target**: Java 17, update dependencies
- **Key Changes**:
  - Java 11 → 17
  - Replace Log4j 2.6.1 (critical security)
  - Replace commons-httpclient 3.1 with modern HTTP client
- **Complexity**: Low

## Dependency Graph for Migration

```
Phase 4: annotator ─── test
                         │
Phase 3:    shop ◀───────┤ (depends on Phase 1 & 2 services)
              │          │
Phase 2: products  conductors
              │
Phase 1:  stock  instruments ──▶ PostgreSQL
```

## Cross-References

- [Test Specifications](test-specifications.md) | [Validation Criteria](validation-criteria.md)
- [Remediation Plan](../technical-debt/remediation-plan.md)
