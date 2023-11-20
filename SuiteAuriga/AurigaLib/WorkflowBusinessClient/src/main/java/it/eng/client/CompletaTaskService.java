/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import java.util.Locale;
import it.eng.core.service.client.FactoryBusiness;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import it.eng.config.WorkflowBusinessClientConfig;
import it.eng.core.service.client.FactoryBusiness.BusinessType;
import org.apache.log4j.Logger;

/**
 * @author IrisServiceClient generator 0.0.1-SNAPSHOT
 */
public class CompletaTaskService {
		
	private static final String SERVICE_NAME = "CompletaTaskService";	
	private static Logger mLogger = Logger.getLogger(CompletaTaskService.class);
	
	private String url = WorkflowBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = WorkflowBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.workflow.service.bean.CompletaTaskServiceOutBean 
  	 public it.eng.workflow.service.bean.CompletaTaskServiceOutBean  completatask(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.workflow.service.bean.CompletaTaskServiceInBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.workflow.service.bean.CompletaTaskServiceOutBean>() {}.getType();
  	  	return (it.eng.workflow.service.bean.CompletaTaskServiceOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.workflow.service.bean.CompletaTaskServiceOutBean.class,outputType, SERVICE_NAME, "completaTask", var2,var3);
	 } 
}    