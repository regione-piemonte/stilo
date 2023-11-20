/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.dao.beans.InputModuliCompBean;
import it.eng.auriga.module.business.entity.DmtInputModuliComp;
import it.eng.auriga.module.business.entity.DmtInputModuliCompId;
import it.eng.core.business.converter.IBeanPopulate;

/**
 * Classe di conversione da bean di interfaccia ad entity della tabella DmtInputModuliComp
 *
 * @author mzanin
 *
 */
public class InputModuliCompBeanToDmtInputModuliCompConverter implements IBeanPopulate<InputModuliCompBean, DmtInputModuliComp> {

	/**
	 * Metodo di popolamento
	 * - qui vanno settate le foreign key ed eventuali proprietà custom del bean
	 */
	public void populate(InputModuliCompBean src, DmtInputModuliComp dest) throws Exception {
	
		/* Primary key*/
		dest.setId(new DmtInputModuliCompId(src.getIdModello(), src.getNomeInput(), src.getFlgMultivalore() != null ? src.getFlgMultivalore() : false));
		/* Gestioni dei flag nulli obbligatori */
		dest.setFlgBarcode(src.getFlgBarcode() != null ? src.getFlgBarcode() : false);
		
		
	}

	/**
	 * Metodo di popolamento per l'update
	 * - da implementare solo in caso di conversione da bean ad entity
	 * - vengono considerate per l'update solo le proprietà del bean che sono state effettivamente settate
	 */
	public void populateForUpdate(InputModuliCompBean src, DmtInputModuliComp dest)
			throws Exception {
		if(src.hasPropertyBeenModified("idModello") || src.hasPropertyBeenModified("nomeInput") || src.hasPropertyBeenModified("flgMultivalore")) {			
			dest.setId(new DmtInputModuliCompId(src.getIdModello(), src.getNomeInput(), src.getFlgMultivalore() != null ? src.getFlgMultivalore() : false));
		}
		/* Gestioni dei flag nulli obbligatori */
		if(src.hasPropertyBeenModified("flgBarcode")) {			
			dest.setFlgBarcode(src.getFlgBarcode() != null ? src.getFlgBarcode() : false);
		}
	}
}
