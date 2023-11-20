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
public class SalvataggioFile {
		
	private static final String SERVICE_NAME = "SalvataggioFile";	
	private static Logger mLogger = Logger.getLogger(SalvataggioFile.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.document.function.bean.FileSavedOut 
  	 public it.eng.document.function.bean.FileSavedOut  savefile(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.FileSavedIn var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.FileSavedOut>() {}.getType();
  	  	return (it.eng.document.function.bean.FileSavedOut)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.FileSavedOut.class,outputType, SERVICE_NAME, "saveFile", var2,var3);
	 } 
}    