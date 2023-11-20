/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import it.eng.aurigamailbusiness.bean.EmailGroupBean;
import it.eng.aurigamailbusiness.bean.EmailSentReferenceBean;
import it.eng.aurigamailbusiness.bean.RispostaInoltro;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TDestinatariEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TRelEmailFolderBean;
import it.eng.aurigamailbusiness.database.dao.DaoTAttachEmailMgo;
import it.eng.aurigamailbusiness.database.dao.DaoTDestinatariEmailMgo;
import it.eng.aurigamailbusiness.database.dao.DaoTEmailMgo;
import it.eng.aurigamailbusiness.database.dao.MailProcessorService;
import it.eng.aurigamailbusiness.database.dao.MailSenderService;
import it.eng.aurigamailbusiness.database.mail.TAttachEmailMgo;
import it.eng.aurigamailbusiness.database.mail.TDestinatariEmailMgo;
import it.eng.aurigamailbusiness.database.mail.TEmailMgo;
import it.eng.aurigamailbusiness.database.mail.TRelEmailFolder;
import it.eng.aurigamailbusiness.database.mail.TRelEmailFolderId;
import it.eng.aurigamailbusiness.database.utility.DaoFactory;
import it.eng.aurigamailbusiness.database.utility.bean.listafolderUtente.SpostaEmailBean;
import it.eng.aurigamailbusiness.database.utility.send.FolderEmail;
import it.eng.aurigamailbusiness.database.utility.send.SendUtils;
import it.eng.aurigamailbusiness.exception.AttachLimitExceedException;
import it.eng.aurigamailbusiness.exception.AurigaMailBusinessException;
import it.eng.aurigamailbusiness.exception.NoAdressesException;
import it.eng.aurigamailbusiness.exception.TooManyAdressesException;
import it.eng.aurigamailbusiness.sender.storage.StorageCenter;
import it.eng.aurigamailbusiness.utility.cryptography.InvalidEncryptionKeyException;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.utility.storageutil.StorageService;

/**
 * Classe che effettua l'invio sincrono delle mail {@link listaMailDaInviare},
 * <br>
 * In caso di errori nell'invio effettua fino a 3 tentativi di re-invio. <br>
 * Sono valorizzati {@link listaMailInviate} e {@link listaMailNonInviate} con
 * la lista dei bean inviati e non
 *
 */

public class Send {

	private String idAccount;
	private SubjectBean subjectBean;
	private List<SenderBean> listaMailSpedite = new ArrayList<SenderBean>();

	public String getIdAccount() {
		return idAccount;
	}

	public List<SenderBean> getListaMailSpedite() {
		return listaMailSpedite;
	}

	private static Logger mLogger = LogManager.getLogger(Send.class);

	public Send(String pStrIdAccount, SubjectBean pSubjectBean) {
		idAccount = pStrIdAccount;
		subjectBean = pSubjectBean;
		SubjectUtil.subject.set(subjectBean);
	}

	/**
	 * Metodo che effettua il tentativo di invio della lista di mail in input
	 * 
	 * @param pListaMailDaInviare
	 * @return la lista delle mail non inviate
	 */

	protected List<SenderBean> send(List<SenderBean> pListaMailDaInviare, Boolean pInvia, Boolean pCancellaDopoInvio) {

		// azzero errori e lista mail non inviate
		// la lista delle mail inviate invece deve rimanere inalterata

		Map<String, String> unsentedError = new HashMap<String, String>();
		List<SenderBean> lListaMailNonSpedite = new ArrayList<SenderBean>();

		for (SenderBean toSend : pListaMailDaInviare) {
			String idMail = toSend.getIdEmail();
			if (mLogger.isDebugEnabled()) {
				mLogger.debug("Inizio l'invio della mail avente id " + idMail);
			}
			try {
				if (pInvia) {
					// se devo cancellare la mail allora non devo salvare il
					// file nello storage
					EmailSentReferenceBean bean = new MailSenderService().sendAndSaveMailFileInStorage(toSend,
							!pCancellaDopoInvio);
					for (SenderBean lSenderBean : bean.getSentMails()) {
						if (!lSenderBean.getIsSent()) {
							lListaMailNonSpedite.add(lSenderBean);
						} else {
							mLogger.info("Mail avente id " + lSenderBean.getIdEmail() + " inviata");
							listaMailSpedite.add(lSenderBean);
						}
					}
				} else {
					throw new AurigaMailBusinessException("Non lo voglio inviare per i test");
				}
			} catch (AurigaMailBusinessException e) {
				mLogger.error("AurigaMailBusinessException nell'invio della mail avente id " + idMail, e);
				String stackTrace = ExceptionUtils.getStackTrace(e);
				unsentedError.put(idMail, stackTrace);
				lListaMailNonSpedite.add(toSend);
			} catch (AttachLimitExceedException e) {
				mLogger.error("AttachLimitExceedException nell'invio della mail avente id " + idMail, e);
				String stackTrace = ExceptionUtils.getStackTrace(e);
				unsentedError.put(idMail, stackTrace);
				lListaMailNonSpedite.add(toSend);
			} catch (InvalidEncryptionKeyException e) {
				mLogger.error("InvalidEncryptionKeyException nell'invio della mail avente id " + idMail, e);
				String stackTrace = ExceptionUtils.getStackTrace(e);
				unsentedError.put(idMail, stackTrace);
				lListaMailNonSpedite.add(toSend);
			} catch (NoAdressesException e) {
				mLogger.error("NoAdressesException nell'invio della mail avente id " + idMail, e);
				String stackTrace = ExceptionUtils.getStackTrace(e);
				unsentedError.put(idMail, stackTrace);
				lListaMailNonSpedite.add(toSend);
			} catch (TooManyAdressesException e) {
				mLogger.error("TooManyAdressesException nell'invio della mail avente id " + idMail, e);
				String stackTrace = ExceptionUtils.getStackTrace(e);
				unsentedError.put(idMail, stackTrace);
				lListaMailNonSpedite.add(toSend);
			} catch (Exception e) {
				mLogger.error("Eccezione nell'invio della mail avente id " + idMail + ": " + e.getMessage(), e);
				String stackTrace = ExceptionUtils.getStackTrace(e);
				unsentedError.put(idMail, stackTrace);
				lListaMailNonSpedite.add(toSend);
			}
		}
		// aggiornamento avvertimenti mail non inviate
		updateAvvertimenti(unsentedError, lListaMailNonSpedite);
		// aggiorno mail correttamente inviate
		DaoTEmailMgo daoMail = (DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class);
		MailProcessorService processor = new MailProcessorService();
		for (SenderBean sender : listaMailSpedite) {
			try {
				// la lista delle mail inviate invece è gestita nel ritorno del
				// metodo, perchè ci sono più tentativi di re-invio
				if (pCancellaDopoInvio) {
					// rimuovo da database la mail appena inviata
					deleteMail(daoMail, sender);
				} else {

					// ogni azione è atomica, non necessita di un'unica
					// transazione
					// si aggiornano i flag di inoltro e risposta solo
					// nelle mail originali

					String warn = null;

					// try catch specifico perchè in caso di errore le
					// informazioni aggiornate della mail devono essere
					// comunque salvate
					// gli errori sono salvati come avvertimenti nella
					// mail principale
					try {
						if (sender.getRispInol() != null) {
							if (sender.getRispInol().getMailOriginaria() != null
									&& StringUtils.isNotBlank(sender.getRispInol().getMailOriginaria().getIdEmail())) {
								if (sender.getRispInol().getRispInol() == RispostaInoltro.RISPOSTA) {
									try {
										processor.updateFlag(sender.getRispInol().getMailOriginaria().getIdEmail(),
												"flgInviataRisposta", true);
									} catch (Exception e) {
										mLogger.warn(
												"Non è possibile aggiornare flgInviataRisposta della mail originaria avente id: "
														+ sender.getRispInol().getMailOriginaria().getIdEmail());
										warn = "Non è possibile aggiornare flgInviataRisposta della mail originaria avente id: "
												+ sender.getRispInol().getMailOriginaria().getIdEmail();
									}
								} else if (sender.getRispInol().getRispInol() == RispostaInoltro.INOLTRO) {
									try {
										processor.updateFlag(sender.getRispInol().getMailOriginaria().getIdEmail(),
												"flgInoltrata", true);
									} catch (Exception e) {
										mLogger.warn(
												"Non è possibile aggiornare flgInoltrata della mail originaria avente id: "
														+ sender.getRispInol().getMailOriginaria().getIdEmail());
										warn = "Non è possibile aggiornare flgInoltrata della mail originaria avente id: "
												+ sender.getRispInol().getMailOriginaria().getIdEmail();
									}
								}

							} else if (sender.getRispInol().getRispInol() == RispostaInoltro.INOLTRO
									&& sender.getRispInol().getMailOrigInoltroMassivo() != null) {
								// inoltro massivo
								for (TEmailMgoBean email : sender.getRispInol().getMailOrigInoltroMassivo()) {
									try {
										processor.updateFlag(email.getIdEmail(), "flgInoltrata", true);
									} catch (Exception e) {
										if (StringUtils.isBlank(warn)) {
											warn = "";
										}
										mLogger.warn(
												"Non è possibile aggiornare flgInoltrata della mail originaria avente id: "
														+ email.getIdEmail() + " - Inoltro massivo");
										warn += "Non è possibile aggiornare flgInoltrata della mail originaria avente id: "
												+ email.getIdEmail() + " - Inoltro massivo" + System.lineSeparator();
									}
								}
							}
						}
					} catch (Exception e) {
						mLogger.error(
								"Si è verificato un errore nell'aggiornamento dei flag inoltro/risposta delle mail originarie",
								e);
						warn = "Non è possibile aggiornare i flag inoltro/risposta delle mail originarie";
					}

					// spostamento nelle folder delle mail inviate
					// in caso di errore salvo l'avvertimento ma non
					// blocco l'operazione, l'invio infatti è stato
					// eseguito correttamente

					try {

						List<String> emailDaSpostare = new ArrayList<String>();
						emailDaSpostare.add(sender.getIdEmail());
						SpostaEmailBean spostaEmailBean = new SpostaEmailBean();
						spostaEmailBean.setIdEmails(emailDaSpostare);
						spostaEmailBean.setClassificazioneFrom(FolderEmail.STANDARD_FOLDER_USCITA.getValue());
						spostaEmailBean.setFolderArrivo(SendUtils.retrieveFolderForEmailMailInviata(sender));
						EmailGroupBean out = processor.spostaEmailFolderMultiple(spostaEmailBean);
						if (out.getIdEmails() != null && !out.getIdEmails().isEmpty()) {
							for (Integer index = 0; index < out.getIdEmails().size(); index++) {
								if (StringUtils.isBlank(warn)) {
									warn = "";
								}
								warn += "Impossibile spostare la mail nella folder di arrivo: "
										+ spostaEmailBean.getFolderArrivo() + System.lineSeparator();
							}
						}

					} catch (Exception e) {
						mLogger.warn("Eccezione nello spostamento della mail nella folder");
					}

					// aggiorno la mail

					Session session = null;

					try {

						session = HibernateUtil.begin();
						Transaction transaction = session.beginTransaction();
						TEmailMgo mail = daoMail.getTEmailMgoInSession(sender.getIdEmail(), session);
						StorageCenter storage = new StorageCenter();
						StorageService globalStorageService = storage
								.getGlobalStorage(SendUtils.ricavaMailboxFromIdAccount(idAccount).getIdMailbox());

						String uriToDelete = null;

						if (StringUtils.isNotBlank(sender.getUriSavedMimeMessage())) {
							// ricavo global storage
							// mail da aggiornare nello storage, in modo
							// da avere il file realmente inviato
							// il riferimento salvato in database può
							// essere stato salvato precedentemente
							// all'invio e quindi non essere aggiornato
							if (globalStorageService.extractFile(sender.getUriSavedMimeMessage()) != null) {
								uriToDelete = mail.getUriEmail();
								mail.setUriEmail(sender.getUriSavedMimeMessage());
							}
						}

						if (sender.getMessageId() != null) {
							mail.setMessageId(sender.getMessageId());
						}
						mail.setStatoConsolidamento(null);

						// aggiorno eventuali errori e avvertimenti
						// riscontrati, se non ce ne sono si resettano
						// quelli riscontrati eventualmente nel
						// precedente invio
						mail.setAvvertimenti(warn);

						daoMail.updateInSession(mail, session);
						session.flush();
						transaction.commit();

						try {
							// cancello il precedente riferimento
							// salvato in database
							if (StringUtils.isNotBlank(uriToDelete)
									&& !uriToDelete.equalsIgnoreCase(sender.getUriSavedMimeMessage())
									&& globalStorageService.extractFile(uriToDelete) != null) {
								globalStorageService.delete(uriToDelete);
							}
						} catch (Exception e) {
							mLogger.warn("Errore nella cancellazione dell'URI della mail inviata", e);
						}

						mLogger.info(
								"Salvataggio mail inviata avente id " + sender.getIdEmail() + " eseguito con successo");
					} catch (Exception e) {
						mLogger.error("Errore salvataggio mail inviata avente id " + sender.getIdEmail(), e);
					} finally {
						releaseHibernateSession(session);
					}

				}
			} catch (Exception e) {
				mLogger.error("Eccezione " + (pCancellaDopoInvio ? "nella cancellazione" : "nell'aggiornamento")
						+ "della mail post invio", e);
			}
		}
		return lListaMailNonSpedite;
	}

	/**
	 * Metodo che rimuove in database il record della mail e in cascata rimuove
	 * tutti i record associate nelle mail di destinatari, allegati, relazioni e
	 * infine rimuove la mail dallo storage
	 * 
	 * @param daoMail
	 * @param sender
	 */
	private void deleteMail(DaoTEmailMgo daoMail, SenderBean sender) {
		Session session = null;
		try {
			// apro transizione per eliminare relazioni
			// della folder e la mail stessa
			session = HibernateUtil.begin();
			Transaction transaction = session.beginTransaction();
			TEmailMgoBean mail = daoMail.getInSession(sender.getIdEmail(), session);
			TRelEmailFolderBean lTRelEmailFolderBean = SendUtils.retrieveFolderInSession(idAccount, mail,
					FolderEmail.STANDARD_FOLDER_USCITA, session);
			TRelEmailFolderId lTRelEmailFolderId = new TRelEmailFolderId(lTRelEmailFolderBean.getIdFolderCasella(),
					lTRelEmailFolderBean.getIdEmail());
			TRelEmailFolder lTRelEmailFolder = (TRelEmailFolder) session.load(TRelEmailFolder.class,
					lTRelEmailFolderId);
			if (mLogger.isInfoEnabled()) {
				mLogger.info(
						"Devo cancellare la relazione fra la folder con id " + lTRelEmailFolderBean.getIdFolderCasella()
								+ " e la mail con id" + lTRelEmailFolderBean.getIdEmail());
			}
			session.delete(lTRelEmailFolder);
			if (mLogger.isInfoEnabled()) {
				mLogger.info("Relazione rimossa");
			}
			TFilterFetch<TAttachEmailMgoBean> filter = new TFilterFetch<TAttachEmailMgoBean>();
			TAttachEmailMgoBean lTAttachEmailMgoBean = new TAttachEmailMgoBean();
			lTAttachEmailMgoBean.setIdEmail(sender.getIdEmail());
			filter.setFilter(lTAttachEmailMgoBean);
			List<TAttachEmailMgoBean> lTAttachEmailMgoBeanList = ((DaoTAttachEmailMgo) DaoFactory
					.getDao(DaoTAttachEmailMgo.class)).searchInSession(filter, session).getData();
			if (lTAttachEmailMgoBeanList != null) {
				for (TAttachEmailMgoBean lTAttachEmailMgoBean2 : lTAttachEmailMgoBeanList) {
					TAttachEmailMgo lTAttachEmailMgo = (TAttachEmailMgo) session.load(TAttachEmailMgo.class,
							lTAttachEmailMgoBean2.getIdAttachEmail());
					if (mLogger.isInfoEnabled()) {
						mLogger.info("Devo cancellare l'allegato con id: " + lTAttachEmailMgoBean2.getIdAttachEmail()
								+ " e id mail " + lTAttachEmailMgoBean2.getIdEmail());
					}
					session.delete(lTAttachEmailMgo);
					if (mLogger.isInfoEnabled()) {
						mLogger.info("Allegato rimosso");
					}
				}
			}
			TFilterFetch<TDestinatariEmailMgoBean> filterDestinatari = new TFilterFetch<TDestinatariEmailMgoBean>();
			TDestinatariEmailMgoBean lTDestinatariEmailMgoBean = new TDestinatariEmailMgoBean();
			lTDestinatariEmailMgoBean.setIdEmail(sender.getIdEmail());
			filterDestinatari.setFilter(lTDestinatariEmailMgoBean);
			List<TDestinatariEmailMgoBean> destinatari = ((DaoTDestinatariEmailMgo) DaoFactory
					.getDao(DaoTDestinatariEmailMgo.class)).searchInSession(filterDestinatari, session).getData();

			if (destinatari != null) {
				for (TDestinatariEmailMgoBean lTDestinatariEmailMgoBean2 : destinatari) {
					TDestinatariEmailMgo lTDestinatariEmailMgo = (TDestinatariEmailMgo) session
							.load(TDestinatariEmailMgo.class, lTDestinatariEmailMgoBean2.getIdDestinatarioEmail());
					if (mLogger.isInfoEnabled()) {
						mLogger.info("Devo cancellare il destinatario con id: "
								+ lTDestinatariEmailMgoBean2.getIdDestinatarioEmail() + " e id mail "
								+ lTDestinatariEmailMgoBean2.getIdEmail());
					}
					session.delete(lTDestinatariEmailMgo);
					if (mLogger.isInfoEnabled()) {
						mLogger.info("Destinatario rimosso");
					}
				}
			}
			TEmailMgo lTEmailMgo = daoMail.getTEmailMgoInSession(sender.getIdEmail(), session);
			if (mLogger.isInfoEnabled()) {
				mLogger.info("Devo cancellare ma mail con id: " + sender.getIdEmail());
			}
			String uriToDelete = lTEmailMgo.getUriEmail();
			session.delete(lTEmailMgo);
			if (mLogger.isInfoEnabled()) {
				mLogger.info("Mail rimossa");
			}
			transaction.commit();
			// cancello anche la mail dallo storage
			try {
				if (StringUtils.isNotBlank(uriToDelete)) {
					StorageCenter storage = new StorageCenter();
					StorageService globalStorageService = storage.getGlobalStorage(
							SendUtils.ricavaMailboxFromIdAccountInSession(idAccount, session).getIdMailbox());
					if (globalStorageService.extractFile(uriToDelete) != null) {
						if (mLogger.isInfoEnabled()) {
							mLogger.info("Devo rimuovere file emil: " + uriToDelete);
						}
						globalStorageService.delete(uriToDelete);
					}
					if (mLogger.isInfoEnabled()) {
						mLogger.info("Rimosso file eml dallo storage");
					}
				}
			} catch (Exception e) {
				mLogger.warn("Errore nella cancellazione del file eml della mail inviata", e);
			}

			if (mLogger.isInfoEnabled()) {
				mLogger.info("Cancellazione mail con id " + sender.getIdEmail() + " eseguita con successo");
			}
		} catch (Exception e) {
			mLogger.error("Errore cancellazione mail inviata avente id " + sender.getIdEmail() + ": " + e.getMessage(),
					e);
		} finally {
			releaseHibernateSession(session);
		}
	}

	/**
	 * Metodo che aggiorna avvertimenti per le mail non inviate
	 * 
	 * @param unsentedError
	 * @param lListaMailNonSpedite
	 */
	private void updateAvvertimenti(Map<String, String> unsentedError, List<SenderBean> lListaMailNonSpedite) {
		for (SenderBean es : lListaMailNonSpedite) {
			DaoTEmailMgo daoMail = (DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class);
			String idMail = es.getIdEmail();
			if (idMail != null) {
				Session session = null;
				try {
					session = HibernateUtil.begin();
					Transaction transaction = session.beginTransaction();
					TEmailMgo emailMgo = daoMail.getTEmailMgoInSession(idMail, session);
					String errorMessage = unsentedError.get(idMail);
					if (StringUtils.isNotEmpty(errorMessage)) {
						Integer index = errorMessage.toLowerCase().indexOf("fallito invio");
						if (index != -1) {
							// per agevolare gli utenti elimino l'eccezione e
							// inizio il messaggio con il dettaglio
							// dell'eccezione
							errorMessage = errorMessage.substring(index);
						}
						emailMgo.setAvvertimenti(errorMessage);
						daoMail.updateInSession(emailMgo, session);
						session.flush();
						transaction.commit();
					}
				} catch (Exception e) {
					mLogger.error("Errore aggiornamento avvertimenti in database per la mail non inviata avente id "
							+ idMail + ": " + e.getMessage(), e);
				} finally {
					releaseHibernateSession(session);
				}
			}
		}
	}

	/**
	 * Rilascio della sessione Hibernate in input
	 * 
	 * @param session
	 */
	private void releaseHibernateSession(Session session) {
		if (session != null) {
			try {
				HibernateUtil.release(session);
			} catch (Exception e) {
				mLogger.error("Errore release sessione Hibernate", e);
			}
		}
	}
}
