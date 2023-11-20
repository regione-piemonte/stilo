/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.springframework.beans.BeansException;

import com.google.gson.Gson;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerInsbatchBean;
import it.eng.auriga.database.store.dmpk_bmanager.store.impl.InsbatchImpl;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAddverdocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreDel_ud_doc_verBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpdverdocBean;
import it.eng.auriga.database.store.dmpk_core.store.Addverdoc;
import it.eng.auriga.database.store.dmpk_core.store.Del_ud_doc_ver;
import it.eng.auriga.database.store.dmpk_core.store.Updverdoc;
import it.eng.auriga.database.store.dmpk_core.store.impl.AdddocImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.AddverdocImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.Del_ud_doc_verImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.UpddocudImpl;
import it.eng.auriga.database.store.dmpk_core.store.impl.UpdverdocImpl;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityGetestremiregnumud_jBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityGetiddocprimarioallegatofromudBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityGetidudofdocBean;
import it.eng.auriga.database.store.dmpk_utility.store.Getestremiregnumud_j;
import it.eng.auriga.database.store.dmpk_utility.store.Getidudofdoc;
import it.eng.auriga.database.store.dmpk_utility.store.impl.Getestremiregnumud_jImpl;
import it.eng.auriga.database.store.dmpk_utility.store.impl.GetiddocprimarioallegatofromudImpl;
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
import it.eng.document.function.bean.AggiungiDocumentoInBean;
import it.eng.document.function.bean.AggiungiDocumentoOutBean;
import it.eng.document.function.bean.AllegatiBean;
import it.eng.document.function.bean.AllegatiOutBean;
import it.eng.document.function.bean.CreaDocumentiRegMultiplaUscitaBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.CreaModDocumentoOutBean;
import it.eng.document.function.bean.DelUdDocVerIn;
import it.eng.document.function.bean.DelUdDocVerOut;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FilePrimarioBean;
import it.eng.document.function.bean.FileStoreBean;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.document.function.bean.RegNumPrincipale;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.TipoNumerazioneBean;
import it.eng.document.function.bean.UrlVersione;
import it.eng.document.function.bean.ValueBean;
import it.eng.document.function.bean.VersionaDocumentiInTransactionInBean;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.document.sharepoint.SharePointUtil;
import it.eng.document.storage.DocumentStorage;
import it.eng.spring.utility.SpringAppContext;
import it.eng.storeutil.AnalyzeResult;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.xml.XmlUtilitySerializer;

@Service(name = "GestioneDocumenti")
public class GestioneDocumenti {

	private static Logger mLogger = Logger.getLogger(GestioneDocumenti.class);

	@Operation(name = "creaDocumento")
	public CreaModDocumentoOutBean creaDocumento(AurigaLoginBean pAurigaLoginBean, CreaModDocumentoInBean pCreaDocumentoInBean,
			FilePrimarioBean pFilePrimarioBean, AllegatiBean pAllegatiBean) throws JAXBException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		return manageCreaDocumento(pAurigaLoginBean, pCreaDocumentoInBean, pFilePrimarioBean, pAllegatiBean, false, false);
	}

	@Operation(name = "creaDocumentoWithVersInTransaction")
	public CreaModDocumentoOutBean creaDocumentoWithVersInTransaction(AurigaLoginBean pAurigaLoginBean, CreaModDocumentoInBean pCreaDocumentoInBean,
			FilePrimarioBean pFilePrimarioBean, AllegatiBean pAllegatiBean) throws JAXBException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		return manageCreaDocumento(pAurigaLoginBean, pCreaDocumentoInBean, pFilePrimarioBean, pAllegatiBean, true, false);
	}

	@Operation(name = "creaDocumentoWithVersInTransactionSkipNullValues")
	public CreaModDocumentoOutBean creaDocumentoWithVersInTransactionSkipNullValues(AurigaLoginBean pAurigaLoginBean, CreaModDocumentoInBean pCreaDocumentoInBean,
			FilePrimarioBean pFilePrimarioBean, AllegatiBean pAllegatiBean) throws JAXBException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		return manageCreaDocumento(pAurigaLoginBean, pCreaDocumentoInBean, pFilePrimarioBean, pAllegatiBean, true, true);
	}
	
	@Operation(name = "creaDocumentiRegistrazioneMultiplaUscita")
	public CreaDocumentiRegMultiplaUscitaBean creaDocumentiRegistrazioneMultiplaUscita(AurigaLoginBean pAurigaLoginBean, CreaDocumentiRegMultiplaUscitaBean pCreaDocumentiRegMultiplaUscitaBean) throws JAXBException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

//		for(int i = 0; i < pCreaDocumentiRegMultiplaUscitaBean.getListaDocRegMultiplaUscita().size(); i++) {
//			CreaDocWithFileBean lCreaDocWithFileBean = pCreaDocumentiRegMultiplaUscitaBean.getListaDocRegMultiplaUscita().get(i);
//			lCreaDocWithFileBean.setCreaDocumentoOut(manageCreaDocumento(pAurigaLoginBean, lCreaDocWithFileBean.getCreaDocumentoIn(), lCreaDocWithFileBean.getFilePrimario(), lCreaDocWithFileBean.getAllegati(), false, false));			
//		}
		
		Session session = null;
		try {
			
			mLogger.debug("Schema per connessione con Hibernate: " + pAurigaLoginBean.getSchema());
			
			SubjectBean subject = SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject);
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			
			mLogger.debug("Avvio sessione Hibernate");
			
			try {
				// dati da inserire in BMT_JOBS
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
				
				if (output.isInError()) {
					
					mLogger.error("Errore insert in BMT_JOBS " + output.getStoreName() + " " + output.getErrorCode() + " " + output.getErrorContext() + " " + output.getDefaultMessage());
					throw new StoreException(output);	
					
				} else {
					
					mLogger.debug("Insert in BMT_JOBS completato");
	
					session.flush();
	
					// estrazione idJob da sequence
					DmpkBmanagerInsbatchBean resultBean = output.getResultBean();
					BigDecimal jobId = new BigDecimal(resultBean.getIdjobout());
	
					List<JobParameterBean> jobParamList = new ArrayList<JobParameterBean>();
	
					String jsonRequest = StringEscapeUtils.unescapeJava(new Gson().toJson(pCreaDocumentiRegMultiplaUscitaBean));
							       
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
	
					lTransaction.commit();
				}		
				
			} finally {
				HibernateUtil.release(session);
			}			
			
		} catch (StoreException e) {
			mLogger.error("Si è verificata la seguente eccezione", e);
			BeanUtilsBean2.getInstance().copyProperties(pCreaDocumentiRegMultiplaUscitaBean, ((StoreException) e).getError());			
		} catch (Exception e) {
			mLogger.error("Si è verificata la seguente eccezione", e);
			pCreaDocumentiRegMultiplaUscitaBean.setDefaultMessage(e.getMessage());			
		}
		
		return pCreaDocumentiRegMultiplaUscitaBean;
	}
	
	public CreaModDocumentoOutBean manageCreaDocumento(AurigaLoginBean pAurigaLoginBean, CreaModDocumentoInBean pCreaDocumentoInBean,
			FilePrimarioBean pFilePrimarioBean, AllegatiBean pAllegatiBean, boolean isVersInTransaction, boolean skipNullValues) throws JAXBException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		DmpkCoreAdddocBean lAdddocBean = new DmpkCoreAdddocBean();
		lAdddocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		lAdddocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		lAdddocBean.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(pCreaDocumentoInBean, skipNullValues));

		lAdddocBean.setFlgautocommitin(0); // blocco l'autocommit

		// Preparo la risposta
		BigDecimal idUdResult = null;
		// Preparo i files
		List<RebuildedFile> versioni = new ArrayList<RebuildedFile>();

		CreaModDocumentoOutBean lCreaDocumentoOutBean = new CreaModDocumentoOutBean();
		try {
			SubjectBean subject = SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject);
			Session session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			try {
				idUdResult = manageAddDocs(pAurigaLoginBean, pFilePrimarioBean, pAllegatiBean, lAdddocBean, lXmlUtilitySerializer, versioni, session);
				/*
				if (pCreaDocumentoInBean.getTipoNumerazioni() != null) {
					TipoNumerazioneBean tipoNumerazione = pCreaDocumentoInBean.getTipoNumerazioni().get(0);

					// AGGIUNTA GESTIONE PROTOCOLLAZIONE MILANO
					if (tipoNumerazione.getSigla() != null && "PGWEB".equals(tipoNumerazione.getSigla())) {
						mLogger.error("PGWEB - RECUPERO I PARAMETRI NECESSARI PER LA PROTOCOLLAZIONE");
						PGWebUtil pgWebUtil = (PGWebUtil) SpringAppContext.getContext().getBean("pgWebUtil");
						ProtocolloBean protocolloBean = null;

						String idCls = null;
						if (pCreaDocumentoInBean.getClassifichefascicoli() != null && !pCreaDocumentoInBean.getClassifichefascicoli().isEmpty())
							if (pCreaDocumentoInBean.getClassifichefascicoli().get(0) != null)
								idCls = pCreaDocumentoInBean.getClassifichefascicoli().get(0).getProvCIClassif();

						// PROTOCOLLAZIONE IN USCITA
						if (pCreaDocumentoInBean.getFlgTipoProv() != null && "U".equals(pCreaDocumentoInBean.getFlgTipoProv().toString())) {

							String id_struttCom = null;
							if (pCreaDocumentoInBean.getMittenti() != null && !pCreaDocumentoInBean.getMittenti().isEmpty())
								if (pCreaDocumentoInBean.getMittenti().get(0) != null)
									id_struttCom = pCreaDocumentoInBean.getMittenti().get(0).getProvCIUo();

							// CREO LA PROTOCOLLAZIONE IN USCITA
							mLogger.error("PGWEB - CREO LA PROTOCOLLAZIONE IN USCITA");
							protocolloBean = CallServicesPGWeb.creaProtocolloDocInUscita(pgWebUtil.getWsUrlDocumento(), pgWebUtil.getUtenteProtocollazione(),
									pgWebUtil.getDominioProtocolloazione(), pgWebUtil.getPasswordProtocolloazione(), pCreaDocumentoInBean.getOggetto(), idCls,
									"C", id_struttCom);

							// MOVIMENTO LA PROTOCOLLAZIONE IN USCITA
							mLogger.error("PGWEB - SETTO IL DESTINATARIO");

							String destinatario = null;
							if (pCreaDocumentoInBean.getMittenti() != null && !pCreaDocumentoInBean.getMittenti().isEmpty())
								if (pCreaDocumentoInBean.getMittenti().get(0) != null)
									destinatario = pCreaDocumentoInBean.getMittenti().get(0).getDenominazioneCognome() + " "
											+ pCreaDocumentoInBean.getMittenti().get(0).getNome();

							mLogger.error("PGWEB - CHIAMO IL SERVIZIO DI MOVIMENTAZIONE");
							CallServicesPGWeb.movimentoProtocollo(pgWebUtil.getWsUrlMovimento(), protocolloBean.getIdDocumento(), pgWebUtil.getIdStruttCom(),
									pgWebUtil.getUtenteProtocollazione(), pgWebUtil.getDominioProtocolloazione(), pgWebUtil.getPasswordProtocolloazione(),
									destinatario, "S");
						}
						// PROTOCOLLAZIONE IN ENTRATA
						if (pCreaDocumentoInBean.getFlgTipoProv() != null && "E".equals(pCreaDocumentoInBean.getFlgTipoProv().toString())) {

							String denomCogn = null;
							String nome = null;
							if (pCreaDocumentoInBean.getMittenti() != null && !pCreaDocumentoInBean.getMittenti().isEmpty())
								if (pCreaDocumentoInBean.getMittenti().get(0) != null) {
									denomCogn = pCreaDocumentoInBean.getMittenti().get(0).getDenominazioneCognome();
									nome = pCreaDocumentoInBean.getMittenti().get(0).getNome();
								}

							// CREO LA PROTOCOLLAZIONE IN ENTRATA
							mLogger.error("PGWEB - CREO LA PROTOCOLLAZIONE IN ENTRATA");
							protocolloBean = CallServicesPGWeb.creaProtocolloDocInEntrata(pgWebUtil.getWsUrlDocumento(), pgWebUtil.getUtenteProtocollazione(),
									pgWebUtil.getDominioProtocolloazione(), pgWebUtil.getPasswordProtocolloazione(), pCreaDocumentoInBean.getOggetto(), idCls,
									"P", denomCogn, nome, null, null, null, null, null);// "piva", "cf", "pg_mittNum", "pg_mittAnno", "pg_mittData");
						}

						// ProtocolloRicevuto
						ProtocolloRicevuto protocolloRicevuto = new ProtocolloRicevuto();
						protocolloRicevuto.setNumero(protocolloBean.getNumProt());
						protocolloRicevuto.setAnno(protocolloBean.getAnnoProt());
						protocolloRicevuto
								.setData(protocolloBean.getDataProt() != null ? new SimpleDateFormat("dd/MM/yyyy").parse(protocolloBean.getDataProt()) : null);
						pCreaDocumentoInBean.setProtocolloRicevuto(protocolloRicevuto);

						// DmpkCoreUpddocudBean
						DmpkCoreUpddocudBean lUpddocudBean = new DmpkCoreUpddocudBean();
						lUpddocudBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
						lUpddocudBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
						lUpddocudBean.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXmlCompleta(pCreaDocumentoInBean));
						lUpddocudBean.setFlgtipotargetin("U");
						lUpddocudBean.setIduddocin(idUdResult);

						final UpddocudImpl store = new UpddocudImpl();
						store.setBean(lUpddocudBean);

						LoginService lLoginService = new LoginService();
						lLoginService.login(pAurigaLoginBean);
						session.doWork(new Work() {

							@Override
							public void execute(Connection paramConnection) throws SQLException {
								paramConnection.setAutoCommit(false);
								store.execute(paramConnection);
							}
						});

						StoreResultBean<DmpkCoreUpddocudBean> result = new StoreResultBean<DmpkCoreUpddocudBean>();
						AnalyzeResult.analyze(lUpddocudBean, result);
						result.setResultBean(lUpddocudBean);

						if (result.isInError()) {
							throw new StoreException(result);
						}
					}
					// FINE AGGIUNTA GESTIONE PROTOCOLLAZIONE MILANO
				}
				*/
				if (isVersInTransaction) {
					lCreaDocumentoOutBean.setIdUd(idUdResult);
					lCreaDocumentoOutBean.setIdDoc(lAdddocBean.getIddocout());
					lCreaDocumentoOutBean.setRowidDoc(lAdddocBean.getErrcontextout());

					// Parte di versionamento
					Map<String, String> fileErrors = aggiungiFilesInTransaction(pAurigaLoginBean, versioni, session);
					lCreaDocumentoOutBean.setFileInErrors(fileErrors);

					recuperaEstremiInTransaction(pAurigaLoginBean, pCreaDocumentoInBean, lCreaDocumentoOutBean, session);

					if (SharePointUtil.isIntegratoConSharePoint(pAurigaLoginBean)) {
						SharePointUtil shUtil = (SharePointUtil) SpringAppContext.getContext().getBean("sharePointUtil");
						shUtil.aggiornaMetadati(pAurigaLoginBean, lCreaDocumentoOutBean);
					}
				}
				session.flush();
				lTransaction.commit();
			} catch (StoreException e) {
				mLogger.error("Si è verificata la seguente eccezione", e);
				BeanUtilsBean2.getInstance().copyProperties(lCreaDocumentoOutBean, ((StoreException) e).getError());
				return lCreaDocumentoOutBean;
			} catch (Exception e) {
				mLogger.error("Si è verificata la seguente eccezione", e);
				lCreaDocumentoOutBean.setDefaultMessage(e.getMessage());
				return lCreaDocumentoOutBean;
			} finally {
				HibernateUtil.release(session);
			}

			if (!isVersInTransaction) {
				lCreaDocumentoOutBean.setIdUd(idUdResult);
				lCreaDocumentoOutBean.setIdDoc(lAdddocBean.getIddocout());
				lCreaDocumentoOutBean.setRowidDoc(lAdddocBean.getErrcontextout());

				// Parte di versionamento
				Map<String, String> fileErrors = aggiungiFiles(pAurigaLoginBean, versioni);
				lCreaDocumentoOutBean.setFileInErrors(fileErrors);

				recuperaEstremi(pAurigaLoginBean, pCreaDocumentoInBean, lCreaDocumentoOutBean);

				if (SharePointUtil.isIntegratoConSharePoint(pAurigaLoginBean)) {
					SharePointUtil shUtil = (SharePointUtil) SpringAppContext.getContext().getBean("sharePointUtil");
					shUtil.aggiornaMetadati(pAurigaLoginBean, lCreaDocumentoOutBean);
				}
			}

		} catch (Exception e) {
			if (e instanceof StoreException) {
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lCreaDocumentoOutBean, ((StoreException) e).getError());
				return lCreaDocumentoOutBean;
			} else {
				mLogger.error("Errore " + e.getMessage(), e);
				lCreaDocumentoOutBean.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");
				return lCreaDocumentoOutBean;
			}
		}

		return lCreaDocumentoOutBean;
	}

	@Operation(name = "modificaDocumento")
	public CreaModDocumentoOutBean modificaDocumento(AurigaLoginBean pAurigaLoginBean, BigDecimal pIdUd, BigDecimal pIdDocPrimario,
			CreaModDocumentoInBean pModificaDocumentoInBean, FilePrimarioBean pFilePrimarioBean, AllegatiBean pAllegatiBean) throws JAXBException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		CreaModDocumentoOutBean lModificaDocumentoOutBean = new CreaModDocumentoOutBean();

		BigDecimal idUd = (pIdUd != null) ? new BigDecimal(pIdUd.longValue() + "") : null;
		BigDecimal idDocPrimario = (pIdDocPrimario != null) ? new BigDecimal(pIdDocPrimario.longValue() + "") : null;

		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(idUd);
		RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean;

		DocumentoXmlOutBean lDocumentoXmlOutBean = null;
		try {
			lRecuperaDocumentoOutBean = lRecuperoDocumenti.loadDocumento(pAurigaLoginBean, lRecuperaDocumentoInBean);
			lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
		} catch (Exception e) {
			mLogger.error("Errore " + e.getMessage(), e);
			lModificaDocumentoOutBean.setDefaultMessage("Il documento da aggiornare e' inesistente");
			return lModificaDocumentoOutBean;
		}

		DmpkCoreUpddocudBean lUpddocudBean = new DmpkCoreUpddocudBean();
		lUpddocudBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		lUpddocudBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		try {
			if (SharePointUtil.isIntegratoConSharePoint(pAurigaLoginBean)) {
				if (lDocumentoXmlOutBean.getFilePrimario() != null && lDocumentoXmlOutBean.getFilePrimario().getUri() != null) {
					SharePointUtil shUtil = (SharePointUtil) SpringAppContext.getContext().getBean("sharePointUtil");
					List<UrlVersione> uriList = shUtil.getUriPrecentiVersioni(pAurigaLoginBean, lDocumentoXmlOutBean.getFilePrimario().getUri());
					pModificaDocumentoInBean.setUriVerPrecSuSharePoint_Ver(uriList);
				}
			}
		} catch (BeansException e1) {
			mLogger.warn(e1);
		} catch (Exception e1) {
			mLogger.warn(e1);
		}

		lUpddocudBean.setAttributiuddocxmlin(settaBindXml(pModificaDocumentoInBean));

		lUpddocudBean.setFlgtipotargetin("D");
		lUpddocudBean.setIduddocin(idDocPrimario);
		lUpddocudBean.setFlgautocommitin(0); // blocco l'autocommit

		// Preparo i files
		List<RebuildedFile> versioni = new ArrayList<RebuildedFile>();
		List<RebuildedFile> versioniDaRimuovere = new ArrayList<RebuildedFile>();
		List<RebuildedFile> allegatiDaRimuovere = new ArrayList<RebuildedFile>();

		try {
			SubjectBean subject = SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject);
			Session session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			try {
				manageAddUpdDocs(pAurigaLoginBean, pFilePrimarioBean, pAllegatiBean, lUpddocudBean, lXmlUtilitySerializer, idUd, idDocPrimario,
						lDocumentoXmlOutBean, versioni, versioniDaRimuovere, allegatiDaRimuovere, session);
				lTransaction.commit();
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				if (e instanceof StoreException) {
					BeanUtilsBean2.getInstance().copyProperties(lModificaDocumentoOutBean, ((StoreException) e).getError());
					return lModificaDocumentoOutBean;
				} else {
					lModificaDocumentoOutBean.setDefaultMessage(e.getMessage());
					return lModificaDocumentoOutBean;
				}
			} finally {
				HibernateUtil.release(session);
			}
			lModificaDocumentoOutBean.setIdUd(idUd);
			// Parte di versionamento
			Map<String, String> fileErrors = new HashMap<String, String>();
			fileErrors.putAll(aggiungiFiles(pAurigaLoginBean, versioni));
			fileErrors.putAll(rimuoviFiles(pAurigaLoginBean, versioniDaRimuovere, allegatiDaRimuovere));
			lModificaDocumentoOutBean.setFileInErrors(fileErrors);
			recuperaEstremi(pAurigaLoginBean, pModificaDocumentoInBean, lModificaDocumentoOutBean);

			if (SharePointUtil.isIntegratoConSharePoint(pAurigaLoginBean)) {
				SharePointUtil shUtil = (SharePointUtil) SpringAppContext.getContext().getBean("sharePointUtil");
				shUtil.aggiornaMetadati(pAurigaLoginBean, lModificaDocumentoOutBean);
			}

		} catch (Exception e) {
			if (e instanceof StoreException) {
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lModificaDocumentoOutBean, ((StoreException) e).getError());
				return lModificaDocumentoOutBean;
			} else {
				mLogger.error("Errore " + e.getMessage(), e);
				lModificaDocumentoOutBean.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");
				return lModificaDocumentoOutBean;
			}
		}

		return lModificaDocumentoOutBean;
	}

	
	@Operation(name = "modificaDocumentoSkipNullValues")
	public CreaModDocumentoOutBean modificaDocumentoSkipNullValues(AurigaLoginBean pAurigaLoginBean, BigDecimal pIdUd, BigDecimal pIdDocPrimario,
			CreaModDocumentoInBean pModificaDocumentoInBean, FilePrimarioBean pFilePrimarioBean, AllegatiBean pAllegatiBean) throws JAXBException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		CreaModDocumentoOutBean lModificaDocumentoOutBean = new CreaModDocumentoOutBean();

		BigDecimal idUd = (pIdUd != null) ? new BigDecimal(pIdUd.longValue() + "") : null;
		BigDecimal idDocPrimario = (pIdDocPrimario != null) ? new BigDecimal(pIdDocPrimario.longValue() + "") : null;

		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(idUd);
		RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean;

		DocumentoXmlOutBean lDocumentoXmlOutBean = null;
		try {
			lRecuperaDocumentoOutBean = lRecuperoDocumenti.loadDocumento(pAurigaLoginBean, lRecuperaDocumentoInBean);
			lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
		} catch (Exception e) {
			mLogger.error("Errore " + e.getMessage(), e);
			lModificaDocumentoOutBean.setDefaultMessage("Il documento da aggiornare e' inesistente");
			return lModificaDocumentoOutBean;
		}

		DmpkCoreUpddocudBean lUpddocudBean = new DmpkCoreUpddocudBean();
		lUpddocudBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		lUpddocudBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		try {
			if (SharePointUtil.isIntegratoConSharePoint(pAurigaLoginBean)) {
				if (lDocumentoXmlOutBean.getFilePrimario() != null && lDocumentoXmlOutBean.getFilePrimario().getUri() != null) {
					SharePointUtil shUtil = (SharePointUtil) SpringAppContext.getContext().getBean("sharePointUtil");
					List<UrlVersione> uriList = shUtil.getUriPrecentiVersioni(pAurigaLoginBean, lDocumentoXmlOutBean.getFilePrimario().getUri());
					pModificaDocumentoInBean.setUriVerPrecSuSharePoint_Ver(uriList);
				}
			}
		} catch (BeansException e1) {
			mLogger.warn(e1);
		} catch (Exception e1) {
			mLogger.warn(e1);
		}

		// escludo i  valori null
		lUpddocudBean.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(pModificaDocumentoInBean,true));
		
		lUpddocudBean.setFlgtipotargetin("D");
		lUpddocudBean.setIduddocin(idDocPrimario);
		lUpddocudBean.setFlgautocommitin(0); // blocco l'autocommit

		// Preparo i files
		List<RebuildedFile> versioni = new ArrayList<RebuildedFile>();
		List<RebuildedFile> versioniDaRimuovere = new ArrayList<RebuildedFile>();
		List<RebuildedFile> allegatiDaRimuovere = new ArrayList<RebuildedFile>();

		try {
			SubjectBean subject = SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject);
			Session session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			try {
				manageAddUpdDocs(pAurigaLoginBean, pFilePrimarioBean, pAllegatiBean, lUpddocudBean, lXmlUtilitySerializer, idUd, idDocPrimario,
						lDocumentoXmlOutBean, versioni, versioniDaRimuovere, allegatiDaRimuovere, session);
				lTransaction.commit();
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				if (e instanceof StoreException) {
					BeanUtilsBean2.getInstance().copyProperties(lModificaDocumentoOutBean, ((StoreException) e).getError());
					return lModificaDocumentoOutBean;
				} else {
					lModificaDocumentoOutBean.setDefaultMessage(e.getMessage());
					return lModificaDocumentoOutBean;
				}
			} finally {
				HibernateUtil.release(session);
			}
			lModificaDocumentoOutBean.setIdUd(idUd);
			// Parte di versionamento
			Map<String, String> fileErrors = new HashMap<String, String>();
			fileErrors.putAll(aggiungiFiles(pAurigaLoginBean, versioni));
			fileErrors.putAll(rimuoviFiles(pAurigaLoginBean, versioniDaRimuovere, allegatiDaRimuovere));
			lModificaDocumentoOutBean.setFileInErrors(fileErrors);
			recuperaEstremi(pAurigaLoginBean, pModificaDocumentoInBean, lModificaDocumentoOutBean);

			if (SharePointUtil.isIntegratoConSharePoint(pAurigaLoginBean)) {
				SharePointUtil shUtil = (SharePointUtil) SpringAppContext.getContext().getBean("sharePointUtil");
				shUtil.aggiornaMetadati(pAurigaLoginBean, lModificaDocumentoOutBean);
			}

		} catch (Exception e) {
			if (e instanceof StoreException) {
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lModificaDocumentoOutBean, ((StoreException) e).getError());
				return lModificaDocumentoOutBean;
			} else {
				mLogger.error("Errore " + e.getMessage(), e);
				lModificaDocumentoOutBean.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");
				return lModificaDocumentoOutBean;
			}
		}

		return lModificaDocumentoOutBean;
	}
	
	@Operation(name = "salvaDocumentiUd")
	public CreaModDocumentoOutBean salvaDocumentiUd(AurigaLoginBean pAurigaLoginBean, BigDecimal pIdUd, BigDecimal pIdDocPrimario, FilePrimarioBean pFilePrimarioBean, AllegatiBean pAllegatiBean) throws JAXBException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		CreaModDocumentoOutBean lModificaDocumentoOutBean = new CreaModDocumentoOutBean();
		
		BigDecimal idUd = (pIdUd != null) ? new BigDecimal(pIdUd.longValue() + "") : null;
		BigDecimal idDocPrimario = (pIdDocPrimario != null) ? new BigDecimal(pIdDocPrimario.longValue() + "") : null;

		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(idUd);
		RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean;

		DocumentoXmlOutBean lDocumentoXmlOutBean = null;
		try {
			lRecuperaDocumentoOutBean = lRecuperoDocumenti.loadDocumento(pAurigaLoginBean, lRecuperaDocumentoInBean);
			lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
		} catch (Exception e) {
			mLogger.error("Errore " + e.getMessage(), e);
			lModificaDocumentoOutBean.setDefaultMessage("Il documento da aggiornare e' inesistente");
			return lModificaDocumentoOutBean;
		}
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		
		// Preparo i files
		List<RebuildedFile> versioni = new ArrayList<RebuildedFile>();
		List<RebuildedFile> versioniDaRimuovere = new ArrayList<RebuildedFile>();
		List<RebuildedFile> allegatiDaRimuovere = new ArrayList<RebuildedFile>();
		
		try {
			SubjectBean subject = SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject);
			Session session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			try {
				manageAddUpdDocs(pAurigaLoginBean, pFilePrimarioBean, pAllegatiBean, null, lXmlUtilitySerializer, idUd, idDocPrimario,
						lDocumentoXmlOutBean, versioni, versioniDaRimuovere, allegatiDaRimuovere, session);
				lTransaction.commit();
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				if (e instanceof StoreException) {
					BeanUtilsBean2.getInstance().copyProperties(lModificaDocumentoOutBean, ((StoreException) e).getError());
					return lModificaDocumentoOutBean;
				} else {
					lModificaDocumentoOutBean.setDefaultMessage(e.getMessage());
					return lModificaDocumentoOutBean;
				}
			} finally {
				HibernateUtil.release(session);
			}
			lModificaDocumentoOutBean.setIdUd(idUd);
			// Parte di versionamento
			Map<String, String> fileErrors = new HashMap<String, String>();
			fileErrors.putAll(aggiungiFiles(pAurigaLoginBean, versioni));
			fileErrors.putAll(rimuoviFiles(pAurigaLoginBean, versioniDaRimuovere, allegatiDaRimuovere));
			lModificaDocumentoOutBean.setFileInErrors(fileErrors);
//					recuperaEstremi(pAurigaLoginBean, pModificaDocumentoInBean, lModificaDocumentoOutBean);

			if (SharePointUtil.isIntegratoConSharePoint(pAurigaLoginBean)) {
				SharePointUtil shUtil = (SharePointUtil) SpringAppContext.getContext().getBean("sharePointUtil");
				shUtil.aggiornaMetadati(pAurigaLoginBean, lModificaDocumentoOutBean);
			}

		} catch (Exception e) {
			if (e instanceof StoreException) {
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lModificaDocumentoOutBean, ((StoreException) e).getError());
				return lModificaDocumentoOutBean;
			} else {
				mLogger.error("Errore " + e.getMessage(), e);
				lModificaDocumentoOutBean.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");
				return lModificaDocumentoOutBean;
			}
		}

		return lModificaDocumentoOutBean;
	}
	
	public String settaBindXml(CreaModDocumentoInBean pModificaDocumentoInBean) {

		String lStringXml = null;
		try {
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			lStringXml = lXmlUtilitySerializer.bindXmlCompleta(pModificaDocumentoInBean);
			mLogger.debug("attributiBindXml " + lStringXml);
		} catch (Exception e) {
			mLogger.warn(e);
		}

		return lStringXml;
	}

	public BigDecimal manageAddDocs(AurigaLoginBean pAurigaLoginBean, FilePrimarioBean pFilePrimarioBean, AllegatiBean pAllegatiBean,
			DmpkCoreAdddocBean lAdddocBean, XmlUtilitySerializer lXmlUtilitySerializer, List<RebuildedFile> versioni, Session session) throws Exception {

		BigDecimal idUd = null;
		BigDecimal idDoc = null;		
		final AdddocImpl store = new AdddocImpl();
		store.setBean(lAdddocBean);
		LoginService lLoginService = new LoginService();
		lLoginService.login(pAurigaLoginBean);
		session.doWork(new Work() {

			@Override
			public void execute(Connection paramConnection) throws SQLException {
				paramConnection.setAutoCommit(false);
				store.execute(paramConnection);
			}
		});
		StoreResultBean<DmpkCoreAdddocBean> result = new StoreResultBean<DmpkCoreAdddocBean>();
		AnalyzeResult.analyze(lAdddocBean, result);
		result.setResultBean(lAdddocBean);
		if (result.isInError()) {
			throw new StoreException(result);
		} else {			
			mLogger.debug("idUd vale " + result.getResultBean().getIdudout());
			mLogger.debug("idDoc vale " + result.getResultBean().getIddocout());
			idUd = result.getResultBean().getIdudout();
			idDoc = result.getResultBean().getIddocout();
			if (pFilePrimarioBean != null) {			
				if (pFilePrimarioBean.getFile() != null) {
					RebuildedFile lRebuildedFile = new RebuildedFile();
					lRebuildedFile.setIdDocumento(result.getResultBean().getIddocout());
					lRebuildedFile.setFile(pFilePrimarioBean.getFile());
					lRebuildedFile.setInfo(pFilePrimarioBean.getInfo());
					versioni.add(lRebuildedFile);
				}	
				// Vers. con omissis
				if (pFilePrimarioBean.getFileOmissis() != null) {					
					DmpkCoreAdddocBean lAdddocBeanOmissis = new DmpkCoreAdddocBean();
					lAdddocBeanOmissis.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
					lAdddocBeanOmissis.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));				
					// La versione omissis del primario viene salvata come fosse un allegato
					AllegatoVersConOmissisStoreBean attributiOmissis = new AllegatoVersConOmissisStoreBean();
					attributiOmissis.setIdUd(idUd);
					attributiOmissis.setFlgVersConOmissis("1");
					attributiOmissis.setIdDocVersIntegrale(idDoc != null ? String.valueOf(idDoc.longValue()) : null);						
					lAdddocBeanOmissis.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(attributiOmissis));
					final AdddocImpl storeOmissis = new AdddocImpl();
					storeOmissis.setBean(lAdddocBeanOmissis);
					lLoginService.login(pAurigaLoginBean);
					session.doWork(new Work() {

						@Override
						public void execute(Connection paramConnection) throws SQLException {
							paramConnection.setAutoCommit(false);
							storeOmissis.execute(paramConnection);
						}
					});
					StoreResultBean<DmpkCoreAdddocBean> resultOmissis = new StoreResultBean<DmpkCoreAdddocBean>();
					AnalyzeResult.analyze(lAdddocBeanOmissis, resultOmissis);
					resultOmissis.setResultBean(lAdddocBeanOmissis);
					if (resultOmissis.isInError()) {
						throw new StoreException(resultOmissis);
					} else {						
						mLogger.debug("idDocOmissis vale " + resultOmissis.getResultBean().getIddocout());
						RebuildedFile lRebuildedFileOmissis = new RebuildedFile();
						lRebuildedFileOmissis.setIdDocumento(resultOmissis.getResultBean().getIddocout());
						lRebuildedFileOmissis.setFile(pFilePrimarioBean.getFileOmissis());
						lRebuildedFileOmissis.setInfo(pFilePrimarioBean.getInfoOmissis());
						versioni.add(lRebuildedFileOmissis);					
					}										
				}					
			}
			if (pAllegatiBean != null) {
				List<ValueBean> listaIdDocAllegatiOrdinati = new ArrayList<>();
				HashMap<Integer, BigDecimal> mappaIdDocAllegato = new HashMap<Integer, BigDecimal>();
				int count = 1;
				Iterator<File> nextFile = null;
				if (pAllegatiBean.getFileAllegati() != null)
					nextFile = pAllegatiBean.getFileAllegati().iterator();
				if (pAllegatiBean.getIsNull() != null) {
					for (int i = 0; i < pAllegatiBean.getIsNull().size(); i++) {
						AllegatoStoreBean lAllegatoStoreBean = new AllegatoStoreBean();
						lAllegatoStoreBean.setIdUd(idUd);
						lAllegatoStoreBean.setDescrizione(pAllegatiBean.getDescrizione().get(i));
						lAllegatoStoreBean.setIdDocType(pAllegatiBean.getDocType().get(i));
						if (pAllegatiBean.getIdUdFrom() != null){
							lAllegatoStoreBean.setIdUdFrom(pAllegatiBean.getIdUdFrom().get(i));
						}
						if (pAllegatiBean.getIdUdAllegato() != null){
							lAllegatoStoreBean.setIdUdAllegato(pAllegatiBean.getIdUdAllegato().get(i));
						}
						if (pAllegatiBean.getFlgProvEsterna() != null) {
							Boolean flgProvEsterna = pAllegatiBean.getFlgProvEsterna().get(i);
							lAllegatoStoreBean.setFlgProvEsterna(flgProvEsterna != null && flgProvEsterna ? "1" : null);
						}						
						if (pAllegatiBean.getFlgParteDispositivo() != null) {
							Boolean flgParteDispositivo = pAllegatiBean.getFlgParteDispositivo().get(i);
							lAllegatoStoreBean.setFlgParteDispositivo(flgParteDispositivo != null && flgParteDispositivo ? "1" : null);
						}
						if (pAllegatiBean.getIdTask() != null) {
							lAllegatoStoreBean.setIdTask(pAllegatiBean.getIdTask().get(i));
						}
						if (pAllegatiBean.getFlgNoPubbl() != null) {
							Boolean flgNoPubbl = pAllegatiBean.getFlgNoPubbl().get(i);
							lAllegatoStoreBean.setFlgNoPubbl(flgNoPubbl != null && flgNoPubbl ? "1" : null);
						}
						if (pAllegatiBean.getFlgPubblicaSeparato() != null) {
							Boolean flgPubblicaSeparato = pAllegatiBean.getFlgPubblicaSeparato().get(i);
							lAllegatoStoreBean.setFlgPubblicaSeparato(flgPubblicaSeparato != null && flgPubblicaSeparato ? "1" : null);
						}
						if (pAllegatiBean.getNroPagFileUnione() != null) {
							lAllegatoStoreBean.setNroPagFileUnione(pAllegatiBean.getNroPagFileUnione().get(i));
						}
						if (pAllegatiBean.getNroPagFileUnioneOmissis() != null) {					
							lAllegatoStoreBean.setNroPagFileUnioneOmissis(pAllegatiBean.getNroPagFileUnioneOmissis().get(i));
						}
						if (pAllegatiBean.getFlgDatiProtettiTipo1() != null) {
							Boolean flgDatiProtettiTipo1 = pAllegatiBean.getFlgDatiProtettiTipo1().get(i);
							lAllegatoStoreBean.setFlgDatiProtettiTipo1(flgDatiProtettiTipo1 != null && flgDatiProtettiTipo1 ? "1" : null);
						}
						if (pAllegatiBean.getFlgDatiProtettiTipo2() != null) {
							Boolean flgDatiProtettiTipo2 = pAllegatiBean.getFlgDatiProtettiTipo2().get(i);
							lAllegatoStoreBean.setFlgDatiProtettiTipo2(flgDatiProtettiTipo2 != null && flgDatiProtettiTipo2 ? "1" : null);
						}
						if (pAllegatiBean.getFlgDatiProtettiTipo3() != null) {
							Boolean flgDatiProtettiTipo3 = pAllegatiBean.getFlgDatiProtettiTipo3().get(i);
							lAllegatoStoreBean.setFlgDatiProtettiTipo3(flgDatiProtettiTipo3 != null && flgDatiProtettiTipo3 ? "1" : null);
						}
						if (pAllegatiBean.getFlgDatiProtettiTipo4() != null) {
							Boolean flgDatiProtettiTipo4 = pAllegatiBean.getFlgDatiProtettiTipo4().get(i);
							lAllegatoStoreBean.setFlgDatiProtettiTipo4(flgDatiProtettiTipo4 != null && flgDatiProtettiTipo4 ? "1" : null);
						}
						if (pAllegatiBean.getFlgDatiSensibili() != null) {
							Boolean flgDatiSensibili = pAllegatiBean.getFlgDatiSensibili().get(i);
							lAllegatoStoreBean.setFlgDatiSensibili(flgDatiSensibili != null && flgDatiSensibili ? "1" : null);
						}	
						if (pAllegatiBean.getFlgOriginaleCartaceo() != null) {
							Boolean flgOriginaleCartaceo = pAllegatiBean.getFlgOriginaleCartaceo().get(i);
							lAllegatoStoreBean.setFlgOriginaleCartaceo(flgOriginaleCartaceo != null && flgOriginaleCartaceo ? "1" : null);
						}
						if (pAllegatiBean.getFlgCopiaSostitutiva() != null) {
							Boolean flgCopiaSostitutiva = pAllegatiBean.getFlgCopiaSostitutiva().get(i);
							lAllegatoStoreBean.setFlgCopiaSostitutiva(flgCopiaSostitutiva != null && flgCopiaSostitutiva ? "1" : null);
						}
						if (pAllegatiBean.getFlgGenAutoDaModello() != null) {
							Boolean flgGenAutoDaModello = pAllegatiBean.getFlgGenAutoDaModello().get(i);
							lAllegatoStoreBean.setFlgGenAutoDaModello(flgGenAutoDaModello != null && flgGenAutoDaModello ? "1" : null);
						}
						String attributi = lXmlUtilitySerializer.bindXml(lAllegatoStoreBean);
						DmpkCoreAdddocBean lAdddocBeanAllegato = new DmpkCoreAdddocBean();
						lAdddocBeanAllegato.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
						lAdddocBeanAllegato.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
						lAdddocBeanAllegato.setAttributiuddocxmlin(attributi);
						final AdddocImpl storeAllegato = new AdddocImpl();
						lAdddocBeanAllegato.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
						storeAllegato.setBean(lAdddocBeanAllegato);
						lLoginService.login(pAurigaLoginBean);
						session.doWork(new Work() {
	
							@Override
							public void execute(Connection paramConnection) throws SQLException {
								paramConnection.setAutoCommit(false);
								storeAllegato.execute(paramConnection);
							}
						});
						StoreResultBean<DmpkCoreAdddocBean> resultAllegato = new StoreResultBean<DmpkCoreAdddocBean>();
						AnalyzeResult.analyze(lAdddocBeanAllegato, resultAllegato);
						resultAllegato.setResultBean(lAdddocBeanAllegato);
						if (resultAllegato.isInError()) {
							throw new StoreException(resultAllegato);
						} else {
							mLogger.debug("idDocAllegato vale " + resultAllegato.getResultBean().getIddocout());
							mappaIdDocAllegato.put(new Integer(count), resultAllegato.getResultBean().getIddocout());
							if (!pAllegatiBean.getIsNull().get(i)) {
								RebuildedFile lRebuildedFile = new RebuildedFile();
								lRebuildedFile.setFile(nextFile.next());
								lRebuildedFile.setInfo(pAllegatiBean.getInfo().get(i));
								lRebuildedFile.setIdDocumento(resultAllegato.getResultBean().getIddocout());
								lRebuildedFile.setPosizione(count);
								versioni.add(lRebuildedFile);
							}
						}
						count++;
					}
				}
				
				for (BigDecimal idBigDecimal : mappaIdDocAllegato.values()) {
					ValueBean valueBean = new ValueBean();
					valueBean.setValue(String.valueOf(idBigDecimal.longValue()));
					listaIdDocAllegatiOrdinati.add(valueBean);
				}
				
				// Vers. con omissis
				int countOmissis = 1;
				Iterator<File> nextFileOmissis = null;
				if (pAllegatiBean.getFileAllegatiOmissis() != null)
					nextFileOmissis = pAllegatiBean.getFileAllegatiOmissis().iterator();	
				if (pAllegatiBean.getIsNullOmissis() != null) {
					for (int i = 0; i < pAllegatiBean.getIsNullOmissis().size(); i++) {	
						if (!pAllegatiBean.getIsNullOmissis().get(i)) {						
							BigDecimal idDocAllegato = mappaIdDocAllegato.get(new Integer(countOmissis));
							AllegatoVersConOmissisStoreBean lAllegatoStoreBeanOmissis = new AllegatoVersConOmissisStoreBean();
							lAllegatoStoreBeanOmissis.setIdUd(idUd);						
							lAllegatoStoreBeanOmissis.setFlgVersConOmissis("1");
							lAllegatoStoreBeanOmissis.setIdDocVersIntegrale(idDocAllegato != null ? String.valueOf(idDocAllegato.longValue()) : null);					
							String attributiAllegatoOmissis = lXmlUtilitySerializer.bindXml(lAllegatoStoreBeanOmissis);
							DmpkCoreAdddocBean lAdddocBeanAllegatoOmissis = new DmpkCoreAdddocBean();
							lAdddocBeanAllegatoOmissis.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
							lAdddocBeanAllegatoOmissis.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
							lAdddocBeanAllegatoOmissis.setAttributiuddocxmlin(attributiAllegatoOmissis);
							final AdddocImpl storeAllegatoOmissis = new AdddocImpl();
							storeAllegatoOmissis.setBean(lAdddocBeanAllegatoOmissis);
							lLoginService.login(pAurigaLoginBean);
							session.doWork(new Work() {
		
								@Override
								public void execute(Connection paramConnection) throws SQLException {
									paramConnection.setAutoCommit(false);
									storeAllegatoOmissis.execute(paramConnection);
								}
							});
							StoreResultBean<DmpkCoreAdddocBean> resultAllegatoOmissis = new StoreResultBean<DmpkCoreAdddocBean>();
							AnalyzeResult.analyze(lAdddocBeanAllegatoOmissis, resultAllegatoOmissis);
							resultAllegatoOmissis.setResultBean(lAdddocBeanAllegatoOmissis);
							if (resultAllegatoOmissis.isInError()) {
								throw new StoreException(resultAllegatoOmissis);
							} else {
								mLogger.debug("idDocAllegatoOmissis vale " + resultAllegatoOmissis.getResultBean().getIddocout());
								RebuildedFile lRebuildedFileOmissis = new RebuildedFile();
								lRebuildedFileOmissis.setFile(nextFileOmissis.next());
								lRebuildedFileOmissis.setInfo(pAllegatiBean.getInfoOmissis().get(i));
								lRebuildedFileOmissis.setIdDocumento(resultAllegatoOmissis.getResultBean().getIddocout());
								lRebuildedFileOmissis.setPosizione(countOmissis);
								versioni.add(lRebuildedFileOmissis);						
							}
						}
						countOmissis++;
					}
				}
				if(pAllegatiBean.getFlgSalvaOrdAllegati() != null && pAllegatiBean.getFlgSalvaOrdAllegati()) {					
					// Nuova chiamata all'update per il solo ordinamento degli allegati
					updateDocUdXOrdAllegati(pAurigaLoginBean, session, lLoginService, listaIdDocAllegatiOrdinati, idDoc);
				}
			}
		}
		return idUd;
	}

	public void manageAddUpdDocs(AurigaLoginBean pAurigaLoginBean, FilePrimarioBean pFilePrimarioBean, AllegatiBean pAllegatiBean,
			DmpkCoreUpddocudBean lUpddocudBean, XmlUtilitySerializer lXmlUtilitySerializer, BigDecimal idUd, BigDecimal idDocPrimario,
			DocumentoXmlOutBean pDocumentoXmlOutBean, List<RebuildedFile> versioni, List<RebuildedFile> versioniDaRimuovere,
			List<RebuildedFile> allegatiDaRimuovere, Session session) throws Exception {
	
		LoginService lLoginService = new LoginService();
		if (lUpddocudBean != null) {

			final UpddocudImpl store = new UpddocudImpl();
			store.setBean(lUpddocudBean);
	
			lLoginService.login(pAurigaLoginBean);
			session.doWork(new Work() {
	
				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					store.execute(paramConnection);
				}
			});
	
			StoreResultBean<DmpkCoreUpddocudBean> result = new StoreResultBean<DmpkCoreUpddocudBean>();
			AnalyzeResult.analyze(lUpddocudBean, result);
			result.setResultBean(lUpddocudBean);
	
			if (result.isInError()) {
				throw new StoreException(result);
			} 
		}
		if (pFilePrimarioBean != null) {
			if(pFilePrimarioBean.getIsNewOrChanged() != null && pFilePrimarioBean.getIsNewOrChanged()) {
				RebuildedFile lRebuildedFile = new RebuildedFile();
				lRebuildedFile.setIdDocumento(pFilePrimarioBean.getIdDocumento());
				lRebuildedFile.setFile(pFilePrimarioBean.getFile());
				lRebuildedFile.setInfo(pFilePrimarioBean.getInfo());
				if (pDocumentoXmlOutBean.getFilePrimario() != null && StringUtils.isNotBlank(pDocumentoXmlOutBean.getFilePrimario().getUri())) {
					if (isProtocolloRepertorio(pDocumentoXmlOutBean)) {
						lRebuildedFile.setAnnullaLastVer(true);
					} else if (pFilePrimarioBean.getFlgSostituisciVerPrec() != null && pFilePrimarioBean.getFlgSostituisciVerPrec()) {
						lRebuildedFile.setUpdateVersion(true);
					}
				}
				versioni.add(lRebuildedFile);
			}
			// Vers. con omissis
			if(pFilePrimarioBean.getIsNewOrChangedOmissis() != null && pFilePrimarioBean.getIsNewOrChangedOmissis()) {
				if(pFilePrimarioBean.getIdDocumentoOmissis() == null) {						
					DmpkCoreAdddocBean lAdddocBeanOmissis = new DmpkCoreAdddocBean();
					lAdddocBeanOmissis.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
					lAdddocBeanOmissis.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));				
					// La versione omissis del primario viene salvata come fosse un allegato
					AllegatoVersConOmissisStoreBean attributiOmissis = new AllegatoVersConOmissisStoreBean();
					attributiOmissis.setIdUd(idUd);
					attributiOmissis.setFlgVersConOmissis("1");
					attributiOmissis.setIdDocVersIntegrale(pFilePrimarioBean.getIdDocumento() != null ? String.valueOf(pFilePrimarioBean.getIdDocumento().longValue()) : null);							
					lAdddocBeanOmissis.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(attributiOmissis));
					final AdddocImpl storeOmissis = new AdddocImpl();
					storeOmissis.setBean(lAdddocBeanOmissis);
					lLoginService.login(pAurigaLoginBean);
					session.doWork(new Work() {

						@Override
						public void execute(Connection paramConnection) throws SQLException {
							paramConnection.setAutoCommit(false);
							storeOmissis.execute(paramConnection);
						}
					});
					StoreResultBean<DmpkCoreAdddocBean> resultOmissis = new StoreResultBean<DmpkCoreAdddocBean>();
					AnalyzeResult.analyze(lAdddocBeanOmissis, resultOmissis);
					resultOmissis.setResultBean(lAdddocBeanOmissis);
					if (resultOmissis.isInError()) {
						throw new StoreException(resultOmissis);
					} else {						
						mLogger.debug("idDocOmissis vale " + resultOmissis.getResultBean().getIddocout());
						RebuildedFile lRebuildedFileOmissis = new RebuildedFile();
						lRebuildedFileOmissis.setIdDocumento(resultOmissis.getResultBean().getIddocout());
						lRebuildedFileOmissis.setFile(pFilePrimarioBean.getFileOmissis());
						lRebuildedFileOmissis.setInfo(pFilePrimarioBean.getInfoOmissis());
						versioni.add(lRebuildedFileOmissis);					
					}		
				} else {					
					RebuildedFile lRebuildedFileOmissis = new RebuildedFile();
					lRebuildedFileOmissis.setIdDocumento(pFilePrimarioBean.getIdDocumentoOmissis());
					lRebuildedFileOmissis.setFile(pFilePrimarioBean.getFileOmissis());
					lRebuildedFileOmissis.setInfo(pFilePrimarioBean.getInfoOmissis());
					if (pDocumentoXmlOutBean.getFilePrimario() != null && StringUtils.isNotBlank(pDocumentoXmlOutBean.getFilePrimario().getUriOmissis())) {
						if (isProtocolloRepertorio(pDocumentoXmlOutBean)) {
							lRebuildedFileOmissis.setAnnullaLastVer(true);
						} else if (pFilePrimarioBean.getFlgSostituisciVerPrecOmissis() != null && pFilePrimarioBean.getFlgSostituisciVerPrecOmissis()) {
							lRebuildedFileOmissis.setUpdateVersion(true);
						}
					}
					versioni.add(lRebuildedFileOmissis);
				}
			}
		} else {
			if (pDocumentoXmlOutBean.getFilePrimario() != null && StringUtils.isNotBlank(pDocumentoXmlOutBean.getFilePrimario().getUri())) {
				RebuildedFile lRebuildedFile = new RebuildedFile();
				lRebuildedFile.setIdDocumento(idDocPrimario);
				FileInfoBean info = new FileInfoBean();
				info.setTipo(TipoFile.PRIMARIO);
				GenericFile allegatoRiferimento = new GenericFile();
				allegatoRiferimento.setDisplayFilename(pDocumentoXmlOutBean.getFilePrimario().getDisplayFilename());
				info.setAllegatoRiferimento(allegatoRiferimento);
				lRebuildedFile.setInfo(info);
				versioniDaRimuovere.add(lRebuildedFile);
			}
			// Vers. con omissis
			if (pDocumentoXmlOutBean.getFilePrimario() != null && StringUtils.isNotBlank(pDocumentoXmlOutBean.getFilePrimario().getUriOmissis())) {
				RebuildedFile lRebuildedFileOmissis = new RebuildedFile();
				lRebuildedFileOmissis.setIdDocumento(new BigDecimal(pDocumentoXmlOutBean.getFilePrimario().getIdDocOmissis()));
				FileInfoBean infoOmissis = new FileInfoBean();
				infoOmissis.setTipo(TipoFile.PRIMARIO);
				GenericFile allegatoRiferimentoOmissis = new GenericFile();
				allegatoRiferimentoOmissis.setDisplayFilename(pDocumentoXmlOutBean.getFilePrimario().getDisplayFilenameOmissis());
				infoOmissis.setAllegatoRiferimento(allegatoRiferimentoOmissis);
				lRebuildedFileOmissis.setInfo(infoOmissis);
				versioniDaRimuovere.add(lRebuildedFileOmissis);
			}
		}
		if (pAllegatiBean != null) {
			
			List<ValueBean> listaIdDocAllegatiOrdinati = new ArrayList<>();

			HashMap<String, AllegatiOutBean> allegatiMap = new HashMap<String, AllegatiOutBean>();
			int pos = 1;
			for (AllegatiOutBean allegato : pDocumentoXmlOutBean.getAllegati()) {
				boolean trovato = false;
				for (int i = 0; i < pAllegatiBean.getIdDocumento().size(); i++) {
					String idDocAllegato = (pAllegatiBean.getIdDocumento().get(i) != null) ? pAllegatiBean.getIdDocumento().get(i).longValue() + "" : null;
					if (idDocAllegato != null && idDocAllegato.equals(allegato.getIdDoc())) {
						trovato = true;
						allegatiMap.put(idDocAllegato, allegato);
						break;
					}
				}
				if (!trovato) {
					RebuildedFile lRebuildedFile = new RebuildedFile();
					lRebuildedFile.setIdDocumento(new BigDecimal(allegato.getIdDoc()));
					FileInfoBean info = new FileInfoBean();
					info.setTipo(TipoFile.ALLEGATO);
					GenericFile allegatoRiferimento = new GenericFile();
					allegatoRiferimento.setDisplayFilename(allegato.getDisplayFileName());
					info.setAllegatoRiferimento(allegatoRiferimento);
					info.setPosizione(pos);
					lRebuildedFile.setInfo(info);
					lRebuildedFile.setPosizione(pos);
					allegatiDaRimuovere.add(lRebuildedFile);
				}
				pos++;
			}
			mLogger.debug("allegatiMap " + allegatiMap);
			HashMap<Integer, BigDecimal> mappaIdDocAllegato = new HashMap<Integer, BigDecimal>();
			int count = 1;
			Iterator<File> nextFile = null;
			if (pAllegatiBean.getFileAllegati() != null)
				nextFile = pAllegatiBean.getFileAllegati().iterator();
			if (pAllegatiBean.getIdDocumento() != null) {
				for (int i = 0; i < pAllegatiBean.getIdDocumento().size(); i++) {
					BigDecimal idDocAllegato = pAllegatiBean.getIdDocumento().get(i);
					boolean isAllegatoNewOrChanged = (idDocAllegato == null) || (pAllegatiBean.getIsNewOrChanged().get(i) != null && pAllegatiBean.getIsNewOrChanged().get(i));
					boolean skipAggiornaMetadati = pAllegatiBean.getFlgSkipAggiornaMetadatiAllegatiNonModificati() != null && pAllegatiBean.getFlgSkipAggiornaMetadatiAllegatiNonModificati() && !isAllegatoNewOrChanged;
					boolean skipAggiornaMetadatiAllegato = pAllegatiBean.getFlgNoModificaDati() != null && pAllegatiBean.getFlgNoModificaDati().get(i) != null && pAllegatiBean.getFlgNoModificaDati().get(i) && !isAllegatoNewOrChanged;
					if (!skipAggiornaMetadati && !skipAggiornaMetadatiAllegato) {
						AllegatoStoreBean lAllegatoStoreBean = new AllegatoStoreBean();
						lAllegatoStoreBean.setIdUd(idUd);
						lAllegatoStoreBean.setDescrizione(pAllegatiBean.getDescrizione().get(i));
						lAllegatoStoreBean.setIdDocType(pAllegatiBean.getDocType().get(i));
						if (pAllegatiBean.getIdUdFrom() != null){
							lAllegatoStoreBean.setIdUdFrom(pAllegatiBean.getIdUdFrom().get(i));
						}
						if (pAllegatiBean.getIdUdAllegato() != null){
							lAllegatoStoreBean.setIdUdAllegato(pAllegatiBean.getIdUdAllegato().get(i));
						}
						if (pAllegatiBean.getFlgProvEsterna() != null) {
							Boolean flgProvEsterna = pAllegatiBean.getFlgProvEsterna().get(i);
							lAllegatoStoreBean.setFlgProvEsterna(flgProvEsterna != null && flgProvEsterna ? "1" : null);
						}						
						if (pAllegatiBean.getFlgParteDispositivo() != null) {
							Boolean flgParteDispositivo = pAllegatiBean.getFlgParteDispositivo().get(i);
							lAllegatoStoreBean.setFlgParteDispositivo(flgParteDispositivo != null && flgParteDispositivo ? "1" : null);
						}
						if (pAllegatiBean.getIdTask() != null) {
							lAllegatoStoreBean.setIdTask(pAllegatiBean.getIdTask().get(i));
						}
						if (pAllegatiBean.getFlgNoPubbl() != null) {
							Boolean flgNoPubbl = pAllegatiBean.getFlgNoPubbl().get(i);
							lAllegatoStoreBean.setFlgNoPubbl(flgNoPubbl != null && flgNoPubbl ? "1" : null);
						}	
						if (pAllegatiBean.getFlgPubblicaSeparato() != null) {
							Boolean flgPubblicaSeparato = pAllegatiBean.getFlgPubblicaSeparato().get(i);
							lAllegatoStoreBean.setFlgPubblicaSeparato(flgPubblicaSeparato != null && flgPubblicaSeparato ? "1" : null);
						}
						if (pAllegatiBean.getNroPagFileUnione() != null) {
							lAllegatoStoreBean.setNroPagFileUnione(pAllegatiBean.getNroPagFileUnione().get(i));
						}
						if (pAllegatiBean.getNroPagFileUnioneOmissis() != null) {					
							lAllegatoStoreBean.setNroPagFileUnioneOmissis(pAllegatiBean.getNroPagFileUnioneOmissis().get(i));
						}
						if (pAllegatiBean.getFlgDatiProtettiTipo1() != null) {
							Boolean flgDatiProtettiTipo1 = pAllegatiBean.getFlgDatiProtettiTipo1().get(i);
							lAllegatoStoreBean.setFlgDatiProtettiTipo1(flgDatiProtettiTipo1 != null && flgDatiProtettiTipo1 ? "1" : null);
						}
						if (pAllegatiBean.getFlgDatiProtettiTipo2() != null) {
							Boolean flgDatiProtettiTipo2 = pAllegatiBean.getFlgDatiProtettiTipo2().get(i);
							lAllegatoStoreBean.setFlgDatiProtettiTipo2(flgDatiProtettiTipo2 != null && flgDatiProtettiTipo2 ? "1" : null);
						}
						if (pAllegatiBean.getFlgDatiProtettiTipo3() != null) {
							Boolean flgDatiProtettiTipo3 = pAllegatiBean.getFlgDatiProtettiTipo3().get(i);
							lAllegatoStoreBean.setFlgDatiProtettiTipo3(flgDatiProtettiTipo3 != null && flgDatiProtettiTipo3 ? "1" : null);
						}
						if (pAllegatiBean.getFlgDatiProtettiTipo4() != null) {
							Boolean flgDatiProtettiTipo4 = pAllegatiBean.getFlgDatiProtettiTipo4().get(i);
							lAllegatoStoreBean.setFlgDatiProtettiTipo4(flgDatiProtettiTipo4 != null && flgDatiProtettiTipo4 ? "1" : null);
						}
						if (pAllegatiBean.getFlgDatiSensibili() != null) {
							Boolean flgDatiSensibili = pAllegatiBean.getFlgDatiSensibili().get(i);
							lAllegatoStoreBean.setFlgDatiSensibili(flgDatiSensibili != null && flgDatiSensibili ? "1" : null);
						}						
						if (pAllegatiBean.getFlgOriginaleCartaceo() != null) {
							Boolean flgOriginaleCartaceo = pAllegatiBean.getFlgOriginaleCartaceo().get(i);
							lAllegatoStoreBean.setFlgOriginaleCartaceo(flgOriginaleCartaceo != null && flgOriginaleCartaceo ? "1" : null);
						}
						if (pAllegatiBean.getFlgCopiaSostitutiva() != null) {
							Boolean flgCopiaSostitutiva = pAllegatiBean.getFlgCopiaSostitutiva().get(i);
							lAllegatoStoreBean.setFlgCopiaSostitutiva(flgCopiaSostitutiva != null && flgCopiaSostitutiva ? "1" : null);
						}
						if (pAllegatiBean.getFlgGenAutoDaModello() != null) {
							Boolean flgGenAutoDaModello = pAllegatiBean.getFlgGenAutoDaModello().get(i);
							lAllegatoStoreBean.setFlgGenAutoDaModello(flgGenAutoDaModello != null && flgGenAutoDaModello ? "1" : null);
						}
						if (SharePointUtil.isIntegratoConSharePoint(pAurigaLoginBean)) {
							SharePointUtil shUtil = (SharePointUtil) SpringAppContext.getContext().getBean("sharePointUtil");
							AllegatiOutBean allegato = allegatiMap.get(pAllegatiBean.getIdDocumento());
							if (allegato != null) {
								List<UrlVersione> uriList = shUtil.getUriPrecentiVersioni(pAurigaLoginBean, allegato.getUri());
								lAllegatoStoreBean.setUriVerPrecSuSharePoint_Ver(uriList);
							}
						}
						String attributi = lXmlUtilitySerializer.bindXmlCompleta(lAllegatoStoreBean);
						if (idDocAllegato != null) {
							mappaIdDocAllegato.put(new Integer(count), idDocAllegato);							
							DmpkCoreUpddocudBean lUpddocudBeanAllegato = new DmpkCoreUpddocudBean();
							lUpddocudBeanAllegato.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
							lUpddocudBeanAllegato.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
							lUpddocudBeanAllegato.setFlgtipotargetin("D");
							lUpddocudBeanAllegato.setIduddocin(idDocAllegato);
							lUpddocudBeanAllegato.setAttributiuddocxmlin(attributi);
							final UpddocudImpl storeAllegato = new UpddocudImpl();
							lUpddocudBeanAllegato.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
							storeAllegato.setBean(lUpddocudBeanAllegato);
							lLoginService.login(pAurigaLoginBean);
							session.doWork(new Work() {
	
								@Override
								public void execute(Connection paramConnection) throws SQLException {
									paramConnection.setAutoCommit(false);
									storeAllegato.execute(paramConnection);
								}
							});
							StoreResultBean<DmpkCoreUpddocudBean> resultAllegato = new StoreResultBean<DmpkCoreUpddocudBean>();
							AnalyzeResult.analyze(lUpddocudBeanAllegato, resultAllegato);
							resultAllegato.setResultBean(lUpddocudBeanAllegato);
							if (resultAllegato.isInError()) {
								throw new StoreException(resultAllegato);
							} else {
								if (!pAllegatiBean.getIsNull().get(i) && pAllegatiBean.getIsNewOrChanged().get(i)) {
									RebuildedFile lRebuildedFile = new RebuildedFile();
									lRebuildedFile.setFile(nextFile.next());
									lRebuildedFile.setInfo(pAllegatiBean.getInfo().get(i));
									lRebuildedFile.setIdDocumento(idDocAllegato);
									lRebuildedFile.setPosizione(count);
									for (AllegatiOutBean allegato : pDocumentoXmlOutBean.getAllegati()) {
										if (StringUtils.isNotBlank(allegato.getIdDoc()) && String.valueOf(idDocAllegato.longValue()).equals(allegato.getIdDoc())) {
											if (allegato.getUri() != null && StringUtils.isNotBlank(allegato.getUri())) {
												if (isProtocolloRepertorio(pDocumentoXmlOutBean)) {
													lRebuildedFile.setAnnullaLastVer(true);
												} else if (pAllegatiBean.getFlgSostituisciVerPrec() != null
														&& pAllegatiBean.getFlgSostituisciVerPrec().get(i) != null
														&& pAllegatiBean.getFlgSostituisciVerPrec().get(i)) {
													lRebuildedFile.setUpdateVersion(true);
												}
											}
											break;
										}
									}
									versioni.add(lRebuildedFile);
								} else if (pAllegatiBean.getIsNull().get(i)) {
									for (AllegatiOutBean allegato : pDocumentoXmlOutBean.getAllegati()) {
										if (StringUtils.isNotBlank(allegato.getIdDoc()) && String.valueOf(idDocAllegato.longValue()).equals(allegato.getIdDoc())) {
											if (allegato.getUri() != null && StringUtils.isNotBlank(allegato.getUri())) {
												RebuildedFile lRebuildedFile = new RebuildedFile();
												lRebuildedFile.setIdDocumento(idDocAllegato);
												FileInfoBean info = new FileInfoBean();
												info.setTipo(TipoFile.ALLEGATO);
												GenericFile allegatoRiferimento = new GenericFile();
												allegatoRiferimento.setDisplayFilename(allegato.getDisplayFileName());
												info.setAllegatoRiferimento(allegatoRiferimento);
												info.setPosizione(count);
												lRebuildedFile.setInfo(info);
												lRebuildedFile.setPosizione(count);
												versioniDaRimuovere.add(lRebuildedFile);
											}
											break;
										}
									}
								}
							}							
						} else {
							DmpkCoreAdddocBean lAdddocBeanAllegato = new DmpkCoreAdddocBean();
							lAdddocBeanAllegato.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
							lAdddocBeanAllegato.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
							lAdddocBeanAllegato.setAttributiuddocxmlin(attributi);
							final AdddocImpl storeAllegato = new AdddocImpl();
							lAdddocBeanAllegato.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
							storeAllegato.setBean(lAdddocBeanAllegato);
							lLoginService.login(pAurigaLoginBean);
							session.doWork(new Work() {
	
								@Override
								public void execute(Connection paramConnection) throws SQLException {
									paramConnection.setAutoCommit(false);
									storeAllegato.execute(paramConnection);
								}
							});
							StoreResultBean<DmpkCoreAdddocBean> resultAllegato = new StoreResultBean<DmpkCoreAdddocBean>();
							AnalyzeResult.analyze(lAdddocBeanAllegato, resultAllegato);
							resultAllegato.setResultBean(lAdddocBeanAllegato);
							if (resultAllegato.isInError()) {
								throw new StoreException(resultAllegato);
							} else {
								mLogger.debug("idDocAllegato vale " + resultAllegato.getResultBean().getIddocout());
								mappaIdDocAllegato.put(new Integer(count), resultAllegato.getResultBean().getIddocout());								
								if (!pAllegatiBean.getIsNull().get(i)) {
									RebuildedFile lRebuildedFile = new RebuildedFile();
									lRebuildedFile.setFile(nextFile.next());
									lRebuildedFile.setInfo(pAllegatiBean.getInfo().get(i));
									lRebuildedFile.setIdDocumento(resultAllegato.getResultBean().getIddocout());
									lRebuildedFile.setPosizione(count);
									versioni.add(lRebuildedFile);
								}
							}							
						}	
					} else {
						// se ha fatto lo skip dell'aggiornamento dei metadati vuol dire che !isNewOrChanged quindi idDocAllegato sarà sempre valorizzato
						// in questo caso lo devo comunque aggiungere a mappaIdDocAllegato per il salvataggio dell'ordine degli allegati
						if (idDocAllegato != null) {
							mappaIdDocAllegato.put(new Integer(count), idDocAllegato);
						}
					}
					count++;
				}
			}
			
			for (BigDecimal idBigDecimal : mappaIdDocAllegato.values()) {
				ValueBean valueBean = new ValueBean();
				valueBean.setValue(String.valueOf(idBigDecimal.longValue()));
				listaIdDocAllegatiOrdinati.add(valueBean);
			}
			
			// Vers. con omissis	
			int countOmissis = 1;
			Iterator<File> nextFileOmissis = null;
			if (pAllegatiBean.getFileAllegatiOmissis() != null)
				nextFileOmissis = pAllegatiBean.getFileAllegatiOmissis().iterator();
			if (pAllegatiBean.getIdDocumentoOmissis() != null) {
				for (int i = 0; i < pAllegatiBean.getIdDocumentoOmissis().size(); i++) {
					BigDecimal idDocAllegatoOmissis = pAllegatiBean.getIdDocumentoOmissis().get(i);
					boolean isAllegatoOmissisNewOrChanged = (idDocAllegatoOmissis == null) || (pAllegatiBean.getIsNewOrChangedOmissis().get(i) != null && pAllegatiBean.getIsNewOrChangedOmissis().get(i));
					boolean skipAggiornaMetadatiOmissis = pAllegatiBean.getFlgSkipAggiornaMetadatiAllegatiNonModificati() != null && pAllegatiBean.getFlgSkipAggiornaMetadatiAllegatiNonModificati() && !isAllegatoOmissisNewOrChanged;
					boolean skipAggiornaMetadatiAllegatoOmissis = pAllegatiBean.getFlgNoModificaDati() != null && pAllegatiBean.getFlgNoModificaDati().get(i) != null && pAllegatiBean.getFlgNoModificaDati().get(i) && !isAllegatoOmissisNewOrChanged;										
					if (!skipAggiornaMetadatiOmissis && !skipAggiornaMetadatiAllegatoOmissis) {
						if (idDocAllegatoOmissis != null) {
							// Non ha senso fare la Upddocud per la versione con omissis?
							if (!pAllegatiBean.getIsNullOmissis().get(i) && pAllegatiBean.getIsNewOrChangedOmissis().get(i)) {
								RebuildedFile lRebuildedFileOmissis = new RebuildedFile();
								lRebuildedFileOmissis.setFile(nextFileOmissis.next());
								lRebuildedFileOmissis.setInfo(pAllegatiBean.getInfoOmissis().get(i));
								lRebuildedFileOmissis.setIdDocumento(idDocAllegatoOmissis);
								lRebuildedFileOmissis.setPosizione(count);
								for (AllegatiOutBean allegato : pDocumentoXmlOutBean.getAllegati()) {									
									if (StringUtils.isNotBlank(allegato.getIdDocOmissis()) && String.valueOf(idDocAllegatoOmissis.longValue()).equals(allegato.getIdDocOmissis())) {
										if (StringUtils.isNotBlank(allegato.getUriOmissis())) {
											if (isProtocolloRepertorio(pDocumentoXmlOutBean)) {
												lRebuildedFileOmissis.setAnnullaLastVer(true);
											} else if (pAllegatiBean.getFlgSostituisciVerPrecOmissis() != null
													&& pAllegatiBean.getFlgSostituisciVerPrecOmissis().get(i) != null
													&& pAllegatiBean.getFlgSostituisciVerPrecOmissis().get(i)) {
												lRebuildedFileOmissis.setUpdateVersion(true);
											}
										}
										break;
									}
								}
								versioni.add(lRebuildedFileOmissis);
							} else if (pAllegatiBean.getIsNullOmissis().get(i)) {
								for (AllegatiOutBean allegato : pDocumentoXmlOutBean.getAllegati()) {
									if (StringUtils.isNotBlank(allegato.getIdDocOmissis()) && String.valueOf(idDocAllegatoOmissis.longValue()).equals(allegato.getIdDocOmissis())) {
										if (StringUtils.isNotBlank(allegato.getUriOmissis())) {
											RebuildedFile lRebuildedFileOmissis = new RebuildedFile();
											lRebuildedFileOmissis.setIdDocumento(idDocAllegatoOmissis);
											FileInfoBean infoOmissis = new FileInfoBean();
											infoOmissis.setTipo(TipoFile.ALLEGATO);
											GenericFile allegatoRiferimentoOmissis = new GenericFile();
											allegatoRiferimentoOmissis.setDisplayFilename(allegato.getDisplayFileNameOmissis());
											infoOmissis.setAllegatoRiferimento(allegatoRiferimentoOmissis);
											infoOmissis.setPosizione(count);
											lRebuildedFileOmissis.setInfo(infoOmissis);
											lRebuildedFileOmissis.setPosizione(count);
											versioniDaRimuovere.add(lRebuildedFileOmissis);
										}
										break;
									}
								}
							}						
						} else if (!pAllegatiBean.getIsNullOmissis().get(i)) {								
							BigDecimal idDocAllegato = mappaIdDocAllegato.get(new Integer(countOmissis));
							AllegatoVersConOmissisStoreBean lAllegatoStoreBeanOmissis = new AllegatoVersConOmissisStoreBean();
							lAllegatoStoreBeanOmissis.setIdUd(idUd);						
							lAllegatoStoreBeanOmissis.setFlgVersConOmissis("1");
							lAllegatoStoreBeanOmissis.setIdDocVersIntegrale(idDocAllegato != null ? String.valueOf(idDocAllegato.longValue()) : null);				
							String attributiAllegatoOmissis = lXmlUtilitySerializer.bindXml(lAllegatoStoreBeanOmissis);			
							DmpkCoreAdddocBean lAdddocBeanAllegatoOmissis = new DmpkCoreAdddocBean();
							lAdddocBeanAllegatoOmissis.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
							lAdddocBeanAllegatoOmissis.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
							lAdddocBeanAllegatoOmissis.setAttributiuddocxmlin(attributiAllegatoOmissis);
							final AdddocImpl storeAllegatoOmissis = new AdddocImpl();
							lAdddocBeanAllegatoOmissis.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
							storeAllegatoOmissis.setBean(lAdddocBeanAllegatoOmissis);
							lLoginService.login(pAurigaLoginBean);
							session.doWork(new Work() {
	
								@Override
								public void execute(Connection paramConnection) throws SQLException {
									paramConnection.setAutoCommit(false);
									storeAllegatoOmissis.execute(paramConnection);
								}
							});
							StoreResultBean<DmpkCoreAdddocBean> resultAllegatoOmissis = new StoreResultBean<DmpkCoreAdddocBean>();
							AnalyzeResult.analyze(lAdddocBeanAllegatoOmissis, resultAllegatoOmissis);
							resultAllegatoOmissis.setResultBean(lAdddocBeanAllegatoOmissis);
							if (resultAllegatoOmissis.isInError()) {
								throw new StoreException(resultAllegatoOmissis);
							} else {
								mLogger.debug("idDocAllegatoOmissis vale " + resultAllegatoOmissis.getResultBean().getIddocout());
								RebuildedFile lRebuildedFileOmissis = new RebuildedFile();
								lRebuildedFileOmissis.setFile(nextFileOmissis.next());
								lRebuildedFileOmissis.setInfo(pAllegatiBean.getInfoOmissis().get(i));
								lRebuildedFileOmissis.setIdDocumento(resultAllegatoOmissis.getResultBean().getIddocout());
								lRebuildedFileOmissis.setPosizione(countOmissis);
								versioni.add(lRebuildedFileOmissis);								
							}
						}
					}
					countOmissis++;
				}
			}
			
			if (lUpddocudBean != null) {
				if(pAllegatiBean.getFlgSalvaOrdAllegati() != null && pAllegatiBean.getFlgSalvaOrdAllegati()) {					
					// Nuova chiamata all'update per il solo ordinamento degli allegati
					updateDocUdXOrdAllegati(pAurigaLoginBean, session, lLoginService, listaIdDocAllegatiOrdinati, lUpddocudBean.getIduddocin());
				}
			}
		}
	}

	protected void updateDocUdXOrdAllegati(AurigaLoginBean pAurigaLoginBean, Session session, LoginService lLoginService,
			List<ValueBean> listaIdDocAllegatiOrdinati, BigDecimal idDoc)
			throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, Exception {
		
		DmpkCoreUpddocudBean lUpddocudXOrdBean = new DmpkCoreUpddocudBean();
		lUpddocudXOrdBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		lUpddocudXOrdBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));

		CreaModDocumentoInBean pModificaDocumentoInXOrdBean = new CreaModDocumentoInBean();
		pModificaDocumentoInXOrdBean.setIdDocAllegatiOrdinati(listaIdDocAllegatiOrdinati);
		XmlUtilitySerializer lXmlUtilitySerializerXOrd = new XmlUtilitySerializer();
		lUpddocudXOrdBean.setAttributiuddocxmlin(lXmlUtilitySerializerXOrd.bindXml(pModificaDocumentoInXOrdBean));
		lUpddocudXOrdBean.setFlgtipotargetin("D");
		lUpddocudXOrdBean.setIduddocin(idDoc);
		lUpddocudXOrdBean.setFlgautocommitin(0); // blocco l'autocommit
		
		final UpddocudImpl store = new UpddocudImpl();
		store.setBean(lUpddocudXOrdBean);
		lLoginService.login(pAurigaLoginBean);
		session.doWork(new Work() {

			@Override
			public void execute(Connection paramConnection) throws SQLException {
				paramConnection.setAutoCommit(false);
				store.execute(paramConnection);
			}
		});

		StoreResultBean<DmpkCoreUpddocudBean> result = new StoreResultBean<DmpkCoreUpddocudBean>();
		AnalyzeResult.analyze(lUpddocudXOrdBean, result);
		result.setResultBean(lUpddocudXOrdBean);

		if (result.isInError()) {
			throw new StoreException(result);
		}
	}

	private boolean isProtocolloRepertorio(DocumentoXmlOutBean pDocumentoXmlOutBean) {
		RegNumPrincipale lRegNumPrincipale = pDocumentoXmlOutBean.getEstremiRegistrazione();
		String tipoNumPrincipale = lRegNumPrincipale != null ? lRegNumPrincipale.getTipo() : null;
		if (tipoNumPrincipale != null) {
			return tipoNumPrincipale.equalsIgnoreCase("PG") || tipoNumPrincipale.equalsIgnoreCase("PI") || tipoNumPrincipale.equalsIgnoreCase("R");
		}
		return false;
	}

	protected Map<String, String> aggiungiFiles(AurigaLoginBean pAurigaLoginBean, List<RebuildedFile> versioni) {

		Map<String, String> fileErrors = new HashMap<String, String>();

		for (RebuildedFile lRebuildedFile : versioni) {
			try {
				VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);
				VersionaDocumentoOutBean lVersionaDocumentoOutBean = versionaDocumento(pAurigaLoginBean, lVersionaDocumentoInBean);
				if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {
					throw new Exception(lVersionaDocumentoOutBean.getDefaultMessage());
				}
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				if (lRebuildedFile.getInfo().getTipo() == TipoFile.PRIMARIO) {
					fileErrors.put("0", "Il file primario " + lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename()
							+ " non è stato salvato correttamente." + (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
				} else if (lRebuildedFile.getInfo().getTipo() == TipoFile.ALLEGATO) {
					fileErrors.put("" + lRebuildedFile.getInfo().getPosizione(), "Il file allegato "
							+ lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename() + " in posizione "
							+ lRebuildedFile.getInfo().getPosizione() + " non è stato salvato correttamente."
							+ (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
				}

			}
		}

		return fileErrors;
	}

	protected Map<String, String> aggiungiFilesInTransaction(AurigaLoginBean pAurigaLoginBean, List<RebuildedFile> versioni, Session session) {

		Map<String, String> fileErrors = new HashMap<String, String>();

		for (RebuildedFile lRebuildedFile : versioni) {
			try {
				VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);
				VersionaDocumentoOutBean lVersionaDocumentoOutBean = versionaDocumentoInTransaction(pAurigaLoginBean, lVersionaDocumentoInBean, session);
				if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {
					throw new Exception(lVersionaDocumentoOutBean.getDefaultMessage());
				}
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				if (lRebuildedFile.getInfo().getTipo() == TipoFile.PRIMARIO) {
					fileErrors.put("0", "Il file primario " + lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename()
							+ " non è stato salvato correttamente." + (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
				} else if (lRebuildedFile.getInfo().getTipo() == TipoFile.ALLEGATO) {
					fileErrors.put("" + lRebuildedFile.getInfo().getPosizione(), "Il file allegato "
							+ lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename() + " in posizione "
							+ lRebuildedFile.getInfo().getPosizione() + " non è stato salvato correttamente."
							+ (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
				}

			}
		}

		return fileErrors;
	}

	protected Map<String, String> rimuoviFiles(AurigaLoginBean pAurigaLoginBean, List<RebuildedFile> versioniDaRimuovere, List<RebuildedFile> allegatiDaRimuovere) {

		Map<String, String> fileErrors = new HashMap<String, String>();

		for (RebuildedFile lRebuildedFile : versioniDaRimuovere) {
			try {
				rimuoviVersioneDocumento(lRebuildedFile, pAurigaLoginBean);
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				if (lRebuildedFile.getInfo().getTipo() == TipoFile.PRIMARIO) {
					fileErrors.put("0", "Il file primario " + lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename()
							+ " non è stato eliminato." + (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
				} else if (lRebuildedFile.getInfo().getTipo() == TipoFile.ALLEGATO) {
					fileErrors.put("" + lRebuildedFile.getInfo().getPosizione(),
							"Il file allegato " + lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename() + " in posizione "
									+ lRebuildedFile.getInfo().getPosizione() + " non è stato eliminato."
									+ (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
				}

			}
		}

		for (RebuildedFile lRebuildedFile : allegatiDaRimuovere) {
			try {
				rimuoviAllegato(lRebuildedFile, pAurigaLoginBean);
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				fileErrors.put("" + lRebuildedFile.getInfo().getPosizione(), "L'allegato con id. " + lRebuildedFile.getIdDocumento()
						+ " non è stato eliminato." + (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
			}
		}

		return fileErrors;
	}

	protected void recuperaEstremi(AurigaLoginBean pAurigaLoginBean, CreaModDocumentoInBean pCreaDocumentoInBean, CreaModDocumentoOutBean lCreaDocumentoOutBean)
			throws Exception, StoreException {

		DmpkUtilityGetestremiregnumud_jBean lGetestremiregnumud_jBean = new DmpkUtilityGetestremiregnumud_jBean();
		lGetestremiregnumud_jBean.setIdudin(lCreaDocumentoOutBean.getIdUd());

		Getestremiregnumud_j lGetestremiregnumud_j = new Getestremiregnumud_j();
		mLogger.debug("Chiamo la getEstremiRegNumUd_j " + lGetestremiregnumud_jBean);

		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(pAurigaLoginBean.getSchema());

		StoreResultBean<DmpkUtilityGetestremiregnumud_jBean> lStoreResultBean = lGetestremiregnumud_j.execute(lSchemaBean, lGetestremiregnumud_jBean);
		if (lStoreResultBean.isInError()) {
			throw new StoreException(lStoreResultBean);
		}

		lCreaDocumentoOutBean.setAnno(lStoreResultBean.getResultBean().getAnnoregout());
		lCreaDocumentoOutBean.setNumero(lStoreResultBean.getResultBean().getNumregout());
		lCreaDocumentoOutBean.setData(lStoreResultBean.getResultBean().getTsregout());
		if (pCreaDocumentoInBean != null && pCreaDocumentoInBean.getTipoNumerazioni() != null && pCreaDocumentoInBean.getTipoNumerazioni().size() > 0) {
			TipoNumerazioneBean tipoNumerazione = pCreaDocumentoInBean.getTipoNumerazioni().get(0);
			lCreaDocumentoOutBean.setSigla(tipoNumerazione != null ? tipoNumerazione.getSigla() : null);
		}
	}

	protected void recuperaEstremiInTransaction(AurigaLoginBean pAurigaLoginBean, CreaModDocumentoInBean pCreaDocumentoInBean,
			CreaModDocumentoOutBean lCreaDocumentoOutBean, Session session) throws Exception, StoreException {

		DmpkUtilityGetestremiregnumud_jBean lGetestremiregnumud_jBean = new DmpkUtilityGetestremiregnumud_jBean();
		lGetestremiregnumud_jBean.setIdudin(lCreaDocumentoOutBean.getIdUd());

		final Getestremiregnumud_jImpl store = new Getestremiregnumud_jImpl();
		store.setBean(lGetestremiregnumud_jBean);
		mLogger.debug("Chiamo la getEstremiRegNumUd_j " + lGetestremiregnumud_jBean);

		LoginService lLoginService = new LoginService();
		lLoginService.login(pAurigaLoginBean);
		session.doWork(new Work() {

			@Override
			public void execute(Connection paramConnection) throws SQLException {
				paramConnection.setAutoCommit(false);
				store.execute(paramConnection);
			}
		});

		StoreResultBean<DmpkUtilityGetestremiregnumud_jBean> lStoreResultBean = new StoreResultBean<DmpkUtilityGetestremiregnumud_jBean>();
		AnalyzeResult.analyze(lGetestremiregnumud_jBean, lStoreResultBean);
		lStoreResultBean.setResultBean(lGetestremiregnumud_jBean);

		if (lStoreResultBean.isInError()) {
			throw new StoreException(lStoreResultBean);
		}

		lCreaDocumentoOutBean.setAnno(lStoreResultBean.getResultBean().getAnnoregout());
		lCreaDocumentoOutBean.setNumero(lStoreResultBean.getResultBean().getNumregout());
		lCreaDocumentoOutBean.setData(lStoreResultBean.getResultBean().getTsregout());
		if (pCreaDocumentoInBean != null && pCreaDocumentoInBean.getTipoNumerazioni() != null && pCreaDocumentoInBean.getTipoNumerazioni().size() > 0) {
			TipoNumerazioneBean tipoNumerazione = pCreaDocumentoInBean.getTipoNumerazioni().get(0);
			lCreaDocumentoOutBean.setSigla(tipoNumerazione != null ? tipoNumerazione.getSigla() : null);
		}

	}

	@Operation(name = "versionaDocumento")
	public VersionaDocumentoOutBean versionaDocumento(AurigaLoginBean pAurigaLoginBean, VersionaDocumentoInBean lVersionaDocumentoInBean) throws Exception {
		mLogger.debug("VERSIONA DOCUMENTO");
		VersionaDocumentoOutBean lVersionaDocumentoOutBean = new VersionaDocumentoOutBean();
		try {
			mLogger.debug("idDoc : " + lVersionaDocumentoInBean.getIdDocumento());
			mLogger.debug("posizione : " + lVersionaDocumentoInBean.getPosizione());
			mLogger.debug("path : " + lVersionaDocumentoInBean.getFile().getPath());
			mLogger.debug("tipo : " + lVersionaDocumentoInBean.getInfo().getTipo());
			mLogger.debug("displayFilename : " + lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento().getDisplayFilename());
			mLogger.debug("impronta : " + lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento().getImpronta());
			mLogger.debug("mimetype : " + lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento().getMimetype());
			mLogger.debug("updateVersion : " + lVersionaDocumentoInBean.getUpdateVersion());

			String uriVer = null;
			if (SharePointUtil.isIntegratoConSharePoint(pAurigaLoginBean)) {
				SharePointUtil shUtil = (SharePointUtil) SpringAppContext.getContext().getBean("sharePointUtil");
				uriVer = shUtil.salvaFile(pAurigaLoginBean, lVersionaDocumentoInBean);
			} else {
				mLogger.debug("idDominio : " + pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
				uriVer = DocumentStorage.store(lVersionaDocumentoInBean.getFile(), pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
			}

			FileStoreBean lFileStoreBean = new FileStoreBean();
			lFileStoreBean.setUri(uriVer);
			mLogger.debug("uri salvato : " + uriVer);
			lFileStoreBean.setDimensione(lVersionaDocumentoInBean.getFile().length());
			mLogger.debug("dimensione : " + lVersionaDocumentoInBean.getFile().length());

			try {
				BeanUtilsBean2.getInstance().copyProperties(lFileStoreBean, lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento());
			} catch (Exception e) {
				mLogger.warn(e);
			}

			if (SharePointUtil.isIntegratoConSharePoint(pAurigaLoginBean)) {
				SharePointUtil shUtil = (SharePointUtil) SpringAppContext.getContext().getBean("sharePointUtil");
				List<UrlVersione> uriList = shUtil.getUriPrecentiVersioni(pAurigaLoginBean, uriVer);
				lFileStoreBean.setUriVerPrecSuSharePoint_Ver(uriList);
			}

			String lStringXml = "";
			try {
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				lStringXml = lXmlUtilitySerializer.bindXml(lFileStoreBean);
				mLogger.debug("attributiVerXml : " + lStringXml);
			} catch (Exception e) {
				mLogger.warn(e);
			}

			if (lVersionaDocumentoInBean.getUpdateVersion() != null && lVersionaDocumentoInBean.getUpdateVersion()) {

				DmpkCoreUpdverdocBean lUpdverdocBean = new DmpkCoreUpdverdocBean();
				lUpdverdocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
				lUpdverdocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
				lUpdverdocBean.setIddocin(lVersionaDocumentoInBean.getIdDocumento());
				lUpdverdocBean.setNroprogrverio(null);
				lUpdverdocBean.setAttributixmlin(lStringXml);
				Updverdoc lUpdverdoc = new Updverdoc();
				mLogger.debug("Chiamo la updverdoc " + lUpdverdocBean);

				StoreResultBean<DmpkCoreUpdverdocBean> lUpdverdocStoreResultBean = lUpdverdoc.execute(pAurigaLoginBean, lUpdverdocBean);

				if (lUpdverdocStoreResultBean.isInError()) {

					mLogger.error("Default message: "+ lUpdverdocStoreResultBean.getDefaultMessage());
					mLogger.error("Error context: " + lUpdverdocStoreResultBean.getErrorContext());
					mLogger.error("Error code: " + lUpdverdocStoreResultBean.getErrorCode());

					BeanUtilsBean2.getInstance().copyProperties(lVersionaDocumentoOutBean, lUpdverdocStoreResultBean);
					return lVersionaDocumentoOutBean;
				}

			} else {

				if (lVersionaDocumentoInBean.getAnnullaLastVer() != null && lVersionaDocumentoInBean.getAnnullaLastVer()) {
					mLogger.debug("rimuoviVersioneDocumento()");
					rimuoviVersioneDocumento(lVersionaDocumentoInBean, pAurigaLoginBean);
				}

				DmpkCoreAddverdocBean lAddverdocBean = new DmpkCoreAddverdocBean();
				lAddverdocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
				lAddverdocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
				lAddverdocBean.setIddocin(lVersionaDocumentoInBean.getIdDocumento());
				lAddverdocBean.setAttributiverxmlin(lStringXml);
				Addverdoc lAddverdoc = new Addverdoc();
				mLogger.debug("Chiamo la addVerdoc " + lAddverdocBean);

				StoreResultBean<DmpkCoreAddverdocBean> lAddverdocStoreResultBean = lAddverdoc.execute(pAurigaLoginBean, lAddverdocBean);

				if (lAddverdocStoreResultBean.isInError()) {

					mLogger.error("Default message: "+ lAddverdocStoreResultBean.getDefaultMessage());
					mLogger.error("Error context: " + lAddverdocStoreResultBean.getErrorContext());
					mLogger.error("Error code: " + lAddverdocStoreResultBean.getErrorCode());

					BeanUtilsBean2.getInstance().copyProperties(lVersionaDocumentoOutBean, lAddverdocStoreResultBean);
					return lVersionaDocumentoOutBean;
				}

			}

		} catch (Throwable e) {

			if (StringUtils.isNotBlank(e.getMessage())) {
				mLogger.error(e.getMessage(), e);
				lVersionaDocumentoOutBean.setDefaultMessage(e.getMessage());
			} else {
				mLogger.error("Errore generico", e);
				lVersionaDocumentoOutBean.setDefaultMessage("Errore generico");
			}
			return lVersionaDocumentoOutBean;

		}
		return lVersionaDocumentoOutBean;
	}
	
	//TODO da controllare e sistemare
//	@Operation(name = "versionaDocumentiInTransaction")
	public VersionaDocumentoOutBean versionaDocumentiInTransaction(AurigaLoginBean pAurigaLoginBean, VersionaDocumentiInTransactionInBean lVersionaDocumentiInTransactionInBean) throws Exception {
		mLogger.debug("VERSIONA DOCUMENTO IN TRANSACTION");
		VersionaDocumentoOutBean lVersionaDocumentoOutBean = new VersionaDocumentoOutBean();
		// gestiore delle sessioni 
		try {
			
			SubjectBean subject = SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject);
			Session session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			
			try {
			
				if (lVersionaDocumentiInTransactionInBean != null && lVersionaDocumentiInTransactionInBean.getListaDocumenti() != null) {
					for (VersionaDocumentoInBean lVersionaDocumentoInBean : lVersionaDocumentiInTransactionInBean.getListaDocumenti()) {
						
						mLogger.debug("idDoc : " + lVersionaDocumentoInBean.getIdDocumento());
						mLogger.debug("posizione : " + lVersionaDocumentoInBean.getPosizione());
						mLogger.debug("path : " + lVersionaDocumentoInBean.getFile().getPath());
						mLogger.debug("tipo : " + lVersionaDocumentoInBean.getInfo().getTipo());
						mLogger.debug("displayFilename : " + lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento().getDisplayFilename());
						mLogger.debug("impronta : " + lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento().getImpronta());
						mLogger.debug("mimetype : " + lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento().getMimetype());
						mLogger.debug("updateVersion : " + lVersionaDocumentoInBean.getUpdateVersion());
						
						String uriVer = null;
						if (SharePointUtil.isIntegratoConSharePoint(pAurigaLoginBean)) {
							SharePointUtil shUtil = (SharePointUtil) SpringAppContext.getContext().getBean("sharePointUtil");
							uriVer = shUtil.salvaFile(pAurigaLoginBean, lVersionaDocumentoInBean);
						} else {
							uriVer = DocumentStorage.store(lVersionaDocumentoInBean.getFile(), pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
						}
						mLogger.debug("Salvato " + uriVer);
	
						FileStoreBean lFileStoreBean = new FileStoreBean();
						lFileStoreBean.setUri(uriVer);
						lFileStoreBean.setDimensione(lVersionaDocumentoInBean.getFile().length());
	
						try {
							BeanUtilsBean2.getInstance().copyProperties(lFileStoreBean, lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento());
						} catch (Exception e) {
							mLogger.warn(e);
						}
	
						if (SharePointUtil.isIntegratoConSharePoint(pAurigaLoginBean)) {
							SharePointUtil shUtil = (SharePointUtil) SpringAppContext.getContext().getBean("sharePointUtil");
							List<UrlVersione> uriList = shUtil.getUriPrecentiVersioni(pAurigaLoginBean, uriVer);
							lFileStoreBean.setUriVerPrecSuSharePoint_Ver(uriList);
						}
	
						String lStringXml = "";
						try {
							XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
							lStringXml = lXmlUtilitySerializer.bindXml(lFileStoreBean);
							mLogger.debug("attributiVerXml " + lStringXml);
						} catch (Exception e) {
							mLogger.warn(e);
						}
	
						if (lVersionaDocumentoInBean.getUpdateVersion() != null && lVersionaDocumentoInBean.getUpdateVersion()) {
	
							DmpkCoreUpdverdocBean lUpdverdocBean = new DmpkCoreUpdverdocBean();
							lUpdverdocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
							lUpdverdocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
							lUpdverdocBean.setIddocin(lVersionaDocumentoInBean.getIdDocumento());
							lUpdverdocBean.setNroprogrverio(null);
							lUpdverdocBean.setAttributixmlin(lStringXml);
	
							final UpdverdocImpl store = new UpdverdocImpl();
							store.setBean(lUpdverdocBean);
							mLogger.debug("Chiamo la updverdoc " + lUpdverdocBean);
	
							LoginService lLoginService = new LoginService();
							lLoginService.login(pAurigaLoginBean);
							session.doWork(new Work() {
	
								@Override
								public void execute(Connection paramConnection) throws SQLException {
									paramConnection.setAutoCommit(false);
									store.execute(paramConnection);
								}
							});
	
							StoreResultBean<DmpkCoreUpdverdocBean> lUpdverdocStoreResultBean = new StoreResultBean<DmpkCoreUpdverdocBean>();
							AnalyzeResult.analyze(lUpdverdocBean, lUpdverdocStoreResultBean);
							lUpdverdocStoreResultBean.setResultBean(lUpdverdocBean);
	
							if (lUpdverdocStoreResultBean.isInError()) {
								mLogger.error("Default message: "+ lUpdverdocStoreResultBean.getDefaultMessage());
								mLogger.error("Error context: " + lUpdverdocStoreResultBean.getErrorContext());
								mLogger.error("Error code: " + lUpdverdocStoreResultBean.getErrorCode());
								BeanUtilsBean2.getInstance().copyProperties(lVersionaDocumentoOutBean, lUpdverdocStoreResultBean);
								return lVersionaDocumentoOutBean;
							}
	
						} else {
	
							if (lVersionaDocumentoInBean.getAnnullaLastVer() != null && lVersionaDocumentoInBean.getAnnullaLastVer()) {
								rimuoviVersioneDocumentoInTransaction(lVersionaDocumentoInBean, pAurigaLoginBean, session);
							}
	
							DmpkCoreAddverdocBean lAddverdocBean = new DmpkCoreAddverdocBean();
							lAddverdocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
							lAddverdocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
							lAddverdocBean.setIddocin(lVersionaDocumentoInBean.getIdDocumento());
							lAddverdocBean.setAttributiverxmlin(lStringXml);
	
							final AddverdocImpl store = new AddverdocImpl();
							store.setBean(lAddverdocBean);
							mLogger.debug("Chiamo la addVerdoc " + lAddverdocBean);
	
							LoginService lLoginService = new LoginService();
							lLoginService.login(pAurigaLoginBean);
							session.doWork(new Work() {
	
								@Override
								public void execute(Connection paramConnection) throws SQLException {
									paramConnection.setAutoCommit(false);
									store.execute(paramConnection);
								}
							});
	
							StoreResultBean<DmpkCoreAddverdocBean> lAddverdocStoreResultBean = new StoreResultBean<DmpkCoreAddverdocBean>();
							AnalyzeResult.analyze(lAddverdocBean, lAddverdocStoreResultBean);
							lAddverdocStoreResultBean.setResultBean(lAddverdocBean);
	
							if (lAddverdocStoreResultBean.isInError()) {
								mLogger.error("Default message: "+ lAddverdocStoreResultBean.getDefaultMessage());
								mLogger.error("Error context: " + lAddverdocStoreResultBean.getErrorContext());
								mLogger.error("Error code: " + lAddverdocStoreResultBean.getErrorCode());
								BeanUtilsBean2.getInstance().copyProperties(lVersionaDocumentoOutBean, lAddverdocStoreResultBean);
								return lVersionaDocumentoOutBean;
							}
						}
					}
				}
				session.flush();
				lTransaction.commit();
			} catch (Throwable e) {
				if (StringUtils.isNotBlank(e.getMessage())) {
					mLogger.error(e.getMessage(), e);
					lVersionaDocumentoOutBean.setDefaultMessage(e.getMessage());
				} else {
					mLogger.error("Errore generico", e);
					lVersionaDocumentoOutBean.setDefaultMessage("Errore generico");
				}
				return lVersionaDocumentoOutBean;
			} finally {
				HibernateUtil.release(session);
			}
			
		} catch (Exception e) {
			if (e instanceof StoreException) {
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lVersionaDocumentoOutBean, ((StoreException) e).getError());
				return lVersionaDocumentoOutBean;
			} else {
				mLogger.error("Errore " + e.getMessage(), e);
				lVersionaDocumentoOutBean.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");
				return lVersionaDocumentoOutBean;
			}
		}

		return lVersionaDocumentoOutBean;
	}

	public VersionaDocumentoOutBean versionaDocumentoInTransaction(AurigaLoginBean pAurigaLoginBean, VersionaDocumentoInBean lVersionaDocumentoInBean,
			Session session) throws Exception {
		VersionaDocumentoOutBean lVersionaDocumentoOutBean = new VersionaDocumentoOutBean();
		try {

			String uriVer = null;
			if (SharePointUtil.isIntegratoConSharePoint(pAurigaLoginBean)) {
				SharePointUtil shUtil = (SharePointUtil) SpringAppContext.getContext().getBean("sharePointUtil");
				uriVer = shUtil.salvaFile(pAurigaLoginBean, lVersionaDocumentoInBean);
			} else {
				uriVer = DocumentStorage.store(lVersionaDocumentoInBean.getFile(), pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
			}
			mLogger.debug("Salvato " + uriVer);

			FileStoreBean lFileStoreBean = new FileStoreBean();
			lFileStoreBean.setUri(uriVer);
			lFileStoreBean.setDimensione(lVersionaDocumentoInBean.getFile().length());

			try {
				BeanUtilsBean2.getInstance().copyProperties(lFileStoreBean, lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento());
			} catch (Exception e) {
				mLogger.warn(e);
			}

			if (SharePointUtil.isIntegratoConSharePoint(pAurigaLoginBean)) {
				SharePointUtil shUtil = (SharePointUtil) SpringAppContext.getContext().getBean("sharePointUtil");
				List<UrlVersione> uriList = shUtil.getUriPrecentiVersioni(pAurigaLoginBean, uriVer);
				lFileStoreBean.setUriVerPrecSuSharePoint_Ver(uriList);
			}

			String lStringXml = "";
			try {
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				lStringXml = lXmlUtilitySerializer.bindXml(lFileStoreBean);
				mLogger.debug("attributiVerXml " + lStringXml);
			} catch (Exception e) {
				mLogger.warn(e);
			}

			if (lVersionaDocumentoInBean.getUpdateVersion() != null && lVersionaDocumentoInBean.getUpdateVersion()) {

				DmpkCoreUpdverdocBean lUpdverdocBean = new DmpkCoreUpdverdocBean();
				lUpdverdocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
				lUpdverdocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
				lUpdverdocBean.setIddocin(lVersionaDocumentoInBean.getIdDocumento());
				lUpdverdocBean.setNroprogrverio(null);
				lUpdverdocBean.setAttributixmlin(lStringXml);

				final UpdverdocImpl store = new UpdverdocImpl();
				store.setBean(lUpdverdocBean);
				mLogger.debug("Chiamo la updverdoc " + lUpdverdocBean);

				LoginService lLoginService = new LoginService();
				lLoginService.login(pAurigaLoginBean);
				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						store.execute(paramConnection);
					}
				});

				StoreResultBean<DmpkCoreUpdverdocBean> lUpdverdocStoreResultBean = new StoreResultBean<DmpkCoreUpdverdocBean>();
				AnalyzeResult.analyze(lUpdverdocBean, lUpdverdocStoreResultBean);
				lUpdverdocStoreResultBean.setResultBean(lUpdverdocBean);

				if (lUpdverdocStoreResultBean.isInError()) {
					mLogger.error("Default message: "+ lUpdverdocStoreResultBean.getDefaultMessage());
					mLogger.error("Error context: " + lUpdverdocStoreResultBean.getErrorContext());
					mLogger.error("Error code: " + lUpdverdocStoreResultBean.getErrorCode());
					BeanUtilsBean2.getInstance().copyProperties(lVersionaDocumentoOutBean, lUpdverdocStoreResultBean);
					return lVersionaDocumentoOutBean;
				}

			} else {

				if (lVersionaDocumentoInBean.getAnnullaLastVer() != null && lVersionaDocumentoInBean.getAnnullaLastVer()) {
					rimuoviVersioneDocumentoInTransaction(lVersionaDocumentoInBean, pAurigaLoginBean, session);
				}

				DmpkCoreAddverdocBean lAddverdocBean = new DmpkCoreAddverdocBean();
				lAddverdocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
				lAddverdocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
				lAddverdocBean.setIddocin(lVersionaDocumentoInBean.getIdDocumento());
				lAddverdocBean.setAttributiverxmlin(lStringXml);

				final AddverdocImpl store = new AddverdocImpl();
				store.setBean(lAddverdocBean);
				mLogger.debug("Chiamo la addVerdoc " + lAddverdocBean);

				LoginService lLoginService = new LoginService();
				lLoginService.login(pAurigaLoginBean);
				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						store.execute(paramConnection);
					}
				});

				StoreResultBean<DmpkCoreAddverdocBean> lAddverdocStoreResultBean = new StoreResultBean<DmpkCoreAddverdocBean>();
				AnalyzeResult.analyze(lAddverdocBean, lAddverdocStoreResultBean);
				lAddverdocStoreResultBean.setResultBean(lAddverdocBean);

				if (lAddverdocStoreResultBean.isInError()) {
					mLogger.error("Default message: "+ lAddverdocStoreResultBean.getDefaultMessage());
					mLogger.error("Error context: " + lAddverdocStoreResultBean.getErrorContext());
					mLogger.error("Error code: " + lAddverdocStoreResultBean.getErrorCode());
					BeanUtilsBean2.getInstance().copyProperties(lVersionaDocumentoOutBean, lAddverdocStoreResultBean);
					return lVersionaDocumentoOutBean;
				}
			}
		} catch (Throwable e) {

			if (StringUtils.isNotBlank(e.getMessage())) {
				mLogger.error(e.getMessage(), e);
				lVersionaDocumentoOutBean.setDefaultMessage(e.getMessage());
			} else {
				mLogger.error("Errore generico", e);
				lVersionaDocumentoOutBean.setDefaultMessage("Errore generico");
			}
			return lVersionaDocumentoOutBean;
		}

		return lVersionaDocumentoOutBean;
	}

	private void rimuoviAllegato(RebuildedFile lRebuildedFile, AurigaLoginBean pAurigaLoginBean) throws Exception {
		try {

			DmpkCoreDel_ud_doc_verBean lDel_ud_doc_verBean = new DmpkCoreDel_ud_doc_verBean();
			lDel_ud_doc_verBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
			lDel_ud_doc_verBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
			lDel_ud_doc_verBean.setFlgtipotargetin("D");
			lDel_ud_doc_verBean.setNroprogrverio(null);
			lDel_ud_doc_verBean.setFlgcancfisicain(new Integer(0));
			lDel_ud_doc_verBean.setIduddocin(lRebuildedFile.getIdDocumento());
			Del_ud_doc_ver lDel_ud_doc_ver = new Del_ud_doc_ver();
			mLogger.debug("Chiamo la del_Ud_Doc_Ver");

			StoreResultBean<DmpkCoreDel_ud_doc_verBean> lStoreResultBean = lDel_ud_doc_ver.execute(pAurigaLoginBean, lDel_ud_doc_verBean);
			if (lStoreResultBean.isInError()) {
				throw new StoreException(lStoreResultBean);
			}

		} catch (Exception e) {
			String errorMessage = e.getMessage();
			if (e instanceof StoreException) {
				errorMessage = ((StoreException) e).getError() != null ? ((StoreException) e).getError().getDefaultMessage() : ((StoreException) e)
						.getMessage();
			}
			mLogger.error("Errore " + errorMessage, e);
			throw new Exception(errorMessage);
		}
	}

	public void rimuoviVersioneDocumento(RebuildedFile lRebuildedFile, AurigaLoginBean pAurigaLoginBean) throws Exception {
		try {

			DmpkCoreDel_ud_doc_verBean lDel_ud_doc_verBean = new DmpkCoreDel_ud_doc_verBean();
			lDel_ud_doc_verBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
			lDel_ud_doc_verBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
			lDel_ud_doc_verBean.setFlgtipotargetin("V");
			lDel_ud_doc_verBean.setNroprogrverio(new BigDecimal(-1)); // in questo modo annullo tutte le versioni presenti sula file e non solo l'ultima
			lDel_ud_doc_verBean.setFlgcancfisicain(new Integer(0));
			lDel_ud_doc_verBean.setIduddocin(lRebuildedFile.getIdDocumento());
			Del_ud_doc_ver lDel_ud_doc_ver = new Del_ud_doc_ver();
			mLogger.debug("Chiamo la del_Ud_Doc_Ver " + lDel_ud_doc_verBean);

			StoreResultBean<DmpkCoreDel_ud_doc_verBean> lStoreResultBean = lDel_ud_doc_ver.execute(pAurigaLoginBean, lDel_ud_doc_verBean);
			if (lStoreResultBean.isInError()) {
				throw new StoreException(lStoreResultBean);
			}

		} catch (Exception e) {
			String errorMessage = e.getMessage();
			if (e instanceof StoreException) {
				errorMessage = ((StoreException) e).getError() != null ? ((StoreException) e).getError().getDefaultMessage() : ((StoreException) e)
						.getMessage();
			}
			mLogger.error("Errore " + errorMessage, e);
			throw new Exception(errorMessage);
		}
	}

	public void rimuoviVersioneDocumentoInTransaction(RebuildedFile lRebuildedFile, AurigaLoginBean pAurigaLoginBean, Session session) throws Exception {
		try {

			DmpkCoreDel_ud_doc_verBean lDel_ud_doc_verBean = new DmpkCoreDel_ud_doc_verBean();
			lDel_ud_doc_verBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
			lDel_ud_doc_verBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
			lDel_ud_doc_verBean.setFlgtipotargetin("V");
			lDel_ud_doc_verBean.setNroprogrverio(new BigDecimal(-1)); // in questo modo annullo tutte le versioni presenti sula file e non solo l'ultima
			lDel_ud_doc_verBean.setFlgcancfisicain(new Integer(0));
			lDel_ud_doc_verBean.setIduddocin(lRebuildedFile.getIdDocumento());

			final Del_ud_doc_verImpl store = new Del_ud_doc_verImpl();
			store.setBean(lDel_ud_doc_verBean);
			mLogger.debug("Chiamo la del_Ud_Doc_Ver " + lDel_ud_doc_verBean);

			LoginService lLoginService = new LoginService();
			lLoginService.login(pAurigaLoginBean);
			session.doWork(new Work() {

				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					store.execute(paramConnection);
				}
			});

			StoreResultBean<DmpkCoreDel_ud_doc_verBean> lDel_ud_doc_verStoreResultBean = new StoreResultBean<DmpkCoreDel_ud_doc_verBean>();
			AnalyzeResult.analyze(lDel_ud_doc_verBean, lDel_ud_doc_verStoreResultBean);
			lDel_ud_doc_verStoreResultBean.setResultBean(lDel_ud_doc_verBean);

			if (lDel_ud_doc_verStoreResultBean.isInError()) {
				throw new StoreException(lDel_ud_doc_verStoreResultBean);
			}

		} catch (Exception e) {
			String errorMessage = e.getMessage();
			if (e instanceof StoreException) {
				errorMessage = ((StoreException) e).getError() != null ? ((StoreException) e).getError().getDefaultMessage() : ((StoreException) e)
						.getMessage();
			}
			mLogger.error("Errore " + errorMessage, e);
			throw new Exception(errorMessage);
		}
	}

	@Operation(name = "aggiungiDocumento")
	public AggiungiDocumentoOutBean aggiungiDocumento(AurigaLoginBean pAurigaLoginBean, AggiungiDocumentoInBean pAggiungiDocumentoInBean)
			throws StorageException, FileNotFoundException, IOException, JAXBException {
		String uriVer = DocumentStorage.store(pAggiungiDocumentoInBean.getFile(), pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
		mLogger.debug(uriVer);
		AggiungiDocumentoOutBean outBean = new AggiungiDocumentoOutBean();
		outBean.setUri(uriVer);
		return outBean;
	}

	protected BigDecimal getIdUserLavoro(AurigaLoginBean pAurigaLoginBean) {
		return StringUtils.isNotBlank(pAurigaLoginBean.getIdUserLavoro()) ? new BigDecimal(pAurigaLoginBean.getIdUserLavoro()) : null;
	}

	@Operation(name = "delUdDocVer")
	public DelUdDocVerOut delUdDocVer(AurigaLoginBean pAurigaLoginBean, DelUdDocVerIn pDelUdDocVerIn) throws Exception {

		// popolo input
		DmpkCoreDel_ud_doc_verBean bean = new DmpkCoreDel_ud_doc_verBean();
		bean.setFlgtipotargetin(pDelUdDocVerIn.getFlgTipoTargetIn());
		bean.setIduddocin(StringUtils.isNotBlank(pDelUdDocVerIn.getIdUdDocIn()) ? new BigDecimal(pDelUdDocVerIn.getIdUdDocIn()) : null);
		bean.setFlgcancfisicain(pDelUdDocVerIn.getFlgCancFisicaIn());

		// eseguo il servizio
		Del_ud_doc_ver store = new Del_ud_doc_ver();
		StoreResultBean<DmpkCoreDel_ud_doc_verBean> lStoreResultBean = store.execute(pAurigaLoginBean, bean);

		if (lStoreResultBean.isInError()) {
			mLogger.error("Default message: "+ lStoreResultBean.getDefaultMessage());
			mLogger.error("Error context: " + lStoreResultBean.getErrorContext());
			mLogger.error("Error code: " + lStoreResultBean.getErrorCode());
			DelUdDocVerOut lDelUdDocVerOut = new DelUdDocVerOut();
			BeanUtilsBean2.getInstance().copyProperties(lDelUdDocVerOut, lStoreResultBean);
			return lDelUdDocVerOut;
		}

		// leggo output
		DelUdDocVerOut lDelUdDocVerOut = new DelUdDocVerOut();
		lDelUdDocVerOut.setUriOut(lStoreResultBean.getResultBean().getUrixmlout());
		if (lStoreResultBean.getResultBean().getNroprogrverio() != null)
			lDelUdDocVerOut.setNroProgrVerOut(lStoreResultBean.getResultBean().getNroprogrverio());

		return lDelUdDocVerOut;
	}

	/***********************************************************************************
	 * 
	 * Funzioni utilizzate dai WS
	 * 
	 ************************************************************************************/

	/*
	 * Funzione per aggiungere i file elettronici ad una registrazione fatta da WS
	 * 
	 * @param idUd : id dell'unita documentaria
	 * 
	 * @param idDocPrimario : id del documento
	 * 
	 * @param FilePrimarioBean : info del File Primario
	 * 
	 * @param pAllegatiBean : info dei file allegati
	 * 
	 * @return CreaModDocumentoOutBean :
	 * 
	 * @exception Exception
	 */
//	@Operation(name = "addDocsWS")
	public CreaModDocumentoOutBean addDocsWS(AurigaLoginBean pAurigaLoginBean, String flgTipoFile, String pIdUd, BigDecimal pIdDocFile, AllegatiBean pAllegatiBean, Session session) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		CreaModDocumentoOutBean lModificaDocumentoOutBean = new CreaModDocumentoOutBean();

		BigDecimal idUd = (pIdUd != null) ? new BigDecimal(pIdUd) : null;
		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(idUd);
		
		BigDecimal idDocPrimario = null;
		try {
			idDocPrimario = leggiIdDocPrimarioWSInTransaction(getLocale(), pAurigaLoginBean, pIdUd, session);
		} catch (Exception e) {
			lModificaDocumentoOutBean.setDefaultMessage("Il documento da aggiornare e' inesistente");
			return lModificaDocumentoOutBean;
		}
		// Preparo i files
		List<RebuildedFile> versioni = new ArrayList<RebuildedFile>();
		try {
			
			try {
				manageAddDocsWS(pAurigaLoginBean, flgTipoFile, idDocPrimario, pAllegatiBean, idUd, pIdDocFile, versioni);
				
			} catch (Exception e) {
				if (e instanceof StoreException) {
					BeanUtilsBean2.getInstance().copyProperties(lModificaDocumentoOutBean, ((StoreException) e).getError());
					return lModificaDocumentoOutBean;
				} else {
					lModificaDocumentoOutBean.setDefaultMessage(e.getMessage());
					return lModificaDocumentoOutBean;
				}
			} 
			lModificaDocumentoOutBean.setIdUd(idUd);

			// Parte di versionamento
			Map<String, String> fileErrors = new HashMap<String, String>();
			fileErrors.putAll(aggiungiFilesWSInTransaction(pAurigaLoginBean, versioni, session));
			lModificaDocumentoOutBean.setFileInErrors(fileErrors);

		} catch (Exception e) {
			if (e instanceof StoreException) {
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lModificaDocumentoOutBean, ((StoreException) e).getError());
				return lModificaDocumentoOutBean;
			} else {
				mLogger.error("Errore " + e.getMessage(), e);
				lModificaDocumentoOutBean.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");
				return lModificaDocumentoOutBean;
			}
		}
		return lModificaDocumentoOutBean;
	}

	public void manageAddDocsWS(AurigaLoginBean pAurigaLoginBean, String flgTipoFile, BigDecimal idDocPrimario, AllegatiBean pAllegatiBean, BigDecimal idUd, BigDecimal pIdDocFile, List<RebuildedFile> versioni) throws Exception {

		// PRIMARIO
		if (flgTipoFile.equalsIgnoreCase("P")) {
			RebuildedFile lRebuildedFilePrimario = new RebuildedFile();
			lRebuildedFilePrimario.setIdDocumento(idDocPrimario);

			// Salvo le informazioni del file elettronico
			if (pAllegatiBean.getFileAllegati() != null && pAllegatiBean.getFileAllegati().size() > 0) {
				List<File> lFileAllegati = new ArrayList<File>();
				lFileAllegati = pAllegatiBean.getFileAllegati();
				lRebuildedFilePrimario.setFile(lFileAllegati.get(0));
			}

			// Salvo le info generiche del file elettronico
			if (pAllegatiBean.getInfo() != null && pAllegatiBean.getInfo().size() > 0) {
				List<FileInfoBean> lInfo = new ArrayList<FileInfoBean>();
				lInfo = pAllegatiBean.getInfo();
				lRebuildedFilePrimario.setInfo(lInfo.get(0));
			}

			versioni.add(lRebuildedFilePrimario);
		}

		// ALLEGATO
		else if (flgTipoFile.equalsIgnoreCase("A")) {
			if (pAllegatiBean != null && pAllegatiBean.getIdDocumento() != null && pAllegatiBean.getIdDocumento().size() > 0) {
				
				int count = 1;
				Iterator<File> nextFile = null;
				if (pAllegatiBean.getFileAllegati() != null && pAllegatiBean.getFileAllegati().size() > 0)
					nextFile = pAllegatiBean.getFileAllegati().iterator();

				if (pAllegatiBean.getIdDocumento() != null) {
					for (int i = 0; i < pAllegatiBean.getIdDocumento().size(); i++) {
						String idDocAllegato = (pAllegatiBean.getIdDocumento().get(i) != null) ? pAllegatiBean.getIdDocumento().get(i).longValue() + "" : null;

						RebuildedFile lRebuildedFileAllegato = new RebuildedFile();
						if (nextFile != null)
							lRebuildedFileAllegato.setFile(nextFile.next());

						if (pAllegatiBean.getInfo() != null && pAllegatiBean.getInfo().size() > 0)
							lRebuildedFileAllegato.setInfo(pAllegatiBean.getInfo().get(i));

						lRebuildedFileAllegato.setIdDocumento(new BigDecimal(idDocAllegato));
						lRebuildedFileAllegato.setPosizione(count);

						versioni.add(lRebuildedFileAllegato);

						count++;
					}
				}
			}
		}
	}

	/*
	 * Funzione per aggiongere e/o modificare i file elettronici ad una registrazione fatta da WS
	 * 
	 * @param idUd : id dell'unita documentaria
	 * 
	 * @param idDocPrimario : id del documento
	 * 
	 * @param FilePrimarioBean : info del File Primario
	 * 
	 * @param pAllegatiBean : info dei file allegati
	 * 
	 * @return CreaModDocumentoOutBean :
	 * 
	 * @exception Exception
	 */
//	@Operation(name = "addUpdDocsWS")
	public CreaModDocumentoOutBean addUpdDocsWS(AurigaLoginBean pAurigaLoginBean, String flgTipoFile, String pIdUd, BigDecimal pIdDocFile,
			AllegatiBean pAllegatiBean, Session session) throws JAXBException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		CreaModDocumentoOutBean lModificaDocumentoOutBean = new CreaModDocumentoOutBean();

		BigDecimal idUd = (pIdUd != null && !pIdUd.equalsIgnoreCase("")) ? new BigDecimal(pIdUd) : null;

		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(idUd);
		
		BigDecimal idDocPrimario = null;
		try {
			idDocPrimario = leggiIdDocPrimarioWSInTransaction(getLocale(), pAurigaLoginBean, pIdUd, session);
		} catch (Exception e) {
			lModificaDocumentoOutBean.setDefaultMessage("Il documento da aggiornare e' inesistente");
			return lModificaDocumentoOutBean;
		}
		
		RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean;

		DocumentoXmlOutBean lDocumentoXmlOutBean = null;
		try {
			lRecuperaDocumentoOutBean = lRecuperoDocumenti.loadDocumento(pAurigaLoginBean, lRecuperaDocumentoInBean);
			lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
		} catch (Exception e) {
			mLogger.error("Errore " + e.getMessage(), e);
			lModificaDocumentoOutBean.setDefaultMessage("Il documento da aggiornare e' inesistente");
			return lModificaDocumentoOutBean;
		}

		// Preparo i files
		List<RebuildedFile> versioni = new ArrayList<RebuildedFile>();
		List<RebuildedFile> versioniDaRimuovere = new ArrayList<RebuildedFile>();
		List<RebuildedFile> allegatiDaRimuovere = new ArrayList<RebuildedFile>();

		try {
			
			try {
				manageAddUpdDocsWS(pAurigaLoginBean, flgTipoFile, idDocPrimario, pAllegatiBean, idUd, pIdDocFile, lDocumentoXmlOutBean, versioni, versioniDaRimuovere, allegatiDaRimuovere, session);
				
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				if (e instanceof StoreException) {
					BeanUtilsBean2.getInstance().copyProperties(lModificaDocumentoOutBean, ((StoreException) e).getError());
					return lModificaDocumentoOutBean;
				} else {
					lModificaDocumentoOutBean.setDefaultMessage("Errore nella manageAddUpdDocsWS = " + e.getMessage());
					return lModificaDocumentoOutBean;
				}
			} 
			lModificaDocumentoOutBean.setIdUd(idUd);

			// Parte di versionamento
			Map<String, String> fileErrors = new HashMap<String, String>();
			fileErrors.putAll(aggiungiFilesWSInTransaction(pAurigaLoginBean, versioni, session));
			fileErrors.putAll(rimuoviFilesWS(pAurigaLoginBean, versioniDaRimuovere, allegatiDaRimuovere));
			lModificaDocumentoOutBean.setFileInErrors(fileErrors);

		} catch (Exception e) {
			if (e instanceof StoreException) {
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lModificaDocumentoOutBean, ((StoreException) e).getError());
				return lModificaDocumentoOutBean;
			} else {
				mLogger.error("Errore " + e.getMessage(), e);
				lModificaDocumentoOutBean.setDefaultMessage(e.getMessage() != null ? e.getMessage() : "Errore generico");
				return lModificaDocumentoOutBean;
			}
		}

		return lModificaDocumentoOutBean;
	}

	public void manageAddUpdDocsWS(AurigaLoginBean pAurigaLoginBean, String flgTipoFile, BigDecimal idDocPrimario, AllegatiBean pAllegatiBean, BigDecimal idUd,
			BigDecimal pIdDocFile, DocumentoXmlOutBean pDocumentoXmlOutBean, List<RebuildedFile> versioni, List<RebuildedFile> versioniDaRimuovere,
			List<RebuildedFile> allegatiDaRimuovere, Session session) throws Exception {

		// PRIMARIO
		if (flgTipoFile.equalsIgnoreCase("P")) {

			// calcolo le versioni da MODIFICARE
			if (pAllegatiBean != null) {
				RebuildedFile lRebuildedFilePrimario = new RebuildedFile();
				lRebuildedFilePrimario.setIdDocumento(idDocPrimario);

				// Salvo le informazioni del file elettronico
				if (pAllegatiBean.getFileAllegati() != null && pAllegatiBean.getFileAllegati().size() > 0) {
					List<File> lFileAllegati = new ArrayList<File>();
					lFileAllegati = pAllegatiBean.getFileAllegati();
					lRebuildedFilePrimario.setFile(lFileAllegati.get(0));
				}
				// Salvo le info generiche del file elettronico
				if (pAllegatiBean.getInfo() != null && pAllegatiBean.getInfo().size() > 0) {
					List<FileInfoBean> lInfo = new ArrayList<FileInfoBean>();
					lInfo = pAllegatiBean.getInfo();
					lRebuildedFilePrimario.setInfo(lInfo.get(0));
					lRebuildedFilePrimario.setPosizione(lInfo.get(0).getPosizione());
				}

				if (pDocumentoXmlOutBean.getFilePrimario() != null && StringUtils.isNotBlank(pDocumentoXmlOutBean.getFilePrimario().getUri())) {
					if (isProtocolloRepertorio(pDocumentoXmlOutBean)) {
						lRebuildedFilePrimario.setAnnullaLastVer(true);
												
					} else if (pAllegatiBean.getFlgSostituisciVerPrec() != null && pAllegatiBean.getFlgSostituisciVerPrec().get(0) != null && pAllegatiBean.getFlgSostituisciVerPrec().get(0)) {
						lRebuildedFilePrimario.setUpdateVersion(true);
					}
				}

				versioni.add(lRebuildedFilePrimario);

				// calcolo le versioni da RIMUOVERE
			} else if (pAllegatiBean == null) {
				if (pDocumentoXmlOutBean.getFilePrimario() != null && StringUtils.isNotBlank(pDocumentoXmlOutBean.getFilePrimario().getUri())) {
					RebuildedFile lRebuildedFilePrimario2 = new RebuildedFile();
					lRebuildedFilePrimario2.setIdDocumento(idDocPrimario);
					FileInfoBean info = new FileInfoBean();
					info.setTipo(TipoFile.PRIMARIO);
					GenericFile allegatoRiferimento = new GenericFile();
					allegatoRiferimento.setDisplayFilename(pDocumentoXmlOutBean.getFilePrimario().getDisplayFilename());
					info.setAllegatoRiferimento(allegatoRiferimento);
					lRebuildedFilePrimario2.setInfo(info);
					versioniDaRimuovere.add(lRebuildedFilePrimario2);
				}
			}
		}

		// ALLEGATO
		else if (flgTipoFile.equalsIgnoreCase("A")) {
			if (pAllegatiBean != null && pAllegatiBean.getIdDocumento() != null && pAllegatiBean.getIdDocumento().size() > 0) {
				HashMap<String, AllegatiOutBean> allegatiMap = new HashMap<String, AllegatiOutBean>();
				int pos = 1;
				for (AllegatiOutBean allegato : pDocumentoXmlOutBean.getAllegati()) {
					boolean trovato = false;
					for (int i = 0; i < pAllegatiBean.getIdDocumento().size(); i++) {
						String idDocAllegato = (pAllegatiBean.getIdDocumento().get(i) != null) ? pAllegatiBean.getIdDocumento().get(i).longValue() + "" : null;
						if (idDocAllegato != null && idDocAllegato.equals(allegato.getIdDoc())) {
							trovato = true;
							allegatiMap.put(idDocAllegato, allegato);
							break;
						}
					}
					/*
					if (!trovato) {
						RebuildedFile lRebuildedFileAllegato = new RebuildedFile();
						lRebuildedFileAllegato.setIdDocumento(new BigDecimal(allegato.getIdDoc()));
						FileInfoBean info = new FileInfoBean();
						info.setTipo(TipoFile.ALLEGATO);
						GenericFile allegatoRiferimento = new GenericFile();
						allegatoRiferimento.setDisplayFilename(allegato.getDisplayFileName());
						info.setAllegatoRiferimento(allegatoRiferimento);
						info.setPosizione(pos);
						lRebuildedFileAllegato.setInfo(info);
						lRebuildedFileAllegato.setPosizione(pos);
						allegatiDaRimuovere.add(lRebuildedFileAllegato);
					}
					pos++;
					*/
				}

				int count = 1;
				Iterator<File> nextFile = null;
				if (pAllegatiBean.getFileAllegati() != null)
					nextFile = pAllegatiBean.getFileAllegati().iterator();

				if (pAllegatiBean.getIdDocumento() != null) {

					for (int i = 0; i < pAllegatiBean.getIdDocumento().size(); i++) {

						String idDocAllegato = (pAllegatiBean.getIdDocumento().get(i) != null) ? pAllegatiBean.getIdDocumento().get(i).longValue() + "" : null;

						// calcolo le versioni da MODIFICARE o RIMUOVERE
						if (idDocAllegato != null) {

							// calcolo le versioni da MODIFICARE
							if (!pAllegatiBean.getIsNull().get(i) && pAllegatiBean.getIsNewOrChanged().get(i)) {
								RebuildedFile lRebuildedFileAllegato2 = new RebuildedFile();
								if (nextFile != null)
									lRebuildedFileAllegato2.setFile(nextFile.next());

								if (pAllegatiBean.getInfo() != null && pAllegatiBean.getInfo().size() > 0)
									lRebuildedFileAllegato2.setInfo(pAllegatiBean.getInfo().get(i));

								lRebuildedFileAllegato2.setIdDocumento(new BigDecimal(idDocAllegato));
								lRebuildedFileAllegato2.setPosizione(count);

								for (AllegatiOutBean allegato : pDocumentoXmlOutBean.getAllegati()) {
									if (allegato.getIdDoc().equals(idDocAllegato)) {
										if (allegato.getUri() != null && StringUtils.isNotBlank(allegato.getUri())) {
											if (isProtocolloRepertorio(pDocumentoXmlOutBean)) {
												lRebuildedFileAllegato2.setAnnullaLastVer(true);
											} else if (pAllegatiBean.getFlgSostituisciVerPrec().get(i) != null
													&& pAllegatiBean.getFlgSostituisciVerPrec().get(i)) {
												lRebuildedFileAllegato2.setUpdateVersion(true);
											}
										}
									}
								}

								versioni.add(lRebuildedFileAllegato2);
							}

							// calcolo le versioni da RIMUOVERE
							else if (pAllegatiBean.getIsNull().get(i)) {
								for (AllegatiOutBean allegato : pDocumentoXmlOutBean.getAllegati()) {
									if (allegato.getIdDoc().equals(idDocAllegato)) {
										if (allegato.getUri() != null && StringUtils.isNotBlank(allegato.getUri())) {
											RebuildedFile lRebuildedFileAllegato3 = new RebuildedFile();
											lRebuildedFileAllegato3.setIdDocumento(new BigDecimal(idDocAllegato));
											FileInfoBean info = new FileInfoBean();
											info.setTipo(TipoFile.ALLEGATO);
											GenericFile allegatoRiferimento = new GenericFile();
											allegatoRiferimento.setDisplayFilename(allegato.getDisplayFileName());
											info.setAllegatoRiferimento(allegatoRiferimento);
											info.setPosizione(count);
											lRebuildedFileAllegato3.setInfo(info);
											lRebuildedFileAllegato3.setPosizione(count);
											versioniDaRimuovere.add(lRebuildedFileAllegato3);
										}
									}
								}
							}

							count++;
						}
						// calcolo le versioni da AGGIUNGERE
						else {
							if (!pAllegatiBean.getIsNull().get(i)) {
								RebuildedFile lRebuildedFile = new RebuildedFile();
								lRebuildedFile.setFile(nextFile.next());
								lRebuildedFile.setInfo(pAllegatiBean.getInfo().get(i));
								lRebuildedFile.setIdDocumento(new BigDecimal(idDocAllegato));
								lRebuildedFile.setPosizione(count);
								versioni.add(lRebuildedFile);
							}
							count++;
						}
					}
				}
			}
		}
	}

	protected Map<String, String> aggiungiFilesWS(AurigaLoginBean pAurigaLoginBean, List<RebuildedFile> versioni) {
		Map<String, String> fileErrors = new HashMap<String, String>();
		for (RebuildedFile lRebuildedFile : versioni) {
			try {
				VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);
				VersionaDocumentoOutBean lVersionaDocumentoOutBean = versionaDocumentoWS(pAurigaLoginBean, lVersionaDocumentoInBean);
				if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {
					throw new Exception(lVersionaDocumentoOutBean.getDefaultMessage());
				}
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				if (lRebuildedFile.getInfo().getTipo() == TipoFile.PRIMARIO) {
					fileErrors.put("0", "Il file primario " + lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename()
							+ " non è stato salvato correttamente." + (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
				} else if (lRebuildedFile.getInfo().getTipo() == TipoFile.ALLEGATO) {
					fileErrors.put("" + lRebuildedFile.getInfo().getPosizione(), "Il file allegato "
							+ lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename() + " in posizione "
							+ lRebuildedFile.getInfo().getPosizione() + " non è stato salvato correttamente."
							+ (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
				}
			}
		}
		return fileErrors;
	}
	
	protected Map<String, String> aggiungiFilesWSInTransaction(AurigaLoginBean pAurigaLoginBean, List<RebuildedFile> versioni, Session session) {
		Map<String, String> fileErrors = new HashMap<String, String>();
		for (RebuildedFile lRebuildedFile : versioni) {
			try {
				VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
				BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);
				VersionaDocumentoOutBean lVersionaDocumentoOutBean = versionaDocumentoWSInTransaction(pAurigaLoginBean, lVersionaDocumentoInBean, session);
				if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {
					throw new Exception(lVersionaDocumentoOutBean.getDefaultMessage());
				}
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				if (lRebuildedFile.getInfo().getTipo() == TipoFile.PRIMARIO) {
					fileErrors.put("0", "Il file primario " + lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename()
							+ " non è stato salvato correttamente." + (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
				} else if (lRebuildedFile.getInfo().getTipo() == TipoFile.ALLEGATO) {
					fileErrors.put("" + lRebuildedFile.getInfo().getPosizione(), "Il file allegato "
							+ lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename() + " in posizione "
							+ lRebuildedFile.getInfo().getPosizione() + " non è stato salvato correttamente."
							+ (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
				}
			}
		}
		return fileErrors;
	}

	protected Map<String, String> rimuoviFilesWS(AurigaLoginBean pAurigaLoginBean, List<RebuildedFile> versioniDaRimuovere,
			List<RebuildedFile> allegatiDaRimuovere) {

		Map<String, String> fileErrors = new HashMap<String, String>();

		for (RebuildedFile lRebuildedFile : versioniDaRimuovere) {
			try {
				rimuoviVersioneDocumento(lRebuildedFile, pAurigaLoginBean);
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				if (lRebuildedFile.getInfo().getTipo() == TipoFile.PRIMARIO) {
					fileErrors.put("0", "Il file primario " + lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename()
							+ " non è stato eliminato." + (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
				} else if (lRebuildedFile.getInfo().getTipo() == TipoFile.ALLEGATO) {
					fileErrors.put("" + lRebuildedFile.getInfo().getPosizione(),
							"Il file allegato " + lRebuildedFile.getInfo().getAllegatoRiferimento().getDisplayFilename() + " in posizione "
									+ lRebuildedFile.getInfo().getPosizione() + " non è stato eliminato."
									+ (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
				}

			}
		}

		for (RebuildedFile lRebuildedFile : allegatiDaRimuovere) {
			try {
				rimuoviAllegato(lRebuildedFile, pAurigaLoginBean);
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				fileErrors.put("" + lRebuildedFile.getInfo().getPosizione(), "L'allegato con id. " + lRebuildedFile.getIdDocumento()
						+ " non è stato eliminato." + (StringUtils.isNotBlank(e.getMessage()) ? " Motivo: " + e.getMessage() : ""));
			}
		}

		return fileErrors;
	}

	@Operation(name = "versionaDocumentoWS")
	public VersionaDocumentoOutBean versionaDocumentoWS(AurigaLoginBean pAurigaLoginBean, VersionaDocumentoInBean lVersionaDocumentoInBean) throws Exception {
		mLogger.debug("VERSIONA DOCUMENTO");
		VersionaDocumentoOutBean lVersionaDocumentoOutBean = new VersionaDocumentoOutBean();
		try {
			mLogger.debug("idDoc : " + lVersionaDocumentoInBean.getIdDocumento());
			mLogger.debug("posizione : " + lVersionaDocumentoInBean.getPosizione());
			mLogger.debug("path : " + lVersionaDocumentoInBean.getFile().getPath());
			mLogger.debug("tipo : " + lVersionaDocumentoInBean.getInfo().getTipo());
			mLogger.debug("displayFilename : " + lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento().getDisplayFilename());
			mLogger.debug("impronta : " + lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento().getImpronta());
			mLogger.debug("mimetype : " + lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento().getMimetype());
			mLogger.debug("updateVersion : " + lVersionaDocumentoInBean.getUpdateVersion());

			String uriVer = null;
			if (SharePointUtil.isIntegratoConSharePoint(pAurigaLoginBean)) {
				SharePointUtil shUtil = (SharePointUtil) SpringAppContext.getContext().getBean("sharePointUtil");
				uriVer = shUtil.salvaFile(pAurigaLoginBean, lVersionaDocumentoInBean);
			} else {
				mLogger.debug("idDominio : " + pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
				uriVer = DocumentStorage.store(lVersionaDocumentoInBean.getFile(), pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
			}

			FileStoreBean lFileStoreBean = new FileStoreBean();
			lFileStoreBean.setUri(uriVer);
			mLogger.debug("uri salvato : " + uriVer);
			lFileStoreBean.setDimensione(lVersionaDocumentoInBean.getFile().length());
			mLogger.debug("dimensione : " + lVersionaDocumentoInBean.getFile().length());

			try {
				if (lVersionaDocumentoInBean.getInfo() != null && lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento() != null)
					BeanUtilsBean2.getInstance().copyProperties(lFileStoreBean, lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento());

			} catch (Exception e) {
				if (e instanceof StoreException) {
					mLogger.error(e.getMessage(), e);
					BeanUtilsBean2.getInstance().copyProperties(lVersionaDocumentoOutBean, ((StoreException) e).getError());
					return lVersionaDocumentoOutBean;
				} else {
					if (StringUtils.isNotBlank(e.getMessage())) {
						mLogger.error(e.getMessage(), e);
						lVersionaDocumentoOutBean.setDefaultMessage(e.getMessage());
					} else {
						mLogger.error("Errore generico", e);
						lVersionaDocumentoOutBean.setDefaultMessage("Errore generico");
					}
					return lVersionaDocumentoOutBean;
				}
			}

			if (SharePointUtil.isIntegratoConSharePoint(pAurigaLoginBean)) {
				SharePointUtil shUtil = (SharePointUtil) SpringAppContext.getContext().getBean("sharePointUtil");
				List<UrlVersione> uriList = shUtil.getUriPrecentiVersioni(pAurigaLoginBean, uriVer);
				lFileStoreBean.setUriVerPrecSuSharePoint_Ver(uriList);
			}

			String lStringXml = "";
			try {
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				lStringXml = lXmlUtilitySerializer.bindXml(lFileStoreBean);
				mLogger.debug("attributiVerXml : " + lStringXml);
			} catch (Exception e) {
				mLogger.warn(e);
			}

			if (lVersionaDocumentoInBean.getUpdateVersion() != null && lVersionaDocumentoInBean.getUpdateVersion()) {

				DmpkCoreUpdverdocBean lUpdverdocBean = new DmpkCoreUpdverdocBean();
				lUpdverdocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
				lUpdverdocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
				lUpdverdocBean.setIddocin(lVersionaDocumentoInBean.getIdDocumento());
				lUpdverdocBean.setNroprogrverio(null);
				lUpdverdocBean.setAttributixmlin(lStringXml);
				Updverdoc lUpdverdoc = new Updverdoc();
				mLogger.debug("Chiamo la updverdoc " + lUpdverdocBean);

				StoreResultBean<DmpkCoreUpdverdocBean> lUpdverdocStoreResultBean = lUpdverdoc.execute(pAurigaLoginBean, lUpdverdocBean);

				if (lUpdverdocStoreResultBean.isInError()) {
					throw new StoreException(lUpdverdocStoreResultBean);
				}

			} else {

				if (lVersionaDocumentoInBean.getAnnullaLastVer() != null && lVersionaDocumentoInBean.getAnnullaLastVer()) {
					mLogger.debug("rimuoviVersioneDocumento()");
					rimuoviVersioneDocumento(lVersionaDocumentoInBean, pAurigaLoginBean);
				}

				DmpkCoreAddverdocBean lAddverdocBean = new DmpkCoreAddverdocBean();
				lAddverdocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
				lAddverdocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
				lAddverdocBean.setIddocin(lVersionaDocumentoInBean.getIdDocumento());
				lAddverdocBean.setAttributiverxmlin(lStringXml);
				Addverdoc lAddverdoc = new Addverdoc();
				mLogger.debug("Chiamo la addVerdoc " + lAddverdocBean);

				StoreResultBean<DmpkCoreAddverdocBean> lAddverdocStoreResultBean = lAddverdoc.execute(pAurigaLoginBean, lAddverdocBean);

				if (lAddverdocStoreResultBean.isInError()) {
					throw new StoreException(lAddverdocStoreResultBean);
				}

			}

		} catch (Throwable e) {
			if (e instanceof StoreException) {
				mLogger.error(e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lVersionaDocumentoOutBean, ((StoreException) e).getError());
				return lVersionaDocumentoOutBean;
			} else {
				if (StringUtils.isNotBlank(e.getMessage())) {
					mLogger.error(e.getMessage(), e);
					lVersionaDocumentoOutBean.setDefaultMessage(e.getMessage());
				} else {
					mLogger.error("Errore generico", e);
					lVersionaDocumentoOutBean.setDefaultMessage("Errore generico");
				}
				return lVersionaDocumentoOutBean;
			}
		}
		return lVersionaDocumentoOutBean;
	}
	
	public VersionaDocumentoOutBean versionaDocumentoWSInTransaction(AurigaLoginBean pAurigaLoginBean, VersionaDocumentoInBean lVersionaDocumentoInBean, Session session) throws Exception {
		
		mLogger.debug("VERSIONA DOCUMENTO");		
		VersionaDocumentoOutBean lVersionaDocumentoOutBean = new VersionaDocumentoOutBean();
		try {
			mLogger.debug("idDoc : " + lVersionaDocumentoInBean.getIdDocumento());
			mLogger.debug("posizione : " + lVersionaDocumentoInBean.getPosizione());
			mLogger.debug("path : " + lVersionaDocumentoInBean.getFile().getPath());
			mLogger.debug("tipo : " + lVersionaDocumentoInBean.getInfo().getTipo());
			mLogger.debug("displayFilename : " + lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento().getDisplayFilename());
			mLogger.debug("impronta : " + lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento().getImpronta());
			mLogger.debug("mimetype : " + lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento().getMimetype());
			mLogger.debug("updateVersion : " + lVersionaDocumentoInBean.getUpdateVersion());

			String uriVer = null;
			if (SharePointUtil.isIntegratoConSharePoint(pAurigaLoginBean)) {
				SharePointUtil shUtil = (SharePointUtil) SpringAppContext.getContext().getBean("sharePointUtil");
				uriVer = shUtil.salvaFile(pAurigaLoginBean, lVersionaDocumentoInBean);
			} else {
				mLogger.debug("idDominio : " + pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
				uriVer = DocumentStorage.store(lVersionaDocumentoInBean.getFile(), pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
			}

			FileStoreBean lFileStoreBean = new FileStoreBean();
			lFileStoreBean.setUri(uriVer);
			mLogger.debug("uri salvato : " + uriVer);
			lFileStoreBean.setDimensione(lVersionaDocumentoInBean.getFile().length());
			mLogger.debug("dimensione : " + lVersionaDocumentoInBean.getFile().length());

			try {
				
				if (lVersionaDocumentoInBean.getInfo() != null && lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento() != null){
					BeanUtilsBean2.getInstance().copyProperties(lFileStoreBean, lVersionaDocumentoInBean.getInfo().getAllegatoRiferimento());
				}
			} catch (Exception e) {
				if (e instanceof StoreException) {
					mLogger.error(e.getMessage(), e);
					BeanUtilsBean2.getInstance().copyProperties(lVersionaDocumentoOutBean, ((StoreException) e).getError());
					return lVersionaDocumentoOutBean;
				} else {
					if (StringUtils.isNotBlank(e.getMessage())) {
						mLogger.error(e.getMessage(), e);
						lVersionaDocumentoOutBean.setDefaultMessage(e.getMessage());
					} else {
						mLogger.error("Errore generico", e);
						lVersionaDocumentoOutBean.setDefaultMessage("Errore generico");
					}
					return lVersionaDocumentoOutBean;
				}
			}

			if (SharePointUtil.isIntegratoConSharePoint(pAurigaLoginBean)) {
				SharePointUtil shUtil = (SharePointUtil) SpringAppContext.getContext().getBean("sharePointUtil");
				List<UrlVersione> uriList = shUtil.getUriPrecentiVersioni(pAurigaLoginBean, uriVer);
				lFileStoreBean.setUriVerPrecSuSharePoint_Ver(uriList);
			}

			String lStringXml = "";
			try {
				XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
				lStringXml = lXmlUtilitySerializer.bindXml(lFileStoreBean);
				mLogger.debug("attributiVerXml : " + lStringXml);
			} catch (Exception e) {
				mLogger.warn(e);
			}

			if (lVersionaDocumentoInBean.getUpdateVersion() != null && lVersionaDocumentoInBean.getUpdateVersion()) {

				// Inizializzo input
				DmpkCoreUpdverdocBean lUpdverdocBean = new DmpkCoreUpdverdocBean();
				lUpdverdocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
				lUpdverdocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
				lUpdverdocBean.setIddocin(lVersionaDocumentoInBean.getIdDocumento());
				lUpdverdocBean.setNroprogrverio(null);
				lUpdverdocBean.setAttributixmlin(lStringXml);
				
				// Eseguo la store
				final UpdverdocImpl store = new UpdverdocImpl();
				store.setBean(lUpdverdocBean);
				
				LoginService lLoginService = new LoginService();
				lLoginService.login(pAurigaLoginBean);
				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						store.execute(paramConnection);
					}
				});

				StoreResultBean<DmpkCoreUpdverdocBean> lUpdverdocStoreResultBean = new StoreResultBean<DmpkCoreUpdverdocBean>();
				AnalyzeResult.analyze(lUpdverdocBean, lUpdverdocStoreResultBean);
				lUpdverdocStoreResultBean.setResultBean(lUpdverdocBean);

				if (lUpdverdocStoreResultBean.isInError()) {
					throw new StoreException(lUpdverdocStoreResultBean);
				}

			} else {

				if (lVersionaDocumentoInBean.getAnnullaLastVer() != null && lVersionaDocumentoInBean.getAnnullaLastVer()) {
					rimuoviVersioneDocumentoInTransaction(lVersionaDocumentoInBean, pAurigaLoginBean, session);
				}

				DmpkCoreAddverdocBean lAddverdocBean = new DmpkCoreAddverdocBean();
				lAddverdocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
				lAddverdocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
				lAddverdocBean.setIddocin(lVersionaDocumentoInBean.getIdDocumento());
				lAddverdocBean.setAttributiverxmlin(lStringXml);
				
				final AddverdocImpl store = new AddverdocImpl();
				store.setBean(lAddverdocBean);
				mLogger.debug("Chiamo la addVerdoc " + lAddverdocBean);

				LoginService lLoginService = new LoginService();
				lLoginService.login(pAurigaLoginBean);
				session.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						store.execute(paramConnection);
					}
				});

				StoreResultBean<DmpkCoreAddverdocBean> lAddverdocStoreResultBean = new StoreResultBean<DmpkCoreAddverdocBean>();
				AnalyzeResult.analyze(lAddverdocBean, lAddverdocStoreResultBean);
				lAddverdocStoreResultBean.setResultBean(lAddverdocBean);
							
				if (lAddverdocStoreResultBean.isInError()) {
					throw new StoreException(lAddverdocStoreResultBean);
				}

			}

		} catch (Throwable e) {
			if (e instanceof StoreException) {
				mLogger.error(e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lVersionaDocumentoOutBean, ((StoreException) e).getError());
				return lVersionaDocumentoOutBean;
			} else {
				if (StringUtils.isNotBlank(e.getMessage())) {
					mLogger.error(e.getMessage(), e);
					lVersionaDocumentoOutBean.setDefaultMessage(e.getMessage());
				} else {
					mLogger.error("Errore generico", e);
					lVersionaDocumentoOutBean.setDefaultMessage("Errore generico");
				}
				return lVersionaDocumentoOutBean;
			}
		}
		return lVersionaDocumentoOutBean;
	}

	@Operation(name = "leggiIdDocPrimarioWS")
	public BigDecimal leggiIdDocPrimarioWS(AurigaLoginBean pAurigaLoginBean, String pIdUd) throws Exception {
		BigDecimal idUd = (pIdUd != null) ? new BigDecimal(pIdUd) : null;
		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(idUd);
		RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean;
		DocumentoXmlOutBean lDocumentoXmlOutBean = null;
		try {
			lRecuperaDocumentoOutBean = lRecuperoDocumenti.loadDocumento(pAurigaLoginBean, lRecuperaDocumentoInBean);
			if (lRecuperaDocumentoOutBean.isInError()){
				String errMessage = "StoreName = " + lRecuperaDocumentoOutBean.getStoreName() + ", ErrorContext = " + lRecuperaDocumentoOutBean.getErrorContext() + ", ErroreCode = " + lRecuperaDocumentoOutBean.getErrorCode() + ", defaultMessage = " + lRecuperaDocumentoOutBean.getDefaultMessage();
				throw new Exception(errMessage);
			}
			lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
		} catch (Exception e) {
			throw new Exception("Impossibile reperire l'idDoc del documento primario. ERRORE = " + e.getMessage());
		}
		BigDecimal pIdDocPrimarioOut = (lDocumentoXmlOutBean.getIdDocPrimario() != null) ? new BigDecimal(lDocumentoXmlOutBean.getIdDocPrimario()) : null;

		return pIdDocPrimarioOut;
	}
	
	

	
//	@Operation(name = "leggiIdDocPrimarioWSInTransaction")
	public BigDecimal leggiIdDocPrimarioWSInTransaction(Locale localeIn ,AurigaLoginBean loginBeanIn, String idUdIn,  Session sessionIn) throws Exception{
	
		BigDecimal idUd = (idUdIn != null) ? new BigDecimal(idUdIn) : null;
		BigDecimal result = null;
	
		mLogger.debug("*");
		mLogger.debug("****************************************************************************");
		mLogger.debug("*");
		mLogger.debug("*  inizio GestioneDocumenti.leggiIdDocPrimarioWS() ");
		mLogger.debug("*");
		mLogger.debug("****************************************************************************");
		mLogger.debug("*");
		mLogger.debug("Recupero l'iddocprimario con idUd " + idUdIn);
	
		try {		
			
				// Inizializzo lo schema
				SubjectBean subject = SubjectUtil.subject.get();
				if (subject == null) {
					SubjectUtil.subject = new InheritableThreadLocal<SubjectBean>();
					subject = new SubjectBean();
				}
				subject.setIdDominio(loginBeanIn.getSchema());
				subject.setTokenid(loginBeanIn.getToken());
				SubjectUtil.subject.set(subject);
				SchemaBean lSchemaBean = new SchemaBean();
				lSchemaBean.setSchema(loginBeanIn.getSchema());
								
				// inizializzo input
				DmpkUtilityGetiddocprimarioallegatofromudBean inputBean = new DmpkUtilityGetiddocprimarioallegatofromudBean();
				inputBean.setIdudin(idUd);
				inputBean.setNrodocvsudin(new Integer(0));

				LoginService lLoginService = new LoginService();
				lLoginService.login(loginBeanIn);
				
	            // Eseguo la store
				final GetiddocprimarioallegatofromudImpl store = new GetiddocprimarioallegatofromudImpl();
				store.setBean(inputBean);

				sessionIn.doWork(new Work() {

					@Override
					public void execute(Connection paramConnection) throws SQLException {
						paramConnection.setAutoCommit(false);
						store.execute(paramConnection);
					}
				});
				
				
				StoreResultBean<DmpkUtilityGetiddocprimarioallegatofromudBean> resultStore = new StoreResultBean<DmpkUtilityGetiddocprimarioallegatofromudBean>();
				AnalyzeResult.analyze(inputBean, resultStore);
				resultStore.setResultBean(inputBean);
				
				// leggo il risultato		
				BigDecimal errore = new BigDecimal(-1);
				result = resultStore.getResultBean().getParametro_1();
				
				if(result== null){
					throw new Exception("Errore nella leggiIdDocPrimarioWS(). Il result della store Dmpk_Utility->Getiddocprimarioallegatofromud e' null");
				}
				
				if (result.compareTo(errore) == 0){
					throw new Exception("Errore nella leggiIdDocPrimarioWS(). No esiste un id doc primario associato all'id ud :" +idUdIn);
				}		
	}
	catch (Exception e) {
		String errMsg = e.getMessage();
		mLogger.error(errMsg);
		throw new Exception (errMsg);
	}
			
	return result;
  }
	
	
	
	@Operation(name = "leggiIdUDOfDocWS")
	public BigDecimal leggiIdUDOfDocWS(AurigaLoginBean pAurigaLoginBean, String pIdDoc) throws Exception {
		BigDecimal idDoc = (pIdDoc != null) ? new BigDecimal(pIdDoc) : null;
		DmpkUtilityGetidudofdocBean lGetidudofdocBean = new DmpkUtilityGetidudofdocBean();
		lGetidudofdocBean.setIddocin(idDoc);
		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(pAurigaLoginBean.getSchema());
		Getidudofdoc lGetidudofdoc = new Getidudofdoc();
		StoreResultBean<DmpkUtilityGetidudofdocBean> lStoreResultBean = lGetidudofdoc.execute(lSchemaBean, lGetidudofdocBean);
		if (lStoreResultBean.isInError()) {
			throw new StoreException(lStoreResultBean);
		}
		lStoreResultBean.getResultBean().getParametro_1();
		BigDecimal pIdUdOut = (lStoreResultBean.getResultBean().getParametro_1() != null) ? new BigDecimal(lStoreResultBean.getResultBean().getParametro_1())
				: null;
		return pIdUdOut;
	}


	public Locale getLocale(){
		return new Locale("it", "IT");
	}
	
}
