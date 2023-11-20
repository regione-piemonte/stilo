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
public class ContabilitaAttiImpl {
		
	private static final String SERVICE_NAME = "ContabilitaAttiImpl";	
	private static Logger mLogger = Logger.getLogger(ContabilitaAttiImpl.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.document.function.bean.ContabilitaAdspGetCapitoliBPResponse 
  	 public it.eng.document.function.bean.ContabilitaAdspGetCapitoliBPResponse  ricercacapitolibp(Locale locale,it.eng.document.function.bean.ContabilitaAdspGetCapitoliBPRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabilitaAdspGetCapitoliBPResponse>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabilitaAdspGetCapitoliBPResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabilitaAdspGetCapitoliBPResponse.class,outputType, SERVICE_NAME, "ricercaCapitoliBP", var2);
	 } 
	 //it.eng.document.function.bean.ContabilitaAdspDeleteRicResponse 
  	 public it.eng.document.function.bean.ContabilitaAdspDeleteRicResponse  cancellarichiesta(Locale locale,it.eng.document.function.bean.ContabilitaAdspDeleteRicRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabilitaAdspDeleteRicResponse>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabilitaAdspDeleteRicResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabilitaAdspDeleteRicResponse.class,outputType, SERVICE_NAME, "cancellaRichiesta", var2);
	 } 
	 //it.eng.document.function.bean.ContabilitaAdspInsertRicResponse 
  	 public it.eng.document.function.bean.ContabilitaAdspInsertRicResponse  inseriscirichiesta(Locale locale,it.eng.document.function.bean.ContabilitaAdspInsertRicRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabilitaAdspInsertRicResponse>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabilitaAdspInsertRicResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabilitaAdspInsertRicResponse.class,outputType, SERVICE_NAME, "inserisciRichiesta", var2);
	 } 
	 //it.eng.document.function.bean.ContabilitaAdspUpdateRicResponse 
  	 public it.eng.document.function.bean.ContabilitaAdspUpdateRicResponse  aggiornarichiesta(Locale locale,it.eng.document.function.bean.ContabilitaAdspUpdateRicRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabilitaAdspUpdateRicResponse>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabilitaAdspUpdateRicResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabilitaAdspUpdateRicResponse.class,outputType, SERVICE_NAME, "aggiornaRichiesta", var2);
	 } 
	 //it.eng.document.function.bean.ContabilitaAdspTokenResponse 
  	 public it.eng.document.function.bean.ContabilitaAdspTokenResponse  generatoken(Locale locale) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabilitaAdspTokenResponse>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabilitaAdspTokenResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabilitaAdspTokenResponse.class,outputType, SERVICE_NAME, "generaToken", null);
	 } 
	 //it.eng.document.function.bean.ContabilitaAdspTokenResponse 
  	 public it.eng.document.function.bean.ContabilitaAdspTokenResponse  checktoken(Locale locale,it.eng.document.function.bean.ContabilitaAdspTokenRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabilitaAdspTokenResponse>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabilitaAdspTokenResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabilitaAdspTokenResponse.class,outputType, SERVICE_NAME, "checkToken", var2);
	 } 
	 //it.eng.document.function.bean.ContabilitaAdspGetRichiestaResponse 
  	 public it.eng.document.function.bean.ContabilitaAdspGetRichiestaResponse  ricercarichiesta(Locale locale,it.eng.document.function.bean.ContabilitaAdspInsertRicRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabilitaAdspGetRichiestaResponse>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabilitaAdspGetRichiestaResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabilitaAdspGetRichiestaResponse.class,outputType, SERVICE_NAME, "ricercaRichiesta", var2);
	 } 
	 //it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse 
  	 public it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse  attoadottato(Locale locale,it.eng.document.function.bean.ContabilitaAdspUpdateAttoRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse.class,outputType, SERVICE_NAME, "attoAdottato", var2);
	 } 
	 //it.eng.document.function.bean.ContabilitaAdspInsertAttoResponse 
  	 public it.eng.document.function.bean.ContabilitaAdspInsertAttoResponse  inserisciattoric(Locale locale,it.eng.document.function.bean.ContabilitaAdspInsertAttoRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabilitaAdspInsertAttoResponse>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabilitaAdspInsertAttoResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabilitaAdspInsertAttoResponse.class,outputType, SERVICE_NAME, "inserisciAttoRic", var2);
	 } 
	 //it.eng.document.function.bean.ContabilitaAdspTokenResponse 
  	 public it.eng.document.function.bean.ContabilitaAdspTokenResponse  destroytoken(Locale locale,it.eng.document.function.bean.ContabilitaAdspTokenRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabilitaAdspTokenResponse>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabilitaAdspTokenResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabilitaAdspTokenResponse.class,outputType, SERVICE_NAME, "destroyToken", var2);
	 } 
	 //it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse 
  	 public it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse  attoinbozza(Locale locale,it.eng.document.function.bean.ContabilitaAdspUpdateAttoRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse.class,outputType, SERVICE_NAME, "attoInBozza", var2);
	 } 
	 //it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse 
  	 public it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse  attoconfermato(Locale locale,it.eng.document.function.bean.ContabilitaAdspUpdateAttoRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse.class,outputType, SERVICE_NAME, "attoConfermato", var2);
	 } 
	 //it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse 
  	 public it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse  attesaconformita(Locale locale,it.eng.document.function.bean.ContabilitaAdspUpdateAttoRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse.class,outputType, SERVICE_NAME, "attesaConformita", var2);
	 } 
	 //it.eng.document.function.bean.ContabilitaAdspGetAttoResponse 
  	 public it.eng.document.function.bean.ContabilitaAdspGetAttoResponse  ricercaatto(Locale locale,it.eng.document.function.bean.ContabilitaAdspGetAttoRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabilitaAdspGetAttoResponse>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabilitaAdspGetAttoResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabilitaAdspGetAttoResponse.class,outputType, SERVICE_NAME, "ricercaAtto", var2);
	 } 
	 //it.eng.document.function.bean.ContabilitaAdspDeleteAttoResponse 
  	 public it.eng.document.function.bean.ContabilitaAdspDeleteAttoResponse  cancellaatto(Locale locale,it.eng.document.function.bean.ContabilitaAdspDeleteAttoRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabilitaAdspDeleteAttoResponse>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabilitaAdspDeleteAttoResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabilitaAdspDeleteAttoResponse.class,outputType, SERVICE_NAME, "cancellaAtto", var2);
	 } 
	 //it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse 
  	 public it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse  attovalidato(Locale locale,it.eng.document.function.bean.ContabilitaAdspUpdateAttoRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabilitaAdspUpdateAttoResponse.class,outputType, SERVICE_NAME, "attoValidato", var2);
	 } 
}    