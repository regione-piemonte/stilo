/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaCapitolo extends ContabiliaEntitaBase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer annoEsercizio;
	private List<ContabiliaClassificatoreGenerico> classificatoriGenerici;
	private String descrizione;
	private String descrizioneArticolo;
	private Integer numeroArticolo;
	private Integer numeroCapitolo;
	private Integer numeroUEB;
	private ContabiliaPianoDeiContiFinanziario pianoDeiContiFinanziario;
	private Boolean rilevanteIva;
	private ContabiliaStrutturaAmministrativa sac;
	private ContabiliaTipoFinanziamento tipoFinanziamento;
	private ContabiliaTipoFondo tipoFondo;
	private ContabiliaTitolo titolo;
	
	public Integer getAnnoEsercizio() {
		return annoEsercizio;
	}
	
	public void setAnnoEsercizio(Integer annoEsercizio) {
		this.annoEsercizio = annoEsercizio;
	}
	
	public List<ContabiliaClassificatoreGenerico> getClassificatoriGenerici() {
		return classificatoriGenerici;
	}
	
	public void setClassificatoriGenerici(List<ContabiliaClassificatoreGenerico> classificatoriGenerici) {
		this.classificatoriGenerici = classificatoriGenerici;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public String getDescrizioneArticolo() {
		return descrizioneArticolo;
	}
	
	public void setDescrizioneArticolo(String descrizioneArticolo) {
		this.descrizioneArticolo = descrizioneArticolo;
	}
	
	public Integer getNumeroArticolo() {
		return numeroArticolo;
	}
	
	public void setNumeroArticolo(Integer numeroArticolo) {
		this.numeroArticolo = numeroArticolo;
	}
	
	public Integer getNumeroCapitolo() {
		return numeroCapitolo;
	}
	
	public void setNumeroCapitolo(Integer numeroCapitolo) {
		this.numeroCapitolo = numeroCapitolo;
	}
	
	public Integer getNumeroUEB() {
		return numeroUEB;
	}
	
	public void setNumeroUEB(Integer numeroUEB) {
		this.numeroUEB = numeroUEB;
	}
	
	public ContabiliaPianoDeiContiFinanziario getPianoDeiContiFinanziario() {
		return pianoDeiContiFinanziario;
	}
	
	public void setPianoDeiContiFinanziario(ContabiliaPianoDeiContiFinanziario pianoDeiContiFinanziario) {
		this.pianoDeiContiFinanziario = pianoDeiContiFinanziario;
	}
	
	public Boolean getRilevanteIva() {
		return rilevanteIva;
	}
	
	public void setRilevanteIva(Boolean rilevanteIva) {
		this.rilevanteIva = rilevanteIva;
	}
	
	public ContabiliaStrutturaAmministrativa getSac() {
		return sac;
	}
	
	public void setSac(ContabiliaStrutturaAmministrativa sac) {
		this.sac = sac;
	}
	
	public ContabiliaTipoFinanziamento getTipoFinanziamento() {
		return tipoFinanziamento;
	}
	
	public void setTipoFinanziamento(ContabiliaTipoFinanziamento tipoFinanziamento) {
		this.tipoFinanziamento = tipoFinanziamento;
	}
	
	public ContabiliaTipoFondo getTipoFondo() {
		return tipoFondo;
	}
	
	public void setTipoFondo(ContabiliaTipoFondo tipoFondo) {
		this.tipoFondo = tipoFondo;
	}
	
	public ContabiliaTitolo getTitolo() {
		return titolo;
	}
	
	public void setTitolo(ContabiliaTitolo titolo) {
		this.titolo = titolo;
	}
	
	@Override
	public String toString() {
		return "ContabiliaCapitolo [annoEsercizio=" + annoEsercizio + ", classificatoriGenerici="
				+ classificatoriGenerici + ", descrizione=" + descrizione + ", descrizioneArticolo="
				+ descrizioneArticolo + ", numeroArticolo=" + numeroArticolo + ", numeroCapitolo=" + numeroCapitolo
				+ ", numeroUEB=" + numeroUEB + ", pianoDeiContiFinanziario=" + pianoDeiContiFinanziario
				+ ", rilevanteIva=" + rilevanteIva + ", sac=" + sac + ", tipoFinanziamento=" + tipoFinanziamento
				+ ", tipoFondo=" + tipoFondo + ", titolo=" + titolo + "]";
	}
	
}
