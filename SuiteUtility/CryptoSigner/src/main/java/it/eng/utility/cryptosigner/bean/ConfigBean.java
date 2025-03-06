package it.eng.utility.cryptosigner.bean;


import java.io.Serializable;

/**
 * Bean contenente le configurazioni dei certificati e delle CRL.
 * @author Rigo Michele
 */
public class ConfigBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String subjectDN;
	private String crlURL;
	private String schedule;
	
	/**
	 * Recupera il nome dell'entità associata al certificato così
	 * come  riportato nel Distinguished Name (RFC2459)
	 * @return
	 */
	public String getSubjectDN() {
		return subjectDN;
	}
	
	/**
	 * Definisce il nome dell'entità associata al certificato
	 * @return
	 */
	public void setSubjectDN(String subjectDN) {
		this.subjectDN = subjectDN;
	}
	
	/**
	 * Recupera l'URL da cui scaricare la CRL
	 * @return
	 */
	public String getCrlURL() {
		return crlURL;
	}
	
	/**
	 * Definisce l'URL da cui scaricare la CRL
	 * @param crlURL
	 */
	public void setCrlURL(String crlURL) {
		this.crlURL = crlURL;
	}
	
	/**
	 * Recupera il pattern di schedulazione configurato
	 * @return
	 */
	public String getSchedule() {
		return schedule;
	}
	
	/**
	 * Definisce un pattern di schedulazione
	 * @param schedule
	 */
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
}