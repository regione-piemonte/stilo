/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityGetestremiregnumud_jBean;
import it.eng.auriga.database.store.dmpk_utility.store.impl.Getestremiregnumud_jImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.login.service.LoginService;
import it.eng.aurigamailbusiness.bean.CheckLockOutBean;
import it.eng.aurigamailbusiness.bean.InfoProtocolloBean;
import it.eng.aurigamailbusiness.bean.InfoRelazioneProtocolloBean;
import it.eng.aurigamailbusiness.bean.LockBean;
import it.eng.aurigamailbusiness.bean.MailLoginBean;
import it.eng.aurigamailbusiness.bean.ProtocolloAttachmentBean;
import it.eng.aurigamailbusiness.bean.RegistrazioneProtocollo;
import it.eng.aurigamailbusiness.bean.RegistrazioneProtocolloBean;
import it.eng.aurigamailbusiness.bean.ResultBean;
import it.eng.aurigamailbusiness.bean.StatoConfermaAutomaticaBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.client.AurigaMailService;
import it.eng.client.CheckService;
import it.eng.client.MailProcessorService;
import it.eng.converter.DateConverter;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.function.bean.AllegatiBean;
import it.eng.document.function.bean.AttachAndPosizioneBean;
import it.eng.document.function.bean.AttachAndPosizioneCollectionBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.CreaModDocumentoOutBean;
import it.eng.document.function.bean.EmailProvBean;
import it.eng.document.function.bean.FilePrimarioBean;
import it.eng.document.function.bean.MailDocumentoIn;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.storeutil.AnalyzeResult;
import it.eng.xml.XmlUtilitySerializer;

@Service(name = "GestioneEmail")
public class GestioneEmail {

	private static Logger mLogger = Logger.getLogger(GestioneEmail.class);

	Locale locale = new Locale("it");

	@Operation(name = "creaDocumento")
	public CreaModDocumentoOutBean creaDocumento(AurigaLoginBean pAurigaLoginBean, MailDocumentoIn pMailDocumentoIn,
			FilePrimarioBean pFilePrimarioBean, AllegatiBean pAllegatiBean,
			AttachAndPosizioneCollectionBean pAttachAndPosizioneCollectionBean) throws Exception {

		ConvertUtils.register(new DateConverter(), Date.class);
		mLogger.debug("Chiesta la protocollazione della mail " + pMailDocumentoIn.getIdEmail());

		CreaModDocumentoOutBean lCreaDocumentoOutBean = new CreaModDocumentoOutBean();
		if (mailLockata(pMailDocumentoIn.getIdEmail(), pAurigaLoginBean)) {
			lCreaDocumentoOutBean.setDefaultMessage("Operazione non consentita: sull'email sta lavorando un altro utente");
			lCreaDocumentoOutBean.setErrorCode(-100);
			lCreaDocumentoOutBean.setErrorContext("Lock");
			return lCreaDocumentoOutBean;
		}
		if (mailProtocollata(pMailDocumentoIn.getIdEmail(), pAurigaLoginBean)) {
			lCreaDocumentoOutBean.setDefaultMessage("E-mail già protocollata");
			lCreaDocumentoOutBean.setErrorCode(-100);
			lCreaDocumentoOutBean.setErrorContext("Protocollazione");
			return lCreaDocumentoOutBean;
		}

		DmpkCoreAdddocBean lAdddocBean = new DmpkCoreAdddocBean();
		lAdddocBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
		lAdddocBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		CreaModDocumentoInBean lCreaDocumentoInBean = new CreaModDocumentoInBean();
		BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lCreaDocumentoInBean, pMailDocumentoIn);

		EmailProvBean emailProv = new EmailProvBean();
		emailProv.setId(pMailDocumentoIn.getIdEmail());
		lCreaDocumentoInBean.setEmailProv(emailProv);

		lAdddocBean.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lCreaDocumentoInBean));

		// Tolgo l'autocommit a 1 sulla prima AddDoc del primario che potrebbe essere la causa del ritardo tra inserimento primario e inserimento primo allegato quando protocolliamo una mail (deve essere fatto tutto in una unica transazione)
//		lAdddocBean.setFlgautocommitin(1); // forzo l'autocommit

		// Preparo la risposta
		BigDecimal idUdResult = null;
		// Preparo i files
		List<RebuildedFile> versioni = new ArrayList<RebuildedFile>();

		try {
			SubjectBean subject = SubjectUtil.subject.get();
			subject.setIdDominio(pAurigaLoginBean.getSchema());
			SubjectUtil.subject.set(subject);
			Session session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			// Transaction lTransaction = session.beginTransaction();
			GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
			try {
				idUdResult = lGestioneDocumenti.manageAddDocs(pAurigaLoginBean, pFilePrimarioBean, pAllegatiBean, lAdddocBean,
						lXmlUtilitySerializer, versioni, session);
				// Metodo che aggancia la mail alla registrazione
//				agganciaRegistrazioneAllaMail(pAurigaLoginBean,
//				pMailDocumentoIn, idUdResult, session, pAttachAndPosizioneCollectionBean);
//				lTransaction.commit();
				/*
				if(pMailDocumentoIn.getTipoNumerazioni() != null) {
					 TipoNumerazioneBean tipoNumerazione = pMailDocumentoIn.getTipoNumerazioni().get(0);
					 // AGGIUNTA GESTIONE PROTOCOLLAZIONE MILANO
					 if(tipoNumerazione.getSigla() != null && "PGWEB".equals(tipoNumerazione.getSigla())) {
						mLogger.error("PGWEB - RECUPERO I PARAMETRI NECESSARI PER LA PROTOCOLLAZIONE");
						PGWebUtil pgWebUtil =  (PGWebUtil)SpringAppContext.getContext().getBean("pgWebUtil"); 
						ProtocolloBean protocolloBean = null;

						String idCls = null;
						if(pMailDocumentoIn.getClassifichefascicoli()!=null && !pMailDocumentoIn.getClassifichefascicoli().isEmpty())
							if(pMailDocumentoIn.getClassifichefascicoli().get(0)!=null)
								idCls = pMailDocumentoIn.getClassifichefascicoli().get(0).getProvCIClassif();

						// PROTOCOLLAZIONE IN USCITA
						if(pMailDocumentoIn.getFlgTipoProv() != null && "U".equals(pMailDocumentoIn.getFlgTipoProv().toString())) {

							String id_struttCom = null;
							if(pMailDocumentoIn.getMittenti()!=null && !pMailDocumentoIn.getMittenti().isEmpty())
								if(pMailDocumentoIn.getMittenti().get(0)!=null)
									id_struttCom = pMailDocumentoIn.getMittenti().get(0).getProvCIUo();
							
							// CREO LA PROTOCOLLAZIONE IN USCITA
							mLogger.error("PGWEB - CREO LA PROTOCOLLAZIONE IN USCITA");
							protocolloBean = CallServicesPGWeb.creaProtocolloDocInUscita(pgWebUtil.getWsUrlDocumento(), pgWebUtil.getUtenteProtocollazione(),pgWebUtil.getDominioProtocolloazione(),pgWebUtil.getPasswordProtocolloazione(), pMailDocumentoIn.getOggetto(), idCls, "C", id_struttCom);
							
							// MOVIMENTO LA PROTOCOLLAZIONE IN USCITA
							mLogger.error("PGWEB - SETTO IL DESTINATARIO");

							String destinatario = null;
							if(pMailDocumentoIn.getMittenti()!=null && !pMailDocumentoIn.getMittenti().isEmpty())
								if(pMailDocumentoIn.getMittenti().get(0)!=null)
									destinatario = pMailDocumentoIn.getMittenti().get(0).getDenominazioneCognome()+" "+pMailDocumentoIn.getMittenti().get(0).getNome();
							
							
							mLogger.error("PGWEB - CHIAMO IL SERVIZIO DI MOVIMENTAZIONE");
							CallServicesPGWeb.movimentoProtocollo(pgWebUtil.getWsUrlMovimento(),protocolloBean.getIdDocumento(),pgWebUtil.getIdStruttCom(),pgWebUtil.getUtenteProtocollazione(),pgWebUtil.getDominioProtocolloazione(),pgWebUtil.getPasswordProtocolloazione(),destinatario,"S");
						}
						// PROTOCOLLAZIONE IN ENTRATA
						if(pMailDocumentoIn.getFlgTipoProv() != null && "E".equals(pMailDocumentoIn.getFlgTipoProv().toString())) {
							
							String denomCogn = null;
							String nome = null;
							if(pMailDocumentoIn.getMittenti()!=null && !pMailDocumentoIn.getMittenti().isEmpty())
								if(pMailDocumentoIn.getMittenti().get(0)!=null)
								{
									denomCogn = pMailDocumentoIn.getMittenti().get(0).getDenominazioneCognome();
									nome = pMailDocumentoIn.getMittenti().get(0).getNome();
								}
							
							// CREO LA PROTOCOLLAZIONE IN ENTRATA
							mLogger.error("PGWEB - CREO LA PROTOCOLLAZIONE IN ENTRATA");
							protocolloBean = CallServicesPGWeb.creaProtocolloDocInEntrata(pgWebUtil.getWsUrlDocumento(), pgWebUtil.getUtenteProtocollazione(),pgWebUtil.getDominioProtocolloazione(),pgWebUtil.getPasswordProtocolloazione(), pMailDocumentoIn.getOggetto(), idCls, "P", denomCogn, nome,null,null,null,null,null);// "piva", "cf", "pg_mittNum", "pg_mittAnno", "pg_mittData");
						}	
						//ProtocolloRicevuto
						ProtocolloRicevuto protocolloRicevuto = new ProtocolloRicevuto();
						protocolloRicevuto.setNumero(protocolloBean.getNumProt());
						protocolloRicevuto.setAnno(protocolloBean.getAnnoProt());
						protocolloRicevuto.setData(protocolloBean.getDataProt() != null ? new SimpleDateFormat("dd/MM/yyyy").parse(protocolloBean.getDataProt()) : null);
						pMailDocumentoIn.setProtocolloRicevuto(protocolloRicevuto);
						
						//DmpkCoreUpddocudBean
						DmpkCoreUpddocudBean lUpddocudBean = new DmpkCoreUpddocudBean();
						lUpddocudBean.setCodidconnectiontokenin(pAurigaLoginBean.getToken());
						lUpddocudBean.setIduserlavoroin(getIdUserLavoro(pAurigaLoginBean));
						lUpddocudBean.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXmlCompleta(pMailDocumentoIn));								
						lUpddocudBean.setFlgtipotargetin("U");
						lUpddocudBean.setIduddocin(idUdResult);
						
						final UpddocudImpl store = new UpddocudImpl();
						store.setBean(lUpddocudBean);
						//serve questo oggetto? lLoginService poi non lo passiamo da nessuna parte e anche questo pAurigaLoginBean
						//viene passato a tutti i metodi e noi non lo modifichiamo
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
						
						if (result.isInError()){
							throw new StoreException(result);
						}
					 }
					 // FINE AGGIUNTA GESTIONE PROTOCOLLAZIONE MILANO
				}
				*/
				lTransaction.commit();
			} catch (Exception e) {
				mLogger.error("Errore " + e.getMessage(), e);
				// lTransaction.rollback();
				sganciaRegistrazioneDallaMail(pAurigaLoginBean, pMailDocumentoIn);
				if (e instanceof StoreException) {
					BeanUtilsBean2.getInstance().copyProperties(lCreaDocumentoOutBean, ((StoreException) e).getError());
					return lCreaDocumentoOutBean;
				} else {
					lCreaDocumentoOutBean.setDefaultMessage(e.getMessage());
					return lCreaDocumentoOutBean;
				}
			} finally {
				HibernateUtil.release(session);
			}
			
			lCreaDocumentoOutBean.setIdUd(idUdResult);
			
			// Parte di versionamento
			Map<String, String> fileErrors = lGestioneDocumenti.aggiungiFiles(pAurigaLoginBean, versioni);
			lCreaDocumentoOutBean.setFileInErrors(fileErrors);
			
			aggiornaStatoProtocollazione(pAurigaLoginBean, pMailDocumentoIn, idUdResult);
			
			if (pMailDocumentoIn.getFlgNonArchiviareEmail() == null || pMailDocumentoIn.getFlgNonArchiviareEmail() == 0) {
				try {
					archivaMail(pMailDocumentoIn, pAurigaLoginBean);
					mLogger.error("FlgNonArchiviareEmail: " + pMailDocumentoIn.getFlgNonArchiviareEmail() + " -> Mail archiviata");
				} catch (Exception e) {
					mLogger.error("Errore: " + e.getMessage(), e);
					lCreaDocumentoOutBean.setArchiviazioneError(true);
				}
			} else {
				mLogger.error("FlgNonArchiviareEmail: " + pMailDocumentoIn.getFlgNonArchiviareEmail() + " -> Mail non archiviata");
			}
			
			lGestioneDocumenti.recuperaEstremi(pAurigaLoginBean, pMailDocumentoIn, lCreaDocumentoOutBean);
			inviaConfermaAutomatica(pAurigaLoginBean, pMailDocumentoIn, lCreaDocumentoOutBean, pAttachAndPosizioneCollectionBean);
		} catch (Exception e) {
			if (e instanceof StoreException) {
				mLogger.error("Errore " + e.getMessage(), e);
				BeanUtilsBean2.getInstance().copyProperties(lCreaDocumentoOutBean, ((StoreException) e).getError());
				return lCreaDocumentoOutBean;
			} else {
				mLogger.error("Errore " + e.getMessage(), e);
				lCreaDocumentoOutBean.setDefaultMessage(e.getMessage());
				return lCreaDocumentoOutBean;
			}
		}

		return lCreaDocumentoOutBean;
	}

	private void archivaMail(MailDocumentoIn pMailDocumentoIn, AurigaLoginBean lAurigaLoginBean) throws Exception {

		MailProcessorService lMailProcessorService = new MailProcessorService();
		TEmailMgoBean mail = AurigaMailService.getDaoTEmailMgo().get(locale, pMailDocumentoIn.getIdEmail());
		lMailProcessorService.eseguiarchiviazione(locale, mail);

	}

	private void aggiornaStatoProtocollazione(AurigaLoginBean pAurigaLoginBean, MailDocumentoIn pMailDocumentoIn, BigDecimal idUdResult)
			throws Exception {
		MailProcessorService lMailProcessorService = new MailProcessorService();
		InfoProtocolloBean lRegistrazioneProtocolloBean = new InfoProtocolloBean();
		lRegistrazioneProtocolloBean.setIdEmail(pMailDocumentoIn.getIdEmail());
		MailLoginBean lMailLoginBean = new MailLoginBean();
		lMailLoginBean.setSchema(pAurigaLoginBean.getSchema());
		lMailLoginBean.setToken(pAurigaLoginBean.getToken());
		lMailLoginBean.setUserId(pAurigaLoginBean.getIdUserLavoro());
		lRegistrazioneProtocolloBean.setLoginBean(lMailLoginBean);
		lMailProcessorService.updatemailprotocollata(locale, lRegistrazioneProtocolloBean);

	}

	protected void inviaConfermaAutomatica(AurigaLoginBean pAurigaLoginBean, MailDocumentoIn pMailDocumentoIn,
			CreaModDocumentoOutBean lCreaDocumentoOutBean, AttachAndPosizioneCollectionBean pAttachAndPosizioneCollectionBean)
			throws Exception, StoreException {
		MailProcessorService lMailProcessorService = new MailProcessorService();
		RegistrazioneProtocolloBean lRegistrazioneProtocolloBean = new RegistrazioneProtocolloBean();
		lRegistrazioneProtocolloBean.setIdEmail(pMailDocumentoIn.getIdEmail());
		MailLoginBean lMailLoginBean = new MailLoginBean();
		lMailLoginBean.setSchema(pAurigaLoginBean.getSchema());
		lMailLoginBean.setToken(pAurigaLoginBean.getToken());
		lMailLoginBean.setUserId(pAurigaLoginBean.getIdUserLavoro());
		lRegistrazioneProtocolloBean.setLoginBean(lMailLoginBean);
		RegistrazioneProtocollo lRegistrazioneProtocollo = new RegistrazioneProtocollo();
		lRegistrazioneProtocollo.setIdProvReg(lCreaDocumentoOutBean.getIdUd() + "");
		lRegistrazioneProtocollo.setCategoriaReg("PG");
		lRegistrazioneProtocollo.setAnnoReg(lCreaDocumentoOutBean.getAnno().shortValue());
		lRegistrazioneProtocollo.setNumReg(lCreaDocumentoOutBean.getNumero());
		Calendar lGregorianCalendar = GregorianCalendar.getInstance();
		lGregorianCalendar.setTime(lCreaDocumentoOutBean.getData());
		lRegistrazioneProtocollo.setDataRegistrazione(lGregorianCalendar);
		lRegistrazioneProtocolloBean.setRegistrazione(lRegistrazioneProtocollo);
		List<ProtocolloAttachmentBean> attachmentsProtocollati = new ArrayList<ProtocolloAttachmentBean>();
		for (AttachAndPosizioneBean lAttachAndPosizioneBean : pAttachAndPosizioneCollectionBean.getLista()) {
			ProtocolloAttachmentBean lProtocolloAttachmentBean = new ProtocolloAttachmentBean();
			lProtocolloAttachmentBean.setIdAttachment(lAttachAndPosizioneBean.getIdAttachment());
			lProtocolloAttachmentBean.setNumeroAllegatoRegProt(lAttachAndPosizioneBean.getPosizione().byteValue());
			attachmentsProtocollati.add(lProtocolloAttachmentBean);
		}
		lRegistrazioneProtocolloBean.setAttachmentsProtocollati(attachmentsProtocollati);

		try {
			ResultBean<StatoConfermaAutomaticaBean> lInviaConfermaAutomaticaResult = lMailProcessorService.inviaconfermaautomatica(locale,
					lRegistrazioneProtocolloBean);
			if (lInviaConfermaAutomaticaResult.getDefaultMessage() != null) {
				lCreaDocumentoOutBean.setInvioConfermaAutomaticaError(true);
			}
		} catch (Exception e) {
			lCreaDocumentoOutBean.setInvioConfermaAutomaticaError(true);
		}

	}

	protected void agganciaRegistrazioneAllaMail(AurigaLoginBean pAurigaLoginBean, MailDocumentoIn pMailDocumentoIn, BigDecimal idUdResult,
			Session session, AttachAndPosizioneCollectionBean pAttachAndPosizioneCollectionBean) throws Exception, StoreException {
		MailProcessorService lMailProcessorService = new MailProcessorService();
		RegistrazioneProtocolloBean lRegistrazioneProtocolloBean = new RegistrazioneProtocolloBean();
		lRegistrazioneProtocolloBean.setIdEmail(pMailDocumentoIn.getIdEmail());
		MailLoginBean lMailLoginBean = new MailLoginBean();
		lMailLoginBean.setSchema(pAurigaLoginBean.getSchema());
		lMailLoginBean.setToken(pAurigaLoginBean.getToken());
		lMailLoginBean.setUserId(pAurigaLoginBean.getIdUserLavoro());
		lRegistrazioneProtocolloBean.setLoginBean(lMailLoginBean);
		RegistrazioneProtocollo lRegistrazioneProtocollo = new RegistrazioneProtocollo();
		lRegistrazioneProtocollo.setIdProvReg(idUdResult + "");
		DmpkUtilityGetestremiregnumud_jBean infoRegistrazione = recuperaEstremiInTransazione(pAurigaLoginBean, pMailDocumentoIn, session,
				idUdResult);
		lRegistrazioneProtocollo.setCategoriaReg("PG");
		lRegistrazioneProtocollo.setAnnoReg(infoRegistrazione.getAnnoregout().shortValue());
		lRegistrazioneProtocollo.setNumReg(infoRegistrazione.getNumregout());
		Calendar lGregorianCalendar = GregorianCalendar.getInstance();
		lGregorianCalendar.setTime(infoRegistrazione.getTsregout());
		lRegistrazioneProtocollo.setDataRegistrazione(lGregorianCalendar);
		lRegistrazioneProtocolloBean.setRegistrazione(lRegistrazioneProtocollo);
		List<ProtocolloAttachmentBean> attachmentsProtocollati = new ArrayList<ProtocolloAttachmentBean>();
		for (AttachAndPosizioneBean lAttachAndPosizioneBean : pAttachAndPosizioneCollectionBean.getLista()) {
			ProtocolloAttachmentBean lProtocolloAttachmentBean = new ProtocolloAttachmentBean();
			lProtocolloAttachmentBean.setIdAttachment(lAttachAndPosizioneBean.getIdAttachment());
			lProtocolloAttachmentBean.setNumeroAllegatoRegProt(lAttachAndPosizioneBean.getPosizione().byteValue());
			attachmentsProtocollati.add(lProtocolloAttachmentBean);
		}
		lRegistrazioneProtocolloBean.setAttachmentsProtocollati(attachmentsProtocollati);

		ResultBean<InfoRelazioneProtocolloBean> lCreaRelazioneProtocolloResult = lMailProcessorService.crearelazioneprotocollo(locale,
				lRegistrazioneProtocolloBean);

		if (lCreaRelazioneProtocolloResult.getDefaultMessage() != null) {
			throw new Exception(lCreaRelazioneProtocolloResult.getDefaultMessage());
		}

		ResultBean<StatoConfermaAutomaticaBean> lInviaConfermaAutomaticaResult = lMailProcessorService.inviaconfermaautomatica(locale,
				lRegistrazioneProtocolloBean);

		if (lInviaConfermaAutomaticaResult.getDefaultMessage() != null) {
			throw new Exception(lInviaConfermaAutomaticaResult.getDefaultMessage());
		}
	}

	protected void sganciaRegistrazioneDallaMail(AurigaLoginBean pAurigaLoginBean, MailDocumentoIn pMailDocumentoIn) throws Exception,
			StoreException {
		// Teoricamente per adesso non deve fare nulla
	}

	private boolean mailProtocollata(String idEmail, AurigaLoginBean pAurigaLoginBean) throws Exception {
		MailProcessorService lMailProcessorService = new MailProcessorService();
		InfoProtocolloBean lInfoProtocolloBean = new InfoProtocolloBean();
		lInfoProtocolloBean.setIdEmail(idEmail);

		MailLoginBean lLoginBean = new MailLoginBean();
		lLoginBean.setToken(pAurigaLoginBean.getToken());
		lLoginBean.setUserId(pAurigaLoginBean.getIdUserLavoro());
		lLoginBean.setSchema(pAurigaLoginBean.getSchema());
		lInfoProtocolloBean.setLoginBean(lLoginBean);
		mLogger.debug("Lo schema vale " + lInfoProtocolloBean.getLoginBean().getSchema());
		mLogger.debug("Il token vale " + lInfoProtocolloBean.getLoginBean().getToken());
		mLogger.debug("L'userId vale " + lInfoProtocolloBean.getLoginBean().getUserId());
		String lStringStatoProtocollazione = lMailProcessorService.getstatoprotocollazione(locale, lInfoProtocolloBean)
				.getFlagStatoProtocollazione();
		mLogger.debug("Stato protocollazione: " + lStringStatoProtocollazione);
		if (lStringStatoProtocollazione != null && lStringStatoProtocollazione.equals("C"))
			return true;
		else
			return false;
	}

	private boolean mailLockata(String idEmail, AurigaLoginBean pAurigaLoginBean) throws Exception {
		CheckService lCheckService = new CheckService();
		String lStrIdUtente = pAurigaLoginBean.getSpecializzazioneBean().getIdUtenteModPec();
		LockBean lLockBean = new LockBean();
		lLockBean.setMailId(idEmail);
		lLockBean.setUserId(lStrIdUtente);
		CheckLockOutBean lCheckLockOutBean = lCheckService.checklock(locale, lLockBean);
		return lCheckLockOutBean.getIsLocked();
	}

	protected DmpkUtilityGetestremiregnumud_jBean recuperaEstremiInTransazione(AurigaLoginBean pAurigaLoginBean,
			CreaModDocumentoInBean pCreaDocumentoInBean, Session session, BigDecimal idUd) throws Exception, StoreException {
		DmpkUtilityGetestremiregnumud_jBean lDmpkUtilityGetestremiregnumud_jBean = new DmpkUtilityGetestremiregnumud_jBean();
		lDmpkUtilityGetestremiregnumud_jBean.setIdudin(idUd);
		final Getestremiregnumud_jImpl lGetestremiregnumud_j = new Getestremiregnumud_jImpl();
		lGetestremiregnumud_j.setBean(lDmpkUtilityGetestremiregnumud_jBean);
		SubjectBean subject = SubjectUtil.subject.get();
		subject.setIdDominio(pAurigaLoginBean.getSchema());
		SubjectUtil.subject.set(subject);
		LoginService lLoginService = new LoginService();
		lLoginService.login(pAurigaLoginBean);
		session.doWork(new Work() {
			@Override
			public void execute(Connection paramConnection) throws SQLException {
				paramConnection.setAutoCommit(false);
				lGetestremiregnumud_j.execute(paramConnection);
			}
		});
		StoreResultBean<DmpkUtilityGetestremiregnumud_jBean> result = new StoreResultBean<DmpkUtilityGetestremiregnumud_jBean>();
		AnalyzeResult.analyze(lDmpkUtilityGetestremiregnumud_jBean, result);
		result.setResultBean(lDmpkUtilityGetestremiregnumud_jBean);

		if (result.isInError()) {
			throw new StoreException(result);
		}
		return result.getResultBean();

	}

	protected BigDecimal getIdUserLavoro(AurigaLoginBean pAurigaLoginBean) {
		return StringUtils.isNotBlank(pAurigaLoginBean.getIdUserLavoro()) ? new BigDecimal(pAurigaLoginBean.getIdUserLavoro()) : null;
	}

}
