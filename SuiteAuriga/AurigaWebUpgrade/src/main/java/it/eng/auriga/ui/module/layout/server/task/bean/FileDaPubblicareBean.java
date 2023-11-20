/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class FileDaPubblicareBean {

	@NumeroColonna(numero = "1")
	private String uri;
	@NumeroColonna(numero = "2")
	private String nomeFile;
	@NumeroColonna(numero = "3")
	private String daTimbrare;
	@NumeroColonna(numero = "4")
	private String idUd;
	@NumeroColonna(numero = "5")
	private String mimetype;

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

	public String getDaTimbrare() {
		return daTimbrare;
	}

	public void setDaTimbrare(String daTimbrare) {
		this.daTimbrare = daTimbrare;
	}

	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

}
