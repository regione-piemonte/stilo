/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.dao.beans.AvvioIterAttributesBean;
import it.eng.auriga.module.business.entity.TAvvioIterAttributes;
import it.eng.core.business.converter.IBeanPopulate;

/**
 * Converte l'entity relativo alla tabella T_AVVIO_ITER_ATTRIBUTES nel relativo bean
 * @author massimo malvestio
 *
 */
public class TAvvioAttributesToAvvioIterAttributesBeanConverter implements IBeanPopulate<TAvvioIterAttributes, AvvioIterAttributesBean>{

	@Override
	public void populate(TAvvioIterAttributes src, AvvioIterAttributesBean dest)
			throws Exception {
	
		dest.setAttributeName(src.getAttributeName());
		dest.setAttributeValue(src.getAttributeName());
		dest.setIdProcess(src.getIdProcess());
		
		
	}

	@Override
	public void populateForUpdate(TAvvioIterAttributes src,
			AvvioIterAttributesBean dest) throws Exception {

		
	}
}
