/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class AssegnatoNotificatoABean extends VisualBean {

	private String chiave;
	private String codice;
	private String descrizione;
	private String descrizioneOrig;
	private String typeNodo;
	private String tipoUo;
	private String iconaTypeNodo;

	public String getChiave() {
		return chiave;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizioneOrig() {
		return descrizioneOrig;
	}

	public void setDescrizioneOrig(String descrizioneOrig) {
		this.descrizioneOrig = descrizioneOrig;
	}

	public String getTypeNodo() {
		return typeNodo;
	}

	public void setTypeNodo(String typeNodo) {
		this.typeNodo = typeNodo;
	}

	public String getTipoUo() {
		return tipoUo;
	}

	public void setTipoUo(String tipoUo) {
		this.tipoUo = tipoUo;
	}

	public String getIconaTypeNodo() {
		return iconaTypeNodo;
	}

	public void setIconaTypeNodo(String iconaTypeNodo) {
		this.iconaTypeNodo = iconaTypeNodo;
	}

}
