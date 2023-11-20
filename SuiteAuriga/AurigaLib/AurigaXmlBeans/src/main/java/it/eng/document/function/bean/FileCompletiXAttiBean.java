/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class FileCompletiXAttiBean implements Serializable {
	
	@NumeroColonna(numero = "1")
	private String uri;
	@NumeroColonna(numero = "2")
	private String nomeFile;
	@NumeroColonna(numero = "3")
	private BigDecimal dimensione;
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public BigDecimal getDimensione() {
		return dimensione;
	}
	public void setDimensione(BigDecimal dimensione) {
		this.dimensione = dimensione;
	}
	
}