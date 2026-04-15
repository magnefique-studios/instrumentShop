# Technical Debt Report — JavaShop

## 🎯 AWS Transformation Recommendation

### **RECOMMENDED TRANSFORMATIONS: AWS/java-version-upgrade, AWS/early-access-log4j-to-slf4j-migration**

This codebase contains multiple modules running on outdated Java versions (Java 8 in shop and stock modules) and outdated Spring Boot versions (1.5.x, 2.1.x, 2.7.x). The `AWS/java-version-upgrade` transformation can upgrade Java 8 modules to a modern JDK version (e.g., Java 17 or 21) with comprehensive dependency modernization including Jakarta EE migration. Additionally, the `AWS/early-access-log4j-to-slf4j-migration` transformation can address the critical Log4j 2.6.1 security vulnerability (CVE-2021-44228) present in the shop and test modules by migrating to SLF4J with Logback.

---

## Executive Summary

The JavaShop codebase carries **significant technical debt** across multiple dimensions. The most critical issues are:

1. **Critical security vulnerability**: Log4j 2.6.1 in shop and test modules (CVE-2021-44228)
2. **EOL runtimes and frameworks**: Spring Boot 1.5.19 (shop), 2.1.3 (stock), and 2.7.5 (instruments) are all end-of-life
3. **Mixed Java versions**: Java 8, 11, and 17 across modules with javax→jakarta migration needed
4. **Deprecated libraries**: Netflix Hystrix, Spring Cloud Dalston, commons-httpclient 3.1
5. **Code quality issues**: SQL injection vulnerability, Cartesian join bug, intentional Thread.sleep() calls

## Prioritized Findings

### 🔴 High Severity — EOL/Deprecated Runtimes & Frameworks

| # | Component | Current | Issue | Module(s) |
|---|-----------|---------|-------|-----------|
| 1 | Spring Boot | 1.5.19.RELEASE | EOL since August 2019 | shop |
| 2 | Spring Boot | 2.1.3.RELEASE | EOL (out of OSS support) | stock |
| 3 | Spring Boot | 2.7.5 | EOL since November 2023 | instruments |
| 4 | Spring Cloud | Dalston.SR5 | EOL | shop |
| 5 | Netflix Hystrix | (via Spring Cloud) | Deprecated/maintenance mode | shop |
| 6 | Java Target | 1.8 | Java 8 extended support ending | shop, stock |
| 7 | javax.persistence | (JPA 2.x) | Superseded by Jakarta EE (jakarta.persistence) | instruments, stock |
| 8 | javax.validation | (Bean Validation 2.0) | Superseded by Jakarta EE | stock |
| 9 | JUnit | 3.8.1 | Extremely outdated (current: JUnit 5.10+) | root POM |

### 🟠 Medium Severity — Outdated Dependencies

| # | Component | Current | Latest | Module(s) |
|---|-----------|---------|--------|-----------|
| 10 | Log4j | 2.6.1 | 2.23+ | shop, test |
| 11 | commons-httpclient | 3.1 | EOL (→ HttpComponents 5.x) | test |
| 12 | nekohtml | 1.9.22 | Outdated | shop |
| 13 | OTel Annotations | 1.19.1-alpha / 1.19.2-alpha | 2.x stable | shop, instruments, annotator |
| 14 | Cucumber | 1.2.5 (info.cukes) | 7.x (io.cucumber) | stock |
| 15 | JavaParser | 3.23.1 | 3.26+ | annotator |

### 🟡 Low Severity — Code Quality & Architectural Issues

| # | Issue | Location | Impact |
|---|-------|----------|--------|
| 16 | SQL Injection vulnerability | `FindInstrumentRepositoryImpl.findInstrumentByID()` line 33 | Security risk |
| 17 | Cartesian join (cross-table) | `FindInstrumentRepositoryImpl.findInstruments()` line 27 | Data correctness |
| 18 | Intentional Thread.sleep() calls | `ProductFilterService` (products, conductors) | Performance (workshop design) |
| 19 | Deprecated `finalize()` method | `Exercises.java` line 35 | Code quality |
| 20 | Typo in endpoint | `StockResource` `/insruments` vs `/instruments` | API consistency |
| 21 | Hardcoded location override | `ConductorsController` line 22 (`location = "Oregon"`) | Logic bug |
| 22 | Inconsistent Spring Boot versions | Across all modules | Maintenance burden |
| 23 | Code duplication | Products ↔ Conductors modules | Maintenance burden |

## Navigation

- [Detailed Summary](technical-debt/summary.md)
- [Outdated Components](technical-debt/outdated-components.md)
- [Maintenance Burden](technical-debt/maintenance-burden.md)
- [Remediation Plan](technical-debt/remediation-plan.md)
- [Architecture Overview](architecture/system-overview.md)
