/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

public class AbstractFileIndice {

	private int numeroRiga;
	private List<String> erroriDatiObbligatori = new ArrayList<String>();
	private boolean datiObbligatoriValidi;

	public AbstractFileIndice() {
		super();
	}

	public void setNumeroRiga(int numeroRiga) {
		this.numeroRiga = numeroRiga;
	}
	public int getNumeroRiga() {
		return numeroRiga;
	}
	
	public void setErroriDatiObbligatori(List<String> erroriDatiObbligatori) {
		this.erroriDatiObbligatori = erroriDatiObbligatori;
	}
	public List<String> getErroriDatiObbligatori() {
		return erroriDatiObbligatori;
	}

	public void setDatiObbligatoriValidi(boolean datiObbligatoriValidi) {
		this.datiObbligatoriValidi = datiObbligatoriValidi;
	}
	public boolean isDatiObbligatoriValidi() {
		return datiObbligatoriValidi;
	}

}