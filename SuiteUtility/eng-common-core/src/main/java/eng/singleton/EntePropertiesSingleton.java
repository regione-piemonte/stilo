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

public class EntePropertiesSingleton
{
    private static int MAX_NUM_ENTI;
    private static EntePropertiesSingleton singleton;
    private Hashtable enteProp;
    
    private EntePropertiesSingleton() {
        this.enteProp = null;
    }
    
    private static Hashtable EntePropertiesInit(final EngResultSet rsParam) {
        final Hashtable myEnteProp = new Hashtable();
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
    
    private static synchronized EntePropertiesSingleton getSingleton() {
        if (EntePropertiesSingleton.singleton == null) {
            EntePropertiesSingleton.singleton = new EntePropertiesSingleton();
        }
        return EntePropertiesSingleton.singleton;
    }
    
    public static synchronized Hashtable getInstance(final int codEnte, final EngResultSet rsParam) {
        return getInstance(codEnte + "", rsParam);
    }
    
    public static synchronized Hashtable getInstance(final String codEnte, final EngResultSet rsParam) {
        Hashtable ret = null;
        final EntePropertiesSingleton single = getSingleton();
        Logger.getLogger().info((Object)("CodEnte=" + codEnte));
        if (single.getEnteProp() == null) {
            single.setEnteProp(new Hashtable());
        }
        ret = single.getEnteProp().get(codEnte);
        if (ret == null) {
            Logger.getLogger().debug((Object)("Inizializzo EntePropertiesSingleton per l'ente " + codEnte));
            ret = EntePropertiesInit(rsParam);
            single.getEnteProp().put(codEnte, ret);
        }
        return ret;
    }
    
    public static synchronized Hashtable getInstance(final String codEnte, final String idDominio, final EngResultSet rsParam) {
        Hashtable ret = null;
        final EntePropertiesSingleton single = getSingleton();
        Logger.getLogger().info((Object)("CodEnte=" + codEnte));
        Logger.getLogger().info((Object)("IdDominio=" + idDominio));
        if (single.getEnteProp() == null) {
            single.setEnteProp(new Hashtable());
        }
        final Hashtable entetable = single.getEnteProp().get(codEnte);
        if (entetable == null) {
            single.getEnteProp().put(codEnte, new Hashtable<String, Hashtable>());
        }
        ret = single.getEnteProp().get(codEnte).get(idDominio);
        if (ret == null) {
            Logger.getLogger().debug((Object)("Inizializzo EntePropertiesSingleton per l'ente " + codEnte));
            ret = EntePropertiesInit(rsParam);
            single.getEnteProp().get(codEnte).put(idDominio, ret);
        }
        return ret;
    }
    
    public static synchronized Hashtable getInstance(final int codEnte) {
        return getInstance(codEnte + "");
    }
    
    public static synchronized Hashtable getInstance(final int codEnte, final int idDominio) {
        return getInstance(codEnte + "", idDominio + "");
    }
    
    public static synchronized Hashtable getInstance(final String codEnte) {
        Hashtable ret = null;
        final EntePropertiesSingleton single = getSingleton();
        ret = single.getEnteProp().get(codEnte);
        if (ret == null) {
            Logger.getLogger().debug((Object)("Inizializzo EntePropertiesSingleton per l'ente " + codEnte));
            ret = EntePropertiesInit(null);
            single.getEnteProp().put(codEnte, ret);
        }
        return ret;
    }
    
    public static synchronized Hashtable getInstance(final String codEnte, final String idDomino) {
        Hashtable ret = new Hashtable();
        final EntePropertiesSingleton single = getSingleton();
        final Hashtable entetable = single.getEnteProp().get(codEnte);
        if (entetable != null) {
            final Hashtable dominiotable = entetable.get(idDomino);
            if (dominiotable != null) {
                ret = dominiotable;
            }
        }
        return ret;
    }
    
    public static synchronized void setNumEnti(final String strPathRootConfFile) {
        if (EntePropertiesSingleton.MAX_NUM_ENTI == 0) {
            try {
                final Properties props = new Properties();
                props.load(new FileInputStream(new File(strPathRootConfFile, "Enti.properties")));
                EntePropertiesSingleton.MAX_NUM_ENTI = Integer.parseInt(props.getProperty("MAX_NUM_ENTI", "100"));
                getSingleton().setEnteProp(new Hashtable());
                Logger.getLogger().info((Object)("MAX_NUM_ENTI=" + EntePropertiesSingleton.MAX_NUM_ENTI));
            }
            catch (Exception ex) {
                Logger.getLogger().error((Object)ex.getMessage());
            }
        }
    }
    
    public Hashtable getEnteProp() {
        return this.enteProp;
    }
    
    public void setEnteProp(final Hashtable enteProp) {
        this.enteProp = enteProp;
    }
    
    static {
        EntePropertiesSingleton.MAX_NUM_ENTI = 0;
        EntePropertiesSingleton.singleton = null;
    }
}
