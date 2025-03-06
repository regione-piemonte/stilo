package it.eng.xml;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.converter.BigDecimalConverters;
import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga.Colonna;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;

public class XmlUtilityDeserializer {

	private static Logger mLogger = Logger.getLogger(XmlUtilityDeserializer.class);

	public <T> T unbindXml(String lStrXmlIn,  Class<T> lClass) throws JAXBException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ParseException {
		ConvertUtils.register(new BigDecimalConverters(), BigDecimal.class);
		SezioneCache lSezioneCache = (SezioneCache) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(new StringReader(lStrXmlIn));
		Map<String, Variabile> lMapValori = retrieveMap(lSezioneCache);
		Object lObject = lClass.newInstance();
		BeanWrapperImpl wrapperObject = BeanPropertyUtility.getBeanWrapper(lObject);
		//Field[] lFields = retrieveFields(lClass);
		Field[] lFields = ReflectionUtility.getFields(lClass);
		for (Field lField : lFields){
			XmlVariabile lXmlVariabile = lField.getAnnotation(XmlVariabile.class);
			if (lXmlVariabile!=null){
				Variabile lVariabile = lMapValori.remove(lXmlVariabile.nome());
				if (lVariabile != null){
					if (lXmlVariabile.tipo() == TipoVariabile.SEMPLICE){
						String property = lField.getName();
						String valore = lVariabile.getValoreSemplice();
						if (lField.getType().isEnum()){
							BeanWrapperImpl wrapperObjectEnum = BeanPropertyUtility.getBeanWrapper();
							for (Object lObjectEnum : lField.getType().getEnumConstants()){
								wrapperObjectEnum = BeanPropertyUtility.updateBeanWrapper(wrapperObjectEnum, lObjectEnum);
								String value = BeanPropertyUtility.getPropertyValueAsString(lObjectEnum, wrapperObjectEnum, "dbValue");
								// String value = BeanUtilsBean2.getInstance().getProperty(lObjectEnum, "dbValue");
								if (value.equals(valore)){
									BeanPropertyUtility.setPropertyValue(lObject, wrapperObject, property, lObjectEnum);
									// BeanUtilsBean2.getInstance().setProperty(lObject, property, lObjectEnum);
								}
							}
						} else if (Date.class.isAssignableFrom(lField.getType())){
							if (StringUtils.isNotEmpty(valore)){
								TipoData lTipoData = lField.getAnnotation(TipoData.class);
								SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(lTipoData.tipo().getPattern());
								Date lDate = lSimpleDateFormat.parse(valore);
								BeanPropertyUtility.setPropertyValue(lObject, wrapperObject, property, lDate);
								// BeanUtilsBean2.getInstance().setProperty(lObject, property, lDate);
							}
						} else {
							BeanPropertyUtility.setPropertyValue(lObject, wrapperObject, property, valore);
							// BeanUtilsBean2.getInstance().setProperty(lObject, property, valore);
						}
					} else if (lXmlVariabile.tipo() == TipoVariabile.LISTA){
						String property = lField.getName();
						ParameterizedType genericListType = (ParameterizedType) lField.getGenericType();
						Class<?> genericListClass = (Class<?>) genericListType.getActualTypeArguments()[0];
						List lList = new ArrayList();
						List<Riga> righe = lVariabile.getLista().getRiga();
						/*Field[] lFieldsLista = genericListClass.getDeclaredFields();
						if (genericListClass.getSuperclass()!=null && genericListClass.getSuperclass()!=java.lang.Object.class){
							Field[] inherited = genericListClass.getSuperclass().getDeclaredFields();
							Field[] original = lFieldsLista;
							lFieldsLista = new Field[inherited.length + original.length];
							int k = 0;
							for (Field lFieldInherited : inherited){
								lFieldsLista[k] = lFieldInherited;
								k++;
							}
							for (Field lFieldOriginal : original){
								lFieldsLista[k] = lFieldOriginal;
								k++;
							}
						}*/
						Field[] lFieldsLista = ReflectionUtility.getFields(genericListClass);
						for (Riga lRiga : righe){
							Object lObjectLista = genericListClass.newInstance();
							for (Field lFieldLista : lFieldsLista){
								NumeroColonna lNumeroColonna = lFieldLista.getAnnotation(NumeroColonna.class);
								if (lNumeroColonna!=null){
									int index = Integer.valueOf(lNumeroColonna.numero());
									for (Colonna lColonna : lRiga.getColonna()){
										if (lColonna.getNro().intValue() == index){
											String value = lColonna.getContent();
											setValueOnBean(lObjectLista, lFieldLista, lFieldLista.getName(), value);
											// BeanUtilsBean2.getInstance().setProperty(lObjectLista, lFieldLista.getName(), value);
										}
									}
								}
							}
							lList.add(lObjectLista);
						}
						BeanPropertyUtility.setPropertyValue(lObject, wrapperObject, property, lList);
						// BeanUtilsBean2.getInstance().setProperty(lObject, property, lList);
					}
				} else {
					if (lXmlVariabile.tipo() == TipoVariabile.NESTED){
						String property = lField.getName();
						Object nested = lField.getType().newInstance();
						//Field[] lFieldsNested = lField.getType().getDeclaredFields();
						Field[] lFieldsNested = ReflectionUtility.getFields(lField.getType());
						for (Field lFieldNest : lFieldsNested){
							XmlVariabile lXmlVariabileNested = lFieldNest.getAnnotation(XmlVariabile.class);
							if (lXmlVariabileNested!=null){
								Variabile lVariabileNested = lMapValori.remove(lXmlVariabile.nome() + "." + lXmlVariabileNested.nome());
								if (lVariabileNested!=null){
									if (lXmlVariabileNested.tipo() == TipoVariabile.SEMPLICE){
										String propertyNested = lFieldNest.getName();
										String valore = lVariabileNested.getValoreSemplice();
										setValueOnBean(nested, lFieldNest, propertyNested, valore);
									}
								}
							}
						}
						if (isNotNull(nested)) {
							BeanPropertyUtility.setPropertyValue(lObject, wrapperObject, property, nested);
							// BeanUtilsBean2.getInstance().setProperty(lObject, property, nested);
						}
					} else {
						mLogger.debug(lXmlVariabile.nome() + " non trovata");
					}
				}
			}
		}
		for (String lString: lMapValori.keySet()){
			mLogger.debug("Variabile non censita " + lString);
		}
		return (T) lObject;
	}
	
	private boolean isNotNull(Object beanObject) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (beanObject==null) return false;
		Map<String, Object> lMap = BeanUtilsBean2.getInstance().getPropertyUtils().describe(beanObject);
		lMap.remove("class");
		if (lMap.size()==0) return false;
		for (String lString : lMap.keySet()){
			if (lMap.get(lString)!=null){
				return true;
			}
		}
		return false;
	}
	
	/*
	protected Field[] retrieveFields(Class lClass){
		Field[] lFieldsLista = lClass.getDeclaredFields();;
		if (lClass.getSuperclass()!=null && lClass.getSuperclass()!=java.lang.Object.class){
			Field[] inherited = lClass.getSuperclass().getDeclaredFields();
			Field[] original = lFieldsLista;
			lFieldsLista = new Field[inherited.length + original.length];
			int k = 0;
			for (Field lFieldInherited : inherited){
				lFieldsLista[k] = lFieldInherited;
				k++;
			}
			for (Field lFieldOriginal : original){
				lFieldsLista[k] = lFieldOriginal;
				k++;
			}
		}
		return lFieldsLista;
	}*/

	protected void setValueOnBean(Object nested, Field lFieldNest, String propertyNested, String valore) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, ParseException {
		BeanWrapperImpl wrapperNested = BeanPropertyUtility.getBeanWrapper(nested);;
		if (lFieldNest.getType().isEnum()){
			BeanWrapperImpl wrapperObjectEnum = BeanPropertyUtility.getBeanWrapper();
			for (Object lObjectEnum : lFieldNest.getType().getEnumConstants()){
				wrapperObjectEnum = BeanPropertyUtility.updateBeanWrapper(wrapperObjectEnum, lObjectEnum);
				String value = BeanPropertyUtility.getPropertyValueAsString(lObjectEnum, wrapperObjectEnum, "dbValue");
				// String value = BeanUtilsBean2.getInstance().getProperty(lObjectEnum, "dbValue");
				if (value == null){
					if (valore == null){
						BeanPropertyUtility.setPropertyValue(nested, wrapperNested, propertyNested, lObjectEnum);
						// BeanUtilsBean2.getInstance().setProperty(nested, propertyNested, lObjectEnum);
					} 
				} else if (value.equals(valore)){
					BeanPropertyUtility.setPropertyValue(nested, wrapperNested, propertyNested, lObjectEnum);
					// BeanUtilsBean2.getInstance().setProperty(nested, propertyNested, lObjectEnum);
				}
			}
		} else if (Date.class.isAssignableFrom(lFieldNest.getType())){
			TipoData lTipoData = lFieldNest.getAnnotation(TipoData.class);
			SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(lTipoData.tipo().getPattern());
			if(StringUtils.isNotBlank(valore)) {
				Date lDate = lSimpleDateFormat.parse(valore);
				BeanPropertyUtility.setPropertyValue(nested, wrapperNested, propertyNested, lDate);
				// BeanUtilsBean2.getInstance().setProperty(nested, propertyNested, lDate);
			}
		} else {
			BeanPropertyUtility.setPropertyValue(nested, wrapperNested, propertyNested, valore);
			// BeanUtilsBean2.getInstance().setProperty(nested, propertyNested, valore);
		}
	}

	private Map<String, Variabile> retrieveMap(SezioneCache lSezioneCache) {
		Map<String, Variabile> lMap = new HashMap<String, SezioneCache.Variabile>();
		for (Variabile lVariabile : lSezioneCache.getVariabile()){
			lMap.put(lVariabile.getNome(), lVariabile);
		}
		return lMap;
	}
}
