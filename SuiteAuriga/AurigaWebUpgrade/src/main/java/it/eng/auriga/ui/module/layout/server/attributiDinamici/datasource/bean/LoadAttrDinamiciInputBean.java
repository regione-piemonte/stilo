/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class LoadAttrDinamiciInputBean {

	private String nomeTabella;	
	private String rowId;
	private String tipoEntita;
	private String listaTipiReg;
	private String attrValuesXml;
	private Integer flgMostraTutti;
	private Integer flgNomeAttrConSuff;
	
	public String getNomeTabella() {
		return nomeTabella;
	}
	public void setNomeTabella(String nomeTabella) {
		this.nomeTabella = nomeTabella;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getTipoEntita() {
		return tipoEntita;
	}
	public void setTipoEntita(String tipoEntita) {
		this.tipoEntita = tipoEntita;
	}
	public String getListaTipiReg() {
		return listaTipiReg;
	}
	public void setListaTipiReg(String listaTipiReg) {
		this.listaTipiReg = listaTipiReg;
	}
	public String getAttrValuesXml() {
		return attrValuesXml;
	}
	public void setAttrValuesXml(String attrValuesXml) {
		this.attrValuesXml = attrValuesXml;
	}
	public Integer getFlgMostraTutti() {
		return flgMostraTutti;
	}
	public void setFlgMostraTutti(Integer flgMostraTutti) {
		this.flgMostraTutti = flgMostraTutti;
	}
	public Integer getFlgNomeAttrConSuff() {
		return flgNomeAttrConSuff;
	}
	public void setFlgNomeAttrConSuff(Integer flgNomeAttrConSuff) {
		this.flgNomeAttrConSuff = flgNomeAttrConSuff;
	}	
	
}
