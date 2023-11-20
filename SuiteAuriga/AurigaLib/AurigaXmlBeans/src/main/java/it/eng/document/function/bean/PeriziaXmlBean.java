/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PeriziaXmlBean implements Serializable {

	@NumeroColonna(numero = "1")
	private String perizia;

	@NumeroColonna(numero = "2")
	private String descrizione;

	public String getPerizia() {
		return perizia;
	}

	public void setPerizia(String perizia) {
		this.perizia = perizia;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
