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
public class GestioneFonti {
		
	private static final String SERVICE_NAME = "GestioneFonti";	
	private static Logger mLogger = Logger.getLogger(GestioneFonti.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.document.function.bean.VersionaDocumentoOutBean 
  	 public it.eng.document.function.bean.VersionaDocumentoOutBean  versionadocumento(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.VersionaDocumentoInBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.VersionaDocumentoOutBean>() {}.getType();
  	  	return (it.eng.document.function.bean.VersionaDocumentoOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.VersionaDocumentoOutBean.class,outputType, SERVICE_NAME, "versionaDocumento", var2,var3);
	 } 
	 //it.eng.document.function.bean.AggiungiDocumentoOutBean 
  	 public it.eng.document.function.bean.AggiungiDocumentoOutBean  aggiungidocumento(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.AggiungiDocumentoInBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.AggiungiDocumentoOutBean>() {}.getType();
  	  	return (it.eng.document.function.bean.AggiungiDocumentoOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.AggiungiDocumentoOutBean.class,outputType, SERVICE_NAME, "aggiungiDocumento", var2,var3);
	 } 
	 //it.eng.document.function.bean.CreaModFonteOutBean 
  	 public it.eng.document.function.bean.CreaModFonteOutBean  modificafonte(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,java.math.BigDecimal var3,it.eng.document.function.bean.CreaModFonteInBean var4,it.eng.document.function.bean.CreaModDocumentoInBean var5,it.eng.document.function.bean.FilePrimarioBean var6) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.CreaModFonteOutBean>() {}.getType();
  	  	return (it.eng.document.function.bean.CreaModFonteOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.CreaModFonteOutBean.class,outputType, SERVICE_NAME, "modificaFonte", var2,var3,var4,var5,var6);
	 } 
	 //it.eng.document.function.bean.CreaModFonteOutBean 
  	 public it.eng.document.function.bean.CreaModFonteOutBean  creafonte(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.CreaModFonteInBean var3,it.eng.document.function.bean.CreaModDocumentoInBean var4,it.eng.document.function.bean.FilePrimarioBean var5) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.CreaModFonteOutBean>() {}.getType();
  	  	return (it.eng.document.function.bean.CreaModFonteOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.CreaModFonteOutBean.class,outputType, SERVICE_NAME, "creaFonte", var2,var3,var4,var5);
	 } 
}    