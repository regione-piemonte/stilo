package it.eng.dm.sira.service.search;

public class GenericSearchServiceBeanIn {
	
	private Integer categoria;

	private Integer natura;
	
	private String denominazione;
	
	private String comune;
	
	private String provincia;
	
	private String localita;
	
	private String partitaIva;

	
	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getLocalita() {
		return localita;
	}

	public void setLocalita(String localita) {
		this.localita = localita;
	}

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

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}


}
