/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;


import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailIuemailBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.aurigamailbusiness.bean.AddressBean;
import it.eng.aurigamailbusiness.bean.AnonymousSenderBean;
import it.eng.aurigamailbusiness.bean.Classificazione;
import it.eng.aurigamailbusiness.bean.DestinatariInvioMailXmlBean;
import it.eng.aurigamailbusiness.bean.Dizionario;
import it.eng.aurigamailbusiness.bean.DraftAndWorkItemsBean;
import it.eng.aurigamailbusiness.bean.DraftAndWorkItemsSavedBean;
import it.eng.aurigamailbusiness.bean.EmailBean;
import it.eng.aurigamailbusiness.bean.EmailGroupBean;
import it.eng.aurigamailbusiness.bean.EmailSavedReferenceBean;
import it.eng.aurigamailbusiness.bean.EmailSentReferenceBean;
import it.eng.aurigamailbusiness.bean.IdMailInoltrataMailXmlBean;
import it.eng.aurigamailbusiness.bean.InvioMailXmlBean;
import it.eng.aurigamailbusiness.bean.ItemLavorazioneMailXmlBean;
import it.eng.aurigamailbusiness.bean.MailAttachmentsBean;
import it.eng.aurigamailbusiness.bean.MailLoginBean;
import it.eng.aurigamailbusiness.bean.NotificaInteroperabileBean;
import it.eng.aurigamailbusiness.bean.RegistrazioneProtocollo;
import it.eng.aurigamailbusiness.bean.ResultBean;
import it.eng.aurigamailbusiness.bean.RispostaInoltro;
import it.eng.aurigamailbusiness.bean.SenderAttachmentsBean;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.aurigamailbusiness.bean.SenderMailProtocollataBean;
import it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TDestinatariEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TFolderCaselleBean;
import it.eng.aurigamailbusiness.bean.TParametersBean;
import it.eng.aurigamailbusiness.bean.TRelEmailFolderBean;
import it.eng.aurigamailbusiness.bean.TRelEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TValDizionarioBean;
import it.eng.aurigamailbusiness.bean.TipoInteroperabilita;
import it.eng.aurigamailbusiness.bean.TipoRicevuta;
import it.eng.aurigamailbusiness.config.DebugInvioBean;
import it.eng.aurigamailbusiness.database.mail.TEmailMgo;
import it.eng.aurigamailbusiness.database.utility.DaoFactory;
import it.eng.aurigamailbusiness.database.utility.bean.listafolderUtente.SpostaEmailBean;
import it.eng.aurigamailbusiness.database.utility.send.FolderEmail;
import it.eng.aurigamailbusiness.database.utility.send.SendInfoBean;
import it.eng.aurigamailbusiness.database.utility.send.SendUtils;
import it.eng.aurigamailbusiness.database.utility.send.StatoConsolidamentoEmail;
import it.eng.aurigamailbusiness.database.utility.send.TipoDestinatario;
import it.eng.aurigamailbusiness.exception.AttachLimitExceedException;
import it.eng.aurigamailbusiness.exception.AurigaMailBusinessException;
import it.eng.aurigamailbusiness.exception.NoAdressesException;
import it.eng.aurigamailbusiness.exception.TooManyAdressesException;
import it.eng.aurigamailbusiness.send.ThreadSend;
import it.eng.aurigamailbusiness.sender.AccountConfigKey;
import it.eng.aurigamailbusiness.sender.storage.StorageCenter;
import it.eng.aurigamailbusiness.utility.MailBreaker;
import it.eng.aurigamailbusiness.utility.MailUtil;
import it.eng.aurigamailbusiness.utility.MailboxUtil;
import it.eng.aurigamailbusiness.utility.Util;

import it.eng.aurigamailbusiness.utility.cryptography.CryptographyUtil;
import it.eng.client.DmpkIntMgoEmailIuemail;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.spring.utility.SpringAppContext;
import it.eng.util.ListUtil;
import it.eng.utility.storageutil.StorageService;
import it.eng.xml.XmlUtilitySerializer;

/**
 * classe di servizio per la spedizione delle email
 * 
 * @author jravagnan
 * 
 */
@Service(name = "MailSenderService")
public class MailSenderService {

	public static final String DEFAULT_ENTE = "DEFAULT";

	private Logger log = LogManager.getLogger(MailSenderService.class);
//	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(MailSenderService.class);

	private MailProcessorService processor = null;

	private StorageCenter storage = null;

	private static final String RECEIPT_PARAMETER_KEY = "TIPO_RICEVUTA_PEC";

	private static final String ATTACHMENT_MAX_SIZE = "ATTACHMENT_MAX_SIZE";

	private static final String ATTACHMENT_CHECK_TYPE = "ATTACHMENT_CHECK_TYPE";

	private static final String ARCHIVIA_ECCEZIONE_PARAMETER = "ARCHIVIAZIONE_AUTO_EMAIL_POST_ECCEZIONE";

	private static final String AZIONE_SPEDIZIONE = "spedire";

	private static final String AZIONE_SALVATAGGIO = "salvare";

	private static final Integer BYTES_IN_MEGA = 1048576;

	private static final String ERRORE_NESSUN_DESTINATARIO = "Specificare almeno un destinatario.";

	private static final String ERRORE_NESSUN_DESTINATARIO_PEC = "Specificare almeno un destinatario principale per inviare una mail certificata.";

	private static final String AVVERTIMENTO_INVIO_MITTENTE = "Si ricorda che l’invio allo stesso indirizzo mittente non è consentito.";
	
	private static final String ATTIVA_FRAZ_ATTACH_MAIL = "ATTIVA_FRAZ_ATTACH_MAIL";

	private Integer maxNumDestinatari = null;

	/**
	 * Metodo che invoca la funzione IUEmail della store DMPK_INT_MGO_EMAIL per salvare i dati gli item di lavorazione e l'eventuale bozza. <br>
	 * La store si occupa sia dell'insert, che dell'update dei dati in input. Se in input il parametro {@link saveDraftEml} è true allora i dati in input alla
	 * store relativi alla mail devono essere utilizzati per generare un file eml, altrimenti nel caricamento della mail non è possibile recuperare i dati e gli
	 * allegati. <br>
	 * In caso di update è necessario aggiornare l'URI del file nello storage, rimuovendo quello precedente al termine delle operazioni <br>
	 * Analogamente, se occorre salvare gli item di lavorazione si procede al salvataggio del file nello storage e alla rimozione di quelli precedenti <br>
	 * In caso di errore si eliminano gli eventuali nuovi file nello storage <br>
	 * Se si tratta di effettuare il salvataggio finale pre-invio allora si cambia anche lo stato di consolidamento della bozza e la cartella della mail
	 * 
	 * 
	 * @param draftInputBean
	 *            oggetto contenente le informazioni sulla bozza email da salvare e/o i relativi item di lavorazione.
	 *            {@link DraftAndWorkItemsBean#getIdemailio()} contiene eventualmente l'id della mail da aggiornare
	 * @param saveDraftEml
	 *            modalità di salvataggio:
	 *            <li>se vale {@link DraftAndWorkItemsBean#MODE_SAVE_DRAFT} allora si salva la bozza e i gli item di lavorazione nello storage
	 *            <li>se vale {@link DraftAndWorkItemsBean#MODE_SAVE_ONLY_WORK_ITEMS} si salvano nello storage solo gli item di lavorazione
	 *            <li>se vale {@link DraftAndWorkItemsBean#MODE_SAVE_DRAFT_PRE_SEND} oltre al salvataggio nello storage e all'invocazione della store si cambia
	 *            lo stato di consolidamento da {@link StatoConsolidamentoEmail#IN_BOZZA} a {@link StatoConsolidamentoEmail#IN_INVIO} e la folder da
	 *            {@link FolderEmail#STANDARD_BOZZE} a {@link FolderEmail#STANDARD_FOLDER_USCITA} in modo da permettere l'invio della bozza
	 * @param login
	 *            oggetto contenente le informazioni sull'utente che esegue le operazioni, attenzione che l'utente di lavoro può essere quello in delega
	 * 
	 * @throws AurigaMailBusinessException
	 */

	@Operation(name = "saveDraftAndWorkItems")
	public ResultBean<DraftAndWorkItemsSavedBean> saveDraftAndWorkItems(DraftAndWorkItemsBean draftInputBean, Integer saveDraftEml, MailLoginBean login)
			throws AurigaMailBusinessException {

		ResultBean<DraftAndWorkItemsSavedBean> result = new ResultBean<DraftAndWorkItemsSavedBean>();

		String idMailBox = null;
		StorageService globalStorageService = null;
		String uriNew = null;
		String uriOld = null;
		ArrayList<String> listaURIItemLavorazioneNew = new ArrayList<String>();
		ArrayList<String> listaURIItemLavorazioneOld = new ArrayList<String>();
		SenderBean senderBean = null;
		boolean isEmailSaved = false;

		try {

			if (login == null) {
				throw new AurigaMailBusinessException("Impossibile salvare la bozza: credenziali di invocazione store nulle");
			}

			AurigaLoginBean schema = new AurigaLoginBean();

			schema.setSchema(login.getSchema());
			schema.setToken(login.getToken()); // dal token si recupera l'id
												// utente collegato
			schema.setIdUserLavoro(login.getUserId()); // valorizzato con id
														// utente in delega se
														// presente

			InvioMailXmlBean datiMailUnbind = draftInputBean.getDatiemailin();
			List<File> listaFileItemLavorazione = draftInputBean.getListaFileItemLavorazione();

			String account = datiMailUnbind.getAccountMittente();

			if (StringUtils.isBlank(account)) {
				// il mittente deve essere obbligatorio per salvare
				// correttamente la bozza. Alcune informazioni infatti si
				// ricavano direttamente dall'account
				throw new AurigaMailBusinessException("Impossibile salvare la bozza: account mittente nullo");
			}
			String idAccount = MailboxUtil.getIdAccount(account);

			storage = new StorageCenter();
			idMailBox = MailboxUtil.getIdMailbox(idAccount);
			globalStorageService = storage.getGlobalStorage(idMailBox);

			if (saveDraftEml >= DraftAndWorkItemsBean.MODE_SAVE_DRAFT) {

				senderBean = new SenderBean();

				// ricavo le proprietà dell'account
				Properties propertiesAccount = SendUtils.getAccountProperties(idAccount);
				JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
				mailSender.setJavaMailProperties(propertiesAccount);
				mailSender.setHost(propertiesAccount.getProperty(AccountConfigKey.SMTP_HOST.keyname()));
				mailSender.setPort(Integer.valueOf(propertiesAccount.getProperty(AccountConfigKey.SMTP_PORT.keyname())));
				mailSender.setUsername(propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname()));
				mailSender.setPassword(CryptographyUtil.decryptionAccountPasswordWithAES(propertiesAccount));
				if (propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_IS_PEC.keyname(), "false").equalsIgnoreCase("true")) {
					senderBean.setIsPec(true);
				} else {
					senderBean.setIsPec(false);
				}
				String tipoRicevuta = ricavaValoreRicevuta(idAccount);

				// modalità di salvataggio della bozza o salvataggio finale
				// della bozza pre-invio
				// in questa modalità occorre generare e salvare su file system
				// un file eml

				log.info("Inizio salvataggio della mail come bozza");

				// recupero dal bean in input le informazioni della mail

				uriOld = datiMailUnbind.getUri(); // valorizzato solo in caso di
													// update del draft

				// completo oggetto senderBean con le informazioni recuperate
				// dal bean della store in input
				SendUtils.convertInvioMailXmlBeanToSenderBean(datiMailUnbind, senderBean);

				// posso aver salvato una bozza senza destinatario, i controlli
				// sui destinatari richiedono quindi la non obbligatorietà di
				// una mail principale
				verificaDestinatariEPulisciDoppioni(senderBean, false);

				// aggiorno la lista di destinatari anche nel bean da passare
				// alla store
				ArrayList<DestinatariInvioMailXmlBean> listaDestinatari = new ArrayList<DestinatariInvioMailXmlBean>();

				int numeroDestinatari = 0;

				// aggiorno i destinatari anche nel bean iniziale
				if (senderBean.getAddressTo() != null && !senderBean.getAddressTo().isEmpty()) {
					for (String addressTo : senderBean.getAddressTo()) {
						DestinatariInvioMailXmlBean destinatario = new DestinatariInvioMailXmlBean();
						destinatario.setIndirizzo(addressTo);
						destinatario.setTipo(TipoDestinatario.TO.getValue());
						listaDestinatari.add(destinatario);
					}
					numeroDestinatari += senderBean.getAddressTo().size();
				}
				if (senderBean.getAddressCc() != null && !senderBean.getAddressCc().isEmpty()) {
					for (String addressCc : senderBean.getAddressCc()) {
						DestinatariInvioMailXmlBean destinatario = new DestinatariInvioMailXmlBean();
						destinatario.setIndirizzo(addressCc);
						destinatario.setTipo(TipoDestinatario.CC.getValue());
						listaDestinatari.add(destinatario);
					}
					numeroDestinatari += senderBean.getAddressCc().size();
				}
				if (senderBean.getAddressBcc() != null && !senderBean.getAddressBcc().isEmpty()) {
					for (String addressBcc : senderBean.getAddressBcc()) {
						DestinatariInvioMailXmlBean destinatario = new DestinatariInvioMailXmlBean();
						destinatario.setIndirizzo(addressBcc);
						destinatario.setTipo(TipoDestinatario.BCC.getValue());
						listaDestinatari.add(destinatario);
					}
					numeroDestinatari += senderBean.getAddressBcc().size();
				}

				datiMailUnbind.setListaDestinatari(listaDestinatari);

				// se sono nel salvataggio finale della bozza allora ho già
				// verificato ed eventualmente formato i gruppi di destinatari
				// viceversa nel salvataggio iniziale non devo tener contro dei
				// limiti dei gruppi di destinatari

				// verifico la dimensione totale degli allegati prima di salvare la bozza
				checkLimiteAttachments(idAccount, numeroDestinatari, senderBean, 0, AZIONE_SALVATAGGIO);

				// id email valorizzata in caso di aggiornamento
				senderBean.setIdEmail(draftInputBean.getIdemailio());

				// creazione del file eml a partire dall'oggetto SenderBean
				MimeMessage message = createMimeMessage(mailSender, senderBean, tipoRicevuta);

				// salvo la nuova mail nello storage globale = Mail archiviate
				uriNew = globalStorageService.writeTo(message);

				// ricavo la dimensione del file
				Long mailSize = globalStorageService.getRealFile(uriNew).length();
				// aggiorno l'URI e la dimensione nelle informazioni della mail
				// per la store
				datiMailUnbind.setUri(uriNew);
				datiMailUnbind.setDimensione(String.valueOf(mailSize));

				// Non setto in questa fase il messageId visto che verrà creata
				// un nuovo MimeMessage e conseguentemente un nuovo messageId al
				// momento dell'invio
				// vero e proprio
				// senderBean.setMessageId(message.getMessageID());

				// dopo aver generato il file eml pulisco il corpo della mail in
				// modo da visualizzarlo correttamente nella lista delle bozze
				// nel dettaglio della mail il corpo viene recuperato dal file
				// eml e quindi sarà visualizzato correttamente

				datiMailUnbind.setBody(Util.clearCarriageReturn(datiMailUnbind.getBody()));

				log.info("Salvataggio della bozza nello storage eseguito con successo");

			}

			// in tutti i casi devo processare gli item di lavorazione se
			// presenti

			if (ListUtil.isNotEmpty(datiMailUnbind.getListaItemLavorazione())) {

				// devo processare gli item di lavorazione

				for (ItemLavorazioneMailXmlBean itemLavorazione : datiMailUnbind.getListaItemLavorazione()) {

					if (StringUtils.isNotBlank(itemLavorazione.getUriFile())) {

						// item di lavorazione con file

						String uriItemNew = itemLavorazione.getUriFile();
						String uriItemOld = itemLavorazione.getUriFileSaved(); // eventuale
																				// file
																				// precedente
																				// da
																				// eliminare
						Integer posizione = itemLavorazione.getPosFileInLista();

						// verifico se il file è nuovo o cambiato rispetto al
						// valore precedente
						// in questo caso occorre salvarlo nello storage
						if (StringUtils.isBlank(uriItemOld) || !uriItemOld.equalsIgnoreCase(uriItemNew)) {
							// recupero il relativo file
							if (ListUtil.isNotEmpty(listaFileItemLavorazione) && posizione != null && listaFileItemLavorazione.get(posizione) != null) {
								uriItemNew = globalStorageService.store(listaFileItemLavorazione.get(posizione));
								if (StringUtils.isBlank(uriItemNew)) {
									throw new AurigaMailBusinessException("Errore nel salvataggio del file dell'item di lavorazione");
								} else {
									// aggiorno l'URI dell'item di lavorazione
									itemLavorazione.setUriFile(uriItemNew);
									listaURIItemLavorazioneNew.add(uriItemNew);
									// devo eliminare dallo storage il
									// precedente file in caso di successo, se è
									// presente
									if (StringUtils.isNotBlank(uriItemOld)) {
										listaURIItemLavorazioneOld.add(uriItemOld);
									}
								}
							} else {
								throw new AurigaMailBusinessException("Errore nel salvataggio del file dell'item di lavorazione: file non presente in input");
							}
						}
						// se indirizzo nuovo e vecchio sono uguali non occorre
						// fare alcuna operazione, il file nello storage rimane
						// invariato

					} else if (StringUtils.isNotBlank(itemLavorazione.getUriFileSaved())) {
						// in questo caso l'item di lavorazione contiene una
						// nota testuale, ma precedentemente conteneva un file
						// che deve essere rimosso dallo
						// storage
						listaURIItemLavorazioneOld.add(itemLavorazione.getUriFileSaved());
					}

				}

				log.info("Salvataggio degli item di lavorazione nello storage eseguito con successo");

			}

			// creo l'xml della sezione cache con i dati della mail aggiornati
			XmlUtilitySerializer xmlUtilitySerializer = new XmlUtilitySerializer();

			DmpkIntMgoEmailIuemailBean storeBean = new DmpkIntMgoEmailIuemailBean();
			storeBean.setIdemailio(draftInputBean.getIdemailio());
			storeBean.setDatiemailin(xmlUtilitySerializer.bindXml(datiMailUnbind));

			log.info("Chiamata alla store procedure DmpkIntMgoEmailIuemail");

			DmpkIntMgoEmailIuemail storeIuEmail = new DmpkIntMgoEmailIuemail();
			StoreResultBean<DmpkIntMgoEmailIuemailBean> output = storeIuEmail.execute(Locale.ITALIAN, schema, storeBean);

			// gestione dei messaggi di errore
			if (output.isInError()) {
				if(StringUtils.isNotBlank(output.getDefaultMessage())) {
					throw new AurigaMailBusinessException(output.getDefaultMessage());
				} else {
					throw new AurigaMailBusinessException("Si è verificato un errore durante il salvataggio della mail");
				}
			} else {
				log.info("Salvataggio della bozza e degli item di lavorazione eseguito con successo");
				isEmailSaved = true;
			}
			
			// recupero id della mail ritornata dalla store
			String idEmail = output.getResultBean().getIdemailio();

			// consolidamento della bozza se sto per inviarla

			if (saveDraftEml == DraftAndWorkItemsBean.MODE_SAVE_DRAFT_PRE_SEND) {
				this.consolidaBozzaPerInvio(idEmail, true);
				log.info("Consolidamento della bozza per invio eseguito con successo");
			}

			// preparo output del metodo
			DraftAndWorkItemsSavedBean resultStore = new DraftAndWorkItemsSavedBean();
			if (senderBean != null) {
				// ritorno i dati della bozza per l'invio, solo se è stata
				// salvata
				senderBean.setIdEmail(idEmail);
				resultStore.setDraftEmail(senderBean);
			}
			// in tutti i casi ritorno l'id della mail
			resultStore.setIdEmail(idEmail);
			result.setResultBean(resultStore);

		} catch (Exception exc) {

			log.error("Errore nel salvataggio della bozza e degli item di lavorazione", exc);

			result.setDefaultMessage(exc.getMessage());
			result.setErrorStackTrace(ExceptionUtils.getStackTrace(exc));
			result.setInError(true);

		}  finally {
			
			if(isEmailSaved) {
				
				// se sono in update e l'invocazione della store di salvataggio della mail è 
				// avvenuta con successo allora cancello il vecchio URI dallo storage
				// viceversa in caso di errore elimino quello nuovo visto che è inutile e non 
				// è stato aggiornato in database
				// analogamente cancello la lista degli item di lavorazione non più utilizzati

				try {
					if (StringUtils.isNotBlank(uriOld) && globalStorageService.extractFile(uriOld) != null) {
						globalStorageService.delete(uriOld);
					}
				} catch (Exception e) {
					log.warn("Errore nella cancellazione dell'URI della bozza", e);
				}

				if (ListUtil.isNotEmpty(listaURIItemLavorazioneOld)) {
					for (String uriItem : listaURIItemLavorazioneOld) {
						try {
							if (globalStorageService.extractFile(uriItem) != null) {
								globalStorageService.delete(uriItem);
							}
						} catch (Exception e) {
							log.warn("Errore nella cancellazione dell'URI dell'item", e);
						}
					}
				}
			
			} else {		
				
				try {

					if (globalStorageService != null && StringUtils.isNotBlank(uriNew)) {
						// in caso di errori cancello il nuovo URI della mail
						globalStorageService.delete(uriNew);
					}

				} catch (Exception e) {
					log.warn("Errore nella cancellazione dell'URI della bozza", e);
				}

				if (globalStorageService != null && ListUtil.isNotEmpty(listaURIItemLavorazioneNew)) {
					// analogamente cancello i nuovo URI degli item di lavorazione
					for (String uriItem : listaURIItemLavorazioneNew) {
						try {
							globalStorageService.delete(uriItem);
						} catch (Exception e) {
							log.warn("Errore nella cancellazione dell'URI dell'item", e);
						}
					}
				}		
			}
		}

		return result;

	}

	@Operation(name = "sendAndSaveMailProtocollata")
	public ResultBean<EmailSentReferenceBean> sendAndSaveMailProcollata(SenderMailProtocollataBean bean) throws AurigaMailBusinessException {
		return salvaEmailESpedisci(bean.getMail(), null, null, bean.getRegProt(), null, false, null);
	}

	/**
	 * Salvataggio e invio della mail in uscita
	 * 
	 * @param bean
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	@Operation(name = "sendAndSave")
	public ResultBean<EmailSentReferenceBean> sendAndSave(SenderBean bean) throws AurigaMailBusinessException {
		SubjectBean subject = SubjectUtil.subject.get();
		if (subject == null) {
			subject = new SubjectBean();
			SubjectUtil.subject.set(subject);
		}
		subject.setIduser(bean.getIdUtenteModPec());
		if (subject.getIdDominio() == null) {
			subject.setIdDominio(DEFAULT_ENTE);
		}
		
		log.debug("*** sendAndSave ***");
		
		return salvaEmailESpedisci(bean, null, null, null, null, false, null);
	}

	/**
	 * Salvataggio e invio della mail in uscita, salvataggio di eventuali item di lavorazione
	 * 
	 * @param bean
	 * @param login
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	@Operation(name = "sendAndSaveWorkItems")
	public ResultBean<EmailSentReferenceBean> sendAndSaveWorkItems(SenderBean bean, MailLoginBean login) throws AurigaMailBusinessException {
		SubjectBean subject = SubjectUtil.subject.get();
		if (subject == null) {
			subject = new SubjectBean();
			SubjectUtil.subject.set(subject);
		}
		subject.setIduser(bean.getIdUtenteModPec());
		if (subject.getIdDominio() == null) {
			subject.setIdDominio(DEFAULT_ENTE);
		}
		return salvaEmailESpedisci(bean, null, null, null, null, false, login);
	}

	/**
	 * Consolidamento finale e invio della bozza
	 * 
	 * @param draftInputBean
	 *            dati della mail
	 * @param login
	 *            dati di autenticazione
	 * @param idUtenteModPec
	 *            utente che sta modificando la pec
	 * @param mode
	 *            invio da lista o dettaglio
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	@Operation(name = "saveAndSendDraft")
	public ResultBean<EmailSentReferenceBean> saveAndSendDraft(DraftAndWorkItemsBean draftInputBean, MailLoginBean login, String idUtenteModPec, Integer mode)
			throws AurigaMailBusinessException {
		SubjectBean subject = SubjectUtil.subject.get();
		if (subject == null) {
			subject = new SubjectBean();
			SubjectUtil.subject.set(subject);
		}
		subject.setIduser(idUtenteModPec);
		if (subject.getIdDominio() == null) {
			subject.setIdDominio(DEFAULT_ENTE);
		}
		return consolidaBozzaEmailESpedisci(draftInputBean, login, false, mode);
	}

	@Operation(name = "sendAndDeleteAfterSend")
	public ResultBean<EmailSentReferenceBean> sendAndDeleteAfterSend(SenderBean bean) throws AurigaMailBusinessException {
		SubjectBean subject = SubjectUtil.subject.get();
		if (subject == null) {
			subject = new SubjectBean();
			SubjectUtil.subject.set(subject);
		}
		subject.setIduser(bean.getIdUtenteModPec());
		if (subject.getIdDominio() == null) {
			subject.setIdDominio(DEFAULT_ENTE);
		}
		return salvaEmailESpedisci(bean, null, null, null, null, true, null);
	}

	@Operation(name = "reSend")
	public ResultBean<EmailSentReferenceBean> resend(SenderBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		if (subject == null) {
			subject = new SubjectBean();
			SubjectUtil.subject.set(subject);
		}
		subject.setIduser(bean.getIdUtenteModPec());
		if (subject.getIdDominio() == null) {
			subject.setIdDominio(DEFAULT_ENTE);
		}
		return resendEmail(bean);
	}

	private ResultBean<EmailSentReferenceBean> resendEmail(SenderBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		if (subject == null) {
			subject = new SubjectBean();
			SubjectUtil.subject.set(subject);
		}
		subject.setIduser(bean.getIdUtenteModPec());
		if (subject.getIdDominio() == null) {
			subject.setIdDominio(DEFAULT_ENTE);
		}
		String idEmail = bean.getIdEmail();
		TEmailMgoBean lTEmailMgoBean = ((DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class)).get(idEmail);
		EmailBean lEmailBean = new EmailBean();
		lEmailBean.setMail(lTEmailMgoBean);
		TFilterFetch<TAttachEmailMgoBean> filter = new TFilterFetch<TAttachEmailMgoBean>();
		TAttachEmailMgoBean lTAttachEmailMgoBean = new TAttachEmailMgoBean();
		lTAttachEmailMgoBean.setIdEmail(idEmail);
		filter.setFilter(lTAttachEmailMgoBean);
		List<TAttachEmailMgoBean> lTAttachEmailMgoBeanList = ((DaoTAttachEmailMgo) DaoFactory.getDao(DaoTAttachEmailMgo.class)).search(filter).getData();
		lEmailBean.setAttachments(lTAttachEmailMgoBeanList);
		TFilterFetch<TDestinatariEmailMgoBean> filterDestinatari = new TFilterFetch<TDestinatariEmailMgoBean>();
		TDestinatariEmailMgoBean lTDestinatariEmailMgoBean = new TDestinatariEmailMgoBean();
		lTDestinatariEmailMgoBean.setIdEmail(idEmail);
		filterDestinatari.setFilter(lTDestinatariEmailMgoBean);
		List<TDestinatariEmailMgoBean> destinatari = ((DaoTDestinatariEmailMgo) DaoFactory.getDao(DaoTDestinatariEmailMgo.class)).search(filterDestinatari)
				.getData();
		lEmailBean.setDestinatari(destinatari);
		SenderBean lSenderBean = SendUtils.convertEmailBeanToSenderBean(lEmailBean);
		String idAccount = MailboxUtil.getIdAccount(lSenderBean.getAccount());

		List<SenderBean> daSpedire = new ArrayList<SenderBean>();
		Properties propertiesaccount = SendUtils.getAccountProperties(idAccount);
		JavaMailSenderImpl mailsender = new JavaMailSenderImpl();
		mailsender.setJavaMailProperties(propertiesaccount);

		mailsender.setHost(propertiesaccount.getProperty(AccountConfigKey.SMTP_HOST.keyname()));
		mailsender.setPort(Integer.valueOf(propertiesaccount.getProperty(AccountConfigKey.SMTP_PORT.keyname())));
		mailsender.setUsername(propertiesaccount.getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname()));
		// Federico Cacco 18.12.2015
		// Decripto la password assosciata all'account
		mailsender.setPassword(CryptographyUtil.decryptionAccountPasswordWithAES(propertiesaccount));
		// mailsender.setPassword(propertiesaccount.getProperty(AccountConfigKey.ACCOUNT_PASSWORD.keyname()));
		// verifico che i destinatari siano validi
		// e ripulisco il senderBean dai doppioni dei destinatari
		if (propertiesaccount.getProperty(AccountConfigKey.ACCOUNT_IS_PEC.keyname(), "false").equalsIgnoreCase("true")) {
			lSenderBean.setIsPec(true);
		} else {
			lSenderBean.setIsPec(false);
		}
		AddressBean emailaddress = verificaDestinatariEPulisciDoppioni(lSenderBean);
		Integer maxAddress = new Integer(propertiesaccount.getProperty(AccountConfigKey.SMTP_MAX_ADDRESS.keyname(), "50"));
		// se attivo il flag di invio separato significa che deve essere spedita
		// una mail per ogni destinatario
		if (lSenderBean.getFlgInvioSeparato() != null && lSenderBean.getFlgInvioSeparato()) {
			maxAddress = 1;
		}
		// Numero di gruppi
		SenderBean sen = null;
		String messageId = null;
		Integer group = emailaddress.getgroup(maxAddress);
		List<String> addresses = null;
		for (int i = 0; i < group; i++) {
			// Ciclo i gruppi
			addresses = emailaddress.getAddressForGroup(i, maxAddress);
			if (group > 1) {
				sen = creaNuovoSenderBean(lSenderBean, messageId, addresses, false, false, 0).get(0);
			} else {
				sen = creaNuovoSenderBean(lSenderBean, messageId, null, false, false, 0).get(0);
			}
			daSpedire.add(sen);
		}
		// arrivato a questo punto la mail è sicuramente già stata consolidata
		// per l'invio e quindi può essere salvata
		// utilizzo invio sincrono
		// Send send = new Send(idAccount, subject);
		// send.send(daSpedire, true, false);
		// // valuto risultati dell'invio mail
		// ResultBean<EmailSentReferenceBean> resultBean = new
		// ResultBean<EmailSentReferenceBean>();
		// EmailSentReferenceBean sentReferenceBean = new
		// EmailSentReferenceBean();
		// ArrayList<String> listaIdMail = new ArrayList<String>();
		// for (SenderBean senderBean : send.getListaMailSpedite()) {
		// if (senderBean.getIdEmail() != null) {
		// listaIdMail.add(senderBean.getIdEmail());
		// }
		// }
		// sentReferenceBean.setSentMails(send.getListaMailSpedite());
		// sentReferenceBean.setIdEmails(listaIdMail);
		// resultBean.setResultBean(sentReferenceBean);
		// return resultBean;
		// arrivato a questo punto la mail è sicuramente già stata consolidata
		// per l'invio e quindi può essere salvata
		ThreadSend lThreadSend = new ThreadSend(daSpedire, idAccount, subject, true, false);
		Thread lThread = new Thread(lThreadSend);
		lThread.join();
		lThread.start();
		return new ResultBean<EmailSentReferenceBean>();
	}

	@Operation(name = "sendAndSaveInteropNotifica")
	public ResultBean<EmailSentReferenceBean> sendAndSaveInteropNotifica(NotificaInteroperabileBean beanIn) throws AurigaMailBusinessException {
		SubjectBean subject = SubjectUtil.subject.get();
		if (subject == null) {
			subject = new SubjectBean();
			SubjectUtil.subject.set(subject);
		}
		subject.setIduser(beanIn.getSenderBean().getIdUtenteModPec());
		if (subject.getIdDominio() == null) {
			subject.setIdDominio(DEFAULT_ENTE);
		}
		return salvaEmailESpedisci(beanIn.getSenderBean(), beanIn.getXmlNotifica(), beanIn.getTipoNotifica(), null, beanIn.getMailPartenza(), false, null);
	}

	/**
	 * Invio della mail in input, senza aggiornare il file della mail nello storage
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */

	@Operation(name = "send")
	public EmailSentReferenceBean send(SenderBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		if (subject == null) {
			subject = new SubjectBean();
			SubjectUtil.subject.set(subject);
		}
		subject.setIduser(bean.getIdUtenteModPec());
		if (subject.getIdDominio() == null) {
			subject.setIdDominio(DEFAULT_ENTE);
		}
		EmailSentReferenceBean ref = new EmailSentReferenceBean();
		SendInfoBean info = sendMail(bean);
		ref.setSentMails(info.getEmails());
		return ref;
	}

	/**
	 * Invio della mail in input. Se il parametro {@link saveInStorage} è impostato a TRUE allora si procede anche al salvataggio nello storage della mail e
	 * all'aggiornamento del riferimento al file in database
	 * 
	 * @param bean
	 * @param saveAndUpdateMailInStorage
	 * @return
	 * @throws Exception
	 */

	@Operation(name = "sendAndSaveMailFileInStorage")
	public EmailSentReferenceBean sendAndSaveMailFileInStorage(SenderBean bean, Boolean saveAndUpdateMailInStorage) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		if (subject == null) {
			subject = new SubjectBean();
			SubjectUtil.subject.set(subject);
		}
		subject.setIduser(bean.getIdUtenteModPec());
		if (subject.getIdDominio() == null) {
			subject.setIdDominio(DEFAULT_ENTE);
		}
		EmailSentReferenceBean ref = new EmailSentReferenceBean();
		SendInfoBean info = sendMail(bean, saveAndUpdateMailInStorage);
		ref.setSentMails(info.getEmails());
		return ref;
	}

	@Operation(name = "sendAnonymous")
	public EmailSentReferenceBean sendAnonymous(AnonymousSenderBean bean) throws Exception {
		SubjectBean subject = SubjectUtil.subject.get();
		if (subject == null) {
			subject = new SubjectBean();
			SubjectUtil.subject.set(subject);
		}
		subject.setIduser(bean.getIdUtenteModPec());
		if (subject.getIdDominio() == null) {
			subject.setIdDominio(DEFAULT_ENTE);
		}
		EmailSentReferenceBean ref = new EmailSentReferenceBean();
		SendInfoBean info = sendMail(bean, false);
		ref.setSentMails(info.getEmails());
		return ref;
	}

	/**
	 * Riceve in ingresso una mail da spedire e (a seconda del numero dei destinatari) restituisce tutte le email spedite, l'idAccount e il messaggio spedito
	 * <br>
	 * Dopo l'invio non effettua alcun aggiornamento in database
	 * 
	 * @param senderBean
	 *            bean con le informazioni della mail da inviare
	 * @param saveAndUpdateMailInStorage
	 *            flag che indica se salvare la mail nello storage di archiviazione
	 * @return
	 * @throws Exception
	 */
	private SendInfoBean sendMail(SenderBean senderBean, Boolean saveAndUpdateMailInStorage) throws Exception {
		SendInfoBean sendInfo = new SendInfoBean();
		List<SenderBean> result = new ArrayList<SenderBean>();
		MimeMessage message = null;
		Properties propertiesAccount = null;
		String tipoRicevuta = null;
		String idAccount = null;
		String idMailBox = null;
		StorageService globalStorageService = null;
		String uriNew = null;
		String logIdMail = (StringUtils.isNotEmpty(senderBean.getIdEmail()) ? " avente id " + senderBean.getIdEmail() : "");
		try {
			if (!(senderBean instanceof AnonymousSenderBean)) {
				idAccount = MailboxUtil.getIdAccount(senderBean.getAccount());
				storage = new StorageCenter();
				idMailBox = MailboxUtil.getIdMailbox(idAccount);
				globalStorageService = storage.getGlobalStorage(idMailBox);
				tipoRicevuta = ricavaValoreRicevuta(idAccount);
				sendInfo.setIdAccount(idAccount);
				propertiesAccount = SendUtils.getAccountProperties(idAccount);
			} else {
				// dev'essere un invio anonimo per cui devo settare tutte le
				// proprietà per la spedizione
				propertiesAccount = new Properties();
				AnonymousSenderBean aBean = (AnonymousSenderBean) senderBean;
				String smtpHost = aBean.getSmtpEndpointAccountMitt();
				String stmpPort = aBean.getSmtpPortAccountMitt();
				propertiesAccount.setProperty(AccountConfigKey.SMTP_HOST.keyname(), smtpHost);
				propertiesAccount.setProperty(AccountConfigKey.SMTP_PORT.keyname(), stmpPort);
			}
			if (propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_IS_PEC.keyname(), "false").equalsIgnoreCase("true")) {
				senderBean.setIsPec(true);
			} else {
				senderBean.setIsPec(false);
			}
			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
			mailSender.setJavaMailProperties(propertiesAccount);
			mailSender.setHost(propertiesAccount.getProperty(AccountConfigKey.SMTP_HOST.keyname()));
			mailSender.setPort(Integer.valueOf(propertiesAccount.getProperty(AccountConfigKey.SMTP_PORT.keyname())));
			if (propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname()) != null) {
				mailSender.setUsername(propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname()));
			}
			if (propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_PASSWORD.keyname()) != null) {
				mailSender.setPassword(CryptographyUtil.decryptionAccountPasswordWithAES(propertiesAccount));
			}
			// verifico i destinatari
			// e pulisco i destinatari doppi
			log.error("senderBean.getAddressTo().size(): "+senderBean.getAddressTo().size());
			
			AddressBean emailaddress = verificaDestinatariEPulisciDoppioni(senderBean);
			log.error("emailaddress.getAddressto(): "+ emailaddress.getAddressto().size());
			// Numero massimo di indirizzi
			Integer maxAddress = new Integer(propertiesAccount.getProperty(AccountConfigKey.SMTP_MAX_ADDRESS.keyname(), "50"));
			// se attivo il flag di invio separato significa che deve essere
			// spedita una mail per ogni destinatario
			
			if (senderBean.getFlgInvioSeparato() != null && senderBean.getFlgInvioSeparato()) {
				log.error("INVIO SEPARATO TRUE");
				maxAddress = 1;
			} else {
				log.error("INVIO SEPARATO FALSE");
				// verifico la dimensione totale degli allegati per capire
				// se posso effettuare o meno l'invio
				Integer newMaxAddress = checkLimiteAttachments(idAccount, null, senderBean, maxAddress, AZIONE_SPEDIZIONE);
				if(newMaxAddress > 0)
					maxAddress = newMaxAddress;
			}
			// Numero di gruppi
			SenderBean newSenderBean = null;
			String messageId = null;
			
			Integer group = emailaddress.getgroup(maxAddress);
			log.error("GROUP: "+ group);
			log.error("MAX ADDRESS: "+ maxAddress);
			List<String> addresses = null;
			// occorre verificare anche in questo caso i destinatari, potrebbe
			// infatti trattarsi di un invio senza passare dal salvataggio
			// se il gruppo è uno solo mantengo la distribuzione TO, CC, BCC
			if (group == 1) {
				try {
					List<InternetAddress> internetAddressTo = new ArrayList<InternetAddress>();
					List<InternetAddress> internetAddressCc = new ArrayList<InternetAddress>();
					List<InternetAddress> internetAddressBcc = new ArrayList<InternetAddress>();
					if (senderBean.getAddressTo() != null) {
						for (String addressto : senderBean.getAddressTo()) {
							internetAddressTo.add(new InternetAddress(addressto));
						}
					}
					if (senderBean.getAddressCc() != null) {
						for (String addresscc : senderBean.getAddressCc()) {
							internetAddressCc.add(new InternetAddress(addresscc));
						}
					}
					if (senderBean.getAddressBcc() != null) {
						for (String addressbcc : senderBean.getAddressBcc()) {
							internetAddressBcc.add(new InternetAddress(addressbcc));
						}
					}
					message = createMimeMessage(mailSender, senderBean, tipoRicevuta);
					message.setRecipients(RecipientType.TO, internetAddressTo.toArray(new InternetAddress[0]));
					message.setRecipients(RecipientType.CC, internetAddressCc.toArray(new InternetAddress[0]));
					message.setRecipients(RecipientType.BCC, internetAddressBcc.toArray(new InternetAddress[0]));
					if (log.isDebugEnabled()) {
						log.debug("Proprietà mail sender: " + mailSender.toString());
					}
					// Invio la mail
					mailSender.send(message);
					// recupero il message id, se vuoto non devo calcolare il
					// digest
					messageId = MailUtil.getInstance(message).getMessageID(false);
					if (StringUtils.isBlank(messageId)) {
						throw new AurigaMailBusinessException("MessageId non valorizzato");
					}
					if (saveAndUpdateMailInStorage && globalStorageService != null) {

						try {
							// salvo il messaggio creato nello storage di
							// archiviazione
							uriNew = globalStorageService.writeTo(message);
						} catch (Exception exc) {
							log.error("Errore nel salvataggio della mail" + logIdMail + " nello storage di archiviazione: ", exc);
						}

						if (StringUtils.isNotBlank(uriNew)) {
							senderBean.setUriSavedMimeMessage(uriNew);
						}

					}
					newSenderBean = creaNuovoSenderBean(senderBean, messageId, null, true, false, 0).get(0);
				} catch (MailSendException mse) {
					log.debug(mse.getLocalizedMessage(), mse);
					if (mse.getMessageExceptions()[0].getMessage().equalsIgnoreCase("Invalid Addresses")) {
						throw new AurigaMailBusinessException("Verificare che i destinatari siano corretti", mse);
					} else {
						throw new AurigaMailBusinessException("Verificare che i parametri siano corretti", mse);
					}
				} catch (AttachLimitExceedException e) {
					log.debug(e.getLocalizedMessage(), e);
					throw e;
				} catch (Exception e) {
					log.error("Impossibile spedire la mail" + logIdMail + ": ", e);
					newSenderBean = creaNuovoSenderBean(senderBean, messageId, null, false, false, 0).get(0);
				}
				result.add(newSenderBean);
			} else {
				for (int i = 0; i < group; i++) {
					try {
						// Ciclo i gruppi
						addresses = emailaddress.getAddressForGroup(i, maxAddress);
						List<InternetAddress> internetaddress = new ArrayList<InternetAddress>();
						for (String addressto : addresses) {
							internetaddress.add(new InternetAddress(addressto));
						}
						message = createMimeMessage(mailSender, senderBean, tipoRicevuta);
						message.setRecipients(RecipientType.TO, internetaddress.toArray(new InternetAddress[0]));
						message.setRecipients(RecipientType.CC, new InternetAddress[0]);
						message.setRecipients(RecipientType.BCC, new InternetAddress[0]);
						// Invio la mail
						if (log.isDebugEnabled()) {
							log.debug("Proprietà mail sender: " + mailSender.toString());
						}
						mailSender.send(message);
						// recupero il message id, se vuoto non devo calcolare
						// il digest
						messageId = MailUtil.getInstance(message).getMessageID(false);
						if (StringUtils.isBlank(messageId)) {
							throw new AurigaMailBusinessException("MessageId non valorizzato");
						}
						// salvo il messaggio creato nello storage di
						// archiviazione
						uriNew = null;
						if (saveAndUpdateMailInStorage && globalStorageService != null) {
							try {
								uriNew = globalStorageService.writeTo(message);
							} catch (Exception exc) {
								log.error("Errore nel salvataggio della mail" + logIdMail + " nello storage di archiviazione: ", exc);
							}

							if (StringUtils.isNotBlank(uriNew)) {
								senderBean.setUriSavedMimeMessage(uriNew);
							}

						}
						newSenderBean = creaNuovoSenderBean(senderBean, messageId, addresses, true, false, 0).get(0);
					} catch (MailSendException mse) {
						log.debug(mse.getLocalizedMessage(), mse);
						if (mse.getMessageExceptions()[0].getMessage().equalsIgnoreCase("Invalid Addresses")) {
							throw new AurigaMailBusinessException("Verificare che i destinatari siano corretti", mse);
						} else {
							throw new AurigaMailBusinessException("Verificare che i parametri siano corretti", mse);
						}
					} catch (AttachLimitExceedException e) {
						log.debug(e.getLocalizedMessage(), e);
						throw e;
					} catch (Exception e) {
						log.error("Impossibile spedire la mail" + logIdMail + ": ", e);
						newSenderBean = creaNuovoSenderBean(senderBean, messageId, addresses, false, false, 0).get(0);
					}
					result.add(newSenderBean);
				}
			}
			sendInfo.setMessage(message);
			sendInfo.setEmails(result);
		} catch (Exception e) {
			log.error("Eccezione sendMail: ", e);
			throw e;
		}
		return sendInfo;
	}

	/**
	 * Metodo di salvataggio in database della mail e successivo invio tramite thread asincrono
	 * 
	 * @param senderBean
	 * @param xmlNotifica
	 * @param tipoNotifica
	 * @param regProt
	 * @param mailPartenza
	 * @param deleteAfterSend
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	private ResultBean<EmailSentReferenceBean> salvaEmailESpedisci(SenderBean senderBean, String xmlNotifica, TipoInteroperabilita tipoNotifica,
			RegistrazioneProtocollo regProt, TEmailMgoBean mailPartenza, boolean deleteAfterSend, MailLoginBean login) throws AurigaMailBusinessException {
		ResultBean<EmailSentReferenceBean> resultBean = new ResultBean<EmailSentReferenceBean>();

		try {
			log.debug("*** salvaEmailESpedisci - start salvaEmail ***");
			ResultBean<EmailSavedReferenceBean> resultBeanSave = salvaEmail(senderBean, xmlNotifica, tipoNotifica, regProt, mailPartenza, deleteAfterSend,
					login);
			log.debug("*** salvaEmailESpedisci - end salvaEmail ***");
			if (resultBeanSave.isInError() || resultBeanSave.getResultBean() == null || resultBeanSave.getResultBean().getSavedEmails() == null
					|| resultBeanSave.getResultBean().getSavedEmails().isEmpty()) {
				resultBean.setDefaultMessage(resultBeanSave.getDefaultMessage());
				resultBean.setErrorStackTrace(resultBeanSave.getErrorStackTrace());
				resultBean.setInError(true);
				return resultBean;
			}

			final EmailSavedReferenceBean bean = resultBeanSave.getResultBean();
			final List<SenderBean> listaMailSalvate = bean.getSavedEmails();

			List<SenderBean> listaSenderBean = new ArrayList<SenderBean>();
			for (SenderBean currentBean : listaMailSalvate) {
				listaSenderBean.add((SenderBean) BeanUtils.cloneBean(currentBean));
				currentBean.setAttachments(null);
			}

			List<String> listaIdMailSalvate = bean.getIdEmails();
			log.info("Mail salvata con successo. Avvio il thread di invio");
			String idAccount = MailboxUtil.getIdAccount(senderBean.getAccount());
			Boolean invioMail = true;
			try {
				DebugInvioBean lDebugInvioBean = SpringAppContext.getContext().getBean(DebugInvioBean.class);
				invioMail = lDebugInvioBean.isInvia();
			} catch (NoSuchBeanDefinitionException exc) {
				log.warn("DebugInvioBean non configurato");
			}
			ThreadSend lThreadSend = new ThreadSend(listaSenderBean, idAccount, SubjectUtil.subject.get(), invioMail, deleteAfterSend);
			Thread lThread = new Thread(lThreadSend);
			lThread.start();
			resultBean.setInError(false);
			// in realtà le mail non sono ancora inviate, restituisco la lista
			// di mail predisposte per l'invio
			EmailSentReferenceBean referenceBean = new EmailSentReferenceBean();
			referenceBean.setIdEmails(listaIdMailSalvate);
			referenceBean.setSentMails(listaMailSalvate);
			resultBean.setResultBean(referenceBean);
		} catch (MailSendException mse) {
			log.debug(mse.getLocalizedMessage(), mse);
			if (mse.getMessageExceptions()[0].getMessage().equalsIgnoreCase("Invalid Addresses")) {
				resultBean.setDefaultMessage("Impossibile spedire la mail: verificare che i destinatari siano corretti");
				resultBean.setErrorStackTrace(ExceptionUtils.getStackTrace(mse));
				resultBean.setInError(true);
			} else {
				resultBean.setDefaultMessage(mse.getMessage());
				resultBean.setErrorStackTrace(ExceptionUtils.getStackTrace(mse));
				resultBean.setInError(true);
			}
			throw mse;
		} catch (NoAdressesException e) {
			log.error("Impossibile spedire la mail: nessun destinatario specificato", e);
			resultBean.setDefaultMessage(e.getMessage());
			resultBean.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
			resultBean.setInError(true);
		} catch (TooManyAdressesException e) {
			log.error("Impossibile spedire la mail: troppi destinatari per una stessa email rispetto ai consentiti: ", e);
			resultBean.setDefaultMessage(e.getMessage());
			resultBean.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
			resultBean.setInError(true);
		} catch (AttachLimitExceedException e) {
			log.error(e.getLocalizedMessage(), e);
			resultBean.setDefaultMessage(e.getMessage());
			resultBean.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
			resultBean.setInError(true);
		} catch (Exception e) {
			log.error("Impossibile spedire la mail: ", e);
			resultBean.setDefaultMessage("Impossibile spedire la mail: si è verificato un errore");
			resultBean.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
			resultBean.setInError(true);
		}
		return resultBean;
	}

	/**
	 * Metodo di salvataggio finale in database della bozza e successivo invio tramite thread asincrono <br>
	 * Non sono previste bozze di mail interoperabili
	 * 
	 * @param draftInputBean
	 *            dati della mail da salvare
	 * @param login
	 *            dati di autenticazione
	 * @param cancellaDopoInvio
	 *            indica se cancellare la mail dopo averla inviata
	 * @param mode
	 *            se vale {@link DraftAndWorkItemsBean#SENT_FROM_LIST allora bisogna recuperare eventuali allegati in database prima di effettuare il
	 *            salvataggio }
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	private ResultBean<EmailSentReferenceBean> consolidaBozzaEmailESpedisci(DraftAndWorkItemsBean draftInputBean, MailLoginBean login,
			Boolean cancellaDopoInvio, Integer mode) throws AurigaMailBusinessException {
		ResultBean<EmailSentReferenceBean> resultBean = new ResultBean<EmailSentReferenceBean>();
		EmailSentReferenceBean referenceBean = new EmailSentReferenceBean();
		String logIdMail = (StringUtils.isNotEmpty(draftInputBean.getIdemailio()) ? " avente id " + draftInputBean.getIdemailio() : "");

		InvioMailXmlBean datiMailUnbind = draftInputBean.getDatiemailin();
		String account = datiMailUnbind.getAccountMittente();

		String idEmail = draftInputBean.getIdemailio();

		if (StringUtils.isBlank(idEmail)) {
			// Nel salvataggio finale della mail l'id mail deve essere presente
			throw new AurigaMailBusinessException("Impossibile salvare la bozza: idEmail nullo");
		}

		if (StringUtils.isBlank(account)) {
			// il mittente deve essere obbligatorio per salvare correttamente la
			// bozza. Alcune informazioni infatti si ricavano direttamente
			// dall'account
			throw new AurigaMailBusinessException("Impossibile salvare la bozza: account mittente nullo");
		}

		try {

			// recupero le informazioni per inoltro/risposta visto che non
			// arrivano da AurigaWeb, ma sono presenti solo nell'inserimento
			// iniziale della bozza
			// non occorre aggiornare queste informazioni in aggiornamento della
			// bozza ma solo prima dell'invio
			populateRispostaInoltroBozza(datiMailUnbind, idEmail);

			String idAccount = MailboxUtil.getIdAccount(account);

			storage = new StorageCenter();
			String idMailBox = MailboxUtil.getIdMailbox(idAccount);
			StorageService globalStorageService = storage.getGlobalStorage(idMailBox);

			Properties propertiesAccount = SendUtils.getAccountProperties(idAccount);

			// creo oggetto senderBean con le informazioni recuperate dal bean
			// della store in input
			// al momento mi interessano solo i dati dei destinatari della mail
			SenderBean senderBean = SendUtils.convertInvioMailXmlBeanToSenderBean(datiMailUnbind);
			if (propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_IS_PEC.keyname(), "false").equalsIgnoreCase("true")) {
				senderBean.setIsPec(true);
			} else {
				senderBean.setIsPec(false);
			}

			// setto id email
			senderBean.setIdEmail(idEmail);

			// verifico se i destinatari inseriti sono validi e non superano il
			// numero totale di indirizzi impostato nel parametro
			// MAX_NUM_DESTINATARI_INVIO_EMAIL in T_PARAMETERS. Attenzione che
			// il parametro è configurato con il numero massimo di indirizzi,
			// indipendentemente dal numero di mail in cui è suddivisa la mail
			// originale
			// e inoltre ripulisco il senderBean dai doppioni
			AddressBean emailAddresses = verificaDestinatariEPulisciDoppioni(senderBean);

			// aggiorno la lista di destinatari anche nel bean da passare alla
			// store
			ArrayList<DestinatariInvioMailXmlBean> listaDestinatari = new ArrayList<DestinatariInvioMailXmlBean>();

			for (String addressTo : senderBean.getAddressTo()) {
				DestinatariInvioMailXmlBean destinatario = new DestinatariInvioMailXmlBean();
				destinatario.setIndirizzo(addressTo);
				destinatario.setTipo(TipoDestinatario.TO.getValue());
				listaDestinatari.add(destinatario);
			}

			if (senderBean.getAddressCc() != null && !senderBean.getAddressCc().isEmpty()) {
				for (String addressCc : senderBean.getAddressCc()) {
					DestinatariInvioMailXmlBean destinatario = new DestinatariInvioMailXmlBean();
					destinatario.setIndirizzo(addressCc);
					destinatario.setTipo(TipoDestinatario.CC.getValue());
					listaDestinatari.add(destinatario);
				}
			}

			if (senderBean.getAddressBcc() != null && !senderBean.getAddressBcc().isEmpty()) {
				for (String addressBcc : senderBean.getAddressBcc()) {
					DestinatariInvioMailXmlBean destinatario = new DestinatariInvioMailXmlBean();
					destinatario.setIndirizzo(addressBcc);
					destinatario.setTipo(TipoDestinatario.BCC.getValue());
					listaDestinatari.add(destinatario);
				}
			}

			datiMailUnbind.setListaDestinatari(listaDestinatari);

			// il controllo della dimensione degli allegati è fatto all'interno
			// degli eventuali gruppi di destinatari

			// Numero massimo di indirizzi presenti in una singola email (per
			// spezzare l'invio in n mail)
			Integer maxAddress = new Integer(propertiesAccount.getProperty(AccountConfigKey.SMTP_MAX_ADDRESS.keyname(), "50"));
			// se attivo il flag di invio separato significa che deve essere
			// spedita una mail per ogni destinatario
			if (datiMailUnbind.getFlgInvioSeparato() != null && datiMailUnbind.getFlgInvioSeparato()) {
				maxAddress = 1;
			}
			// Numero di gruppi
			SenderBean newSenderBean = null;
			Integer group = emailAddresses.getgroup(maxAddress);
			List<String> addresses = null;
			List<SenderBean> listaMailSalvate = new ArrayList<SenderBean>();
			List<String> listaIdMailSalvate = new ArrayList<String>();

			if (mode.equals(DraftAndWorkItemsBean.SENT_FROM_LIST)) {

				// se arriva da lista occorre recuperare gli allegati perchè non
				// sono presenti in input

				if (StringUtils.isBlank(datiMailUnbind.getUri())) {
					throw new AurigaMailBusinessException("Impossibile salvare la bozza: URI della mail nullo");
				}

				// devo recuperare gli allegati visto che nell'invio dalla lista
				// non sono forniti dal bean
				MailBreaker breaker = new MailBreaker();
				// recupero allegati della mail
				File eml = globalStorageService.extractFile(datiMailUnbind.getUri());
				List<MailAttachmentsBean> attachs = breaker.getAttachments(eml);
				List<SenderAttachmentsBean> listaAttachment = SendUtils.creaListaAttachs(attachs);
				// nessun confronto con la lista di allegati in input, visto che
				// è sicuramente vuota
				senderBean.setAttachments(listaAttachment);
				datiMailUnbind.setlSenderAttachmentsBean(listaAttachment);

				// devo aggiornare anche il body visto che è stato salvato
				// pulendo i tag html

				String body = breaker.getBodyHtml(eml);
				if (StringUtils.isBlank(body)) {
					body = breaker.getBody(eml);
				}

				senderBean.setBody(body);
				senderBean.setIsHtml(false);
				if (StringUtils.isNotEmpty(body)) {
					try {
						senderBean.setIsHtml(SendUtils.isHtml(body));
					} catch (Exception e) {
						log.error("Impossibile capire se html: ", e);
						throw e;
					}
				}
				datiMailUnbind.setBody(body);
			}

			if (group == 1) {
				// un solo gruppo, quindi solo la bozza da salvare, procedo con
				// il consolidamento in database
				// se sono nel dettaglio mail, se sono in lista è sufficiente
				// aggiornare i flag

				if (mode.equals(DraftAndWorkItemsBean.SENT_FROM_LIST)) {
					// in questo caso infatti non sono state apportate modifiche
					// alla bozza, e quindi il database e il file nello storage
					// non sono da aggiornare
					// al momento dell'invio vero e proprio viene comunque
					// generato il file eml vero e proprio che verrà spedito
					consolidaBozzaPerInvio(idEmail, true);
					newSenderBean = creaNuovoSenderBean(senderBean, null, null, false, false, 0).get(0);
					listaMailSalvate.add(newSenderBean);
					listaIdMailSalvate.add(idEmail);
				} else {

					ResultBean<DraftAndWorkItemsSavedBean> resultSave = saveDraftAndWorkItems(draftInputBean, DraftAndWorkItemsBean.MODE_SAVE_DRAFT_PRE_SEND,
							login);
					if (resultSave == null || resultSave.isInError() || resultSave.getResultBean().getDraftEmail() == null) {
						throw new AurigaMailBusinessException("Errore nel salvataggio finale della bozza");
					}
					// bozza salvata, aggiungo alla lista per l'invio
					listaMailSalvate.add(resultSave.getResultBean().getDraftEmail());
					listaIdMailSalvate.add(idEmail);
				}

			} else {

				// creo una copia del bean principale, in caso di errori posso
				// ripristinare la situazione iniziale

				InvioMailXmlBean newDatiMail = SerializationUtils.clone(datiMailUnbind);

				// Ciclo i gruppi

				Session session = null;
				Transaction lTransaction = null;
				Boolean draftUpdated = false;
				try {

					// apro una transazione, in modo da annullare le modifiche
					// in caso di errore
					// attenzione al fatto che la store non è in transazione, ma
					// è gestita da un'apposita sessione creata in
					// AurigaBusiness
					// per cui eventuali modifiche dovranno essere ripristinate
					// effettuando una nuova chiamata alla store, ripristinando
					// i destinatari
					// "originali"
					// attenzione al fatto che la URI e la dimensione sono
					// ripristinate

					// ciclo for invertito in modo da salvare prima le mail
					// "secondarie" e poi la bozza principale, in caso di errore
					// non si aggiorna nemmeno la
					// store

					for (int index = 0; index < group; index++) {

						addresses = emailAddresses.getAddressForGroup(index, maxAddress);
						if (index == 0) {
							// ho più gruppi, quindi più mail da salvare, di cui
							// solo la prima come bozza e le altre come mail
							// "standard" in uscita
							// gli item di lavorazione saranno quindi associati
							// solo alla mail consolidata
							// mentre gli altri non avranno gli item associati
							// tutti gli indirizzi del gruppo sono impostati
							// come principali
							listaDestinatari = new ArrayList<DestinatariInvioMailXmlBean>();
							for (String address : addresses) {
								DestinatariInvioMailXmlBean destinatario = new DestinatariInvioMailXmlBean();
								destinatario.setIndirizzo(address);
								destinatario.setTipo(TipoDestinatario.TO.getValue());
								listaDestinatari.add(destinatario);
							}
							newDatiMail.setListaDestinatari(listaDestinatari);

							if (mode.equals(DraftAndWorkItemsBean.SENT_FROM_LIST)) {

								// è cambiata la lista di destinatari, questa è
								// l'unica modifica che va aggiornata tramite la
								// store
								// inoltre occorre salvare il nuovo EML sullo
								// storage, per farlo devo recuperare tutti i
								// destinatari visto che non sono presenti
								// in
								// input da lista

								newDatiMail.setFlgAggSoloDestinatari(1);

							}

							draftInputBean.setDatiemailin(newDatiMail);
							ResultBean<DraftAndWorkItemsSavedBean> resultSave = saveDraftAndWorkItems(draftInputBean, DraftAndWorkItemsBean.MODE_SAVE_DRAFT,
									login);
							if (resultSave == null || resultSave.isInError() || resultSave.getResultBean().getDraftEmail() == null) {
								throw new AurigaMailBusinessException("Errore nel salvataggio della bozza con gruppo di destinatari ridotto");
							}
							// bozza salvata, aggiungo alla lista per l'invio
							draftUpdated = true;

							listaMailSalvate.add(resultSave.getResultBean().getDraftEmail());
							listaIdMailSalvate.add(resultSave.getResultBean().getIdEmail());

						} else {

							if (index == 1) {
								// apro la transazione per salvare le mail
								// successive
								session = HibernateUtil.begin();
								lTransaction = session.beginTransaction();
							}

							// nuova mail da salvare e schedulare per l'invio
							newSenderBean = creaNuovoSenderBean(senderBean, null, addresses, false, false, 0).get(0);
							// salvataggio classico della mail
							ResultBean<EmailSavedReferenceBean> resultBeanSave = salvaEmailInSession(newSenderBean, null, null, null, null, session);
							if (resultBeanSave.isInError() || resultBeanSave.getResultBean() == null || resultBeanSave.getResultBean().getSavedEmails() == null
									|| resultBeanSave.getResultBean().getSavedEmails().isEmpty()) {
								throw new AurigaMailBusinessException("Errore nel salvataggio della mail per il gruppo di destinatari ridotto" + logIdMail);
							}
							listaMailSalvate.addAll(resultBeanSave.getResultBean().getSavedEmails());
							listaIdMailSalvate.addAll(resultBeanSave.getResultBean().getIdEmails());

							if (index == (group - 1)) {
								session.flush();
								// aggiorno lo stato di consolidamento e la
								// folder per predisporre l'invio della bozza,
								// sempre nella sessione corrente
								consolidaBozzaPerInvioInSession(idEmail, true, session);
								lTransaction.commit();
							}

						}

					}

				} catch (Exception e) {
					log.debug(e.getLocalizedMessage(), e);
					if (lTransaction != null && !lTransaction.wasCommitted()) {
						// effettuo subito il rollback, in modo da non avere
						// problemi con la chiamata alla store
						lTransaction.rollback();
					}

					// errore nel salvataggio delle mail "divise"
					// ripristino la situazione precedente
					if (draftUpdated) {
						// ripristino la store con tutti i destinatari originali
						datiMailUnbind.setFlgAggSoloDestinatari(1);
						// è l'unica modifica che deve essere cambiata dalla
						// store, gli altri dati devono essere mantenuti
						// l'URI della bozza da cancellare però è l'ultimo
						// salvato in file system
						if (newDatiMail != null) {
							datiMailUnbind.setUri(newDatiMail.getUri());
						}
						draftInputBean.setDatiemailin(datiMailUnbind);
						ResultBean<DraftAndWorkItemsSavedBean> resultSave = saveDraftAndWorkItems(draftInputBean, DraftAndWorkItemsBean.MODE_SAVE_DRAFT, login);
						if (resultSave == null || resultSave.isInError() || resultSave.getResultBean().getDraftEmail() == null) {
							throw new AurigaMailBusinessException("Errore nel ripristino dei destinatari della bozza");
						}
					}

					// rimozione di eventuali URI di mail che saranno rimosse
					for (SenderBean mailSalvata : listaMailSalvate) {
						try {
							if (StringUtils.isNotBlank(mailSalvata.getUriSavedMimeMessage())) {
								if (globalStorageService.extractFile(mailSalvata.getUriSavedMimeMessage()) != null) {
									globalStorageService.delete(mailSalvata.getUriSavedMimeMessage());
								}
							}
						} catch (Exception exc) {
							log.warn("Errore nella cancellazione dell'URI della bozza", exc);
						}
					}
					throw e;
				} finally {
					try {
						HibernateUtil.release(session);
					} catch (Exception e) {
						log.error("Errore nel rilascio sessione Hibernate post salva/spedisci ", e);
						throw e;
					}
				}
			}
			if (log.isInfoEnabled()) {
				log.info("Bozza" + logIdMail + " salvata con successo. Avvio il thread di invio");
			}
			Boolean invioMail = true;
			try {
				DebugInvioBean lDebugInvioBean = SpringAppContext.getContext().getBean(DebugInvioBean.class);
				invioMail = lDebugInvioBean.isInvia();
			} catch (NoSuchBeanDefinitionException exc) {
				log.warn("DebugInvioBean non configurato");
			}
			ThreadSend lThreadSend = new ThreadSend(listaMailSalvate, idAccount, SubjectUtil.subject.get(), invioMail, cancellaDopoInvio);
			Thread lThread = new Thread(lThreadSend);
			lThread.start();
			resultBean.setInError(false);
			// in realtà le mail non sono ancora inviate, restituisco la lista
			// di mail predisposte per l'invio
			referenceBean.setIdEmails(listaIdMailSalvate);
			referenceBean.setSentMails(listaMailSalvate);
			resultBean.setResultBean(referenceBean);
		} catch (MailSendException mse) {
			log.debug(mse.getLocalizedMessage(), mse);
			if (mse.getMessageExceptions()[0].getMessage().equalsIgnoreCase("Invalid Addresses")) {
				resultBean.setDefaultMessage("Impossibile spedire la mail" + logIdMail + ": verificare che i destinatari siano corretti");
				resultBean.setErrorStackTrace(ExceptionUtils.getStackTrace(mse));
				resultBean.setInError(true);
			} else {
				resultBean.setDefaultMessage(mse.getMessage());
				resultBean.setErrorStackTrace(ExceptionUtils.getStackTrace(mse));
				resultBean.setInError(true);
			}
		} catch (NoAdressesException e) {
			log.error("Impossibile spedire la mail" + logIdMail + ": nessun destinatario specificato", e);
			resultBean.setDefaultMessage(e.getMessage());
			resultBean.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
			resultBean.setInError(true);
		} catch (TooManyAdressesException e) {
			log.error("Impossibile spedire la mail" + logIdMail + ": troppi destinatari per una stessa email rispetto ai consentiti: ", e);
			resultBean.setDefaultMessage(e.getMessage());
			resultBean.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
			resultBean.setInError(true);
		} catch (AttachLimitExceedException e) {
			log.error("Impossibile spedire la mail" + logIdMail + ": la dimensione degli allegati supera il limite previsto di " + e.getLimit(), e);
			resultBean.setDefaultMessage(e.getMessage());
			resultBean.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
			resultBean.setInError(true);
		} catch (Exception e) {
			log.error("Impossibile spedire la mail" + logIdMail + ": ", e);
			resultBean.setDefaultMessage("Impossibile spedire la mail: si è verificato un errore");
			resultBean.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
			resultBean.setInError(true);
		}
		return resultBean;
	}

	/**
	 * Metodo che salva l'email in database e eventuali item di lavorazione invocando la relativa store
	 * 
	 * @param senderBean
	 * @param xmlNotifica
	 * @param tipoNotifica
	 * @param regProt
	 * @param mailPartenza
	 * @param login
	 * @return
	 * @throws Exception
	 */

	private ResultBean<EmailSavedReferenceBean> salvaEmail(SenderBean senderBean, String xmlNotifica, TipoInteroperabilita tipoNotifica,
			RegistrazioneProtocollo regProt, TEmailMgoBean mailPartenza, boolean deleteAfterSend, MailLoginBean login) throws Exception {
		Session session = null;
		ResultBean<EmailSavedReferenceBean> resultBean = new ResultBean<EmailSavedReferenceBean>();
		try {
			session = HibernateUtil.begin();
			Transaction lTransaction = session.beginTransaction();
			log.debug("*** salvaEmail - start salvaEmailInSession ***");
			resultBean = salvaEmailInSession(senderBean, xmlNotifica, tipoNotifica, regProt, mailPartenza, session);
			log.debug("*** salvaEmail - end salvaEmailInSession ***");
			if (resultBean == null) {
				throw new AurigaMailBusinessException("Nessun risultato nel metodo salvaEmailInSession");
			}
			if (resultBean.isInError()) {
				return resultBean;
			}
			lTransaction.commit();

			// funzionalità per il salvataggio degli item di lavorazione
			// utilizzando la relativa store

			String idMailBox = null;
			StorageService globalStorageService = null;
			ArrayList<String> listaURIItemLavorazioneNew = new ArrayList<String>();

			try {
				// se la cancellazione della mail è attiva non ha senso invocare
				// la store per salvare gli item di lavorazione
				if (!deleteAfterSend && senderBean.getListaItemInLavorazioneInvioMail() != null && !senderBean.getListaItemInLavorazioneInvioMail().isEmpty()) {

					if (login == null) {
						throw new AurigaMailBusinessException("Impossibile salvare gli item di lavorazione: credenziali di invocazione store nulle");
					}

					AurigaLoginBean schema = new AurigaLoginBean();

					schema.setSchema(login.getSchema());
					schema.setToken(login.getToken()); // dal token si recupera
														// l'id utente collegato
					schema.setIdUserLavoro(login.getUserId()); // valorizzato
																// con id utente
																// in delega se
																// presente

					String account = senderBean.getAccount();

					if (StringUtils.isBlank(account)) {
						// il mittente deve essere obbligatorio per salvare
						// correttamente la bozza. Alcune informazioni infatti
						// si ricavano direttamente
						// dall'account
						throw new AurigaMailBusinessException("Impossibile salvare gli item di lavorazione: account mittente nullo");
					}
					String idAccount = MailboxUtil.getIdAccount(account);

					storage = new StorageCenter();
					idMailBox = MailboxUtil.getIdMailbox(idAccount);
					globalStorageService = storage.getGlobalStorage(idMailBox);

					List<File> listaFileItemLavorazione = senderBean.getListaFileItemLavorazione();

					for (ItemLavorazioneMailXmlBean itemLavorazione : senderBean.getListaItemInLavorazioneInvioMail()) {

						if (StringUtils.isNotBlank(itemLavorazione.getUriFile())) {

							// item di lavorazione con file

							String uriItemNew = itemLavorazione.getUriFile();
							Integer posizione = itemLavorazione.getPosFileInLista();

							// recupero il relativo file
							if (listaFileItemLavorazione != null && !listaFileItemLavorazione.isEmpty() && posizione != null
									&& listaFileItemLavorazione.get(posizione) != null) {
								uriItemNew = globalStorageService.store(listaFileItemLavorazione.get(posizione));
								if (StringUtils.isBlank(uriItemNew)) {
									throw new AurigaMailBusinessException("Errore nel salvataggio del file dell'item di lavorazione");
								} else {
									// aggiorno l'URI dell'item di lavorazione
									itemLavorazione.setUriFile(uriItemNew);
									listaURIItemLavorazioneNew.add(uriItemNew);
								}
							} else {
								throw new AurigaMailBusinessException("Errore nel salvataggio del file dell'item di lavorazione: file non presente in input");
							}

						}

					}

					log.info("Salvataggio degli item di lavorazione nello storage eseguito con successo");

					InvioMailXmlBean datiMailUnbind = new InvioMailXmlBean();
					datiMailUnbind.setListaItemLavorazione(senderBean.getListaItemInLavorazioneInvioMail());

					// creo l'xml della sezione cache con i dati della mail
					// aggiornati
					XmlUtilitySerializer xmlUtilitySerializer = new XmlUtilitySerializer();

					DmpkIntMgoEmailIuemailBean storeBean = new DmpkIntMgoEmailIuemailBean();
					// salvo gli item di lavorazione solo per la prima mail

					storeBean.setIdemailio(resultBean.getResultBean().getIdEmails().get(0));
					storeBean.setDatiemailin(xmlUtilitySerializer.bindXml(datiMailUnbind));

					log.info("Chiamata alla store procedure DmpkIntMgoEmailIuemail");

					DmpkIntMgoEmailIuemail storeIuEmail = new DmpkIntMgoEmailIuemail();
					StoreResultBean<DmpkIntMgoEmailIuemailBean> output = storeIuEmail.execute(Locale.ITALIAN, schema, storeBean);

					// gestione dei messaggi di errore
					if (output.isInError()) {
						if(StringUtils.isNotBlank(output.getDefaultMessage())) {
							throw new AurigaMailBusinessException(output.getDefaultMessage());
						} else {
							throw new AurigaMailBusinessException("Si è verificato un errore durante il salvataggio della mail");
						}
					}
				}

				log.info("Salvataggio degli item di lavorazione eseguito con successo");

			} catch (Exception exc) {

				log.error("Errore nel salvataggio degli item di lavorazione", exc);

				// si è verificato un errore, cancello le mail precedentemente
				// inserite
				DaoTEmailMgo daoMail = (DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class);

				for (String idEmailToDelete : resultBean.getResultBean().getIdEmails()) {
					TEmailMgoBean mailBean = new TEmailMgoBean();
					mailBean.setIdEmail(idEmailToDelete);
					daoMail.delete(mailBean);
				}

				if (globalStorageService != null && listaURIItemLavorazioneNew != null && !listaURIItemLavorazioneNew.isEmpty()) {
					// cancello gli URI degli item di lavorazione dallo storage
					for (String uriItem : listaURIItemLavorazioneNew) {
						try {
							globalStorageService.delete(uriItem);
						} catch (Exception e) {
							log.warn("Errore nella cancellazione dell'URI dell'item", e);
						}
					}
				}

				throw exc;

			}

		} catch (NoAdressesException e) {
			log.error("Impossibile spedire la mail: nessun destinatario specificato", e);
			resultBean.setDefaultMessage(e.getMessage());
			resultBean.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
			resultBean.setInError(true);
		} catch (TooManyAdressesException e) {
			log.error(e.getLocalizedMessage(), e);
			resultBean.setDefaultMessage(e.getMessage());
			resultBean.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
			resultBean.setInError(true);
			throw e;
		} catch (AttachLimitExceedException e) {
			log.error(e.getLocalizedMessage(), e);
			resultBean.setDefaultMessage(e.getMessage());
			resultBean.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
			resultBean.setInError(true);
			throw e;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
			resultBean.setDefaultMessage("Impossibile salvare la mail: si è verificato un errore");
			resultBean.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
			resultBean.setInError(true);
			throw e;
		}

		finally {
			try {
				HibernateUtil.release(session);
			} catch (Exception e) {
				log.error("Errore nel rilascio sessione Hibernate post salvataggio ", e);
				throw e;
			}
		}
		return resultBean;
	}

	/**
	 * Metodo di salvataggio in database della mail e successivo invio tramite thread asincrono
	 * 
	 * @param senderBean
	 * @param xmlNotifica
	 * @param tipoNotifica
	 * @param regProt
	 * @param mailPartenza
	 * @param deleteAfterSend
	 * @return
	 * @throws AurigaMailBusinessException
	 * @throws TooManyAdressesException
	 * @throws AttachLimitExceedException
	 */

	private ResultBean<EmailSavedReferenceBean> salvaEmailInSession(SenderBean senderBean, String xmlNotifica, TipoInteroperabilita tipoNotifica,
			RegistrazioneProtocollo regProt, TEmailMgoBean mailPartenza, Session session)
			throws AurigaMailBusinessException, TooManyAdressesException, AttachLimitExceedException {
		ResultBean<EmailSavedReferenceBean> resultBean = new ResultBean<EmailSavedReferenceBean>();
		EmailSavedReferenceBean referenceBean = null;
		try {
			log.debug("salvaEmailInSession start");
			// preparo i dati per un 'eventuale spedizione
			String idAccount = MailboxUtil.getIdAccountInSession(senderBean.getAccount(), session);
			List<SenderBean> daSalvare = new ArrayList<SenderBean>();
			Properties propertiesAccount = SendUtils.getAccountPropertiesInSession(idAccount, session);
			JavaMailSenderImpl mailsender = new JavaMailSenderImpl();
			mailsender.setJavaMailProperties(propertiesAccount);
			mailsender.setHost(propertiesAccount.getProperty(AccountConfigKey.SMTP_HOST.keyname()));
			mailsender.setPort(Integer.valueOf(propertiesAccount.getProperty(AccountConfigKey.SMTP_PORT.keyname())));
			mailsender.setUsername(propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname()));
			// Decripto la password associata all'account
			mailsender.setPassword(CryptographyUtil.decryptionAccountPasswordWithAES(propertiesAccount));
			// verifico i destinatari
			// e ripulisco il senderBean dai doppioni
			if (propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_IS_PEC.keyname(), "false").equalsIgnoreCase("true")) {
				senderBean.setIsPec(true);
			} else {
				senderBean.setIsPec(false);
			}
			AddressBean emailAddresses = verificaDestinatariEPulisciDoppioni(senderBean);
			// aggiungo il file di notifica come attachment se presente
			if (xmlNotifica != null && tipoNotifica != null) {
				
				// se invio una notifica di eccezione sposto la email originaria
				// in standard.archiviata.arrivo.respinte
				DaoTParameters daoParametri = (DaoTParameters) DaoFactory.getDao(DaoTParameters.class);
				TParametersBean parametroEccezione = daoParametri.getInSession(ARCHIVIA_ECCEZIONE_PARAMETER, session);
				if (tipoNotifica == TipoInteroperabilita.NOTIFICA_ECCEZIONE && mailPartenza != null
						&& parametroEccezione.getStrValue().equalsIgnoreCase("true")) {
					archiviaEmailConEccezioneInSession(mailPartenza, session);
				}
				SenderAttachmentsBean attach = new SenderAttachmentsBean();
				File tmpFile = File.createTempFile("tmp_", "_xml");
				FileUtils.writeStringToFile(tmpFile, xmlNotifica);
				if(tmpFile != null && tmpFile.getPath() != null) {
					log.debug("xmlNotifica tmpFile: "+ tmpFile.getPath());
					attach.setFile(tmpFile);
				}
				attach.setFilename(tipoNotifica.getFilename());
				if (senderBean.getAttachments() != null) {
					senderBean.getAttachments().add(attach);
				} else {
					List<SenderAttachmentsBean> listaAttach = new ArrayList<SenderAttachmentsBean>();
					listaAttach.add(attach);
					senderBean.setAttachments(listaAttach);
				}
				attach.setNrAllegato(senderBean.getAttachments().size()+1);
			}
			// archivia mail con eccezione anche per le mail non interoperabile
			if (senderBean.getRispInol() != null && senderBean.getRispInol().getRispInol() != null
					&& senderBean.getRispInol().getRispInol().equals(RispostaInoltro.NOTIFICA_ECCEZIONE)) {
				DaoTParameters daoParametri = (DaoTParameters) DaoFactory.getDao(DaoTParameters.class);
				TParametersBean parametroEccezione = daoParametri.getInSession(ARCHIVIA_ECCEZIONE_PARAMETER, session);
				if (parametroEccezione.getStrValue().equalsIgnoreCase("true")) {
					archiviaEmailConEccezioneInSession(senderBean.getRispInol().getMailOriginaria(), session);
				}
			}
			// Numero massimo di indirizzi presenti in una singola email (per
			// spezzare l'invio in n mail)
			Integer maxAddress = new Integer(propertiesAccount.getProperty(AccountConfigKey.SMTP_MAX_ADDRESS.keyname(), "50"));
			// verifico se è attivo il parametro ATTIVA_FRAZ_ATTACH_MAIL per l'attivazione dello spezzettamento
			boolean attivaFrazAttachMail = Boolean.FALSE;
			try {
				DaoTParameters daoParametri = (DaoTParameters) DaoFactory.getDao(DaoTParameters.class);
				TParametersBean parametroAttivaFraz = daoParametri.getInSession(ATTIVA_FRAZ_ATTACH_MAIL, session);
				if (parametroAttivaFraz != null
						&& parametroAttivaFraz.getNumValue().intValue() == 1 ) {
					attivaFrazAttachMail = Boolean.TRUE;
				}
			} catch (Exception ex) {
				log.warn("Parametro ATTIVA_FRAZ_ATTACH_MAIL nella T_PARAMITERS non presente, sarà considerato FALSE.");
			}
			log.debug("attivaFrazAttachMail: " + attivaFrazAttachMail);
			// Recupero le entity di hibernate
//			Reflections reflection = new Reflections("it.eng.aurigamailbusiness.bean", new TypeAnnotationsScanner().filterResultsBy(new Predicate<String>() {
//
//				@Override
//				public boolean apply(String input) {
//					if (StringUtils.equalsIgnoreCase(input, XmlRootElement.class.getName())) {
//						return true;
//					} else {
//						return false;
//					}
//				}
//			}));
//			Set<Class<?>> classesXML = reflection.getTypesAnnotatedWith(XmlRootElement.class);
//			try {
//				XmlUtil.setContext(JAXBContext.newInstance(classesXML.toArray(new Class[0])));
//			} catch (JAXBException e) {
//				log.error("Impossibile istanziare il contesto di jaxb");
//				throw new AurigaMailBusinessException("Impossibile istanziare il contesto di jaxb", e);
//			}			
//			log.debug("SenderBean: "+ XmlUtil.objectToXml(senderBean));
			
			
			BigDecimal limiteAttachSizeDB = getAttachmentMaxSizeDBInSession(idAccount, session);
			Double limiteAttachSizeDBDouble = limiteAttachSizeDB != null ? limiteAttachSizeDB.doubleValue() : 0;
			double limitAttachSizeDBMega = limiteAttachSizeDBDouble * BYTES_IN_MEGA;
			// se attivo il flag di invio separato significa che deve essere
			// spedita una mail per ogni destinatario
			//Aggiunta gestione anche senza allegato ,
			//per evitare n mail inviate con message_id diversi

			if (senderBean.getAttachments() != null && senderBean.getAttachments().size() > 0) {
				log.error("senderBean.getAttachments().size(): " + senderBean.getAttachments().size());
				Integer newMaxAddress = checkLimiteAttachmentsInSession(idAccount, null,
						senderBean.getFlgInvioSeparato(), senderBean, maxAddress, session, AZIONE_SALVATAGGIO,
						attivaFrazAttachMail, limiteAttachSizeDBDouble, limitAttachSizeDBMega);
				if (newMaxAddress > 0)
					maxAddress = newMaxAddress;
			} else {
				log.error("senderBean.getAttachments().size(): 0 ");
				Integer newMaxAddress = numeroDestinatariSenzaAllegato(idAccount, null,
						senderBean.getFlgInvioSeparato(), senderBean, maxAddress, session);
				if (newMaxAddress > 0)
					maxAddress = newMaxAddress;
			}
			log.error("maxAddress: " + maxAddress);
			// Numero di gruppi
			List<SenderBean> newSenderBeans = null;
			Integer group = emailAddresses.getgroup(maxAddress);
			List<String> addresses = null;
			for (int i = 0; i < group; i++) {
				// Ciclo i gruppi
				addresses = emailAddresses.getAddressForGroup(i, maxAddress);
				if (group > 1) {
					newSenderBeans = creaNuovoSenderBean(senderBean, null, addresses, false, attivaFrazAttachMail, limitAttachSizeDBMega);
				} else {
					newSenderBeans = creaNuovoSenderBean(senderBean, null, null, false, attivaFrazAttachMail, limitAttachSizeDBMega);
				}
				daSalvare.addAll(newSenderBeans);
			}

			referenceBean = new EmailSavedReferenceBean();
			// salvo email in database
			List<SenderBean> mailSalvate = processaMail(daSalvare, propertiesAccount, idAccount, mailsender, regProt, mailPartenza, session);
			// id dei risultati delle mail salvate
			List<String> idMailSalvate = new ArrayList<String>();
			for (SenderBean mailsalvata : mailSalvate) {
				idMailSalvate.add(mailsalvata.getIdEmail());
			}
			session.flush();
			referenceBean.setIdEmails(idMailSalvate);
			referenceBean.setSavedEmails(mailSalvate);
			resultBean.setInError(false);
			resultBean.setResultBean(referenceBean);
			log.info("Mail salvata con successo");
		} catch (NoAdressesException e) {
			log.error("Impossibile salvare la mail: nessun destinatario specificato", e);
			resultBean.setDefaultMessage(e.getMessage());
			resultBean.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
			resultBean.setInError(true);
		} catch (TooManyAdressesException e) {
			log.error("Impossibile salvare la mail: troppi destinatari per una stessa email rispetto ai consentiti: ", e);
			resultBean.setDefaultMessage(e.getMessage());
			resultBean.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
			resultBean.setInError(true);
			throw e;
		} catch (AttachLimitExceedException e) {
			log.error(e.getLocalizedMessage(), e);
			resultBean.setDefaultMessage(e.getMessage());
			resultBean.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
			resultBean.setInError(true);
			throw e;
		} catch (Exception e) {
			log.debug("Impossibile salvare la mail: " + e.getMessage() + " - " + ExceptionUtils.getStackTrace(e));
			log.error("Impossibile salvare la mail: ", e);
			resultBean.setDefaultMessage("Impossibile salvare la mail: si è verificato un errore");
			resultBean.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
			resultBean.setInError(true);
			throw new AurigaMailBusinessException("Impossibile salvare la mail: si è verificato un errore", e);
		}
		return resultBean;
	}

	private void archiviaEmailConEccezioneInSession(TEmailMgoBean mailPartenza, Session session) throws Exception {
		processor = new MailProcessorService();
		DaoTRelEmailFolder daoRelEmailFolder = (DaoTRelEmailFolder) DaoFactory.getDao(DaoTRelEmailFolder.class);
		TFilterFetch<TRelEmailFolderBean> filterFetch = new TFilterFetch<TRelEmailFolderBean>();
		TRelEmailFolderBean filter = new TRelEmailFolderBean();
		filter.setIdEmail(mailPartenza.getIdEmail());
		filterFetch.setFilter(filter);
		List<TRelEmailFolderBean> listaRel = daoRelEmailFolder.searchInSession(filterFetch, session).getData();
		List<String> classificazioniPartenza = new ArrayList<String>();
		DaoTFolderCaselle daoFolder = (DaoTFolderCaselle) DaoFactory.getDao(DaoTFolderCaselle.class);
		for (TRelEmailFolderBean rel : listaRel) {
			TFolderCaselleBean folder = daoFolder.getInSession(rel.getIdFolderCasella(), session);
			classificazioniPartenza.add(folder.getClassificazione());
		}
		for (String classificazioneFrom : classificazioniPartenza) {
			EmailGroupBean in = new EmailGroupBean();
			List<String> ids = new ArrayList<String>();
			ids.add(mailPartenza.getIdEmail());
			in.setIdEmails(ids);
			in.setClassificazioneTo(Classificazione.STANDARD_FOLDER_ARCHIVIATA_ARRIVO_RESPINTE);
			in.setClassificazioneFrom(classificazioneFrom);
			processor.spostaEmailInSession(in, session);
		}
	}

	private String ricavaValoreRicevuta(String idAccount) throws AurigaMailBusinessException {
		try {
			DaoTParameters daoParametri = (DaoTParameters) DaoFactory.getDao(DaoTParameters.class);
			TParametersBean parametro = null;
			TFilterFetch<TParametersBean> ff = new TFilterFetch<TParametersBean>();
			TParametersBean filterPar = new TParametersBean();
			filterPar.setParKey(RECEIPT_PARAMETER_KEY + "." + idAccount);
			ff.setFilter(filterPar);
			List<TParametersBean> listaRis = daoParametri.search(ff).getData();
			if (!ListUtil.isEmpty(listaRis)) {
				parametro = listaRis.get(0);
			}
			// se nullo cerco il parametro generico
			if (parametro == null) {
				filterPar.setParKey(RECEIPT_PARAMETER_KEY);
				ff.setFilter(filterPar);
				listaRis = daoParametri.search(ff).getData();
				if (!ListUtil.isEmpty(listaRis)) {
					parametro = listaRis.get(0);
				}
			}
			if (parametro != null && StringUtils.isNotEmpty(parametro.getStrValue())) {
				if (parametro.getStrValue().equals("S")) {
					return TipoRicevuta.SINTETICA.getValue();
				}
				if (parametro.getStrValue().equals("B")) {
					return TipoRicevuta.BREVE.getValue();
				}
				if (parametro.getStrValue().equals("C")) {
					return TipoRicevuta.COMPLETA.getValue();
				}
			}
		} catch (Exception e) {
			log.error("Impossibile ricavare il valore della ricevuta: ", e);
			throw new AurigaMailBusinessException("Impossibile ricavare il valore della ricevuta: ", e);
		}
		return TipoRicevuta.COMPLETA.getValue();
	}

	/**
	 * Invio mail in input, senza salvare il file eml creato nello storage
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	private SendInfoBean sendMail(SenderBean bean) throws Exception {
		return sendMail(bean, false);
	}

	private List<SenderBean> processaMail(List<SenderBean> toSend, Properties propertiesAccount, String idAccount, JavaMailSenderImpl mailSender,
			RegistrazioneProtocollo regProt, TEmailMgoBean mailPartenza, Session session) throws Exception {
		
		log.debug("processaMail - start");
		
		List<String> idEmails = new ArrayList<String>();
		MimeMessage message = null;
		if (!toSend.isEmpty()) {
			storage = new StorageCenter();
			String idMailBox = MailboxUtil.getIdMailboxInSession(idAccount, session);
			log.debug("idMailbox: "+ idMailBox);
			StorageService lStorageService = storage.getGlobalStorage(idMailBox);
			log.debug("processaMail: "+ lStorageService.getConfigurazioniStorage());
			for (SenderBean sb : toSend) {

				message = createMimeMessage(mailSender, sb, null);
				// salvo in database l'URI
				String idMail = sb.getIdEmail();
				if (idMail == null) {
					try {
						String uri = lStorageService.writeTo(message);
						log.debug("processaMail - uri "+ uri);
						Long mailsize = lStorageService.getRealFile(uri).length();
						EmailBean mail = SendUtils.convertSendBeanToEmailBean(sb, sb.getMessageId(), idAccount, uri, mailsize, regProt, mailPartenza, false,
								idMailBox, session);
						processor = new MailProcessorService();
						mail = processor.saveInSession(mail, session);
						idEmails.add(mail.getMail().getIdEmail());
						sb.setIdEmail(mail.getMail().getIdEmail());
						sb.setUriSavedMimeMessage(uri);
					} catch (Exception e) {
						log.error("ProcessaMail - impossibile salvare la mail spedita", e);
						if (log.isDebugEnabled()) {
							log.debug(String.format("Mailbox di riferimento %1$s", idMailBox));
						}
						throw new AurigaMailBusinessException("Impossibile salvare la mail spedita", e);
					}
				} else {
					idEmails.add(idMail);
				}
			}
		}
		log.debug("processaMail - end");
		return toSend;
	}

	// private List<String> processaMail(SenderBean bean, SendInfoBean info)
	// throws AurigaMailBusinessException {
	// return processaMail(bean, info, null, null);
	// }
	//
	// private List<String> processaMail(SenderBean bean, SendInfoBean info,
	// RegistrazioneProtocollo regProt)
	// throws AurigaMailBusinessException {
	// return processaMail(bean, info, regProt, null);
	// }

	/**
	 * fa una copia clone del bean di spedizione (solo di alcuni campi), cambiando i destinatari e gestendo gli attach
	 * 
	 * Se attivaFrazionamentoAttach restituisce più SenderBean per quante mail devono essere mandate rimanendo sotto soglia
	 * 
	 * @param bean
	 * @param messageId
	 * @param addresses
	 * @param sent
	 * @param attivaFrazionamentoAttach
	 * @param limitAttachSizeDBMega
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<SenderBean> creaNuovoSenderBean(SenderBean bean, String messageId, List<String> addresses, boolean sent, boolean attivaFrazionamentoAttach, double limitAttachSizeDBMega) throws Exception {
		// SenderBean newSenderBean = SerializationUtils.clone(bean); soluzione
		// troppo dispendiosa (soprattutto in caso di allegati)!
		SenderBean newSenderBean = (SenderBean) BeanUtils.cloneBean(bean);
		ArrayList<SenderBean> resultSenderBean = new ArrayList<SenderBean>();
		List<String> newAddressBcc = null;
		List<String> newAddressCc = null;
		List<String> newAddressTo = null;
		if (addresses != null) {
			newAddressTo = new ArrayList<String>(addresses);
			newAddressBcc = new ArrayList<String>();
			newAddressCc = new ArrayList<String>();
		} else {
			newAddressTo = SerializationUtils.clone(ArrayList.class.cast(bean.getAddressTo()));
			newAddressBcc = SerializationUtils.clone(ArrayList.class.cast(bean.getAddressBcc()));
			newAddressCc = SerializationUtils.clone(ArrayList.class.cast(bean.getAddressCc()));
		}
		newSenderBean.setAddressTo(newAddressTo);
		newSenderBean.setAddressBcc(newAddressBcc);
		newSenderBean.setAddressCc(newAddressCc);

		newSenderBean.setIsSent(sent);
		newSenderBean.setMessageId(messageId);
		
		if (newSenderBean.getAttachments() != null &&
				newSenderBean.getAttachments().size() >0 && 
				attivaFrazionamentoAttach) {
			int numeroTotaleDestinatari =  (newSenderBean.getAddressTo() == null ? null : newSenderBean.getAddressTo().size()) + 
					(newSenderBean.getAddressCc() == null ? 0 : newSenderBean.getAddressCc().size())
					+ (newSenderBean.getAddressBcc() == null ? 0 : newSenderBean.getAddressBcc().size());
		
			double dimensioneTotaleAttachments = 0;
			SenderBean senderBeanTmp = (SenderBean) BeanUtils.cloneBean(newSenderBean);
			senderBeanTmp.setMessageId(null);
			senderBeanTmp.setIsSent(Boolean.FALSE);
			senderBeanTmp.setAttachments(new ArrayList<SenderAttachmentsBean>());
			// aggiungo attach finchè posso, poi creo un nuovo senderbean
			List<SenderAttachmentsBean> tmpAttachments = newSenderBean.getAttachments();
			for (SenderAttachmentsBean attachment : tmpAttachments) {
				if(attachment.getFile() != null) {
					dimensioneTotaleAttachments += attachment.getFile().length(); 
				} else if(attachment.getContent() != null){
					dimensioneTotaleAttachments += attachment.getContent().length;
				}
				if (dimensioneTotaleAttachments < limitAttachSizeDBMega) {
					senderBeanTmp.getAttachments().add(attachment);
				} else {
					resultSenderBean.add(senderBeanTmp);
					dimensioneTotaleAttachments = 0;					
					if(attachment.getFile() != null) {
						dimensioneTotaleAttachments += attachment.getFile().length();
					} else if(attachment.getContent() != null){
						dimensioneTotaleAttachments += attachment.getContent().length;
					}
					senderBeanTmp = (SenderBean) BeanUtils.cloneBean(newSenderBean);
					senderBeanTmp.setMessageId(null);
					senderBeanTmp.setIsSent(Boolean.FALSE);
					senderBeanTmp.setAttachments(new ArrayList<SenderAttachmentsBean>());
					senderBeanTmp.getAttachments().add(attachment);
				}					
			}
			resultSenderBean.add(senderBeanTmp);
			
			// aggiusto gli oggetti delle mail se ho più mail da inviare per il frazionamento
			if (resultSenderBean.size() > 1) {
				if (numeroTotaleDestinatari != 1) {
					throw new AttachLimitExceedException("Impossibile spedire la mail: il numero dei destinatari ["+numeroTotaleDestinatari+"] è diverso da 1" , null, limitAttachSizeDBMega, null);
				} 
				int totMailFraz = 1;
				for (SenderBean senderBeanTemp : resultSenderBean) {
					senderBeanTemp.setSubject(senderBeanTemp.getSubject() + " MESSAGGIO INVIATO PER PARTI - PARTE "+ totMailFraz++ + " di " + resultSenderBean.size());
				}
			} 
		} else {
			resultSenderBean.add(newSenderBean);
		}

		return resultSenderBean;
	}

	/**
	 * Metodo che controlla gli indirizzi mail dei destinatari:
	 * <li>Verifica che gli indirizzi mail siano validi
	 * <li>Verifica che almeno un indirizzo sia valorizzato
	 * <li>Verifica che il numero di indirizzi non superi il valore del parametro MAX_NUM_DESTINATARI_INVIO_EMAIL in T_PARAMETERS, altrimenti lancia
	 * TooManyAdressesException
	 * 
	 * @param bean
	 * @return
	 * @throws AurigaMailBusinessException
	 * @throws TooManyAdressesException
	 * @throws NoAdressesException
	 */

	private AddressBean verificaDestinatariEPulisciDoppioni(SenderBean bean) throws AurigaMailBusinessException, TooManyAdressesException, NoAdressesException {
		return verificaDestinatariEPulisciDoppioni(bean, true);
	}

	/**
	 * Metodo che controlla gli indirizzi mail dei destinatari:
	 * <li>Verifica che almeno un indirizzo sia valorizzato, se il paramatro {@link destinatarioObbligatorio} è TRUE, viceversa è impostato a FALSE se
	 * l'indirizzo principale non è obbligatorio, come nel caso delle bozze
	 * <li>Verifica che gli indirizzi mail siano validi
	 * <li>Verifica che il numero di indirizzi non superi il valore del parametro MAX_NUM_DESTINATARI_INVIO_EMAIL in T_PARAMETERS, altrimenti lancia
	 * TooManyAdressesException
	 * 
	 * @param senderBean
	 * @param destinatarioObbligatorio
	 * @return
	 * @throws AurigaMailBusinessException
	 * @throws TooManyAdressesException
	 * @throws NoAdressesException
	 */
	private AddressBean verificaDestinatariEPulisciDoppioni(SenderBean senderBean, Boolean destinatarioObbligatorio)
			throws AurigaMailBusinessException, TooManyAdressesException, NoAdressesException {

		Boolean abilitaInvioMittente = false;
		try {
			DebugInvioBean lDebugInvioBean = SpringAppContext.getContext().getBean(DebugInvioBean.class);
			abilitaInvioMittente = lDebugInvioBean.isAbilitaInvioMittente();
		} catch (NoSuchBeanDefinitionException exc) {
			log.warn("DebugInvioBean non configurato");
		}

		// elimino i doppioni
		ripulisciDoppioniDestinatari(senderBean, abilitaInvioMittente);

		// verifico la validità della lista finale
		AddressBean addressbean = new AddressBean();
		try {
			if (senderBean.getAddressTo() != null && !senderBean.getAddressTo().isEmpty()) {
				for (String address : senderBean.getAddressTo()) {
					new InternetAddress(address);
					addressbean.getAddressto().add(address);
				}
			}
			// Cc
			if (senderBean.getAddressCc() != null && !senderBean.getAddressCc().isEmpty()) {
				for (String address : senderBean.getAddressCc()) {
					new InternetAddress(address);
					addressbean.getAddresscc().add(address);
				}
			}
			// Bcc
			if (senderBean.getAddressBcc() != null && !senderBean.getAddressBcc().isEmpty()) {
				for (String address : senderBean.getAddressBcc()) {
					new InternetAddress(address);
					addressbean.getAddressbcc().add(address);
				}
			}
		} catch (Exception e) {
			log.error("Errore nella verifica dei destinatari", e);
			throw new AurigaMailBusinessException("Indirizzo non conforme", e);
		}

		List<String> indirizziAll = new ArrayList<String>();

		if (addressbean.getAddressto() != null) {
			indirizziAll.addAll(addressbean.getAddressto());
		}
		if (addressbean.getAddresscc() != null) {
			indirizziAll.addAll(addressbean.getAddresscc());
		}
		if (addressbean.getAddressbcc() != null) {
			indirizziAll.addAll(addressbean.getAddressbcc());
		}

		if ((senderBean.getIsPec() != null && senderBean.getIsPec() && (addressbean.getAddressto() == null || addressbean.getAddressto().isEmpty()))) {
			String error = ERRORE_NESSUN_DESTINATARIO_PEC;
			if (!abilitaInvioMittente) {
				error = error.concat(" ").concat(AVVERTIMENTO_INVIO_MITTENTE);
			}
			throw new NoAdressesException(error);
		}
		if (indirizziAll.isEmpty() && destinatarioObbligatorio) {
			String error = ERRORE_NESSUN_DESTINATARIO;
			if (!abilitaInvioMittente) {
				error = AVVERTIMENTO_INVIO_MITTENTE;
			}
			throw new NoAdressesException(error);
		}

		// sono già stati rimossi i duplicati
		addressbean.setAlladdress(indirizziAll);
		// modifica jravagnan 14 gennaio 2016: inserimento nuovo parametro di
		// controllo
		// sul numero massimo di indirizzi utilizzabili in una email
		int numeroMassimoIndirizziEmail = getNumeroMassimoDestinatari();
		if (numeroMassimoIndirizziEmail != -1) {
			int numeroIndirizziEffettivi = addressbean.getAddressSize();
			if (numeroIndirizziEffettivi > numeroMassimoIndirizziEmail) {
				log.error("Numero di indirizzi (" + numeroIndirizziEffettivi + ") superiore a quello previsto (" + numeroMassimoIndirizziEmail + ")");
				throw new TooManyAdressesException(
						"Numero di indirizzi (" + numeroIndirizziEffettivi + ") superiore a quello previsto (" + numeroMassimoIndirizziEmail + ")");
			}
		}
		return addressbean;
	}

	/**
	 * verifico se devo ricavarmi il valore del massimo numero o meno
	 * 
	 * @return
	 * @throws AurigaMailBusinessException
	 */
	private int getNumeroMassimoDestinatari() throws AurigaMailBusinessException {
		if (maxNumDestinatari == null) {
			int maxDest = SendUtils.verifyMaxNumAddress();
			maxNumDestinatari = maxDest;
		}
		return maxNumDestinatari;
	}

	/**
	 * metodo per creare il mimeMessage da inviare o salvare
	 * 
	 * @param mailsender
	 * @param senderBean
	 * @return
	 * @throws AurigaMailBusinessException
	 */
	public MimeMessage createMimeMessage(JavaMailSenderImpl mailsender, SenderBean senderBean, String tipoRicevuta) throws AurigaMailBusinessException {
		// Creo il messaggio
		MimeMessage message = mailsender.createMimeMessage();
		// VERIFICO CHE BODY e SUBJECT NON SIANO NULLI
		if (senderBean.getSubject() == null) {
			senderBean.setSubject("");
		}
		if (senderBean.getBody() == null) {
			senderBean.setBody("");
		}
		// Message Multipart
		MimeMessageHelper helper;
		try {
			if (senderBean.getIsPec() != null && senderBean.getIsPec()) {
				if (!StringUtils.isEmpty(tipoRicevuta))
					message.setHeader("X-TipoRicevuta", tipoRicevuta);
				else
					message.setHeader("X-TipoRicevuta", TipoRicevuta.COMPLETA.getValue());
			}
			// se desidero la ricevuta di ritorno la inserisco
			if (senderBean.getReturnReceipt() != null && senderBean.getReturnReceipt()) {
				message.setHeader("Return-Receipt-To", senderBean.getAddressFrom());
				message.setHeader("Disposition-Notification-To", senderBean.getAddressFrom());
			}
			helper = new MimeMessageHelper(message, true, "UTF-8");
			
			
			// Setto il Reply-To  
			if (StringUtils.isNotBlank(senderBean.getReplyTo())) {
				helper.setReplyTo(senderBean.getReplyTo());
			} 
			
			// Setto l'indirizzo from
			if (StringUtils.isNotBlank(senderBean.getAliasAddressFrom())) {
				try {
					helper.setFrom(senderBean.getAddressFrom(), senderBean.getAliasAddressFrom());
				} catch (UnsupportedEncodingException e) {
					log.error("Impossibile impostare l'alias " + senderBean.getAliasAddressFrom() + " per il mittente " + senderBean.getAddressFrom(), e);
					helper.setFrom(senderBean.getAddressFrom());
				}
			} else {
				helper.setFrom(senderBean.getAddressFrom());
			}
			// Setto il subject
			helper.setSubject(senderBean.getSubject());
			// Data di invio
			helper.setSentDate(new Date());
			// Setto il corpo della mail
			boolean ishtml = false;
			if (senderBean.getIsHtml() != null) {
				ishtml = senderBean.getIsHtml();
			}
			helper.setText(senderBean.getBody(), ishtml);
			// Setto gli attachments
			if (senderBean.getAttachments() != null && !senderBean.getAttachments().isEmpty()) {
				for (SenderAttachmentsBean attachment : senderBean.getAttachments()) {
					if(attachment.getFile() != null) {
						helper.addAttachment(attachment.getFilename(), attachment.getFile());		
					} else if(attachment.getContent() != null) {
						helper.addAttachment(attachment.getFilename(), new ByteArrayResource(attachment.getContent()));
					}
				}
			}
			List<InternetAddress> internetAddressTo = new ArrayList<InternetAddress>();

			List<InternetAddress> internetAddressCc = new ArrayList<InternetAddress>();

			List<InternetAddress> internetAddressBcc = new ArrayList<InternetAddress>();

			if (senderBean.getAddressTo() != null) {
				for (String addressto : senderBean.getAddressTo()) {
					internetAddressTo.add(new InternetAddress(addressto));
				}
			}
			if (senderBean.getAddressCc() != null) {
				for (String addresscc : senderBean.getAddressCc()) {
					internetAddressCc.add(new InternetAddress(addresscc));
				}
			}
			if (senderBean.getAddressBcc() != null) {
				for (String addressbcc : senderBean.getAddressBcc()) {
					internetAddressBcc.add(new InternetAddress(addressbcc));
				}
			}
			message.setRecipients(RecipientType.TO, internetAddressTo.toArray(new InternetAddress[0]));
			message.setRecipients(RecipientType.CC, internetAddressCc.toArray(new InternetAddress[0]));
			message.setRecipients(RecipientType.BCC, internetAddressBcc.toArray(new InternetAddress[0]));
		} catch (MessagingException e) {
			log.error("Impossibile creare il messaggio", e);
			throw new AurigaMailBusinessException("Impossibile creare il messaggio", e);
		}
		return message;
	}

	/**
	 * Metodo che elimina i destinatari doppi, se sono già presenti nella lista dei destinatari principali o in copia<br>
	 * Elimino anche il mittente se già presente, in modo da evitare di ricevere nel sistema una mail che ho già inviato. Questo è configurabile tramite il
	 * parametro {@link enableYourSelf}<br>
	 * Inoltre rende tutti gli indirizzi minuscoli
	 * 
	 * @param senderBean
	 */

	private void ripulisciDoppioniDestinatari(SenderBean senderBean, Boolean abilitaInvioMittente) {

		// creando una HashSet dalla lista elimino i duplicati per lo stesso
		// tipo di destinatario, elimino anche l'indirizzo stesso dalla lista
		// rendo minuscoli tutti gli indirizzi, si posso considerare
		// case-insensitive

		if (senderBean != null && senderBean.getAddressTo() != null && !senderBean.getAddressTo().isEmpty()) {
			List<String> newTo = new ArrayList<String>();
			for (String addressTo : senderBean.getAddressTo()) {
				if (!newTo.contains(addressTo.toLowerCase())) {
					if (senderBean.getAddressFrom() != null && addressTo.equalsIgnoreCase(senderBean.getAddressFrom()) && !abilitaInvioMittente) {
						if (log.isInfoEnabled()) {
							log.info("Escludo mittente dalla lista dei destinatari principali");
						}
					} else {
						// in questo modo sono sicuro di non inserire duplicati
						newTo.add(addressTo.toLowerCase());
					}
				} else {
					if (log.isInfoEnabled()) {
						log.info("Escludo " + addressTo + "  - è già incluso nella lista dei destinatari");
					}
				}
			}
			senderBean.setAddressTo(newTo);
		}
		if (senderBean != null && senderBean.getAddressCc() != null && !senderBean.getAddressCc().isEmpty()) {
			List<String> newCc = new ArrayList<String>();
			for (String adressCC : senderBean.getAddressCc()) {
				if (!newCc.contains(adressCC.toLowerCase())
						&& (senderBean.getAddressTo() == null || !senderBean.getAddressTo().contains(adressCC.toLowerCase()))) {
					if (senderBean.getAddressFrom() != null && adressCC.equalsIgnoreCase(senderBean.getAddressFrom()) && !abilitaInvioMittente) {
						log.info("Escludo mittente dalla lista dei destinatari in copia");
					} else {
						newCc.add(adressCC.toLowerCase());
					}
				} else {
					if (log.isInfoEnabled()) {
						log.info("Escludo " + adressCC + "  - è già incluso nella lista dei destinatari");
					}
				}
			}
			senderBean.setAddressCc(newCc);
		}
		if (senderBean != null && senderBean.getAddressBcc() != null && !senderBean.getAddressBcc().isEmpty()) {
			List<String> newBcc = new ArrayList<String>();
			for (String adressBCC : senderBean.getAddressBcc()) {
				if ((senderBean.getAddressTo() == null || !senderBean.getAddressTo().contains(adressBCC.toLowerCase()))
						&& (senderBean.getAddressCc() == null || !senderBean.getAddressCc().contains(adressBCC.toLowerCase()))
						&& !newBcc.contains(adressBCC.toLowerCase())) {
					if (senderBean.getAddressFrom() != null && adressBCC.equalsIgnoreCase(senderBean.getAddressFrom()) && !abilitaInvioMittente) {
						log.info("Escludo mittente dalla lista dei destinatari in copia nascosta");
					} else {
						newBcc.add(adressBCC.toLowerCase());
					}
				} else {
					if (log.isInfoEnabled()) {
						log.info("Escludo " + adressBCC + "  - è già incluso nella lista dei destinatari");
					}
				}
			}
			senderBean.setAddressBcc(newBcc);
		}
	}

	/**
	 * Metodo che effettua le modifiche necessarie in database per consolidare lo stato della bozza e permetterne l'invio
	 * 
	 * @param idEmail
	 *            id della mail in bozza da consolidare
	 * @param throwsException
	 *            indica se lanciare un'eccezione nel caso in cui la bozza non sia da consolidare, ad esempio se è già stato tentato un'invio
	 * @param session
	 *            sessione Hibernate
	 * @throws Exception
	 */

	private void consolidaBozzaPerInvioInSession(String idEmail, Boolean throwsException, Session session) throws Exception {

		try {
			// recupero la mail con id in input
			DaoTEmailMgo daoMail = (DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class);
			TEmailMgo mail = daoMail.getTEmailMgoInSession(idEmail, session);
			if (mail.getStatoConsolidamento() != null && !mail.getStatoConsolidamento().equalsIgnoreCase(StatoConsolidamentoEmail.IN_BOZZA.getValue())) {
				throw new AurigaMailBusinessException("Impossibile aggiornare lo stato di consolidamento della bozza avente id: " + idEmail
						+ " - Lo stato di consolidamento è diverso da quello previsto " + StatoConsolidamentoEmail.IN_BOZZA.name());
			}
			// cambio lo stato di consolidamento della mail, se diverso da in
			// bozza
			mail.setStatoConsolidamento(StatoConsolidamentoEmail.IN_INVIO.getValue());
			daoMail.updateInSession(mail, session);
			session.flush();
			// lista delle mail da spostare
			List<String> emailDaSpostare = new ArrayList<String>();
			emailDaSpostare.add(idEmail);
			processor = new MailProcessorService();
			SpostaEmailBean spostaBean = new SpostaEmailBean();
			spostaBean.setIdEmails(emailDaSpostare);
			// sposto la bozza dalla folder di invio a quella di uscita
			spostaBean.setClassificazioneFrom(FolderEmail.STANDARD_BOZZE.getValue());
			List<String> listaFolder = new ArrayList<String>();
			listaFolder.add(FolderEmail.STANDARD_FOLDER_USCITA.getValue());
			spostaBean.setFolderArrivo(listaFolder);
			// se la cartella non è quella corretta allora non viene effettuato
			// alcun spostamento
			EmailGroupBean out = processor.spostaEmailFolderMultipleInSession(spostaBean, session);
			// id della mail non spostate
			if (out.getIdEmails() != null && out.getIdEmails().size() > 0) {
				for (String id : out.getIdEmails()) {
					throw new AurigaMailBusinessException("Impossibile spostare la seguente bozza nella folder di uscita: " + id);
				}
			}
		} catch (Exception e) {
			log.debug(e.getLocalizedMessage(), e);
			if (throwsException) {
				throw e;
			}
		}

	}

	/**
	 * Metodo che effettua le modifiche necessarie in database per consolidare lo stato della bozza e permetterne l'invio
	 * 
	 * @param idEmail
	 *            id della mail in bozza da consolidare
	 * @param throwsException
	 *            indica se lanciare un'eccezione nel caso in cui la bozza non sia da consolidare, ad esempio se è già stato tentato un'invio
	 * @throws AurigaMailBusinessException
	 */

	private void consolidaBozzaPerInvio(String idEmail, Boolean throwsException) throws AurigaMailBusinessException {

		Session session = null;
		try {
			session = HibernateUtil.begin();
			// avvio transazione
			Transaction transaction = session.beginTransaction();
			consolidaBozzaPerInvioInSession(idEmail, throwsException, session);
			transaction.commit();
		} catch (Exception e) {
			log.debug(e.getLocalizedMessage(), e);
			if (throwsException) {
				throw new AurigaMailBusinessException("Impossibile consolidare lo stato della bozza avente id per : " + idEmail, e);
			}
		} finally {
			try {
				HibernateUtil.release(session);
			} catch (Exception e) {
				log.error("Errore nel rilascio della sessione Hibernate post consolidamento dello stato della bozza avente id: " + idEmail, e);
				throw new AurigaMailBusinessException(
						"Errore nel rilascio della sessione Hibernate post consolidamento dello stato della bozza avente id: " + idEmail, e);
			}
		}

	}

	/**
	 * Metodo che ricava le relazioni di inoltro e risposta della mail in input e valorizza le proprietà del bean
	 * 
	 * @param mailXmlBean
	 *            bean da aggiornare
	 * @param idMail
	 *            id della bozza
	 * @throws AurigaMailBusinessException
	 */

	private void populateRispostaInoltroBozza(InvioMailXmlBean mailXmlBean, String idMail) throws AurigaMailBusinessException {

		try {

			DaoTRelEmailMgo daoTRel = (DaoTRelEmailMgo) DaoFactory.getDao(DaoTRelEmailMgo.class);
			TFilterFetch<TRelEmailMgoBean> filterfetch = new TFilterFetch<TRelEmailMgoBean>();
			TRelEmailMgoBean filter = new TRelEmailMgoBean();
			// nel caso di relazioni per inoltro e risposta l'id della mail
			// della mail è salvato in idEmail1
			filter.setIdEmail1(idMail);
			TValDizionarioBean dizCategoria = SendUtils.ricavaDizionarioDaValore(Dizionario.CAUSALE);
			TValDizionarioBean dizRuolo = SendUtils.ricavaDizionarioDaValore(Dizionario.INOLTRO);
			filter.setIdRecDizCategRel(dizCategoria.getIdRecDiz());
			filter.setIdRecDizRuolo1Vs2(dizRuolo.getIdRecDiz());
			filterfetch.setFilter(filter);
			// ricavo tutte le relazioni di inoltro
			List<TRelEmailMgoBean> listaRelazioniInoltro = daoTRel.search(filterfetch).getData();

			if (listaRelazioniInoltro != null && !listaRelazioniInoltro.isEmpty()) {
				List<IdMailInoltrataMailXmlBean> listaIdEmailInoltrate = new ArrayList<IdMailInoltrataMailXmlBean>();
				mailXmlBean.setEmailPredTipoRel(RispostaInoltro.INOLTRO.getValue());
				for (TRelEmailMgoBean relEmailMgoBean : listaRelazioniInoltro) {
					IdMailInoltrataMailXmlBean idMailInoltro = new IdMailInoltrataMailXmlBean();
					idMailInoltro.setIdMailInoltrata(relEmailMgoBean.getIdEmail2());
					listaIdEmailInoltrate.add(idMailInoltro);
				}
				if (listaIdEmailInoltrate.size() == 1) {
					mailXmlBean.setEmailPredIdEmail(listaIdEmailInoltrate.get(0).getIdMailInoltrata());
				} else {
					mailXmlBean.setListaIdEmailInoltrate(listaIdEmailInoltrate);
				}
			}

			filterfetch = new TFilterFetch<TRelEmailMgoBean>();
			filter = new TRelEmailMgoBean();
			// nel caso di relazioni per inoltro e risposta l'id della mail
			// della mail è salvato in idEmail1
			filter.setIdEmail1(idMail);
			dizRuolo = SendUtils.ricavaDizionarioDaValore(Dizionario.RISPOSTA);
			filter.setIdRecDizCategRel(dizCategoria.getIdRecDiz());
			filter.setIdRecDizRuolo1Vs2(dizRuolo.getIdRecDiz());
			filterfetch.setFilter(filter);
			// ricavo tutte le relazioni di risposta, può essere solo una
			List<TRelEmailMgoBean> listaRelazioniRisposta = daoTRel.search(filterfetch).getData();
			if (listaRelazioniRisposta != null && !listaRelazioniRisposta.isEmpty()) {
				mailXmlBean.setEmailPredTipoRel(RispostaInoltro.RISPOSTA.getValue());
				mailXmlBean.setEmailPredIdEmail(listaRelazioniRisposta.get(0).getIdEmail2());
			}

		} catch (Exception e) {
			log.debug(e.getLocalizedMessage(), e);
			throw new AurigaMailBusinessException("Errore nel recupero delle relazioni di inoltro e risposta della bozza", e);
		}

	}

	public MailProcessorService getProcessor() {
		return processor;
	}

	public void setProcessor(MailProcessorService processor) {
		this.processor = processor;
	}

	public StorageCenter getStorage() {
		return storage;
	}

	public void setStorage(StorageCenter storage) {
		this.storage = storage;
	}
	
	
	////////////////////// VERIFICA DIMENSIONE //////////////////////////////
	
	/**
	 * Verifica se la dimensione totale degli allegati in input superano il limite impostato in database per l'account specificato
	 * 
	 * @param idAccount
	 * @param numeroDestinatari
	 * @param attachments
	 * @throws Exception
	 */
	private Integer checkLimiteAttachments(String idAccount, Integer numeroDestinatariIniziali, SenderBean senderBean, Integer maxAddress, String azione) throws Exception {
		
		Integer newMaxAddress = 0;
		// 1)Se la mail NON ha allegati oppure sono in salvataggio NON si fa nessun controllo
		if (senderBean.getAttachments() != null && senderBean.getAttachments().size() > 0) {
			
			BigDecimal limiteAttachSizeDB = getAttachmentMaxSizeDB(idAccount);
			Double limiteAttachSizeDBDouble = limiteAttachSizeDB != null ? limiteAttachSizeDB.doubleValue() : 0;
			double dimensioneTotaleAttachments = 0;
			for (SenderAttachmentsBean attach : senderBean.getAttachments()) {
				if (attach.getFile() != null) {
					dimensioneTotaleAttachments += attach.getFile().length();
				} else if(attach.getContent() != null){
					dimensioneTotaleAttachments += attach.getContent().length;
				}
			}
			double limitAttachSizeDBMega = limiteAttachSizeDBDouble * BYTES_IN_MEGA;
			// 2) Se la mail ha allegati si vede se la somma della dimensione allegati > ATTACHMENT_MAX_SIZE 
			if (dimensioneTotaleAttachments > limitAttachSizeDBMega) {
				throw new AttachLimitExceedException(limiteAttachSizeDBDouble, azione);
			} else {		
				// 3) ATTACHMENT_CHECK_TYPE = relativo
				String attachmentCheckType = getAttachmentCheckTypeDB(idAccount);
				if (attachmentCheckType != null && attachmentCheckType.equalsIgnoreCase("relativo")) {
					// si calcola trunc (ATTACHMENT_MAX_SIZE / dim_totale_allegati) =  max nro destinatari x mail
					double maxNroDestXMail =  (limiteAttachSizeDBDouble / dimensioneTotaleAttachments);
					double maxNroDestXMailMega = maxNroDestXMail * BYTES_IN_MEGA;					
					// se max nro destinatari x mail < nro destinatari specificati (primari + cc) => si spezza in N mail,
					// con arrotonda(N nro destinatari specificati (primari + cc)/ max nro destinatari x mail)
					
					int numeroDestinatari = 0;
					if(numeroDestinatariIniziali != null ) {
						numeroDestinatari = numeroDestinatariIniziali;
					} else {
						int sizeTo = 0;
						if (senderBean.getAddressTo() != null && !senderBean.getAddressTo().isEmpty()) {
							sizeTo = senderBean.getAddressTo().size();
						}
						int sizeCc = 0;
						if (senderBean.getAddressCc() != null && !senderBean.getAddressCc().isEmpty()) {
							sizeCc = senderBean.getAddressCc().size();
						}
						int sizeBcc = 0;
						if (senderBean.getAddressBcc() != null && !senderBean.getAddressBcc().isEmpty()) {
							sizeBcc = senderBean.getAddressBcc().size();
						}
						numeroDestinatari = sizeTo + sizeCc + sizeBcc;
					} 
						
					int value = (int)Math.floor(maxNroDestXMailMega);
					if(maxNroDestXMailMega > 0 && maxNroDestXMailMega < numeroDestinatari) {
						newMaxAddress = value;
					}
				}
			}
		}
		return newMaxAddress;
	}
	
	private BigDecimal getAttachmentMaxSizeDB(String idAccount) throws AurigaMailBusinessException {
		
		try {
			DaoTParameters daoParametri = (DaoTParameters) DaoFactory.getDao(DaoTParameters.class);
			TParametersBean parametro = null;
			TFilterFetch<TParametersBean> ff = new TFilterFetch<TParametersBean>();
			TParametersBean filterPar = new TParametersBean();
			filterPar.setParKey(ATTACHMENT_CHECK_TYPE + "." + idAccount);
			ff.setFilter(filterPar);
			List<TParametersBean> listaRis = daoParametri.search(ff).getData();
			if (!ListUtil.isEmpty(listaRis)) {
				parametro = listaRis.get(0);
			}
			// se nullo cerco il parametro generico
			if (parametro == null) {
				filterPar.setParKey(ATTACHMENT_CHECK_TYPE);
				ff.setFilter(filterPar);
				listaRis = daoParametri.search(ff).getData();
				if (!ListUtil.isEmpty(listaRis)) {
					parametro = listaRis.get(0);
				}
			}
			parametro = null;
			TParametersBean filtro = new TParametersBean();
			filtro.setParKey(ATTACHMENT_MAX_SIZE + "." + idAccount);
			ff.setFilter(filtro);
			listaRis = daoParametri.search(ff).getData();
			if (!ListUtil.isEmpty(listaRis)) {
				parametro = listaRis.get(0);
			}
			// se nullo cerco il parametro generico
			if (parametro == null) {
				filtro.setParKey(ATTACHMENT_MAX_SIZE);
				ff.setFilter(filtro);
				listaRis = daoParametri.search(ff).getData();
				if (!ListUtil.isEmpty(listaRis)) {
					parametro = listaRis.get(0);
				}
			}
			BigDecimal valoreMassimo = new BigDecimal("0");
			if (parametro != null) {
				valoreMassimo = parametro.getNumValue();
			}

			return valoreMassimo;
			
		} catch (Exception e) {
			log.error("Impossibile ricavare il valore del parametro per il controllo attachments: ", e);
			throw new AurigaMailBusinessException("Impossibile ricavare il valore del parametro per il controllo attachments: ", e);
		}
	}
	
	private String getAttachmentCheckTypeDB(String idAccount) throws AurigaMailBusinessException {
		
		try {
			DaoTParameters daoParametri = (DaoTParameters) DaoFactory.getDao(DaoTParameters.class);
			TParametersBean parametro = null;
			TFilterFetch<TParametersBean> ff = new TFilterFetch<TParametersBean>();
			TParametersBean filterPar = new TParametersBean();
			filterPar.setParKey(ATTACHMENT_CHECK_TYPE + "." + idAccount);
			ff.setFilter(filterPar);
			List<TParametersBean> listaRis = daoParametri.search(ff).getData();
			if (!ListUtil.isEmpty(listaRis)) {
				parametro = listaRis.get(0);
			}
			// se nullo cerco il parametro generico
			if (parametro == null) {
				filterPar.setParKey(ATTACHMENT_CHECK_TYPE);
				ff.setFilter(filterPar);
				listaRis = daoParametri.search(ff).getData();
				if (!ListUtil.isEmpty(listaRis)) {
					parametro = listaRis.get(0);
				}
			}
			String valoreFinaleControllo = null;
			if (parametro != null) {
				valoreFinaleControllo = parametro.getStrValue();
			}

			return valoreFinaleControllo;
			
		} catch (Exception e) {
			log.error("Impossibile ricavare il valore del parametro per il controllo attachments: ", e);
			throw new AurigaMailBusinessException("Impossibile ricavare il valore del parametro per il controllo attachments: ", e);
		}
	}
	
	/**
	 * Verifica se la dimensione totale degli allegati in input superano il limite impostato in database per l'account specificato
	 * 
	 * @param idAccount
	 * @param numeroDestinatari
	 * @param flgInvioSeparato
	 * @param attachments
	 * @throws Exception
	 */

	private Integer checkLimiteAttachmentsInSession(String idAccount, Integer numeroDestinatariIniziali, boolean flgInvioSeparato, SenderBean senderBean,
			Integer maxAddress, Session session,String azione, boolean attivaFrazAttachMail, Double limiteAttachSizeDBDouble, double limitAttachSizeDBMega) throws Exception {
		
		log.debug("checkLimiteAttachmentsInSession start");

		
		Integer newMaxAddress = 0;
		// 1)Se la mail NON ha allegati oppure sono in salvataggio NON si fa nessun controllo
		if (senderBean.getAttachments() != null && senderBean.getAttachments().size() > 0) {
			double dimensioneTotaleAttachments = 0;
			int numeroDestinatari = 0;
			if(numeroDestinatariIniziali != null ) {
				numeroDestinatari = numeroDestinatariIniziali;
			} else {
				int sizeTo = 0;
				if (senderBean.getAddressTo() != null && !senderBean.getAddressTo().isEmpty()) {
					sizeTo = senderBean.getAddressTo().size();
				}
				int sizeCc = 0;
				if (senderBean.getAddressCc() != null && !senderBean.getAddressCc().isEmpty()) {
					sizeCc = senderBean.getAddressCc().size();
				}
				int sizeBcc = 0;
				if (senderBean.getAddressBcc() != null && !senderBean.getAddressBcc().isEmpty()) {
					sizeBcc = senderBean.getAddressBcc().size();
				}
				numeroDestinatari = sizeTo + sizeCc + sizeBcc;
			} 
			for (SenderAttachmentsBean attach : senderBean.getAttachments()) {

				
				if (attach.getFile() != null) {
					dimensioneTotaleAttachments += attach.getFile().length();
					// verifico se c'è il parametro ATTIVA_FRAZ_ATTACH_MAIL attivo controllo per ogni allegato se supera la grandezza massima
					if (attivaFrazAttachMail) {
						if (attach.getFile().length() > limitAttachSizeDBMega) {
							throw new AttachLimitExceedException("Impossibile spedire la mail: uno o più allegati della mail superano la dimensione soglia di "+limiteAttachSizeDBDouble+" Mb" , null, limiteAttachSizeDBDouble, azione);
						}
					}					
				} else if (attach.getContent() != null) {
					dimensioneTotaleAttachments += attach.getContent().length;
					// verifico se c'è il parametro ATTIVA_FRAZ_ATTACH_MAIL attivo controllo per ogni allegato se supera la grandezza massima
					if (attivaFrazAttachMail) {
						if (attach.getContent().length > limitAttachSizeDBMega) {
							throw new AttachLimitExceedException("Impossibile spedire la mail: uno o più allegati della mail superano la dimensione soglia di "+limiteAttachSizeDBDouble+" Mb" , null, limiteAttachSizeDBDouble, azione);
						}
					}					
				}
			}	
			
			// 2) Se la mail ha allegati si vede se la somma della dimensione allegati > ATTACHMENT_MAX_SIZE 
			// 		aggiunto: se ha un numero di destinatari maggiore di 1 e non ha il flaginvioSeparato
			/*
			 * value flgInvioSeparato = true && numeroDestinatari > 1: false
               value flgInvioSeparato = false && numeroDestinatari > 1: true
			 */
			if (!flgInvioSeparato && numeroDestinatari > 1) {
				if (dimensioneTotaleAttachments > limitAttachSizeDBMega) {
					if (attivaFrazAttachMail)
						throw new AttachLimitExceedException(limiteAttachSizeDBDouble, azione);
					else
						throw new AttachLimitExceedException("Impossibile spedire la mail: la dimensione complessiva degli allegati supera la soglia di " +limiteAttachSizeDBDouble+" Mb" , null, limiteAttachSizeDBDouble, azione);
				} else {		
					// 3) ATTACHMENT_CHECK_TYPE = relativo
					String attachmentCheckType = getAttachmentCheckTypeDBInSession(idAccount, session);
					if (attachmentCheckType != null && attachmentCheckType.equalsIgnoreCase("relativo")) {
						// si calcola trunc (ATTACHMENT_MAX_SIZE / dim_totale_allegati) =  max nro destinatari x mail
						double maxNroDestXMail =  (limiteAttachSizeDBDouble / dimensioneTotaleAttachments);
						double maxNroDestXMailMega = maxNroDestXMail * BYTES_IN_MEGA;					
						// se max nro destinatari x mail < nro destinatari specificati (primari + cc) => si spezza in N mail,
						// con arrotonda(N nro destinatari specificati (primari + cc)/ max nro destinatari x mail)
						int value = (int)Math.floor(maxNroDestXMailMega);
						if(maxNroDestXMailMega > 0 && maxNroDestXMailMega < numeroDestinatari) {
							newMaxAddress = value;
						}
					}
				}
			} else {
				// se ho messo il flag di invio separato lo imposto qui
				if (dimensioneTotaleAttachments > limitAttachSizeDBMega) {
					if (!attivaFrazAttachMail)
						throw new AttachLimitExceedException("Impossibile spedire la mail: la dimensione complessiva degli allegati supera la soglia di " +limiteAttachSizeDBDouble+" Mb" , null, limiteAttachSizeDBDouble, azione);
				}
				newMaxAddress = 1;
			}
				
			
		}
		
		log.debug("checkLimiteAttachmentsInSession end");
		
		return newMaxAddress;
	}
	
	private Integer numeroDestinatariSenzaAllegato(String idAccount, Integer numeroDestinatariIniziali,
			boolean flgInvioSeparato, SenderBean senderBean, Integer maxAddress, Session session) throws Exception {

		log.error("numeroDestinatariSenzaAllegato start");

		Integer newMaxAddress = 0;

		int numeroDestinatari = 0;
		if (numeroDestinatariIniziali != null) {
			numeroDestinatari = numeroDestinatariIniziali;
		} else {
			int sizeTo = 0;
			if (senderBean.getAddressTo() != null && !senderBean.getAddressTo().isEmpty()) {
				sizeTo = senderBean.getAddressTo().size();
			}
			int sizeCc = 0;
			if (senderBean.getAddressCc() != null && !senderBean.getAddressCc().isEmpty()) {
				sizeCc = senderBean.getAddressCc().size();
			}
			int sizeBcc = 0;
			if (senderBean.getAddressBcc() != null && !senderBean.getAddressBcc().isEmpty()) {
				sizeBcc = senderBean.getAddressBcc().size();
			}
			numeroDestinatari = sizeTo + sizeCc + sizeBcc;
		}
		log.error("numeroDestinatari: " + numeroDestinatari);
		log.error("flgInvioSeparato: " + flgInvioSeparato);
		/*
		 * value flgInvioSeparato = true && numeroDestinatari > 1: false value
		 * flgInvioSeparato = false && numeroDestinatari > 1: true
		 */
		if (!flgInvioSeparato && numeroDestinatari > 1) {

			newMaxAddress = 0;

		} else {

			newMaxAddress = 1;
		}

		log.error("newMaxAddress: " + newMaxAddress);

		log.error("numeroDestinatariSenzaAllegato end");

		return newMaxAddress;
	}
	
	private BigDecimal getAttachmentMaxSizeDBInSession(String idAccount, Session session) throws AurigaMailBusinessException {
		
		try {
			DaoTParameters daoParametri = (DaoTParameters) DaoFactory.getDao(DaoTParameters.class);
			TParametersBean parametro = null;
			TFilterFetch<TParametersBean> ff = new TFilterFetch<TParametersBean>();
			TParametersBean filterPar = new TParametersBean();
			filterPar.setParKey(ATTACHMENT_CHECK_TYPE + "." + idAccount);
			ff.setFilter(filterPar);
			List<TParametersBean> listaRis = daoParametri.searchInSession(ff, session).getData();
			if (!ListUtil.isEmpty(listaRis)) {
				parametro = listaRis.get(0);
			}
			// se nullo cerco il parametro generico
			if (parametro == null) {
				filterPar.setParKey(ATTACHMENT_CHECK_TYPE);
				ff.setFilter(filterPar);
				listaRis = daoParametri.searchInSession(ff, session).getData();
				if (!ListUtil.isEmpty(listaRis)) {
					parametro = listaRis.get(0);
				}
			}
			parametro = null;
			TParametersBean filtro = new TParametersBean();
			filtro.setParKey(ATTACHMENT_MAX_SIZE + "." + idAccount);
			ff.setFilter(filtro);
			listaRis = daoParametri.search(ff).getData();
			if (!ListUtil.isEmpty(listaRis)) {
				parametro = listaRis.get(0);
			}
			// se nullo cerco il parametro generico
			if (parametro == null) {
				filtro.setParKey(ATTACHMENT_MAX_SIZE);
				ff.setFilter(filtro);
				listaRis = daoParametri.search(ff).getData();
				if (!ListUtil.isEmpty(listaRis)) {
					parametro = listaRis.get(0);
				}
			}
			BigDecimal valoreMassimo = new BigDecimal("0");
			if (parametro != null) {
				valoreMassimo = parametro.getNumValue();
			}

			return valoreMassimo;
			
		} catch (Exception e) {
			log.error("Impossibile ricavare il valore del parametro per il controllo attachments: ", e);
			throw new AurigaMailBusinessException("Impossibile ricavare il valore del parametro per il controllo attachments: ", e);
		}
	}
	
	private String getAttachmentCheckTypeDBInSession(String idAccount, Session session) throws AurigaMailBusinessException {
		
		try {
			DaoTParameters daoParametri = (DaoTParameters) DaoFactory.getDao(DaoTParameters.class);
			TParametersBean parametro = null;
			TFilterFetch<TParametersBean> ff = new TFilterFetch<TParametersBean>();
			TParametersBean filterPar = new TParametersBean();
			filterPar.setParKey(ATTACHMENT_CHECK_TYPE + "." + idAccount);
			ff.setFilter(filterPar);
			List<TParametersBean> listaRis = daoParametri.searchInSession(ff, session).getData();
			if (!ListUtil.isEmpty(listaRis)) {
				parametro = listaRis.get(0);
			}
			// se nullo cerco il parametro generico
			if (parametro == null) {
				filterPar.setParKey(ATTACHMENT_CHECK_TYPE);
				ff.setFilter(filterPar);
				listaRis = daoParametri.searchInSession(ff, session).getData();
				if (!ListUtil.isEmpty(listaRis)) {
					parametro = listaRis.get(0);
				}
			}
			String valoreFinaleControllo = null;
			if (parametro != null) {
				valoreFinaleControllo = parametro.getStrValue();
			}

			return valoreFinaleControllo;
			
		} catch (Exception e) {
			log.error("Impossibile ricavare il valore del parametro per il controllo attachments: ", e);
			throw new AurigaMailBusinessException("Impossibile ricavare il valore del parametro per il controllo attachments: ", e);
		}
	}

}