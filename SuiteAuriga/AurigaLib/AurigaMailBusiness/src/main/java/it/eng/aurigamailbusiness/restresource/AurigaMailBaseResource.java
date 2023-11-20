/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.FileSystems;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.sun.jersey.api.core.ResourceContext;
import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.file.StreamDataBodyPart;

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailCtrlutenzaabilitatainvioBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.aurigamailbusiness.bean.SenderAttachmentsBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.config.SessionFile;
import it.eng.aurigamailbusiness.config.SessionFileConfigurator;
import it.eng.aurigamailbusiness.database.dao.DaoTEmailMgo;
import it.eng.aurigamailbusiness.database.dao.MailProcessorService;
import it.eng.aurigamailbusiness.database.utility.DaoFactory;
import it.eng.aurigamailbusiness.sender.storage.StorageCenter;
import it.eng.aurigamailbusiness.utility.MailBreaker;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.core.config.ConfigUtil;
import it.eng.core.service.client.config.Configuration;
import it.eng.core.service.serialization.IServiceSerialize;
import it.eng.core.service.serialization.SerializerRepository;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.storageutil.StorageService;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;

public abstract class AurigaMailBaseResource {

	private static final Logger log = Logger.getLogger(AurigaMailBaseResource.class);
	private static final String BEAN_ID_SESSION_FILE_CONFIGURATOR = "SessionFileConfigurator";
	private static final String BEAN_ID_STORAGE_SERVICE = "storageService";
	protected static final String HEADER_NAME_AUTHENTICATION_TOKEN = "token";
	protected static final String HEADER_NAME_SCHEMA_SELEZIONATO = "schema";
	protected static final String KEY_SCHEMA = "rs.database.schema";
	// se non specificato l'autenticazione viene eseguita comunque
	protected static final String KEY_FLAG_SKIP_AUTH = "rs.authentication.flag.skip";
	protected final Configuration cfg;
	protected IServiceSerialize serializeUtil;
	protected final Boolean flagSkipAuthentication;
	protected final AurigaMailFunctionCallHelper callHelper;
	protected final JAXBContext jaxbContextSezCache;
	protected final XmlUtilityDeserializer xmlUtilityDeserializer;
	protected final XmlUtilitySerializer xmlUtilitySerializer;
	protected final StorageCenter storageCenter;
	protected StorageService storageService;
	protected MailProcessorService mailProcessorService;
	@Context
	protected ResourceContext resourceCtx;
	protected SessionFileConfigurator sessionFileConfigurator;
	protected String defaultSchema;

	public AurigaMailBaseResource() throws Exception {
		cfg = Configuration.getInstance();
		serializeUtil = SerializerRepository.getSerializationUtil(cfg.getSerializationtype());
		flagSkipAuthentication = ConfigUtil.getConfig().getBoolean(KEY_FLAG_SKIP_AUTH, Boolean.FALSE);
		callHelper = new AurigaMailFunctionCallHelper();
		jaxbContextSezCache = SingletonJAXBContext.getInstance();
		storageCenter = new StorageCenter();
		xmlUtilityDeserializer = new XmlUtilityDeserializer();
		xmlUtilitySerializer = new XmlUtilitySerializer();
		try {
			sessionFileConfigurator = (SessionFileConfigurator) SpringAppContext.getContext().getBean(BEAN_ID_SESSION_FILE_CONFIGURATOR);
			defaultSchema = getDefaultSchema(sessionFileConfigurator);
		} catch (Exception e) {
			log.error("Schema non configurato: " + e);
		}
		if (defaultSchema == null || defaultSchema.isEmpty()) {
			defaultSchema = ConfigUtil.getConfig().getString(KEY_SCHEMA);
			log.warn("Imposto lo schema con " + String.valueOf(defaultSchema));
		}
		try {
			storageService = (StorageService) SpringAppContext.getContext().getBean(BEAN_ID_STORAGE_SERVICE);
		} catch (Exception e) {
			log.error("Storage service non configurato: " + e);
		}
		mailProcessorService = new MailProcessorService();
	}

	protected DmpkIntMgoEmailCtrlutenzaabilitatainvioBean checkPermissionRS(final String token, final String account, final String schema) {
		if (BooleanUtils.isTrue(flagSkipAuthentication)) {
			log.warn("Autenticazione disabilitata dal parametro di configurazione '" + KEY_FLAG_SKIP_AUTH);
			return null;
		}

		DmpkIntMgoEmailCtrlutenzaabilitatainvioBean result;
		try {
			result = checkPermission(token, account, schema);
		} catch (Exception e) {
			throw new AurigaMailException(e);
		}

		if (!Integer.valueOf(1).equals(result.getFlgabilinvioout())) {
			throw new AurigaMailException(Response.Status.FORBIDDEN.getStatusCode());
		}

		return result;
	}

	private DmpkIntMgoEmailCtrlutenzaabilitatainvioBean checkPermission(String token, String account, String schema) throws Exception {
		log.debug("checkPermission inizio");

		final StoreResultBean<DmpkIntMgoEmailCtrlutenzaabilitatainvioBean> storeResultBean = callHelper.callCtrlUtenzaAbilitataInvioFunc(token, account,
				schema);

		if (storeResultBean.isInError()) {
			log.error("L'esecuzione della function 'DMPK_INT_MGO_EMAIL.CtrlUtenzaAbilitataInvio' si è conclusa con errore:\n"
					+ String.valueOf(storeResultBean.getDefaultMessage()));
			throw new Exception(storeResultBean.getDefaultMessage());
		}

		// La function 'CtrlUtenzaAbilitataInvio' è stata eseguita con successo
		final DmpkIntMgoEmailCtrlutenzaabilitatainvioBean result = storeResultBean.getResultBean();

		log.debug("checkPermission fine");
		return result;
	}

	protected String getIdUtenteModPec(final DmpkIntMgoEmailCtrlutenzaabilitatainvioBean out) {
		if (out == null)
			return null;
		String idUtenteModPec = out.getIdutentepecout();
		if (out.getIduserout() != null && StringUtils.isBlank(idUtenteModPec)) {
			final BigInteger idUserInt = out.getIduserout().toBigInteger();
			idUtenteModPec = idUserInt.toString();
		}
		return idUtenteModPec;
	}

	/**
	 * Restituisce il file eml associato all'id mail in input<br>
	 * Se l'URI è quello di una ricevuta PEC completa recupera il file dalla ricevuta
	 * 
	 * @param idEmail
	 * @return
	 */
	protected File getFileEmail(String idEmail, String schema) {
		try {
			initSubject(schema);
			MailBreaker mailBreaker = new MailBreaker();
			return mailBreaker.getEmlFromIdEmail(idEmail);
		} catch (Exception e) {
			log.error("Errore nel recupero del file eml", e);
			throw new AurigaMailException(500, "Non è stato possibile recuperare il file eml.");
		}
	}// getEmail

	protected TEmailMgoBean retrieveTEmailMgoBean(String idEmail, String schema) {
		try {
			initSubject(schema);
			final DaoTEmailMgo daoTEMailMgo = (DaoTEmailMgo) DaoFactory.getDao(DaoTEmailMgo.class);
			return daoTEMailMgo.get(idEmail);
		} catch (InvocationTargetException ex) {
			log.error("Errore in retrieveTEmailMgoBean()", ex);
			throw new AurigaMailException(404, "E' necessario specificare un ID email valido.");
		} catch (Exception e) {
			log.error("Errore in retrieveTEmailMgoBean()", e);
			throw new AurigaMailException(e);
		}
	}// retrieveTEmailMgoBean

	protected void initSubject(String idDominio) {
		SubjectBean subject = SubjectUtil.subject.get();
		if (subject == null) {
			subject = new SubjectBean();
			SubjectUtil.subject.set(subject);
			subject.setIdDominio(idDominio);
		}
	}

	private String getDefaultSchema(SessionFileConfigurator sessionFileConfigurator) {
		final SessionFile sf = getSessionFile(sessionFileConfigurator);
		return sf != null ? sf.getSessionName() : null;
	}

	private SessionFile getSessionFile(SessionFileConfigurator sessionFileConfigurator) {
		SessionFile sf = sessionFileConfigurator.getDefaultSession();
		final List<SessionFile> sessions = sessionFileConfigurator.getSessions();
		if (sessions != null && !sessions.isEmpty()) {
			sf = sessionFileConfigurator.getSessions().get(0);
		}
		return sf;
	}

	protected String getSchema(String schema) {
		return StringUtils.isNotBlank(schema) ? schema : defaultSchema;
	}

	protected Map<String, String> retrieveMessages(final String xml) throws Exception {
		Map<String, String> messages = new HashMap<>(0);
		if (StringUtils.isBlank(xml)) {
			return messages;
		}
		final StringReader reader = new StringReader(xml);
		final Lista lista = (Lista) jaxbContextSezCache.createUnmarshaller().unmarshal(reader);
		final int rowNbr = lista.getRiga().size();
		if (rowNbr > 0) {
			messages = new HashMap<>();
		}
		for (int i = 0; i < rowNbr; i++) {
			final Lista.Riga rigaI = lista.getRiga().get(i);
			final List<Colonna> cols = rigaI.getColonna();
			if (cols.size() <= 4)
				continue;
			final Colonna colonna3 = cols.get(3);
			if (colonna3 == null)
				continue;
			final String content3 = colonna3.getContent();
			if ("KO".equalsIgnoreCase(content3)) {
				messages.put(cols.get(0).getContent(), cols.get(4).getContent());
			}
		} // for

		return messages;
	}// retrieveMessages

	protected void saveFile(InputStream file, String name) {
		try {
			/* Change directory path */
			java.nio.file.Path path = FileSystems.getDefault().getPath("C:/temp/" + name);
			/* Save InputStream as file */
			java.nio.file.Files.copy(file, path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	protected List<SenderAttachmentsBean> getAttachments(final List<FormDataBodyPart> fileParts) {
		if (fileParts == null) {
			return new ArrayList<SenderAttachmentsBean>(0);
		}
		final List<SenderAttachmentsBean> attachments = new ArrayList<SenderAttachmentsBean>(fileParts.size());
		try {
			for (int i = 0; i < fileParts.size(); i++) {
				InputStream is = null;
				try {
					final FormDataBodyPart fbp = fileParts.get(i);
					if (fbp == null) {
						continue;
					}
					final Object entity = fbp.getEntity();
					MediaType mediaType = fbp.getMediaType();
					
					String filename = null;
					if (fbp.getClass() == StreamDataBodyPart.class) {
						final StreamDataBodyPart sbp = (StreamDataBodyPart) fbp;
						is = sbp.getStreamEntity();
						filename = sbp.getFilename();
						if (sbp.getMediaType() != null) {
						   mediaType = sbp.getMediaType();
						}
					} else {
						if (entity instanceof BodyPartEntity) {
							final BodyPartEntity bodyPartEntity = (BodyPartEntity) fbp.getEntity();
							is = bodyPartEntity.getInputStream();
							filename = fbp.getContentDisposition().getFileName();
						} else {
							// TODO
						}
					}
					String mimetype = null;
					if (mediaType != null) {
						mimetype = mediaType.toString();
					}
					
					final SenderAttachmentsBean senderAttachmentsBean = new SenderAttachmentsBean();
					final File tempAttachFile = File.createTempFile(filename,"");
					FileUtils.copyInputStreamToFile(is, tempAttachFile);
					senderAttachmentsBean.setFile(tempAttachFile);
					senderAttachmentsBean.setMimetype(mimetype);
					senderAttachmentsBean.setFilename(filename);
					senderAttachmentsBean.setOriginalName(filename);
					senderAttachmentsBean.setDimensione(BigDecimal.valueOf(tempAttachFile.length()));
					attachments.add(senderAttachmentsBean);
				} finally {
					if(is != null) {
						try {
							is.close();
						} catch (Exception e) {
							log.warn("Errore nella chiusura dello stream", e);
						}
					}
				}
			} // for
		} catch (Exception e) {
			throw new AurigaMailException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "File allegati non recuperati.");
		}
		return attachments;
	} // if

	protected final String toString(it.eng.jaxb.variabili.Lista lista) throws JAXBException  {
		if (lista == null)
			return null;
		final StringWriter stringWriter = new StringWriter();
		final Marshaller marshaller = jaxbContextSezCache.createMarshaller();
		marshaller.marshal(lista, stringWriter);
		return stringWriter.toString();
	}

}// BaseResource
