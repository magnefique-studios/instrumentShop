# Tech Debt (Analysis Cross-Reference)

[← Back to README](../README.md) | [Technical Debt Report](../technical-debt-report.md) | [Security Patterns](security-patterns.md)

## Summary

This file provides a cross-reference between the analysis findings and the detailed technical debt documentation.

| Category | Severity | Detail Location |
|----------|----------|----------------|
| EOL Spring Boot versions (1.5.19, 2.1.3, 2.7.5) | High | [Outdated Components](../technical-debt/outdated-components.md) |
| Deprecated Spring Cloud Dalston | High | [Outdated Components](../technical-debt/outdated-components.md) |
| Deprecated Netflix Hystrix | High | [Outdated Components](../technical-debt/outdated-components.md) |
| Java 1.8 runtime (shop, stock) | High | [Outdated Components](../technical-debt/outdated-components.md) |
| Log4j 2.6.1 vulnerability (test) | Medium | [Security Patterns](security-patterns.md) |
| JUnit 3.8.1 (root) | Medium | [Outdated Components](../technical-debt/outdated-components.md) |
| Cucumber info.cukes (stock) | Medium | [Outdated Components](../technical-debt/outdated-components.md) |
| OTel alpha annotations | Medium | [Outdated Components](../technical-debt/outdated-components.md) |
| commons-httpclient 3.1 (test) | Medium | [Outdated Components](../technical-debt/outdated-components.md) |
| SQL injection vulnerability | Low | [Security Patterns](security-patterns.md) |
| Empty catch blocks (60+) | Low | [Maintenance Burden](../technical-debt/maintenance-burden.md) |
| Thread.sleep anti-patterns | Low | [Maintenance Burden](../technical-debt/maintenance-burden.md) |
| Cartesian product query | Low | [Security Patterns](security-patterns.md) |
| Endpoint typos | Low | [Maintenance Burden](../technical-debt/maintenance-burden.md) |
| Code duplication | Low | [Maintenance Burden](../technical-debt/maintenance-burden.md) |
| Zero test coverage | Low | [Code Metrics](code-metrics.md) |

## AWS Transformation Recommendation

See [Technical Debt Report](../technical-debt-report.md) for the recommended `AWS/java-version-upgrade` transformation.

---

## Related Documents

- [Technical Debt Report](../technical-debt-report.md) — Root-level report with AWS recommendation
- [Remediation Plan](../technical-debt/remediation-plan.md) — Prioritized action items
- [Security Patterns](security-patterns.md) — Security analysis
