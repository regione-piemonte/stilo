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
public class StorageServiceWS {
		
	private static final String SERVICE_NAME = "StorageServiceWS";	
	private static Logger mLogger = Logger.getLogger(StorageServiceWS.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //java.lang.String 
  	 public java.lang.String  store(Locale locale,java.lang.String var2,java.io.File var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<java.lang.String>() {}.getType();
  	  	return (java.lang.String)FactoryBusiness.getBusiness(type).call(url, locale,java.lang.String.class,outputType, SERVICE_NAME, "store", var2,var3);
	 } 
	 //void 
  	 public void  delete(Locale locale,java.lang.String var2,java.lang.String var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	null;
  	  	FactoryBusiness.getBusiness(type).call(url, locale,null,outputType, SERVICE_NAME, "delete", var2,var3);
	 } 
	 //java.io.File 
  	 public java.io.File  getrealfile(Locale locale,java.lang.String var2,java.lang.String var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<java.io.File>() {}.getType();
  	  	return (java.io.File)FactoryBusiness.getBusiness(type).call(url, locale,java.io.File.class,outputType, SERVICE_NAME, "getRealFile", var2,var3);
	 } 
	 //java.io.File 
  	 public java.io.File  extractfile(Locale locale,java.lang.String var2,java.lang.String var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<java.io.File>() {}.getType();
  	  	return (java.io.File)FactoryBusiness.getBusiness(type).call(url, locale,java.io.File.class,outputType, SERVICE_NAME, "extractFile", var2,var3);
	 } 
	 //java.lang.String 
  	 public java.lang.String  gettipostorage(Locale locale,java.lang.String var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<java.lang.String>() {}.getType();
  	  	return (java.lang.String)FactoryBusiness.getBusiness(type).call(url, locale,java.lang.String.class,outputType, SERVICE_NAME, "getTipoStorage", var2);
	 } 
	 //java.lang.String 
  	 public java.lang.String  getconfigurazionistorage(Locale locale,java.lang.String var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<java.lang.String>() {}.getType();
  	  	return (java.lang.String)FactoryBusiness.getBusiness(type).call(url, locale,java.lang.String.class,outputType, SERVICE_NAME, "getConfigurazioniStorage", var2);
	 } 
}    