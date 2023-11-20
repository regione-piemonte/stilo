/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class ContenutoDocumentoParam implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String tipoVariazione;
	private String ente;
	private String annoAttoChiave;
	private String numeroAttoChiave;
	private String tipoAttoChiave;
	private String direzioneChiave;
	private String centroCostoChiave;
	private String annoAtto;
	private String numeroAtto;
	private String tipoAtto;
	private String direzione;
	private String centroCosto;
	private String dataCreazione;
	private String dataProposta;
	private String dataApprovazione;
	private String dataEsecutivita;
	private String statoOperativo;
	private String oggetto;
	private String note;
	private String identificativo;
	private String dirigenteResponsabile;
	private String trasparenza;
	private String codiceBlocco;
	
	public String getTipoVariazione() {
		return tipoVariazione;
	}
	
	public void setTipoVariazione(String tipoVariazione) {
		this.tipoVariazione = tipoVariazione;
	}
	
	public String getEnte() {
		return ente;
	}
	
	public void setEnte(String ente) {
		this.ente = ente;
	}
	
	public String getAnnoAttoChiave() {
		return annoAttoChiave;
	}
	
	public void setAnnoAttoChiave(String annoAttoChiave) {
		this.annoAttoChiave = annoAttoChiave;
	}
	
	public String getNumeroAttoChiave() {
		return numeroAttoChiave;
	}
	
	public void setNumeroAttoChiave(String numeroAttoChiave) {
		this.numeroAttoChiave = numeroAttoChiave;
	}
	
	public String getTipoAttoChiave() {
		return tipoAttoChiave;
	}
	
	public void setTipoAttoChiave(String tipoAttoChiave) {
		this.tipoAttoChiave = tipoAttoChiave;
	}
	
	public String getDirezioneChiave() {
		return direzioneChiave;
	}
	
	public void setDirezioneChiave(String direzioneChiave) {
		this.direzioneChiave = direzioneChiave;
	}
	
	public String getCentroCostoChiave() {
		return centroCostoChiave;
	}
	
	public void setCentroCostoChiave(String centroCostoChiave) {
		this.centroCostoChiave = centroCostoChiave;
	}
	
	public String getAnnoAtto() {
		return annoAtto;
	}
	
	public void setAnnoAtto(String annoAtto) {
		this.annoAtto = annoAtto;
	}
	
	public String getNumeroAtto() {
		return numeroAtto;
	}
	
	public void setNumeroAtto(String numeroAtto) {
		this.numeroAtto = numeroAtto;
	}
	
	public String getTipoAtto() {
		return tipoAtto;
	}
	
	public void setTipoAtto(String tipoAtto) {
		this.tipoAtto = tipoAtto;
	}
	
	public String getDirezione() {
		return direzione;
	}
	
	public void setDirezione(String direzione) {
		this.direzione = direzione;
	}
	
	public String getCentroCosto() {
		return centroCosto;
	}
	
	public void setCentroCosto(String centroCosto) {
		this.centroCosto = centroCosto;
	}
	
	public String getDataCreazione() {
		return dataCreazione;
	}
	
	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	
	public String getDataProposta() {
		return dataProposta;
	}
	
	public void setDataProposta(String dataProposta) {
		this.dataProposta = dataProposta;
	}
	
	public String getDataApprovazione() {
		return dataApprovazione;
	}
	
	public void setDataApprovazione(String dataApprovazione) {
		this.dataApprovazione = dataApprovazione;
	}
	
	public String getDataEsecutivita() {
		return dataEsecutivita;
	}
	
	public void setDataEsecutivita(String dataEsecutivita) {
		this.dataEsecutivita = dataEsecutivita;
	}
	
	public String getStatoOperativo() {
		return statoOperativo;
	}
	
	public void setStatoOperativo(String statoOperativo) {
		this.statoOperativo = statoOperativo;
	}
	
	public String getOggetto() {
		return oggetto;
	}
	
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getIdentificativo() {
		return identificativo;
	}
	
	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}
	
	public String getDirigenteResponsabile() {
		return dirigenteResponsabile;
	}
	
	public void setDirigenteResponsabile(String dirigenteResponsabile) {
		this.dirigenteResponsabile = dirigenteResponsabile;
	}
	
	public String getTrasparenza() {
		return trasparenza;
	}
	
	public void setTrasparenza(String trasparenza) {
		this.trasparenza = trasparenza;
	}
	
	public String getCodiceBlocco() {
		return codiceBlocco;
	}
	
	public void setCodiceBlocco(String codiceBlocco) {
		this.codiceBlocco = codiceBlocco;
	}
	
	@Override
	public String toString() {
		return "ContenutoDocumentoParam [tipoVariazione=" + tipoVariazione + ", ente=" + ente + ", annoAttoChiave="
				+ annoAttoChiave + ", numeroAttoChiave=" + numeroAttoChiave + ", tipoAttoChiave=" + tipoAttoChiave
				+ ", direzioneChiave=" + direzioneChiave + ", centroCostoChiave=" + centroCostoChiave + ", annoAtto="
				+ annoAtto + ", numeroAtto=" + numeroAtto + ", tipoAtto=" + tipoAtto + ", direzione=" + direzione
				+ ", centroCosto=" + centroCosto + ", dataCreazione=" + dataCreazione + ", dataProposta=" + dataProposta
				+ ", dataApprovazione=" + dataApprovazione + ", dataEsecutivita=" + dataEsecutivita
				+ ", statoOperativo=" + statoOperativo + ", oggetto=" + oggetto + ", note=" + note + ", identificativo="
				+ identificativo + ", dirigenteResponsabile=" + dirigenteResponsabile + ", trasparenza=" + trasparenza
				+ ", codiceBlocco=" + codiceBlocco + "]";
	}
	
}
