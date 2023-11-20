/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import java.util.Locale;
import it.eng.core.service.client.FactoryBusiness;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import it.eng.config.AurigaMailBusinessClientConfig;
import it.eng.core.service.client.FactoryBusiness.BusinessType;
import org.apache.log4j.Logger;

/**
 * @author ServiceClient generator 1.0.4
 */
public class SearchUtility {
		
	private static final String SERVICE_NAME = "SearchUtility";	
	private static Logger mLogger = Logger.getLogger(SearchUtility.class);
	
	private String url = AurigaMailBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaMailBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.aurigamailbusiness.database.utility.bean.search.SearchOut 
  	 public it.eng.aurigamailbusiness.database.utility.bean.search.SearchOut  search(Locale locale,it.eng.aurigamailbusiness.database.utility.bean.search.SearchIn var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.database.utility.bean.search.SearchOut>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.database.utility.bean.search.SearchOut)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.database.utility.bean.search.SearchOut.class,outputType, SERVICE_NAME, "search", var2);
	 } 
}    