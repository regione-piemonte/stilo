package eng.singleton;

/**
 * <p>Title: EntePropertiesSingleton </p>
 * <p>Description: Memorizza le proprietà dell'ente </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Engineering</p>
 * @author Nalesso Mirko
 * @version 1.0
 */

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import eng.database.modal.EngResultSet;

public class EntePropertiesSingletonOLD {

    //
    private static int MAX_NUM_ENTI = 0;

    // e' il semaforo per la sincronizzazione degli accessi in scrittura alla collezione
    private static Object syncObj = new Object();

    // Needs to be static as the instance has to be created from
    // the static function getInstance().
    private static Hashtable m_SingletonInstance = null;

    // hashtable delle properties
    private static Hashtable enteProp = null;

    /**
     * Create a private constructor so that the singleton class
     * cannot be instantiated by the developer explicitly.
     */
    private EntePropertiesSingletonOLD() {  }

    /**
     * Metodo che preleva le informazioni dell'ente
     */
    private static Hashtable EntePropertiesInit(EngResultSet rsParam)
    {
        // Do initialization.
        Hashtable myEnteProp = null;
        myEnteProp = new Hashtable();
        try {
            // Da implementare il reperimento dei dati dell'ente dal DB
            if (rsParam != null)
            {
              while (rsParam.next()) {
                myEnteProp.put(rsParam.getElement(1), rsParam.getElement(2));
                eng.util.Logger.getLogger().info("EnteProprerties: " +
                                                 rsParam.getElement(1).
                                                 toUpperCase() + "=" +
                                                 rsParam.getElement(2));
              }
            }
        } catch(Exception e)
        { eng.util.Logger.getLogger().error(e.getMessage()); }

        return myEnteProp;

    }

    /**
     * Nel caso non ci sia l'istanza o non ci sia la voce della hashtable
     * dell'ente, allora viene richiamato l'inizializzatore
     */
    public synchronized static Hashtable getInstance(int codEnte, EngResultSet rsParam)
    {
        /*
    	eng.util.Logger.getLogger().info("CodEnte="+codEnte);
        m_SingletonInstance = enteProp[codEnte];
        if (m_SingletonInstance == null)
        {
          synchronized (syncObj)
          {
            // se il pool non e' mai stato inizializzato allora lo faccio
            if (m_SingletonInstance == null)
            {
            	eng.util.Logger.getLogger().debug("Inizializzo EntePropertiesSingleton per l'ente " + codEnte);
            	enteProp[codEnte] = EntePropertiesInit(rsParam);
            }
          }
        }

        return (Hashtable)enteProp[codEnte];
        */
    	return getInstance(codEnte+"", rsParam);
    }
    
    // MRK - 13/12/2007
    // Adesso gestisco anche il caso con l'idEnte stringa
    public synchronized static Hashtable getInstance(String codEnte, EngResultSet rsParam)
    {
        eng.util.Logger.getLogger().info("CodEnte="+codEnte);
        // Aggiungo questa verifica per i contesti che non usano Enti.properties
        if (enteProp==null) enteProp = new Hashtable();
        m_SingletonInstance = (Hashtable)enteProp.get(codEnte);
        if (m_SingletonInstance == null)
        {
          synchronized (syncObj)
          {
            // se il pool non e' mai stato inizializzato allora lo faccio
            if (m_SingletonInstance == null)
            {
            	eng.util.Logger.getLogger().debug("Inizializzo EntePropertiesSingleton per l'ente " + codEnte);
            	enteProp.put(codEnte, EntePropertiesInit(rsParam));
            }
          }
        }

        return (Hashtable)enteProp.get(codEnte);
    }

    /**
     * Nel caso non ci sia l'istanza o non ci sia la voce della hashtable
     * dell'ente, allora viene richiamato il costruttore
     */
    public synchronized static Hashtable getInstance(int codEnte)
    {
        /*
    	m_SingletonInstance = enteProp[codEnte];
        if (m_SingletonInstance == null)
        {
        	eng.util.Logger.getLogger().debug("Inizializzo EntePropertiesSingleton per l'ente " + codEnte);
        	enteProp[codEnte] = EntePropertiesInit(null);
        }

        return (Hashtable)enteProp[codEnte];
        */
    	return getInstance(codEnte+"");
    }
    
    // MRK - 13/12/2007
    // Adesso gestisco anche il caso con l'idEnte stringa
    public synchronized static Hashtable getInstance(String codEnte)
    {
        m_SingletonInstance = (Hashtable)enteProp.get(codEnte);
        if (m_SingletonInstance == null)
        {
        	eng.util.Logger.getLogger().debug("Inizializzo EntePropertiesSingleton per l'ente " + codEnte);
        	enteProp.put(codEnte, EntePropertiesInit(null));
        }

        return (Hashtable)enteProp.get(codEnte);
    }

    synchronized public static void setNumEnti(String strPathRootConfFile)
    {
      if (MAX_NUM_ENTI == 0)
      {
        try {
          java.util.Properties props = new java.util.Properties();
          props.load(new java.io.FileInputStream(new java.io.File(
              strPathRootConfFile, "Enti.properties")));
          MAX_NUM_ENTI = Integer.parseInt(props.getProperty("MAX_NUM_ENTI",
              "100"));
          //enteProp = new Hashtable[MAX_NUM_ENTI];
          enteProp = new Hashtable();
          eng.util.Logger.getLogger().info("MAX_NUM_ENTI="+MAX_NUM_ENTI);
        }
        catch (Exception ex) {
          eng.util.Logger.getLogger().error(ex.getMessage());
        }
      }
    }

}
