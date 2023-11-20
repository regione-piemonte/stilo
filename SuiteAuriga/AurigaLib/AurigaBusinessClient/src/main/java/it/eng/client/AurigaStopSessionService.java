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
public class AurigaStopSessionService {
		
	private static final String SERVICE_NAME = "AurigaStopSessionService";	
	private static Logger mLogger = Logger.getLogger(AurigaStopSessionService.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.auriga.bean.AurigaStoppableSearchBean 
  	 public it.eng.auriga.bean.AurigaStoppableSearchBean  stopsession(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.auriga.bean.AurigaStoppableSearchBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.auriga.bean.AurigaStoppableSearchBean>() {}.getType();
  	  	return (it.eng.auriga.bean.AurigaStoppableSearchBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.auriga.bean.AurigaStoppableSearchBean.class,outputType, SERVICE_NAME, "stopSession", var2,var3);
	 } 
}    