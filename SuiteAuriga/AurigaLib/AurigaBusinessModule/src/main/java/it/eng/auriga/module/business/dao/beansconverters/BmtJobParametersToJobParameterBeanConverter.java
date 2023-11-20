/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.dao.beans.JobParameterBean;
import it.eng.auriga.module.business.entity.BmtJobParameters;
import it.eng.core.business.converter.IBeanPopulate;

/**
 * Classe di conversione da entity della tabella di BmtJobParameters a bean di interfaccia
 *
 * @author mzanin
 *
 */
public class BmtJobParametersToJobParameterBeanConverter implements IBeanPopulate<BmtJobParameters, JobParameterBean> {

	/**
	 * Metodo di popolamento
	 * - qui vanno settate le foreign key ed eventuali proprietà custom del bean
	 */
	public void populate(BmtJobParameters src, JobParameterBean dest) throws Exception {			
		dest.setIdJob(src.getId() != null ? src.getId().getIdJob() : null);
		dest.setParameterId(src.getId() != null ? src.getId().getParameterId() : null);
	}

	/**
	 * Metodo di popolamento per l'update
	 * - da implementare solo in caso di conversione da bean ad entity
	 * - vengono considerate per l'update solo le proprietà del bean che sono state effettivamente settate
	 */
	public void populateForUpdate(BmtJobParameters src, JobParameterBean dest)
			throws Exception {
	}
	
}
