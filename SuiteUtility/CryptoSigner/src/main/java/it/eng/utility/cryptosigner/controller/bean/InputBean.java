package it.eng.utility.cryptosigner.controller.bean;

import java.io.File;
import java.security.cert.CRL;
import java.util.HashMap;
import java.util.Map;

public class InputBean {
	
	// Mappa dei flag indicanti i controlli da effettuare
	Map<String,Boolean>	checks;
	
	// Lista delle crl 
	private CRL crl;
	
	//dir temporanea
	File tempDir;// 
	/**
	 * Recupera il valore di un flag di controllo
	 * @param property nome del flag da recuperare
	 * @return il valore del flag
	 */
	public Boolean getFlag(String property) {
		if (checks!=null && checks.containsKey(property))
			return checks.get(property);
		return false;
	}
	
	/**
	 * Definisce il valore di un flag di controllo
	 * @param property nome del flag da settare
	 * @param value valore del flag
	 */
	public void setFlag(String property, Boolean value) {
		if (checks==null)
			checks = new HashMap<String, Boolean>();
		checks.put(property, value);
	}
	
	/**
	 * Recupera i flag dei controlli da effettuare
	 * @return map
	 */
	public Map<String, Boolean> getChecks() {
		return checks;
	}
	
	/**
	 * Definisce i flag dei controlli da effettuare
	 * @param checks
	 */
	public void setChecks(Map<String, Boolean> checks) {
		this.checks = checks;
	}

	
	/**
	 * Recupera la crl in input
	 * @return crl
	 */
	public CRL getCrl() {
		return crl;
	}
	
	/**
	 * Definisce la crl in input
	 * @param crl
	 */
	public void setCrl(CRL crl) {
		this.crl = crl;
	}

	public File getTempDir() {
		return tempDir;
	}

	public void setTempDir(File tempDir) {
		this.tempDir = tempDir;
	}
	
}
