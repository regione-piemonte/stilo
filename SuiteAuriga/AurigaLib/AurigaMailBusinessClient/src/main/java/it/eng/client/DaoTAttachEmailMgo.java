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
public class DaoTAttachEmailMgo {
		
	private static final String SERVICE_NAME = "DaoTAttachEmailMgo";	
	private static Logger mLogger = Logger.getLogger(DaoTAttachEmailMgo.class);
	
	private String url = AurigaMailBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaMailBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean 
  	 public it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean  get(Locale locale,java.lang.String var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean.class,outputType, SERVICE_NAME, "get", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean 
  	 public it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean  update(Locale locale,it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean.class,outputType, SERVICE_NAME, "update", var2);
	 } 
	 //void 
  	 public void  delete(Locale locale,it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	null;
  	  	FactoryBusiness.getBusiness(type).call(url, locale,null,outputType, SERVICE_NAME, "delete", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean 
  	 public it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean  save(Locale locale,it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean.class,outputType, SERVICE_NAME, "save", var2);
	 } 
	 //it.eng.core.business.TPagingList 
  	 public it.eng.core.business.TPagingList<it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean>  search(Locale locale,it.eng.core.business.TFilterFetch<it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean> var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.core.business.TPagingList<it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean>>() {}.getType();
  	  	return (it.eng.core.business.TPagingList<it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.core.business.TPagingList.class,outputType, SERVICE_NAME, "search", var2);
	 } 
	 //void 
  	 public void  forcedelete(Locale locale,it.eng.aurigamailbusiness.bean.TAttachEmailMgoBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	null;
  	  	FactoryBusiness.getBusiness(type).call(url, locale,null,outputType, SERVICE_NAME, "forcedelete", var2);
	 } 
}    