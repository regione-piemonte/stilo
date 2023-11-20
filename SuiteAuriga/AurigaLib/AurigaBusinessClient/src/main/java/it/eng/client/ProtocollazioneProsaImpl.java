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
public class ProtocollazioneProsaImpl {
		
	private static final String SERVICE_NAME = "ProtocollazioneProsaImpl";	
	private static Logger mLogger = Logger.getLogger(ProtocollazioneProsaImpl.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.document.function.bean.prosa.ProsaAllegatiResult 
  	 public it.eng.document.function.bean.prosa.ProsaAllegatiResult  getallegati(Locale locale,long var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.prosa.ProsaAllegatiResult>() {}.getType();
  	  	return (it.eng.document.function.bean.prosa.ProsaAllegatiResult)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.prosa.ProsaAllegatiResult.class,outputType, SERVICE_NAME, "getAllegati", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.prosa.ProsaDocumentoProtocollato>  protocolla(Locale locale,it.eng.document.function.bean.prosa.ProsaDocumentoProtocollato var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.prosa.ProsaDocumentoProtocollato>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.prosa.ProsaDocumentoProtocollato>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "protocolla", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.prosa.ProsaAssegnazione>  assegna(Locale locale,it.eng.document.function.bean.prosa.ProsaAssegnazione var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.prosa.ProsaAssegnazione>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.prosa.ProsaAssegnazione>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "assegna", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.prosa.ProsaAllegato>  getallegato(Locale locale,long var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.prosa.ProsaAllegato>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.prosa.ProsaAllegato>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "getAllegato", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<java.lang.String>  testlogin(Locale locale) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<java.lang.String>>() {}.getType();
  	  	return (it.eng.bean.Result<java.lang.String>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "testLogin", null);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<java.lang.String>  teststato(Locale locale) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<java.lang.String>>() {}.getType();
  	  	return (it.eng.bean.Result<java.lang.String>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "testStato", null);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.prosa.ProsaAllegato>  rimuoviallegato(Locale locale,long var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.prosa.ProsaAllegato>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.prosa.ProsaAllegato>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "rimuoviAllegato", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<java.lang.String>  protocollaprofilo(Locale locale,it.eng.document.function.bean.prosa.ProsaDatiProtocollo var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<java.lang.String>>() {}.getType();
  	  	return (it.eng.bean.Result<java.lang.String>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "protocollaProfilo", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.prosa.ProsaAllegato>  inserisciallegato(Locale locale,it.eng.document.function.bean.prosa.ProsaAllegato var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.prosa.ProsaAllegato>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.prosa.ProsaAllegato>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "inserisciAllegato", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<java.lang.String>  inserisciallegatozip(Locale locale,it.eng.document.function.bean.prosa.ProsaAllegato var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<java.lang.String>>() {}.getType();
  	  	return (it.eng.bean.Result<java.lang.String>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "inserisciAllegatoZip", var2);
	 } 
	 //it.eng.bean.BytesResult 
  	 public it.eng.bean.BytesResult  getcontenutoallegato(Locale locale,long var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.BytesResult>() {}.getType();
  	  	return (it.eng.bean.BytesResult)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.BytesResult.class,outputType, SERVICE_NAME, "getContenutoAllegato", var2);
	 } 
	 //it.eng.bean.BytesResult 
  	 public it.eng.bean.BytesResult  getcontenutodocumento(Locale locale,long var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.BytesResult>() {}.getType();
  	  	return (it.eng.bean.BytesResult)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.BytesResult.class,outputType, SERVICE_NAME, "getContenutoDocumento", var2);
	 } 
	 //it.eng.document.function.bean.prosa.ProsaDocumentiProtocollatiResult 
  	 public it.eng.document.function.bean.prosa.ProsaDocumentiProtocollatiResult  ricercaprotocolli(Locale locale,it.eng.document.function.bean.prosa.ProsaRicerca var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.prosa.ProsaDocumentiProtocollatiResult>() {}.getType();
  	  	return (it.eng.document.function.bean.prosa.ProsaDocumentiProtocollatiResult)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.prosa.ProsaDocumentiProtocollatiResult.class,outputType, SERVICE_NAME, "ricercaProtocolli", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.prosa.ProsaImmagineDocumentale>  inseriscicontenuto(Locale locale,it.eng.document.function.bean.prosa.ProsaImmagineDocumentale var2,boolean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.prosa.ProsaImmagineDocumentale>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.prosa.ProsaImmagineDocumentale>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "inserisciContenuto", var2,var3);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.prosa.ProsaImmagineDocumentale>  recuperacontenuto(Locale locale,long var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.prosa.ProsaImmagineDocumentale>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.prosa.ProsaImmagineDocumentale>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "recuperaContenuto", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<java.lang.String>  inserisciallegatodafascicolo(Locale locale,java.lang.Long var2,it.eng.document.function.bean.prosa.ProsaAllegato var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<java.lang.String>>() {}.getType();
  	  	return (it.eng.bean.Result<java.lang.String>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "inserisciAllegatoDaFascicolo", var2,var3);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<java.lang.String>  inseriscicontenutodadocumentale(Locale locale,java.lang.Long var2,java.lang.Long var3,boolean var4) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<java.lang.String>>() {}.getType();
  	  	return (it.eng.bean.Result<java.lang.String>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "inserisciContenutoDaDocumentale", var2,var3,var4);
	 } 
	 //it.eng.document.function.bean.prosa.ProsaVociTitolarioResult 
  	 public it.eng.document.function.bean.prosa.ProsaVociTitolarioResult  ricercatitolariopercodicedescrizione(Locale locale,it.eng.document.function.bean.prosa.ProsaVoceTitolario var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.prosa.ProsaVociTitolarioResult>() {}.getType();
  	  	return (it.eng.document.function.bean.prosa.ProsaVociTitolarioResult)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.prosa.ProsaVociTitolarioResult.class,outputType, SERVICE_NAME, "ricercaTitolarioPerCodiceDescrizione", var2);
	 } 
}    