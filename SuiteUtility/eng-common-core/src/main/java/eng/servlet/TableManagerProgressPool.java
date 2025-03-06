// 
// Decompiled by Procyon v0.5.36
// 

package eng.servlet;

import eng.database.exception.EngSqlNoApplException;
import eng.database.tablemanager.TableManagerDbProgress;
import eng.database.tablemanager.TableManagerDb;

public class TableManagerProgressPool extends TableManagerProgress
{
    @Override
    protected TableManagerDb createTableManagerDb(final String a) throws EngSqlNoApplException {
        return new TableManagerDbProgress(a);
    }
}
