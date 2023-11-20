/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.dao.beans.PreferenceBean;
import it.eng.auriga.module.business.entity.TUserPreferences;
import it.eng.auriga.module.business.entity.TUserPreferencesId;
import it.eng.core.business.converter.IBeanPopulate;

import org.hibernate.Session;


/**
 * Classe di conversione da bean di interfaccia ad entity della tabella T_RICHIESTE
 *
 * @author upescato
 *
 */
public class PreferenceBeanToTUserPreferencesConverter implements IBeanPopulate<PreferenceBean, TUserPreferences> {

	@SuppressWarnings("unused")
	private Session session;
	
	public PreferenceBeanToTUserPreferencesConverter(Session session) {
		this.session = session;
	}
	
	/**
	 * Metodo di popolamento
	 * - qui vanno settate le foreign key ed eventuali proprietà custom del bean
	 */
	public void populate(PreferenceBean src, TUserPreferences dest) throws Exception {
	
		/* Primary key*/
		dest.setId(new TUserPreferencesId(src.getUserid(), src.getPrefKey(), src.getPrefName()));
		
	}

	/**
	 * Metodo di popolamento per l'update
	 * - da implementare solo in caso di conversione da bean ad entity
	 * - vengono considerate per l'update solo le proprietà del bean che sono state effettivamente settate
	 */
	public void populateForUpdate(PreferenceBean src, TUserPreferences dest)
			throws Exception {
		if(src.hasPropertyBeenModified("userId") || src.hasPropertyBeenModified("prefKey") || src.hasPropertyBeenModified("prefName")) {			
			dest.setId(new TUserPreferencesId(src.getUserid(), src.getPrefKey(), src.getPrefName()));			
		}
	}
}
