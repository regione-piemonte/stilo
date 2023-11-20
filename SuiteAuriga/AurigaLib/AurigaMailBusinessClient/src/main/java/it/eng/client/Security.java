/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import java.util.Locale;
import it.eng.core.service.client.FactoryBusiness;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import it.eng.config.AurigaMailBusinessClientConfig;
/**
 * @author IrisServiceClient generator 0.0.1-SNAPSHOT
 */
public class Security {
		
	private static final String SERVICE_NAME = "Security";	
	
	private String url = AurigaMailBusinessClientConfig.getInstance().getUrl();  	
	  	
	
	 //java.lang.String 
  	 public java.lang.String  login(Locale locale,java.lang.String var2,java.lang.String var3,java.lang.String var4) throws Exception {
  	 
  	  	Type   outputType =	new TypeToken<java.lang.String>() {}.getType();
  	  	return (java.lang.String)FactoryBusiness.getBusiness().call(url, locale,java.lang.String.class,outputType, SERVICE_NAME, "login", var2,var3,var4);
	 } 
}    