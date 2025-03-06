package it.eng.applet.stampaFile.preferences;

public interface PreferenceKeys {
	
	public final static String MAINPANEL_WIDTH="mainPanel.width";
	public final static String MAINPANEL_HEIGHT="mainPanel.height";
	
	public final static String MENU_CONFIGURAZIONI_ENABLED="menuConfigurazioni.enabled";
	public final static String MENU_LOG_ENABLED="menuLog.enabled";
	public final static String PREFERENCERETEPANEL_ENABLED="preferenceRetePanel.enabled";
	
	public final static String PREFERENCESTAMPAPANEL_ENABLED="preferenceStampaPanel.enabled";
	
	public final static String PREFERENCEINVIOLOGPANEL_WIDTH="preferenceInvioLogPanel.width";
	public final static String PREFERENCEINVIOLOGPANEL_HEIGHT="preferenceInvioLogPanel.height";
	public final static String PREFERENCEINVIOLOGPANEL_ENABLED="preferenceInvioLogPanel.enabled";
	
	public final static String INVIOLOGPANEL_WIDTH="invioLogPanel.width";
	public final static String INVIOLOGPANEL_HEIGHT="invioLogPanel.height";
	
	//property di logging
	public final static String PROPERTY_LOGENABLED="log.enabled";
	public final static String PROPERTY_LOGFILEENABLED="logFile.enabled";
	public final static String PROPERTY_LOGARRAYENABLED="logArray.enabled";
	
	public final static String PROPERTY_FILEINPUT_PROVIDER="fileInputProvider";
	public final static String PROPERTY_OUTPUT_PROVIDER="outputProvider";
	public final static String PROPERTY_LOCALFILE="localFile";
	public final static String PROPERTY_FILENAME="fileName";
	public final static String PROPERTY_DIRECTURL="directUrl";
	public final static String PROPERTY_NUMFILES="numFiles";
	public final static String PROPERTY_AUTOCLOSEPOSTPRINT="autoClosePostPrint";
	public final static String PROPERTY_CALLBACKASKFORCLOSE="callBackAskForClose";
	
	public final static String PROPERTY_STAMPANTE_SELEZIONATA="stampanteSelezionata";
	public final static String PROPERTY_SKIP_USER_INTERFACE="skipUserInterface";
	public final static String PROPERTY_SKIP_SCELTA_STAMPANTE="skipSceltaStampante";
	
	public static final String PROPERTY_TEST_URL = "testUrl";
	
	public final static String PROPERTY_MAIL_TO="mail.to" ;
	public final static String PROPERTY_MAIL_FROM="mail.from" ;
	public final static String PROPERTY_MAIL_SUBJECT="mail.subject" ;
	public final static String PROPERTY_MAIL_BODY="mail.body" ;
	public final static String PROPERTY_MAIL_ATTACHMENTNAME="mail.attachmentName" ;
	public final static String PROPERTY_MAIL_SMTPHOST="mail.smtpHost" ;
	public final static String PROPERTY_MAIL_USEAUTHENTICATION="mail.useAuthentication" ;
	public final static String PROPERTY_MAIL_AUTHENTICATION_USER="mail.authenticationUser" ;
	public final static String PROPERTY_MAIL_AUTHENTICATION_PASSWORD="mail.authenticationPassword" ;
	
	public final static String PROPERTY_PROPERTIES_SERVLET="pdfServlet" ;
	public final static String PROPERTY_ID_UTENTE="idUtente" ;
	public final static String PROPERTY_ID_SCHEMA="idSchema" ;
	public final static String PROPERTY_ID_DOMINIO="idDominio" ;
	
}
