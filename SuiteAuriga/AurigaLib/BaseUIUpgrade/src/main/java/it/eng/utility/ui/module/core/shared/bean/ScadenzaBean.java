/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class ScadenzaBean {

	private String tipoScadenza;
	private Integer entroGiorni;
	private Integer trascorsaDaGiorni;
	private Boolean soloSeTermineScadenzaNonAvvenuto;
	public String getTipoScadenza() {
		return tipoScadenza;
	}
	public void setTipoScadenza(String tipoScadenza) {
		this.tipoScadenza = tipoScadenza;
	}
	public Integer getEntroGiorni() {
		return entroGiorni;
	}
	public void setEntroGiorni(Integer entroGiorni) {
		this.entroGiorni = entroGiorni;
	}
	public Integer getTrascorsaDaGiorni() {
		return trascorsaDaGiorni;
	}
	public void setTrascorsaDaGiorni(Integer trascorsaDaGiorni) {
		this.trascorsaDaGiorni = trascorsaDaGiorni;
	}
	public Boolean getSoloSeTermineScadenzaNonAvvenuto() {
		return soloSeTermineScadenzaNonAvvenuto;
	}
	public void setSoloSeTermineScadenzaNonAvvenuto(
			Boolean soloSeTermineScadenzaNonAvvenuto) {
		this.soloSeTermineScadenzaNonAvvenuto = soloSeTermineScadenzaNonAvvenuto;
	}
	
	
}
