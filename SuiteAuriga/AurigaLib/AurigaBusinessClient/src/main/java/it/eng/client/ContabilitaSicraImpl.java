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
public class ContabilitaSicraImpl {
		
	private static final String SERVICE_NAME = "ContabilitaSicraImpl";	
	private static Logger mLogger = Logger.getLogger(ContabilitaSicraImpl.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.document.function.bean.sicra.SicraOutputArchiviaAtto 
  	 public it.eng.document.function.bean.sicra.SicraOutputArchiviaAtto  sicraarchiviaatto(Locale locale,it.eng.document.function.bean.sicra.SicraInputArchiviaAtto var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.sicra.SicraOutputArchiviaAtto>() {}.getType();
  	  	return (it.eng.document.function.bean.sicra.SicraOutputArchiviaAtto)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.sicra.SicraOutputArchiviaAtto.class,outputType, SERVICE_NAME, "sicraArchiviaAtto", var2);
	 } 
	 //it.eng.document.function.bean.sicra.SicraOutputAggiornaRifAttoLiquidazione 
  	 public it.eng.document.function.bean.sicra.SicraOutputAggiornaRifAttoLiquidazione  sicraaggiornarifattoliquidazione(Locale locale,it.eng.document.function.bean.sicra.SicraInputAggiornaRifAttoLiquidazione var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.sicra.SicraOutputAggiornaRifAttoLiquidazione>() {}.getType();
  	  	return (it.eng.document.function.bean.sicra.SicraOutputAggiornaRifAttoLiquidazione)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.sicra.SicraOutputAggiornaRifAttoLiquidazione.class,outputType, SERVICE_NAME, "sicraAggiornaRifAttoLiquidazione", var2);
	 } 
	 //it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto 
  	 public it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto  sicraaggiornamentomovimentiatto(Locale locale,it.eng.document.function.bean.sicra.SicraInputSetMovimentiAtto var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto>() {}.getType();
  	  	return (it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto.class,outputType, SERVICE_NAME, "sicraAggiornamentoMovimentiAtto", var2);
	 } 
	 //it.eng.document.function.bean.sicra.SicraOutputRicercaAttoLiquidazione 
  	 public it.eng.document.function.bean.sicra.SicraOutputRicercaAttoLiquidazione  sicraricercaattoliquidazione(Locale locale,it.eng.document.function.bean.sicra.SicraInputRicercaAttoLiquidazione var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.sicra.SicraOutputRicercaAttoLiquidazione>() {}.getType();
  	  	return (it.eng.document.function.bean.sicra.SicraOutputRicercaAttoLiquidazione)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.sicra.SicraOutputRicercaAttoLiquidazione.class,outputType, SERVICE_NAME, "sicraRicercaAttoLiquidazione", var2);
	 } 
	 //it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto 
  	 public it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto  sicracancellazionemovimentiatto(Locale locale,it.eng.document.function.bean.sicra.SicraInputSetMovimentiAtto var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto>() {}.getType();
  	  	return (it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto.class,outputType, SERVICE_NAME, "sicraCancellazioneMovimentiAtto", var2);
	 } 
	 //it.eng.document.function.bean.sicra.SicraOutputRicercaVociBilancio 
  	 public it.eng.document.function.bean.sicra.SicraOutputRicercaVociBilancio  sicraricercavocibilancio(Locale locale,it.eng.document.function.bean.sicra.SicraInputRicercaVociBilancio var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.sicra.SicraOutputRicercaVociBilancio>() {}.getType();
  	  	return (it.eng.document.function.bean.sicra.SicraOutputRicercaVociBilancio)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.sicra.SicraOutputRicercaVociBilancio.class,outputType, SERVICE_NAME, "sicraRicercaVociBilancio", var2);
	 } 
	 //it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto 
  	 public it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto  sicrainserimentomovimentiatto(Locale locale,it.eng.document.function.bean.sicra.SicraInputSetMovimentiAtto var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto>() {}.getType();
  	  	return (it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto.class,outputType, SERVICE_NAME, "sicraInserimentoMovimentiAtto", var2);
	 } 
	 //it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto 
  	 public it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto  sicrasetmovimentiatto(Locale locale,it.eng.document.function.bean.sicra.SicraInputSetMovimentiAtto var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto>() {}.getType();
  	  	return (it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto.class,outputType, SERVICE_NAME, "sicraSetMovimentiAtto", var2);
	 } 
	 //it.eng.document.function.bean.sicra.SicraOutputSetEsecutivitaAtto 
  	 public it.eng.document.function.bean.sicra.SicraOutputSetEsecutivitaAtto  sicrasetesecutivitaatto(Locale locale,it.eng.document.function.bean.sicra.SicraInputSetEsecutivitaAtto var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.sicra.SicraOutputSetEsecutivitaAtto>() {}.getType();
  	  	return (it.eng.document.function.bean.sicra.SicraOutputSetEsecutivitaAtto)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.sicra.SicraOutputSetEsecutivitaAtto.class,outputType, SERVICE_NAME, "sicraSetEsecutivitaAtto", var2);
	 } 
	 //it.eng.document.function.bean.sicra.SicraOutputRicercaAnagrafica 
  	 public it.eng.document.function.bean.sicra.SicraOutputRicercaAnagrafica  sicraricercaanagrafica(Locale locale,it.eng.document.function.bean.sicra.SicraInputRicercaAnagrafica var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.sicra.SicraOutputRicercaAnagrafica>() {}.getType();
  	  	return (it.eng.document.function.bean.sicra.SicraOutputRicercaAnagrafica)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.sicra.SicraOutputRicercaAnagrafica.class,outputType, SERVICE_NAME, "sicraRicercaAnagrafica", var2);
	 } 
	 //it.eng.document.function.bean.sicra.SicraOutputRicercaOrdinativoAttoLiquidazione 
  	 public it.eng.document.function.bean.sicra.SicraOutputRicercaOrdinativoAttoLiquidazione  sicraricercaordinativoattoliquidazione(Locale locale,it.eng.document.function.bean.sicra.SicraInputRicercaOrdinativoAttoLiquidazione var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.sicra.SicraOutputRicercaOrdinativoAttoLiquidazione>() {}.getType();
  	  	return (it.eng.document.function.bean.sicra.SicraOutputRicercaOrdinativoAttoLiquidazione)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.sicra.SicraOutputRicercaOrdinativoAttoLiquidazione.class,outputType, SERVICE_NAME, "sicraRicercaOrdinativoAttoLiquidazione", var2);
	 } 
}    