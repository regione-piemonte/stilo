/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfGettaskinstidinflowBean;
import it.eng.auriga.database.store.dmpk_wf.store.impl.GettaskinstidinflowImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.dm.engine.manage.EngineManager;
import it.eng.storeutil.AnalyzeResult;
import it.eng.workflow.service.bean.CompletaTaskServiceOutBean;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

public class CompletaTaskProcService {

	private static Logger mLogger = Logger.getLogger(CompletaTaskProcService.class);
	
	public void completaTask(String codFlussoWf, AurigaLoginBean pAurigaLoginBean, Session pSession) throws Exception{
		mLogger.debug("Start completaTask");
		manageTaskFlusso(codFlussoWf, pAurigaLoginBean, TaskDaCompletare.COMPILAISTANZA, pSession);
		manageTaskFlusso(codFlussoWf, pAurigaLoginBean, TaskDaCompletare.ALLEGADOCUMENTAZIONE, pSession);
		manageTaskFlusso(codFlussoWf, pAurigaLoginBean, TaskDaCompletare.PRESENTAISTANZA, pSession);
		mLogger.debug("End completaTask");
	}

	private void manageTaskFlusso(String codFlussoWf, AurigaLoginBean pAurigaLoginBean, TaskDaCompletare pTaskDaCompletare, Session pSession) throws Exception {
		mLogger.debug("Recupero idTask per il flusso " + codFlussoWf + " e task " + pTaskDaCompletare.getNomeTask());
		String idTask = retrieveIdTaskFromTaskDaCompletare(pTaskDaCompletare, codFlussoWf, pAurigaLoginBean, pSession);
		if (StringUtils.isNotEmpty(idTask)){
			mLogger.debug("L'idTask non è vuoto");
			callCompleteTaskOnWorkflow(idTask, pAurigaLoginBean);
		}
	}

	private void callCompleteTaskOnWorkflow(String idTask, AurigaLoginBean lAurigaLoginBean) throws Exception {
		mLogger.debug("Chiamo il servizio per completare il task con id " + idTask);		
		CompletaTaskServiceOutBean lCompletaTaskServiceOutBean = new CompletaTaskServiceOutBean();
		try {
			EngineManager lEngineManager = new EngineManager();
			boolean completato = lEngineManager.completeTask(idTask);
			if (!completato){
				throw new Exception("Task non completato, vedere i log di activity");
			}
			lCompletaTaskServiceOutBean.setEsito(true);
		} catch (Exception e){
			mLogger.error("Errore: " + e.getMessage(), e);
			lCompletaTaskServiceOutBean.setEsito(false);
			lCompletaTaskServiceOutBean.setError("Errore del motore di Workflow: " + e.getMessage());			
		}
		if (!lCompletaTaskServiceOutBean.getEsito()){
			throw new Exception(lCompletaTaskServiceOutBean.getError());
		}
	}

	/**
	 * Chiama la store per recuperare l'idTask
	 * @param TaskDaCompletare
	 * @param pAurigaLoginBean
	 * @return
	 * @throws Exception 
	 */
	private String retrieveIdTaskFromTaskDaCompletare(TaskDaCompletare pTaskDaCompletare, String codFlussoWf, AurigaLoginBean pAurigaLoginBean, Session pSession) throws Exception {
		mLogger.debug("Chiamo la store per recuperare l'idTask per il flusso " + codFlussoWf + " e task " + pTaskDaCompletare.getNomeTask());
		DmpkWfGettaskinstidinflowBean lDmpkWfGettaskinstidinflowBean = new DmpkWfGettaskinstidinflowBean();
		lDmpkWfGettaskinstidinflowBean.setActivitynamein(pTaskDaCompletare.getNomeTask());
		lDmpkWfGettaskinstidinflowBean.setCiflussowfin(codFlussoWf);
		
		final GettaskinstidinflowImpl lGettaskinstidinflowImpl = new GettaskinstidinflowImpl();
		lGettaskinstidinflowImpl.setBean(lDmpkWfGettaskinstidinflowBean);
		pSession.doWork(new Work() {
			@Override
			public void execute(Connection paramConnection) throws SQLException {
				paramConnection.setAutoCommit(false);
				lGettaskinstidinflowImpl.execute(paramConnection);
			}
		});
		StoreResultBean<DmpkWfGettaskinstidinflowBean> result = new StoreResultBean<DmpkWfGettaskinstidinflowBean>();
		AnalyzeResult.analyze(lDmpkWfGettaskinstidinflowBean, result);
		result.setResultBean(lDmpkWfGettaskinstidinflowBean);
		if (result.isInError()){
			throw new Exception(result.getDefaultMessage() + "; code: " + result.getErrorCode() + "; context: " + result.getErrorContext());
		}
		return result.getResultBean().getTaskinstidout();
	}
}
