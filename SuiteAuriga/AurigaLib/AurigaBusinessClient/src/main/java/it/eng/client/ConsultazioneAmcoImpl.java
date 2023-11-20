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
public class ConsultazioneAmcoImpl {
	
	private static final String SERVICE_NAME = "ConsultazioneAmcoImpl";	
	private static Logger mLogger = Logger.getLogger(ConsultazioneAmcoImpl.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();
	
	//it.eng.document.function.bean.AmcoContiCreditoDebitoResponse
	public it.eng.document.function.bean.AmcoContiCreditoDebitoResponse  ricercaContiCreditoDebito(Locale locale,it.eng.document.function.bean.AmcoContiCreditoDebitoRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.AmcoContiCreditoDebitoResponse>() {}.getType();
  	  	return (it.eng.document.function.bean.AmcoContiCreditoDebitoResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.AmcoContiCreditoDebitoResponse.class,outputType, SERVICE_NAME, "ricercaContiCreditoDebito", var2);
	}
	//it.eng.document.function.bean.AmcoContiImputazioneResponse
	public it.eng.document.function.bean.AmcoContiImputazioneResponse  ricercaContiImputazione(Locale locale,it.eng.document.function.bean.AmcoContiImputazioneRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.AmcoContiImputazioneResponse>() {}.getType();
	  	return (it.eng.document.function.bean.AmcoContiImputazioneResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.AmcoContiImputazioneResponse.class,outputType, SERVICE_NAME, "ricercaContiImputazione", var2);
	}
	//it.eng.document.function.bean.AmcoTipiDocumentoResponse
	public it.eng.document.function.bean.AmcoTipiDocumentoResponse  ricercaTipiDocumento(Locale locale,it.eng.document.function.bean.AmcoTipiDocumentoRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.AmcoTipiDocumentoResponse>() {}.getType();
	  	return (it.eng.document.function.bean.AmcoTipiDocumentoResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.AmcoTipiDocumentoResponse.class,outputType, SERVICE_NAME, "ricercaTipiDocumento", var2);
	}
	//it.eng.document.function.bean.AmcoBusinessPartnersResponse
	public it.eng.document.function.bean.AmcoBusinessPartnersResponse  ricercaBusinessPartners(Locale locale,it.eng.document.function.bean.AmcoBusinessPartnersRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.AmcoBusinessPartnersResponse>() {}.getType();
	  	return (it.eng.document.function.bean.AmcoBusinessPartnersResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.AmcoBusinessPartnersResponse.class,outputType, SERVICE_NAME, "ricercaBusinessPartners", var2);
	}
}
