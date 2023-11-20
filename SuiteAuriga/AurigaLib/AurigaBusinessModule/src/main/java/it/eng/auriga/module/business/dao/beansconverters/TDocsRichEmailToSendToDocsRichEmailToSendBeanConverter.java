/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.dao.beans.DocsRichEmailToSendBean;
import it.eng.auriga.module.business.entity.TDocsRichEmailToSend;
import it.eng.core.business.converter.IBeanPopulate;

import org.hibernate.Session;

/**
 * @author Federico Cacco
 */

public class TDocsRichEmailToSendToDocsRichEmailToSendBeanConverter implements IBeanPopulate<TDocsRichEmailToSend, DocsRichEmailToSendBean> {

	@SuppressWarnings("unused")
	private Session session;

	public TDocsRichEmailToSendToDocsRichEmailToSendBeanConverter(Session session) {
		this.session = session;
	}

	@Override
	public void populate(TDocsRichEmailToSend src, DocsRichEmailToSendBean dest) throws Exception {

		// Mappo TRichEmailToSend
		if (src.getTRichEmailToSend() != null) {
			dest.setIdRichiesta(src.getTRichEmailToSend().getIdRichiesta());
		}
	}

	@Override
	public void populateForUpdate(TDocsRichEmailToSend src, DocsRichEmailToSendBean dest) throws Exception {
	}

}