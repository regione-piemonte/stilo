// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction.singleton;

import java.util.Iterator;
import eng.database.exception.EngException;
import eng.singleton.stores.StoredDefInterface;
import eng.storefunction.StoreFunction;
import java.util.Hashtable;
import eng.storefunction.StoreFunctionSearcher;

public final class TableStoreFunctionDefinitionSingleton implements StoreFunctionSearcher
{
    private static TableStoreFunctionDefinitionSingleton instance;
    private static final Hashtable functions;
    
    private TableStoreFunctionDefinitionSingleton() {
        try {
            this.jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static TableStoreFunctionDefinitionSingleton getInstance() {
        return TableStoreFunctionDefinitionSingleton.instance;
    }
    
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
    
    @Override
    public StoreFunction searchFromName(final String fullyLogicName) {
        if (!TableStoreFunctionDefinitionSingleton.functions.containsKey(fullyLogicName)) {
            try {
                final StoredDefInterface sdi = (StoredDefInterface)Class.forName(fullyLogicName).newInstance();
                sdi.addNewStoredFunction(TableStoreFunctionDefinitionSingleton.functions);
                if (TableStoreFunctionDefinitionSingleton.functions.get(fullyLogicName) == null) {
                    final StringBuffer erb = new StringBuffer("La classe ");
                    erb.append(fullyLogicName);
                    erb.append(" non aggiunge la stored function di nome logico ");
                    erb.append(fullyLogicName);
                    throw new StoreFunctionDefinitionError(erb.toString());
                }
            }
            catch (ClassNotFoundException e) {
                throw new StoreFunctionDefinitionError(e);
            }
            catch (ClassCastException e2) {
                throw new StoreFunctionDefinitionError(e2);
            }
            catch (EngException e3) {
                throw new StoreFunctionDefinitionError(e3);
            }
            catch (InstantiationException e4) {
                throw new StoreFunctionDefinitionError(e4);
            }
            catch (IllegalAccessException e5) {
                throw new StoreFunctionDefinitionError(e5);
            }
        }
        return TableStoreFunctionDefinitionSingleton.functions.get(fullyLogicName);
    }
    
    @Override
    public String toString() {
        final Iterator i = TableStoreFunctionDefinitionSingleton.functions.values().iterator();
        final StringBuffer sb = new StringBuffer("(TableStoreFunctionDefinitionSingleton:");
        while (i.hasNext()) {
            sb.append("\n" + i.next() + ",");
        }
        sb.append(")");
        return sb.toString();
    }
    
    private void jbInit() throws Exception {
    }
    
    static {
        TableStoreFunctionDefinitionSingleton.instance = new TableStoreFunctionDefinitionSingleton();
        functions = new Hashtable();
    }
}
