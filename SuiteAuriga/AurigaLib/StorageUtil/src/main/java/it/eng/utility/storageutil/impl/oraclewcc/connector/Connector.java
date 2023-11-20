/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.storageutil.impl.oraclewcc.OracleWccStorageConfig;
import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.IdcClientManager;
import oracle.stellent.ridc.IdcContext;
import oracle.stellent.ridc.protocol.intradoc.IntradocClient;
import oracle.stellent.ridc.protocol.intradoc.IntradocClientConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Connector {
	
	private static final Log oLogger = LogFactory.getLog(Connector.class);
	private static Connector INSTANCE;
	private IdcContext context=null;
	private String serverUrl=null;
	private IdcClientManager manager = null;
	private OracleWccStorageConfig config;

	/**
	 * 
	 * @param config
	 * @return
	 */
	public synchronized static Connector getInstance(OracleWccStorageConfig config)
	{
		if(INSTANCE==null)
		{
			INSTANCE= new Connector(config);
		}
		return INSTANCE;
	}
	
	private Connector(OracleWccStorageConfig config){
		
		try{
			this.config=config;
	        manager = new IdcClientManager ();
	        StringBuilder sb = new StringBuilder();
	        boolean useSSL=Boolean.parseBoolean(config.getUseSSL());
	        if(useSSL){
	        	sb.append("idcs://");
	        }else{
	        	sb.append("idc://");
	        }
	        sb.append(config.getHost()).append(":").append(config.getPort());
	        serverUrl = sb.toString();
	        context = new IdcContext(config.getContentUser(), config.getPassword());
	        }catch(Exception ex){
				oLogger.error("Errore durante l'inizializazzione del connettore ", ex);
				throw new RuntimeException("Errore durante l'inizializzazione del connettore "+ex.getMessage());
			}
	}
	public  IdcContext getIdcContext (){		
		    return context;
	} 
	
	public IntradocClient getIntradocClient() throws IdcClientException{
		IntradocClient client = (IntradocClient) manager.createClient(serverUrl);
		IntradocClientConfig configClient= client.getConfig();
		configClient.setKeystoreFile (config.getSslKeystoreFile()); // keystore file
		configClient.setKeystorePassword (config.getSslKeystorePassword()); // keystore password
		configClient.setKeystoreAlias (config.getSslKeystoreAlias()); // keystore alias
		configClient.setKeystoreAliasPassword (config.getSslKeystoreAliasPassword()); // keystore alias password    
		configClient.setSocketTimeout (Integer.parseInt(String.valueOf(config.getConnectionTimeout()))); // timeout        
		return client;
	}
}