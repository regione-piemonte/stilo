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
public class DaoTRelAlboAvbVsAuriga {
		
	private static final String SERVICE_NAME = "DaoTRelAlboAvbVsAuriga";	
	private static Logger mLogger = Logger.getLogger(DaoTRelAlboAvbVsAuriga.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //java.math.BigDecimal 
  	 public java.math.BigDecimal  getidattoavb(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,java.math.BigDecimal var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<java.math.BigDecimal>() {}.getType();
  	  	return (java.math.BigDecimal)FactoryBusiness.getBusiness(type).call(url, locale,java.math.BigDecimal.class,outputType, SERVICE_NAME, "getIdAttoAvb", var2,var3);
	 } 
}    