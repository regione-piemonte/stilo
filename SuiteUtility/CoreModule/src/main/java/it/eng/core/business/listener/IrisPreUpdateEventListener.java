package it.eng.core.business.listener;

import it.eng.core.business.HibernateConstants;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.UniqueConstraintsUtil;
import it.eng.core.business.beans.EntityConstraint;
import it.eng.core.business.beans.PropValue;
import it.eng.core.business.subject.SubjectUtil;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultUpdateEventListener;


public class IrisPreUpdateEventListener extends DefaultUpdateEventListener {

	private static final long serialVersionUID = 1L;

	Logger log = Logger.getLogger(IrisPreUpdateEventListener.class);
	
	@Override
	public void onSaveOrUpdate(SaveOrUpdateEvent event) {
		try{			
			log.debug("Pre Update Event Listener");			
			
			BeanUtilsBean util = BeanUtilsBean2.getInstance();			
			Object entity = event.getObject();
			
//			Session session = event.getSession();			
//			Class<?> classe = event.getObject().getClass();			
//			
//			List<?> lista = checkConstraintViolation(event);
//			if(lista != null && lista.size() > 0) {
//				Object result = lista.get(0);
//				if(util.getProperty(result, "flgAnn").equalsIgnoreCase("true") && !util.getProperty(entity, "flgAnn").equals("true")) {
//					session.delete(HibernateConstants.Delete.FORCE, lista.get(0));		
//					session.flush();
//				} else {
//					String pkField = HibernateUtil.getPrimaryKey(classe);					
//					if(!util.getProperty(result, pkField).equalsIgnoreCase(util.getProperty(entity, pkField))) {
//						throw new Exception("Restrizione di unicit� violata");	
//					}					
//				}					
//			}	
			
			//Recupero l'entity da db
			//Object updateentity = event.getSession().get(entity.getClass(), (Serializable)util.getProperty(entity,HibernateUtil.getPrimaryKey(entity.getClass())));
					
			//Setto l'utente di update
			String iduser = SubjectUtil.subject.get().getIduser();
			util.setProperty(entity, "idUteUltimoAgg", iduser);		
			
			//Setto il timestamp di ultimo update
			Calendar cal = Calendar.getInstance();
			util.setProperty(entity, "tsUltimoAgg", cal);	
			
			//Setto il flgann
			try {
				if(util.getProperty(entity, "flgAnn") == null) {
					util.setProperty(entity, "flgAnn", Boolean.FALSE);
				}
			} catch (Exception e) {}
			
			//Setto il nuovo oggetto
			//event.setObject(updateentity);
						
			super.onSaveOrUpdate(event);
		}catch(Throwable e){
			log.error("Warning Update Listener", e);
		}
	}
	
//	private List<?> checkConstraintViolation(SaveOrUpdateEvent event) throws Exception {
//		Session session = null;
//	    try {
//	    	session = HibernateUtil.begin();
//	    	BeanUtilsBean util = BeanUtilsBean2.getInstance();		
//			Object entity = event.getObject();		
//			Class<?> classe = event.getObject().getClass();			
//			Criteria crit = session.createCriteria(classe);		
//			//Prepara la query
//			Disjunction dj = Restrictions.disjunction();		//Ogni constraint va in or
//			List<EntityConstraint> constraints = UniqueConstraintsUtil.getEntityUniqueContraintFields(classe);			
//			for(EntityConstraint ec:constraints) {
//				Junction jc = Restrictions.conjunction();		//Faccio una and degli attributi che costituiscono una singola constraint
//				for(PropValue prop:ec.getPropertyNames()) {
//					//Se la property si riferisce ad una chiave esterna allora entro in questo if:
//					if(StringUtils.isNotBlank(prop.getPropertyPKName())) {
//						String nomeCampo = prop.getPropertyName();                //nome della property
//						String nomeProperty = prop.getPropertyPKName();			  //nome della pk della property
//						crit.createAlias(nomeCampo, nomeCampo);					  //creo l'alias	
//						String aliasCond = nomeCampo+"."+nomeProperty;			  //alias.nomeProperty
//						Object relatedBean = util.getProperty(entity, nomeCampo); //recupero l'istanza della property	
//						jc.add(Restrictions.eq(aliasCond, util.getProperty(relatedBean, nomeProperty)).ignoreCase());	//creo la condizione della select
//					}
//					else {
//						jc.add(Restrictions.eq(prop.getPropertyName(),util.getProperty(entity, prop.getPropertyName())).ignoreCase());
//					}
//				}
//				dj.add(jc);				//Ogni constraint � messa in or con le altre
//			}
//			crit.add(dj);
//			return crit.list();
//	    } catch (Exception e) {
//	    	e.printStackTrace();
//	    } finally {
//	    	HibernateUtil.release(session);
//	    }
//	    return null;
//	}
	
}
