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
public class EsportazioneDocActaImpl {
		
	private static final String SERVICE_NAME = "EsportazioneDocActaImpl";	
	private static Logger mLogger = Logger.getLogger(EsportazioneDocActaImpl.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.document.function.bean.acta.ActaOutputGetClassificazioneEstesa 
  	 public it.eng.document.function.bean.acta.ActaOutputGetClassificazioneEstesa  getclassificazioneestesa(Locale locale,it.eng.document.function.bean.acta.ActaInputGetClassificazioneEstesa var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.acta.ActaOutputGetClassificazioneEstesa>() {}.getType();
  	  	return (it.eng.document.function.bean.acta.ActaOutputGetClassificazioneEstesa)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.acta.ActaOutputGetClassificazioneEstesa.class,outputType, SERVICE_NAME, "getClassificazioneEstesa", var2);
	 } 
	 //it.eng.document.function.bean.acta.ActaOutputGetDestinatariSmistamento 
  	 public it.eng.document.function.bean.acta.ActaOutputGetDestinatariSmistamento  getdestinatarismistamento(Locale locale,it.eng.document.function.bean.acta.ActaInputGetDestinatariSmistamento var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.acta.ActaOutputGetDestinatariSmistamento>() {}.getType();
  	  	return (it.eng.document.function.bean.acta.ActaOutputGetDestinatariSmistamento)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.acta.ActaOutputGetDestinatariSmistamento.class,outputType, SERVICE_NAME, "getDestinatariSmistamento", var2);
	 } 
	 //it.eng.document.function.bean.acta.ActaOutputGetFascicoloDossierInVoceTitolario 
  	 public it.eng.document.function.bean.acta.ActaOutputGetFascicoloDossierInVoceTitolario  getfascicolodossierinvocetitolario(Locale locale,it.eng.document.function.bean.acta.ActaInputGetFascicoloDossierInVoceTitolario var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.acta.ActaOutputGetFascicoloDossierInVoceTitolario>() {}.getType();
  	  	return (it.eng.document.function.bean.acta.ActaOutputGetFascicoloDossierInVoceTitolario)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.acta.ActaOutputGetFascicoloDossierInVoceTitolario.class,outputType, SERVICE_NAME, "getFascicoloDossierInVoceTitolario", var2);
	 } 
}    