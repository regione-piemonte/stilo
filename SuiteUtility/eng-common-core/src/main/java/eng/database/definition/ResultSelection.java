// 
// Decompiled by Procyon v0.5.36
// 

package eng.database.definition;

import java.util.Vector;
import eng.database.exception.EngSqlNoApplException;

public interface ResultSelection
{
    boolean next() throws EngSqlNoApplException;
    
    String getElement(final int p0) throws EngSqlNoApplException;
    
    void addColumn(final String p0);
    
    void addRow();
    
    void addRow(final Vector p0);
    
    Vector getRow(final int p0);
    
    boolean isEmpty();
}
