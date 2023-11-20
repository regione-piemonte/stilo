/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAddfolderBean;
import it.eng.auriga.database.store.dmpk_core.store.impl.AdddocImpl;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesIuassociazioneudvsprocBean;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesSetobjprocessforBean;
import it.eng.auriga.database.store.dmpk_processes.store.Insupdprocess;
import it.eng.auriga.database.store.dmpk_processes.store.Setobjprocessfor;
import it.eng.auriga.database.store.dmpk_processes.store.impl.IuassociazioneudvsprocImpl;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityGetestremiregnumud_jBean;
import it.eng.auriga.database.store.dmpk_utility.store.impl.Getestremiregnumud_jImpl;
import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfGetprocdefidflussowfBean;
import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfStartflussowfforprocessBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.dm.engine.manage.EngineManager;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.storeutil.AnalyzeResult;
import it.eng.workflow.service.bean.AvvioIterAttiServiceInBean;
import it.eng.workflow.service.bean.AvvioIterAttiServiceOutBean;
import it.eng.workflow.service.bean.AvvioProcFascicoloServiceInBean;
import it.eng.workflow.service.bean.AvvioProcFascicoloServiceOutBean;
import it.eng.workflow.service.bean.AvvioProcedimentoServiceInBean;
import it.eng.workflow.service.bean.AvvioProcedimentoServiceOutBean;
import it.eng.workflow.service.document.IuAssociazioneUdVsProc;
import it.eng.workflow.service.folder.AddFolderProcess;
import it.eng.workflow.service.folder.SetObjProcessFor;
import it.eng.workflow.service.process.RetrieveProcessDefinition;
import it.eng.workflow.service.process.StartFlussoProcess;
import it.eng.xml.XmlUtilitySerializer;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

/**
 * Classe di service che si occupa di richiamare i servizi di workflow
 * @author Rametta
 *
 */
@Service(name = "AvvioProcedimentoService")
public class AvvioProcedimentoService {

	private static Logger mLogger = Logger.getLogger(AvvioProcedimentoService.class);

	/**
	 * Classe che riceve un procedimento da avviare, chiama la startProcess della {@link EngineManager} di workflowManager,
	 * poi chiama la store di inserimento del processo {@link Insupdprocess} e si fa una addFolder per il procedimento creato.
	 * 
	 * Successivamente di chiama la store {@link Setobjprocessfor} per associare il procedimento al folder creato.
	 * 
	 * @param pAvvioProcedimentoServiceInBean
	 * @return
	 * @throws Exception 
	 */
	@Operation(name = "avviaProcesso")
	public AvvioProcedimentoServiceOutBean avviaProcesso(AurigaLoginBean pAurigaLoginBean, AvvioProcedimentoServiceInBean pAvvioProcedimentoServiceInBean) {
		long start, end;
		
		initSubject(pAurigaLoginBean);
		
		AvvioProcedimentoServiceOutBean lAvvioProcedimentoServiceOutBean = new AvvioProcedimentoServiceOutBean();
		mLogger.debug("Richiesto avvio processo " + pAvvioProcedimentoServiceInBean.getFlowTypeId());
		String processInstanceId = null;
		try {
			EngineManager lEngineManager = new EngineManager();
			mLogger.debug("EngineManager.startProcess()");
			start = new Date().getTime();			
			processInstanceId = lEngineManager.startProcess(pAvvioProcedimentoServiceInBean.getFlowTypeId());
			end = new Date().getTime();			
			mLogger.debug("Eseguito in " + (end - start) + " ms");
			lAvvioProcedimentoServiceOutBean.setProcessInstanceId(processInstanceId);
			mLogger.debug("Procedimento avviato con processInstanceId: " + processInstanceId);
		} catch (Exception e){
			mLogger.error("Errore: " + e.getMessage(), e);
			lAvvioProcedimentoServiceOutBean.setEsito(false);
			lAvvioProcedimentoServiceOutBean.setError("Errore del motore di workflow");
			return lAvvioProcedimentoServiceOutBean;
		}	
			
		Session lSession = null;
		Transaction lTransaction = null;
		try {
			lSession = HibernateUtil.begin();
			lTransaction = lSession.beginTransaction();
						
			CompletaTaskProcService lCompletaTaskService = new CompletaTaskProcService();
			mLogger.debug("CompletaTaskService.completaTask()");
			start = new Date().getTime();			
			lCompletaTaskService.completaTask(processInstanceId, pAurigaLoginBean, lSession);
			end = new Date().getTime();			
			mLogger.debug("Eseguito in " + (end - start) + " ms");
			
			StartFlussoProcess lStartFlussoProcess = new StartFlussoProcess();
			mLogger.debug("StartFlussoProcess.startFlussoAvvioProcedimento()");
			start = new Date().getTime();			
			StoreResultBean<DmpkWfStartflussowfforprocessBean> lStoreResultBeanStartFlusso = lStartFlussoProcess.startFlussoAvvioProcedimento(processInstanceId, pAvvioProcedimentoServiceInBean, pAurigaLoginBean, lSession);
			end = new Date().getTime();			
			mLogger.debug("Eseguito in " + (end - start) + " ms");			
			if (lStoreResultBeanStartFlusso.isInError()){
				throw new Exception(lStoreResultBeanStartFlusso.getDefaultMessage());
			}
			lAvvioProcedimentoServiceOutBean.setIdProcesso(lStoreResultBeanStartFlusso.getResultBean().getIdprocessio());
			
			AddFolderProcess lAddFolderProcess = new AddFolderProcess();
			mLogger.debug("AddFolderProcess.addFolderAvvioProcedimento()");
			start = new Date().getTime();			
			StoreResultBean<DmpkCoreAddfolderBean> lStoreResultBeanAddFolder = lAddFolderProcess.addFolderAvvioProcedimento(lAvvioProcedimentoServiceOutBean.getIdProcesso(), pAurigaLoginBean, lSession);
			end = new Date().getTime();			
			mLogger.debug("Eseguito in " + (end - start) + " ms");
			if (lStoreResultBeanAddFolder.isInError()){
				throw new Exception(lStoreResultBeanAddFolder.getDefaultMessage());
			}
			lAvvioProcedimentoServiceOutBean.setIdFolder(lStoreResultBeanAddFolder.getResultBean().getIdfolderout());
			mLogger.debug("Fascicolo creato con idFolder: " + lAvvioProcedimentoServiceOutBean.getIdFolder());
			
			RetrieveProcessDefinition lRetrieveProcessDefinition = new RetrieveProcessDefinition();
			mLogger.debug("RetrieveProcessDefinition.getProcessDefinition()");
			start = new Date().getTime();			
			StoreResultBean<DmpkWfGetprocdefidflussowfBean> lStoreResultBeanRetrieveProcessDefinition =	lRetrieveProcessDefinition.getProcessDefinition(processInstanceId, pAurigaLoginBean, lSession);
			end = new Date().getTime();			
			mLogger.debug("Eseguito in " + (end - start) + " ms");
			if(StringUtils.isBlank(lStoreResultBeanRetrieveProcessDefinition.getResultBean().getParametro_1())){
				throw new Exception("Impossibile recuperare il processDefinitionId per il processInstanceId " + processInstanceId);
			}
			lAvvioProcedimentoServiceOutBean.setProcessDefinitionId(lStoreResultBeanRetrieveProcessDefinition.getResultBean().getParametro_1());
			mLogger.debug("Recuperato processDefinitionId: " + lAvvioProcedimentoServiceOutBean.getIdFolder());	
			lSession.flush();
			lTransaction.commit();
		} catch (Exception e) {
			mLogger.error("Errore: " + e.getMessage(), e);
			mLogger.debug("Annullo la transazione");
			if (lTransaction!=null && !lTransaction.wasCommitted()){
				lTransaction.rollback();
			}
			lAvvioProcedimentoServiceOutBean.setEsito(false);
			lAvvioProcedimentoServiceOutBean.setError(e.getMessage());
			return lAvvioProcedimentoServiceOutBean;
		} finally {
			if (lSession != null){
				lSession.close();
			}
		}
		lAvvioProcedimentoServiceOutBean.setEsito(true);
		return lAvvioProcedimentoServiceOutBean;
	}
	
	@Operation(name = "avviaIterAtti")
	public AvvioIterAttiServiceOutBean avviaIterAtti(AurigaLoginBean pAurigaLoginBean, AvvioIterAttiServiceInBean pAvvioIterAttiServiceInBean) {
		
		initSubject(pAurigaLoginBean);
		
		String idUoProponente = StringUtils.isNotBlank(pAvvioIterAttiServiceInBean.getIdUoProponente()) ? pAvvioIterAttiServiceInBean.getIdUoProponente() : null;
		pAvvioIterAttiServiceInBean.setIdUoProponente(idUoProponente != null && idUoProponente.startsWith("UO") ? idUoProponente.substring(2) : idUoProponente);
		AvvioIterAttiServiceOutBean lAvvioIterAttiServiceOutBean = new AvvioIterAttiServiceOutBean();
		String lStrProcessInstanceId = null;
		try {
			EngineManager lEngineManager = new EngineManager();
			lStrProcessInstanceId = lEngineManager.startProcess(pAvvioIterAttiServiceInBean.getIdTipoFlussoActiviti());
		} catch (Exception e){
			mLogger.error("Errore: " + e.getMessage(), e);
			lAvvioIterAttiServiceOutBean.setEsito(false);
			lAvvioIterAttiServiceOutBean.setError("Errore del motore di Workflow");
			return lAvvioIterAttiServiceOutBean;
		}	
		mLogger.debug("Procedimento avviato con processInstanceId: " + lStrProcessInstanceId);
		lAvvioIterAttiServiceOutBean.setProcessInstanceId(lStrProcessInstanceId);
		Session lSession = null;
		Transaction lTransaction = null;
		try {
			lSession = HibernateUtil.begin();
			lTransaction = lSession.beginTransaction();			
			
//			AddDocProposta lAddDocProposta = new AddDocProposta();
//			StoreResultBean<DmpkCoreAdddocBean> lStoreResultBeanAddDoc = lAddDocProposta.addDocAvvioIterAtti(pAvvioIterAttiServiceInBean.getIdTipoDocProposta(), pAvvioIterAttiServiceInBean.getSiglaProposta(), pAvvioIterAttiServiceInBean.getIdUoProponente(), pAurigaLoginBean, lSession);
//			if (lStoreResultBeanAddDoc.isInError()){
//				throw new Exception(lStoreResultBeanAddDoc.getDefaultMessage());
//			}
//			BigDecimal lBigDecimalIdUd = lStoreResultBeanAddDoc.getResultBean().getIdudout();
			
			BigDecimal lBigDecimalIdUd = new BigDecimal(pAvvioIterAttiServiceInBean.getIdUd());
			mLogger.debug("L'idUd della proposta appena creata vale " + lBigDecimalIdUd);
			
			lAvvioIterAttiServiceOutBean.setIdUd(lBigDecimalIdUd);
			
			StoreResultBean<DmpkUtilityGetestremiregnumud_jBean> lGetEstremiRegNumUdResult = getEstremiRegNumUd(lBigDecimalIdUd, pAvvioIterAttiServiceInBean.getSiglaProposta(), pAurigaLoginBean, lSession);		
			String lEstremiRegNumUd = pAvvioIterAttiServiceInBean.getNomeTipoDocProposta() + " N° " + lGetEstremiRegNumUdResult.getResultBean().getSiglaregio() + " " + lGetEstremiRegNumUdResult.getResultBean().getNumregout().toPlainString() + "/" + lGetEstremiRegNumUdResult.getResultBean().getAnnoregout();
			lAvvioIterAttiServiceOutBean.setEstremiRegNumUd(lEstremiRegNumUd);
			
			StartFlussoProcess lStartFlussoProcess = new StartFlussoProcess();
			StoreResultBean<DmpkWfStartflussowfforprocessBean> lStoreResultBeanStartFlusso = lStartFlussoProcess.startFlussoAvvioIterAtti(lStrProcessInstanceId, lBigDecimalIdUd, lEstremiRegNumUd, pAvvioIterAttiServiceInBean, pAurigaLoginBean, lSession);
			if (lStoreResultBeanStartFlusso.isInError()){
				throw new Exception(lStoreResultBeanStartFlusso.getDefaultMessage());
			}
			BigDecimal lBigDecimalIdProcesso = lStoreResultBeanStartFlusso.getResultBean().getIdprocessio();
			mLogger.debug("L'idProcesso appena creato vale " + lBigDecimalIdProcesso);
			lAvvioIterAttiServiceOutBean.setIdProcesso(lBigDecimalIdProcesso);
			
			if(StringUtils.isBlank(pAvvioIterAttiServiceInBean.getIdFolder())) {			
				AddFolderProcess lAddFolderProcess = new AddFolderProcess();
				StoreResultBean<DmpkCoreAddfolderBean> lStoreResultBeanAddFolder = lAddFolderProcess.addFolderAvvioIterAtti(lBigDecimalIdProcesso, pAvvioIterAttiServiceInBean.getIdUoProponente(), pAurigaLoginBean, lSession);
				if (lStoreResultBeanAddFolder.isInError()){
					throw new Exception(lStoreResultBeanAddFolder.getDefaultMessage());
				}
				BigDecimal lBigDecimalIdFolderOut = lStoreResultBeanAddFolder.getResultBean().getIdfolderout();
				mLogger.debug("L'idFolder appena creato vale " + lBigDecimalIdFolderOut);
				lAvvioIterAttiServiceOutBean.setIdFolder(lBigDecimalIdFolderOut);
			} else {
				SetObjProcessFor lSetObjProcessFor = new SetObjProcessFor();
				BigDecimal lBigDecimalIdFolder = new BigDecimal(pAvvioIterAttiServiceInBean.getIdFolder());
				StoreResultBean<DmpkProcessesSetobjprocessforBean> lStoreResultBeanSetObjProcessFor = lSetObjProcessFor.setObjProcessForAvvioProc(lBigDecimalIdProcesso, lBigDecimalIdFolder, "F", pAurigaLoginBean, lSession);
				if (lStoreResultBeanSetObjProcessFor.isInError()){
					throw new Exception(lStoreResultBeanSetObjProcessFor.getDefaultMessage());
				}		
				mLogger.debug("L'idFolder agganciato al processo vale " + lBigDecimalIdFolder);
				lAvvioIterAttiServiceOutBean.setIdFolder(lBigDecimalIdFolder);
			}		
			
			IuAssociazioneUdVsProc lIuAssociazioneUdVsProc = new IuAssociazioneUdVsProc();			
			StoreResultBean<DmpkProcessesIuassociazioneudvsprocBean> lStoreResultBeanIuAssociazioneUdVsProc = lIuAssociazioneUdVsProc.salvaAssociazioneUdVsProc(lBigDecimalIdProcesso, lBigDecimalIdUd, pAurigaLoginBean, lSession);
			if (lStoreResultBeanIuAssociazioneUdVsProc.isInError()){
				throw new Exception(lStoreResultBeanIuAssociazioneUdVsProc.getDefaultMessage());
			}	
			
			if(StringUtils.isNotBlank(pAvvioIterAttiServiceInBean.getIdUdRichiesta())) {
				BigDecimal lBigDecimalIdUdRichiesta = new BigDecimal(pAvvioIterAttiServiceInBean.getIdUdRichiesta());
				mLogger.debug("L'idUd di richiesta della proposta associata al processo vale " + lBigDecimalIdUdRichiesta);				
				StoreResultBean<DmpkProcessesIuassociazioneudvsprocBean> lStoreResultBeanIuAssociazioneUdRichiestaVsProc = lIuAssociazioneUdVsProc.salvaAssociazioneUdRichiestaVsProc(lBigDecimalIdProcesso, lBigDecimalIdUdRichiesta, pAurigaLoginBean, lSession);
				if (lStoreResultBeanIuAssociazioneUdRichiestaVsProc.isInError()){
					throw new Exception(lStoreResultBeanIuAssociazioneUdRichiestaVsProc.getDefaultMessage());
				}	
			}
			
			RetrieveProcessDefinition lRetrieveProcessDefinition = new RetrieveProcessDefinition();
			StoreResultBean<DmpkWfGetprocdefidflussowfBean> lStoreResultBeanRetrieveProcessDefinition =	lRetrieveProcessDefinition.getProcessDefinition(lStrProcessInstanceId, pAurigaLoginBean, lSession);
			if (StringUtils.isEmpty(lStoreResultBeanRetrieveProcessDefinition.getResultBean().getParametro_1())){
				throw new Exception("Impossibile recuperare il ProcessDefinitionId per il ProcessInstanceId " + lStrProcessInstanceId);
			}
			lAvvioIterAttiServiceOutBean.setProcessDefinitionId(lStoreResultBeanRetrieveProcessDefinition.getResultBean().getParametro_1());
			lSession.flush();
			lTransaction.commit();
		} catch (Exception e) {
			mLogger.error("Errore: " + e.getMessage(), e);
			mLogger.debug("Annullo la transazione");
			if (lTransaction!=null && !lTransaction.wasCommitted()){
				lTransaction.rollback();
			}
			lAvvioIterAttiServiceOutBean.setEsito(false);
			lAvvioIterAttiServiceOutBean.setError(e.getMessage());
			return lAvvioIterAttiServiceOutBean;
		} finally {
			if (lSession != null){
				lSession.close();
			}
		}
		lAvvioIterAttiServiceOutBean.setEsito(true);
		return lAvvioIterAttiServiceOutBean;
	}
	
	@Operation(name = "avviaProcFascicolo")
	public AvvioProcFascicoloServiceOutBean avviaProcFascicolo(AurigaLoginBean pAurigaLoginBean, AvvioProcFascicoloServiceInBean pAvvioProcFascicoloServiceInBean) {
		
		initSubject(pAurigaLoginBean);
		
		AvvioProcFascicoloServiceOutBean lAvvioProcFascicoloServiceOutBean = new AvvioProcFascicoloServiceOutBean();
		String lStrProcessInstanceId = null;
		try {
			EngineManager lEngineManager = new EngineManager();
			lStrProcessInstanceId = lEngineManager.startProcess(pAvvioProcFascicoloServiceInBean.getFlowTypeId());
		} catch (Exception e){
			mLogger.error("Errore: " + e.getMessage(), e);
			lAvvioProcFascicoloServiceOutBean.setEsito(false);
			lAvvioProcFascicoloServiceOutBean.setError("Errore del motore di Workflow");
			return lAvvioProcFascicoloServiceOutBean;
		}	
		mLogger.debug("Procedimento avviato con processInstanceId: " + lStrProcessInstanceId);
		lAvvioProcFascicoloServiceOutBean.setProcessInstanceId(lStrProcessInstanceId);
		Session lSession = null;
		Transaction lTransaction = null;
		try {
			lSession = HibernateUtil.begin();
			lTransaction = lSession.beginTransaction();			
						
			StartFlussoProcess lStartFlussoProcess = new StartFlussoProcess();
			StoreResultBean<DmpkWfStartflussowfforprocessBean> lStoreResultBeanStartFlusso = lStartFlussoProcess.startFlussoAvvioProcedimento(lStrProcessInstanceId, pAvvioProcFascicoloServiceInBean, pAurigaLoginBean, lSession);
			if (lStoreResultBeanStartFlusso.isInError()){
				throw new Exception(lStoreResultBeanStartFlusso.getDefaultMessage());
			}
			BigDecimal lBigDecimalIdProcesso = lStoreResultBeanStartFlusso.getResultBean().getIdprocessio();
			mLogger.debug("Processo creato con idProc = " + lBigDecimalIdProcesso);
			lAvvioProcFascicoloServiceOutBean.setIdProcesso(lBigDecimalIdProcesso);
			
			//Creo il fascicolo del procedimento
			AddFolderProcess lAddFolderProcess = new AddFolderProcess();
			StoreResultBean<DmpkCoreAddfolderBean> lStoreResultBeanAddFolder = lAddFolderProcess.addFolderAvvioProcedimento(lBigDecimalIdProcesso, pAurigaLoginBean, lSession);
			if (lStoreResultBeanAddFolder.isInError()){
				throw new Exception(lStoreResultBeanAddFolder.getDefaultMessage());
			}
			BigDecimal lBigDecimalIdFolderOut = lStoreResultBeanAddFolder.getResultBean().getIdfolderout();
			mLogger.debug("Folder del proc. creato con idFolder = " + lBigDecimalIdFolderOut);
			lAvvioProcFascicoloServiceOutBean.setIdFolder(lBigDecimalIdFolderOut);
			
			//Creo il documento di risposta all'istanza
			DmpkCoreAdddocBean lDmpkCoreAdddocBean = new DmpkCoreAdddocBean();
			lDmpkCoreAdddocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
			lDmpkCoreAdddocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
			CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();
			lCreaModDocumentoInBean.setTipoDocumento(pAvvioProcFascicoloServiceInBean.getIdTipoDoc());			
			lCreaModDocumentoInBean.setOggetto(pAvvioProcFascicoloServiceInBean.getNomeTipoDoc());			
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			String lStrXml = lXmlUtilitySerializer.bindXml(lCreaModDocumentoInBean);
			lDmpkCoreAdddocBean.setAttributiuddocxmlin(lStrXml);
			lDmpkCoreAdddocBean.setFlgrollbckfullin(1);
			lDmpkCoreAdddocBean.setFlgautocommitin(null);
			final AdddocImpl lAdddocImpl = new AdddocImpl();
			lAdddocImpl.setBean(lDmpkCoreAdddocBean);
			lSession.doWork(new Work() {
				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					lAdddocImpl.execute(paramConnection);
				}
			});
			StoreResultBean<DmpkCoreAdddocBean> lStoreResultBeanAddDoc = new StoreResultBean<DmpkCoreAdddocBean>();
			AnalyzeResult.analyze(lDmpkCoreAdddocBean, lStoreResultBeanAddDoc);
			lStoreResultBeanAddDoc.setResultBean(lDmpkCoreAdddocBean);
			BigDecimal lBigDecimalIdUdOut = lStoreResultBeanAddDoc.getResultBean().getIdudout();
			BigDecimal lBigDecimalIdDocOut = lStoreResultBeanAddDoc.getResultBean().getIddocout();
			mLogger.debug("Documento di risposta all'istanza creato con idUd = " + lBigDecimalIdUdOut + " e idDoc = " + lBigDecimalIdDocOut);
			
			//Associo il documento di risposta al fascicolo del procedimento con ruolo AFIN (atto finale)
			DmpkProcessesIuassociazioneudvsprocBean lDmpkProcessesIuassociazioneudvsprocBean = new DmpkProcessesIuassociazioneudvsprocBean();
			lDmpkProcessesIuassociazioneudvsprocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
			lDmpkProcessesIuassociazioneudvsprocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
			lDmpkProcessesIuassociazioneudvsprocBean.setIdprocessin(lBigDecimalIdProcesso);
			lDmpkProcessesIuassociazioneudvsprocBean.setIdudin(lBigDecimalIdUdOut);
			lDmpkProcessesIuassociazioneudvsprocBean.setFlgacqprodin("P");
			lDmpkProcessesIuassociazioneudvsprocBean.setCodruolodocinprocin("AFIN");
			
			final IuassociazioneudvsprocImpl lIuassociazioneudvsprocImpl = new IuassociazioneudvsprocImpl();
			lIuassociazioneudvsprocImpl.setBean(lDmpkProcessesIuassociazioneudvsprocBean);
			lSession.doWork(new Work() {
				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					lIuassociazioneudvsprocImpl.execute(paramConnection);
				}
			});
			StoreResultBean<DmpkProcessesIuassociazioneudvsprocBean> lStoreResultBeanIuAssociazioneUdVsProc = new StoreResultBean<DmpkProcessesIuassociazioneudvsprocBean>();
			AnalyzeResult.analyze(lDmpkProcessesIuassociazioneudvsprocBean, lStoreResultBeanIuAssociazioneUdVsProc);
			lStoreResultBeanIuAssociazioneUdVsProc.setResultBean(lDmpkProcessesIuassociazioneudvsprocBean);
			
			//Associo gli altri documenti al fascicolo del procedimento con ruolo INI
			if(pAvvioProcFascicoloServiceInBean.getListaIdUd() != null) {
				for(String lStringIdUd : pAvvioProcFascicoloServiceInBean.getListaIdUd()) {
					BigDecimal lBigDecimalIdUd = new BigDecimal(lStringIdUd);
					IuAssociazioneUdVsProc lIuAssociazioneUdVsProc = new IuAssociazioneUdVsProc();
					lStoreResultBeanIuAssociazioneUdVsProc = lIuAssociazioneUdVsProc.salvaAssociazioneUdVsProc(lBigDecimalIdProcesso, lBigDecimalIdUd, pAurigaLoginBean, lSession);
					if (lStoreResultBeanIuAssociazioneUdVsProc.isInError()) {
						throw new Exception(lStoreResultBeanIuAssociazioneUdVsProc.getDefaultMessage());
					}			
				}
			}		
			
			RetrieveProcessDefinition lRetrieveProcessDefinition = new RetrieveProcessDefinition();
			StoreResultBean<DmpkWfGetprocdefidflussowfBean> lStoreResultBeanRetrieveProcessDefinition =	lRetrieveProcessDefinition.getProcessDefinition(lStrProcessInstanceId, pAurigaLoginBean, lSession);
			if (StringUtils.isEmpty(lStoreResultBeanRetrieveProcessDefinition.getResultBean().getParametro_1())){
				throw new Exception("Impossibile recuperare il ProcessDefinitionId per il ProcessInstanceId " + lStrProcessInstanceId);
			}
			lAvvioProcFascicoloServiceOutBean.setProcessDefinitionId(lStoreResultBeanRetrieveProcessDefinition.getResultBean().getParametro_1());
			lSession.flush();
			lTransaction.commit();
		} catch (Exception e) {
			mLogger.error("Errore: " + e.getMessage(), e);
			mLogger.debug("Annullo la transazione");
			if (lTransaction!=null && !lTransaction.wasCommitted()){
				lTransaction.rollback();
			}
			lAvvioProcFascicoloServiceOutBean.setEsito(false);
			lAvvioProcFascicoloServiceOutBean.setError(e.getMessage());
			return lAvvioProcFascicoloServiceOutBean;
		} finally {
			if (lSession != null){
				lSession.close();
			}
		}
		lAvvioProcFascicoloServiceOutBean.setEsito(true);
		return lAvvioProcFascicoloServiceOutBean;
	}
	
	@Operation(name = "avviaProcGenerico")
	public AvvioProcedimentoServiceOutBean avviaProcGenerico(AurigaLoginBean pAurigaLoginBean, AvvioProcedimentoServiceInBean pAvvioProcedimentoServiceInBean) {
		
		initSubject(pAurigaLoginBean);
		
		AvvioProcedimentoServiceOutBean lAvvioProcedimentoServiceOutBean = new AvvioProcedimentoServiceOutBean();
		String lStrProcessInstanceId = null;
		try {
			EngineManager lEngineManager = new EngineManager();
			lStrProcessInstanceId = lEngineManager.startProcess(pAvvioProcedimentoServiceInBean.getFlowTypeId());
		} catch (Exception e){
			mLogger.error("Errore: " + e.getMessage(), e);
			lAvvioProcedimentoServiceOutBean.setEsito(false);
			lAvvioProcedimentoServiceOutBean.setError("Errore del motore di Workflow");
			return lAvvioProcedimentoServiceOutBean;
		}	
		mLogger.debug("Procedimento avviato con processInstanceId: " + lStrProcessInstanceId);
		lAvvioProcedimentoServiceOutBean.setProcessInstanceId(lStrProcessInstanceId);
		Session lSession = null;
		Transaction lTransaction = null;
		try {
			lSession = HibernateUtil.begin();
			lTransaction = lSession.beginTransaction();			
						
			StartFlussoProcess lStartFlussoProcess = new StartFlussoProcess();
			StoreResultBean<DmpkWfStartflussowfforprocessBean> lStoreResultBeanStartFlusso = lStartFlussoProcess.startFlussoAvvioProcedimento(lStrProcessInstanceId, pAvvioProcedimentoServiceInBean, pAurigaLoginBean, lSession);
			if (lStoreResultBeanStartFlusso.isInError()){
				throw new Exception(lStoreResultBeanStartFlusso.getDefaultMessage());
			}
			BigDecimal lBigDecimalIdProcesso = lStoreResultBeanStartFlusso.getResultBean().getIdprocessio();
			mLogger.debug("Processo creato con idProc = " + lBigDecimalIdProcesso);
			lAvvioProcedimentoServiceOutBean.setIdProcesso(lBigDecimalIdProcesso);
			
			BigDecimal lBigDecimalIdUd = pAvvioProcedimentoServiceInBean.getIdUd() != null && pAvvioProcedimentoServiceInBean.getIdUd().intValue() > 0 ? new BigDecimal(pAvvioProcedimentoServiceInBean.getIdUd()) : null;
			BigDecimal lBigDecimalIdFolder = pAvvioProcedimentoServiceInBean.getIdFolder() != null && pAvvioProcedimentoServiceInBean.getIdFolder().intValue() > 0 ? new BigDecimal(pAvvioProcedimentoServiceInBean.getIdFolder()) : null;
			
			if(lBigDecimalIdUd != null) {
				// se arrivo da un documento				
				SetObjProcessFor lSetObjProcessFor = new SetObjProcessFor();
				StoreResultBean<DmpkProcessesSetobjprocessforBean> lStoreResultBeanSetObjProcessFor = lSetObjProcessFor.setObjProcessForAvvioProc(lBigDecimalIdProcesso, lBigDecimalIdUd, "U", pAurigaLoginBean, lSession);
				if (lStoreResultBeanSetObjProcessFor.isInError()){
					throw new Exception(lStoreResultBeanSetObjProcessFor.getDefaultMessage());
				}	
			} else if(lBigDecimalIdFolder != null) {
				// se arrivo da un fascicolo
				SetObjProcessFor lSetObjProcessFor = new SetObjProcessFor();
				StoreResultBean<DmpkProcessesSetobjprocessforBean> lStoreResultBeanSetObjProcessFor = lSetObjProcessFor.setObjProcessForAvvioProc(lBigDecimalIdProcesso, lBigDecimalIdFolder, "F", pAurigaLoginBean, lSession);
				if (lStoreResultBeanSetObjProcessFor.isInError()){
					throw new Exception(lStoreResultBeanSetObjProcessFor.getDefaultMessage());
				}	
			} else {
				throw new Exception("Impossibile avviare il processo. Manca l'indicazione del fascicolo o dell'unità documentaria da associare.");
			}

			lAvvioProcedimentoServiceOutBean.setIdFolder(lBigDecimalIdFolder);	
						
			RetrieveProcessDefinition lRetrieveProcessDefinition = new RetrieveProcessDefinition();
			StoreResultBean<DmpkWfGetprocdefidflussowfBean> lStoreResultBeanRetrieveProcessDefinition =	lRetrieveProcessDefinition.getProcessDefinition(lStrProcessInstanceId, pAurigaLoginBean, lSession);
			if (StringUtils.isEmpty(lStoreResultBeanRetrieveProcessDefinition.getResultBean().getParametro_1())){
				throw new Exception("Impossibile recuperare il ProcessDefinitionId per il ProcessInstanceId " + lStrProcessInstanceId);
			}
			lAvvioProcedimentoServiceOutBean.setProcessDefinitionId(lStoreResultBeanRetrieveProcessDefinition.getResultBean().getParametro_1());
			lSession.flush();
			lTransaction.commit();
		} catch (Exception e) {
			mLogger.error("Errore: " + e.getMessage(), e);
			mLogger.debug("Annullo la transazione");
			if (lTransaction!=null && !lTransaction.wasCommitted()){
				lTransaction.rollback();
			}
			lAvvioProcedimentoServiceOutBean.setEsito(false);
			lAvvioProcedimentoServiceOutBean.setError(e.getMessage());
			return lAvvioProcedimentoServiceOutBean;
		} finally {
			if (lSession != null){			
				lSession.close();
			}
		}
		lAvvioProcedimentoServiceOutBean.setEsito(true);
		return lAvvioProcedimentoServiceOutBean;
	}
	
	public StoreResultBean<DmpkUtilityGetestremiregnumud_jBean> getEstremiRegNumUd(BigDecimal idUd, String sigla, AurigaLoginBean pAurigaLoginBean, Session lSession) throws Exception{
		DmpkUtilityGetestremiregnumud_jBean lDmpkUtilityGetestremiregnumud_jBean = new DmpkUtilityGetestremiregnumud_jBean();
		lDmpkUtilityGetestremiregnumud_jBean.setIdudin(idUd);
		lDmpkUtilityGetestremiregnumud_jBean.setSiglaregio(sigla);
		final Getestremiregnumud_jImpl lGetestremiregnumud_jImpl = new Getestremiregnumud_jImpl();
		lGetestremiregnumud_jImpl.setBean(lDmpkUtilityGetestremiregnumud_jBean);
		lSession.doWork(new Work() {
			@Override
			public void execute(Connection paramConnection) throws SQLException {
				paramConnection.setAutoCommit(false);
				lGetestremiregnumud_jImpl.execute(paramConnection);
			}
		});
		StoreResultBean<DmpkUtilityGetestremiregnumud_jBean> result = new StoreResultBean<DmpkUtilityGetestremiregnumud_jBean>();
		AnalyzeResult.analyze(lDmpkUtilityGetestremiregnumud_jBean, result);
		result.setResultBean(lDmpkUtilityGetestremiregnumud_jBean);
//		if (result.isInError()){
//			throw new Exception(result.getDefaultMessage());
//		}
		return result;
	}
	
	protected BigDecimal getIdUserLavoro(AurigaLoginBean pAurigaLoginBean) {		
		return StringUtils.isNotBlank(pAurigaLoginBean.getIdUserLavoro()) ? new BigDecimal(pAurigaLoginBean.getIdUserLavoro()) : null;
	}

	private void initSubject(AurigaLoginBean pAurigaLoginBean) {
		SubjectBean subject =  SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		subject.setUuidtransaction(pAurigaLoginBean.getUuid());
		SubjectUtil.subject.set(subject);
	}
	
}
