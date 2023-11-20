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
public class GestioneMultiplaDocumenti {
		
	private static final String SERVICE_NAME = "GestioneMultiplaDocumenti";	
	private static Logger mLogger = Logger.getLogger(GestioneMultiplaDocumenti.class);
	
	private String url = AurigaBusinessClientConfig.getInstance().getUrl();  	
	private BusinessType type = AurigaBusinessClientConfig.getInstance().getBusinesstype();  	
	  	
	
	 //it.eng.document.function.bean.ListCreaModDocumentoOutBean 
  	 public it.eng.document.function.bean.ListCreaModDocumentoOutBean  creamodificadocumento(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.ListCreaModDocumentoInBean var3,it.eng.document.function.bean.ListPrimariBean var4,it.eng.document.function.bean.ListAllegatiBean var5) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.ListCreaModDocumentoOutBean>() {}.getType();
  	  	return (it.eng.document.function.bean.ListCreaModDocumentoOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.ListCreaModDocumentoOutBean.class,outputType, SERVICE_NAME, "creaModificaDocumento", var2,var3,var4,var5);
	 } 
	 //it.eng.document.function.bean.CreaModDocumentoOutBean 
  	 public it.eng.document.function.bean.CreaModDocumentoOutBean  creadocumento(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.CreaModDocumentoInBean var3,it.eng.document.function.bean.FilePrimarioBean var4,it.eng.document.function.bean.AllegatiBean var5) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.CreaModDocumentoOutBean>() {}.getType();
  	  	return (it.eng.document.function.bean.CreaModDocumentoOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.CreaModDocumentoOutBean.class,outputType, SERVICE_NAME, "creaDocumento", var2,var3,var4,var5);
	 } 
	 //it.eng.document.function.bean.CreaModDocumentoOutBean 
  	 public it.eng.document.function.bean.CreaModDocumentoOutBean  salvadocumentiud(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,java.math.BigDecimal var3,java.math.BigDecimal var4,it.eng.document.function.bean.FilePrimarioBean var5,it.eng.document.function.bean.AllegatiBean var6) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.CreaModDocumentoOutBean>() {}.getType();
  	  	return (it.eng.document.function.bean.CreaModDocumentoOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.CreaModDocumentoOutBean.class,outputType, SERVICE_NAME, "salvaDocumentiUd", var2,var3,var4,var5,var6);
	 } 
	 //java.math.BigDecimal 
  	 public java.math.BigDecimal  leggiidudofdocws(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,java.lang.String var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<java.math.BigDecimal>() {}.getType();
  	  	return (java.math.BigDecimal)FactoryBusiness.getBusiness(type).call(url, locale,java.math.BigDecimal.class,outputType, SERVICE_NAME, "leggiIdUDOfDocWS", var2,var3);
	 } 
	 //it.eng.document.function.bean.DelUdDocVerOut 
  	 public it.eng.document.function.bean.DelUdDocVerOut  deluddocver(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.DelUdDocVerIn var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.DelUdDocVerOut>() {}.getType();
  	  	return (it.eng.document.function.bean.DelUdDocVerOut)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.DelUdDocVerOut.class,outputType, SERVICE_NAME, "delUdDocVer", var2,var3);
	 } 
	 //it.eng.document.function.bean.CreaModDocumentoOutBean 
  	 public it.eng.document.function.bean.CreaModDocumentoOutBean  modificadocumento(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,java.math.BigDecimal var3,java.math.BigDecimal var4,it.eng.document.function.bean.CreaModDocumentoInBean var5,it.eng.document.function.bean.FilePrimarioBean var6,it.eng.document.function.bean.AllegatiBean var7) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.CreaModDocumentoOutBean>() {}.getType();
  	  	return (it.eng.document.function.bean.CreaModDocumentoOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.CreaModDocumentoOutBean.class,outputType, SERVICE_NAME, "modificaDocumento", var2,var3,var4,var5,var6,var7);
	 } 
	 //it.eng.document.function.bean.CreaModDocumentoOutBean 
  	 public it.eng.document.function.bean.CreaModDocumentoOutBean  modificadocumentoskipnullvalues(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,java.math.BigDecimal var3,java.math.BigDecimal var4,it.eng.document.function.bean.CreaModDocumentoInBean var5,it.eng.document.function.bean.FilePrimarioBean var6,it.eng.document.function.bean.AllegatiBean var7) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.CreaModDocumentoOutBean>() {}.getType();
  	  	return (it.eng.document.function.bean.CreaModDocumentoOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.CreaModDocumentoOutBean.class,outputType, SERVICE_NAME, "modificaDocumentoSkipNullValues", var2,var3,var4,var5,var6,var7);
	 } 
	 //it.eng.document.function.bean.AggiungiDocumentoOutBean 
  	 public it.eng.document.function.bean.AggiungiDocumentoOutBean  aggiungidocumento(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.AggiungiDocumentoInBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.AggiungiDocumentoOutBean>() {}.getType();
  	  	return (it.eng.document.function.bean.AggiungiDocumentoOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.AggiungiDocumentoOutBean.class,outputType, SERVICE_NAME, "aggiungiDocumento", var2,var3);
	 } 
	 //java.math.BigDecimal 
  	 public java.math.BigDecimal  leggiiddocprimariows(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,java.lang.String var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<java.math.BigDecimal>() {}.getType();
  	  	return (java.math.BigDecimal)FactoryBusiness.getBusiness(type).call(url, locale,java.math.BigDecimal.class,outputType, SERVICE_NAME, "leggiIdDocPrimarioWS", var2,var3);
	 } 
	 //it.eng.document.function.bean.VersionaDocumentoOutBean 
  	 public it.eng.document.function.bean.VersionaDocumentoOutBean  versionadocumentows(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.VersionaDocumentoInBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.VersionaDocumentoOutBean>() {}.getType();
  	  	return (it.eng.document.function.bean.VersionaDocumentoOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.VersionaDocumentoOutBean.class,outputType, SERVICE_NAME, "versionaDocumentoWS", var2,var3);
	 } 
	 //it.eng.document.function.bean.VersionaDocumentoOutBean 
  	 public it.eng.document.function.bean.VersionaDocumentoOutBean  versionadocumento(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.VersionaDocumentoInBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.VersionaDocumentoOutBean>() {}.getType();
  	  	return (it.eng.document.function.bean.VersionaDocumentoOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.VersionaDocumentoOutBean.class,outputType, SERVICE_NAME, "versionaDocumento", var2,var3);
	 } 
	 //it.eng.document.function.bean.CreaModDocumentoOutBean 
  	 public it.eng.document.function.bean.CreaModDocumentoOutBean  creadocumentowithversintransaction(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.CreaModDocumentoInBean var3,it.eng.document.function.bean.FilePrimarioBean var4,it.eng.document.function.bean.AllegatiBean var5) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.CreaModDocumentoOutBean>() {}.getType();
  	  	return (it.eng.document.function.bean.CreaModDocumentoOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.CreaModDocumentoOutBean.class,outputType, SERVICE_NAME, "creaDocumentoWithVersInTransaction", var2,var3,var4,var5);
	 } 
	 //it.eng.document.function.bean.CreaDocumentiRegMultiplaUscitaBean 
  	 public it.eng.document.function.bean.CreaDocumentiRegMultiplaUscitaBean  creadocumentiregistrazionemultiplauscita(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.CreaDocumentiRegMultiplaUscitaBean var3) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.CreaDocumentiRegMultiplaUscitaBean>() {}.getType();
  	  	return (it.eng.document.function.bean.CreaDocumentiRegMultiplaUscitaBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.CreaDocumentiRegMultiplaUscitaBean.class,outputType, SERVICE_NAME, "creaDocumentiRegistrazioneMultiplaUscita", var2,var3);
	 } 
	 //it.eng.document.function.bean.CreaModDocumentoOutBean 
  	 public it.eng.document.function.bean.CreaModDocumentoOutBean  creadocumentowithversintransactionskipnullvalues(Locale locale,it.eng.auriga.module.business.beans.AurigaLoginBean var2,it.eng.document.function.bean.CreaModDocumentoInBean var3,it.eng.document.function.bean.FilePrimarioBean var4,it.eng.document.function.bean.AllegatiBean var5) throws Exception {
		mLogger.debug("URL vale " + url);
		mLogger.debug("BusinessType vale " + type.name());
  	  	Type   outputType =	new TypeToken<it.eng.document.function.bean.CreaModDocumentoOutBean>() {}.getType();
  	  	return (it.eng.document.function.bean.CreaModDocumentoOutBean)FactoryBusiness.getBusiness(type).call(url, locale,it.eng.document.function.bean.CreaModDocumentoOutBean.class,outputType, SERVICE_NAME, "creaDocumentoWithVersInTransactionSkipNullValues", var2,var3,var4,var5);
	 } 
}    