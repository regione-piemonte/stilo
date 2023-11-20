/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabilitaAdspDatiCapitoloResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String annoEsercizio;
	private String esercizioApprovato;
	private String capitoloParte1;
	private String capitoloParte2;
	private String descrizioneVoce;
	private Integer progkeyvb;
	private String liv1pf;
	private String liv2pf;
	private String liv3pf;
	private String liv4pf;
	private String liv5pf;
	private String descrizionePianoFin;
	private String missione;
	private String descrizioneMissione;
	private String programma;
	private String descrizioneProgramma;
	private String cofog1;
	private String cofog2;
	private String descrizioneCofog;
	private String assegnatarioPeg;
	private String assegnatarioPegCod;
	private String AssegnatarioAliasCdr;
	private String SommaDisponibile;
	private String sommaDisponibileProv;
	private String attribProgRicBilancio;
	private String desRicBilancio;
	private String dataInser;
	private String attribScheda;
	private String attribNote;
	
	private ContabilitaAdspDatiContabiliResponse datiContabili;
	
	public String getAnnoEsercizio() {
		return annoEsercizio;
	}
	
	public void setAnnoEsercizio(String annoEsercizio) {
		this.annoEsercizio = annoEsercizio;
	}
	
	public String getEsercizioApprovato() {
		return esercizioApprovato;
	}
	
	public void setEsercizioApprovato(String esercizioApprovato) {
		this.esercizioApprovato = esercizioApprovato;
	}
	
	public String getCapitoloParte1() {
		return capitoloParte1;
	}
	
	public void setCapitoloParte1(String capitoloParte1) {
		this.capitoloParte1 = capitoloParte1;
	}
	
	public String getCapitoloParte2() {
		return capitoloParte2;
	}
	
	public void setCapitoloParte2(String capitoloParte2) {
		this.capitoloParte2 = capitoloParte2;
	}
	
	public String getDescrizioneVoce() {
		return descrizioneVoce;
	}

	public void setDescrizioneVoce(String descrizioneVoce) {
		this.descrizioneVoce = descrizioneVoce;
	}

	public Integer getProgkeyvb() {
		return progkeyvb;
	}
	
	public void setProgkeyvb(Integer progkeyvb) {
		this.progkeyvb = progkeyvb;
	}
	
	public String getLiv1pf() {
		return liv1pf;
	}
	
	public void setLiv1pf(String liv1pf) {
		this.liv1pf = liv1pf;
	}
	
	public String getLiv2pf() {
		return liv2pf;
	}
	
	public void setLiv2pf(String liv2pf) {
		this.liv2pf = liv2pf;
	}
	
	public String getLiv3pf() {
		return liv3pf;
	}
	
	public void setLiv3pf(String liv3pf) {
		this.liv3pf = liv3pf;
	}
	
	public String getLiv4pf() {
		return liv4pf;
	}
	
	public void setLiv4pf(String liv4pf) {
		this.liv4pf = liv4pf;
	}
	
	public String getLiv5pf() {
		return liv5pf;
	}
	
	public void setLiv5pf(String liv5pf) {
		this.liv5pf = liv5pf;
	}
	
	public String getDescrizionePianoFin() {
		return descrizionePianoFin;
	}
	
	public void setDescrizionePianoFin(String descrizionePianoFin) {
		this.descrizionePianoFin = descrizionePianoFin;
	}
	
	public String getMissione() {
		return missione;
	}
	
	public void setMissione(String missione) {
		this.missione = missione;
	}
	
	public String getDescrizioneMissione() {
		return descrizioneMissione;
	}
	
	public void setDescrizioneMissione(String descrizioneMissione) {
		this.descrizioneMissione = descrizioneMissione;
	}
	
	public String getProgramma() {
		return programma;
	}
	
	public void setProgramma(String programma) {
		this.programma = programma;
	}
	
	public String getDescrizioneProgramma() {
		return descrizioneProgramma;
	}
	
	public void setDescrizioneProgramma(String descrizioneProgramma) {
		this.descrizioneProgramma = descrizioneProgramma;
	}
	
	public String getCofog1() {
		return cofog1;
	}
	
	public void setCofog1(String cofog1) {
		this.cofog1 = cofog1;
	}
	
	public String getCofog2() {
		return cofog2;
	}
	
	public void setCofog2(String cofog2) {
		this.cofog2 = cofog2;
	}
	
	public String getDescrizioneCofog() {
		return descrizioneCofog;
	}
	
	public void setDescrizioneCofog(String descrizioneCofog) {
		this.descrizioneCofog = descrizioneCofog;
	}
	
	public String getAssegnatarioPeg() {
		return assegnatarioPeg;
	}
	
	public void setAssegnatarioPeg(String assegnatarioPeg) {
		this.assegnatarioPeg = assegnatarioPeg;
	}
	
	public String getAssegnatarioPegCod() {
		return assegnatarioPegCod;
	}
	
	public void setAssegnatarioPegCod(String assegnatarioPegCod) {
		this.assegnatarioPegCod = assegnatarioPegCod;
	}
	
	public String getAssegnatarioAliasCdr() {
		return AssegnatarioAliasCdr;
	}
	
	public void setAssegnatarioAliasCdr(String assegnatarioAliasCdr) {
		AssegnatarioAliasCdr = assegnatarioAliasCdr;
	}
	
	public String getSommaDisponibile() {
		return SommaDisponibile;
	}
	
	public void setSommaDisponibile(String sommaDisponibile) {
		SommaDisponibile = sommaDisponibile;
	}
	
	public String getSommaDisponibileProv() {
		return sommaDisponibileProv;
	}

	public void setSommaDisponibileProv(String sommaDisponibileProv) {
		this.sommaDisponibileProv = sommaDisponibileProv;
	}

	public ContabilitaAdspDatiContabiliResponse getDatiContabili() {
		return datiContabili;
	}

	public void setDatiContabili(ContabilitaAdspDatiContabiliResponse datiContabili) {
		this.datiContabili = datiContabili;
	}
	
	public String getAttribProgRicBilancio() {
		return attribProgRicBilancio;
	}

	public void setAttribProgRicBilancio(String attribProgRicBilancio) {
		this.attribProgRicBilancio = attribProgRicBilancio;
	}

	public String getDesRicBilancio() {
		return desRicBilancio;
	}

	public void setDesRicBilancio(String desRicBilancio) {
		this.desRicBilancio = desRicBilancio;
	}

	public String getDataInser() {
		return dataInser;
	}

	public void setDataInser(String dataInser) {
		this.dataInser = dataInser;
	}

	public String getAttribScheda() {
		return attribScheda;
	}

	public void setAttribScheda(String attribScheda) {
		this.attribScheda = attribScheda;
	}

	public String getAttribNote() {
		return attribNote;
	}

	public void setAttribNote(String attribNote) {
		this.attribNote = attribNote;
	}
	
	@Override
	public String toString() {
		return "ContabilitaAdspDatiCapitoloResponse [annoEsercizio=" + annoEsercizio + ", esercizioApprovato="
				+ esercizioApprovato + ", capitoloParte1=" + capitoloParte1 + ", capitoloParte2=" + capitoloParte2
				+ ", descrizioneVoce=" + descrizioneVoce + ", progkeyvb=" + progkeyvb + ", liv1pf=" + liv1pf
				+ ", liv2pf=" + liv2pf + ", liv3pf=" + liv3pf + ", liv4pf=" + liv4pf + ", liv5pf=" + liv5pf
				+ ", descrizionePianoFin=" + descrizionePianoFin + ", missione=" + missione + ", descrizioneMissione="
				+ descrizioneMissione + ", programma=" + programma + ", descrizioneProgramma=" + descrizioneProgramma
				+ ", cofog1=" + cofog1 + ", cofog2=" + cofog2 + ", descrizioneCofog=" + descrizioneCofog
				+ ", assegnatarioPeg=" + assegnatarioPeg + ", assegnatarioPegCod=" + assegnatarioPegCod
				+ ", AssegnatarioAliasCdr=" + AssegnatarioAliasCdr + ", SommaDisponibile=" + SommaDisponibile
				+ ", sommaDisponibileProv=" + sommaDisponibileProv + ", attribProgRicBilancio=" + attribProgRicBilancio
				+ ", desRicBilancio=" + desRicBilancio + ", dataInser=" + dataInser + ", attribScheda=" + attribScheda
				+ ", attribNote=" + attribNote + ", datiContabili=" + datiContabili + "]";
	}
	
}
