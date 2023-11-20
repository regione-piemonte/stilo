/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.BaseWindow;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;

/**
 * 
 * @author Cristiano Daniele
 * 
 *         Window utilizzata sia per la visualizzazione dei file di tipo image(.png .jpg .tif ) sia per l'anteprima e la conseguente stampa degli stessi.
 */

public class PreviewImageWindow extends BaseWindow {

	public PreviewImageWindow(final String uri, Boolean remoteUri, InfoFileRecord info) {
		
		super ("previewImageWindows");
		
		Record lRecord = new Record();
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("remote", remoteUri);
		lRecord.setAttribute("recordType", "FileToExtractBean");
		lRecord.setAttribute("infoFile", info);
		// Recupero le dimensioni della window
		int width = com.google.gwt.user.client.Window.getClientWidth();
		int height = com.google.gwt.user.client.Window.getClientHeight();
		lRecord.setAttribute("width", width);
		lRecord.setAttribute("height", height);
		new GWTRestService<Record, Record>("PreviewFileDatasource").call(lRecord, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				manageResponse(object);
			}
		});

	}

	private void manageResponse(Record object) {

		if (object.getAttribute("mimetype").equals("notValid")) {
			Layout.addMessage(new MessageBean("Impossibile visualizzare il file", "Impossibile visualizzare il file", MessageType.WARNING));
			return;
		}
		try {
			String recordType = object.getAttributeAsString("recordType");
			String mimetype = object.getAttributeAsString("mimetype");
			String servlet = object.getAttributeAsString("servlet");
			servlet = (servlet == null || servlet.length() == 0) ? "preview" : servlet;
			Record lRecord = new Record();
			lRecord.setAttribute("displayFilename", "");
			lRecord.setAttribute("uri", object.getAttributeAsString("uri"));
			lRecord.setAttribute("sbustato", "false");
			lRecord.setAttribute("remoteUri", object.getAttributeAsBoolean("remoteUri"));

			String url = GWT.getHostPageBaseURL() + "springdispatcher/" + servlet + "?fromRecord=true&mimetype=" + mimetype + "&recordType="
					+ DownloadFile.encodeURL(recordType) + "&record=" + DownloadFile.encodeURL(JSON.encode(lRecord.getJsObj(), new JSONEncoder()));

			Img lImg = new Img(url);
			// lImg.setOverflow(Overflow.VISIBLE);
			lImg.setWidth(object.getAttributeAsInt("width"));
			lImg.setHeight(object.getAttributeAsInt("height"));
			Canvas.showPrintPreview(new Object[] { lImg });

		} catch (Exception e) {
			markForDestroy();
		}
	}

}
