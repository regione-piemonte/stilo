package eng.singleton;


import java.util.Hashtable;

import eng.database.modal.EngResultSet;

public class EntePropertiesSingleton {

    private static int MAX_NUM_ENTI = 0;
    
    private static EntePropertiesSingleton singleton = null;
     
    //Hashtable degli enti
    private Hashtable enteProp = null;
    
    //Costruttore privato
    private EntePropertiesSingleton() {  }

    
    /**
     * Metodo che preleva le informazioni dell'ente
     */
    private static Hashtable EntePropertiesInit(EngResultSet rsParam)
    {
        // Do initialization.
        Hashtable myEnteProp = new Hashtable();
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
    
    private static synchronized EntePropertiesSingleton getSingleton(){
    	if(singleton == null){
    		singleton = new EntePropertiesSingleton();
    	}
    	return singleton;
    }
    

    /**
     * Nel caso non ci sia l'istanza o non ci sia la voce della hashtable
     * dell'ente, allora viene richiamato l'inizializzatore
     */
    public synchronized static Hashtable getInstance(int codEnte, EngResultSet rsParam)
    {
    	return getInstance(codEnte+"", rsParam);
    }
    
    
    
    
    public static synchronized Hashtable getInstance(String codEnte, EngResultSet rsParam)
    {
    	Hashtable ret = null;
    	//Recupero il singleton altimenti lo creo
    	EntePropertiesSingleton single = getSingleton();
    	eng.util.Logger.getLogger().info("CodEnte="+codEnte);
        // Aggiungo questa verifica per i contesti che non usano Enti.properties
        if (single.getEnteProp()==null) single.setEnteProp(new Hashtable());
        ret = (Hashtable)single.getEnteProp().get(codEnte);
        if (ret == null){
           	eng.util.Logger.getLogger().debug("Inizializzo EntePropertiesSingleton per l'ente " + codEnte);
           	ret = EntePropertiesInit(rsParam);
           	single.getEnteProp().put(codEnte, ret);
        }
        return ret;
    }
    
    /**
     * Metodo che istanzia le properties con il dominio
     * @param codEnte
     * @param idDominio
     * @param rsParam
     * @return
     */
    public static synchronized Hashtable getInstance(String codEnte,String idDominio,EngResultSet rsParam)
    {
    	Hashtable ret = null;
    	//Recupero il singleton altimenti lo creo
    	EntePropertiesSingleton single = getSingleton();
    	eng.util.Logger.getLogger().info("CodEnte="+codEnte);
    	eng.util.Logger.getLogger().info("IdDominio="+idDominio);
        //Controllo se esistono già enti altrimenti creo la mappa degli enti
        if(single.getEnteProp()==null){
        	single.setEnteProp(new Hashtable());
        }
        //Recupero la hashtable dell'ente
        Hashtable entetable = (Hashtable)single.getEnteProp().get(codEnte);
        //Se null la creo nuova
        if(entetable == null){
        	single.getEnteProp().put(codEnte, new Hashtable());
        }
        //Cerco il dominio
        ret = (Hashtable)((Hashtable)single.getEnteProp().get(codEnte)).get(idDominio);
        if (ret == null){
           	eng.util.Logger.getLogger().debug("Inizializzo EntePropertiesSingleton per l'ente " + codEnte);
           	ret = EntePropertiesInit(rsParam);
           	((Hashtable)single.getEnteProp().get(codEnte)).put(idDominio, ret);
        }
        return ret;
    }

    /**
     * Nel caso non ci sia l'istanza o non ci sia la voce della hashtable
     * dell'ente, allora viene richiamato il costruttore
     */
    public synchronized static Hashtable getInstance(int codEnte)
    {
    	return getInstance(codEnte+"");
    }
    
    public synchronized static Hashtable getInstance(int codEnte,int idDominio)
    {
    	return getInstance(codEnte+"",idDominio+"");
    }
    
    

    public synchronized static Hashtable getInstance(String codEnte)
    {
    	Hashtable ret = null;
    	//Recupero il singleton altimenti lo creo
    	EntePropertiesSingleton single = getSingleton();
    	ret = (Hashtable)single.getEnteProp().get(codEnte);
        if (ret == null)
        {
        	eng.util.Logger.getLogger().debug("Inizializzo EntePropertiesSingleton per l'ente " + codEnte);
        	ret = EntePropertiesInit(null);
        	single.getEnteProp().put(codEnte, ret);
        }

        return ret;
    }
    
    public synchronized static Hashtable getInstance(String codEnte,String idDomino)
    {
    	Hashtable ret = new Hashtable();
    	//Recupero il singleton altimenti lo creo
    	EntePropertiesSingleton single = getSingleton();
    	//Recupero l'ente 
    	Hashtable entetable = (Hashtable)single.getEnteProp().get(codEnte);
        if (entetable != null)
        {
        	Hashtable dominiotable = (Hashtable)entetable.get(idDomino);
        	if(dominiotable!=null){
        		ret = dominiotable;
        	}
        }
        return ret;
    }

    synchronized public static void setNumEnti(String strPathRootConfFile)
    {
      if (MAX_NUM_ENTI == 0)
      {
        try {
          java.util.Properties props = new java.util.Properties();
          props.load(new java.io.FileInputStream(new java.io.File(strPathRootConfFile, "Enti.properties")));
          MAX_NUM_ENTI = Integer.parseInt(props.getProperty("MAX_NUM_ENTI","100"));
          //enteProp = new Hashtable[MAX_NUM_ENTI];
          getSingleton().setEnteProp(new Hashtable());
          eng.util.Logger.getLogger().info("MAX_NUM_ENTI="+MAX_NUM_ENTI);
        }
        catch (Exception ex) {
          eng.util.Logger.getLogger().error(ex.getMessage());
        }
      }
    }


	public Hashtable getEnteProp() {
		return enteProp;
	}


	public void setEnteProp(Hashtable enteProp) {
		this.enteProp = enteProp;
	}

}
