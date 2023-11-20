/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.dao.beans.OperRichEmailToSendBean;
import it.eng.auriga.module.business.entity.TOperRichEmailToSend;
import it.eng.core.business.converter.IBeanPopulate;

import org.hibernate.Session;

/**
 * @author Federico Cacco
 */

public class TOperRichEmailToSendToOperRichEmailToSendBeanConverter implements IBeanPopulate<TOperRichEmailToSend, OperRichEmailToSendBean> {

	@SuppressWarnings("unused")
	private Session session;

	public TOperRichEmailToSendToOperRichEmailToSendBeanConverter(Session session) {
		this.session = session;
	}

	@Override
	public void populate(TOperRichEmailToSend src, OperRichEmailToSendBean dest) throws Exception {

		// Mappo TRichEmailToSend
		if (src.getTRichEmailToSend() != null) {
			dest.setIdRichiesta(src.getTRichEmailToSend().getIdRichiesta());
		}
	}

	@Override
	public void populateForUpdate(TOperRichEmailToSend src, OperRichEmailToSendBean dest) throws Exception {
	}

}