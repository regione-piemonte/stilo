/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.utility.ui.module.layout.client;

import java.util.HashMap;
import java.util.Map;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.OneCallGWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.error.ErrorWindow;
import it.eng.utility.ui.module.layout.client.message.MessageBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.HandleErrorCallback;
import com.smartgwt.client.rpc.HandleTransportErrorCallback;
import com.smartgwt.client.rpc.LoginRequiredCallback;
import com.smartgwt.client.rpc.RPCCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;

public abstract class ExternalPortlet extends Index {
	
	protected Canvas portletLayout;
	protected String nomeEntita;
	
	protected boolean loginError;	

	public abstract Canvas createPortletLayout(String nomeEntita, HashMap<String, String> params);

	@Override
	public void onModuleLoad() {
		 
		Dictionary dictionary = Dictionary.getDictionary("params");
		
		setExternalLoginParams(dictionary);		
		
		nomeEntita = dictionary.get("nomeEntita");
		
//		Event.addNativePreviewHandler(new NativePreviewHandler() {
//
//			@Override
//			public void onPreviewNativeEvent(NativePreviewEvent event) {poi non 
//				manageEvent(event);
//
//			}
//		});
		
		loginwindowDefault = new LoginWindow(this);
				
		configure();

		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {			
			@Override
			public void onUncaughtException(Throwable pThrowable) {
				try {
					Layout.hideWaitPopup();
				} catch (Exception e) {
				}
				GWT.log("Uncaught Exception: " + pThrowable.getMessage(), pThrowable);
				if(showErrorWindowOnUncaughtException()) {
					ErrorWindow lErrorWindow = new ErrorWindow(pThrowable);
					lErrorWindow.show();				
				}
			}
		});
		
		RPCManager.setHandleTransportErrorCallback(new HandleTransportErrorCallback() {			
			@Override
			public void handleTransportError(int transactionNum, int status,
					int httpResponseCode, String httpResponseText) {
				try {
					Layout.hideWaitPopup();
				} catch (Exception e) {
				}
				GWT.log("Transport Error: " + httpResponseText + " [" + httpResponseCode + "]");
				if (Layout.utenteLoggato != null) {
					RPCManager.clearTransaction(transactionNum);
				}
				RPCManager.cancelDefaultErrorHandling();	
			}
		});	
		
		//SC.showConsole();
		RPCManager.setHandleErrorCallback(new HandleErrorCallback() {
			@Override
			public void handleError(DSResponse response, DSRequest request) {					
				// String errorMessage = response.getData()[0].getAttribute("errorMessage");
				// int errorCode = response.getData()[0].getAttributeAsInt("errorCode");
				// String htmlStackTrace = response.getData()[0].getAttribute("htmlStackTrace");
				try {
					Layout.hideWaitPopup();
				} catch (Exception e) {
				}				
				ErrorWindow lErrorWindow = new ErrorWindow(response.getData()[0]);
				lErrorWindow.show();			
			}
		});		
		
		RPCManager.setLoginRequiredCallback(new LoginRequiredCallback(){
			@Override
			public void loginRequired(int transactionNum, RPCRequest request,
					RPCResponse response) {
				try {
					Layout.hideWaitPopup();
				} catch (Exception e) {
				}
				if (Layout.utenteLoggato != null) {
					RPCManager.clearTransaction(transactionNum);
				}
				if(isExternalLogin()) {	
					externalLogin();									
				} 
				else if ((!loginwindowDefault.isDrawn()) || (!loginwindowDefault.isVisible())) {
					if(layout == null) {
						layout = buildPortalLayout();
					}					
					loginwindowDefault.getForm().clearValues();					
					loginwindowDefault.markForRedraw();
					loginwindowDefault.show();
					loginwindowDefault.getForm().focusInItem("j_username");	
					if(loginError) {
						layout.addMessage(new MessageBean(I18NUtil.getMessages().loginError_message(), "", MessageType.ERROR));
						loginError = false;						
					}
				}
			}
	    });

		Window.addWindowClosingHandler(new ClosingHandler() {			
			public void onWindowClosing(ClosingEvent event) {
				RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, GWT.getHostPageBaseURL() + "springdispatcher/logout");
				builder.setHeader("Content-type", "application/x-www-form-urlencoded");		
				try {
					Request response = builder.sendRequest("", new RequestCallback() {

						@Override
						public void onResponseReceived(Request request,
								Response response) {
//							System.out.println("Risposta ricevuta");
						}

						@Override
						public void onError(Request request, Throwable exception) {
//							System.out.println("Errore Risposta ricevuta");
							
						}
						
					});
				} catch (RequestException e) {
				}	
			}
		});		
		
		Window.addResizeHandler(new ResizeHandler() {		
			@Override
			public void onResize(ResizeEvent event) {
				
				layout.getOpenedPortlets().redrawOpenedPortletMenuButtons();
				layout.getMessagebox().redrawMessageBoxStack();
			}
		});
		
		RootPanel.get("loadingWrapper").setVisible(false);
		adaptFilterBuilderDefaults();

	}
	
	protected abstract boolean isExternalLogin();
	
	protected abstract void setExternalLoginParams(Dictionary dictionary);
	
	protected abstract String getUsernameForExternalLogin();
	
	protected abstract String getPasswordForExternalLogin();
	
	protected abstract void resetParamsOnLoginError();
	
	private void externalLogin() {
				
		RPCRequest req = new RPCRequest();
		req.setContainsCredentials(true);
		req.setActionURL("j_security_check");
		req.setUseSimpleHttp(true);
		req.setShowPrompt(false);
		Map params = new HashMap();
		// adjust parameter names to match your authentication system
		params.put("j_username", getUsernameForExternalLogin());
		params.put("j_password", getPasswordForExternalLogin());
		req.setParams(params);
					
		RPCManager.sendRequest(req, new RPCCallback(){
			public void execute(RPCResponse response, Object rawData, RPCRequest request) {
				if (response.getStatus() == RPCResponse.STATUS_SUCCESS) {
					createSessionLoginInfo(new ServiceCallback<Record>() {						
						@Override
						public void execute(Record object) {
							// get rid of login window											
							RPCManager.resendTransaction();	
							try {	                	 
								if (getLayout().getConfigured())
									getLayout().aggiornaUtente();	                	 	                	 
							} catch(Exception e) {};							
							if(object != null && object.getAttribute("idApplicazione") != null) {
								layout = buildPortalLayout();
							}
						}
					});													
				} else {	
					loginError = true;
					resetParamsOnLoginError();
					new OneCallGWTRestService<Record, Record>("LogoutDataSource").call(new Record(), new ServiceCallback<Record>() {				
						@Override
						public void execute(Record object) {}
					});		
				}					
			}
		});				
		
	}
	
	@Override
	public Layout buildPortalLayout() {
		return new Layout() {
			@Override
			public void afterAggiornaUtente() {					
				portletLayout = createPortletLayout(nomeEntita, null);
				portletLayout.setHeight100();
				portletLayout.setWidth100();
				portletLayout.markForRedraw();		
			}	
			
			@Override
			public MessageBox buildMessageBox() {
				return new MessageBox(false);
			}
		};		
	}

}
