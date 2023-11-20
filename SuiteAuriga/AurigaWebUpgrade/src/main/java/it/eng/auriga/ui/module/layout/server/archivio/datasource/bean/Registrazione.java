/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;


public class Registrazione implements Serializable {
	
	@NumeroColonna(numero = "1")
	private String categoria;
	@NumeroColonna(numero = "2")
	private String sigla;
	@NumeroColonna(numero = "3")
	private String anno;
	@NumeroColonna(numero = "4")
	private String numeroDa;
	@NumeroColonna(numero = "5")
	private String numeroA;
	@NumeroColonna(numero = "6")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataDa;
	@NumeroColonna(numero = "7")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataA;
	@NumeroColonna(numero = "8")
	private String annoDa;
	@NumeroColonna(numero = "9")
	private String annoA;
	@NumeroColonna(numero = "10")
	private String flgCreataDaMe;
	@NumeroColonna(numero = "11")
	private String effettuataDa;
	@NumeroColonna(numero = "12")
	private String subDa;
	@NumeroColonna(numero = "13")
	private String subA;
	
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	public String getNumeroDa() {
		return numeroDa;
	}
	public void setNumeroDa(String numeroDa) {
		this.numeroDa = numeroDa;
	}
	public String getNumeroA() {
		return numeroA;
	}
	public void setNumeroA(String numeroA) {
		this.numeroA = numeroA;
	}
	public Date getDataDa() {
		return dataDa;
	}
	public void setDataDa(Date dataDa) {
		this.dataDa = dataDa;
	}
	public Date getDataA() {
		return dataA;
	}
	public void setDataA(Date dataA) {
		this.dataA = dataA;
	}
	public String getAnnoDa() {
		return annoDa;
	}
	public void setAnnoDa(String annoDa) {
		this.annoDa = annoDa;
	}
	public String getAnnoA() {
		return annoA;
	}
	public void setAnnoA(String annoA) {
		this.annoA = annoA;
	}
	public String getFlgCreataDaMe() {
		return flgCreataDaMe;
	}
	public void setFlgCreataDaMe(String flgCreataDaMe) {
		this.flgCreataDaMe = flgCreataDaMe;
	}
	public String getEffettuataDa() {
		return effettuataDa;
	}
	public void setEffettuataDa(String effettuataDa) {
		this.effettuataDa = effettuataDa;
	}
	public String getSubDa() {
		return subDa;
	}
	public void setSubDa(String subDa) {
		this.subDa = subDa;
	}
	public String getSubA() {
		return subA;
	}
	public void setSubA(String subA) {
		this.subA = subA;
	}
}