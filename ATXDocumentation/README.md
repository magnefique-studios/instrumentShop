# ATXDocumentation — InstrumenT-ation Shop (JavaShop)

## 🎯 Quick Navigation

- **[Technical Debt Report](technical-debt-report.md)** — ⚠️ Start here for AWS transformation recommendation and critical findings
- **[Project Overview](project-overview.md)** — Executive summary of the project

---

## Table of Contents

### Root Documents
| Document | Description |
|----------|-------------|
| [README.md](README.md) | This file — master navigation |
| [project-overview.md](project-overview.md) | Executive summary, tech stack, key findings |
| [technical-debt-report.md](technical-debt-report.md) | **AWS Transformation Recommendation** + prioritized debt summary |

### Architecture (`architecture/`)
| Document | Description |
|----------|-------------|
| [system-overview.md](architecture/system-overview.md) | Technology stack, module overview, deployment model |
| [components.md](architecture/components.md) | All classes per module with responsibilities |
| [dependencies.md](architecture/dependencies.md) | Internal service graph + all external library versions |
| [patterns.md](architecture/patterns.md) | MVC, Repository, Circuit Breaker, DTO, Builder, Singleton, Visitor |

### Behavior (`behavior/`) *(Early Access)*
| Document | Description |
|----------|-------------|
| [business-logic.md](behavior/business-logic.md) | Business rules for all 7 modules |
| [workflows.md](behavior/workflows.md) | 8 application-level workflows |
| [decision-logic.md](behavior/decision-logic.md) | All conditional branches in application code |
| [error-handling.md](behavior/error-handling.md) | Exception classes, Hystrix fallbacks, error patterns |

### Technical Debt (`technical-debt/`)
| Document | Description |
|----------|-------------|
| [summary.md](technical-debt/summary.md) | Consolidated debt overview with counts |
| [outdated-components.md](technical-debt/outdated-components.md) | Detailed version analysis for all dependencies |
| [maintenance-burden.md](technical-debt/maintenance-burden.md) | Mixed frameworks, code duplication, anti-patterns |
| [remediation-plan.md](technical-debt/remediation-plan.md) | Phased upgrade plan with priorities |

### Reference (`reference/`)
| Document | Description |
|----------|-------------|
| [program-structure.md](reference/program-structure.md) | Complete class hierarchy for all 58 Java files |
| [interfaces.md](reference/interfaces.md) | All REST endpoints with parameters |
| [data-models.md](reference/data-models.md) | JPA entities, DTOs, POJOs with field types |
| [api-reference.md](reference/api-reference.md) | Full HTTP API specification per service |
| [modules.md](reference/modules.md) | Maven module organization and inter-dependencies |

### Analysis (`analysis/`)
| Document | Description |
|----------|-------------|
| [code-metrics.md](analysis/code-metrics.md) | LOC, class counts, method counts, test coverage |
| [complexity-analysis.md](analysis/complexity-analysis.md) | Module complexity rankings, code smells |
| [dependency-analysis.md](analysis/dependency-analysis.md) | Dependency graph, coupling analysis, risk assessment |
| [security-patterns.md](analysis/security-patterns.md) | SQL injection, Log4j CVE, credential handling |
| [tech-debt.md](analysis/tech-debt.md) | Cross-reference to technical debt files |

### Diagrams (`diagrams/`)
| Document | Description |
|----------|-------------|
| [structural/component-diagrams.md](diagrams/structural/component-diagrams.md) | Service architecture, class dependencies, packages |
| [behavioral/sequence-diagrams.md](diagrams/behavioral/sequence-diagrams.md) | Request flows: California, Utah, Colorado, Chicago |
| [architecture/system-context.md](diagrams/architecture/system-context.md) | System boundaries, communication patterns, ports |

### Migration (`migration/`)
| Document | Description |
|----------|-------------|
| [component-order.md](migration/component-order.md) | Dependency-based migration sequence (4 phases) |
| [test-specifications.md](migration/test-specifications.md) | 12 test cases for validation |
| [validation-criteria.md](migration/validation-criteria.md) | Build, runtime, functional, security criteria |

### Specialized (`specialized/`)
| Document | Description |
|----------|-------------|
| [database-schemas.md](specialized/database-schemas.md) | PostgreSQL tables + H2 schema + seeded data |
| [deployment-configuration.md](specialized/deployment-configuration.md) | Docker Compose services, ports, volumes, scripts |

---

## Component Index

| Component | Module | Key File | Documentation |
|-----------|--------|----------|---------------|
| HomeController | shop | `shop/src/main/java/.../controllers/HomeController.java` | [Components](architecture/components.md) |
| ProductService | shop | `shop/src/main/java/.../services/ProductService.java` | [Business Logic](behavior/business-logic.md) |
| InstrumentService | shop | `shop/src/main/java/.../services/InstrumentService.java` | [Business Logic](behavior/business-logic.md) |
| ProductRepo | shop | `shop/src/main/java/.../repo/ProductRepo.java` | [Decision Logic](behavior/decision-logic.md) |
| InstrumentRepo | shop | `shop/src/main/java/.../repo/InstrumentRepo.java` | [Error Handling](behavior/error-handling.md) |
| Exercises | shop | `shop/src/main/java/.../Exercises.java` | [Decision Logic](behavior/decision-logic.md) |
| ProductFilterService | products | `products/src/main/java/.../services/ProductFilterService.java` | [Complexity Analysis](analysis/complexity-analysis.md) |
| InstrumentService | instruments | `instruments/src/main/java/.../services/InstrumentService.java` | [Workflows](behavior/workflows.md) |
| FindInstrumentRepositoryImpl | instruments | `instruments/src/main/java/.../repositories/FindInstrumentRepositoryImpl.java` | [Security Patterns](analysis/security-patterns.md) |
| DataGenerator | stock | `stock/src/main/java/.../config/DataGenerator.java` | [Database Schemas](specialized/database-schemas.md) |
| OpenTelemetryAnnotator | annotator | `annotator/src/main/java/.../OpenTelemetryAnnotator.java` | [Patterns](architecture/patterns.md) |
| GenerateTraffic | test | `test/src/main/java/GenerateTraffic.java` | [Workflows](behavior/workflows.md) |
