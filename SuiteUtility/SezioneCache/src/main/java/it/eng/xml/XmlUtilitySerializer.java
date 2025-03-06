package it.eng.xml;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.XmlAttributiCustom;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga.Colonna;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;

/**
 * <p>Classe per la trasformazione e successiva serializzazione di un bean, 
 * contenente campi annotati con {@link XmlVariabile}, in uno di tipo {@link SezioneCache}
 * <p>Note:
 * <ul>
 * <li>I campi {@link XmlVariabile#NESTED} vengono gestiti solo fino al primo livello 
 * e solo per campi {@link XmlVariabile#SEMPLICE}  
 * <li>Vengono gestiti tutti i livelli di ereditarietà di classe
 * <li>Sono gestite anche le classi che estendono e sovrascrivono i campi e i metodi delle classi estese, 
 * <b>ma considerando valido solo il primo campo trovato navigando l'albero di ereditarietà 
 * a partire dalla classe finale verso le classi estese</b>
 * </ul>
 * </p>
 * 
 * @author fabrizio.rametta
 * @author mattia.zanin
 * @author denis.bragato
 *
 */
public class XmlUtilitySerializer {
	
	/**
     * Non vengono gestiti i valori null: se il bean ha dei campi a null non vengono inseriti nell'xml
     * 
	 * @param lObject
	 * @return
	 * @throws JAXBException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public String bindXml(Object lObject) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		return bindXml(lObject, false);
	}
	
	public String bindXml(Object lObject, boolean skipNullValuesInVariabiliLista) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		SezioneCache lSezioneCache = createSezioneCacheSkipNullValues(lObject, skipNullValuesInVariabiliLista);
		StringWriter lStringWriter = new StringWriter();
		Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
		lMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
		lMarshaller.marshal(lSezioneCache, lStringWriter);
		return lStringWriter.toString();
	}

	/**
     * Tutti i campi del bean vengono inseriti nell'xml, che siano a null oppure no
     * 
	 * @param lObject
	 * @return String
	 * @throws JAXBException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public String bindXmlCompleta(Object lObject) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		return bindXmlParziale(lObject, null);
	}
	
	/**
     * Solo i campi del bean presenti nella lista passata in ingresso vengono inseriti nell'xml, 
     * che siano null oppure no
     * 
	 * @param lObject
	 * @param lListNomiVariabiliToSet
	 * @return String
	 * @throws JAXBException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public String bindXmlParziale(Object lObject, List<String> lListNomiVariabiliToSet) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		SezioneCache lSezioneCache = createSezioneCache(lObject, lListNomiVariabiliToSet);
		StringWriter lStringWriter = new StringWriter();
		Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
		lMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
		lMarshaller.marshal(lSezioneCache, lStringWriter);
		return lStringWriter.toString();
	}
	
	/**
	 * Trasforma il bean {@code lObjectToSerialize} in un bean {@link SezioneCache},
	 * 
	 * @param lObjectToSerialize
	 * @return SezioneCache
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private SezioneCache createSezioneCacheSkipNullValues(Object lObjectToSerialize, boolean skipNullValuesInVariabiliLista) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		BeanWrapperImpl wrapperObjectToSerialize = BeanPropertyUtility.getBeanWrapper(lObjectToSerialize);
		SezioneCache lSezioneCache = new SezioneCache();
		Field[] lFieldsToSerialize = ReflectionUtility.getFields(lObjectToSerialize);
		BeanWrapperImpl wrapperObjRealValue = BeanPropertyUtility.getBeanWrapper();
		BeanWrapperImpl wrapperXmlVariabileNested = BeanPropertyUtility.getBeanWrapper();
		BeanWrapperImpl wrapperObjNestedValue = BeanPropertyUtility.getBeanWrapper();
		for (Field field : lFieldsToSerialize){
			XmlVariabile lXmlVariabile = field.getAnnotation(XmlVariabile.class);
			if (lXmlVariabile!=null){
				Variabile lVariabile = new Variabile();
				lVariabile.setNome(lXmlVariabile.nome());
				Object lObjRealValue = BeanPropertyUtility.getPropertyValue(lObjectToSerialize, wrapperObjectToSerialize, field.getName());
				// Object lObjRealValue = BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(lObjectToSerialize, field.getName());
				if (lObjRealValue!=null){
					wrapperObjRealValue = BeanPropertyUtility.updateBeanWrapper(wrapperObjRealValue, lObjRealValue);
					if (lXmlVariabile.tipo() == TipoVariabile.SEMPLICE){
						if (field.getType().isEnum()){
							setValoreSempliceVariabile(lVariabile, BeanPropertyUtility.getPropertyValueAsString(lObjRealValue, wrapperObjRealValue, "dbValue"));
							// setValoreSempliceVariabile(lVariabile, BeanUtilsBean2.getInstance().getProperty(lObjRealValue, "dbValue"));
						} else if (Date.class.isAssignableFrom(field.getType())){
							TipoData lTipoData = field.getAnnotation(TipoData.class);
							SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(lTipoData.tipo().getPattern());
							String lStrDate = lSimpleDateFormat.format((Date)lObjRealValue);
							setValoreSempliceVariabile(lVariabile, lStrDate);
						} else {
							setValoreSempliceVariabile(lVariabile, lObjRealValue + "");
						}
						lSezioneCache.getVariabile().add(lVariabile);
					} else if (lXmlVariabile.tipo() == TipoVariabile.LISTA){
						Lista lLista = null;
						if(skipNullValuesInVariabiliLista) {
							lLista = createVariabileListaSkipNullValues(lObjRealValue);
						} else {
							lLista = createVariabileLista(lObjRealValue);
						}						
						lVariabile.setLista(lLista);
						lSezioneCache.getVariabile().add(lVariabile);
					} else if (lXmlVariabile.tipo() == TipoVariabile.NESTED){
						Field[] lFieldsNested = ReflectionUtility.getFields(field.getType());
						for (Field lFieldNest : lFieldsNested){
							XmlVariabile lXmlVariabileNested = lFieldNest.getAnnotation(XmlVariabile.class);
							if (lXmlVariabileNested != null){
								wrapperXmlVariabileNested = BeanPropertyUtility.updateBeanWrapper(wrapperXmlVariabileNested, lXmlVariabileNested);
								if (lXmlVariabileNested.tipo() == TipoVariabile.SEMPLICE){
									Object lObjNestedValue = BeanPropertyUtility.getPropertyValue(lObjRealValue, wrapperObjRealValue, lFieldNest.getName());
									// Object lObjNestedValue = BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(lObjRealValue, lFieldNest.getName());
									if (lObjNestedValue != null){
										Variabile lVariabileNested = new Variabile();
										if (StringUtils.isNotBlank(lXmlVariabile.nome())) {
											lVariabileNested.setNome(lXmlVariabile.nome() + "." + lXmlVariabileNested.nome());
										} else {
											lVariabileNested.setNome(lXmlVariabileNested.nome());
										}
										if (lFieldNest.getType().isEnum()){
											if (lObjNestedValue!=null) {
												wrapperObjNestedValue = BeanPropertyUtility.updateBeanWrapper(wrapperObjNestedValue, lObjNestedValue);
												setValoreSempliceVariabile(lVariabileNested, BeanPropertyUtility.getPropertyValueAsString(lObjNestedValue, wrapperObjNestedValue, "dbValue"));
												// setValoreSempliceVariabile(lVariabileNested, BeanUtilsBean2.getInstance().getProperty(lObjNestedValue, "dbValue"));
											}
										} else if (Date.class.isAssignableFrom(lFieldNest.getType())){
											TipoData lTipoData = lFieldNest.getAnnotation(TipoData.class);
											SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(lTipoData.tipo().getPattern());
											String lStrDate = lSimpleDateFormat.format((Date)lObjNestedValue);
											setValoreSempliceVariabile(lVariabileNested, lStrDate);
										} else {
											setValoreSempliceVariabile(lVariabileNested, lObjNestedValue + "");
										}
										lSezioneCache.getVariabile().add(lVariabileNested);
									}
								}
							}
						}
					}					
				}
			}				
			XmlAttributiCustom lXmlAttributiCustom = field.getAnnotation(XmlAttributiCustom.class);
			if (lXmlAttributiCustom!=null){
				Object lObjRealValue = BeanPropertyUtility.getPropertyValue(lObjectToSerialize, wrapperObjectToSerialize, field.getName());
				// Object lObjRealValue = BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(lObjectToSerialize, field.getName());
				if (lObjRealValue!=null) {
					SezioneCache lSezioneCacheAttributiCustom = (SezioneCache) lObjRealValue;
					if(lSezioneCacheAttributiCustom != null) {
						for(Variabile lVariabileAttributiCustom : lSezioneCacheAttributiCustom.getVariabile()) {
							lSezioneCache.getVariabile().add(lVariabileAttributiCustom);
						}
					}
				}
			}							
		}
		return lSezioneCache;
	}

	/**
	 * Trasforma il bean {@code lObjectToSerialize} in un bean {@link SezioneCache},
	 * considerando solo i campi presenti nella lista {@code lListNomiVariabiliToSet}, 
	 * o tutti se la lista è null
	 *  
	 * @param lObjectToSerialize
	 * @param lListNomiVariabiliToSet
	 * @return SezioneCache
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private SezioneCache createSezioneCache(Object lObjectToSerialize, List<String> lListNomiVariabiliToSet) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		BeanWrapperImpl wrapperObjectToSerialize = BeanPropertyUtility.getBeanWrapper(lObjectToSerialize);
		SezioneCache lSezioneCache = new SezioneCache();
		Field[] lFieldsToSerialize = ReflectionUtility.getFields(lObjectToSerialize);
		BeanWrapperImpl wrapperObjRealValue = BeanPropertyUtility.getBeanWrapper();
		BeanWrapperImpl wrapperObjNestedValue = BeanPropertyUtility.getBeanWrapper();
		for (Field field : lFieldsToSerialize){
			XmlVariabile lXmlVariabile = field.getAnnotation(XmlVariabile.class);
			if (lXmlVariabile!=null){
				if (lListNomiVariabiliToSet != null && StringUtils.isNotBlank(lXmlVariabile.nome())  && !lListNomiVariabiliToSet.contains(lXmlVariabile.nome())){
					continue;
				}
				Variabile lVariabile = new Variabile();
				lVariabile.setNome(lXmlVariabile.nome());
				Object lObjRealValue = BeanPropertyUtility.getPropertyValue(lObjectToSerialize, wrapperObjectToSerialize, field.getName());
				// Object lObjRealValue = BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(lObjectToSerialize, field.getName());
				if (lObjRealValue != null) { 
					
					if (lXmlVariabile.tipo() == TipoVariabile.SEMPLICE){
						if (field.getType().isEnum()){
							if (lObjRealValue != null) {
								wrapperObjRealValue = BeanPropertyUtility.updateBeanWrapper(wrapperObjRealValue, lObjRealValue);
								setValoreSempliceVariabile(lVariabile, BeanPropertyUtility.getPropertyValueAsString(lObjRealValue, wrapperObjRealValue, "dbValue"), lListNomiVariabiliToSet);
								// setValoreSempliceVariabile(lVariabile, BeanUtilsBean2.getInstance().getProperty(lObjRealValue, "dbValue"), lListNomiVariabiliToSet);
							}
						} else if (Date.class.isAssignableFrom(field.getType())){
							TipoData lTipoData = field.getAnnotation(TipoData.class);
							SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(lTipoData.tipo().getPattern());
							String lStrDate = lSimpleDateFormat.format((Date)lObjRealValue);
							setValoreSempliceVariabile(lVariabile, lStrDate, lListNomiVariabiliToSet);
						} else setValoreSempliceVariabile(lVariabile, lObjRealValue + "", lListNomiVariabiliToSet);
						lSezioneCache.getVariabile().add(lVariabile);
					} else if (lXmlVariabile.tipo() == TipoVariabile.LISTA){
						Lista lLista = createVariabileLista(lObjRealValue);
						lVariabile.setLista(lLista);
						lSezioneCache.getVariabile().add(lVariabile);
					} else if (lXmlVariabile.tipo() == TipoVariabile.NESTED){
						Field[] lFieldsNested = ReflectionUtility.getFields(field.getType());
						for (Field lFieldNest : lFieldsNested){
							XmlVariabile lXmlVariabileNested = lFieldNest.getAnnotation(XmlVariabile.class);
							if (lXmlVariabileNested!=null){
								if (lXmlVariabileNested.tipo() == TipoVariabile.SEMPLICE){
									Object lObjNestedValue = BeanPropertyUtility.getPropertyValue(lObjRealValue, wrapperObjRealValue, lFieldNest.getName());
									// Object lObjNestedValue = BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(lObjRealValue, lFieldNest.getName());
									Variabile lVariabileNested = new Variabile();
									if (StringUtils.isNotBlank(lXmlVariabile.nome())) {
										lVariabileNested.setNome(lXmlVariabile.nome() + "." + lXmlVariabileNested.nome());
									}else { 
										lVariabileNested.setNome(lXmlVariabileNested.nome());
									}
									if (lObjNestedValue != null){	
										if (lFieldNest.getType().isEnum()){
											if (lObjNestedValue != null) {
												wrapperObjNestedValue = BeanPropertyUtility.updateBeanWrapper(wrapperObjNestedValue, lObjNestedValue);
												setValoreSempliceVariabile(lVariabileNested, BeanPropertyUtility.getPropertyValueAsString(lObjNestedValue, wrapperObjNestedValue, "dbValue"), lListNomiVariabiliToSet);
												// setValoreSempliceVariabile(lVariabileNested, BeanUtilsBean2.getInstance().getProperty(lObjNestedValue, "dbValue"), lListNomiVariabiliToSet);
											}
										} else if (Date.class.isAssignableFrom(lFieldNest.getType())){
											TipoData lTipoData = lFieldNest.getAnnotation(TipoData.class);
											SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(lTipoData.tipo().getPattern());
											String lStrDate = lSimpleDateFormat.format((Date)lObjNestedValue);
											setValoreSempliceVariabile(lVariabileNested, lStrDate, lListNomiVariabiliToSet);
										} else {
											setValoreSempliceVariabile(lVariabileNested, lObjNestedValue + "", lListNomiVariabiliToSet);
										}
										lSezioneCache.getVariabile().add(lVariabileNested);
									} else {										
										setValoreSempliceVariabile(lVariabileNested, "", lListNomiVariabiliToSet);
										lSezioneCache.getVariabile().add(lVariabileNested);																																																				
									}
								}
							}
						}
					}					
				} else {
					if (lXmlVariabile.tipo() == TipoVariabile.SEMPLICE){
						setValoreSempliceVariabile(lVariabile, "", lListNomiVariabiliToSet);
						lSezioneCache.getVariabile().add(lVariabile);
					} 											
				}
			}
			XmlAttributiCustom lXmlAttributiCustom = field.getAnnotation(XmlAttributiCustom.class);
			if (lXmlAttributiCustom!=null){
				Object lObjRealValue = BeanPropertyUtility.getPropertyValue(lObjectToSerialize, wrapperObjectToSerialize, field.getName());
				// Object lObjRealValue = BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(lObjectToSerialize, field.getName());
				if (lObjRealValue!=null) {
					SezioneCache lSezioneCacheAttributiCustom = (SezioneCache) lObjRealValue;
					if(lSezioneCacheAttributiCustom != null) {
						for(Variabile lVariabileAttributiCustom : lSezioneCacheAttributiCustom.getVariabile()) {
							lSezioneCache.getVariabile().add(lVariabileAttributiCustom);
						}
					}
				}
			}				
		}
		return lSezioneCache;
	}
	
	private void setValoreSempliceVariabile(Variabile lVariabile, String lValue, List<String> lListNomiVariabiliToSet) {
		if(lListNomiVariabiliToSet == null || lListNomiVariabiliToSet.contains(lVariabile.getNome())) {
			setValoreSempliceVariabile(lVariabile, lValue);
		}
	}
	
	private void setValoreSempliceVariabile(Variabile lVariabile, String lValue) {
		lVariabile.setValoreSemplice(lValue);
	}
	
	private void setListaVariabile(Variabile lVariabile, Lista lLista) {
		lVariabile.setLista(lLista);
	}
	
	public String bindXmlList(List<?> lista) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		if (lista == null) return null; 
		it.eng.jaxb.variabili.Lista lLista = new it.eng.jaxb.variabili.Lista();
		BeanWrapperImpl wrapperObject = BeanPropertyUtility.getBeanWrapper();
		for (Object lObject : lista){
			wrapperObject = BeanPropertyUtility.updateBeanWrapper(wrapperObject, lObject);
			it.eng.jaxb.variabili.Lista.Riga lRiga = new it.eng.jaxb.variabili.Lista.Riga();
			Field[] lFields = ReflectionUtility.getFields(lObject);
			for (Field lField : lFields){
				NumeroColonna lNumeroColonna = lField.getAnnotation(NumeroColonna.class);
				TipoData lTipoData = lField.getAnnotation(TipoData.class);
				if (lNumeroColonna != null){
					String value = null;
					if (lTipoData != null){
						Date lDate = (Date) BeanPropertyUtility.getPropertyValue(lObject, wrapperObject, lField.getName());
						// Date lDate = (Date) BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(lObject, lField.getName());
						if (lDate == null) value = null;
						else {
							SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(lTipoData.tipo().getPattern());
							value = lSimpleDateFormat.format((Date)lDate);
						}
					} else {
						value = BeanPropertyUtility.getPropertyValueAsString(lObject, wrapperObject, lField.getName());
						// value = BeanUtilsBean2.getInstance().getProperty(lObject, lField.getName());
					}
					if (value == null) {
						value ="";
					}
					it.eng.jaxb.variabili.Lista.Riga.Colonna lColonna = new it.eng.jaxb.variabili.Lista.Riga.Colonna();
					lColonna.setContent(value);
					lColonna.setNro(new BigInteger(lNumeroColonna.numero()));
					lRiga.getColonna().add(lColonna);
				}
			}
			lLista.getRiga().add(lRiga);
		}
		StringWriter lStringWriter = new StringWriter();
		Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
		lMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
		lMarshaller.marshal(lLista, lStringWriter);
		return lStringWriter.toString();
	}

	/**
	 * <p>Trasforma il bean {@code lObjRealValue} 
	 * in una {@link it.eng.jaxb.variabili.SezioneCache.Variabile.Lista}, 
	 * popolando la lista
	 * 
	 * @param lObjRealValue
	 * @param lLista
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * 
	 * @author denis.bragato
	 */
	
	public Lista createVariabileListaSkipNullValues(Object lObjRealValue) throws SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if(lObjRealValue instanceof Lista) {
			return (Lista) lObjRealValue;
		}
		BeanWrapperImpl wrapperObject = BeanPropertyUtility.getBeanWrapper();
		Lista lLista = new Lista();
		List<?> lista = (List<?>)lObjRealValue;
		for (Object lObject : lista){
			wrapperObject = BeanPropertyUtility.updateBeanWrapper(wrapperObject, lObject);
			Riga lRiga = new Riga();
			Field[] lFields = ReflectionUtility.getFields(lObject);
			for (Field lField : lFields){
				NumeroColonna lNumeroColonna = lField.getAnnotation(NumeroColonna.class);
				TipoData lTipoData = lField.getAnnotation(TipoData.class);
				if (lNumeroColonna!=null){
					String value = null;
					if (lTipoData!=null){
						Date lDate = (Date) BeanPropertyUtility.getPropertyValue(lObject, wrapperObject, lField.getName());
						// Date lDate = (Date) BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(lObject, lField.getName());
						if (lDate == null) {
							value = null;
						} else {
							SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(lTipoData.tipo().getPattern());
							value = lSimpleDateFormat.format((Date)lDate);
						}
					} else {
						value = BeanPropertyUtility.getPropertyValueAsString(lObject, wrapperObject, lField.getName());
						// value = BeanUtilsBean2.getInstance().getProperty(lObject, lField.getName());
					}
					if (value != null) {
						Colonna lColonna = new Colonna();
						lColonna.setContent(value);
						lColonna.setNro(new BigInteger(lNumeroColonna.numero()));
						lRiga.getColonna().add(lColonna);
					}
				}
			}
			lLista.getRiga().add(lRiga);
		}
		return lLista;
	}
	
	public Lista createVariabileLista(Object lObjRealValue) throws SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if(lObjRealValue instanceof Lista) {
			return (Lista) lObjRealValue;
		}
		BeanWrapperImpl wrapperObject = BeanPropertyUtility.getBeanWrapper();
		Lista lLista = new Lista();
		List<?> lista = (List<?>)lObjRealValue;
		for (Object lObject : lista){
			wrapperObject = BeanPropertyUtility.updateBeanWrapper(wrapperObject, lObject);
			Riga lRiga = new Riga();
			Field[] lFields = ReflectionUtility.getFields(lObject);
			for (Field lField : lFields){
				NumeroColonna lNumeroColonna = lField.getAnnotation(NumeroColonna.class);
				TipoData lTipoData = lField.getAnnotation(TipoData.class);
				if (lNumeroColonna!=null){
					String value = null;
					if (lTipoData!=null){
						Date lDate = (Date) BeanPropertyUtility.getPropertyValue(lObject, wrapperObject, lField.getName());
						// Date lDate = (Date) BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(lObject, lField.getName());
						if (lDate == null) {
							value = null;
						} else {
							SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(lTipoData.tipo().getPattern());
							value = lSimpleDateFormat.format((Date)lDate);
						}
					} else {
						value = BeanPropertyUtility.getPropertyValueAsString(lObject, wrapperObject, lField.getName());
						// value = BeanUtilsBean2.getInstance().getProperty(lObject, lField.getName());
					}
					if (value == null) {
						value = "";
					}
					Colonna lColonna = new Colonna();
					lColonna.setContent(value);
					lColonna.setNro(new BigInteger(lNumeroColonna.numero()));
					lRiga.getColonna().add(lColonna);
				}
			}
			lLista.getRiga().add(lRiga);
		}
		return lLista;
	}

}
