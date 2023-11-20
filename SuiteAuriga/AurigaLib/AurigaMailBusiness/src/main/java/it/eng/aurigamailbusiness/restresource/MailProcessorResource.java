/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.sun.jersey.spi.resource.Singleton;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.eng.aurigamailbusiness.bean.EmailAttachsBean;
import it.eng.aurigamailbusiness.bean.EmailBean;
import it.eng.aurigamailbusiness.bean.EmailGroupBean;
import it.eng.aurigamailbusiness.bean.EmailInfoBean;
import it.eng.aurigamailbusiness.bean.EmailInfoRelazioniBean;
import it.eng.aurigamailbusiness.bean.GetStatoProtocollazioneOutBean;
import it.eng.aurigamailbusiness.bean.InfoProtocolloBean;
import it.eng.aurigamailbusiness.bean.InfoRelazioneProtocolloBean;
import it.eng.aurigamailbusiness.bean.InteroperabilitaAttachmentBeanIn;
import it.eng.aurigamailbusiness.bean.InterrogazioneRelazioneEmailBean;
import it.eng.aurigamailbusiness.bean.MailAttachmentsBean;
import it.eng.aurigamailbusiness.bean.MailAttachmentsInfoBean;
import it.eng.aurigamailbusiness.bean.RegistrazioneProtocollo;
import it.eng.aurigamailbusiness.bean.RegistrazioneProtocolloBean;
import it.eng.aurigamailbusiness.bean.SinteticEmailInfoBean;
import it.eng.aurigamailbusiness.bean.StatoConfermaAutomaticaBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.bean.restrepresentation.EmailAttachment;
import it.eng.aurigamailbusiness.bean.restrepresentation.output.GetEmailAttachmentsResponse;

/* http://localhost:8080/AurigaMail/rest/mailProcessor/getHtmlInMainBody */
@Singleton
@Path("/mailProcessor")
@Api(tags={"Contenuto"}, hidden=true)
public class MailProcessorResource extends BaseResource {
	
	public static final String SERVICE_NAME = "MailProcessorService";

	private static final Logger logger = Logger.getLogger(MailProcessorResource.class);

	public MailProcessorResource() throws Exception {
	}

	@POST @Path(Operation.OP_GET_HTML_IN_MAIN_BODY)
	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@ApiOperation(value="Recupera il messaggio come html", notes="xxxxxxxx", response=EmailInfoBean.class)
 	@ApiImplicitParams({
        @ApiImplicitParam(name="emlFile", value="Il file .eml", required=true, dataType="file", paramType="form")
    })
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo."),
    })
	public EmailInfoBean getHtmlInMainBody(@ApiParam(hidden=true) File emlFile) {
		logger.debug("getHtmlInMainBody() inizio: "+emlFile);
		final Response response = invokeService(Operation.OP_GET_HTML_IN_MAIN_BODY, emlFile);
		final EmailInfoBean result = parseResponse(response, EmailInfoBean.class);
		logger.debug("getHtmlInMainBody() fine: "+String.valueOf(result));
		return result;
	}//getHtmlInMainBody
	
 	@POST @Path(Operation.OP_GET_TEXT_IN_MAIN_BODY)
 	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@Consumes(MediaType.MULTIPART_FORM_DATA)
 	@ApiOperation(value="Recupera il messaggio come testo", notes="yyyyyyyy", response=EmailInfoBean.class)
 	@ApiImplicitParams({
        @ApiImplicitParam(name="emlFile", value="Il file .eml", required=true, dataType="file", paramType="form")
    })
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo."),
    })
 	public EmailInfoBean getTextInMainBody(@ApiParam(hidden=true) File emlFile) {
 		logger.debug("getTextInMainBody() inizio: "+emlFile);
 		final Response response = invokeService(Operation.OP_GET_TEXT_IN_MAIN_BODY, emlFile);
 		final EmailInfoBean result = parseResponse(response, EmailInfoBean.class);
 		logger.debug("getTextInMainBody() fine: "+String.valueOf(result));
 		return result;
 	}//getTextInMainBody
 	
 	@POST @Path(Operation.OP_GET_BODY_HTML)
 	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@Consumes(MediaType.MULTIPART_FORM_DATA)
 	@ApiOperation(value="Recupera il corpo del messaggio come html", notes="yyyyyyyy", response=EmailInfoBean.class)
 	@ApiImplicitParams({
        @ApiImplicitParam(name="emlFile", value="Il file .eml", required=true, dataType="file", paramType="form")
    })
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo."),
    })
 	public EmailInfoBean getBodyHtml(@ApiParam(hidden=true) File emlFile) {
 		logger.debug("getBodyHtml() inizio: "+emlFile);
 		final Response response = invokeService(Operation.OP_GET_BODY_HTML, emlFile);
 		final EmailInfoBean result = parseResponse(response, EmailInfoBean.class);
 		logger.debug("getBodyHtml() fine: "+String.valueOf(result));
 		return result;
 	}//getBodyHtml
 	
 	@POST @Path(Operation.OP_GET_BODY_TEXT)
 	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@Consumes(MediaType.MULTIPART_FORM_DATA)
 	@ApiOperation(value="Recupera il corpo del messaggio come testo", notes="yyyyyyyy", response=EmailInfoBean.class)
 	@ApiImplicitParams({
        @ApiImplicitParam(name="emlFile", value="Il file .eml", required=true, dataType="file", paramType="form")
    })
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo."),
    })
 	public EmailInfoBean getBodyText(@ApiParam(hidden=true) File emlFile) throws Exception {
 		logger.debug("getBodyText() inizio: "+emlFile);
 		final Response response = invokeService(Operation.OP_GET_BODY_TEXT, emlFile);
 		final EmailInfoBean result = parseResponse(response, EmailInfoBean.class);
 		logger.debug("getBodyText() fine: "+String.valueOf(result));
 		return result;
 	}//getBodyText
 	
 	@POST @Path(Operation.OP_GET_MAIL_INFO)
 	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@Consumes(MediaType.MULTIPART_FORM_DATA)
 	@ApiOperation(value="Recupera le informazioni del messaggio", notes="yyyyyyyy", response=EmailInfoBean.class)
 	@ApiImplicitParams({
        @ApiImplicitParam(name="emlFile", value="Il file .eml", required=true, dataType="file", paramType="form")
    })
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo."),
    })
 	public EmailInfoBean getMailInfo(@ApiParam(hidden=true) File emlFile) {
 		logger.debug("getMailInfo() inizio: "+emlFile);
 		final Response response = invokeService(Operation.OP_GET_MAIL_INFO, emlFile);
 		final EmailInfoBean result = parseResponse(response, EmailInfoBean.class);
 		logger.debug("getMailInfo() fine: "+String.valueOf(result));
 		return result;
 	}//getMailInfo

 	@POST @Path(Operation.OP_GET_ATTACHMENTS)
 	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@Consumes(MediaType.MULTIPART_FORM_DATA)
 	@ApiOperation(value="Recupera gli allegati del messaggio", notes="yyyyyyyy", response=EmailAttachsBean.class)
 	@ApiImplicitParams({
        @ApiImplicitParam(name="emlFile", value="Il file .eml", required=true, dataType="file", paramType="form")
    })
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo."),
    })
 	public EmailAttachsBean getAttachments(@ApiParam(hidden=true) File emlFile) {
 		logger.debug("getAttachments() inizio: "+String.valueOf(emlFile));
 		final Response response = invokeService(Operation.OP_GET_ATTACHMENTS, emlFile);
 		final EmailAttachsBean result = parseResponse(response, EmailAttachsBean.class);
 		logger.debug("getAttachments() fine.");
 		return result;
 	}//getAttachments
 	
 	@POST @Path(Operation.OP_GET_ATTACHMENTS_WITH_CONTENTS)
 	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@Consumes(MediaType.MULTIPART_FORM_DATA)
 	@ApiOperation(value="Recupera gli allegati del messaggio", notes="yyyyyyyy", response=GetEmailAttachmentsResponse.class)
 	@ApiImplicitParams({
        @ApiImplicitParam(name="emlFile", value="Il file .eml", required=true, dataType="file", paramType="form")
    })
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo."),
    })
 	public GetEmailAttachmentsResponse getAttachmentsWithContents(@ApiParam(hidden=true) File emlFile,
 			@QueryParam("flagGetContent") @DefaultValue("true") boolean flagGetContent) throws Exception {
 		logger.debug("getAttachmentsWithContents() inizio: "+String.valueOf(emlFile));
 		Response response = null;
 		GetEmailAttachmentsResponse result = null;
 		if (flagGetContent) {
 			response = invokeService(Operation.OP_GET_ATTACHMENTS_WITH_CONTENTS, emlFile);
 			result = parseResponse(response, GetEmailAttachmentsResponse.class);
 		} else {
 			response = invokeService(Operation.OP_GET_ATTACHMENTS, emlFile);
 			final EmailAttachsBean tempResult = parseResponse(response, EmailAttachsBean.class);
 			result = new GetEmailAttachmentsResponse();
 			result.setFiles(tempResult.getFiles());
 			List<MailAttachmentsInfoBean> origList = tempResult.getMailAttachments();
 			if (origList != null) {	 
	 			final int origSize = origList.size();
	 			final List<EmailAttachment> mailAttachments = new ArrayList<EmailAttachment>(origSize);
	 			result.setMailAttachments(mailAttachments);
	 			for (int i = 0; i < origSize; i++) {
	 				final MailAttachmentsInfoBean orig = origList.get(i);
	 				final EmailAttachment dest = new EmailAttachment();
	 				mailAttachments.add(dest);
	 			    BeanUtils.copyProperties(dest, orig);
	 			}//for
 			}
 		}
 		logger.debug("getAttachmentsWithContents() fine.");
 		return result;
 	}//getAttachmentsWithContents
 	
 	@POST @Path(Operation.OP_GET_POSTA_CERT)
 	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@Consumes(MediaType.MULTIPART_FORM_DATA)
 	@ApiOperation(value="Recupera la posta certificata del messaggio", notes="yyyyyyyy", response=MailAttachmentsBean.class)
 	@ApiImplicitParams({
        @ApiImplicitParam(name="emlFile", value="Il file .eml", required=true, dataType="file", paramType="form")
    })
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo."),
		@ApiResponse(code=404, message="Informazione non trovata.")
    })
 	public MailAttachmentsBean getPostacert(@ApiParam(hidden=true) File emlFile) {
 		logger.debug("getPostacert() inizio: "+emlFile);
 		final Response response = invokeService(Operation.OP_GET_POSTA_CERT, emlFile);
 		final MailAttachmentsBean result = parseResponse(response, MailAttachmentsBean.class);
 		logger.debug("getPostacert() fine: "+String.valueOf(result));
 		return result;
 	}//getPostacert

 	@POST @Path(Operation.OP_GET_SINTETIC_MAIL)
 	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@Consumes(MediaType.MULTIPART_FORM_DATA)
 	@ApiOperation(value="Recupera il messaggio in modalità semplificata", notes="yyyyyyyy", response=SinteticEmailInfoBean.class)
 	@ApiImplicitParams({
        @ApiImplicitParam(name="emlFile", value="Il file .eml", required=true, dataType="file", paramType="form")
    })
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo.")
    })
 	public SinteticEmailInfoBean getSinteticMail(@ApiParam(hidden=true) File emlFile) {
 		logger.debug("getSinteticMail() inizio: "+emlFile);
 		final Response response = invokeService(Operation.OP_GET_SINTETIC_MAIL, emlFile);
 		final SinteticEmailInfoBean result = parseResponse(response, SinteticEmailInfoBean.class);
 		logger.debug("getSinteticMail() fine: "+String.valueOf(result));
 		return result;
 	}//getSinteticMail
 	
 	//==================================================================================================================================
 	@POST @Path(Operation.OP_GET_MAIL_PROTOCOLLATE)
 	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@Consumes({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@ApiOperation(value="Recupera le mail protocollate", notes="yyyyyyyy", response=EmailGroupBean.class)
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo.")
    })
 	public EmailGroupBean getMailProtocollate(@ApiParam(value="Oggetto RegistrazioneProtocollo", required=true) RegistrazioneProtocollo bean) {
 		logger.debug("getMailProtocollate() inizio: "+bean);
 		final Response response = invokeService(Operation.OP_GET_MAIL_PROTOCOLLATE, bean);
 		final EmailGroupBean result = parseResponse(response, EmailGroupBean.class);
 		logger.debug("getMailProtocollate() fine: "+String.valueOf(result));
 		return result;
 	}//getMailProtocollate
 	
 	@POST @Path(Operation.OP_GET_ATTACHMENT_INTEROPERABILITA)
 	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@Consumes({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@ApiOperation(value="Recupera ...", notes="yyyyyyyy", response=MailAttachmentsBean.class)
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo.")
    })
 	public MailAttachmentsBean getAttachmentInteroperabilita(@ApiParam(value="Oggetto InteroperabilitaAttachmentBeanIn", required=true) InteroperabilitaAttachmentBeanIn bean) {
 		logger.debug("getAttachmentInteroperabilita() inizio: "+bean);
 		final Response response = invokeService(Operation.OP_GET_ATTACHMENT_INTEROPERABILITA, bean);
 		final MailAttachmentsBean result = parseResponse(response, MailAttachmentsBean.class);
 		logger.debug("getAttachmentInteroperabilita() fine: "+String.valueOf(result));
 		return result;
 	}//getAttachmentInteroperabilita
 	
 	@POST @Path(Operation.OP_GET_STATO_PROTOCOLLAZIONE)
 	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@Consumes({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@ApiOperation(value="Recupera lo stato di protocollazione", notes="yyyyyyyy", response=GetStatoProtocollazioneOutBean.class)
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo.")
    })
 	public GetStatoProtocollazioneOutBean getStatoProtocollazione(@ApiParam(value="Oggetto InfoProtocolloBean", required=true) InfoProtocolloBean bean) {
 		logger.debug("getStatoProtocollazione() inizio: "+bean);
 		final Response response = invokeService(Operation.OP_GET_STATO_PROTOCOLLAZIONE, bean);
 		final GetStatoProtocollazioneOutBean result = parseResponse(response, GetStatoProtocollazioneOutBean.class);
 		logger.debug("getStatoProtocollazione() fine: "+String.valueOf(result));
 		return result;
 	}//getStatoProtocollazione
 	
 	@POST @Path(Operation.OP_GET_RELAZIONI_MAIL)
 	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@Consumes({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@ApiOperation(value="Recupera ...", notes="yyyyyyyy", response=EmailInfoRelazioniBean.class)
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo.")
    })
 	public EmailInfoRelazioniBean getRelazioniMail(@ApiParam(value="Oggetto InterrogazioneRelazioneEmailBean", required=true) InterrogazioneRelazioneEmailBean bean) {
 		logger.debug("getRelazioniMail() inizio: "+bean);
 		final Response response = invokeService(Operation.OP_GET_RELAZIONI_MAIL, bean);
 		final EmailInfoRelazioniBean result = parseResponse(response, EmailInfoRelazioniBean.class);
 		logger.debug("getRelazioniMail() fine: "+String.valueOf(result));
 		return result;
 	}//getRelazioniMail

 	@POST @Path(Operation.OP_UPDATE_MAIL_PROTOCOLLATA)
 	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@Consumes({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@ApiOperation(value="Aggiorna la mail protocollata", notes="yyyyyyyy", response=TEmailMgoBean.class, hidden=true)
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo.")
    })
 	public TEmailMgoBean updateMailProtocollata(@ApiParam(value="Oggetto InfoProtocolloBean", required=true) InfoProtocolloBean bean) {
 		logger.debug("updateMailProtocollata() inizio: "+bean);
 		final Response response = invokeService(Operation.OP_UPDATE_MAIL_PROTOCOLLATA, bean);
 		final TEmailMgoBean result = parseResponse(response, TEmailMgoBean.class);
 		logger.debug("updateMailProtocollata() fine: "+String.valueOf(result));
 		return result;
 	}//updateMailProtocollata
 	
 	@POST @Path(Operation.OP_ELIMINA_PROTOCOLLAZIONE_MAIL)
 	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@Consumes({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@ApiOperation(value="Elimina la protocollazione", notes="yyyyyyyy", response=TEmailMgoBean.class, hidden=true)
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo.")
    })
 	public TEmailMgoBean eliminaProtocollazioneMail(@ApiParam(value="Oggetto RegistrazioneProtocolloBean", required=true) RegistrazioneProtocolloBean bean) {
 		logger.debug("eliminaProtocollazioneMail() inizio: "+bean);
 		final Response response = invokeService(Operation.OP_ELIMINA_PROTOCOLLAZIONE_MAIL, bean);
 		final TEmailMgoBean result = parseResponse(response, TEmailMgoBean.class);
 		logger.debug("eliminaProtocollazioneMail() fine: "+String.valueOf(result));
 		return result;
 	}//eliminaProtocollazioneMail
 	
 	@POST @Path(Operation.OP_ESEGUI_ARCHIVIAZIONE)
 	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@Consumes({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@ApiOperation(value="Esegue l'archiviazione", notes="yyyyyyyy", response=TEmailMgoBean.class, hidden=true)
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo.")
    })
 	public TEmailMgoBean eseguiArchiviazione(@ApiParam(value="Oggetto TEmailMgoBean", required=true) TEmailMgoBean bean) {
 		logger.debug("eseguiArchiviazione() inizio: "+bean);
 		final Response response = invokeService(Operation.OP_ESEGUI_ARCHIVIAZIONE, bean);
 		final TEmailMgoBean result = parseResponse(response, TEmailMgoBean.class);
 		logger.debug("eseguiArchiviazione() fine: "+String.valueOf(result));
 		return result;
 	}//eseguiArchiviazione
 	
 	@POST @Path(Operation.OP_SAVE)
 	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@Consumes({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@ApiOperation(value="Salva", notes="yyyyyyyy", response=EmailBean.class, hidden=true)
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo.")
    })
 	public EmailBean save(@ApiParam(value="Oggetto EmailBean", required=true) EmailBean bean) {
 		logger.debug("save() inizio: "+bean);
 		final Response response = invokeService(Operation.OP_SAVE, bean);
 		final EmailBean result = parseResponse(response, EmailBean.class);
 		logger.debug("save() fine: "+String.valueOf(result));
 		return result;
 	}//save
 	
 	@POST @Path(Operation.OP_MOVE_MAIL)
 	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@Consumes({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@ApiOperation(value="Sposta l'email", notes="yyyyyyyy", response=EmailGroupBean.class, hidden=true)
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo.")
    })
 	public EmailGroupBean moveMail(@ApiParam(value="Oggetto EmailGroupBean", required=true) EmailGroupBean bean) throws Exception {
 		logger.debug("moveMail() inizio: "+bean);
 		final Response response = invokeService(Operation.OP_MOVE_MAIL, bean);
 		final EmailGroupBean result = parseResponse(response, EmailGroupBean.class);
 		logger.debug("moveMail() fine: "+String.valueOf(result));
 		return result;
 	}//moveMail
 	
 	@POST @Path(Operation.OP_CREA_RELAZIONE_PROTOCOLLO)
 	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@Consumes({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@ApiOperation(value="Crea ...", notes="yyyyyyyy", response=InfoRelazioneProtocolloBean.class, hidden=true)
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo.")
    })
 	public InfoRelazioneProtocolloBean creaRelazioneProtocollo(@ApiParam(value="Oggetto RegistrazioneProtocolloBean", required=true) RegistrazioneProtocolloBean bean) throws Exception {
 		logger.debug("creaRelazioneProtocollo() inizio: "+bean);
 		final Response response = invokeService(Operation.OP_CREA_RELAZIONE_PROTOCOLLO, bean);
 		final InfoRelazioneProtocolloBean result = parseResponse(response, InfoRelazioneProtocolloBean.class);
 		logger.debug("creaRelazioneProtocollo() fine: "+String.valueOf(result));
 		return result;
 	}//creaRelazioneProtocollo
 	
 	@POST @Path(Operation.OP_INVIA_CONFERMA_AUTOMATICA)
 	@Produces({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@Consumes({MediaType.APPLICATION_XML/*, MediaType.APPLICATION_JSON*/})
 	@ApiOperation(value="Crea ...", notes="yyyyyyyy", response=StatoConfermaAutomaticaBean.class, hidden=true)
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo.")
    })
 	public StatoConfermaAutomaticaBean inviaConfermaAutomatica(@ApiParam(value="Oggetto RegistrazioneProtocolloBean", required=true) RegistrazioneProtocolloBean bean) throws Exception {
 		logger.debug("inviaConfermaAutomatica() inizio: "+bean);
 		final Response response = invokeService(Operation.OP_INVIA_CONFERMA_AUTOMATICA, bean);
 		final StatoConfermaAutomaticaBean result = parseResponse(response, StatoConfermaAutomaticaBean.class);
 		logger.debug("inviaConfermaAutomatica() fine: "+String.valueOf(result));
 		return result;
 	}//inviaConfermaAutomatica


 	
	@Override
	protected String getServiceName() {
		return SERVICE_NAME;
	}

}//MailProcessorResource