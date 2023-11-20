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
public class AlboPretorioReggioImpl {
		
	private static final String SERVICE_NAME = "AlboPretorioReggioImpl";	
	private static Logger mLogger = Logger.getLogger(AlboPretorioReggioImpl.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>  nuovoatto(Locale locale,it.eng.document.function.bean.alboreggio.AlboReggioAtto var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "nuovoAtto", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioUtente>  getutente(Locale locale) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioUtente>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioUtente>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "getUtente", null);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>  nuovoutente(Locale locale,it.eng.document.function.bean.alboreggio.AlboReggioUtente var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "nuovoUtente", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>  modificauo(Locale locale,it.eng.document.function.bean.alboreggio.AlboReggioUO var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "modificaUO", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>  modificautente(Locale locale,it.eng.document.function.bean.alboreggio.AlboReggioUtente var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "modificaUtente", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>  nuovauo(Locale locale,it.eng.document.function.bean.alboreggio.AlboReggioUO var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "nuovaUO", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>  eliminauo(Locale locale,it.eng.document.function.bean.alboreggio.AlboReggioUO var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "eliminaUO", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>  modificatipoatto(Locale locale,it.eng.document.function.bean.alboreggio.AlboReggioAtto var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "modificaTipoAtto", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>  nuovotipoatto(Locale locale,it.eng.document.function.bean.alboreggio.AlboReggioAtto var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "nuovoTipoAtto", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>  eliminatipoatto(Locale locale,it.eng.document.function.bean.alboreggio.AlboReggioAtto var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "eliminaTipoAtto", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>  relataatto(Locale locale,java.lang.String var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "relataAtto", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>  annullaatto(Locale locale,it.eng.document.function.bean.alboreggio.AlboReggioAtto var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "annullaAtto", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>  eliminaatto(Locale locale,it.eng.document.function.bean.alboreggio.AlboReggioAtto var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "eliminaAtto", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>  modificaatto(Locale locale,it.eng.document.function.bean.alboreggio.AlboReggioAtto var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "modificaAtto", var2);
	 } 
	 //it.eng.document.function.bean.alboreggio.AlboReggioAttiResult 
  	 public it.eng.document.function.bean.alboreggio.AlboReggioAttiResult  listaatto(Locale locale,java.lang.String var2,int var3,int var4,java.util.Calendar var5,java.util.Calendar var6,java.lang.String var7,java.lang.String var8) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.alboreggio.AlboReggioAttiResult>() {}.getType();
  	  	return (it.eng.document.function.bean.alboreggio.AlboReggioAttiResult)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.alboreggio.AlboReggioAttiResult.class,outputType, SERVICE_NAME, "listaAtto", var2,var3,var4,var5,var6,var7,var8);
	 } 
	 //it.eng.document.function.bean.alboreggio.AlboReggioAllegatiResult 
  	 public it.eng.document.function.bean.alboreggio.AlboReggioAllegatiResult  listaallegato(Locale locale,java.lang.String var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.alboreggio.AlboReggioAllegatiResult>() {}.getType();
  	  	return (it.eng.document.function.bean.alboreggio.AlboReggioAllegatiResult)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.alboreggio.AlboReggioAllegatiResult.class,outputType, SERVICE_NAME, "listaAllegato", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>  eliminaallegato(Locale locale,it.eng.document.function.bean.alboreggio.AlboReggioAllegato var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "eliminaAllegato", var2);
	 } 
	 //it.eng.document.function.bean.alboreggio.AlboReggioTipoAttiResult 
  	 public it.eng.document.function.bean.alboreggio.AlboReggioTipoAttiResult  listatipoatto(Locale locale,java.lang.String var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.alboreggio.AlboReggioTipoAttiResult>() {}.getType();
  	  	return (it.eng.document.function.bean.alboreggio.AlboReggioTipoAttiResult)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.alboreggio.AlboReggioTipoAttiResult.class,outputType, SERVICE_NAME, "listaTipoAtto", var2);
	 } 
	 //it.eng.document.function.bean.alboreggio.AlboReggioUtentiResult 
  	 public it.eng.document.function.bean.alboreggio.AlboReggioUtentiResult  listautenti(Locale locale,java.lang.String var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.alboreggio.AlboReggioUtentiResult>() {}.getType();
  	  	return (it.eng.document.function.bean.alboreggio.AlboReggioUtentiResult)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.alboreggio.AlboReggioUtentiResult.class,outputType, SERVICE_NAME, "listaUtenti", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>  modificaallegato(Locale locale,it.eng.document.function.bean.alboreggio.AlboReggioAllegato var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "modificaAllegato", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>  nuovoallegato(Locale locale,it.eng.document.function.bean.alboreggio.AlboReggioAllegato var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.alboreggio.AlboReggioResult>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "nuovoAllegato", var2);
	 } 
	 //it.eng.document.function.bean.alboreggio.AlboReggioUOResult 
  	 public it.eng.document.function.bean.alboreggio.AlboReggioUOResult  listauo(Locale locale,java.lang.String var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.alboreggio.AlboReggioUOResult>() {}.getType();
  	  	return (it.eng.document.function.bean.alboreggio.AlboReggioUOResult)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.alboreggio.AlboReggioUOResult.class,outputType, SERVICE_NAME, "listaUO", var2);
	 } 
}    