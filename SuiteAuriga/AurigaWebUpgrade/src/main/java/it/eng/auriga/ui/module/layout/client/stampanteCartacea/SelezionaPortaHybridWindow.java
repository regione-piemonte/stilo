/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.SC;

import it.eng.auriga.ui.module.layout.client.PortScannerUtility.PortScannerCallback;
import it.eng.auriga.ui.module.layout.client.applet.HybridEng;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.HybridWindow;

public class SelezionaPortaHybridWindow extends HybridWindow {

	protected final HybridEng instanceApplet;
	protected HybridWindow instanceWindow;
	private String smartId;
	private PortScannerCallback returnCallback;
	private PortScannerCallback returnCallbackCancelSelectPort;

	public SelezionaPortaHybridWindow(String nomePorta, PortScannerCallback pReturnCallback, PortScannerCallback pReturnCallbackCancelSelectPort) {

		super(I18NUtil.getMessages().selezionaStampante_title(), "PortScanner.jar");

		instanceWindow = this;
		returnCallback = pReturnCallback;
		returnCallbackCancelSelectPort = pReturnCallbackCancelSelectPort;
		smartId = SC.generateID();

		String idCreated = "appletEnd" + smartId + new Date().getTime();
		initCallBack(this, idCreated + "callbackSelectPort");
		initCallBackCancelSelectPort(this, idCreated + "callbackCancelSelectPort");

		Map<String, String> lMapParams = new HashMap<String, String>();
		lMapParams.put("callbackSelectPort", idCreated + "callbackSelectPort");
		lMapParams.put("callbackCancelSelectPort", idCreated + "callbackCancelSelectPort");
		lMapParams.put("portaSelezionata", nomePorta != null ? nomePorta : "");

		HybridEng lAppletEng = new HybridEng("PortScanner", "PortScanner.jnlp", lMapParams, 400, 200, true, true, this,
				new String[] { "PortScanner.jar" }, "it.eng.applet.PortScannerApplet") {

			@Override
			public void uploadFromServlet(String file) {

			}

			@Override
			protected void uploadAfterDatasourceCall(Record object) {

			}

			@Override
			protected Record getRecordForAppletDataSource() {
				return null;
			}

			// @Override
			// public void defaultCloseClick(Window pWindow) {
			// pWindow.markForDestroy();
			// }

			@Override
			protected void startHybrid(JavaScriptObject jsParams) {
				callHybrid(jsParams);
			}
		};
		instanceApplet = lAppletEng;
	}

	public static native void callHybrid(JavaScriptObject lMapParams) /*-{
		$wnd.doPortScanner(lMapParams);
	}-*/;

	private native void initCallBack(SelezionaPortaHybridWindow pSelezionaPorta, String functionName) /*-{
		$wnd[functionName] = function(value) {
			return pSelezionaPorta.@it.eng.auriga.ui.module.layout.client.stampanteCartacea.SelezionaPortaHybridWindow::selectAndCloseWindow(Ljava/lang/String;)(value);
		}
	}-*/;
	
	private native void initCallBackCancelSelectPort(SelezionaPortaHybridWindow pSelezionaPorta, String functionName) /*-{
		$wnd[functionName] = function(value) {
			return pSelezionaPorta.@it.eng.auriga.ui.module.layout.client.stampanteCartacea.SelezionaPortaHybridWindow::cancelAndCloseWindow(Ljava/lang/String;)(value);
		}
	}-*/;

	public void selectAndCloseWindow(final String portSelected) {
		if (returnCallback != null) {
			returnCallback.execute(portSelected);
		}
		instanceApplet.markForDestroy();
	}
	
	public void cancelAndCloseWindow(final String portSelected) {
		if (returnCallbackCancelSelectPort != null) {
			returnCallbackCancelSelectPort.execute(portSelected);
		}
		instanceApplet.markForDestroy();
	}
	
}
