/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.common.base.Throwables;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.multipart.file.StreamDataBodyPart;
import com.sun.jersey.spi.resource.Singleton;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerInsbatchBean;
import it.eng.auriga.database.store.dmpk_dizionario.bean.DmpkDizionarioTrovadictvaluesfordictentryBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailAnnullaarchiviazioneemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailAnnullaassegnazioneemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailArchiviaemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailAssegnaemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailCollegaregtoemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailCtrlutenzaabilitatainvioBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailLoaddettemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailLockemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailSetazionedafaresuemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailTagemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailTrovaemailBean;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailUnlockemailBean;
import it.eng.auriga.database.store.dmpk_load_combo.bean.DmpkLoadComboDmfn_load_comboBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginconcredenzialiesterneBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.dao.beans.JobBean;
import it.eng.aurigamailbusiness.bean.EmailInfoBean;
import it.eng.aurigamailbusiness.bean.EmailSentReferenceBean;
import it.eng.aurigamailbusiness.bean.SenderAttachmentsBean;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.aurigamailbusiness.bean.restrepresentation.EmailMessage;
import it.eng.aurigamailbusiness.bean.restrepresentation.EmailReferences;
import it.eng.aurigamailbusiness.bean.restrepresentation.SezioneCacheXMLDatiEmailRecuperaDettaglioEmail;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.AssignRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.CancelAssignmentRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.CancelClosingRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.CloseRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.CollegaRegToEmailRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.DictionaryLookupRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.ExternalApplicationLoginRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.GetEmailBoxesRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.LockRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.LookupRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.MassSubmissionRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.SendAndSaveNewRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.SetActionToDoRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.TagRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.UnlockRequest;
import it.eng.aurigamailbusiness.bean.restrepresentation.output.CancelClosingResponse;
import it.eng.aurigamailbusiness.bean.restrepresentation.output.CloseResponse;
import it.eng.aurigamailbusiness.bean.restrepresentation.output.CollegaRegToEmailRequestResponse;
import it.eng.aurigamailbusiness.bean.restrepresentation.output.DictionaryLookupResponse;
import it.eng.aurigamailbusiness.bean.restrepresentation.output.ExternalApplicationLoginResponse;
import it.eng.aurigamailbusiness.bean.restrepresentation.output.GetDetailResponse;
import it.eng.aurigamailbusiness.bean.restrepresentation.output.GetEmailAttachmentsResponse;
import it.eng.aurigamailbusiness.bean.restrepresentation.output.GetEmailBoxesResponse;
import it.eng.aurigamailbusiness.bean.restrepresentation.output.LockResponse;
import it.eng.aurigamailbusiness.bean.restrepresentation.output.LookupResponse;
import it.eng.aurigamailbusiness.bean.restrepresentation.output.SendAndSaveNewResponse;
import it.eng.aurigamailbusiness.bean.restrepresentation.output.TagResponse;
import it.eng.aurigamailbusiness.bean.restrepresentation.output.UnlockResponse;
import it.eng.aurigamailbusiness.bean.restrepresentation.row.RigaCaselleInvRic;
import it.eng.aurigamailbusiness.bean.restrepresentation.row.RigaEsitiAnnullaArchiviazioneEmail;
import it.eng.aurigamailbusiness.bean.restrepresentation.row.RigaEsitiArchiviaEmail;
import it.eng.aurigamailbusiness.bean.restrepresentation.row.RigaEsitiLockEmail;
import it.eng.aurigamailbusiness.bean.restrepresentation.row.RigaEsitiTagEmail;
import it.eng.aurigamailbusiness.bean.restrepresentation.row.RigaEsitiUnlockEmail;
import it.eng.aurigamailbusiness.bean.restrepresentation.row.RigaListaXMLTrovaValoriDizionario;
import it.eng.aurigamailbusiness.bean.restrepresentation.row.RigaResultTrovaEmail;
import it.eng.aurigamailbusiness.bean.restrepresentation.xmllist.ListaCaselleInvRic;
import it.eng.aurigamailbusiness.bean.restrepresentation.xmllist.ListaEsitiAnnullaArchiviazioneEmail;
import it.eng.aurigamailbusiness.bean.restrepresentation.xmllist.ListaEsitiArchiviaEmail;
import it.eng.aurigamailbusiness.bean.restrepresentation.xmllist.ListaEsitiLockEmail;
import it.eng.aurigamailbusiness.bean.restrepresentation.xmllist.ListaEsitiTagEmail;
import it.eng.aurigamailbusiness.bean.restrepresentation.xmllist.ListaEsitiUnlockEmail;
import it.eng.aurigamailbusiness.bean.restrepresentation.xmllist.ListaListaXMLTrovaValoriDizionario;
import it.eng.aurigamailbusiness.bean.restrepresentation.xmllist.ListaResultTrovaEmail;
import it.eng.aurigamailbusiness.exception.AurigaMailBusinessException;
import it.eng.aurigamailbusiness.sender.storage.StorageImplementation;
import it.eng.aurigamailbusiness.utility.JobParameterRiga;
import it.eng.aurigamailbusiness.utility.MailboxUtil;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.xml.XmlListaUtility;

@Singleton
@Api
@Path("/email")
public class AurigaMailResource extends AurigaMailBaseResource {

	private static final Logger logger = Logger.getLogger(AurigaMailResource.class);

	private static final String SENDANDSAVE_NOTES = "E' il servizio REST di riferimento richiamabile con un client qualsiasi.<br/>"
			+ "<b>Per chiamare questo servizio da UI Swagger (conviene) usare il servizio equivalente su '/email/sendingSWA'.</b><br/>"
			+ "Il parametro 'data' deve essere valorizzato con una stringa XML la cui struttura è documentata nel servizio su '/email/noAttachment/sending'.<br/>"
			+ "In realtà il tipo di dato per il parametro 'data' non è 'string' bensì un 'object' istanza di SendAndSaveNewRequest.<br/>"
			+ "Per un servizio così definito, purtroppo, la libreria client Swagger non è ancora in grado di produrre un file descrittore del servizio esaustivo.<br/>"
			+ "Di seguito un esempio di un messaggio HTTP semplificato che questo servizio prende in carico:<br/><br/>"
			+ "Content-Type: multipart/form-data; boundary=Boundary_1_511262261_1369143433608<br/><br/>" + "--Boundary_1_511262261_1369143433608<br/>"
			+ "Content-Disposition: form-data; name=\"data\"<br/>" + "Content-Type: application/xml<br/><br/><br/>"
			+ "--Boundary_1_511262261_1369143433608<br/>" + "Content-Disposition: form-data; name=\"file\"; filename=\"aaa.pdf\"<br/>"
			+ "Content-Type: application/pdf<br/><br/><br/>" + "--Boundary_1_511262261_1369143433608<br/>"
			+ "Content-Disposition: form-data; name=\"file\"; filename=\"bbb.txt\"<br/>" + "Content-Type: text/plain<br/><br/><br/>"
			+ "--Boundary_1_511262261_1369143433608--<br/>";

	private static final String DESC_PARAM_TOKEN = "Token di Autenticazione ottenibile chiamando il servizio di Login";

	public AurigaMailResource() throws Exception {
		logger.warn("AurigaMailResource creation");
	}

	@POST
	@Path("/login")
	@Produces({ MediaType.APPLICATION_XML/* , MediaType.APPLICATION_JSON */ })
	@Consumes({ MediaType.APPLICATION_XML/* , MediaType.APPLICATION_JSON */ })
	@ApiOperation(value = "Permette di autenticarsi nel sistema.(Login)", notes = "Nella risposta è fornito un token di autenticazione.", response = ExternalApplicationLoginResponse.class, tags = "Autenticazione")
	@ApiResponses({ @ApiResponse(code = 422, message = "Operazione interrotta a causa di un errore gestito."),
			@ApiResponse(code = 500, message = "Operazione interrotta a causa di un errore imprevisto.") })
	public ExternalApplicationLoginResponse login(
			@ApiParam(value = "Oggetto ExternalApplicationLoginRequest", required = true) ExternalApplicationLoginRequest entity,
			@ApiParam(value = "Alias dello schema", required = false, hidden = true) @HeaderParam(HEADER_NAME_SCHEMA_SELEZIONATO) String schema) {
		logger.debug("login() inizio");

		if (StringUtils.isBlank(schema)) {
			schema = defaultSchema;
			if (StringUtils.isBlank(schema)) {
				throw new AurigaMailException(Response.Status.BAD_REQUEST.getStatusCode(), "E' necessario valorizzare l'header HTTP 'schema'.");
			}
		}

		StoreResultBean<DmpkLoginLoginconcredenzialiesterneBean> storeResultBean = null;
		try {
			storeResultBean = callHelper.callLoginConCredenzialiEsterneFunc(entity, getSchema(schema));
		} catch (Exception e) {
			logger.error("Errore nella chiamata callLoginConCredenzialiEsterneFunc()", e);
			throw new AurigaMailException(e);
		}

		if (storeResultBean.isInError()) {
			logger.error("L'esecuzione della function 'DMPK_LOGIN.LOGINCONCREDENZIALIESTERNE' si è conclusa con errore:\n"
					+ String.valueOf(storeResultBean.getDefaultMessage()));
			throw new AurigaMailException(422, storeResultBean.getDefaultMessage());
		}

		// La function LOGINCONCREDENZIALIESTERNE è stata eseguita con successo
		final DmpkLoginLoginconcredenzialiesterneBean result = storeResultBean.getResultBean();

		final ExternalApplicationLoginResponse response = new ExternalApplicationLoginResponse();
		response.setDomainDescription(result.getDesdominioout());
		response.setDomainId(result.getIddominioout() != null ? result.getIddominioout().longValueExact() : null);
		response.setToken(result.getCodidconnectiontokenout());
		response.setUserDescription(result.getDesuserout());

		logger.debug("login() fine");
		return response;
	}// login

	// ==================================================================================================================================
	@POST
	// @Path("/multipart/sending")
	@Path("/sending")
	@Produces({ MediaType.APPLICATION_XML/* , MediaType.APPLICATION_JSON */ })
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@ApiOperation(value = "SERVIZIO ESPOSTO DI RIFERIMENTO.\nSpedisce una email con eventuali allegati e salva le informazioni nel sistema.(SpedisciESalvaEmail)", notes = SENDANDSAVE_NOTES, response = SendAndSaveNewResponse.class, tags = "Invio")
	@ApiResponses({ @ApiResponse(code = 400, message = "Operazione interrotta a causa di input non valido."),
			@ApiResponse(code = 403, message = "Operazione non permessa."),
			@ApiResponse(code = 500, message = "Operazione interrotta a causa di un errore imprevisto.") })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "data", value = "XML richiestaSpedisciESalvaEmail", required = true, dataType = "string", paramType = "form"), })
	public SendAndSaveNewResponse sendAndSave(
			/* @ApiParam(value = "XML richiestaSpedisciESalvaEmail", required = true) */ @FormDataParam("data") final SendAndSaveNewRequest data,
			@ApiParam(value = "Files eventuali da allegare all'email", required = false) @FormDataParam("file") final List<FormDataBodyPart> fileParts,
			/* @ApiParam(value = "Files eventuali da allegare all'email", required = false) @FormDataParam("file") final List<File> files, */
			@ApiParam(value = DESC_PARAM_TOKEN, required = true) @HeaderParam(HEADER_NAME_AUTHENTICATION_TOKEN) final String token,
			@ApiParam(value = "Alias dello schema", required = false, hidden = true) @HeaderParam(HEADER_NAME_SCHEMA_SELEZIONATO) String schema) {

		SendAndSaveUtil.check(data);

		if (StringUtils.isBlank(token)) {
			throw new AurigaMailException(Response.Status.BAD_REQUEST.getStatusCode(), "E' necessario valorizzare l'header HTTP 'token'.");
		}

		if (StringUtils.isBlank(schema)) {
			schema = defaultSchema;
			if (StringUtils.isBlank(schema)) {
				throw new AurigaMailException(Response.Status.BAD_REQUEST.getStatusCode(), "E' necessario valorizzare l'header HTTP 'schema'.");
			}
		}

		final EmailMessage message = data.getEmailMessage();
		final DmpkIntMgoEmailCtrlutenzaabilitatainvioBean resp = checkPermissionRS(token, message.getAddressFrom(), schema);

		final List<SenderAttachmentsBean> attachments = getAttachments(fileParts);

		final SenderBean senderBean = new SenderBean();
		SendAndSaveUtil.setSenderBeanFields(data, senderBean);
		senderBean.setAttachments(attachments);
		senderBean.setIdUtenteModPec(getIdUtenteModPec(resp));

		final EmailSentReferenceBean result = SendAndSaveUtil.getResult(senderBean);

		final List<SenderBean> sentMails = result.getSentMails();
		final SendAndSaveNewResponse response = new SendAndSaveNewResponse();
		if (sentMails != null) {
			final List<EmailReferences> list = new ArrayList<>(sentMails.size());
			if (sentMails.size() > 0) {
				response.setMittentePEC(sentMails.get(0).getIsPec());
				response.setNotificationReading(sentMails.get(0).getReturnReceipt());
				response.setIdUtenteModPec(sentMails.get(0).getIdUtenteModPec());
			}
			for (SenderBean bean : sentMails) {
				final EmailReferences emailReferences = new EmailReferences();
				SendAndSaveUtil.setEmailReferencesFields(bean, emailReferences);
				list.add(emailReferences);
			} // for
			response.setList(list);
		}

		SendAndSaveUtil.setResponseFields(attachments, response);
		return response;
	}//

	@POST
	@Path("/noAttachment/sending")
	@Produces({ MediaType.APPLICATION_XML/* , MediaType.APPLICATION_JSON */ })
	@Consumes({ MediaType.APPLICATION_XML/* , MediaType.APPLICATION_JSON */ })
	@ApiOperation(value = "SERVIZIO PER L'INVIO SENZA ALLEGATI.\nSpedisce una email senza allegati e salva le informazioni nel sistema. (SpedisciESalvaEmail)", notes = "E' il servizio REST per testare l'invio senza allegati da UI Swagger. "
			+ "E' anche richiamabile con un client qualsiasi. <br/>"
			+ "E' stato messo a disposizione principalmente per documentare l'XML con cui valorizzare il parametro 'data'.<br/>"
			+ "Questo servizio ha il vantaggio di essere fruibile con richieste senza 'multipart/*'.", response = SendAndSaveNewResponse.class, tags = "Invio")
	@ApiResponses({ @ApiResponse(code = 400, message = "Operazione interrotta a causa di input non valido."),
			@ApiResponse(code = 403, message = "Operazione non permessa."),
			@ApiResponse(code = 500, message = "Operazione interrotta a causa di un errore imprevisto.") })
	public SendAndSaveNewResponse sendAndSave(@ApiParam(name = "data", value = "XML richiestaSpedisciESalvaEmail", required = true) SendAndSaveNewRequest data,
			@ApiParam(value = DESC_PARAM_TOKEN, required = true) @HeaderParam(HEADER_NAME_AUTHENTICATION_TOKEN) final String token,
			@ApiParam(value = "Alias dello schema", required = false, hidden = true) @HeaderParam(HEADER_NAME_SCHEMA_SELEZIONATO) String schema) {

		return sendAndSave(data, null, token, schema);
	}//

	@POST
	// @Path("/multipart/sendingSWA")
	@Path("/sendingSWA")
	@Produces({ MediaType.APPLICATION_XML/* , MediaType.APPLICATION_JSON */ })
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@ApiOperation(value = "SERVIZIO AD USO ESCLUSIVO DI UI SWAGGER.\nSpedisce una email con eventuali allegati e salva le informazioni nel sistema. (SpedisciESalvaEmail)", notes = "E' il servizio REST per testare l'invio con allegati da UI Swagger.<br/>"
			+ "Il parametro 'data' deve essere valorizzato con una stringa XML la cui struttura è documentata nel servizio su '/email/noAttachment/sending'.<br/>"
			+ "Conviene fare copia e incolla sul campo testo relativo.", response = SendAndSaveNewResponse.class, tags = "Invio")
	@ApiResponses({ @ApiResponse(code = 400, message = "Operazione interrotta a causa di input non valido."),
			@ApiResponse(code = 403, message = "Operazione non permessa."),
			@ApiResponse(code = 500, message = "Operazione interrotta a causa di un errore imprevisto.") })
	@ApiImplicitParams({ @ApiImplicitParam(name = "data", value = "XML richiestaSpedisciESalvaEmail", required = true, dataType = "string", paramType = "form"),
			@ApiImplicitParam(name = "file1", value = "Allegato uno", dataType = "file", paramType = "form"),
			@ApiImplicitParam(name = "file2", value = "Allegato due", dataType = "file", paramType = "form"),
			@ApiImplicitParam(name = "file3", value = "Allegato tre", dataType = "file", paramType = "form"),
			@ApiImplicitParam(name = "file4", value = "Allegato quattro", dataType = "file", paramType = "form"),
			@ApiImplicitParam(name = "file5", value = "Allegato cinque", dataType = "file", paramType = "form"), })
	public SendAndSaveNewResponse sendAndSave(@FormDataParam("data") SendAndSaveNewRequest data, @FormDataParam("file1") InputStream file1,
			@FormDataParam("file1") FormDataContentDisposition file1Disposition, @FormDataParam("file2") InputStream file2,
			@FormDataParam("file2") FormDataContentDisposition file2Disposition, @FormDataParam("file3") InputStream file3,
			@FormDataParam("file3") FormDataContentDisposition file3Disposition, @FormDataParam("file4") InputStream file4,
			@FormDataParam("file4") FormDataContentDisposition file4Disposition, @FormDataParam("file5") InputStream file5,
			@FormDataParam("file5") FormDataContentDisposition file5Disposition,
			@ApiParam(value = DESC_PARAM_TOKEN, required = true) @HeaderParam(HEADER_NAME_AUTHENTICATION_TOKEN) final String token,
			@ApiParam(value = "Alias dello schema", required = false, hidden = true) @HeaderParam(HEADER_NAME_SCHEMA_SELEZIONATO) String schema) {

		final List<FormDataBodyPart> fileParts = new ArrayList<>();
		if (file1 != null) {
			fileParts.add(new StreamDataBodyPart(file1Disposition.getName(), file1, file1Disposition.getFileName()));
		}
		if (file2 != null) {
			fileParts.add(new StreamDataBodyPart(file2Disposition.getName(), file2, file2Disposition.getFileName()));
		}
		if (file3 != null) {
			fileParts.add(new StreamDataBodyPart(file3Disposition.getName(), file3, file3Disposition.getFileName()));
		}
		if (file4 != null) {
			fileParts.add(new StreamDataBodyPart(file4Disposition.getName(), file4, file4Disposition.getFileName()));
		}
		if (file5 != null) {
			fileParts.add(new StreamDataBodyPart(file5Disposition.getName(), file5, file5Disposition.getFileName()));
		}
		return sendAndSave(data, fileParts, token, schema);
	}

	@POST
	@Path("/invioMassivo")
	@Produces({ MediaType.APPLICATION_XML/* , MediaType.APPLICATION_JSON */ })
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@ApiOperation(value = "Effettua l'invio massivo di email", notes = "L'invio effettivo viene fatto da un job", tags = "Invio", hidden = false)
	@ApiResponses({ @ApiResponse(code = 400, message = "Operazione interrotta a causa di input non valido."),
			@ApiResponse(code = 403, message = "Operazione non permessa."),
			@ApiResponse(code = 422, message = "Operazione interrotta a causa di un errore gestito."),
			@ApiResponse(code = 500, message = "Operazione interrotta a causa di un errore imprevisto.") })
	@ApiImplicitParams({ @ApiImplicitParam(name = "data", value = "XML richiestaInvioMassivoEmail", required = true, dataType = "string", paramType = "form"),
			@ApiImplicitParam(name = "fileXlsDestinatariMail", value = "File excel", dataType = "file", paramType = "form") })
	public Response invioMassivo(
			/* @ApiParam(value = "XML richiestaSpedisciESalvaEmail", required = true) */ @FormDataParam("data") final MassSubmissionRequest data,
			@FormDataParam("fileXlsDestinatariMail") final InputStream excel,
			@FormDataParam("fileXlsDestinatariMail") final FormDataContentDisposition excelDisposition,
			@ApiParam(value = "Files eventuali da allegare all'email", required = false) @FormDataParam("file") final List<FormDataBodyPart> fileParts,
			@ApiParam(value = "Alias dello schema", required = false, hidden = true) @HeaderParam(HEADER_NAME_SCHEMA_SELEZIONATO) String schema) throws Exception {
		logger.debug("invioMassivo() inizio");
		
		final int status400 = Response.Status.BAD_REQUEST.getStatusCode();

		if (excel == null) {
			throw new AurigaMailException(status400, "Manca il file xls con i destinatari dell'invio.");
		}
		
		InvioMassivoUtil.check(data);

		if (StringUtils.isBlank(schema)) {
			schema = defaultSchema;
			if (StringUtils.isBlank(schema)) {
				throw new AurigaMailException(status400, "E' necessario valorizzare l'header HTTP 'schema'.");
			}
		}

		StoreResultBean<DmpkLoginLoginBean> storeResultBean = null;
		try {
			storeResultBean = callHelper.callLoginFunc(data.getUserId(), data.getPassword(), getSchema(schema));
		} catch (Exception e) {
			logger.error("Errore nella chiamata callLoginFunc()", e);
			throw new AurigaMailException(500, "Non è stato possibile autorizzare l'accesso dell'utente.");
		}

		if (storeResultBean.isInError()) {
			logger.error("L'esecuzione della function 'DMPK_LOGIN.LOGIN' si è conclusa con errore:\n" + String.valueOf(storeResultBean.getDefaultMessage()));
			throw new AurigaMailException(Response.Status.FORBIDDEN.getStatusCode(), storeResultBean.getDefaultMessage());
		}

		// La function LOGIN è stata eseguita con successo
		final DmpkLoginLoginBean result = storeResultBean.getResultBean();
		
		initSubject(schema);
		String idAccount = null;
		try {
		   idAccount = MailboxUtil.getIdAccount(data.getAddressFrom());
		   logger.info("id account del mittente: "+idAccount);
		} catch (AurigaMailBusinessException e) {
			throw new AurigaMailException(422, e.getLocalizedMessage());
		}
		
		final Lista lista = InvioMassivoUtil.getLista(data.getColumns());
		final String xmlLista = toString(lista);
		logger.debug("xmlLista: "+xmlLista);
		
		final String userId = data.getUserId();
		final String subject = data.getSubject();
		final String body = data.getBody();
		
		if (storageService == null) {
			storageService = StorageImplementation.getStorage();
		}

		String uriExcel = null;
		try {
			uriExcel = storageService.storeStream(excel);			
		} catch (StorageException e) {
			logger.error("Errore durante il salvataggio nello storage dei file xls in input al servizio.", e);
			throw new AurigaMailException(500, "Non è stato possibile salvare il file xls nell'area di storage.");
		}
		logger.info("file xls salvato con successo nello storage: "+uriExcel);
		
		final List<SenderAttachmentsBean> attachments = getAttachments(fileParts);
		try {		
			for (SenderAttachmentsBean attach : attachments) {
				if(attach.getFile() != null) {
					byte[] attachByte;
					InputStream isAttachStream = null;
					try {
						attachByte = Files.readAllBytes(attach.getFile().toPath());
						isAttachStream = new ByteArrayInputStream(attachByte);
						final String uriAttach = storageService.storeStream(isAttachStream);
						logger.info("allegato salvato con successo nello storage: "+uriAttach);
						attach.setFilename(uriAttach);
					} catch (IOException e) {
						logger.error("Impossibile recuperare l'allegato con uri: " + attach.getFile().toPath());
					} finally {
						if(isAttachStream != null) {
							try {
								isAttachStream.close();
							} catch (Exception e) {
								logger.warn("Errore nella chiusura dello stream", e);
							}
						}
					}
				} else {
					final InputStream attachStream = new ByteArrayInputStream(attach.getContent());
					final String uriAttach = storageService.storeStream(attachStream);
					logger.info("allegato salvato con successo nello storage: "+uriAttach);
					attach.setFilename(uriAttach);
				}
			}//for
			
		} catch (StorageException e) {
			logger.error("Errore durante il salvataggio nello storage degli allegati in input al servizio.", e);
			throw new AurigaMailException(500, "Non è stato possibile salvare gli allegati nell'area di storage.");
		}
	
		for (SenderAttachmentsBean attach : attachments) {				
			final MessageDigest msgDigest = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
			byte[] attachByte;
			if(attach.getFile() != null) {
				try {
					attachByte = Files.readAllBytes(attach.getFile().toPath());
					final byte[] hash = msgDigest.digest(attachByte);
					attach.setImpronta(Base64.encodeBase64String(hash));					
				} catch (IOException e) {
					logger.error("Impossibile recuperare l'allegato con uri: " + attach.getFile().toPath());
				}	
			} else {
		    	final byte[] hash = msgDigest.digest(attach.getContent());
				attach.setImpronta(Base64.encodeBase64String(hash));
			}
			attach.setAlgoritmo("sha-256");
			attach.setEncoding("base64");
		}//for
		
		final Lista listaAllegati = InvioMassivoUtil.getListaAllegati(attachments);
		final String xmlListaAllegati = toString(listaAllegati);
		logger.debug("xmlListaAllegati: "+xmlListaAllegati);
		
		final BigDecimal idJob = null;
		final List<JobParameterRiga> righe = InvioMassivoUtil.getJobParameterRighe(idAccount, xmlLista, subject, body, attachments.size(), uriExcel, xmlListaAllegati, idJob);
		final String jobParamsXml = xmlUtilitySerializer.bindXmlList(righe);
		logger.debug("jobParamsXml: "+jobParamsXml);
		
		final JobBean jobBean = new JobBean();
		jobBean.setTipo("INVIO_MAIL_VS_LISTA_DEST_XLS");
		jobBean.setIdUser(userId);
		
		StoreResultBean<DmpkBmanagerInsbatchBean> storeResultBean2 = null;
		try {
			storeResultBean2 = callHelper.callInsBatchFunc(jobBean, jobParamsXml, getSchema(schema));
		} catch (Exception e) {
			logger.error("Errore nella chiamata callInsBatchFunc()", e);
			throw new AurigaMailException(500, "Non è stato possibile salvare i dati nella base dati.");
		}

		if (storeResultBean2.isInError()) {
			logger.error("L'esecuzione della function 'DMPK_BMANAGER.INSBATCH' si è conclusa con errore:\n" + String.valueOf(storeResultBean.getDefaultMessage()));
			throw new AurigaMailException(422, storeResultBean2.getDefaultMessage());
		}
		
		final DmpkBmanagerInsbatchBean result2 = storeResultBean2.getResultBean();
		
		final ResponseBuilder builder = Response.created(URI.create("/jobs/"+String.valueOf(result2.getIdjobout())));

		final Response response = builder.build();
		logger.debug("invioMassivo() fine");
		return response;
		
	}// invioMassivo

	// ==================================================================================================================================
	@GET
	@Path("/{emailId}/downloading")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@ApiOperation(value = "Recupera il file eml della mail (DownloadEmail)", notes = "Se la mail che si richiede è una PEC inviata, il file potrebbe essere recuperato dalla ricevuta di consegna completa", tags = "Interazione")
	@ApiResponses({ @ApiResponse(code = 404, message = "Operazione interrotta a causa di dati non trovati e/o non validi."),
			@ApiResponse(code = 500, message = "Operazione interrotta a causa di un errore imprevisto.") })
	public Response downloadEmlFile(@ApiParam(value = "ID dell'email", required = true) @PathParam("emailId") final String idEmail,
			final @ApiParam(value = "Alias dello schema", required = false, hidden = true) @HeaderParam(HEADER_NAME_SCHEMA_SELEZIONATO) String schema) {
		logger.debug("downloadEmlFile() inizio: " + String.valueOf(idEmail));

		final StreamingOutput fileStream = new StreamingOutput() {

			@Override
			public void write(OutputStream output) throws IOException, WebApplicationException {
				final File emlFile = getFileEmail(idEmail, getSchema(schema));
				output.write(com.google.common.io.Files.toByteArray(emlFile));
			}
		};
		final ResponseBuilder builder = Response.ok(fileStream, MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition",
				"attachment; filename=" + idEmail + ".eml");

		final Response response = builder.build();
		logger.debug("downloadEmlFile() fine");
		return response;
	}

	@GET
	@Path("/{emailId}/attachments")
	@Produces({ MediaType.APPLICATION_XML/* , MediaType.APPLICATION_JSON */ })
	@ApiOperation(value = "Recupera gli allegati dell'email (RecuperaAllegatiEmail)", notes = "Chiama ServiceRestStore.invoke() del core-module (nuova operazione 'getAttachmentsWithContents').", response = GetEmailAttachmentsResponse.class, tags = "Interazione")
	@ApiResponses({ @ApiResponse(code = 400, message = "Operazione interrotta a causa di input non valido."),
			@ApiResponse(code = 404, message = "Operazione interrotta a causa di dati non trovati e/o non validi."),
			@ApiResponse(code = 500, message = "Operazione interrotta a causa di un errore imprevisto.") })
	public GetEmailAttachmentsResponse getAttachmentsWithContents(@ApiParam(value = "ID email", required = true) @PathParam("emailId") String idEmail,
			@ApiParam(value = "se false non recupera il contenuto") @DefaultValue("true") @QueryParam("flagGetContent") boolean flag,
			@ApiParam(value = "Alias dello schema", required = false, hidden = true) @HeaderParam(HEADER_NAME_SCHEMA_SELEZIONATO) String schema)
			throws Exception {
		logger.debug("getAttachmentsWithContents() inizio: " + String.valueOf(idEmail));

		if (StringUtils.isBlank(idEmail)) {
			throw new AurigaMailException(400/* Bad Request */, "E' necessario specificare l'ID email.");
		}

		final File emlFile = getFileEmail(idEmail, getSchema(schema));

		final MailProcessorResource mailProcessorResource = resourceCtx.getResource(MailProcessorResource.class);
		final GetEmailAttachmentsResponse result = mailProcessorResource.getAttachmentsWithContents(emlFile, flag);

		logger.debug("getAttachmentsWithContents() fine");
		return result;
	}// getAttachmentsWithContents

	// RICERCA EMAILs
	@POST
	@Path("/lookup")
	@Produces({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@Consumes({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@ApiOperation(value = "Ricerca le mail (TrovaEmail)", notes = "Al momento nessuna nota", response = LookupResponse.class, tags = "Interazione")
	@ApiResponses({ @ApiResponse(code = 422, message = "Operazione interrotta a causa di un errore gestito."),
			@ApiResponse(code = 500, message = "Operazione interrotta a causa di un errore imprevisto.") })
	public LookupResponse lookup(@ApiParam(value = "Oggetto LookupRequest", required = true) LookupRequest entity,
			@ApiParam(value = "Alias dello schema", required = false, hidden = true) @HeaderParam(HEADER_NAME_SCHEMA_SELEZIONATO) String schema) {
		logger.debug("lookup() inizio");

		StoreResultBean<DmpkIntMgoEmailTrovaemailBean> storeResultBean = null;
		try {
			storeResultBean = callHelper.callTrovaEmailFunc(entity, getSchema(schema));
		} catch (Exception e) {
			logger.error("Errore nella chiamata callTrovaEmailFunc()", e);
			throw new AurigaMailException(e);
		}

		if (storeResultBean.isInError()) {
			logger.error("L'esecuzione della function 'DMPK_INT_MGO_EMAIL.TROVAEMAIL' si è conclusa con errore:\n"
					+ String.valueOf(storeResultBean.getDefaultMessage()));
			throw new AurigaMailException(422, storeResultBean.getDefaultMessage());
		}

		// La function TROVAEMAIL è stata eseguita con successo
		final DmpkIntMgoEmailTrovaemailBean result = storeResultBean.getResultBean();

		final String xml = result.getResultout();
		// logger.debug("ResultOut:\n"+String.valueOf(xml));

		ListaResultTrovaEmail list = null;
		if (StringUtils.isNotBlank(xml)) {
			List<RigaResultTrovaEmail> items = null;
			try {
				items = XmlListaUtility.recuperaLista(xml, RigaResultTrovaEmail.class);
			} catch (Exception e) {
				logger.error("Errore nella chiamata recuperaLista()", e);
				throw new AurigaMailException(e);
			}
			list = new ListaResultTrovaEmail();
			list.setItems(items);
		}

		final LookupResponse response = new LookupResponse();
		response.setResultTrovaEmailList(list);
		response.setTotalSize(result.getNrototrecout());// NRO_TOT_REC
		if (entity.isFlagPaginazione()) {
			response.setPageSize(result.getNrorecinpaginaout());// NRO_REC_IN_PAGINA
			response.setPageNumber(result.getNropaginaio());// NRO_PAGINA
		} else {
			response.setPageSize(result.getNrototrecout());// NRO_TOT_REC
			response.setPageNumber(1);// NRO_PAGINA
		}

		logger.debug("lookup() fine");
		return response;
	}

	// DETTAGLIO EMAIL
	@GET
	@Path("/{emailId}")
	@Produces({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@Consumes({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@ApiOperation(value = "Recupera tutti i dati di una e-mail (in entrata o in uscita) e delle sue e-mail collegate (RecuperaDettaglioEmail)", notes = "Al momento nessuna nota", response = GetDetailResponse.class, tags = "Interazione")
	@ApiResponses({ @ApiResponse(code = 400, message = "Operazione interrotta a causa di input non valido."),
			@ApiResponse(code = 404, message = "Email specificata non trovata."),
			@ApiResponse(code = 422, message = "Operazione interrotta a causa di un errore gestito."),
			@ApiResponse(code = 500, message = "Operazione interrotta a causa di un errore imprevisto.") })
	public GetDetailResponse getDetail(@ApiParam(value = "ID email", required = true) @PathParam("emailId") String idEmail,
			@ApiParam(value = DESC_PARAM_TOKEN, required = true) @QueryParam("token") String token,
			@ApiParam(value = "Alias dello schema", required = false, hidden = true) @HeaderParam(HEADER_NAME_SCHEMA_SELEZIONATO) String schema) {
		logger.debug("getDetail() inizio: " + String.valueOf(idEmail));

		if (StringUtils.isBlank(idEmail)) {
			throw new AurigaMailException(400, "E' necessario specificare l'ID email.");
		}

		if (StringUtils.isBlank(token)) {
			throw new AurigaMailException(400, "E' necessario specificare il token di autenticazione.");
		}

		StoreResultBean<DmpkIntMgoEmailLoaddettemailBean> storeResultBean = null;
		try {
			storeResultBean = callHelper.callLoadDettEmailFunc(token, getSchema(schema), idEmail, null);
		} catch (Exception e) {
			logger.error("Errore nella chiamata callLoadDettEmailFunc()", e);
			throw new AurigaMailException(e);
		}

		if (storeResultBean.isInError()) {
			logger.error("L'esecuzione della function 'DMPK_INT_MGO_EMAIL.LOADDETTEMAIL' si è conclusa con errore:\n"
					+ String.valueOf(storeResultBean.getDefaultMessage()));
			throw new AurigaMailException(422, storeResultBean.getDefaultMessage());
		}

		// La function LOADDETTEMAIL è stata eseguita con successo
		final DmpkIntMgoEmailLoaddettemailBean result = storeResultBean.getResultBean();

		final String xml = result.getXmldatiemailout();
		// logger.debug("XMLDatiEmailOut:\n"+String.valueOf(xml));

		SezioneCacheXMLDatiEmailRecuperaDettaglioEmail data = null;
		if (StringUtils.isNotBlank(xml)) {
			try {
				data = xmlUtilityDeserializer.unbindXml(xml, SezioneCacheXMLDatiEmailRecuperaDettaglioEmail.class);
			} catch (Exception e) {
				logger.error("Errore nella chiamata unbindXml()", e);
				throw new AurigaMailException(e);
			}
		}

		final GetDetailResponse response = new GetDetailResponse();
		response.setDatiEmail(data);

		logger.debug("getDetail() fine");

		return response;
	}// getDetail
	
	@POST
	@Path("/emailBoxes")
	@Produces({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@Consumes({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@ApiOperation(value = "Recupera le caselle da cui si può inviare o di cui si è smistatori (TrovaCaselleEmail)", 
	notes = "Al momento nessuna nota", response = GetEmailBoxesResponse.class, tags = "Interazione")
	@ApiResponses({ @ApiResponse(code = 400, message = "Operazione interrotta a causa di input non valido."),
		    @ApiResponse(code = 422, message = "Operazione interrotta a causa di un errore gestito."),
			@ApiResponse(code = 500, message = "Operazione interrotta a causa di un errore.") })
	public GetEmailBoxesResponse getEmailBoxes(@ApiParam(value = "Oggetto GetEmailBoxesRequest", required = true) GetEmailBoxesRequest entity,
			@ApiParam(value = DESC_PARAM_TOKEN, required = true) @QueryParam("token") String token,
			@ApiParam(value = "Alias dello schema", required = false, hidden = true) @HeaderParam(HEADER_NAME_SCHEMA_SELEZIONATO) String schema) {
		logger.debug("getEmailBoxes() inizio");
		
		final int status400 = Response.Status.BAD_REQUEST.getStatusCode();
		
		if (StringUtils.isBlank(schema)) {
			schema = defaultSchema;
			if (StringUtils.isBlank(schema)) {
				throw new AurigaMailException(status400, "E' necessario valorizzare l'header HTTP 'schema'.");
			}
		}

		StoreResultBean<DmpkLoadComboDmfn_load_comboBean> storeResultBean = null;
		try {
			storeResultBean = callHelper.callDmfnLoadComboFunc(entity, token, getSchema(schema));
		} catch (Exception e) {
			logger.error("Errore nella chiamata callDmfnLoadComboFunc()", e);
			throw new AurigaMailException(e);
		}

		if (storeResultBean.isInError()) {
			logger.error("L'esecuzione della function 'ORACLE DMPK_LOAD_COMBO.DMFN_LOAD_COMBO' si è conclusa con errore:\n"
					+ String.valueOf(storeResultBean.getDefaultMessage()));
			throw new AurigaMailException(422, storeResultBean.getDefaultMessage());
		}

		// La function DMFN_LOAD_COMBO è stata eseguita con successo
		final DmpkLoadComboDmfn_load_comboBean result = storeResultBean.getResultBean();

		final String xml = result.getListaxmlout();
//		logger.debug("ListaXMLOut:\n"+String.valueOf(xml));

		ListaCaselleInvRic listaCaselleInvRic = new ListaCaselleInvRic();
		if (StringUtils.isNotBlank(xml)) {
			List<RigaCaselleInvRic> items = null;
			try {
				items = XmlListaUtility.recuperaLista(xml, RigaCaselleInvRic.class);
			} catch (Exception e) {
				logger.error("Errore nella chiamata recuperaLista()", e);
				throw new AurigaMailException(e);
			}
			listaCaselleInvRic.setItems(items);
		}

		final GetEmailBoxesResponse response = new GetEmailBoxesResponse();
		response.setFinalita(entity.getFinalita());
		response.setListaCaselleInvRic(listaCaselleInvRic);
		
		logger.debug("getEmailBoxes() fine");
		return response;
	}// getEmailBoxes
	

	//TODO
	@GET
	@Path("/{emailId}/info")
	@Produces({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@ApiOperation(hidden=true, value = "Recupera tutti i dati di una e-mail (in entrata o in uscita) (RecuperaInformazioniEmail)", notes = "Al momento nessuna nota", response = EmailInfoBean.class, tags = "Interazione")
	@ApiResponses({ @ApiResponse(code = 400, message = "Operazione interrotta a causa di input non valido."),
			/*@ApiResponse(code = 404, message = "Email specificata non trovata."),*/
			/*@ApiResponse(code = 422, message = "Operazione interrotta a causa di un errore gestito."),*/
			@ApiResponse(code = 500, message = "Operazione interrotta a causa di un errore imprevisto.") })
	public EmailInfoBean getInformation(@ApiParam(value = "ID email", required = true) @PathParam("emailId") String idEmail,
			@ApiParam(value = DESC_PARAM_TOKEN, required = false) @QueryParam("token") String token,
			@ApiParam(value = "Alias dello schema", required = false, hidden = true) @HeaderParam(HEADER_NAME_SCHEMA_SELEZIONATO) String schema) {
		logger.debug("getInformation() inizio: " + String.valueOf(idEmail));

		if (false && StringUtils.isBlank(token)) {
			throw new AurigaMailException(Status.BAD_REQUEST.getStatusCode(), "E' necessario specificare il token di autenticazione.");
		}
		
		if (StringUtils.isBlank(schema)) {
			schema = defaultSchema;
			if (StringUtils.isBlank(schema)) {
				throw new AurigaMailException(Response.Status.BAD_REQUEST.getStatusCode(), "E' necessario valorizzare l'header HTTP 'schema'.");
			}
		}
		initSubject(schema);

		EmailInfoBean emailInfoBean = null;
		try {
			emailInfoBean = mailProcessorService.getMailInfoByIdEmail(idEmail);
		} catch (AurigaMailBusinessException ex) {
			logger.error("Errore nella chiamata all'operazione MailProcessorService#getMailInfoByIdEmail", ex);
			throw new AurigaMailException(Status.INTERNAL_SERVER_ERROR.getStatusCode(), Throwables.getRootCause(ex).getLocalizedMessage());
		} catch (Exception e) {
			logger.error("Errore nella chiamata all'operazione MailProcessorService#getMailInfoByIdEmail", e);
			throw new AurigaMailException(Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Non è stato possibile esaudire la richiesta.");
		}

		logger.debug("getInformation() fine");

		return emailInfoBean;
	}// getInformation


	// AZIONE DA FARE SU EMAIL
	@POST
	@Path("/actionToDo")
	@Produces({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@Consumes({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@ApiOperation(value = "Imposta l'azione da fare su una mail (ImpostaAzioneDaFareSuEmail)", notes = "Al momento nessuna nota", response = Void.class, tags = "Interazione")
	@ApiResponses({ @ApiResponse(code = 422, message = "Operazione interrotta a causa di un errore gestito."),
			@ApiResponse(code = 500, message = "Operazione interrotta a causa di un errore imprevisto.") })
	public void setActionToDo(@ApiParam(value = "Oggetto SetActionToDoRequest", required = true) SetActionToDoRequest entity,
			@ApiParam(value = "Alias dello schema", required = false, hidden = true) @HeaderParam(HEADER_NAME_SCHEMA_SELEZIONATO) String schema) {
		logger.debug("setActionToDo() inizio");

		StoreResultBean<DmpkIntMgoEmailSetazionedafaresuemailBean> storeResultBean = null;
		try {
			storeResultBean = callHelper.callSetAzioneDaFareSuEmailFunc(entity, getSchema(schema));
		} catch (Exception e) {
			logger.error("Errore nella chiamata callSetAzioneDaFareSuEmailFunc()", e);
			throw new AurigaMailException(e);
		}

		if (storeResultBean.isInError()) {
			logger.error("L'esecuzione della function 'DMPK_INT_MGO_EMAIL.SETAZIONEDAFARESUEMAIL' si è conclusa con errore:\n"
					+ String.valueOf(storeResultBean.getDefaultMessage()));
			throw new AurigaMailException(422, storeResultBean.getDefaultMessage());
		}

		// La function SETAZIONEDAFARESUEMAIL è stata eseguita con successo
		final DmpkIntMgoEmailSetazionedafaresuemailBean result = storeResultBean.getResultBean();

		logger.debug("setActionToDo() fine");
	}// setActionToDo

	// CHIUSURA EMAILs
	@POST
	@Path("/closing")
	@Produces({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@Consumes({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@ApiOperation(value = "Archivia una o più mail (sia ricevute che inviate) (ArchiviaEmail)", notes = "Al momento nessuna nota", response = CloseResponse.class, tags = "Archiviazione")
	@ApiResponses({ @ApiResponse(code = 422, message = "Operazione interrotta a causa di un errore gestito."),
			@ApiResponse(code = 500, message = "Operazione interrotta a causa di un errore imprevisto.") })
	public CloseResponse close(@ApiParam(value = "Oggetto CloseRequest", required = true) CloseRequest entity,
			@ApiParam(value = "Alias dello schema", required = false, hidden = true) @HeaderParam(HEADER_NAME_SCHEMA_SELEZIONATO) String schema) {
		logger.debug("close() inizio");

		StoreResultBean<DmpkIntMgoEmailArchiviaemailBean> storeResultBean = null;
		try {
			storeResultBean = callHelper.callArchiviaEmailFunc(entity, getSchema(schema));
		} catch (Exception e) {
			logger.error("Errore nella chiamata callArchiviaEmailFunc()", e);
			throw new AurigaMailException(e);
		}

		if (storeResultBean.isInError()) {
			logger.error("L'esecuzione della function 'DMPK_INT_MGO_EMAIL.ARCHIVIAEMAIL' si è conclusa con errore:\n"
					+ String.valueOf(storeResultBean.getDefaultMessage()));
			throw new AurigaMailException(422, storeResultBean.getDefaultMessage());
		}

		// La function ARCHIVIAEMAIL è stata eseguita con successo
		final DmpkIntMgoEmailArchiviaemailBean result = storeResultBean.getResultBean();

		final String xml = result.getEsitiout();
		// logger.debug("EsitiOut:\n"+String.valueOf(xml));

		ListaEsitiArchiviaEmail list = null;
		if (StringUtils.isNotBlank(xml)) {
			List<RigaEsitiArchiviaEmail> items = null;
			try {
				items = XmlListaUtility.recuperaLista(xml, RigaEsitiArchiviaEmail.class);
			} catch (Exception e) {
				logger.error("Errore nella chiamata recuperaLista()", e);
				throw new AurigaMailException(e);
			}
			list = new ListaEsitiArchiviaEmail();
			list.setItems(items);
		}

		// final Map<String, String> messages = retrieveMessagesFromResource(result.getEsitiout());

		final CloseResponse response = new CloseResponse();
		response.setEsitiArchiviaEmailList(list);
		// response.setMessages(messages);

		logger.debug("close() fine");
		return response;
	}// close

	// RIAPERTURA EMAILs
	@POST
	@Path("/closingCancellation")
	@Produces({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@Consumes({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@ApiOperation(value = "Riapre, ovvero annulla l'archiviazione di una o più mail precedentemente archiviate (AnnullaArchiviazioneEmail)", notes = "Al momento nessuna nota", response = CancelClosingResponse.class, tags = "Archiviazione")
	@ApiResponses({ @ApiResponse(code = 422, message = "Operazione interrotta a causa di un errore gestito."),
			@ApiResponse(code = 500, message = "Operazione interrotta a causa di un errore imprevisto.") })
	public CancelClosingResponse cancelClosing(@ApiParam(value = "Oggetto CancelClosingRequest", required = true) CancelClosingRequest entity,
			@ApiParam(value = "Alias dello schema", required = false, hidden = true) @HeaderParam(HEADER_NAME_SCHEMA_SELEZIONATO) String schema) {
		logger.debug("cancelClosing() inizio");

		StoreResultBean<DmpkIntMgoEmailAnnullaarchiviazioneemailBean> storeResultBean = null;
		try {
			storeResultBean = callHelper.callAnnullaArchiviazioneEmailFunc(entity, getSchema(schema));
		} catch (Exception e) {
			logger.error("Errore nella chiamata callAnnullaArchiviazioneEmailFunc()", e);
			throw new AurigaMailException(e);
		}

		if (storeResultBean.isInError()) {
			logger.error("L'esecuzione della function 'DMPK_INT_MGO_EMAIL.ANNULLAARCHIVIAZIONEEMAIL' si è conclusa con errore:\n"
					+ String.valueOf(storeResultBean.getDefaultMessage()));
			throw new AurigaMailException(422, storeResultBean.getDefaultMessage());
		}

		// La function ANNULLAARCHIVIAZIONEEMAIL è stata eseguita con successo
		final DmpkIntMgoEmailAnnullaarchiviazioneemailBean result = storeResultBean.getResultBean();

		final String xml = result.getEsitiout();
		// logger.debug("EsitiOut:\n"+String.valueOf(xml));

		ListaEsitiAnnullaArchiviazioneEmail list = null;
		if (StringUtils.isNotBlank(xml)) {
			List<RigaEsitiAnnullaArchiviazioneEmail> items = null;
			try {
				items = XmlListaUtility.recuperaLista(xml, RigaEsitiAnnullaArchiviazioneEmail.class);
			} catch (Exception e) {
				logger.error("Errore nella chiamata recuperaLista()", e);
				throw new AurigaMailException(e);
			}
			list = new ListaEsitiAnnullaArchiviazioneEmail();
			list.setItems(items);
		}

		// final Map<String, String> messages = retrieveMessagesFromResource(result.getEsitiout());

		final CancelClosingResponse response = new CancelClosingResponse();
		response.setEsitiAnnullaArchiviazioneEmailList(list);
		// response.setMessages(messages);

		logger.debug("cancelClosing() fine");

		return response;
	}// cancelClosing

	// ASSEGNA EMAIL
	@POST
	@Path("/assignment")
	@Produces({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@Consumes({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@ApiOperation(value = "Assegna una mail (tipicamente ricevuta) ad una UO o utente o scrivania (AssegnaEmail)", notes = "Viene elaborato solo il primo 'assegnatarioEmail' specificato nella richiesta", response = Void.class, tags = "Assegnazione")
	@ApiResponses({ @ApiResponse(code = 422, message = "Operazione interrotta a causa di un errore gestito."),
			@ApiResponse(code = 500, message = "Operazione interrotta a causa di un errore imprevisto.") })
	public void assign(@ApiParam(value = "Oggetto AssignRequest", required = true) AssignRequest entity,
			@ApiParam(value = "Alias dello schema", required = false, hidden = true) @HeaderParam(HEADER_NAME_SCHEMA_SELEZIONATO) String schema) {
		logger.debug("assign() inizio");

		StoreResultBean<DmpkIntMgoEmailAssegnaemailBean> storeResultBean = null;
		try {
			storeResultBean = callHelper.callAssegnaEmailFunc(entity, getSchema(schema));
		} catch (Exception e) {
			logger.error("Errore nella chiamata callAssegnaEmailFunc()", e);
			throw new AurigaMailException(e);
		}

		if (storeResultBean.isInError()) {
			logger.error("L'esecuzione della function 'DMPK_INT_MGO_EMAIL.ASSEGNAEMAIL' si è conclusa con errore:\n"
					+ String.valueOf(storeResultBean.getDefaultMessage()));
			throw new AurigaMailException(422, storeResultBean.getDefaultMessage());
		}

		// La function ASSEGNAEMAIL è stata eseguita con successo
		final DmpkIntMgoEmailAssegnaemailBean result = storeResultBean.getResultBean();

		logger.debug("assign() fine");
	}// assign

	// ANNULLA ASSEGNAZIONE EMAIL
	@POST
	@Path("/assignmentCancellation")
	@Produces({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@Consumes({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@ApiOperation(value = "Annulla l'assegnazione di una mail ad un dato soggetto (UO/utente) (AnnullaAssegnazioneEmail)", notes = "Al momento nessuna nota", response = Void.class, tags = "Assegnazione")
	@ApiResponses({ @ApiResponse(code = 422, message = "Operazione interrotta a causa di un errore gestito."),
			@ApiResponse(code = 500, message = "Operazione interrotta a causa di un errore imprevisto.") })
	public void cancelAssignment(@ApiParam(value = "Oggetto CancelAssignmentRequest", required = true) CancelAssignmentRequest entity,
			@ApiParam(value = "Alias dello schema", required = false, hidden = true) @HeaderParam(HEADER_NAME_SCHEMA_SELEZIONATO) String schema) {
		logger.debug("cancelAssignment() inizio");

		StoreResultBean<DmpkIntMgoEmailAnnullaassegnazioneemailBean> storeResultBean = null;
		try {
			storeResultBean = callHelper.callAnnullaAssegnazioneEmailFunc(entity, getSchema(schema));
		} catch (Exception e) {
			logger.error("Errore nella chiamata callAnnullaAssegnazioneEmailFunc()", e);
			throw new AurigaMailException(e);
		}

		if (storeResultBean.isInError()) {
			logger.error("L'esecuzione della function 'DMPK_INT_MGO_EMAIL.ANNULLAASSEGNAZIONEEMAIL' si è conclusa con errore:\n"
					+ String.valueOf(storeResultBean.getDefaultMessage()));
			throw new AurigaMailException(422, storeResultBean.getDefaultMessage());
		}

		// La function ANNULLAASSEGNAZIONEEMAIL è stata eseguita con successo
		final DmpkIntMgoEmailAnnullaassegnazioneemailBean result = storeResultBean.getResultBean();

		logger.debug("cancelAssignment() fine");
	}// cancelAssignment

	// MESSA IN CARICO EMAILs
	@POST
	@Path("/locking")
	@Produces({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@Consumes({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@ApiOperation(value = "Mette un lock su una o più mail (sia ricevute che inviate) (LockEmail)", notes = "Al momento nessuna nota", response = LockResponse.class, tags = "Locking")
	@ApiResponses({ @ApiResponse(code = 422, message = "Operazione interrotta a causa di un errore gestito."),
			@ApiResponse(code = 500, message = "Operazione interrotta a causa di un errore.") })
	public LockResponse lock(@ApiParam(value = "Oggetto LockRequest", required = true) LockRequest entity,
			@ApiParam(value = "Alias dello schema", required = false, hidden = true) @HeaderParam(HEADER_NAME_SCHEMA_SELEZIONATO) String schema) {
		logger.debug("lock() inizio: " + String.valueOf(entity));

		StoreResultBean<DmpkIntMgoEmailLockemailBean> storeResultBean = null;
		try {
			storeResultBean = callHelper.callLockEmailFunc(entity, getSchema(schema));
		} catch (Exception e) {
			logger.error("Errore nella chiamata callLockEmailFunc()", e);
			throw new AurigaMailException(e);
		}

		if (storeResultBean.isInError()) {
			logger.error("L'esecuzione della function 'DMPK_INT_MGO_EMAIL.LOCKEMAIL' si è conclusa con errore:\n"
					+ String.valueOf(storeResultBean.getDefaultMessage()));
			throw new AurigaMailException(422, storeResultBean.getDefaultMessage());
		}

		// La function LOCKEMAIL è stata eseguita con successo
		final DmpkIntMgoEmailLockemailBean result = storeResultBean.getResultBean();

		final String xml = result.getEsitiout();
		// logger.debug("EsitiOut:\n"+String.valueOf(xml));

		ListaEsitiLockEmail list = null;
		if (StringUtils.isNotBlank(xml)) {
			List<RigaEsitiLockEmail> items = null;
			try {
				items = XmlListaUtility.recuperaLista(xml, RigaEsitiLockEmail.class);
			} catch (Exception e) {
				logger.error("Errore nella chiamata recuperaLista()", e);
				throw new AurigaMailException(e);
			}
			list = new ListaEsitiLockEmail();
			list.setItems(items);
		}

		// final Map<String, String> messages = retrieveMessagesFromResource(result.getEsitiout());

		final LockResponse response = new LockResponse();
		response.setEsitiLockEmailList(list);
		// response.setMessages(messages);

		logger.debug("lock() fine");

		return response;
	}// lock

	// RILASCIO EMAILs
	@POST
	@Path("/unlocking")
	@Produces({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@Consumes({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@ApiOperation(value = "Rimuove un lock su una o più mail (sia ricevute che inviate) (UnlockEmail)", notes = "Al momento nessuna nota", response = UnlockResponse.class, tags = "Locking")
	@ApiResponses({ @ApiResponse(code = 422, message = "Operazione interrotta a causa di un errore gestito."),
			@ApiResponse(code = 500, message = "Operazione interrotta a causa di un errore.") })
	public UnlockResponse unlock(@ApiParam(value = "Oggetto UnlockRequest", required = true) UnlockRequest entity,
			@ApiParam(value = "Alias dello schema", required = false, hidden = true) @HeaderParam(HEADER_NAME_SCHEMA_SELEZIONATO) String schema) {
		logger.debug("unlock() inizio");

		StoreResultBean<DmpkIntMgoEmailUnlockemailBean> storeResultBean = null;
		try {
			storeResultBean = callHelper.callUnlockEmailFunc(entity, getSchema(schema));
		} catch (Exception e) {
			logger.error("Errore nella chiamata callUnlockEmailFunc()", e);
			throw new AurigaMailException(e);
		}

		if (storeResultBean.isInError()) {
			logger.error("L'esecuzione della function 'DMPK_INT_MGO_EMAIL.UNLOCKEMAIL' si è conclusa con errore:\n"
					+ String.valueOf(storeResultBean.getDefaultMessage()));
			throw new AurigaMailException(422, storeResultBean.getDefaultMessage());
		}

		// La function UNLOCKEMAIL è stata eseguita con successo
		final DmpkIntMgoEmailUnlockemailBean result = storeResultBean.getResultBean();

		final String xml = result.getEsitiout();
		// logger.debug("EsitiOut:\n"+String.valueOf(xml));

		ListaEsitiUnlockEmail list = null;
		if (StringUtils.isNotBlank(xml)) {
			List<RigaEsitiUnlockEmail> items = null;
			try {
				items = XmlListaUtility.recuperaLista(xml, RigaEsitiUnlockEmail.class);
			} catch (Exception e) {
				logger.error("Errore nella chiamata recuperaLista()", e);
				throw new AurigaMailException(e);
			}
			list = new ListaEsitiUnlockEmail();
			list.setItems(items);
		}

		// final Map<String, String> messages = retrieveMessagesFromResource(result.getEsitiout());

		final UnlockResponse response = new UnlockResponse();
		response.setEsitiUnlockEmailList(list);
		// response.setMessages(messages);

		logger.debug("unlock() fine");

		return response;
	}// unlock

	@POST
	@Path("/tag")
	@Produces({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@Consumes({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@ApiOperation(value = "Appone tag ed eventuali note su una o più mail (sia ricevute che inviate) (TagEmail)", notes = "Al momento nessuna nota", response = TagResponse.class, tags = "Interazione")
	@ApiResponses({ @ApiResponse(code = 422, message = "Operazione interrotta a causa di un errore gestito."),
			@ApiResponse(code = 500, message = "Operazione interrotta a causa di un errore.") })
	public TagResponse tag(@ApiParam(value = "Oggetto TagRequest", required = true) TagRequest entity,
			@ApiParam(value = "Alias dello schema", required = false, hidden = true) @HeaderParam(HEADER_NAME_SCHEMA_SELEZIONATO) String schema) {
		logger.debug("tag() inizio");

		StoreResultBean<DmpkIntMgoEmailTagemailBean> storeResultBean = null;
		try {
			storeResultBean = callHelper.callTagEmailFunc(entity, getSchema(schema));
		} catch (Exception e) {
			logger.error("Errore nella chiamata callTagEmailFunc()", e);
			throw new AurigaMailException(e);
		}

		if (storeResultBean.isInError()) {
			logger.error("L'esecuzione della function 'DMPK_INT_MGO_EMAIL.TAGEMAIL' si è conclusa con errore:\n"
					+ String.valueOf(storeResultBean.getDefaultMessage()));
			throw new AurigaMailException(422, storeResultBean.getDefaultMessage());
		}

		// La function TAGEMAIL è stata eseguita con successo
		final DmpkIntMgoEmailTagemailBean result = storeResultBean.getResultBean();

		final String xml = result.getEsitiout();
		// logger.debug("EsitiOut:\n"+String.valueOf(xml));

		ListaEsitiTagEmail list = null;
		if (StringUtils.isNotBlank(xml)) {
			List<RigaEsitiTagEmail> items = null;
			try {
				items = XmlListaUtility.recuperaLista(xml, RigaEsitiTagEmail.class);
			} catch (Exception e) {
				logger.error("Errore nella chiamata recuperaLista()", e);
				throw new AurigaMailException(e);
			}
			list = new ListaEsitiTagEmail();
			list.setItems(items);
		}

		final TagResponse response = new TagResponse();
		response.setEsitiTagEmailList(list);

		logger.debug("tag() fine");
		return response;
	}// tag

	// TODO: probabilmente in futuro sarà da collocare in un'altra classe
	@POST
	@Path("/dictionaryValues")
	@Produces({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@Consumes({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@ApiOperation(value = "Ottiene i valori possibili per una data voce di dizionario (TrovaValoriDizionario)", notes = "Al momento nessuna nota", response = DictionaryLookupResponse.class, tags = "Dizionario")
	@ApiResponses({ @ApiResponse(code = 422, message = "Operazione interrotta a causa di un errore gestito."),
			@ApiResponse(code = 500, message = "Operazione interrotta a causa di un errore.") })
	public DictionaryLookupResponse dictionaryLookup(@ApiParam(value = "Oggetto DictionaryLookupRequest", required = true) DictionaryLookupRequest entity,
			@ApiParam(value = "Alias dello schema", required = false, hidden = true) @HeaderParam(HEADER_NAME_SCHEMA_SELEZIONATO) String schema) {
		logger.debug("dictionaryLookup() inizio");

		StoreResultBean<DmpkDizionarioTrovadictvaluesfordictentryBean> storeResultBean = null;
		try {
			storeResultBean = callHelper.callTrovadictvaluesfordictentryFunc(entity, getSchema(schema));
		} catch (Exception e) {
			logger.error("Errore nella chiamata callTrovadictvaluesfordictentryFunc()", e);
			throw new AurigaMailException(e);
		}

		if (storeResultBean.isInError()) {
			logger.error("L'esecuzione della function 'DMPK_DIZIONARIO.TROVADICTVALUESFORDICTENTRY' si è conclusa con errore:\n"
					+ String.valueOf(storeResultBean.getDefaultMessage()));
			throw new AurigaMailException(422, storeResultBean.getDefaultMessage());
		}

		// La function TROVADICTVALUESFORDICTENTRY è stata eseguita con successo
		final DmpkDizionarioTrovadictvaluesfordictentryBean result = storeResultBean.getResultBean();

		final String xml = result.getListaxmlout();
		// logger.debug("ListaXMLOut:\n"+String.valueOf(xml));

		ListaListaXMLTrovaValoriDizionario list = null;
		if (StringUtils.isNotBlank(xml)) {
			List<RigaListaXMLTrovaValoriDizionario> items = null;
			try {
				items = XmlListaUtility.recuperaLista(xml, RigaListaXMLTrovaValoriDizionario.class);
			} catch (Exception e) {
				logger.error("Errore nella chiamata recuperaLista()", e);
				throw new AurigaMailException(e);
			}
			list = new ListaListaXMLTrovaValoriDizionario();
			list.setItems(items);
		}

		final DictionaryLookupResponse response = new DictionaryLookupResponse();
		response.setRequest(entity);
		response.setListaListaXML(list);
		response.setTotalSize(result.getNrototrecout());// NRO_TOT_REC
		if (entity.isFlagPaginazione()) {
			response.setPageSize(result.getNrorecinpaginaout());// NRO_REC_IN_PAGINA
			response.setPageNumber(result.getNropaginaio());// NRO_PAGINA
		} else {
			response.setPageSize(result.getNrototrecout());// NRO_TOT_REC
			response.setPageNumber(1);// NRO_PAGINA
		}

		logger.debug("dictionaryLookup() fine");
		return response;
	}// dictionaryLookup

	@POST
	@Path("/collegaRegToEmail")
	@Produces({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@Consumes({ MediaType.APPLICATION_XML/* ,MediaType.APPLICATION_JSON */ })
	@ApiOperation(value = "Collega una registrazione (in genere di protocollo) ad una mail (CollegaRegToEmail)", notes = "Al momento nessuna nota", response = CollegaRegToEmailRequestResponse.class, tags = "Interazione")
	@ApiResponses({ @ApiResponse(code = 400, message = "Operazione interrotta a causa di input non valido."),
			@ApiResponse(code = 403, message = "Operazione non permessa."),
			@ApiResponse(code = 500, message = "Operazione interrotta a causa di un errore imprevisto.") })
	public CollegaRegToEmailRequestResponse collegaRegToEmail(
			@ApiParam(value = "Oggetto CollegaRegToEmailRequest", required = true) CollegaRegToEmailRequest bean,
			@ApiParam(value = "Alias dello schema", required = false, hidden = true) @HeaderParam(HEADER_NAME_SCHEMA_SELEZIONATO) String schema)
			throws Exception {
		// TODO: impostare meglio gli errori / fare dei controlli nei parametri
		StoreResultBean<DmpkIntMgoEmailCollegaregtoemailBean> storeResultBean = null;
		try {
			storeResultBean = callHelper.callCollegaRegToEmailFunc(bean, getSchema(schema));
		} catch (Exception e) {
			logger.error("Errore nella chiamata callCollegaRegToEmailFunc()", e);
			throw new AurigaMailException(e);
		}

		if (storeResultBean.isInError()) {
			logger.error("L'esecuzione della function 'DMPK_INT_MGO_EMAIL.COLLEGA_REG_TO_EMAIL' si è conclusa con errore:\n"
					+ String.valueOf(storeResultBean.getDefaultMessage()));
			throw new AurigaMailException(422, storeResultBean.getDefaultMessage());
		}

		// La function COLLEGA_REG_TO_EMAIL è stata eseguita con successo
		final DmpkIntMgoEmailCollegaregtoemailBean result = storeResultBean.getResultBean();
		CollegaRegToEmailRequestResponse response = new CollegaRegToEmailRequestResponse();
		if (StringUtils.isNotBlank(result.getErrmsgout())) {
			response.setEsito(result.getErrmsgout());
		} else {
			response.setEsito("Operazione andata a buon fine");
		}
		return response;
	}

	private Map<String, String> retrieveMessagesFromResource(final String xml) {
		try {
			return retrieveMessages(xml);
		} catch (Exception e) {
			logger.error("Errore nella chiamata retrieveMessages()", e);
			throw new AurigaMailException(e);
		}
	}

}// MailSenderResource