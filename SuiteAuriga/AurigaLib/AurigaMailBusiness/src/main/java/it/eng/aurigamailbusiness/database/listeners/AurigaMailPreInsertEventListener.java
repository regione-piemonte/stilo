/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Calendar;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultSaveEventListener;

import it.eng.aurigamailbusiness.database.mail.TAttachEmailMgo;
import it.eng.core.business.subject.SubjectUtil;

public class AurigaMailPreInsertEventListener extends DefaultSaveEventListener {

	private static final long serialVersionUID = -7882952700403120156L;

	private static Logger mLogger = LogManager.getLogger(AurigaMailPreInsertEventListener.class);

	@Override
	public void onSaveOrUpdate(SaveOrUpdateEvent event) {

		try {
			if (mLogger.isDebugEnabled()) {
				mLogger.debug("AurigaMailPreInsertEventListener");
			}

			BeanUtilsBean util = BeanUtilsBean2.getInstance();

			Object entity = event.getObject();
			if (entity instanceof TAttachEmailMgo) {
				mLogger.info("displayFileName: " + ((TAttachEmailMgo) entity).getDisplayFilename());
				mLogger.info("originalName: " + ((TAttachEmailMgo) entity).getNomeOriginale());
				mLogger.info("mimetype: " + ((TAttachEmailMgo) entity).getMimetype());
			}

			// Setto l'utente di inserimento e di update
			String iduser = SubjectUtil.subject.get().getIduser();
			// if (iduser == null) iduser = "AURIGA_BUSINESS";

			// Setto l'applicativo di inserimento e di update
			String idAppl = SubjectUtil.subject.get().getIdapplicazione();
			// if (idAppl == null) idAppl = "AURIGA_BUSINESS";
			// Setto il timestamp di inserimento che di ultimo update
			Calendar cal = Calendar.getInstance();

			try {
				util.setProperty(entity, "tsIns", cal.getTime());
			} catch (Exception e) {
				mLogger.error("AurigaPreInsertEventListener tsIns", e);
			}

			try {
				util.setProperty(entity, "applIns", idAppl);
			} catch (Exception e) {
				mLogger.error("AurigaPreInsertEventListener applIns", e);
			}

			try {
				util.setProperty(entity, "uteIns", iduser);
			} catch (Exception e) {
				mLogger.error("AurigaPreInsertEventListener uteIns", e);
			}

			try {
				util.setProperty(entity, "idUteIns", iduser);
			} catch (Exception e) {
				mLogger.error("AurigaPreInsertEventListener idUteIns", e);
			}
			try {
				util.setProperty(entity, "tsUltimoAgg", cal.getTime());
			} catch (Exception e) {
				mLogger.error("AurigaPreInsertEventListener tsUltimoAgg", e);
			}

			try {
				util.setProperty(entity, "applUltimoAgg", idAppl);
			} catch (Exception e) {
				mLogger.error("AurigaPreInsertEventListener applUltimoAgg", e);
			}

			try {
				util.setProperty(entity, "uteUltimoAgg", iduser);
			} catch (Exception e) {
				mLogger.error("AurigaPreInsertEventListener uteUltimoAgg", e);
			}
			try {
				util.setProperty(entity, "idUteUltimoAgg", iduser);
			} catch (Exception e) {
				mLogger.error("AurigaPreInsertEventListener idUteUltimoAgg", e);
			}

			try {
				util.setProperty(entity, "flgAnn", false);
			} catch (Exception e) {
				mLogger.error("AurigaPreInsertEventListener flgAnn", e);
			}

			super.onSaveOrUpdate(event);
		} catch (Throwable e) {
			mLogger.error("AurigaPreInsertEventListener", e);
		}
	}
}
