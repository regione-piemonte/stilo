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
public class GestioneTask {
		
	private static final String SERVICE_NAME = "GestioneTask";	
	private static Logger mLogger = Logger.getLogger(GestioneTask.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.document.function.bean.EseguiTaskOutBean 
  	 public it.eng.document.function.bean.EseguiTaskOutBean  salvatask(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.EseguiTaskInBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.EseguiTaskOutBean>() {}.getType();
  	  	return (it.eng.document.function.bean.EseguiTaskOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.EseguiTaskOutBean.class,outputType, SERVICE_NAME, "salvaTask", var2,var3);
	 } 
	 //it.eng.document.function.bean.SalvaOstOutBean 
  	 public it.eng.document.function.bean.SalvaOstOutBean  salvaost(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.SalvaOstInBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.SalvaOstOutBean>() {}.getType();
  	  	return (it.eng.document.function.bean.SalvaOstOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.SalvaOstOutBean.class,outputType, SERVICE_NAME, "salvaOst", var2,var3);
	 } 
	 //java.util.List 
  	 public java.util.List<java.lang.String>  completataskscaduti(Locale locale) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<java.util.List<java.lang.String>>() {}.getType();
  	  	return (java.util.List<java.lang.String>)FactoryBusiness.getBusiness(type).call(url, locale,java.util.List.class,outputType, SERVICE_NAME, "completaTaskScaduti", null);
	 } 
}    