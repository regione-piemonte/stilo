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
public class AvvioProcedimentoService {
		
	private static final String SERVICE_NAME = "AvvioProcedimentoService";	
	private static Logger mLogger = Logger.getLogger(AvvioProcedimentoService.class);
	
	private String url = WorkflowBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = WorkflowBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.workflow.service.bean.AvvioProcFascicoloServiceOutBean 
  	 public it.eng.workflow.service.bean.AvvioProcFascicoloServiceOutBean  avviaprocfascicolo(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.workflow.service.bean.AvvioProcFascicoloServiceInBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.workflow.service.bean.AvvioProcFascicoloServiceOutBean>() {}.getType();
  	  	return (it.eng.workflow.service.bean.AvvioProcFascicoloServiceOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.workflow.service.bean.AvvioProcFascicoloServiceOutBean.class,outputType, SERVICE_NAME, "avviaProcFascicolo", var2,var3);
	 } 
	 //it.eng.workflow.service.bean.AvvioProcedimentoServiceOutBean 
  	 public it.eng.workflow.service.bean.AvvioProcedimentoServiceOutBean  avviaprocgenerico(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.workflow.service.bean.AvvioProcedimentoServiceInBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.workflow.service.bean.AvvioProcedimentoServiceOutBean>() {}.getType();
  	  	return (it.eng.workflow.service.bean.AvvioProcedimentoServiceOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.workflow.service.bean.AvvioProcedimentoServiceOutBean.class,outputType, SERVICE_NAME, "avviaProcGenerico", var2,var3);
	 } 
	 //it.eng.workflow.service.bean.AvvioProcedimentoServiceOutBean 
  	 public it.eng.workflow.service.bean.AvvioProcedimentoServiceOutBean  avviaprocesso(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.workflow.service.bean.AvvioProcedimentoServiceInBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.workflow.service.bean.AvvioProcedimentoServiceOutBean>() {}.getType();
  	  	return (it.eng.workflow.service.bean.AvvioProcedimentoServiceOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.workflow.service.bean.AvvioProcedimentoServiceOutBean.class,outputType, SERVICE_NAME, "avviaProcesso", var2,var3);
	 } 
	 //it.eng.workflow.service.bean.AvvioIterAttiServiceOutBean 
  	 public it.eng.workflow.service.bean.AvvioIterAttiServiceOutBean  avviaiteratti(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.workflow.service.bean.AvvioIterAttiServiceInBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.workflow.service.bean.AvvioIterAttiServiceOutBean>() {}.getType();
  	  	return (it.eng.workflow.service.bean.AvvioIterAttiServiceOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.workflow.service.bean.AvvioIterAttiServiceOutBean.class,outputType, SERVICE_NAME, "avviaIterAtti", var2,var3);
	 } 
}    