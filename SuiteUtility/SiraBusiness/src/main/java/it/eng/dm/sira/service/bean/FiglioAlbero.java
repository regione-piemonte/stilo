package it.eng.dm.sira.service.bean;

public class FiglioAlbero {

	private java.lang.Integer categoria;

	private java.lang.Integer idOst;

	private java.lang.Integer natura;

	private java.lang.Integer idCcost;

	private java.lang.Integer idUbicazione;

	private java.lang.String denominazione;

	public java.lang.Integer getCategoria() {
		return categoria;
	}

	public void setCategoria(java.lang.Integer categoria) {
		this.categoria = categoria;
	}

	public java.lang.Integer getIdOst() {
		return idOst;
	}

	public void setIdOst(java.lang.Integer idOst) {
		this.idOst = idOst;
	}

	public java.lang.Integer getNatura() {
		return natura;
	}

	public void setNatura(java.lang.Integer natura) {
		this.natura = natura;
	}

	public java.lang.String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(java.lang.String denominazione) {
		this.denominazione = denominazione;
	}

	public java.lang.Integer getIdCcost() {
		return idCcost;
	}

	public void setIdCcost(java.lang.Integer idCcost) {
		this.idCcost = idCcost;
	}

	public java.lang.Integer getIdUbicazione() {
		return idUbicazione;
	}

	public void setIdUbicazione(java.lang.Integer idUbicazione) {
		this.idUbicazione = idUbicazione;
	}

	public String toString() {
		return "idOst: " + idOst + " natura "+natura+" categoria: " + categoria + " denominazione: " + denominazione + " idCCost: " + idCcost
				+ " idUbicazione: " + idUbicazione;
	}

}
