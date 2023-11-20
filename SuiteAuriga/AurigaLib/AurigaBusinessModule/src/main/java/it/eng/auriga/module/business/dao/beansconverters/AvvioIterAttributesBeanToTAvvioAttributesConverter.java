/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.dao.beans.AvvioIterAttributesBean;
import it.eng.auriga.module.business.entity.TAvvioIterAttributes;
import it.eng.core.business.converter.IBeanPopulate;

import org.hibernate.Session;

/**
 * Converte il bean relativo agli attributi temporanei di avvio di un iter nell'entity di riferimento della tabella T_AVVIO_ITER_ATTRIBUTES 
 * @author massimo malvestio
 *
 */
public class AvvioIterAttributesBeanToTAvvioAttributesConverter implements
		IBeanPopulate<AvvioIterAttributesBean, TAvvioIterAttributes> {

	public AvvioIterAttributesBeanToTAvvioAttributesConverter(Session session) {
		
	}
	
	@Override
	public void populate(AvvioIterAttributesBean src, TAvvioIterAttributes dest)
			throws Exception {

		dest.setAttributeName(src.getAttributeName());
		dest.setAttributeValue(src.getAttributeName());
		dest.setIdProcess(src.getIdProcess());

	}

	@Override
	public void populateForUpdate(AvvioIterAttributesBean src,
			TAvvioIterAttributes dest) throws Exception {

		dest.setAttributeName(src.getAttributeName());
		dest.setAttributeValue(src.getAttributeName());
		dest.setIdProcess(src.getIdProcess());

	}

}
