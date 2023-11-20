/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import java.util.Locale;
import it.eng.core.service.client.FactoryBusiness;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import it.eng.config.AurigaBusinessClientConfig;
import it.eng.core.service.client.FactoryBusiness.BusinessType;
import org.apache.log4j.Logger;

/**
 * @author ServiceClient generator 1.0.4
 */
public class DaoBmtJobParameters {
		
	private static final String SERVICE_NAME = "DaoBmtJobParameters";	
	private static Logger mLogger = Logger.getLogger(DaoBmtJobParameters.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.core.business.TPagingList 
  	 public it.eng.core.business.TPagingList<it.eng.auriga.module.business.dao.beans.JobParameterBean>  search(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.core.business.TFilterFetch<it.eng.auriga.module.business.dao.beans.JobParameterBean> var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.core.business.TPagingList<it.eng.auriga.module.business.dao.beans.JobParameterBean>>() {}.getType();
  	  	return (it.eng.core.business.TPagingList<it.eng.auriga.module.business.dao.beans.JobParameterBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.core.business.TPagingList.class,outputType, SERVICE_NAME, "search", var2,var3);
	 } 
	 //it.eng.auriga.module.business.dao.beans.JobParameterBean 
  	 public it.eng.auriga.module.business.dao.beans.JobParameterBean  update(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.auriga.module.business.dao.beans.JobParameterBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.auriga.module.business.dao.beans.JobParameterBean>() {}.getType();
  	  	return (it.eng.auriga.module.business.dao.beans.JobParameterBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.auriga.module.business.dao.beans.JobParameterBean.class,outputType, SERVICE_NAME, "update", var2,var3);
	 } 
	 //void 
  	 public void  delete(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.auriga.module.business.dao.beans.JobParameterBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	null;
  	  	FactoryBusiness.getBusiness(type).call(url, locale,null,outputType, SERVICE_NAME, "delete", var2,var3);
	 } 
	 //void 
  	 public void  forcedelete(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.auriga.module.business.dao.beans.JobParameterBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	null;
  	  	FactoryBusiness.getBusiness(type).call(url, locale,null,outputType, SERVICE_NAME, "forcedelete", var2,var3);
	 } 
	 //it.eng.auriga.module.business.dao.beans.JobParameterBean 
  	 public it.eng.auriga.module.business.dao.beans.JobParameterBean  save(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.auriga.module.business.dao.beans.JobParameterBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.auriga.module.business.dao.beans.JobParameterBean>() {}.getType();
  	  	return (it.eng.auriga.module.business.dao.beans.JobParameterBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.auriga.module.business.dao.beans.JobParameterBean.class,outputType, SERVICE_NAME, "save", var2,var3);
	 } 
	 //it.eng.auriga.module.business.dao.beans.JobParameterBean 
  	 public it.eng.auriga.module.business.dao.beans.JobParameterBean  get(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.auriga.module.business.dao.beans.JobParameterBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.auriga.module.business.dao.beans.JobParameterBean>() {}.getType();
  	  	return (it.eng.auriga.module.business.dao.beans.JobParameterBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.auriga.module.business.dao.beans.JobParameterBean.class,outputType, SERVICE_NAME, "get", var2,var3);
	 } 
}    