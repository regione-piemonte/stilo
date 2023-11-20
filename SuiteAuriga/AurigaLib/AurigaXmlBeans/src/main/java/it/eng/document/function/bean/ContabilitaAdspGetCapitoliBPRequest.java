/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabilitaAdspGetCapitoliBPRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String token;
	private String codiceFiscaleOp;
	private String annoEsercizio;
	private String annoRiferimento;
	private String movimento;
	private String capitolo1;
	private String capitolo2;
	private String descrizioneVoce;
	private String tipoRicercaVoce;
	private String liv1pf;
	private String liv2pf;
	private String liv3pf;
	private String liv4pf;
	private String liv5pf;
	private String missione;
	private String programma;
	private String aliasCDR;
	private Double importoRichiesto;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCodiceFiscaleOp() {
		return codiceFiscaleOp;
	}
	
	public void setCodiceFiscaleOp(String codiceFiscaleOp) {
		this.codiceFiscaleOp = codiceFiscaleOp;
	}
	
	public String getAnnoEsercizio() {
		return annoEsercizio;
	}
	
	public void setAnnoEsercizio(String annoEsercizio) {
		this.annoEsercizio = annoEsercizio;
	}
	
	public String getAnnoRiferimento() {
		return annoRiferimento;
	}
	
	public void setAnnoRiferimento(String annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}
	
	public String getMovimento() {
		return movimento;
	}
	
	public void setMovimento(String movimento) {
		this.movimento = movimento;
	}
	
	public String getCapitolo1() {
		return capitolo1;
	}
	
	public void setCapitolo1(String capitolo1) {
		this.capitolo1 = capitolo1;
	}
	
	public String getCapitolo2() {
		return capitolo2;
	}
	
	public void setCapitolo2(String capitolo2) {
		this.capitolo2 = capitolo2;
	}
	
	public String getDescrizioneVoce() {
		return descrizioneVoce;
	}
	
	public void setDescrizioneVoce(String descrizioneVoce) {
		this.descrizioneVoce = descrizioneVoce;
	}
	
	public String getTipoRicercaVoce() {
		return tipoRicercaVoce;
	}
	
	public void setTipoRicercaVoce(String tipoRicercaVoce) {
		this.tipoRicercaVoce = tipoRicercaVoce;
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
	
	public String getMissione() {
		return missione;
	}
	
	public void setMissione(String missione) {
		this.missione = missione;
	}
	
	public String getProgramma() {
		return programma;
	}
	
	public void setProgramma(String programma) {
		this.programma = programma;
	}
	
	public String getAliasCDR() {
		return aliasCDR;
	}
	
	public void setAliasCDR(String aliasCDR) {
		this.aliasCDR = aliasCDR;
	}

	public Double getImportoRichiesto() {
		return importoRichiesto;
	}

	public void setImportoRichiesto(Double importoRichiesto) {
		this.importoRichiesto = importoRichiesto;
	}

	@Override
	public String toString() {
		return "ContabilitaAdspGetCapitoliBPRequest [token=" + token + ", codiceFiscaleOp=" + codiceFiscaleOp
				+ ", annoEsercizio=" + annoEsercizio + ", annoRiferimento=" + annoRiferimento + ", movimento="
				+ movimento + ", capitolo1=" + capitolo1 + ", capitolo2=" + capitolo2 + ", descrizioneVoce="
				+ descrizioneVoce + ", tipoRicercaVoce=" + tipoRicercaVoce + ", liv1pf=" + liv1pf + ", liv2pf=" + liv2pf
				+ ", liv3pf=" + liv3pf + ", liv4pf=" + liv4pf + ", liv5pf=" + liv5pf + ", missione=" + missione
				+ ", programma=" + programma + ", aliasCDR=" + aliasCDR + ", importoRichiesto=" + importoRichiesto
				+ "]";
	}
	
}
