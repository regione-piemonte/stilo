// 
// Decompiled by Procyon v0.5.36
// 

package eng.database.definition;

import eng.database.exception.EngSqlApplException;
import eng.database.exception.EngSqlNoApplException;
import eng.util.Properties;

public interface SQLTransaction
{
    ResultSelection execute(final Properties p0) throws EngSqlNoApplException, EngSqlApplException;
    
    void commit() throws EngSqlNoApplException;
    
    void rollback() throws EngSqlNoApplException;
}
