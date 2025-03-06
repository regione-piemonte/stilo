package it.eng.utility.cryptosigner.storage.impl.database.hsql;

import java.sql.Types;

/**
 * Personalizzazione di {@link org.hibernate.dialect.HSQLDialect} 
 * @author Administrator
 *
 */
public class CustomHSQLDialect extends org.hibernate.dialect.HSQLDialect
{
    public CustomHSQLDialect()
    {
        registerColumnType(Types.BOOLEAN, "boolean");
        registerHibernateType(Types.BOOLEAN, "boolean");
    }
}
