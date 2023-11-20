/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Quota;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.Transformers;

import com.sun.mail.imap.IMAPSSLStore;
import com.sun.mail.imap.IMAPStore;

import it.eng.aurigamailbusiness.bean.MailboxAccountBean;
import it.eng.aurigamailbusiness.bean.MailboxBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.converters.TEmailMgoToTEmailMgoBean;
import it.eng.aurigamailbusiness.database.dao.DaoMailbox;
import it.eng.aurigamailbusiness.database.dao.DaoMailboxAccount;
import it.eng.aurigamailbusiness.database.mail.MailboxMessage;
import it.eng.aurigamailbusiness.database.mail.MailboxMessageOperation;
import it.eng.aurigamailbusiness.database.mail.TEmailMgo;
import it.eng.aurigamailbusiness.database.utility.DaoFactory;
import it.eng.aurigamailbusiness.database.utility.send.InputOutput;
import it.eng.aurigamailbusiness.database.utility.send.SendUtils;
import it.eng.aurigamailbusiness.exception.AurigaMailBusinessException;
import it.eng.aurigamailbusiness.sender.AccountConfigKey;
import it.eng.aurigamailbusiness.utility.cryptography.CryptographyUtil;
import it.eng.aurigamailbusiness.utility.cryptography.InvalidEncryptionKeyException;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.converter.UtilPopulate;

public class MailboxUtil {

	private static Logger LOGGER = LogManager.getLogger(MailboxUtil.class);

	/**
	 * Restituisce l'id account a partire dall'account in input
	 * 
	 * @param account
	 * @param session
	 * @return
	 * @throws Exception
	 */

	public static String getIdAccountInSession(String account, org.hibernate.Session session) throws Exception {

		if (StringUtils.isBlank(account)) {
			throw new AurigaMailBusinessException("Account non valorizzato");
		}

		String idAccount = null;
		TFilterFetch<MailboxAccountBean> ff = new TFilterFetch<MailboxAccountBean>();
		MailboxAccountBean filtro = new MailboxAccountBean();
		filtro.setAccount(account);
		ff.setFilter(filtro);
		List<MailboxAccountBean> mailboxes = null;
		try {
			mailboxes = ((DaoMailboxAccount) DaoFactory.getDao(DaoMailboxAccount.class)).searchInSession(ff, session).getData();
			if (mailboxes == null || mailboxes.size() == 0) {
				LOGGER.error(String.format("Nessun id account trovato per %1$s", account));
				throw new AurigaMailBusinessException(String.format("Nessun id account trovato per %1$s", account));
			}
			idAccount = mailboxes.get(0).getIdAccount();
		} catch (Exception e) {
			LOGGER.error(String.format("Si è verificata la seguente eccezione durante il recupero dell'idAccount per %1$s", account), e);
			throw e;
		}
		return idAccount;
	}

	/**
	 * Restituisce l'id account a partire dall'account in input
	 * 
	 * @param account
	 * @return
	 * @throws Exception
	 */

	public static String getIdAccount(String account) throws Exception {

		if (StringUtils.isBlank(account)) {
			throw new AurigaMailBusinessException("Account non valorizzato");
		}

		String idAccount = null;
		TFilterFetch<MailboxAccountBean> ff = new TFilterFetch<MailboxAccountBean>();
		MailboxAccountBean filtro = new MailboxAccountBean();
		filtro.setAccount(account);
		ff.setFilter(filtro);
		List<MailboxAccountBean> mailboxes = null;
		try {
			mailboxes = ((DaoMailboxAccount) DaoFactory.getDao(DaoMailboxAccount.class)).search(ff).getData();
			if (mailboxes == null || mailboxes.size() == 0) {
				LOGGER.error(String.format("Nessun id account trovato per %1$s", account));
				throw new AurigaMailBusinessException(String.format("Nessun id account trovato per %1$s", account));
			}
			idAccount = mailboxes.get(0).getIdAccount();
		} catch (Exception e) {
			LOGGER.error(String.format("Si è verificata la seguente eccezione durante il recupero dell'idAccount per %1$s", account), e);
			throw e;
		}
		return idAccount;
	}

	/**
	 * Restituisce l'id della mailbox associata all'id account in input
	 * 
	 * @param idAccount
	 * @return
	 * @throws Exception
	 */

	public static String getIdMailbox(String idAccount) throws Exception {
		List<MailboxBean> boxes = null;
		try {
			TFilterFetch<MailboxBean> filter = new TFilterFetch<MailboxBean>();
			MailboxBean config = new MailboxBean();
			config.setIdAccount(idAccount);
			filter.setFilter(config);
			boxes = ((DaoMailbox) DaoFactory.getDao(DaoMailbox.class)).search(filter).getData();
			if (boxes == null || boxes.isEmpty()) {
				LOGGER.error(String.format("Nessuna mailbox configurata per l'account scelto %1$s", idAccount));
				throw new AurigaMailBusinessException("Nessuna mailbox configurata per l'account scelto");
			}
		} catch (Exception e) {
			LOGGER.error(String.format("Si è verificata la seguente eccezione durante il recupero della mailbox per l'account scelto %1$s", idAccount), e);
			throw e;
		}
		return boxes.get(0).getIdMailbox();
	}

	/**
	 * Restituisce l'id account associato all'id mailbox in input
	 * 
	 * @param idAccount
	 * @return
	 * @throws Exception
	 */

	public static String getIdAccountByIdMailbox(String idMailbox) throws Exception {
		List<MailboxBean> boxes = null;
		try {
			TFilterFetch<MailboxBean> filter = new TFilterFetch<MailboxBean>();
			MailboxBean config = new MailboxBean();
			config.setIdMailbox(idMailbox);
			filter.setFilter(config);
			boxes = ((DaoMailbox) DaoFactory.getDao(DaoMailbox.class)).search(filter).getData();
			if (boxes == null || boxes.size() == 0) {
				LOGGER.error(String.format("Nessuna mailbox configurata per l'id %1$s", idMailbox));
				throw new AurigaMailBusinessException(String.format("Nessuna mailbox configurata per l'id %1$s", idMailbox));
			}
		} catch (Exception e) {
			LOGGER.error(String.format("Si è verificata la seguente eccezione durante il recupero della mailbox associata all'id %1$s", idMailbox), e);
			throw e;
		}
		return boxes.get(0).getIdAccount();
	}

	/**
	 * Restituisce l'id account associato all'id mailbox in input
	 * 
	 * @param idAccount
	 * @return
	 * @throws Exception
	 */

	public static String getIdAccountByIdMailboxInSession(String idMailbox, org.hibernate.Session session) throws Exception {
		List<MailboxBean> boxes = null;
		try {
			TFilterFetch<MailboxBean> fet = new TFilterFetch<MailboxBean>();
			MailboxBean config = new MailboxBean();
			config.setIdMailbox(idMailbox);
			fet.setFilter(config);
			boxes = ((DaoMailbox) DaoFactory.getDao(DaoMailbox.class)).searchInSession(fet, session).getData();
			if (boxes == null || boxes.size() == 0) {
				LOGGER.error(String.format("Nessuna mailbox configurata per l'id %1$s", idMailbox));
				throw new AurigaMailBusinessException(String.format("Nessuna mailbox configurata per l'id %1$s", idMailbox));
			}
		} catch (Exception e) {
			LOGGER.error(String.format("Si è verificata la seguente eccezione durante il recupero della mailbox associata all'id %1$s", idMailbox), e);
			throw e;
		}
		return boxes.get(0).getIdAccount();
	}

	/**
	 * Restituisce l'id della mailbox associata all'id account in input
	 * 
	 * @param idAccount
	 * @param session
	 * @return
	 * @throws Exception
	 */

	public static String getIdMailboxInSession(String idAccount, org.hibernate.Session session) throws Exception {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format("Ricerca della mailbox per %1$s", idAccount));
		}

		List<MailboxBean> boxes = null;
		try {
			TFilterFetch<MailboxBean> fet = new TFilterFetch<MailboxBean>();
			MailboxBean config = new MailboxBean();
			config.setIdAccount(idAccount);
			fet.setFilter(config);
			boxes = ((DaoMailbox) DaoFactory.getDao(DaoMailbox.class)).searchInSession(fet, session).getData();
			if (boxes == null || boxes.size() == 0) {
				LOGGER.error(String.format("Nessuna mailbox configurata per l'account %1$s", idAccount));
				throw new AurigaMailBusinessException("Nessuna mailbox configurata per l'account scelto");
			}
		} catch (Exception e) {
			LOGGER.error(String.format("Si è verificata la seguente eccezione durante il recupero della mailbox per %1$s", idAccount), e);
			throw e;
		}
		return boxes.get(0).getIdMailbox();
	}

	/**
	 * Metodo che recupera l'id Account e le relative proprietà associate a {@link idMailbox} e testa la connessione al server SMTP
	 * 
	 * @param idMailbox
	 * @throws Exception
	 *
	 */

	public static Boolean connectSMTPByIdMailbox(String idMailbox) throws Exception {

		String idAccount = getIdAccountByIdMailbox(idMailbox);

		return connectSMTPByIdAccount(idAccount);

	}

	/**
	 * Metodo che recupera da database le configurazioni associate a {@link idAccount} e testa la connessione al server SMTP
	 * 
	 * @param idAccount
	 * @throws Exception
	 *
	 */

	public static Boolean connectSMTPByIdAccount(String idAccount) throws Exception {

		Transport transport = null;
		Boolean result = false;

		// è sufficiente un timeout basso per verificare la connessione al
		// server
		final String MAIL_TIMEOUT = "10000";

		try {

			// Recupero le proprietà dell'account
			Properties propertiesAccount = SendUtils.getAccountProperties(idAccount);

			// recupero la sessione con le proprietà precedentemente impostate
			// imposto timeout SMTP

			propertiesAccount.setProperty(AccountConfigKey.SMTP_CONNECTION_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																												// connection
																												// timeout
																												// in
																												// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.SMTP_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																									// read
																									// connection
																									// timeout
																									// in
																									// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.SMTP_WRITE_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																										// write
																										// connection
																										// timeout
																										// in
																										// millisecondi

			propertiesAccount.setProperty(AccountConfigKey.SMTPS_CONNECTION_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																												// connection
																												// timeout
																												// in
																												// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.SMTPS_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																									// read
																									// connection
																									// timeout
																									// in
																									// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.SMTPS_WRITE_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																											// write
																											// connection
																											// timeout
																											// in
																											// millisecondi

			Session session = Session.getInstance(propertiesAccount);
			session.setDebug(true);
			LOGGER.info("Proprietà sessione: " + session.getProperties());

			// Host
			String host = propertiesAccount.getProperty(AccountConfigKey.SMTP_HOST.keyname());
			// Porta
			Integer port = Integer.valueOf(propertiesAccount.getProperty(AccountConfigKey.SMTP_PORT.keyname()));
			// Username
			String username = null;
			if (propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname()) != null) {
				username = propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname());
			}
			// Password
			String password = null;
			if (propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_PASSWORD.keyname()) != null) {
				try {
					password = CryptographyUtil.decryptionAccountPasswordWithAES(propertiesAccount);
				} catch (InvalidEncryptionKeyException e) {
					LOGGER.error("Eccezione nella decriptazione della password", e);
					throw e;
				}
			}

			Boolean isSSL = false;

			if (propertiesAccount.getProperty(AccountConfigKey.SMTP_SSL.keyname()) != null) {
				isSSL = Boolean.parseBoolean(propertiesAccount.getProperty(AccountConfigKey.SMTP_SSL.keyname(), "false"));
			}

			if (isSSL) {
				transport = session.getTransport("smtps");
			} else {
				transport = session.getTransport("smtp");
			}

			// connessione al server smtp
			transport.connect(host, port, username, password);

			LOGGER.info("Connessione eseguita con successo a " + host);

			result = transport.isConnected();

		} catch (AuthenticationFailedException e) {
			LOGGER.error("Eccezione autenticazione", e);
			throw e;
		} catch (MessagingException e) {
			LOGGER.error("Eccezione JavaMail", e);
			throw e;
		} catch (Exception e) {
			LOGGER.error("Eccezione generica", e);
			throw e;
		}

		finally {
			// chiusura della connessione
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					LOGGER.error("Eccezione nella chiusura della connessione al server SMTP", e);
					throw e;
				}
			}

		}

		return result;

	}

	/**
	 * Metodo che recupera l'id Account e le relative proprietà associate a {@link idMailbox} e testa la connessione al server IMAP
	 * 
	 * @param idMailbox
	 * @throws Exception
	 *
	 */

	public static Boolean connectIMAPByIdMailbox(String idMailbox) throws Exception {

		String idAccount = getIdAccountByIdMailbox(idMailbox);

		return connectIMAPByIdAccount(idAccount);

	}

	/**
	 * Metodo che recupera da database le configurazioni associate a {@link idAccount} e testa la connessione al server IMAP
	 * 
	 * @param idAccount
	 * @throws Exception
	 *
	 */

	public static Boolean connectIMAPByIdAccount(String idAccount) throws Exception {

		Store store = null;
		Boolean result = false;

		// è sufficiente un timeout basso per verificare la connessione al
		// server
		final String MAIL_TIMEOUT = "10000";

		try {

			// Recupero le proprietà dell'account
			Properties propertiesAccount = SendUtils.getAccountProperties(idAccount);

			// recupero la sessione con le proprietà precedentemente impostate
			// imposto timeout SMTP

			propertiesAccount.setProperty(AccountConfigKey.IMAP_CONNECTION_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																												// connection
																												// timeout
																												// in
																												// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.IMAP_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																									// read
																									// connection
																									// timeout
																									// in
																									// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.IMAP_WRITE_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																										// write
																										// connection
																										// timeout
																										// in
																										// millisecondi

			propertiesAccount.setProperty(AccountConfigKey.IMAPS_CONNECTION_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																												// connection
																												// timeout
																												// in
																												// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.IMAPS_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																									// read
																									// connection
																									// timeout
																									// in
																									// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.IMAPS_WRITE_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																											// write
																											// connection
																											// timeout
																											// in
																											// millisecondi

			Session session = Session.getInstance(propertiesAccount);
			session.setDebug(true);
			LOGGER.info("Proprietà sessione: " + session.getProperties());

			// Host
			String host = propertiesAccount.getProperty(AccountConfigKey.IMAP_HOST.keyname());
			// Porta
			Integer port = Integer.valueOf(propertiesAccount.getProperty(AccountConfigKey.IMAP_PORT.keyname()));
			// Username
			String username = null;
			if (propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname()) != null) {
				username = propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname());
			}
			// in alcuni casi potrebbe essere specificato un username specifico
			// per imap
			if (propertiesAccount.getProperty(AccountConfigKey.IMAP_USERNAME.keyname()) != null) {
				username = propertiesAccount.getProperty(AccountConfigKey.IMAP_USERNAME.keyname());
			}
			// Password
			String password = null;
			if (propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_PASSWORD.keyname()) != null) {
				try {
					password = CryptographyUtil.decryptionAccountPasswordWithAES(propertiesAccount);
				} catch (InvalidEncryptionKeyException e) {
					LOGGER.error("Eccezione nella decriptazione della password", e);
					throw e;
				}
			}

			Boolean isSSL = false;

			if (propertiesAccount.getProperty(AccountConfigKey.IMAP_SSL.keyname()) != null) {
				isSSL = Boolean.parseBoolean(propertiesAccount.getProperty(AccountConfigKey.IMAP_SSL.keyname(), "false"));
			}

			if (isSSL) {
				store = session.getStore("imaps");
			} else {
				store = session.getStore("imap");
			}

			// connessione al server smtp
			store.connect(host, port, username, password);

			LOGGER.info("Connessione eseguita con successo a " + host);

			result = store.isConnected();

		} catch (AuthenticationFailedException e) {
			LOGGER.error("Eccezione autenticazione", e);
			throw e;
		} catch (MessagingException e) {
			LOGGER.error("Eccezione JavaMail", e);
			throw e;
		} catch (Exception e) {
			LOGGER.error("Eccezione generica", e);
			throw e;
		}

		finally {
			// chiusura della connessione
			if (store != null) {
				try {
					store.close();
				} catch (MessagingException e) {
					LOGGER.error("Eccezione nella chiusura della connessione al server IMAP", e);
					throw e;
				}
			}

		}

		return result;

	}
	
	/**
	 * Metodo che recupera da database le configurazioni associate a {@link idAccount} e testa la connessione al server IMAP
	 * 
	 * @param idAccount
	 * @param password
	 * @throws Exception
	 *
	 */

	public static Boolean connectIMAPByEmail(String email, String password) throws Exception {

		Store store = null;
		Boolean result = false;

		// è sufficiente un timeout basso per verificare la connessione al
		// server
		final String MAIL_TIMEOUT = "10000";

		try {

			// Recupero le proprietà dell'account
			Properties propertiesAccount = SendUtils.getAccountPropertiesByEmail(email);

			// recupero la sessione con le proprietà precedentemente impostate
			// imposto timeout SMTP

			propertiesAccount.setProperty(AccountConfigKey.IMAP_CONNECTION_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																												// connection
																												// timeout
																												// in
																												// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.IMAP_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																									// read
																									// connection
																									// timeout
																									// in
																									// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.IMAP_WRITE_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																										// write
																										// connection
																										// timeout
																										// in
																										// millisecondi

			propertiesAccount.setProperty(AccountConfigKey.IMAPS_CONNECTION_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																												// connection
																												// timeout
																												// in
																												// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.IMAPS_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																									// read
																									// connection
																									// timeout
																									// in
																									// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.IMAPS_WRITE_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																											// write
																											// connection
																											// timeout
																											// in
																											// millisecondi

			Session session = Session.getInstance(propertiesAccount);
			session.setDebug(true);
			LOGGER.info("Proprietà sessione: " + session.getProperties());

			// Host
			String host = propertiesAccount.getProperty(AccountConfigKey.IMAP_HOST.keyname());
			// Porta
			Integer port = Integer.valueOf(propertiesAccount.getProperty(AccountConfigKey.IMAP_PORT.keyname()));
			// Username
			String username = null;
			if (propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname()) != null) {
				username = propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname());
			}
			// in alcuni casi potrebbe essere specificato un username specifico
			// per imap
			if (propertiesAccount.getProperty(AccountConfigKey.IMAP_USERNAME.keyname()) != null) {
				username = propertiesAccount.getProperty(AccountConfigKey.IMAP_USERNAME.keyname());
			}
			Boolean isSSL = false;

			if (propertiesAccount.getProperty(AccountConfigKey.IMAP_SSL.keyname()) != null) {
				isSSL = Boolean.parseBoolean(propertiesAccount.getProperty(AccountConfigKey.IMAP_SSL.keyname(), "false"));
			}

			if (isSSL) {
				store = session.getStore("imaps");
			} else {
				store = session.getStore("imap");
			}

			// connessione al server smtp
			store.connect(host, port, username, password);

			LOGGER.info("Connessione eseguita con successo a " + host);

			result = store.isConnected();

		} catch (AuthenticationFailedException e) {
			LOGGER.error("Eccezione autenticazione", e);
			throw e;
		} catch (MessagingException e) {
			LOGGER.error("Eccezione JavaMail", e);
			throw e;
		} catch (Exception e) {
			LOGGER.error("Eccezione generica", e);
			throw e;
		}

		finally {
			// chiusura della connessione
			if (store != null) {
				try {
					store.close();
				} catch (MessagingException e) {
					LOGGER.error("Eccezione nella chiusura della connessione al server IMAP", e);
					throw e;
				}
			}

		}

		return result;

	}

	/**
	 * Metodo che recupera l'id Account e le relative proprietà associate a {@link idMailbox} e effettua la connessione al server IMAP restituendo tutte le
	 * folder attive e il numero di messaggi presenti in esse
	 * 
	 * @param idMailbox
	 * @throws Exception
	 *
	 */

	public static List<MailboxFolderInfoBean> getFolderByIdMailbox(String idMailbox) throws Exception {

		String idAccount = getIdAccountByIdMailbox(idMailbox);

		return getFolderByIdAccount(idAccount);

	}

	/**
	 * Metodo che recupera l'id Account e le relative proprietà associate a {@link idMailbox} e effettua la connessione al server IMAP restituendo tutte le
	 * quote associate alla mailbox
	 * 
	 * @param idMailbox
	 * @throws Exception
	 *
	 */

	public static List<Quota> getQuotaByIdMailbox(String idMailbox) throws Exception {

		String idAccount = getIdAccountByIdMailbox(idMailbox);

		return getQuotaByIdAccount(idAccount);

	}

	/**
	 * Metodo che recupera da database le configurazioni associate a {@link idAccount} e effettua la connessione al server IMAP restituendo tutte le folder
	 * attive e il numero di messaggi presenti in esse
	 * 
	 * @param idAccount
	 * @throws Exception
	 *
	 */

	public static List<MailboxFolderInfoBean> getFolderByIdAccount(String idAccount) throws Exception {

		Store store = null;
		List<MailboxFolderInfoBean> result = new ArrayList<MailboxFolderInfoBean>();

		// è sufficiente un timeout basso per verificare la connessione al
		// server
		final String MAIL_TIMEOUT = "10000";

		try {

			// Recupero le proprietà dell'account
			Properties propertiesAccount = SendUtils.getAccountProperties(idAccount);

			// recupero la sessione con le proprietà precedentemente impostate
			// imposto timeout SMTP

			propertiesAccount.setProperty(AccountConfigKey.IMAP_CONNECTION_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																												// connection
																												// timeout
																												// in
																												// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.IMAP_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																									// read
																									// connection
																									// timeout
																									// in
																									// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.IMAP_WRITE_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																										// write
																										// connection
																										// timeout
																										// in
																										// millisecondi

			propertiesAccount.setProperty(AccountConfigKey.IMAPS_CONNECTION_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																												// connection
																												// timeout
																												// in
																												// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.IMAPS_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																									// read
																									// connection
																									// timeout
																									// in
																									// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.IMAPS_WRITE_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																											// write
																											// connection
																											// timeout
																											// in
																											// millisecondi

			Session session = Session.getInstance(propertiesAccount);
			session.setDebug(true);
			LOGGER.info("Proprietà sessione: " + session.getProperties());

			// Host
			String host = propertiesAccount.getProperty(AccountConfigKey.IMAP_HOST.keyname());
			// Porta
			Integer port = Integer.valueOf(propertiesAccount.getProperty(AccountConfigKey.IMAP_PORT.keyname()));
			// Username
			String username = null;
			if (propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname()) != null) {
				username = propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname());
			}
			// in alcuni casi potrebbe essere specificato un username specifico
			// per imap
			if (propertiesAccount.getProperty(AccountConfigKey.IMAP_USERNAME.keyname()) != null) {
				username = propertiesAccount.getProperty(AccountConfigKey.IMAP_USERNAME.keyname());
			}
			// Password
			String password = null;
			if (propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_PASSWORD.keyname()) != null) {
				try {
					password = CryptographyUtil.decryptionAccountPasswordWithAES(propertiesAccount);
				} catch (InvalidEncryptionKeyException e) {
					LOGGER.error("Eccezione nella decriptazione della password", e);
					throw e;
				}
			}

			Boolean isSSL = false;

			if (propertiesAccount.getProperty(AccountConfigKey.IMAP_SSL.keyname()) != null) {
				isSSL = Boolean.parseBoolean(propertiesAccount.getProperty(AccountConfigKey.IMAP_SSL.keyname(), "false"));
			}

			if (isSSL) {
				store = session.getStore("imaps");
			} else {
				store = session.getStore("imap");
			}

			// connessione al server smtp
			store.connect(host, port, username, password);

			LOGGER.info("Connessione eseguita con successo a " + host);

			Folder[] folders = store.getDefaultFolder().list("*");

			for (Folder folder : folders) {
				// folder.list()
				MailboxFolderInfoBean infoBean = new MailboxFolderInfoBean();
				LOGGER.info(folder.getFullName() + ": " + folder.getMessageCount());
				infoBean.setName(folder.getFullName());
				infoBean.setCountMessage(folder.getMessageCount());
				result.add(infoBean);
			}

		} catch (AuthenticationFailedException e) {
			LOGGER.error("Eccezione autenticazione", e);
			throw e;
		} catch (MessagingException e) {
			LOGGER.error("Eccezione JavaMail", e);
			throw e;
		} catch (Exception e) {
			LOGGER.error("Eccezione generica", e);
			throw e;
		}

		finally {
			// chiusura della connessione
			if (store != null) {
				try {
					store.close();
				} catch (MessagingException e) {
					LOGGER.error("Eccezione nella chiusura della connessione al server IMAP", e);
					throw e;
				}
			}

		}

		return result;

	}

	/**
	 * Metodo che recupera l'id Account e le relative proprietà associate a {@link idMailbox} e effettua la connessione al server IMAP restituendo tutte le
	 * folder attive e il numero di messaggi presenti in esse, oltre alle informazioni sulle quote disponibili
	 * 
	 * @param idMailbox
	 * @throws Exception
	 *
	 */

	public static MailboxIMAPInfoBean getFolderAndQuotaByIdMailbox(String idMailbox) throws Exception {

		String idAccount = getIdAccountByIdMailbox(idMailbox);

		return getFolderAndQuotaByIdAccount(idAccount);

	}

	/**
	 * Metodo che recupera da database le configurazioni associate a {@link idAccount} e effettua la connessione al server IMAP restituendo tutte le folder
	 * attive e il numero di messaggi presenti in esse, oltre alle informazioni sulle quote disponibili
	 * 
	 * @param idAccount
	 * @throws Exception
	 *
	 */

	public static MailboxIMAPInfoBean getFolderAndQuotaByIdAccount(String idAccount) throws Exception {

		IMAPStore store = null;
		MailboxIMAPInfoBean result = new MailboxIMAPInfoBean();

		// è sufficiente un timeout basso per verificare la connessione al
		// server
		final String MAIL_TIMEOUT = "10000";

		try {

			// Recupero le proprietà dell'account
			Properties propertiesAccount = SendUtils.getAccountProperties(idAccount);

			// recupero la sessione con le proprietà precedentemente impostate
			// imposto timeout SMTP

			propertiesAccount.setProperty(AccountConfigKey.IMAP_CONNECTION_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																												// connection
																												// timeout
																												// in
																												// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.IMAP_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																									// read
																									// connection
																									// timeout
																									// in
																									// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.IMAP_WRITE_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																										// write
																										// connection
																										// timeout
																										// in
																										// millisecondi

			propertiesAccount.setProperty(AccountConfigKey.IMAPS_CONNECTION_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																												// connection
																												// timeout
																												// in
																												// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.IMAPS_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																									// read
																									// connection
																									// timeout
																									// in
																									// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.IMAPS_WRITE_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																											// write
																											// connection
																											// timeout
																											// in
																											// millisecondi

			Session session = Session.getInstance(propertiesAccount);
			session.setDebug(true);
			LOGGER.info("Proprietà sessione: " + session.getProperties());

			// Host
			String host = propertiesAccount.getProperty(AccountConfigKey.IMAP_HOST.keyname());
			// Porta
			Integer port = Integer.valueOf(propertiesAccount.getProperty(AccountConfigKey.IMAP_PORT.keyname()));
			// Username
			String username = null;
			if (propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname()) != null) {
				username = propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname());
			}
			// in alcuni casi potrebbe essere specificato un username specifico
			// per imap
			if (propertiesAccount.getProperty(AccountConfigKey.IMAP_USERNAME.keyname()) != null) {
				username = propertiesAccount.getProperty(AccountConfigKey.IMAP_USERNAME.keyname());
			}
			// Password
			String password = null;
			if (propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_PASSWORD.keyname()) != null) {
				try {
					password = CryptographyUtil.decryptionAccountPasswordWithAES(propertiesAccount);
				} catch (InvalidEncryptionKeyException e) {
					LOGGER.error("Eccezione nella decriptazione della password", e);
					throw e;
				}
			}

			Boolean isSSL = false;

			if (propertiesAccount.getProperty(AccountConfigKey.IMAP_SSL.keyname()) != null) {
				isSSL = Boolean.parseBoolean(propertiesAccount.getProperty(AccountConfigKey.IMAP_SSL.keyname(), "false"));
			}

			if (isSSL) {
				store = (IMAPSSLStore) session.getStore("imaps");
			} else {
				store = (IMAPStore) session.getStore("imap");
			}

			// connessione al server smtp
			store.connect(host, port, username, password);

			LOGGER.info("Connessione eseguita con successo a " + host);

			List<MailboxFolderInfoBean> infoFolders = new ArrayList<MailboxFolderInfoBean>();
			Folder[] folders = store.getDefaultFolder().list("*");

			for (Folder folder : folders) {
				// folder.list()
				MailboxFolderInfoBean infoBean = new MailboxFolderInfoBean();
				LOGGER.info(folder.getFullName() + ": " + folder.getMessageCount());
				infoBean.setName(folder.getFullName());
				infoBean.setCountMessage(folder.getMessageCount());
				infoFolders.add(infoBean);
			}

			List<Quota> infoQuotas = new ArrayList<Quota>();
			try {
				// visualizzo info quote
				Quota[] quotas = store.getQuota("INBOX");

				for (Quota quota : quotas) {
					if (quota != null) {
						for (Quota.Resource resource : quota.resources) {
							LOGGER.info(String.format("Nome:'%s', Limite:'%s', Utilizzo:'%s'", resource.name, resource.limit, resource.usage));
							// LOGGER.info(String.format("Percentuale
							// usata:'%f'",
							// MailboxIMAPInfoBean.getPercentageQuota(resource)));
						}
						infoQuotas.add(quota);
					}
				}

			} catch (MessagingException exc) {
				LOGGER.warn("Il server non supporta l'estensione QUOTA", exc);
			}

			// valorizzo oggetto result
			result.setFolders(infoFolders);
			// result.setQuotas(infoQuotas);

		} catch (AuthenticationFailedException e) {
			LOGGER.error("Eccezione autenticazione", e);
			throw e;
		} catch (MessagingException e) {
			LOGGER.error("Eccezione JavaMail", e);
			throw e;
		} catch (Exception e) {
			LOGGER.error("Eccezione generica", e);
			throw e;
		}

		finally {
			// chiusura della connessione
			if (store != null) {
				try {
					store.close();
				} catch (MessagingException e) {
					LOGGER.error("Eccezione nella chiusura della connessione al server IMAP", e);
					throw e;
				}
			}

		}

		return result;

	}

	/**
	 * Metodo che recupera da database le configurazioni associate a {@link idAccount} e effettua la connessione al server IMAP restituendo tutte le quote
	 * associate alla mailbox
	 * 
	 * @param idAccount
	 * @throws Exception
	 *
	 */

	public static List<Quota> getQuotaByIdAccount(String idAccount) throws Exception {

		IMAPStore store = null;
		List<Quota> result = new ArrayList<Quota>();

		// è sufficiente un timeout basso per verificare la connessione al
		// server
		final String MAIL_TIMEOUT = "10000";

		try {

			// Recupero le proprietà dell'account
			Properties propertiesAccount = SendUtils.getAccountProperties(idAccount);

			// recupero la sessione con le proprietà precedentemente impostate
			// imposto timeout SMTP

			propertiesAccount.setProperty(AccountConfigKey.IMAP_CONNECTION_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																												// connection
																												// timeout
																												// in
																												// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.IMAP_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																									// read
																									// connection
																									// timeout
																									// in
																									// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.IMAP_WRITE_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																										// write
																										// connection
																										// timeout
																										// in
																										// millisecondi

			propertiesAccount.setProperty(AccountConfigKey.IMAPS_CONNECTION_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																												// connection
																												// timeout
																												// in
																												// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.IMAPS_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																									// read
																									// connection
																									// timeout
																									// in
																									// millisecondi
			propertiesAccount.setProperty(AccountConfigKey.IMAPS_WRITE_TIMEOUT.keyname(), MAIL_TIMEOUT); // Socket
																											// write
																											// connection
																											// timeout
																											// in
																											// millisecondi

			Session session = Session.getInstance(propertiesAccount);
			session.setDebug(true);
			LOGGER.info("Proprietà sessione: " + session.getProperties());

			// Host
			String host = propertiesAccount.getProperty(AccountConfigKey.IMAP_HOST.keyname());
			// Porta
			Integer port = Integer.valueOf(propertiesAccount.getProperty(AccountConfigKey.IMAP_PORT.keyname()));
			// Username
			String username = null;
			if (propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname()) != null) {
				username = propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_USERNAME.keyname());
			}
			// in alcuni casi potrebbe essere specificato un username specifico
			// per imap
			if (propertiesAccount.getProperty(AccountConfigKey.IMAP_USERNAME.keyname()) != null) {
				username = propertiesAccount.getProperty(AccountConfigKey.IMAP_USERNAME.keyname());
			}
			// Password
			String password = null;
			if (propertiesAccount.getProperty(AccountConfigKey.ACCOUNT_PASSWORD.keyname()) != null) {
				try {
					password = CryptographyUtil.decryptionAccountPasswordWithAES(propertiesAccount);
				} catch (InvalidEncryptionKeyException e) {
					LOGGER.error("Eccezione nella decriptazione della password", e);
					throw e;
				}
			}

			Boolean isSSL = false;

			if (propertiesAccount.getProperty(AccountConfigKey.IMAP_SSL.keyname()) != null) {
				isSSL = Boolean.parseBoolean(propertiesAccount.getProperty(AccountConfigKey.IMAP_SSL.keyname(), "false"));
			}

			if (isSSL) {
				store = (IMAPSSLStore) session.getStore("imaps");
			} else {
				store = (IMAPStore) session.getStore("imap");
			}

			// connessione al server smtp
			store.connect(host, port, username, password);

			try {
				// visualizzo info quote
				Quota[] quotas = store.getQuota("INBOX");
				for (Quota quota : quotas) {
					if (quota != null) {
						for (Quota.Resource resource : quota.resources) {
							LOGGER.info(String.format("Nome:'%s', Limite:'%s', Utilizzo:'%s'", resource.name, resource.limit, resource.usage));
							// LOGGER.info(String.format("Percentuale
							// usata:'%f'",
							// MailboxIMAPInfoBean.getPercentageQuota(resource)));
						}
						result.add(quota);
					}
				}

			} catch (MessagingException exc) {
				LOGGER.error("Il server non supporta l'estensione QUOTA", exc);
				throw exc;
			}

		} catch (AuthenticationFailedException e) {
			LOGGER.error("Eccezione autenticazione", e);
			throw e;
		} catch (MessagingException e) {
			LOGGER.error("Eccezione JavaMail", e);
			throw e;
		} catch (Exception e) {
			LOGGER.error("Eccezione generica", e);
			throw e;
		}

		finally {
			// chiusura della connessione
			if (store != null) {
				try {
					store.close();
				} catch (MessagingException e) {
					LOGGER.error("Eccezione nella chiusura della connessione al server IMAP", e);
					throw e;
				}
			}

		}

		return result;

	}

	/**
	 * Metodo che restituisce l'ultimo messaggio scaricato per la mailbox avente id {@link idMailbox}
	 * 
	 * @param idMailbox
	 * @return
	 * @throws Exception
	 */

	public static MailboxMessage getLastMessageDischarged(String idMailbox) throws Exception {

		MailboxMessage result = null;
		org.hibernate.Session session = null;

		try {
			session = HibernateUtil.begin();

			Criteria criteria = session.createCriteria(MailboxMessage.class).add(Restrictions.eq("id.idMailbox", idMailbox)).setMaxResults(1)
					.addOrder(Order.desc("dateDischarged"));

			result = (MailboxMessage) criteria
					.setProjection(
							Projections.projectionList().add(Projections.property("id"), "id").add(Projections.property("dateDischarged"), "dateDischarged"))
					.setResultTransformer(Transformers.aliasToBean(MailboxMessage.class)).uniqueResult();

		} catch (Exception e) {
			LOGGER.error("Errore recupero ultimo messaggio scaricato dalla mailbox avente id: " + idMailbox, e);
			throw e;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}

		return result;

	}

	// /**
	// * Metodo che restituisce l'ultimo messaggio cancellato dalla mailbox
	// avente id {@link idMailbox}
	// *
	// * @param idMailbox
	// * @return
	// * @throws Exception
	// */
	//
	// public static MailboxMessage getLastMessageDeleted(String idMailbox)
	// throws Exception {
	//
	// MailboxMessage result = null;
	// org.hibernate.Session session = null;
	//
	// try {
	// session = HibernateUtil.begin();
	//
	// Criteria criteria = session.createCriteria(MailboxMessage.class).
	// createAlias("mailboxMessageOperations", "operation",
	// CriteriaSpecification.LEFT_JOIN)
	// .add(Restrictions.eq("id.idMailbox", idMailbox))
	// .add(Restrictions)
	// .setMaxResults(1)
	// .addOrder(Order.desc("dateDischarged"));
	//
	// result = (MailboxMessage) criteria
	// .setProjection(
	// Projections.projectionList().add(Projections.property("id"),
	// "id").add(Projections.property("dateDischarged"), "dateDischarged"))
	// .setResultTransformer(Transformers.aliasToBean(MailboxMessage.class)).uniqueResult();
	//
	// } catch (Exception e) {
	// LOGGER.error("Errore recupero ultimo messaggio scaricato dalla mailbox
	// avente id: " + idMailbox, e);
	// throw e;
	// } finally {
	// if (session != null) {
	// session.close();
	// session = null;
	// }
	// }
	//
	// return result;
	//
	// }

	/**
	 * Metodo che restituisce il numero totale di messaggi scaricati per la mailbox avente id {@link idMailbox}
	 * 
	 * @param idMailbox
	 * @return
	 * @throws Exception
	 */

	public static Long getTotalMessagesDischarged(String idMailbox) throws Exception {

		Long result = null;
		org.hibernate.Session session = null;

		try {

			session = HibernateUtil.begin();

			result = (Long) session.createCriteria(MailboxMessage.class).add(Restrictions.eq("id.idMailbox", idMailbox)).setProjection(Projections.rowCount())
					.uniqueResult();

		} catch (Exception e) {
			LOGGER.error("Errore recupero totale messaggi dalla mailbox avente id: " + idMailbox, e);
			throw e;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}

		return result;

	}

	/**
	 * Metodo che restituisce l'ultimo messaggio processato con successo per l'account avente id {@link idAccount}
	 * 
	 * @param idMailbox
	 * @return
	 * @throws Exception
	 */

	public static TEmailMgoBean getLastMessageProcessed(String idAccount) throws Exception {

		TEmailMgoBean result = null;
		org.hibernate.Session session = null;

		try {
			session = HibernateUtil.begin();

			Criteria criteria = session.createCriteria(TEmailMgo.class).add(Restrictions.eq("mailboxAccount.idAccount", idAccount))
					.add(Restrictions.eq("flgIo", InputOutput.INGRESSO.getValue())).setMaxResults(1).addOrder(Order.desc("tsIns"));

			TEmailMgo emailMgo = (TEmailMgo) criteria
					.setProjection(Projections.projectionList().add(Projections.property("idEmail"), "idEmail")
							.add(Projections.property("messageId"), "messageId").add(Projections.property("tsIns"), "tsIns"))
					.setResultTransformer(Transformers.aliasToBean(TEmailMgo.class)).uniqueResult();

			if (emailMgo != null) {
				result = (TEmailMgoBean) UtilPopulate.populate((TEmailMgo) emailMgo, TEmailMgoBean.class, new TEmailMgoToTEmailMgoBean());
			}

		} catch (Exception e) {
			LOGGER.error("Errore recupero ultima mail processata per l'acount avente id: " + idAccount, e);
			throw e;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}

		return result;

	}

	/**
	 * Metodo che restituisce l'ultimo messaggio spedito per l'account avente id {@link idAccount}
	 * 
	 * @param idMailbox
	 * @return
	 * @throws Exception
	 */

	public static TEmailMgoBean getLastMessageSent(String idAccount) throws Exception {

		TEmailMgoBean result = null;
		org.hibernate.Session session = null;

		try {
			session = HibernateUtil.begin();

			Criteria criteria = session.createCriteria(TEmailMgo.class).add(Restrictions.eq("mailboxAccount.idAccount", idAccount))
					.add(Restrictions.neProperty("idEmail", "messageId")).add(Restrictions.isNotNull("tsInvio"))
					.add(Restrictions.eq("flgIo", InputOutput.USCITA.getValue())).setMaxResults(1).addOrder(Order.desc("tsInvio"));

			TEmailMgo emailMgo = (TEmailMgo) criteria
					.setProjection(Projections.projectionList().add(Projections.property("idEmail"), "idEmail")
							.add(Projections.property("messageId"), "messageId").add(Projections.property("tsInvio"), "tsInvio"))
					.setResultTransformer(Transformers.aliasToBean(TEmailMgo.class)).uniqueResult();

			if (emailMgo != null) {
				result = (TEmailMgoBean) UtilPopulate.populate((TEmailMgo) emailMgo, TEmailMgoBean.class, new TEmailMgoToTEmailMgoBean());
			}

		} catch (Exception e) {
			LOGGER.error("Errore recupero ultima mail spedita per l'acount avente id " + idAccount, e);
			throw e;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}

		return result;

	}

	/**
	 * Metodo che restituisce il numero totale di messaggi processati per l'account avente id {@link idAccount}
	 * 
	 * @param idAccount
	 * @return
	 * @throws Exception
	 */

	public static Long getTotalMessagesProcessed(String idAccount) throws Exception {

		Long result = null;
		org.hibernate.Session session = null;

		try {

			session = HibernateUtil.begin();

			Criteria criteria = session.createCriteria(TEmailMgo.class).add(Restrictions.eq("mailboxAccount.idAccount", idAccount))
					.add(Restrictions.eq("flgIo", InputOutput.INGRESSO.getValue()));

			result = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();

		} catch (Exception e) {
			LOGGER.error("Errore recupero totale messaggi processati per account avente id: " + idAccount, e);
			throw e;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}

		return result;

	}

	/**
	 * Metodo che restituisce il numero totale di messaggi spediti per l'account avente id {@link idAccount}
	 * 
	 * @param idAccount
	 * @return
	 * @throws Exception
	 */

	public static Long getTotalMessagesSent(String idAccount) throws Exception {

		Long result = null;
		org.hibernate.Session session = null;

		try {

			session = HibernateUtil.begin();

			Criteria criteria = session.createCriteria(TEmailMgo.class).add(Restrictions.eq("mailboxAccount.idAccount", idAccount))
					.add(Restrictions.neProperty("idEmail", "messageId")).add(Restrictions.isNotNull("tsInvio"))
					.add(Restrictions.eq("flgIo", InputOutput.USCITA.getValue()));

			result = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();

		} catch (Exception e) {
			LOGGER.error("Errore recupero totale messaggi spediti da account avente id: " + idAccount, e);
			throw e;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
		}

		return result;

	}

	/**
	 * Restituisce le informazioni sulla mailbox associato a {@link idMailbox} e {@link idAccount}
	 * 
	 * @param idMailbox
	 * @param idAccount
	 * @return
	 * @throws Exception
	 */

	public static MailboxAccountInfoBean getMailboxInfo(String idMailbox, String idAccount) throws Exception {

		MailboxAccountInfoBean info = new MailboxAccountInfoBean();
		info.setIdAccount(idAccount);
		info.setIdMailbox(idMailbox);

		try {

			/* connessione a IMAP e visualizzazione delle folder e quote */

			MailboxIMAPInfoBean infoIMAP = getFolderAndQuotaByIdAccount(idAccount);
			info.setInfoIMAP(infoIMAP);

			/* informazioni ultimo messaggio scaricato dalla mailbox */
			MailboxMessage lastMessageDischarged = getLastMessageDischarged(idMailbox);

			if (lastMessageDischarged != null) {
				info.setLastMessageIdDischarged(lastMessageDischarged.getId().getMessageId());
				info.setDateLastMessageDischarged(lastMessageDischarged.getDateDischarged());
			}

			/* numero totale di messaggi scaricati dalla mailbox */
			Long totalMessageDischarged = getTotalMessagesDischarged(idMailbox);

			if (totalMessageDischarged != null) {
				info.setTotalMessagesDischarged(totalMessageDischarged);
			}

			/* informazioni ultimo messaggio processato */
			TEmailMgoBean lastMessageProcessed = getLastMessageProcessed(idAccount);

			if (lastMessageProcessed != null) {
				info.setLastIdEmailProcessed(lastMessageProcessed.getIdEmail());
				info.setLastMessageIdProcessed(lastMessageProcessed.getMessageId());
				info.setDateLastMessageProcessed(lastMessageProcessed.getTsIns());
			}

			/* numero totale di messaggi processati */
			Long totalMessagesProcessed = getTotalMessagesProcessed(idAccount);

			if (totalMessagesProcessed != null) {
				info.setTotalMessagesProcessed(totalMessagesProcessed);
			}

			/* informazioni ultimo messaggio spedito */
			TEmailMgoBean lastMessageSent = getLastMessageSent(idAccount);

			if (lastMessageSent != null) {
				info.setLastMessageIdSent(lastMessageSent.getMessageId());
				info.setLastIdEmailSent(lastMessageSent.getIdEmail());
				info.setDateLastMessageSent(lastMessageSent.getTsInvio());
			}

			/* numero totale di messaggi spediti */
			Long totalMessagesSent = getTotalMessagesSent(idAccount);

			if (totalMessagesSent != null) {
				info.setTotalMessagesSent(totalMessagesSent);
			}

			LOGGER.info("MailboxInfo: " + info);

		}

		catch (Exception e) {
			LOGGER.error("Eccezione generica", e);
			throw e;
		}

		return info;

	}

	/**
	 * Restituisce le informazioni sulla mailbox associato a {@link idAccount}
	 * 
	 * @param idAccount
	 * @return
	 * @throws Exception
	 */

	public static MailboxAccountInfoBean getMailboxInfoByIdAccount(String idAccount) throws Exception {

		String idMailbox = getIdMailbox(idAccount);

		return getMailboxInfo(idMailbox, idAccount);

	}

	/**
	 * Restituisce le informazioni sulla mailbox associato a {@link idMailbox}
	 * 
	 * @param idMailbox
	 * @return
	 * @throws Exception
	 */

	public static MailboxAccountInfoBean getMailboxInfoByIdMailbox(String idMailbox) throws Exception {

		String idAccount = getIdAccountByIdMailbox(idMailbox);

		return getMailboxInfo(idMailbox, idAccount);

	}

	/**
	 * Metodo che restituisce la lista di mailbox e relative configurazioni
	 * 
	 * @return
	 * @throws Exception
	 */

	public static List<MailboxBean> getAllMailBox() throws Exception {

		// ricavo tutte le mailbox presenti, con ordinamento standard
		DaoMailbox daoMail = ((DaoMailbox) DaoFactory.getDao(DaoMailbox.class));
		List<MailboxBean> mailboxes = daoMail.getAllMailbox(null).getData();

		return mailboxes;

	}

	/**
	 * Metodo che restituisce la lista di mailbox e relative configurazioni in base allo stato
	 * 
	 * @return
	 * @throws Exception
	 */

	public static List<MailboxBean> getAllMailBoxByStatus(Boolean status) throws Exception {

		// ricavo tutte le mailbox con un determinato stato, con ordinamento
		// standard
		DaoMailbox daoMail = ((DaoMailbox) DaoFactory.getDao(DaoMailbox.class));
		TFilterFetch<MailboxBean> filter = new TFilterFetch<MailboxBean>();
		MailboxBean mailbox = new MailboxBean();
		mailbox.setStatus(status.toString());
		filter.setFilter(mailbox);
		List<MailboxBean> mailboxes = daoMail.search(filter).getData();

		return mailboxes;

	}

	public static List<TEmailMgoBean> recuperaInfoMailCancellate(String idAccount, String idMailbox, Date startDate, String[] listaStatiOperazione)
			throws Exception {

		List<TEmailMgoBean> result = new ArrayList<TEmailMgoBean>();

		org.hibernate.Session session = null;
		try {
			session = HibernateUtil.begin();
		} catch (Exception e) {
			throw new AurigaMailBusinessException("Impossibile aprire la connessione ", e);
		}

		try {

			Criteria criteria = session.createCriteria(TEmailMgo.class, "emailMgo").add(Restrictions.eq("mailboxAccount.idAccount", idAccount))
					.add(Restrictions.eq("flgIo", InputOutput.INGRESSO.getValue())).add(Restrictions.ge("tsIns", startDate.getTime()));

			// devo considerare la cancellazione effettuata
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MailboxMessageOperation.class, "mailboxMessageOperation");
			detachedCriteria.createAlias("mailboxOperation", "mailboxOperation");
			detachedCriteria.add(Restrictions.eq("id.idMailbox", idMailbox));
			detachedCriteria.add(Property.forName("id.messageId").eqProperty("emailMgo.messageId"));
			detachedCriteria.add(Restrictions.eq("mailboxOperation.operationName", "DeleteMessageOperation"));
			detachedCriteria.add(Restrictions.in("operationStatus", listaStatiOperazione));

			criteria.add(Subqueries.exists(detachedCriteria.setProjection(Projections.property("id"))));

			List<TEmailMgo> listaMail = null;

			listaMail = criteria.list();

			for (int i = 0; i < listaMail.size(); i++) {

				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Processo IdMail: " + listaMail.get(i).getIdEmail());
					LOGGER.debug("Message Id: " + listaMail.get(i).getMessageId());
					LOGGER.debug("Oggetto: " + listaMail.get(i).getOggetto());
					LOGGER.debug("Dimensione: " + listaMail.get(i).getDimensione());
					LOGGER.debug("URI: " + listaMail.get(i).getUriEmail());
				}

				TEmailMgoBean bean = (TEmailMgoBean) UtilPopulate.populate((TEmailMgo) listaMail.get(i), TEmailMgoBean.class, new TEmailMgoToTEmailMgoBean());
				result.add(bean);

			}

		}

		catch (Exception e) {
			LOGGER.error("Eccezione recuperaInfoMailCancellate", e);
			throw new AurigaMailBusinessException("Eccezione recuperaInfoMailCancellate", e);
		} finally {
			try {
				HibernateUtil.release(session);
			} catch (Exception e) {
				LOGGER.error("Eccezione nel rilascio della sessione post recuperaInfoMailCancellate", e);
				throw new AurigaMailBusinessException("Errore nel rilascio della sessione post recuperaInfoMailCancellate", e);
			}
		}

		return result;

	}

}
