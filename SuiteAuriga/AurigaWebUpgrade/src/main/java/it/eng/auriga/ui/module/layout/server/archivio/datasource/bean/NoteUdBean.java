/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.util.List;

public class NoteUdBean {
	
	@XmlVariabile(nome="#NoteUD", tipo = TipoVariabile.SEMPLICE)
	private String noteUd;
 
    @XmlVariabile(nome="CAUSALE_AGG_NOTE_Ud", tipo = TipoVariabile.LISTA)
	private List<CausaleAggNoteBean> listaCausaleAggNote;

	public String getNoteUd() {
		return noteUd;
	}

	public void setNoteUd(String noteUd) {
		this.noteUd = noteUd;
	}

	public List<CausaleAggNoteBean> getListaCausaleAggNote() {
		return listaCausaleAggNote;
	}

	public void setListaCausaleAggNote(List<CausaleAggNoteBean> listaCausaleAggNote) {
		this.listaCausaleAggNote = listaCausaleAggNote;
	}
  	
}
