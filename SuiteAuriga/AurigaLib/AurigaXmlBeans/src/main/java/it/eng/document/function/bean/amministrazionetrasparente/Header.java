/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "header")
public class Header implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9006078264786342914L;

	private String titolo;
	
	private List<String> rifNormartivi;
	
	private List<String> contenuti;
	
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public List<String> getRifNormartivi() {
		return rifNormartivi;
	}
	public void setRifNormartivi(List<String> rifNormartivi) {
		this.rifNormartivi = rifNormartivi;
	}
	public List<String> getContenuti() {
		return contenuti;
	}
	public void setContenuti(List<String> contenuti) {
		this.contenuti = contenuti;
	}
	
}
