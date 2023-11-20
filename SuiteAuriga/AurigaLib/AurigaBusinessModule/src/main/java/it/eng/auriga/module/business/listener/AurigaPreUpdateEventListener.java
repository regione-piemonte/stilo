/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.subject.SubjectUtil;

import java.util.Calendar;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.log4j.Logger;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultUpdateEventListener;


/**
 * Classe listener per l'operazione di update dei dao.
 * @author upescato
 */
public class AurigaPreUpdateEventListener extends DefaultUpdateEventListener {

	private static final long serialVersionUID = 1L;

	Logger log = Logger.getLogger(AurigaPreUpdateEventListener.class);
	
	@Override
	public void onSaveOrUpdate(SaveOrUpdateEvent event) {
		try{
			
			log.debug("AurigaPreUpdateEventListener");
			
			BeanUtilsBean util = BeanUtilsBean2.getInstance();
						
			Object entity = event.getObject();
			
			//Setto l'utente di inserimento e di update
			String iduser = SubjectUtil.subject.get().getIduser();
			
			//Setto l'applicativo di inserimento e di update
			String idAppl = SubjectUtil.subject.get().getIdapplicazione();
			
			//Setto il timestamp di inserimento che di ultimo update
			Calendar cal = Calendar.getInstance();
				
			//Recupero l'entity da db
			//Object updateentity = event.getSession().get(entity.getClass(), (Serializable)util.getProperty(entity,HibernateUtil.getPrimaryKey(entity.getClass())));
					
			//Setto l'utente di update
			try {
				util.setProperty(entity, "uteUltimoAgg", iduser);
			} catch (Exception e) {
				log.warn(e);
			}		
			
			//Setto l'utente di update
			try {
				util.setProperty(entity, "applUltimoAgg", idAppl);
			} catch (Exception e) {
				log.warn(e);
			}
			
			//Setto il timestamp di ultimo update
			try {
				util.setProperty(entity, "tsUltimoAgg", cal);
			} catch (Exception e) {
				log.warn(e);
			}	
						
			super.onSaveOrUpdate(event);
		}catch(Throwable e){
			log.error("AurigaPreUpdateEventListener", e);
		}
	}
}
