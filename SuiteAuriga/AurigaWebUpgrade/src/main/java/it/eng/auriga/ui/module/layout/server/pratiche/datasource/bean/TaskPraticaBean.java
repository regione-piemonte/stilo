/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class TaskPraticaBean {

	@NumeroColonna(numero = "1")
	private String idGUIEvento;
	@NumeroColonna(numero = "2")
	private String idTipoEvento;	
	@NumeroColonna(numero = "3")
	private String rowId;	
	@NumeroColonna(numero = "6")
	private String idEvento;
	@NumeroColonna(numero = "4")
	private String flgFatta;
	@NumeroColonna(numero = "5")
	private String flgDisponibile;
	
	private String fase;
	private String displayName;
	private String dettagli;

	public String getIdGUIEvento() {
		return idGUIEvento;
	}
	public void setIdGUIEvento(String idGUIEvento) {
		this.idGUIEvento = idGUIEvento;
	}
	public String getIdTipoEvento() {
		return idTipoEvento;
	}
	public void setIdTipoEvento(String idTipoEvento) {
		this.idTipoEvento = idTipoEvento;
	}
	public String getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(String idEvento) {
		this.idEvento = idEvento;
	}
	public String getFase() {
		return fase;
	}
	public void setFase(String fase) {
		this.fase = fase;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getFlgFatta() {
		return flgFatta;
	}
	public void setFlgFatta(String flgFatta) {
		this.flgFatta = flgFatta;
	}
	public String getFlgDisponibile() {
		return flgDisponibile;
	}
	public void setFlgDisponibile(String flgDisponibile) {
		this.flgDisponibile = flgDisponibile;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getDettagli() {
		return dettagli;
	}
	public void setDettagli(String dettagli) {
		this.dettagli = dettagli;
	}	
	
}
