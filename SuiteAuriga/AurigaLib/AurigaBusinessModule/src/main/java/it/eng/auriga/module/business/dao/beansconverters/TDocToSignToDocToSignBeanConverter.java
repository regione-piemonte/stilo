/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.module.business.dao.beansconverters;

import it.eng.auriga.module.business.dao.beans.DocToSignBean;
import it.eng.auriga.module.business.entity.TDocToSign;
import it.eng.core.business.converter.IBeanPopulate;

/**
 * Classe di conversione da entity della tabella di T_DOC_TO_SIGN a bean di interfaccia
 *
 * @author matzanin
 *
 */
public class TDocToSignToDocToSignBeanConverter implements IBeanPopulate<TDocToSign, DocToSignBean> {

	/**
	 * Metodo di popolamento
	 * - qui vanno settate le foreign key ed eventuali proprietà custom del bean
	 */
	public void populate(TDocToSign src, DocToSignBean dest) throws Exception {
		
	}

	/**
	 * Metodo di popolamento per l'update
	 * - da implementare solo in caso di conversione da bean ad entity
	 * - vengono considerate per l'update solo le proprietà del bean che sono state effettivamente settate
	 */
	public void populateForUpdate(TDocToSign src, DocToSignBean dest) throws Exception {
		
	}
	
}
