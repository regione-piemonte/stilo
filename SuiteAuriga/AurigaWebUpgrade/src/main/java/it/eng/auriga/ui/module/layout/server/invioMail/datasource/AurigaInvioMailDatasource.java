/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedWriter;
import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerInsbatchBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailLoaddettemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailReinviaemailBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.LoadAttrDinamicoListaDatasource;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.LoadAttrDinamicoListaInputBean;
import it.eng.auriga.ui.module.layout.server.common.LoginDataSource;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.AttachmentBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.InsBatchParametriXmlBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.InvioMailBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.InvioMailMultiDestinatariResultBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.InvioMailResultBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.InvioMultiDestinatariXlsAttachmentsXmlBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.ItemLavorazioneMailBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.SalvaInBozzaMailBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.LockUnlockMail;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.AurigaLoadDettaglioEmailDataSource;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.CorpoMailDataSource;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.ProtocolloUtility;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.DettaglioEmailAllegatoBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.DettaglioEmailBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.DettaglioPostaElettronicaEstremiDoc;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.OperazioneMassivaPostaElettronicaBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.XmlDatiPostaElettronicaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.auriga.ui.module.layout.shared.util.IndirizziEmailSplitter;
import it.eng.aurigamailbusiness.bean.AllegatiInvioMailXmlBean;
import it.eng.aurigamailbusiness.bean.DestinatariInvioMailXmlBean;
import it.eng.aurigamailbusiness.bean.DettagliXlsIndirizziEmailXmlBean;
import it.eng.aurigamailbusiness.bean.DraftAndWorkItemsBean;
import it.eng.aurigamailbusiness.bean.EmailSentReferenceBean;
import it.eng.aurigamailbusiness.bean.IdMailInoltrataMailXmlBean;
import it.eng.aurigamailbusiness.bean.InvioMailXmlBean;
import it.eng.aurigamailbusiness.bean.ItemLavorazioneMailXmlBean;
import it.eng.aurigamailbusiness.bean.MailLoginBean;
import it.eng.aurigamailbusiness.bean.ResultBean;
import it.eng.aurigamailbusiness.bean.RispostaInoltro;
import it.eng.aurigamailbusiness.bean.RispostaInoltroBean;
import it.eng.aurigamailbusiness.bean.SenderAttachmentsBean;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TRegProtVsEmailBean;
import it.eng.client.AurigaMailService;
import it.eng.client.DmpkBmanagerInsbatch;
import it.eng.client.DmpkIntMgoEmailLoaddettemail;
import it.eng.client.DmpkIntMgoEmailReinviaemail;
import it.eng.client.GestioneDocumenti;
import it.eng.client.MailSenderService;
import it.eng.client.RecuperoDocumenti;
import it.eng.core.business.KeyGenerator;
import it.eng.document.function.bean.AggiungiDocumentoInBean;
import it.eng.document.function.bean.AggiungiDocumentoOutBean;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlUtilityDeserializer;

@Datasource(id = "AurigaInvioMailDatasource")
public class AurigaInvioMailDatasource extends AbstractServiceDataSource<OperazioneMassivaPostaElettronicaBean, InvioMailBean> {

	private static Logger mLogger = Logger.getLogger(AurigaInvioMailDatasource.class);

	@Override
	public InvioMailBean call(OperazioneMassivaPostaElettronicaBean bean) throws Exception {
		/*
		 * for(PostaElettronicaBean email : bean.getListaRecord()) { boolean isLocked = recuperaIsLocked(email); if (isLocked) throw new
		 * StoreException("Operazione non consentita: sull'email sta lavorando un altro utente"); //Va messo un lock sulla mail TEmailMgoBean lTEmailMgoBean =
		 * lockMail(email); }
		 */
		return new InvioMailBean();
	}

	public InvioMailResultBean reinvia(InvioMailBean pInvioMailBean) throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		SenderBean bean = new SenderBean();
		bean.setIdUtenteModPec(lAurigaLoginBean.getSpecializzazioneBean().getIdUtenteModPec());
		bean.setIdEmail(pInvioMailBean.getIdEmail());

		if(ParametriDBUtil.getParametroDB(getSession(), "CASELLE_ALIAS_UTENTE_IN_INVIO") != null
				&& !"".equalsIgnoreCase(ParametriDBUtil.getParametroDB(getSession(), "CASELLE_ALIAS_UTENTE_IN_INVIO"))) {
			String caselleAliasUtenteInvio = ParametriDBUtil.getParametroDB(getSession(), "CASELLE_ALIAS_UTENTE_IN_INVIO");
			StringSplitterServer mittenteDBSplit = new StringSplitterServer(caselleAliasUtenteInvio, ";");
			while (mittenteDBSplit.hasMoreElements()) {
				if(pInvioMailBean.getMittente().equalsIgnoreCase(mittenteDBSplit.nextToken().trim())) {
					if(StringUtils.isNotBlank(lAurigaLoginBean.getDelegaDenominazione())) {
						bean.setAliasAddressFrom(lAurigaLoginBean.getDelegaDenominazione());
						// Setto anche il Reply-To
						bean.setReplyTo(getEmailReplyTo(lAurigaLoginBean.getUseridForPrefs()));
					} else {
						bean.setAliasAddressFrom(lAurigaLoginBean.getSpecializzazioneBean().getDesUserOut());
						// Setto anche il Reply-To
						bean.setReplyTo(getEmailReplyTo(lAurigaLoginBean.getUserid()));
					}
					break;
				}
			} 
		}

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		DmpkIntMgoEmailReinviaemail store = new DmpkIntMgoEmailReinviaemail();

		DmpkIntMgoEmailReinviaemailBean inputReinvio = new DmpkIntMgoEmailReinviaemailBean();
		inputReinvio.setCodidconnectiontokenin(token);
		inputReinvio.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		inputReinvio.setIdemailin(pInvioMailBean.getIdEmail());
		
		StoreResultBean<DmpkIntMgoEmailReinviaemailBean> result = store.execute(getLocale(), loginBean, inputReinvio);
		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + result.getDefaultMessage());
				throw new StoreException(result);
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		MailSenderService lMailSenderService = new MailSenderService();
		lMailSenderService.resend(getLocale(), bean);
		
		return new InvioMailResultBean();
	}

	public InvioMailResultBean invioBozzaMail(SalvaInBozzaMailBean pInBean) throws Exception {

		mLogger.debug("start invioBozzaMail");
				
		//Controllo il body
		pInBean = checkBodyHtml(pInBean);

		String tipoRel = getExtraparams().get("tipoRel") != null ? getExtraparams().get("tipoRel") : "";
		String operazione = getExtraparams().get("operazione") != null ? getExtraparams().get("operazione") : "N";

		Integer posizione = getExtraparams().get("posizione") != null && "L".equals(getExtraparams().get("posizione")) ? 1 : 0;

		DraftAndWorkItemsBean input = getDatiBozza(pInBean, tipoRel, operazione, posizione);
		input.setIdemailio(pInBean.getIdEmailPrincipale());

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		MailLoginBean lMailLoginBean = new MailLoginBean();
		lMailLoginBean.setSchema(loginBean.getSchema());
		lMailLoginBean.setToken(loginBean.getToken());
		lMailLoginBean.setUserId(loginBean.getIdUserLavoro());
		lMailLoginBean.setIdUtente(loginBean.getIdUtente());

		ResultBean<EmailSentReferenceBean> output = AurigaMailService.getMailSenderService().saveandsenddraft(getLocale(), input, lMailLoginBean,
				loginBean.getSpecializzazioneBean().getIdUtenteModPec(), posizione);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output.getDefaultMessage());
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		} 

		return new InvioMailResultBean();
	}

	public InvioMailResultBean invioMail(SalvaInBozzaMailBean pInvioMailBean) throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		//Controllo il body
		pInvioMailBean = checkBodyHtml(pInvioMailBean);
		
		MailLoginBean lMailLoginBean = new MailLoginBean();
		lMailLoginBean.setSchema(lAurigaLoginBean.getSchema());
		lMailLoginBean.setToken(lAurigaLoginBean.getToken());
		lMailLoginBean.setUserId(lAurigaLoginBean.getIdUserLavoro());
		lMailLoginBean.setIdUtente(lAurigaLoginBean.getIdUtente());

		SenderBean senderBean = new SenderBean();
		senderBean.setIdUtenteModPec(lAurigaLoginBean.getSpecializzazioneBean().getIdUtenteModPec());

		senderBean.setFlgInvioSeparato(pInvioMailBean.getFlgInvioSeparato() != null && pInvioMailBean.getFlgInvioSeparato() ? true : false);
		senderBean.setAccount(pInvioMailBean.getMittente());
		
		if(ParametriDBUtil.getParametroDB(getSession(), "CASELLE_ALIAS_UTENTE_IN_INVIO") != null
				&& !"".equalsIgnoreCase(ParametriDBUtil.getParametroDB(getSession(), "CASELLE_ALIAS_UTENTE_IN_INVIO"))) {
			String caselleAliasUtenteInvio = ParametriDBUtil.getParametroDB(getSession(), "CASELLE_ALIAS_UTENTE_IN_INVIO");
			StringSplitterServer mittenteDBSplit = new StringSplitterServer(caselleAliasUtenteInvio, ";");
			while (mittenteDBSplit.hasMoreElements()) {
				if(pInvioMailBean.getMittente().equalsIgnoreCase(mittenteDBSplit.nextToken().trim())) {
					if(StringUtils.isNotBlank(lAurigaLoginBean.getDelegaDenominazione())) {
						senderBean.setAliasAddressFrom(lAurigaLoginBean.getDelegaDenominazione());
						// Setto anche il Reply-To
						senderBean.setReplyTo(getEmailReplyTo(lAurigaLoginBean.getUseridForPrefs()));
					} else {
						senderBean.setAliasAddressFrom(lAurigaLoginBean.getSpecializzazioneBean().getDesUserOut());
						// Setto anche il Reply-To
						senderBean.setReplyTo(getEmailReplyTo(lAurigaLoginBean.getUserid()));
					}
					break;
				}
			} 
		}

		String tipoRel = getExtraparams().get("tipoRel") != null ? getExtraparams().get("tipoRel") : "";
		String tipoRelCopia = getExtraparams().get("tipoRelCopia") != null ? getExtraparams().get("tipoRelCopia") : "";
	
		//Destinatari principali
		List<String> destinatari = new ArrayList<String>();
		String[] lStringDestinatari = IndirizziEmailSplitter.split(pInvioMailBean.getDestinatari());
		destinatari = Arrays.asList(lStringDestinatari);
		senderBean.setAddressTo(destinatari);
		//Destinatari CC
		if (StringUtils.isNotEmpty(pInvioMailBean.getDestinatariCC())) {
			List<String> destinatariCC = new ArrayList<String>();
			String[] lStringDestinatariCC = IndirizziEmailSplitter.split(pInvioMailBean.getDestinatariCC());
			destinatariCC = Arrays.asList(lStringDestinatariCC);
			senderBean.setAddressCc(destinatariCC);
		}
		
		if(pInvioMailBean.getCasellaIsPec() == null || !"true".equals(pInvioMailBean.getCasellaIsPec())){
			//Destinatari CCN
			if (StringUtils.isNotEmpty(pInvioMailBean.getDestinatariCCN())) {
				List<String> destinatariCCN = new ArrayList<String>();
				String[] lStringDestinatariCCN = IndirizziEmailSplitter.split(pInvioMailBean.getDestinatariCCN());
				destinatariCCN = Arrays.asList(lStringDestinatariCCN);
				senderBean.setAddressBcc(destinatariCCN);
			}
		}
		
		senderBean.setAddressFrom(pInvioMailBean.getMittente());
		senderBean.setSubject(pInvioMailBean.getOggetto());
		if (pInvioMailBean.getTextHtml().equals("text")) {
			senderBean.setBody(pInvioMailBean.getBodyText());
			senderBean.setIsHtml(false);
		} else {
			senderBean.setBody("<html>" + pInvioMailBean.getBodyHtml() + "</html>");
			senderBean.setIsHtml(true);
		}

		// Controllo se ho predecessori. Posso averli in idEmail (un solo predecessore come Stringa) o in listaIdEmailPredecessore
		// nel caso abbia più predecessori
		boolean isNuovoInvioCopia = tipoRel != null && tipoRel.equals("InvioNuovoMessaggioCopia") && (pInvioMailBean.getIdEmail() != null
				|| (pInvioMailBean.getListaIdEmailPredecessore() != null && pInvioMailBean.getListaIdEmailPredecessore().size() > 0));

		if (tipoRel.equals("risposta") || tipoRel.equals("inoltro") || tipoRel.equals("inoltroAllegaMailOrig") || isNuovoInvioCopia) {
			RispostaInoltroBean lRispostaInoltroBean = new RispostaInoltroBean();
			if (pInvioMailBean.getIdEmail() != null && pInvioMailBean.getIdEmail().length() > 0) {
				// Ho un solo predecessore
				TEmailMgoBean lTEmailMgoBean = AurigaMailService.getDaoTEmailMgo().get(getLocale(), pInvioMailBean.getIdEmail());
				lRispostaInoltroBean.setMailOriginaria(lTEmailMgoBean);
			} else if (pInvioMailBean.getListaIdEmailPredecessore() != null && pInvioMailBean.getListaIdEmailPredecessore().size() == 1) {
				// Ho solo un precedessore, ma salvato come lista. Devo comunque metterlo nel campo del non massivo
				String idPrecedessore = pInvioMailBean.getListaIdEmailPredecessore().get(0).getIdMailInoltrata();
				TEmailMgoBean lTEmailMgoBean = AurigaMailService.getDaoTEmailMgo().get(getLocale(), idPrecedessore);
				lRispostaInoltroBean.setMailOriginaria(lTEmailMgoBean);
				// lRispostaInoltroBean.setMailOriginaria(listaTEmailMgoBeans.get(0));
			} else if (pInvioMailBean.getListaIdEmailPredecessore() != null && pInvioMailBean.getListaIdEmailPredecessore().size() > 1) {
				// Ho una lista di predecessori
				List<TEmailMgoBean> listaTEmailMgoBeans = new ArrayList<TEmailMgoBean>();
				for (IdMailInoltrataMailXmlBean predecessore : pInvioMailBean.getListaIdEmailPredecessore()) {
					TEmailMgoBean lTEmailMgoBean = AurigaMailService.getDaoTEmailMgo().get(getLocale(), predecessore.getIdMailInoltrata());
					listaTEmailMgoBeans.add(lTEmailMgoBean);
				}
				lRispostaInoltroBean.setMailOrigInoltroMassivo(listaTEmailMgoBeans);
				// lRispostaInoltroBean.setMailOriginaria(listaTEmailMgoBeans.get(0));
			}

			if (isNuovoInvioCopia) {
				if (tipoRelCopia.equals("risposta"))
					lRispostaInoltroBean.setRispInol(RispostaInoltro.RISPOSTA);
				else if (tipoRelCopia.equals("inoltro") || tipoRel.equals("inoltroAllegaMailOrig"))
					lRispostaInoltroBean.setRispInol(RispostaInoltro.INOLTRO);
				else if (tipoRelCopia.equals("notifica_eccezione"))
					lRispostaInoltroBean.setRispInol(RispostaInoltro.NOTIFICA_ECCEZIONE);
				else if (tipoRelCopia.equals("notifica_conferma"))
					lRispostaInoltroBean.setRispInol(RispostaInoltro.NOTIFICA_CONFERMA);
			} else {
				if (tipoRel.equals("risposta"))
					lRispostaInoltroBean.setRispInol(RispostaInoltro.RISPOSTA);
				else if (tipoRel.equals("inoltro") || tipoRel.equals("inoltroAllegaMailOrig"))
					lRispostaInoltroBean.setRispInol(RispostaInoltro.INOLTRO);
			}

			senderBean.setRispInol(lRispostaInoltroBean);
		}

		if (pInvioMailBean.getAttach() != null && !pInvioMailBean.getAttach().isEmpty()) {
			List<SenderAttachmentsBean> lista = new ArrayList<SenderAttachmentsBean>();
			if (pInvioMailBean.getIdEmailPrincipale() != null && !"".equalsIgnoreCase(pInvioMailBean.getIdEmailPrincipale())) {

				for (AttachmentBean lAttachmentBean : pInvioMailBean.getAttach()) {
					String uri = lAttachmentBean.getUriAttach();
					if (uri.equals("_noUri")) {
						uri = recuperaAttachments(getAttachInDettaglioEmailBean(pInvioMailBean.getIdEmailPrincipale()),
								lAttachmentBean.getFileNameAttach());
					}
					MimeTypeFirmaBean lInfoFileRecord = lAttachmentBean.getInfoFileAttach();
					SenderAttachmentsBean atta = new SenderAttachmentsBean();
					//atta.setContent(IOUtils.toByteArray(StorageImplementation.getStorage().extract(uri)));
					atta.setFile(StorageImplementation.getStorage().extractFile(uri));
					atta.setFilename(StringUtils.isNotBlank(lInfoFileRecord.getCorrectFileName()) ? lInfoFileRecord.getCorrectFileName()
							: lAttachmentBean.getFileNameAttach());
					atta.setFirmato(lInfoFileRecord.isFirmato());
					atta.setFirmaValida(lInfoFileRecord.isFirmaValida());
					atta.setMimetype(lInfoFileRecord.getMimetype());
					atta.setOriginalName(lAttachmentBean.getFileNameAttach());
					atta.setPdfConCommenti(lInfoFileRecord.isPdfConCommenti());
					atta.setPdfEditabile(lInfoFileRecord.isPdfEditabile());
					
					//Per ottenere le informazioni relative a encoding, algoritmo relativo e impronta
					InfoFileUtility lFileUtility = new InfoFileUtility();
					RebuildedFile lRebuildedFile = new RebuildedFile();
					lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(uri));
					
					MimeTypeFirmaBean lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(),
							lAttachmentBean.getInfoFileAttach().getCorrectFileName(), false, null);
					
					atta.setDimensione(new BigDecimal(lMimeTypeFirmaBean.getBytes()));
					atta.setAlgoritmo(lMimeTypeFirmaBean.getAlgoritmo());
					atta.setEncoding(lMimeTypeFirmaBean.getEncoding());
					atta.setImpronta(lMimeTypeFirmaBean.getImpronta());
					
					lista.add(atta);
				}
				senderBean.setAttachments(lista);
			} else {
				for (AttachmentBean lAttachmentBean : pInvioMailBean.getAttach()) {
					
					String uri = lAttachmentBean.getUriAttach();
					MimeTypeFirmaBean lInfoFileRecord = lAttachmentBean.getInfoFileAttach();
					SenderAttachmentsBean atta = new SenderAttachmentsBean();
					//atta.setContent(IOUtils.toByteArray(StorageImplementation.getStorage().extract(uri)));
					atta.setFile(StorageImplementation.getStorage().extractFile(uri));
					atta.setFilename(StringUtils.isNotBlank(lInfoFileRecord.getCorrectFileName()) ? lInfoFileRecord.getCorrectFileName()
							: lAttachmentBean.getFileNameAttach());
					atta.setFirmato(lInfoFileRecord.isFirmato());
					atta.setFirmaValida(lInfoFileRecord.isFirmaValida());
					atta.setMimetype(lInfoFileRecord.getMimetype());
					atta.setOriginalName(lAttachmentBean.getFileNameAttach());
					atta.setPdfConCommenti(lInfoFileRecord.isPdfConCommenti());
					atta.setPdfEditabile(lInfoFileRecord.isPdfEditabile());
					
					//Per ottenere le informazioni relative a encoding, algoritmo relativo e impronta
					InfoFileUtility lFileUtility = new InfoFileUtility();
					RebuildedFile lRebuildedFile = new RebuildedFile();
					lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(uri));
					
					MimeTypeFirmaBean lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(),
							lAttachmentBean.getInfoFileAttach().getCorrectFileName(), false, null);
					
					atta.setDimensione(new BigDecimal(lMimeTypeFirmaBean.getBytes()));
					atta.setAlgoritmo(lMimeTypeFirmaBean.getAlgoritmo());
					atta.setEncoding(lMimeTypeFirmaBean.getEncoding());
					atta.setImpronta(lMimeTypeFirmaBean.getImpronta());
					
					lista.add(atta);
				}
				senderBean.setAttachments(lista);
			}
		}

		if (pInvioMailBean.getConfermaLettura() != null && pInvioMailBean.getConfermaLettura()) {
			senderBean.setReturnReceipt(true);
		}

		/**
		 * Se presente valorizzo la lista degli Item In Lavorazione,Tab: Appunti & Note
		 */
		if (pInvioMailBean.getListaItemInLavorazione() != null && pInvioMailBean.getListaItemInLavorazione().size() > 0) {
			populateItemFileInvioMail(senderBean, pInvioMailBean);
		}

		ResultBean<EmailSentReferenceBean> output = null;

		/*
		 * CASISTICA DI INVIO NUOVO MESSAGGIO COME COPIA
		 */
		if (tipoRel != null && tipoRel.equals("InvioNuovoMessaggioCopia")) {
			if (pInvioMailBean.getSalvaInviati() != null && pInvioMailBean.getSalvaInviati()) {
				output = AurigaMailService.getMailSenderService().sendandsaveworkitems(getLocale(), senderBean, lMailLoginBean);
				if (StringUtils.isNotBlank(output.getDefaultMessage())) {
					if (output.isInError()) {
						throw new StoreException(output.getDefaultMessage());
					} else {
						addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
					}
				}
			} else {
				output = AurigaMailService.getMailSenderService().sendanddeleteaftersend(getLocale(), senderBean);
				if (StringUtils.isNotBlank(output.getDefaultMessage())) {
					if (output.isInError()) {
						throw new StoreException(output.getDefaultMessage());
					} else {
						addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
					}
				}
			}
		} else {
			/**
			 * CASISTICA DI INVIO MESSAGGIO COME: RISPOSTA - INOLTRO ( tutti i casi di inoltro )
			 */
			if (tipoRel != null && (tipoRel.equals("risposta") || tipoRel.equals("inoltro") || tipoRel.equals("inoltroAllegaMailOrig"))) {
				output = AurigaMailService.getMailSenderService().sendandsaveworkitems(getLocale(), senderBean, lMailLoginBean);
				if (StringUtils.isNotBlank(output.getDefaultMessage())) {
					if (output.isInError()) {
						throw new StoreException(output.getDefaultMessage());
					} else {
						addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
					}
				}
			} else {
				/**
				 * CASISTICA DI INVIO NUOVO MESSAGGIO
				 */
				if (pInvioMailBean.getSalvaInviati() != null && pInvioMailBean.getSalvaInviati()) {						
					output = AurigaMailService.getMailSenderService().sendandsaveworkitems(getLocale(), senderBean, lMailLoginBean);
					if (StringUtils.isNotBlank(output.getDefaultMessage())) {
						if (output.isInError()) {
							throw new StoreException(output.getDefaultMessage());
						} else {
							addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
						}
					}						
				} else {
					output = AurigaMailService.getMailSenderService().sendanddeleteaftersend(getLocale(), senderBean);
					if (StringUtils.isNotBlank(output.getDefaultMessage())) {
						if (output.isInError()) {
							throw new StoreException(output.getDefaultMessage());
						} else {
							addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
						}
					}
				}
			}
		}			
		
		String idUD = getExtraparams().get("idUD") != null ? getExtraparams().get("idUD") : "";	
		if (idUD != null && !idUD.equalsIgnoreCase("")) {
			EmailSentReferenceBean lEmailSentReferenceBean = output.getResultBean();
			for (String idEmailNew : lEmailSentReferenceBean.getIdEmails()) {
				associaUD(idUD, idEmailNew);
			}	
		}

		String idEmailUD = getExtraparams().get("idEmailUD") != null ? getExtraparams().get("idEmailUD") : "";	
		if (idEmailUD != null && !idEmailUD.equalsIgnoreCase("")) {
			EmailSentReferenceBean lEmailSentReferenceBean = output.getResultBean();
			for (String idEmailNew : lEmailSentReferenceBean.getIdEmails()) {
				associaEmailUD(idEmailUD, idEmailNew);
			}			
		}
		
		InvioMailResultBean lInvioMailResultBean = new InvioMailResultBean();
		lInvioMailResultBean.setDestinatari(pInvioMailBean.getDestinatari());
		lInvioMailResultBean.setDestinatariCC(pInvioMailBean.getDestinatariCC());
		lInvioMailResultBean.setOggetto(pInvioMailBean.getOggetto());
		if(output.getResultBean() != null && output.getResultBean().getIdEmails() != null &&
				!output.getResultBean().getIdEmails().isEmpty() && output.getResultBean().getIdEmails().size() == 1) {
			lInvioMailResultBean.setIdEmail(output.getResultBean().getIdEmails().get(0));
		}
		
		return lInvioMailResultBean;
	}

	private SalvaInBozzaMailBean checkBodyHtml(SalvaInBozzaMailBean mailBean) {
		/*
		 * Richiamo il datasource in cui è definito il metodo che controlla se effettivamente
		 * il body deve essere vuoto o meno.
		 * Se ad esempio il body è formato solamente da <!-- commenti --> o <div>..... allora deve
		 * essere vuoto 
		 */
		CorpoMailDataSource corpoMailDataSource = new CorpoMailDataSource();
		
		String newBodyHtml = "";
		try {
			newBodyHtml = corpoMailDataSource.removeMarker(mailBean.getBodyHtml());
			newBodyHtml = corpoMailDataSource.checkBodyHtml(newBodyHtml);
			
			//Espressione regolare utilizzata per rimuovere i caratteri speciali non decodificabili nei vari charset
			newBodyHtml = newBodyHtml.replaceAll("\\p{C}", " ");
		} catch (Exception e) {
			mLogger.error("Errore in AurigaInvioMailDS -  checkBodyHtml", e);
		}
		
		mailBean.setBodyHtml(newBodyHtml);
		mailBean.setBodyText(mailBean.getBodyText()); //Il bodyText è già stato modificato come voluto 
		
		return mailBean;
	}
	
	// Associa una mail inviata alla UD
	private void associaUD(String idUDIn, String idEmailNewIn) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		if (idUDIn != null && !idUDIn.equalsIgnoreCase("") && idEmailNewIn != null && !idEmailNewIn.equalsIgnoreCase("")) {		
			
			StringSplitterServer st = new StringSplitterServer(idUDIn, ";");
			
			while(st.hasMoreTokens()) {
				
				String idUd = st.nextToken();
				
				RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
				lRecuperaDocumentoInBean.setIdUd(new BigDecimal(idUd));
				RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
				RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), loginBean, lRecuperaDocumentoInBean);
				if(lRecuperaDocumentoOutBean.isInError()) {
					throw new StoreException(lRecuperaDocumentoOutBean);
				}
				DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
				ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
				ProtocollazioneBean lProtocollazioneBean = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean, getExtraparams());
				
				// Inizializzo l'INPUT
				TRegProtVsEmailBean lTRegProtVsEmailSaveIn = new TRegProtVsEmailBean();
				lTRegProtVsEmailSaveIn.setAnnoReg(StringUtils.isNotBlank(lProtocollazioneBean.getAnnoProtocollo()) ? new Short(lProtocollazioneBean.getAnnoProtocollo()) : null);
				lTRegProtVsEmailSaveIn.setCategoriaReg(lProtocollazioneBean.getCodCategoriaProtocollo());
				lTRegProtVsEmailSaveIn.setIdEmail(idEmailNewIn);
				lTRegProtVsEmailSaveIn.setIdProvReg(idUd);
				lTRegProtVsEmailSaveIn.setNumReg(lProtocollazioneBean.getNroProtocollo());
				lTRegProtVsEmailSaveIn.setSiglaRegistro(lProtocollazioneBean.getSiglaProtocollo());
				lTRegProtVsEmailSaveIn.setTsReg(lProtocollazioneBean.getDataProtocollo());
				lTRegProtVsEmailSaveIn.setIdRegProtEmail(KeyGenerator.gen());

				// Eseguo il servizio
				try {
					TRegProtVsEmailBean lTRegProtVsEmailBeanOut = AurigaMailService.getDaoTRegProtVsEmail().save(getLocale(), lTRegProtVsEmailSaveIn);
				} catch (Exception e) {
					String msgError = "Fallita associazione e-mail inviata al documento.";
					addMessage(msgError, "", MessageType.WARNING);
				}
			}
		}
	}

	// Associa una mail inviata alla UD della mail di provenienza
	private void associaEmailUD(String idEmailUDIn, String idEmailNewIn) throws Exception {
		
		String idUdEmail = null;
		// ---------------------------------------------------------------------------------------------------------------------------------------------------------
		// Cerco il dettaglio della mail per leggere gli estremi della registrazione : idUd , categoriaRegUD, numRegUD , siglaRegUD , annoRegUD , tsRegUD
		// ---------------------------------------------------------------------------------------------------------------------------------------------------------

		// Inizializzo l'INPUT
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		DmpkIntMgoEmailLoaddettemailBean input = new DmpkIntMgoEmailLoaddettemailBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdemailin(idEmailUDIn);

		// Eseguo il servizio
		DmpkIntMgoEmailLoaddettemail loaddettemailBean = new DmpkIntMgoEmailLoaddettemail();
		StoreResultBean<DmpkIntMgoEmailLoaddettemailBean> output = loaddettemailBean.execute(getLocale(), loginBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		if (StringUtils.isNotBlank(output.getResultBean().getXmldatiemailout())) {
			XmlUtilityDeserializer lXmlUtility = new XmlUtilityDeserializer();
			XmlDatiPostaElettronicaBean lXmlDatiPostaElettronicaBean = lXmlUtility.unbindXml(output.getResultBean().getXmldatiemailout(),
					XmlDatiPostaElettronicaBean.class);

			if (lXmlDatiPostaElettronicaBean.getListaEstremiDocTrasmessi() != null && lXmlDatiPostaElettronicaBean.getListaEstremiDocTrasmessi().size() > 0) {
				DettaglioPostaElettronicaEstremiDoc lDettaglioPostaElettronicaEstremiDoc = new DettaglioPostaElettronicaEstremiDoc();
				lDettaglioPostaElettronicaEstremiDoc = lXmlDatiPostaElettronicaBean.getListaEstremiDocTrasmessi().get(0);
				idUdEmail = lDettaglioPostaElettronicaEstremiDoc.getIdUD();
			}

			// ---------------------------------------------------------------------------------------------------------------------------------------------------------
			// Associo la mail all'UD ( nella T_REG_PROT_VS_EMAIL )
			// ---------------------------------------------------------------------------------------------------------------------------------------------------------
			if (idUdEmail != null && !idUdEmail.equalsIgnoreCase("") && idEmailNewIn != null && !idEmailNewIn.equalsIgnoreCase("")) {
				// Inizializzo l'INPUT
				TRegProtVsEmailBean lTRegProtVsEmailSaveIn = new TRegProtVsEmailBean();
				lTRegProtVsEmailSaveIn.setAnnoReg(lXmlDatiPostaElettronicaBean.getAnnoRegUD() != null && !"".equals(lXmlDatiPostaElettronicaBean.getAnnoRegUD())
						? new Short(lXmlDatiPostaElettronicaBean.getAnnoRegUD()) : null);
				lTRegProtVsEmailSaveIn.setCategoriaReg(lXmlDatiPostaElettronicaBean.getCategoriaRegUD());
				lTRegProtVsEmailSaveIn.setIdEmail(idEmailNewIn);
				lTRegProtVsEmailSaveIn.setIdProvReg(idUdEmail);
				lTRegProtVsEmailSaveIn.setNumReg(lXmlDatiPostaElettronicaBean.getNumRegUD() != null && !"".equals(lXmlDatiPostaElettronicaBean.getNumRegUD())
						? new BigDecimal(lXmlDatiPostaElettronicaBean.getNumRegUD()) : null);
				lTRegProtVsEmailSaveIn.setSiglaRegistro(lXmlDatiPostaElettronicaBean.getSiglaRegUD());
				lTRegProtVsEmailSaveIn.setTsReg(lXmlDatiPostaElettronicaBean.getTsRegUD());
				lTRegProtVsEmailSaveIn.setIdRegProtEmail(KeyGenerator.gen());

				// Eseguo il servizio
				try {
					TRegProtVsEmailBean lTRegProtVsEmailBeanOut = AurigaMailService.getDaoTRegProtVsEmail().save(getLocale(), lTRegProtVsEmailSaveIn);
				} catch (Exception e) {
					String msgError = "Fallita associazione e-mail inviata al documento trasmesso con la mail da cui è stata creata come copia.";
					addMessage(msgError, "", MessageType.WARNING);
				}
			}
		}
	}

	private String recuperaAttachments(DettaglioEmailBean lDettaglioEmailBean, String fileNameAttach) throws Exception {
		if (lDettaglioEmailBean != null) {
			for (DettaglioEmailAllegatoBean lDettaglioEmailAllegatoBean : lDettaglioEmailBean.getAllegati()) {
				if (lDettaglioEmailAllegatoBean.getNomeFileAllegato().equals(fileNameAttach)) {
					return lDettaglioEmailAllegatoBean.getUriFileAllegato();
				}
			}
		}
		throw new StoreException("Impossibile recuperare l'allegato " + fileNameAttach);
	}

	private DettaglioEmailBean getAttachInDettaglioEmailBean(String idEmail) throws StoreException {
		DettaglioEmailBean lDettaglioEmailBean = new DettaglioEmailBean();
		lDettaglioEmailBean.setIdEmail(idEmail);
		AurigaLoadDettaglioEmailDataSource lAurigaLoadDettaglioEmailDataSource = new AurigaLoadDettaglioEmailDataSource();
		try {
			lAurigaLoadDettaglioEmailDataSource.setSession(getSession());
			lDettaglioEmailBean = lAurigaLoadDettaglioEmailDataSource.retrieveAttach(lDettaglioEmailBean);
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		return lDettaglioEmailBean;
	}

	public InvioMailResultBean invioMailInoltroMassivo(SalvaInBozzaMailBean pInvioMailBean) throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
	
		//Controllo il body
		pInvioMailBean = checkBodyHtml(pInvioMailBean);

		MailLoginBean lMailLoginBean = new MailLoginBean();
		lMailLoginBean.setSchema(lAurigaLoginBean.getSchema());
		lMailLoginBean.setToken(lAurigaLoginBean.getToken());
		lMailLoginBean.setUserId(lAurigaLoginBean.getIdUserLavoro());
		lMailLoginBean.setIdUtente(lAurigaLoginBean.getIdUtente());

		SenderBean senderBean = new SenderBean();
		senderBean.setIdUtenteModPec(lAurigaLoginBean.getSpecializzazioneBean().getIdUtenteModPec());
		
		if(ParametriDBUtil.getParametroDB(getSession(), "CASELLE_ALIAS_UTENTE_IN_INVIO") != null
				&& !"".equalsIgnoreCase(ParametriDBUtil.getParametroDB(getSession(), "CASELLE_ALIAS_UTENTE_IN_INVIO"))) {
			String caselleAliasUtenteInvio = ParametriDBUtil.getParametroDB(getSession(), "CASELLE_ALIAS_UTENTE_IN_INVIO");
			StringSplitterServer mittenteDBSplit = new StringSplitterServer(caselleAliasUtenteInvio, ";");
			while (mittenteDBSplit.hasMoreElements()) {
				if(pInvioMailBean.getMittente().equalsIgnoreCase(mittenteDBSplit.nextToken().trim())) {
					if(StringUtils.isNotBlank(lAurigaLoginBean.getDelegaDenominazione())) {
						senderBean.setAliasAddressFrom(lAurigaLoginBean.getDelegaDenominazione());
						// Setto anche il Reply-To
						senderBean.setReplyTo(getEmailReplyTo(lAurigaLoginBean.getUseridForPrefs()));
					} else {
						senderBean.setAliasAddressFrom(lAurigaLoginBean.getSpecializzazioneBean().getDesUserOut());
						// Setto anche il Reply-To
						senderBean.setReplyTo(getEmailReplyTo(lAurigaLoginBean.getUserid()));
					}
					break;
				}
			} 
		}

		// bean.setSaveToSent(true);
		senderBean.setFlgInvioSeparato(pInvioMailBean.getFlgInvioSeparato() != null && pInvioMailBean.getFlgInvioSeparato() 
				? true : false);
		senderBean.setAccount(pInvioMailBean.getMittente());

		if (StringUtils.isNotEmpty(pInvioMailBean.getDestinatari())) {
			List<String> destinatari = new ArrayList<String>();
			String[] lStringDestinatari = IndirizziEmailSplitter.split(pInvioMailBean.getDestinatari());
			destinatari = Arrays.asList(lStringDestinatari);
			senderBean.setAddressTo(destinatari);
		}

		if (StringUtils.isNotEmpty(pInvioMailBean.getDestinatariCC())) {
			List<String> destinatariCC = new ArrayList<String>();
			String[] lStringDestinatariCC = IndirizziEmailSplitter.split(pInvioMailBean.getDestinatariCC());
			destinatariCC = Arrays.asList(lStringDestinatariCC);
			senderBean.setAddressCc(destinatariCC);
		}
		
		if(pInvioMailBean.getCasellaIsPec() == null || !"true".equals(pInvioMailBean.getCasellaIsPec())){
			if (StringUtils.isNotEmpty(pInvioMailBean.getDestinatariCCN())) {
				List<String> destinatariCCN = new ArrayList<String>();
				String[] lStringDestinatariCCN = IndirizziEmailSplitter.split(pInvioMailBean.getDestinatariCCN());
				destinatariCCN = Arrays.asList(lStringDestinatariCCN);
				senderBean.setAddressBcc(destinatariCCN);
			}
		}
		
		senderBean.setAddressFrom(pInvioMailBean.getMittente());
		senderBean.setSubject(pInvioMailBean.getOggetto());
		if (pInvioMailBean.getTextHtml().equals("text")) {
			senderBean.setBody(pInvioMailBean.getBodyText());
			senderBean.setIsHtml(false);
		} else {
			senderBean.setBody("<html>" + pInvioMailBean.getBodyHtml() + "</html>");
			senderBean.setIsHtml(true);
		}

		List<TEmailMgoBean> lTEmailMgoBeanList = new ArrayList<TEmailMgoBean>();
		for (PostaElettronicaBean lPostaElettronicaBean : pInvioMailBean.getListaRecord()) {
			TEmailMgoBean lTEmailMgoBean = AurigaMailService.getDaoTEmailMgo().get(getLocale(), lPostaElettronicaBean.getIdEmail());
			lTEmailMgoBeanList.add(lTEmailMgoBean);
		}

		RispostaInoltroBean lRispostaInoltroBean = new RispostaInoltroBean();
		lRispostaInoltroBean.setMailOrigInoltroMassivo(lTEmailMgoBeanList);
		String tipoRel = getExtraparams().get("tipoRel") != null ? getExtraparams().get("tipoRel") : "";
		if (tipoRel.equals("risposta"))
			lRispostaInoltroBean.setRispInol(RispostaInoltro.RISPOSTA);
		else if (tipoRel.equals("inoltro"))
			lRispostaInoltroBean.setRispInol(RispostaInoltro.INOLTRO);
		senderBean.setRispInol(lRispostaInoltroBean);

		if (pInvioMailBean.getAttach() != null && pInvioMailBean.getAttach().size() > 0) {
			List<SenderAttachmentsBean> lista = new ArrayList<SenderAttachmentsBean>();
			for (AttachmentBean lAttachmentBean : pInvioMailBean.getAttach()) {
				SenderAttachmentsBean atta = new SenderAttachmentsBean();
				//atta.setContent(IOUtils.toByteArray(StorageImplementation.getStorage().extract(lAttachmentBean.getUriAttach())));
				atta.setFile(StorageImplementation.getStorage().extractFile(lAttachmentBean.getUriAttach()));
				atta.setFilename(lAttachmentBean.getFileNameAttach());
				atta.setOriginalName(lAttachmentBean.getFileNameAttach());
				atta.setFirmato(false);
				atta.setMimetype("message/rfc822");
				
				//Per ottenere le informazioni relative a encoding, algoritmo relativo e impronta
				InfoFileUtility lFileUtility = new InfoFileUtility();
				RebuildedFile lRebuildedFile = new RebuildedFile();
				String uri = lAttachmentBean.getUriAttach();
				lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(uri));
				
				MimeTypeFirmaBean lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(),
						lAttachmentBean.getInfoFileAttach().getCorrectFileName(), false, null);
				
				atta.setDimensione(new BigDecimal(lMimeTypeFirmaBean.getBytes()));
				atta.setAlgoritmo(lMimeTypeFirmaBean.getAlgoritmo());
				atta.setEncoding(lMimeTypeFirmaBean.getEncoding());
				atta.setImpronta(lMimeTypeFirmaBean.getImpronta());
				
				
				lista.add(atta);
			}
			senderBean.setAttachments(lista);
		}

		/**
		 * Se presente valorizzo la lista degli Item In Lavorazione,Tab: Appunti & Note
		 */
		if (pInvioMailBean.getListaItemInLavorazione() != null && pInvioMailBean.getListaItemInLavorazione().size() > 0) {
			populateItemFileInvioMail(senderBean, pInvioMailBean);
		}
		
		ResultBean<EmailSentReferenceBean> output = AurigaMailService.getMailSenderService().sendandsaveworkitems(getLocale(), senderBean, lMailLoginBean);
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				throw new StoreException(output.getDefaultMessage());
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		return new InvioMailResultBean();
	}

	public InvioMailResultBean sbloccaMail(InvioMailBean pInvioMailBean) throws Exception {
		LockUnlockMail lLockUnlockEmail = new LockUnlockMail(getSession());
		try {
			lLockUnlockEmail.unlockMail(pInvioMailBean.getIdEmail());
		} catch (Exception e) {
		}
		return new InvioMailResultBean();
	}

	protected DraftAndWorkItemsBean getDatiBozza(SalvaInBozzaMailBean bean, String tipoRel, String operazione, Integer posizione) throws Exception {

		DraftAndWorkItemsBean input = new DraftAndWorkItemsBean();
		InvioMailXmlBean xmlItem = new InvioMailXmlBean();
		
		xmlItem.setFlgInvioSeparato(bean.getFlgInvioSeparato() != null && bean.getFlgInvioSeparato() ? true : false);

		if (StringUtils.isNotBlank(bean.getIdEmailPrincipale())) {
			xmlItem.setDimensione(bean.getDimensioneMail());
			xmlItem.setUri(bean.getUriMail());
		}

		// FIXME: Secondo me questo è inutile perchè operazione non viene mai settata come extraparams (Vale sempre N)
		if ("I".equals(operazione)) {
			xmlItem.setEmailPredTipoRel("I");
			xmlItem.setEmailPredIdEmail(bean.getIdEmail());
		} else if ("R".equals(operazione)) {
			xmlItem.setEmailPredTipoRel("R");
			xmlItem.setEmailPredIdEmail(bean.getIdEmail());
		} else if ("N".equals(operazione)) {
			xmlItem.setEmailPredTipoRel(null);
			xmlItem.setEmailPredIdEmail(null);
		}

		xmlItem.setAccountMittente(
				bean.getAliasAccountMittente() != null && !"".equals(bean.getAliasAccountMittente()) ? bean.getAliasAccountMittente() : bean.getMittente());
		xmlItem.setSubject(bean.getOggetto());
		xmlItem.setFlgEmailFirmata(0);

		if (bean.getTextHtml() != null) {
			if (bean.getTextHtml().equals("text")) {
				xmlItem.setBody(bean.getBodyText());
			} else {
				xmlItem.setBody("<html>" + bean.getBodyHtml() + "</html>");
			}
		}

		
		List<DestinatariInvioMailXmlBean> xmlDestinatariInvioMail = new ArrayList<DestinatariInvioMailXmlBean>();
		if (StringUtils.isNotEmpty(bean.getDestinatari())) {
			List<String> destinatari = new ArrayList<String>();
			String[] lStringDestinatari = IndirizziEmailSplitter.split(bean.getDestinatari());
			destinatari = Arrays.asList(lStringDestinatari);
			for (String destinatarioItem : destinatari) {
				DestinatariInvioMailXmlBean lDestinatariInvioMailXmlBean = new DestinatariInvioMailXmlBean();
				lDestinatariInvioMailXmlBean.setIndirizzo(destinatarioItem.trim());
				lDestinatariInvioMailXmlBean.setTipo("P");
				xmlDestinatariInvioMail.add(lDestinatariInvioMailXmlBean);
			}
		}
		
		if (StringUtils.isNotEmpty(bean.getDestinatariCC())) {
			List<String> ldestinatariCCList = new ArrayList<String>();
			String[] lStringDestinatariCC = IndirizziEmailSplitter.split(bean.getDestinatariCC());
			ldestinatariCCList = Arrays.asList(lStringDestinatariCC);
			for (String destinatarioItem : ldestinatariCCList) {
				DestinatariInvioMailXmlBean lDestinatariInvioMailXmlBean = new DestinatariInvioMailXmlBean();
				lDestinatariInvioMailXmlBean.setIndirizzo(destinatarioItem.trim());
				lDestinatariInvioMailXmlBean.setTipo("CC");
				xmlDestinatariInvioMail.add(lDestinatariInvioMailXmlBean);
			}
		}
		
		if(bean.getCasellaIsPec() == null || !"true".equals(bean.getCasellaIsPec())){
			if (StringUtils.isNotEmpty(bean.getDestinatariCCN())) {
				List<String> ldestinatariCCNList = new ArrayList<String>();
				String[] lStringDestinatariCCN = IndirizziEmailSplitter.split(bean.getDestinatariCCN());
				ldestinatariCCNList = Arrays.asList(lStringDestinatariCCN);
				for (String destinatarioItem : ldestinatariCCNList) {
					DestinatariInvioMailXmlBean lDestinatariInvioMailXmlBean = new DestinatariInvioMailXmlBean();
					lDestinatariInvioMailXmlBean.setIndirizzo(destinatarioItem.trim());
					lDestinatariInvioMailXmlBean.setTipo("CCN");
					xmlDestinatariInvioMail.add(lDestinatariInvioMailXmlBean);
				}
			}
		}
		
		xmlItem.setListaDestinatari(xmlDestinatariInvioMail);

		List<AllegatiInvioMailXmlBean> xmlAllegatiInvioMail = new ArrayList<AllegatiInvioMailXmlBean>();
		List<SenderAttachmentsBean> lSenderAttachmentsBean = new ArrayList<SenderAttachmentsBean>();
		if (bean.getAttach() != null && bean.getAttach().size() > 0) {

			if (bean.getIdEmailPrincipale() != null && !"".equalsIgnoreCase(bean.getIdEmailPrincipale())) {

				for (AttachmentBean lAttachmentBean : bean.getAttach()) {
					AllegatiInvioMailXmlBean lAllegatiInvioMailXmlBean = new AllegatiInvioMailXmlBean();
					String uri = lAttachmentBean.getUriAttach();
					if (uri.equals("_noUri")) {
						uri = recuperaAttachments(getAttachInDettaglioEmailBean(bean.getIdEmailPrincipale()), lAttachmentBean.getFileNameAttach());
					}

					InfoFileUtility lFileUtility = new InfoFileUtility();
					RebuildedFile lRebuildedFile = new RebuildedFile();
					lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(uri));
					MimeTypeFirmaBean lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(),
							lAttachmentBean.getInfoFileAttach().getCorrectFileName(), false, null);

					lAllegatiInvioMailXmlBean.setNomeFile(StringUtils.isNotBlank(lMimeTypeFirmaBean.getCorrectFileName())
							? lMimeTypeFirmaBean.getCorrectFileName() : lAttachmentBean.getFileNameAttach());
					lAllegatiInvioMailXmlBean.setFlgFirmato(lMimeTypeFirmaBean.isFirmato() ? 1 : 0);
					lAllegatiInvioMailXmlBean.setMimetype(lMimeTypeFirmaBean.getMimetype());

					lAllegatiInvioMailXmlBean.setDimensione(new BigDecimal(lMimeTypeFirmaBean.getBytes()));

					lAllegatiInvioMailXmlBean.setAlgoritmo(lMimeTypeFirmaBean.getAlgoritmo());
					lAllegatiInvioMailXmlBean.setEncoding(lMimeTypeFirmaBean.getEncoding());
					lAllegatiInvioMailXmlBean.setImpronta(lMimeTypeFirmaBean.getImpronta());
					xmlAllegatiInvioMail.add(lAllegatiInvioMailXmlBean);

					SenderAttachmentsBean atta = new SenderAttachmentsBean();
					//atta.setContent(IOUtils.toByteArray(StorageImplementation.getStorage().extract(uri)));
					atta.setFile(StorageImplementation.getStorage().extractFile(uri));
					atta.setFilename(StringUtils.isNotBlank(lMimeTypeFirmaBean.getCorrectFileName()) ? lMimeTypeFirmaBean.getCorrectFileName()
							: lAttachmentBean.getFileNameAttach());
					atta.setFirmato(lMimeTypeFirmaBean.isFirmato());
					atta.setMimetype(lMimeTypeFirmaBean.getMimetype());
					atta.setPdfConCommenti(lMimeTypeFirmaBean.isPdfConCommenti());
					atta.setPdfEditabile(lMimeTypeFirmaBean.isPdfEditabile());
					atta.setOriginalName(lAttachmentBean.getFileNameAttach());
					lSenderAttachmentsBean.add(atta);

				}

				xmlItem.setlSenderAttachmentsBean(lSenderAttachmentsBean);
				xmlItem.setListaAllegati(xmlAllegatiInvioMail);

			} else {
				for (AttachmentBean lAttachmentBean : bean.getAttach()) {
					AllegatiInvioMailXmlBean lAllegatiInvioMailXmlBean = new AllegatiInvioMailXmlBean();
					String uri = lAttachmentBean.getUriAttach();

					InfoFileUtility lFileUtility = new InfoFileUtility();
					RebuildedFile lRebuildedFile = new RebuildedFile();
					lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(uri));
					MimeTypeFirmaBean lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(),
							lAttachmentBean.getInfoFileAttach().getCorrectFileName(), false, null);

					lAllegatiInvioMailXmlBean.setNomeFile(StringUtils.isNotBlank(lMimeTypeFirmaBean.getCorrectFileName())
							? lMimeTypeFirmaBean.getCorrectFileName() : lAttachmentBean.getFileNameAttach());
					lAllegatiInvioMailXmlBean.setFlgFirmato(lMimeTypeFirmaBean.isFirmato() ? 1 : 0);
					lAllegatiInvioMailXmlBean.setMimetype(lMimeTypeFirmaBean.getMimetype());

					lAllegatiInvioMailXmlBean.setDimensione(new BigDecimal(lMimeTypeFirmaBean.getBytes()));

					lAllegatiInvioMailXmlBean.setAlgoritmo(lMimeTypeFirmaBean.getAlgoritmo());
					lAllegatiInvioMailXmlBean.setEncoding(lMimeTypeFirmaBean.getEncoding());
					lAllegatiInvioMailXmlBean.setImpronta(lMimeTypeFirmaBean.getImpronta());
					xmlAllegatiInvioMail.add(lAllegatiInvioMailXmlBean);

					SenderAttachmentsBean atta = new SenderAttachmentsBean();
					//atta.setContent(IOUtils.toByteArray(StorageImplementation.getStorage().extract(uri)));
					atta.setFile(StorageImplementation.getStorage().extractFile(uri));
					atta.setFilename(StringUtils.isNotBlank(lMimeTypeFirmaBean.getCorrectFileName()) ? lMimeTypeFirmaBean.getCorrectFileName()
							: lAttachmentBean.getFileNameAttach());
					atta.setFirmato(lMimeTypeFirmaBean.isFirmato());
					atta.setMimetype(lMimeTypeFirmaBean.getMimetype());
					atta.setPdfConCommenti(lMimeTypeFirmaBean.isPdfConCommenti());
					atta.setPdfEditabile(lMimeTypeFirmaBean.isPdfEditabile());
					atta.setOriginalName(lAttachmentBean.getFileNameAttach());
					lSenderAttachmentsBean.add(atta);

				}

				xmlItem.setlSenderAttachmentsBean(lSenderAttachmentsBean);
				xmlItem.setListaAllegati(xmlAllegatiInvioMail);
			}
		}
		xmlItem.setFlgRichConfermaLettura(bean.getConfermaLettura() != null && bean.getConfermaLettura() ? 1 : 0);

		List<File> listaFileItemLavorazione = salvaListaItemLavorazione(bean, xmlItem);

		input.setDatiemailin(xmlItem);
		input.setListaFileItemLavorazione(listaFileItemLavorazione);
		return input;
	}

	/**
	 * Metodo di popolamento della lista degli ItemLavorazioneMailXmlBean in fase di INVIO_BOZZA_MAIL
	 * 
	 * @param invioMailBean
	 * @return List<ItemLavorazioneMailXmlBean>
	 * @throws StorageException
	 */
	private List<File> salvaListaItemLavorazione(SalvaInBozzaMailBean bean, InvioMailXmlBean xmlItem) throws StorageException {
		List<ItemLavorazioneMailXmlBean> listaItemLavorazione = new ArrayList<ItemLavorazioneMailXmlBean>();
		List<File> listaFileAllegati = new ArrayList<File>();
		if (bean.getListaItemInLavorazione() != null && bean.getListaItemInLavorazione().size() > 0) {
			for (ItemLavorazioneMailBean item : bean.getListaItemInLavorazione()) {
				// Mi serve per scartare gli item vuoti
				boolean flagItemValido = false;
				ItemLavorazioneMailXmlBean lItemLavorazioneMailXmlBean = new ItemLavorazioneMailXmlBean();
				lItemLavorazioneMailXmlBean.setSceltaTipo(item.getItemLavTipo() != null && !"".equals(item.getItemLavTipo()) ? item.getItemLavTipo() : "F");
				lItemLavorazioneMailXmlBean.setNumProgressivo(item.getItemLavNrItem() != null ? String.valueOf(item.getItemLavNrItem()) : null);
				lItemLavorazioneMailXmlBean.setCaricatoDa(item.getItemLavCaricatoDa());
				lItemLavorazioneMailXmlBean.setDataOraCaricamento(item.getItemLavDataOraCaricamento() != null ? item.getItemLavDataOraCaricamento() : null);
				lItemLavorazioneMailXmlBean.setModificatoDa(item.getItemLavModificatoDa());
				lItemLavorazioneMailXmlBean.setDataOraModifica(item.getItemLavDataOraModifica() != null ? item.getItemLavDataOraModifica() : null);
				if (lItemLavorazioneMailXmlBean.getSceltaTipo().equals("F")) {
					if (StringUtils.isNotBlank(item.getItemLavUriFile())) {
						flagItemValido = true;
						lItemLavorazioneMailXmlBean.setUriFile(item.getItemLavUriFile());
						// uri del file originale, per l'eventuale cancellazione
						lItemLavorazioneMailXmlBean.setUriFileSaved(item.getItemLavUriFileSaved());
						lItemLavorazioneMailXmlBean.setNomeFile(item.getItemLavNomeFile());
						lItemLavorazioneMailXmlBean
								.setDimensioneFile(item.getItemLavInfoFile() != null ? String.valueOf(item.getItemLavInfoFile().getBytes()) : null);
						lItemLavorazioneMailXmlBean.setMimeType(item.getItemLavInfoFile() != null ? item.getItemLavInfoFile().getMimetype() : null);
						lItemLavorazioneMailXmlBean.setFlgFileFirmato(item.getItemLavFlgFileFirmato() != null && !"".equals(item.getItemLavFlgFileFirmato())
								&& "true".equals(item.getItemLavFlgFileFirmato()) ? 1 : 0);
						lItemLavorazioneMailXmlBean.setImpronta(item.getItemLavInfoFile() != null ? item.getItemLavInfoFile().getImpronta() : null);
						lItemLavorazioneMailXmlBean.setAgoritmoImpronta(item.getItemLavInfoFile() != null ? item.getItemLavInfoFile().getAlgoritmo() : null);
						lItemLavorazioneMailXmlBean.setAlgoritmoEncoding(item.getItemLavInfoFile() != null ? item.getItemLavInfoFile().getEncoding() : null);
						lItemLavorazioneMailXmlBean.setFlgConvertibilePdf(item.getItemLavFlgConvertibilePdf() != null
								&& !"".equals(item.getItemLavFlgConvertibilePdf()) && "true".equals(item.getItemLavFlgConvertibilePdf()) ? 1 : 0);
						if (isFileCambiato(item.getItemLavUriFile(), item.getItemLavUriFileSaved())) {
							listaFileAllegati.add(StorageImplementation.getStorage().extractFile(item.getItemLavUriFile()));
							lItemLavorazioneMailXmlBean.setPosFileInLista(listaFileAllegati.size() - 1);
						}
					}
				} else if (lItemLavorazioneMailXmlBean.getSceltaTipo().equals("AT")) {
					if ((StringUtils.isNotBlank(item.getItemLavCommento())) || (StringUtils.isNotBlank(item.getItemLavTag()))) {
						flagItemValido = true;
						// uri dell'eventuale file da cancellare
						lItemLavorazioneMailXmlBean.setUriFileSaved(item.getItemLavUriFileSaved());
						lItemLavorazioneMailXmlBean.setNota(item.getItemLavCommento());
						lItemLavorazioneMailXmlBean.setTag(item.getItemLavTag());
					}
				}
				if (flagItemValido) {
					listaItemLavorazione.add(lItemLavorazioneMailXmlBean);
				}
			}
			xmlItem.setListaItemLavorazione(listaItemLavorazione);
		}
		return listaFileAllegati;
	}

	private boolean isFileCambiato(String uri2, String uri1) {
		if ((StringUtils.isNotBlank(uri1)) && (StringUtils.isNotBlank(uri2)) && (uri1.equalsIgnoreCase(uri2))) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Metodo di popolamento della lista degli ItemLavorazioneMailXmlBean in fase di INVIO_MAIL ed INOLTRO_MASSIVO_MAIL
	 * 
	 * @param invioMailBean
	 * @return List<ItemLavorazioneMailXmlBean>
	 * @throws StorageException
	 */
	private void populateItemFileInvioMail(SenderBean senderBean, SalvaInBozzaMailBean invioMailBean) throws StorageException {
		List<ItemLavorazioneMailXmlBean> listaItemLavorazione = new ArrayList<ItemLavorazioneMailXmlBean>();
		List<File> listaFileAllegati = new ArrayList<File>();
		for (ItemLavorazioneMailBean item : invioMailBean.getListaItemInLavorazione()) {
			// Mi serve per scartare gli item vuoti
			boolean flagItemValido = false;
			ItemLavorazioneMailXmlBean lItemLavorazioneMailXmlBean = new ItemLavorazioneMailXmlBean();
			lItemLavorazioneMailXmlBean.setSceltaTipo(item.getItemLavTipo() != null && !"".equals(item.getItemLavTipo()) ? item.getItemLavTipo() : "F");
			lItemLavorazioneMailXmlBean.setNumProgressivo(item.getItemLavNrItem() != null ? String.valueOf(item.getItemLavNrItem()) : null);
			lItemLavorazioneMailXmlBean.setCaricatoDa(item.getItemLavCaricatoDa());
			lItemLavorazioneMailXmlBean.setDataOraCaricamento(item.getItemLavDataOraCaricamento() != null ? item.getItemLavDataOraCaricamento() : null);
			lItemLavorazioneMailXmlBean.setModificatoDa(item.getItemLavModificatoDa());
			lItemLavorazioneMailXmlBean.setDataOraModifica(item.getItemLavDataOraModifica() != null ? item.getItemLavDataOraModifica() : null);
			lItemLavorazioneMailXmlBean.setFlgNonModCancLocked((item.getFlgNonModCancLocked() != null && item.getFlgNonModCancLocked()) ? 1 : 0);
			if (lItemLavorazioneMailXmlBean.getSceltaTipo().equals("F")) {
				if (StringUtils.isNotBlank(item.getItemLavUriFile())) {
					flagItemValido = true;
					lItemLavorazioneMailXmlBean.setUriFile(item.getItemLavUriFile());
					// uri del file originale, per l'eventuale cancellazione
					lItemLavorazioneMailXmlBean.setUriFileSaved(item.getItemLavUriFileSaved());
					lItemLavorazioneMailXmlBean.setNomeFile(item.getItemLavNomeFile());
					lItemLavorazioneMailXmlBean
							.setDimensioneFile(item.getItemLavInfoFile() != null ? String.valueOf(item.getItemLavInfoFile().getBytes()) : null);
					lItemLavorazioneMailXmlBean.setMimeType(item.getItemLavInfoFile() != null ? item.getItemLavInfoFile().getMimetype() : null);
					lItemLavorazioneMailXmlBean.setFlgFileFirmato(item.getItemLavFlgFileFirmato() != null && !"".equals(item.getItemLavFlgFileFirmato())
							&& "true".equals(item.getItemLavFlgFileFirmato()) ? 1 : 0);
					lItemLavorazioneMailXmlBean.setImpronta(item.getItemLavInfoFile() != null ? item.getItemLavInfoFile().getImpronta() : null);
					lItemLavorazioneMailXmlBean.setAgoritmoImpronta(item.getItemLavInfoFile() != null ? item.getItemLavInfoFile().getAlgoritmo() : null);
					lItemLavorazioneMailXmlBean.setAlgoritmoEncoding(item.getItemLavInfoFile() != null ? item.getItemLavInfoFile().getEncoding() : null);
					lItemLavorazioneMailXmlBean.setFlgConvertibilePdf(item.getItemLavFlgConvertibilePdf() != null
							&& !"".equals(item.getItemLavFlgConvertibilePdf()) && "true".equals(item.getItemLavFlgConvertibilePdf()) ? 1 : 0);
					if (isFileCambiato(item.getItemLavUriFile(), item.getItemLavUriFileSaved())) {
						listaFileAllegati.add(StorageImplementation.getStorage().extractFile(item.getItemLavUriFile()));
						lItemLavorazioneMailXmlBean.setPosFileInLista(listaFileAllegati.size() - 1);
					}
				}
			} else if (lItemLavorazioneMailXmlBean.getSceltaTipo().equals("AT")) {
				if ((StringUtils.isNotBlank(item.getItemLavCommento())) || (StringUtils.isNotBlank(item.getItemLavTag()))) {
					flagItemValido = true;
					// uri dell'eventuale file da cancellare
					lItemLavorazioneMailXmlBean.setUriFileSaved(item.getItemLavUriFileSaved());
					lItemLavorazioneMailXmlBean.setNota(item.getItemLavCommento());
					lItemLavorazioneMailXmlBean.setTag(item.getItemLavTag());
				}
			}
			if (flagItemValido) {
				listaItemLavorazione.add(lItemLavorazioneMailXmlBean);
			}
		}
		senderBean.setListaItemInLavorazioneInvioMail(listaItemLavorazione);
		senderBean.setListaFileItemLavorazione(listaFileAllegati);
	}
	
	public InvioMailMultiDestinatariResultBean invioMailMultiDestinatariXls(SalvaInBozzaMailBean pInvioMailBean) throws Exception {

		//Controllo il body
		pInvioMailBean = checkBodyHtml(pInvioMailBean);

		// Inizializzo l'INPUT
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		String idUser =  (loginBean.getUserid() != null ? loginBean.getUserid() : null);
		
		SchemaBean lSchemaBean = new SchemaBean();
		lSchemaBean.setSchema(loginBean.getSchema());
		
		DmpkBmanagerInsbatchBean input = new DmpkBmanagerInsbatchBean();
		input.setIddominioin(AurigaUserUtil.getLoginInfo(getSession()).getSpecializzazioneBean().getIdDominio());
		input.setUseridin(idUser);
		
		input.setTipojobin("INVIO_MAIL_VS_LISTA_DEST_XLS");
		
		// Setto xml con i parametri del servizio
		String parametriin = creaXmlParametriIn(pInvioMailBean);
		input.setParametrixmlin(parametriin);
		
		
		// Eseguo il servizio
		DmpkBmanagerInsbatch lservizio = new DmpkBmanagerInsbatch();
		StoreResultBean<DmpkBmanagerInsbatchBean> output = lservizio.execute(getLocale(), lSchemaBean, input);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
				if (output.isInError()) {
					mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
					throw new StoreException(output);
				} else {
					addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
				}
		}
		
		
		InvioMailMultiDestinatariResultBean result = new InvioMailMultiDestinatariResultBean();		
		result.setIdJobOut(output.getResultBean().getIdjobout() != null ? String.valueOf(output.getResultBean().getIdjobout()) : null);
		//result.setIdJobOut("1");		
		return result;
	}
	
	private String creaXmlParametriIn(SalvaInBozzaMailBean pInvioMailBean) throws Exception {
	
		String xmlOut = null;
		List<InsBatchParametriXmlBean> listaParametriXmlIn = new ArrayList<InsBatchParametriXmlBean>();
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		// Salvo il file nello storage
		GestioneDocumenti gestioneDocumenti = new GestioneDocumenti();
		AggiungiDocumentoInBean  lInput  = new AggiungiDocumentoInBean();
		AggiungiDocumentoOutBean lOutput = new AggiungiDocumentoOutBean();
		
		File lFile = StorageImplementation.getStorage().extractFile(pInvioMailBean.getUriXls());
		lInput.setDisplayFilename(pInvioMailBean.getNomeFileXls());
		lInput.setFile(lFile);
		lOutput = gestioneDocumenti.aggiungidocumento(getLocale(), lAurigaLoginBean, lInput);
		
		// Parametro URI FILE
		String uriFileXlsNew = lOutput.getUri();
		InsBatchParametriXmlBean parametroUriFileBean = new InsBatchParametriXmlBean();
		parametroUriFileBean.setNomeParametro("URI_FILE");
		parametroUriFileBean.setTipoParametro("VARCHAR2");
		parametroUriFileBean.setValoreParametro(uriFileXlsNew);
		parametroUriFileBean.setVerso("IN");
		listaParametriXmlIn.add(parametroUriFileBean);
		
		// Parametro ID ACCOUNT MITTENTE MAIL
		InsBatchParametriXmlBean parametroIdAccountMittenteBean = new InsBatchParametriXmlBean();
		parametroIdAccountMittenteBean.setNomeParametro("ID_ACCOUNT_MITTENTE_MAIL");
		parametroIdAccountMittenteBean.setTipoParametro("VARCHAR2");
		parametroIdAccountMittenteBean.setValoreParametro(pInvioMailBean.getMittente());
		parametroIdAccountMittenteBean.setVerso("IN");
		listaParametriXmlIn.add(parametroIdAccountMittenteBean);
		
		// Parametro OGGETTO
		InsBatchParametriXmlBean parametroOggettoBean = new InsBatchParametriXmlBean();
		parametroOggettoBean.setNomeParametro("OGGETTO_MAIL");
		parametroOggettoBean.setTipoParametro("VARCHAR2");
		parametroOggettoBean.setValoreParametro(pInvioMailBean.getOggetto());
		parametroOggettoBean.setVerso("IN");
		listaParametriXmlIn.add(parametroOggettoBean);
		
		// Parametro CORPO_MAIL
		InsBatchParametriXmlBean parametroCorpoMailBean = new InsBatchParametriXmlBean();
		parametroCorpoMailBean.setNomeParametro("CORPO_MAIL");
		parametroCorpoMailBean.setTipoParametro("CLOB");
		if (pInvioMailBean.getTextHtml().equals("text")) {
			parametroCorpoMailBean.setValoreParametro(pInvioMailBean.getBodyText());
		} else {
			parametroCorpoMailBean.setValoreParametro("<html>" + pInvioMailBean.getBodyHtml() + "</html>");
		}
		parametroCorpoMailBean.setVerso("IN");
		listaParametriXmlIn.add(parametroCorpoMailBean);

		// Parametro RIGA_INIZIO_FILE 
		Integer rigaXlsDa = pInvioMailBean.getRigaXlsDa() != null ? new Integer(pInvioMailBean.getRigaXlsDa()) : new Integer(0);
		if (rigaXlsDa>0) rigaXlsDa--;
		InsBatchParametriXmlBean parametroRigaInizioFileBean = new InsBatchParametriXmlBean();
		parametroRigaInizioFileBean.setNomeParametro("RIGA_INIZIO_FILE");
		parametroRigaInizioFileBean.setTipoParametro("NUMERIC");
		parametroRigaInizioFileBean.setValoreParametro(String.valueOf(rigaXlsDa));
		parametroRigaInizioFileBean.setVerso("IN");
		listaParametriXmlIn.add(parametroRigaInizioFileBean);
		
		// Parametro RIGA_FINE_FILE 
		Integer rigaXlsA = pInvioMailBean.getRigaXlsA() != null ? new Integer(pInvioMailBean.getRigaXlsA()) : null;
		if (rigaXlsA != null && rigaXlsA>0) rigaXlsA--;
		String lrigaXlsA = (rigaXlsA !=null ? String.valueOf(rigaXlsA) : null) ;
		InsBatchParametriXmlBean parametroRigaFineFileBean = new InsBatchParametriXmlBean();
		parametroRigaFineFileBean.setNomeParametro("RIGA_FINE_FILE");
		parametroRigaFineFileBean.setTipoParametro("NUMERIC");
		parametroRigaFineFileBean.setValoreParametro(String.valueOf(lrigaXlsA));
		parametroRigaFineFileBean.setVerso("IN");
		listaParametriXmlIn.add(parametroRigaFineFileBean);

        // Parametro XML_CONTENUTI_FILE 
		String xmlContenuti = creaXmlContenutiFile(pInvioMailBean.getDettagliXlsIndirizziEmail());
		InsBatchParametriXmlBean parametroContenutiFileBean = new InsBatchParametriXmlBean();
		parametroContenutiFileBean.setNomeParametro("XML_CONTENUTI_FILE");
		parametroContenutiFileBean.setTipoParametro("CLOB");
		parametroContenutiFileBean.setValoreParametro(xmlContenuti);
		parametroContenutiFileBean.setVerso("IN");
		listaParametriXmlIn.add(parametroContenutiFileBean);
		
		// Parametro NRO_ALLEGATI_MAIL
		Integer nroAttach =  (pInvioMailBean.getAttach() != null && pInvioMailBean.getAttach().size() > 0   ? pInvioMailBean.getAttach().size() : new Integer(0)); 
		InsBatchParametriXmlBean parametroNroAllegatiMailBean = new InsBatchParametriXmlBean();
		parametroNroAllegatiMailBean.setNomeParametro("NRO_ALLEGATI_MAIL");
		parametroNroAllegatiMailBean.setTipoParametro("INTEGER");
		parametroNroAllegatiMailBean.setValoreParametro(String.valueOf(nroAttach));
		parametroNroAllegatiMailBean.setVerso("IN");
		listaParametriXmlIn.add(parametroNroAllegatiMailBean);
		
		// Parametro URI_ALLEGATI_MAIL
		String xmlAllegati = creaXmlAllegatiMultiDestinatariXls(pInvioMailBean.getAttach());
		
		InsBatchParametriXmlBean parametroUriAllegatiMailBean = new InsBatchParametriXmlBean();
		parametroUriAllegatiMailBean.setNomeParametro("URI_ALLEGATI_MAIL");
		parametroUriAllegatiMailBean.setTipoParametro("CLOB");
		parametroUriAllegatiMailBean.setValoreParametro(xmlAllegati);
		parametroUriAllegatiMailBean.setVerso("IN");
		listaParametriXmlIn.add(parametroUriAllegatiMailBean);
		
		Lista listaParametri = new Lista();
		for (InsBatchParametriXmlBean funzione : listaParametriXmlIn) {
			Riga riga = new Riga();
			
			// Colonna 1 - Nome parametro
			Colonna col1 = new Colonna();
			col1.setNro(new BigInteger("1"));
			col1.setContent(funzione.getNomeParametro());
			riga.getColonna().add(col1);
			
			// Colonna 2 - Tipo parametro
			Colonna col2 = new Colonna();
			col2.setNro(new BigInteger("2"));
			col2.setContent(funzione.getTipoParametro());
			riga.getColonna().add(col2);
			
			// Colonna 3 - Verso
			Colonna col3 = new Colonna();
			col3.setNro(new BigInteger("3"));
			col3.setContent(funzione.getVerso());
			riga.getColonna().add(col3);
			
			// Colonna 4 - Valore parametro
			Colonna col4 = new Colonna();
			col4.setNro(new BigInteger("4"));
			col4.setContent(funzione.getValoreParametro());
			riga.getColonna().add(col4);

			listaParametri.getRiga().add(riga);
		}
		xmlOut = marshal(listaParametri);
		return xmlOut;
	}
	
	
	
	private String creaXmlAllegatiMultiDestinatariXls(List<AttachmentBean> listaAttachIn) throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String xmlOut = null;
		
		List<InvioMultiDestinatariXlsAttachmentsXmlBean> listaAttachInvioMultiDestXlsXml = new ArrayList<InvioMultiDestinatariXlsAttachmentsXmlBean>();
		
		if (listaAttachIn != null && listaAttachIn.size() > 0){
			for (AttachmentBean lAttachmentBean : listaAttachIn) {
								
				// Salvo il file nello storage
				GestioneDocumenti gestioneDocumenti = new GestioneDocumenti();
				AggiungiDocumentoInBean  lInput  = new AggiungiDocumentoInBean();
				AggiungiDocumentoOutBean lOutput = new AggiungiDocumentoOutBean();
				
				File lFile = StorageImplementation.getStorage().extractFile(lAttachmentBean.getUriAttach());
				lInput.setDisplayFilename(lAttachmentBean.getFileNameAttach());
				lInput.setFile(lFile);
				lOutput = gestioneDocumenti.aggiungidocumento(getLocale(), lAurigaLoginBean, lInput);
				
				// Salvo il file nello storage
				String uriAttachNew = lOutput.getUri();
				
				MimeTypeFirmaBean lInfoFileRecord = lAttachmentBean.getInfoFileAttach();
				InvioMultiDestinatariXlsAttachmentsXmlBean atta = new InvioMultiDestinatariXlsAttachmentsXmlBean();
			
				atta.setUrifile(uriAttachNew);
				//atta.setContent(IOUtils.toByteArray(StorageImplementation.getStorage().extract(uriAttachNew)));
				atta.setFilename(StringUtils.isNotBlank(lInfoFileRecord.getCorrectFileName()) ? lInfoFileRecord.getCorrectFileName() : lAttachmentBean.getFileNameAttach());
				atta.setFirmato(lInfoFileRecord.isFirmato());
				atta.setFirmaValida(lInfoFileRecord.isFirmaValida());
				atta.setMimetype(lInfoFileRecord.getMimetype());
				//atta.setOriginalName(lAttachmentBean.getFileNameAttach());
				
				//Per ottenere le informazioni relative a encoding, algoritmo relativo e impronta
				InfoFileUtility lFileUtility = new InfoFileUtility();
				RebuildedFile lRebuildedFile = new RebuildedFile();
				lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(uriAttachNew));
			
				MimeTypeFirmaBean lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(),lAttachmentBean.getInfoFileAttach().getCorrectFileName(), false, null);
			
				atta.setDimensione(new BigDecimal(lMimeTypeFirmaBean.getBytes()));
				atta.setAlgoritmo(lMimeTypeFirmaBean.getAlgoritmo());
				atta.setEncoding(lMimeTypeFirmaBean.getEncoding());
				atta.setImpronta(lMimeTypeFirmaBean.getImpronta());
			
				listaAttachInvioMultiDestXlsXml.add(atta);
			}
		}
		
		if (listaAttachInvioMultiDestXlsXml != null && listaAttachInvioMultiDestXlsXml.size() > 0){

			Lista listaParametri = new Lista();
			for (InvioMultiDestinatariXlsAttachmentsXmlBean funzione : listaAttachInvioMultiDestXlsXml) {
				Riga riga = new Riga();
				
				// Colonna 1 - Uri File
				Colonna col1 = new Colonna();
				col1.setNro(new BigInteger("1"));
				col1.setContent(funzione.getUrifile());
				riga.getColonna().add(col1);
				
				
				// Colonna 2 - File name
				Colonna col2 = new Colonna();
				col2.setNro(new BigInteger("2"));
				col2.setContent(funzione.getFilename());
				riga.getColonna().add(col2);

				// Colonna 3 - File size
				Colonna col3 = new Colonna();
				col3.setNro(new BigInteger("3"));
				col3.setContent(funzione.getDimensione() != null ? String.valueOf(funzione.getDimensione()) : null);
				riga.getColonna().add(col3);

				// Colonna 4 - MimeType
				Colonna col4 = new Colonna();
				col4.setNro(new BigInteger("4"));
				col4.setContent(funzione.getMimetype());
				riga.getColonna().add(col4);

				// Colonna 5 - Is Firmato
				Colonna col5 = new Colonna();
				col5.setNro(new BigInteger("5"));
				col5.setContent(String.valueOf(funzione.isFirmato()));
				riga.getColonna().add(col5);

				// Colonna 6 - Is Firma valida
				Colonna col6 = new Colonna();
				col6.setNro(new BigInteger("6"));
				col6.setContent(String.valueOf(funzione.isFirmaValida()));
				riga.getColonna().add(col6);

				// Colonna 7 - impronta
				Colonna col7 = new Colonna();
				col7.setNro(new BigInteger("7"));
				col7.setContent(funzione.getImpronta());
				riga.getColonna().add(col7);

				// Colonna 8 - algoritmo
				Colonna col8 = new Colonna();
				col8.setNro(new BigInteger("8"));
				col8.setContent(funzione.getAlgoritmo());
				riga.getColonna().add(col8);

				// Colonna 9 - encoding
				Colonna col9 = new Colonna();
				col9.setNro(new BigInteger("9"));
				col9.setContent(funzione.getEncoding());
				riga.getColonna().add(col9);
				
				listaParametri.getRiga().add(riga);
			}
			xmlOut = marshal(listaParametri);

		}
		
		return xmlOut;
	}
	
	private String creaXmlContenutiFile(List<DettagliXlsIndirizziEmailXmlBean> listaDettagliXlsIndirizziEmailXml) throws Exception {
		String xmlOut = null;
		
		Lista listaParametri = new Lista();
		for (DettagliXlsIndirizziEmailXmlBean funzione : listaDettagliXlsIndirizziEmailXml) {
			Riga riga = new Riga();
			
			// Colonna 1 - campoXls
			Colonna col1 = new Colonna();
			col1.setNro(new BigInteger("1"));
			col1.setContent(funzione.getCampoXls());
			riga.getColonna().add(col1);
			
			// Colonna 2 - colonnaXls
			Colonna col2 = new Colonna();
			col2.setNro(new BigInteger("2"));
			col2.setContent(funzione.getColonnaXls());
			riga.getColonna().add(col2);

			listaParametri.getRiga().add(riga);
		}
		xmlOut = marshal(listaParametri);
		return xmlOut;
	}

	protected String marshal(Lista objectsList) throws JAXBException {
		StringWriter stringWriter = new StringWriter();
		BufferedWriter bufferedWriter = new BufferedWriter(stringWriter);
		SingletonJAXBContext.getInstance().createMarshaller().marshal(objectsList, bufferedWriter);
		return stringWriter.toString();
	}
	
	
	
	private String getEmailReplyTo(String usernameIn) throws Exception {
		String result = null;
		LoginDataSource lDataSource = new LoginDataSource();
		try {
			lDataSource.setSession(getSession());
			result = lDataSource.getUsernameMail(usernameIn);
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}
		return result; 
	}
	
}