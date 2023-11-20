/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.inject.Named;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import it.eng.database.dao.MailboxAccountConfigDAOImpl;
import it.eng.database.dao.MessaggiMailDAOImpl;
import it.eng.database.dao.TNotifAllEmailDAOImpl;
import it.eng.database.dao.TNotifAllEmailInvDAOImpl;
import it.eng.database.dao.TNotifEmailInviateDAOImpl;
import it.eng.database.dao.TRichNotifEmailDAOImpl;
import it.eng.database.utility.HibernateUtil;
import it.eng.entity.MailboxAccountConfig;
import it.eng.entity.TNotifAllEmail;
import it.eng.entity.TNotifAllEmailInv;
import it.eng.entity.TNotifEmailInviate;
import it.eng.entity.TRichNotifEmail;
import it.eng.job.util.StorageImplementation;
import it.eng.simplesendermail.service.bean.SmtpSenderBean;
import it.eng.utility.jobmanager.quartz.AbstractJob;
import it.eng.utility.jobmanager.quartz.Job;

@Job(type = "SendNotificheEmailJob")
@Named
public class SendNotificheEmailJob extends AbstractJob<String> {
	
	private static final Logger logger = Logger.getLogger(SendNotificheEmailJob.class);
	
	private static final String JOBATTRKEY_ENTITY_PACKAGE = "entityPackage";
	private static final String JOBATTRKEY_HIBERNATE_CONFIG_FILE = "hibernateConfigFile";
	private static final String JOBATTRKEY_TRY_NUMBER = "tryNumber";
	private static final String JOB_CLASS_NAME = SendNotificheEmailJob.class.getName();

	@Override
	protected List<String> load() {
		ArrayList<String> ret = new ArrayList<String>();
		ret.add(JOB_CLASS_NAME);
		
		String entityPackage = (String) getAttribute(JOBATTRKEY_ENTITY_PACKAGE);
		String hibernateConfigFile = (String) getAttribute(JOBATTRKEY_HIBERNATE_CONFIG_FILE);
		HibernateUtil.setEntitypackage( entityPackage );
		HibernateUtil.buildSessionFactory( hibernateConfigFile, JOB_CLASS_NAME );
		
		return ret;
	}
	
	@Override
	protected void execute(String obj) {
		
		org.hibernate.Session sessionJob = null;
		try {
			logger.info("Inizio esecuzione job SendNotificheEmailJob");
			
			String trynumberStr = (String) getAttribute(JOBATTRKEY_TRY_NUMBER);
			int tryNumber = 5;
			if (trynumberStr != null && !trynumberStr.isEmpty()) {
				tryNumber = Integer.parseInt(trynumberStr);
			}
			logger.info("numero di tentativi massimo: " + tryNumber);
			
			sessionJob = HibernateUtil.openSession(JOB_CLASS_NAME);
			logger.info("Aperta sessione a DB");
			
			TRichNotifEmailDAOImpl tRichNotifEmailDao = new  TRichNotifEmailDAOImpl();
			MessaggiMailDAOImpl messaggiMailDao = new MessaggiMailDAOImpl();
			MailboxAccountConfigDAOImpl mailboxAccountConfigDao = new MailboxAccountConfigDAOImpl();
			TNotifAllEmailDAOImpl tNotifAllEmailDao = new TNotifAllEmailDAOImpl();
			TNotifAllEmailInvDAOImpl tNotifAllEmailInvDao = new TNotifAllEmailInvDAOImpl();
			TNotifEmailInviateDAOImpl tNotifEmailInviateDao = new TNotifEmailInviateDAOImpl();
			
			// acquisizione lista email da inviare
			List<TRichNotifEmail> listaEmail = tRichNotifEmailDao.getRecordsByStato("TO_SEND", tryNumber, sessionJob);
			logger.info("Email da invare: " + listaEmail.size());
			
			if (listaEmail.size() > 0) {
				//SimpleSenderMail simpleSenderMail = new SimpleSenderMail();
				Properties paramMail = new Properties();
				
				for (TRichNotifEmail record : listaEmail) {

					try { 
						boolean flagError = false;
						String msgErrore = null;
						
						String accountMittente = null;
						
						if (record.getIdAccountMitt() != null && !record.getIdAccountMitt().equals("")) {
							logger.info("Ricerca account per idAccountMitt: " + record.getIdAccountMitt());
							
							// ID_ACCOUNT_MITT valorizzato: cerco mail censita in MAILBOX_ACCOUNT
							accountMittente = getMailboxAccount(messaggiMailDao, record.getIdAccountMitt(), sessionJob);
							
							if (accountMittente == null || accountMittente.equals("")) {
								flagError = true;
								msgErrore = "Nessun indirizzo trovato In MAILBOX_ACCOUNT con ID_ACCOUNT_MITT: " + record.getIdAccountMitt();
								logger.info(msgErrore);
							}
							
							// acquisizione parametri di configurazione smtp
							//smptSenderBean = getMailboxAccountConfig(mailboxAccountConfigDao, record.getIdAccountMitt());
							paramMail = getMailboxAccountConfigProp(mailboxAccountConfigDao, record.getIdAccountMitt(), sessionJob);
							
							logger.info("configurazioni mail: " + paramMail.get("mail.smtp.auth") + " " + paramMail.get("mail.username") +
										paramMail.get("mail.password") + " " + paramMail.get("mail.smtp.host") + " " + paramMail.get("mail.smtp.port") + 
										paramMail.get("mail.smtp.starttls.enable") + " " + paramMail.get("mail.smtp.ssl.trust"));
						}
						else {
							// ID_ACCOUNT_MITT null: uso valore in colonna ACCOUNT_MITTENTE
							accountMittente = record.getAccountMittente();
							logger.info("Account mittente: " + accountMittente + " in T_RICH_NOTIF_EMAIL");
							
							if (accountMittente == null || accountMittente.equals("")) {
								flagError = true;
								msgErrore = "ACCOUNT_MITTENTE non valorizzato in T_RICH_NOTIF_EMAIL";
							}
							
							// imposto parametri smtp
							paramMail.put("mail.smtp.auth", "false");
							paramMail.put("mail.smtp.host", record.getSmtpEndpointAccountMitt());
							paramMail.put("mail.smtp.port", record.getSmtpPortAccountMitt());
							paramMail.put("mail.username", "");
							paramMail.put("mail.password", "");
							paramMail.put("mail.smtp.starttls.enable", "true");
							paramMail.put("mail.smtp.ssl.trust", record.getSmtpEndpointAccountMitt());
							
							logger.info("Parametri smtp: " + record.getSmtpEndpointAccountMitt() + " - " + record.getSmtpPortAccountMitt());
						}
						
						// destinatari
						List<String> listDestinatari = generaDestinatari(record.getDestNotifica());
						InternetAddress[] address = new InternetAddress[listDestinatari.size()];
						if (listDestinatari.size() == 0) {
							// nessun destinatario
							flagError = true;
							msgErrore = "Destinatari non presenti in DEST_NOTIFICA";
						}
						else {
						    for (int i = 0; i < listDestinatari.size(); i++) {
						        address[i] = new InternetAddress(listDestinatari.get(i));
						    }
						}
						
						// definizione cc
						List<String> listDestinatariCc = generaDestinatari(record.getDestCcNotifica());
						InternetAddress[] addressCC = new InternetAddress[listDestinatariCc.size()];
						if (listDestinatariCc.size() > 0) {
							for (int i = 0; i < listDestinatariCc.size(); i++) {
								if (listDestinatariCc.get(i) != null)
									addressCC[i] = new InternetAddress(listDestinatariCc.get(i));
						    }
						}
						
						
						
							if (!flagError) {
								logger.info("provo ad effettuare l'invio della maill.... ("+record.getIdRichNotifica()+")");
								final String username = paramMail.getProperty("mail.username");
								final String password = paramMail.getProperty("mail.password");
								
								Session session = Session.getInstance(paramMail,
										new javax.mail.Authenticator() {
											protected PasswordAuthentication getPasswordAuthentication() {
												return new PasswordAuthentication(username, password);
											}
					            		});
								
								if (logger.isDebugEnabled()) {
									StringWriter writer = new StringWriter();
									paramMail.list(new PrintWriter(writer));
									logger.info("paramMail: " + writer.getBuffer().toString());
									
									if (address !=null)
										logger.info("address: " + InternetAddress.toString(address));
								}
								
								Message message = new MimeMessage(session);
								message.setFrom(new InternetAddress(accountMittente));
								message.setRecipients(
										Message.RecipientType.TO,
										address
									);
								if (addressCC !=null &&addressCC.length > 0 && isNotNullAdress(addressCC)) {
									logger.info("Sono presenti utenti in CC");
									message.setRecipients(
											Message.RecipientType.CC,
											addressCC
										);
								}
								
								String body = "";
								if (record.getBody() != null && !record.getBody().isEmpty()) {
									body = record.getBody();
									logger.info("body email valorizzato " + body);
								}
								
								message.setSubject(record.getSubject());
								//message.setText(record.getBody());
								//message.setContent(body, "text/html");
								
								// acquisizione eventuali allegati
								List<TNotifAllEmail> allegatiList = tNotifAllEmailDao.getAllegatiByIdRich(record.getIdRichNotifica(), sessionJob);
								logger.info("Allegati da inviare: " + allegatiList.size());
								
								// verifico se presente record con stesso id in T_NOTIF_EMAIL_INVIATE
								boolean check = checkInsertNotifica(tNotifEmailInviateDao, record.getIdRichNotifica(), sessionJob);
								logger.info("id richiesta " + record.getIdRichNotifica() + " presente " + check);
								
								if (!check) {
									logger.info("Record non presente in T_NOTIF_EMAIL_INVIATE");
									
									if (allegatiList.size() > 0) {
										try {
											Multipart multipart = new MimeMultipart();
											logger.info("allegati presenti - inizio chiamata a storage per recuperare file");
											
											for (TNotifAllEmail item : allegatiList) {
												if (!item.getUri().isEmpty() || item.getUri() != null) {
													File contenutoAllegato = StorageImplementation.getStorage().extractFile(item.getUri());
													logger.info("recuperato file da url " + item.getUri());
													
													MimeBodyPart attachmentPart = new MimeBodyPart();
													attachmentPart.attachFile(contenutoAllegato);
													attachmentPart.setFileName(item.getDisplayFilename());
													
													multipart.addBodyPart(attachmentPart);
													
													logger.info("aggunto alla mail allegato " + item.getDisplayFilename());
												}
											}
											
											BodyPart htmlBodyPart = new MimeBodyPart(); //4
											htmlBodyPart.setContent(body , "text/html"); //5
											multipart.addBodyPart(htmlBodyPart);
											
											logger.info("aggiunto BodyPart html");
											
											message.setContent(multipart);
										} catch (Exception es) {
											// aggiornamento tabella allegati richiesta con messaggio di errore
											updateTNotifAllEmail(tNotifAllEmailDao, allegatiList, ExceptionUtils.getFullStackTrace(es), sessionJob);
											
											// aggiornamento tabella richeista con messggio di errore allegati
											updateTRichNotifEmail(tRichNotifEmailDao, record, ExceptionUtils.getFullStackTrace(es), sessionJob);
											
											logger.error("Errore: "+ es.getLocalizedMessage(), es);
										}
									}
									else {
										// non ci sono allegati body con solo messaggio html
										message.setContent(body, "text/html");
									}
									
									Transport.send(message);
									
									logger.info("email inviate correttamente");
									
									//
									// email inviata correttamente - procedo con insert e delete
									//
									
									boolean checkAll = checkInsertAllegatiNotifica(tNotifAllEmailInvDao, record.getIdRichNotifica(), sessionJob);
									if (!checkAll && allegatiList.size() > 0) {
										// inserimento allegati in tabella T_NOTIF_ALL_EMAIL_INV
										List<TNotifAllEmail> insAllList = allegatiList;
										insertAllegatiInviati(tNotifAllEmailInvDao, insAllList, sessionJob);
										logger.info("Record inseriti in T_NOTIF_ALL_EMAIL_INV");
										
										// cancellazione allegati da tabella T_NOTIF_ALL_EMAIL
										List<TNotifAllEmail> delAllList = allegatiList;
										deleteRecordTNotifAllEmail(tNotifAllEmailDao, delAllList, sessionJob);
										logger.info("Allegati eliminati in T_NOTIF_ALL_EMAIL");
									}
									
									// inserimento record in T_NOTIF_EMAIL_INVIATE
									TRichNotifEmail ins = record;
									insertRecord(tNotifEmailInviateDao, ins, sessionJob);
									logger.info("Record inserito in T_NOTIF_EMAIL_INVIATE");
								}
								
								// cancellazione record in T_RICH_NOTIF_EMAIL
								TRichNotifEmail del = record;
								deleteRecordTRichNotifEmail(tRichNotifEmailDao, del, sessionJob);
								logger.info("Record eliminato in T_RICH_NOTIF_EMAIL perche email inviata precedentemente");
							}
							else {
								logger.info("Non è possibile inviare la mail per mancanza di parametri");
								
								// errore: non sono presenti valori per invio mail
								updateTRichNotifEmail(tRichNotifEmailDao, record, msgErrore, sessionJob);
								
								logger.info("Record aggiornato in T_RICH_NOTIF_EMAIL con messaggio di errore");
							}
					} catch (Exception ex) {
						logger.error("Errore: "+ex.getLocalizedMessage(), ex);
						// errori nell'invio
						updateTRichNotifEmail(tRichNotifEmailDao, record, ExceptionUtils.getFullStackTrace(ex), sessionJob);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Errore nel job notifiche email", e);
		} finally {
			try {
				HibernateUtil.release(sessionJob);
				logger.info("Rilascio sessione DB");
			} catch (Exception e) {
				logger.error("Errore: " + e.getLocalizedMessage(), e);
			}
		}
		
		logger.info("Job SendNotificheEmailJob terminato");
	}

	@Override
	protected void end(String obj) {
		logger.info("fine jbo - chiusura connessione");
		HibernateUtil.closeConnection();
	}
	
	private boolean isNotNullAdress(InternetAddress[] addresses ) {
		boolean result = Boolean.TRUE;
			if (addresses != null && addresses.length >0) {
				for (int i=0; i<addresses.length;i++) {
					if (addresses[i] == null)
						result = Boolean.FALSE;
					
					if (addresses[i] != null && addresses[i].getAddress() == null)
						result = Boolean.FALSE;
				}
			}
		return result;
	}
	
	private String getMailboxAccount(MessaggiMailDAOImpl messaggiMailDao, String idAccount, org.hibernate.Session sessionJob) throws Exception {
		logger.info("Metodo getMailboxAccount");
		
		String accountMittente = messaggiMailDao.getAccount(idAccount, sessionJob);
		logger.info("Account mittente trovato: " + accountMittente);
		
		return accountMittente;
	}
	
	
	
	private SmtpSenderBean getMailboxAccountConfig(MailboxAccountConfigDAOImpl mailboxAccountConfigDao,
															String idAccount,
															org.hibernate.Session sessionJob) throws Exception {
		
		logger.info("Metodo getMailboxAccountConfig");
		
		SmtpSenderBean result = new SmtpSenderBean();
		
		List<MailboxAccountConfig> listConfig = mailboxAccountConfigDao.getRecordsByIdAccount(idAccount, sessionJob);
		logger.info("configurazioni trovate: " + listConfig.size());
		if (listConfig.size() > 0) {
			logger.info("Parametri di configurazione smtp trovati per idAccount " + idAccount);
			for (MailboxAccountConfig item : listConfig) {
				if (item.getIdAccount().equals(idAccount)) {
					boolean isAuth = false;
					if (item.getConfigKey().equalsIgnoreCase("mail.smtp.auth")) {
						isAuth = Boolean.parseBoolean(item.getConfigValue());
						logger.info("Autenticazione richiesta: " + item.getConfigValue());
					}
					result.setAuth(isAuth);
					logger.info("isAuth: " + result.isAuth());
					
					if (item.getConfigKey().equalsIgnoreCase("mail.username")) {
						result.setUsernameAccount(item.getConfigValue());
						logger.info("mail usernname: " + result.getUsernameAccount());
					}
					
					if (item.getConfigKey().equalsIgnoreCase("mail.password")) {
						result.setUsernamePassword(item.getConfigValue());
						logger.info("mail password: " + result.getUsernamePassword());
					}
					
					if (item.getConfigKey().equalsIgnoreCase("mail.smtp.host")) {
						result.setSmtpEndpoint(item.getConfigValue());
						logger.info("smtp endpoint: " + result.getSmtpEndpoint());
					}
					
					if (item.getConfigKey().equalsIgnoreCase("mail.smtp.port")) {
						result.setSmtpPort(Integer.parseInt(item.getConfigValue()));
						logger.info("smtp porta: " + result.getSmtpPort());
					}
					
					boolean isPec = false;
					if (item.getConfigKey().equalsIgnoreCase("mail.account.ispec")) {
						isPec = Boolean.parseBoolean(item.getConfigValue());
					}
					result.setIsPec(isPec);
					logger.info("is pec: " + result.getIsPec());
					
					if (item.getConfigKey().equalsIgnoreCase("mail.smtp.socketFactory.class")) {
						result.setSocketFactoryClass(item.getConfigValue());
						logger.info("socket factory class: " + result.getSocketFactoryClass());
					}
					
					boolean isSocketFallback = false;
					if (item.getConfigKey().equalsIgnoreCase("mail.smtp.socketFactory.fallback")) {
						isSocketFallback = Boolean.parseBoolean(item.getConfigValue());
					}
					result.setSocketFactoryFallback(isSocketFallback);
					logger.info("socket factory fallback: " + result.getSocketFactoryFallback());
					
					boolean isSslEnabled = false;
					if (item.getConfigKey().equalsIgnoreCase("mail.smtp.ssl.enable")) {
						isSslEnabled = Boolean.parseBoolean(item.getConfigValue());
					}
					result.setSslEnabled(isSslEnabled);
					logger.info("is ssl enabled: " + result.isSslEnabled());
					
					boolean loginDisable = false;
					if (item.getConfigKey().equalsIgnoreCase("mail.smtp.auth.login.disable")) {
						loginDisable = Boolean.parseBoolean(item.getConfigValue());
					}
					result.setLoginDisable(loginDisable);
					logger.info("is login disable: " + result.isLoginDisable());
				}
			}
		}
		else {
			logger.info("Nessun Parametro di configurazione smtp trovato per idAccount " + idAccount);
		}
		
		return result;	
	}
	
	private Properties getMailboxAccountConfigProp(MailboxAccountConfigDAOImpl mailboxAccountConfigDao,
			String idAccount, org.hibernate.Session sessionJob) throws Exception {
		
		logger.info("Metodo getMailboxAccountConfigProp");
		
		Properties result = new Properties();
		
		List<MailboxAccountConfig> listConfig = mailboxAccountConfigDao.getRecordsByIdAccount(idAccount, sessionJob);
		logger.info("configurazioni trovate: " + listConfig.size());
		if (listConfig.size() > 0) {
			logger.info("Parametri di configurazione smtp trovati per idAccount " + idAccount);
			for (MailboxAccountConfig item : listConfig) {
				if (item.getIdAccount().equals(idAccount)) {
					result.put(item.getConfigKey(), item.getConfigValue());
					logger.info(item.getConfigKey()+": " + item.getConfigValue());
					
				}
			}
		}
		else {
			logger.info("Nessun Parametro di configurazione smtp trovato per idAccount " + idAccount);
		}
		
		return result;
	}
	
	private List<String> generaDestinatari(String destinatari) {
		logger.info("Lista destinatari: " + destinatari);
		
		List<String> listDestinatari = new ArrayList<String>();
		
		if (destinatari != null && !destinatari.equals("")) {
			if (destinatari.contains(";")) {
				String str[] = destinatari.split(";");
				if (str !=null && str.length > 0)
					listDestinatari = Arrays.asList(str);
			}
			else {
				listDestinatari.add(destinatari);
			}
		}
		
		return listDestinatari;
	}
	
	private void updateTRichNotifEmail(TRichNotifEmailDAOImpl tRichNotifEmailDao, 
									   TRichNotifEmail record, 
									   String msgError,
									   org.hibernate.Session sessionJob) throws Exception {
		
		// dati da aggiornare
		record.setTryNum(record.getTryNum().add(new BigDecimal(1)));
		record.setTsLastTry(new Date());
		record.setMsgErrInvio(msgError);
		record.setTsLastUpd(new Date());
		
		// update tabella T_RICH_NOTIF_EMAIL
		tRichNotifEmailDao.update(record, sessionJob);
	}
	
	private void updateTNotifAllEmail(TNotifAllEmailDAOImpl tNotifAllEmailDao,
			  List<TNotifAllEmail> allegatiList,
			  String msgError,
			  org.hibernate.Session sessionJob) throws Exception {
		
		if (allegatiList.size() > 0) {
			for (TNotifAllEmail record : allegatiList) {
				record.setTryNum(record.getTryNum().add(new BigDecimal(1)));
				record.setTsLastTry(new Date());
				record.setErrMsg(msgError);
				record.setTsLastUpd(new Date());
				
				// update tabella
				tNotifAllEmailDao.update(record, sessionJob);
			}
		}	
	}
	
	private boolean checkInsertNotifica(TNotifEmailInviateDAOImpl tNotifEmailInviateDao,
										 String idRicNotifica, org.hibernate.Session sessionJob) throws Exception {
		
		logger.info("Verifico se in T_NOTIF_EMAIL_INVIATE presente richiesta " + idRicNotifica);
		
		boolean result = false;
		
		int count = tNotifEmailInviateDao.getNotificaEmailById(idRicNotifica, sessionJob);
		logger.info("richiesta " + idRicNotifica + " presente: " + count);
		
		if (count > 0) {
			result = true;
		}
		
		return result;
	}
	
	private boolean checkInsertAllegatiNotifica(TNotifAllEmailInvDAOImpl tNotifAllEmailInvDao,
			 									String idRicNotifica, 
			 									org.hibernate.Session sessionJob) throws Exception {

		logger.info("Verifico se in T_NOTIF_ALL_EMAIL_INV presente richiesta " + idRicNotifica);
		
		boolean result = false;
		
		int count = tNotifAllEmailInvDao.getAllegatiEmailByIdRich(idRicNotifica, sessionJob);
		logger.info("per richiesta " + idRicNotifica + " presenti: " + count + " allegati");
		
		if (count > 0) {
			result = true;
		}
		
		return result;
	}
	
	private void insertRecord(TNotifEmailInviateDAOImpl tNotifEmailInviateDao,
							  TRichNotifEmail record, org.hibernate.Session sessionJob) throws Exception {
		// bean per insert
		TNotifEmailInviate insertBean = new TNotifEmailInviate();
		insertBean.setIdRichNotifica(record.getIdRichNotifica());
		insertBean.setIdSpAoo(record.getIdSpAoo());
		insertBean.setIdProcess(record.getIdProcess());
		insertBean.setIdUd(record.getIdUd());
		insertBean.setIdFolder(record.getIdFolder());
		insertBean.setTriggerCondition(record.getTriggerCondition());
		insertBean.setDestNotifica(record.getDestNotifica());
		insertBean.setDestCcNotifica(record.getDestCcNotifica());
		insertBean.setIdAccountMitt(record.getIdAccountMitt());
		insertBean.setAccountMittente(record.getAccountMittente());
		insertBean.setAliasAccountMittente(record.getAliasAccountMittente());
		insertBean.setSmtpEndpointAccountMitt(record.getSmtpEndpointAccountMitt());
		insertBean.setSmtpPortAccountMitt(record.getSmtpPortAccountMitt());
		insertBean.setSubject(record.getSubject());
		insertBean.setBody(record.getBody());
		insertBean.setTsLastTry(record.getTsLastTry());
		insertBean.setTryNum(record.getTryNum());
		insertBean.setMsgErrInvio(record.getMsgErrInvio());
		insertBean.setTsInvio(new Date());
		insertBean.setMailMessAgeId("111");
		insertBean.setTsIns(record.getTsIns());
		insertBean.setIdUserIns(record.getIdUserIns());
		insertBean.setTsLastUpd(record.getTsLastUpd());
		insertBean.setIdUserLastUpd(record.getIdUserLastUpd());
		
		tNotifEmailInviateDao.save(insertBean, sessionJob);
	}
	
	private void insertAllegatiInviati(TNotifAllEmailInvDAOImpl tNotifAllEmailInvDao,
			  						   List<TNotifAllEmail> allegatiList,
			  						   org.hibernate.Session sessionJob) throws Exception {
		
		if (allegatiList.size() > 0) {
			for (TNotifAllEmail record : allegatiList) {
				Date now = new Date();
				
				TNotifAllEmailInv bean = new TNotifAllEmailInv();
				bean.setDisplayFilename(record.getDisplayFilename());
				bean.setErrCode(record.getErrCode());
				bean.setErrMsg(record.getErrMsg());
				bean.setIdEmail(record.getIdEmail());
				bean.setIdRichNotAll(record.getIdRichNotAll());
				bean.setIdRichNotifica(record.getIdRichNotifica());
				bean.setIdSpAoo(record.getIdSpAoo());
				bean.setIdUserIns(record.getIdUserIns());
				bean.setIdUserLastUpd(record.getIdUserLastUpd());
				bean.setStato("OK");
				bean.setTryNum(record.getTryNum());
				bean.setTsIns(record.getTsIns());
				bean.setTsLastTry(now);
				bean.setTsLastUpd(now);
				bean.setUri(record.getUri());
				
				// insert
				tNotifAllEmailInvDao.save(bean, sessionJob);
			}
		}	
	}
	
	private void deleteRecordTRichNotifEmail(TRichNotifEmailDAOImpl tRichNotifEmailDao, 
			   						   		 TRichNotifEmail record, org.hibernate.Session sessionJob) throws Exception {
		
		// delete record tabella T_RICH_NOTIF_EMAIL
		tRichNotifEmailDao.delete(record, sessionJob);
	}
	
	private void deleteRecordTNotifAllEmail(TNotifAllEmailDAOImpl tNotifAllEmailDao,
			  								List<TNotifAllEmail> allegatiList,
			  								org.hibernate.Session sessionJob) throws Exception {
		
		if (allegatiList.size() > 0) {
			for (TNotifAllEmail record : allegatiList) {
				// cancellazione record allegati inviati
				tNotifAllEmailDao.delete(record, sessionJob);
			}
		}	
	}
}