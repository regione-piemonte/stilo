/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class TagNoteXmlBean {
	
	@NumeroColonna(numero = "1")
	private String tag;
	
	@NumeroColonna(numero = "2")
	private String note;
	
	@NumeroColonna(numero = "3")
	private Integer flgInibitaModificaCancellazione;

	
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
	
	public Integer getFlgInibitaModificaCancellazione() {
		return flgInibitaModificaCancellazione;
	}

	public void setFlgInibitaModificaCancellazione(Integer flgInibitaModificaCancellazione) {
		this.flgInibitaModificaCancellazione = flgInibitaModificaCancellazione;
	}

}
