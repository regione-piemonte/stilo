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
public class GestioneFascicoli {
		
	private static final String SERVICE_NAME = "GestioneFascicoli";	
	private static Logger mLogger = Logger.getLogger(GestioneFascicoli.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.document.function.bean.ModificaFascicoloOut 
  	 public it.eng.document.function.bean.ModificaFascicoloOut  modificafascicolows(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,java.lang.String var3,java.lang.String var4) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ModificaFascicoloOut>() {}.getType();
  	  	return (it.eng.document.function.bean.ModificaFascicoloOut)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ModificaFascicoloOut.class,outputType, SERVICE_NAME, "modificaFascicoloWS", var2,var3,var4);
	 } 
	 //it.eng.document.function.bean.ModificaFascicoloOut 
  	 public it.eng.document.function.bean.ModificaFascicoloOut  modificafascicolo(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.ModificaFascicoloIn var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ModificaFascicoloOut>() {}.getType();
  	  	return (it.eng.document.function.bean.ModificaFascicoloOut)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ModificaFascicoloOut.class,outputType, SERVICE_NAME, "modificaFascicolo", var2,var3);
	 } 
	 //it.eng.document.function.bean.CancellaFascicoloOut 
  	 public it.eng.document.function.bean.CancellaFascicoloOut  cancellafascicolo(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.CancellaFascicoloIn var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.CancellaFascicoloOut>() {}.getType();
  	  	return (it.eng.document.function.bean.CancellaFascicoloOut)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.CancellaFascicoloOut.class,outputType, SERVICE_NAME, "cancellaFascicolo", var2,var3);
	 } 
	 //it.eng.document.function.bean.SalvaFascicoloOut 
  	 public it.eng.document.function.bean.SalvaFascicoloOut  salvafascicolo(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.SalvaFascicoloIn var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.SalvaFascicoloOut>() {}.getType();
  	  	return (it.eng.document.function.bean.SalvaFascicoloOut)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.SalvaFascicoloOut.class,outputType, SERVICE_NAME, "salvaFascicolo", var2,var3);
	 } 
	 //it.eng.document.function.bean.SalvaFascicoloOut 
  	 public it.eng.document.function.bean.SalvaFascicoloOut  salvafascicolows(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,java.lang.String var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.SalvaFascicoloOut>() {}.getType();
  	  	return (it.eng.document.function.bean.SalvaFascicoloOut)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.SalvaFascicoloOut.class,outputType, SERVICE_NAME, "salvaFascicoloWS", var2,var3);
	 } 
	 //it.eng.document.function.bean.LoadFascicoloOut 
  	 public it.eng.document.function.bean.LoadFascicoloOut  loadfascicolo(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.LoadFascicoloIn var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.LoadFascicoloOut>() {}.getType();
  	  	return (it.eng.document.function.bean.LoadFascicoloOut)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.LoadFascicoloOut.class,outputType, SERVICE_NAME, "loadFascicolo", var2,var3);
	 } 
}    