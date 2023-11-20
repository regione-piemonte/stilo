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
public class FattureService {
		
	private static final String SERVICE_NAME = "FattureService";	
	private static Logger mLogger = Logger.getLogger(FattureService.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.document.function.bean.FileExtractedOut 
  	 public it.eng.document.function.bean.FileExtractedOut  generapdf(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.auriga.function.bean.FattureServiceInputBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.FileExtractedOut>() {}.getType();
  	  	return (it.eng.document.function.bean.FileExtractedOut)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.FileExtractedOut.class,outputType, SERVICE_NAME, "generaPdf", var2,var3);
	 } 
}    