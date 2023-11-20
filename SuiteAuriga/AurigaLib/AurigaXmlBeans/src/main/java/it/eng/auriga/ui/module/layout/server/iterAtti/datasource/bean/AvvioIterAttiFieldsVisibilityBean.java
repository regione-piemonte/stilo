/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Set;

/**
 * Bean di configurazione che permette di definire la visibilità dei campi nella maschera di avvio iter, per i diversi tipi di iter
 * @author massimo malvestio
 *
 */
public class AvvioIterAttiFieldsVisibilityBean {

	//identificativo del tipo di atto
	private Integer tipoAttoId;
	
	//i campi presenti nel set non devono essere visibili
	private Set<String> hiddenFields;
	
	public Integer getTipoAttoId() {
		return tipoAttoId;
	}

	public void setTipoAttoId(Integer attoId) {
		this.tipoAttoId = attoId;
	}

	public Set<String> getHiddenFields() {
		return hiddenFields;
	}

	public void setHiddenFields(Set<String> hiddenFields) {
		this.hiddenFields = hiddenFields;
	}

}
