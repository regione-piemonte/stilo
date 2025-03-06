package it.eng.utility.springBeanWrapper;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.springBeanWrapper.bean.SpringBeanWrapperConfigBean;
import it.eng.utility.springBeanWrapper.customEditor.CustomBooleanEditor;

/**
 * 
 * @author Federico Cacco
 * 
 * Classe di utility per manipolare i bean tramite reflection e wrapper
 *
 */
public class BeanPropertyUtility {

	private static SpringBeanWrapperConfigBean springBeanWrapperConfig = null;
	private static Logger mLogger = Logger.getLogger(BeanPropertyUtility.class);
	
	/**
	 * Costruisce il bean wrapper di default, ovvero senza mappatua e non associato ad alcun bean
	 * 
	 * @return Il bean wrapper di default se la funzionalità è attivata, null altrimenti
	 */
	public static BeanWrapperImpl getBeanWrapper() {
		if (isSpringBeanWrapperEnabled()) {
			BeanWrapperImpl beanWrapper = new BeanWrapperImpl();
			registerCustomEditor(beanWrapper);
			return beanWrapper;
		} else {
			return null;
		}
	}
	
	/**
	 * Costruisce il bean wrapper mappato sull'oggetto in ingresso
	 * 
	 * @param bean Il bean su cui mappare il wrapper
	 * @return Il bean wrapper mappato sull'oggetto in ingresso se la funzionalità è attivata, null se la funzionalità è disattivata o il parametro bean è nullo
	 */
	public static BeanWrapperImpl getBeanWrapper(Object bean) {
		if (bean != null && isSpringBeanWrapperEnabled()) {
			BeanWrapperImpl beanWrapper = new BeanWrapperImpl(bean);
			registerCustomEditor(beanWrapper);
			return beanWrapper;
		} else {
			return null;
		}
	}
	
	/**
	 * Aggiorna la mappatura del wrapper sulla base dell'oggetto ricevuto in ingresso. Se l'oggetto appartiene alla stessa classe di quello mappato in 
	 * precedenza viene riutilizzata la mappatura precedente, altrimenti la mappatura viene aggiornata con quella relativa alla classe dell'oggetto in ingresso
	 * 
	 * @param beanWrapper Il wrapper da aggiornare
	 * @param bean Il bean con cui aggiornare la mappatura del wrapper
	 * @return Il wrapper aggiornato con la nuova mappatura, null se non è stato passato nessun wrapper in ingresso o il parametro bean è nullo
	 */
	public static BeanWrapperImpl updateBeanWrapper(BeanWrapperImpl beanWrapper, Object bean) {
		if (beanWrapper != null && bean != null && isSpringBeanWrapperEnabled()) {
			beanWrapper.setWrappedInstance(bean);
			return beanWrapper;
		} else {
			return null;
		}
	}
	
	/**
	 * Restituisce il valore di una proprietà del bean in ingresso, usando il BeanWrapper se esso è passato in ingresso o l'utility BeanUtilsBean2 in caso contrario
	 * L'oggetto in ingresso viene utilizzato solamente nel caso in cui non venga utilizzato il wrapper
	 * 
	 * @param bean Il bean da cui estrarre la properità (viene utilizzato se il wrapper è null)
	 * @param beanWrapper Il wrapper usato per mappare il bean
	 * @param propertyName Il nome della proprietà da cui estrarre il valore
	 * @return Il valore della proprietà
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static Object getPropertyValue(Object bean, BeanWrapperImpl beanWrapper, String propertyName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (beanWrapper != null && bean != null && isSpringBeanWrapperEnabled()) {
			if (beanWrapper.isReadableProperty(propertyName)) {
				return beanWrapper.getPropertyValue(propertyName);
			}
		}
		return BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(bean, propertyName);
	}
	
	/**
	 * Restituisce il valore di una proprietà come stringa del bean in ingresso, usando il BeanWrapper se esso è passato in ingresso o l'utility BeanUtilsBean2 in caso contrario
	 * L'oggetto in ingresso viene utilizzato solamente nel caso in cui non venga utilizzato il wrapper
	 * 
	 * @param bean Il bean da cui estrarre la properità (viene utilizzato se il wrapper è null)
	 * @param beanWrapper Il wrapper usato per mappare il bean
	 * @param propertyName Il nome della proprietà da cui estrarre il valore
	 * @return Il valore della proprietà
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static String getPropertyValueAsString(Object bean, BeanWrapperImpl beanWrapper, String propertyName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (beanWrapper != null && bean != null && isSpringBeanWrapperEnabled()) {
			if (beanWrapper.isReadableProperty(propertyName)) {
				Object propertyValue = beanWrapper.getPropertyValue(propertyName);
				//TODEL Controllare questo toString()
				return propertyValue != null ? propertyValue.toString() : null;
			}
		}
		return BeanUtilsBean2.getInstance().getProperty(bean, propertyName);
	}
	
	/**
	 * Salva il valore di una proprietà nel bean in ingresso, usando il BeanWrapper se esso è passato in ingresso o l'utility BeanUtilsBean2 in caso contrario
	 * L'oggetto in ingresso viene utilizzato solamente nel caso in cui non venga utilizzato il wrapper
	 * 
	 * @param bean Il bean su cui valorizzare la properità (viene utilizzato se il wrapper è null)
	 * @param beanWrapper Il wrapper usato per mappare il bean
	 * @param propertyName Il nome della proprietà da settare
	 * @param propertyValue Il valore della proprietà
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void setPropertyValue(Object bean, BeanWrapperImpl beanWrapper, String propertyName, Object propertyValue) throws IllegalAccessException, InvocationTargetException {
		if (beanWrapper != null && bean != null && isSpringBeanWrapperEnabled()) {
			if (beanWrapper.isWritableProperty(propertyName)) {
				beanWrapper.setPropertyValue(propertyName, propertyValue);
				return;
			}
		}
		BeanUtilsBean2.getInstance().setProperty(bean, propertyName, propertyValue);
	}
	
	public static boolean isSpringBeanWrapperEnabled() {
		if (springBeanWrapperConfig == null) {
			retrieveSpringBeanWrapperConfig();
		}
		return (springBeanWrapperConfig != null && springBeanWrapperConfig.getEnableSpringBeanWrapper() != null && springBeanWrapperConfig.getEnableSpringBeanWrapper());
	}
	
	
	private static void retrieveSpringBeanWrapperConfig() 
	{
		try
		{
			if (SpringAppContext.getContext().containsBeanDefinition("SpringBeanWrapperConfig")) 		
				springBeanWrapperConfig = (SpringBeanWrapperConfigBean) SpringAppContext.getContext().getBean("SpringBeanWrapperConfig");
			else 
			{
				springBeanWrapperConfig = new SpringBeanWrapperConfigBean();
				springBeanWrapperConfig.setEnableSpringBeanWrapper(false);
			}
		}
		catch (Exception e)
		{
			mLogger.debug("Errore nel recupero del retrieveSpringBeanWrapperConfig: " + e.getMessage() + " non attivo lo springBeanWrapper");
			springBeanWrapperConfig = new SpringBeanWrapperConfigBean();
			springBeanWrapperConfig.setEnableSpringBeanWrapper(false);
		}
	}
	
	private static void registerCustomEditor(BeanWrapperImpl beanWrapper) {
		// Setto un edit per il tipo boolean che nel metodo setAsText() mi converta le stringhe vuote in false
		// L'editor di default non gestisce la stringa vuota nel metodo setAsText(), per tutto il resto sono identici
		beanWrapper.registerCustomEditor(boolean.class, new CustomBooleanEditor(false));
	}
}
