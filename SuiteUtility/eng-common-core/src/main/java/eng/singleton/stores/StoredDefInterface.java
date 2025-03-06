// 
// Decompiled by Procyon v0.5.36
// 

package eng.singleton.stores;

import eng.database.exception.EngException;
import java.util.Hashtable;

public interface StoredDefInterface
{
    public static final String MESSAGE_SUCCESS_GENERIC = "Operazione avvenuta con successo;";
    public static final String MESSAGE_SUCCESS_INSERT = "Inserimento avvenuto con successo;";
    public static final String MESSAGE_SUCCESS_MODIFY = "Modifica avvenuta con successo";
    public static final String MESSAGE_SUCCESS_DELETE = "Cancellazione avvenuta con successo";
    
    void addNewStoredFunction(final Hashtable p0) throws EngException;
}
