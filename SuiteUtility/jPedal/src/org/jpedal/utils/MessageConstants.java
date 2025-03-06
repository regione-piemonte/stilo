package org.jpedal.utils;

public interface MessageConstants {
	
	//Messaggi del pannello di invio log
	public final static String PANEL_INVIA_LOG_TITLE = "panel.inviaLog.title";
	public final static String PANEL_INVIA_LOG_TEXT = "panel.inviaLog.text";
	public final static String PANEL_INVIA_LOG_TEXT1 = "panel.inviaLog.text1";
	public final static String PANEL_INVIA_LOG_BUTTON_SENDMAIL = "panel.inviaLog.button.sendMail";
	public final static String PANEL_INVIA_LOG_BUTTON_SENDMAIL_TOOLTIP = "panel.inviaLog.button.sendMail.tooltip";
	
	//Messaggi del pannello di firma e marcatura
	public final static String PANEL_FIRMAMARCA_TITLE = "panel.firmaMarca.title";
	public final static String PANEL_FIRMAMARCA_FIELD_PIN = "panel.firmaMarca.field.pin";
	public final static String PANEL_FIRMAMARCA_FIELD_SO = "panel.firmaMarca.field.so";
	public final static String PANEL_FIRMAMARCA_FIELD_EMULATION = "panel.firmaMarca.field.emulation";
	public final static String PANEL_FIRMAMARCA_FIELD_OUTPUTFILE = "panel.firmaMarca.field.outputFile";
	public final static String PANEL_FIRMAMARCA_FIELD_SKIPCA = "panel.firmaMarca.field.skipCa";
	public final static String PANEL_FIRMAMARCA_FIELD_SKIPCRL = "panel.firmaMarca.field.skipCrl";
	public final static String PANEL_FIRMAMARCA_FIELD_ENABLEOCSP = "panel.firmaMarca.field.enableOCSP";
	public final static String PANEL_FIRMAMARCA_FIELD_REASON = "panel.firmaMarca.field.reason";
	public final static String PANEL_FIRMAMARCA_FIELD_LOCATION = "panel.firmaMarca.field.reason.location";
	public final static String PANEL_FIRMAMARCA_FIELD_CONTACT = "panel.firmaMarca.field.reason.contact";
	public final static String PANEL_FIRMAMARCA_FIELD_VISIBLESIGNATURE = "panel.firmaMarca.field.visibleSignature";
	public final static String PANEL_FIRMAMARCA_FIELD_APPEND = "panel.firmaMarca.field.append";
	public final static String PANEL_FIRMAMARCA_PREVIEW_TOOLTIP = "panel.firmaMarca.preview.tooltip";
	public final static String PANEL_FIRMAMARCA_PAGENUMBER = "panel.firmaMarca.pageNumber";
	public final static String PANEL_FIRMAMARCA_BUTTON_SIGNORIGINAL = "panel.firmaMarca.button.signOriginal";
	public final static String PANEL_FIRMAMARCA_BUTTON_SIGNSIGNED = "panel.firmaMarca.button.signSigned";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODEROOT = "panel.firmaMarca.hierarchyNodeRoot";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODEFIRMA = "panel.firmaMarca.hierarchyNodeFirma";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODECERTIFICATO = "panel.firmaMarca.hierarchyNodeCertificato";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODEMARCA = "panel.firmaMarca.hierarchyNodeMarca";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODETSANAME = "panel.firmaMarca.hierarchyNodeTsaName";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODESERIALNUMBER = "panel.firmaMarca.hierarchyNodeSerialNumber";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODEPOLICY = "panel.firmaMarca.hierarchyNodePolicy";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODEDATA = "panel.firmaMarca.hierarchyNodeData";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODECONTROFIRMA = "panel.firmaMarca.hierarchyNodeControFirma";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODEESITOFIRMA = "panel.firmaMarca.hierarchyNodeEsitoFirma";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODEESITOCERTIFICATO = "panel.firmaMarca.hierarchyNodeEsitoCertificato";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODEESITOCA = "panel.firmaMarca.hierarchyNodeEsitoCA";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODEESITOCRL = "panel.firmaMarca.hierarchyNodeEsitoCRL";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODEESITOMARCA = "panel.firmaMarca.hierarchyNodeEsitoMarca";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODEDATASCADENZA = "panel.firmaMarca.hierarchyNodeDataScadenza";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODEDATADECORRENZA = "panel.firmaMarca.hierarchyNodeDataDecorrenza";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODEISSUER = "panel.firmaMarca.hierarchyNodeIssuer";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODESUBJECT = "panel.firmaMarca.hierarchyNodeSubject";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODENOME = "panel.firmaMarca.hierarchyNodeNome";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODECN = "panel.firmaMarca.hierarchyNodeCn";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODEC = "panel.firmaMarca.hierarchyNodeC";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODEOU = "panel.firmaMarca.hierarchyNodeOu";
	public final static String PANEL_FIRMAMARCA_HIERARCHYNODEO = "panel.firmaMarca.hierarchyNodeO";
	
	//Messaggi del pannello delle impostazioni di firmatura e marcatura
	public final static String PANEL_PREFERENCE_FIRMAMARCA_TITLE = "panel.preference.firmaMarca.title";
	public final static String PANEL_PREFERENCE_FIRMAMARCA_TABS_FIRMA_TITLE = "panel.preference.firmaMarca.tabs.firma.title";
	public final static String PANEL_PREFERENCE_FIRMAMARCA_TABS_MARCA_TITLE = "panel.preference.firmaMarca.tabs.marca.title";
	public final static String PANEL_PREFERENCE_FIRMAMARCA_FIELD_APPEND = "panel.preference.firmaMarca.field.append";
	public final static String PANEL_PREFERENCE_FIRMAMARCA_FIELD_ACRO6LAYER = "panel.preference.firmaMarca.field.acro6Layer";
	public final static String PANEL_PREFERENCE_FIRMAMARCA_FIELD_RENDERMODE = "panel.preference.firmaMarca.field.renderMode";
	public final static String PANEL_PREFERENCE_FIRMAMARCA_FIELD_L2FONTSIZELAYER = "panel.preference.firmaMarca.field.l2FontSizeLayer";
	public final static String PANEL_PREFERENCE_FIRMAMARCA_FIELD_L2FONTLAYER = "panel.preference.firmaMarca.field.l2TextLayer";
	public final static String PANEL_PREFERENCE_FIRMAMARCA_FIELD_L4FONTLAYER = "panel.preference.firmaMarca.field.l4TextLayer";
	public final static String PANEL_PREFERENCE_FIRMAMARCA_FIELD_HASHALGORITHM = "panel.preference.firmaMarca.field.hashAlgorithm";
	public final static String PANEL_PREFERENCE_FIRMAMARCA_FIELD_CERTIFYMODE = "panel.preference.firmaMarca.field.certifyMode";
	public final static String PANEL_PREFERENCE_FIRMAMARCA_FIELD_IMGPATH = "panel.preference.firmaMarca.field.imgPath";
	public final static String PANEL_PREFERENCE_FIRMAMARCA_FIELD_BGIMGPATH = "panel.preference.firmaMarca.field.bgImgPath";
	public final static String PANEL_PREFERENCE_FIRMAMARCA_FIELD_BGIMGSCALE = "panel.preference.firmaMarca.field.bgImgScale";
	public final static String PANEL_PREFERENCE_FIRMAMARCA_FIELD_ENABLECACHECKCERTIFICATO = "panel.preference.firmaMarca.field.enableCaCheckCertificato";
	public final static String PANEL_PREFERENCE_FIRMAMARCA_FIELD_ENABLECRLCHECKCERTIFICATO = "panel.preference.firmaMarca.field.enableCrlCheckCertificato";
	public final static String PANEL_PREFERENCE_FIRMAMARCA_FIELD_ENABLECACHECKFIRME = "panel.preference.firmaMarca.field.enableCaCheckFirme";
	public final static String PANEL_PREFERENCE_FIRMAMARCA_FIELD_ENABLECRLCHECKFIRME = "panel.preference.firmaMarca.field.enableCrlCheckFirme";
	public final static String PANEL_PREFERENCE_FIRMAMARCA_FIELD_TSAURL = "panel.preference.firmaMarca.field.tsaUrl";
	public final static String PANEL_PREFERENCE_FIRMAMARCA_FIELD_TSAUSER = "panel.preference.firmaMarca.field.tsaUser";
	public final static String PANEL_PREFERENCE_FIRMAMARCA_FIELD_TSAPASSWORD = "panel.preference.firmaMarca.field.tsaPassword";
	public final static String PANEL_PREFERENCE_FIRMAMARCA_FIELD_TSAAUTH = "panel.preference.firmaMarca.field.tsaAuth";
	
	
	//Messaggi del pannello del timbro
	public final static String PANEL_TIMBRO_TITLE = "panel.timbro.title";
	public final static String PANEL_TIMBRO_BUTTON_TIMBRA = "panel.timbro.button.timbra";
	public final static String PANEL_TIMBRO_BUTTON_TIMBRA_TOOLTIP = "panel.timbro.button.timbra.tooltip";
	
	//Messaggi del pannello delle impostazioni del timbro
	public final static String PANEL_PREFERENCE_TIMBRO_TITLE = "panel.preference.timbro.title";
	public final static String PANEL_PREFERENCE_TIMBRO_TABS_CODIFICA_TITLE = "panel.preference.timbro.tabs.codifica.title";
	public final static String PANEL_PREFERENCE_TIMBRO_TABS_TESTI_TITLE = "panel.preference.timbro.tabs.testi.title";
	public final static String PANEL_PREFERENCE_TIMBRO_TABS_ALTRE_OPZIONI_TITLE = "panel.preference.timbro.tabs.altreOpzioni.title";
	public final static String PANEL_PREFERENCE_TIMBRO_FIELD_TESTO = "panel.preference.timbro.field.testo";
	public final static String PANEL_PREFERENCE_TIMBRO_FIELD_ADDDIGEST = "panel.preference.timbro.field.addDigest";
	public final static String PANEL_PREFERENCE_TIMBRO_FIELD_ADDDN = "panel.preference.timbro.field.addDn";
	public final static String PANEL_PREFERENCE_TIMBRO_FIELD_ADDDATA = "panel.preference.timbro.field.addData";
	public final static String PANEL_PREFERENCE_TIMBRO_FIELD_ALGORITMODIGEST = "panel.preference.timbro.field.algoritmoDigest";
	public final static String PANEL_PREFERENCE_TIMBRO_FIELD_TIMBROSINGOLO = "panel.preference.timbro.field.timbroSingolo";
	public final static String PANEL_PREFERENCE_TIMBRO_FIELD_CODIFICA = "panel.preference.timbro.field.codifica";
	public final static String PANEL_PREFERENCE_TIMBRO_FIELD_POSIZIONE = "panel.preference.timbro.field.posizione";
	public final static String PANEL_PREFERENCE_TIMBRO_FIELD_ROTAZIONE = "panel.preference.timbro.field.rotazione";
	public final static String PANEL_PREFERENCE_TIMBRO_FIELD_TESTO_INTESTAZIONE = "panel.preference.timbro.field.testoIntestazione";
	public final static String PANEL_PREFERENCE_TIMBRO_FIELD_POSIZIONE_INTESTAZIONE = "panel.preference.timbro.field.posizioneIntestazione";
	public final static String PANEL_PREFERENCE_TIMBRO_FIELD_POSIZIONE_TESTOINCHIARO = "panel.preference.timbro.field.posizioneTestoInChiaro";
	public final static String PANEL_PREFERENCE_TIMBRO_FIELD_MORELINES = "panel.preference.timbro.field.moreLines";
	public final static String PANEL_PREFERENCE_TIMBRO_FIELD_FONTNAME = "panel.preference.timbro.field.fontName";
	public final static String PANEL_PREFERENCE_TIMBRO_FIELD_FONTSIZE = "panel.preference.timbro.field.fontSize";
	public final static String PANEL_PREFERENCE_TIMBRO_FIELD_TIPOPAGINA = "panel.preference.timbro.field.tipoPagina";
	public final static String PANEL_PREFERENCE_TIMBRO_FIELD_PAGINACORRENTE = "panel.preference.timbro.field.paginaCorrente";
	public final static String PANEL_PREFERENCE_TIMBRO_FIELD_PAGINADA = "panel.preference.timbro.field.paginaDa";
	public final static String PANEL_PREFERENCE_TIMBRO_FIELD_PAGINAA = "panel.preference.timbro.field.paginaA";
	
	//Messaggi del pannello delle impostazioni di rete
	public final static String PANEL_PREFERENCE_RETE_TITLE = "panel.preference.rete.title";
	
	//Messaggi del pannello delle impostazioni di invio Log
	public final static String PANEL_PREFERENCE_INVIOLOG_TITLE = "panel.preference.invioLog.title";
	public final static String PANEL_PREFERENCE_INVIOLOG_FIELD_SMTPHOST = "panel.preference.invioLog.field.smtpHost";
	public final static String PANEL_PREFERENCE_INVIOLOG_FIELD_USEAUTHENTICATION = "panel.preference.invioLog.field.useAuthentication";
	public final static String PANEL_PREFERENCE_INVIOLOG_FIELD_USERAUTHENTICATION = "panel.preference.invioLog.field.userAuthentication";
	public final static String PANEL_PREFERENCE_INVIOLOG_FIELD_PASSWORDAUTHENTICATION = "panel.preference.invioLog.field.passwordAuthentication";
	public final static String PANEL_PREFERENCE_INVIOLOG_FIELD_MAILATTACHMENTNAME = "panel.preference.invioLog.field.mailAttachmentName";
	public final static String PANEL_PREFERENCE_INVIOLOG_FIELD_MAILFROM = "panel.preference.invioLog.field.mailFrom";
	public final static String PANEL_PREFERENCE_INVIOLOG_FIELD_MAILTO = "panel.preference.invioLog.field.mailTo";
	public final static String PANEL_PREFERENCE_INVIOLOG_FIELD_MAILSUBJECT = "panel.preference.invioLog.field.mailSubject";
	public final static String PANEL_PREFERENCE_INVIOLOG_FIELD_MAILBODY = "panel.preference.invioLog.field.mailBody";
	
	//Messaggi generici dei pannelli delle impostazioni
	public final static String PANEL_BUTTON_SAVE = "panel.button.save";
	public final static String PANEL_BUTTON_SAVE_TOOLTIP = "panel.button.save.tooltip";
	public final static String PANEL_BUTTON_CLOSE = "panel.button.close";
	public final static String PANEL_BUTTON_CLOSE_TOOLTIP = "panel.button.close.tooltip";
	public final static String PANEL_BUTTON_BROWSE = "panel.button.browse";
	public final static String PANEL_BUTTON_BROWSE_TOOLTIP = "panel.button.browse.tooltip";
	public final static String PANEL_BUTTON_CANCEL = "panel.button.cancel";
	public final static String PANEL_BUTTON_CANCEL_TOOLTIP = "panel.button.cancel.tooltip";
	
	//MESSAGGI GENERICI
	public final static String MSG_SI ="msg.si";
	public final static String MSG_NO ="msg.no";
	public final static String MSG_DETAILS ="msg.details";
	public final static String MSG_STEP_TITLE1 ="msg.step.title1";
	public final static String MSG_STEP_TITLE2 ="msg.step.title2";
	public final static String MSG_ERROR_GENERAL_TITLE ="msg.error.general.title";
	public final static String MSG_WARNING_TITLE ="msg.warning.title";
	
	public final static String MSG_OPENFILE_NOTPDF ="msg.openFile.notPdf";
	public final static String MSG_OPENFILE_FILENOTEXIST ="msg.openFile.fileNotExist";
	public final static String MSG_OPENFILE_FILENOTFOUND ="msg.openFile.fileNotFound";
	public final static String MSG_MISSINGPARAMETERS ="msg.missingParameters";
	public final static String MSG_FO_EXCEPTION ="msg.fo.exception";	
	public final static String MSG_FO_CONVERSION_EXCEPTION ="msg.fo.conversion.exception";	
	public final static String MSG_FO_UNPACK_EXCEPTION ="msg.fo.unpack.exception";	
	
	//MESSAGGI DELLE OPERAZIONI DI TIMBRO
	public final static String MSG_TIMBRO_SUCCESS = "msg.timbro.ok";
	public final static String MSG_TIMBRO_SAVE_SUCCESS = "msg.timbro.save.ok";
	public final static String MSG_TIMBRO_PDFROTATED ="msg.timbro.pdfRotated";
	public final static String MSG_TIMBRO_FONTFIXED ="msg.timbro.fontFixed";
	public final static String MSG_TIMBRO_ERROR_GENERIC ="msg.timbro.error.generic";
	public final static String MSG_TIMBRO_ERROR_GENERIC_SAVE ="msg.timbro.error.generic.save";
	public final static String MSG_TIMBRO_ERROR_POSIZIONETESTO ="msg.timbro.error.posizioneTesto";
	public final static String MSG_TIMBRO_ERROR_POSIZIONETESTI ="msg.timbro.error.posizioneTesti";
	public final static String MSG_TIMBRO_ERROR_PAGINAUNDEFINED ="msg.timbro.error.paginaUndefined";
	public final static String MSG_TIMBRO_ERROR_PAGINAINT ="msg.timbro.error.paginaInt";
	
	//MESSAGGI DELLE OPERAZIONI DI FIRMA
	public final static String MSG_FIRMAMARCA_SAVE_SUCCESS = "msg.firmaMarca.save.ok";
	public final static String MSG_FIRMAMARCA_CANCEL = "msg.firmaMarca.cancel";
	public final static String MSG_FIRMAMARCA_ERROR_GENERIC = "msg.firmaMarca.error.generic";
	public final static String MSG_FIRMAMARCA_ERROR_GENERIC_SAVE = "msg.firmaMarca.error.generic.save";
	public final static String MSG_INVALID_SIGNING_CERT_NAME ="msg.sign.invalidSigningCertName";
	public final static String MSG_INVALID_SIGNING_CERT_CF ="msg.sign.invalidSigningCertCF";
	public final static String MSG_FIRMAMARCA_INVALIDEXISTINGSIGNATURE = "msg.firmaMarca.invalidExistingSignature";
	public final static String MSG_FIRMAMARCA_INVALIDPDFVERSION = "msg.firmaMarca.invalidPdfVersione";
	public final static String MSG_FIRMAMARCA_INVALIDPIN = "msg.firmaMarca.invalidPin";
	public final static String MSG_FIRMAMARCA_INVALIDCERTIFICATE = "msg.firmaMarca.invalidCertificate";
	public final static String MSG_FIRMAMARCA_NOCERTIFICATE = "msg.firmaMarca.noCertificate";
	
	//MESSAGGI DELLE OPERAZIONI DI INVIO LOG
	public final static String MSG_INVIOLOG_SUCCESS = "msg.invioLog.ok";
	public final static String MSG_INVIOLOG_SAVE_SUCCESS = "msg.invioLog.save.ok";
	public final static String MSG_INVIOLOG_CANCEL = "msg.invioLog.cancel";
	public final static String MSG_INVIOLOG_ERROR_GENERIC = "msg.invioLog.error.generic";
	public final static String MSG_INVIOLOG_ERROR_GENERIC_SAVE = "msg.invioLog.error.generic.save";
	
	//Messaggi del menu
	public final static String MENU_TIMBRO_TITLE ="menu.timbro.title";
	public final static String MENU_TIMBRO_TOOLTIP ="menu.timbro.tooltip";
	public final static String MENU_FIRMA_TITLE ="menu.firma.title";
	public final static String MENU_FIRMA_TOOLTIP ="menu.firma.tooltip";
	public final static String MENU_MARCA_TITLE ="menu.marca.title";
	public final static String MENU_MARCA_TOOLTIP ="menu.marca.tooltip";
	public final static String MENU_FIRMAMARCA_TITLE ="menu.firmaMarca.title";
	public final static String MENU_FIRMAMARCA_TOOLTIP ="menu.firmaMarca.tooltip";
	public final static String MENU_PRINT_TITLE ="menu.print.title";
	public final static String MENU_PRINT_TOOLTIP ="menu.print.tooltip";
	public final static String MENU_INVIOLOG_TITLE ="menu.invioLog.title";
	public final static String MENU_INVIOLOG_TOOLTIP ="menu.invioLog.tooltip";
	public final static String MENU_BACKTO_ORIGINALFILE_TITLE ="menu.backOriginalFile.title";
	public final static String MENU_BACKTO_ORIGINALFILE_TOOLTIP ="menu.backOriginalFile.tooltip";
	
	public final static String MENU_PREFERENCE_TITLE ="menu.preference.title";
	public final static String MENU_PREFERENCE_TIMBRO_TITLE ="menu.preference.timbro.title";
	public final static String MENU_PREFERENCE_RETE_TITLE ="menu.preference.rete.title";
	public final static String MENU_PREFERENCE_FIRMAMARCA_TITLE ="menu.preference.firmaMarca.title";
	public final static String MENU_PREFERENCE_INVIOLOG_TITLE ="menu.preference.invioLog.title";
	public final static String MENU_PREFERENCE_INVIOLOG_TOOLTIP ="menu.preference.invioLog.tooltip";
	public final static String MENU_PREFERENCE_RETE_TOOLTIP ="menu.preference.rete.tooltip";
	public final static String MENU_PREFERENCE_TIMBRO_TOOLTIP ="menu.preference.timbro.tooltip";
	public final static String MENU_PREFERENCE_FIRMAMARCA_TOOLTIP ="menu.preference.firmaMarca.tooltip";

}
