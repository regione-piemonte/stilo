/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.regexp.shared.RegExp;

import it.eng.utility.ui.module.core.shared.DefaultScriptPatterns;

public class RequestValidatorClient {
	
	private static String[] dbPatterns = null;

	public static String[] getPatterns() {
		if (dbPatterns == null) {
			String patternsReadFromDb = UserInterfaceFactory.getParametroDB("SCRIPT_CLEANER_PATTERNS");
			if (patternsReadFromDb != null && !"".equalsIgnoreCase(patternsReadFromDb)) {
				dbPatterns = patternsReadFromDb.split("\\|\\*\\|");
				return dbPatterns;
			}
			return DefaultScriptPatterns.getPatterns();
		} else {
			return dbPatterns;
		}
	}
	
	public static boolean containsScript(String stringToCheck) {		
		if (stringToCheck != null && !"".equalsIgnoreCase(stringToCheck)) {
			// Vedo se ci sono script usando le espressioni regolari con modifier im:
			// - case-insensitive [i]: ignores case of [a-zA-Z]
			// - multi-line match [m]
			for (String scriptPattern : getPatterns()){
				if (scriptPattern != null && !"".equalsIgnoreCase(scriptPattern)) {
					RegExp lRegExp = RegExp.compile(scriptPattern, "im");
					if(lRegExp.exec(stringToCheck) != null) {
						return true;
					}
				}
			}			
		}       
		return false;
	};

}
