# Modules

[← Back to README](../README.md) | [Program Structure](program-structure.md) | [Dependencies](../architecture/dependencies.md)

## Module Organization

The project uses a Maven multi-module structure with a root POM aggregator.

```
javashop (root POM - com.splunk:javashop)
├── shop       (com.shabushabu.javashop:javashop.shop)
├── stock      (com.shabushabu.javashop:javashop.stock)
├── products   (com.shabushabu.javashop:javashop.products)
├── instruments(com.shabushabu.javashop:javashop.instruments)
├── conductors (com.shabushabu.javashop:javashop.conductors)
├── annotator  (com.splunk.otel:annotator)
└── test       (com.shabushabu.javashop:javashop.tester)
```

## Module Details

| Module | Type | Parent | Packaging | Has Tests |
|--------|------|--------|-----------|-----------|
| root | Aggregator POM | none | pom | No |
| shop | Spring Boot Web App | spring-boot-starter-parent 1.5.19 | jar | Yes (empty) |
| products | Spring Boot Web App | spring-boot-starter-parent 3.2.2 | jar | No |
| conductors | Spring Boot Web App | spring-boot-starter-parent 3.2.2 | jar | No |
| instruments | Spring Boot Web App | spring-boot-starter-parent 2.7.5 | jar | No |
| stock | Spring Boot Web App | spring-boot-starter-parent 2.1.3 | jar | No |
| annotator | Standalone Java App | none | jar | No |
| test | Standalone Java App | none | jar | No |

## Inter-Module Dependencies (Runtime via HTTP)

| From | To | Protocol | Endpoints Called |
|------|----|----------|----------------|
| shop | products | HTTP/REST | `GET /products?location=` |
| shop | conductors | HTTP/REST | `GET /conductors?location=` (Utah only) |
| shop | stock | HTTP/REST | `GET /legacy`, `GET /instrumemnts` |
| shop | instruments | HTTP/REST | `GET /instruments?location=` |
| instruments | postgresDB | JDBC | PostgreSQL connection |
| test | shop | HTTP | `GET /?name=&location=&userid=` |

**Note**: There are no compile-time Maven dependencies between the application modules. All inter-module communication is via HTTP REST at runtime.

---

## Related Documents

- [Architecture Dependencies](../architecture/dependencies.md) — Full dependency listing
- [Component Diagrams](../diagrams/structural/component-diagrams.md) — Visual module relationships
- [Deployment Configuration](../specialized/deployment-configuration.md) — Docker networking
