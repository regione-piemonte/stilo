/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import it.eng.document.NumeroColonna;

/**
 * @author Antonio Peluso
 */

public class FileAgibilitaOutBean {
	
	@NumeroColonna(numero = "1")
	private String nomeFile;
	
	@NumeroColonna(numero = "2")
	private String uri;
	
	@NumeroColonna(numero = "3")
	private String mimetype;
	
	@NumeroColonna(numero = "4")
	private BigDecimal dimensione;
	
	@NumeroColonna(numero = "5")
	private String idUD;

	public String getIdUD() {
		return idUD;
	}

	public void setIdUD(String idUD) {
		this.idUD = idUD;
	}

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

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public BigDecimal getDimensione() {
		return dimensione;
	}

	public void setDimensione(BigDecimal dimensione) {
		this.dimensione = dimensione;
	}
	
	

}
