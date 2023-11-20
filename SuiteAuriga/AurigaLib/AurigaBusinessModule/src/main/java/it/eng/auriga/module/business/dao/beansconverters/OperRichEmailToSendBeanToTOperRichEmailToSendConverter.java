/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.dao.beans.OperRichEmailToSendBean;
import it.eng.auriga.module.business.entity.TOperRichEmailToSend;
import it.eng.auriga.module.business.entity.TRichEmailToSend;
import it.eng.core.business.converter.IBeanPopulate;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;

/**
 * @author Federico Cacco
 */

public class OperRichEmailToSendBeanToTOperRichEmailToSendConverter implements IBeanPopulate<OperRichEmailToSendBean, TOperRichEmailToSend> {

	@SuppressWarnings("unused")
	private Session session;

	public OperRichEmailToSendBeanToTOperRichEmailToSendConverter(Session session) {
		this.session = session;
	}

	@Override
	public void populate(OperRichEmailToSendBean src, TOperRichEmailToSend dest) throws Exception {

		// Popolo TRichEmailToSend
		if (StringUtils.isNotBlank(src.getIdRichiesta())) {
			TRichEmailToSend obj = dest.getTRichEmailToSend() != null ? dest.getTRichEmailToSend() : new TRichEmailToSend();
			obj.setIdRichiesta(src.getIdRichiesta());
			dest.setTRichEmailToSend(obj);
		}

	}

	@Override
	public void populateForUpdate(OperRichEmailToSendBean src, TOperRichEmailToSend dest) throws Exception {

		// Popolo TRichEmailToSend
		if (src.hasPropertyBeenModified("idRichiesta")) {
			TRichEmailToSend obj = dest.getTRichEmailToSend() != null ? dest.getTRichEmailToSend() : new TRichEmailToSend();
			obj.setIdRichiesta(src.getIdRichiesta());
			dest.setTRichEmailToSend(obj);
		}

	}

}