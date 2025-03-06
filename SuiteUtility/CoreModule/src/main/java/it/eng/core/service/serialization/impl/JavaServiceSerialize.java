package it.eng.core.service.serialization.impl;

import it.eng.core.service.client.config.Configuration;
import it.eng.core.service.serialization.IServiceSerialize;

import java.io.Serializable;
import java.lang.reflect.Type;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *  Implementazione per serializare e deserializzare gli oggetti come serializzazione java
 *  Gli oggetti in ingresso devono implementare l'interfaccia java.io.Serializable
 * @author michele
 *
 */
public class JavaServiceSerialize implements IServiceSerialize{

	@Override
	public String serialize(Object obj) throws Exception {
		byte[] bytes = SerializationUtils.serialize((Serializable)obj);
		return StringUtils.toString(bytes, "ISO-8859-1");
	}

	@Override
	public Serializable deserialize(String str,Class classe,Type type) throws Exception {
		return (Serializable)SerializationUtils.deserialize(str.getBytes("ISO-8859-1"));
	}

	@Override
	public void initByClientConfig(Configuration config) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initByServerConfig() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
