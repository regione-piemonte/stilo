/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.apache.commons.io.IOUtils;
import it.eng.aurigamailbusiness.bean.AllegatiInvioMailXmlBean;
import it.eng.aurigamailbusiness.bean.Categoria;
import it.eng.aurigamailbusiness.bean.DestinatariInvioMailXmlBean;
import it.eng.aurigamailbusiness.bean.Dizionario;
import it.eng.aurigamailbusiness.bean.EmailBean;
import it.eng.aurigamailbusiness.bean.IdMailInoltrataMailXmlBean;
import it.eng.aurigamailbusiness.bean.InvioMailXmlBean;
import it.eng.aurigamailbusiness.bean.MailAttachmentsBean;
import it.eng.aurigamailbusiness.bean.MailboxAccountBean;
import it.eng.aurigamailbusiness.bean.MailboxAccountConfigBean;
import it.eng.aurigamailbusiness.bean.MailboxBean;
import it.eng.aurigamailbusiness.bean.RegistrazioneProtocollo;
import it.eng.aurigamailbusiness.bean.RispostaInoltro;
import it.eng.aurigamailbusiness.bean.RispostaInoltroBean;
import it.eng.aurigamailbusiness.bean.SenderAttachmentsBean;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.aurigamailbusiness.bean.TAnagFruitoriCaselleBean;
import it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TDestinatariEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TFolderCaselleBean;
import it.eng.aurigamailbusiness.bean.TParametersBean;
import it.eng.aurigamailbusiness.bean.TRegProtVsEmailBean;
import it.eng.aurigamailbusiness.bean.TRelCaselleFruitoriBean;
import it.eng.aurigamailbusiness.bean.TRelEmailFolderBean;
import it.eng.aurigamailbusiness.bean.TRelEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TRubricaEmailBean;
import it.eng.aurigamailbusiness.bean.TValDizionarioBean;
import it.eng.aurigamailbusiness.bean.TipoInteroperabilita;
import it.eng.aurigamailbusiness.database.dao.DaoMailbox;
import it.eng.aurigamailbusiness.database.dao.DaoMailboxAccount;
import it.eng.aurigamailbusiness.database.dao.DaoMailboxAccountConfig;
import it.eng.aurigamailbusiness.database.dao.DaoTAnagFruitoriCaselle;
import it.eng.aurigamailbusiness.database.dao.DaoTFolderCaselle;
import it.eng.aurigamailbusiness.database.dao.DaoTParameters;
import it.eng.aurigamailbusiness.database.dao.DaoTRelCaselleFruitori;
import it.eng.aurigamailbusiness.database.dao.DaoTValDizionario;
import it.eng.aurigamailbusiness.database.utility.DaoFactory;
import it.eng.aurigamailbusiness.exception.AurigaMailBusinessException;
import it.eng.aurigamailbusiness.fileoperation.EnhancedFile;
import it.eng.aurigamailbusiness.fileoperation.FileUtilities;
import it.eng.aurigamailbusiness.sender.storage.StorageCenter;
import it.eng.aurigamailbusiness.utility.MailBreaker;
import it.eng.aurigamailbusiness.utility.Util;
import it.eng.core.business.KeyGenerator;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;

/**
 * utilità relative alla spedizione di una email
 * 
 * @author jravagnan
 * 
 */
public class SendUtils {

	private static Map<String, TParametersBean> parametri = new ConcurrentHashMap<String, TParametersBean>();

	private static Logger log = LogManager.getLogger(SendUtils.class);

	private static FileUtilities fileUtils;

	private static StorageCenter storage;

	private static MailBreaker breaker;

	private static final String NUM_MAX_ADDRESS = "MAX_NUM_DESTINATARI_INVIO_EMAIL";

	/**
	 * 
	 * converte un SenderBean in un EmailBean pronto ad essere salvato in banca dati
	 * 
	 * @param bean
	 * @param messageId
	 * @param idAccount
	 * @param uri
	 * @param mailsize
	 * @param registrazione
	 * @param mailOriginaria
	 * @param isDraft
	 * @param mailbox
	 * @return
	 * @throws Exception
	 */
	public static EmailBean convertSendBeanToEmailBean(SenderBean sender, String messageId, String idAccount, String uri, Long mailsize,
			RegistrazioneProtocollo registrazione, TEmailMgoBean mailOriginaria, boolean isDraft, String mailbox, Session session) throws Exception {
		EmailBean email = new EmailBean();
		getParametersValueInSession(session);
		TEmailMgoBean mail = creaEmailMgo(sender, messageId, idAccount, uri, mailsize);
		email.setMail(mail);
		if (log.isDebugEnabled()) {
			log.debug("creato TEmailMgoBean");
		}
		if (salvataggioInRubricaMittente(sender)) {
			String idFruitore = ricavaFruitoreCasellaInSession(idAccount, session);
			TRubricaEmailBean voceMittente = populateRubricaMittente(idFruitore, sender);
			email.setVoceRubricaMittente(voceMittente);
		}
		List<TRelEmailFolderBean> relsMailFolder = creaRelemailInUscitaInSession(email, idAccount, isDraft, session);
		email.setFolders(relsMailFolder);
		if (log.isDebugEnabled()) {
			log.debug("creato List<TRelEmailFolderBean>");
		}
		List<TAttachEmailMgoBean> attachs = populateAttachEmailMgoBean(email, sender, mailbox);
		email.setAttachments(attachs);
		if (log.isDebugEnabled()) {
			log.debug("creato List<TAttachEmailMgoBean>");
		}
		List<TDestinatariEmailMgoBean> destinatari = populateDestinatari(sender, email);
		Map<String, String> relEmailRubrica = new HashMap<String, String>();
		if (salvataggioInRubricaDestinatari()) {
			String idFruitore = ricavaFruitoreCasellaInSession(idAccount, session);
			List<TRubricaEmailBean> vociRubricaDestinatari = populateRubricaDestinatari(destinatari, idFruitore);
			for (TRubricaEmailBean voce : vociRubricaDestinatari) {
				// hashmap d'appoggio per ottenere il valore della voce rubrica
				// relativa al destinatario
				relEmailRubrica.put(voce.getAccountEmail(), voce.getIdVoceRubricaEmail());
			}
			email.setVociRubricaDestinatari(vociRubricaDestinatari);
			if (log.isDebugEnabled()) {
				log.debug("List<TRubricaEmailBean> creata");
			}
			for (TDestinatariEmailMgoBean destinatario : destinatari) {
				destinatario.setIdVoceRubricaDest(relEmailRubrica.get(destinatario.getAccountDestinatario().trim()));
			}
			email.setDestinatari(destinatari);
			// se non bisogna salvare i destinatari in rubrica li salvo
			// direttamente
		} else {
			email.setDestinatari(destinatari);
		}
		if (registrazione != null) {
			email = creaRelRegistrazione(registrazione, email);
		}
		if (mailOriginaria != null) {
			email = populateTRelEmailNotificaInSession(mailOriginaria, email, session);
		}
		if (sender.getRispInol() != null) {
			email = populateTRelEmailRispostaInoltroInSession(sender, email, session);
		}
		if (log.isDebugEnabled()) {
			log.debug("List<TDestinatariEmailMgoBean> creata");
		}
		return email;
	}

	/**
	 * restituisce il numero massimo di indirizzi selezionabili per l'invio di una mail
	 * 
	 * @return
	 * @throws AurigaMailBusinessException
	 */
	public static int verifyMaxNumAddress() throws AurigaMailBusinessException {
		int ret = -1;
		try {
			DaoTParameters daoParametri = (DaoTParameters) DaoFactory.getDao(DaoTParameters.class);
			TFilterFetch<TParametersBean> filterFetch = new TFilterFetch<TParametersBean>();
			TParametersBean filter = new TParametersBean();
			filter.setParKey(NUM_MAX_ADDRESS);
			filterFetch.setFilter(filter);
			List<TParametersBean> risultato = daoParametri.search(filterFetch).getData();
			if (risultato != null && risultato.size() > 0) {
				ret = risultato.get(0).getNumValue().intValue();
			}
		} catch (Exception e) {
			log.error("Impossibile ricavare il valore del parametro desiderato: " + NUM_MAX_ADDRESS, e);
			throw new AurigaMailBusinessException("Impossibile ricavare il valore del parametro desiderato: " + NUM_MAX_ADDRESS, e);
		}
		return ret;
	}

	/**
	 * crea la relazione con i dati di protocollazione
	 * 
	 * @param registrazione
	 * @param email
	 * @return
	 */
	private static EmailBean creaRelRegistrazione(RegistrazioneProtocollo registrazione, EmailBean email) {
		TRegProtVsEmailBean regProt = new TRegProtVsEmailBean();
		regProt.setAnnoReg(registrazione.getAnnoReg());
		regProt.setCategoriaReg(registrazione.getCategoriaReg());
		regProt.setIdEmail(email.getMail().getIdEmail());
		regProt.setIdProvReg(registrazione.getIdProvReg());
		regProt.setNumReg(registrazione.getNumReg());
		regProt.setSiglaRegistro(registrazione.getSiglaRegistro());
		email.setRegistrazioneProtocollo(regProt);
		return email;
	}

	/**
	 * crea la relazione tra email basandosi sul fatto che è una risposta/inoltro
	 * 
	 * @param bean
	 * @param email
	 * @return
	 * @throws Exception
	 */
	private static EmailBean populateTRelEmailRispostaInoltro(SenderBean sender, EmailBean email) throws Exception {
		List<TRelEmailMgoBean> listaRel = new ArrayList<TRelEmailMgoBean>();
		if (sender != null && sender.getRispInol() != null) {
			if (sender.getRispInol().getMailOriginaria() != null) {
				TRelEmailMgoBean relazione = new TRelEmailMgoBean();
				relazione.setIdRelEmail(KeyGenerator.gen());
				relazione.setIdEmail1(email.getMail().getIdEmail());
				relazione.setIdEmail2(sender.getRispInol().getMailOriginaria().getIdEmail());
				relazione.setFlgRelAutomatica(true);
				switch (sender.getRispInol().getRispInol()) {
				case RISPOSTA: {
					relazione.setDettRelazione("Risposta");
					TValDizionarioBean dizCategoria = ricavaDizionarioDaValore(Dizionario.CAUSALE);
					TValDizionarioBean dizRuolo = ricavaDizionarioDaValore(Dizionario.RISPOSTA);
					relazione.setIdRecDizCategRel(dizCategoria.getIdRecDiz());
					relazione.setIdRecDizRuolo1Vs2(dizRuolo.getIdRecDiz());
					break;
				}
				case INOLTRO: {
					relazione.setDettRelazione("Inoltro");
					TValDizionarioBean dizCategoria = ricavaDizionarioDaValore(Dizionario.CAUSALE);
					TValDizionarioBean dizRuolo = ricavaDizionarioDaValore(Dizionario.INOLTRO);
					relazione.setIdRecDizCategRel(dizCategoria.getIdRecDiz());
					relazione.setIdRecDizRuolo1Vs2(dizRuolo.getIdRecDiz());
					break;
				}
				case NOTIFICA_CONFERMA: {
					relazione.setDettRelazione("Notifica Conferma");
					TValDizionarioBean dizCategoria = ricavaDizionarioDaValore(Dizionario.CAUSALE);
					TValDizionarioBean dizRuolo = ricavaDizionarioDaValore(Dizionario.NOTIFICA_CONFERMA);
					relazione.setIdRecDizCategRel(dizCategoria.getIdRecDiz());
					relazione.setIdRecDizRuolo1Vs2(dizRuolo.getIdRecDiz());
					break;
				}
				case NOTIFICA_ECCEZIONE: {
					relazione.setDettRelazione("Notifica Eccezione");
					TValDizionarioBean dizCategoria = ricavaDizionarioDaValore(Dizionario.CAUSALE);
					TValDizionarioBean dizRuolo = ricavaDizionarioDaValore(Dizionario.NOTIFICA_ECCEZIONE);
					relazione.setIdRecDizCategRel(dizCategoria.getIdRecDiz());
					relazione.setIdRecDizRuolo1Vs2(dizRuolo.getIdRecDiz());
					break;
				}
				}
				listaRel.add(relazione);
			} else if (sender.getRispInol().getMailOrigInoltroMassivo() != null && sender.getRispInol().getRispInol() == RispostaInoltro.INOLTRO) {
				for (TEmailMgoBean emailOrig : sender.getRispInol().getMailOrigInoltroMassivo()) {
					TRelEmailMgoBean relazione = new TRelEmailMgoBean();
					relazione.setIdRelEmail(KeyGenerator.gen());
					relazione.setIdEmail1(email.getMail().getIdEmail());
					relazione.setIdEmail2(emailOrig.getIdEmail());
					relazione.setFlgRelAutomatica(true);
					relazione.setDettRelazione("Inoltro");
					TValDizionarioBean dizCategoria = ricavaDizionarioDaValore(Dizionario.CAUSALE);
					TValDizionarioBean dizRuolo = ricavaDizionarioDaValore(Dizionario.INOLTRO);
					relazione.setIdRecDizCategRel(dizCategoria.getIdRecDiz());
					relazione.setIdRecDizRuolo1Vs2(dizRuolo.getIdRecDiz());
					listaRel.add(relazione);
				}
			}
		}
		email.setRelMail(listaRel);
		return email;
	}

	/**
	 * crea la relazione tra email basandosi sul fatto che è una risposta/inoltro
	 * 
	 * @param bean
	 * @param email
	 * @return
	 * @throws Exception
	 */
	private static EmailBean populateTRelEmailRispostaInoltroInSession(SenderBean sender, EmailBean email, Session session) throws Exception {
		List<TRelEmailMgoBean> listaRel = new ArrayList<TRelEmailMgoBean>();
		if (sender != null && sender.getRispInol() != null) {
			if (sender.getRispInol().getMailOriginaria() != null) {
				TRelEmailMgoBean relazione = new TRelEmailMgoBean();
				relazione.setIdRelEmail(KeyGenerator.gen());
				relazione.setIdEmail1(email.getMail().getIdEmail());
				relazione.setIdEmail2(sender.getRispInol().getMailOriginaria().getIdEmail());
				relazione.setFlgRelAutomatica(true);
				switch (sender.getRispInol().getRispInol()) {
				case RISPOSTA: {
					relazione.setDettRelazione("Risposta");
					TValDizionarioBean dizCategoria = ricavaDizionarioDaValoreInSession(Dizionario.CAUSALE, session);
					TValDizionarioBean dizRuolo = ricavaDizionarioDaValoreInSession(Dizionario.RISPOSTA, session);
					relazione.setIdRecDizCategRel(dizCategoria.getIdRecDiz());
					relazione.setIdRecDizRuolo1Vs2(dizRuolo.getIdRecDiz());
					break;
				}
				case INOLTRO: {
					relazione.setDettRelazione("Inoltro");
					TValDizionarioBean dizCategoria = ricavaDizionarioDaValoreInSession(Dizionario.CAUSALE, session);
					TValDizionarioBean dizRuolo = ricavaDizionarioDaValoreInSession(Dizionario.INOLTRO, session);
					relazione.setIdRecDizCategRel(dizCategoria.getIdRecDiz());
					relazione.setIdRecDizRuolo1Vs2(dizRuolo.getIdRecDiz());
					break;
				}
				case NOTIFICA_CONFERMA: {
					relazione.setDettRelazione("Notifica Conferma");
					TValDizionarioBean dizCategoria = ricavaDizionarioDaValoreInSession(Dizionario.CAUSALE, session);
					TValDizionarioBean dizRuolo = ricavaDizionarioDaValoreInSession(Dizionario.NOTIFICA_CONFERMA, session);
					relazione.setIdRecDizCategRel(dizCategoria.getIdRecDiz());
					relazione.setIdRecDizRuolo1Vs2(dizRuolo.getIdRecDiz());
					break;
				}
				case NOTIFICA_ECCEZIONE: {
					relazione.setDettRelazione("Notifica Eccezione");
					TValDizionarioBean dizCategoria = ricavaDizionarioDaValoreInSession(Dizionario.CAUSALE, session);
					TValDizionarioBean dizRuolo = ricavaDizionarioDaValoreInSession(Dizionario.NOTIFICA_ECCEZIONE, session);
					relazione.setIdRecDizCategRel(dizCategoria.getIdRecDiz());
					relazione.setIdRecDizRuolo1Vs2(dizRuolo.getIdRecDiz());
					break;
				}
				}
				listaRel.add(relazione);
			} else if (sender.getRispInol().getMailOrigInoltroMassivo() != null && sender.getRispInol().getRispInol() == RispostaInoltro.INOLTRO) {
				for (TEmailMgoBean emailOrig : sender.getRispInol().getMailOrigInoltroMassivo()) {
					TRelEmailMgoBean relazione = new TRelEmailMgoBean();
					relazione.setIdRelEmail(KeyGenerator.gen());
					relazione.setIdEmail1(email.getMail().getIdEmail());
					relazione.setIdEmail2(emailOrig.getIdEmail());
					relazione.setFlgRelAutomatica(true);
					relazione.setDettRelazione("Inoltro");
					TValDizionarioBean dizCategoria = ricavaDizionarioDaValoreInSession(Dizionario.CAUSALE, session);
					TValDizionarioBean dizRuolo = ricavaDizionarioDaValoreInSession(Dizionario.INOLTRO, session);
					relazione.setIdRecDizCategRel(dizCategoria.getIdRecDiz());
					relazione.setIdRecDizRuolo1Vs2(dizRuolo.getIdRecDiz());
					listaRel.add(relazione);
				}
			}
		}
		email.setRelMail(listaRel);
		return email;
	}

	/**
	 * Ottiene le proprietà utili alla spedizione per un determinato account
	 * 
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public static Properties getAccountProperties(String idAccount) throws Exception {

		Properties propertiesAccount = new Properties();

		try {

			propertiesAccount = Util.getJavaMailDefaultProperties();

			TFilterFetch<MailboxAccountConfigBean> fet = new TFilterFetch<MailboxAccountConfigBean>();
			MailboxAccountConfigBean config = new MailboxAccountConfigBean();
			config.setIdAccount(idAccount);
			fet.setFilter(config);
			List<MailboxAccountConfigBean> configurazioni = ((DaoMailboxAccountConfig) DaoFactory.getDao(DaoMailboxAccountConfig.class)).search(fet).getData();
			if (configurazioni != null) {
				for (MailboxAccountConfigBean configaccount : configurazioni) {
					if (StringUtils.isEmpty(configaccount.getConfigValue())) {
						throw new AurigaMailBusinessException("Valore non impostato per il parametro " + configaccount.getConfigKey());
					}
					propertiesAccount.put(configaccount.getConfigKey(), configaccount.getConfigValue());
				}
			}
		} catch (Exception e) {
			log.error("Nessuna mailbox configurata per l'account scelto", e);
			throw e;
		}

		return propertiesAccount;

	}

	/**
	 * Ottiene le proprietà utili alla spedizione per un determinato account
	 * 
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public static Properties getAccountPropertiesByEmail(String email) throws Exception {

		Properties propertiesAccount = new Properties();

		try {

			propertiesAccount = Util.getJavaMailDefaultProperties();
			String idAccount = null;
			TFilterFetch<MailboxAccountBean> fetAccount = new TFilterFetch<MailboxAccountBean>();
			MailboxAccountBean configAccount = new MailboxAccountBean();
			configAccount.setAccount(email);
			fetAccount.setFilter(configAccount);
			List<MailboxAccountBean> account = ((DaoMailboxAccount) DaoFactory.getDao(DaoMailboxAccount.class)).search(fetAccount).getData();
			if (account != null && account.size() == 1) {
				MailboxAccountBean mailboxAccountBean = account.get(0);
				idAccount = mailboxAccountBean.getIdAccount();
			} else {
				throw new AurigaMailBusinessException("Email " + email + " non presente.");
			}
			
			TFilterFetch<MailboxAccountConfigBean> fet = new TFilterFetch<MailboxAccountConfigBean>();
			MailboxAccountConfigBean config = new MailboxAccountConfigBean();
			config.setIdAccount(idAccount);
			fet.setFilter(config);
			List<MailboxAccountConfigBean> configurazioni = ((DaoMailboxAccountConfig) DaoFactory.getDao(DaoMailboxAccountConfig.class)).search(fet).getData();
			if (configurazioni != null) {
				for (MailboxAccountConfigBean configaccount : configurazioni) {
					if (StringUtils.isEmpty(configaccount.getConfigValue())) {
						throw new AurigaMailBusinessException("Valore non impostato per il parametro " + configaccount.getConfigKey());
					}
					propertiesAccount.put(configaccount.getConfigKey(), configaccount.getConfigValue());
				}
			}
		} catch (Exception e) {
			log.error("Nessuna mailbox configurata per l'account scelto", e);
			throw e;
		}

		return propertiesAccount;

	}

	
	/**
	 * ottiene le proprietà utili alla spedizione per un determinato account
	 * 
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public static Properties getAccountPropertiesInSession(String idAccount, Session session) throws Exception {
		Properties propertiesaccount = new Properties();
		try {
			TFilterFetch<MailboxAccountConfigBean> fet = new TFilterFetch<MailboxAccountConfigBean>();
			MailboxAccountConfigBean config = new MailboxAccountConfigBean();
			config.setIdAccount(idAccount);
			fet.setFilter(config);
			List<MailboxAccountConfigBean> configurazioni = ((DaoMailboxAccountConfig) DaoFactory.getDao(DaoMailboxAccountConfig.class))
					.searchInSession(fet, session).getData();
			if (configurazioni != null) {
				for (MailboxAccountConfigBean configaccount : configurazioni) {
					if (StringUtils.isEmpty(configaccount.getConfigValue())) {
						throw new AurigaMailBusinessException("Valore non impostato per il parametro " + configaccount.getConfigKey());
					}
					propertiesaccount.put(configaccount.getConfigKey(), configaccount.getConfigValue());
				}
			}
		} catch (Exception e) {
			log.error("Nessuna mailbox configurata per l'account scelto", e);
			throw e;
		}
		return propertiesaccount;
	}

	/**
	 * crea la relazione tra email basnadosi sul fatto che è una notifica interoperabile
	 * 
	 * @param mailOriginaria
	 * @param email
	 * @return
	 * @throws Exception
	 */
	private static EmailBean populateTRelEmailNotifica(TEmailMgoBean mailOriginaria, EmailBean email) throws Exception {
		List<TRelEmailMgoBean> listaRel = new ArrayList<TRelEmailMgoBean>();
		TRelEmailMgoBean relazione = new TRelEmailMgoBean();
		relazione.setIdRelEmail(KeyGenerator.gen());
		relazione.setIdEmail1(email.getMail().getIdEmail());
		relazione.setIdEmail2(mailOriginaria.getIdEmail());
		// L'ID DESTINATARIO NON MI INTERESSA PERCHE'
		// POTREBBE ESSERE UN DESTINATARIO DIVERSO DAL MITTENTE
		relazione.setFlgRelAutomatica(true);
		relazione.setDettRelazione("Notifica interoperabile");
		TValDizionarioBean dizCategoria = ricavaDizionarioDaValore(Dizionario.CAUSALE);
		TValDizionarioBean dizRuolo = ricavaDizionarioDaValore(Dizionario.NOTIFICA_INTEROPERABILE);
		relazione.setIdRecDizCategRel(dizCategoria.getIdRecDiz());
		relazione.setIdRecDizRuolo1Vs2(dizRuolo.getIdRecDiz());
		listaRel.add(relazione);
		email.setRelMail(listaRel);
		return email;
	}

	/**
	 * crea la relazione tra email basandosi sul fatto che è una notifica interoperabile
	 * 
	 * @param mailOriginaria
	 * @param email
	 * @return
	 * @throws Exception
	 */
	private static EmailBean populateTRelEmailNotificaInSession(TEmailMgoBean mailOriginaria, EmailBean email, Session session) throws Exception {
		List<TRelEmailMgoBean> listaRel = new ArrayList<TRelEmailMgoBean>();
		TRelEmailMgoBean relazione = new TRelEmailMgoBean();
		relazione.setIdRelEmail(KeyGenerator.gen());
		relazione.setIdEmail1(email.getMail().getIdEmail());
		relazione.setIdEmail2(mailOriginaria.getIdEmail());
		// L'ID DESTINATARIO NON MI INTERESSA PERCHE'
		// POTREBBE ESSERE UN DESTINATARIO DIVERSO DAL MITTENTE
		relazione.setFlgRelAutomatica(true);
		relazione.setDettRelazione("Notifica interoperabile");
		TValDizionarioBean dizCategoria = ricavaDizionarioDaValoreInSession(Dizionario.CAUSALE, session);
		TValDizionarioBean dizRuolo = ricavaDizionarioDaValoreInSession(Dizionario.NOTIFICA_INTEROPERABILE, session);
		relazione.setIdRecDizCategRel(dizCategoria.getIdRecDiz());
		relazione.setIdRecDizRuolo1Vs2(dizRuolo.getIdRecDiz());
		listaRel.add(relazione);
		email.setRelMail(listaRel);
		return email;
	}

	/**
	 * converte un senderBean in un EmailBean
	 * 
	 * @param bean
	 * @param messageId
	 * @param idAccount
	 * @param uri
	 * @param mailsize
	 * @param isDraft
	 * @return
	 * @throws Exception
	 */
	public static EmailBean convertSendBeanToEmailBean(SenderBean sender, String messageId, String idAccount, String uri, Long mailsize, boolean isDraft,
			String mailbox, Session session) throws Exception {
		return convertSendBeanToEmailBean(sender, messageId, idAccount, uri, mailsize, null, null, isDraft, mailbox, session);
	}

	/**
	 * converte una email archiviata in un bean di spedizione
	 * 
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public static SenderBean convertEmailBeanToSenderBean(EmailBean email) throws Exception {
		SenderBean sender = new SenderBean();
		String idEmail = email.getMail().getIdEmail();
		String messageId = email.getMail().getMessageId();
		String subject = email.getMail().getOggetto();
		String corpo = email.getMail().getCorpo();
		List<String> indirizziTo = ricavaIndirizziTo(email.getDestinatari(), TipoDestinatario.TO);
		List<String> indirizziCC = ricavaIndirizziTo(email.getDestinatari(), TipoDestinatario.CC);
		List<String> indirizziBcc = ricavaIndirizziTo(email.getDestinatari(), TipoDestinatario.BCC);
		String addressFrom = email.getMail().getAccountMittente();
		String account = email.getMail().getAccountMittente();
		List<MailAttachmentsBean> attachs = breaker.getAttachments(idEmail);
		List<SenderAttachmentsBean> listaAttachment = creaListaAttachs(attachs);
		sender.setIdEmail(idEmail);
		sender.setMessageId(messageId);
		sender.setAccount(account);
		sender.setAddressBcc(indirizziBcc);
		sender.setAddressCc(indirizziCC);
		sender.setAddressFrom(addressFrom);
		sender.setAddressTo(indirizziTo);
		sender.setAttachments(listaAttachment);
		sender.setBody(corpo);
		sender.setIsHtml(false);
		if (StringUtils.isNotEmpty(corpo)) {
			try {
				sender.setIsHtml(isHtml(corpo));
			} catch (Exception e) {
				throw new AurigaMailBusinessException("Impossibile capire se html: ", e);
			}
		}
		sender.setReturnReceipt(email.getMail().isFlgRichConfLettura());
		sender.setSubject(subject);

		return sender;
	}

	public static SenderBean convertInvioMailXmlBeanToSenderBean(InvioMailXmlBean invioMailXmlBean) throws AurigaMailBusinessException {
		SenderBean sender = new SenderBean();
		convertInvioMailXmlBeanToSenderBean(invioMailXmlBean, sender);
		return sender;
	}

	public static void convertInvioMailXmlBeanToSenderBean(InvioMailXmlBean invioMailXmlBean, SenderBean sender) throws AurigaMailBusinessException {

		// imposto sempre la lista di allegati altrimenti la store se riceva
		// null lascia inalterata la lista precedente

		if (invioMailXmlBean.getListaAllegati() == null) {
			invioMailXmlBean.setListaAllegati(new ArrayList<AllegatiInvioMailXmlBean>());
		}

		if (invioMailXmlBean.getlSenderAttachmentsBean() == null) {
			invioMailXmlBean.setlSenderAttachmentsBean(new ArrayList<SenderAttachmentsBean>());
		}

		sender.setSubject(invioMailXmlBean.getSubject());
		sender.setAddressTo(ricavaIndirizziInvioMail(invioMailXmlBean.getListaDestinatari(), TipoDestinatario.TO));
		sender.setAddressCc(ricavaIndirizziInvioMail(invioMailXmlBean.getListaDestinatari(), TipoDestinatario.CC));
		sender.setAddressBcc(ricavaIndirizziInvioMail(invioMailXmlBean.getListaDestinatari(), TipoDestinatario.BCC));
		sender.setAddressFrom(invioMailXmlBean.getAccountMittente());
		sender.setAccount(invioMailXmlBean.getAccountMittente());
		sender.setAttachments(invioMailXmlBean.getlSenderAttachmentsBean());

		if (StringUtils.isNotBlank(invioMailXmlBean.getEmailPredTipoRel())) {
			RispostaInoltroBean rispInol = new RispostaInoltroBean();
			if (invioMailXmlBean.getEmailPredTipoRel().equals(RispostaInoltro.RISPOSTA.getValue())) {
				if (StringUtils.isBlank(invioMailXmlBean.getEmailPredIdEmail())) {
					throw new AurigaMailBusinessException("Id mail precedessore in riposta non valorizzato");
				}
				rispInol.setRispInol(RispostaInoltro.RISPOSTA);
				TEmailMgoBean mailOriginaria = new TEmailMgoBean();
				// in tutte le successive chiamate viene utilizzato solo l'id
				// mail
				mailOriginaria.setIdEmail(invioMailXmlBean.getEmailPredIdEmail());
				rispInol.setMailOriginaria(mailOriginaria);
			} else if (invioMailXmlBean.getEmailPredTipoRel().equals(RispostaInoltro.INOLTRO.getValue())) {
				rispInol.setRispInol(RispostaInoltro.INOLTRO);
				// deve essere valorizzato l'id mail origianle o la lista di id
				// mail di inoltro massivo
				if (!(StringUtils.isNotBlank(invioMailXmlBean.getEmailPredIdEmail())
						|| (invioMailXmlBean.getListaIdEmailInoltrate() != null && !invioMailXmlBean.getListaIdEmailInoltrate().isEmpty()))) {
					throw new AurigaMailBusinessException("Id mail precedessore per inoltro non valorizzato");
				} else {
					if (StringUtils.isBlank(invioMailXmlBean.getEmailPredIdEmail())) {
						// lista mail inoltro massivo
						List<TEmailMgoBean> listaMailInoltro = new ArrayList<TEmailMgoBean>();
						for (IdMailInoltrataMailXmlBean idMail : invioMailXmlBean.getListaIdEmailInoltrate()) {
							TEmailMgoBean mailOriginariaInoltro = new TEmailMgoBean();
							mailOriginariaInoltro.setIdEmail(idMail.getIdMailInoltrata());
							listaMailInoltro.add(mailOriginariaInoltro);
						}
						rispInol.setMailOrigInoltroMassivo(listaMailInoltro);
					} else {
						// inoltro singolo
						TEmailMgoBean mailOriginaria = new TEmailMgoBean();
						mailOriginaria.setIdEmail(invioMailXmlBean.getEmailPredIdEmail());
						rispInol.setMailOriginaria(mailOriginaria);
					}
				}
			}
			sender.setRispInol(rispInol);
		}

		String corpo = invioMailXmlBean.getBody();
		sender.setBody(corpo);
		sender.setIsHtml(false);
		if (StringUtils.isNotEmpty(corpo)) {
			try {
				sender.setIsHtml(isHtml(corpo));
			} catch (Exception e) {
				log.error("Impossibile capire se html: ", e);
				throw new AurigaMailBusinessException("Impossibile capire se html: ", e);
			}
		}
		sender.setReturnReceipt(invioMailXmlBean.getFlgRichConfermaLettura() != null && invioMailXmlBean.getFlgRichConfermaLettura() == 1);
		// nessun controllo sulla mail firmata, sono mail che mandiamo noi e
		// sicuramente non sono firmate

	}

	public static boolean isHtml(String pStrHtml) throws Exception {
		Document doc = Jsoup.parseBodyFragment(pStrHtml);
		if (!doc.body().text().equals(pStrHtml)) {
			return true;
		} else
			return false;

	}

	/**
	 * popola i dati fondamentali della mail da salvare
	 * 
	 * @param send
	 * @param messageId
	 * @param idAccount
	 * @param uri
	 * @param mailsize
	 * @return
	 * @throws AurigaMailBusinessException
	 */
	private static TEmailMgoBean creaEmailMgo(SenderBean sender, String messageId, String idAccount, String uri, Long mailsize)
			throws AurigaMailBusinessException {
		TEmailMgoBean bean = new TEmailMgoBean();
		try {
			// per l'indicizzazione su Lucene elimino il carattere
			// speciale 'meno' dall'UID
			String idMail = KeyGenerator.gen().replace("-", "");
			bean.setIdEmail(idMail);
			bean.setMessageId(messageId == null ? idMail : messageId);
			bean.setIdCasella(idAccount);
			bean.setFlgIo(InputOutput.USCITA.getValue());
			bean.setStatoConsolidamento(StatoConsolidamentoEmail.IN_INVIO.getValue());
			bean.setCategoria(identificaCategoria(sender).getValue());
			bean.setDimensione(mailsize);
			bean.setUriEmail(uri);
			bean.setFlgSpam(false);
			bean.setMotivoEccezione(sender.getMotivoEccezione());
			if (sender.getAttachments() != null) {
				bean.setNroAllegati((short) sender.getAttachments().size());
				short firmati = 0;
				for (SenderAttachmentsBean att : sender.getAttachments()) {
					if (att.isFirmato()) {
						firmati++;
					}
				}
				bean.setNroAllegatiFirmati(firmati);
			} else {
				bean.setNroAllegati((short) 0);
				bean.setNroAllegatiFirmati((short) 0);
			}
			bean.setFlgEmailFirmata(false);
			bean.setAccountMittente(sender.getAccount());
			// pulisco eventuali caratteri non ASCII prima dell'inserimento in
			// database
			bean.setOggetto(Util.clearNotASCII(sender.getSubject()));
			bean.setCorpo(Util.clearCarriageReturn(Util.clearNotASCII(sender.getBody())));
		} catch (Exception e) {
			throw new AurigaMailBusinessException("Impossibile creare il bean relativo alla mail", e);
		}

		bean.setFlgInviataRisposta(false);
		bean.setFlgInoltrata(false);

		bean.setFlgNotifInteropAgg(false);
		bean.setFlgNotifInteropAnn(false);
		bean.setFlgNotifInteropConf(false);
		bean.setFlgNotifInteropEcc(false);
		switch (Categoria.valueOfValue(bean.getCategoria())) {
		case INTER_AGG:
			bean.setFlgNotifInteropAgg(true);
			break;
		case INTER_ANN:
			bean.setFlgNotifInteropAnn(true);
			break;
		case INTER_CONF:
			bean.setFlgNotifInteropConf(true);
			break;
		case INTER_ECC:
			bean.setFlgNotifInteropEcc(true);
			break;
		}
		// modifica 12/10/2014: gestione richiesta conferma di lettura PEO ed
		// interoperabile
		bean.setFlgRichConferma(sender.getRichiestaConfermaInteroperabile() == null ? false : sender.getRichiestaConfermaInteroperabile());
		bean.setFlgRichConfLettura(sender.getReturnReceipt() == null ? false : sender.getReturnReceipt());

		bean.setTsInvio(new Date());
		bean.setTsInvioClient(new Date());
		return bean;
	}

	/**
	 * ricava il valore del dizionario
	 * 
	 * @param diz
	 * @return
	 * @throws Exception
	 */
	public static TValDizionarioBean ricavaDizionarioDaValore(Dizionario diz) throws Exception {
		TFilterFetch<TValDizionarioBean> ff = new TFilterFetch<TValDizionarioBean>();
		TValDizionarioBean fil = new TValDizionarioBean();
		fil.setValore(diz.getValue());
		ff.setFilter(fil);
		List<TValDizionarioBean> valori = null;
		try {
			valori = ((DaoTValDizionario) DaoFactory.getDao(DaoTValDizionario.class)).search(ff).getData();
		} catch (Exception e) {
			log.error("Impossibile ottenere il dizionario desiderato", e);
			throw e;
		}
		if (valori == null || valori.size() == 0 || valori.size() > 1) {
			log.error("Impossibile ottenere il dizionario desiderato");
			throw new AurigaMailBusinessException("Impossibile ottenere il dizionario desiderato");
		}
		return valori.get(0);
	}

	/**
	 * ricava il valore del dizionario
	 * 
	 * @param diz
	 * @return
	 * @throws Exception
	 */
	public static TValDizionarioBean ricavaDizionarioDaValoreInSession(Dizionario diz, Session session) throws Exception {
		TFilterFetch<TValDizionarioBean> ff = new TFilterFetch<TValDizionarioBean>();
		TValDizionarioBean fil = new TValDizionarioBean();
		fil.setValore(diz.getValue());
		ff.setFilter(fil);
		List<TValDizionarioBean> valori = null;
		try {
			valori = ((DaoTValDizionario) DaoFactory.getDao(DaoTValDizionario.class)).searchInSession(ff, session).getData();
		} catch (Exception e) {
			log.error("Impossibile ottenere il dizionario desiderato", e);
			throw e;
		}
		if (valori == null || valori.size() == 0 || valori.size() > 1) {
			log.error("Impossibile ottenere il dizionario desiderato");
			throw new AurigaMailBusinessException("Impossibile ottenere il dizionario desiderato");
		}
		return valori.get(0);
	}

	/**
	 * ricava la mailbox dall'account
	 * 
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public static MailboxBean ricavaMailboxFromAccount(String account) throws Exception {
		try {
			DaoMailboxAccount daoAccount = ((DaoMailboxAccount) DaoFactory.getDao(DaoMailboxAccount.class));
			TFilterFetch<MailboxAccountBean> ff = new TFilterFetch<MailboxAccountBean>();
			MailboxAccountBean mab = new MailboxAccountBean();
			mab.setAccount(account);
			ff.setFilter(mab);
			List<MailboxAccountBean> accs = daoAccount.search(ff).getData();
			if (accs.size() == 0) {
				log.error("Id account non presente per account:" + account);
				throw new AurigaMailBusinessException("Id account non presente per account:" + account);
			}
			MailboxAccountBean res = accs.get(0);
			TFilterFetch<MailboxBean> filter = new TFilterFetch<MailboxBean>();
			MailboxBean bean = new MailboxBean();
			bean.setIdAccount(res.getIdAccount());
			filter.setFilter(bean);
			DaoMailbox daoMail = ((DaoMailbox) DaoFactory.getDao(DaoMailbox.class));
			List<MailboxBean> mailboxes = daoMail.search(filter).getData();
			if (mailboxes.size() == 0) {
				log.error("Mailbox non presente per account: " + account);
				throw new AurigaMailBusinessException("Mailboxt non presente per account: " + account);
			}
			return mailboxes.get(0);
		} catch (Exception e) {
			log.error("Impossibile ricavare la mailbox associata all'id account: ", e);
			throw e;
		}
	}

	/**
	 * ricava la mailbox dall'id account
	 * 
	 * @param idAccount
	 * @return
	 * @throws Exception
	 */
	public static MailboxBean ricavaMailboxFromIdAccount(String idAccount) throws Exception {
		try {
			TFilterFetch<MailboxBean> filter = new TFilterFetch<MailboxBean>();
			MailboxBean bean = new MailboxBean();
			bean.setIdAccount(idAccount);
			filter.setFilter(bean);
			DaoMailbox daoMail = ((DaoMailbox) DaoFactory.getDao(DaoMailbox.class));
			List<MailboxBean> mailboxes = daoMail.search(filter).getData();
			if (mailboxes.size() == 0) {
				log.error("mailbox non presente per id account:" + idAccount);
				throw new AurigaMailBusinessException("mailboxt non presente per id account:" + idAccount);
			}
			return mailboxes.get(0);
		} catch (Exception e) {
			log.error("Impossibile ricavare la mailbox associata all'id account: ", e);
			throw e;
		}
	}

	/**
	 * ricava la mailbox dall'id account
	 * 
	 * @param idAccount
	 * @return
	 * @throws Exception
	 */
	public static MailboxBean ricavaMailboxFromIdAccountInSession(String idAccount, Session session) throws Exception {
		try {
			TFilterFetch<MailboxBean> ffmail = new TFilterFetch<MailboxBean>();
			MailboxBean mb = new MailboxBean();
			mb.setIdAccount(idAccount);
			ffmail.setFilter(mb);
			DaoMailbox daoMail = ((DaoMailbox) DaoFactory.getDao(DaoMailbox.class));
			List<MailboxBean> mailboxes = daoMail.searchInSession(ffmail, session).getData();
			if (mailboxes.size() == 0) {
				log.error("mailbox non presente per id account:" + idAccount);
				throw new AurigaMailBusinessException("mailboxt non presente per id account:" + idAccount);
			}
			return mailboxes.get(0);
		} catch (Exception e) {
			log.error("Impossibile ricavare la mailbox associata all'id account: ", e);
			throw e;
		}
	}

	/**
	 * trasforma i MailAttach in SenderAttach
	 * 
	 * @param attachs
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static List<SenderAttachmentsBean> creaListaAttachs(List<MailAttachmentsBean> attachments) throws FileNotFoundException, IOException {
		List<SenderAttachmentsBean> ret = new ArrayList<SenderAttachmentsBean>();
		for (MailAttachmentsBean attach : attachments) {
			SenderAttachmentsBean sb = new SenderAttachmentsBean();
			sb.setFilename(attach.getFilename());
			if(attach.getFile() != null) {
				sb.setFile(attach.getFile());
			}
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(attach.getFile());
				if (attach.getData() == null) {
					sb.setContent(IOUtils.toByteArray(fis));
				} else
					sb.setContent(attach.getData());
				ret.add(sb);
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (Exception e) {
						log.warn("Errore nella chiusura dello stream", e);
					}
				}
			}
			ret.add(sb);
		}
		return ret;
	}

	/**
	 * identifica la categoria della mail
	 * 
	 * @param sender
	 * @return
	 */
	private static Categoria identificaCategoria(SenderBean sender) {
		if (sender.getAttachments() != null) {
			for (SenderAttachmentsBean attachment : sender.getAttachments()) {
				String name = attachment.getFilename();
				TipoInteroperabilita type = TipoInteroperabilita.getValueForFileName(name, false);
				if (type != null) {
					switch (type) {
					case SEGNATURA:
						return Categoria.INTER_SEGN;
					case CONFERMA_RICEZIONE:
						return Categoria.INTER_CONF;
					case AGGIORNAMENTO_CONFERMA:
						return Categoria.INTER_AGG;
					case ANNULLAMENTO_PROTOCOLLAZIONE:
						return Categoria.INTER_ANN;
					case NOTIFICA_ECCEZIONE:
						return Categoria.INTER_ECC;
					}

				}
			}
		}
		if (sender.getIsPec() != null && sender.getIsPec()) {
			return Categoria.PEC;
		}
		return Categoria.ANOMALIA;
	}

	/**
	 * crea le voci della rubrica
	 * 
	 * @param destinatari
	 * @param idFruitore
	 * @return
	 */
	private static List<TRubricaEmailBean> populateRubricaDestinatari(List<TDestinatariEmailMgoBean> destinatari, String idFruitore) {
		List<TRubricaEmailBean> vociRubrica = new ArrayList<TRubricaEmailBean>();
		for (TDestinatariEmailMgoBean desti : destinatari) {
			TRubricaEmailBean voceRubrica = new TRubricaEmailBean();
			voceRubrica.setAccountEmail(desti.getAccountDestinatario().trim());
			voceRubrica.setIdVoceRubricaEmail(KeyGenerator.gen());
			voceRubrica.setIdFruitoreCasella(idFruitore);
			voceRubrica.setFlgMailingList(false);
			voceRubrica.setDescrizioneVoce(desti.getAccountDestinatario());
			voceRubrica.setFlgPecVerificata(false);
			voceRubrica.setFlgPresenteInIpa(false);
			voceRubrica.setFlgAnn(false);
			vociRubrica.add(voceRubrica);
		}
		return vociRubrica;
	}

	/**
	 * popola la rubrica per la parte del mittente
	 * 
	 * @param idFruitore
	 * @param sb
	 * @return
	 */
	private static TRubricaEmailBean populateRubricaMittente(String idFruitore, SenderBean sender) {
		TRubricaEmailBean voceRubrica = new TRubricaEmailBean();
		voceRubrica.setAccountEmail(sender.getAddressFrom().trim());
		voceRubrica.setIdVoceRubricaEmail(KeyGenerator.gen());
		voceRubrica.setIdFruitoreCasella(idFruitore);
		voceRubrica.setFlgMailingList(false);
		voceRubrica.setDescrizioneVoce(sender.getAddressFrom());
		if (sender.getIsPec() != null && sender.getIsPec()) {
			voceRubrica.setFlgPecVerificata(true);
		} else {
			voceRubrica.setFlgPecVerificata(false);
		}
		voceRubrica.setFlgPresenteInIpa(false);
		voceRubrica.setFlgAnn(false);
		return voceRubrica;
	}

	/**
	 * crea i bean dei destinatari
	 * 
	 * @param message
	 * @param mail
	 * @return
	 */
	private static List<TDestinatariEmailMgoBean> populateDestinatari(SenderBean sender, EmailBean mail) {
		List<TDestinatariEmailMgoBean> lista = new ArrayList<TDestinatariEmailMgoBean>();
		List<String> destinatariTo = sender.getAddressTo();
		List<String> destinatariCC = sender.getAddressCc();
		List<String> destinatariBCC = sender.getAddressBcc();
		for (String destTo : destinatariTo) {
			TDestinatariEmailMgoBean destinatario = new TDestinatariEmailMgoBean();
			destinatario.setIdEmail(mail.getMail().getIdEmail());
			destinatario.setIdDestinatarioEmail(KeyGenerator.gen());
			destinatario.setFlgDestOrigInvio(true);
			destinatario.setAccountDestinatario(destTo.trim());
			destinatario.setFlgTipoDestinatario(TipoDestinatario.TO.getValue());
			destinatario.setFlgDestEffettivo(true);
			destinatario.setFlgNotifInteropAgg(false);
			destinatario.setFlgNotifInteropAnn(false);
			destinatario.setFlgNotifInteropConf(false);
			destinatario.setFlgNotifInteropEcc(false);
			lista.add(destinatario);
		}
		if (destinatariCC != null) {
			for (String destCC : destinatariCC) {
				TDestinatariEmailMgoBean destinatario = new TDestinatariEmailMgoBean();
				destinatario.setIdEmail(mail.getMail().getIdEmail());
				destinatario.setIdDestinatarioEmail(KeyGenerator.gen());
				destinatario.setFlgDestOrigInvio(true);
				destinatario.setAccountDestinatario(destCC.trim());
				destinatario.setFlgTipoDestinatario(TipoDestinatario.CC.getValue());
				destinatario.setFlgDestEffettivo(true);
				destinatario.setFlgNotifInteropAgg(false);
				destinatario.setFlgNotifInteropAnn(false);
				destinatario.setFlgNotifInteropConf(false);
				destinatario.setFlgNotifInteropEcc(false);
				lista.add(destinatario);
			}
		}
		if (destinatariBCC != null) {
			for (String destBCC : destinatariBCC) {
				TDestinatariEmailMgoBean destinatario = new TDestinatariEmailMgoBean();
				destinatario.setIdEmail(mail.getMail().getIdEmail());
				destinatario.setIdDestinatarioEmail(KeyGenerator.gen());
				destinatario.setFlgDestOrigInvio(true);
				destinatario.setAccountDestinatario(destBCC.trim());
				destinatario.setFlgTipoDestinatario(TipoDestinatario.BCC.getValue());
				destinatario.setFlgDestEffettivo(true);
				destinatario.setFlgNotifInteropAgg(false);
				destinatario.setFlgNotifInteropAnn(false);
				destinatario.setFlgNotifInteropConf(false);
				destinatario.setFlgNotifInteropEcc(false);
				lista.add(destinatario);
			}
		}
		return lista;
	}

	private static List<TRelEmailFolderBean> creaRelemailInUscita(EmailBean mail, String idAccount, boolean isDraft) throws Exception {
		List<TRelEmailFolderBean> relazioni = new ArrayList<TRelEmailFolderBean>();
		relazioni.add(retrieveFolder(idAccount, mail.getMail(), FolderEmail.STANDARD_FOLDER_USCITA));
		return relazioni;
	}

	private static List<TRelEmailFolderBean> creaRelemailInUscitaInSession(EmailBean mail, String idAccount, boolean isDraft, Session session)
			throws Exception {
		List<TRelEmailFolderBean> relazioni = new ArrayList<TRelEmailFolderBean>();
		relazioni.add(retrieveFolderInSession(idAccount, mail.getMail(), FolderEmail.STANDARD_FOLDER_USCITA, session));
		return relazioni;
	}

	/**
	 * Metodo per ricavare le caselle da associare alla mail spedita
	 * 
	 * @param senderBean
	 * @param isDraft
	 * @return
	 */

	public static List<String> retrieveFolderForEmailMailInviata(SenderBean senderBean) {
		List<String> folderOut = new ArrayList<String>();
		// in base alla categoria imposto la folder corretta
		Categoria categoria = identificaCategoria(senderBean);
		switch (categoria) {
		case PEC:
			folderOut.add(FolderEmail.STANDARD_FOLDER_INVIO_PEC_DA_CONTROLLARE.getValue());
			folderOut.add(FolderEmail.STANDARD_FOLDER_INVIO_PEC_NON_INTEROPERABILI.getValue());
			break;
		case INTER_SEGN:
			folderOut.add(FolderEmail.STANDARD_FOLDER_INVIO_PEC_DA_CONTROLLARE.getValue());
			folderOut.add(FolderEmail.STANDARD_FOLDER_INVIO_PEC_INTEROPERABILI.getValue());
			break;
		case INTER_ECC:
			folderOut.add(FolderEmail.STANDARD_FOLDER_INVIO_PEC_DA_CONTROLLARE.getValue());
			folderOut.add(FolderEmail.STANDARD_FOLDER_INVIO_NOTIFICHE_INTEROPERABILITA_ECCEZIONE.getValue());
			break;
		case INTER_CONF:
			folderOut.add(FolderEmail.STANDARD_FOLDER_INVIO_PEC_DA_CONTROLLARE.getValue());
			folderOut.add(FolderEmail.STANDARD_FOLDER_INVIO_NOTIFICHE_INTEROPERABILITA_CONFERMA.getValue());
			break;
		case INTER_AGG:
			folderOut.add(FolderEmail.STANDARD_FOLDER_INVIO_PEC_DA_CONTROLLARE.getValue());
			folderOut.add(FolderEmail.STANDARD_FOLDER_INVIO_NOTIFICHE_INTEROPERABILITA_AGGIORNAMENTO.getValue());
			break;
		case INTER_ANN:
			folderOut.add(FolderEmail.STANDARD_FOLDER_INVIO_PEC_DA_CONTROLLARE.getValue());
			folderOut.add(FolderEmail.STANDARD_FOLDER_INVIO_NOTIFICHE_INTEROPERABILITA_ANNULLAMENTO.getValue());
			break;
		case ANOMALIA:
			folderOut.add(FolderEmail.STANDARD_FOLDER_INVIO_PEO.getValue());
			break;
		case ALTRO:
			folderOut.add(FolderEmail.STANDARD_FOLDER_INVIO.getValue());
			break;
		}
		// verifico se è un inoltro o una risposta
		if (senderBean.getRispInol() != null && senderBean.getRispInol().getRispInol() != null
				&& senderBean.getRispInol().getRispInol().equals(RispostaInoltro.RISPOSTA)) {
			folderOut.add(FolderEmail.STANDARD_FOLDER_INVIO_RISPOSTE.getValue());
		}
		if (senderBean.getRispInol() != null && senderBean.getRispInol().getRispInol() != null
				&& senderBean.getRispInol().getRispInol().equals(RispostaInoltro.INOLTRO)) {
			folderOut.add(FolderEmail.STANDARD_FOLDER_INVIO_INOLTRO.getValue());
		}
		return folderOut;

	}

	/**
	 * 
	 * @param bean
	 * @param sender
	 * @return
	 * @throws AurigaMailBusinessException
	 */
	private static List<TAttachEmailMgoBean> populateAttachEmailMgoBean(EmailBean bean, SenderBean sender, String mailbox) throws AurigaMailBusinessException {
		List<TAttachEmailMgoBean> attachsEmailList = new ArrayList<TAttachEmailMgoBean>();
		List<SenderAttachmentsBean> attachments = sender.getAttachments();
		if (attachments != null) {
			List<EnhancedFile> checkedAttachsList = populateCheckedAttachsList(sender, mailbox);
			for (EnhancedFile attach : checkedAttachsList) {
				TAttachEmailMgoBean attachMgoBean = new TAttachEmailMgoBean();
				attachMgoBean.setIdAttachEmail(KeyGenerator.gen());
				attachMgoBean.setFlgFirmato(attach.getIsFirmato());
				attachMgoBean.setFlgFirmaValida(attach.getIsFirmaValida());
				attachMgoBean.setDimensione(attach.getDimensione());
				if (StringUtils.isEmpty(attach.getOriginalFileName()) && StringUtils.isEmpty(attach.getNewName())) {
					throw new AurigaMailBusinessException("Nome dell'allegato non valorizzato");
				}
				// valorizzo sempre il nome dell'allegato con una delle
				// proprietà presenti
				else if (StringUtils.isEmpty(attach.getOriginalFileName())) {
					attachMgoBean.setNomeOriginale(attach.getNewName());
					attachMgoBean.setDisplayFilename(attach.getNewName());
				} else if (StringUtils.isEmpty(attach.getNewName())) {
					attachMgoBean.setNomeOriginale(attach.getOriginalFileName());
					attachMgoBean.setDisplayFilename(attach.getOriginalFileName());
				} else {
					attachMgoBean.setNomeOriginale(attach.getOriginalFileName());
					attachMgoBean.setDisplayFilename(attach.getNewName());
				}
				attachMgoBean.setIdEmail(bean.getMail().getIdEmail());
				attachMgoBean.setMimetype(attach.getMimeType());
				attachMgoBean.setImpronta(attach.getImpronta());
				attachMgoBean.setEncodingImpronta(attach.getEncoding());
				attachMgoBean.setAlgoritmoImpronta(attach.getAlgoritmo());
				attachMgoBean.setFlgPdfEditabile(attach.getIsPdfEditabile() != null ? attach.getIsPdfEditabile() : false);
				attachMgoBean.setFlgPdfConCommenti(attach.getIsPdfConCommenti() != null ? attach.getIsPdfConCommenti() : false);
				attachsEmailList.add(attachMgoBean);
			}
		}
		return attachsEmailList;
	}

	/**
	 * crea il bean della folder
	 * 
	 * @param idAccount
	 * @param mail
	 * @param tipoFolder
	 * @return
	 * @throws Exception
	 * @throws MailServerException
	 */
	public static TRelEmailFolderBean retrieveFolder(String idAccount, TEmailMgoBean mail, FolderEmail tipoFolder) throws Exception {
		TRelEmailFolderBean relEmailFolder = new TRelEmailFolderBean();
		TFolderCaselleBean folder = ricavaEmailFolder(idAccount, tipoFolder);
		relEmailFolder.setIdEmail(mail.getIdEmail());
		relEmailFolder.setIdFolderCasella(folder.getIdFolderCasella());
		return relEmailFolder;
	}

	/**
	 * crea il bean della folder
	 * 
	 * @param idAccount
	 * @param mail
	 * @param tipoFolder
	 * @return
	 * @throws Exception
	 * @throws MailServerException
	 */
	public static TRelEmailFolderBean retrieveFolderInSession(String idAccount, TEmailMgoBean mail, FolderEmail tipoFolder, Session session) throws Exception {
		TRelEmailFolderBean relEmailFolder = new TRelEmailFolderBean();
		TFolderCaselleBean folder = ricavaEmailFolderInSession(idAccount, tipoFolder, session);
		relEmailFolder.setIdEmail(mail.getIdEmail());
		relEmailFolder.setIdFolderCasella(folder.getIdFolderCasella());
		return relEmailFolder;
	}

	/**
	 * ricava la folder partendo dal nome della stessa e dall'idAccount
	 * 
	 * @param idAccount
	 * @param folder
	 * @return
	 * @throws Exception
	 * @throws MailServerException
	 */
	private static TFolderCaselleBean ricavaEmailFolder(String idAccount, FolderEmail folder) throws Exception {
		TFilterFetch<TFolderCaselleBean> filter = new TFilterFetch<TFolderCaselleBean>();
		TFolderCaselleBean filtro = new TFolderCaselleBean();
		filtro.setIdCasella(idAccount);
		filtro.setClassificazione(folder.getValue());
		filter.setFilter(filtro);
		TPagingList<TFolderCaselleBean> listaRis = null;
		try {
			listaRis = ((DaoTFolderCaselle) DaoFactory.getDao(DaoTFolderCaselle.class)).search(filter);
			if (listaRis.getData() == null || listaRis.getData().size() == 0) {
				log.error("Nessuna directory prevista per questa casella: " + idAccount);
				throw new AurigaMailBusinessException("Nessuna directory prevista per questa casella: " + idAccount);
			}
			if (listaRis.getData().size() > 1) {
				log.error("Presenza di due folder con lo stesso nome per la casella: " + idAccount);
				throw new AurigaMailBusinessException("Presenza di due folder con lo stesso nome per la casella: " + idAccount);
			}
		} catch (Exception e) {
			log.error("Impossibile creare il bean della casella: ", e);
			throw e;
		}
		return listaRis.getData().get(0);
	}

	/**
	 * ricava la folder partendo dal nome della stessa e dall'idAccount
	 * 
	 * @param idAccount
	 * @param folder
	 * @return
	 * @throws Exception
	 * @throws MailServerException
	 */
	private static TFolderCaselleBean ricavaEmailFolderInSession(String idAccount, FolderEmail folder, Session session) throws Exception {
		TFilterFetch<TFolderCaselleBean> filter = new TFilterFetch<TFolderCaselleBean>();
		TFolderCaselleBean filtro = new TFolderCaselleBean();
		filtro.setIdCasella(idAccount);
		filtro.setClassificazione(folder.getValue());
		filter.setFilter(filtro);
		TPagingList<TFolderCaselleBean> listaRis = null;
		try {
			listaRis = ((DaoTFolderCaselle) DaoFactory.getDao(DaoTFolderCaselle.class)).searchInSession(filter, session);
			if (listaRis.getData() == null || listaRis.getData().isEmpty()) {
				log.error("Nessuna directory prevista per la PEC per questa casella: " + idAccount);
				throw new AurigaMailBusinessException("Nessuna directory prevista per la PEC per questa casella: " + idAccount);
			}
			if (listaRis.getData().size() > 1) {
				log.error("Presenza di due folder con lo stesso nome per la casella: " + idAccount);
				throw new AurigaMailBusinessException("Presenza di due folder con lo stesso nome per la casella: " + idAccount);
			}
		} catch (Exception e) {
			log.error("Impossibile creare il bean della casella: ", e);
			throw e;
		}
		return listaRis.getData().get(0);
	}

	/**
	 * metodo che legge tutti i parametri di configurazione e li inserisce in una mappa
	 */
	public static void getParametersValue() {
		parametri = new ConcurrentHashMap<String, TParametersBean>();
		TFilterFetch<TParametersBean> filter = new TFilterFetch<TParametersBean>();
		try {
			List<TParametersBean> listaRis = ((DaoTParameters) DaoFactory.getDao(DaoTParameters.class)).getAllParameters(filter).getData();
			for (TParametersBean parametro : listaRis) {
				parametri.put(parametro.getParKey(), parametro);
			}
		} catch (Exception e) {
			log.error("Impossibile caricare i parametri, si useranno le impostazioni di default: ", e);
		}
	}

	/**
	 * metodo che legge tutti i parametri di configurazione e li inserisce in una mappa
	 */
	public static void getParametersValueInSession(Session session) {
		parametri = new ConcurrentHashMap<String, TParametersBean>();
		TFilterFetch<TParametersBean> filter = new TFilterFetch<TParametersBean>();
		try {
			List<TParametersBean> listaRis = ((DaoTParameters) DaoFactory.getDao(DaoTParameters.class)).getAllParametersInSession(filter, session).getData();
			for (TParametersBean parametro : listaRis) {
				parametri.put(parametro.getParKey(), parametro);
			}
		} catch (Exception e) {
			log.error("Impossibile caricare i parametri, si useranno le impostazioni di default: ", e);
		}
	}

	/**
	 * valorizzo la lista degli attach controllati
	 * 
	 * @param mailbox
	 * 
	 * @throws AurigaMailBusinessException
	 */
	private static List<EnhancedFile> populateCheckedAttachsList(SenderBean sender, String mailbox) throws AurigaMailBusinessException {
		List<SenderAttachmentsBean> lista = sender.getAttachments();
		List<EnhancedFile> checkedAttachsList = new ArrayList<EnhancedFile>();
		if (lista != null && lista.size() != 0) {
			long startGlobale = new Date().getTime();
			for (SenderAttachmentsBean att : lista) {
				EnhancedFile ef = new EnhancedFile();
				ef.setOriginalFileName(att.getOriginalName());
				ef.setNewName(att.getFilename());
				ef.setMimeType(att.getMimetype());
				ef.setIsFirmato(att.isFirmato());
				ef.setIsFirmaValida(att.isFirmaValida());
				ef.setImpronta(att.getImpronta());
				ef.setEncoding(att.getEncoding());
				ef.setAlgoritmo(att.getAlgoritmo());
				if(att.getFile() != null) {
					ef.setDimensione((long) att.getFile().length());
				} else {
					ef.setDimensione((long) att.getContent().length);
				}
				ef.setIsPdfEditabile(att.isPdfEditabile());
				ef.setIsPdfConCommenti(att.isPdfConCommenti());
				checkedAttachsList.add(ef);
			}
			long endGlobale = new Date().getTime();
			if (log.isDebugEnabled()) {
				log.debug("Tempo populateCheckedAttachsList " + (endGlobale - startGlobale));
			}
		}
		return checkedAttachsList;
	}

	/**
	 * verifico se devo salvare i destinatari in rubrica
	 * 
	 * @return
	 */
	private static boolean salvataggioInRubricaDestinatari() {
		TParametersBean parametroRubrica = parametri.get("INS_AUTO_DESTINATARI_IN_RUBRICA");
		boolean salvataggioInRubrica = false;
		if (parametroRubrica != null && (parametroRubrica.getStrValue().equalsIgnoreCase("TRUE") || parametroRubrica.getStrValue().equalsIgnoreCase("OUT"))) {
			salvataggioInRubrica = true;
		}
		return salvataggioInRubrica;
	}

	/**
	 * metodo per conoscere la necessità di salvare il mittente in rubrica
	 * 
	 * @return
	 */
	private static boolean salvataggioInRubricaMittente(SenderBean sender) {
		TParametersBean parametroRubrica = parametri.get("INS_AUTO_MITTENTI_IN_RUBRICA");
		boolean salvataggioInRubrica = false;
		if (parametroRubrica != null && parametroRubrica.getStrValue().equalsIgnoreCase("TRUE")) {
			salvataggioInRubrica = true;
		}
		if (sender.getIsPec() != null && sender.getIsPec() && parametroRubrica != null && parametroRubrica.getStrValue().equalsIgnoreCase("PEC")) {
			salvataggioInRubrica = true;
		}
		return salvataggioInRubrica;
	}

	/**
	 * ottengo l'id fruitore AOO della casella di interesse
	 * 
	 * @param idCasella
	 * @return
	 * @throws MailServerException
	 * @throws Exception
	 */
	public static String ricavaFruitoreCasella(String idCasella) throws Exception {
		List<TRelCaselleFruitoriBean> listaRelCaselleFruitori = new ArrayList<TRelCaselleFruitoriBean>();
		TFilterFetch<TRelCaselleFruitoriBean> filterFetch = new TFilterFetch<TRelCaselleFruitoriBean>();
		TRelCaselleFruitoriBean filtro = new TRelCaselleFruitoriBean();
		filtro.setIdCasella(idCasella);
		filterFetch.setFilter(filtro);
		try {
			listaRelCaselleFruitori = ((DaoTRelCaselleFruitori) DaoFactory.getDao(DaoTRelCaselleFruitori.class)).search(filterFetch).getData();
			if (listaRelCaselleFruitori.size() == 0) {
				log.error("Impossibile ricavare il fruitore della casella");
				throw new AurigaMailBusinessException("Impossibile ricavare il fruitore della casella");
			}
			TRelCaselleFruitoriBean relCaselleFruitori = listaRelCaselleFruitori.get(0);
			return restituisceFruitoreCasella(relCaselleFruitori);
		} catch (Exception e) {
			log.error("Impossibile ricavare il fruitore della casella", e);
			throw e;
		}
	}

	/**
	 * ottengo l'id fruitore AOO della casella di interesse
	 * 
	 * @param idCasella
	 * @return
	 * @throws MailServerException
	 * @throws Exception
	 */
	public static String ricavaFruitoreCasellaInSession(String idCasella, Session session) throws Exception {
		List<TRelCaselleFruitoriBean> listaRelCaselleFruitori = new ArrayList<TRelCaselleFruitoriBean>();
		TFilterFetch<TRelCaselleFruitoriBean> filterFetch = new TFilterFetch<TRelCaselleFruitoriBean>();
		TRelCaselleFruitoriBean filtro = new TRelCaselleFruitoriBean();
		filtro.setIdCasella(idCasella);
		filterFetch.setFilter(filtro);
		try {
			listaRelCaselleFruitori = ((DaoTRelCaselleFruitori) DaoFactory.getDao(DaoTRelCaselleFruitori.class)).searchInSession(filterFetch, session)
					.getData();
			if (listaRelCaselleFruitori.size() == 0) {
				log.error("Impossibile ricavare il fruitore della casella");
				throw new AurigaMailBusinessException("Impossibile ricavare il fruitore della casella");
			}
			TRelCaselleFruitoriBean relCaselleFruitori = listaRelCaselleFruitori.get(0);
			return restituisceFruitoreCasellaInSession(relCaselleFruitori, session);
		} catch (Exception e) {
			log.error("Impossibile ricavare il fruitore della casella", e);
			throw e;
		}
	}

	/**
	 * ricava il fruitore di tipo di riferimento
	 * 
	 * @param relCaselleFruitori
	 * @return
	 * @throws Exception
	 * @throws MailServerException
	 */
	private static String restituisceFruitoreCasella(TRelCaselleFruitoriBean relCaselleFruitori) throws Exception {
		List<TAnagFruitoriCaselleBean> listaFruitori = new ArrayList<TAnagFruitoriCaselleBean>();
		TFilterFetch<TAnagFruitoriCaselleBean> filterFetch = new TFilterFetch<TAnagFruitoriCaselleBean>();
		TAnagFruitoriCaselleBean filtro = new TAnagFruitoriCaselleBean();
		filtro.setIdFruitoreCasella(relCaselleFruitori.getIdFruitoreCasella());
		filterFetch.setFilter(filtro);
		try {
			listaFruitori = ((DaoTAnagFruitoriCaselle) DaoFactory.getDao(DaoTAnagFruitoriCaselle.class)).search(filterFetch).getData();
			if (listaFruitori.size() == 0) {
				log.error("Impossibile ricavare il fruitore della casella");
				throw new AurigaMailBusinessException("Impossibile ricavare il fruitore della casella");
			}
			TAnagFruitoriCaselleBean fruitore = listaFruitori.get(0);
			return fruitore.getIdEnteAOO();
		} catch (Exception e) {
			log.error("Impossibile ricavare il fruitore della casella", e);
			throw e;
		}
	}

	/**
	 * ricava il fruitore di tipo di riferimento
	 * 
	 * @param relCaselleFruitori
	 * @return
	 * @throws Exception
	 * @throws MailServerException
	 */
	private static String restituisceFruitoreCasellaInSession(TRelCaselleFruitoriBean relCaselleFruitori, Session session) throws Exception {
		List<TAnagFruitoriCaselleBean> listaFruitori = new ArrayList<TAnagFruitoriCaselleBean>();
		TFilterFetch<TAnagFruitoriCaselleBean> filterFetch = new TFilterFetch<TAnagFruitoriCaselleBean>();
		TAnagFruitoriCaselleBean filtro = new TAnagFruitoriCaselleBean();
		filtro.setIdFruitoreCasella(relCaselleFruitori.getIdFruitoreCasella());
		filterFetch.setFilter(filtro);
		try {
			listaFruitori = ((DaoTAnagFruitoriCaselle) DaoFactory.getDao(DaoTAnagFruitoriCaselle.class)).searchInSession(filterFetch, session).getData();
			if (listaFruitori.size() == 0) {
				log.error("Impossibile ricavare il fruitore della casella");
				throw new AurigaMailBusinessException("Impossibile ricavare il fruitore della casella");
			}
			TAnagFruitoriCaselleBean fruitore = listaFruitori.get(0);
			return fruitore.getIdEnteAOO();
		} catch (Exception e) {
			log.error("Impossibile ricavare il fruitore della casella", e);
			throw e;
		}
	}

	/**
	 * ricava gli indirizzi di interesse da una lista di indirizzi di tipo {@link DestinatariInvioMailXmlBean}
	 * 
	 * @param destinatari
	 * @param tipo
	 * @return
	 */
	private static List<String> ricavaIndirizziInvioMail(List<DestinatariInvioMailXmlBean> destinatari, TipoDestinatario tipo) {
		List<String> indirizzi = new ArrayList<String>();
		if (destinatari != null && destinatari.size() > 0) {
			for (DestinatariInvioMailXmlBean dest : destinatari) {
				if (dest.getTipo().equalsIgnoreCase(tipo.getValue()))
					indirizzi.add(dest.getIndirizzo());
			}
		}

		return indirizzi;
	}

	/**
	 * ricava gli indirizzi di interesse
	 * 
	 * @param destinatari
	 * @param tipo
	 * @return
	 */
	private static List<String> ricavaIndirizziTo(List<TDestinatariEmailMgoBean> destinatari, TipoDestinatario tipo) {
		List<String> indirizzi = new ArrayList<String>();
		if (destinatari != null && destinatari.size() > 0) {
			for (TDestinatariEmailMgoBean dest : destinatari) {
				if (dest.getFlgTipoDestinatario().equals(tipo.getValue()))
					indirizzi.add(dest.getAccountDestinatario());
			}
		}

		return indirizzi;
	}

	public static FileUtilities getFileUtils() {
		return fileUtils;
	}

	public static void setFileUtils(FileUtilities fileUtils) {
		SendUtils.fileUtils = fileUtils;
	}

	public static StorageCenter getStorage() {
		return storage;
	}

	public static void setStorage(StorageCenter storage) {
		SendUtils.storage = storage;
	}

	public static MailBreaker getBreaker() {
		return breaker;
	}

	public static void setBreaker(MailBreaker breaker) {
		SendUtils.breaker = breaker;
	}

}
