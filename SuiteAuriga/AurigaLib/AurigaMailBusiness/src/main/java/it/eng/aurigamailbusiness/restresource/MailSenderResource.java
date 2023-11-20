/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.common.io.Files;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.spi.resource.Singleton;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailCtrlutenzaabilitatainvioBean;
import it.eng.aurigamailbusiness.bean.AnonymousSenderBean;
import it.eng.aurigamailbusiness.bean.DraftAndWorkItemsSavedBean;
import it.eng.aurigamailbusiness.bean.EmailSentReferenceBean;
import it.eng.aurigamailbusiness.bean.NotificaInteroperabileBean;
import it.eng.aurigamailbusiness.bean.SenderAttachmentsBean;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.aurigamailbusiness.bean.SenderMailProtocollataBean;
import it.eng.aurigamailbusiness.bean.restrepresentation.MailLoginDraftAndWorkItemsBean;
import it.eng.aurigamailbusiness.bean.restrepresentation.MailLoginSenderBean;
import it.eng.aurigamailbusiness.bean.restrepresentation.input.SendAndSaveRequest;

/* http://localhost:8080/AurigaMail/rest/mailSender/sendAndSaveInteropNotifica */
@Singleton
@Path("/mailSender")
@Api(tags={"Spedizione"}, hidden=true)
public class MailSenderResource extends BaseResource {
	
	public static final String SERVICE_NAME = "MailSenderService";

	private static final Logger logger = Logger.getLogger(MailSenderResource.class);
	
	public MailSenderResource() throws Exception {
	}
	
  	@POST @Path(Operation.OP_RE_SEND)
  	@Produces({MediaType.APPLICATION_XML/*MediaType.APPLICATION_JSON*/})
  	@Consumes({MediaType.APPLICATION_XML/*MediaType.APPLICATION_JSON*/})
  	@ApiOperation(value="Spedisce ...", notes="xxxxxxxx", response=EmailSentReferenceBean.class)
  	@ApiResponses({
  		@ApiResponse(code=200, message="Operazione conclusa con successo."),
      })
  	public EmailSentReferenceBean reSend(@ApiParam(value="Oggetto SenderBean", required=true) SenderBean bean) throws Exception {
  		logger.debug("reSend() inizio");
  		//FIXME
  		final Response response = invokeService("token", Operation.OP_RE_SEND, bean);
  		final EmailSentReferenceBean result = parseResponse(response, EmailSentReferenceBean.class);
  		logger.debug("reSend() fine: "+String.valueOf(result));
  		return result;
  	}//reSend

	@POST @Path(Operation.OP_SAVE_AND_SEND_DRAFT)
	@Produces({MediaType.APPLICATION_XML/*,MediaType.APPLICATION_JSON*/})
	@Consumes({MediaType.APPLICATION_XML/*,MediaType.APPLICATION_JSON*/})
	@ApiOperation(value="Spedisce ...", notes="xxxxxxxx", response=EmailSentReferenceBean.class)
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo."),
    })
	public EmailSentReferenceBean saveAndSendDraft(
		        @ApiParam(name="input", value="Oggetto MailLoginDraftAndWorkItemsBean", required=true) MailLoginDraftAndWorkItemsBean bean,
		        @ApiParam(value="Modalità di salvataggio") @DefaultValue("0") @QueryParam("savemode") Integer savemode,
		        @ApiParam(value="ID utente modifica PEC", required=true) @QueryParam("idUtenteModPec") String idUtenteModPec
		    ) throws Exception {
		logger.debug("saveAndSendDraft() inizio: "+bean);
		//FIXME
		final Response response = invokeService("token", Operation.OP_SAVE_AND_SEND_DRAFT, bean.getDraftAndWorkItemsBean(), bean.getMailLoginBean(), idUtenteModPec, savemode);
		final EmailSentReferenceBean result = parseResponse(response, EmailSentReferenceBean.class);
		logger.debug("saveAndSendDraft() fine: "+String.valueOf(result));
		return result;
	}//saveAndSendDraft
	
	@POST @Path(Operation.OP_SAVE_DRAFT_AND_WORK_ITEMS)
	@Produces({MediaType.APPLICATION_XML/*,MediaType.APPLICATION_JSON*/})
	@Consumes({MediaType.APPLICATION_XML/*,MediaType.APPLICATION_JSON*/})
	@ApiOperation(value="Spedisce ...", notes="xxxxxxxx", response=EmailSentReferenceBean.class)
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo."),
    })
	public DraftAndWorkItemsSavedBean saveDraftAndWorkItems(
			    @ApiParam(name="input", value="Oggetto MailLoginDraftAndWorkItemsBean", required=true) MailLoginDraftAndWorkItemsBean bean,
			    @ApiParam(value="Modalità di salvataggio") @DefaultValue("0") @QueryParam("savemode") Integer savemode
			) throws Exception {
		logger.debug("saveDraftAndWorkItems() inizio: "+bean);
		//FIXME
		final Response response = invokeService("token", Operation.OP_SAVE_DRAFT_AND_WORK_ITEMS, bean.getDraftAndWorkItemsBean(), savemode, bean.getMailLoginBean());
		final DraftAndWorkItemsSavedBean result = parseResponse(response, DraftAndWorkItemsSavedBean.class);
		logger.debug("saveDraftAndWorkItems() fine: "+String.valueOf(result));
		return result;
	}//saveDraftAndWorkItems
	
	@POST @Path(Operation.OP_SEND_AND_SAVE_INTEROP_NOTIFICA)
	@Produces({MediaType.APPLICATION_XML/*,MediaType.APPLICATION_JSON*/})
	@Consumes({MediaType.APPLICATION_XML/*,MediaType.APPLICATION_JSON*/})
	@ApiOperation(value="Spedisce ...", notes="xxxxxxxx", response=EmailSentReferenceBean.class)
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo."),
    })
	public EmailSentReferenceBean sendAndSaveInteropNotifica(@ApiParam(value="Oggetto NotificaInteroperabileBean", required=true) NotificaInteroperabileBean bean) throws Exception {
		logger.debug("sendAndSaveInteropNotifica() inizio: "+bean);
		//FIXME
		final Response response = invokeService("token", Operation.OP_SEND_AND_SAVE_INTEROP_NOTIFICA, bean);
		final EmailSentReferenceBean result = parseResponse(response, EmailSentReferenceBean.class);
		logger.debug("sendAndSaveInteropNotifica() fine: "+String.valueOf(result));
		return result;
	}//sendAndSaveInteropNotifica
	
 	@POST @Path(Operation.OP_SEND_AND_DELETE_AFTER_SEND)
 	@Produces({MediaType.APPLICATION_XML/*,MediaType.APPLICATION_JSON*/})
 	@Consumes({MediaType.APPLICATION_XML/*,MediaType.APPLICATION_JSON*/})
 	@ApiOperation(value="Spedisce ...", notes="xxxxxxxx", response=EmailSentReferenceBean.class)
 	@ApiResponses({
 		@ApiResponse(code=200, message="Operazione conclusa con successo."),
     })
 	public EmailSentReferenceBean sendAndDeleteAfterSend(@ApiParam(value="Oggetto SenderBean", required=true) SenderBean bean) throws Exception {
 		logger.debug("sendAndDeleteAfterSend() inizio");
 		//FIXME
 		final Response response = invokeService("token", Operation.OP_SEND_AND_DELETE_AFTER_SEND, bean);
 		final EmailSentReferenceBean result = parseResponse(response, EmailSentReferenceBean.class);
 		logger.debug("sendAndDeleteAfterSend() fine: "+String.valueOf(result));
 		return result;
 	}//sendAndDeleteAfterSend

	@POST @Path(Operation.OP_SEND_AND_SAVE_WORK_ITEMS)
	@Produces({MediaType.APPLICATION_XML/*,MediaType.APPLICATION_JSON*/})
	@Consumes({MediaType.APPLICATION_XML/*,MediaType.APPLICATION_JSON*/})
	@ApiOperation(value="Spedisce ...", notes="xxxxxxxx", response=EmailSentReferenceBean.class)
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo."),
    })
	public EmailSentReferenceBean sendAndSaveWorkItems(@ApiParam(name="input", value="Oggetto MailLoginSenderBean", required=true) MailLoginSenderBean bean) throws Exception {
		logger.debug("sendAndSaveWorkItems() inizio");
		//FIXME
		final Response response = invokeService("token", Operation.OP_SEND_AND_SAVE_WORK_ITEMS, bean.getSenderBean(), bean.getMailLoginBean());
		final EmailSentReferenceBean result = parseResponse(response, EmailSentReferenceBean.class);
		logger.debug("sendAndSaveWorkItems() fine: "+String.valueOf(result));
		return result;
	}//sendAndSaveWorkItems
	
	@POST @Path(Operation.OP_SEND_AND_SAVE_MAIL_PROTOCOLLATA)
	@Produces({MediaType.APPLICATION_XML/*,MediaType.APPLICATION_JSON*/})
	@Consumes({MediaType.APPLICATION_XML/*,MediaType.APPLICATION_JSON*/})
	@ApiOperation(value="Spedisce ...", notes="xxxxxxxx", response=EmailSentReferenceBean.class)
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo."),
    })
	public EmailSentReferenceBean sendAndSaveMailProtocollata(@ApiParam(value="Oggetto SenderMailProtocollataBean", required=true) SenderMailProtocollataBean bean) throws Exception {
		logger.debug("sendAndSaveMailProtocollata() inizio: "+bean);
		//TODO
		final Response response = null; //invokeService(bean.getMail().getMailLoginBean(), OP_SEND_AND_SAVE_MAIL_PROTOCOLLATA, bean);
		final EmailSentReferenceBean result = parseResponse(response, EmailSentReferenceBean.class);
		logger.debug("sendAndSaveMailProtocollata() fine: "+String.valueOf(result));
		return result;
	}//sendAndSaveMailProtocollata
	
		
//	@POST @Path(Operation.OP_SEND_AND_SAVE)
//	@Produces({MediaType.APPLICATION_XML/*,MediaType.APPLICATION_JSON*/})
//	@Consumes({MediaType.APPLICATION_XML/*,MediaType.APPLICATION_JSON*/})
//	@ApiResponses({
//		@ApiResponse(code=200, message="Operazione conclusa con successo."),
//		@ApiResponse(code=403, message="Operazione non permessa."),
//		@ApiResponse(code=500, message="Operazione interrotta a causa di un errore.")
//    })
	public EmailSentReferenceBean sendAndSave(/*@ApiParam(value="Oggetto SenderBean", required=true)*/ SenderBean bean) throws Exception {
		EmailSentReferenceBean result = null;
		logger.debug("sendAndSave() inizio");
		
		final Response response = invokeService(Operation.OP_SEND_AND_SAVE, bean);
		result = parseResponse(response, EmailSentReferenceBean.class);
		
		logger.debug("sendAndSave() fine");
		
		return result;
	}//sendAndSave
	
//==========================================================================================================================================================================================

	@POST @Path(Operation.OP_SEND)
	@Produces({MediaType.APPLICATION_XML/*,MediaType.APPLICATION_JSON*/})
	@Consumes({MediaType.APPLICATION_XML/*,MediaType.APPLICATION_JSON*/})
	@ApiOperation(value="Spedisce ...", notes="xxxxxxxx", response=EmailSentReferenceBean.class)
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo."),
    })
	public EmailSentReferenceBean send(@ApiParam(value="Oggetto SenderBean", required=true) SenderBean bean) throws Exception {
		logger.debug("send() inizio: "+bean);
		//FIXME
		final Response response = invokeService("token", Operation.OP_SEND, bean);
		final EmailSentReferenceBean result = parseResponse(response, EmailSentReferenceBean.class);
		logger.debug("send() fine: "+String.valueOf(result));
		return result;
	}//send
	
//	@POST @Path(OP_SEND_ANONYMOUS)
//	@Produces({MediaType.APPLICATION_XML/*,MediaType.APPLICATION_JSON*/})
//	@Consumes({MediaType.APPLICATION_XML/*,MediaType.APPLICATION_JSON*/})
//	@ApiOperation(value="Spedisce in anonimo ...", notes="xxxxxxxx", response=EmailSentReferenceBean.class)
//	@ApiResponses({
//		@ApiResponse(code=200, message="Operazione conclusa con successo."),
//    })
	public EmailSentReferenceBean sendAnonymous(@ApiParam(value="Oggetto AnonymousSenderBean", required=true) AnonymousSenderBean bean) throws Exception {
		logger.debug("sendAnonymous() inizio: "+bean);
		//FIXME
		final Response response = invokeService("token", Operation.OP_SEND_ANONYMOUS, bean);
		final EmailSentReferenceBean result = parseResponse(response, EmailSentReferenceBean.class);
		logger.debug("sendAnonymous() fine: "+String.valueOf(result));
		return result;
	}//sendAnonymous
	
	@POST @Path(Operation.OP_SEND_AND_SAVE_MAIL_FILE_IN_STORAGE)
	@Produces({MediaType.APPLICATION_XML/*,MediaType.APPLICATION_JSON*/})
	@Consumes({MediaType.APPLICATION_XML/*,MediaType.APPLICATION_JSON*/})
	@ApiOperation(value="Spedisce e salva email nello storage", notes="xxxxxxxx", response=EmailSentReferenceBean.class)
	@ApiResponses({
		@ApiResponse(code=200, message="Operazione conclusa con successo."),
    })
	public EmailSentReferenceBean sendAndSaveMailFileInStorage(
			     @ApiParam(value="Oggetto SenderBean", required=true) SenderBean bean, 
			     @ApiParam(value="Flag") @DefaultValue("false") @QueryParam("flag") Boolean flag
			                      ) throws Exception {
		logger.debug("sendAndSaveMailFileInStorage() inizio: "+bean);
		//FIXME
		final Response response = invokeService("token", Operation.OP_SEND_AND_SAVE_MAIL_FILE_IN_STORAGE, bean, flag);
		final EmailSentReferenceBean result = parseResponse(response, EmailSentReferenceBean.class);
		logger.debug("sendAndSaveMailFileInStorage() fine: "+String.valueOf(result));
		return result;
	}//sendAndSaveMailFileInStorage


 	
	@Override
	protected String getServiceName() {
		return SERVICE_NAME;
	}

}//MailSenderResource