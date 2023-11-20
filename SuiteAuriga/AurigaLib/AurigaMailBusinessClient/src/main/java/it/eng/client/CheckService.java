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
public class CheckService {
		
	private static final String SERVICE_NAME = "CheckService";	
	private static Logger mLogger = Logger.getLogger(CheckService.class);
	
	private String url = AurigaMailBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaMailBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.aurigamailbusiness.bean.TEmailMgoBean 
  	 public it.eng.aurigamailbusiness.bean.TEmailMgoBean  lock(Locale locale,it.eng.aurigamailbusiness.bean.LockBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.TEmailMgoBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.TEmailMgoBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.TEmailMgoBean.class,outputType, SERVICE_NAME, "lock", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.TEmailMgoBean 
  	 public it.eng.aurigamailbusiness.bean.TEmailMgoBean  unlock(Locale locale,it.eng.aurigamailbusiness.bean.LockBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.TEmailMgoBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.TEmailMgoBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.TEmailMgoBean.class,outputType, SERVICE_NAME, "unlock", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.CheckLockOutBean 
  	 public it.eng.aurigamailbusiness.bean.CheckLockOutBean  checklock(Locale locale,it.eng.aurigamailbusiness.bean.LockBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.CheckLockOutBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.CheckLockOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.CheckLockOutBean.class,outputType, SERVICE_NAME, "checkLock", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.TEmailMgoBean 
  	 public it.eng.aurigamailbusiness.bean.TEmailMgoBean  forceunlock(Locale locale,it.eng.aurigamailbusiness.bean.LockBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.TEmailMgoBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.TEmailMgoBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.TEmailMgoBean.class,outputType, SERVICE_NAME, "forceUnlock", var2);
	 } 
}    