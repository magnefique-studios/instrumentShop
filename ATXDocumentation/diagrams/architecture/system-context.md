# Architecture Diagrams

## System Context Diagram

```
┌──────────────────────────────────────────────────────────────────────┐
│                          External Context                             │
│                                                                       │
│  ┌───────────┐                                       ┌─────────────┐ │
│  │  Browser   │          HTTP :8010                  │   Redis     │ │
│  │  (User)    │──────────────────────┐               │  (unused)   │ │
│  └───────────┘                       │               └─────────────┘ │
│                                      │                                │
│  ┌───────────┐                       │                                │
│  │  Traffic   │     HTTP :8010       │                                │
│  │  Generator │──────────────────────┤                                │
│  │  (test)    │                      │                                │
│  └───────────┘                       │                                │
│                                      │                                │
│                     ┌────────────────▼──────────────────────┐        │
│                     │         JavaShop System                │        │
│                     │                                        │        │
│                     │  ┌──────┐ ┌─────┐ ┌─────┐ ┌────────┐ │        │
│                     │  │ Shop │ │Prod │ │Stock│ │Instrmts│ │        │
│                     │  │:8010 │ │:8020│ │:8030│ │ :8040  │ │        │
│                     │  └──────┘ └─────┘ └─────┘ └───┬────┘ │        │
│                     │  ┌──────────┐                  │      │        │
│                     │  │Conductors│                   │      │        │
│                     │  │  :8050   │                   │      │        │
│                     │  └──────────┘                   │      │        │
│                     └────────────────────────────────┼──────┘        │
│                                                      │                │
│                                               ┌──────▼──────┐        │
│                                               │ PostgreSQL  │        │
│                                               │   :5432     │        │
│                                               └─────────────┘        │
└──────────────────────────────────────────────────────────────────────┘
```

## Service Map with Ports

```
                    ┌─────────────────────────────┐
                    │       Shop (Gateway)        │
                    │     localhost:8010           │
                    │  Spring Boot 1.5.19/Java 8  │
                    └──┬──────┬──────┬─────────┬──┘
                       │      │      │         │
          ┌────────────┘      │      │         └────────────┐
          │                   │      │                      │
    ┌─────▼──────┐    ┌──────▼────┐ ┌▼────────────┐  ┌─────▼────────┐
    │  Products  │    │Conductors │ │    Stock     │  │ Instruments  │
    │ :8020      │    │  :8050    │ │   :8030      │  │   :8040      │
    │ SB 3.2.2   │    │ SB 3.2.2 │ │  SB 2.1.3   │  │  SB 2.7.5   │
    │ Java 17    │    │ Java 17  │ │  Java 8      │  │  Java 17     │
    │ In-Memory  │    │ In-Memory│ │  H2 DB       │  │  JPA/Hibern. │
    └────────────┘    └──────────┘ └──────────────┘  └──────┬───────┘
                                                            │ JDBC
                                                     ┌──────▼───────┐
                                                     │  PostgreSQL  │
                                                     │    :5432     │
                                                     │  13.1-alpine │
                                                     └──────────────┘
```

## Security Boundaries

```
┌──────────────────────────────────────────────────────────┐
│                     PUBLIC ACCESS                          │
│                 (No Authentication)                        │
│                                                           │
│  All endpoints on all services are publicly accessible    │
│  No Spring Security configured                            │
│  No HTTPS/TLS                                             │
│                                                           │
│  ┌─────────────────────────────────────────────────────┐ │
│  │              Docker Network (internal)                │ │
│  │                                                       │ │
│  │  Services communicate over plain HTTP                 │ │
│  │  PostgreSQL credentials hardcoded in docker-compose   │ │
│  │  Database port (5432) exposed to host                 │ │
│  │                                                       │ │
│  │  ┌─────────────┐                                     │ │
│  │  │ PostgreSQL  │ ← Credentials: instruments/instruments│ │
│  │  │             │ ← DDL: hibernate.ddl-auto=update     │ │
│  │  └─────────────┘                                     │ │
│  └─────────────────────────────────────────────────────┘ │
│                                                           │
│  ⚠️ SECURITY CONCERNS:                                   │
│  • Log4j 2.6.1 (CVE-2021-44228)                          │
│  • SQL Injection in FindInstrumentRepositoryImpl          │
│  • No input validation on location parameter              │
│  • Hardcoded credentials                                  │
│  • No CSRF protection                                     │
└──────────────────────────────────────────────────────────┘
```

## Cross-References

- [Structural Diagrams](../structural/component-diagrams.md)
- [Behavioral Diagrams](../behavioral/sequence-diagrams.md)
- [System Overview](../../architecture/system-overview.md)
- [Security Patterns](../../analysis/security-patterns.md)
