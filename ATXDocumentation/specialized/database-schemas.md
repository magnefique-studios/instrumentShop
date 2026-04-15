# Database Schemas

## PostgreSQL Database (Instruments Service)

### Table: instruments_for_sale
- **Source**: `db/sql/instruments-latest.sql`
- **Records**: 66 instrument listings

```sql
CREATE TABLE IF NOT EXISTS instruments_for_sale(
   ID              VARCHAR(50) NOT NULL PRIMARY KEY,
   Title           VARCHAR(140) NOT NULL,
   Sub_title       VARCHAR(58) NOT NULL,
   Price           VARCHAR(12) NOT NULL,
   Instrument_Type VARCHAR(29) NOT NULL,
   Condition       VARCHAR(4) NOT NULL,
   Location        VARCHAR(33) NOT NULL,
   Post_URL        VARCHAR(114) NOT NULL,
   Seller_type     VARCHAR(14) NOT NULL,
   published_date  VARCHAR(14) NOT NULL
);
```

### Table: instruments_for_sale_chicago
- **Source**: `db/sql/instruments-chicago.sql`
- **Records**: 66 instrument listings (identical data to instruments_for_sale)

```sql
CREATE TABLE IF NOT EXISTS instruments_for_sale_chicago(
   ID              VARCHAR(50) NOT NULL PRIMARY KEY,
   Title           VARCHAR(140) NOT NULL,
   Sub_title       VARCHAR(58) NOT NULL,
   Price           VARCHAR(12) NOT NULL,
   Instrument_Type VARCHAR(29) NOT NULL,
   Condition       VARCHAR(4) NOT NULL,
   Location        VARCHAR(33) NOT NULL,
   Post_URL        VARCHAR(114) NOT NULL,
   Seller_type     VARCHAR(14) NOT NULL,
   published_date  VARCHAR(14) NOT NULL
);
```

### JPA Entity Mapping: Instrument
- **Entity**: `instruments/src/main/java/.../model/Instrument.java`
- **Table**: `instruments_for_sale`
- **Annotations**: `@Entity`, `@Table(name = "instruments_for_sale")`
- **ID Strategy**: `@GeneratedValue` (auto)

### JPA Entity Mapping: Stock (Instruments)
- **Entity**: `instruments/src/main/java/.../model/Stock.java`
- **Table**: `InstrumentStocks`
- **Note**: This table is not initialized by SQL scripts; must be created by JPA `hibernate.ddl-auto=update`

## H2 Database (Stock Service)

### Table: Stock (auto-created by JPA)
- **Entity**: `stock/src/main/java/.../model/Stock.java`
- **Annotations**: `@Entity`, `@Id` on productId

```
Stock
├── productId  VARCHAR (PK)
├── sku        VARCHAR
└── amountAvailable  INT
```

### Initial Data (DataGenerator)
```java
stockRepository.save(new Stock("1", "12345678", 5));
stockRepository.save(new Stock("2", "34567890", 2));
stockRepository.save(new Stock("3", "54326745", 999));
stockRepository.save(new Stock("4", "93847614", 0));
stockRepository.save(new Stock("5", "11856388", 1));
```

## Database Connection Configuration

### Instruments → PostgreSQL
- **URL**: `jdbc:postgresql://postgresDB:5432/instruments` (via `SPRING_DATASOURCE_URL` env var)
- **Username**: `instruments`
- **Password**: `instruments`
- **DDL**: `hibernate.ddl-auto=update` (via `SPRING_JPA_HIBERNATE_DDL_AUTO` env var)
- **Properties**: `spring.jpa.database=POSTGRESQL`, `spring.jpa.show-sql=false`

### Stock → H2
- **URL**: Default H2 in-memory (auto-configured by Spring Boot)
- **DDL**: Auto-created by JPA

## Known Database Issues

1. **Cartesian Join**: `FindInstrumentRepositoryImpl.findInstruments()` performs `SELECT * FROM instruments_for_sale, instruments_for_sale_chicago` which creates a Cartesian product
2. **SQL Injection**: `FindInstrumentRepositoryImpl.findInstrumentByID()` uses string concatenation
3. **Duplicate Data**: Both `instruments_for_sale` and `instruments_for_sale_chicago` contain identical data

## Cross-References

- [Data Models](../reference/data-models.md) | [Security Patterns](../analysis/security-patterns.md)
