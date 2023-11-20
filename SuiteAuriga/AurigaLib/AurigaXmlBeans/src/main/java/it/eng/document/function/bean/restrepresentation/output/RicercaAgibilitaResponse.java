/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.function.bean.restrepresentation.ElencoAgibilita;
import it.eng.document.function.bean.restrepresentation.Errore;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ricercaAgibilitaResponse")
public class RicercaAgibilitaResponse implements Serializable {

	private static long serialVersionUID = 1L;
	@XmlElement(required = true)
	private String esito;
	private Errore errore;
	private String protocollo;
	private ElencoAgibilita elencoAgibilita;
	private String pathServiceFile;

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public Errore getErrore() {
		return errore;
	}

	public void setErrore(Errore errore) {
		this.errore = errore;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public ElencoAgibilita getElencoAgibilita() {
		return elencoAgibilita;
	}

	public void setElencoAgibilita(ElencoAgibilita elencoAgibilita) {
		this.elencoAgibilita = elencoAgibilita;
	}

	public String getPathServiceFile() {
		return pathServiceFile;
	}

	public void setPathServiceFile(String pathServiceFile) {
		this.pathServiceFile = pathServiceFile;
	}
}
