/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.client;

import java.lang.reflect.Type;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.google.gson.reflect.TypeToken;

import it.eng.config.AurigaBusinessClientConfig;
import it.eng.core.service.client.FactoryBusiness;
import it.eng.core.service.client.FactoryBusiness.BusinessType;

/**
 * @author ServiceClient generator 1.0.4
 */
public class PubblicazioniImpl {
	
	private static final String SERVICE_NAME = "PubblicazioniImpl";	
	private static Logger mLogger = Logger.getLogger(PubblicazioniImpl.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();
	
	//it.eng.document.function.bean.PubblicazioneResponse
	public it.eng.document.function.bean.PubblicazioneResponse  notificaEsito(Locale locale,it.eng.document.function.bean.PubblicazioneNotificaEsitoRequest var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.PubblicazioneResponse>() {}.getType();
	  	return (it.eng.document.function.bean.PubblicazioneResponse)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.PubblicazioneResponse.class,outputType, SERVICE_NAME, "notificaEsito", var2);
	}
	
}
