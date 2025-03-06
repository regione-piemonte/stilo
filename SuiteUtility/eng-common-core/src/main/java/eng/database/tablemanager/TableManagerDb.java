// 
// Decompiled by Procyon v0.5.36
// 

package eng.database.tablemanager;

import eng.database.exception.EngSqlNoApplException;
import eng.database.modal.EngConnectionForSelection;

public abstract class TableManagerDb extends EngConnectionForSelection
{
    protected static final String MESSAGE_EXCEPTION_COMMAND_SQL = "SQL: ";
    protected static final String MESSAGE_EXCEPTION_GENERIC = "Errore: ";
    
    public TableManagerDb() throws EngSqlNoApplException {
    }
    
    public TableManagerDb(final int a) throws EngSqlNoApplException {
        super(a);
    }
    
    public TableManagerDb(final String a) throws EngSqlNoApplException {
        super(a);
    }
}
