/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.rpc.HandleTransportErrorCallback;
import com.smartgwt.client.rpc.LoginRequiredCallback;
import com.smartgwt.client.rpc.RPCCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;

import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.utility.ui.module.core.client.EscapeHtmlClient;
import it.eng.utility.ui.module.core.client.SJCLClient;
import it.eng.utility.ui.module.core.client.ScriptCleanerClient;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.datasource.OneCallGWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.util.ClientFactory;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Index;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.error.ErrorWindow;
import it.eng.utility.ui.module.layout.client.message.MessageBox;
import it.eng.utility.ui.module.layout.shared.bean.FilterPrivilegiContainer;
import it.eng.utility.ui.module.layout.shared.bean.FilterPrivilegiImpl;

public class AurigaIndex extends Index {
	
	protected Dictionary dictionary;
	
	protected String schemaPortlet;
	protected String codApplication;
	protected String codApplicationIst;
	protected String applicationKey;
	protected String userid;
	protected String idUd;
	protected String codCategoriaReg;
	protected String siglaReg;
	protected String annoReg;
	protected String numReg;
	
	protected boolean loginError;	

	// private static Logger logger = java.util.logging.Logger.getLogger("AurigaIndex");
	protected SchemaSelectionWindow lSchemaSelectionWindow;
	public String schemaSelezionato;
	public String dominioSelezionato;
	
	protected String usernameSSO;
	protected String autenticazioneIam;
	protected String showResetPasswordLogin = "N";

	protected AurigaLoginWindow loginwindow;
	protected static AurigaIndex index;
	protected ClientFactory clientFactory;

	protected String logLoginDaPortlet = "";
	
	@Override
	public void onModuleLoad() {
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			SC.setScreenReaderMode(true);		
		}
		
		dictionary = Dictionary.getDictionary("params");
		
		setLoginDaPortletParams(dictionary);
		setDettaglioUdParams(dictionary);
		
		try {
			usernameSSO = (dictionary.keySet().contains("usernameSSO") && dictionary.get("usernameSSO") != null) ? dictionary.get("usernameSSO") : null;
		} catch (Exception e) {
			usernameSSO = null;
		}
		
		try {
			autenticazioneIam = (dictionary.keySet().contains("autenticazioneIam") && dictionary.get("autenticazioneIam") != null) ? dictionary.get("autenticazioneIam") : "false";
		} catch (Exception e) {
			autenticazioneIam = "false";
		}
		
		try {
			showResetPasswordLogin = dictionary.get("showResetPasswordLogin");
		} catch (Exception e) {
			showResetPasswordLogin = "false";
		}

		clientFactory = GWT.create(ClientFactory.class);

		// Configuro il client per la cifratura dei Json
		final SJCLClient sjclClient = clientFactory.getSJCLClient();
		sjclClient.setEnabled(getFlagCifratura(dictionary));
		GWTRestDataSource.setSjclClient(sjclClient);

		// Configuro il validatore della request per individuare eventuali script
		GWTRestDataSource.setFlagAttivaRequestValidator(getFlagAttivaRequestValidator(dictionary));

		// Configuro il client per la rimozione degli script nei json
		final ScriptCleanerClient scriptCleanerClient = clientFactory.getScriptCleanerClient();
		scriptCleanerClient.setEnabled(getFlagRimuoviScript(dictionary));
		GWTRestDataSource.setScriptCleanerClient(scriptCleanerClient);

		// Configuro il client per l'escape dei caratteri html
		final EscapeHtmlClient escapeHtmlClient = clientFactory.getEcapeHtmlClient();
		escapeHtmlClient.setEnabled(getFlagEscapeHtml(dictionary));
		GWTRestDataSource.setEscapeHtmlClient(escapeHtmlClient);

		initMessageListener(getOrigin());
		
		RPCManager.setAllowCrossDomainCalls(true);
		RPCManager.setDefaultTimeout(0);
		initAutomaticDownload(this, "automaticDownload");
		lSchemaSelectionWindow = new SchemaSelectionWindow(this);
		
		String schema = null;
		try {
			schema = (dictionary.keySet().contains("schema") && dictionary.get("schema") != null) ? dictionary.get("schema") : null;
		} catch (Exception e) {
			schema = null;
		}
		final Record lRecordSchema = new Record(JSON.decode(schema));
		
		if (autenticazioneIam != null && "true".equalsIgnoreCase(autenticazioneIam)){
			Record lRecord = new Record();
			if (lRecordSchema != null && lRecordSchema.getAttributeAsRecord("defaultSchema") != null) {
				lRecord.setAttribute("schema", lRecordSchema.getAttributeAsRecord("defaultSchema").getAttribute("name"));
			}
			
			new GWTRestService<Record, Record>("IAMLoadComboDominioDataSource").call(lRecord, new ServiceCallback<Record>() {
	
				@Override
				public void execute(Record object) {
					RecordList lRecordList = object.getAttributeAsRecordList("domini");
					if (lRecordList != null && lRecordList.getLength() > 1) {
						LinkedHashMap<String, String> lHashMap = new LinkedHashMap<String, String>();
						for (int i = 0; i < lRecordList.getLength(); i++) {
							lHashMap.put(lRecordList.get(i).getAttribute("tipoIdSpAoo"), lRecordList.get(i).getAttribute("denominazione"));
						}
						final DominioWindowSSO lDominioWindow = new DominioWindowSSO(false) {
							@Override
							public void dominioSelezionato(String dominio) {
								markForDestroy();
								afterSelezionaDominio(dictionary, lRecordSchema, dominio);
							}
						};
						lDominioWindow.setDominio(lHashMap);
						lDominioWindow.show();
					} else if (lRecordList != null && lRecordList.getLength() == 1) {
						String dominio = lRecordList.get(0).getAttribute("tipoIdSpAoo");
						afterSelezionaDominio(dictionary, lRecordSchema, dominio);
					} else {
						String iamUsername = object.getAttribute("iamUsername");
						String iamMatricola = object.getAttribute("iamMatricola");
						SC.warn("Non ho trovato nessun dominio associato a username \"" + iamUsername + "\" o matricola \"" + iamMatricola + "\"");
					}
				}
			});
		} else {
			// Il metodo continuaOnModuleLoad viene sovrascritto nelle varie classi derivate
			continuaOnModuleLoad(dictionary, lRecordSchema);
		}
	}
	
	protected boolean isLoginDaPortlet() {
		return codApplication != null && !"".equals(codApplication);
	}
	
	protected void setLoginDaPortletParams(Dictionary dictionary) {
		try {
			schemaPortlet = (dictionary.keySet().contains("schemaPortlet") && dictionary.get("schemaPortlet") != null) ? dictionary.get("schemaPortlet") : null;
		} catch (Exception e) {
			schemaPortlet = null;
		}	
		try {
			codApplication = (dictionary.keySet().contains("codApplication") && dictionary.get("codApplication") != null) ? dictionary.get("codApplication") : null;
		} catch (Exception e) {
			codApplication = null;
		}	
		try {
			codApplicationIst = (dictionary.keySet().contains("codApplicationIst") && dictionary.get("codApplicationIst") != null) ? dictionary.get("codApplicationIst") : null;
		} catch (Exception e) {
			codApplicationIst = null;
		}	
		try {
			applicationKey = (dictionary.keySet().contains("applicationKey") && dictionary.get("applicationKey") != null) ? dictionary.get("applicationKey") : null;
		} catch (Exception e) {
			applicationKey = null;
		}	
		try {
			userid = (dictionary.keySet().contains("userid") && dictionary.get("userid") != null) ? dictionary.get("userid") : null;
		} catch (Exception e) {
			userid = null;
		}
	}
	
	protected void setDettaglioUdParams(Dictionary dictionary) {
		try {
			idUd = (dictionary.keySet().contains("idUd") && dictionary.get("idUd") != null) ? dictionary.get("idUd") : null;
		} catch (Exception e) {
			idUd = null;
		}		
		try {
			codCategoriaReg = (dictionary.keySet().contains("codCategoriaReg") && dictionary.get("codCategoriaReg") != null) ? dictionary.get("codCategoriaReg") : null;
		} catch (Exception e) {
			codCategoriaReg = null;
		}
		try {
			siglaReg = (dictionary.keySet().contains("siglaReg") && dictionary.get("siglaReg") != null) ? dictionary.get("siglaReg") : null;
		} catch (Exception e) {
			siglaReg = null;
		}
		try {
			annoReg = (dictionary.keySet().contains("annoReg") && dictionary.get("annoReg") != null) ? dictionary.get("annoReg") : null;
		} catch (Exception e) {
			annoReg = null;
		}
		try {
			numReg = (dictionary.keySet().contains("numReg") && dictionary.get("numReg") != null) ? dictionary.get("numReg") : null;
		} catch (Exception e) {
			numReg = null;
		}
	}
	
	protected String getUsernameForLoginDaPortlet() {
		return userid + "#COD_APPL#" + codApplication + "#COD_APPL_IST#" + codApplicationIst;
	}
	
	protected String getPasswordForLoginDaPortlet() {	
		String schemaPortlet = null;
		try {
			schemaPortlet = (dictionary.keySet().contains("schemaPortlet") && dictionary.get("schemaPortlet") != null) ? dictionary.get("schemaPortlet") : null;
		} catch (Exception e) {
			schemaPortlet = null;
		}
		if(schemaPortlet != null && !"".equals(schemaPortlet)) {
			return applicationKey + "#SCHEMA#" + schemaPortlet;
		} else {
			String schema = null;
			try {
				schema = (dictionary.keySet().contains("schema") && dictionary.get("schema") != null) ? dictionary.get("schema") : null;
			} catch (Exception e) {
				schema = null;
			}
			Record lRecordSchema = new Record(JSON.decode(schema));
			return applicationKey + "#SCHEMA#" + lRecordSchema.getAttributeAsRecord("defaultSchema").getAttribute("name");
		}
	}
	
	protected void resetParamsOnLoginError() {
		schemaPortlet = null;
		codApplication = null;
		codApplicationIst = null;
		applicationKey = null;
		userid = null;
	}
	
	private void loginDaPortlet() {
		Layout.showWaitPopup("Autenticazione in corso...");		
		RPCRequest req = new RPCRequest();
		req.setContainsCredentials(true);
		req.setActionURL("j_security_check");
		req.setUseSimpleHttp(true);
		req.setShowPrompt(false);
		Map params = new HashMap();
		// adjust parameter names to match your authentication system
		params.put("j_username", getUsernameForLoginDaPortlet());
		params.put("j_password", getPasswordForLoginDaPortlet());
		req.setParams(params);				
		RPCManager.sendRequest(req, new RPCCallback(){
			public void execute(RPCResponse response, Object rawData, RPCRequest request) {
				try {
					Layout.hideWaitPopup();				
				} catch(Exception e) {
				}
				logLoginDaPortlet += "login status " + response.getStatus() + "<br/>";
				if (response.getStatus() == RPCResponse.STATUS_SUCCESS) {
					createSessionLoginInfo(new ServiceCallback<Record>() {	
						
						@Override
						public void execute(Record object) {
							// get rid of login window											
							RPCManager.resendTransaction();
							if(object != null && object.getAttribute("idApplicazione") != null) {
								logLoginDaPortlet += "buildPortalLayout()<br/>";
								layout = buildPortalLayout();
							}
//							try {	                	 
//								if (getLayout().getConfigured()) {
//									logLoginDaPortlet += "configured<br/>";							
//									getLayout().aggiornaUtente();	
//									logLoginDaPortlet += "aggiornaUtente()<br/>";																
//								} else {
//									logLoginDaPortlet += "not configured<br/>";
//								}
//							} catch(Exception e) {
//								logLoginDaPortlet += "errore " + e.getMessage() + "<br/>";
//							};	
						}
					});													
				} else {	
					final MessageBox messagebox = new MessageBox(false);
					// Setto la messagebox
					GWTRestDataSource.settingMessageBox(messagebox);											
					loginError = true;
					resetParamsOnLoginError();
					List<MessageBean> messages = new ArrayList<MessageBean>();
					messages.add(new MessageBean("Login non valido", "", MessageType.ERROR));
					messagebox.addMessages(messages);
//					new OneCallGWTRestService<Record, Record>("LogoutDataSource").call(new Record(), new ServiceCallback<Record>() {		
//						
//						@Override
//						public void execute(Record object) {
//							
//						}
//					});
				}				
			}
		});				
		
	}
	
	private void afterSelezionaDominio(final Dictionary dictionary, final Record lRecordSchema, String dominio) {
		dominioSelezionato = dominio;
		final GWTRestDataSource lServiceRestUserUtil = new GWTRestDataSource("ServiceRestUserUtil");
		final Record record = new Record();
		// Salvo in sessione DOMINIO_SELEZIONATO_SSO, che verrà letto dal filtro di login per fare la login nel dominio selezionato
		record.setAttribute("key", "DOMINIO_SELEZIONATO_SSO");
		record.setAttribute("value", dominio);
		
		lServiceRestUserUtil.executecustom("setSessionAttributeAsString", record, new DSCallback() {

			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					// Il metodo continuaOnModuleLoad viene sovrascritto nelle varie classi derivate
					continuaOnModuleLoad(dictionary, lRecordSchema);
				} 
			}
		});
	}

	public static String getOrigin() {
		String origin = null;
		String hostPageBaseUrl = GWT.getHostPageBaseURL();
		if (hostPageBaseUrl.endsWith("/")) {
			hostPageBaseUrl = hostPageBaseUrl.substring(0, hostPageBaseUrl.length() - 1);
		}
		origin = hostPageBaseUrl.substring(0, hostPageBaseUrl.lastIndexOf("/"));
		return origin;
	}

	public static native void initMessageListener(String origin) /*-{
		$wnd.addEventListener("message", function(event) {
			if (!event.origin.indexOf(origin)) {
				var pass_data = JSON.parse(event.data);
				if ($wnd[pass_data.functionName]) {
					$wnd[pass_data.functionName].apply(undefined,
							pass_data.params);
				}
			} else {
				return;
			}
		});
	}-*/;

	private boolean getFlagCifratura(Dictionary dictionary) {
		boolean result = false;
		try {
			if (dictionary == null) {
				dictionary = Dictionary.getDictionary("params");
			}
			result = Boolean.parseBoolean(dictionary.get("flagCifratura"));
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	public boolean getFlagCifratura() {
		return getFlagCifratura(null);
	}
	
	private boolean getFlagAttivaRequestValidator(Dictionary dictionary) {
		boolean result = false;
		try {
			if (dictionary == null) {
				dictionary = Dictionary.getDictionary("params");
			}
			result = Boolean.parseBoolean(dictionary.get("flagAttivaRequestValidator"));
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	private boolean getFlagRimuoviScript(Dictionary dictionary) {
		boolean result = false;
		try {
			if (dictionary == null) {
				dictionary = Dictionary.getDictionary("params");
			}
			result = Boolean.parseBoolean(dictionary.get("flagRimuoviScript"));
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	public boolean getFlagEscapeHtml() {
		return getFlagEscapeHtml(null);
	}

	private boolean getFlagEscapeHtml(Dictionary dictionary) {
		boolean result = false;
		try {
			if (dictionary == null) {
				dictionary = Dictionary.getDictionary("params");
			}
			result = Boolean.parseBoolean(dictionary.get("flagEscapeHtml"));
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	protected void continuaOnModuleLoad(Dictionary dictionary, Record lRecordSchema) {
		final boolean skipLogin = Boolean.valueOf((dictionary.get("skipLogin") != null ? dictionary.get("skipLogin") : "false"));
		final String schemaSelezionato = dictionary.get("schemaSelezionato") != null ? dictionary.get("schemaSelezionato") : null;
		final boolean flagSchemaSelezionato = schemaSelezionato != null && schemaSelezionato.length() > 0;
		if (skipLogin && flagSchemaSelezionato) {
			proceedAsUsual(schemaSelezionato);
		} else if (lRecordSchema.getAttributeAsBoolean("showSelection")) {
			proceedAsUsual(null);
		} else {
			String name = lRecordSchema.getAttributeAsRecordList("schemi").get(0).getAttribute("name");
			proceedAsUsual(name);
		}
		RPCManager.setHandleTransportErrorCallback(new HandleTransportErrorCallback() {

			@Override
			public void handleTransportError(final int transactionNum, int status, int httpResponseCode, String httpResponseText) {
				try {
					Layout.hideWaitPopup();
				} catch (Exception e) {
				}
				if(Layout.isDebugClientEnable()) { GWT.log("Transport Error: " + httpResponseText + " [" + httpResponseCode + "]"); };
				RPCManager.cancelDefaultErrorHandling();
				if (httpResponseCode == 403) {
					new GWTRestService<Record, Record>("LogoutDataSource").call(new Record(), new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {

						}
					});
				} else {
					if (Layout.utenteLoggato != null) {
						RPCManager.clearTransaction(transactionNum);
					}
					if (httpResponseText != null && httpResponseText.contains("AURIGA_CAS_LOGIN_ERROR")) {
						int beginIndex = httpResponseText.indexOf("AURIGA_CAS_LOGIN_ERROR|*|") + 25;
						int endIndex = httpResponseText.indexOf("|*|", beginIndex);
						final String errorMessage = httpResponseText.substring(beginIndex, endIndex);
						Record lRecordError = new Record();
						lRecordError.setAttribute("errorMessage", errorMessage);
						ErrorWindow lErrorWindow = new ErrorWindow(lRecordError);
						lErrorWindow.show();
					}
				}
			}
		});
		if (skipLogin == true) {
			setLoginNotRequired(lRecordSchema);
		} else {
			setLoginRequired(lRecordSchema);
		}
	}

	private void registraUtente(String dominioSelezionato, String schemaSelezionato) {
		this.dominioSelezionato = dominioSelezionato;
		createSessionLoginInfo(dominioSelezionato, schemaSelezionato, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				if (getLayout().getConfigured())
					getLayout().aggiornaUtente();
			}
		});
	}

	protected void setLoginNotRequired(final Record lRecordSchema) {
		final Dictionary dictionary = Dictionary.getDictionary("params");
		final String dominioSelezionato = dictionary.get("dominioSelezionato") != null ? dictionary.get("dominioSelezionato") : null;
		final String schemaSelezionato = dictionary.get("schemaSelezionato") != null ? dictionary.get("schemaSelezionato") : null;
		registraUtente(dominioSelezionato, schemaSelezionato);
		RPCManager.setLoginRequiredCallback(new LoginRequiredCallback() {

			@Override
			public void loginRequired(int transactionNum, RPCRequest request, RPCResponse response) {
				try {
					Layout.hideWaitPopup();
				} catch (Exception e) {
				}
				if (Layout.utenteLoggato != null) {
					Record requestRecord = new Record(request.getJsObj());
					if(requestRecord.getAttribute("operationType") != null && ((String)requestRecord.getAttribute("operationType")).equalsIgnoreCase("fetch")) {
						RPCManager.suspendTransaction();
					} else {
						RPCManager.clearTransaction(transactionNum);
					}
				}
				showLogin(true);
			}
		});
	}

	protected void setLoginRequired(final Record lRecordSchema) {
		RPCManager.setLoginRequiredCallback(new LoginRequiredCallback() {

			@Override
			public void loginRequired(int transactionNum, RPCRequest request, RPCResponse response) {
				try {
					Layout.hideWaitPopup();
				} catch (Exception e) {
				}
				if (Layout.utenteLoggato != null) {
					Record requestRecord = new Record(request.getJsObj());
					if(requestRecord.getAttribute("operationType") != null && ((String)requestRecord.getAttribute("operationType")).equalsIgnoreCase("fetch")) {
						RPCManager.suspendTransaction();
					} else {
						RPCManager.clearTransaction(transactionNum);
					}
				}
				if (Layout.getShibbolethLogoutUrl() != null && !"".equals(Layout.getShibbolethLogoutUrl())) {
					// Sono loggato con shibboleth, faccio la redirect al portale per ricaricare auriga
					if (Layout.getShibbolethRedirectUrlAfterSessionExpired() != null && !"".equals(Layout.getShibbolethRedirectUrlAfterSessionExpired())){
						Window.Location.replace(Layout.getShibbolethRedirectUrlAfterSessionExpired());
					} else {
						Window.Location.replace(GWT.getHostPageBaseURL());
					}
				} else if ("true".equalsIgnoreCase(autenticazioneIam)){
					// Sono loggato con iam
					// Non ho una url di redirect, pulisco la sessione e faccio il reload
					Window.Location.reload();
				} else if (lRecordSchema.getAttributeAsBoolean("showSelection")) {
					showSelection(lRecordSchema.getAttributeAsRecordList("schemi"), lRecordSchema.getAttributeAsMap("defaultSchema"));
				} else if (  AurigaLayout.getGenericConfig() != null && AurigaLayout.getGenericConfig().getReloadAfterSessionExpired() != null && AurigaLayout.getGenericConfig().getReloadAfterSessionExpired()) {
					Window.Location.reload();
				} else if(isLoginDaPortlet()) {					
					loginDaPortlet();
				} else {
					// Sono sicuro che è unico
					showLogin();
				}
			}
		});
	}
	
	@Override
	public Layout buildPortalLayout() {
		if(isLoginDaPortlet()) {
			Layout.showWaitPopup("Caricamento in corso...");
			AurigaLayout lAurigaLayout = new AurigaLayout() {
				
					@Override
					public boolean isLoginDaPortlet() {
						return true;
					}
				
					@Override
					public void afterAggiornaUtente() {
						clearUserPreferences();
						logLoginDaPortlet += "utenteLoggato " + (Layout.utenteLoggato != null ? Layout.utenteLoggato.getUserid() : "NULL")  + "<br/>";
						logLoginDaPortlet += "apriDettaglioUd()<br/>";
						if(Layout.isDebugClientEnable()) { GWT.log("LOGIN DA PORTLET<br/>" + logLoginDaPortlet); }
						if(idUd != null && !"".equals(idUd)) {
							apriDettaglioUd(idUd);
						} else if(numReg != null && !"".equals(numReg)) {
							Record lRecord = new Record();
							lRecord.setAttribute("codCategoriaProtocollo", codCategoriaReg);
							lRecord.setAttribute("siglaProtocollo", siglaReg);
							lRecord.setAttribute("nroProtocollo", numReg);
							lRecord.setAttribute("annoProtocollo", annoReg);
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
							lGwtRestDataSource.executecustom("recuperaIdUd", lRecord, new DSCallback() {
				
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
				
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record record = response.getData()[0];
										String idUd = record.getAttributeAsString("idUd");
										if (idUd != null && !"".equals(idUd)) {
											apriDettaglioUd(idUd);				
										}					
									} else {
										Layout.addMessage(new MessageBean("Documento inesistente", "", MessageType.ERROR));
									}
								}
							});
						}
					}
					
					@Override
					public MessageBox buildMessageBox() {
						return new MessageBox(false);
					}
			};
			lAurigaLayout.setVisibility(Visibility.HIDDEN);
			return lAurigaLayout;
		} else {
			return new AurigaLayout();
		}
	}

	public Canvas apriDettaglioUd(String idUd) {
		String title = "Dettaglio unità documentaria";
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", idUd);
		DettaglioRegProtAssociatoWindow lDettaglioRegProtAssociatoWindow = new DettaglioRegProtAssociatoWindow(lRecord, null, title) {
			
			@Override
			public void show() {
				super.show();
				Layout.hideWaitPopup();
			};
			
			@Override
			public boolean isFromPortlet() {
				return true;
			}
		};
		lDettaglioRegProtAssociatoWindow.setIsModal(true);
		lDettaglioRegProtAssociatoWindow.setShowModalMask(true);
		lDettaglioRegProtAssociatoWindow.setModalMaskOpacity(50);         
		lDettaglioRegProtAssociatoWindow.setShowMinimizeButton(false);
		lDettaglioRegProtAssociatoWindow.setShowMaximizeButton(false);
		lDettaglioRegProtAssociatoWindow.setShowCloseButton(false);				 
		lDettaglioRegProtAssociatoWindow.setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL); 
		lDettaglioRegProtAssociatoWindow.setHeight100();
		lDettaglioRegProtAssociatoWindow.setWidth100();
		return lDettaglioRegProtAssociatoWindow;
	}

	@Override
	protected void simpleLoginRequired() {
		showLogin();
	}

	/**
	 * Mostra la finestra di login. Con questa chiamata si assume che la login non sia dovuta alla scadenza della sessione, ma ad esempio per un nuovo avvio
	 * dell'applicazione
	 */
	protected void showLogin() {
		showLogin(false);
	}

	/**
	 * Mostra la finestra di login, parametrizzando se la nuova login è dovuta alla scadenza della sessione o ad altri motivi
	 * 
	 * @param ignoraControlloLingua
	 *            True se la login è richiesta per la scedenza della sessione
	 */
	protected void showLogin(boolean ignoraControlloLingua) {
		if (loginwindow != null && (!loginwindow.isDrawn() || !loginwindow.isVisible())) {
			loginwindow.setIgnoraControlloLingua(ignoraControlloLingua);
			loginwindow.getForm().clearValues();
			loginwindow.redraw();
			loginwindow.show();
			loginwindow.getForm().focusInItem("j_username");
			if(loginError) {
				Layout.addMessage(new MessageBean(I18NUtil.getMessages().loginError_message(), "", MessageType.ERROR));
				loginError = false;						
			}
		}
	}

	protected void showSelection(RecordList recordList, Map map) {
		if ((!lSchemaSelectionWindow.isDrawn()) || (!lSchemaSelectionWindow.isVisible())) {
			LinkedHashMap<String, String> lHashMap = new LinkedHashMap<String, String>();
			for (int i = 0; i < recordList.getLength(); i++) {
				lHashMap.put(recordList.get(i).getAttribute("name"), recordList.get(i).getAttribute("alias"));
			}
			lSchemaSelectionWindow.setSchema(lHashMap, map.get("name"));
			lSchemaSelectionWindow.show();
		}
	}

	protected void proceedAsUsual(String schema) {
		schemaSelezionato = schema;
		if (!isLoginDaPortlet() && schemaSelezionato != null && !"".equals(schemaSelezionato)) {
			manageOnModuleLoad(true);
		} else {
			manageOnModuleLoad(false);
		}

		if (usernameSSO == null) {
			loginwindow = new AurigaLoginWindow(this);
		}
		adaptFilterBuilderDefaults();
		if (!isLoginDaPortlet()) {
			layout.filterPrivilegi = new FilterPrivilegiImpl();
			new GWTRestService<Record, Record>("ServiceRestPrivilegiFilterUtil").call(new Record(), new ServiceCallback<Record>() {
	
				@Override
				public void execute(Record object) {
					FilterPrivilegiContainer lFilterPrivilegiContainer = new FilterPrivilegiContainer();
					lFilterPrivilegiContainer.setConfigMap(object.getAttributeAsMap("configMap"));
					layout.filterPrivilegi.setContainer(lFilterPrivilegiContainer);
				}
			});
		}

	}

	public SJCLClient getSJCLClient() {
		return clientFactory.getSJCLClient();
	}

	public static AurigaIndex getIndex() {
		return index;
	}

	@Override
	public void configure() {

		UserInterfaceFactory.configure(new AurigaUserInterfaceConfig());
		this.afterConfigure();
	}

	protected native void adaptFilterBuilderDefaults() /*-{
		//set widths for field picker, operator picker and value item
		$wnd.isc.FilterClause.addProperties({
			fieldPickerWidth : 180,
			operatorPickerWidth : "*",
			valueItemWidth : 220,
			valueItemTextHint : ""
		});
	}-*/;

	@Override
	protected void createSessionLoginInfo(ServiceCallback<Record> callback) {
		Record lRecord = new Record();
		lRecord.setAttribute("schema", schemaSelezionato);
		lRecord.setAttribute("dominio", dominioSelezionato);
		new GWTRestService("AurigaLoginDataSource").call(lRecord, callback);
	}

	protected void createSessionLoginInfo(String userid, ServiceCallback<Record> callback) {
		Record lRecord = new Record();
		lRecord.setAttribute("userid", userid);
		lRecord.setAttribute("schema", schemaSelezionato);
		lRecord.setAttribute("dominio", dominioSelezionato);
		new GWTRestService("AurigaLoginDataSource").call(lRecord, callback);
	}

	protected void createSessionLoginInfo(String dominio, String schema, ServiceCallback<Record> callback) {
		Record lRecord = new Record();
		lRecord.setAttribute("userid", "");
		lRecord.setAttribute("schema", schema);
		lRecord.setAttribute("dominio", dominio);
		new GWTRestService("AurigaLoginDataSource").call(lRecord, callback);
	}

	protected void createSessionLoginInfo(String userid, String dominio, String schema, ServiceCallback<Record> callback) {
		Record lRecord = new Record();
		lRecord.setAttribute("userid", userid);
		lRecord.setAttribute("schema", schema);
		lRecord.setAttribute("dominio", dominio);
		new GWTRestService("AurigaLoginDataSource").call(lRecord, callback);
	}

	public void downloadFunction(String value) {
		String[] split = value.split("#");
		String display = split[0];
		String uri = split[1];
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", "false");
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	private native void initAutomaticDownload(AurigaIndex pAurigaIndex, String functionName) /*-{
		$wnd[functionName] = function(value) {
			pAurigaIndex.@it.eng.auriga.ui.module.layout.client.AurigaIndex::downloadFunction(Ljava/lang/String;)(value);
		};
	}-*/;
	
	private void afterConfigure () {
		UserInterfaceFactory.initIsAttivaAccessibilita();
	}

}
