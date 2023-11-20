/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import com.google.common.base.Predicate;

import it.eng.aurigamailbusiness.bean.MailAttachmentsBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TipoInteroperabilita;
import it.eng.aurigamailbusiness.database.dao.DaoTEmailMgo;
import it.eng.aurigamailbusiness.database.utility.DaoFactory;
import it.eng.aurigamailbusiness.exception.AurigaMailBusinessException;
import it.eng.aurigamailbusiness.sender.storage.StorageCenter;
import it.eng.aurigamailbusiness.structure.HeaderInfo;
import it.eng.aurigamailbusiness.structure.MessageInfos;
import it.eng.aurigamailbusiness.utility.mail.MailVerifier;
import it.eng.utility.storageutil.StorageService;

/**
 * classe di utilit per lo 'smembramento' della email
 * 
 * @author jravagnan
 * 
 */
public class MailBreaker {

	private static final String POSTACERT_EML = "postacert.eml";

	private static final String ERRORE_ALLEGATO_POSTACERT = "Impossibile ricavare il file postacert.eml";

	private static final String ERRORE_ALLEGATO_INTEROPERABILE = "Impossibile ricavare l'allegato associato all'interoperabilità";

	private static final String ERROR_GET_BODY_MAIL = "Impossibile ricavare il body della email";

	private MailVerifier verifier;

	private StorageCenter storageCenter;

	private Logger log = LogManager.getLogger(MailBreaker.class);

	/**
	 * istanzia il contesto di JAXB
	 * 
	 * @throws AurigaMailBusinessException
	 */
	public MailBreaker() throws AurigaMailBusinessException {

		storageCenter = new StorageCenter();

		// Recupero le entity di hibernate
		Reflections reflection = new Reflections("it.eng.aurigamailbusiness.daticert", new TypeAnnotationsScanner().filterResultsBy(new Predicate<String>() {

			@Override
			public boolean apply(String input) {
				if (StringUtils.equalsIgnoreCase(input, XmlRootElement.class.getName())) {
					return true;
				} else {
					return false;
				}
			}
		}));
		Set<Class<?>> classesXML = reflection.getTypesAnnotatedWith(XmlRootElement.class);
		try {
			XmlUtil.setContext(JAXBContext.newInstance(classesXML.toArray(new Class[0])));
		} catch (JAXBException e) {
			log.error("Impossibile istanziare il contesto di jaxb");
			throw new AurigaMailBusinessException("Impossibile istanziare il contesto di jaxb", e);
		}

	}

	/**
	 * Ritorna le informazioni relative all'header della mail in input
	 * 
	 * @param eml
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	public HeaderInfo getHeaderInfo(File eml) throws AurigaMailBusinessException {
		return getInfosFromEml(eml).getHeaderinfo();
	}

	/**
	 * Ritorna le informazioni relative all'header della mail associata all'id in input. <br>
	 * Se una PEC le informazioni potrebbero essere recuperate dalla relativa ricevuta completa 
	 * 
	 * @param idEmail
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	public HeaderInfo getHeaderInfo(String idEmail) throws AurigaMailBusinessException {
		return getInfosFromIdEmail(idEmail).getHeaderinfo();
	}

	/**
	 * Restituisce gli allegati della mail in input
	 * 
	 * @param eml
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	public List<MailAttachmentsBean> getAttachments(File eml) throws AurigaMailBusinessException {
		return getInfosFromEml(eml).getAttachmentsWithPrincipalMail();
	}

	/**
	 * Restituisce gli allegati della mail associata all'id in input. <br>
	 * Se  una PEC le informazioni potrebbero essere recuperate dalla relativa ricevuta completa
	 * 
	 * @param eml
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	public List<MailAttachmentsBean> getAttachments(String idEmail) throws AurigaMailBusinessException {
		return getInfosFromIdEmail(idEmail).getAttachmentsWithPrincipalMail();
	}

	/**
	 * Restituisce gli allegati della busta esterna della mail in input
	 * 
	 * @param eml
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	public List<MailAttachmentsBean> getExternalAttachments(File eml) throws AurigaMailBusinessException {
		return getInfosFromEml(eml).getDirectAttachments();
	}

	/**
	 * Restituisce oggetto {@link MessageInfos} con le informazioni relative alla mail in input
	 * 
	 * @param eml
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	public MessageInfos getInfosFromEml(File eml) throws AurigaMailBusinessException {

		InputStream source = null;
		MessageInfos info = null;
		try {
			source = new FileInputStream(eml);
			info = getInfoFromSource(source);
		} catch (Exception e) {
			log.error("Impossibile ricavare le info della email");
			throw new AurigaMailBusinessException("Impossibile ricavare le info della mail", e);
		} finally {
			closeStream(source);
		}
		return info;
	}

	/**
	 * Restituisce oggetto {@link TEmailMgoBean} associato all'id mail in input
	 * 
	 * @param idEmail
	 * @return
	 * @throws Exception
	 */

	private TEmailMgoBean getTEmailMgoBeanFromIdEmail(String idEmail) throws Exception {

		if (idEmail == null) {
			throw new AurigaMailBusinessException("IdEmail non valorizzato");
		}

		DaoTEmailMgo daoTEmailMgo = (DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class);
		TEmailMgoBean emailBean = daoTEmailMgo.get(idEmail);

		if (emailBean == null) {
			throw new AurigaMailBusinessException("Email non trovata");
		}

		return emailBean;

	}

	/**
	 * Restituisce il file eml associato al bean mail in input
	 * 
	 * @param emailBean
	 * @return
	 * @throws Exception
	 */

	private File getEmlFromTEmailMgoBean(TEmailMgoBean emailBean) throws Exception {

		StorageCenter storage = new StorageCenter();
		String idMailBox = MailboxUtil.getIdMailbox(emailBean.getIdCasella());
		StorageService globalStorageService = storage.getGlobalStorage(idMailBox);
		File eml = globalStorageService.extractFile(emailBean.getUriEmail());

		if (eml == null || !eml.exists()) {
			throw new AurigaMailBusinessException("File mail non trovato");
		}

		return eml;

	}

	/**
	 * Metodo che restituisce il file della mail associato all'id mail in input. <br>
	 * Se una PEC inviata allora il file potrebbe essere recuperato dalla ricevuta completa
	 * 
	 * @param idEmail
	 * @return
	 * @throws Exception
	 */

	public File getEmlFromIdEmail(String idEmail) throws Exception {

		TEmailMgoBean emailBean = getTEmailMgoBeanFromIdEmail(idEmail);
		final File emlFile = storageCenter.getGlobalStorage(emailBean.getIdCasella()).extractFile(emailBean.getUriEmail());

		if (BooleanUtils.isTrue(emailBean.getFlgUriRicevuta())) {
			return getFilePostcert(emlFile);
		}

		return emlFile;
	}

	/**
	 * Restituisce oggetto {@link MessageInfos} con le informazioni relative all'id mail in input. <br>
	 * Se una PEC le informazioni potrebbero essere recuperate dalla relativa ricevuta completa
	 * 
	 * @param idEmail
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	public MessageInfos getInfosFromIdEmail(String idEmail) throws AurigaMailBusinessException {

		InputStream source = null;
		MessageInfos info = null;
		try {

			TEmailMgoBean emailBean = getTEmailMgoBeanFromIdEmail(idEmail);

			source = new FileInputStream(getEmlFromTEmailMgoBean(emailBean));
			info = getInfoFromSource(source);

			// se  il flag true significa che devo restituire le informazioni partendo dalla ricevuta
			if (BooleanUtils.isTrue(emailBean.getFlgUriRicevuta())) {
				info = info.getPostecerteml();
			}

		} catch (Exception e) {
			log.error("Impossibile ricavare le info della email");
			throw new AurigaMailBusinessException("Impossibile ricavare le info della mail", e);
		} finally {
			closeStream(source);
		}
		return info;
	}

	/**
	 * @param source
	 */

	private void closeStream(InputStream source) {
		if (source != null) {
			try {
				source.close();
			} catch (IOException e) {
				log.warn("Eccezione nella chiusura dello stream della mail", e);
			}
		}
	}

	/**
	 * Restituisce oggetto {@link MessageInfos} partendo dallo stream in input
	 * 
	 * @param source
	 * @return
	 * @throws Exception
	 * @throws MessagingException
	 */

	private MessageInfos getInfoFromSource(InputStream source) throws Exception, MessagingException {

		// istanzio una nuova sessione con le propriet comuni
		Session mailSession = Session.getInstance(Util.getJavaMailDefaultProperties());
		return new MailVerifier().verifyAnalize(new MimeMessage(mailSession, source), false);  //TODO: da mettere il verifier come var di classe!!!
	}

	/**
	 * Restituisce il corpo della mail se ha formato text/plain, partendo dal file in input
	 * 
	 * @param eml
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	public String getBody(File eml) throws AurigaMailBusinessException {
		return getBodyTextPlainFromMessageInfos(getInfosFromEml(eml));
	}

	/**
	 * Restituisce il corpo della mail se ha formato text/plain, recuperando il file della mail associata all'id in input. <br>
	 * Se  una PEC il corpo potrebbe essere recuperato dalla relativa ricevuta completa
	 * 
	 * @param idEmail
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	public String getBody(String idEmail) throws AurigaMailBusinessException {
		return getBodyTextPlainFromMessageInfos(getInfosFromIdEmail(idEmail));
	}

	/**
	 * Restituisce corpo text/plain se presente fra le parti della mail
	 * 
	 * @param info
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	private String getBodyTextPlainFromMessageInfos(MessageInfos info) throws AurigaMailBusinessException {

		String body = null;
		try {
			body = info.getBodyTextPlainOnlyWithPrincipalMail();
		} catch (Exception e) {
			log.error(ERROR_GET_BODY_MAIL, e);
			throw new AurigaMailBusinessException(ERROR_GET_BODY_MAIL, e);
		}
		return body;
	}

	/**
	 * Restituisce il corpo della mail se ha formato text/html, partendo dal file in input
	 * 
	 * @param eml
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	public String getBodyHtml(File eml) throws AurigaMailBusinessException {

		return getBodyTextHtmlFromMessageInfos(getInfosFromEml(eml));
	}

	/**
	 * Restituisce il corpo della mail se ha formato text/html, recuperando il file della mail associata all'id in input. <br>
	 * Se una PEC il corpo potrebbe essere recuperato dalla relativa ricevuta completa
	 * 
	 * @param eml
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	public String getBodyHtml(String idEmail) throws AurigaMailBusinessException {

		return getBodyTextHtmlFromMessageInfos(getInfosFromIdEmail(idEmail));
	}

	/**
	 * Restituisce corpo text/html se presente fra le parti della mail
	 * 
	 * @param info
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	private String getBodyTextHtmlFromMessageInfos(MessageInfos info) throws AurigaMailBusinessException {

		String body = null;
		try {
			body = info.getBodyHtmlWithPrincipalMail();
		} catch (Exception e) {
			log.error(ERROR_GET_BODY_MAIL);
			throw new AurigaMailBusinessException(ERROR_GET_BODY_MAIL, e);
		}
		return body;
	}

	/**
	 * Metodo che restituisce il corpo della mail  esterna se ha formato text/html, partendo dal file in input
	 * 
	 * @param eml
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	public String getHtmlInMainBody(File eml) throws AurigaMailBusinessException {

		return getBodyTextHtmlExternalFromMessageInfos(getInfosFromEml(eml));
	}

	/**
	 * Metodo che restituisce il corpo della mail  esterna se ha formato text/html, recuperando il file della mail associata all'id in input. <br>
	 * Se una PEC il corpo potrebbe essere recuperato dalla relativa ricevuta completa
	 * 
	 * @param eml
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	public String getHtmlInMainBody(String idEmail) throws AurigaMailBusinessException {
		return getBodyTextHtmlExternalFromMessageInfos(getInfosFromIdEmail(idEmail));
	}

	/**
	 * Restituisce corpo text/html se presente fra le parti della mail esterna
	 * 
	 * @param info
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	private String getBodyTextHtmlExternalFromMessageInfos(MessageInfos info) throws AurigaMailBusinessException {

		String body = null;
		try {
			body = info.getBodyExternalHtml();
		} catch (Exception e) {
			log.error(ERROR_GET_BODY_MAIL);
			throw new AurigaMailBusinessException(ERROR_GET_BODY_MAIL, e);
		}
		return body;
	}

	/**
	 * Metodo che restituisce il corpo della mail esterna se ha formato text/html, partendo dal file in input
	 * 
	 * @param eml
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	public String getTextInMainBody(File eml) throws AurigaMailBusinessException {

		return getBodyTextPlainExternalFromMessageInfos(getInfosFromEml(eml));
	}

	/**
	 * Metodo che restituisce il corpo della mail  esterna se ha formato text/html, recuperando il file della mail associata all'id in input. <br>
	 * Se  una PEC il corpo potrebbe essere recuperato dalla relativa ricevuta completa
	 * 
	 * @param eml
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	public String getTextInMainBody(String idEmail) throws AurigaMailBusinessException {

		return getBodyTextPlainExternalFromMessageInfos(getInfosFromIdEmail(idEmail));
	}

	/**
	 * @param info
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	protected String getBodyTextPlainExternalFromMessageInfos(MessageInfos info) throws AurigaMailBusinessException {

		String body = null;
		try {
			body = info.getBodyExternalPlain();
		} catch (Exception e) {
			log.error(ERROR_GET_BODY_MAIL);
			throw new AurigaMailBusinessException(ERROR_GET_BODY_MAIL, e);
		}
		return body;
	}

	/**
	 * Ritorna l'attachment che contiene il file di interoperabilita'
	 * 
	 * @param eml
	 * @param type
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	public MailAttachmentsBean getAttach(File eml, TipoInteroperabilita type) throws AurigaMailBusinessException {
		return getAllegatoInteroperabile(type, getAttachments(eml));
	}

	/**
	 * Ritorna l'allegato che contiene il file di interoperabilita'. <br>
	 * Potrebbe essere recuperato dalla ricevuta completa della PEC
	 * 
	 * @param eml
	 * @param type
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	public MailAttachmentsBean getAttach(String idEmail, TipoInteroperabilita type) throws AurigaMailBusinessException {
		try {
			List<MailAttachmentsBean> attachs = getAttachments(getEmlFromTEmailMgoBean(getTEmailMgoBeanFromIdEmail(idEmail)));
			return getAllegatoInteroperabile(type, attachs);
		} catch (Exception e) {
			log.error(ERRORE_ALLEGATO_INTEROPERABILE);
			throw new AurigaMailBusinessException(ERRORE_ALLEGATO_INTEROPERABILE, e);
		}
	}

	/**
	 * Cicla fra gli allegati in input e restituisce quello associato alla tipologia interopabile in input
	 * 
	 * @param type
	 * @param attachs
	 */
	private MailAttachmentsBean getAllegatoInteroperabile(TipoInteroperabilita type, List<MailAttachmentsBean> attachs) {

		for (MailAttachmentsBean att : attachs) {
			if (att.getFilename().equalsIgnoreCase(type.getFilename())) {
				return att;
			}
		}
		return null;
	}

	/**
	 * Ritorna l'allegato postacert.eml
	 * 
	 * @param eml
	 * @param type
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	public MailAttachmentsBean getPostcert(File eml) throws AurigaMailBusinessException {

		return getAllegatoPostaCert(getInfosFromEml(eml));

	}

	/**
	 * Ritorna il bean dell'allegato postacert. <br>
	 * Potrebbe essere recuperato dalla ricevuta completa della PEC
	 * 
	 * @param eml
	 * @param type
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	public MailAttachmentsBean getPostcert(String idEmail) throws AurigaMailBusinessException {

		return getAllegatoPostaCert(getInfosFromIdEmail(idEmail));

	}

	/**
	 * Ritorna il file dell'allegato postaCert. <br>
	 * Potrebbe essere recuperato dalla ricevuta completa della PEC
	 */

	public File getFilePostcert(String idEmail) throws AurigaMailBusinessException {

		return getFileAllegatoPostaCert(getInfosFromIdEmail(idEmail));

	}

	/**
	 * Ritorna il file dell'allegato postaCert
	 */

	public File getFilePostcert(File eml) throws AurigaMailBusinessException {

		return getFileAllegatoPostaCert(getInfosFromEml(eml));

	}

	/**
	 * Restituisce il bean dell'allegato postacert.eml ciclando fra gli allegati della mail
	 * 
	 * @param info
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	private MailAttachmentsBean getAllegatoPostaCert(MessageInfos info) throws AurigaMailBusinessException {
		try {
			List<MailAttachmentsBean> attachs = info.getDirectAttachments();

			for (MailAttachmentsBean mab : attachs) {
				if (POSTACERT_EML.equals(mab.getFilename())) {
					return mab;
				}
			}
			return null;
		} catch (Exception e) {
			log.error(ERRORE_ALLEGATO_POSTACERT);
			throw new AurigaMailBusinessException(ERRORE_ALLEGATO_POSTACERT, e);
		}
	}

	/**
	 * Restituisce il file dell'allegato postacert.eml ciclando fra gli allegati della mail
	 * 
	 * @param info
	 * @return
	 * @throws AurigaMailBusinessException
	 */

	private File getFileAllegatoPostaCert(MessageInfos info) throws AurigaMailBusinessException {
		try {
			List<MailAttachmentsBean> attachs = info.getDirectAttachments();

			for (MailAttachmentsBean mab : attachs) {
				if (POSTACERT_EML.equals(mab.getFilename())) {
					return mab.getFile();
				}
			}
			return null;
		} catch (Exception e) {
			log.error(ERRORE_ALLEGATO_POSTACERT);
			throw new AurigaMailBusinessException(ERRORE_ALLEGATO_POSTACERT, e);
		}
	}

	public MailVerifier getVerifier() {
		return verifier;
	}

	public void setVerifier(MailVerifier verifier) {
		this.verifier = verifier;
	}

}
