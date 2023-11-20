/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import java.util.Locale;
import it.eng.core.service.client.FactoryBusiness;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import it.eng.config.AurigaMailBusinessClientConfig;
import it.eng.core.service.client.FactoryBusiness.BusinessType;
import org.apache.log4j.Logger;

/**
 * @author ServiceClient generator 1.0.4
 */
public class NotificheManager {
		
	private static final String SERVICE_NAME = "NotificheManager";	
	private static Logger mLogger = Logger.getLogger(NotificheManager.class);
	
	private String url = AurigaMailBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaMailBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.aurigamailbusiness.bean.ResultBean 
  	 public it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.InfoNotificheBean>  invianotifiche(Locale locale,it.eng.aurigamailbusiness.bean.VerificaInvioMailNotificaBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.InfoNotificheBean>>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.InfoNotificheBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.ResultBean.class,outputType, SERVICE_NAME, "inviaNotifiche", var2);
	 } 
}    