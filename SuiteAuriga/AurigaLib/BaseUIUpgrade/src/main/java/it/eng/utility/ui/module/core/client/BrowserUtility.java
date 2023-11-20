/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.user.client.Window.Navigator;

public class BrowserUtility {

	public static boolean detectIfIEBrowser() {
		String userAgent = Navigator.getUserAgent();
		if (userAgent != null) {
			return userAgent.toUpperCase().contains("MSIE") || userAgent.toUpperCase().contains("TRIDENT");
		} else {
			return false;
		}
	}
	
	public static boolean detectIfFireFox60Browser() {
		String userAgent = Navigator.getUserAgent();
		if (userAgent != null) {
			return userAgent.toUpperCase().contains("MOZILLA") && userAgent.toUpperCase().contains("FIREFOX/6");
		} else {
			return false;
		}
	}

}
