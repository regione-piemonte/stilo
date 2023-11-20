/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.servlet.http.HttpSession;

import it.eng.auriga.ui.module.layout.shared.bean.ParametriDBBean;

public class ParametriDBUtil {

	public static void setParametriDB(HttpSession session, ParametriDBBean bean) {
		session.setAttribute("PARAMETRI_DB", bean);
		
		// questi parametri li aggiungo anche direttamente in sessione perchè devono essere letti dalle servlet di upload lato BaseUI
		session.setAttribute("ATTIVA_GEST_PDF_EDITABILI_IN_UPLOAD", bean.getParametriDB().get("ATTIVA_GEST_PDF_EDITABILI_IN_UPLOAD"));
		session.setAttribute("ATTIVA_CONV_IMG_PDF_CORROTTI", bean.getParametriDB().get("ATTIVA_CONV_IMG_PDF_CORROTTI"));
		session.setAttribute("ATTIVA_GEST_PDF_CON_COMMENTI", bean.getParametriDB().get("ATTIVA_GEST_PDF_CON_COMMENTI"));
	} 

	public static ParametriDBBean getParametriDB(HttpSession session) {
		ParametriDBBean bean = (ParametriDBBean) session.getAttribute("PARAMETRI_DB");
//		setParametriDBForLocalTest(bean); // SERVE SOLO PER FARE I TEST IN LOCALE: DA TENERE COMMENTATA! 
		return bean;
	}

	public static boolean getParametroDBAsBoolean(HttpSession session, String nome) {
		ParametriDBBean bean = getParametriDB(session);
		String value = bean != null ? (String) bean.getParametriDB().get(nome) : null;
		return (value != null && (value.equalsIgnoreCase("true") || value.equals("1")));
	}

	public static String getParametroDB(HttpSession session, String nome) {
		ParametriDBBean bean = getParametriDB(session);
		String value = bean != null ? (String) bean.getParametriDB().get(nome) : null;
		return value != null ? value : "";
	}

	public static void setParametriDBForLocalTest(ParametriDBBean bean) {
		
//		bean.getParametriDB().put("ATTIVA_GEN_FMT_DOC_ATTI_COLL", "true");
		
//		bean.getParametriDB().put("APERTURA_AUTO_FIRST_TASK_ESEG_ATTI", "true");
		
//		bean.getParametriDB().put("SHOW_IMPRONTA_FILE", "true");
				
//		bean.getParametriDB().put("ATTIVA_COPIA_ALLEGATI_ATTO", "true");
		
//		bean.getParametriDB().put("ATTIVA_NRO_ODG_STRINGA_IN_SEDUTE_GNT", "true");
		
//		bean.getParametriDB().put("ATTIVA_NRO_ODG_STRINGA_IN_SEDUTE_CNS", "false");
		
//		bean.getParametriDB().put("ATTIVA_COPIA_ALLEGATI_ATTO", "true");	
		
//		bean.getParametriDB().put("ATTIVA_INDENTAZIONE_CKEDITOR", "true");
		
//		bean.getParametriDB().put("ATTIVA_SCARICO_ATTO_VERS_MODIFICABILE", "false");
		
//		bean.getParametriDB().put("ATTIVA_CONFRONTO_ATTI", "true");
			  
//		bean.getParametriDB().put("SCRIPT_CLEANER_PATTERNS", "<\\s*script\\s*>(.*?)<\\s*\\/\\s*script\\s*>|*|<\\s*(script|iframe)(.*?)>|*|<\\s*\\/\\s*(script|ifra me)\\s*>|*|(eval|expression)\\s*\\((.*?)\\)|*|(vbscript|javascript)\\s*:|*|(onerror|onclick|onload)\\s*=|*|(onerror|onclick|onload)\\s*\\(.*?\\)\\s*=|*|");
		
//		bean.getParametriDB().put("ATTIVA_GRID_ALLEGATI_IN_PROT", "true");
		
//		bean.getParametriDB().put("SCANSIONE_COMPRESSION_QUALITY", "1");
		
//		bean.getParametriDB().put("ATTIVA_TIMBRATURA_CARTACEO", "true");
		
//		bean.getParametriDB().put("MODALITA_STAMPA_ETICHETTA_CARTACEO", "JNLP");
		
//		bean.getParametriDB().put("CLIENTE", "A2A");
		
//		bean.getParametriDB().put("ATTIVA_GEST_ADSP_EDITOR_ATTI", "true");
		
//		bean.getParametriDB().put("DISABILITA_CONTROLLO_CERTIFICATO_SCADUTO_FIRMA_CLIENT", "true");
				
//		bean.getParametriDB().put("SHOW_POPUP_GUI_MESSAGES", "false");
		
//		bean.getParametriDB().put("ATTIVA_CTRL_USER_VS_CERTIF_FIRMA_DIG", "true");
		
//		bean.getParametriDB().put("DISATTIVA_CLASSIFICAZIONE", "true");
		
//		bean.getParametriDB().put("ATTIVA_FOLDER_CUSTOM", "true");

//		bean.getParametriDB().put("SKIP_CTRL_BUSTA_FIRM", "false");
		
//		bean.getParametriDB().put("RESTRIZIONI_ASS_NOT_DOC", "CONF_1");
		
//		bean.getParametriDB().put("HIDE_FILTER_FULLTEXT_REP_DOC", "true");
//		bean.getParametriDB().put("MAIL_NON_INTEROP_SOLO_DEST_NON_IPA", "false");
		
//		bean.getParametriDB().put("ATTIVA_GEST_PROV_ALLEG_ATTI", "true");
//		bean.getParametriDB().put("FASC_SMIST_ACTA", "mandatory");
		
//		bean.getParametriDB().put("ATTIVA_CONV_IMG_PDF_CORROTTI", "true");
//		bean.getParametriDB().put("ATTIVA_GEST_PDF_EDITABILI_IN_UPLOAD", "true");
//		bean.getParametriDB().put("ATTIVA_GEST_PDF_CON_COMMENTI", "true");
		
		
//		bean.getParametriDB().put("ESCLUDI_FIRMA_ALLEGATI_FIRMATI_PI_ATTO", "false");
//		bean.getParametriDB().put("RIFIUTO_ALLEGATI_ATTI_CON_FIRME_NON_VALIDE", "false");
//		bean.getParametriDB().put("ATTIVA_CHECK_FILE_CORROTTI", "true");
//		bean.getParametriDB().put("ATTIVA_CONV_IMG_PRE_PECCETTE", "true");
		
//		bean.getParametriDB().put("CONV_PDF_PRE_FIRMA", "false");
//		
//		bean.getParametriDB().put("ATTIVA_PAGINAZIONE_ATTI", "true");
//		bean.getParametriDB().put("DISATTIVA_AUTOCOMPLETAMENTO_FILTRI", "true");
		
//		bean.getParametriDB().put("ID_UD_PUBBL_POST", "28286226;28319957;28339742");
		
//		bean.getParametriDB().put("ATTIVO_MODULO_PROT", "false");
		
//		bean.getParametriDB().put("CLIENTE", "CMMI");
		
//		bean.getParametriDB().put("ATTIVA_ESTENSIONI_PERMESSI_DOC", "true");
		
//		bean.getParametriDB().put("ATTIVA_SOST_BR_HTML_MODELLI", "true");
	
//		bean.getParametriDB().put("DISATTIVA_NUOVO_VIEWER_PDFJS", "false");
		
//		bean.getParametriDB().put("ATTIVA_NUOVA_PROPOSTA_ATTO_2", "true");
//		bean.getParametriDB().put("GESTIONE_ATTI_COMPLETA", "false");
//		bean.getParametriDB().put("SHOW_VERS_CON_OMISSIS", "false");
		
//		bean.getParametriDB().put("DIMENSIONE_MAX_FILE_PREVIEW", "1");
//		bean.getParametriDB().put("DIMENSIONE_MAX_FILE_DOWNLOAD", "1");
//		bean.getParametriDB().put("DIMENSIONE_MAX_FILE_CONVERSIONE", "1");
		
//		bean.getParametriDB().put("ATTIVA_MULTI_MITT_U", "false");
//		bean.getParametriDB().put("ATTIVA_MULTI_MITT_E", "true");
//				
//		bean.getParametriDB().put("SHOW_UP_IN_MITT_REG", "false");
//		bean.getParametriDB().put("MITT_SOLO_IN_ORGANIGRAMMA", "true");
		
//		bean.getParametriDB().put("ISTAT_COMUNE_RIF", null);
		
//		bean.getParametriDB().put("ATTIVA_OPZ_STAMPA_ETICHETTA", "true");	
		
//		bean.getParametriDB().put("HIDE_STOP_SEARCH_BUTTON", "true");
		
//		bean.getParametriDB().put("SIGNER_APPLET_MULTI_VER", "1.0");
//		bean.getParametriDB().put("SEL_CERTIFICATO_FIRMA_APPLET_VER", "1.0");
//		bean.getParametriDB().put("FIRMA_CON_CERTIF_INPUT_APPLET_VER", "1.0");
//		bean.getParametriDB().put("EDIT_APPLET_VER", "1.0");
//		bean.getParametriDB().put("SCAN_APPLET_VER", "1.0");
//		bean.getParametriDB().put("STAMPA_ETICHETTA_APPLET_VER", "1.0");
//		bean.getParametriDB().put("STAMPA_FILE_APPLET_VER", "1.0");
//		bean.getParametriDB().put("JPEDAL_APPLET_VER", "1.0");
//		bean.getParametriDB().put("STATISTICHE_APPLET_VER", "1.0");
//		bean.getParametriDB().put("SEL_STAMPANTE_APPLET_VER", "1.0");
		
//		bean.getParametriDB().put("ATTIVA_PROTOCOLLO_WIZARD", "false");
//		bean.getParametriDB().put("ATTIVA_PROTOCOLLO_PGWEB", "true");
//		bean.getParametriDB().put("ATTIVA_ATT_CUSTOM_TIPO_GUI", "true");
//		bean.getParametriDB().put("ATTIVA_ESIBENTE_SENZA_WIZARD", "true");

//		bean.getParametriDB().put("DIM_ORGANIGRAMMA_NONSTD", "true");
//		bean.getParametriDB().put("UPPERCASE_OGGETTO_ATTO", "true");
//		bean.getParametriDB().put("ATTIVA_CAPOFILA", "true");
		
//		bean.getParametriDB().put("MODALITA_FIRMA", "GRAFOMETRICA");
//		bean.getParametriDB().put("MODALITA_FIRMA", "JNLP");
//		bean.getParametriDB().put("MODALITA_FIRMA", "HSM");
//		bean.getParametriDB().put("MODALITA_FIRMA", "APPLET");
//		bean.getParametriDB().put("FIRMA_AUTOMATICA", "false");
//		bean.getParametriDB().put("MODALITA_SCANSIONE", "JNLP");
//		bean.getParametriDB().put("MODALITA_EDIT_ONLINE", "JNLP");
//		bean.getParametriDB().put("MODALITA_STAMPA_ETICHETTA", "JNLP");
//		bean.getParametriDB().put("FIRMA_CONGIUNTA", "FALSE");
		
//		bean.getParametriDB().put("MODALITA_FIRMA", "JNLP");
//		bean.getParametriDB().put("HSM_TIPO_FIRMA", "CAdES");
//		bean.getParametriDB().put("HSM_TIPO_FIRMA", "");
//		bean.getParametriDB().put("FIRMA_CONGIUNTA", "TRUE");
//		bean.getParametriDB().put("HSM_TIPO_FIRMA_ATTI", "CAdES");
//		bean.getParametriDB().put("TIPO_HSM", "ARUBA");
//		bean.getParametriDB().put("TIPO_HSM", "MEDAS");
//		String hsmParam_Aruba = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><SezioneCache><Variabile><Nome>delegatedDomain</Nome><ValoreSemplice>engineering</ValoreSemplice></Variabile><Variabile><Nome>wsSignConfig.serviceNS</Nome><ValoreSemplice>http://arubasignservice.arubapec.it/</ValoreSemplice></Variabile><Variabile><Nome>wsSignConfig.serviceName</Nome><ValoreSemplice>ArubaSignServiceService</ValoreSemplice></Variabile><Variabile><Nome>wsSignConfig.wsdlEndpoint</Nome><ValoreSemplice>https://asbfe/ArubaSignService/ArubaSignService?wsdl</ValoreSemplice></Variabile><Variabile><Nome>wsCertConfig.serviceNS</Nome><ValoreSemplice>http://arubasignservice.arubapec.it/</ValoreSemplice></Variabile><Variabile><Nome>wsCertConfig.serviceName</Nome><ValoreSemplice>ArubaSignServiceService</ValoreSemplice></Variabile><Variabile><Nome>wsCertConfig.wsdlEndpoint</Nome><ValoreSemplice>https://asbfe/ArubaSignService/ArubaSignService?wsdl</ValoreSemplice></Variabile><Variabile><Nome>marcaConfig.serviceUrl</Nome><ValoreSemplice>http://services.globaltrustfinder.com/adss/tsa</ValoreSemplice></Variabile><Variabile><Nome>marcaConfig.serviceUser</Nome><ValoreSemplice/></Variabile><Variabile><Nome>marcaConfig.servicePassword</Nome><ValoreSemplice/></Variabile><Variabile><Nome>marcaConfig.useAuth</Nome><ValoreSemplice/></Variabile><Variabile><Nome>proxyConfig.proxyHost</Nome><ValoreSemplice>proxy.eng.it</ValoreSemplice></Variabile><Variabile><Nome>proxyConfig.proxyPort</Nome><ValoreSemplice>3128</ValoreSemplice></Variabile><Variabile><Nome>proxyConfig.proxyUser</Nome><ValoreSemplice>campello</ValoreSemplice></Variabile><Variabile><Nome>proxyConfig.proxyPassword</Nome><ValoreSemplice>pelllo123</ValoreSemplice></Variabile><Variabile><Nome>proxyConfig.useProxy</Nome><ValoreSemplice>true</ValoreSemplice></Variabile><Variabile><Nome>requireSignatureInSession</Nome><ValoreSemplice>false</ValoreSemplice></Variabile><Variabile><Nome>delegatedUser</Nome><ValoreSemplice>digidoc</ValoreSemplice></Variabile><Variabile><Nome>delegatedPassword</Nome><ValoreSemplice>6DMXKRkSbj3H</ValoreSemplice></Variabile><Variabile><Nome>typeOtpAuth</Nome><ValoreSemplice>engineering</ValoreSemplice></Variabile><Variabile><Nome>useDelegate</Nome><ValoreSemplice>false</ValoreSemplice></Variabile><Variabile><Nome>user</Nome><ValoreSemplice>martinuc</ValoreSemplice></Variabile><Variabile><Nome>password</Nome><ValoreSemplice>;DOCman112001</ValoreSemplice></Variabile><Variabile><Nome>otpPwd</Nome><ValoreSemplice>dsign</ValoreSemplice></Variabile><Variabile><Nome>requireAuthentication</Nome><ValoreSemplice>true</ValoreSemplice></Variabile></SezioneCache>";
//		String hsmParam_Aruba_del = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><SezioneCache><Variabile><Nome>delegatedDomain</Nome><ValoreSemplice>engineering</ValoreSemplice></Variabile><Variabile><Nome>wsSignConfig.serviceNS</Nome><ValoreSemplice>http://arubasignservice.arubapec.it/</ValoreSemplice></Variabile><Variabile><Nome>wsSignConfig.serviceName</Nome><ValoreSemplice>ArubaSignServiceService</ValoreSemplice></Variabile><Variabile><Nome>wsSignConfig.wsdlEndpoint</Nome><ValoreSemplice>https://asbfe/ArubaSignService/ArubaSignService?wsdl</ValoreSemplice></Variabile><Variabile><Nome>wsCertConfig.serviceNS</Nome><ValoreSemplice>http://arubasignservice.arubapec.it/</ValoreSemplice></Variabile><Variabile><Nome>wsCertConfig.serviceName</Nome><ValoreSemplice>ArubaSignServiceService</ValoreSemplice></Variabile><Variabile><Nome>wsCertConfig.wsdlEndpoint</Nome><ValoreSemplice>https://asbfe/ArubaSignService/ArubaSignService?wsdl</ValoreSemplice></Variabile><Variabile><Nome>marcaConfig.serviceUrl</Nome><ValoreSemplice>http://services.globaltrustfinder.com/adss/tsa</ValoreSemplice></Variabile><Variabile><Nome>marcaConfig.serviceUser</Nome><ValoreSemplice/></Variabile><Variabile><Nome>marcaConfig.servicePassword</Nome><ValoreSemplice/></Variabile><Variabile><Nome>marcaConfig.useAuth</Nome><ValoreSemplice/></Variabile><Variabile><Nome>proxyConfig.proxyHost</Nome><ValoreSemplice>proxy.eng.it</ValoreSemplice></Variabile><Variabile><Nome>proxyConfig.proxyPort</Nome><ValoreSemplice>3128</ValoreSemplice></Variabile><Variabile><Nome>proxyConfig.proxyUser</Nome><ValoreSemplice>campello</ValoreSemplice></Variabile><Variabile><Nome>proxyConfig.proxyPassword</Nome><ValoreSemplice>pelllo123</ValoreSemplice></Variabile><Variabile><Nome>proxyConfig.useProxy</Nome><ValoreSemplice>true</ValoreSemplice></Variabile><Variabile><Nome>requireSignatureInSession</Nome><ValoreSemplice>false</ValoreSemplice></Variabile><Variabile><Nome>delegatedUser</Nome><ValoreSemplice>digidoc</ValoreSemplice></Variabile><Variabile><Nome>delegatedPassword</Nome><ValoreSemplice>6DMXKRkSbj3H</ValoreSemplice></Variabile><Variabile><Nome>typeOtpAuth</Nome><ValoreSemplice>engineering</ValoreSemplice></Variabile><Variabile><Nome>useDelegate</Nome><ValoreSemplice>true</ValoreSemplice></Variabile><Variabile><Nome>user</Nome><ValoreSemplice>martinuc</ValoreSemplice></Variabile><Variabile><Nome>otpPwd</Nome><ValoreSemplice>dsign</ValoreSemplice></Variabile><Variabile><Nome>requireAuthentication</Nome><ValoreSemplice>false</ValoreSemplice></Variabile></SezioneCache>";
//		String hsmParam_Aruba_auth = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><SezioneCache><Variabile><Nome>delegatedDomain</Nome><ValoreSemplice>engineering</ValoreSemplice></Variabile><Variabile><Nome>wsSignConfig.serviceNS</Nome><ValoreSemplice>http://arubasignservice.arubapec.it/</ValoreSemplice></Variabile><Variabile><Nome>wsSignConfig.serviceName</Nome><ValoreSemplice>ArubaSignServiceService</ValoreSemplice></Variabile><Variabile><Nome>wsSignConfig.wsdlEndpoint</Nome><ValoreSemplice>https://asbfe/ArubaSignService/ArubaSignService?wsdl</ValoreSemplice></Variabile><Variabile><Nome>wsCertConfig.serviceNS</Nome><ValoreSemplice>http://arubasignservice.arubapec.it/</ValoreSemplice></Variabile><Variabile><Nome>wsCertConfig.serviceName</Nome><ValoreSemplice>ArubaSignServiceService</ValoreSemplice></Variabile><Variabile><Nome>wsCertConfig.wsdlEndpoint</Nome><ValoreSemplice>https://asbfe/ArubaSignService/ArubaSignService?wsdl</ValoreSemplice></Variabile><Variabile><Nome>marcaConfig.serviceUrl</Nome><ValoreSemplice>http://services.globaltrustfinder.com/adss/tsa</ValoreSemplice></Variabile><Variabile><Nome>marcaConfig.serviceUser</Nome><ValoreSemplice/></Variabile><Variabile><Nome>marcaConfig.servicePassword</Nome><ValoreSemplice/></Variabile><Variabile><Nome>marcaConfig.useAuth</Nome><ValoreSemplice/></Variabile><Variabile><Nome>proxyConfig.proxyHost</Nome><ValoreSemplice>proxy.eng.it</ValoreSemplice></Variabile><Variabile><Nome>proxyConfig.proxyPort</Nome><ValoreSemplice>3128</ValoreSemplice></Variabile><Variabile><Nome>proxyConfig.proxyUser</Nome><ValoreSemplice>campello</ValoreSemplice></Variabile><Variabile><Nome>proxyConfig.proxyPassword</Nome><ValoreSemplice>pelllo123</ValoreSemplice></Variabile><Variabile><Nome>proxyConfig.useProxy</Nome><ValoreSemplice>true</ValoreSemplice></Variabile><Variabile><Nome>requireSignatureInSession</Nome><ValoreSemplice>false</ValoreSemplice></Variabile><Variabile><Nome>delegatedUser</Nome><ValoreSemplice>digidoc</ValoreSemplice></Variabile><Variabile><Nome>delegatedPassword</Nome><ValoreSemplice>6DMXKRkSbj3H</ValoreSemplice></Variabile><Variabile><Nome>typeOtpAuth</Nome><ValoreSemplice>engineering</ValoreSemplice></Variabile><Variabile><Nome>useDelegate</Nome><ValoreSemplice>false</ValoreSemplice></Variabile><Variabile><Nome>user</Nome><ValoreSemplice>martinuc</ValoreSemplice></Variabile><Variabile><Nome>otpPwd</Nome><ValoreSemplice>dsign</ValoreSemplice></Variabile><Variabile><Nome>requireAuthentication</Nome><ValoreSemplice>true</ValoreSemplice></Variabile></SezioneCache>";
//		bean.getParametriDB().put("HSM_PARAMETERS", hsmParam_Aruba_del);
//		bean.getParametriDB().put("TIPO_HSM", "MEDAS");
//		String hsmParam_Medas = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><SezioneCache><Variabile><Nome>wsSignConfig.serviceNS</Nome><ValoreSemplice>http://www.medas-solutions.it/ScrybaSignServer/</ValoreSemplice></Variabile><Variabile><Nome>wsSignConfig.serviceName</Nome><ValoreSemplice>SyncSignService</ValoreSemplice></Variabile><Variabile><Nome>wsSignConfig.wsdlEndpoint</Nome><ValoreSemplice>http://10.10.7.22:8080/ScrybaSignServer/services/syncsign/v2?wsdl</ValoreSemplice></Variabile><Variabile><Nome>wsCertConfig.serviceNS</Nome><ValoreSemplice>http://www.medas-solutions.it/ScrybaSignServer/</ValoreSemplice></Variabile><Variabile><Nome>wsCertConfig.serviceName</Nome><ValoreSemplice>UtilsService</ValoreSemplice></Variabile><Variabile><Nome>wsCertConfig.wsdlEndpoint</Nome><ValoreSemplice>http://10.10.7.22:8080/ScrybaSignServer/services/utils?wsdl</ValoreSemplice></Variabile><Variabile><Nome>wsOtpConfig.serviceNS</Nome><ValoreSemplice>http://www.medas-solutions.it/ScrybaSignServer/</ValoreSemplice></Variabile><Variabile><Nome>wsOtpConfig.serviceName</Nome><ValoreSemplice>UtilsService</ValoreSemplice></Variabile><Variabile><Nome>wsOtpConfig.wsdlEndpoint</Nome><ValoreSemplice>http://10.10.7.22:8080/ScrybaSignServer/services/utils?wsdl</ValoreSemplice></Variabile><Variabile><Nome>processId</Nome><ValoreSemplice>AURIGA</ValoreSemplice></Variabile><Variabile><Nome>requireSignatureInSession</Nome><ValoreSemplice>true</ValoreSemplice></Variabile><Variabile><Nome>requireAuthentication</Nome><ValoreSemplice>true</ValoreSemplice></Variabile></SezioneCache>";
// 		String hsmParam_Medas_Aosta = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><SezioneCache><Variabile><Nome>wsSignConfig.serviceNS</Nome><ValoreSemplice>http://www.medas-solutions.it/ScrybaSignServer/</ValoreSemplice></Variabile><Variabile><Nome>wsSignConfig.serviceName</Nome><ValoreSemplice>SyncSignService</ValoreSemplice></Variabile><Variabile><Nome>wsSignConfig.wsdlEndpoint</Nome><ValoreSemplice>http://10.10.7.21:8080/ScrybaSignServer/services/syncsign/v2?wsdl</ValoreSemplice></Variabile><Variabile><Nome>wsCertConfig.serviceNS</Nome><ValoreSemplice>http://www.medas-solutions.it/ScrybaSignServer/</ValoreSemplice></Variabile><Variabile><Nome>wsCertConfig.serviceName</Nome><ValoreSemplice>UtilsService</ValoreSemplice></Variabile><Variabile><Nome>wsCertConfig.wsdlEndpoint</Nome><ValoreSemplice>http://10.10.7.21:8080/ScrybaSignServer/services/utils?wsdl</ValoreSemplice></Variabile><Variabile><Nome>wsOtpConfig.serviceNS</Nome><ValoreSemplice>http://www.medas-solutions.it/ScrybaSignServer/</ValoreSemplice></Variabile><Variabile><Nome>wsOtpConfig.serviceName</Nome><ValoreSemplice>UtilsService</ValoreSemplice></Variabile><Variabile><Nome>wsOtpConfig.wsdlEndpoint</Nome><ValoreSemplice>http://10.10.7.21:8080/ScrybaSignServer/services/utils?wsdl</ValoreSemplice></Variabile><Variabile><Nome>processId</Nome><ValoreSemplice>AURIGA</ValoreSemplice></Variabile><Variabile><Nome>requireSignatureInSession</Nome><ValoreSemplice>true</ValoreSemplice></Variabile><Variabile><Nome>requireAuthentication</Nome><ValoreSemplice>true</ValoreSemplice></Variabile></SezioneCache>"; 
//		bean.getParametriDB().put("HSM_PARAMETERS", hsmParam_Medas_Aosta);
		
//		bean.getParametriDB().put("SHOW_GRAPHIC_SIGNATURE_IN_PADES", "true");
//		bean.getParametriDB().put("POSITION_GRAPHIC_SIGNATURE_IN_PADES", "{\"posizioneFirma\":\"BASSO_DESTRA\", \"marginePaginaVerticale\":150, \"marginePaginaOrizzontale\":10, \"distanzaFirme\":0, \"rectWidth\":300, \"rectHeight\":15}");
		
		/************************************************************************************************************
		 * DECOMMENTARE LE SEGUENTI RIGHE PER ABILITARE LA GEREZIONE DI FILE PDFA NELLE CONVERSIONI PDF 
		 ************************************************************************************************************/
//		bean.getParametriDB().put("FIRMA_ABILITA_PDFA", "true");
//		bean.getParametriDB().put("GENERAZIONE_DA_MODELLO_ABILITA_PDFA", "true");
//		bean.getParametriDB().put("SCANSIONE_ABILITA_PDFA", "true");
//		bean.getParametriDB().put("TIMBRATURA_ABILITA_PDFA", "true");
		
		/************************************************************************************************************
		 * DECOMMENTARE LE SEGUENTI RIGHE PER FIRMARE CON LA SIGNER MULTI SENZA SELEZIONE DEL CERTIFICATO PRECEDENTE 
		 ************************************************************************************************************/
//		bean.getParametriDB().put("MODALITA_FIRMA", "JNLP");
//		bean.getParametriDB().put("APPLET_TIPO_FIRMA", "");
//		bean.getParametriDB().put("SHOW_GRAPHIC_SIGNATURE_IN_PADES", "false");
		/************************ 	FINE	*********************/
		
//		bean.getParametriDB().put("FLAG_CIFRATURA", "true");
//		bean.getParametriDB().put("PRODUCT_INST_NAME", "AURIGA");
		
//		bean.getParametriDB().put("ATTIVA_CHAT", "TRUE");
		
		/************************************************************************************************************
		 * DECOMMENTARE LE SEGUENTI RIGHE PER ABILITARE LE APPLET INVECE CHE I JNLP
		 ************************************************************************************************************/
//		bean.getParametriDB().put("MODALITA_FIRMA", "APPLET");
		/************************ 	FINE	*********************/
		
		/************************************************************************************************************
		 * DECOMMENTARE LE SEGUENTI RIGHE PER ABILITARE I JNLP INVECE CHE LE APPLET
		 ************************************************************************************************************/
//		bean.getParametriDB().put("MODALITA_FIRMA", "JNLP");
		/************************ 	FINE	*********************/
		
		/************************************************************************************************************
		 * DECOMMENTARE LE SEGUENTI RIGHE Per visualizzare le firme in calce
		 * Se posto a true allora ci si trova nella modalità per cui si vogliono personalizzare le firme in calce,
		 * altrimenti c'è solo una firma
		 ************************************************************************************************************/
//		bean.getParametriDB().put("SHOW_FIRME_IN_CALCE", "true");
		/************************ 	FINE	*********************/
		
		/************************************************************************************************************
		 * DECOMMENTARE LE SEGUENTI RIGHE Per attivare o meno il controllo del body della mail alla transizione
		 * da testo in html a testo semplice
		 ************************************************************************************************************/
//		bean.getParametriDB().put("CANCELLA_TAG_VUOTI_CORPO_MAIL", "true");
		/************************ 	FINE	*********************/

		/************************************************************************************************************
		 * DECOMMENTARE LE SEGUENTI RIGHE Per indicare quale opzione deve essere abilitata di default nel popup 
		 * per l'apposizione del visto
		 ************************************************************************************************************/
//		bean.getParametriDB().put("AZIONE_DEFAULT_POST_FIRMA", "visto");
		/************************ 	FINE	*********************/
		
		/************************************************************************************************************
		 * DECOMMENTARE LE SEGUENTI RIGHE Per attivare la stampa etichette tramite il pdf viewer di sistema
		 ************************************************************************************************************/
//		bean.getParametriDB().put("MODALITA_STAMPA_ETICHETTE", "PDF");
		/************************ 	FINE	*********************/
		
		/************************************************************************************************************
		 * DECOMMENTARE LE SEGUENTI RIGHE Per disattivare l'utilizzo di jnlp e applet
		 ************************************************************************************************************/
//		bean.getParametriDB().put("DISABILITA_JNLP", "true");
		/************************ 	FINE	*********************/
		
//		bean.getParametriDB().put("ATTIVA_SCARICO_ATTO_VERS_MODIFICABILE", "true");
		
	}

}
