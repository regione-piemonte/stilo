package it.eng.dm.sira.service.bean;

import java.util.Calendar;
import java.util.List;

public class InfoAnagraficheULBean {
	
	private String denominazione;

	private List<String> codAteco;

	private String numeroAddetti;

	private String telefono;

	private String fax;

	private String indrizzo;

	private String cap;

	private String localita;

	private String comune;
	
	private String numeroCivico;

	private String numeroRegistroImprese;

	private Calendar dataRegistroImprese;

	private String provRegistroImprese;

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public List<String> getCodAteco() {
		return codAteco;
	}

	public void setCodAteco(List<String> codAteco) {
		this.codAteco = codAteco;
	}

	public String getNumeroAddetti() {
		return numeroAddetti;
	}

	public void setNumeroAddetti(String numeroAddetti) {
		this.numeroAddetti = numeroAddetti;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getIndrizzo() {
		return indrizzo;
	}

	public void setIndrizzo(String indrizzo) {
		this.indrizzo = indrizzo;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getLocalita() {
		return localita;
	}

	public void setLocalita(String localita) {
		this.localita = localita;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getNumeroRegistroImprese() {
		return numeroRegistroImprese;
	}

	public void setNumeroRegistroImprese(String numeroRegistroImprese) {
		this.numeroRegistroImprese = numeroRegistroImprese;
	}

	public Calendar getDataRegistroImprese() {
		return dataRegistroImprese;
	}

	public void setDataRegistroImprese(Calendar dataRegistroImprese) {
		this.dataRegistroImprese = dataRegistroImprese;
	}

	public String getProvRegistroImprese() {
		return provRegistroImprese;
	}

	public void setProvRegistroImprese(String provRegistroImprese) {
		this.provRegistroImprese = provRegistroImprese;
	}

	public String getNumeroCivico() {
		return numeroCivico;
	}

	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}

}
