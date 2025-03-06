package it.eng.utility.client.contabilia.documenti.data;

import java.io.Serializable;

public class ElaboraAttiAmministrativiRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer annoAttoProposta;
	private String numeroAttoProposta;
	private String tipoAttoProposta;
	private Integer annoAttoDefinitivo;
	private String numeroAttoDefinitivo;
	private String tipoAttoDefinitivo;
	private String centroResponsabilita;
	private String centroCosto;
	private String dataCreazione;
	private String dataProposta;
	private String dataApprovazione;
	private String dataEsecutivita;
	private String oggetto;
	private String note;
	private String identificativo;
	private String dirigenteResponsabile;
	private String trasparenza;
	private String idSpAoo;
	
	public Integer getAnnoAttoProposta() {
		return annoAttoProposta;
	}
	
	public void setAnnoAttoProposta(Integer annoAttoProposta) {
		this.annoAttoProposta = annoAttoProposta;
	}
	
	public String getNumeroAttoProposta() {
		return numeroAttoProposta;
	}
	
	public void setNumeroAttoProposta(String numeroAttoProposta) {
		this.numeroAttoProposta = numeroAttoProposta;
	}
	
	public String getTipoAttoProposta() {
		return tipoAttoProposta;
	}
	
	public void setTipoAttoProposta(String tipoAttoProposta) {
		this.tipoAttoProposta = tipoAttoProposta;
	}
	
	public Integer getAnnoAttoDefinitivo() {
		return annoAttoDefinitivo;
	}
	
	public void setAnnoAttoDefinitivo(Integer annoAttoDefinitivo) {
		this.annoAttoDefinitivo = annoAttoDefinitivo;
	}
	
	public String getNumeroAttoDefinitivo() {
		return numeroAttoDefinitivo;
	}
	
	public void setNumeroAttoDefinitivo(String numeroAttoDefinitivo) {
		this.numeroAttoDefinitivo = numeroAttoDefinitivo;
	}
	
	public String getTipoAttoDefinitivo() {
		return tipoAttoDefinitivo;
	}
	
	public void setTipoAttoDefinitivo(String tipoAttoDefinitivo) {
		this.tipoAttoDefinitivo = tipoAttoDefinitivo;
	}
	
	public String getCentroResponsabilita() {
		return centroResponsabilita;
	}
	
	public void setCentroResponsabilita(String centroResponsabilita) {
		this.centroResponsabilita = centroResponsabilita;
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
	
	public String getIdSpAoo() {
		return idSpAoo;
	}
	
	public void setIdSpAoo(String idSpAoo) {
		this.idSpAoo = idSpAoo;
	}
	
	@Override
	public String toString() {
		return "ElaboraAttiAmministrativiRequest [annoAttoProposta=" + annoAttoProposta + ", numeroAttoProposta="
				+ numeroAttoProposta + ", tipoAttoProposta=" + tipoAttoProposta + ", annoAttoDefinitivo="
				+ annoAttoDefinitivo + ", numeroAttoDefinitivo=" + numeroAttoDefinitivo + ", tipoAttoDefinitivo="
				+ tipoAttoDefinitivo + ", centroResponsabilita=" + centroResponsabilita + ", centroCosto=" + centroCosto
				+ ", dataCreazione=" + dataCreazione + ", dataProposta=" + dataProposta + ", dataApprovazione="
				+ dataApprovazione + ", dataEsecutivita=" + dataEsecutivita + ", oggetto=" + oggetto + ", note=" + note
				+ ", identificativo=" + identificativo + ", dirigenteResponsabile=" + dirigenteResponsabile
				+ ", trasparenza=" + trasparenza + ", idSpAoo=" + idSpAoo + "]";
	}
	
}
