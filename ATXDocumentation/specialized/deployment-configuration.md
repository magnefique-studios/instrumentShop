# Deployment Configuration

## Docker Compose Architecture

**File**: `docker-compose.yml`

### Services

| Service | Image | Port | Network | Dependencies |
|---------|-------|------|---------|--------------|
| shop | `shabushabu/javashop.shop:latest` | 8010:8010 | instrument_shop | products, stock, instruments |
| products | `shabushabu/javashop.products:latest` | 8020:8020 | instrument_shop | — |
| conductors | `shabushabu/javashop.conductors:latest` | 8050:8050 | instrument_shop | — |
| stock | `shabushabu/javashop.stock:latest` | 8030:8030 | instrument_shop | — |
| instruments | `shabushabu/javashop.instruments:latest` | 8040:8040 | instrument_shop | postgresDB (healthy) |
| postgresDB | `postgres:13.1-alpine` | 5432:5432 | instrument_shop | — |
| redis | `redis` | — | instrument_shop | — |
| shoptester | `shabushabu/javashop.tester:latest` | — | instrument_shop | shop |

### Health Checks

All application services have health checks configured:
```yaml
healthcheck:
  test: curl -sS http://localhost:{port}/healthcheck | grep -c 200 > /dev/null
  interval: 2s
  retries: 5
  start_period: 5s
  timeout: 2s
```

PostgreSQL uses:
```yaml
healthcheck:
  test: ["CMD-SHELL", "pg_isready"]
  interval: 2s
  timeout: 2s
  retries: 5
```

### Environment Variables

| Variable | Service | Purpose |
|----------|---------|---------|
| `USERNAME` / `SHOP_USER` | All | User identification |
| `SPRING_DATASOURCE_URL` | instruments | PostgreSQL connection URL |
| `SPRING_DATASOURCE_USERNAME` | instruments | DB username ("instruments") |
| `SPRING_DATASOURCE_PASSWORD` | instruments | DB password ("instruments") |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | instruments | DDL strategy ("update") |

### Volumes

| Service | Source | Target | Purpose |
|---------|--------|--------|---------|
| shop | `./shop/data` | `/container/shop/data` | Exercise properties files |
| shoptester | `./test/data` | `/container/test/data` | Test data |
| postgresDB | `./db/sql/instruments-latest.sql` | `/docker-entrypoint-initdb.d/` | DB init |
| postgresDB | `./db/sql/instruments-chicago.sql` | `/docker-entrypoint-initdb.d/` | DB init |

### Network

- **Network Name**: `instrument_shop`
- **Type**: External (must be created before `docker-compose up`)
- **Creation**: `docker network create instrument_shop`

## Build Scripts

| Script | Purpose |
|--------|---------|
| `BuildAndDeploy.sh` | Build all modules and deploy via Docker |
| `BuildAndDeployNoSudo.sh` | Build and deploy without sudo |
| `BuildOnly.sh` | Build modules only (no deploy) |
| `AutomateManualInstrumentation.sh` | Run annotator to add OTel annotations |
| `CleanUpAnnotations.sh` | Remove OTel annotations |
| `send_traffic.sh` | Generate test traffic |
| `all_tests.sh` | Run all tests |

## Commented-Out Configurations

The docker-compose.yml contains significant commented-out configuration for:
- **Datadog Agent**: Full agent configuration with APM, logs, and monitoring
- **Service labels**: Datadog log source labels
- **DD_INSTRUMENT_SERVICE_WITH_APM**: Datadog APM flag on all services

## Cross-References

- [System Overview](../architecture/system-overview.md)
- [Component Details](../architecture/components.md)
