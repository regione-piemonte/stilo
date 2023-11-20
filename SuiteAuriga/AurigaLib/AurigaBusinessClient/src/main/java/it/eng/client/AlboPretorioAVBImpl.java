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
public class AlboPretorioAVBImpl {
		
	private static final String SERVICE_NAME = "AlboPretorioAVBImpl";	
	private static Logger mLogger = Logger.getLogger(AlboPretorioAVBImpl.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.alboavb.AlboAVBPubblicaAttoResponseReturn>  pubblicaatto(Locale locale,it.eng.document.function.bean.alboavb.AlboAVBPubblicaAttoIn var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.alboavb.AlboAVBPubblicaAttoResponseReturn>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.alboavb.AlboAVBPubblicaAttoResponseReturn>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "pubblicaAtto", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.alboavb.AlboAVBSalvaAllegatoResponseReturn>  salvaallegato(Locale locale,it.eng.document.function.bean.alboavb.AlboAVBSalvaAllegatoIn var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.alboavb.AlboAVBSalvaAllegatoResponseReturn>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.alboavb.AlboAVBSalvaAllegatoResponseReturn>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "salvaAllegato", var2);
	 } 
	 //it.eng.bean.Result 
  	 public it.eng.bean.Result<it.eng.document.function.bean.alboavb.AlboAVBPubblicaAttoByEnteResponseReturn>  pubblicaattobyente(Locale locale,it.eng.document.function.bean.alboavb.AlboAVBPubblicaAttoByEnteIn var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.bean.Result<it.eng.document.function.bean.alboavb.AlboAVBPubblicaAttoByEnteResponseReturn>>() {}.getType();
  	  	return (it.eng.bean.Result<it.eng.document.function.bean.alboavb.AlboAVBPubblicaAttoByEnteResponseReturn>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.bean.Result.class,outputType, SERVICE_NAME, "pubblicaAttoByEnte", var2);
	 } 
}    