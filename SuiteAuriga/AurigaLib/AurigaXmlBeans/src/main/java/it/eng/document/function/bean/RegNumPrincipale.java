/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegNumPrincipale implements Serializable{

	@XmlVariabile(nome="Tipo", tipo=TipoVariabile.SEMPLICE)
	private String tipo;
	
	@XmlVariabile(nome="Registro", tipo=TipoVariabile.SEMPLICE)
	private String registro;
	
	@XmlVariabile(nome="CodCategoria", tipo=TipoVariabile.SEMPLICE)
	private String codCategoria;
	
	@XmlVariabile(nome="Sigla", tipo=TipoVariabile.SEMPLICE)
	private String sigla;
	
	@XmlVariabile(nome="Anno", tipo=TipoVariabile.SEMPLICE)
	private String anno;
	
	@XmlVariabile(nome="Nro", tipo=TipoVariabile.SEMPLICE)
	private String nro;
	
	@XmlVariabile(nome="SubNro", tipo=TipoVariabile.SEMPLICE)
	private String subNro;
	
	@XmlVariabile(nome="TsRegistrazione", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo=Tipo.DATA)
	private Date tsRegistrazione;
	
	@XmlVariabile(nome="IdUDAttoAutAnnullamento", tipo=TipoVariabile.SEMPLICE)
	private String idUdAttoAutAnnReg;
	
	@XmlVariabile(nome="DesUser", tipo=TipoVariabile.SEMPLICE)
	private String desUser;
	
	@XmlVariabile(nome="IdUO", tipo=TipoVariabile.SEMPLICE)
	private String idUO;
	
	@XmlVariabile(nome="DesUO", tipo=TipoVariabile.SEMPLICE)
	private String desUO;
	
	@XmlVariabile(nome="Annullata", tipo=TipoVariabile.SEMPLICE)
	private Boolean annullata;
	
	@XmlVariabile(nome="DatiAnnullamento", tipo=TipoVariabile.SEMPLICE)
	private String datiAnnullamento;
	
	@XmlVariabile(nome="RichAnnullamento", tipo=TipoVariabile.SEMPLICE)
	private Boolean richAnnullamento;
	
	@XmlVariabile(nome="DatiRichAnnullamento", tipo=TipoVariabile.SEMPLICE)
	private String datiRichAnnullamento;
	
	@XmlVariabile(nome="MotiviRichAnnullamento", tipo=TipoVariabile.SEMPLICE)
	private String motiviRichAnnullamento;
	
	@XmlVariabile(nome="TsAnnullamento", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo=Tipo.DATA)
	private Date tsAnnullamento;
	
	@XmlVariabile(nome="UserAnnullamento", tipo=TipoVariabile.SEMPLICE)
	private String userAnnullamento;
	
	@XmlVariabile(nome="MotiviAnnullamento", tipo=TipoVariabile.SEMPLICE)
	private String motiviAnnullamento;
	
	@XmlVariabile(nome="EstremiAttoAutAnnullamento", tipo=TipoVariabile.SEMPLICE)
	private String estremiAttoAutAnnullamento;
	
	// ***************** Integrazione Open Text A2A *******************//	
	
	@XmlVariabile(nome = "DataOraRegistrazione", tipo = TipoVariabile.SEMPLICE)
	private String dataOraRegistrazione;
	
	@XmlVariabile(nome = "Societa", tipo = TipoVariabile.SEMPLICE)
	private String societa;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getRegistro() {
		return registro;
	}
	public void setRegistro(String registro) {
		this.registro = registro;
	}
	public String getCodCategoria() {
		return codCategoria;
	}
	public void setCodCategoria(String codCategoria) {
		this.codCategoria = codCategoria;
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
	public String getNro() {
		return nro;
	}
	public void setNro(String nro) {
		this.nro = nro;
	}
	public String getSubNro() {
		return subNro;
	}
	public void setSubNro(String subNro) {
		this.subNro = subNro;
	}
	public Date getTsRegistrazione() {
		return tsRegistrazione;
	}
	public void setTsRegistrazione(Date tsRegistrazione) {
		this.tsRegistrazione = tsRegistrazione;
	}
	public String getIdUdAttoAutAnnReg() {
		return idUdAttoAutAnnReg;
	}
	public void setIdUdAttoAutAnnReg(String idUdAttoAutAnnReg) {
		this.idUdAttoAutAnnReg = idUdAttoAutAnnReg;
	}
	public String getDesUser() {
		return desUser;
	}
	public void setDesUser(String desUser) {
		this.desUser = desUser;
	}
	public String getIdUO() {
		return idUO;
	}
	public void setIdUO(String idUO) {
		this.idUO = idUO;
	}
	public String getDesUO() {
		return desUO;
	}
	public void setDesUO(String desUO) {
		this.desUO = desUO;
	}
	public Boolean getAnnullata() {
		return annullata;
	}
	public void setAnnullata(Boolean annullata) {
		this.annullata = annullata;
	}
	public String getDatiAnnullamento() {
		return datiAnnullamento;
	}
	public void setDatiAnnullamento(String datiAnnullamento) {
		this.datiAnnullamento = datiAnnullamento;
	}
	public Boolean getRichAnnullamento() {
		return richAnnullamento;
	}
	public void setRichAnnullamento(Boolean richAnnullamento) {
		this.richAnnullamento = richAnnullamento;
	}
	public String getDatiRichAnnullamento() {
		return datiRichAnnullamento;
	}
	public void setDatiRichAnnullamento(String datiRichAnnullamento) {
		this.datiRichAnnullamento = datiRichAnnullamento;
	}
	public String getMotiviRichAnnullamento() {
		return motiviRichAnnullamento;
	}
	public void setMotiviRichAnnullamento(String motiviRichAnnullamento) {
		this.motiviRichAnnullamento = motiviRichAnnullamento;
	}
	public Date getTsAnnullamento() {
		return tsAnnullamento;
	}
	public void setTsAnnullamento(Date tsAnnullamento) {
		this.tsAnnullamento = tsAnnullamento;
	}
	public String getUserAnnullamento() {
		return userAnnullamento;
	}
	public void setUserAnnullamento(String userAnnullamento) {
		this.userAnnullamento = userAnnullamento;
	}
	public String getMotiviAnnullamento() {
		return motiviAnnullamento;
	}
	public void setMotiviAnnullamento(String motiviAnnullamento) {
		this.motiviAnnullamento = motiviAnnullamento;
	}
	public String getEstremiAttoAutAnnullamento() {
		return estremiAttoAutAnnullamento;
	}
	public void setEstremiAttoAutAnnullamento(String estremiAttoAutAnnullamento) {
		this.estremiAttoAutAnnullamento = estremiAttoAutAnnullamento;
	}
	public String getDataOraRegistrazione() {
		return dataOraRegistrazione;
	}
	public void setDataOraRegistrazione(String dataOraRegistrazione) {
		this.dataOraRegistrazione = dataOraRegistrazione;
	}
	public String getSocieta() {
		return societa;
	}
	public void setSocieta(String societa) {
		this.societa = societa;
	}
	
}