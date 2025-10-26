```@Column(
name = "user_name",     // DB column name (if different from field name)
nullable = false,       // NOT NULL constraint
unique = true,          // UNIQUE constraint
length = 100,           // VARCHAR length (for String types)
columnDefinition = "TEXT", // custom SQL type definition
insertable = true,      // include in INSERT statements
updatable = false,      // exclude from UPDATEs
precision = 10,         // for BigDecimal: total digits
scale = 2               // for BigDecimal: digits after decimal
)
```