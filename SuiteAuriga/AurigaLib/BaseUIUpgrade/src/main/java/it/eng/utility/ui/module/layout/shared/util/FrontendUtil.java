/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class FrontendUtil {

	public static boolean showAsteriskIconInRequiredFormItemTitle() {
		return false;		
	}
	
	public static String getRequiredFormItemTitle(String title) {
		return getRequiredFormItemTitle(title, true);
	}
	
	public static String getRequiredFormItemTitle(String title, boolean mostraIconaRequired) {
		if(title == null) return null;		
		if (mostraIconaRequired && !isRequiredFormItemTitle(title)) {
			if(showAsteriskIconInRequiredFormItemTitle()) {
				return "<img src=\"images/required.png\" width=\"10\" size =\"10\" align=MIDDLE/>&nbsp;<font style=\"font-weight:bold\">" + title + "</font>";
			} else {
				return "<font style=\"font-weight:bold\">" + title + "&nbsp;*</font>";
			}
		} else {
			return "<font style=\"font-weight:bold\">" + title + "</font>";			
		}
	}
	
	public static boolean isRequiredFormItemTitle(String title) {
		if(showAsteriskIconInRequiredFormItemTitle()) {
			return title != null && title.contains("<img src=\"images/required.png\" width=\"10\" size =\"10\" align=MIDDLE/>");
		} else {
			return title != null && title.endsWith("&nbsp;*</font>");
		}
	}
	
}
