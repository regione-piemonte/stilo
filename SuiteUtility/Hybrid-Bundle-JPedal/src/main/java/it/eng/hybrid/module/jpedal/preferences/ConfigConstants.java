package it.eng.hybrid.module.jpedal.preferences;

public interface ConfigConstants {
	
	public final static String PROPERTY_FILEINPUT_PROVIDER="fileInputProvider" ;
	public final static String PROPERTY_FILEOUTPUT_PROVIDER="fileOutputProvider" ;
	
	public final static String PROPERTY_FILEPROPERTIES="propertiesFile";
	public final static String PROPERTY_TIPOAPPLET="tipoApplet" ;
	
	public final static String PROPERTY_READONLY="readOnly" ;
	
	public final static String PROPERTY_CODICEAPPLICAZIONE="codiceApplicazione" ;
	public final static String PROPERTY_CODICEISTANZAAPPLICAZIONE="codiceIstanzaApplicazione" ;
	public final static String PROPERTY_AURIGAHOST="aurigaHost" ;
	public final static String PROPERTY_AURIGAPORT="aurigaPort" ;
	public final static String PROPERTY_AURIGAUSERNAME="aurigaUsername" ;
	public final static String PROPERTY_AURIGAPASSWORD="aurigaPassword" ;
	public final static String PROPERTY_AURIGAIDDOCUMENTO="aurigaIdDocumento" ;
	public final static String PROPERTY_AURIGAIDVERSIONEDOCUMENTO="aurigaIdVersioneDocumento" ;
	public final static String PROPERTY_AURIGANUMEROALLEGATO="aurigaNumeroAllegato" ;
	public final static String PROPERTY_LOCALFILE="localFile" ;
	public final static String PROPERTY_FILENAME="fileName" ;
	public final static String PROPERTY_DIRECTURL="directUrl" ;
	public final static String PROPERTY_SERVLET="servlet" ;
	public final static String PROPERTY_OPENURL="openURL" ;
	public final static String PROPERTY_OUTPUTDIR="outputDir" ;
	public final static String PROPERTY_OUTPUTFILENAME="outputFileName" ;
	public final static String PROPERTY_OUTPUTURL="outputUrl" ;
	public final static String PROPERTY_IDSELECTED="idSelected" ;
	public final static String PROPERTY_CALLBACK="callBack" ;
	
	public final static String PROPERTY_EMULATION="emulation" ;
	public final static String PROPERTY_LOGENABLED="log.enabled" ;
	public final static String PROPERTY_LOGFILEENABLED="logFile.enabled";
	public final static String PROPERTY_LOGARRAYENABLED="logArray.enabled";
	public final static String PROPERTY_PRINTENABLED="print.enabled" ;
	public final static String PROPERTY_SAVEENABLED="save.enabled" ;
	public final static String PROPERTY_SIGNENABLED="sign.enabled" ;
	public final static String PROPERTY_MARKENABLED="mark.enabled" ;
	
	public final static String PROPERTY_SUNPKCS11LIB_WINDOWS="SunPKCS11LibWindows" ;
	public final static String PROPERTY_SUNPKCS11LIB_LINUX="SunPKCS11LibLinux" ;
	
	public final static String FIRMA_PROPERTY_HASHALGORITHM="firma.hashAlgorithm" ;
	public final static String FIRMA_PROPERTY_CERTIFYMODE="firma.certifyMode" ;
	public final static String FIRMA_PROPERTY_OUTPUTFILENAME="firma.outputFileName" ;
	public final static String FIRMA_PROPERTY_TSAURL="firma.tsaUrl" ;
	public final static String FIRMA_PROPERTY_TSAAUTH="firma.tsaAuth";
	public final static String FIRMA_PROPERTY_TSAUSER="firma.tsaUser" ;
	public final static String FIRMA_PROPERTY_TSAPASSWORD="firma.tsaPassword" ;
	public final static String FIRMA_PROPERTY_RENDERMODE="firma.renderMode" ;
	public final static String FIRMA_PROPERTY_APPENDMODE="firma.appendMode" ;
	public final static String FIRMA_PROPERTY_ACRO6LAYER="firma.acro6Layer" ;	
	public final static String FIRMA_PROPERTY_L2TEXT="firma.l2Text" ;
	public final static String FIRMA_PROPERTY_L4TEXT="firma.l4Text" ;
	public final static String FIRMA_PROPERTY_L2TEXTFONTSIZE="firma.l2TextFontSize" ;
	public final static String FIRMA_PROPERTY_IMGPATH="firma.imgPath" ;
	public final static String FIRMA_PROPERTY_BGIMGPATH="firma.bgImgPath" ;
	public final static String FIRMA_PROPERTY_BGIMGSCALE="firma.bgImgScale" ;
	public final static String FIRMA_PROPERTY_ENABLECACHECK="firma.enableCaCheck" ;
	public final static String FIRMA_PROPERTY_ENABLECRLCHECK="firma.enableCrlCheck" ;
	public final static String FIRMA_PROPERTY_ENABLECACHECKCERTIFICATO="firma.enableCaCheckCertificato" ;
	public final static String FIRMA_PROPERTY_ENABLECRLCHECKCERTIFICATO="firma.enableCrlCheckCertificato" ;
	public final static String FIRMA_PROPERTY_DATARIFERIMENTOVERIFICA="firma.dataRiferimentoVerifica" ;
	public final static String FIRMA_PROPERTY_DATARIFERIMENTOVERIFICA_FORMATO="firma.dataRiferimentoVerifica.formato" ;
	
	public static final String TIMBRO_PROPERTY_ENABLED = "timbro.enabled";
	public static final String TIMBRO_PROPERTY_TESTO = "timbro.testo";
	public static final String TIMBRO_PROPERTY_CODIFICA = "timbro.codifica";
	public static final String TIMBRO_PROPERTY_TIMBROSINGOLO = "timbro.timbroSingolo";
	public static final String TIMBRO_PROPERTY_POSIZIONE = "timbro.posizioneTimbro";
	public static final String TIMBRO_PROPERTY_POSIZIONE_INTESTAZIONE = "timbro.posizioneIntestazione";
	public static final String TIMBRO_PROPERTY_POSIZIONE_TESTO_IN_CHIARO = "timbro.posizioneTestoInChiaro";
	public static final String TIMBRO_PROPERTY_TESTO_INTESTAZIONE = "timbro.testoIntestazione";
	public static final String TIMBRO_PROPERTY_TIPO_PAGINA = "timbro.tipoPagina";
	public static final String TIMBRO_PROPERTY_PAGINA_CORRENTE = "timbro.paginaCorrente";
	public static final String TIMBRO_PROPERTY_PAGINA_DA = "timbro.paginaDa";
	public static final String TIMBRO_PROPERTY_PAGINA_A = "timbro.paginaA";
	public static final String TIMBRO_PROPERTY_ROTAZIONE = "timbro.rotazioneTimbro";
	public static final String TIMBRO_PROPERTY_PARAMETER_LIST = "timbro.listaParametri";
	public static final String TIMBRO_PROPERTY_ALGORITMO_DIGEST = "timbro.algoritmoDigest";
	public static final String TIMBRO_PROPERTY_FORMATTAZIONE_DATA = "timbro.formattazioneData";
	public static final String TIMBRO_PROPERTY_MAX_LENGHT_TIMBRO = "timbro.maxLenghtTimbro";
	public static final String TIMBRO_PROPERTY_FONT_SIZE = "timbro.fontSize";
	public static final String TIMBRO_PROPERTY_MARGINE_SUP = "timbro.margineSup";
	public static final String TIMBRO_PROPERTY_MARGINE_INF = "timbro.margineInf";
	public static final String TIMBRO_PROPERTY_MARGINE_DX = "timbro.margineDx";
	public static final String TIMBRO_PROPERTY_MARGINE_SN = "timbro.margineSn";
	public static final String TIMBRO_PROPERTY_MARGINE_TIMBRO_TESTO = "timbro.margineTimbroTesto";
	public static final String TIMBRO_PROPERTY_OUTPUTFILENAME = "timbro.outputFileName";
	public static final String TIMBRO_PROPERTY_FONT_NAME = "timbro.fontName";
	public static final String TIMBRO_PROPERTY_MORE_LINES = "timbro.moreLines";
	public static final String TIMBRO_PROPERTY_ADDDIGEST = "timbro.addDigest";
	public static final String TIMBRO_PROPERTY_ADDDATA = "timbro.addData";
	public static final String TIMBRO_PROPERTY_ADDDN = "timbro.addDN";
	
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
	
	public final static String ENVELOPE_DEFAULT_TYPE="envelope.defaultType";
	//public final static String PROPERTY_SHOW_INVIOMAILFIELD="show.invioMailField";
	
	public final static String PROPERTY_COGNOMENOME_CERTIFICATO="cognomeNome.certificato" ;
	public final static String PROPERTY_CODICEFISCALE_CERTIFICATO="codiceFiscale.certificato" ;
	
	public static final String PROPERTY_CALLBACK_START = "callBackStart";
	
	public final static String PROPERTY_SAVE_AS_OUTPUTURL="saveAsOutputUrl" ;
	public final static String PROPERTY_EXTERNAL_SAVE_AS="externalSaveAs" ;
	
	public final static String TEST_URL="testUrl";
}
