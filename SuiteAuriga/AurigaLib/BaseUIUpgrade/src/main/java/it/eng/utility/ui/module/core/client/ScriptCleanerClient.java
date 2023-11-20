/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.regexp.shared.RegExp;

import it.eng.utility.ui.module.core.shared.DefaultScriptPatterns;

public class ScriptCleanerClient {
	
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
	
	private boolean enabled;
	
	public ScriptCleanerClient() {
		setEnabled(false);
	}
	
	public synchronized String clearFromScript(String stringToClean) {		
		if (isEnabled() && stringToClean != null && !"".equalsIgnoreCase(stringToClean)) {
			// Avoid null characters
			stringToClean = stringToClean.replaceAll("\0", "");			
			// Elimino gli script usando le espressioni regolari con modifier gim:
			// - global [g]: all matches (don't return on first match)
			// - case-insensitive [i]: ignores case of [a-zA-Z]
			// - multi-line match [m]
			for (String scriptPattern : getPatterns()){
				if (scriptPattern != null && !"".equalsIgnoreCase(scriptPattern)) {
					RegExp lRegExp = RegExp.compile(scriptPattern, "gim");
					stringToClean = lRegExp.replace(stringToClean, "");
				}
			}			
		}       
		return stringToClean;
	};
	
	public Object clearFromScript(Object objectToClean) {
		if (isEnabled() && objectToClean instanceof String) {
			objectToClean = clearFromScript((String) objectToClean);
		}		
		return objectToClean;
	};
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
