/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.rpc.RPCCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormItemType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;

import it.eng.auriga.ui.module.layout.client.passwordScaduta.CambioPasswordWindow;
import it.eng.utility.ui.module.core.client.BrowserUtility;
import it.eng.utility.ui.module.core.client.SJCLClient;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.error.ErrorWindow;

public class AurigaLoginForm extends DynamicForm {

	private AurigaLoginWindow loginWindow;
	private AurigaLoginForm form;
	private boolean ignoraControlloLingua;
	private SJCLClient sjclClient;

	public AurigaLoginForm(AurigaLoginWindow pLoginWindow) {
		form = this;
		loginWindow = pLoginWindow;
		sjclClient = pLoginWindow.getIndex().getSJCLClient();
		setKeepInParentRect(true);
		setWidth100();
		setHeight100();
		setNumCols(2);
		setColWidths(80, "*");
		setCellPadding(5);
		
		// setProperty("rowSpan", 10);
		TextItem lUsernameFormItem = new TextItem();
		lUsernameFormItem.setName("j_username");
		// lUsernameFormItem.setCharacterCasing(CharacterCasing.UPPER);
		lUsernameFormItem.setTitle(I18NUtil.getMessages().usernameItem_title());
		lUsernameFormItem.setWidth(220);

		/**
		 * Queste due istruzioni sono state aggiunte in quanto con le ultime versioni di firefox (> 60) quando si premeva il tasto "Tab" dall username, non
		 * settava il focus sulla password ma sull item ad esso successivo in quanto effettuava un doppio tab.
		 * 
		 * Qualora FireFox risolve il bug eliminare queste due righe
		 */
		if (BrowserUtility.detectIfFireFox60Browser()) {
			lUsernameFormItem.setTabIndex(-1);
			lUsernameFormItem.setCanFocus(false);
		}

		lUsernameFormItem.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					form.focusInItem("j_password");
				}
			}
		});

		FormItem lPasswordFormItem = new FormItem();
		lPasswordFormItem.setName("j_password");
		lPasswordFormItem.setType(FormItemType.PASSWORD_ITEM.getValue());
		lPasswordFormItem.setTitle(I18NUtil.getMessages().passwordItem_title());
		lPasswordFormItem.setWidth(220);
		lPasswordFormItem.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					provideLoginAction(ignoraControlloLingua);
				}
			}
		});

		ButtonItem lButtonItem = new ButtonItem();
		lButtonItem.setName("login");
		lButtonItem.setTitle(I18NUtil.getMessages().loginButton_title());
		lButtonItem.setColSpan(2);
		lButtonItem.setWidth(100);
		lButtonItem.setTop(20);
		lButtonItem.setAlign(Alignment.CENTER);

		/**
		 * Queste due istruzioni sono state aggiunte in quanto con le ultime versioni di firefox (> 60) quando si premeva il tasto "Tab" dall username, non
		 * settava il focus sulla password ma sull item ad esso successivo in quanto effettuava un doppio tab.
		 * 
		 * Qualora FireFox risolve il bug eliminare queste due righe
		 */
		if (BrowserUtility.detectIfFireFox60Browser()) {
			lButtonItem.setCanFocus(false);
			lButtonItem.setTabIndex(-1);
		}

		lButtonItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				provideLoginAction(ignoraControlloLingua);
			}
		});

		// Link recupera password
		StaticTextItem linkResetPasswordItem = new StaticTextItem("linkResetPassword");
		linkResetPasswordItem.setShowTitle(false);
		linkResetPasswordItem.setWidth(200);
		linkResetPasswordItem.setColSpan(2);
		linkResetPasswordItem.setAlign(Alignment.RIGHT);
		linkResetPasswordItem
				.setDefaultValue("<div style=\"cursor:pointer\"><br><u><i>" + "Password dimenticata? Imposta una nuova password" + "</i></u></div>");
		linkResetPasswordItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				resetPassword();	
			}
		});

		if (pLoginWindow.isShowResetPasswordLogin())
			linkResetPasswordItem.setVisible(true);
		else
			linkResetPasswordItem.setVisible(false);

		setFields(new FormItem[] { lUsernameFormItem, lPasswordFormItem, lButtonItem, linkResetPasswordItem });

		setAlign(Alignment.CENTER);
		setTop(50);
	}

	// DA METTERE CON JAAS
	private void provideLoginAction(final boolean ignoraControlloLingua) {
		System.out.println(this + ".provideLoginAction(" + ignoraControlloLingua + ") CALLED.");
		RPCRequest request = new RPCRequest();
		request.setContainsCredentials(true);
		request.setActionURL("j_security_check");
		request.setUseSimpleHttp(true);
		request.setShowPrompt(false);
		Map params = new HashMap();
		// adjust parameter names to match your authentication system
		String username = (String) getValue("j_username");
		String password = getValue("j_password") != null ? (String) getValue("j_password") : "";

		// Pulisco username e password dallo script injection
		username = GWTRestDataSource.clearFromScript(username);
		password = GWTRestDataSource.clearFromScript(password);

		// Pulisco username e password dall'html
		username = GWTRestDataSource.escapeHtml(username);
		password = GWTRestDataSource.escapeHtml(password);

		params.put("j_username", username);
		AurigaIndex lAurigaIndex = (AurigaIndex) loginWindow.getIndex();
		sjclClient.setEnabled(lAurigaIndex.getFlagCifratura());
		params.put("j_password", sjclClient.encrypt(password + "#SCHEMA#" + lAurigaIndex.schemaSelezionato));
		params.put("action", "login");
		System.out.println("params: " + params);
		request.setParams(params);
		RPCManager.sendRequest(request, new RPCCallback() {

			public void execute(RPCResponse response, Object rawData, RPCRequest request) {
				if (response.getStatus() == RPCResponse.STATUS_SUCCESS) {
					completeLogin(ignoraControlloLingua);
				} else {
					if (AurigaLayout.getIsAttivaAccessibilita()) {
						SC.say("ERRORE",I18NUtil.getMessages().loginError_message());					
					} else {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().loginError_message(), "", MessageType.ERROR));
					}
				}
			}
		});
	}

	// DA METTERE SENZA JAAS
	/*
	 * private void provideLoginAction(final boolean ignoraControlloLingua) { RPCRequest request = new RPCRequest(); request.setContainsCredentials(true);
	 * request.setActionURL("springdispatcher/verificaLogin/"); request.setUseSimpleHttp(true); request.setShowPrompt(false); Map params = new HashMap(); //
	 * adjust parameter names to match your authentication system final String username = (String) getValue("j_username"); final String password =
	 * getValue("j_password") != null ? (String) getValue("j_password") : ""; params.put("j_username", username); AurigaIndex lAurigaIndex = (AurigaIndex)
	 * loginWindow.getIndex(); sjclClient.setEnabled( lAurigaIndex.getFlagCifratura() ); params.put("j_password", sjclClient.encrypt(password + "#SCHEMA#" +
	 * lAurigaIndex.schemaSelezionato)); params.put("action", "login"); request.setParams(params); RPCManager.sendRequest(request, new RPCCallback() {
	 * 
	 * public void execute(RPCResponse response, Object rawData, RPCRequest request) { if(isLoginOk(response)) { completeLogin(ignoraControlloLingua); } else {
	 * Layout.addMessage(new MessageBean(I18NUtil.getMessages().loginError_message(), "", MessageType.ERROR)); } } }); }
	 * 
	 * private boolean isLoginOk(RPCResponse response) { if (response.getStatus() == RPCResponse.STATUS_SUCCESS) {
	 * if(response.getAttribute("httpResponseText").equalsIgnoreCase("loginOK")) { return true; } } return false; }
	 */

	// Cacco Federico 02-12-2015
	// Mantis 0000027
	// Devo tenere traccia se la login è dovuta allo scadere della sessione
	/**
	 * Avvia le azioni per completare la login
	 * 
	 * @param ignoraControlloLingua
	 *            True se la login è richiesta per la scadenza della sessione
	 */
	protected void completeLogin(final boolean ignoraControlloLingua) {
		// Chiamiamo la loadCombo
		Record lRecord = new Record();
		lRecord.setAttribute("userid", (String) getValue("j_username"));
		lRecord.setAttribute("password", (String) getValue("j_password"));
		AurigaIndex lAurigaIndex = (AurigaIndex) loginWindow.getIndex();
		lRecord.setAttribute("schema", lAurigaIndex.schemaSelezionato);
		new GWTRestService<Record, Record>("LoadComboDominioDataSource").call(lRecord, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				manageLoadComboResponse(object, ignoraControlloLingua);
			}
		});

		final String cookieNameToken = GWTRestDataSource.determineCookieName(false);
		Cookies.removeCookie(cookieNameToken, "/");
	}

	// Cacco Federico 02-12-2015
	// Mantis 0000027
	// Devo tenere traccia se la login è dovuta alla scadenza della sessione
	protected void manageLoadComboResponse(Record object, boolean ignoraControlloLingua) {
		RecordList lRecordList = object.getAttributeAsRecordList("domini");
		if (lRecordList == null || lRecordList.getLength() == 0) {
			waitAndLogout("Utente non accreditato in nessun dominio");
		} else if (lRecordList.getLength() > 1) {
			LinkedHashMap<String, String> lHashMap = new LinkedHashMap<String, String>();
			for (int i = 0; i < lRecordList.getLength(); i++) {
				lHashMap.put(lRecordList.get(i).getAttribute("tipoIdSpAoo"), lRecordList.get(i).getAttribute("denominazione"));
			}
			DominioWindow lDominioWindow = new DominioWindow(this, ignoraControlloLingua);
			lDominioWindow.setDominio(lHashMap);
			lDominioWindow.show();
			loginWindow.hide();
		} else if (lRecordList.getLength() == 1) {
			registraUtente(lRecordList.get(0).getAttribute("tipoIdSpAoo"), ignoraControlloLingua);
		}
	}

	// Cacco Federico 02-12-2015
	// Mantis 0000027
	// In caso di login dopo la scadenza della sessione devo ignorare il controllo lingua.
	// Questo perchè perdo l'informazione della lingua corrente (il parametro
	// era stato passato in post, e con la scadenza della sessione l'ho perso) e inoltre
	// l'interfacca grafica è già nella lingua corretta (lo scadere della sessione non
	// ha provocato il ricaricamento della GUI e delle relative etichette)
	/**
	 * Il metodo si occupa della registrazione dell'utente e di caricare l'applicazione nella lingua corretta (considerando anche le eventuali specializzazioni)
	 * Con questa chiamata si assume che la login non sia dovuta alla scadenza della sessione (ma ad esempio ad un nuovo avvio dell'applicazione)
	 * 
	 * @param dominio
	 *            Il dominio su cui registrare l'utente
	 */
	public void registraUtente(String dominio) {
		registraUtente(dominio, false);
	}

	/**
	 * Il metodo si occupa della registrazione dell'utente e di caricare l'applicazione nella lingua corretta (considerando anche le eventuali specializzazioni)
	 * Se la login è dovuta alla scadenza della sessione, i controlli sulla lingua vengono ignorati
	 * 
	 * @param dominio
	 *            Il dominio su cui registrare l'utente
	 * @param ignoraControlloLingua
	 *            flag che indica se la login è dovuta alla scadenza della sessione
	 */
	public void registraUtente(String dominio, final boolean ignoraControlloLingua) {

		AurigaIndex lAurigaIndex = (AurigaIndex) loginWindow.getIndex();
		lAurigaIndex.dominioSelezionato = dominio;
		// DA METTERE SENZA JAAS
		// lAurigaIndex.createSessionLoginInfo(getValueAsString("j_username"), new ServiceCallback<Record>() {
		// DA METTERE CON JAAS
		lAurigaIndex.createSessionLoginInfo(new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				if (object.getAttributeAsString("token") == null) {
					// Lancio il logout
					waitAndLogout(object.getAttributeAsString("loginReject"));
				} else if (object.getAttributeAsBoolean("passwordScaduta")) {
					// Lancio il form di password scaduta.
					passwordScaduta();
				} else {
					// Se la login è dovuta alla sessione scaduta non devo verificare
					// la lingua dell'applicazione, perchè tutte le label sono già state
					// caricate nella login precedente
					// Allo stesso modo non devo effettuare alcuna verifica se il multilingua
					// è disabilitato
					boolean multilinguaAbilitato = (Boolean.valueOf(object.getAttribute("attivaMultilingua")));
					if (!ignoraControlloLingua && multilinguaAbilitato) {
						String localUtente = "";
						// Estraggo la lingua dell'utente
						String linguaApplUtente = ((object.getAttribute("linguaApplicazione") != null)
								&& (object.getAttribute("linguaApplicazione").length() > 0)) ? object.getAttribute("linguaApplicazione") : "it";
						// Verifico se l'utente ha un domonio di specializzazione delle label
						boolean asSpecGui = true;
						String specGUI = ((object.getAttribute("specLabelGui") != null) && (object.getAttribute("specLabelGui").length() > 0))
								? object.getAttribute("specLabelGui") : "";
						if ((specGUI == null) || "".equalsIgnoreCase(specGUI.trim())) {
							specGUI = "";
							asSpecGui = false;
						}
						// Se l'utente non ha un dominio di speciaizzazione della GUI allora verifico
						// solamente che l'applicazione sia nella lingua corretta, altrimenti devo verificare
						// tutta la locale (lingua_PAESE_dominio)
						boolean needReload = false;
						if (asSpecGui) {
							// Controllo che il locale dell'utente sia uguale a quello corrente
							// Estraggo la locale dell'utente
							// La local dell'utente è la concatenazione di lingua + regione + dominio (separati da _)
							localUtente = (specGUI.length() > 0) ? (linguaApplUtente + "_" + linguaApplUtente.toUpperCase() + "_" + specGUI.toLowerCase())
									: linguaApplUtente;
							// Estraggo la local corrente
							String localApplCorrente = com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().getLocaleName();
							needReload = !localApplCorrente.equalsIgnoreCase(localUtente);
						} else {
							// Controllo che il locale dell'utente sia uguale a quello corrente
							// Estraggo la locale dell'utente
							localUtente = (specGUI.length() > 0) ? (linguaApplUtente + "_" + linguaApplUtente.toUpperCase() + "_" + specGUI.toLowerCase())
									: linguaApplUtente;
							// Estraggo la local corrente
							String localApplCorrente = com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().getLocaleName();
							// Considero accettabile il fatto che l'utente abbia lingua it e che l'applicazione sia in it_IT
							// (ovvero considero solo la lingua e non l'eventuale variante che dipende dal paese)
							needReload = (localApplCorrente.indexOf(localUtente) == -1);
						}
						if (needReload) {
							reloadApplication(object, localUtente);
						} else {
							passowrdOk();
						}
					} else {
						passowrdOk();
					}
				}
			}
		});
	}

	// Metodo per ricaricare Auriga nella lingua localUtente
	private void reloadApplication(Record object, String localUtente) {
		// Ricarico l'applicazione saltando il login e impostando la lingua desiderata
		// Salvo il dominio e lo schema selezionati dall'utente
		String dominio = object.getAttribute("dominio");
		String schema = object.getAttribute("schema");
		// Creo il form per l'invio dei parametri in post
		FormPanel form = new FormPanel("_self");
		form.setMethod(FormPanel.METHOD_POST);
		// Questi parametri devono essere nascosti, quindi vanno in post
		Hidden params1 = new Hidden("dominioSelezionato", dominio);
		Hidden params2 = new Hidden("schemaSelezionato", schema);
		Hidden params3 = new Hidden("skipLogin", "true");
		Hidden params4 = new Hidden("locale", localUtente);
		// Creo un form al volo per fare la redirect con i parametri in post
		FlowPanel panel = new FlowPanel();
		panel.add(params1);
		panel.add(params2);
		panel.add(params3);
		panel.add(params4);
		form.add(panel);
		form.setAction(removeLocalParamFromQueryString(Window.Location.getHref()));
		RootPanel.get().add(form);
		form.submit();
	}

	public void passwordScaduta() {
		CambioPasswordWindow lCambioPasswordWindow = new CambioPasswordWindow(form, "login");
		lCambioPasswordWindow.show();
		GWTRestDataSource.printMessage(new MessageBean(I18NUtil.getMessages().passwordScadutaError_message(), "", MessageType.WARNING));
	}

	public void passowrdOk() {
		RPCManager.resendTransaction();
		try {
			if (loginWindow.getIndex().getLayout().getConfigured())
				loginWindow.getIndex().getLayout().aggiornaUtente();
		} catch (Exception e) {
		}
		loginWindow.hide();
	}

	public void logout() {
		new GWTRestService<Record, Record>("LogoutDataSource").call(new Record(), new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				Window.Location.reload();
			}
		});
	}

	public void waitAndLogout(String message) {
		// MessageBean lMessageBean = new MessageBean(message, message, MessageType.ERROR);
		// Layout.addMessage(lMessageBean, 10000, new WaitingMessageCallback() {
		//
		// @Override
		// public void execute() {
		// logout();
		// }
		// });
		Record lRecordError = new Record();
		lRecordError.setAttribute("errorMessage", message);
		ErrorWindow lErrorWindow = new ErrorWindow(lRecordError) {

			@Override
			public void manageOnCloseWindow() {
				super.manageOnCloseWindow();
				logout();
			}
		};
		lErrorWindow.show();
	}

	private void resetPassword() {
		final String username = (String) getValue("j_username");
		if (username != null && !username.equalsIgnoreCase("")) {
			SC.ask("Confermi di voler resettare la tua password e ricevere via mail la nuova password ?", new BooleanCallback() {

				@Override
				public void execute(Boolean value) {
					if (value) {
						try {
							Layout.showWaitPopup(I18NUtil.getMessages().resetPasswordShowWaitPopup_message());

							RPCRequest request = new RPCRequest();
							request.setContainsCredentials(true);
							request.setActionURL("springdispatcher/servletResetPasswordNoAuth/");
							AurigaIndex lAurigaIndex = (AurigaIndex) loginWindow.getIndex();
							Map params = new HashMap();
							// adjust parameter names to match your authentication system
							final String username = (String) getValue("j_username");
							params.put("j_username", username);
							params.put("schema", lAurigaIndex.schemaSelezionato);
							request.setParams(params);
							RPCManager.sendRequest(request, new RPCCallback() {

								public void execute(RPCResponse response, Object rawData, RPCRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Layout.hideWaitPopup();
										Record lRecord = new Record(JSON.decode((String) rawData));
										if (lRecord.getAttributeAsBoolean("changeOk")) {
											SC.say(I18NUtil.getMessages().resetPasswordEsitoOK_message());
										} else {
											SC.say("Si e' verificato un errore nella procedura : " + lRecord.getAttributeAsString("errorMessages")
													+ "\nContattare Help Desk.");
										}
									} else {
										Layout.addMessage(new MessageBean("Si e' verificato un errore", "", MessageType.ERROR));
									}
								}
							});
						} catch (Exception e) {
							Layout.hideWaitPopup();
							Layout.addMessage(new MessageBean("Si e' verificato un errore ", "", MessageType.ERROR));
						}
					}
				}
			});
		} else {
			SC.warn(I18NUtil.getMessages().usernameRequired_message());
		}
	}

	// Cacco Federico 29-10-2015
	// Rimuove, se presente, il parametro locale dalla query string o url
	private static String removeLocalParamFromQueryString(String queryString) {
		try {
			// Controllo se la stringa contiene caratteri
			if ((queryString != null) && (queryString.length() > 0)) {
				int startPos = (queryString.toUpperCase().indexOf("?LOCALE=") > queryString.toUpperCase().indexOf("&LOCALE="))
						? queryString.toUpperCase().indexOf("?LOCALE=") : queryString.toUpperCase().indexOf("&LOCALE=");
				if (startPos > -1) {
					// Mi porto sulla lettera l di locale
					startPos += 1;
					// Ho trovato il parametro locale, cerco la sua fine
					int finishPos = queryString.indexOf("&", startPos);
					if (finishPos == -1) {
						// Non ci sono altri parametri oltre locale
						finishPos = queryString.length() - 1;
						// Evito che resti un "?" o una "&" alla fine della stringa
						startPos = startPos - 1;
					}
					finishPos += 1;
					return queryString.replace(queryString.substring(startPos, finishPos), "");
				}
			}
			return queryString;
		} catch (Exception e) {
			return queryString;
		}
	}

	public AurigaLoginWindow getLoginWindow() {
		return loginWindow;
	}

	public void setLoginWindow(AurigaLoginWindow loginWindow) {
		this.loginWindow = loginWindow;
	}

	public boolean isIgnoraControlloLingua() {
		return ignoraControlloLingua;
	}

	public void setIgnoraControlloLingua(boolean ignoraControlloLingua) {
		this.ignoraControlloLingua = ignoraControlloLingua;
	}

	// private String convertMapParamsToUrl(Map<String, List<String>> mappaParametri){
	// String parametriUrl = "";
	// // Ottengo il set delle chiavi della mappa
	// Set<String> keySet = mappaParametri.keySet();
	// // Ottengo l'iteratore del set delle chiavi
	// Iterator<String> keyIterator = keySet.iterator();
	// // Itero per estrarre i parametri
	// while (keyIterator.hasNext()){
	// String key = keyIterator.next();
	// // Ottengo la lista dei parametri della chiave corrente
	// List<String> listaParametri = mappaParametri.get(key);
	// // Ottengo la lista separata da virgola
	// String parametri = "";
	// for (int i = 0; i < listaParametri.size(); i++){
	// parametri = parametri + listaParametri.get(i) + ",";
	// }
	// // Se ci sono parametri tolgo la , finale e concateno
	// if (parametri.length() > 1){
	// parametri = parametri.substring(0, parametri.length() - 1);
	// parametriUrl = parametriUrl + key + "=" + parametri + "&";
	// }
	// }
	// // Elimino la & finale
	// if (parametriUrl.length() > 1){
	// return parametriUrl.substring(0, parametriUrl.length() - 1);
	// }else{
	// return parametriUrl;
	// }
	// }
}