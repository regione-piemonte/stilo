package it.eng.core.business.subject;

import java.io.Serializable;
import java.util.Locale;

/**
 * Classe bean nel quale vengono settate le variabili di accesso del soggetto in connessione
 * @author michele
 *
 */
public class SubjectBean implements Serializable {


	private static final long serialVersionUID = 1L;

	/**
	 * Id dell'ente su cui si esegue l'operazione
	 */
	private String idDominio;
		
	/**
	 * Id dell'utente che esegue l'operazione
	 */
	private String iduser;
	
	/**
	 * Token id di login
	 */
	private String tokenid;
	
	/**
	 * Eventuale uuidtransaction per effettuare chiamate transazionali
	 */
	private String uuidtransaction = null;
	
	/**
	 * Id dell'applicazione con cui si accede
	 */
	private String idapplicazione;
	
	private String nomeapplicazione;

	
	private Locale locale;
	
		
	public String getIduser() {
		return iduser;
	}
	public void setIduser(String iduser) {
		this.iduser = iduser;
	}
	public String getTokenid() {
		return tokenid;
	}
	public void setTokenid(String tokenid) {
		this.tokenid = tokenid;
	}
	public String getUuidtransaction() {
		return uuidtransaction;
	}
	public void setUuidtransaction(String uuidtransaction) {
		this.uuidtransaction = uuidtransaction;
	}
	public String getIdapplicazione() {
		return idapplicazione;
	}
	public void setIdapplicazione(String idapplicazione) {
		this.idapplicazione = idapplicazione;
	}
	public String getIdDominio() {
		return idDominio;
	}
	public void setIdDominio(String idente) {
		this.idDominio = idente;
	}
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	public String getNomeapplicazione() {
		return nomeapplicazione;
	}
	public void setNomeapplicazione(String nomeapplicazione) {
		this.nomeapplicazione = nomeapplicazione;
	}
	
	
}