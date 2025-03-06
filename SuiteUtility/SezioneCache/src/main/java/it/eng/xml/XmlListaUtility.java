package it.eng.xml;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;

public class XmlListaUtility {

	public static <T> List<T> recuperaLista(String xmlIn, Class<T> lClass) throws Exception {
		Lista lLista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller()
				.unmarshal(new StringReader(xmlIn));
		Field[] lFields = ReflectionUtility.getFields(lClass);
		BeanWrapperImpl wrapperObject = BeanPropertyUtility.getBeanWrapper();
		List<T> lList = new ArrayList<T>();
		
		for (Riga lRiga : lLista.getRiga()) {
			Object lObject = lClass.newInstance();
			wrapperObject = BeanPropertyUtility.updateBeanWrapper(wrapperObject, lObject);
			for (Field lField : lFields) {
				NumeroColonna lNumeroColonna = lField.getAnnotation(NumeroColonna.class);
				TipoData lTipoData = lField.getAnnotation(TipoData.class);
				if (lNumeroColonna != null) {
					int index = Integer.valueOf(lNumeroColonna.numero());
					for (Colonna lColonna : lRiga.getColonna()) {
						if (lColonna.getNro().intValue() == index) {
							String value = lColonna.getContent();
							if (lTipoData != null) {
								if (StringUtils.isNotBlank(value)) {
									SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(
											lTipoData.tipo().getPattern());
									Date lDateValue = lSimpleDateFormat.parse(value);
									BeanPropertyUtility.setPropertyValue(lObject, wrapperObject, lField.getName(), lDateValue);
									// BeanUtilsBean2.getInstance().setProperty(lObject, lField.getName(), lDateValue);
								}
							} else
								BeanPropertyUtility.setPropertyValue(lObject, wrapperObject, lField.getName(), value);
								// BeanUtilsBean2.getInstance().setProperty(lObject, lField.getName(), value);
						}
					}
				}
			}
			lList.add((T) lObject);
		}
		return lList;
	}

	/**
	 * Wrapper sincronizzato del metodo "recuperaLista" da utilizzare in
	 * contesti multithread
	 * 
	 * @param xmlIn
	 * @param lClass
	 * @return
	 * @throws Exception
	 */
	public static synchronized <T> List<T> recuperaListaSynchronized(String xmlIn, Class<T> lClass) throws Exception {

		Lista lLista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller()
				.unmarshal(new StringReader(xmlIn));
		Field[] lFields = ReflectionUtility.getFields(lClass);
		BeanWrapperImpl wrapperObject = BeanPropertyUtility.getBeanWrapper();
		List<T> lList = new ArrayList<T>();
		
		for (Riga lRiga : lLista.getRiga()) {
			Object lObject = lClass.newInstance();
			wrapperObject = BeanPropertyUtility.updateBeanWrapper(wrapperObject, lObject);
			for (Field lField : lFields) {
				NumeroColonna lNumeroColonna = lField.getAnnotation(NumeroColonna.class);
				TipoData lTipoData = lField.getAnnotation(TipoData.class);
				if (lNumeroColonna != null) {
					int index = Integer.valueOf(lNumeroColonna.numero());
					for (Colonna lColonna : lRiga.getColonna()) {
						if (lColonna.getNro().intValue() == index) {
							String value = lColonna.getContent();
							if (lTipoData != null) {
								if (StringUtils.isNotBlank(value)) {
									SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(
											lTipoData.tipo().getPattern());
									Date lDateValue = lSimpleDateFormat.parse(value);
									BeanPropertyUtility.setPropertyValue(lObject, wrapperObject, lField.getName(), lDateValue);
									// BeanUtilsBean2.getInstance().setProperty(lObject, lField.getName(), lDateValue);
								}
							} else
								BeanPropertyUtility.setPropertyValue(lObject, wrapperObject, lField.getName(), value);
								// BeanUtilsBean2.getInstance().setProperty(lObject, lField.getName(), value);
						}
					}
				}
			}
			lList.add((T) lObject);
		}
		return lList;

	}

	/**
	 * Converte la stringa data in ingresso contenente i dati in formato xml in
	 * una lista di oggetti di tipo T
	 * 
	 * @param xmlIn
	 *            xml da deserializzare nell'oggetto specificato
	 * @param lClass
	 *            il class degli oggetti deserializzati che si vogliono ottenere
	 * @param helper
	 *            classe di supporto per gestire la rimappatura dei campi in
	 *            base a vincoli esterni
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> recuperaLista(String xmlIn, Class<T> lClass, DeserializationHelper helper)
			throws Exception {
		Lista lLista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller()
				.unmarshal(new StringReader(xmlIn));
		Field[] lFields = ReflectionUtility.getFields(lClass);
		BeanWrapperImpl wrapperObject = BeanPropertyUtility.getBeanWrapper();
		List<T> lList = new ArrayList<T>();
		
		for (Riga lRiga : lLista.getRiga()) {
			T lObject = lClass.newInstance();
			wrapperObject = BeanPropertyUtility.updateBeanWrapper(wrapperObject, lObject);
			for (Field lField : lFields) {
				NumeroColonna lNumeroColonna = lField.getAnnotation(NumeroColonna.class);
				TipoData lTipoData = lField.getAnnotation(TipoData.class);
				if (lNumeroColonna != null) {
					int index = Integer.valueOf(lNumeroColonna.numero());
					for (Colonna lColonna : lRiga.getColonna()) {
						if (lColonna.getNro().intValue() == index) {
							String value = lColonna.getContent();
							if (lTipoData != null) {
								if (StringUtils.isNotBlank(value)) {
									SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(
											lTipoData.tipo().getPattern());
									Date lDateValue = lSimpleDateFormat.parse(value);
									BeanPropertyUtility.setPropertyValue(lObject, wrapperObject, lField.getName(), lDateValue);
									// BeanUtilsBean2.getInstance().setProperty(lObject, lField.getName(), lDateValue);
								}
							} else
								BeanPropertyUtility.setPropertyValue(lObject, wrapperObject, lField.getName(), value);
								// BeanUtilsBean2.getInstance().setProperty(lObject, lField.getName(), value);
						}
					}
				}
			}

			helper.remapValues(lObject, lRiga);

			lList.add(lObject);
		}
		return lList;
	}

	/**
	 * Versione sincronizzata del metodo
	 * "recuperaLista(String xmlIn, Class<T> lClass, DeserializationHelper helper)"
	 * 
	 * @param xmlIn
	 * @param lClass
	 * @param helper
	 * @return
	 * @throws Exception
	 */
	public static synchronized <T> List<T> recuperaListaSynchronized(String xmlIn, Class<T> lClass,
			DeserializationHelper helper) throws Exception {
		Lista lLista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller()
				.unmarshal(new StringReader(xmlIn));
		Field[] lFields = ReflectionUtility.getFields(lClass);
		BeanWrapperImpl wrapperObject = BeanPropertyUtility.getBeanWrapper();
		List<T> lList = new ArrayList<T>();
		
		for (Riga lRiga : lLista.getRiga()) {
			T lObject = lClass.newInstance();
			wrapperObject = BeanPropertyUtility.updateBeanWrapper(wrapperObject, lObject);
			for (Field lField : lFields) {
				NumeroColonna lNumeroColonna = lField.getAnnotation(NumeroColonna.class);
				TipoData lTipoData = lField.getAnnotation(TipoData.class);
				if (lNumeroColonna != null) {
					int index = Integer.valueOf(lNumeroColonna.numero());
					for (Colonna lColonna : lRiga.getColonna()) {
						if (lColonna.getNro().intValue() == index) {
							String value = lColonna.getContent();
							if (lTipoData != null) {
								if (StringUtils.isNotBlank(value)) {
									SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(
											lTipoData.tipo().getPattern());
									Date lDateValue = lSimpleDateFormat.parse(value);
									BeanPropertyUtility.setPropertyValue(lObject, wrapperObject, lField.getName(), lDateValue);
									// BeanUtilsBean2.getInstance().setProperty(lObject, lField.getName(), lDateValue);
								}
							} else {
								BeanPropertyUtility.setPropertyValue(lObject, wrapperObject, lField.getName(), value);
								// BeanUtilsBean2.getInstance().setProperty(lObject, lField.getName(), value);
							}
						}
					}
				}
			}

			helper.remapValues(lObject, lRiga);

			lList.add(lObject);
		}
		return lList;
	}

	public static <T> List<T> recuperaListaWithEnum(String xmlIn, Class<T> lClass) throws Exception {
		Lista lLista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller()
				.unmarshal(new StringReader(xmlIn));
		Field[] lFields = ReflectionUtility.getFields(lClass);
		BeanWrapperImpl wrapperObject = BeanPropertyUtility.getBeanWrapper();
		List<T> lList = new ArrayList<T>();
		
		for (Riga lRiga : lLista.getRiga()) {
			Object lObject = lClass.newInstance();
			wrapperObject = BeanPropertyUtility.updateBeanWrapper(wrapperObject, lObject);
			for (Field lField : lFields) {
				NumeroColonna lNumeroColonna = lField.getAnnotation(NumeroColonna.class);
				TipoData lTipoData = lField.getAnnotation(TipoData.class);
				if (lNumeroColonna != null) {
					int index = Integer.valueOf(lNumeroColonna.numero());
					for (Colonna lColonna : lRiga.getColonna()) {
						if (lColonna.getNro().intValue() == index) {
							String value = lColonna.getContent();
							if (lTipoData != null) {
								if (StringUtils.isNotBlank(value)) {
									SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(
											lTipoData.tipo().getPattern());
									Date lDateValue = lSimpleDateFormat.parse(value);
									BeanPropertyUtility.setPropertyValue(lObject, wrapperObject, lField.getName(), lDateValue);
									// BeanUtilsBean2.getInstance().setProperty(lObject, lField.getName(), lDateValue);
								}
							} else if (lField.getType().isEnum()) {
								if (StringUtils.isNotBlank(value)) {
									BeanWrapperImpl wrapperObjectEnum = BeanPropertyUtility.getBeanWrapper();
									for (Object lObjectEnum : lField.getType().getEnumConstants()) {
										wrapperObjectEnum = BeanPropertyUtility.updateBeanWrapper(wrapperObjectEnum, lObjectEnum);
										String valueEnum = null;
										try {
											// TODEL: Controllare coesto toString
											valueEnum = BeanPropertyUtility.getPropertyValueAsString(lObjectEnum, wrapperObjectEnum, "dbValue");
											//valueEnum = BeanUtilsBean2.getInstance().getProperty(lObjectEnum, "dbValue");
										} catch (Exception e) {

										}
										// Se non ha il dbValue
										if (valueEnum == null) {
											Method lMethod = lObjectEnum.getClass().getMethod("name");
											String name = (String) lMethod.invoke(lObjectEnum);
											if (name.equalsIgnoreCase(value)) {
												BeanPropertyUtility.setPropertyValue(lObject, wrapperObject, lField.getName(), lObjectEnum);
												// BeanUtilsBean2.getInstance().setProperty(lObject, lField.getName(), lObjectEnum);
												break;
											}
										} else if (value.equals(valueEnum)) {
											BeanPropertyUtility.setPropertyValue(lObject, wrapperObject, lField.getName(), lObjectEnum);
											// BeanUtilsBean2.getInstance().setProperty(lObject, lField.getName(), lObjectEnum);
											break;
										}
									}
								}
							} else {
								BeanPropertyUtility.setPropertyValue(lObject, wrapperObject, lField.getName(), value);
								// BeanUtilsBean2.getInstance().setProperty(lObject, lField.getName(), value);
							}
						}
					}
				}
			}
			lList.add((T) lObject);
		}
		return lList;
	}
}
