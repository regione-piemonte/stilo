package it.eng.core.service.serialization.impl.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.reflections.Reflections;

import it.eng.core.annotation.XmlWrapper;
import it.eng.core.service.client.RestBusiness;

/**
 *
 * Poichè i tipi base come string, date, byte etc non sono direttamnte serializzabili se non sono in un bean, se un metodo ha nella firma uno di questi
 * paramentri, non può essere richiamato remotamente usando la serializzazione xml, per farlo si può wrappare il dato effettivo in un bean che contiene il
 * valore. Questa classe gestisce le classi wrappabili serializzabili in xml con JAXB.
 * 
 * @author Russo
 *
 */
public class WrapperManager {

	private static Logger log = Logger.getLogger(RestBusiness.class);

	private String packageToSearch = StringWrapper.class.getPackage().getName();
	/* lista dei wrapper caricati */
	private Set<Class<?>> wrappers = new HashSet<Class<?>>();
	/**
	 * mappa dei wrapper in cui la chiave è il nome della classe wrappata
	 */
	private Map<String, Class<?>> wrappersMap = new HashMap<String, Class<?>>();
	private static WrapperManager instance = null;
	// //wrappable class
	// private static Class[] wrappable={String.class,Byte.class,Boolean.class};
	//
	// public static boolean isWrappable(Class clazz){
	//
	// boolean ret=false;
	// List<Class> wrappableClass=Arrays.asList(wrappable);
	// if(wrappableClass.contains(clazz)){
	// ret=true;
	// }
	// return ret;
	// }
	//
	// public static Class getWrapperForClass(Class clazz){
	// //clazz.getCanonicalName()
	// return null;
	// }

	private WrapperManager() {
	}

	public static synchronized WrapperManager getInstance() {
		if (instance == null) {
			instance = new WrapperManager();
			instance.init();
		}
		return instance;
	}

	private void init() {
		Reflections ref = new Reflections(packageToSearch);
		wrappers = ref.getTypesAnnotatedWith(XmlWrapper.class);

		// store class in map
		for (Class<?> class1 : wrappers) {
			String forClass = class1.getAnnotation(XmlWrapper.class).forClass();
			wrappersMap.put(forClass, class1);
		}

	}

	public String getPackageToSearch() {
		return packageToSearch;
	}

	public Set<Class<?>> getWrappers() {
		return wrappers;
	}

	public Map<String, Class<?>> getWrappersMap() {
		return wrappersMap;
	}

	public boolean isWrappable(Object o) {
		boolean ret = false;
		if (o != null && o instanceof Serializable && wrappersMap.containsKey(o.getClass().getName())) {
			ret = true;
		}
		return ret;
	}

	/**
	 * costruisce un wrapper per un oggetto sserializzabile se l'oggetto NON è wrappabile rutorna null
	 * 
	 * @param o
	 * @return
	 */
	public XmlWrappable buildWrapperForObject(Serializable o) {
		XmlWrappable ret = null;
		Class clazz = o.getClass();
		try {
			if (wrappersMap.containsKey(clazz.getName())) {
				Class<?> wrapperClass = wrappersMap.get(clazz.getName());
				Object obj = wrapperClass.newInstance();
				if (obj instanceof XmlWrappable) {
					XmlWrappable wrap = (XmlWrappable) obj;
					wrap.setData(o);
					ret = wrap;
				} else {
					log.warn("Wrapper non valido " + clazz);
				}
			}
		} catch (Exception ex) {
			log.fatal("Eccezione buildWrapperForObject", ex);
		}
		return ret;
	}

}
