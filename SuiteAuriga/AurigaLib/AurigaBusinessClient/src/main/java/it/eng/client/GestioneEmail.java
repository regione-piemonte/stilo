/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import java.util.Locale;
import it.eng.core.service.client.FactoryBusiness;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import it.eng.config.AurigaBusinessClientConfig;
import it.eng.core.service.client.FactoryBusiness.BusinessType;
import org.apache.log4j.Logger;

/**
 * @author ServiceClient generator 0.0.4-SNAPSHOT
 */
public class GestioneEmail {
		
	private static final String SERVICE_NAME = "GestioneEmail";	
	private static Logger mLogger = Logger.getLogger(GestioneEmail.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.document.function.bean.CreaModDocumentoOutBean 
  	 public it.eng.document.function.bean.CreaModDocumentoOutBean  creadocumento(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.MailDocumentoIn var3,it.eng.document.function.bean.FilePrimarioBean var4,it.eng.document.function.bean.AllegatiBean var5,it.eng.document.function.bean.AttachAndPosizioneCollectionBean var6) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.CreaModDocumentoOutBean>() {}.getType();
  	  	return (it.eng.document.function.bean.CreaModDocumentoOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.CreaModDocumentoOutBean.class,outputType, SERVICE_NAME, "creaDocumento", var2,var3,var4,var5,var6);
	 } 
}    