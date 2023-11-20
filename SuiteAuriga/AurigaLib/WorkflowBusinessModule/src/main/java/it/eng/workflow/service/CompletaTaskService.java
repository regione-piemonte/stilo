/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.dm.engine.manage.EngineManager;
import it.eng.workflow.service.bean.CompletaTaskServiceInBean;
import it.eng.workflow.service.bean.CompletaTaskServiceOutBean;

import org.apache.log4j.Logger;

@Service(name = "CompletaTaskService")
public class CompletaTaskService {

	private static Logger mLogger = Logger.getLogger(CompletaTaskService.class);
	
	@Operation(name = "completaTask")
	public CompletaTaskServiceOutBean completaTask(AurigaLoginBean pAurigaLoginBean, CompletaTaskServiceInBean pCompletaTaskServiceInBean) {
		
		initSubject(pAurigaLoginBean);
		
		CompletaTaskServiceOutBean lCompletaTaskServiceOutBean = new CompletaTaskServiceOutBean();
		
		try {
			EngineManager lEngineManager = new EngineManager();
			boolean completato = lEngineManager.completeTask(pCompletaTaskServiceInBean.getIdTask());
			if (!completato){
				throw new Exception("Task non completato, vedere i log di activity");
			}
		} catch (Exception e){
			mLogger.error("Errore: " + e.getMessage(), e);
			lCompletaTaskServiceOutBean.setEsito(false);
			lCompletaTaskServiceOutBean.setError("Errore del motore di Workflow: " + e.getMessage());
			return lCompletaTaskServiceOutBean;
		}
		
		lCompletaTaskServiceOutBean.setEsito(true);
		
		return lCompletaTaskServiceOutBean;
	}
	
	private void initSubject(AurigaLoginBean pAurigaLoginBean) {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		subject.setUuidtransaction(pAurigaLoginBean.getUuid());
		SubjectUtil.subject.set(subject);
	}
	
}
