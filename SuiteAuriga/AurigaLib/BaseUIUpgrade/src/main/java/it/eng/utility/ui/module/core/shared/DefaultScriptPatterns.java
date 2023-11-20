/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class DefaultScriptPatterns {
	
	private static String[] patterns = new String[]{
	        // script fragments
			"<\\s*script\\s*>(.*?)<\\s*\\/\\s*script\\s*>",
	        // src='...' 
			// queste espressioni regolari le devo commentare altrimenti se ad es. inserisco un'immagine in base64 nel corpo di una mail mi restituisce errore o me la rimuove
			// bisogna vedere come modificarle per far si che vengano individuati solo gli script all'interno di src
//			"src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
//			"src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
//			"src[\r\n]*=[\r\n]*\'(.*?)\'",
//			"src[\r\n]*=[\r\n]*\"(.*?)\"",
	        // lonely script or iframe tags
			"<\\s*(script|iframe)(.*?)>",
			"<\\s*\\/\\s*(script|iframe)\\s*>",
	        // eval(...) or expression(...)
			"(eval|expression)\\s*\\((.*?)\\)",
	        // vbscript or javascript:...
			"(vbscript|javascript)\\s*:",
            // onload or onerror or onclick
			"(onerror|onclick|onload)\\s*=",
			// onload(...)= or onerror(...)= or onclick(...)=
			"(onerror|onclick|onload)\\s*\\(.*?\\)\\s*=",
	    };
	
	public static String[] getPatterns() {
		return patterns;
	}
	
}
