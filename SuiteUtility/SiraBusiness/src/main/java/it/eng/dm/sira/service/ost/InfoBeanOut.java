package it.eng.dm.sira.service.ost;

import com.hyperborea.sira.ws.OggettiStruttureTerritoriali;

public class InfoBeanOut {

	private Integer categoria;
	
	private String descCategoria;

	private String descNatura;

	private Integer natura;

	private OggettiStruttureTerritoriali oggetto;

	private Integer figli;
	
	private String denominazione;
	
	private String idUbicazione;

	private Integer idCCost;
	
	private Integer idOst;

	public Integer getCategoria() {
		return categoria;
	}

	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}

	public Integer getNatura() {
		return natura;
	}

	public void setNatura(Integer natura) {
		this.natura = natura;
	}

	public OggettiStruttureTerritoriali getOggetto() {
		return oggetto;
	}

	public void setOggetto(OggettiStruttureTerritoriali oggetto) {
		this.oggetto = oggetto;
	}

	public Integer getFigli() {
		return figli;
	}

	public void setFigli(Integer figli) {
		this.figli = figli;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getIdUbicazione() {
		return idUbicazione;
	}

	public void setIdUbicazione(String idUbicazione) {
		this.idUbicazione = idUbicazione;
	}

	public Integer getIdCCost() {
		return idCCost;
	}

	public void setIdCCost(Integer idCCost) {
		this.idCCost = idCCost;
	}

	public Integer getIdOst() {
		return idOst;
	}

	public void setIdOst(Integer idOst) {
		this.idOst = idOst;
	}

	public String getDescCategoria() {
		return descCategoria;
	}

	public void setDescCategoria(String descCategoria) {
		this.descCategoria = descCategoria;
	}

	public String getDescNatura() {
		return descNatura;
	}

	public void setDescNatura(String descNatura) {
		this.descNatura = descNatura;
	}

}
