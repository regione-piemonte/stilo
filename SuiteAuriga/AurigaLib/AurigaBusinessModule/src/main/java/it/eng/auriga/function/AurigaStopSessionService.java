/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.auriga.bean.AurigaStoppableSearchBean;

@Service(name="AurigaStopSessionService")
public class AurigaStopSessionService {

	@Operation(name = "stopSession")
	public AurigaStoppableSearchBean stopSession(AurigaLoginBean pAurigaLoginBean, AurigaStoppableSearchBean pStoppableSearchBean) throws Exception{
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		subject.setUuidtransaction(pStoppableSearchBean.getUuid());
		SubjectUtil.subject.set(subject);
		HibernateUtil.closeStatement(pStoppableSearchBean.getUuid());
		HibernateUtil.closeConnection(pStoppableSearchBean.getUuid());
	    return pStoppableSearchBean;
	}
}
