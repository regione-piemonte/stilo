/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="datiAggiuntiviEmail")
public class RigaXMLTagTagEmail {

	@NumeroColonna(numero="1")
	@XmlElement(name="tag")
	private String tag;
	
	@NumeroColonna(numero="2")
	@XmlElement(name="note")
	private String note;
	
	@NumeroColonna(numero="3")
	@XmlElement(name="flagInibitaModificaECancellazione", defaultValue="0")
	private Integer flagInibitaModificaECancellazione;

	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	public Integer getFlagInibitaModificaECancellazione() {
		return flagInibitaModificaECancellazione;
	}
	public void setFlagInibitaModificaECancellazione(Integer flagInibitaModificaECancellazione) {
		this.flagInibitaModificaECancellazione = flagInibitaModificaECancellazione;
	}

}//RigaXMLTagTagEmail
