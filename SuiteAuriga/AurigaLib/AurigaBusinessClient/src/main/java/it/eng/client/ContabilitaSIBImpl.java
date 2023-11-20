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
public class ContabilitaSIBImpl {
		
	private static final String SERVICE_NAME = "ContabilitaSIBImpl";	
	private static Logger mLogger = Logger.getLogger(ContabilitaSIBImpl.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.document.function.bean.SibOutputGetVociPeg 
  	 public it.eng.document.function.bean.SibOutputGetVociPeg  sibgetvocipegcapitolo(Locale locale,it.eng.document.function.bean.SibInputGetVociPeg var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.SibOutputGetVociPeg>() {}.getType();
  	  	return (it.eng.document.function.bean.SibOutputGetVociPeg)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.SibOutputGetVociPeg.class,outputType, SERVICE_NAME, "sibGetVociPegCapitolo", var2);
	 } 
	 //it.eng.document.function.bean.SibOutputGetVociPeg 
  	 public it.eng.document.function.bean.SibOutputGetVociPeg  sibgetvocipegvliv(Locale locale,it.eng.document.function.bean.SibInputGetVociPegVLiv var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.SibOutputGetVociPeg>() {}.getType();
  	  	return (it.eng.document.function.bean.SibOutputGetVociPeg)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.SibOutputGetVociPeg.class,outputType, SERVICE_NAME, "sibGetVociPegVLiv", var2);
	 } 
	 //it.eng.document.function.bean.SibOutputGetCapitolo 
  	 public it.eng.document.function.bean.SibOutputGetCapitolo  sibgetcapitolo(Locale locale,it.eng.document.function.bean.SibInputGetCapitolo var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.SibOutputGetCapitolo>() {}.getType();
  	  	return (it.eng.document.function.bean.SibOutputGetCapitolo)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.SibOutputGetCapitolo.class,outputType, SERVICE_NAME, "sibGetCapitolo", var2);
	 } 
	 //it.eng.document.function.bean.SibOutputAggiornaAtto 
  	 public it.eng.document.function.bean.SibOutputAggiornaAtto  sibaggiornaatto(Locale locale,it.eng.document.function.bean.SibInputAggiornaAtto var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.SibOutputAggiornaAtto>() {}.getType();
  	  	return (it.eng.document.function.bean.SibOutputAggiornaAtto)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.SibOutputAggiornaAtto.class,outputType, SERVICE_NAME, "sibAggiornaAtto", var2);
	 } 
	 //it.eng.document.function.bean.SibOutputCreaProposta 
  	 public it.eng.document.function.bean.SibOutputCreaProposta  sibcreaproposta(Locale locale,it.eng.document.function.bean.SibInputCreaProposta var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.SibOutputCreaProposta>() {}.getType();
  	  	return (it.eng.document.function.bean.SibOutputCreaProposta)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.SibOutputCreaProposta.class,outputType, SERVICE_NAME, "sibCreaProposta", var2);
	 } 
	 //it.eng.document.function.bean.SibOutputElencoDettagliContabili 
  	 public it.eng.document.function.bean.SibOutputElencoDettagliContabili  sibelencodettaglicontabili(Locale locale,it.eng.document.function.bean.SibInputElencoDettagliContabili var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.SibOutputElencoDettagliContabili>() {}.getType();
  	  	return (it.eng.document.function.bean.SibOutputElencoDettagliContabili)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.SibOutputElencoDettagliContabili.class,outputType, SERVICE_NAME, "sibElencoDettagliContabili", var2);
	 } 
	 //it.eng.document.function.bean.SibOutputGetVociPeg 
  	 public it.eng.document.function.bean.SibOutputGetVociPeg  sibgetvocipeg(Locale locale,it.eng.document.function.bean.SibInputGetVociPeg var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.SibOutputGetVociPeg>() {}.getType();
  	  	return (it.eng.document.function.bean.SibOutputGetVociPeg)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.SibOutputGetVociPeg.class,outputType, SERVICE_NAME, "sibGetVociPeg", var2);
	 } 
}    