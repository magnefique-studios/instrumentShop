# Deployment Configuration

[← Back to README](../README.md) | [Database Schemas](database-schemas.md) | [System Context](../diagrams/architecture/system-context.md)

## Docker Compose Configuration

**File**: `docker-compose.yml` (Docker Compose v3)
**Network**: `instrument_shop` (external — must be created before deployment)

### Service Configuration

| Service | Image | Build Context | Port | Health Check |
|---------|-------|--------------|------|-------------|
| shop | `shabushabu/javashop.shop:latest` | `./shop` | 8010:8010 | `curl http://localhost:8010/healthcheck` |
| products | `shabushabu/javashop.products:latest` | `./products` | 8020:8020 | `curl http://localhost:8020/products/healthcheck` |
| conductors | `shabushabu/javashop.conductors:latest` | `./conductors` | 8050:8050 | `curl http://localhost:8050/conductors/healthcheck` |
| stock | `shabushabu/javashop.stock:latest` | `./stock` | 8030:8030 | `curl http://localhost:8030/healthcheck` |
| instruments | `shabushabu/javashop.instruments:latest` | `./instruments` | 8040:8040 | `curl http://localhost:8040/healthcheck` |
| shoptester | `shabushabu/javashop.tester:latest` | `./test` | none | none |
| postgresDB | `postgres:13.1-alpine` | N/A | 5432:5432 | `pg_isready` |
| redis | `redis` | N/A | none exposed | none |

### Health Check Settings (common)
```yaml
interval: 2s
retries: 5
start_period: 5s
timeout: 2s
```

### Service Links
| Service | Links To |
|---------|---------|
| shop | products, stock, instruments |
| instruments | postgresDB |
| shoptester | shop |

### Dependencies
| Service | Depends On | Condition |
|---------|-----------|-----------|
| instruments | postgresDB | service_healthy |

### Volume Mounts
| Service | Source | Target |
|---------|--------|--------|
| shop | `./shop/data` | `/container/shop/data` |
| shoptester | `./test/data` | `/container/test/data` |
| postgresDB | `./db/sql/instruments-latest.sql` | `/docker-entrypoint-initdb.d/instruments-latest.sql` |
| postgresDB | `./db/sql/instruments-chicago.sql` | `/docker-entrypoint-initdb.d/instruments-chicago.sql` |

### Environment Variables
| Service | Variable | Value |
|---------|----------|-------|
| all services | `USERNAME` | `${SHOP_USER}` |
| instruments | `SPRING_DATASOURCE_URL` | `jdbc:postgresql://postgresDB:5432/instruments` |
| instruments | `SPRING_DATASOURCE_USERNAME` | `instruments` |
| instruments | `SPRING_DATASOURCE_PASSWORD` | `instruments` |
| instruments | `SPRING_JPA_HIBERNATE_DDL_AUTO` | `update` |
| postgresDB | `POSTGRES_USER` | `instruments` |
| postgresDB | `POSTGRES_PASSWORD` | `instruments` |

## Additional Docker Compose Files

| File | Purpose |
|------|---------|
| `docker-compose.yml` | Primary configuration |
| `docker-compose copy.yml` | Backup/alternative configuration |
| `docker-compose copy 2.yml` | Backup/alternative configuration |
| `docker-compose.yml22` | Unknown variant |
| `docker-compose-conductors.yml` | Conductors-specific configuration |

## Shell Scripts

| Script | Purpose |
|--------|---------|
| `BuildAndDeploy.sh` | Build and deploy (with sudo) |
| `BuildAndDeployNoSudo.sh` | Build and deploy (without sudo) |
| `BuildOnly.sh` | Build only |
| `AutomateManualInstrumentation.sh` | Run annotator tool |
| `CleanUpAnnotations.sh` | Remove annotation changes |
| `all_tests.sh` | Run all tests |
| `send_traffic.sh` | Send test traffic |
| `run_code_analysis.sh` | Run code analysis |

---

## Related Documents

- [System Context](../diagrams/architecture/system-context.md) — System boundaries diagram
- [Database Schemas](database-schemas.md) — PostgreSQL and H2 schemas
- [Modules](../reference/modules.md) — Module organization
