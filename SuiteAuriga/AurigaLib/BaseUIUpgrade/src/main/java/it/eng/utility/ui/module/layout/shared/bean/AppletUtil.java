/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import it.eng.utility.ui.module.layout.client.Layout;

public class AppletUtil {

	public static String getParameters(String appletName){
		Map<String, String> lMapParams = Layout.getAppletParameter().get(appletName);
		StringBuffer lStringBuffer = new StringBuffer();
		for (String lStrProperty : lMapParams.keySet()){
			lStringBuffer.append("<param name =\"");
			lStringBuffer.append(lStrProperty);
			lStringBuffer.append("\" value =\"");
			lStringBuffer.append(lMapParams.get(lStrProperty));
			lStringBuffer.append("\">");
//			"<param name=\"filename\" 				value=\"filetmp_preview\">" +
		}
		return lStringBuffer.toString();
	}
	
	public static Map<String, String> getMapParameters(String appletName){
		Map<String, String> lMapParams = Layout.getAppletParameter().get(appletName);
		
		return lMapParams;
	}
}
