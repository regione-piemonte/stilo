/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import java.util.Locale;
import it.eng.core.service.client.FactoryBusiness;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import it.eng.config.AurigaMailBusinessClientConfig;
import it.eng.core.service.client.FactoryBusiness.BusinessType;
import org.apache.log4j.Logger;

/**
 * @author ServiceClient generator 1.0.4
 */
public class MailProcessorService {
		
	private static final String SERVICE_NAME = "MailProcessorService";	
	private static Logger mLogger = Logger.getLogger(MailProcessorService.class);
	
	private String url = AurigaMailBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaMailBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.aurigamailbusiness.bean.EmailBean 
  	 public it.eng.aurigamailbusiness.bean.EmailBean  save(Locale locale,it.eng.aurigamailbusiness.bean.EmailBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.EmailBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.EmailBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.EmailBean.class,outputType, SERVICE_NAME, "save", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.MailAttachmentsBean 
  	 public it.eng.aurigamailbusiness.bean.MailAttachmentsBean  getattachmentinteroperabilita(Locale locale,it.eng.aurigamailbusiness.bean.InteroperabilitaAttachmentBeanIn var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.MailAttachmentsBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.MailAttachmentsBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.MailAttachmentsBean.class,outputType, SERVICE_NAME, "getAttachmentInteroperabilita", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.TEmailMgoBean 
  	 public it.eng.aurigamailbusiness.bean.TEmailMgoBean  updatemailprotocollata(Locale locale,it.eng.aurigamailbusiness.bean.InfoProtocolloBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.TEmailMgoBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.TEmailMgoBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.TEmailMgoBean.class,outputType, SERVICE_NAME, "updateMailProtocollata", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.TEmailMgoBean 
  	 public it.eng.aurigamailbusiness.bean.TEmailMgoBean  eliminaprotocollazionemail(Locale locale,it.eng.aurigamailbusiness.bean.RegistrazioneProtocolloBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.TEmailMgoBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.TEmailMgoBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.TEmailMgoBean.class,outputType, SERVICE_NAME, "eliminaProtocollazioneMail", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.EmailAttachsBean 
  	 public it.eng.aurigamailbusiness.bean.EmailAttachsBean  getattachmentsbyidemail(Locale locale,java.lang.String var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.EmailAttachsBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.EmailAttachsBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.EmailAttachsBean.class,outputType, SERVICE_NAME, "getAttachmentsByIdEmail", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.restrepresentation.output.GetEmailAttachmentsResponse 
  	 public it.eng.aurigamailbusiness.bean.restrepresentation.output.GetEmailAttachmentsResponse  getattachmentswithcontents(Locale locale,java.io.File var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.restrepresentation.output.GetEmailAttachmentsResponse>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.restrepresentation.output.GetEmailAttachmentsResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.restrepresentation.output.GetEmailAttachmentsResponse.class,outputType, SERVICE_NAME, "getAttachmentsWithContents", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.restrepresentation.output.GetEmailAttachmentsResponse 
  	 public it.eng.aurigamailbusiness.bean.restrepresentation.output.GetEmailAttachmentsResponse  getattachmentswithcontentsbyidemail(Locale locale,java.lang.String var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.restrepresentation.output.GetEmailAttachmentsResponse>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.restrepresentation.output.GetEmailAttachmentsResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.restrepresentation.output.GetEmailAttachmentsResponse.class,outputType, SERVICE_NAME, "getAttachmentsWithContentsByIdEmail", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.EmailGroupBean 
  	 public it.eng.aurigamailbusiness.bean.EmailGroupBean  getmailprotocollate(Locale locale,it.eng.aurigamailbusiness.bean.RegistrazioneProtocollo var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.EmailGroupBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.EmailGroupBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.EmailGroupBean.class,outputType, SERVICE_NAME, "getMailProtocollate", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.SinteticEmailInfoBean 
  	 public it.eng.aurigamailbusiness.bean.SinteticEmailInfoBean  getsinteticmailbyidemail(Locale locale,java.lang.String var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.SinteticEmailInfoBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.SinteticEmailInfoBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.SinteticEmailInfoBean.class,outputType, SERVICE_NAME, "getSinteticMailByIdEmail", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.EmailInfoBean 
  	 public it.eng.aurigamailbusiness.bean.EmailInfoBean  getbodyhtmlbyidemail(Locale locale,java.lang.String var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.EmailInfoBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.EmailInfoBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.EmailInfoBean.class,outputType, SERVICE_NAME, "getBodyHtmlByIdEmail", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.EmailInfoBean 
  	 public it.eng.aurigamailbusiness.bean.EmailInfoBean  gethtmlinmainbodybyidemail(Locale locale,java.lang.String var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.EmailInfoBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.EmailInfoBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.EmailInfoBean.class,outputType, SERVICE_NAME, "getHtmlInMainBodyByIdEmail", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.ResultBean 
  	 public it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.InfoRelazioneProtocolloBean>  crearelazioneprotocollo(Locale locale,it.eng.aurigamailbusiness.bean.RegistrazioneProtocolloBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.InfoRelazioneProtocolloBean>>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.InfoRelazioneProtocolloBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.ResultBean.class,outputType, SERVICE_NAME, "creaRelazioneProtocollo", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.EmailInfoBean 
  	 public it.eng.aurigamailbusiness.bean.EmailInfoBean  getbodytextbyidemail(Locale locale,java.lang.String var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.EmailInfoBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.EmailInfoBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.EmailInfoBean.class,outputType, SERVICE_NAME, "getBodyTextByIdEmail", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.EmailInfoBean 
  	 public it.eng.aurigamailbusiness.bean.EmailInfoBean  getmailinfobyidemail(Locale locale,java.lang.String var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.EmailInfoBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.EmailInfoBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.EmailInfoBean.class,outputType, SERVICE_NAME, "getMailInfoByIdEmail", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.EmailInfoBean 
  	 public it.eng.aurigamailbusiness.bean.EmailInfoBean  gethtmlinmainbody(Locale locale,java.io.File var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.EmailInfoBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.EmailInfoBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.EmailInfoBean.class,outputType, SERVICE_NAME, "getHtmlInMainBody", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.EmailInfoBean 
  	 public it.eng.aurigamailbusiness.bean.EmailInfoBean  gettextinmainbody(Locale locale,java.io.File var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.EmailInfoBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.EmailInfoBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.EmailInfoBean.class,outputType, SERVICE_NAME, "getTextInMainBody", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.EmailInfoBean 
  	 public it.eng.aurigamailbusiness.bean.EmailInfoBean  gettextinmainbodybyidemail(Locale locale,java.lang.String var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.EmailInfoBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.EmailInfoBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.EmailInfoBean.class,outputType, SERVICE_NAME, "getTextInMainBodyByIdEmail", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.ResultBean 
  	 public it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.StatoConfermaAutomaticaBean>  inviaconfermaautomatica(Locale locale,it.eng.aurigamailbusiness.bean.RegistrazioneProtocolloBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.StatoConfermaAutomaticaBean>>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.StatoConfermaAutomaticaBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.ResultBean.class,outputType, SERVICE_NAME, "inviaConfermaAutomatica", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.MailAttachmentsBean 
  	 public it.eng.aurigamailbusiness.bean.MailAttachmentsBean  getpostacertbyidemail(Locale locale,java.lang.String var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.MailAttachmentsBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.MailAttachmentsBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.MailAttachmentsBean.class,outputType, SERVICE_NAME, "getPostacertByIdEmail", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.GetStatoProtocollazioneOutBean 
  	 public it.eng.aurigamailbusiness.bean.GetStatoProtocollazioneOutBean  getstatoprotocollazione(Locale locale,it.eng.aurigamailbusiness.bean.InfoProtocolloBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.GetStatoProtocollazioneOutBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.GetStatoProtocollazioneOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.GetStatoProtocollazioneOutBean.class,outputType, SERVICE_NAME, "getStatoProtocollazione", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.TEmailMgoBean 
  	 public it.eng.aurigamailbusiness.bean.TEmailMgoBean  eseguiarchiviazione(Locale locale,it.eng.aurigamailbusiness.bean.TEmailMgoBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.TEmailMgoBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.TEmailMgoBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.TEmailMgoBean.class,outputType, SERVICE_NAME, "eseguiArchiviazione", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.EmailInfoRelazioniBean 
  	 public it.eng.aurigamailbusiness.bean.EmailInfoRelazioniBean  getrelazionimail(Locale locale,it.eng.aurigamailbusiness.bean.InterrogazioneRelazioneEmailBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.EmailInfoRelazioniBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.EmailInfoRelazioniBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.EmailInfoRelazioniBean.class,outputType, SERVICE_NAME, "getRelazioniMail", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.EmailAttachsBean 
  	 public it.eng.aurigamailbusiness.bean.EmailAttachsBean  getattachments(Locale locale,java.io.File var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.EmailAttachsBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.EmailAttachsBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.EmailAttachsBean.class,outputType, SERVICE_NAME, "getAttachments", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.EmailInfoBean 
  	 public it.eng.aurigamailbusiness.bean.EmailInfoBean  getmailinfo(Locale locale,java.io.File var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.EmailInfoBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.EmailInfoBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.EmailInfoBean.class,outputType, SERVICE_NAME, "getMailInfo", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.SinteticEmailInfoBean 
  	 public it.eng.aurigamailbusiness.bean.SinteticEmailInfoBean  getsinteticmail(Locale locale,java.io.File var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.SinteticEmailInfoBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.SinteticEmailInfoBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.SinteticEmailInfoBean.class,outputType, SERVICE_NAME, "getSinteticMail", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.EmailInfoBean 
  	 public it.eng.aurigamailbusiness.bean.EmailInfoBean  getbodyhtml(Locale locale,java.io.File var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.EmailInfoBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.EmailInfoBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.EmailInfoBean.class,outputType, SERVICE_NAME, "getBodyHtml", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.EmailInfoBean 
  	 public it.eng.aurigamailbusiness.bean.EmailInfoBean  getbodytext(Locale locale,java.io.File var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.EmailInfoBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.EmailInfoBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.EmailInfoBean.class,outputType, SERVICE_NAME, "getBodyText", var2);
	 } 
	 //void 
  	 public void  updateflag(Locale locale,java.lang.String var2,java.lang.String var3,java.lang.Boolean var4) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	null;
  	  	FactoryBusiness.getBusiness(type).call(url, locale,null,outputType, SERVICE_NAME, "updateFlag", var2,var3,var4);
	 } 
	 //it.eng.aurigamailbusiness.bean.EmailGroupBean 
  	 public it.eng.aurigamailbusiness.bean.EmailGroupBean  movemail(Locale locale,it.eng.aurigamailbusiness.bean.EmailGroupBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.EmailGroupBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.EmailGroupBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.EmailGroupBean.class,outputType, SERVICE_NAME, "moveMail", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.MailAttachmentsBean 
  	 public it.eng.aurigamailbusiness.bean.MailAttachmentsBean  getpostacert(Locale locale,java.io.File var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.MailAttachmentsBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.MailAttachmentsBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.MailAttachmentsBean.class,outputType, SERVICE_NAME, "getPostacert", var2);
	 } 
}    