/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class DocumentoCollegato implements Serializable{

	@NumeroColonna(numero = "1")
	private String idUd;
	@NumeroColonna(numero = "2")
	private TipoProtocollo tipo;
	@NumeroColonna(numero = "3")
	private String siglaRegistro;
	@NumeroColonna(numero = "4")
	private Integer anno;
	@NumeroColonna(numero = "5")
	private Integer numero;
	@NumeroColonna(numero = "6")
	private String cValue = "C";
	@NumeroColonna(numero = "8")
	private String scValue = "SC";
	@NumeroColonna(numero = "10")
	private String prcValue = "PRC";
	@NumeroColonna(numero = "12")
	private String motiviCollegamento;
	@NumeroColonna(numero = "13")
	private String oggetto;	
	@NumeroColonna(numero = "18")
	private String estremiReg;	
	@NumeroColonna(numero = "19")
	private String datiCollegamento;	
	@NumeroColonna(numero = "21")
	private String flgLocked;	
	@NumeroColonna(numero = "22")
	private Integer subNumero;	
	@NumeroColonna(numero = "23")
	private String flgPresenteASistema;	
	@NumeroColonna(numero = "24")
	private String idTipoDoc;
	@NumeroColonna(numero = "25")
	private String nomeTipoDoc;

	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public TipoProtocollo getTipo() {
		return tipo;
	}
	public void setTipo(TipoProtocollo tipo) {
		this.tipo = tipo;
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
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getcValue() {
		return cValue;
	}
	public void setcValue(String cValue) {
		this.cValue = cValue;
	}
	public String getScValue() {
		return scValue;
	}
	public void setScValue(String scValue) {
		this.scValue = scValue;
	}
	public String getPrcValue() {
		return prcValue;
	}
	public void setPrcValue(String prcValue) {
		this.prcValue = prcValue;
	}	
	public String getMotiviCollegamento() {
		return motiviCollegamento;
	}
	public void setMotiviCollegamento(String motiviCollegamento) {
		this.motiviCollegamento = motiviCollegamento;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
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
	public String getFlgLocked() {
		return flgLocked;
	}
	public void setFlgLocked(String flgLocked) {
		this.flgLocked = flgLocked;
	}
	public Integer getSubNumero() {
		return subNumero;
	}
	public void setSubNumero(Integer subNumero) {
		this.subNumero = subNumero;
	}
	public String getFlgPresenteASistema() {
		return flgPresenteASistema;
	}
	public void setFlgPresenteASistema(String flgPresenteASistema) {
		this.flgPresenteASistema = flgPresenteASistema;
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
	
}
