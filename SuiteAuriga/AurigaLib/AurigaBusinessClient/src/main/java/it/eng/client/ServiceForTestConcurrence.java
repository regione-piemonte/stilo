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
public class ServiceForTestConcurrence {
		
	private static final String SERVICE_NAME = "ServiceForTestConcurrence";	
	private static Logger mLogger = Logger.getLogger(ServiceForTestConcurrence.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.document.function.bean.TestConcurrenceBean 
  	 public it.eng.document.function.bean.TestConcurrenceBean  testconcurrence(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.TestConcurrenceBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.TestConcurrenceBean>() {}.getType();
  	  	return (it.eng.document.function.bean.TestConcurrenceBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.TestConcurrenceBean.class,outputType, SERVICE_NAME, "testConcurrence", var2,var3);
	 } 
}    