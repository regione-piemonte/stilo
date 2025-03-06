// 
// Decompiled by Procyon v0.5.36
// 

package eng.singleton;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;
import eng.util.Logger;
import eng.database.modal.EngResultSet;
import java.util.Hashtable;

public class EntePropertiesSingletonOLD
{
    private static int MAX_NUM_ENTI;
    private static Object syncObj;
    private static Hashtable m_SingletonInstance;
    private static Hashtable enteProp;
    
    private EntePropertiesSingletonOLD() {
    }
    
    private static Hashtable EntePropertiesInit(final EngResultSet rsParam) {
        Hashtable myEnteProp = null;
        myEnteProp = new Hashtable();
        try {
            if (rsParam != null) {
                while (rsParam.next()) {
                    myEnteProp.put(rsParam.getElement(1), rsParam.getElement(2));
                    Logger.getLogger().info((Object)("EnteProprerties: " + rsParam.getElement(1).toUpperCase() + "=" + rsParam.getElement(2)));
                }
            }
        }
        catch (Exception e) {
            Logger.getLogger().error((Object)e.getMessage());
        }
        return myEnteProp;
    }
    
    public static synchronized Hashtable getInstance(final int codEnte, final EngResultSet rsParam) {
        return getInstance(codEnte + "", rsParam);
    }
    
    public static synchronized Hashtable getInstance(final String codEnte, final EngResultSet rsParam) {
        Logger.getLogger().info((Object)("CodEnte=" + codEnte));
        if (EntePropertiesSingletonOLD.enteProp == null) {
            EntePropertiesSingletonOLD.enteProp = new Hashtable();
        }
        EntePropertiesSingletonOLD.m_SingletonInstance = EntePropertiesSingletonOLD.enteProp.get(codEnte);
        if (EntePropertiesSingletonOLD.m_SingletonInstance == null) {
            synchronized (EntePropertiesSingletonOLD.syncObj) {
                if (EntePropertiesSingletonOLD.m_SingletonInstance == null) {
                    Logger.getLogger().debug((Object)("Inizializzo EntePropertiesSingleton per l'ente " + codEnte));
                    EntePropertiesSingletonOLD.enteProp.put(codEnte, EntePropertiesInit(rsParam));
                }
            }
        }
        return EntePropertiesSingletonOLD.enteProp.get(codEnte);
    }
    
    public static synchronized Hashtable getInstance(final int codEnte) {
        return getInstance(codEnte + "");
    }
    
    public static synchronized Hashtable getInstance(final String codEnte) {
        EntePropertiesSingletonOLD.m_SingletonInstance = EntePropertiesSingletonOLD.enteProp.get(codEnte);
        if (EntePropertiesSingletonOLD.m_SingletonInstance == null) {
            Logger.getLogger().debug((Object)("Inizializzo EntePropertiesSingleton per l'ente " + codEnte));
            EntePropertiesSingletonOLD.enteProp.put(codEnte, EntePropertiesInit(null));
        }
        return EntePropertiesSingletonOLD.enteProp.get(codEnte);
    }
    
    public static synchronized void setNumEnti(final String strPathRootConfFile) {
        if (EntePropertiesSingletonOLD.MAX_NUM_ENTI == 0) {
            try {
                final Properties props = new Properties();
                props.load(new FileInputStream(new File(strPathRootConfFile, "Enti.properties")));
                EntePropertiesSingletonOLD.MAX_NUM_ENTI = Integer.parseInt(props.getProperty("MAX_NUM_ENTI", "100"));
                EntePropertiesSingletonOLD.enteProp = new Hashtable();
                Logger.getLogger().info((Object)("MAX_NUM_ENTI=" + EntePropertiesSingletonOLD.MAX_NUM_ENTI));
            }
            catch (Exception ex) {
                Logger.getLogger().error((Object)ex.getMessage());
            }
        }
    }
    
    static {
        EntePropertiesSingletonOLD.MAX_NUM_ENTI = 0;
        EntePropertiesSingletonOLD.syncObj = new Object();
        EntePropertiesSingletonOLD.m_SingletonInstance = null;
        EntePropertiesSingletonOLD.enteProp = null;
    }
}
