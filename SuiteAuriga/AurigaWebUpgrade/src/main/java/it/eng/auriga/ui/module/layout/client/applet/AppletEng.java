/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.shared.bean.AppletUtil;

public abstract class AppletEng extends HTMLFlow {

	private String appletName;
	private String jnplName;
	private Map<String, String> parameters;
	private int width;
	private int height;
	private boolean isHeightPerc;
	private boolean isWidthPerc;
	private String code;
	private String smartId;
	private Window window;

	public abstract void uploadFromServlet(String file);

	public AppletEng(String appletName, String jnplName, Map<String, String> parameters, int width, int height, boolean isWidthPerc, boolean isHeightPerc,
			final Window pWindow, final String[] appletJarName, String appletClass) {
		setWidth(width);
		setHeight(height);
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
		StringBuffer lStringBuffer = new StringBuffer();
		lStringBuffer.append("<APPLET ID=\"SignerApplet\" CODE=\"" + appletClass + "\" NAME=\"Applet di firma\" HEIGHT='100%' WIDTH='100%' archive=\"");
		boolean first = true;
		for (String lString : appletJarName) {
			if (first)
				first = false;
			else
				lStringBuffer.append(",");
			lStringBuffer.append(GWT.getHostPageBaseURL() + "applets/" + lString);
		}
		lStringBuffer.append("\">");
		Map<String, String> lMapExtra = AppletUtil.getMapParameters(appletName);
		if (lMapExtra != null)
			parameters.putAll(lMapExtra);
		parameters.put("callBack", idCreated + "uploadFunction");
		parameters.put("callBackCancel", idCreated + "cancelUpload");
		for (String lString : parameters.keySet()) {
			lStringBuffer.append("<PARAM name=\"" + lString + "\" value = \"" + parameters.get(lString).replace("\"", "&quot;") + "\">");
		}
		lStringBuffer.append("</APPLET>");
		setContents(lStringBuffer.toString());
		setDynamicContents(true);
		initUploadFunction(this, idCreated + "uploadFunction");
		initCancelUpload(this, idCreated + "cancelUpload");

		this.setRedrawOnResize(true);

		pWindow.addCloseClickHandler(new CloseClickHandler() {

			@Override
			public void onCloseClick(CloseClickEvent event) {
				defaultCloseClick(pWindow);

			}

		});
	}

	protected abstract void uploadAfterDatasourceCall(Record object);

	protected abstract Record getRecordForAppletDataSource();

	public void realCloseClick(final Window pWindow) {
		pWindow.markForDestroy();
		if (uploadDone) {
			final WaitPopup loadWindow = new WaitPopup();
			loadWindow.setZIndex(getZIndex());
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AppletDatasource");
			loadWindow.setZIndex(getZIndex());
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
			// }
		}
	}

	public void defaultCloseClick(final Window pWindow) {
		realCloseClick(pWindow);
	}

	private native void initUploadFunction(AppletEng appletEng, String functionName) /*-{
		$wnd[functionName] = function(value) {
			appletEng.@it.eng.auriga.ui.module.layout.client.applet.AppletEng::uploadFunction(Ljava/lang/String;)(value);
		};
	}-*/;

	private native void initCancelUpload(AppletEng appletEng, String functionName) /*-{
		$wnd[functionName] = function() {
			appletEng.@it.eng.auriga.ui.module.layout.client.applet.AppletEng::cancelUpload()();
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
