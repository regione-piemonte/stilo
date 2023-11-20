/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.dao.beans.JobParameterBean;
import it.eng.auriga.module.business.entity.BmtJobParameters;
import it.eng.auriga.module.business.entity.BmtJobParametersId;
import it.eng.core.business.converter.IBeanPopulate;

/**
 * Classe di conversione da bean di interfaccia ad entity della tabella BmtJobParameters
 *
 * @author mzanin
 *
 */
public class JobParameterBeanToBmtJobParametersConverter implements IBeanPopulate<JobParameterBean, BmtJobParameters> {

	/**
	 * Metodo di popolamento
	 * - qui vanno settate le foreign key ed eventuali proprietà custom del bean
	 */
	public void populate(JobParameterBean src, BmtJobParameters dest) throws Exception {
	
		/* Primary key*/
		dest.setId(new BmtJobParametersId(src.getIdJob(), src.getParameterId()));
		
	}

	/**
	 * Metodo di popolamento per l'update
	 * - da implementare solo in caso di conversione da bean ad entity
	 * - vengono considerate per l'update solo le proprietà del bean che sono state effettivamente settate
	 */
	public void populateForUpdate(JobParameterBean src, BmtJobParameters dest)
			throws Exception {
		if(src.hasPropertyBeenModified("idJob") || src.hasPropertyBeenModified("parameterId")) {			
			dest.setId(new BmtJobParametersId(src.getIdJob(), src.getParameterId()));		
		}
	}
}
