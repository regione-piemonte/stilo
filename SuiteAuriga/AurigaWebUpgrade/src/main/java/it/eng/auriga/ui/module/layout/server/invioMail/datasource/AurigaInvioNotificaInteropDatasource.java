/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.config.VersioneConfig;
import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailPreparainvionotificaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.AttachmentBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.InvioMailResultBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.InvioNotificaInteropBean;
import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.InvioNotificaInteropCache;
import it.eng.auriga.ui.module.layout.server.postaElettronica.LockUnlockMail;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaBean;
import it.eng.aurigamailbusiness.bean.EmailAttachsBean;
import it.eng.aurigamailbusiness.bean.EmailSentReferenceBean;
import it.eng.aurigamailbusiness.bean.NotificaInteroperabileBean;
import it.eng.aurigamailbusiness.bean.ResultBean;
import it.eng.aurigamailbusiness.bean.RispostaInoltro;
import it.eng.aurigamailbusiness.bean.RispostaInoltroBean;
import it.eng.aurigamailbusiness.bean.SenderAttachmentsBean;
import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.aurigamailbusiness.bean.TEmailMgoBean;
import it.eng.aurigamailbusiness.bean.TRegProtVsEmailBean;
import it.eng.aurigamailbusiness.bean.TipoInteroperabilita;
import it.eng.client.AurigaMailService;
import it.eng.client.DmpkIntMgoEmailPreparainvionotifica;
import it.eng.client.MailProcessorService;
import it.eng.core.business.TFilterFetch;
import it.eng.document.function.bean.Flag;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.servlet.fileExtractor.FileToExtractBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilityDeserializer;

@Datasource(id = "AurigaInvioNotificaInteropDatasource")
public class AurigaInvioNotificaInteropDatasource extends AbstractServiceDataSource<PostaElettronicaBean, InvioNotificaInteropBean> {
	
	private static Logger mLogger = Logger.getLogger(AurigaInvioNotificaInteropDatasource.class);
	private static String invioSeparatoMessage = "L’opzione di invio con mail separate è consentita solo se non ci sono destinatari in cc";

	@Override
	public InvioNotificaInteropBean call(PostaElettronicaBean pPostaElettronicaBean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		VersioneConfig lVersioneConfig = (VersioneConfig)SpringAppContext.getContext().getBean("VersioneConfig");
		
		TEmailMgoBean lTEmailMgoBean = AurigaMailService.getDaoTEmailMgo().get(getLocale(), pPostaElettronicaBean.getIdEmail());
		
		DmpkIntMgoEmailPreparainvionotifica store = new DmpkIntMgoEmailPreparainvionotifica();
		
		DmpkIntMgoEmailPreparainvionotificaBean input = new DmpkIntMgoEmailPreparainvionotificaBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);		
		input.setIdemailin(pPostaElettronicaBean.getIdEmail());
		input.setTiponotificain(getExtraparams().get("tipoNotifica"));		
		input.setFlgnoattachxmlin(0);
		input.setVersionedtdin(lVersioneConfig.getVersione());		
		input.setIdudin(null);
		
		//Segnatura
		if (lTEmailMgoBean.getCategoria().equals("INTEROP_SEGN")){
			MailProcessorService lMailProcessorService = new MailProcessorService();
			EmailAttachsBean lEmailAttachsBean = lMailProcessorService.getattachmentsbyidemail(getLocale(), pPostaElettronicaBean.getIdEmail());
			String segnaturaXml = null;
			if (lEmailAttachsBean.getMailAttachments() != null) {
				int count = 0;
				for (File lFile : lEmailAttachsBean.getFiles()) {
					if (lEmailAttachsBean.getMailAttachments().get(count).getFilename().equalsIgnoreCase("segnatura.xml")) {
						segnaturaXml = IOUtils.toString(new FileInputStream(lFile));
					}
					count++;
				}
			}	
			input.setXmlsegnaturain(segnaturaXml);			
		}
		
		StoreResultBean<DmpkIntMgoEmailPreparainvionotificaBean> result = store.execute(getLocale(), loginBean, input);
		if (result.isInError()){
			throw new StoreException(result);
		}

		DmpkIntMgoEmailPreparainvionotificaBean output = result.getResultBean();
		
		XmlUtilityDeserializer lXmlUtilityDeserializer = new XmlUtilityDeserializer();
		InvioNotificaInteropCache beanxml = lXmlUtilityDeserializer.unbindXml(output.getXmldatiinviomailout(), InvioNotificaInteropCache.class);
		
		InvioNotificaInteropBean lInvioNotificaInteropBean = new InvioNotificaInteropBean();
		BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lInvioNotificaInteropBean, beanxml);
		lInvioNotificaInteropBean.setSalvaInviati(beanxml.getFlagSalvaInviati().equals(Flag.SETTED));
		
		if(beanxml.getBodyFormat() != null && beanxml.getBodyFormat().equals("text/html")) {
			lInvioNotificaInteropBean.setTextHtml("html");
			lInvioNotificaInteropBean.setBodyHtml(beanxml.getBody());
		} else {
			lInvioNotificaInteropBean.setTextHtml("text");
			lInvioNotificaInteropBean.setBodyText(beanxml.getBody());
		}
		
		//Ho un attachment
		if (StringUtils.isNotEmpty(output.getNomexmlattachout())){
			AttachmentBean lAttachmentBean = new AttachmentBean();
			lAttachmentBean.setFileNameAttach(output.getNomexmlattachout());
			String uri = StorageImplementation.getStorage().storeStream(IOUtils.toInputStream(output.getXmlattachout()));
			lAttachmentBean.setUriAttach(uri);
			List<AttachmentBean> lList = new ArrayList<AttachmentBean>();
			lList.add(lAttachmentBean);
			lInvioNotificaInteropBean.setAttach(lList);
		}
		
		lInvioNotificaInteropBean.setIdEmail(pPostaElettronicaBean.getIdEmail());
		lInvioNotificaInteropBean.setTipoNotifica(getExtraparams().get("tipoNotifica"));
		lInvioNotificaInteropBean.setCategoriaPartenza(lTEmailMgoBean.getCategoria());
		return lInvioNotificaInteropBean;
 
	}

	public InvioMailResultBean invioMail(InvioNotificaInteropBean pInvioNotificaInteropBean) throws Exception{
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		SenderBean bean = new SenderBean(); 
		bean.setIdUtenteModPec(lAurigaLoginBean.getSpecializzazioneBean().getIdUtenteModPec());
		bean.setFlgInvioSeparato(pInvioNotificaInteropBean.getFlgInvioSeparato() != null && 
				pInvioNotificaInteropBean.getFlgInvioSeparato() ? true : false);
		bean.setAccount(pInvioNotificaInteropBean.getMittente());
		List<String> destinatari = new ArrayList<String>();
		String[] lStringDestinatari = pInvioNotificaInteropBean.getDestinatari().split(";");
		destinatari = Arrays.asList(lStringDestinatari);
		bean.setAddressTo(destinatari);
		if (StringUtils.isNotEmpty(pInvioNotificaInteropBean.getDestinatariCC())){
			List<String> destinatariCC = new ArrayList<String>();
			String[] lStringDestinatariCC = pInvioNotificaInteropBean.getDestinatariCC().split(";");
			destinatariCC = Arrays.asList(lStringDestinatariCC);
			bean.setAddressCc(destinatariCC);
		}
		bean.setAddressFrom(pInvioNotificaInteropBean.getMittente());
		List<SenderAttachmentsBean> lista = new ArrayList<SenderAttachmentsBean>();		
		bean.setSubject(pInvioNotificaInteropBean.getOggetto());		
		if (pInvioNotificaInteropBean.getTextHtml().equals("text")){
			bean.setBody(pInvioNotificaInteropBean.getBodyText());
			bean.setIsHtml(false);
		} else {
			bean.setBody("<html>" + pInvioNotificaInteropBean.getBodyHtml() + "</html>");
			bean.setIsHtml(true);
		}
		TEmailMgoBean lTEmailMgoBean = AurigaMailService.getDaoTEmailMgo().get(getLocale(), pInvioNotificaInteropBean.getIdEmail());
		EmailSentReferenceBean lEmailSentReferenceBean = null;
		if (lTEmailMgoBean.getCategoria().equals("INTEROP_SEGN")) {
			NotificaInteroperabileBean lNotificaInteroperabileBean = new NotificaInteroperabileBean();
			lNotificaInteroperabileBean.setMailPartenza(lTEmailMgoBean);
			lNotificaInteroperabileBean.setSenderBean(bean);
			if (pInvioNotificaInteropBean.getTipoNotifica().equals("conferma"))
				lNotificaInteroperabileBean.setTipoNotifica(TipoInteroperabilita.CONFERMA_RICEZIONE);
			else if (pInvioNotificaInteropBean.getTipoNotifica().equals("eccezione"))
				lNotificaInteroperabileBean.setTipoNotifica(TipoInteroperabilita.NOTIFICA_ECCEZIONE);
			else if (pInvioNotificaInteropBean.getTipoNotifica().equals("aggiornamento"))
				lNotificaInteroperabileBean.setTipoNotifica(TipoInteroperabilita.AGGIORNAMENTO_CONFERMA);
			else if (pInvioNotificaInteropBean.getTipoNotifica().equals("annullamento"))
				lNotificaInteroperabileBean.setTipoNotifica(TipoInteroperabilita.ANNULLAMENTO_PROTOCOLLAZIONE);
			String xmlToSend = null;
			if (pInvioNotificaInteropBean.getAttach()!=null  && pInvioNotificaInteropBean.getAttach().size()>0){
				for (AttachmentBean lAttachmentBean : pInvioNotificaInteropBean.getAttach()){
					List<String> linee = FileUtils.readLines(StorageImplementation.getStorage().extractFile(lAttachmentBean.getUriAttach()));
					StringBuffer lStringBuffer = new StringBuffer();
					for (String lString : linee){
						lStringBuffer.append(lString);
					}
					xmlToSend = lStringBuffer.toString();
					if (lNotificaInteroperabileBean.getTipoNotifica() == TipoInteroperabilita.NOTIFICA_ECCEZIONE
							&& pInvioNotificaInteropBean.getCategoriaPartenza().equals("INTEROP_SEGN")) {
						xmlToSend = xmlToSend.replace("<Motivo>", "<Motivo>" + pInvioNotificaInteropBean.getMotivo());
					}
				}
			}
			lNotificaInteroperabileBean.setXmlNotifica(xmlToSend);
			TFilterFetch<TRegProtVsEmailBean> lTFilterFetch = new TFilterFetch<TRegProtVsEmailBean>();
			TRegProtVsEmailBean lTRegProtVsEmailBean = new TRegProtVsEmailBean();
			lTRegProtVsEmailBean.setIdEmail(lTEmailMgoBean.getIdEmail());
			lTFilterFetch.setFilter(lTRegProtVsEmailBean);
			bean.setAttachments(lista);
			bean.setMotivoEccezione(pInvioNotificaInteropBean.getMotivo());
			
			ResultBean<EmailSentReferenceBean> output = AurigaMailService.getMailSenderService().sendandsaveinteropnotifica(getLocale(),lNotificaInteroperabileBean);			
			if(StringUtils.isNotBlank(output.getDefaultMessage())) {
				if(output.isInError()) {
					throw new StoreException(output.getDefaultMessage());		
				} else {
					addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
				}
			}
			lEmailSentReferenceBean = output.getResultBean();	
		} else {
			RispostaInoltroBean lRispostaInoltroBean = new RispostaInoltroBean();
			lRispostaInoltroBean.setMailOriginaria(lTEmailMgoBean);
			if (pInvioNotificaInteropBean.getTipoNotifica().equals("conferma"))
				lRispostaInoltroBean.setRispInol(RispostaInoltro.NOTIFICA_CONFERMA);
			else if (pInvioNotificaInteropBean.getTipoNotifica().equals("eccezione"))
				lRispostaInoltroBean.setRispInol(RispostaInoltro.NOTIFICA_ECCEZIONE);
			bean.setRispInol(lRispostaInoltroBean);
			bean.setMotivoEccezione(pInvioNotificaInteropBean.getMotivo());
					
			ResultBean<EmailSentReferenceBean> output = AurigaMailService.getMailSenderService().sendandsave(getLocale(), bean);
			if(StringUtils.isNotBlank(output.getDefaultMessage())) {
				if(output.isInError()) {
					throw new StoreException(output.getDefaultMessage());		
				} else {
					addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
				}
			}
			lEmailSentReferenceBean = output.getResultBean();
		}
		return new InvioMailResultBean();
	}

	public InvioMailResultBean sbloccaMail(InvioNotificaInteropBean pInvioNotificaInteropBean) throws Exception{
		LockUnlockMail lLockUnlockEmail = new LockUnlockMail(getSession());
		try {
			lLockUnlockEmail.unlockMail(pInvioNotificaInteropBean.getIdEmail());
		} catch(Exception e) {
			mLogger.error("AurigaInvioNotificaInteropDatasource -  sbloccaMail", e);
		}
		return new InvioMailResultBean();
	}

	public FileToExtractBean upedateXml(InvioNotificaInteropBean pInvioNotificaInteropBean) throws StoreException, Exception{
		if (pInvioNotificaInteropBean.getAttach()==null  || pInvioNotificaInteropBean.getAttach().size()==0){
			throw new StoreException("Impossibile recuperare l'xml, non sono presenti allegati");
		}
		String uriXml = getUriEccezione(pInvioNotificaInteropBean);
		if (StringUtils.isEmpty(uriXml)) throw new StoreException("Impossibile recuperare l'xml tra gli allegati");
		List<String> linee = FileUtils.readLines(StorageImplementation.getStorage().extractFile(uriXml));
		StringBuffer lStringBuffer = new StringBuffer();
		for (String lString : linee){
			lStringBuffer.append(lString);
		}
		String xmlToSend = lStringBuffer.toString();
		if (StringUtils.isNotEmpty(pInvioNotificaInteropBean.getMotivo()))
			xmlToSend = xmlToSend.replace("<Motivo>", "<Motivo>" + pInvioNotificaInteropBean.getMotivo());
		
		String uri = StorageImplementation.getStorage().storeStream(IOUtils.toInputStream(xmlToSend));
		FileToExtractBean lFileToExtractBean = new FileToExtractBean();
		lFileToExtractBean.setRemoteUri(false);
		lFileToExtractBean.setSbustato(false);
		lFileToExtractBean.setUri(uri);
		return lFileToExtractBean;
	}
	
	public String getUriEccezione(InvioNotificaInteropBean pInvioNotificaInteropBean){
		for (AttachmentBean lAttachmentBean : pInvioNotificaInteropBean.getAttach()){
			if (lAttachmentBean.getFileNameAttach().equalsIgnoreCase("eccezione.xml")
					|| lAttachmentBean.getFileNameAttach().equalsIgnoreCase("Aggiornamento.xml")
					|| lAttachmentBean.getFileNameAttach().equalsIgnoreCase("conferma.xml")){
				return lAttachmentBean.getUriAttach();
			}
		}
		return null;
	}
}
