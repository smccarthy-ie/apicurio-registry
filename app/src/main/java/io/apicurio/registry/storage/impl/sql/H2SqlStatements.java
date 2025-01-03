package io.apicurio.registry.storage.impl.sql;

/**
 * H2 implementation of the sql statements interface. Provides sql statements that are specific to H2, where
 * applicable.
 */
public class H2SqlStatements extends CommonSqlStatements {

    /**
     * Constructor.
     */
    public H2SqlStatements() {
    }

    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#dbType()
     */
    @Override
    public String dbType() {
        return "h2";
    }

    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#isPrimaryKeyViolation(java.lang.Exception)
     */
    @Override
    public boolean isPrimaryKeyViolation(Exception error) {
        return error.getMessage() != null && error.getMessage().contains("primary key violation");
    }

    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#isForeignKeyViolation(java.lang.Exception)
     */
    @Override
    public boolean isForeignKeyViolation(Exception error) {
        return error.getMessage() != null
                && error.getMessage().contains("Referential integrity constraint violation");
    }

    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#isDatabaseInitialized()
     */
    @Override
    public String isDatabaseInitialized() {
        return "SELECT COUNT(*) AS count FROM information_schema.tables WHERE table_name = 'APICURIO'";
    }

    @Override
    public String isDatabaseSchemaInitialized() {
        return "SELECT COUNT(*) AS count FROM information_schema.tables WHERE table_schema = ? AND table_name = 'APICURIO'";
    }

    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#getNextSequenceValue()
     */
    @Override
    public String getNextSequenceValue() {
        throw new RuntimeException("Not applicable when using H2 as the database kind.");
    }

    /**
     * @see io.apicurio.registry.storage.impl.sql.SqlStatements#resetSequenceValue()
     */
    @Override
    public String resetSequenceValue() {
        throw new RuntimeException("Not applicable when using H2 as the database kind.");
    }

    @Override
    public String insertSequenceValue() {
        throw new RuntimeException("Not applicable when using H2 as the database kind.");
    }

    @Override
    public String selectCurrentSequenceValue() {
        throw new RuntimeException("Not applicable when using H2 as the database kind.");
    }

    @Override
    public String createDataSnapshot() {
        return "SCRIPT TO ?";
    }

    @Override
    public String restoreFromSnapshot() {
        return "RUNSCRIPT FROM ?";
    }
}
