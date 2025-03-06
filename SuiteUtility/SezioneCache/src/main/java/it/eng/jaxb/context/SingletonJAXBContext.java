package it.eng.jaxb.context;

import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Riga;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class SingletonJAXBContext {

	private static JAXBContext context;
	
	private SingletonJAXBContext(){
		
	}
	
	public Marshaller createMarshaller() throws JAXBException{
		return context.createMarshaller();
	}
	
	public Unmarshaller createUnmarshaller() throws JAXBException{
		return context.createUnmarshaller();
	}
	
	public static JAXBContext getInstance() throws JAXBException{
		if (context == null){
			init();
		} 
		return context;
	}

	private static void init() throws JAXBException {
		context = JAXBContext.newInstance(new Class[]{SezioneCache.class, Lista.class, Riga.class});
	}
	 
}
