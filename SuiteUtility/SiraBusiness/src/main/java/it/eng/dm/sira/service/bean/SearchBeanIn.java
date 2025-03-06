package it.eng.dm.sira.service.bean;

import it.eng.core.business.beans.AbstractBean;

public class SearchBeanIn extends AbstractBean {
	
	private static final long serialVersionUID = 5643232877998229971L;

	private String denominazione;
	
	private String comune;

	private String provincia;

	private String localita;
	
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

}
