/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

import java.io.Serializable;

public class CausaleAggNoteBean implements Serializable {
	
	@NumeroColonna(numero = "1")
	private String causaleAggNote;

	public String getCausaleAggNote() {
		return causaleAggNote;
	}

	public void setCausaleAggNote(String causaleAggNote) {
		this.causaleAggNote = causaleAggNote;
	}

}
