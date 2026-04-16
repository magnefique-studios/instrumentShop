# Technical Debt Report

[← Back to README](README.md) | [Detailed Analysis: Technical Debt](technical-debt/summary.md)

## 🎯 AWS Transformation Recommendation

### **RECOMMENDED TRANSFORMATIONS: AWS/java-version-upgrade**

This Java multi-module Maven project has critical modules running on Java 1.8 (shop, stock) with Spring Boot 1.5.x and 2.1.x, both well past end-of-life. The `AWS/java-version-upgrade` transformation can systematically upgrade these modules to a modern JDK version (e.g., Java 17 or 21), modernizing dependencies including the Jakarta EE migration needed for javax.* → jakarta.* namespace changes, and updating Spring Boot to supported versions.

---

## Executive Summary

The InstrumenT-ation Shop codebase contains **significant technical debt** across its 7 Maven modules, primarily stemming from **end-of-life frameworks and runtimes**. The most critical findings are:

- **3 of 7 modules** use end-of-life Spring Boot versions (1.5.19, 2.1.3, 2.7.5)
- **Critical security vulnerability**: Log4j 2.6.1 in the test module (CVE-2021-44228 — Log4Shell)
- **SQL injection vulnerability** in `FindInstrumentRepositoryImpl`
- **Deprecated Netflix Hystrix** circuit breaker in the shop module
- **Mixed Java versions**: Java 1.8 (shop, stock), Java 11 (test), Java 17 (products, conductors, instruments, annotator)

---

## Priority Summary

| Priority | Category | Count | Key Items |
|----------|----------|-------|-----------|
| **High** | EOL/Deprecated Runtimes & Frameworks | 6 | Spring Boot 1.5.19, 2.1.3, 2.7.5; Spring Cloud Dalston; Netflix Hystrix; Java 1.8 |
| **Medium** | Outdated Dependencies | 7 | Log4j 2.6.1 (test), JUnit 3.8.1, Cucumber 1.2.5 (info.cukes), OTel alpha annotations, commons-httpclient 3.1, nekohtml 1.9.22 |
| **Low** | Code Quality & Architecture | 8 | SQL injection, empty catch blocks, Thread.sleep anti-patterns, cartesian query, endpoint typos, hardcoded values, commented-out code |

---

## Critical Findings

### 🔴 High Severity — EOL/Deprecated Runtimes & Frameworks

1. **Spring Boot 1.5.19.RELEASE** (shop module) — EOL August 2019
2. **Spring Boot 2.1.3.RELEASE** (stock module) — EOL late 2019
3. **Spring Boot 2.7.5** (instruments module) — EOL November 2023
4. **Spring Cloud Dalston.SR5** (shop module) — EOL December 2018
5. **Netflix Hystrix** (shop module) — Deprecated, in maintenance mode since 2018
6. **Java 1.8** (shop, stock modules) — While still receiving updates, many frameworks no longer support it

### 🟡 Medium Severity — Outdated Dependencies

1. **Log4j 2.6.1** (test module) — Vulnerable to CVE-2021-44228 (Log4Shell RCE), CVE-2021-45046, CVE-2017-5645
2. **JUnit 3.8.1** (root pom) — Extremely outdated (current: JUnit 5.x)
3. **Cucumber 1.2.5** (stock module, `info.cukes` groupId) — Deprecated, replaced by `io.cucumber`
4. **OTel instrumentation annotations 1.19.1-alpha / 1.19.2-alpha** (annotator, shop, instruments) — Alpha pre-release versions
5. **commons-httpclient 3.1** (test module) — Deprecated, replaced by Apache HttpComponents
6. **nekohtml 1.9.22** (shop module) — Outdated HTML parser
7. **Log4j 2.24.3** (shop module) — ✅ Already patched, NOT vulnerable

### 🟢 Low Severity — Code Quality & Architecture

1. **SQL Injection**: `FindInstrumentRepositoryImpl.findInstrumentByID()` concatenates user input into JPQL
2. **60+ empty catch blocks**: Silently swallow exceptions in ProductFilterService
3. **Thread.sleep anti-patterns**: Used for artificial latency simulation
4. **Cartesian product query**: `SELECT * FROM instruments_for_sale, instruments_for_sale_chicago`
5. **Endpoint typos**: `/instrumemnts` (StockRepo), `/insruments` (StockResource)
6. **Hardcoded location**: Conductors controller always sets location to "Oregon"
7. **Non-Spring-managed beans**: ProductController creates service instances manually
8. **Excessive commented-out code** throughout the codebase

---

## Navigation

- [Detailed Summary](technical-debt/summary.md)
- [Outdated Components](technical-debt/outdated-components.md)
- [Maintenance Burden](technical-debt/maintenance-burden.md)
- [Remediation Plan](technical-debt/remediation-plan.md)
- [Architecture Dependencies](architecture/dependencies.md)
- [Security Patterns](analysis/security-patterns.md)
