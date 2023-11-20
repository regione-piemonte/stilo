/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;

/**
 * 
 * @author DANCRIST
 *
 */

public class DownloadClob {
	
	public static void downloadClobFromRecord(Record record){		
		if(isExternalPortlet()) {
			Window.open(GWT.getHostPageBaseURL() + "springdispatcher/downloadClob?record=" + encodeURL(JSON.encode(record.getJsObj(), new JSONEncoder())),"_blank","");	
		} else {
			Window.open(GWT.getHostPageBaseURL() + "springdispatcher/downloadClob?record=" + encodeURL(JSON.encode(record.getJsObj(), new JSONEncoder())),"downloadTarget","");	
		}		
	}
	
	private static boolean isExternalPortlet() {
		String moduleBaseURL = GWT.getModuleBaseURL();
		String href = Window.Location.getHref();
		int endIndex = moduleBaseURL.lastIndexOf("/", moduleBaseURL.length()-2);
		String relativePath = href.substring(endIndex+1, href.length());
		return relativePath != null && !"".equals(relativePath) && relativePath.startsWith("portlet.jsp");
	}
	
	public static String encodeURL(String str) {
		if(str != null) {			
			return URL.encode(str.replaceAll("&", "%26"));
		}
		return null;
	}

}
