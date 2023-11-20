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
public class MailSenderService {
		
	private static final String SERVICE_NAME = "MailSenderService";	
	private static Logger mLogger = Logger.getLogger(MailSenderService.class);
	
	private String url = AurigaMailBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaMailBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.aurigamailbusiness.bean.EmailSentReferenceBean 
  	 public it.eng.aurigamailbusiness.bean.EmailSentReferenceBean  sendandsavemailfileinstorage(Locale locale,it.eng.aurigamailbusiness.bean.SenderBean var2,java.lang.Boolean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.EmailSentReferenceBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.EmailSentReferenceBean.class,outputType, SERVICE_NAME, "sendAndSaveMailFileInStorage", var2,var3);
	 } 
	 //it.eng.aurigamailbusiness.bean.ResultBean 
  	 public it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>  sendandsaveinteropnotifica(Locale locale,it.eng.aurigamailbusiness.bean.NotificaInteroperabileBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.ResultBean.class,outputType, SERVICE_NAME, "sendAndSaveInteropNotifica", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.ResultBean 
  	 public it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>  sendandsavemailprotocollata(Locale locale,it.eng.aurigamailbusiness.bean.SenderMailProtocollataBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.ResultBean.class,outputType, SERVICE_NAME, "sendAndSaveMailProtocollata", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.ResultBean 
  	 public it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>  sendandsaveworkitems(Locale locale,it.eng.aurigamailbusiness.bean.SenderBean var2,it.eng.aurigamailbusiness.bean.MailLoginBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.ResultBean.class,outputType, SERVICE_NAME, "sendAndSaveWorkItems", var2,var3);
	 } 
	 //it.eng.aurigamailbusiness.bean.ResultBean 
  	 public it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.DraftAndWorkItemsSavedBean>  savedraftandworkitems(Locale locale,it.eng.aurigamailbusiness.bean.DraftAndWorkItemsBean var2,java.lang.Integer var3,it.eng.aurigamailbusiness.bean.MailLoginBean var4) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.DraftAndWorkItemsSavedBean>>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.DraftAndWorkItemsSavedBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.ResultBean.class,outputType, SERVICE_NAME, "saveDraftAndWorkItems", var2,var3,var4);
	 } 
	 //it.eng.aurigamailbusiness.bean.ResultBean 
  	 public it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>  sendanddeleteaftersend(Locale locale,it.eng.aurigamailbusiness.bean.SenderBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.ResultBean.class,outputType, SERVICE_NAME, "sendAndDeleteAfterSend", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.EmailSentReferenceBean 
  	 public it.eng.aurigamailbusiness.bean.EmailSentReferenceBean  send(Locale locale,it.eng.aurigamailbusiness.bean.SenderBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.EmailSentReferenceBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.EmailSentReferenceBean.class,outputType, SERVICE_NAME, "send", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.EmailSentReferenceBean 
  	 public it.eng.aurigamailbusiness.bean.EmailSentReferenceBean  sendanonymous(Locale locale,it.eng.aurigamailbusiness.bean.AnonymousSenderBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.EmailSentReferenceBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.EmailSentReferenceBean.class,outputType, SERVICE_NAME, "sendAnonymous", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.ResultBean 
  	 public it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>  resend(Locale locale,it.eng.aurigamailbusiness.bean.SenderBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.ResultBean.class,outputType, SERVICE_NAME, "reSend", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.ResultBean 
  	 public it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>  sendandsave(Locale locale,it.eng.aurigamailbusiness.bean.SenderBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.ResultBean.class,outputType, SERVICE_NAME, "sendAndSave", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.ResultBean 
  	 public it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>  saveandsenddraft(Locale locale,it.eng.aurigamailbusiness.bean.DraftAndWorkItemsBean var2,it.eng.aurigamailbusiness.bean.MailLoginBean var3,java.lang.String var4,java.lang.Integer var5) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.EmailSentReferenceBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.ResultBean.class,outputType, SERVICE_NAME, "saveAndSendDraft", var2,var3,var4,var5);
	 } 
}    