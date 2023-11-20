/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.error.ErrorWindow;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.bean.BeanFactory;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.HandleErrorCallback;
import com.smartgwt.client.rpc.HandleTransportErrorCallback;
import com.smartgwt.client.rpc.LoginRequiredCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.form.fields.FormItem;

public abstract class Index implements EntryPoint {

	protected LoginWindow loginwindowDefault;
	protected Layout layout;

	public abstract void configure();

	@Override
	public void onModuleLoad() {
		manageOnModuleLoad(true);
	}

	public void manageOnModuleLoad(boolean toBuild) {
		
		/*
		Event.addNativePreviewHandler(new NativePreviewHandler() {
		
			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
			manageEvent(event);
			
			}
		});
		*/
		
		loginwindowDefault = new LoginWindow(this);

		configure();
		
		// Trigger generation of BeanFactories for any classes annotated with BeanFactory.Generate 
        GWT.create(BeanFactory.AnnotationMetaFactory.class); 

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
			public void handleTransportError(int transactionNum, int status, int httpResponseCode, String httpResponseText) {
				try {
					Layout.hideWaitPopup();
				} catch (Exception e) {
				}
				GWT.log("Transport Error: " + httpResponseText + " [" + httpResponseCode + "]");
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
				}
			}
		});

		// SC.showConsole();
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
				simpleLoginRequired();
			}
		});

		Window.addWindowClosingHandler(new ClosingHandler() {

			public void onWindowClosing(ClosingEvent event) {
				
			}
		});

		Window.addResizeHandler(new ResizeHandler() {

			@Override
			public void onResize(ResizeEvent event) {
				if(layout != null) {
					layout.getOpenedPortlets().redrawOpenedPortletMenuButtons();
					layout.getMessagebox().redrawMessageBoxStack();
				}
			}
		});
		
		FormItem.setWarnOnEditorTypeConversionDefault(false);

		if (layout != null) {
			layout.closeAllPortlets();
			if (layout.getMessagebox() != null) {
				layout.getMessagebox().destroyMessageBoxLabels();
			}
			layout.destroy();
			layout = null;
		}

		if (toBuild) {
			layout = buildPortalLayout();
			layout.draw();
		}

		RootPanel.get("loadingWrapper").setVisible(false);
		adaptFilterBuilderDefaults();
	}

	protected void simpleLoginRequired() {
		
		if ((!loginwindowDefault.isDrawn()) || (!loginwindowDefault.isVisible())) {
			loginwindowDefault.getForm().clearValues();
			loginwindowDefault.markForRedraw();
			loginwindowDefault.show();
			loginwindowDefault.getForm().focusInItem("j_username");
		}
	}

	public Layout buildPortalLayout() {
		return new Layout();
	}

	public Layout getLayout() {
		return layout;
	}

	public static String getLanguage() {
		return I18NUtil.getMessages().language();
	}

	public static native void startdockmenu() /*-{
		$wnd.startmenu();
	}-*/;

	protected native void adaptFilterBuilderDefaults() /*-{
		
		//set widths for field picker, operator picker and value item
		$wnd.isc.FilterClause.addProperties({
			fieldPickerWidth : 200,
			operatorPickerWidth : "*",
			valueItemWidth : 200,
	    	valueItemTextHint: ""
		});
	}-*/;

	protected void manageEvent(NativePreviewEvent event) {
		
		if (event.getTypeInt() == Event.ONKEYDOWN) {
			if (event.getNativeEvent().getKeyCode() == 8) {
				JavaScriptObject lJavaScriptObject = event.getNativeEvent().getEventTarget();
				String lStrId = JSOHelper.getAttribute(lJavaScriptObject, "id");
				if (lStrId.equalsIgnoreCase("body")) {
					event.cancel();
				}
			}
		}
	}

	protected void createSessionLoginInfo(ServiceCallback<Record> callback) {
		
	};
	
	protected boolean showErrorWindowOnUncaughtException() {
		return Layout.isDebugClientEnable();
	}
	
	protected boolean showStackTraceInErrorWindowOnResponseError() {
		return Layout.isDebugClientEnable();
	}

}
