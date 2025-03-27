/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function;

import java.io.BufferedWriter;
import java.io.File;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

import com.google.gson.Gson;

import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerCaricacontfoglioximportBean;
import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerInsbatchBean;
import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerIufoglioximportBean;
import it.eng.auriga.database.store.dmpk_bmanager.store.impl.CaricacontfoglioximportImpl;
import it.eng.auriga.database.store.dmpk_bmanager.store.impl.InsbatchImpl;
import it.eng.auriga.database.store.dmpk_bmanager.store.impl.IufoglioximportImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.DaoBmtJobParameters;
import it.eng.auriga.module.business.dao.beans.JobParameterBean;
import it.eng.auriga.module.business.login.service.LoginService;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.function.bean.CreaDocWithFileBean;
import it.eng.document.function.bean.CreaDocumentiRegMultiplaUscitaBean;
import it.eng.document.function.bean.DestinatariBean;
import it.eng.document.function.bean.GestioneInserimentoRichXRegMultiplaUscitaInBean;
import it.eng.document.function.bean.GestioneInserimentoRichXRegMultiplaUscitaOutBean;
import it.eng.document.storage.DocumentStorage;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.storeutil.AnalyzeResult;

@Service(name = "GestioneInserimentoRichXRegMultiplaUscita")
public class GestioneInserimentoRichXRegMultiplaUscita {
	
	private static Logger mLogger = Logger.getLogger(GestioneInserimentoRichXRegMultiplaUscita.class);
	public static final String STATO_IN = "da_elaborare";
	
	protected BigDecimal getIdUserLavoro(AurigaLoginBean pAurigaLoginBean) {
		return StringUtils.isNotBlank(pAurigaLoginBean.getIdUserLavoro()) ? new BigDecimal(pAurigaLoginBean.getIdUserLavoro()) : null;
	}
	
	@Operation(name = "creaFoglioXImport")
	public GestioneInserimentoRichXRegMultiplaUscitaOutBean creaFoglioXImport(AurigaLoginBean pAurigaLoginBean, GestioneInserimentoRichXRegMultiplaUscitaInBean pGestioneInserimentoRichXRegMultiplaUscitaInBean) throws JAXBException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		
		GestioneInserimentoRichXRegMultiplaUscitaOutBean outBean = new GestioneInserimentoRichXRegMultiplaUscitaOutBean();
		
		try{
			String uriFile = null;
			String idFoglio = "";
			SubjectBean subject = SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject);
			Session session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			try {
				// SALVATAGGIO EXCEL
				File realFile = DocumentStorage.getRealFile(pGestioneInserimentoRichXRegMultiplaUscitaInBean.getXlsXImport().getUriFileExcel(), pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
				uriFile = DocumentStorage.store(realFile, pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
//				uriFile = inputBean.getXlsXImport().getUriFileExcel();
				if(StringUtils.isEmpty(uriFile)){
					throw new Exception("File non archiviato");
				}
				
				// INSERIMENTO NELLA DMT_FOGLI_X_IMPORT - INIZIO
				DmpkBmanagerIufoglioximportBean lDmpkBmanagerIufoglioximportBean = getDmpkBmanagerIufoglioximportBean(pAurigaLoginBean, pGestioneInserimentoRichXRegMultiplaUscitaInBean, uriFile);
				
				final IufoglioximportImpl iufoglioximport = new IufoglioximportImpl();
				iufoglioximport.setBean(lDmpkBmanagerIufoglioximportBean);
				LoginService lLoginService = new LoginService();
				lLoginService.login(pAurigaLoginBean);
				
				// effettuo chiamata alla store
				session.doWork(new Work() {
					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						iufoglioximport.execute(paramConnection);
					}
				});
				
				StoreResultBean<DmpkBmanagerIufoglioximportBean> resultStore = new StoreResultBean<DmpkBmanagerIufoglioximportBean>();
				AnalyzeResult.analyze(lDmpkBmanagerIufoglioximportBean, resultStore);
				resultStore.setResultBean(lDmpkBmanagerIufoglioximportBean);
				
				if (resultStore.isInError()) {
					throw new StoreException(resultStore);
				} else {
					// salvo id foglio restituito dalla store
//					outBean.setIdFoglio(resultStore.getResultBean().getIdfoglioio());
					idFoglio = resultStore.getResultBean().getIdfoglioio();
				}
				// INSERIMENTO NELLA DMT_FOGLI_X_IMPORT - FINE
				
				// INSERIMENTO NELLA DMT_CONT_FOGLI_X_IMPORT - INIZIO
				DmpkBmanagerCaricacontfoglioximportBean lDmpkBmanagerCaricacontfoglioximportBean = getDmpkBmanagerCaricacontfoglioximportBean(pAurigaLoginBean, pGestioneInserimentoRichXRegMultiplaUscitaInBean, idFoglio);
				final CaricacontfoglioximportImpl caricacontfoglioximportImpl = new CaricacontfoglioximportImpl();
				caricacontfoglioximportImpl.setBean(lDmpkBmanagerCaricacontfoglioximportBean);
				
				// effettuo chiamata alla store
				session.doWork(new Work() {
					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						caricacontfoglioximportImpl.execute(paramConnection);
					}
				});
				
				StoreResultBean<DmpkBmanagerCaricacontfoglioximportBean> result2Store = new StoreResultBean<DmpkBmanagerCaricacontfoglioximportBean>();
				AnalyzeResult.analyze(lDmpkBmanagerCaricacontfoglioximportBean, result2Store);
				result2Store.setResultBean(lDmpkBmanagerCaricacontfoglioximportBean);
				
				if (result2Store.isInError()) {
					throw new StoreException(result2Store);
				}
				// INSERIMENTO NELLA DMT_CONT_FOGLI_X_IMPORT - FINE
				
				// INSERIMENTO NELLA BMT_JOBS - INIZIO
				DmpkBmanagerInsbatchBean insertBean = new DmpkBmanagerInsbatchBean();
				insertBean.setIddominioin(new BigDecimal(2));
				insertBean.setUseridin(pAurigaLoginBean.getUserid());
				insertBean.setTipojobin("OP_AURIGA_REG_MULTIPLA_USCITA");
				
				Locale locale = new Locale("it", "IT");
				final InsbatchImpl service = new InsbatchImpl();
				service.setBean(insertBean);
				session.doWork(new Work() {
	
					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						service.execute(paramConnection);
					}
				});
	
				StoreResultBean<DmpkBmanagerInsbatchBean> output = new StoreResultBean<DmpkBmanagerInsbatchBean>();
				AnalyzeResult.analyze(insertBean, output);
				output.setResultBean(insertBean);

				// INSERIMENTO NELLA BMT_JOBS - FINE
				if (output.isInError()) {
					
					mLogger.error("Errore insert in BMT_JOBS " + output.getStoreName() + " " + output.getErrorCode() + " " + output.getErrorContext() + " " + output.getDefaultMessage());
					throw new StoreException(output);	
					
				} else {
					mLogger.debug("Insert in BMT_JOBS completato");
					session.flush();
					
					// INSERIMENTO NELLA BMT_JOB_PARAMETERS - INIZIO
					CreaDocumentiRegMultiplaUscitaBean pCreaDocumentiRegMultiplaUscitaBean = pGestioneInserimentoRichXRegMultiplaUscitaInBean.getpCreaDocumentiRegMultiplaUscitaBean();
					
					// estrazione idJob da sequence
					DmpkBmanagerInsbatchBean resultBean = output.getResultBean();
					BigDecimal jobId = new BigDecimal(resultBean.getIdjobout());
	
					List<JobParameterBean> jobParamList = new ArrayList<JobParameterBean>();
	
					String jsonRequest = StringEscapeUtils.unescapeJava(new Gson().toJson(pCreaDocumentiRegMultiplaUscitaBean));
					String numRegistrazioniDaEffettuare = "" + pCreaDocumentiRegMultiplaUscitaBean.getListaDocRegMultiplaUscita().size(); // DA RIADATTARE CON LA LOGICA DI PIù MITTENTI PER SINGOLA REGISTRAZIONE
//					String numEmailDaInviare = "";
					int numEmailDaInviare = 0;
					Set<String> setEmailDestinatari = new HashSet<>();
					for (CreaDocWithFileBean doc : pCreaDocumentiRegMultiplaUscitaBean.getListaDocRegMultiplaUscita()) {
						List<DestinatariBean> destinatari = doc.getCreaDocumentoIn().getDestinatari();
						for (DestinatariBean destinatario : destinatari) {
							String indirizzoMail = destinatario.getIndirizzoMail();
							if (StringUtils.isNotBlank(indirizzoMail)) {
								setEmailDestinatari.add(indirizzoMail);
							}
						}
					}
					numEmailDaInviare = setEmailDestinatari.size();
					
					jobParamList = populateListJobParameter(pAurigaLoginBean, jobId, idFoglio, pCreaDocumentiRegMultiplaUscitaBean, numRegistrazioniDaEffettuare, "" + numEmailDaInviare);
					// parametro REQUEST_XML da inserire in BMT_JOB_PATRAMETERS
					if (StringUtils.isNotBlank(jsonRequest)) {
						JobParameterBean jobParameterBean = new JobParameterBean();
						jobParameterBean.setIdJob(jobId);
						jobParameterBean.setParameterId(BigDecimal.ZERO);
						jobParameterBean.setParameterType("VARCHAR2");
						jobParameterBean.setParameterDir("IN");
						jobParameterBean.setParameterSubtype("REQUEST_JSON");
						jobParameterBean.setParameterValue(jsonRequest);
						jobParamList.add(jobParameterBean);
					}
	
					mLogger.debug("Insert in BMT_JOB_PATRAMETERS - REQUEST_JSON: " + jsonRequest);
					
					// insert in BMT_JOB_PATRAMETERS
					DaoBmtJobParameters daoBmtJobParameters = new DaoBmtJobParameters();
					for (JobParameterBean jobParam : jobParamList) {
						daoBmtJobParameters.saveInSession(jobParam, session);
					}
					
					pCreaDocumentiRegMultiplaUscitaBean.setIdJob(String.valueOf(jobId.longValue()));			
					outBean.setIdJob(String.valueOf(jobId.longValue()));
				}
				// INSERIMENTO NELLA BMT_JOB_PARAMETERS - FINE
				session.flush();
				lTransaction.commit();
			} catch (StoreException e) {
				mLogger.error("Si è verificata la seguente eccezione nel metodo GestioneInserimentoRichXRegMultiplaUscita - creaFoglioXImport", e);
				BeanUtilsBean2.getInstance().copyProperties(outBean, ((StoreException) e).getError());

				if(StringUtils.isNotEmpty(uriFile)){
					try{
						DocumentStorage.delete(uriFile, pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
					}
					catch(Exception e1){						
						mLogger.error("Si è verificata un'eccezione nella cancellazione dal file dallo storage", e);
					}
				}
				return outBean;
			} catch (Exception e) {
				mLogger.error("Si è verificata la seguente eccezione nel metodo GestioneInserimentoRichXRegMultiplaUscita - creaFoglioXImport", e);
				outBean.setDefaultMessage(e.getMessage());

				if(StringUtils.isNotEmpty(uriFile)){
					try{
						DocumentStorage.delete(uriFile, pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
					}
					catch(Exception e1){						
						mLogger.error("Si è verificata un'eccezione nella cancellazione dal file dallo storage", e);
					}
				}
				return outBean;
			} finally {
				HibernateUtil.release(session);
			}
		}
		catch (Exception e) {
			if (e instanceof StoreException) {
				mLogger.error("Errore GestioneInserimentoRichXRegMultiplaUscita - creaFoglioXImport: " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(outBean, ((StoreException) e).getError());
				return outBean;
			} else {
				mLogger.error("Errore GestioneInserimentoRichXRegMultiplaUscita - creaFoglioXImport: " + e.getMessage(), e);
				outBean.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");
				return outBean;
			}
		}
		return outBean;					
	}
	
	private DmpkBmanagerIufoglioximportBean getDmpkBmanagerIufoglioximportBean(AurigaLoginBean pAurigaLoginBean, GestioneInserimentoRichXRegMultiplaUscitaInBean inputBean, String uriFile) throws Exception {
		// salvo il file nello storage di archiviazione
		// non elimino il temporaneo, viene gestito dai job
//		uriFile = DocumentStorage.store(inputBean.getXlsXImport().getFile(), pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
//		uriFile = inputBean.getXlsXImport().getUriFileExcel();
//		if(StringUtils.isEmpty(uriFile)){
//			throw new Exception("File non archiviato");
//		}
		DmpkBmanagerIufoglioximportBean lDmpkBmanagerIufoglioximportBean = new DmpkBmanagerIufoglioximportBean();
		lDmpkBmanagerIufoglioximportBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		lDmpkBmanagerIufoglioximportBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
		
		lDmpkBmanagerIufoglioximportBean.setFlgautocommitin(0); // blocco l'autocommit
		
		lDmpkBmanagerIufoglioximportBean.setTipocontenutoin(inputBean.getXlsXImport().getTipoContenuto());
		lDmpkBmanagerIufoglioximportBean.setStatoin(STATO_IN);
		
		// passo URI e tipo contenuto alla store
		lDmpkBmanagerIufoglioximportBean.setUriin(uriFile);
		lDmpkBmanagerIufoglioximportBean.setImprontain(inputBean.getXlsXImport().getInfo().getAllegatoRiferimento().getImpronta());
		lDmpkBmanagerIufoglioximportBean.setAlgoritmoimprontain(inputBean.getXlsXImport().getInfo().getAllegatoRiferimento().getAlgoritmo());
		lDmpkBmanagerIufoglioximportBean.setEncodingimprontain(inputBean.getXlsXImport().getInfo().getAllegatoRiferimento().getEncoding());
		return lDmpkBmanagerIufoglioximportBean;
	}
	
	private DmpkBmanagerCaricacontfoglioximportBean getDmpkBmanagerCaricacontfoglioximportBean(AurigaLoginBean pAurigaLoginBean, GestioneInserimentoRichXRegMultiplaUscitaInBean inputBean, String idFoglioIn) throws Exception {
		DmpkBmanagerCaricacontfoglioximportBean result = new DmpkBmanagerCaricacontfoglioximportBean();
		
		result.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		result.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
		result.setFlgautocommitin(0);
		
		result.setIdfoglioin(idFoglioIn);
		result.setXmlcontenutiin(marshal(inputBean.getXmlContenutiXImportContentFoglio()));
		result.setXmldettcolonnein(marshal(inputBean.getDettagliColonneXImportContentFoglio()));
		
		return result;
	}
	
	protected String marshal(Lista objectsList) throws JAXBException {

		StringWriter stringWriter = new StringWriter();
		BufferedWriter bufferedWriter = new BufferedWriter(stringWriter);
		SingletonJAXBContext.getInstance().createMarshaller().marshal(objectsList, bufferedWriter);
		return stringWriter.toString();
	}
	
	private List<JobParameterBean> populateListJobParameter(AurigaLoginBean pAurigaLoginBean, BigDecimal jobId, String idFoglio, CreaDocumentiRegMultiplaUscitaBean pCreaDocumentiRegMultiplaUscitaBean, String numRegistrazioniDaEffettuare, String numEmailDaInviare) {
		List<JobParameterBean> result = new LinkedList<JobParameterBean>();
		
		JobParameterBean jobParameterBean = new JobParameterBean();
		jobParameterBean.setIdJob(jobId);
		jobParameterBean.setParameterId(BigDecimal.valueOf(1));
		jobParameterBean.setParameterType("VARCHAR2");
		jobParameterBean.setParameterDir("OUT");
		jobParameterBean.setParameterSubtype("ID_FOGLIO");
		jobParameterBean.setParameterValue(idFoglio);
		result.add(jobParameterBean);
		
		JobParameterBean jobParameterBean2 = new JobParameterBean();
		jobParameterBean2.setIdJob(jobId);
		jobParameterBean2.setParameterId(BigDecimal.valueOf(2));
		jobParameterBean2.setParameterType("INTEGER");
		jobParameterBean2.setParameterDir("IN");
		jobParameterBean2.setParameterSubtype("ID_USER_LOGGED");
		jobParameterBean2.setParameterValue(pAurigaLoginBean.getIdUser().toString());
		result.add(jobParameterBean2);
		
		JobParameterBean jobParameterBean3 = new JobParameterBean();
		jobParameterBean3.setIdJob(jobId);
		jobParameterBean3.setParameterId(BigDecimal.valueOf(3));
		jobParameterBean3.setParameterType("INTEGER");
		jobParameterBean3.setParameterDir("IN");
		jobParameterBean3.setParameterSubtype("ID_USER_DELEGANTE");
		jobParameterBean3.setParameterValue(pAurigaLoginBean.getIdUserLavoro());
		result.add(jobParameterBean3);
		
		String idUo = pCreaDocumentiRegMultiplaUscitaBean.getListaDocRegMultiplaUscita().get(0).getCreaDocumentoIn().getTipoNumerazioni().get(0).getIdUo();
		String categoria = pCreaDocumentiRegMultiplaUscitaBean.getListaDocRegMultiplaUscita().get(0).getCreaDocumentoIn().getTipoNumerazioni().get(0).getCategoria();
		String sigla = pCreaDocumentiRegMultiplaUscitaBean.getListaDocRegMultiplaUscita().get(0).getCreaDocumentoIn().getTipoNumerazioni().get(0).getSigla();
		
		JobParameterBean jobParameterBean4 = new JobParameterBean();
		jobParameterBean4.setIdJob(jobId);
		jobParameterBean4.setParameterId(BigDecimal.valueOf(4));
		jobParameterBean4.setParameterType("INTEGER");
		jobParameterBean4.setParameterDir("IN");
		jobParameterBean4.setParameterSubtype("ID_UO");
		jobParameterBean4.setParameterValue(idUo);
		result.add(jobParameterBean4);
		
		JobParameterBean jobParameterBean5 = new JobParameterBean();
		jobParameterBean5.setIdJob(jobId);
		jobParameterBean5.setParameterId(BigDecimal.valueOf(5));
		jobParameterBean5.setParameterType("VARCHAR2");
		jobParameterBean5.setParameterDir("IN");
		jobParameterBean5.setParameterSubtype("CATEGORIA_REG");
		jobParameterBean5.setParameterValue(categoria);
		result.add(jobParameterBean5);
		
		JobParameterBean jobParameterBean6 = new JobParameterBean();
		jobParameterBean6.setIdJob(jobId);
		jobParameterBean6.setParameterId(BigDecimal.valueOf(6));
		jobParameterBean6.setParameterType("VARCHAR2");
		jobParameterBean6.setParameterDir("IN");
		jobParameterBean6.setParameterSubtype("SIGLA_REGISTRO_REG");
		jobParameterBean6.setParameterValue(StringUtils.isNotBlank(sigla) ? sigla : "");
		result.add(jobParameterBean6);
		
		JobParameterBean jobParameterBean7 = new JobParameterBean();
		jobParameterBean7.setIdJob(jobId);
		jobParameterBean7.setParameterId(BigDecimal.valueOf(7));
		jobParameterBean7.setParameterType("INTEGER");
		jobParameterBean7.setParameterDir("IN");
		jobParameterBean7.setParameterSubtype("NRO_REG_RICHIESTE");
		jobParameterBean7.setParameterValue(numRegistrazioniDaEffettuare);
		result.add(jobParameterBean7);
		
		JobParameterBean jobParameterBean8 = new JobParameterBean();
		jobParameterBean8.setIdJob(jobId);
		jobParameterBean8.setParameterId(BigDecimal.valueOf(8));
		jobParameterBean8.setParameterType("INTEGER");
		jobParameterBean8.setParameterDir("IN");
		jobParameterBean8.setParameterSubtype("NRO_REG_DA_INVIARE");
		jobParameterBean8.setParameterValue(numEmailDaInviare);
		result.add(jobParameterBean8);
		
		return result;
	}

}
