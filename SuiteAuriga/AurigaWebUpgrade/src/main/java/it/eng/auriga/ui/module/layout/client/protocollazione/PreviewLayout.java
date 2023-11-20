/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.VLayout;

public class PreviewLayout {

	public PreviewLayout(String pUri, Boolean remoteUri, InfoFileRecord lInfoFileRecord, String recordType, final Canvas container,
			final Canvas externalContainer, final boolean adapt) {
		Record lRecord = new Record();
		lRecord.setAttribute("uri", pUri);
		lRecord.setAttribute("remote", remoteUri);
		lRecord.setAttribute("infoFile", lInfoFileRecord);
		lRecord.setAttribute("recordType", recordType);
		int width = 0;
		int height = 0;
		if (adapt) {
			width = com.google.gwt.user.client.Window.getClientWidth();
			height = com.google.gwt.user.client.Window.getClientHeight();
		} else {
			width = externalContainer.getWidth();
			height = externalContainer.getHeight();
		}
		lRecord.setAttribute("width", width);
		lRecord.setAttribute("height", height);
//		if (lInfoFileRecord != null && lInfoFileRecord.getMimetype() != null && lInfoFileRecord.getMimetype().equals("application/pdf")
//				&& (!lInfoFileRecord.isFirmato() || (lInfoFileRecord.isFirmato() && lInfoFileRecord.getTipoFirma().equalsIgnoreCase("PADES")))) {
//			lRecord.setAttribute("mimetype", lInfoFileRecord.getMimetype());
//			manageResponse(lRecord, container, adapt);
//		} else {
			new GWTRestService<Record, Record>("PreviewFileDatasource").call(lRecord, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					manageResponse(object, container, adapt);
				}
			});
//		}
	}

	protected void manageResponse(Record object, Canvas container, boolean adapt) {
		if (object.getAttribute("mimetype").equals("notValid")) {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().preview_window_file_not_valid(), I18NUtil.getMessages().preview_window_file_not_valid(),
					MessageType.WARNING));
			return;
		}
		try {
			String recordType = object.getAttributeAsString("recordType");
			String mimetype = object.getAttributeAsString("mimetype");
			Record lRecord = new Record();
			lRecord.setAttribute("displayFilename", "");
			lRecord.setAttribute("uri", object.getAttributeAsString("uri"));
			lRecord.setAttribute("sbustato", "false");
			lRecord.setAttribute("remoteUri", object.getAttributeAsBoolean("remoteUri"));
			String url = GWT.getHostPageBaseURL() + "springdispatcher/preview?fromRecord=true&mimetype=" + mimetype + "&recordType="
					+ DownloadFile.encodeURL(recordType) + "&record=" + DownloadFile.encodeURL(JSON.encode(lRecord.getJsObj(), new JSONEncoder()));
			if (object.getAttribute("mimetype").equals("application/pdf")) {
				HTMLPane lHtmlPane = new HTMLPane();
				if (adapt) {
					int width = com.google.gwt.user.client.Window.getClientWidth() * 80 / 100;
					int height = com.google.gwt.user.client.Window.getClientHeight() * 80 / 100;
					lHtmlPane.setWidth100();
					lHtmlPane.setHeight100();
					if (adapt && container != null) {
						container.setWidth(width);
						container.setHeight(height);
						container.markForRedraw();
					}
				} else {
					lHtmlPane.setWidth100();
					lHtmlPane.setHeight100();
					container.setWidth100();
					container.setHeight100();
					container.markForRedraw();
				}
				lHtmlPane.setContentsURL(url);
				lHtmlPane.setContentsType(ContentsType.PAGE);
				VLayout lVLayout = new VLayout();
				lVLayout.setWidth100();
				lVLayout.setHeight100();
				lVLayout.addMember(lHtmlPane);
				((AbstractPreviewCanvas) container).finishLoad(lVLayout);
			} else if (object.getAttribute("mimetype").startsWith("image")) {
				Img lImg = new Img(url);
				int width = object.getAttributeAsInt("width");
				int height = object.getAttributeAsInt("height");
				if (adapt && container != null) {
					container.setWidth(width);
					container.setHeight(height);
					container.markForRedraw();
				}
				lImg.setWidth100();
				lImg.setHeight100();
				VLayout lVLayout = new VLayout();
				if (!adapt) {
					lVLayout.setWidth(width);
					lVLayout.setHeight(height);
				} else {
					lVLayout.setWidth100();
					lVLayout.setHeight100();
				}
				lVLayout.addMember(lImg);
				((AbstractPreviewCanvas) container).finishLoad(lVLayout);
			}
		} catch (Exception e) {
			container.markForDestroy();
		}
	}

}
