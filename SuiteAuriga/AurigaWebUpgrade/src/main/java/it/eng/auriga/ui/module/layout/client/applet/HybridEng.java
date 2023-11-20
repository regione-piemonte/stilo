/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLFlow;

import it.eng.auriga.ui.module.layout.client.protocollazione.HybridWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.shared.bean.AppletUtil;

public abstract class HybridEng extends HTMLFlow {

	private String appletName;
	private String jnplName;
	private Map<String, String> parameters;
	private int width;
	private int height;
	private boolean isHeightPerc;
	private boolean isWidthPerc;
	private String code;
	private String smartId;
	private HybridWindow window;
	
	// Vengono inizializzate al primo avvio di un modulo Hybrid
	protected static String hybridPropertyConfiguratorHybridPort = null;
	protected static String hybridPropertyConfiguratorHybridPortSSL = null;
	protected static String hybridPropertyConfiguratorHybridWorkFolder = null;

	public abstract void uploadFromServlet(String file);

	public HybridEng(String appletName, String jnplName, final Map<String, String> parameters, int width, int height, boolean isWidthPerc, boolean isHeightPerc,
			final HybridWindow pWindow, final String[] appletJarName, String appletClass) {
		this.appletName = appletName;
		this.jnplName = jnplName;
		this.parameters = parameters;
		smartId = SC.generateID();
		setID(smartId);
		getElement().setId(smartId);
		getElement().setPropertyString("name", smartId);
		getElement().setPropertyString("ID", smartId);
		String idCreated = "appletEnd" + smartId + new Date().getTime();
		this.window = pWindow;
		Map<String, String> lMapExtra = AppletUtil.getMapParameters(appletName);
		if (lMapExtra != null) {
			parameters.putAll(lMapExtra);
		}
		parameters.put("callBack", idCreated + "uploadFunction");
		parameters.put("callBackCancel", idCreated + "cancelUpload");
		parameters.put("height", height + "");
		parameters.put("width", width + "");
		initUploadFunction(this, idCreated + "uploadFunction");
		initCancelUpload(this, idCreated + "cancelUpload");
		if (hybridPropertyConfiguratorHybridPort != null) {
			parameters.put("hybridPort", hybridPropertyConfiguratorHybridPort);
			parameters.put("hybridPortSSL", hybridPropertyConfiguratorHybridPortSSL);
			parameters.put("hybridWorkFolder", hybridPropertyConfiguratorHybridWorkFolder);
			JavaScriptObject lMpaJS = JSOHelper.convertMapToJavascriptObject(parameters);
			startHybrid(lMpaJS);
		} else {
			new GWTRestService<Record, Record>("HybridConfigurationDataSource").call(new Record(), new ServiceCallback<Record>() {
				
				@Override
				public void execute(Record object) {
					hybridPropertyConfiguratorHybridPort = object.getAttribute("hybridPort");
					hybridPropertyConfiguratorHybridPortSSL = object.getAttribute("hybridPortSSL");
					hybridPropertyConfiguratorHybridWorkFolder = object.getAttribute("hybridWorkFolder");
					parameters.put("hybridPort", hybridPropertyConfiguratorHybridPort);
					parameters.put("hybridPortSSL", hybridPropertyConfiguratorHybridPortSSL);
					parameters.put("hybridWorkFolder", hybridPropertyConfiguratorHybridWorkFolder);
					JavaScriptObject lMpaJS = JSOHelper.convertMapToJavascriptObject(parameters);
					startHybrid(lMpaJS);
				}
			});
				
		}
	}

	protected abstract void startHybrid(JavaScriptObject jsParams);

	protected abstract void uploadAfterDatasourceCall(Record object);

	protected abstract Record getRecordForAppletDataSource();

	public void realCloseClick(final HybridWindow pWindow) {
		// pWindow.markForDestroy();
		if (uploadDone) {
			final WaitPopup loadWindow = new WaitPopup();
			loadWindow.show("Trasferimento file in corso");
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AppletDatasource");
			loadWindow.show("Trasferimento file in corso");
			Record lRecord = getRecordForAppletDataSource();
			lGwtRestService.call(lRecord, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					uploadAfterDatasourceCall(object);
					loadWindow.hideFinal();
				}

				public void manageError() {
					loadWindow.hideFinal();
				};
			});
		}
	}

	private native void initUploadFunction(HybridEng appletEng, String functionName) /*-{
		$wnd[functionName] = function(value) {
			appletEng.@it.eng.auriga.ui.module.layout.client.applet.HybridEng::uploadFunction(Ljava/lang/String;)(value);
		};
	}-*/;

	private native void initCancelUpload(HybridEng appletEng, String functionName) /*-{
		$wnd[functionName] = function() {
			appletEng.@it.eng.auriga.ui.module.layout.client.applet.HybridEng::cancelUpload()();
		};
	}-*/;

	public boolean uploadDone = false;

	public void uploadFunction(String file) {
		uploadFromServlet(file);
		uploadDone = true;
	}

	public void cancelUpload() {
		uploadDone = false;
	}

	public void setAppletName(String appletName) {
		this.appletName = appletName;
	}

	public String getAppletName() {
		return appletName;
	}

	public void setJnplName(String jnplName) {
		this.jnplName = jnplName;
	}

	public String getJnplName() {
		return jnplName;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setHeightPerc(boolean isHeightPerc) {
		this.isHeightPerc = isHeightPerc;
	}

	public boolean isHeightPerc() {
		return isHeightPerc;
	}

}
