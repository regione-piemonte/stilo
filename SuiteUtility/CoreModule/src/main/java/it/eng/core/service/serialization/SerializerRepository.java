package it.eng.core.service.serialization;

import it.eng.core.business.exception.ServiceException;
import it.eng.core.config.CoreConfig;
import it.eng.core.service.client.config.Configuration;
import it.eng.core.service.serialization.impl.JavaServiceSerialize;
import it.eng.core.service.serialization.impl.JsonServiceSerialize;
import it.eng.core.service.serialization.impl.XmlServiceSerialize;

import java.util.EnumMap;

import org.apache.log4j.Logger;


/**
 * Factory dei serializzatori
 *
 */
public class SerializerRepository {
	private static Logger log = Logger.getLogger(SerializerRepository.class);
	 
	private static EnumMap<SerializationType, IServiceSerialize> maps= new EnumMap<SerializationType, IServiceSerialize>(SerializationType.class);
	/**
	 * Ritorna l'implementazione delle classe di serializzazione utilizzata
	 * @return
	 */
	public static IServiceSerialize getSerializationUtil(SerializationType type)throws Exception{
		 if(!maps.containsKey(type)){
			  log.fatal("Serializer non inizializzati ");
			  throw new ServiceException(CoreConfig.modulename, "SER_NOT_INIT");
		 }
		 return maps.get(type);
	}
	
	//init serializer from client config
	public static void initSerializerClient(Configuration config)throws Exception{
		if(maps.size()>0){
			log.warn("Repository già inizializzato reinit ");
		}
		SerializationType type=config.getSerializationtype();
		log.info(">>> init Serializer from client Config <"+type+">");
		switch(type){
		case XML:
			XmlServiceSerialize xmlserializer=new XmlServiceSerialize();
			xmlserializer.initByClientConfig(config);
			maps.put(SerializationType.XML, xmlserializer);
			break;
		case JSON:
			JsonServiceSerialize jsonSerializer=new JsonServiceSerialize();
			jsonSerializer.initByClientConfig(config);
			maps.put(SerializationType.JSON, jsonSerializer);
			break;
		case JAVA_SERIALIZE:
			JavaServiceSerialize javaserializer= new JavaServiceSerialize();
			javaserializer.initByClientConfig(config);
			maps.put(SerializationType.JAVA_SERIALIZE, javaserializer);
			break;
		default:
			log.fatal("serializer non valido....");
			throw new Exception("Serializer non valido");
			//break;
		}
	}
	
	//init serializer from server config
	public static void initSerializerServer()throws Exception{
		log.info("init all server serializer");
		 //TODO rendere dinamici i serilizer!?
		if(maps.size()>0){
			log.warn("Repository già inizializzato reinit ");
		}
		SerializationType[] values=SerializationType.values();
		//IServiceSerialize[] serializer={new XmlServiceSerialize(),new JsonServiceSerialize(),new JavaServiceSerialize()};
		for (int i = 0; i < values.length; i++) {
			log.info("init serializer "+values[i]+" from server");
			switch(values[i]){
			case XML:
				XmlServiceSerialize xmlserializer=new XmlServiceSerialize();
				xmlserializer.initByServerConfig();
				maps.put(SerializationType.XML, xmlserializer);
				break;
			case JSON:
				JsonServiceSerialize jsonSerializer=new JsonServiceSerialize();
				jsonSerializer.initByServerConfig();
				maps.put(SerializationType.JSON, jsonSerializer);
				break;
			case JAVA_SERIALIZE:
				JavaServiceSerialize javaserializer= new JavaServiceSerialize();
				javaserializer.initByServerConfig();
				maps.put(SerializationType.JAVA_SERIALIZE, javaserializer);
				break;
			default:
				
				break;
			}
			 
		} 
	}
}
