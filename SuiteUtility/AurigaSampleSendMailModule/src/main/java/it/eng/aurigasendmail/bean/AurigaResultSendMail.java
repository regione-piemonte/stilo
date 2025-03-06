package it.eng.aurigasendmail.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class AurigaResultSendMail implements Serializable {

	private boolean inviata;
	private List<String> errori;
	/**
	 * Specifica se la mail è stata inviata
	 * @return
	 */
	public boolean isInviata() {
		return inviata;
	}
	/**
	 * Specifica se la mail è stata inviata
	 * @param inviata
	 */
	public void setInviata(boolean inviata) {
		this.inviata = inviata;
	}
	/**
	 * Recupera gli errori in invio della mail
	 * @return
	 */
	public List<String> getErrori() {
		return errori;
	}
	/**
	 * Setta gli errori in invio della mail
	 * @param errori
	 */
	public void setErrori(List<String> errori) {
		this.errori = errori;
	}
}
