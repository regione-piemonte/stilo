/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.common.OperazioneMassivaBean;

import java.io.Serializable;
import java.util.List;

public class StatoRichOpMassiveBean extends OperazioneMassivaBean<StatoRichOpMassiveBean> implements Serializable {

	private static final long serialVersionUID = -6719820631431117639L;

	private List<String> idRichiesteList;	
	private String stato;
	private String note;
	private String storeErrorMessage;
	private boolean storeInError;

	
	public String getStoreErrorMessage() {
		return storeErrorMessage;
	}

	public void setStoreErrorMessage(String storeErrorMessage) {
		this.storeErrorMessage = storeErrorMessage;
	}

	public boolean isStoreInError() {
		return storeInError;
	}

	public void setStoreInError(boolean storeInError) {
		this.storeInError = storeInError;
	}

	public List<String> getIdRichiesteList() {
		return idRichiesteList;
	}

	public void setIdRichiesteList(List<String> idRichiesteList) {
		this.idRichiesteList = idRichiesteList;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
