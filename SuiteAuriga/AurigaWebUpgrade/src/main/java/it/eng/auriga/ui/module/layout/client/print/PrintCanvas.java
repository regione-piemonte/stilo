/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.util.PrintHTMLCallback;
import com.smartgwt.client.util.PrintProperties;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.drawing.DrawPane;

public class PrintCanvas extends DrawPane {
	
	private String url;
	
	public PrintCanvas(String url){
		this.url = url;
	}

	@Override
	public String getPrintHTML(
			PrintProperties printProperties,
			PrintHTMLCallback callback) {
//		return "<embed src=\"page/Rep.pdf\">";
//		HTMLFlow lHtmlFlow = new HTMLFlow();
//		lHtmlFlow.setWidth100();
//		lHtmlFlow.setHeight100();
//		lHtmlFlow.setContentsURL(url);
//		return lHtmlFlow.getPrintHTML(printProperties, callback);
		
		return "<iframe src=\"" + url + "?&embedded=true\" style=\"width:100%; height:100%;\" frameborder=\"0\"\"></iframe>";
	}
}
