package it.eng.utility.cryptosigner.controller.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class OutputBean {

	private Map<String, Object> properties = new LinkedHashMap<String, Object>();

	private OutputSignerBean child;

	/**
	 * Recupera una proprieta settata nel bean
	 * 
	 * @param key
	 *            nome della proprieta da recuperare
	 * @return valore della proprieta
	 */
	public Object getProperty(String key) {
		return properties.get(key);
	}

	/**
	 * Definisce il valore di una proprieta del bean
	 * 
	 * @param key
	 *            nome della proprieta
	 * @param value
	 *            valore della proprieta
	 */
	public void setProperty(String key, Object value) {
		properties.put(key, value);
	}

	/**
	 * Recupera tutte le proprieta settate nel bean
	 * 
	 * @return la mappa tra i nomi e i valori delle proprieta
	 */
	public Map<String, Object> getProperties() {
		return properties;
	}

	/**
	 * Definisce le proprieta del bean
	 * 
	 * @param properties
	 */
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	/**
	 * Recupera l'istanza dell'{@link it.eng.crypto.controller.bean.OutputSignerBean OutputSignerBean} linkato (contenente il risultato del successivo ciclo di
	 * analisi)
	 * 
	 * @return output
	 */
	public OutputSignerBean getChild() {
		return child;
	}

	/**
	 * Definisce l'istanza dell'{@link it.eng.crypto.controller.bean.OutputSignerBean OutputSignerBean} linkato (contenente il risultato del successivo ciclo di
	 * analisi)
	 */
	public void setChild(OutputSignerBean child) {
		this.child = child;
	}

	public String toString() {
		return "Properties: " + properties == null ? "" : properties.toString();
	}

	public <T> Map<String, T> getPropsOfType(Class<T> clazz) {
		Map<String, T> ret = new HashMap<String, T>();
		Map<String, Object> props = getProperties();
		java.util.Set<String> keys = props.keySet();
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Object o = props.get(key);
			// if(o.getClass().getName().equals(clazz.getName())){
			if (clazz.isAssignableFrom(o.getClass())) {
				ret.put(key, (T) o);
			}
		}
		return ret;
	}

	public <T> T getPropOfType(String property, Class<T> clazz) {
		if (properties == null)
			return null;
		T o = null;
		try {
			o = (T) properties.get(property);
		} catch (Exception ex) {

		}
		return o;
	}
}
