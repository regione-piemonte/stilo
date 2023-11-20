/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.dao.beans.PreferenceBean;
import it.eng.auriga.module.business.entity.TUserPreferences;
import it.eng.core.business.converter.IBeanPopulate;

/**
 * Classe di conversione da entity della tabella di T_RICHIESTE a bean di interfaccia
 *
 * @author upescato
 *
 */
public class TUserPreferencesToPreferenceBeanConverter implements IBeanPopulate<TUserPreferences, PreferenceBean> {

	/**
	 * Metodo di popolamento
	 * - qui vanno settate le foreign key ed eventuali proprietà custom del bean
	 */
	public void populate(TUserPreferences src, PreferenceBean dest) throws Exception {	
		
		dest.setUserid(src.getId()==null ? null : src.getId().getUserid());
		dest.setPrefKey(src.getId()==null ? null : src.getId().getPrefKey());
		dest.setPrefName(src.getId()==null ? null : src.getId().getPrefName());
	}

	/**
	 * Metodo di popolamento per l'update
	 * - da implementare solo in caso di conversione da bean ad entity
	 * - vengono considerate per l'update solo le proprietà del bean che sono state effettivamente settate
	 */
	public void populateForUpdate(TUserPreferences src, PreferenceBean dest)
			throws Exception {
	}
}
