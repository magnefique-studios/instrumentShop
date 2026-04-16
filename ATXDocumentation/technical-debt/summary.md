# Technical Debt Summary

[← Back to README](../README.md) | [← Technical Debt Report](../technical-debt-report.md) | [Outdated Components](outdated-components.md) | [Maintenance Burden](maintenance-burden.md) | [Remediation Plan](remediation-plan.md)

## Overview

This document provides a consolidated view of all technical debt identified across the 7 Maven modules of the InstrumenT-ation Shop project.

---

## Debt Distribution by Module

| Module | High | Medium | Low | Total |
|--------|------|--------|-----|-------|
| shop | 3 | 2 | 3 | 8 |
| stock | 1 | 1 | 0 | 2 |
| instruments | 1 | 1 | 2 | 4 |
| products | 0 | 0 | 2 | 2 |
| conductors | 0 | 0 | 3 | 3 |
| annotator | 0 | 1 | 0 | 1 |
| test | 0 | 2 | 0 | 2 |
| root | 0 | 1 | 0 | 1 |

---

## High Severity Items (EOL/Deprecated Runtimes & Frameworks)

1. **Spring Boot 1.5.19.RELEASE** — shop module, EOL August 2019. No longer receives security patches.
2. **Spring Boot 2.1.3.RELEASE** — stock module, EOL late 2019. No longer receives security patches.
3. **Spring Boot 2.7.5** — instruments module, EOL November 2023. Should upgrade to 3.x line.
4. **Spring Cloud Dalston.SR5** — shop module, EOL December 2018. Part of Spring Cloud 1.x.
5. **Netflix Hystrix** — shop module, deprecated since 2018. Netflix moved to resilience4j.
6. **Java 1.8 runtime** — shop and stock modules target Java 1.8 which limits framework upgrade paths.

## Medium Severity Items (Outdated Dependencies)

1. **Log4j 2.6.1** — test module, vulnerable to CVE-2021-44228 (CVSS 10.0). Must upgrade immediately.
2. **JUnit 3.8.1** — root pom, extremely outdated. Current: JUnit 5.10.x.
3. **Cucumber 1.2.5 (info.cukes)** — stock module, groupId deprecated. Use `io.cucumber` 7.x+.
4. **OTel annotations alpha** — shop (1.19.2-alpha), instruments (1.19.2-alpha), annotator (1.19.1-alpha). Use stable GA releases (2.x).
5. **commons-httpclient 3.1** — test module, replaced by Apache HttpComponents HttpClient 4.x/5.x.
6. **nekohtml 1.9.22** — shop module. Outdated HTML parser.
7. **Log4j 2.24.3** — shop module. ✅ Already patched — NOT a vulnerability.

## Low Severity Items (Code Quality & Architecture)

1. **SQL Injection** in `FindInstrumentRepositoryImpl.findInstrumentByID()` — string concatenation in JPQL query.
2. **60+ empty catch blocks** — ProductFilterService in products and conductors modules.
3. **Thread.sleep anti-patterns** — Used extensively for simulated latency.
4. **Cartesian product query** — `findInstruments()` joins two tables without condition.
5. **Endpoint typos** — `/instrumemnts` in StockRepo, `/insruments` in StockResource.
6. **Hardcoded location** — ConductorsController overrides location parameter to "Oregon".
7. **Non-Spring-managed services** — ProductController manually instantiates services.
8. **Commented-out code** — Extensive dead code throughout codebase.

---

## Related Documents

- [Outdated Components](outdated-components.md) — Detailed version analysis
- [Maintenance Burden](maintenance-burden.md) — Areas requiring attention
- [Remediation Plan](remediation-plan.md) — Prioritized action items
