/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.module.business.dao.beansconverters;

import it.eng.auriga.module.business.dao.beans.FileDocToSignBean;
import it.eng.auriga.module.business.entity.TFileDocToSign;
import it.eng.core.business.converter.IBeanPopulate;

import org.hibernate.Session;


/**
 * Classe di conversione da bean di interfaccia ad entity della tabella T_FILE_DOC_TO_SIGN
 *
 * @author matzanin
 *
 */
public class FileDocToSignBeanToTFileDocToSignConverter implements IBeanPopulate<FileDocToSignBean, TFileDocToSign> {

	@SuppressWarnings("unused")
	private Session session;
	
	public FileDocToSignBeanToTFileDocToSignConverter(Session session) {
		this.session = session;
	}
	
	/**
	 * Metodo di popolamento
	 * - qui vanno settate le foreign key ed eventuali proprietà custom del bean
	 */
	public void populate(FileDocToSignBean src, TFileDocToSign dest) throws Exception {
		
	}

	/**
	 * Metodo di popolamento per l'update
	 * - da implementare solo in caso di conversione da bean ad entity
	 * - vengono considerate per l'update solo le proprietà del bean che sono state effettivamente settate
	 */
	public void populateForUpdate(FileDocToSignBean src, TFileDocToSign dest) throws Exception {
		
	}
	
}
