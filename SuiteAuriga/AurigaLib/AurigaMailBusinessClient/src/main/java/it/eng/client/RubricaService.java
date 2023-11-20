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
public class RubricaService {
		
	private static final String SERVICE_NAME = "RubricaService";	
	private static Logger mLogger = Logger.getLogger(RubricaService.class);
	
	private String url = AurigaMailBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaMailBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.aurigamailbusiness.bean.ResultBean 
  	 public it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.RubricaOutBean>  search(Locale locale,it.eng.aurigamailbusiness.bean.RubricaSearchBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.RubricaOutBean>>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.RubricaOutBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.ResultBean.class,outputType, SERVICE_NAME, "search", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.ResultBean 
  	 public it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.TRubricaEmailBean>  inserisciaggiornamailinglist(Locale locale,it.eng.aurigamailbusiness.bean.MailingListBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.TRubricaEmailBean>>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.TRubricaEmailBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.ResultBean.class,outputType, SERVICE_NAME, "inserisciAggiornaMailingList", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.ResultBean 
  	 public it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.TRubricaEmailBean>  aggiungimembrimailinglist(Locale locale,it.eng.aurigamailbusiness.bean.MailingListBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.TRubricaEmailBean>>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.TRubricaEmailBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.ResultBean.class,outputType, SERVICE_NAME, "aggiungiMembriMailingList", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.ResultBean 
  	 public it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.TRubricaEmailBean>  updatevocerubrica(Locale locale,it.eng.aurigamailbusiness.bean.VoceRubricaBeanIn var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.TRubricaEmailBean>>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.TRubricaEmailBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.ResultBean.class,outputType, SERVICE_NAME, "updateVoceRubrica", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.ResultBean 
  	 public it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.TRubricaEmailBean>  eliminavocerubrica(Locale locale,it.eng.aurigamailbusiness.bean.TRubricaEmailBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.TRubricaEmailBean>>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.TRubricaEmailBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.ResultBean.class,outputType, SERVICE_NAME, "eliminaVoceRubrica", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.ResultBean 
  	 public it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.TRubricaEmailBean>  savevocerubrica(Locale locale,it.eng.aurigamailbusiness.bean.VoceRubricaBeanIn var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.TRubricaEmailBean>>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.TRubricaEmailBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.ResultBean.class,outputType, SERVICE_NAME, "saveVoceRubrica", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.ResultBean 
  	 public it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.RubricaOutBean>  getmembriml(Locale locale,it.eng.aurigamailbusiness.bean.TRubricaEmailBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.RubricaOutBean>>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.ResultBean<it.eng.aurigamailbusiness.bean.RubricaOutBean>)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.ResultBean.class,outputType, SERVICE_NAME, "getMembriML", var2);
	 } 
}    