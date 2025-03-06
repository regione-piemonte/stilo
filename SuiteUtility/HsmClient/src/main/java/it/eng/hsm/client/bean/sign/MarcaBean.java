package it.eng.hsm.client.bean.sign;

import it.eng.hsm.client.bean.MessageBean;

/**
 * 
 * @author DANCRIST
 *
 */

public class MarcaBean {
	
	private MessageBean message;
	/*
	 * tsAuthority – TimeStamp Authority se presente
	 */
	private String tsAuthority;
	/*
	 * tsLenght – durata della eventuale marca temporale
	 */
	private String tsLenght;
	/*
	 * timestamp – boolean indicante se è presente la marca temporale
	 */
	private Boolean isPresente;
	/*
	 * validTimestamp – se l'eventuale marca temporale è valida e non scaduta
	 */
	private Boolean isValida;
	
	public MessageBean getMessage() {
		return message;
	}
	public void setMessage(MessageBean message) {
		this.message = message;
	}
	public String getTsAuthority() {
		return tsAuthority;
	}
	public void setTsAuthority(String tsAuthority) {
		this.tsAuthority = tsAuthority;
	}
	public String getTsLenght() {
		return tsLenght;
	}
	public void setTsLenght(String tsLenght) {
		this.tsLenght = tsLenght;
	}
	public Boolean getIsPresente() {
		return isPresente;
	}
	public void setIsPresente(Boolean isPresente) {
		this.isPresente = isPresente;
	}
	public Boolean getIsValida() {
		return isValida;
	}
	public void setIsValida(Boolean isValida) {
		this.isValida = isValida;
	}
}