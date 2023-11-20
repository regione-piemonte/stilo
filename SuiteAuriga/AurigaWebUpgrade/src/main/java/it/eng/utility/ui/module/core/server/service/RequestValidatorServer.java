/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.module.core.shared.DefaultScriptPatterns;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;
import it.eng.utility.ui.user.ParametriDBUtil;

public class RequestValidatorServer {
	
	public static boolean containsScript(HttpSession session, String request) {		
		String[] scriptPatterns = null;
		GenericConfigBean lGenericPropertyConfigurator = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");
		if(lGenericPropertyConfigurator.getFlagAttivaRequestValidator() != null ? Boolean.parseBoolean(lGenericPropertyConfigurator.getFlagAttivaRequestValidator()) : false) {
			String patternsReadFromDb = ParametriDBUtil.getParametroDB(session, "SCRIPT_CLEANER_PATTERNS");	
			if (patternsReadFromDb != null && !"".equalsIgnoreCase(patternsReadFromDb)) {
				scriptPatterns = patternsReadFromDb.split("\\|\\*\\|");				
			} else {
				scriptPatterns = DefaultScriptPatterns.getPatterns();
			}
		}
		if (scriptPatterns != null && request != null && !"".equalsIgnoreCase(request)) {
			// Vedo se ci sono script usando le espressioni regolari con modifier im:
			// - case-insensitive [i]: ignores case of [a-zA-Z]
			// - multi-line match [m]
			for (String scriptPattern : scriptPatterns){
				if (scriptPattern != null && !"".equalsIgnoreCase(scriptPattern)) {
					Pattern pattern = Pattern.compile(scriptPattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
					Matcher matcher = pattern.matcher(request);
					if(matcher.find()) {
						return true;
					}
				}
			}			
		}       
		return false;
	}

}
