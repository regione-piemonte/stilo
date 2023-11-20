/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.dao.beans.DocsRichEmailToSendBean;
import it.eng.auriga.module.business.entity.TDocsRichEmailToSend;
import it.eng.auriga.module.business.entity.TRichEmailToSend;
import it.eng.core.business.converter.IBeanPopulate;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;

/**
 * Converte il bean relativo agli attributi temporanei di avvio di un iter
 * nell'entity di riferimento della tabella T_AVVIO_ITER_ATTRIBUTES
 * 
 * @author massimo malvestio
 *
 */
public class DocsRichEmailToSendBeanToTDocsRichEmailToSendConverter implements IBeanPopulate<DocsRichEmailToSendBean, TDocsRichEmailToSend> {

	public DocsRichEmailToSendBeanToTDocsRichEmailToSendConverter(Session session) {

	}

	@Override
	public void populate(DocsRichEmailToSendBean src, TDocsRichEmailToSend dest) throws Exception {

		// Popolo TRichEmailToSend
		if (StringUtils.isNotBlank(src.getIdRichiesta())) {
			TRichEmailToSend obj = dest.getTRichEmailToSend() != null ? dest.getTRichEmailToSend() : new TRichEmailToSend();
			obj.setIdRichiesta(src.getIdRichiesta());
			dest.setTRichEmailToSend(obj);
		}
	}

	@Override
	public void populateForUpdate(DocsRichEmailToSendBean src, TDocsRichEmailToSend dest) throws Exception {

		// Popolo TRichEmailToSend
		if (src.hasPropertyBeenModified("idRichiesta")) {
			TRichEmailToSend obj = dest.getTRichEmailToSend() != null ? dest.getTRichEmailToSend() : new TRichEmailToSend();
			obj.setIdRichiesta(src.getIdRichiesta());
			dest.setTRichEmailToSend(obj);
		}
	}

}
