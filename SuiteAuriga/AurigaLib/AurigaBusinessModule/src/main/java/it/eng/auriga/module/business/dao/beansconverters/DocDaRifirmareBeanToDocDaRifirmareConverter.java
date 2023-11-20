/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.dao.beans.DocDaRifirmareBean;
import it.eng.auriga.module.business.entity.DocDaRifirmare;
import it.eng.auriga.module.business.entity.DocDaRifirmareId;
import it.eng.core.business.converter.IBeanPopulate;


/**
 * Classe di conversione da bean di interfaccia ad entity della tabella DOC_DA_RIFIRMARE
 *
 * @author upescato
 *
 */
public class DocDaRifirmareBeanToDocDaRifirmareConverter implements IBeanPopulate<DocDaRifirmareBean, DocDaRifirmare> {
	
	/**
	 * Metodo di popolamento
	 * - qui vanno settate le foreign key ed eventuali proprietà custom del bean
	 */
	public void populate(DocDaRifirmareBean src, DocDaRifirmare dest) throws Exception {
	
		/* Primary key*/
		dest.setId(new DocDaRifirmareId(src.getIdDoc(), src.getFirmatario()));
		
	}

	/**
	 * Metodo di popolamento per l'update
	 * - da implementare solo in caso di conversione da bean ad entity
	 * - vengono considerate per l'update solo le proprietà del bean che sono state effettivamente settate
	 */
	public void populateForUpdate(DocDaRifirmareBean src, DocDaRifirmare dest)
			throws Exception {
		if(src.hasPropertyBeenModified("idDoc") || src.hasPropertyBeenModified("firmatario")) {			
			dest.setId(new DocDaRifirmareId(src.getIdDoc(), src.getFirmatario()));			
		}
	}
}
