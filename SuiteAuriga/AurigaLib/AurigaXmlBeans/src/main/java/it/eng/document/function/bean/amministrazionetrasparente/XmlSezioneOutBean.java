/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class XmlSezioneOutBean {

	@NumeroColonna(numero = "1")
	private String id;
	
	@NumeroColonna(numero = "2")
	private String nomeSezione;
	
	@NumeroColonna(numero = "3")
	private String idPadre;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNomeSezione() {
		return nomeSezione;
	}

	public void setNomeSezione(String nomeSezione) {
		this.nomeSezione = nomeSezione;
	}

	public String getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}
	
}
