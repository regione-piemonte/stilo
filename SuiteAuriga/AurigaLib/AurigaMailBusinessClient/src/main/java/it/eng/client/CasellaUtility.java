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
public class CasellaUtility {
		
	private static final String SERVICE_NAME = "CasellaUtility";	
	private static Logger mLogger = Logger.getLogger(CasellaUtility.class);
	
	private String url = AurigaMailBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaMailBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.aurigamailbusiness.database.utility.bean.listacasella.ListaIdCaselleOutput 
  	 public it.eng.aurigamailbusiness.database.utility.bean.listacasella.ListaIdCaselleOutput  getlistacaselleutente(Locale locale,it.eng.aurigamailbusiness.database.utility.bean.listacasella.ListaIdCaselleInput var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.database.utility.bean.listacasella.ListaIdCaselleOutput>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.database.utility.bean.listacasella.ListaIdCaselleOutput)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.database.utility.bean.listacasella.ListaIdCaselleOutput.class,outputType, SERVICE_NAME, "getListaCaselleUtente", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.EmailGroupBean 
  	 public it.eng.aurigamailbusiness.bean.EmailGroupBean  getlistaemailutentesucasella(Locale locale,it.eng.aurigamailbusiness.database.utility.bean.listaprofilituentesucasella.ListaProfiliUtenteSuCasellaIn var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.EmailGroupBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.EmailGroupBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.EmailGroupBean.class,outputType, SERVICE_NAME, "getListaEmailUtenteSuCasella", var2);
	 } 
	 //it.eng.aurigamailbusiness.database.utility.bean.listasubfolder.ListaIdSubFolderOut 
  	 public it.eng.aurigamailbusiness.database.utility.bean.listasubfolder.ListaIdSubFolderOut  getlistaidsubfolder(Locale locale,it.eng.aurigamailbusiness.database.utility.bean.listasubfolder.ListaIdSubFolderIn var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.database.utility.bean.listasubfolder.ListaIdSubFolderOut>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.database.utility.bean.listasubfolder.ListaIdSubFolderOut)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.database.utility.bean.listasubfolder.ListaIdSubFolderOut.class,outputType, SERVICE_NAME, "getListaIdSubFolder", var2);
	 } 
	 //it.eng.aurigamailbusiness.bean.TAnagFruitoriCaselleBean 
  	 public it.eng.aurigamailbusiness.bean.TAnagFruitoriCaselleBean  getfruitorecasellafromdominio(Locale locale,it.eng.aurigamailbusiness.bean.DominioBean var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.bean.TAnagFruitoriCaselleBean>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.bean.TAnagFruitoriCaselleBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.bean.TAnagFruitoriCaselleBean.class,outputType, SERVICE_NAME, "getFruitoreCasellaFromDominio", var2);
	 } 
	 //it.eng.aurigamailbusiness.database.utility.bean.listacasella.ListaCaselleUtenteWithAccountOutput 
  	 public it.eng.aurigamailbusiness.database.utility.bean.listacasella.ListaCaselleUtenteWithAccountOutput  getlistacaselleutentewithaccount(Locale locale,it.eng.aurigamailbusiness.database.utility.bean.listacasella.ListaIdCaselleInput var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.database.utility.bean.listacasella.ListaCaselleUtenteWithAccountOutput>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.database.utility.bean.listacasella.ListaCaselleUtenteWithAccountOutput)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.database.utility.bean.listacasella.ListaCaselleUtenteWithAccountOutput.class,outputType, SERVICE_NAME, "getListaCaselleUtenteWithAccount", var2);
	 } 
	 //it.eng.aurigamailbusiness.database.utility.bean.listafolderUtente.ListaIdFolderUtenteOut 
  	 public it.eng.aurigamailbusiness.database.utility.bean.listafolderUtente.ListaIdFolderUtenteOut  getlistaidfolderutente(Locale locale,it.eng.aurigamailbusiness.database.utility.bean.listafolderUtente.ListaIdFolderUtenteIn var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.database.utility.bean.listafolderUtente.ListaIdFolderUtenteOut>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.database.utility.bean.listafolderUtente.ListaIdFolderUtenteOut)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.database.utility.bean.listafolderUtente.ListaIdFolderUtenteOut.class,outputType, SERVICE_NAME, "getListaIdFolderUtente", var2);
	 } 
	 //it.eng.aurigamailbusiness.database.utility.bean.listacasella.InfoCasella 
  	 public it.eng.aurigamailbusiness.database.utility.bean.listacasella.InfoCasella  getaccountcasella(Locale locale,it.eng.aurigamailbusiness.database.utility.bean.listacasella.InfoCasella var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.database.utility.bean.listacasella.InfoCasella>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.database.utility.bean.listacasella.InfoCasella)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.database.utility.bean.listacasella.InfoCasella.class,outputType, SERVICE_NAME, "getAccountCasella", var2);
	 } 
	 //it.eng.aurigamailbusiness.database.utility.bean.listaprofilituentesucasella.ListaProfiliUtenteSuCasellaOut 
  	 public it.eng.aurigamailbusiness.database.utility.bean.listaprofilituentesucasella.ListaProfiliUtenteSuCasellaOut  getlistaprofiliutentesucasella(Locale locale,it.eng.aurigamailbusiness.database.utility.bean.listaprofilituentesucasella.ListaProfiliUtenteSuCasellaIn var2) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.aurigamailbusiness.database.utility.bean.listaprofilituentesucasella.ListaProfiliUtenteSuCasellaOut>() {}.getType();
  	  	return (it.eng.aurigamailbusiness.database.utility.bean.listaprofilituentesucasella.ListaProfiliUtenteSuCasellaOut)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.aurigamailbusiness.database.utility.bean.listaprofilituentesucasella.ListaProfiliUtenteSuCasellaOut.class,outputType, SERVICE_NAME, "getListaProfiliUtenteSuCasella", var2);
	 } 
}    