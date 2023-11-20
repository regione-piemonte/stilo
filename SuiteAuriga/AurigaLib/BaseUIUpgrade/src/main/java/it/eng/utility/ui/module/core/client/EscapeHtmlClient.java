/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * 
 * @author Federico Cacco
 *
 * L'UTILIZZO DI QUESTI ESCAPE CREA PROBLEMI COI PARAMETRI JSON
 * 
 */
public class EscapeHtmlClient {
	
	private boolean enabled;
	
	public EscapeHtmlClient() {
		this.enabled = false;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String escapeHtml(String stringToClean) {		
		if (enabled && stringToClean != null && !"".equalsIgnoreCase(stringToClean)) {
			
			// L'UTILIZZO DI QUESTI ESCAPE CREA PROBLEMI COI PARAMETRI JSON
			 stringToClean = stringToClean.replace("<", "&lt;");
			 stringToClean = stringToClean.replace(">", "&gt;");
			 stringToClean = stringToClean.replace("&", "&amp;");
			 stringToClean = stringToClean.replace("\"", "&quot;");
			 stringToClean = stringToClean.replace("'", "&#39;");
			
		}
       
       return stringToClean;
	};
	
	public Object escapeHtml(Object objectToClean) {
		if (enabled && objectToClean instanceof String) {
			objectToClean = escapeHtml((String) objectToClean);
		}
		
		return objectToClean;
	};
	
}
