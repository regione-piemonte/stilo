/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import java.util.Locale;
import it.eng.core.service.client.FactoryBusiness;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import it.eng.config.AurigaMailBusinessClientConfig;
/**
 * @author IrisServiceClient generator 0.0.1-SNAPSHOT
 */
public class BusinessTransaction {
		
	private static final String SERVICE_NAME = "BusinessTransaction";	
	
	private String url = AurigaMailBusinessClientConfig.getInstance().getUrl();  	
	  	
	
	 //void 
  	 public void  close(Locale locale) throws Exception {
  	 
  	  	Type   outputType =	null;
  	  	FactoryBusiness.getBusiness().call(url, locale,null,outputType, SERVICE_NAME, "close", null);
	 } 
	 //java.lang.String 
  	 public java.lang.String  begin(Locale locale) throws Exception {
  	 
  	  	Type   outputType =	new TypeToken<java.lang.String>() {}.getType();
  	  	return (java.lang.String)FactoryBusiness.getBusiness().call(url, locale,java.lang.String.class,outputType, SERVICE_NAME, "begin", null);
	 } 
	 //void 
  	 public void  commit(Locale locale) throws Exception {
  	 
  	  	Type   outputType =	null;
  	  	FactoryBusiness.getBusiness().call(url, locale,null,outputType, SERVICE_NAME, "commit", null);
	 } 
}    