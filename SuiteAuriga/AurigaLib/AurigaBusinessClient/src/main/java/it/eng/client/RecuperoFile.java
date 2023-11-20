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
public class RecuperoFile {
		
	private static final String SERVICE_NAME = "RecuperoFile";	
	private static Logger mLogger = Logger.getLogger(RecuperoFile.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.document.function.bean.FileExtractedOut 
  	 public it.eng.document.function.bean.FileExtractedOut  extractfile(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.FileExtractedIn var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.FileExtractedOut>() {}.getType();
  	  	return (it.eng.document.function.bean.FileExtractedOut)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.FileExtractedOut.class,outputType, SERVICE_NAME, "extractFile", var2,var3);
	 } 
	 //it.eng.document.function.bean.ExtractVerDocOutBean 
  	 public it.eng.document.function.bean.ExtractVerDocOutBean  extractverdoc(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,java.math.BigDecimal var3,java.math.BigDecimal var4) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ExtractVerDocOutBean>() {}.getType();
  	  	return (it.eng.document.function.bean.ExtractVerDocOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ExtractVerDocOutBean.class,outputType, SERVICE_NAME, "extractVerDoc", var2,var3,var4);
	 } 
	 //it.eng.document.function.bean.FileExtractedOut 
  	 public it.eng.document.function.bean.FileExtractedOut  extractfilebyuri(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,java.lang.String var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.FileExtractedOut>() {}.getType();
  	  	return (it.eng.document.function.bean.FileExtractedOut)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.FileExtractedOut.class,outputType, SERVICE_NAME, "extractFileByUri", var2,var3);
	 } 
	 //it.eng.document.function.bean.FileExtractedOut 
  	 public it.eng.document.function.bean.FileExtractedOut  extractfilebyiddoc(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,java.math.BigDecimal var3,java.math.BigDecimal var4) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.FileExtractedOut>() {}.getType();
  	  	return (it.eng.document.function.bean.FileExtractedOut)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.FileExtractedOut.class,outputType, SERVICE_NAME, "extractFileByIdDoc", var2,var3,var4);
	 } 
}    