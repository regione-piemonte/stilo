/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PubblicazioneNotificaEsitoRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer annoPubblicazione;
	private String cfOperatore;
	private String dataFinePubblicazione;
	private List<String> elencoPraticheConcorrenti;
	private Long giorniTermineProcedimento;
	private String idDocAvvio;
	private String idUD;
	private String idUDAvvio;
	private boolean modificatiTermini;
	private List<PubblicazioneListaAllegatiAvvio> listaAllegatiAvvio;
	private String nomeFileDocAvvio;
	private String numeroPubblicazione;
	private String tipoAvvio;
	private String tipoDocAvvio;
	
	public Integer getAnnoPubblicazione() {
		return annoPubblicazione;
	}
	
	public void setAnnoPubblicazione(Integer annoPubblicazione) {
		this.annoPubblicazione = annoPubblicazione;
	}
	
	public String getCfOperatore() {
		return cfOperatore;
	}
	
	public void setCfOperatore(String cfOperatore) {
		this.cfOperatore = cfOperatore;
	}
	
	public String getDataFinePubblicazione() {
		return dataFinePubblicazione;
	}
	
	public void setDataFinePubblicazione(String dataFinePubblicazione) {
		this.dataFinePubblicazione = dataFinePubblicazione;
	}
	
	public List<String> getElencoPraticheConcorrenti() {
		return elencoPraticheConcorrenti;
	}
	
	public void setElencoPraticheConcorrenti(List<String> elencoPraticheConcorrenti) {
		this.elencoPraticheConcorrenti = elencoPraticheConcorrenti;
	}
	
	public Long getGiorniTermineProcedimento() {
		return giorniTermineProcedimento;
	}
	
	public void setGiorniTermineProcedimento(Long giorniTermineProcedimento) {
		this.giorniTermineProcedimento = giorniTermineProcedimento;
	}
	
	public String getIdDocAvvio() {
		return idDocAvvio;
	}
	
	public void setIdDocAvvio(String idDocAvvio) {
		this.idDocAvvio = idDocAvvio;
	}
	
	public String getIdUD() {
		return idUD;
	}
	
	public void setIdUD(String idUD) {
		this.idUD = idUD;
	}
	
	public String getIdUDAvvio() {
		return idUDAvvio;
	}
	
	public void setIdUDAvvio(String idUDAvvio) {
		this.idUDAvvio = idUDAvvio;
	}
	
	public boolean isModificatiTermini() {
		return modificatiTermini;
	}

	public void setModificatiTermini(boolean modificatiTermini) {
		this.modificatiTermini = modificatiTermini;
	}

	public List<PubblicazioneListaAllegatiAvvio> getListaAllegatiAvvio() {
		return listaAllegatiAvvio;
	}
	
	public void setListaAllegatiAvvio(List<PubblicazioneListaAllegatiAvvio> listaAllegatiAvvio) {
		this.listaAllegatiAvvio = listaAllegatiAvvio;
	}
	
	public String getNomeFileDocAvvio() {
		return nomeFileDocAvvio;
	}
	
	public void setNomeFileDocAvvio(String nomeFileDocAvvio) {
		this.nomeFileDocAvvio = nomeFileDocAvvio;
	}
	
	public String getNumeroPubblicazione() {
		return numeroPubblicazione;
	}
	
	public void setNumeroPubblicazione(String numeroPubblicazione) {
		this.numeroPubblicazione = numeroPubblicazione;
	}
	
	public String getTipoAvvio() {
		return tipoAvvio;
	}
	
	public void setTipoAvvio(String tipoAvvio) {
		this.tipoAvvio = tipoAvvio;
	}
	
	public String getTipoDocAvvio() {
		return tipoDocAvvio;
	}
	
	public void setTipoDocAvvio(String tipoDocAvvio) {
		this.tipoDocAvvio = tipoDocAvvio;
	}

	@Override
	public String toString() {
		return "PubblicazioneNotificaEsitoRequest [annoPubblicazione=" + annoPubblicazione + ", cfOperatore="
				+ cfOperatore + ", dataFinePubblicazione=" + dataFinePubblicazione + ", elencoPraticheConcorrenti="
				+ elencoPraticheConcorrenti + ", giorniTermineProcedimento=" + giorniTermineProcedimento
				+ ", idDocAvvio=" + idDocAvvio + ", idUD=" + idUD + ", idUDAvvio=" + idUDAvvio + ", modificatiTermini="
				+ modificatiTermini + ", listaAllegatiAvvio=" + listaAllegatiAvvio + ", nomeFileDocAvvio="
				+ nomeFileDocAvvio + ", numeroPubblicazione=" + numeroPubblicazione + ", tipoAvvio=" + tipoAvvio
				+ ", tipoDocAvvio=" + tipoDocAvvio + "]";
	}
	
}
