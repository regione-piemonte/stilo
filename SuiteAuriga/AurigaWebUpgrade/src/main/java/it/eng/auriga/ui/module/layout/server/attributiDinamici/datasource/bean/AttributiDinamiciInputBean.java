/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class AttributiDinamiciInputBean {

	private String nomeTabella;	
	private String tipoEntita;
	private String rowId;	
	private List<String> listaCategorie;
	
	public String getNomeTabella() {
		return nomeTabella;
	}
	public void setNomeTabella(String nomeTabella) {
		this.nomeTabella = nomeTabella;
	}
	public String getTipoEntita() {
		return tipoEntita;
	}
	public void setTipoEntita(String tipoEntita) {
		this.tipoEntita = tipoEntita;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public List<String> getListaCategorie() {
		return listaCategorie;
	}
	public void setListaCategorie(List<String> listaCategorie) {
		this.listaCategorie = listaCategorie;
	}
	
}
