/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class GenericConfigBean {

	private String ente;
	private String applicationName;
	private String menuImage;
	private String logoImage;
	private String backgroundImage;
	private String backgroundColor;
	private String logoDominioFolder;
	private int defaultPortletWidth;
	private int defaultPortletHeight;
	private int maxValueLength;
	private String filterAppliedIcon;
	private String checkIcon;
	private int filtrableSelectPageSize;
	private int recordHeightDefault;
	private int annoPartenza;
	private String displayValueSeparator;
	private String displayValueNull;
	private int delayTimeForDoubleClick;
	private int colonneEstremiRecordMaxValueLength;
	private String prefKeyPrefix;
	private int waitingMessageDuration;
	private int numMaxShortCut;
	private int welcomeMaxLength;
	private int nroMaxRecordsForResizeReorder;
	private String shibbolethLogoutUrlHeaderName;
	private String shibbolethLogoutUrlSuffix;
	private String shibbolethRedirectUrlAfterSessionExpiredHeaderName;
	private String iamLogoutUrlHeaderName;
	private String iamLogoutUrlSuffix;
	private String iamRedirectUrlAfterSessionExpiredHeaderName;
	private Boolean reloadAfterSessionExpired;
	private String logoutRedirectUrl;
	private Boolean defaultDetailAuto;	
	private List<String> editableExtension;
	private List<String> emlExtension;
	private List<String> emlMimetype;
	private String displayApplicationName;
	private int maxNumIconeDesktop;
	private int scannerDefaultResolution;
	private Boolean attivaMultilingua;
	private String defaultLocale;
	private int maxNroDocFirmaMassivaClient;
	private int maxNroDocFirmaMassivaRemotaNonAuto;
	
	// Per impostare il limite inf dell'anno selezionabile sul DatePicker, di Default vale 10 anni prima dell'anno attuale
	private String minAnno;
	// Per impostare il limite sup dell'anno selezionabile sul DatePicker, di Default vale 5 anni dopo l'anno attuale
	private String maxAnno;
	
	// Attiva l'escape dei caratteri html nelle chiamate ai datasource
	// Serve per la sicurezza in INAIL
	private Boolean attivaEscapeHtmlDSCall;
	
	/**
	 * Se TRUE deve essere visualizzato il messaggio che èstato superato il numero massimo di record visualizzabili dalla lista
	 */
	private Boolean showAlertMaxRecord;
	
	// Se TRUE, nella maschera di login  viene mostrato il bottone  "Password dimenticata ?".
	private String showResetPasswordLogin;
	
	
	//se true nasconde stacktrace e url dei servizi nel json di ritorno in caso di errore nelle chiamate a b.e.
	private String hideErrorDetails;
	
	//eventuale classe che implementa ISecurityChecker che verrebbe richiamata ad ogni giro in datasourceservice: usata per implementare check
	//custom per la sicurezza
	private String  securityChecker;
	
	//se true nasconde i bottoni di help e settings nelle finestre
	private String  hidePreferencesButton;
	
	//content type della response di ritorno da datasourceservice: se vuoto usa text/html;charset=UTF-8
	private String  datasourceServiceResponseContentType;
	
	/**
	 * Se TRUE, in tutte le chiamate da GUI a FileOperation viene passato lo stream del file e non l'uri.
	 * Questo permette di lavorare in locale (Windows) con FileOp della 139 (Linux).
	 */
	private Boolean callFileOpWithStream;
	
	private Boolean debugClientEnable = false;
	
	// Se e' valorizzato setta la larghezza del logo menuImg
	private int menuImgWidth;
	
	// Se e' valorizzato setta l'altezza del logo menuImg
	private int menuImgHeight;
	
	private String flagCifratura = "false";
	private String flagAttivaRequestValidator = "false";
	private String flagRimuoviScript = "false";
	private String flagEscapeHtml = "false"; // IN CONFIG.XML VA OMESSA O TENUTA A FALSE POICHE' CAUSA PROBLEMI NEI JSON
	
	private int maxRangeSearcByNumNoAlert;
		
	public String getEnte() {
		return ente;
	}
	
	public void setEnte(String ente) {
		this.ente = ente;
	}

	public void setDefaultPortletWidth(int defaultPortletWidth) {
		this.defaultPortletWidth = defaultPortletWidth;
	}

	public int getDefaultPortletWidth() {
		return defaultPortletWidth;
	}

	public void setDefaultPortletHeight(int defaultPortletHeight) {
		this.defaultPortletHeight = defaultPortletHeight;
	}

	public int getDefaultPortletHeight() {
		return defaultPortletHeight;
	}
	
	public void setMaxValueLength(int maxValueLength) {
		this.maxValueLength = maxValueLength;
	}

	public int getMaxValueLength() {
		return maxValueLength;
	}

	public void setFilterAppliedIcon(String filterAppliedIcon) {
		this.filterAppliedIcon = filterAppliedIcon;
	}

	public String getFilterAppliedIcon() {
		return filterAppliedIcon;
	}

	public void setCheckIcon(String checkIcon) {
		this.checkIcon = checkIcon;
	}

	public String getCheckIcon() {
		return checkIcon;
	}

	public void setFiltrableSelectPageSize(int filtrableSelectPageSize) {
		this.filtrableSelectPageSize = filtrableSelectPageSize;
	}

	public int getFiltrableSelectPageSize() {
		return filtrableSelectPageSize;
	}

	public void setRecordHeightDefault(int recordHeightDefault) {
		this.recordHeightDefault = recordHeightDefault;
	}

	public int getRecordHeightDefault() {
		return recordHeightDefault;
	}

	public void setAnnoPartenza(int annoPartenza) {
		this.annoPartenza = annoPartenza;
	}

	public int getAnnoPartenza() {
		return annoPartenza;
	}

	public void setDisplayValueSeparator(String displayValueSeparator) {
		this.displayValueSeparator = displayValueSeparator;
	}

	public String getDisplayValueSeparator() {
		return displayValueSeparator;
	}

	public void setDisplayValueNull(String displayValueNull) {
		this.displayValueNull = displayValueNull;
	}

	public String getDisplayValueNull() {
		return displayValueNull;
	}

	public void setDelayTimeForDoubleClick(int delayTimeForDoubleClick) {
		this.delayTimeForDoubleClick = delayTimeForDoubleClick;
	}

	public int getDelayTimeForDoubleClick() {
		return delayTimeForDoubleClick;
	}

	public int getColonneEstremiRecordMaxValueLength() {
		return colonneEstremiRecordMaxValueLength;
	}

	public void setColonneEstremiRecordMaxValueLength(
			int colonneEstremiRecordMaxValueLength) {
		this.colonneEstremiRecordMaxValueLength = colonneEstremiRecordMaxValueLength;
	}

	public String getPrefKeyPrefix() {
		return prefKeyPrefix;
	}

	public void setPrefKeyPrefix(String prefKeyPrefix) {
		this.prefKeyPrefix = prefKeyPrefix;
	}

	public int getWaitingMessageDuration() {
		return waitingMessageDuration;
	}

	public void setWaitingMessageDuration(int waitingMessageDuration) {
		this.waitingMessageDuration = waitingMessageDuration;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(String logoImage) {
		this.logoImage = logoImage;
	}

	public String getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public int getNumMaxShortCut() {
		return numMaxShortCut;
	}

	public void setNumMaxShortCut(int numMaxShortCut) {
		this.numMaxShortCut = numMaxShortCut;
	}

	public int getWelcomeMaxLength() {
		return welcomeMaxLength;
	}

	public void setWelcomeMaxLength(int welcomeMaxLength) {
		this.welcomeMaxLength = welcomeMaxLength;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getLogoDominioFolder() {
		return logoDominioFolder;
	}

	public void setLogoDominioFolder(String logoDominioFolder) {
		this.logoDominioFolder = logoDominioFolder;
	}

	public String getMenuImage() {
		return menuImage;
	}

	public void setMenuImage(String menuImage) {
		this.menuImage = menuImage;
	}

	public String getShibbolethLogoutUrlHeaderName() {
		return shibbolethLogoutUrlHeaderName;
	}

	public void setShibbolethLogoutUrlHeaderName(String shibbolethLogoutUrlHeaderName) {
		this.shibbolethLogoutUrlHeaderName = shibbolethLogoutUrlHeaderName;
	}
	
	public String getShibbolethLogoutUrlSuffix() {
		return shibbolethLogoutUrlSuffix;
	}

	public void setShibbolethLogoutUrlSuffix(String shibbolethLogoutUrlSuffix) {
		this.shibbolethLogoutUrlSuffix = shibbolethLogoutUrlSuffix;
	}
	
	public String getShibbolethRedirectUrlAfterSessionExpiredHeaderName() {
		return shibbolethRedirectUrlAfterSessionExpiredHeaderName;
	}
	
	public void setShibbolethRedirectUrlAfterSessionExpiredHeaderName(String shibbolethRedirectUrlAfterSessionExpiredHeaderName) {
		this.shibbolethRedirectUrlAfterSessionExpiredHeaderName = shibbolethRedirectUrlAfterSessionExpiredHeaderName;
	}
	
	public String getIamLogoutUrlHeaderName() {
		return iamLogoutUrlHeaderName;
	}

	public void setIamLogoutUrlHeaderName(String iamLogoutUrlHeaderName) {
		this.iamLogoutUrlHeaderName = iamLogoutUrlHeaderName;
	}

	public String getIamLogoutUrlSuffix() {
		return iamLogoutUrlSuffix;
	}
	
	public void setIamLogoutUrlSuffix(String iamLogoutUrlSuffix) {
		this.iamLogoutUrlSuffix = iamLogoutUrlSuffix;
	}

	public String getIamRedirectUrlAfterSessionExpiredHeaderName() {
		return iamRedirectUrlAfterSessionExpiredHeaderName;
	}
	
	public void setIamRedirectUrlAfterSessionExpiredHeaderName(String iamRedirectUrlAfterSessionExpiredHeaderName) {
		this.iamRedirectUrlAfterSessionExpiredHeaderName = iamRedirectUrlAfterSessionExpiredHeaderName;
	}
	
	public Boolean getReloadAfterSessionExpired() {
		return reloadAfterSessionExpired;
	}

	public void setReloadAfterSessionExpired(Boolean reloadAfterSessionExpired) {
		this.reloadAfterSessionExpired = reloadAfterSessionExpired;
	}

	public String getLogoutRedirectUrl() {
		return logoutRedirectUrl;
	}

	public void setLogoutRedirectUrl(String logoutRedirectUrl) {
		this.logoutRedirectUrl = logoutRedirectUrl;
	}

	public List<String> getEditableExtension() {
		return editableExtension;
	}

	public void setEditableExtension(List<String> editableExtension) {
		this.editableExtension = editableExtension;
	}

	public int getNroMaxRecordsForResizeReorder() {
		return nroMaxRecordsForResizeReorder;
	}

	public void setNroMaxRecordsForResizeReorder(
			int nroMaxRecordsForResizeReorder) {
		this.nroMaxRecordsForResizeReorder = nroMaxRecordsForResizeReorder;
	}

	public String getHideErrorDetails() {
		return hideErrorDetails;
	}

	public void setHideErrorDetails(String hideErrorDetails) {
		this.hideErrorDetails = hideErrorDetails;
	}

	public String getSecurityChecker() {
		return securityChecker;
	}

	public void setSecurityChecker(String securityChecker) {
		this.securityChecker = securityChecker;
	}

	public String getHidePreferencesButton() {
		return hidePreferencesButton;
	}

	public void setHidePreferencesButton(String hidePreferencesButton) {
		this.hidePreferencesButton = hidePreferencesButton;
	}

	public String getDatasourceServiceResponseContentType() {
		return datasourceServiceResponseContentType;
	}

	public void setDatasourceServiceResponseContentType(
			String datasourceServiceResponseContentType) {
		this.datasourceServiceResponseContentType = datasourceServiceResponseContentType;
	}

	public Boolean getDefaultDetailAuto() {
		return defaultDetailAuto;
	}

	public void setDefaultDetailAuto(Boolean defaultDetailAuto) {
		this.defaultDetailAuto = defaultDetailAuto;
	}

	public List<String> getEmlExtension() {
		return emlExtension;
	}

	public void setEmlExtension(List<String> emlExtension) {
		this.emlExtension = emlExtension;
	}

	public List<String> getEmlMimetype() {
		return emlMimetype;
	}

	public void setEmlMimetype(List<String> emlMimetype) {
		this.emlMimetype = emlMimetype;
	}

	public String getShowResetPasswordLogin() {
		return showResetPasswordLogin;
	}

	public void setShowResetPasswordLogin(String showResetPasswordLogin) {
		this.showResetPasswordLogin = showResetPasswordLogin;
	}

	public String getDisplayApplicationName() {
		return displayApplicationName;
	}

	public void setDisplayApplicationName(String displayApplicationName) {
		this.displayApplicationName = displayApplicationName;
	}

	public int getMaxNumIconeDesktop() {
		return maxNumIconeDesktop;
	}

	public void setMaxNumIconeDesktop(int maxNumIconeDesktop) {
		this.maxNumIconeDesktop = maxNumIconeDesktop;
	}

	public int getScannerDefaultResolution() {
		return scannerDefaultResolution;
	}

	public void setScannerDefaultResolution(int scannerDefaultResolution) {
		this.scannerDefaultResolution = scannerDefaultResolution;
	}

	public Boolean getAttivaMultilingua() {
		return attivaMultilingua;
	}

	public void setAttivaMultilingua(Boolean attivaMultilingua) {
		this.attivaMultilingua = attivaMultilingua;
	}

	public Boolean getAttivaEscapeHtmlDSCall() {
		return attivaEscapeHtmlDSCall;
	}

	public void setAttivaEscapeHtmlDSCall(Boolean attivaEscapeHtmlDSCall) {
		this.attivaEscapeHtmlDSCall = attivaEscapeHtmlDSCall;
	}

	public Boolean getShowAlertMaxRecord() {
		return showAlertMaxRecord;
	}

	public void setShowAlertMaxRecord(Boolean showAlertMaxRecord) {
		this.showAlertMaxRecord = showAlertMaxRecord;
	}

	public Boolean getCallFileOpWithStream() {
		return callFileOpWithStream;
	}

	public void setCallFileOpWithStream(Boolean callFileOpWithStream) {
		this.callFileOpWithStream = callFileOpWithStream;
	}
	
	public Boolean getDebugClientEnable() {
		return debugClientEnable;
	}

	public void setDebugClientEnable(Boolean debugClientEnable) {
		this.debugClientEnable = debugClientEnable;
	}

	public int getMenuImgWidth() {
		return menuImgWidth;
	}

	public void setMenuImgWidth(int menuImgWidth) {
		this.menuImgWidth = menuImgWidth;
	}

	public int getMenuImgHeight() {
		return menuImgHeight;
	}

	public void setMenuImgHeight(int menuImgHeight) {
		this.menuImgHeight = menuImgHeight;
	}

	public String getFlagCifratura() {
		return flagCifratura;
	}

	public void setFlagCifratura(String flagCifratura) {
		this.flagCifratura = flagCifratura;
	}
		
	public String getFlagAttivaRequestValidator() {
		return flagAttivaRequestValidator;
	}

	public void setFlagAttivaRequestValidator(String flagAttivaRequestValidator) {
		this.flagAttivaRequestValidator = flagAttivaRequestValidator;
	}

	public String getFlagRimuoviScript() {
		return flagRimuoviScript;
	}

	public void setFlagRimuoviScript(String flagRimuoviScript) {
		this.flagRimuoviScript = flagRimuoviScript;
	}
	
	public String getFlagEscapeHtml() {
		return flagEscapeHtml;
	}

	public void setFlagEscapeHtml(String flagEscapeHtml) {
		this.flagEscapeHtml = flagEscapeHtml;
	}

	public String getDefaultLocale() {
		return defaultLocale;
	}

	public void setDefaultLocale(String defaultLocale) {
		this.defaultLocale = defaultLocale;
	}
	
	public int getMaxNroDocFirmaMassivaClient() {
		return maxNroDocFirmaMassivaClient;
	}

	public void setMaxNroDocFirmaMassivaClient(int maxNroDocFirmaMassivaClient) {
		this.maxNroDocFirmaMassivaClient = maxNroDocFirmaMassivaClient;
	}
	
	public int getMaxNroDocFirmaMassivaRemotaNonAuto() {
		return maxNroDocFirmaMassivaRemotaNonAuto;
	}

	public void setMaxNroDocFirmaMassivaRemotaNonAuto(int maxNroDocFirmaMassivaRemotaNonAuto) {
		this.maxNroDocFirmaMassivaRemotaNonAuto = maxNroDocFirmaMassivaRemotaNonAuto;
	}
	
	public String getMinAnno() {
		return minAnno;
	}

	public void setMinAnno(String minAnno) {
		this.minAnno = minAnno;
	}

	public String getMaxAnno() {
		return maxAnno;
	}

	public void setMaxAnno(String maxAnno) {
		this.maxAnno = maxAnno;
	}
	
	public int getMaxRangeSearcByNumNoAlert() {
		return maxRangeSearcByNumNoAlert;
	}

	public void setMaxRangeSearcByNumNoAlert(int maxRangeSearcByNumNoAlert) {
		this.maxRangeSearcByNumNoAlert = maxRangeSearcByNumNoAlert;
	}

}