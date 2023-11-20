/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.dao.beans.RichEmailToSendBean;
import it.eng.auriga.module.business.entity.TRichEmailToSend;
import it.eng.core.business.converter.IBeanPopulate;

import org.hibernate.Session;

/**
 * Converte il bean relativo agli attributi temporanei di avvio di un iter
 * nell'entity di riferimento della tabella T_AVVIO_ITER_ATTRIBUTES
 * 
 * @author massimo malvestio
 *
 */
public class RichEmailToSendBeanToTRichEmailToSendConverter implements IBeanPopulate<RichEmailToSendBean, TRichEmailToSend> {

	public RichEmailToSendBeanToTRichEmailToSendConverter(Session session) {

	}

	@Override
	public void populate(RichEmailToSendBean src, TRichEmailToSend dest) throws Exception {
	}

	@Override
	public void populateForUpdate(RichEmailToSendBean src, TRichEmailToSend dest) throws Exception {

	}

}
