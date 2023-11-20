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
public class Find {
		
	private static final String SERVICE_NAME = "Find";	
	private static Logger mLogger = Logger.getLogger(Find.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.auriga.function.bean.ArrayBean 
  	 public it.eng.auriga.function.bean.ArrayBean<java.lang.Object>  findrepositoryobject(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.auriga.function.bean.FindRepositoryObjectBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.auriga.function.bean.ArrayBean<java.lang.Object>>() {}.getType();
  	  	return (it.eng.auriga.function.bean.ArrayBean<java.lang.Object>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.auriga.function.bean.ArrayBean.class,outputType, SERVICE_NAME, "findRepositoryObject", var2,var3);
	 } 
	 //it.eng.auriga.function.result.AurigaResultBean 
  	 public it.eng.auriga.function.result.AurigaResultBean  findprocessobject(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.auriga.function.bean.FindProcessObjectBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.auriga.function.result.AurigaResultBean>() {}.getType();
  	  	return (it.eng.auriga.function.result.AurigaResultBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.auriga.function.result.AurigaResultBean.class,outputType, SERVICE_NAME, "findProcessObject", var2,var3);
	 } 
	 //it.eng.auriga.function.bean.ArrayBean 
  	 public it.eng.auriga.function.bean.ArrayBean<java.lang.Object>  findorgstructureobject(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.auriga.function.bean.FindOrgStructureObjectBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.auriga.function.bean.ArrayBean<java.lang.Object>>() {}.getType();
  	  	return (it.eng.auriga.function.bean.ArrayBean<java.lang.Object>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.auriga.function.bean.ArrayBean.class,outputType, SERVICE_NAME, "findOrgStructureObject", var2,var3);
	 } 
	 //it.eng.auriga.function.bean.ArrayBean 
  	 public it.eng.auriga.function.bean.ArrayBean<java.lang.Object>  findtitolarioobject(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.auriga.function.bean.FindTitolarioObjectBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.auriga.function.bean.ArrayBean<java.lang.Object>>() {}.getType();
  	  	return (it.eng.auriga.function.bean.ArrayBean<java.lang.Object>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.auriga.function.bean.ArrayBean.class,outputType, SERVICE_NAME, "findTitolarioObject", var2,var3);
	 } 
	 //it.eng.auriga.function.bean.ArrayBean 
  	 public it.eng.auriga.function.bean.ArrayBean<java.lang.Object>  findrubricaobject(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.auriga.function.bean.FindRubricaObjectBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.auriga.function.bean.ArrayBean<java.lang.Object>>() {}.getType();
  	  	return (it.eng.auriga.function.bean.ArrayBean<java.lang.Object>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.auriga.function.bean.ArrayBean.class,outputType, SERVICE_NAME, "findRubricaObject", var2,var3);
	 } 
	 //it.eng.auriga.function.bean.FindElenchiAlbiResultBean 
  	 public it.eng.auriga.function.bean.FindElenchiAlbiResultBean  findelenchialbi(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.auriga.function.bean.FindElenchiAlbiObjectBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.auriga.function.bean.FindElenchiAlbiResultBean>() {}.getType();
  	  	return (it.eng.auriga.function.bean.FindElenchiAlbiResultBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.auriga.function.bean.FindElenchiAlbiResultBean.class,outputType, SERVICE_NAME, "findElenchiAlbi", var2,var3);
	 } 
}    