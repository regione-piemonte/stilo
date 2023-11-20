/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.dao.beans.InputModuliCompBean;
import it.eng.auriga.module.business.entity.DmtInputModuliComp;
import it.eng.core.business.converter.IBeanPopulate;

/**
 * Classe di conversione da entity della tabella di DmtInputModuliComp a bean di interfaccia
 *
 * @author mzanin
 *
 */
public class DmtInputModuliCompToInputModuliCompBeanConverter implements IBeanPopulate<DmtInputModuliComp, InputModuliCompBean> {

	/**
	 * Metodo di popolamento
	 * - qui vanno settate le foreign key ed eventuali proprietà custom del bean
	 */
	public void populate(DmtInputModuliComp src, InputModuliCompBean dest) throws Exception {			
		dest.setIdModello(src.getId() != null ? src.getId().getIdModello() : null);		
		dest.setNomeInput(src.getId() != null ? src.getId().getNomeInput() : null);
		dest.setFlgMultivalore(src.getId() != null ? src.getId().isFlgMultivalore() : null);
	}

	/**
	 * Metodo di popolamento per l'update
	 * - da implementare solo in caso di conversione da bean ad entity
	 * - vengono considerate per l'update solo le proprietà del bean che sono state effettivamente settate
	 */
	public void populateForUpdate(DmtInputModuliComp src, InputModuliCompBean dest)
			throws Exception {
	}
	
}
