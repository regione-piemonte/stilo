/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Calendar;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultUpdateEventListener;

import it.eng.core.business.subject.SubjectUtil;

public class AurigaMailPreUpdateEventListener extends DefaultUpdateEventListener {

	private static final long serialVersionUID = -7772011915835399218L;

	Logger log = LogManager.getLogger(AurigaMailPreUpdateEventListener.class);

	@Override
	public void onSaveOrUpdate(SaveOrUpdateEvent event) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("AurigaMailPreUpdateEventListener");
			}

			BeanUtilsBean util = BeanUtilsBean2.getInstance();

			Object entity = event.getObject();

			// Setto l'utente di inserimento e di update
			String iduser = SubjectUtil.subject.get().getIduser();
			// if (iduser == null) iduser = "AURIGA_BUSINESS";
			// Setto l'applicativo di inserimento e di update
			String idAppl = SubjectUtil.subject.get().getIdapplicazione();
			// if (idAppl == null) idAppl = "AURIGA_BUSINESS";
			// Setto il timestamp di inserimento che di ultimo update
			Calendar cal = Calendar.getInstance();

			// Recupero l'entity da db
			// Object updateentity = event.getSession().get(entity.getClass(),
			// (Serializable)util.getProperty(entity,HibernateUtil.getPrimaryKey(entity.getClass())));

			// Setto l'utente di update
			try {
				util.setProperty(entity, "uteUltimoAgg", iduser);
			} catch (Exception e) {
				log.error("AurigaMailPreUpdateEventListener uteUltimoAgg", e);
			}
			try {
				util.setProperty(entity, "idUteUltimoAgg", iduser);
			} catch (Exception e) {
				log.error("AurigaMailPreUpdateEventListener idUteUltimoAgg", e);
			}
			// Setto l'utente di update
			try {
				util.setProperty(entity, "applUltimoAgg", idAppl);
			} catch (Exception e) {
				log.error("AurigaMailPreUpdateEventListener applUltimoAgg", e);
			}

			// Setto il timestamp di ultimo update
			try {
				util.setProperty(entity, "tsUltimoAgg", cal.getTime());
			} catch (Exception e) {
				log.error("AurigaMailPreUpdateEventListener tsUltimoAgg", e);
			}

			super.onSaveOrUpdate(event);
		} catch (Throwable e) {
			log.error("AurigaMailPreUpdateEventListener", e);
		}
	}
}
