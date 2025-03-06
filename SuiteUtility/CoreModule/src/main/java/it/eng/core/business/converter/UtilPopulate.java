package it.eng.core.business.converter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.hibernate.Session;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.core.business.ReflectionUtil;
import it.eng.core.business.beans.AbstractBean;
import it.eng.core.business.exception.ServiceException;
import it.eng.core.config.CoreConfig;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;

public class UtilPopulate {

	/**
	 * Crea una lista di oggetti del tipo popolandoli con la lista passati in ingresso 
	 * @param inputs
	 * @param classe
	 * @return
	 * @throws Exception
	 */
	public static List populateList(List inputs,Class classe) throws Exception{
		return populateList(inputs,classe,null);
	}

	public static List populateList(List inputs,Class classe,IBeanPopulate populate) throws Exception{
		if(inputs!=null){
			List ret = new ArrayList();	
			for(Object org:inputs){
				Object dest = classe.newInstance();
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(dest, org);
				if(populate!=null){
					populate.populate(org, dest);
				}
				ret.add(dest);
			}
			return ret;
		}else{
			return null;
		}
	}

	/**
	 * Crea un oggetto del tipo passato in ingresso popolando con l'oggetto bean.
	 * <strong>da usare</strong> nel caso di update di entities
	 * @param session - Sessione corrente di Hibernate
	 * @param bean - Oggetto in input
	 * @param classe - Tipo di oggetto che verrà istanziato e ritornato dal metodo.
	 * @param populate - Instanza del populate specifico per i tipi passati in input
	 * @return
	 * @throws Exception
	 */
	public static Object populateForUpdate(Session session, AbstractBean bean, Class classe, IBeanPopulate populate) throws Exception{

		if(session==null) {
			throw new ServiceException(CoreConfig.modulename,"HB_SESS_EMPTY");
			//throw new Exception("La sessione Hibernate non è correttamente instanziata.");
			}

		if(bean!=null){
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			PropertyUtilsBean util = BeanUtilsBean2.getInstance().getPropertyUtils();

			Object dest = ReflectionUtil.getEntity(session, util, bean, classe);
			BeanWrapperImpl wrapperDest = BeanPropertyUtility.getBeanWrapper(dest);
			
			if(dest==null) {
				return null;
			}

			//Dal bean astratto recupero le property effettivamente modificate (quelle contenute nell'HashSet)
			for(String changedProperty: bean.getUpdatedProperties()) {
				try {
					Object beanPropertyValue = BeanPropertyUtility.getPropertyValue(bean, wrapperBean, changedProperty);
					BeanPropertyUtility.setPropertyValue(dest, wrapperDest, changedProperty, beanPropertyValue);
					// util.setProperty(dest, changedProperty, util.getProperty(bean, changedProperty));
				} catch (Exception e) {}
			}

			//BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(dest, org);
			if(populate!=null){
				populate.populateForUpdate(bean, dest);
			}

			return dest;

		}else{
			return null;
		}
	}

	/**
	 * Aggiorna un oggetto del tipo passato in ingresso popolando con l'oggetto
	 * @param session
	 * @param bean
	 * @param classe
	 * @return
	 * @throws Exception
	 */
	public static Object populateForUpdate(Session session, AbstractBean bean,Class classe) throws Exception{
		return populateForUpdate(session,bean,classe,null);
	}

	/**
	 * Crea un oggetto del tipo passato in ingresso popolando con l'oggetto
	 * @param org
	 * @param classe
	 * @return
	 * @throws Exception
	 */
	public static Object populate(Object org,Class classe,IBeanPopulate populate) throws Exception{
		if(org!=null){
			Object dest = classe.newInstance();	
			BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(dest, org);
			if(populate!=null){
				populate.populate(org, dest);
			}
			return dest;
		}else{
			return null;
		}
	}

	public static Object populate(Object org,Object dest,IBeanPopulate populate) throws Exception{
		if(org!=null){

			BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(dest, org);
			if(populate!=null){
				populate.populate(org, dest);
			}
			return dest;
		}else{
			return null;
		}
	}
	
	/**
	 * Modifica l'istanza della entity passata in ingresso popolando con l'oggetto bean.
	 * <strong>da usare</strong> nel caso di update di entities
	 * @param session - Sessione corrente di Hibernate
	 * @param bean - Oggetto in input
	 * @param dest - Istanza della entity ritornata dal metodo.
	 * @param populate - Instanza del populate specifico per i tipi passati in input
	 * @return
	 * @throws Exception
	 */
	public static Object populateForUpdate(AbstractBean bean, Object dest, IBeanPopulate populate) throws Exception{
		if(bean!=null){
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			BeanWrapperImpl wrapperDest = BeanPropertyUtility.getBeanWrapper(dest);
			PropertyUtilsBean util = BeanUtilsBean2.getInstance().getPropertyUtils();

			if(dest==null) {
				return null;
			}

			//Dal bean astratto recupero le property effettivamente modificate (quelle contenute nell'HashSet)
			for(String changedProperty: bean.getUpdatedProperties()) {
				try {
					Object beanPropertyValue = BeanPropertyUtility.getPropertyValue(bean, wrapperBean, changedProperty);
					BeanPropertyUtility.setPropertyValue(dest, wrapperDest, changedProperty, beanPropertyValue);
					// util.setProperty(dest, changedProperty, util.getProperty(bean, changedProperty));
				} catch (Exception e) {}
			}

			//BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(dest, org);
			if(populate!=null){
				populate.populateForUpdate(bean, dest);
			}

			return dest;

		}else{
			return null;
		}
	}

	/**
	 * Crea un oggetto del tipo passato in ingresso popolando con l'oggetto
	 * @param org
	 * @param classe
	 * @return
	 * @throws Exception
	 */
	public static Object populate(Object org,Class classe) throws Exception{
		return populate(org,classe,null);
	}

}

