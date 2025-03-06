package it.eng.core.business.listener;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.event.DeleteEvent;
import org.hibernate.event.def.DefaultDeleteEventListener;

import it.eng.core.business.HibernateConstants;

public class IrisPreDeleteEventListener extends DefaultDeleteEventListener {

	private static final long serialVersionUID = 1L;

	Logger log = Logger.getLogger(IrisPreDeleteEventListener.class);

	@Override
	public void onDelete(DeleteEvent event) throws HibernateException {
		log.debug("Pre Delete Event Listener");
		if (HibernateConstants.Delete.FORCE.equals(event.getEntityName())) {
			// Delete forzata
			super.onDelete(event);
		} else {
			try {
				// Property utils
				PropertyUtilsBean util = BeanUtilsBean2.getInstance().getPropertyUtils();

				log.debug(event.getEntityName());

				// Setto il flgAnn a 1
				Object obj = event.getObject();
				util.setProperty(obj, "flgAnn", Boolean.TRUE);

				event.getSession().update(obj);
			} catch (Exception e) {
				throw new HibernateException("Error delete listener!", e);
			}
		}
	}
}