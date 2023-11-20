/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.core.service.bean.rest.RestServiceBean;
import it.eng.core.service.server.RestBusinessAfter;

import java.math.BigDecimal;
import java.util.List;

public class AurigaMailBusinessRestBusinessAfter implements RestBusinessAfter {

	@Override
	public void after(RestServiceBean pRestServiceBean) {
		//Recupero il subject
		SubjectBean subject = SubjectUtil.subject.get();
		//Recupero il parametro
		List<String> lList = pRestServiceBean.getInputs();
		try {
			BigDecimal lBigDecimal = new BigDecimal(lList.get(lList.size()-1));
		} catch (Exception e){
			return;
		}	
		//Tolgo l'idutente aggiunto
		String idUtente = lList.remove(lList.size()-1);
		//Lo setto nel subject
		subject.setIduser(idUtente);
		//Risetto il subject
		SubjectUtil.subject.set(subject);
	}


}
