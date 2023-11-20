/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

public class PeriziaBean {

	private String codiceRapido;
	private String perizia;
	private String descrizione;
	
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCodiceRapido() {
		return codiceRapido;
	}

	public void setCodiceRapido(String codiceRapido) {
		this.codiceRapido = codiceRapido;
	}

	public String getPerizia() {
		return perizia;
	}

	public void setPerizia(String perizia) {
		this.perizia = perizia;
	}

}
