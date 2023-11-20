/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class EsistiVisionatoXmlBean {

	@NumeroColonna(numero = "1")
	private String idUdFolder;

	@NumeroColonna(numero = "2")
	private String estremiUdFolder;

	@NumeroColonna(numero = "3")
	private String esito;

	@NumeroColonna(numero = "4")
	private String messaggioErrore;

	public String getIdUdFolder() {
		return idUdFolder;
	}

	public void setIdUdFolder(String idUdFolder) {
		this.idUdFolder = idUdFolder;
	}

	public String getEstremiUdFolder() {
		return estremiUdFolder;
	}

	public void setEstremiUdFolder(String estremiUdFolder) {
		this.estremiUdFolder = estremiUdFolder;
	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public String getMessaggioErrore() {
		return messaggioErrore;
	}

	public void setMessaggioErrore(String messaggioErrore) {
		this.messaggioErrore = messaggioErrore;
	}

	
}