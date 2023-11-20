/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import java.util.Locale;
import it.eng.core.service.client.FactoryBusiness;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import it.eng.config.AurigaBusinessClientConfig;
import it.eng.core.service.client.FactoryBusiness.BusinessType;
import org.apache.log4j.Logger;

/**
 * @author ServiceClient generator 0.0.4-SNAPSHOT
 */
public class SimpleSendMailService {
		
	private static final String SERVICE_NAME = "SimpleSendMailService";	
	private static Logger mLogger = Logger.getLogger(SimpleSendMailService.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.aurigasendmail.bean.AurigaResultSendMail 
  	 public it.eng.aurigasendmail.bean.AurigaResultSendMail  simplesendmail(Locale locale,it.eng.aurigasendmail.bean.AurigaMailToSendBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigasendmail.bean.AurigaResultSendMail>() {}.getType();
  	  	return (it.eng.aurigasendmail.bean.AurigaResultSendMail)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigasendmail.bean.AurigaResultSendMail.class,outputType, SERVICE_NAME, "simpleSendMail", var2);
	 } 
	 //it.eng.aurigasendmail.bean.AurigaResultSendMail 
  	 public it.eng.aurigasendmail.bean.AurigaResultSendMail  asyncsimplesendmail(Locale locale,it.eng.aurigasendmail.bean.AurigaMailToSendBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigasendmail.bean.AurigaResultSendMail>() {}.getType();
  	  	return (it.eng.aurigasendmail.bean.AurigaResultSendMail)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigasendmail.bean.AurigaResultSendMail.class,outputType, SERVICE_NAME, "asyncSimpleSendMail", var2);
	 } 
	 //it.eng.aurigasendmail.bean.AurigaResultSendMail 
  	 public it.eng.aurigasendmail.bean.AurigaResultSendMail  sendfromconfigured(Locale locale,it.eng.aurigasendmail.bean.AurigaDummyMailToSendBean var2,java.lang.String var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigasendmail.bean.AurigaResultSendMail>() {}.getType();
  	  	return (it.eng.aurigasendmail.bean.AurigaResultSendMail)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigasendmail.bean.AurigaResultSendMail.class,outputType, SERVICE_NAME, "sendFromConfigured", var2,var3);
	 } 
	 //it.eng.aurigasendmail.bean.AurigaResultSendMail 
  	 public it.eng.aurigasendmail.bean.AurigaResultSendMail  asyncsendfromconfigured(Locale locale,it.eng.aurigasendmail.bean.AurigaDummyMailToSendBean var2,java.lang.String var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigasendmail.bean.AurigaResultSendMail>() {}.getType();
  	  	return (it.eng.aurigasendmail.bean.AurigaResultSendMail)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigasendmail.bean.AurigaResultSendMail.class,outputType, SERVICE_NAME, "asyncSendFromConfigured", var2,var3);
	 } 
}    