package it.eng.dm.sira.service.procedimento;

import java.math.BigDecimal;

import it.eng.dm.sira.service.bean.TipoRicerca;

public class RicercaProcedimentoBeanIn {

	private TipoRicerca tipoRicerca;

	private String parolaChiave;

	private BigDecimal idArea;

	private BigDecimal idTipologia;

	private BigDecimal dominio;

	public TipoRicerca getTipoRicerca() {
		return tipoRicerca;
	}

	public void setTipoRicerca(TipoRicerca tipoRicerca) {
		this.tipoRicerca = tipoRicerca;
	}

	public String getParolaChiave() {
		return parolaChiave;
	}

	public void setParolaChiave(String parolaChiave) {
		this.parolaChiave = parolaChiave;
	}

	public BigDecimal getIdArea() {
		return idArea;
	}

	public void setIdArea(BigDecimal idArea) {
		this.idArea = idArea;
	}

	public BigDecimal getIdTipologia() {
		return idTipologia;
	}

	public void setIdTipologia(BigDecimal idTipologia) {
		this.idTipologia = idTipologia;
	}

	public BigDecimal getDominio() {
		return dominio;
	}

	public void setDominio(BigDecimal dominio) {
		this.dominio = dominio;
	}

}
