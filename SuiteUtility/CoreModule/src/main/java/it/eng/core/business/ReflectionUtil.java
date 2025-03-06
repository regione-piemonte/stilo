package it.eng.core.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Embeddable;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.reflections.Reflections;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.core.business.beans.AbstractBean;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;

/**
 * Classe contenente metodi di utility per le operazioni di reflection
 * 
 * @author upescato
 *
 */

public class ReflectionUtil {

	static Logger log = Logger.getLogger(ReflectionUtil.class);

	private static String entityPackage = null;

	/**
	 * Lista con package dove sono contenute le entity
	 */

	private static List<String> listEntityPackage = null;

	/**
	 * Metodo che esegue una get a DB recuperando l'effettiva entity a partire dai dati passati in input
	 * 
	 * @param session
	 *            - Sessione a DB
	 * @param util
	 *            - entita' di BeanUtils
	 * @param bean
	 *            - Istanza del bean
	 * @param classe
	 *            - Classe della entity DAO che si vuole ritornare
	 * @return - istanza di una entity
	 * @throws Exception
	 */
	public static Object getEntity(Session session, PropertyUtilsBean util, AbstractBean bean, Class<?> classe) throws Exception {

		String objectPrimaryKey = HibernateUtil.getPrimaryKey(classe);
		Class<?> classPrimarykey = classe.getDeclaredField(objectPrimaryKey).getType();

		Object dest;

		// 1. verifico se l'entity ha una chiave composta
		if (entityPackage == null && (listEntityPackage == null || listEntityPackage.isEmpty())) {
			throw new Exception("Nessun entityPackage configurato!");
		} else {
			if ((listEntityPackage == null || listEntityPackage.isEmpty())) {
				listEntityPackage = new ArrayList<String>();
				listEntityPackage.add(entityPackage);
			} else if (entityPackage != null && !listEntityPackage.contains(entityPackage)) {
				listEntityPackage.add(entityPackage);
			}
			log.debug("entityPackages: " + listEntityPackage);
		}

		// scorro la lista di package e recupero le entità
		boolean isEmbeddableId = false;
		for (Iterator<String> iterator = listEntityPackage.iterator(); iterator.hasNext() && !isEmbeddableId;) {

			String tmpEntityPackage = (String) iterator.next();
			Reflections reflections = new Reflections(tmpEntityPackage);

			Set<Class<?>> embeddableClasses = reflections.getTypesAnnotatedWith(Embeddable.class);

			Iterator<?> iter = embeddableClasses.iterator();
			while (iter.hasNext()) {
				Class<?> embeddableClass = (Class<?>) iter.next();
				if (embeddableClass.equals(classPrimarykey)) {
					isEmbeddableId = true;
					break;
				}
			}
		}

		// 2. in caso affermativo, mi costruisco la chiave primaria composta
		if (isEmbeddableId) {
			String[] properties = new String[classPrimarykey.getDeclaredFields().length];
			for (int i = 0; i < classPrimarykey.getDeclaredFields().length; i++) {
				properties[i] = classPrimarykey.getDeclaredFields()[i].getName();
			}

			Object objectChiaveComposta = classPrimarykey.newInstance();
			BeanWrapperImpl wrapperObjectChiaveComposta = BeanPropertyUtility.getBeanWrapper(objectChiaveComposta);
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);

			// instanzio l'oggetto della chiave con i valori delle property
			for (String prop : properties) {
				Object beanPropertyValue = BeanPropertyUtility.getPropertyValue(bean, wrapperBean, prop);
				BeanPropertyUtility.setPropertyValue(objectChiaveComposta, wrapperObjectChiaveComposta, prop, beanPropertyValue);
				// util.setProperty(objectChiaveComposta, prop, util.getProperty(bean, prop));
			}

			// Recupero l'entita' che sto aggiornando
			dest = session.get(classe, (Serializable) objectChiaveComposta);
		}
		// 3. altrimenti lavoro con la chiave semplice (i.e.: l'id)
		else {
			dest = session.get(classe, (Serializable) util.getProperty(bean, objectPrimaryKey));
		}

		if (dest == null) {
			return null;
		} else
			return dest;
	}
	/*
	 * public static Object getEntity(Session session, PropertyUtilsBean util, AbstractBean bean, Class<?> classe) throws Exception { String objectPrimaryKey =
	 * HibernateUtil.getPrimaryKey(classe);
	 * 
	 * Object dest;
	 * 
	 * //1. verifico se il bean in questione è annotato come bean a chiaveComposta Reflections reflections = new Reflections("it.eng.iris"); Set<Class<?>>
	 * chiaviComposta = reflections.getTypesAnnotatedWith(ChiaveComposta.class);
	 * 
	 * //2. se lo è, recupero le property che compongono la chiave e le setto in //un'istanza dell'entità corrispondente alla chiave composta
	 * if(chiaviComposta!=null && chiaviComposta.contains(bean.getClass())) { String[] properties = null; Iterator<?> iter = chiaviComposta.iterator();
	 * while(iter.hasNext()) { Class<?> annotatedClass = (Class<?>)iter.next(); if(annotatedClass.equals(bean.getClass())) { properties =
	 * ((ChiaveComposta)annotatedClass.getAnnotation(ChiaveComposta.class)).properties(); break; } } Class<?> clazzChiaveComposta =
	 * classe.getDeclaredField(objectPrimaryKey).getType(); Object objChiaveComposta = clazzChiaveComposta.newInstance();
	 * 
	 * //instanzio l'oggetto della chiave con i valori delle property for(String prop:properties) { util.setProperty(objChiaveComposta, prop,
	 * util.getProperty(bean, prop)); }
	 * 
	 * //Recupero l'entita' che sto aggiornando dest = session.get(classe, (Serializable)objChiaveComposta); }
	 * 
	 * //3. se il bean non ha chiave composta, lavoro con la chiave semplice (i.e.: l'id) else { dest = session.get(classe, (Serializable)util.getProperty(bean,
	 * objectPrimaryKey)); }
	 * 
	 * if(dest==null) { return null; } else return dest; }
	 */

	public static String getEntityPackage() {
		return entityPackage;
	}

	public static void setEntityPackage(String entityPackage) {
		ReflectionUtil.entityPackage = entityPackage;
	}

	public static List<String> getListEntityPackage() {
		return listEntityPackage;
	}

	public static void setListEntityPackage(List<String> listEntityPackage) {
		ReflectionUtil.listEntityPackage = listEntityPackage;
	}

}

