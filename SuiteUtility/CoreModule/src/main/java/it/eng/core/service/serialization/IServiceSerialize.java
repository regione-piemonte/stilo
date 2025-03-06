package it.eng.core.service.serialization;

import it.eng.core.service.client.config.Configuration;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Interfaccia che espone i metodi di serializzazione e deserializzazione degli oggetti
 * @author michele
 *
 */
public interface IServiceSerialize {

	/**
	 * Serializza l'oggetto passato in ingresso
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String serialize(Object obj) throws Exception;
	
	/**
	 * Deserializza la stringa passata in ingresso
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public Serializable deserialize(String str,Class classe,Type type) throws Exception;
	
	public void initByClientConfig(Configuration config) throws Exception;
	public void initByServerConfig() throws Exception;
}