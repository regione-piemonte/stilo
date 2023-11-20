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
public class LuceneService {
		
	private static final String SERVICE_NAME = "LuceneService";	
	private static Logger mLogger = Logger.getLogger(LuceneService.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.auriga.function.bean.RetrieveIndexedFieldsBean 
  	 public it.eng.auriga.function.bean.RetrieveIndexedFieldsBean  retrieveindexedfields(Locale locale,it.eng.auriga.function.bean.RetrieveIndexedFieldsBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.auriga.function.bean.RetrieveIndexedFieldsBean>() {}.getType();
  	  	return (it.eng.auriga.function.bean.RetrieveIndexedFieldsBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.auriga.function.bean.RetrieveIndexedFieldsBean.class,outputType, SERVICE_NAME, "retrieveIndexedFields", var2);
	 } 
}    