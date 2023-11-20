/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class ControlloXEsitoTaskDocBean {

	@NumeroColonna(numero = "1")
	private String esito;

	@NumeroColonna(numero = "2")
	private String flgObbligatorio;

	@NumeroColonna(numero = "3")
	private String flgFileObbligatorio;

	@NumeroColonna(numero = "4")
	private String flgFileFirmato;

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public String getFlgObbligatorio() {
		return flgObbligatorio;
	}

	public void setFlgObbligatorio(String flgObbligatorio) {
		this.flgObbligatorio = flgObbligatorio;
	}

	public String getFlgFileObbligatorio() {
		return flgFileObbligatorio;
	}

	public void setFlgFileObbligatorio(String flgFileObbligatorio) {
		this.flgFileObbligatorio = flgFileObbligatorio;
	}

	public String getFlgFileFirmato() {
		return flgFileFirmato;
	}

	public void setFlgFileFirmato(String flgFileFirmato) {
		this.flgFileFirmato = flgFileFirmato;
	}

}