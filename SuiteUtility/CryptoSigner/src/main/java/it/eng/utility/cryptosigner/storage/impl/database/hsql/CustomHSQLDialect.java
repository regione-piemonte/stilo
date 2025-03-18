/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
