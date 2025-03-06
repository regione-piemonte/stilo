package it.eng.core.service.serialization.impl;

import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.reflections.Reflections;

import it.eng.core.business.exception.ServiceException;
import it.eng.core.config.ConfigUtil;
import it.eng.core.config.CoreConfig;
import it.eng.core.service.client.config.Configuration;
import it.eng.core.service.serialization.IServiceSerialize;
import it.eng.core.service.serialization.impl.base.WrapperManager;
import it.eng.core.service.serialization.impl.base.XmlWrappable;

/**
 * Implementazione per serializare e deserializzare gli oggetti come xml Gli oggetti in ingresso devono avere l'annotation
 * javax.xml.bind.annotation.XmlRootElement
 * 
 * @author michele
 * 
 */
public class XmlServiceSerialize implements IServiceSerialize {

	public static final String packageToSearch = "PACKAGE_TO_SEARCH";
	private static Logger log = Logger.getLogger(XmlServiceSerialize.class);

	private static JAXBContext context;

	/**
	 * Setting del contestpo jaxb
	 * 
	 * @param reflection
	 * @throws JAXBException
	 */
	// public static void settingContext(Reflections reflection) throws JAXBException{
	// //Istanzio il contesto jaxb
	// Set<Class<?>> classesXML = reflection.getTypesAnnotatedWith(XmlRootElement.class);
	// context = JAXBContext.newInstance(classesXML.toArray(new Class[0]));
	// }
	//
	// public static void settingContext(Set<Class<?>> classesXML) throws JAXBException{
	// context = JAXBContext.newInstance(classesXML.toArray(new Class[0]));
	// }
	@Override
	public String serialize(Object obj) throws Exception {
		if (context == null) {
			log.fatal("serializer non inizializzato");
			throw new ServiceException(CoreConfig.modulename, "XML_SER_NOT_CONFIG");
		}
		String ret = null;
		if (obj != null) {
			Object toMarshall = obj;
			// controlo se è un tipo base wrappabile
			if (WrapperManager.getInstance().isWrappable(obj)) {
				toMarshall = WrapperManager.getInstance().buildWrapperForObject((Serializable) obj);
			}
			StringWriter writer = new StringWriter();
			context.createMarshaller().marshal(toMarshall, writer);
			ret = writer.toString();
			// retrieveEmptyList(obj);
		}
		return ret;
	}

	@Override
	public Serializable deserialize(String str, Class classe, Type type) throws Exception {
		if (context == null) {
			log.fatal("serializer non inizializzato");
			throw new ServiceException(CoreConfig.modulename, "XML_SER_NOT_CONFIG");
		}
		Serializable ret = null;
		if (str != null) {
			str = stripNonValidXMLCharacters(str);
			ret = (Serializable) context.createUnmarshaller().unmarshal(new StringReader(str));
			if (ret instanceof XmlWrappable) {
				ret = ((XmlWrappable) ret).getData();
			}
		}
		return ret;
	}

	public String stripNonValidXMLCharacters(String in) {
		StringBuffer out = new StringBuffer(); // Used to hold the output.
		char current; // Used to reference the current character.

		if (in == null || ("".equals(in)))
			return ""; // vacancy test.
		for (int i = 0; i < in.length(); i++) {
			current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught here; it should not happen.
			if ((current == 0x9) || (current == 0xA) || (current == 0xD) || ((current >= 0x20) && (current <= 0xD7FF))
					|| ((current >= 0xE000) && (current <= 0xFFFD)) || ((current >= 0x10000) && (current <= 0x10FFFF))) {
				out.append(current);
			} else {
				log.debug(current);
			}
		}
		return out.toString();
	}

	@Override
	public void initByClientConfig(Configuration config) throws Exception {
		log.info(">>>> init Serializer from ClientConfig");
		String packageToSearch = "it.eng";
		if (!StringUtils.isEmpty(config.getPackageref())) {
			packageToSearch = config.getPackageref();
		} else {
			log.warn("package in cui cercare non trovato use il default:" + packageToSearch);
		}
		init(packageToSearch);
	}

	@Override
	public void initByServerConfig() throws Exception {
		log.info(">>>> init Serializer from Server Config");
		// in tal caso puoi usare anche il registro
		String packageToSearch = ConfigUtil.getServicePackage();
		init(packageToSearch);
	}

	private void init(String packageToSearch) throws Exception {
		Reflections ref = new Reflections(packageToSearch);
		Set<Class<?>> classesXML = ref.getTypesAnnotatedWith(XmlRootElement.class);
		// aggiungo le classi wrapper
		Set<Class<?>> tot = new HashSet<Class<?>>();
		tot.addAll(classesXML);
		tot.addAll(WrapperManager.getInstance().getWrappers());
		context = JAXBContext.newInstance(tot.toArray(new Class[0]));
	}
}