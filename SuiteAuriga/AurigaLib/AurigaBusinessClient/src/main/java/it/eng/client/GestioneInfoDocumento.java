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
public class GestioneInfoDocumento {
		
	private static final String SERVICE_NAME = "GestioneInfoDocumento";	
	private static Logger mLogger = Logger.getLogger(GestioneInfoDocumento.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.document.function.bean.InviaInfoDocumentoOutputBean 
  	 public it.eng.document.function.bean.InviaInfoDocumentoOutputBean  inviainfo(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.InviaInfoDocumentoInputBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.InviaInfoDocumentoOutputBean>() {}.getType();
  	  	return (it.eng.document.function.bean.InviaInfoDocumentoOutputBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.InviaInfoDocumentoOutputBean.class,outputType, SERVICE_NAME, "inviaInfo", var2,var3);
	 } 
}    