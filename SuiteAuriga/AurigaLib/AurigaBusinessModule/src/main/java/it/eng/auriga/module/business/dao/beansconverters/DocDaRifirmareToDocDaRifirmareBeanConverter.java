/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.dao.beans.DocDaRifirmareBean;
import it.eng.auriga.module.business.entity.DocDaRifirmare;
import it.eng.core.business.converter.IBeanPopulate;

/**
 * Classe di conversione da entity della tabella DOC_DA_RIFIRMARE a bean di interfaccia
 *
 * @author upescato
 *
 */
public class DocDaRifirmareToDocDaRifirmareBeanConverter implements IBeanPopulate<DocDaRifirmare, DocDaRifirmareBean> {

	/**
	 * Metodo di popolamento
	 * - qui vanno settate le foreign key ed eventuali proprietà custom del bean
	 */
	public void populate(DocDaRifirmare src, DocDaRifirmareBean dest) throws Exception {	
		
		dest.setIdDoc(src.getId()==null ? null : src.getId().getIdDoc());
		dest.setFirmatario(src.getId()==null ? null : src.getId().getFirmatario());	
	}

	/**
	 * Metodo di popolamento per l'update
	 * - da implementare solo in caso di conversione da bean ad entity
	 * - vengono considerate per l'update solo le proprietà del bean che sono state effettivamente settate
	 */
	public void populateForUpdate(DocDaRifirmare src, DocDaRifirmareBean dest)
			throws Exception {
	}
}
