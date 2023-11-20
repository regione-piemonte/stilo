/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.HibernateConstants;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.event.DeleteEvent;
import org.hibernate.event.def.DefaultDeleteEventListener;

/**
 * Classe listener per l'operazione di delete dei dao.
 * @deprecated
 * @author upescato
 */

//Per adesso non è utilizzata nel presente modulo poiché non è necessario
//il FLG_ANN
public class AurigaPreDeleteEventListener extends DefaultDeleteEventListener {

	private static final long serialVersionUID = 1L;
	
	Logger log = Logger.getLogger(AurigaPreDeleteEventListener.class);
		
	@Override
	public void onDelete(DeleteEvent event) throws HibernateException {
		log.debug("Pre Delete Event Listener");
		if(HibernateConstants.Delete.FORCE.equals(event.getEntityName())){
			//Delete forzata
			super.onDelete(event);
		} else {	
			try{
				//Property utils
				PropertyUtilsBean util = BeanUtilsBean2.getInstance().getPropertyUtils();
				
				log.debug(event.getEntityName());
				
				//Setto il flgAnn a 1
				Object obj = event.getObject();
				util.setProperty(obj, "flgAnn", Boolean.TRUE);
				
				event.getSession().update(obj);
			}catch(Exception e){
				throw new HibernateException("Error delete listener!",e);
			}
		}
	}
}