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
public class FormatiService {
		
	private static final String SERVICE_NAME = "FormatiService";	
	private static Logger mLogger = Logger.getLogger(FormatiService.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.core.business.TPagingList 
  	 public it.eng.core.business.TPagingList<it.eng.auriga.module.business.formati.bean.FormatiServiceBean>  loadall(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.core.business.TPagingList<it.eng.auriga.module.business.formati.bean.FormatiServiceBean>>() {}.getType();
  	  	return (it.eng.core.business.TPagingList<it.eng.auriga.module.business.formati.bean.FormatiServiceBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.core.business.TPagingList.class,outputType, SERVICE_NAME, "loadAll", var2);
	 } 
	 //it.eng.auriga.module.business.formati.bean.FormatiServiceBean 
  	 public it.eng.auriga.module.business.formati.bean.FormatiServiceBean  checkpdfconvertibile(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.auriga.module.business.formati.bean.FormatiServiceBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.auriga.module.business.formati.bean.FormatiServiceBean>() {}.getType();
  	  	return (it.eng.auriga.module.business.formati.bean.FormatiServiceBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.auriga.module.business.formati.bean.FormatiServiceBean.class,outputType, SERVICE_NAME, "checkPdfConvertibile", var2,var3);
	 } 
}    