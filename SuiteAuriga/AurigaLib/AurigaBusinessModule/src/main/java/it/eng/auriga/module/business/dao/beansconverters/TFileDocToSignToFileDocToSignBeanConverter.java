/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.module.business.dao.beansconverters;

import it.eng.auriga.module.business.dao.beans.FileDocToSignBean;
import it.eng.auriga.module.business.entity.TFileDocToSign;
import it.eng.core.business.converter.IBeanPopulate;

/**
 * Classe di conversione da entity della tabella di T_FILE_DOC_TO_SIGN a bean di interfaccia
 *
 * @author matzanin
 *
 */
public class TFileDocToSignToFileDocToSignBeanConverter implements IBeanPopulate<TFileDocToSign, FileDocToSignBean> {

	/**
	 * Metodo di popolamento
	 * - qui vanno settate le foreign key ed eventuali proprietà custom del bean
	 */
	public void populate(TFileDocToSign src, FileDocToSignBean dest) throws Exception {
		
	}

	/**
	 * Metodo di popolamento per l'update
	 * - da implementare solo in caso di conversione da bean ad entity
	 * - vengono considerate per l'update solo le proprietà del bean che sono state effettivamente settate
	 */
	public void populateForUpdate(TFileDocToSign src, FileDocToSignBean dest) throws Exception {
		
	}
	
}
