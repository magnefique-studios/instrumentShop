# Dependency Analysis

[← Back to README](../README.md) | [Code Metrics](code-metrics.md) | [Complexity Analysis](complexity-analysis.md) | [Security Patterns](security-patterns.md)

## Internal Dependency Graph

```
                    ┌──────────┐
                    │  test    │
                    │(traffic) │
                    └────┬─────┘
                         │ HTTP
                    ┌────▼─────┐
        ┌──────────►│  shop    │◄──────────┐
        │           │ (8010)   │           │
        │           └─┬──┬──┬─┘           │
        │             │  │  │             │
        │    ┌────────┘  │  └────────┐    │
        │    │           │           │    │
   ┌────▼──▼┐  ┌───────▼───────┐ ┌─▼────▼───┐
   │products│  │  instruments  │ │  stock   │
   │ (8020) │  │   (8040)     │ │  (8030)  │
   └────────┘  └──────┬───────┘ └──────────┘
                      │ JDBC
               ┌──────▼───────┐
               │  postgresDB  │
               │   (5432)     │
               └──────────────┘

   ┌──────────┐
   │conductors│  (called by shop only when location=Utah)
   │ (8050)   │
   └──────────┘

   ┌──────────┐   ┌──────────┐
   │ annotator│   │  redis   │  (declared in docker-compose,
   │ (offline)│   │          │   no application code usage found)
   └──────────┘   └──────────┘
```

## Dependency Coupling Analysis

| Service | Inbound Dependencies | Outbound Dependencies | Coupling Level |
|---------|---------------------|----------------------|----------------|
| shop | test | products, conductors, stock, instruments | High (4 outbound) |
| products | shop | none | Low (leaf service) |
| conductors | shop (Utah only) | none | Low (leaf service) |
| stock | shop | none (H2 embedded) | Low (leaf service) |
| instruments | shop | postgresDB | Medium (1 external DB) |
| postgresDB | instruments | none | Low (data store) |
| test | none | shop | Low |
| annotator | none (offline tool) | none | None |

## External Dependency Risk Assessment

| Dependency | Module(s) | Risk Level | Reason |
|-----------|-----------|------------|--------|
| Log4j 2.6.1 | test | High | CVE-2021-44228 (CVSS 10.0) |
| Spring Boot 1.5.19 | shop | High | EOL, no security patches |
| Spring Boot 2.1.3 | stock | High | EOL, no security patches |
| Netflix Hystrix | shop | High | Deprecated, unmaintained |
| Spring Cloud Dalston | shop | High | EOL, tied to Spring Boot 1.x |
| Spring Boot 2.7.5 | instruments | Medium | EOL Nov 2023 |
| commons-httpclient 3.1 | test | Medium | Deprecated library |
| Cucumber info.cukes | stock | Medium | Deprecated groupId |
| OTel alpha annotations | shop, instruments, annotator | Low | Pre-release, API may change |

---

## Related Documents

- [Architecture Dependencies](../architecture/dependencies.md) — Complete version listing
- [Outdated Components](../technical-debt/outdated-components.md) — EOL analysis
- [Security Patterns](security-patterns.md) — Security concerns
