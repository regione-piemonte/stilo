/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

/**
 * 
 * @author dbe4235
 *
 */

@XmlRootElement
public class DestintarioUoProtocollazioneXmlBean implements Serializable {

	@NumeroColonna(numero ="1")
	private String idUO;
	
	@NumeroColonna(numero ="2")
	private String descrizione;

	public String getIdUO() {
		return idUO;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setIdUO(String idUO) {
		this.idUO = idUO;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}