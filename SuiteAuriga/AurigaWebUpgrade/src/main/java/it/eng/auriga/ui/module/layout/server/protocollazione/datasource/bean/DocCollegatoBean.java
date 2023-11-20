/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public class DocCollegatoBean {
	
	private String flgPresenteASistema;	
	private String idUdCollegata;
	private String tipo;
	private String idTipoDoc;
	private String nomeTipoDoc;
	private String siglaRegistroAltraNum;
	private String siglaRegistro;
	private Integer anno;		
	private String numero;
	private String oggetto;
	private String motivi;
	private String estremiReg;
	private String datiCollegamento;
	private Boolean flgLocked;
	private String sub;
	
	public String getFlgPresenteASistema() {
		return flgPresenteASistema;
	}
	public void setFlgPresenteASistema(String flgPresenteASistema) {
		this.flgPresenteASistema = flgPresenteASistema;
	}
	public String getIdUdCollegata() {
		return idUdCollegata;
	}
	public void setIdUdCollegata(String idUdCollegata) {
		this.idUdCollegata = idUdCollegata;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getIdTipoDoc() {
		return idTipoDoc;
	}
	public void setIdTipoDoc(String idTipoDoc) {
		this.idTipoDoc = idTipoDoc;
	}	
	public String getNomeTipoDoc() {
		return nomeTipoDoc;
	}
	public void setNomeTipoDoc(String nomeTipoDoc) {
		this.nomeTipoDoc = nomeTipoDoc;
	}
	public String getSiglaRegistroAltraNum() {
		return siglaRegistroAltraNum;
	}
	public void setSiglaRegistroAltraNum(String siglaRegistroAltraNum) {
		this.siglaRegistroAltraNum = siglaRegistroAltraNum;
	}
	public String getSiglaRegistro() {
		return siglaRegistro;
	}
	public void setSiglaRegistro(String siglaRegistro) {
		this.siglaRegistro = siglaRegistro;
	}
	public Integer getAnno() {
		return anno;
	}
	public void setAnno(Integer anno) {
		this.anno = anno;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getMotivi() {
		return motivi;
	}
	public void setMotivi(String motivi) {
		this.motivi = motivi;
	}
	public String getEstremiReg() {
		return estremiReg;
	}
	public void setEstremiReg(String estremiReg) {
		this.estremiReg = estremiReg;
	}
	public String getDatiCollegamento() {
		return datiCollegamento;
	}
	public void setDatiCollegamento(String datiCollegamento) {
		this.datiCollegamento = datiCollegamento;
	}
	public Boolean getFlgLocked() {
		return flgLocked;
	}
	public void setFlgLocked(Boolean flgLocked) {
		this.flgLocked = flgLocked;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}	
}