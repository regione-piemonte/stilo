package it.eng.dm.sira.service.bean;

import it.eng.dm.sira.service.search.GenericSearchServiceBeanIn;

public class SoggettoFisicoBeanIn extends GenericSearchServiceBeanIn {

	private String nome;

	private String cognome;
	
	private String codiceFiscale;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

}
