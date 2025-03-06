package it.eng.areas.hybrid.module.siss.preferences;

public interface PreferenceKeys {
	
	public final static String MAINPANEL_WIDTH="mainPanel.width";
	public final static String MAINPANEL_HEIGHT="mainPanel.height";
	
	public final static String MENU_CONFIGURAZIONI_ENABLED="menuConfigurazioni.enabled";
	public final static String MENU_LOG_ENABLED="menuLog.enabled";
	
	public final static String PREFERENCEFIRMAMARCAPANEL_ENABLED="preferenceFirmaMarcaPanel.enabled";
	public final static String PREFERENCEFIRMAMARCAPANEL_WIDTH="preferenceFirmaMarcaPanel.width";
	public final static String PREFERENCEFIRMAMARCAPANEL_HEIGHT="preferenceFirmaMarcaPanel.height";
	public final static String PREFERENCEINVIOLOGPANEL_ENABLED="preferenceInvioLogPanel.enabled";
	public final static String PREFERENCEINVIOLOGPANEL_WIDTH="preferenceInvioLogPanel.width";
	public final static String PREFERENCEINVIOLOGPANEL_HEIGHT="preferenceInvioLogPanel.height";
	public final static String PREFERENCERETEPANEL_ENABLED="preferenceRetePanel.enabled";
	public final static String INVIOLOGPANEL_WIDTH="invioLogPanel.width";
	public final static String INVIOLOGPANEL_HEIGHT="invioLogPanel.height";
	
	public final static String PREFERENCE_ACTIVITYPANEL_ENABLED="preference.activityPanel.enabled";
	
	public final static String PROPERTY_DEBUGENABLED="debug.enabled";
	
	//property di logging
	public final static String PROPERTY_LOGENABLED="log.enabled";
	public final static String PROPERTY_LOGFILEENABLED="logFile.enabled";
	public final static String PROPERTY_LOGARRAYENABLED="logArray.enabled";
	
	//tab da mostrare
	public final static String PROPERTY_TABS_CONFIG="tabs.config";
	
	//SmartCard Provider
	public final static String SMART_PROVIDER_WIND="smart.provider.window";
	public final static String SMART_PROVIDER_LINUX="smart.provider.linux";
	
	public final static String PROPERTY_SIGN_EMULATIONMODE="sign.emulationMode";
	public final static String PROPERTY_SIGN_FILE_SELECTION_ENABLED="sign.fileSelectionEnabled";
	public final static String PROPERTY_MARK_ENABLED="sign.markEnabled";
	public final static String PROPERTY_COUNTERSIGN_ENABLED="sign.counterSignEnabled";
	public final static String PROPERTY_SIGN_SHOWLISTFILE="sign.showListFile";
	public final static String PROPERTY_SIGN_VERIFICAFIRME_ENABLED="sign.verificaFirmeEnabled";
	
	public final static String PROPERTY_SIGN_ALGORITHM="sign.algorithm";
	public final static String PROPERTY_SIGN_ENVELOPE_MERGE="sign.envelope.merge";
	public final static String PROPERTY_SIGN_ENVELOPE_MERGE_OPTIONS="sign.envelope.merge.options";
	public final static String PROPERTY_SIGN_ENVELOPE_TYPE="sign.envelope.type";
	public final static String PROPERTY_SIGN_ENVELOPE_TYPE_OPTIONS="sign.envelope.type.options";
	public final static String PROPERTY_SIGN_MARCATURA_SOLO_NON_MARCATE="sign.marcatura.onlyNotTimestamped";
	
	public final static String PROPERTY_SIGN_PDF_RENDER_MODE="sign.pdf.renderMode";
	public final static String PROPERTY_SIGN_PDF_CERTIFICATIONLEVEL="sign.pdf.certificationLevel";
	public final static String PROPERTY_SIGN_PDF_L2TEXT="sign.pdf.l2Text";
	public final static String PROPERTY_SIGN_PDF_L2TEXTSIZE="sign.pdf.l2TextSize";
	public final static String PROPERTY_SIGN_PDF_IMAGE_BG="sign.pdf.imgBG";
	public final static String PROPERTY_SIGN_PDF_IMAGE_BG_SCALE="sign.pdf.imgBG.scale";
	public final static String PROPERTY_SIGN_PDF_IMAGE_SIGN="sign.pdf.imgSign";
	public final static String PROPERTY_SIGN_PDF_VISIBLE_SIGNATURE="sign.pdf.visibleSignature";
	public final static String PROPERTY_SIGN_PDF_SIGNPAGE="sign.pdf.signPage";
	
	public final static String PROPERTY_SIGN_CADES_PDFCONVERSION="sign.cades.pdfConversion" ;
	
	public final static String PROPERTY_SIGN_TSASERVER="sign.tsaServer";
	public final static String PROPERTY_SIGN_TSASERVER_OPTIONS="sign.tsaServer.options";
	public final static String PROPERTY_SIGN_TSAAUTH="sign.tsaAuth";
	public final static String PROPERTY_SIGN_TSAUSER="sign.tsaUser";
	public final static String PROPERTY_SIGN_TSAPASSWORD="sign.tsaPassword";
	
	public final static String PROPERTY_SIGN_CACHECKCERTIFICATEENABLED="sign.cacheckCertificateEnabled";
	public final static String PROPERTY_SIGN_CRLCHECKCERTIFICATEENABLED="sign.crlcheckCertificateEnabled";
	public final static String PROPERTY_SIGN_CACHECKENABLED="sign.cacheckEnabled";
	public final static String PROPERTY_SIGN_CRLCHECKENABLED="sign.crlcheckEnabled";
	public final static String PROPERTY_SIGN_CODECHECKENABLED="sign.codecheckEnabled";
	public final static String PROPERTY_SIGN_DATARIFERIMENTOVERIFICA="sign.dataRiferimentoVerifica";
	public final static String PROPERTY_SIGN_DATARIFERIMENTOVERIFICA_FORMATO="sign.dataRiferimentoVerifica.formato";
	
	//data a cui verificare la validità del certificato di firma in millisecondi;
	public final static String DATE_TO_CHECK="signer.dateToCheck";
		
	//WS autenticazione
	public final static String PROPERTY_SIGN_ENVELOPEWSAUTH="sign.envelopeWsAuth";

	public final static String PROPERTY_FILEINPUT_PROVIDER="fileInputProvider";
	public final static String PROPERTY_FILEOUTPUT_PROVIDER="fileOutputProvider";
	public final static String PROPERTY_LOCALFILE="localFile";
	public final static String PROPERTY_FILENAME="fileName";
	public final static String PROPERTY_FILENAME1="fileName1";	
	public final static String PROPERTY_OUTPUTDIR="outputDir";
	public final static String PROPERTY_OUTPUTFILENAME="outputFileName";
	public final static String PROPERTY_OUTPUTURL="outputUrl";
	public final static String PROPERTY_IDSELECTED="idSelected";
	public final static String PROPERTY_CALLBACK="callBack";
	public final static String PROPERTY_DIRECTURL="directUrl";
	public static final String PROPERTY_CALLBACK_START = "callBackStart";
	public final static String PROPERTY_NUMFILES="numFiles";
	public final static String PROPERTY_HASHFILE="hash";
	public final static String PROPERTY_FIRMATARIOFILE="firmatario";
	public final static String PROPERTY_ISFIRMAPRESENTE="firmaPresente";
	public final static String PROPERTY_ISFIRMAVALIDA="firmaValida";
	public final static String PROPERTY_VERSIONEFILE="versione";
	public final static String PROPERTY_FILETYPE="fileType";
	public final static String PROPERTY_IDFILE="idFile";
	public final static String PROPERTY_TEMPPATHFILE="tempPathFile";
	public final static String PROPERTY_ISFIRMATO="isFirmato";
	public final static String PROPERTY_AUTOCLOSEPOSTSIGN="autoClosePostSign";
	public final static String PROPERTY_CALLBACKASKFORCLOSE="callBackAskForClose";
	
	public final static String PROPERTY_CODICEAPPLICAZIONE="codiceApplicazione" ;
	public final static String PROPERTY_CODICEISTANZAAPPLICAZIONE="codiceIstanzaApplicazione" ;
	public final static String PROPERTY_AURIGAUSERNAME="aurigaUsername" ;
	public final static String PROPERTY_AURIGAPASSWORD="aurigaPassword" ;
	public final static String PROPERTY_AURIGAIDDOCUMENTO="aurigaIdDocumento" ;
	public final static String PROPERTY_AURIGAIDVERSIONEDOCUMENTO="aurigaIdVersioneDocumento" ;
	public final static String PROPERTY_AURIGANUMEROALLEGATO="aurigaNumeroAllegato" ;
	public final static String PROPERTY_AURIGAHOST="aurigaHost" ;
	public final static String PROPERTY_AURIGAPORT="aurigaPort" ;
		
	public final static String PROPERTY_COGNOMENOME_CERTIFICATO="cognomeNome.certificato" ;
	public final static String PROPERTY_CODICEFISCALE_CERTIFICATO="codiceFiscale.certificato" ;
	
	public final static String PROPERTY_MAIL_TO="mail.to" ;
	public final static String PROPERTY_MAIL_FROM="mail.from" ;
	public final static String PROPERTY_MAIL_SUBJECT="mail.subject" ;
	public final static String PROPERTY_MAIL_BODY="mail.body" ;
	public final static String PROPERTY_MAIL_ATTACHMENTNAME="mail.attachmentName" ;
	public final static String PROPERTY_MAIL_SMTPHOST="mail.smtpHost" ;
	public final static String PROPERTY_MAIL_USEAUTHENTICATION="mail.useAuthentication" ;
	public final static String PROPERTY_MAIL_AUTHENTICATION_USER="mail.authenticationUser" ;
	public final static String PROPERTY_MAIL_AUTHENTICATION_PASSWORD="mail.authenticationPassword" ;
		
	public final static String PROPERTY_FILEOPERATION_WSENDPOINT="fileOperation.wsEndpoint" ;
	public final static String PROPERTY_FILEOPERATION_NAMESPACE="fileOperation.namespace" ;
	public final static String PROPERTY_FILEOPERATION_SERVICE="fileOperation.service" ;
	
	public final static String PROPERTY_VERIFYCERT_WSENDPOINT="verifyCert.wsEndpoint" ;
	public final static String PROPERTY_VERIFYCERT_NAMESPACE="verifyCert.namespace" ;
	public final static String PROPERTY_VERIFYCERT_SERVICE="verifyCert.service" ;
	public static final String PROPERTY_TEST_URL = "testUrl";
	
	public static final String PROPERTY_COMMON_NAME_CALLBACK="commonNameCallback";
	public final static String PROPERTY_COMMON_NAME_PROVIDER="commonNameProvider";
}
