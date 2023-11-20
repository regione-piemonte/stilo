/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.google.common.io.Files;

import it.eng.auriga.database.store.bean.SchemaBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailGetstatoprotemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailPreparaconfermaautomaticaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.aurigamailbusiness.bean.Categoria;
import it.eng.aurigamailbusiness.bean.Classificazione;
import it.eng.aurigamailbusiness.bean.ClassificazioneRicevuta;
import it.eng.aurigamailbusiness.bean.Dizionario;
import it.eng.aurigamailbusiness.bean.EmailAttachsBean;
import it.eng.aurigamailbusiness.bean.EmailBean;
import it.eng.aurigamailbusiness.bean.EmailGroupBean;
import it.eng.aurigamailbusiness.bean.EmailInfoBean;
import it.eng.aurigamailbusiness.bean.EmailInfoRelazioniBean;
import it.eng.aurigamailbusiness.bean.EmailSentReferenceBean;
import it.eng.aurigamailbusiness.bean.GetStatoProtocollazioneOutBean;
import it.eng.aurigamailbusiness.bean.InfoProtocolloBean;
import it.eng.aurigamailbusiness.bean.InfoRelazioneProtocolloBean;
import it.eng.aurigamailbusiness.bean.InteroperabilitaAttachmentBeanIn;
import it.eng.aurigamailbusiness.bean.InterrogazioneRelazioneEmailBean;
import it.eng.aurigamailbusiness.bean.MailAttachmentsBean;
import it.eng.aurigamailbusiness.bean.MailAttachmentsInfoBean;
import it.eng.aurigamailbusiness.bean.MailLoginBean;
import it.eng.aurigamailbusiness.bean.MailboxAccountBean;
import it.eng.aurigamailbusiness.bean.ModalitaRicevuta;
import it.eng.aurigamailbusiness.bean.NotificaInteroperabileBean;
import it.eng.aurigamailbusiness.bean.ProtocolloAttachmentBean;
import it.eng.aurigamailbusiness.bean.ProtocolloAutomaticoConfigBean;
import it.eng.aurigamailbusiness.bean.RegistrazioneProtocollo;
import it.eng.aurigamailbusiness.bean.RegistrazioneProtocolloBean;
import it.eng.aurigamailbusiness.bean.ResultBean;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.aurigamailbusiness.bean.SinteticEmailInfoBean;
import it.eng.aurigamailbusiness.bean.SinteticMailAttachmentsBean;
import it.eng.aurigamailbusiness.bean.StatoConfermaAutomaticaBean;
import it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TDestinatariEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TFolderCaselleBean;
import it.eng.aurigamailbusiness.bean.TParametersBean;
import it.eng.aurigamailbusiness.bean.TRegEstVsEmailBean;
import it.eng.aurigamailbusiness.bean.TRegProtVsEmailBean;
import it.eng.aurigamailbusiness.bean.TRelEmailFolderBean;
import it.eng.aurigamailbusiness.bean.TRelEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TRubricaEmailBean;
import it.eng.aurigamailbusiness.bean.TValDizionarioBean;
import it.eng.aurigamailbusiness.bean.TipoInteroperabilita;
import it.eng.aurigamailbusiness.bean.TipoRelazione;
import it.eng.aurigamailbusiness.bean.restrepresentation.EmailAttachment;
import it.eng.aurigamailbusiness.bean.restrepresentation.output.GetEmailAttachmentsResponse;
import it.eng.aurigamailbusiness.converters.TFolderCaselleToTFolderCaselleBean;
import it.eng.aurigamailbusiness.database.mail.TEmailMgo;
import it.eng.aurigamailbusiness.database.mail.TFolderCaselle;
import it.eng.aurigamailbusiness.database.mail.TRelEmailFolder;
import it.eng.aurigamailbusiness.database.mail.TRelEmailFolderId;
import it.eng.aurigamailbusiness.database.utility.DaoFactory;
import it.eng.aurigamailbusiness.database.utility.bean.listafolderUtente.SpostaEmailBean;
import it.eng.aurigamailbusiness.database.utility.send.InputOutput;
import it.eng.aurigamailbusiness.exception.AurigaMailBusinessException;
import it.eng.aurigamailbusiness.sender.storage.StorageCenter;
import it.eng.aurigamailbusiness.sender.storage.StorageImplementation;
import it.eng.aurigamailbusiness.structure.HeaderInfo;
import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.aurigamailbusiness.structure.XTrasporto;
import it.eng.aurigamailbusiness.utility.MailBreaker;
import it.eng.aurigamailbusiness.utility.mail.MailAutoProtocolException;
import it.eng.client.DmpkIntMgoEmailGetstatoprotemail;
import it.eng.client.DmpkIntMgoEmailPreparaconfermaautomatica;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.KeyGenerator;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.converter.UtilPopulate;
import it.eng.module.archiveUtility.ArchiveUtils;
import it.eng.module.archiveUtility.bean.DecompressioneArchiviBean;
import it.eng.utility.storageutil.StorageService;
import it.eng.spring.utility.SpringAppContext;
import it.eng.util.ListUtil;
/**
 * classe di servizio per la gestione delle email complete
 * 
 * @author jravagnan
 * 
 */
@Service(name = "MailProcessorService")
public class MailProcessorService {

	private static final String ERRORE_AGGIORNAMENTO_STATO_PROTOCOLLO = "Impossibile aggiornare lo stato di protocollo dell'email";

	private static final String ERRORE_DIZIONARIO = "Impossibile ottenere il dizionario desiderato";

	private static final String MAIL_BREAKER_BEAN = "mailBreaker";

	private static Logger log = LogManager.getLogger(MailProcessorService.class);

	private static final String CHIAVE_PARAMETRO_ARCHIVIAZIONE = "ARCHIVIAZIONE_AUTO_EMAIL_PROTOCOLLATE";

	private static final String CHIAVE_PARAMETRO_CONFERMA_PROTOCOLLAZIONE = "INVIO_CONFERMA_EMAIL_PROTOCOLLATA";

	private static final String DOT = ".";

	private static final String PREFIX_FOLDER_ARRIVO = "standard.arrivo.";

	private static final String OLD_VERSION_SEGNATURA = "old";
	
	private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

	@Operation(name = "save")
	public EmailBean save(EmailBean bean) throws Exception {
		// gestione con doppio try altrimenti in caso di errore nel salvataggio
		// della prima transazione
		// si solleverebbe un'eccezione e la sessione non verrebbe mai
		// rilasciata visto che non il finally riguardava solo il secondo
		// try-catch
		Session session = null;
		try {
			if (Thread.currentThread().isInterrupted()) {
				// thread elaborazione messaggio interrotto, interrompo
				// l'operazione corrente
				throw new InterruptedException();
			}
			session = HibernateUtil.begin();
			try {
				Transaction transaction = session.beginTransaction();
				saveInSession(bean, session);
				transaction.commit();
			} catch (Exception e) {
				log.error("Impossibile salvare la mail: ", e);
				throw e;
			}
			// modifica jravagnan 20/11/2015 elimino dalla transazione quelle
			// parti
			// che non sono essenziali
			// per il corretto salvataggio della email
			try {
				Transaction transaction = session.beginTransaction();
				saveVociRubrica(bean, session);
				transaction.commit();
			} catch (Exception e) {
				log.warn("Errore nel salvataggio dei dati in rubrica: ", e);
			}
		} finally {
			HibernateUtil.release(session);
		}
		return bean;
	}

	public EmailBean saveInSession(EmailBean bean, Session session) throws Exception {
		log.debug("MailProcessorService - Inizio saveInSession");
		if (Thread.currentThread().isInterrupted()) {
			// thread elaborazione messaggio interrotto, interrompo l'operazione
			// corrente
			throw new InterruptedException();
		}
		// SALVO LA MAIL
		saveinSessionMail(bean, session);
		// SALVO LA RELAZIONE EMAIL FOLDER
		saveInSessionRelFolder(bean, session);
		// SALVO GLI ATTACHMENT
		saveInSessionAttachments(bean, session);
		// SALVO LE RELAZIONI CON LE ALTRE MAIL
		saveInSessionRelMail(bean, session);
		// SALVO I DESTINATARI
		saveInSessionRecipients(bean, session);
		// SALVO INFORMAZIONI PROTOCOLLO
		saveInSessionMailProtocol(bean, session);
		// SALVO INFO RICEVUTA
		saveInSessionReceipt(bean, session);
		// SALVO INFO INTEROPERABILITA
		saveInSessionInterop(bean, session);
		log.debug("MailProcessorService - Fine saveInSession");
		return bean;
	}

	/**
	 * @param bean
	 * @param session
	 * @throws InterruptedException
	 * @throws Exception
	 */
	private void saveInSessionInterop(EmailBean bean, Session session) throws InterruptedException, Exception {

		if (bean.getInterOp() != null) {
			if (!ListUtil.isEmpty(bean.getInterOp().getMailAgganciate())) {
				log.debug("Aggiorno le informazioni delle mail interoperabili agganciate: " + bean.getInterOp().getMailAgganciate());
				for (TEmailMgoBean mail : bean.getInterOp().getMailAgganciate()) {
					if (Thread.currentThread().isInterrupted()) {
						// thread elaborazione messaggio interrotto, interrompo
						// l'operazione corrente
						throw new InterruptedException();
					}
					((DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class)).updateInSession(mail, session);
				}
				log.debug("Aggiornate le informazioni delle mail interoperabili agganciate");
			}
			if (!ListUtil.isEmpty(bean.getInterOp().getRelazioniEsterneToInsert())) {
				log.debug("Inserisco le relazioni esterne delle mail interoperabili: " + bean.getInterOp().getRelazioniEsterneToInsert());
				for (TRegEstVsEmailBean reg : bean.getInterOp().getRelazioniEsterneToInsert()) {
					if (Thread.currentThread().isInterrupted()) {
						// thread elaborazione messaggio interrotto, interrompo
						// l'operazione corrente
						throw new InterruptedException();
					}
					((DaoTRegEstVsEmail) DaoFactory.getDao(DaoTRegEstVsEmail.class)).saveInSession(reg, session);
				}
				log.debug("Inserite le relazioni esterne delle mail interoperabili");
			}
			if (!ListUtil.isEmpty(bean.getInterOp().getRelazioniEsterneToUpdate())) {
				log.debug("Aggiorno le relazioni esterne della mail interoperabili: " + bean.getInterOp().getRelazioniEsterneToUpdate());
				for (TRegEstVsEmailBean reg : bean.getInterOp().getRelazioniEsterneToUpdate()) {
					if (Thread.currentThread().isInterrupted()) {
						// thread elaborazione messaggio interrotto, interrompo
						// l'operazione corrente
						throw new InterruptedException();
					}
					((DaoTRegEstVsEmail) DaoFactory.getDao(DaoTRegEstVsEmail.class)).updateInSession(reg, session);
				}
				log.debug("Aggiornate le relazioni esterne della mail interoperabili");
			}
			if (!ListUtil.isEmpty(bean.getInterOp().getCaselleMailOriginarie())) {
				// cancellazione relazioni precedenti
				log.debug("Rimuovo le relazioni delle folder della mail interoperabile: " + bean.getInterOp().getCaselleMailOriginarie());
				for (TRelEmailFolderBean folder : bean.getInterOp().getCaselleMailOriginarie()) {
					if (Thread.currentThread().isInterrupted()) {
						// thread elaborazione messaggio interrotto, interrompo
						// l'operazione corrente
						throw new InterruptedException();
					}
					log.debug("Rimuovo la folder della mail interoperabile: " + folder);
					((DaoTRelEmailFolder) DaoFactory.getDao(DaoTRelEmailFolder.class)).deleteInSession(folder, session);
				}
				session.flush();
				log.debug("Rimosse le relazioni delle folder della mail interoperabile");
			}
			if (!ListUtil.isEmpty(bean.getInterOp().getCaselleMailAgganciate())) {
				// inserimento nuove relazioni
				log.debug("Inserisco le nuove relazioni delle folder della mail interoperabile: " + bean.getInterOp().getCaselleMailAgganciate());
				for (TRelEmailFolderBean folder : bean.getInterOp().getCaselleMailAgganciate()) {
					if (Thread.currentThread().isInterrupted()) {
						// thread elaborazione messaggio interrotto, interrompo
						// l'operazione corrente
						throw new InterruptedException();
					}
					log.debug("Inserisco la folder della mail interoperabile: " + folder);
					((DaoTRelEmailFolder) DaoFactory.getDao(DaoTRelEmailFolder.class)).saveInSession(folder, session);
				}
				log.debug("Inserite le nuove relazioni delle folder della mail interoperabile");
			}
			log.debug("Salvate le informazioni della ricevuta interoperabile");
		}
	}

	/**
	 * @param bean
	 * @param session
	 * @throws InterruptedException
	 * @throws Exception
	 */
	private void saveInSessionReceipt(EmailBean bean, Session session) throws InterruptedException, Exception {

		if (bean.getReceipt() != null) {
			if (bean.getReceipt().getMailOriginaria() != null) {
				if (Thread.currentThread().isInterrupted()) {
					// thread elaborazione messaggio interrotto, interrompo
					// l'operazione corrente
					throw new InterruptedException();
				}
				((DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class)).updateInSession(bean.getReceipt().getMailOriginaria(), session);
			}
			if (!ListUtil.isEmpty(bean.getReceipt().getDestinatari())) {
				log.debug("Aggiorno i destinatari della mail originale: " + bean.getReceipt().getDestinatari());
				for (TDestinatariEmailMgoBean dest : bean.getReceipt().getDestinatari()) {
					if (Thread.currentThread().isInterrupted()) {
						// thread elaborazione messaggio interrotto, interrompo
						// l'operazione corrente
						throw new InterruptedException();
					}
					if (dest != null) {
						((DaoTDestinatariEmailMgo) DaoFactory.getDao(DaoTDestinatariEmailMgo.class)).updateInSession(dest, session);
					}
				}
				log.debug("Aggiornati i destinatari della mail originale");
			}
			if (!ListUtil.isEmpty(bean.getReceipt().getVociRubrica())) {
				log.debug("Aggiorno le voci rubrica della mail originale: " + bean.getReceipt().getVociRubrica());
				for (TRubricaEmailBean voce : bean.getReceipt().getVociRubrica()) {
					if (Thread.currentThread().isInterrupted()) {
						// thread elaborazione messaggio interrotto, interrompo
						// l'operazione corrente
						throw new InterruptedException();
					}
					((DaoTRubricaEmail) DaoFactory.getDao(DaoTRubricaEmail.class)).updateInSession(voce, session);
				}
				log.debug("Aggiornate le voci rubrica della mail originale");
			}
			if (!ListUtil.isEmpty(bean.getReceipt().getFolderOriginarie())) {
				DaoTRelEmailFolder daoRelMailFolder = ((DaoTRelEmailFolder) DaoFactory.getDao(DaoTRelEmailFolder.class));
				log.debug("Rimuovo le relazioni delle folder della mail originale: " + bean.getReceipt().getFolderOriginarie());
				for (TRelEmailFolderBean folderToDelete : bean.getReceipt().getFolderOriginarie()) {
					if (Thread.currentThread().isInterrupted()) {
						// thread elaborazione messaggio interrotto, interrompo
						// l'operazione corrente
						throw new InterruptedException();
					}
					daoRelMailFolder.deleteInSession(folderToDelete, session);
				}
				log.debug("Rimosse le relazioni delle folder della mail originale");
				log.debug("Inserisco le nuove relazioni delle folder della mail originale: " + bean.getReceipt().getNuoveFolder());
				for (TRelEmailFolderBean folderToInsert : bean.getReceipt().getNuoveFolder()) {
					if (Thread.currentThread().isInterrupted()) {
						// thread elaborazione messaggio interrotto, interrompo
						// l'operazione corrente
						throw new InterruptedException();
					}
					daoRelMailFolder.saveInSession(folderToInsert, session);
				}
				log.debug("Inserite le nuove relazioni delle folder della mail originale");
			}
			log.debug("Salvate le informazioni della ricevuta pec");
		}
	}

	/**
	 * @param bean
	 * @param session
	 * @throws InterruptedException
	 * @throws Exception
	 */
	private void saveInSessionMailProtocol(EmailBean bean, Session session) throws InterruptedException, Exception {

		if (bean.getRegistrazioneProtocollo() != null) {
			if (Thread.currentThread().isInterrupted()) {
				// thread elaborazione messaggio interrotto, interrompo
				// l'operazione corrente
				throw new InterruptedException();
			}
			log.debug("Salvo le informazioni sulla protocollazione della mail: " + bean.getRegistrazioneProtocollo());
			((DaoTRegProtVsEmail) DaoFactory.getDao(DaoTRegProtVsEmail.class)).saveInSession(bean.getRegistrazioneProtocollo(), session);
			log.debug("Salvate le informazioni sulla protocollazione della mail");
		}
	}

	/**
	 * @param bean
	 * @param session
	 * @throws InterruptedException
	 * @throws Exception
	 */
	private void saveInSessionRecipients(EmailBean bean, Session session) throws InterruptedException, Exception {

		if (!ListUtil.isEmpty(bean.getDestinatari())) {
			log.debug("Salvo i destinatari della mail: " + bean.getDestinatari());
			for (TDestinatariEmailMgoBean dest : bean.getDestinatari()) {
				if (Thread.currentThread().isInterrupted()) {
					// thread elaborazione messaggio interrotto, interrompo
					// l'operazione corrente
					throw new InterruptedException();
				}
				// modifica Jacopo 20/11/2015 setto il valore di idRubrica a
				// null
				// perchè tale
				// valore non serve e mi inibisce la possibilità di spostare il
				// salvataggio
				// della rubrica successivamente a causa di una FK
				dest.setIdVoceRubricaDest(null);
				((DaoTDestinatariEmailMgo) DaoFactory.getDao(DaoTDestinatariEmailMgo.class)).saveInSession(dest, session);
			}
			log.debug("Salvati i destinatari della mail");
		}
	}

	/**
	 * @param bean
	 * @param session
	 * @throws InterruptedException
	 * @throws Exception
	 */
	private void saveInSessionRelMail(EmailBean bean, Session session) throws InterruptedException, Exception {

		if (!ListUtil.isEmpty(bean.getRelMail())) {
			log.debug("Salvo le relazioni della mail: " + bean.getRelMail());
			for (TRelEmailMgoBean rel : bean.getRelMail()) {
				if (Thread.currentThread().isInterrupted()) {
					// thread elaborazione messaggio interrotto, interrompo
					// l'operazione corrente
					throw new InterruptedException();
				}
				((DaoTRelEmailMgo) DaoFactory.getDao(DaoTRelEmailMgo.class)).saveInSession(rel, session);
			}
			log.debug("Salvate le relazioni della mail");
		}
	}

	/**
	 * @param bean
	 * @param session
	 * @throws InterruptedException
	 * @throws Exception
	 */
	private void saveInSessionAttachments(EmailBean bean, Session session) throws InterruptedException, Exception {

		if (!ListUtil.isEmpty(bean.getAttachments())) {
			log.debug("Salvo gli allegati della mail: " + bean.getAttachments());
			for (TAttachEmailMgoBean attach : bean.getAttachments()) {
				if (Thread.currentThread().isInterrupted()) {
					// thread elaborazione messaggio interrotto, interrompo
					// l'operazione corrente
					throw new InterruptedException();
				}
				if (attach != null) {
					log.debug("Nome originale Attach: " + attach.getNomeOriginale());
					log.debug("Display filename Attach: " + attach.getDisplayFilename());
					log.debug("Mimetype Attach: " + attach.getMimetype());
					((DaoTAttachEmailMgo) DaoFactory.getDao(DaoTAttachEmailMgo.class)).saveInSession(attach, session);
				}
			}
			log.debug("Salvati gli allegati della mail");
		}
	}

	/**
	 * @param bean
	 * @param session
	 * @throws InterruptedException
	 * @throws Exception
	 */
	private void saveInSessionRelFolder(EmailBean bean, Session session) throws InterruptedException, Exception {

		if (!ListUtil.isEmpty(bean.getFolders())) {
			log.debug("Salvo le relazioni con le folder: " + bean.getFolders());
			for (TRelEmailFolderBean folder : bean.getFolders()) {
				if (Thread.currentThread().isInterrupted()) {
					// thread elaborazione messaggio interrotto, interrompo
					// l'operazione corrente
					throw new InterruptedException();
				}
				if (folder != null) {
					((DaoTRelEmailFolder) DaoFactory.getDao(DaoTRelEmailFolder.class)).saveInSession(folder, session);
				} else {
					if (bean.getFolders().size() == 1) {
						log.warn("La mail con messageid " + bean.getMail().getMessageId() + " non è stata associata ad alcuna folder");
					}
				}
			}
			log.debug("Salvata la mail nelle relative folder");
		}
	}

	/**
	 * @param bean
	 * @param session
	 * @throws Exception
	 */
	private void saveinSessionMail(EmailBean bean, Session session) throws Exception {
		log.info("Salvo la mail con messageid: " + bean.getMail().getMessageId());
		log.info("Salvo la mail con EmailBean: " + bean.toString());
		((DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class)).saveInSession(bean.getMail(), session);
		log.debug("Salvata la mail con messageid: " + bean.getMail().getMessageId());
	}

	/**
	 * salvo le voci della rubrica
	 * 
	 * @param bean
	 * @param session
	 * @return
	 * @throws Exception
	 */
	private EmailBean saveVociRubrica(EmailBean bean, Session session) throws Exception {

		if (bean.getVoceRubricaMittente() != null) {
			inserisciInRubrica(bean.getVoceRubricaMittente(), session, bean.getMail().getFlgIo());
			log.debug("Salvato il mittente in rubrica");
		}
		if (ListUtil.isNotEmpty(bean.getVociRubricaDestinatari())) {
			for (TRubricaEmailBean voce : bean.getVociRubricaDestinatari()) {
				inserisciInRubrica(voce, session, bean.getMail().getFlgIo());
			}
			log.debug("Salvati i destinatari in rubrica");
		}
		if (bean.getReceipt() != null && bean.getReceipt().getVociRubrica() != null) {
			for (TRubricaEmailBean voce : bean.getReceipt().getVociRubrica()) {
				((DaoTRubricaEmail) DaoFactory.getDao(DaoTRubricaEmail.class)).updateInSession(voce, session);
			}
		}
		return bean;
	}

	@Operation(name = "getAttachments")
	public EmailAttachsBean getAttach(File eml) throws Exception {

		log.debug("Ricavo gli attachment della seguente email: " + eml);
		MailBreaker breaker = (MailBreaker) SpringAppContext.getContext().getBean(MAIL_BREAKER_BEAN);
		List<MailAttachmentsBean> listAtt = breaker.getAttachments(eml);
		return getEmailAttachsBeanAndListFiles(listAtt);
	}

	@Operation(name = "getAttachmentsByIdEmail")
	public EmailAttachsBean getAttachByIdEmail(String idEmail) throws Exception {

		log.debug("Ricavo gli attachment della mail avente id: " + idEmail);
		MailBreaker breaker = (MailBreaker) SpringAppContext.getContext().getBean(MAIL_BREAKER_BEAN);
		List<MailAttachmentsBean> listAtt = breaker.getAttachments(idEmail);
		return getEmailAttachsBeanAndListFiles(listAtt);
	}

	/**
	 * Data la lista di allegati in input restituisce un oggetto {@link EmailAttachsBean} valorizzato con la lista di file e i bean degli allegati
	 * 
	 * @param listAtt
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	private EmailAttachsBean getEmailAttachsBeanAndListFiles(List<MailAttachmentsBean> listAtt)
			throws IllegalAccessException, InvocationTargetException, IOException {

		EmailAttachsBean attach = new EmailAttachsBean();
		List<MailAttachmentsInfoBean> listAttResult = new ArrayList<>(listAtt.size());
		List<File> lListFile = new ArrayList<>(listAtt.size());
		for (MailAttachmentsBean lAttachmentsBean : listAtt) {
			MailAttachmentsInfoBean lMailAttachmentsInfoBean = new MailAttachmentsInfoBean();
			BeanUtilsBean2.getInstance().copyProperties(lMailAttachmentsInfoBean, lAttachmentsBean);
			listAttResult.add(lMailAttachmentsInfoBean);
			if (lAttachmentsBean.getFile() != null) {
				lListFile.add(lAttachmentsBean.getFile());
			} else if (lAttachmentsBean.getData() != null) {
				File lFile = File.createTempFile("att", "tmp");
				FileUtils.writeByteArrayToFile(lFile, lAttachmentsBean.getData());
				lListFile.add(lFile);
			}
		}
		attach.setMailAttachments(listAttResult);
		attach.setFiles(lListFile);
		return attach;
	}

	@Operation(name = "getAttachmentsWithContents")
	public GetEmailAttachmentsResponse getAttachWithContents(File eml) throws Exception {

		log.debug("Ricavo gli attachment della seguente email: " + eml);
		MailBreaker breaker = (MailBreaker) SpringAppContext.getContext().getBean(MAIL_BREAKER_BEAN);
		List<MailAttachmentsBean> listAtt = breaker.getAttachments(eml);
		return getEmailAttachsBean(listAtt);
	}

	@Operation(name = "getAttachmentsWithContentsByIdEmail")
	public GetEmailAttachmentsResponse getAttachWithContentsByIdEmail(String idEmail) throws Exception {

		log.debug("Ricavo gli attachment della mail avente id: " + idEmail);
		MailBreaker breaker = (MailBreaker) SpringAppContext.getContext().getBean(MAIL_BREAKER_BEAN);
		List<MailAttachmentsBean> listAtt = breaker.getAttachments(idEmail);
		return getEmailAttachsBean(listAtt);
	}

	/**
	 * Data la lista di allegati in input restituisce un oggetto {@link EmailAttachsBean} valorizzato con i bean degli allegati
	 * 
	 * @param listAtt
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	private GetEmailAttachmentsResponse getEmailAttachsBean(List<MailAttachmentsBean> listAtt)
			throws IllegalAccessException, InvocationTargetException, IOException {

		GetEmailAttachmentsResponse attach = new GetEmailAttachmentsResponse();
		List<EmailAttachment> listAttResult = new ArrayList<>(listAtt.size());
		for (MailAttachmentsBean att : listAtt) {
			final EmailAttachment bean = new EmailAttachment();
			listAttResult.add(bean);
			BeanUtilsBean2.getInstance().copyProperties(bean, att);
			if (att.getFile() != null && att.getData() == null) {
				bean.setContents(Files.toByteArray(att.getFile()));
			} else if (att.getData() != null) {
				bean.setContents(att.getData());
			}
		}
		attach.setMailAttachments(listAttResult);
		return attach;
	}

	@Operation(name = "getMailProtocollate")
	public EmailGroupBean getMailProtocollate(RegistrazioneProtocollo registrazione) throws Exception {

		EmailGroupBean retBean = new EmailGroupBean();
		retBean.setNameGroup("MailProtocollate");
		DaoTRegProtVsEmail daoTRegProtVsEmail = (DaoTRegProtVsEmail) DaoFactory.getDao(DaoTRegProtVsEmail.class);
		TFilterFetch<TRegProtVsEmailBean> filtro = new TFilterFetch<>();
		TRegProtVsEmailBean beanFiltro = new TRegProtVsEmailBean();
		if (registrazione.getIdProvReg() != null)
			beanFiltro.setIdProvReg(registrazione.getIdProvReg());
		if (registrazione.getAnnoReg() != null)
			beanFiltro.setAnnoReg(registrazione.getAnnoReg());
		if (registrazione.getCategoriaReg() != null)
			beanFiltro.setCategoriaReg(registrazione.getCategoriaReg());
		if (registrazione.getDataRegistrazione() != null)
			beanFiltro.setTsReg(registrazione.getDataRegistrazione().getTime());
		if (registrazione.getNumReg() != null)
			beanFiltro.setNumReg(registrazione.getNumReg());
		if (registrazione.getSiglaRegistro() != null)
			beanFiltro.setSiglaRegistro(registrazione.getSiglaRegistro());
		filtro.setFilter(beanFiltro);
		List<TRegProtVsEmailBean> listaRelazioni = daoTRegProtVsEmail.search(filtro).getData();
		List<String> idEmails = new ArrayList<>();
		if (listaRelazioni != null) {
			for (TRegProtVsEmailBean regProt : listaRelazioni) {
				idEmails.add(regProt.getIdEmail());
			}
		}
		retBean.setIdEmails(idEmails);
		return retBean;
	}

	@Operation(name = "getRelazioniMail")
	public EmailInfoRelazioniBean getMailRelative(InterrogazioneRelazioneEmailBean beanIn) throws Exception {

		EmailInfoRelazioniBean infoRelazioni = new EmailInfoRelazioniBean();
		List<TRelEmailMgoBean> listaFinale = new ArrayList<>();
		String idEmail = beanIn.getIdEmail();
		if (StringUtils.isBlank(idEmail)) {
			log.warn("Id Email non presente: impossibile trovare le relazioni associate");
		} else {
			Categoria categoria = beanIn.getCategoria();
			Dizionario dizionario = beanIn.getDizionario();
			TipoRelazione tipo = beanIn.getTipoRelazione();
			DaoTRelEmailMgo daoRel = (DaoTRelEmailMgo) DaoFactory.getDao(DaoTRelEmailMgo.class);
			TFilterFetch<TRelEmailMgoBean> filterfetch = new TFilterFetch<>();
			TRelEmailMgoBean filter1 = new TRelEmailMgoBean();
			filter1.setIdEmail2(idEmail);
			filterfetch.setFilter(filter1);
			// RICAVO TUTTE LE RELAZIONI DIRETTE
			List<TRelEmailMgoBean> listaRelazioniDirette = daoRel.search(filterfetch).getData();
			TRelEmailMgoBean filter2 = new TRelEmailMgoBean();
			filter2.setIdEmail1(idEmail);
			filterfetch.setFilter(filter2);
			// RICAVO TUTTE LE RELAZIONI INVERSE
			List<TRelEmailMgoBean> listaRelazioniInverse = daoRel.search(filterfetch).getData();
			// FILTRO PER IL TIPO DI RELAZIONE
			if (tipo != null) {
				if (tipo == TipoRelazione.DIRETTA && listaRelazioniDirette != null) {
					listaFinale = listaRelazioniDirette;
				} else if (tipo == TipoRelazione.INVERSA && listaRelazioniInverse != null) {
					listaFinale = listaRelazioniInverse;
				}
			} else {
				if (listaRelazioniDirette != null) {
					listaFinale.addAll(listaRelazioniInverse);
				}
				if (listaRelazioniInverse != null) {
					listaFinale.addAll(listaRelazioniInverse);
				}
			}
			// FILTRO PER LA CATEGORIA
			if (categoria != null) {
				DaoTEmailMgo daoEmail = (DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class);
				// non ciclo direttamente listaFinale perchè sennò quando vado a
				// rimuovere gli elementi mi va' in
				// ConcurrentModificationException
				for (TRelEmailMgoBean rel : new ArrayList<TRelEmailMgoBean>(listaFinale)) {
					TEmailMgoBean email = null;
					if (idEmail.equals(rel.getIdEmail2())) { // SE E' DIRETTA
						email = daoEmail.get(rel.getIdEmail1());
					} else if (idEmail.equals(rel.getIdEmail1())) { // SE E'
																	// INVERSA
						email = daoEmail.get(rel.getIdEmail2());
					}
					if (email != null && !email.getCategoria().equals(categoria.getValue())) {
						listaFinale.remove(rel);
					}

				}
			}
			// FILTRO PER IL DIZIONARIO
			if (dizionario != null) {
				TValDizionarioBean dizBean = ricavaDizionarioDaValore(dizionario);
				String idDiz = dizBean.getIdRecDiz();
				// non ciclo direttamente listaFinale perchè sennò quando vado a
				// rimuovere gli elementi mi va' in
				// ConcurrentModificationException
				for (TRelEmailMgoBean rel : new ArrayList<TRelEmailMgoBean>(listaFinale)) {
					if (!rel.getIdRecDizRuolo1Vs2().equals(idDiz)) {
						listaFinale.remove(rel);
					}
				}
			}
		}
		infoRelazioni.setRelazioni(listaFinale);
		return infoRelazioni;
	}

	@Operation(name = "getMailInfo")
	public EmailInfoBean getMailInfo(File eml) throws Exception {

		MailBreaker breaker = (MailBreaker) SpringAppContext.getContext().getBean(MAIL_BREAKER_BEAN);
		HeaderInfo headerInfo = breaker.getHeaderInfo(eml);

		String body = breaker.getBodyHtml(eml);
		if (StringUtils.isBlank(body)) {
			body = breaker.getBody(eml);
		}

		List<MailAttachmentsBean> listMailAttachmentsBean = breaker.getAttachments(eml);

		return getEmailInfoBean(headerInfo, body, listMailAttachmentsBean);
	}

	@Operation(name = "getMailInfoByIdEmail")
	public EmailInfoBean getMailInfoByIdEmail(String idEmail) throws Exception {
		
		StorageService storage = StorageImplementation.getStorage(); // serve per salvare gli allegati
		MailBreaker breaker = (MailBreaker) SpringAppContext.getContext().getBean(MAIL_BREAKER_BEAN);
		MessageInfos mailInfo = breaker.getInfosFromIdEmail(idEmail);
		HeaderInfo headerInfo = mailInfo.getHeaderinfo();

		String body = mailInfo.getBodyHtmlWithPrincipalMail();
		if (StringUtils.isBlank(body)) {
			body = mailInfo.getBodyTextPlainOnlyWithPrincipalMail();
		}

		List<MailAttachmentsBean> listMailAttachmentsBean = mailInfo.getAttachmentsWithPrincipalMail();
		if(storage != null) {
			for(MailAttachmentsBean lMailAttachmentsBean : listMailAttachmentsBean) {
				String uriFile = storage.store(lMailAttachmentsBean.getFile());
				lMailAttachmentsBean.setUriFile(uriFile);
			}
		}
		return getEmailInfoBean(headerInfo, body, listMailAttachmentsBean);
	}
	

	/**
	 * @param idEmail
	 * @param breaker
	 * @param headerInfo
	 * @param body
	 * @param listMailAttachmentsBean
	 * @return
	 * @throws AurigaMailBusinessException
	 */
	private EmailInfoBean getEmailInfoBean(HeaderInfo headerInfo, String body, List<MailAttachmentsBean> listMailAttachmentsBean)
			throws AurigaMailBusinessException {

		EmailInfoBean infoBean = new EmailInfoBean();
		infoBean.setMessageid(headerInfo.getMessageid());
		infoBean.setRiferimentomessageid(headerInfo.getRiferimentomessageid());
		infoBean.setMittente(headerInfo.getMittente());// mittente
		infoBean.setDestinatarioRicevuta(headerInfo.getMittente());// mittente è
																	// il
																	// destinatario
																	// della
																	// ricevuta
		infoBean.setIsPec(headerInfo.getTrasporto() == XTrasporto.POSTA_CERTIFICATA ? true : false);

		infoBean.setDate(headerInfo.getSendDate());
		infoBean.setDestinatario(headerInfo.getDestinatarito());
		infoBean.setDestinataricc(headerInfo.getDestinataricc());
		infoBean.setOggetto(headerInfo.getSubject());

		infoBean.setMessaggio(body);

		infoBean.setAllegati(listMailAttachmentsBean);

		if (headerInfo.getTiporicevuta() != null) {
			infoBean.setModalitaRicevuta(ModalitaRicevuta.valueOfValue(headerInfo.getTiporicevuta().getValue()));
		}
		if (headerInfo.getRicevuta() != null) {
			infoBean.setRicevuta(ClassificazioneRicevuta.valueOfValue(headerInfo.getRicevuta().getValue()));
		}
		return infoBean;
	}

	@Operation(name = "getSinteticMail")
	public SinteticEmailInfoBean getSinteticMail(File eml) throws Exception {
		MailBreaker breaker = (MailBreaker) SpringAppContext.getContext().getBean(MAIL_BREAKER_BEAN);
		SinteticEmailInfoBean lSinteticEmailInfoBean = new SinteticEmailInfoBean();
		getSinteticEmailInfoBean(lSinteticEmailInfoBean, breaker.getInfosFromEml(eml));
		return lSinteticEmailInfoBean;
	}

	@Operation(name = "getSinteticMailByIdEmail")
	public SinteticEmailInfoBean getSinteticMailByIdEmail(String idEmail) throws Exception {
		MailBreaker breaker = (MailBreaker) SpringAppContext.getContext().getBean(MAIL_BREAKER_BEAN);
		SinteticEmailInfoBean lSinteticEmailInfoBean = new SinteticEmailInfoBean();
		getSinteticEmailInfoBean(lSinteticEmailInfoBean, breaker.getInfosFromIdEmail(idEmail));
		return lSinteticEmailInfoBean;
	}

	/**
	 * @param eml
	 * @param breaker
	 * @param lSinteticEmailInfoBean
	 * @param messageInfos
	 * @throws Exception
	 * @throws AurigaMailBusinessException
	 */
	private void getSinteticEmailInfoBean(SinteticEmailInfoBean lSinteticEmailInfoBean, MessageInfos messageInfos)
			throws Exception, AurigaMailBusinessException {
		HeaderInfo headerInfo = messageInfos.getHeaderinfo();
		lSinteticEmailInfoBean.setMittente((messageInfos.isPec() || messageInfos.isAnomaliaPec())
				? "per conto di: " + messageInfos.getMittenteWithPrincipalMail() + " <" + headerInfo.getMittente() + ">"
				: headerInfo.getMittente());// mittente
		lSinteticEmailInfoBean.setDate(headerInfo.getSendDate());
		lSinteticEmailInfoBean.setDestinatario(headerInfo.getDestinatarito());
		lSinteticEmailInfoBean.setDestinataricc(headerInfo.getDestinataricc());
		lSinteticEmailInfoBean.setOggetto(headerInfo.getSubject());
		// restituisco il body in formato html o eventualmente text/plain
		if (StringUtils.isNotEmpty(messageInfos.getBodyExternalHtml())) {
			lSinteticEmailInfoBean.setMessaggio(messageInfos.getBodyExternalHtml());
		} else {
			lSinteticEmailInfoBean.setMessaggio(messageInfos.getBodyExternalPlain());
		}

		List<MailAttachmentsBean> lAllegati = messageInfos.getDirectAttachments();
		if (lAllegati != null) {
			List<SinteticMailAttachmentsBean> allegati = new ArrayList<>();
			for (MailAttachmentsBean lMailAttachmentsBean : lAllegati) {
				SinteticMailAttachmentsBean lSinteticMailAttachmentsBean = new SinteticMailAttachmentsBean();
				lSinteticMailAttachmentsBean.setFileAttach(lMailAttachmentsBean.getFile());
				lSinteticMailAttachmentsBean.setFilename(lMailAttachmentsBean.getFilename());
				allegati.add(lSinteticMailAttachmentsBean);
			}
			lSinteticEmailInfoBean.setAllegati(allegati);
		}
	}

	@Operation(name = "getBodyHtml")
	public EmailInfoBean getBodyHtml(File eml) throws Exception {
		MailBreaker breaker = (MailBreaker) SpringAppContext.getContext().getBean(MAIL_BREAKER_BEAN);
		EmailInfoBean lEmailInfoBean = new EmailInfoBean();
		lEmailInfoBean.setMessaggio(breaker.getBodyHtml(eml));
		return lEmailInfoBean;
	}

	@Operation(name = "getBodyHtmlByIdEmail")
	public EmailInfoBean getBodyHtmlByIdEmail(String idEmail) throws Exception {
		MailBreaker breaker = (MailBreaker) SpringAppContext.getContext().getBean(MAIL_BREAKER_BEAN);
		EmailInfoBean lEmailInfoBean = new EmailInfoBean();
		lEmailInfoBean.setMessaggio(breaker.getBodyHtml(idEmail));
		return lEmailInfoBean;
	}

	@Operation(name = "getBodyText")
	public EmailInfoBean getBodyText(File eml) throws Exception {
		MailBreaker breaker = (MailBreaker) SpringAppContext.getContext().getBean(MAIL_BREAKER_BEAN);
		EmailInfoBean lEmailInfoBean = new EmailInfoBean();
		lEmailInfoBean.setMessaggio(breaker.getBody(eml));
		return lEmailInfoBean;
	}

	@Operation(name = "getBodyTextByIdEmail")
	public EmailInfoBean getBodyTextByIdEmail(String idEmail) throws Exception {
		MailBreaker breaker = (MailBreaker) SpringAppContext.getContext().getBean(MAIL_BREAKER_BEAN);
		EmailInfoBean lEmailInfoBean = new EmailInfoBean();
		lEmailInfoBean.setMessaggio(breaker.getBody(idEmail));
		return lEmailInfoBean;
	}

	@Operation(name = "getHtmlInMainBody")
	public EmailInfoBean getHtmlInMainBody(File eml) throws Exception {
		MailBreaker breaker = (MailBreaker) SpringAppContext.getContext().getBean(MAIL_BREAKER_BEAN);
		EmailInfoBean lEmailInfoBean = new EmailInfoBean();
		lEmailInfoBean.setMessaggio(breaker.getHtmlInMainBody(eml));
		return lEmailInfoBean;
	}

	@Operation(name = "getHtmlInMainBodyByIdEmail")
	public EmailInfoBean getHtmlInMainBodyByIdEmail(String idEmail) throws Exception {
		MailBreaker breaker = (MailBreaker) SpringAppContext.getContext().getBean(MAIL_BREAKER_BEAN);
		EmailInfoBean lEmailInfoBean = new EmailInfoBean();
		lEmailInfoBean.setMessaggio(breaker.getHtmlInMainBody(idEmail));
		return lEmailInfoBean;
	}

	@Operation(name = "getTextInMainBody")
	public EmailInfoBean getTextInMainBody(File eml) throws Exception {
		MailBreaker breaker = (MailBreaker) SpringAppContext.getContext().getBean(MAIL_BREAKER_BEAN);
		EmailInfoBean lEmailInfoBean = new EmailInfoBean();
		lEmailInfoBean.setMessaggio(breaker.getTextInMainBody(eml));
		return lEmailInfoBean;
	}

	@Operation(name = "getTextInMainBodyByIdEmail")
	public EmailInfoBean getTextInMainBodyByIdEmail(String idEmail) throws Exception {
		MailBreaker breaker = (MailBreaker) SpringAppContext.getContext().getBean(MAIL_BREAKER_BEAN);
		EmailInfoBean lEmailInfoBean = new EmailInfoBean();
		lEmailInfoBean.setMessaggio(breaker.getTextInMainBody(idEmail));
		return lEmailInfoBean;
	}

	@Operation(name = "getAttachmentInteroperabilita")
	public MailAttachmentsBean getAttachmentInteroperabilita(InteroperabilitaAttachmentBeanIn bean) throws Exception {
		MailBreaker breaker = (MailBreaker) SpringAppContext.getContext().getBean(MAIL_BREAKER_BEAN);
		if (StringUtils.isNotBlank(bean.getIdEmail())) {
			return breaker.getAttach(bean.getIdEmail(), bean.getTipoInteroperabilita());
		}
		return breaker.getAttach(bean.getEml(), bean.getTipoInteroperabilita());
	}

	@Operation(name = "creaRelazioneProtocollo")
	public ResultBean<InfoRelazioneProtocolloBean> creaRelazioneProtocollo(RegistrazioneProtocolloBean beanIn) {
		log.debug("Creo la relazione con il protocollo");
		ResultBean<InfoRelazioneProtocolloBean> result = new ResultBean<>();
		InfoRelazioneProtocolloBean info = new InfoRelazioneProtocolloBean();
		TRegProtVsEmailBean regProt = null;
		Session session = null;
		try {
			session = HibernateUtil.begin();
			// creo la registrazione di protocollo
			RegistrazioneProtocollo registrazione = beanIn.getRegistrazione();
			regProt = new TRegProtVsEmailBean();
			regProt.setIdRegProtEmail(KeyGenerator.gen());
			regProt.setAnnoReg(registrazione.getAnnoReg());
			regProt.setCategoriaReg(registrazione.getCategoriaReg());
			regProt.setIdEmail(beanIn.getIdEmail());
			regProt.setIdProvReg(registrazione.getIdProvReg());
			regProt.setNumReg(registrazione.getNumReg());
			regProt.setSiglaRegistro(registrazione.getSiglaRegistro());
			if (registrazione.getDataRegistrazione() != null) {
				regProt.setTsReg(registrazione.getDataRegistrazione().getTime());
			}
			Transaction transaction = session.beginTransaction();
			((DaoTRegProtVsEmail) DaoFactory.getDao(DaoTRegProtVsEmail.class)).saveInSession(regProt, session);
			// aggiorno anche gli attach relativi
			log.debug("Controllo l'esistenza di attach da protocollare");
			DaoTAttachEmailMgo daoAttach = ((DaoTAttachEmailMgo) DaoFactory.getDao(DaoTAttachEmailMgo.class));
			if (ListUtil.isNotEmpty(beanIn.getAttachmentsProtocollati())) {
				for (ProtocolloAttachmentBean attachProt : beanIn.getAttachmentsProtocollati()) {
					TAttachEmailMgoBean attach = daoAttach.get(attachProt.getIdAttachment());
					if (attach.getIdAttachEmail() != null) {
						attach.setIdRegProtEmail(regProt.getIdRegProtEmail());
						attach.setNroAllegRegProt(attachProt.getNumeroAllegatoRegProt());
						daoAttach.updateInSession(attach, session);
					} else
						log.warn("Nessun attachment presente per il seguente id: " + attachProt.getIdAttachment());
				}
			}
			session.flush();
			transaction.commit();
			log.debug("Persisto la relazione con il protocollo");
			info.setRelazione(regProt);
			result.setResultBean(info);
		} catch (Exception e) {
			log.error("Impossibile creare la relazione con il protocollo dell'email", e);
			result.setDefaultMessage("Impossibile creare la relazione con il protocollo dell'email");
			result.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
			result.setInError(true);
		} finally {
			try {
				HibernateUtil.release(session);
			} catch (Exception e) {
				log.warn("Non è stato possibile rilasciare la sessione hibernate");
			}
		}
		return result;
	}

	@Operation(name = "inviaConfermaAutomatica")
	public ResultBean<StatoConfermaAutomaticaBean> inviaConfermaAutomatica(RegistrazioneProtocolloBean beanIn) {
		ResultBean<StatoConfermaAutomaticaBean> result = new ResultBean<>();
		DaoTEmailMgo daoEmail = (DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class);
		try {
			TEmailMgoBean bean = daoEmail.get(beanIn.getIdEmail());
			StatoConfermaAutomaticaBean sca = new StatoConfermaAutomaticaBean();
			if (checkConferma(bean)) {
				log.debug("Cerco di creare la risposta automatica per idEmail: " + beanIn.getIdEmail() + " utente: " + beanIn.getLoginBean().getIdUtente()
						+ " userid " + beanIn.getLoginBean().getUserId() + " schema: " + beanIn.getLoginBean().getSchema() + " token: "
						+ beanIn.getLoginBean().getToken());
				ResultBean<EmailSentReferenceBean> risposta = creaRispostaAutomatica(beanIn.getIdEmail(), beanIn.getLoginBean());
				if (!risposta.isInError() && risposta.getDefaultMessage() == null) {
					sca.setStato("spedita");
					bean.setFlgNotifInteropConf(true);
				} else {
					sca.setStato("non spedita");
					result.setDefaultMessage(risposta.getDefaultMessage());
					result.setErrorStackTrace(risposta.getErrorStackTrace());
					result.setInError(true);
					bean.setFlgNotifInteropConf(false);
				}
				daoEmail.update(bean);
			} else {
				sca.setStato("risposta automatica non prevista per questa email");
			}
			result.setResultBean(sca);
		} catch (Exception e) {
			log.error("Impossibile spedire la conferma automatica: ", e);
			result.setDefaultMessage("Impossibile spedire la conferma automatica");
			result.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
			result.setInError(true);
		}
		return result;
	}

	/**
	 * controlla i valori dei parametri per sapere se inviare o meno la conferma automatica
	 * 
	 * @param beanIn
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	private boolean checkConferma(TEmailMgoBean bean) throws Exception {
		// invio la conferma automatica se e solo se è una mail in ingresso e se
		// prevede
		// la richiesta di conferma
		log.debug("Verifico se è necessario inviare la conferma automatica");
		log.debug("Flag IO: " + bean.getFlgIo() + " richiesta conferma: " + bean.getFlgRichConferma());
		if (bean.getFlgIo().equals((InputOutput.INGRESSO).getValue()) && bean.getFlgRichConferma()) {
			DaoTParameters daoParametri = (DaoTParameters) DaoFactory.getDao(DaoTParameters.class);
			// cerco il parametro specifico
			TParametersBean parametro = null;
			TFilterFetch<TParametersBean> ff = new TFilterFetch<>();
			TParametersBean filterPar = new TParametersBean();
			filterPar.setParKey(CHIAVE_PARAMETRO_CONFERMA_PROTOCOLLAZIONE + DOT + bean.getIdCasella());
			ff.setFilter(filterPar);
			List<TParametersBean> listaRis = daoParametri.search(ff).getData();
			if (!ListUtil.isEmpty(listaRis)) {
				parametro = listaRis.get(0);
			}
			// se nullo cerco il parametro generico
			if (parametro == null) {
				filterPar.setParKey(CHIAVE_PARAMETRO_CONFERMA_PROTOCOLLAZIONE);
				ff.setFilter(filterPar);
				listaRis = daoParametri.search(ff).getData();
				if (!ListUtil.isEmpty(listaRis)) {
					parametro = listaRis.get(0);
				}
			}

			if (parametro != null && parametro.getStrValue().equalsIgnoreCase("AUTO")) {
				return true;
			}
		}
		return false;
	}

	public ResultBean<EmailSentReferenceBean> creaRispostaAutomatica(String idEmail, MailLoginBean login) {
		log.debug("Creo la risposta automatica per la mail: " + idEmail);
		ResultBean<EmailSentReferenceBean> risultato = null;
		try {
			DaoTEmailMgo daoEmail = (DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class);
			TEmailMgoBean mail = daoEmail.get(idEmail);
			DmpkIntMgoEmailPreparaconfermaautomaticaBean input = new DmpkIntMgoEmailPreparaconfermaautomaticaBean();
			input.setIdemailin(idEmail);
			if (login != null) {
				input.setIdutentepecin(login.getIdUtente());
			} else {
				log.error("Impossibile creare la risposta automatica all'email: credenziali di invocazione store nulle");
				throw new AurigaMailBusinessException("Impossibile creare la risposta automatica all'email: credenziali di invocazione store nulle");
			}
			// RICAVO LA SEGNATURA
			String uri = mail.getUriEmail();
			StorageCenter storage = new StorageCenter();
			File eml = storage.getGlobalStorage(mail.getIdCasella()).extractFile(uri);
			InteroperabilitaAttachmentBeanIn beanIn = new InteroperabilitaAttachmentBeanIn();
			beanIn.setEml(eml);
			beanIn.setTipoInteroperabilita(TipoInteroperabilita.SEGNATURA);
			MailAttachmentsBean segnatura = getAttachmentInteroperabilita(beanIn);
			if (segnatura != null && segnatura.getFile() != null) {
				input.setXmlsegnaturain(FileUtils.readFileToString(segnatura.getFile()));
			}
			input.setVersionedtdin(OLD_VERSION_SEGNATURA);
			SchemaBean schb = new SchemaBean();
			schb.setSchema(login.getSchema());
			DmpkIntMgoEmailPreparaconfermaautomatica storeConfermaAutomatica = new DmpkIntMgoEmailPreparaconfermaautomatica();
			StoreResultBean<DmpkIntMgoEmailPreparaconfermaautomaticaBean> output = storeConfermaAutomatica.execute(Locale.ITALIAN, schb, input);
			if (StringUtils.isEmpty(output.getDefaultMessage())) {
				log.debug("La store di creazione della conferma è andata a buon fine");
				String destinatariTo = output.getResultBean().getDestinatarimailnotifout();
				String destinatariCC = output.getResultBean().getDestinatariccmailnotifout();
				String subject = output.getResultBean().getSubjectmailnotifout();
				String body = output.getResultBean().getBodymailnotifout();
				String xmlConferma = output.getResultBean().getXmlconfermaout();
				MailSenderService senderService = new MailSenderService();
				DaoMailboxAccount daoMailboxAccount = (DaoMailboxAccount) DaoFactory.getDao(DaoMailboxAccount.class);
				MailboxAccountBean account = daoMailboxAccount.get(mail.getIdCasella());
				NotificaInteroperabileBean notifica = new NotificaInteroperabileBean();
				SenderBean sb = new SenderBean();
				sb.setAccount(account.getAccount());
				StringTokenizer tokenizerTo = new StringTokenizer(destinatariTo);
				List<String> destinatariA = new ArrayList<>();
				while (tokenizerTo.hasMoreTokens()) {
					destinatariA.add(tokenizerTo.nextToken());
				}
				List<String> destinatariConoscenza = null;
				if (destinatariCC != null) {
					destinatariConoscenza = new ArrayList<>();
					StringTokenizer tokenizerCC = new StringTokenizer(destinatariCC);
					while (tokenizerCC.hasMoreTokens()) {
						destinatariConoscenza.add(tokenizerCC.nextToken());
					}
				}
				sb.setAddressTo(destinatariA);
				sb.setAddressCc(destinatariConoscenza);
				sb.setAddressFrom(account.getAccount());
				sb.setBody(body == null ? "" : body);
				sb.setSubject(subject);
				notifica.setMailPartenza(mail);
				notifica.setSenderBean(sb);
				notifica.setTipoNotifica(TipoInteroperabilita.CONFERMA_RICEZIONE);
				notifica.setXmlNotifica(xmlConferma);
				risultato = senderService.sendAndSaveInteropNotifica(notifica);
			} else {
				log.warn("Impossibile inviare la conferma automatica: " + output.getDefaultMessage());
				risultato = new ResultBean<>();
				risultato.setDefaultMessage(output.getDefaultMessage());
				risultato.setInError(false);
			}
		} catch (Exception e) {
			log.error("Impossibile creare la risposta automatica di conferma ", e);
			risultato = new ResultBean<>();
			risultato.setDefaultMessage("Impossibile creare la risposta automatica di conferma");
			risultato.setInError(true);
			risultato.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
		}
		return risultato;
	}

	@Operation(name = "updateMailProtocollata")
	public TEmailMgoBean updateMailProtocollata(InfoProtocolloBean beanIn) throws AurigaMailBusinessException {
		TEmailMgoBean mail = null;
		try {
			String value = ricavaFlagProtocollo(beanIn).getFlagStatoProtocollazione();
			mail = ((DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class)).get(beanIn.getIdEmail());
			mail.setFlgStatoProt(value);
			((DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class)).update(mail);
		} catch (Exception e) {
			log.error(ERRORE_AGGIORNAMENTO_STATO_PROTOCOLLO, e);
			throw new AurigaMailBusinessException(ERRORE_AGGIORNAMENTO_STATO_PROTOCOLLO, e);
		}
		return mail;
	}

	/**
	 * Metodo che imposta al valore in input un flag in una mail, se non è già impostato a quel valore <br>
	 * Attenzione: i flag vanno aggiornati solo per le mail originali a cui si riferiscono, non per le mail inviate
	 * 
	 * @param idMail
	 *            id della mail da aggiornare
	 * @param flag
	 *            proprietà del bean da aggiornare
	 * @param value
	 *            valore da impostare
	 * @return
	 * @throws Exception
	 */

	@Operation(name = "updateFlag")
	public void updateFlag(String idMail, String flag, Boolean value) throws Exception {
		TEmailMgo mail = null;
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			DaoTEmailMgo daoMail = ((DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class));
			mail = daoMail.getTEmailMgoInSession(idMail, session);
			if (mail != null) {
				BeanUtilsBean util = BeanUtilsBean2.getInstance();
				if (StringUtils.isBlank(util.getProperty(mail, flag)) || Boolean.parseBoolean(util.getProperty(mail, flag)) != value) {
					util.setProperty(mail, flag, value);
					mail = daoMail.updateInSession(mail, session);
					session.flush();
					transaction.commit();
				}
			} else {
				throw new AurigaMailBusinessException("Non è possibile trovare la mail da aggiornare avente id: " + idMail);
			}
		} catch (Exception e) {
			log.error("Impossibile aggiornare il flag inoltro/risposta dell'email", e);
			throw e;
		} finally {
			try {
				HibernateUtil.release(session);
			} catch (Exception e) {
				throw e;
			}
		}
	}

	@Operation(name = "eliminaProtocollazioneMail")
	public TEmailMgoBean eliminaProtocollazioneMail(RegistrazioneProtocolloBean beanIn) throws Exception {
		TEmailMgoBean mail = null;
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			RegistrazioneProtocollo registrazione = beanIn.getRegistrazione();
			TFilterFetch<TRegProtVsEmailBean> filterfetch = new TFilterFetch<>();
			TRegProtVsEmailBean regProt = new TRegProtVsEmailBean();
			regProt.setIdProvReg(registrazione.getIdProvReg());
			filterfetch.setFilter(regProt);
			List<TRegProtVsEmailBean> protocollazioni = ((DaoTRegProtVsEmail) DaoFactory.getDao(DaoTRegProtVsEmail.class)).search(filterfetch).getData();
			for (TRegProtVsEmailBean prot : protocollazioni) {
				((DaoTRegProtVsEmail) DaoFactory.getDao(DaoTRegProtVsEmail.class)).delete(prot);
			}
			DaoTAttachEmailMgo daoAttach = ((DaoTAttachEmailMgo) DaoFactory.getDao(DaoTAttachEmailMgo.class));
			for (ProtocolloAttachmentBean attachProt : beanIn.getAttachmentsProtocollati()) {
				TAttachEmailMgoBean attach = daoAttach.get(attachProt.getIdAttachment());
				if (attach.getIdAttachEmail() != null) {
					attach.setIdRegProtEmail(null);
					daoAttach.updateInSession(attach, session);
				} else
					log.warn("Nessun attachment presente per il seguente id: " + attachProt.getIdAttachment());
			}
			String value = ricavaFlagProtocollo((InfoProtocolloBean) beanIn).getFlagStatoProtocollazione();
			mail = ((DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class)).get(beanIn.getIdEmail());
			mail.setFlgStatoProt(value);
			mail = ((DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class)).updateInSession(mail, session);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			log.error(ERRORE_AGGIORNAMENTO_STATO_PROTOCOLLO, e);
			throw e;
		} finally {
			try {
				HibernateUtil.release(session);
			} catch (Exception e) {
				throw e;
			}
		}
		return mail;
	}

	@Operation(name = "getStatoProtocollazione")
	public GetStatoProtocollazioneOutBean ricavaFlagProtocollo(InfoProtocolloBean beanIn) throws Exception {
		DmpkIntMgoEmailGetstatoprotemailBean statoProtEmail = new DmpkIntMgoEmailGetstatoprotemailBean();
		statoProtEmail.setIdemailin(beanIn.getIdEmail());
		SchemaBean sb = new SchemaBean();
		sb.setSchema(beanIn.getLoginBean().getSchema());
		DmpkIntMgoEmailGetstatoprotemail protService = new DmpkIntMgoEmailGetstatoprotemail();
		StoreResultBean<DmpkIntMgoEmailGetstatoprotemailBean> result = protService.execute(Locale.ITALIAN, sb, statoProtEmail);
		GetStatoProtocollazioneOutBean lGetStatoProtocollazioneOutBean = new GetStatoProtocollazioneOutBean();
		lGetStatoProtocollazioneOutBean.setFlagStatoProtocollazione(result.getResultBean().getFlgstatoprotout());
		return lGetStatoProtocollazioneOutBean;
	}

	/**
	 * 
	 * @param beanIn
	 * @param session
	 * @return
	 * @throws Exception
	 */

	public EmailGroupBean spostaEmailInSession(EmailGroupBean beanIn, Session session) throws Exception {
		EmailGroupBean groupOut = new EmailGroupBean();
		List<String> idEmailsNonSpostati = new ArrayList<>();
		DaoTEmailMgo daoMail = (DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class);
		for (String idMail : beanIn.getIdEmails()) {
			try {
				TEmailMgoBean mail = daoMail.getInSession(idMail, session);
				String idAccount = mail.getIdCasella();
				// LISTA DELLE FOLDER DI PARTENZA
				List<TFolderCaselleBean> listaFolderPartenza = ricavaListaFolderClassificazionePrefissoInSession(idAccount, beanIn.getClassificazioneFrom(),
						session);
				log.info("Possibili folder di partenza associate alla classificazione " + beanIn.getClassificazioneFrom() + ": " + listaFolderPartenza);
				DaoTRelEmailFolder daoRelMailFolder = (DaoTRelEmailFolder) DaoFactory.getDao(DaoTRelEmailFolder.class);
				if (!ListUtil.isEmpty(listaFolderPartenza)) {
					TFilterFetch<TRelEmailFolderBean> filterFetch = new TFilterFetch<>();
					TRelEmailFolderBean relMailFolder = new TRelEmailFolderBean();
					relMailFolder.setIdEmail(idMail);
					// CANCELLO LE RELAZIONI DELLA MAIL CON LE POSSIBILI
					// FOLDER DI PARTENZA
					for (TFolderCaselleBean folderPartenza : listaFolderPartenza) {
						relMailFolder.setIdFolderCasella(folderPartenza.getIdFolderCasella());
						filterFetch.setFilter(relMailFolder);
						List<TRelEmailFolderBean> listaRelazioniPartenza = daoRelMailFolder.searchInSession(filterFetch, session).getData();
						if (!ListUtil.isEmpty(listaRelazioniPartenza)) {
							log.info("Elimino la relazione con la folder: " + folderPartenza.getIdFolderCasella());
							TRelEmailFolderId lTRelEmailFolderId = new TRelEmailFolderId(folderPartenza.getIdFolderCasella(), idMail);
							TRelEmailFolder lTRelEmailFolder = (TRelEmailFolder) session.get(TRelEmailFolder.class, lTRelEmailFolderId);
							session.delete(lTRelEmailFolder);
							session.flush();
							log.info("Relazione eliminata");
						}
					}
				}
				// INSERISCO LA NUOVA RELAZIONE CON LA FOLDER DI ARRIVO
				// SE ESISTE GIA' SOLLEVA ECCEZIONE GESTITA
				TFolderCaselleBean folderArrivo = ricavaFolderClassificazioneInSession(idAccount, beanIn.getClassificazioneTo().getValue(), session);
				log.info("Folder di arrivo associata alla classificazione " + beanIn.getClassificazioneTo().getValue() + ": " + folderArrivo);
				log.info("Creo la relazione con la folder: " + folderArrivo.getIdFolderCasella());
				TRelEmailFolderBean relToInsert = new TRelEmailFolderBean();
				relToInsert.setIdFolderCasella(folderArrivo.getIdFolderCasella());
				relToInsert.setIdEmail(idMail);
				try {
					daoRelMailFolder.saveInSession(relToInsert, session);
					log.info("Relazione creata");
				} catch (ConstraintViolationException e) {
					log.warn("Relazione non creata perchè già esistente");
				}
			} catch (Exception e) {
				idEmailsNonSpostati.add(idMail);
				log.error("Impossibile spostare la email con id: " + idMail, e);
				throw new AurigaMailBusinessException("Impossibile spostare la email con id: " + idMail, e);
			}
		}
		session.flush();
		groupOut.setIdEmails(idEmailsNonSpostati);
		groupOut.setNameGroup("Email non spostate");
		return groupOut;
	}

	/**
	 * Metodo di spostamento della mail a una folder (logica), rimuovendo le folder di partenza specificate nel bean in input
	 * 
	 * @param beanIn
	 * @return
	 * @throws Exception
	 */

	@Operation(name = "moveMail")
	public EmailGroupBean spostaEmail(EmailGroupBean beanIn) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			EmailGroupBean groupOut = spostaEmailInSession(beanIn, session);
			transaction.commit();
			return groupOut;
		} finally {
			HibernateUtil.release(session);
		}
	}

	/**
	 * Metodo di spostamento della mail a più folder (logiche), rimuovendo le folder di partenza specificate nel bean in input
	 * 
	 * @param beanIn
	 * @param session
	 * @return
	 * @throws Exception
	 */

	public EmailGroupBean spostaEmailFolderMultipleInSession(SpostaEmailBean beanIn, Session session) throws Exception {

		EmailGroupBean groupOut = new EmailGroupBean();
		List<String> idEmailsNonSpostati = new ArrayList<>();
		DaoTEmailMgo daoMail = (DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class);
		for (String idMail : beanIn.getIdEmails()) {
			try {
				TEmailMgoBean mail = daoMail.getInSession(idMail, session);
				String idAccount = mail.getIdCasella();
				// LISTA DELLE FOLDER DI PARTENZA
				List<TFolderCaselleBean> listaFolderPartenza = ricavaListaFolderClassificazionePrefissoInSession(idAccount, beanIn.getClassificazioneFrom(),
						session);
				log.info("Possibili folder di partenza associate alla classificazione " + beanIn.getClassificazioneFrom() + ": " + listaFolderPartenza);
				DaoTRelEmailFolder daoRelMailFolder = (DaoTRelEmailFolder) DaoFactory.getDao(DaoTRelEmailFolder.class);
				if (!ListUtil.isEmpty(listaFolderPartenza)) {
					TFilterFetch<TRelEmailFolderBean> filterFetch = new TFilterFetch<>();
					TRelEmailFolderBean relMailFolder = new TRelEmailFolderBean();
					relMailFolder.setIdEmail(idMail);
					// CANCELLO LA MAIL DA TUTTE LE FOLDER DI PARTENZA
					for (TFolderCaselleBean folderPartenza : listaFolderPartenza) {
						relMailFolder.setIdFolderCasella(folderPartenza.getIdFolderCasella());
						filterFetch.setFilter(relMailFolder);
						List<TRelEmailFolderBean> listaRelazioniPartenza = daoRelMailFolder.searchInSession(filterFetch, session).getData();
						if (!ListUtil.isEmpty(listaRelazioniPartenza)) {
							log.info("Elimino la relazione con la folder: " + folderPartenza.getIdFolderCasella());
							TRelEmailFolderId lTRelEmailFolderId = new TRelEmailFolderId(folderPartenza.getIdFolderCasella(), idMail);
							TRelEmailFolder lTRelEmailFolder = (TRelEmailFolder) session.get(TRelEmailFolder.class, lTRelEmailFolderId);
							session.delete(lTRelEmailFolder);
							session.flush();
							log.info("Relazione eliminata");
						}
					}
				}
				// FOLDERS DI ARRIVO
				List<TFolderCaselleBean> cartelleTo = new ArrayList<>();
				List<String> folderTo = beanIn.getFolderArrivo();
				for (String folder : folderTo) {
					TFolderCaselleBean folderArrivo = ricavaFolderClassificazioneInSession(idAccount, folder, session);
					if (folderArrivo != null) {
						cartelleTo.add(folderArrivo);
					}
				}
				log.info("Folder di arrivo: " + cartelleTo);
				// INSERISCO LE NUOVE RELAZIONI
				for (TFolderCaselleBean folder : cartelleTo) {
					log.info("Creo la relazione con la folder: " + folder.getIdFolderCasella());
					TRelEmailFolderBean relToInsert = new TRelEmailFolderBean();
					relToInsert.setIdFolderCasella(folder.getIdFolderCasella());
					relToInsert.setIdEmail(idMail);
					try {
						daoRelMailFolder.saveInSession(relToInsert, session);
						log.info("Relazione creata");
					} catch (ConstraintViolationException e) {
						log.warn("Relazione non creata perchè già esistente");
					}
				}
			} catch (Exception e) {
				idEmailsNonSpostati.add(idMail);
				log.error("Impossibile spostare la email con id " + idMail, e);
			}
		}
		session.flush();
		groupOut.setIdEmails(idEmailsNonSpostati);
		groupOut.setNameGroup("Email non spostate");
		return groupOut;

	}

	/**
	 * Metodo di spostamento della mail a più folder (logiche), rimuovendo le folder di partenza specificate nel bean in input
	 * 
	 * @param beanIn
	 * @return
	 * @throws Exception
	 */

	public EmailGroupBean spostaEmailFolderMultiple(SpostaEmailBean beanIn) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			EmailGroupBean groupOut = spostaEmailFolderMultipleInSession(beanIn, session);
			transaction.commit();
			return groupOut;
		} finally {
			HibernateUtil.release(session);
		}
	}

	/**
	 * Metodo di archiviazione della mail, spostando nella folder "standard.archiviata.arrivo.protocollate" Solo se la mail non è una notifica interoperabile di
	 * annullamento, aggiornamento, eccezione e è attivo il parametro ARCHIVIAZIONE_AUTO_EMAIL_PROTOCOLLATE in T_PARAMETERS e il flag stato protocollazione
	 * della mail è C. Infine la classificazione attuale della folder della mail deve iniziare con "standard.arrivo.". La mail non deve essere stata archiviata
	 * manualmente
	 * 
	 * @param pTEmailMgoBean
	 * @return
	 */

	@Operation(name = "eseguiArchiviazione")
	public TEmailMgoBean archiviaMailSePrevisto(TEmailMgoBean pTEmailMgoBean) {
		TEmailMgoBean mail = null;
		try {
			String idEmail = pTEmailMgoBean.getIdEmail();
			DaoTEmailMgo daoMail = (DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class);
			mail = daoMail.get(idEmail);
			String idCasella = mail.getIdCasella();
			if (!mail.getFlgNotifInteropAgg() && !mail.getFlgNotifInteropAnn() && !mail.getFlgNotifInteropEcc()) {
				DaoTParameters daoParametri = (DaoTParameters) DaoFactory.getDao(DaoTParameters.class);
				// cerco il parametro specifico
				TParametersBean parametro = null;
				TFilterFetch<TParametersBean> ff = new TFilterFetch<>();
				TParametersBean filterPar = new TParametersBean();
				filterPar.setParKey(CHIAVE_PARAMETRO_ARCHIVIAZIONE + DOT + idCasella);
				ff.setFilter(filterPar);
				List<TParametersBean> listaRis = daoParametri.search(ff).getData();
				if (!ListUtil.isEmpty(listaRis)) {
					parametro = listaRis.get(0);
				}
				// se nullo cerco il parametro generico
				if (parametro == null) {
					filterPar.setParKey(CHIAVE_PARAMETRO_ARCHIVIAZIONE);
					ff.setFilter(filterPar);
					listaRis = daoParametri.search(ff).getData();
					if (!ListUtil.isEmpty(listaRis)) {
						parametro = listaRis.get(0);
					}
				}
				// se devo archiviare effettuo tutte le logiche relative
				if (parametro != null && parametro.getStrValue().equalsIgnoreCase("true")) {
					if ((mail.getFlgStatoProt().equalsIgnoreCase("C"))) {
						EmailGroupBean mailGroup = new EmailGroupBean();
						// ricavo la classificazione di partenza
						String classificazioneFrom = null;
						DaoTRelEmailFolder daoRelMailFolder = (DaoTRelEmailFolder) DaoFactory.getDao(DaoTRelEmailFolder.class);
						TFilterFetch<TRelEmailFolderBean> filterfetch = new TFilterFetch<TRelEmailFolderBean>();
						TRelEmailFolderBean filter = new TRelEmailFolderBean();
						filter.setIdEmail(idEmail);
						filterfetch.setFilter(filter);
						List<TRelEmailFolderBean> listaRelEmailFolder = daoRelMailFolder.search(filterfetch).getData();
						if (ListUtil.isEmpty(listaRelEmailFolder)) {
							log.warn("Fallita archiviazione automatica della email - nessuna folder di partenza presente");
						} else {
							List<String> folders = new ArrayList<>();
							for (TRelEmailFolderBean rel : listaRelEmailFolder) {
								folders.add(rel.getIdFolderCasella());
							}
							DaoTFolderCaselle daoFolder = (DaoTFolderCaselle) DaoFactory.getDao(DaoTFolderCaselle.class);
							for (String idFolderCasella : folders) {
								TFolderCaselleBean record = daoFolder.get(idFolderCasella);
								if (record.getClassificazione().startsWith(PREFIX_FOLDER_ARRIVO)) {
									classificazioneFrom = record.getClassificazione();
									break;
								}
							}
						}
						if (classificazioneFrom == null) {
							log.warn("Fallita archiviazione automatica della email - la classificazione della folder attuale non inizia con standard.arrivo.");
						} else {
							// sposto nella cartella di destinazione
							mailGroup.setClassificazioneFrom(classificazioneFrom);
							mailGroup.setClassificazioneTo(Classificazione.STANDARD_FOLDER_ARCHIVIATA_ARRIVO_PROTOCOLLATE);
							mailGroup.setNameGroup("da archiviare");
							List<String> mails = new ArrayList<>();
							mails.add(idEmail);
							mailGroup.setIdEmails(mails);
							spostaEmail(mailGroup);
						}
					}
				}
			}
		} catch (Exception e) {
			log.warn("Fallita archiviazione automatica della email ", e);
		}
		return mail;
	}

	/**
	 * File di postcert.eml che è il messaggio originario senza busta di trasporto
	 * 
	 * @param eml
	 * @return
	 * @throws Exception
	 */
	@Operation(name = "getPostacert")
	public MailAttachmentsBean getPostaCert(File eml) throws Exception {
		MailBreaker breaker = (MailBreaker) SpringAppContext.getContext().getBean(MAIL_BREAKER_BEAN);
		return breaker.getPostcert(eml);
	}

	@Operation(name = "getPostacertByIdEmail")
	public MailAttachmentsBean getPostaCertByIdEmail(String idEmail) throws Exception {
		MailBreaker breaker = (MailBreaker) SpringAppContext.getContext().getBean(MAIL_BREAKER_BEAN);
		return breaker.getPostcert(idEmail);
	}

	private void inserisciInRubrica(TRubricaEmailBean bean, Session session, String flgIO) throws Exception {
		TFilterFetch<TRubricaEmailBean> filterFetch = new TFilterFetch<>();
		TRubricaEmailBean filtro = new TRubricaEmailBean();
		filtro.setAccountEmail(bean.getAccountEmail());
		filtro.setIdFruitoreCasella(bean.getIdFruitoreCasella());
		filterFetch.setFilter(filtro);
		DaoTRubricaEmail daoRubrica = ((DaoTRubricaEmail) DaoFactory.getDao(DaoTRubricaEmail.class));
		List<TRubricaEmailBean> risSearchRubrica = daoRubrica.searchInSession(filterFetch, session).getData();
		if (ListUtil.isEmpty(risSearchRubrica)) {
			log.debug("Inserisco in rubrica il seguente indirizzo email: " + bean.getAccountEmail());
			daoRubrica.saveInSession(bean, session);
		} else {
			// AGGIORNO TUTTE LE VOCI CON QUELL'ACCOUNT
			// SENZA BADARE AL FRUITORE
			// SOLO SE SI TRATTA DI POSTA IN INGRESSO
			if (flgIO.equals(InputOutput.INGRESSO.getValue())) {
				TRubricaEmailBean nuovoFiltro = new TRubricaEmailBean();
				nuovoFiltro.setAccountEmail(bean.getAccountEmail());
				filterFetch.setFilter(nuovoFiltro);
				risSearchRubrica = daoRubrica.searchInSession(filterFetch, session).getData();
				for (TRubricaEmailBean alreadySaved : risSearchRubrica) {
					copyInfoPEC(bean, alreadySaved);
					log.debug("Aggiorno in rubrica il seguente record: " + alreadySaved.getIdVoceRubricaEmail());
					daoRubrica.updateInSession(alreadySaved, session);
				}
			}
			// AGGIORNO IL BEAN CON LA PRIMA VOCE RUBRICA AGGIORNATA IN MODO DA
			// AVERE I VALORI
			// CORRETTI NEL BEAN DEI DESTINATARI
			bean.setIdVoceRubricaEmail(risSearchRubrica.get(0).getIdVoceRubricaEmail());
		}
	}

	/**
	 * per l'update del bean copio i valori del nuovo bean in quello già presente sul db l'update avviene solo per le informazioni relative alla PEC
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */
	private TRubricaEmailBean copyInfoPEC(TRubricaEmailBean src, TRubricaEmailBean dest) {
		if (!dest.getFlgPecVerificata()) {
			dest.setFlgPecVerificata(src.getFlgPecVerificata());
			dest.setTipoAccount(src.getTipoAccount());
		}
		return dest;
	}

	/**
	 * Metodo che restituisce le folder aventi classificazione esattamente uguale a {@link classificazione} e associati all'account avente id {@link idAccount}
	 * 
	 * @param idAccount
	 * @param classificazione
	 * @param session
	 * @return
	 * @throws Exception
	 */

	public TFolderCaselleBean ricavaFolderClassificazioneInSession(String idAccount, String classificazione, Session session) throws Exception {
		TFilterFetch<TFolderCaselleBean> filter = new TFilterFetch<>();
		TFolderCaselleBean filtro = new TFolderCaselleBean();
		filtro.setIdCasella(idAccount);
		filtro.setClassificazione(classificazione);
		filter.setFilter(filtro);
		TPagingList<TFolderCaselleBean> listaRis = null;
		try {
			listaRis = ((DaoTFolderCaselle) DaoFactory.getDao(DaoTFolderCaselle.class)).searchInSession(filter, session);
			if (listaRis == null || ListUtil.isEmpty(listaRis.getData())) {
				log.error("Nessuna directory prevista " + classificazione + " per la casella " + idAccount + " e classificazione " + classificazione);
				throw new AurigaMailBusinessException("Nessuna directory prevista per questa casella " + idAccount + " e classificazione " + classificazione);
			}
			if (listaRis.getData().size() > 1) {
				log.error("Presenza di due folder con lo stesso nome per la casella " + idAccount + " e classificazione " + classificazione);
				throw new AurigaMailBusinessException(
						"Presenza di due folder con lo stesso nome per la casella " + idAccount + " e classificazione " + classificazione);
			}
		} catch (Exception e) {
			log.error("Impossibile trovare la folder avente classificazione " + classificazione + " per la casella: " + idAccount, e);
			throw e;
		}
		return listaRis.getData().get(0);
	}

	/**
	 * Metodo che restituisce le folder aventi classificazione che inizia per {@link classificazione} e associati all'account avente id {@link idAccount}
	 * 
	 * @param idAccount
	 * @param classificazione
	 * @return
	 * @throws Exception
	 */

	public List<TFolderCaselleBean> ricavaListaFolderClassificazionePrefisso(String idAccount, String classificazione) throws Exception {
		Session lSession = null;
		try {
			lSession = HibernateUtil.begin();
			return ricavaListaFolderClassificazionePrefissoInSession(idAccount, classificazione, lSession);
		} catch (Exception exception) {
			log.error("Errore: " + exception.getMessage(), exception);
			throw exception;
		} finally {
			try {
				HibernateUtil.release(lSession);
			} catch (Exception exception) {
				log.error("Errore: " + exception.getMessage(), exception);
			}
		}
	}

	/**
	 * Metodo che restituisce le folder aventi classificazione che inizia per {@link classificazione} e associati all'account avente id {@link idAccount}
	 * 
	 * @param idAccount
	 * @param classificazione
	 * @param session
	 * @return
	 * @throws Exception
	 */

	public List<TFolderCaselleBean> ricavaListaFolderClassificazionePrefissoInSession(String idAccount, String classificazione, Session session)
			throws Exception {
		List<TFolderCaselleBean> listaBean = new ArrayList<>();
		Criteria criteriaFolder = session.createCriteria(TFolderCaselle.class);
		criteriaFolder.add(Restrictions.ilike("classificazione", classificazione, MatchMode.START));
		criteriaFolder.createAlias("mailboxAccount", "mailboxAccount");
		criteriaFolder.add(Restrictions.eq("mailboxAccount.idAccount", idAccount));
		List<TFolderCaselle> listaRis = criteriaFolder.list();
		if (ListUtil.isEmpty(listaRis)) {
			log.error("Nessuna directory prevista per questa casella: " + idAccount);
			throw new AurigaMailBusinessException("Nessuna directory prevista per questa casella: " + idAccount);
		}
		for (TFolderCaselle folder : listaRis) {
			TFolderCaselleBean bean = (TFolderCaselleBean) UtilPopulate.populate((TFolderCaselle) folder, TFolderCaselleBean.class,
					new TFolderCaselleToTFolderCaselleBean());
			listaBean.add(bean);
		}
		return listaBean;
	}

	public TValDizionarioBean ricavaDizionarioDaValore(Dizionario diz) throws Exception {
		TFilterFetch<TValDizionarioBean> ff = new TFilterFetch<>();
		TValDizionarioBean fil = new TValDizionarioBean();
		fil.setValore(diz.getValue());
		ff.setFilter(fil);
		List<TValDizionarioBean> valori = null;
		try {
			valori = ((DaoTValDizionario) DaoFactory.getDao(DaoTValDizionario.class)).search(ff).getData();
		} catch (Exception e) {
			log.error(ERRORE_DIZIONARIO, e);
			throw e;
		}
		if (ListUtil.isEmpty(valori) || valori.size() > 1) {
			log.error(ERRORE_DIZIONARIO);
			throw new AurigaMailBusinessException(ERRORE_DIZIONARIO);
		}
		return valori.get(0);
	}
	
	/**
	 * 
	 * @param autoProtConfig
	 * @param mail
	 * @return
	 */
	public void registrazioneAutoProt (ProtocolloAutomaticoConfigBean autoProtConfig, TEmailMgoBean mail) throws Exception {
		
		//TODO: Il Bean ProtocolloAutomaticoConfigBean contiene gli attributi necessari per poter chiamare una store. 
		//      Viene inizializzato come bean di config mailBusiness.xml su mailUI. In caso servissero ulteriori valori basta aggiungerli
		//      alla classe e alle configurazioni. NB: Nel regno delle mail usiamo il MailLoginBean al posto del AurigaLoginBean.
		
		String idEmail =  mail.getIdEmail();
		EmailAttachsBean attachsBean;
		attachsBean = getAttachByIdEmail(idEmail); // recupero degli allegati della mail nel caso servissero per la Prot.
			// Recupero del file di segnatura se mail interoperabile.
		    if (mail.getCategoria().equals("INTEROP_SEGN") && attachsBean != null) {
		    	String lFileSegnaturaXml = recuperaSegnatura(attachsBean);
				if (lFileSegnaturaXml == null) {
					throw new MailAutoProtocolException("Manca il file di segnatura");
				}
			}
		    //TODO: Parte della decompressione degli eventuali allegati compressi. Bisogna finire il metodo.
		    // A metodo ultimato (e SE ultimato) aggiungerei comunque un flg lato DB per usarla o meno.

		    //		    decompressioneArchivi(attachsBean);
		    
		    
		    //TODO: Parte relativa alla preparazione degli elementi necessari e chiamata alla store relativa  
	}
	
	/**
	 * recupera la segnatura di una mail interoperabile.
	 * @param attachments
	 * @return
	 */
	private String recuperaSegnatura(EmailAttachsBean attachments) {
		List<MailAttachmentsInfoBean> mailAttachments = attachments.getMailAttachments();
		List<File> files = attachments.getFiles();
		for (int i=0; i < mailAttachments.size(); i++) {
			if (mailAttachments.get(i).getFilename().equalsIgnoreCase("segnatura.xml")) {
				try {
					return IOUtils.toString(new FileInputStream(files.get(i)));
				} catch (Exception e) {
					log.error("File segnatura non trovato/recuperato",e);
				}
			}
		}
		return null;
	}
	
	/**
	 * metodo per la decompressione degli archivi presenti come allegati di una mail
	 * @param attachments
	 */
	private void decompressioneArchivi(EmailAttachsBean attachments) {
		List<MailAttachmentsInfoBean> mailAttachments = attachments.getMailAttachments();
		List<File> files = attachments.getFiles();
		for (int i=0; i < mailAttachments.size(); i++) {
			String mimetype = mailAttachments.get(i).getMimetype().split(";")[0]; // Recupero il mimetype
			String filename = mailAttachments.get(i).getFilename();
			if(ArchiveUtils.checkArchive(mimetype, FilenameUtils.getExtension(filename))) {
				String outPath = TEMP_DIR + File.separator + filename + "_dir";
				try {
					DecompressioneArchiviBean lDecompressioneArchiviBean = ArchiveUtils.getArchiveContent(files.get(i), mimetype, outPath);
					// controllo se non ci sono stati errori nella decompressione degli archivi oppure se i file non sono protetti da password.
					if ((lDecompressioneArchiviBean.getErroriArchivi() == null || lDecompressioneArchiviBean.getErroriArchivi().isEmpty()) &&
							lDecompressioneArchiviBean.getErroriFiles() == null || lDecompressioneArchiviBean.getErroriFiles().isEmpty()) {
						List<File> extractedFile = lDecompressioneArchiviBean.getFilesEstratti();
						//TODO: calcola l'impronta dei nuovi file decompressi, sostituzione archivio. 
						/**
						 * 1) creare uno StorageServiceImpl con configurazioni che puntino allo storage dei file temporanei, in modo da salvare i file estratti
						 * 2) A monte doveva essere già creato DocumentoOutXmlBean, quindi chiamata alla store che dia quell elemento
						 * 3) Si va a modificare DocumentoOutXmlBean e gli allegati corrispondenti a seconda dell estrazione o meno
						 * 4) Si chiama la nuova ipotetica store.
						 */
						
						
					}
					
				} catch (Exception e) {
					log.error("Errore nella decompressione dell'archivio",e);
				}
			}
		}
	}

}
