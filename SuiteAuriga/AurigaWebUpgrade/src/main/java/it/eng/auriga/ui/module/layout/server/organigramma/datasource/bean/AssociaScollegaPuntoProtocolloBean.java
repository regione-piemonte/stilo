/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.common.OperazioneMassivaBean;

import java.io.Serializable;
import java.util.List;

public class AssociaScollegaPuntoProtocolloBean extends OperazioneMassivaBean<AssociaScollegaPuntoProtocolloBean> implements Serializable {

	private static final long serialVersionUID = -6719820631431117639L;

	private List<String> idUoList;
	private List<String> idUoPuntiProtocolloList;
	private String storeErrorMessage;
	private boolean storeInError;

	public List<String> getIdUoList() {
		return idUoList;
	}

	public void setIdUoList(List<String> idUoList) {
		this.idUoList = idUoList;
	}

	public List<String> getIdUoPuntiProtocolloList() {
		return idUoPuntiProtocolloList;
	}

	public void setIdUoPuntiProtocolloList(List<String> idUoPuntiProtocolloList) {
		this.idUoPuntiProtocolloList = idUoPuntiProtocolloList;
	}

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

}
