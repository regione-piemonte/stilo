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
public class ConsultazioneContabiliaImpl {
		
	private static final String SERVICE_NAME = "ConsultazioneContabiliaImpl";	
	private static Logger mLogger = Logger.getLogger(ConsultazioneContabiliaImpl.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.document.function.bean.ContabiliaOutputRicercaImpegno 
  	 public it.eng.document.function.bean.ContabiliaOutputRicercaImpegno  ricercaimpegno(Locale locale,it.eng.document.function.bean.ContabiliaRicercaImpegnoRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabiliaOutputRicercaImpegno>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabiliaOutputRicercaImpegno)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabiliaOutputRicercaImpegno.class,outputType, SERVICE_NAME, "ricercaImpegno", var2);
	 } 
	 //it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi 
  	 public it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi  invioproposta(Locale locale,it.eng.document.function.bean.ContabiliaElaboraAttiAmministrativiRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi.class,outputType, SERVICE_NAME, "invioProposta", var2);
	 } 
	 //it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi 
  	 public it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi  aggiornaproposta(Locale locale,it.eng.document.function.bean.ContabiliaElaboraAttiAmministrativiRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi.class,outputType, SERVICE_NAME, "aggiornaProposta", var2);
	 } 
	 //it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi 
  	 public it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi  invioattodef(Locale locale,it.eng.document.function.bean.ContabiliaElaboraAttiAmministrativiRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi.class,outputType, SERVICE_NAME, "invioAttoDef", var2);
	 } 
	 //it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi 
  	 public it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi  invioattodefesec(Locale locale,it.eng.document.function.bean.ContabiliaElaboraAttiAmministrativiRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi.class,outputType, SERVICE_NAME, "invioAttoDefEsec", var2);
	 } 
	 //it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi 
  	 public it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi  invioattoesec(Locale locale,it.eng.document.function.bean.ContabiliaElaboraAttiAmministrativiRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi.class,outputType, SERVICE_NAME, "invioAttoEsec", var2);
	 } 
	 //it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi 
  	 public it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi  crealiquidazione(Locale locale,it.eng.document.function.bean.ContabiliaElaboraAttiAmministrativiRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi.class,outputType, SERVICE_NAME, "creaLiquidazione", var2);
	 } 
	 //it.eng.document.function.bean.ContabiliaOutputRicercaAccertamento 
  	 public it.eng.document.function.bean.ContabiliaOutputRicercaAccertamento  ricercaaccertamento(Locale locale,it.eng.document.function.bean.ContabiliaRicercaAccertamentoRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabiliaOutputRicercaAccertamento>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabiliaOutputRicercaAccertamento)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabiliaOutputRicercaAccertamento.class,outputType, SERVICE_NAME, "ricercaAccertamento", var2);
	 } 
	 //it.eng.document.function.bean.ContabiliaOutputRicercaMovimentoGestione 
  	 public it.eng.document.function.bean.ContabiliaOutputRicercaMovimentoGestione  ricercamovimentogestione(Locale locale,it.eng.document.function.bean.ContabiliaRicercaMovimentoGestioneStiloRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabiliaOutputRicercaMovimentoGestione>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabiliaOutputRicercaMovimentoGestione)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabiliaOutputRicercaMovimentoGestione.class,outputType, SERVICE_NAME, "ricercaMovimentoGestione", var2);
	 } 
	 //it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi 
  	 public it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi  bloccodatiproposta(Locale locale,it.eng.document.function.bean.ContabiliaElaboraAttiAmministrativiRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi.class,outputType, SERVICE_NAME, "bloccoDatiProposta", var2);
	 } 
	 //it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi 
  	 public it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi  sbloccodatiproposta(Locale locale,it.eng.document.function.bean.ContabiliaElaboraAttiAmministrativiRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi.class,outputType, SERVICE_NAME, "sbloccoDatiProposta", var2);
	 } 
	 //it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi 
  	 public it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi  annullamentoproposta(Locale locale,it.eng.document.function.bean.ContabiliaElaboraAttiAmministrativiRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi>() {}.getType();
  	  	return (it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi.class,outputType, SERVICE_NAME, "annullamentoProposta", var2);
	 } 
}    