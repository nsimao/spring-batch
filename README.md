# Spring Batch Example.

## Load CSV to DB
- `http://localhost:8080/load/users` or `http://localhost:8080/load/users2` - Trigger point for Spring Batch.
- `http://localhost:8080/h2-console` - H2 Console for querying the in-memory tables. JDBC URL: `jdbc:h2:mem:testdb`

## Configuration

- File directory location: `input` variable.