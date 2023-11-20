/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.beans.PropertyDescriptor;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

import com.hyperborea.sira.ws.ComuniItalia;
import com.hyperborea.sira.ws.ComuniItaliaId;
import com.hyperborea.sira.ws.RelazioniOst;
import com.hyperborea.sira.ws.RelazioniOstId;
import com.hyperborea.sira.ws.TipologieRost;
import com.hyperborea.sira.ws.WsFonteRef;
import com.hyperborea.sira.ws.WsFontiDatiRef;
import com.hyperborea.sira.ws.WsOstRef;

import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesGetprocobjxmlBean;
import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesIueventocustomBean;
import it.eng.auriga.database.store.dmpk_processes.store.impl.GetprocobjxmlImpl;
import it.eng.auriga.database.store.dmpk_processes.store.impl.IueventocustomImpl;
import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfAggprochist_postexecattwfBean;
import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfExecattflussoBean;
import it.eng.auriga.database.store.dmpk_wf.store.impl.Aggprochist_postexecattwfImpl;
import it.eng.auriga.database.store.dmpk_wf.store.impl.ExecattflussoImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.login.service.LoginService;
import it.eng.core.annotation.Operation;
import it.eng.dm.engine.manage.EngineManager;
import it.eng.dm.sira.service.bean.VocabolarioBeanIn;
import it.eng.dm.sira.service.bean.VocabolarioBeanOut;
import it.eng.dm.sira.service.search.VocabolarioService;
import it.eng.dm.sira.service.util.ComuneUtil;
import it.eng.dm.variable.DelayManager;
import it.eng.document.function.bean.EseguiTaskInBean;
import it.eng.document.function.bean.EseguiTaskOutBean;
import it.eng.document.function.bean.VariabileEsitoBean;
import it.eng.entity.TCodaOperConWf;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.job.exception.StoreException;
import it.eng.storeutil.AnalyzeResult;
import it.eng.xml.XmlListaUtility;

//@Service(name = "GestioneTask")
public class GestioneTaskJob {

	private static Logger mLogger = Logger.getLogger(GestioneTaskJob.class);

	// @Operation(name = "salvaTask")
	public EseguiTaskOutBean salvaTask(Session sessionJob, AurigaLoginBean pAurigaLoginBean,
			EseguiTaskInBean pEseguiTaskInBean,String attrEventType,TCodaOperConWf tCodaOperConWfWf)
			throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		EseguiTaskOutBean lEseguiTaskOutBean = new EseguiTaskOutBean();

		boolean completeTask = false;

		try {

			Transaction lTransaction = sessionJob.beginTransaction();
			LoginService lLoginService = new LoginService();

			try {

				mLogger.error(
						"\n\n############################" + "\n## Procedimento: " + pEseguiTaskInBean.getIdProcess()
								+ "\n## Tipo Evento: " + pEseguiTaskInBean.getIdEventType() + "\n## Istanza Evento: "
								+ pEseguiTaskInBean.getIdEvento() + "\n############################\n\n");

				if (pEseguiTaskInBean.getIdEventType() != null) {

					DmpkProcessesGetprocobjxmlBean lDmpkProcessesGetprocobjxmlBean = new DmpkProcessesGetprocobjxmlBean();
					lDmpkProcessesGetprocobjxmlBean.setIdprocessin(new BigDecimal(pEseguiTaskInBean.getIdProcess()));
					lDmpkProcessesGetprocobjxmlBean
							.setIdtipoeventoin(new BigDecimal(pEseguiTaskInBean.getIdEventType()));

					final GetprocobjxmlImpl lGetprocobjxml = new GetprocobjxmlImpl();
					lGetprocobjxml.setBean(lDmpkProcessesGetprocobjxmlBean);

					sessionJob.doWork(new Work() {

						@Override
						public void execute(Connection paramConnection) throws SQLException {
							paramConnection.setAutoCommit(false);
							lGetprocobjxml.execute(paramConnection);
						}
					});

					StoreResultBean<DmpkProcessesGetprocobjxmlBean> result = new StoreResultBean<DmpkProcessesGetprocobjxmlBean>();
					AnalyzeResult.analyze(lDmpkProcessesGetprocobjxmlBean, result);
					result.setResultBean(lDmpkProcessesGetprocobjxmlBean);

					if (result.isInError()) {
						mLogger.error("\n\n############################\n## " + result.getErrorContext()
								+ "\n############################\n\n");
					}
					String xmlProcObj = result.getResultBean().getXmlprobobjout();
					mLogger.error("\n\n############################\n## Result:\n" + xmlProcObj
							+ "\n############################\n\n");
				}

				// STEP 1: la IUEventoCustom passandole come IdEventoIO quello
				// restituito dalla IUEventoCustom del salva e TsComplEventoIn =
				// data e ora correnti (fmt GG/MM/AAAA HH24:MI:SS)

				if (attrEventType != null && pEseguiTaskInBean.getIdEventType() != null) {

					DmpkProcessesIueventocustomBean lIueventocustomBean = new DmpkProcessesIueventocustomBean();
					lIueventocustomBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
//					lIueventocustomBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
					lIueventocustomBean.setIdprocessin(StringUtils.isNotBlank(pEseguiTaskInBean.getIdProcess())
							? new BigDecimal(pEseguiTaskInBean.getIdProcess()) : null);
//					lIueventocustomBean.setIdeventoio(StringUtils.isNotBlank(pEseguiTaskInBean.getIdEvento())
//							? new BigDecimal(pEseguiTaskInBean.getIdEvento()) : null);
					lIueventocustomBean.setIdtipoeventoin(StringUtils.isNotBlank(pEseguiTaskInBean.getIdEventType())
							? new BigDecimal(pEseguiTaskInBean.getIdEventType()) : null);
//					lIueventocustomBean
//							.setTscompleventoin(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
//					lIueventocustomBean.setMessaggioin(pEseguiTaskInBean.getNoteEvento());
//					SezioneCache sc = new SezioneCache();
//					Variabile variabile = new Variabile();
//					variabile.setNome("TASK_RESULT_ESITO_VAL_PREL");
//					variabile.setValoreSemplice("Proposta di risposta approvata");
//					
//					sc.getVariabile().add(variabile);
//					StringWriter sw = new StringWriter();
//					Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
//					lMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//					lMarshaller.marshal(sc, sw);
//
//					mLogger.error("SEZIONE CACHE: " + sw );

					lIueventocustomBean.setAttributiaddin(attrEventType);

					lIueventocustomBean.setFlgautocommitin(0); // blocco
																// l'autocommit

					final IueventocustomImpl storeIueventocustom = new IueventocustomImpl();
					storeIueventocustom.setBean(lIueventocustomBean);

					lLoginService.login(pAurigaLoginBean);

					sessionJob.doWork(new Work() {

						@Override
						public void execute(Connection paramConnection) throws SQLException {
							paramConnection.setAutoCommit(false);
							storeIueventocustom.execute(paramConnection);
						}
					});

					StoreResultBean<DmpkProcessesIueventocustomBean> resultIueventocustom = new StoreResultBean<DmpkProcessesIueventocustomBean>();
					AnalyzeResult.analyze(lIueventocustomBean, resultIueventocustom);
					resultIueventocustom.setResultBean(lIueventocustomBean);

					if (resultIueventocustom.isInError()) {
						throw new StoreException(resultIueventocustom);
					}

				}

				if (StringUtils.isNotBlank(pEseguiTaskInBean.getInstanceId())) {

					// STEP 2: la dmpk_wf.ExecAttFlusso (solo se colonna 3
					// valorizzata ovvero se task del workflow) . DesEsitoAttOut
					// viene
					// restituito in output

					DmpkWfExecattflussoBean lExecattflussoBean = new DmpkWfExecattflussoBean();
					lExecattflussoBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
					
					//lExecattflussoBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
					lExecattflussoBean.setIduserlavoroin(tCodaOperConWfWf.getIdUserAnomeDi());
					
					lExecattflussoBean.setIdprocessin(StringUtils.isNotBlank(pEseguiTaskInBean.getIdProcess())
							? new BigDecimal(pEseguiTaskInBean.getIdProcess()) : null);
					lExecattflussoBean.setActivitynamein(pEseguiTaskInBean.getActivityName());
					lExecattflussoBean.setAttributiaddin(null);
					lExecattflussoBean.setFlgautocommitin(0); // blocco
																// l'autocommit
					//lExecattflussoBean.setMessaggioin(pEseguiTaskInBean.getNoteEvento());
					if (tCodaOperConWfWf.getMsgComplTask() != null)
					{
						lExecattflussoBean.setMessaggioin(tCodaOperConWfWf.getMsgComplTask());
					}
					else
					{
						lExecattflussoBean.setMessaggioin(null);
					}
					lExecattflussoBean.setMessaggioin(tCodaOperConWfWf.getMsgComplTask());
					
					final ExecattflussoImpl storeExecattflusso = new ExecattflussoImpl();
					storeExecattflusso.setBean(lExecattflussoBean);

					lLoginService.login(pAurigaLoginBean);

					sessionJob.doWork(new Work() {

						@Override
						public void execute(Connection paramConnection) throws SQLException {
							paramConnection.setAutoCommit(false);
							storeExecattflusso.execute(paramConnection);
						}
					});

					StoreResultBean<DmpkWfExecattflussoBean> resultExecattflusso = new StoreResultBean<DmpkWfExecattflussoBean>();
					AnalyzeResult.analyze(lExecattflussoBean, resultExecattflusso);
					resultExecattflusso.setResultBean(lExecattflussoBean);

					if (StringUtils.isNotEmpty(resultExecattflusso.getDefaultMessage())) {
						mLogger.error("\n\n############################ resultExecattflusso.getDefaultMessage()\n "
								+ resultExecattflusso.getDefaultMessage() + "\n############################\n\n");
						throw new Exception(resultExecattflusso.getDefaultMessage());
					}

					if (resultExecattflusso.isInError()) {
						throw new StoreException(resultExecattflusso);
					}

					mLogger.error("resultExecattflusso: " + resultExecattflusso
							+ "; resultExecattflusso.getResultBean() " + resultExecattflusso.getResultBean()
							+ "; resultExecattflusso.getResultBean().getFlgacttocompleteinwfout() "
							+ resultExecattflusso.getResultBean().getFlgacttocompleteinwfout());
					// STEP 3: solo se colonna 1 valorizzata AND se la
					// dmpk_wf.ExecAttFlusso restituisce
					// FlgActToCompleteInWFOut=1: si fa
					// complete di Activiti passando esito restituito allo step
					// 2)

					if (resultExecattflusso.getResultBean().getFlgacttocompleteinwfout() != null
							&& resultExecattflusso.getResultBean().getFlgacttocompleteinwfout().intValue() == 1) {

						EngineManager lEngineManager = new EngineManager();

						if (StringUtils.isNotBlank(resultExecattflusso.getResultBean().getVariabiliesitiout())) {
							List<VariabileEsitoBean> listaVariabiliEsito = XmlListaUtility.recuperaLista(
									resultExecattflusso.getResultBean().getVariabiliesitiout(),
									VariabileEsitoBean.class);
							if (listaVariabiliEsito != null) {
								for (int i = 0; i < listaVariabiliEsito.size(); i++) {
									VariabileEsitoBean variabileEsito = listaVariabiliEsito.get(i);
									lEngineManager.setVariableTask(pEseguiTaskInBean.getInstanceId(),
											variabileEsito.getNome(), variabileEsito.getEsito());
								}
							}
						}
						
						if (StringUtils.isNotBlank(resultExecattflusso.getResultBean().getVariabileesitoout()) &&
						    StringUtils.isNotBlank(resultExecattflusso.getResultBean().getEsitoout())) {

							lEngineManager.setVariableTask(pEseguiTaskInBean.getInstanceId(),
									resultExecattflusso.getResultBean().getVariabileesitoout(),
									resultExecattflusso.getResultBean().getEsitoout());

						}

						// if
						// ("valutazione_preliminare".equals(pEseguiTaskInBean.getActivityName())){
						// lEngineManager.setVariableTask(pEseguiTaskInBean.getInstanceId(),
						// "esito_valutazione_preliminare",
						// pEseguiTaskInBean.getEsito());
						// }
						// if
						// ("visto_regolarita_contabile".equals(pEseguiTaskInBean.getActivityName())){
						// lEngineManager.setVariableTask(pEseguiTaskInBean.getInstanceId(),
						// "esito_visto_reg_contabile",
						// pEseguiTaskInBean.getEsito());
						// }
						completeTask = lEngineManager.completeTask(pEseguiTaskInBean.getInstanceId());

						if (!completeTask) {
							throw new Exception("Si è verificato un errore durante il completamento del task");
						}
					}

				}

				lTransaction.commit();

			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				if (e instanceof StoreException) {
					BeanUtilsBean2.getInstance().copyProperties(lEseguiTaskOutBean, ((StoreException) e).getError());
					return lEseguiTaskOutBean;
				} else {
					lEseguiTaskOutBean.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");
					lEseguiTaskOutBean.setErrorCode(0);
					return lEseguiTaskOutBean;
				}
			}

			// STEP 4: dmpk_wf.AggProcHist_PostExecAttWF SOLO se fatto step 3

			if (completeTask) {

				final Aggprochist_postexecattwfImpl aggprochist_postexecattwf = new Aggprochist_postexecattwfImpl();
				DmpkWfAggprochist_postexecattwfBean lAggprochist_postexecattwfBean = new DmpkWfAggprochist_postexecattwfBean();
				lAggprochist_postexecattwfBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
				//lAggprochist_postexecattwfBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
				lAggprochist_postexecattwfBean.setIduserlavoroin(tCodaOperConWfWf.getIdUserAnomeDi());
				
				lAggprochist_postexecattwfBean.setIdprocessin(StringUtils.isNotBlank(pEseguiTaskInBean.getIdProcess())
						? new BigDecimal(pEseguiTaskInBean.getIdProcess()) : null);
				lAggprochist_postexecattwfBean.setActivitynamein(pEseguiTaskInBean.getActivityName());

				aggprochist_postexecattwf.setBean(lAggprochist_postexecattwfBean);

				sessionJob.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						aggprochist_postexecattwf.execute(paramConnection);
					}
				});

				StoreResultBean<DmpkWfAggprochist_postexecattwfBean> result = new StoreResultBean<DmpkWfAggprochist_postexecattwfBean>();
				AnalyzeResult.analyze(lAggprochist_postexecattwfBean, result);
				result.setResultBean(lAggprochist_postexecattwfBean);
				String xmlProcObj = result.getResultBean().getActivitynamein();

				mLogger.error("\n\n##############################\nResult: " + xmlProcObj
						+ "\n############################\n\n");

				if (StringUtils.isNotEmpty(result.getDefaultMessage())) {
					mLogger.error("\n\n############################ result.getDefaultMessage()\n "
							+ result.getDefaultMessage() + "\n############################\n\n");
					throw new Exception(result.getDefaultMessage());
				}
				if (result.isInError()) {
					mLogger.error("\n\n############################\n " + result.getErrorContext()
							+ "\n############################\n\n");
					throw new Exception(result.getErrorContext());
				}

			}

		} catch (Exception e) {
			mLogger.error("Errore " + e.getMessage(), e);
			if (e instanceof StoreException) {
				BeanUtilsBean2.getInstance().copyProperties(lEseguiTaskOutBean, ((StoreException) e).getError());
				return lEseguiTaskOutBean;
			} else {
				lEseguiTaskOutBean.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");
				return lEseguiTaskOutBean;
			}
		}

		return lEseguiTaskOutBean;

	}

	@Operation(name = "completaTaskScaduti")
	public List<String> completaTaskScaduti() throws Exception {
		List<String> taskConclusi = null;
		DelayManager delayManager = new DelayManager();
		try {
			taskConclusi = delayManager.executeDelayedTasks(null);
		} catch (Exception e) {
			mLogger.error("Impossibile completare i task ", e);
			throw new Exception("Impossibile completare i task ", e);
		}
		return taskConclusi;
	}

	private boolean isRelProperty(String propertyName) {
		propertyName = propertyName.toUpperCase();

		return propertyName.startsWith("GPA_ID_OBJ_CAR_REL_PROC_OBJ")
				|| propertyName.startsWith("GPA_NATURA_REL_PROC_OBJ")
				|| propertyName.startsWith("GPA_PROV_CI_OBJ_REL_PROC_OBJ")
				|| propertyName.startsWith("GPA_DES_DETT_REL_PROC_OBJ")
				|| propertyName.startsWith("GPA_ID_PROC_OBJ_TY_REL_OBJ_TY")
				|| propertyName.startsWith("GPA_ID_PROC_OBJ_TY_REL_OBJ")
				|| propertyName.startsWith("GPA_PROV_CI_OBJ_TY_REL_OBJ_TY")
				|| propertyName.startsWith("GPA_PROV_CI_OBJ_TY_REL_OBJ") || propertyName.startsWith("GPA_DES_OBJ_REL")
				|| propertyName.startsWith("GPA_NATURA_REL_PROC_OBJ_TY");
	}

	private boolean checkRelProperty(Variabile lVariabile, WsFontiDatiRef fonteRef,
			Map<Integer, RelazioniOst> relazioniMap, String currentIdOst, Integer currentNatura,
			Integer currentCategoria, String currentIdProcObj) {
		boolean retVal = false;

		String propertyName = lVariabile.getNome().toUpperCase();
		String propertyValue = lVariabile.getValoreSemplice();

		int relationshipID = -1;
		String app = "";

		RelazioniOst rel = null;

		if (propertyName.startsWith("GPA_NATURA_REL_PROC_OBJ_")) {
			app = propertyName.replaceAll("GPA_NATURA_REL_PROC_OBJ_", "");
			relationshipID = Integer.parseInt(app);
			if (relazioniMap.containsKey(relationshipID)) {
				rel = relazioniMap.get(relationshipID);
			} else {
				rel = new RelazioniOst();
				WsFonteRef fonteRel = new WsFonteRef();
				fonteRel.setIdFontiDati(fonteRef.getIdFontiDati());
				fonteRel.setTipoFonte(1);
				rel.setWsFonteRef(fonteRel);
			}
		}

		if (propertyName.startsWith("GPA_ID_PROC_OBJ_TY_REL_OBJ_")) {
			app = propertyName.replaceAll("GPA_ID_PROC_OBJ_TY_REL_OBJ_", "");
			relationshipID = Integer.parseInt(app);
			if (relazioniMap.containsKey(relationshipID)) {
				rel = relazioniMap.get(relationshipID);
			} else {
				rel = new RelazioniOst();
				WsFonteRef fonteRel = new WsFonteRef();
				fonteRel.setIdFontiDati(fonteRef.getIdFontiDati());
				fonteRel.setTipoFonte(1);
				rel.setWsFonteRef(fonteRel);
			}
		}

		if (propertyName.startsWith("GPA_PROV_CI_OBJ_TY_REL_OBJ_")) {
			app = propertyName.replaceAll("GPA_PROV_CI_OBJ_TY_REL_OBJ_", "");
			relationshipID = Integer.parseInt(app);
			if (relazioniMap.containsKey(relationshipID)) {
				rel = relazioniMap.get(relationshipID);
			} else {
				rel = new RelazioniOst();
				WsFonteRef fonteRel = new WsFonteRef();
				fonteRel.setIdFontiDati(fonteRef.getIdFontiDati());
				fonteRel.setTipoFonte(1);
				rel.setWsFonteRef(fonteRel);
			}
		}

		if (propertyName.startsWith("GPA_PROV_CI_OBJ_REL_PROC_OBJ_")) {
			app = propertyName.replaceAll("GPA_PROV_CI_OBJ_REL_PROC_OBJ_", "");
			relationshipID = Integer.parseInt(app);
			if (relazioniMap.containsKey(relationshipID)) {
				rel = relazioniMap.get(relationshipID);
			} else {
				rel = new RelazioniOst();
				WsFonteRef fonteRel = new WsFonteRef();
				fonteRel.setIdFontiDati(fonteRef.getIdFontiDati());
				fonteRel.setTipoFonte(1);
				rel.setWsFonteRef(fonteRel);
			}
		}

		if (propertyName.startsWith("GPA_DES_OBJ_REL_")) {
			app = propertyName.replaceAll("GPA_DES_OBJ_REL_", "");
			relationshipID = Integer.parseInt(app);
			if (relazioniMap.containsKey(relationshipID)) {
				rel = relazioniMap.get(relationshipID);
			} else {
				rel = new RelazioniOst();
				WsFonteRef fonteRel = new WsFonteRef();
				fonteRel.setIdFontiDati(fonteRef.getIdFontiDati());
				fonteRel.setTipoFonte(1);
				rel.setWsFonteRef(fonteRel);
			}
		}

		if (propertyName.startsWith("GPA_ID_OBJ_CAR_REL_PROC_OBJ_")) {
			app = propertyName.replaceAll("GPA_ID_OBJ_CAR_REL_PROC_OBJ_", "");
			relationshipID = Integer.parseInt(app);
			if (relazioniMap.containsKey(relationshipID)) {
				rel = relazioniMap.get(relationshipID);
			} else {
				rel = new RelazioniOst();
				WsFonteRef fonteRel = new WsFonteRef();
				fonteRel.setIdFontiDati(fonteRef.getIdFontiDati());
				fonteRel.setTipoFonte(1);
				rel.setWsFonteRef(fonteRel);
			}
		}

		if (propertyName.startsWith("GPA_ID_PROC_OBJ_TY_REL_OBJ_TY_")) {
			app = propertyName.replaceAll("GPA_ID_PROC_OBJ_TY_REL_OBJ_TY_", "");
			relationshipID = Integer.parseInt(app);
			if (relazioniMap.containsKey(relationshipID)) {
				rel = relazioniMap.get(relationshipID);
			} else {
				rel = new RelazioniOst();
				WsFonteRef fonteRel = new WsFonteRef();
				fonteRel.setIdFontiDati(fonteRef.getIdFontiDati());
				fonteRel.setTipoFonte(1);
				rel.setWsFonteRef(fonteRel);
			}
		}

		if (propertyName.startsWith("GPA_PROV_CI_OBJ_TY_REL_OBJ_TY_")) {
			app = propertyName.replaceAll("GPA_PROV_CI_OBJ_TY_REL_OBJ_TY_", "");
			relationshipID = Integer.parseInt(app);
			if (relazioniMap.containsKey(relationshipID)) {
				rel = relazioniMap.get(relationshipID);
			} else {
				rel = new RelazioniOst();
				WsFonteRef fonteRel = new WsFonteRef();
				fonteRel.setIdFontiDati(fonteRef.getIdFontiDati());
				fonteRel.setTipoFonte(1);
				rel.setWsFonteRef(fonteRel);
			}
		}

		if (propertyName.startsWith("GPA_NATURA_REL_PROC_OBJ_TY_")) {
			app = propertyName.replaceAll("GPA_NATURA_REL_PROC_OBJ_TY_", "");
			relationshipID = Integer.parseInt(app);
			if (relazioniMap.containsKey(relationshipID)) {
				rel = relazioniMap.get(relationshipID);
			} else {
				rel = new RelazioniOst();
				WsFonteRef fonteRel = new WsFonteRef();
				fonteRel.setIdFontiDati(fonteRef.getIdFontiDati());
				fonteRel.setTipoFonte(1);
				rel.setWsFonteRef(fonteRel);
			}
		}

		if (propertyName.startsWith("GPA_DES_DETT_REL_PROC_OBJ_")) {
			app = propertyName.replaceAll("GPA_DES_DETT_REL_PROC_OBJ_", "");
			relationshipID = Integer.parseInt(app);
			if (relazioniMap.containsKey(relationshipID)) {
				rel = relazioniMap.get(relationshipID);
			} else {
				rel = new RelazioniOst();
				WsFonteRef fonteRel = new WsFonteRef();
				fonteRel.setIdFontiDati(fonteRef.getIdFontiDati());
				fonteRel.setTipoFonte(1);
				rel.setWsFonteRef(fonteRel);
			}

			/**
			 * SPLITTARE SU "#!#" [0] TIPOLOGIA RELAZIONE --> "ATTIVA" /
			 * "PASSIVA" [1] NATURA RELAZIONE (Stringa descrittiva uguale a
			 * GPA_NATURA_REL_PROC_OBJ [2] CODICE DELLA TIPOLOGIA DI RELAZIONE
			 * (Codice TipologiaRost) [3] CATEGORIA DELLA TIPOLOGIA OST
			 * RELAZIONATA [4] NATURA DELLA TIPOLOGIA OST RELAZIONATA
			 */

			String[] campi = propertyValue.split("#!#");
			boolean isAttiva = (campi[0] == "ATTIVA");
			String naturaRelazione = campi[1];
			Integer codiceRost = Integer.parseInt(campi[2]);
			Integer categoriaRelOst = Integer.parseInt(campi[3]);
			Integer naturaRelOst = Integer.parseInt(campi[4]);

			// SET REL di conseguenza

			RelazioniOstId relId = new RelazioniOstId();

			WsOstRef parentOstRef = new WsOstRef();
			WsOstRef childOstRef = new WsOstRef();

			TipologieRost tipologieRost = new TipologieRost();
			tipologieRost.setCodice(codiceRost);

			if (isAttiva) {
				// N.B. da modificare con lo ID_OST post salvataggio OST
				parentOstRef.setIdOst(Integer.parseInt(currentIdProcObj));
				parentOstRef.setNostId(currentNatura);
				parentOstRef.setCostId(currentCategoria);
				relId.setParentOstRef(parentOstRef);

				// SET CHILD
				// TODO Child da modificare con lo ID_OST post salvataggio OST
				childOstRef.setIdOst(null);
				childOstRef.setNostId(naturaRelOst);
				childOstRef.setCostId(categoriaRelOst);
				relId.setChildOstRef(childOstRef);

				tipologieRost.setDescrizione(naturaRelazione);

			} else {
				// N.B. da modificare con lo ID_OST post salvataggio OST
				childOstRef.setIdOst(Integer.parseInt(currentIdProcObj));
				childOstRef.setNostId(currentNatura);
				childOstRef.setCostId(currentCategoria);
				relId.setChildOstRef(childOstRef);

				// SET PARENT
				// TODO Parent da modificare con lo ID_OST post salvataggio OST
				parentOstRef.setIdOst(null);
				parentOstRef.setNostId(naturaRelOst);
				parentOstRef.setCostId(categoriaRelOst);
				relId.setParentOstRef(parentOstRef);

				tipologieRost.setDescrizionePassiva(naturaRelazione);
			}
			relId.setDataInizio(Calendar.getInstance());
			relId.setTipologieRost(tipologieRost);

			rel.setId(relId);
		}

		if (relationshipID != -1 && app != "" && rel != null) {
			relazioniMap.put(relationshipID, rel);
			retVal = true;
		}

		return retVal;
	}

	public static PropertyDescriptor getPropertyDescriptor(Object object, String property) throws Exception {
		if (StringUtils.isNotBlank(property)) {
			String[] props = property.split("\\.");
			Object obj = object;
			for (int i = 0; i < props.length; i++) {
				String propName = props[i];
				Map<String, Object> objDescr = BeanUtilsBean2.getInstance().getPropertyUtils().describe(obj);
				PropertyDescriptor propDescr = BeanUtilsBean2.getInstance().getPropertyUtils()
						.getPropertyDescriptor(obj, propName);
				if (propDescr == null) {
					throw new Exception("Errore durante il recupero della proprietà " + property);
				}
				String className = propDescr.getPropertyType().getName();
				if (i == props.length - 1) {
					return propDescr;
				} else if (objDescr.get(propName) == null) {
					Object val = Class.forName(className).newInstance();
					BeanUtilsBean2.getInstance().getPropertyUtils().setProperty(obj, propName, val);
					obj = val;
				} else {
					obj = objDescr.get(propName);
				}
			}
			object = obj;
		}
		return null;
	}

	public static void setProperty(Object object, String property, String strValue, String vocabolario)
			throws Exception {
		if (object != null && StringUtils.isNotBlank(strValue)) {
			if (StringUtils.isNotBlank(property)) {
				String[] props = property.split("\\.");
				Object obj = object;
				for (int i = 0; i < props.length; i++) {
					String propName = props[i];
					Map<String, Object> objDescr = BeanUtilsBean2.getInstance().getPropertyUtils().describe(obj);
					PropertyDescriptor propDescr = BeanUtilsBean2.getInstance().getPropertyUtils()
							.getPropertyDescriptor(obj, propName);
					if (propDescr == null) {
						throw new Exception("Errore durante il recupero della proprietà " + property);
					}
					String className = propDescr.getPropertyType().getName();
					if (i == props.length - 1) {
						mLogger.debug(property + ": " + strValue);
						if (StringUtils.isNotBlank(vocabolario)) {
							mLogger.debug("vocabolario: " + vocabolario);
							VocabolarioBeanIn input = new VocabolarioBeanIn();
							input.setClassName(vocabolario);
							VocabolarioService service = new VocabolarioService();
							VocabolarioBeanOut output = service.getVociVocabolarioByClassName(input);

							if (output != null) {

								String idProperty = output.getIdProperty();

								if (idProperty.equals("")) {
									mLogger.error("Errore Vocabolario '" + className
											+ "': idPropertySpecific non specificato");
								} else {
									Object voc = Class.forName(className).newInstance();
									mLogger.debug("voc:" + className + " using: " + idProperty);

									Field privateSpecificField = null;

									for (@SuppressWarnings("rawtypes")
									Class currentClass = Class.forName(className); currentClass
											.getSuperclass() != null; currentClass = currentClass.getSuperclass()) {
										try {
											// mLogger.info("\t\tvoc:" +
											// className + ", currentClass --> "
											// +
											// currentClass.getCanonicalName());
											privateSpecificField = Class.forName(currentClass.getCanonicalName())
													.getDeclaredField(idProperty);
											break;
										} catch (NoSuchFieldException ex) {
											// mLogger.info("\t\tvoc:" +
											// className + ", yet not found: " +
											// idProperty);
										}
									}

									privateSpecificField.setAccessible(true);
									String privateSpecificFieldClass = privateSpecificField.getType()
											.getCanonicalName();

									if (privateSpecificFieldClass != null && output.getVociVocabolario().size() > 0) {
										mLogger.debug("\n\n\t" + propName + ": setting vocabulary indexed by '"
												+ idProperty + "' as type '" + privateSpecificFieldClass + "' with '"
												+ strValue + "'");

										if (privateSpecificFieldClass.equals("java.lang.Integer")) {
											mLogger.debug("\t\t" + propName + ": setting 'java.lang.Integer'\n");
											privateSpecificField.set(voc, Integer.parseInt(strValue));
											BeanUtilsBean2.getInstance().getPropertyUtils().setProperty(obj, propName,
													voc);

										} else if (privateSpecificFieldClass.equals("java.lang.String")) {
											mLogger.debug("\t\t" + propName + ": setting 'java.lang.String'\n");
											privateSpecificField.set(voc, strValue);
											BeanUtilsBean2.getInstance().getPropertyUtils().setProperty(obj, propName,
													voc);

										} else {
											mLogger.error("Error " + propName + ": Voc '" + className
													+ "' is using another class for private field '"
													+ privateSpecificField.getName() + "', used class is '"
													+ privateSpecificFieldClass + "'");
										}
									} else {
										mLogger.error("Error privateSpecificFieldClass is null for " + className);
									}
								}

							} else {
								mLogger.error("Voc item not found!!");
							}

						} else if ("ubicazioniOst.comune".equals(property)) {
							if (StringUtils.isBlank(strValue))
								throw new Exception(
										"Errore durante il salvataggio dell'OST: il comune è un campo obbligatorio");
							BeanUtilsBean2.getInstance().getPropertyUtils().setProperty(obj, "comune", strValue);
							ComuniItalia lComuniItalia = new ComuniItalia();
							ComuniItaliaId lComuniItaliaId = new ComuniItaliaId();
							Map<String, String> codiciComProvReg = ComuneUtil.getCodiciComuneProvinciaRegione(strValue);
							lComuniItaliaId.setIstatReg(codiciComProvReg.get("R")); // es.:
																					// 20
							lComuniItaliaId.setIstatProv(codiciComProvReg.get("P")); // es.:
																						// 091
							lComuniItaliaId.setIstatCom(codiciComProvReg.get("C")); // es.:
																					// 051
							lComuniItalia.setComuniItaliaId(lComuniItaliaId);
							BeanUtilsBean2.getInstance().getPropertyUtils().setProperty(obj, "comuneItalia",
									lComuniItalia);
						} else if (className.equals("java.util.Calendar")) {
							Calendar cal = new GregorianCalendar();
							try {
								cal.setTime(new SimpleDateFormat("dd/MM/yyyy HH:ss").parse(strValue));
							} catch (Exception e) {
								cal.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(strValue));
							}
							BeanUtilsBean2.getInstance().getPropertyUtils().setProperty(obj, propName, cal);
						} else if (className.equals("java.lang.Integer")) {
							BeanUtilsBean2.getInstance().getPropertyUtils().setProperty(obj, propName,
									Integer.parseInt(strValue));
						} else if (className.equals("java.lang.Float")) {
							BeanUtilsBean2.getInstance().getPropertyUtils().setProperty(obj, propName,
									Float.parseFloat(strValue));
						} else if (className.equals("java.lang.String")) {
							BeanUtilsBean2.getInstance().getPropertyUtils().setProperty(obj, propName, strValue);
						}
					} else if (objDescr.get(propName) == null) {
						Object val = Class.forName(className).newInstance();
						BeanUtilsBean2.getInstance().getPropertyUtils().setProperty(obj, propName, val);
						obj = val;
					} else {
						obj = objDescr.get(propName);
					}
				}
				object = obj;
			}
		}
	}

	public static void setArrayProperty(Object object, String property, Object[] arrayValue) throws Exception {
		if (object != null && arrayValue != null) {
			if (StringUtils.isNotBlank(property)) {
				String[] props = property.split("\\.");
				Object obj = object;
				for (int i = 0; i < props.length; i++) {
					String propName = props[i];
					Map<String, Object> objDescr = BeanUtilsBean2.getInstance().getPropertyUtils().describe(obj);
					PropertyDescriptor propDescr = BeanUtilsBean2.getInstance().getPropertyUtils()
							.getPropertyDescriptor(obj, propName);
					if (propDescr == null) {
						throw new Exception("Errore durante il recupero della proprietà " + property);
					}
					String className = propDescr.getPropertyType().getName();
					if (i == props.length - 1) {
						BeanUtilsBean2.getInstance().getPropertyUtils().setProperty(obj, propName, arrayValue);
					} else if (objDescr.get(propName) == null) {
						Object val = Class.forName(className).newInstance();
						BeanUtilsBean2.getInstance().getPropertyUtils().setProperty(obj, propName, val);
						obj = val;
					} else {
						obj = objDescr.get(propName);
					}
				}
				object = obj;
			}
		}
	}

	public static boolean hasSetId(String className) {
		Method[] publicMethods;
		try {
			publicMethods = Class.forName(className).getMethods();
			if (publicMethods != null) {
				for (Method method : publicMethods) {
					if (method.getName().equals("setId")) {
						return true;
					}
				}
			}
		} catch (SecurityException e) {
			mLogger.warn(e);
		} catch (ClassNotFoundException e) {
			mLogger.warn(e);
		}

		return false;
	}

	public static boolean hasSetCodiceByInteger(String className) {
		Method[] publicMethods;
		try {
			publicMethods = Class.forName(className).getMethods();
			if (publicMethods != null) {
				for (Method method : publicMethods) {
					Type[] types = method.getGenericParameterTypes();
					if (method.getName().equals("setCodice") && types.length == 1
							&& types[0].toString().contains("java.lang.Integer")) {
						return true;
					}
				}
			}
		} catch (SecurityException e) {
			mLogger.warn(e);
		} catch (ClassNotFoundException e) {
			mLogger.warn(e);
		}

		return false;
	}

	protected BigDecimal getIdUserLavoro(AurigaLoginBean pAurigaLoginBean) {
		return StringUtils.isNotBlank(pAurigaLoginBean.getIdUserLavoro())
				? new BigDecimal(pAurigaLoginBean.getIdUserLavoro()) : null;
	}

	public static void main(String args[]) throws Exception {
		EngineManager em = new EngineManager();
		em.setVariableTask("21587681", "esito_visto_reg_contabile", "visto rilascio");
		em.completeTask("21587681");

	}
}