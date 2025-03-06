package it.eng.suiteutility.module.mimedb.beans;

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean che rappresenta un'istanza di formato digitale
 * @author upescato
 *
 */
@XmlRootElement
public class FormatoDigitaleBean extends AbstractBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String idDigFormat;

	private String nome;
	private String versione;
	private String altriNomi;
	private String estensionePrincipale;
	private String desEstesa;
	private String idInRegistroFmt;
	private String byteOrders;
	private Date dtRilascio;
	private Date dtDesupp;
	private String rischi;
	private String sviluppatoDa;
	private String mantenutoDa;
	private Boolean flgCopyright;
	private BigDecimal flgIndicizzabile;
	private String flgStato;
//	private Date tsAggStato;
//	private Calendar tsIns;
//	private String idUteIns;
//	private Calendar tsUltimoAgg;
//	private String idUteUltimoAgg;
	private Boolean flgAnn;
	
	private String idRecDizRegistroFmt;
	private String idRecDizTipoFmt;
	
	//Insiemi per gestire i mimetype e le estensioni - @author upescato: ho scelto di usare un oggetto Set per evitare i duplicati
	private Set<Mimetype> mimetypes;
	private Set<String> estensioni;
	
	public String getIdDigFormat() {
		return idDigFormat;
	}
	public void setIdDigFormat(String idDigFormat) {
		this.idDigFormat = idDigFormat;
		this.getUpdatedProperties().add("idDigFormat");
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
		this.getUpdatedProperties().add("nome");
	}
	public String getVersione() {
		return versione;
	}
	public void setVersione(String versione) {
		this.versione = versione;
		this.getUpdatedProperties().add("versione");
	}
	public String getAltriNomi() {
		return altriNomi;
	}
	public void setAltriNomi(String altriNomi) {
		this.altriNomi = altriNomi;
		this.getUpdatedProperties().add("altriNomi");
	}
	public String getEstensionePrincipale() {
		return estensionePrincipale;
	}
	public void setEstensionePrincipale(String estensionePrincipale) {
		this.estensionePrincipale = estensionePrincipale;
		this.getUpdatedProperties().add("estensionePrincipale");
	}
	public String getDesEstesa() {
		return desEstesa;
	}
	public void setDesEstesa(String desEstesa) {
		this.desEstesa = desEstesa;
		this.getUpdatedProperties().add("desEstesa");
	}
	public String getIdInRegistroFmt() {
		return idInRegistroFmt;
	}
	public void setIdInRegistroFmt(String idInRegistroFmt) {
		this.idInRegistroFmt = idInRegistroFmt;
		this.getUpdatedProperties().add("idInRegistroFmt");
	}
	public String getByteOrders() {
		return byteOrders;
	}
	public void setByteOrders(String byteOrders) {
		this.byteOrders = byteOrders;
		this.getUpdatedProperties().add("byteOrders");
	}
	public Date getDtRilascio() {
		return dtRilascio;
	}
	public void setDtRilascio(Date dtRilascio) {
		this.dtRilascio = dtRilascio;
		this.getUpdatedProperties().add("dtRilascio");
	}
	public Date getDtDesupp() {
		return dtDesupp;
	}
	public void setDtDesupp(Date dtDesupp) {
		this.dtDesupp = dtDesupp;
		this.getUpdatedProperties().add("dtDesupp");
	}
	public String getRischi() {
		return rischi;
	}
	public void setRischi(String rischi) {
		this.rischi = rischi;
		this.getUpdatedProperties().add("rischi");
	}
	public String getSviluppatoDa() {
		return sviluppatoDa;
	}
	public void setSviluppatoDa(String sviluppatoDa) {
		this.sviluppatoDa = sviluppatoDa;
		this.getUpdatedProperties().add("sviluppatoDa");
	}
	public String getMantenutoDa() {
		return mantenutoDa;
	}
	public void setMantenutoDa(String mantenutoDa) {
		this.mantenutoDa = mantenutoDa;
		this.getUpdatedProperties().add("mantenutoDa");
	}
	public Boolean getFlgCopyright() {
		return flgCopyright;
	}
	public void setFlgCopyright(Boolean flgCopyright) {
		this.flgCopyright = flgCopyright;
		this.getUpdatedProperties().add("flgCopyright");
	}
	public BigDecimal getFlgIndicizzabile() {
		return flgIndicizzabile;
	}
	public void setFlgIndicizzabile(BigDecimal flgIndicizzabile) {
		this.flgIndicizzabile = flgIndicizzabile;
		this.getUpdatedProperties().add("flgIndicizzabile");
	}
	public String getFlgStato() {
		return flgStato;
	}
	public void setFlgStato(String flgStato) {
		this.flgStato = flgStato;
		this.getUpdatedProperties().add("flgStato");
	}
	public Boolean getFlgAnn() {
		return flgAnn;
	}
	public void setFlgAnn(Boolean flgAnn) {
		this.flgAnn = flgAnn;
		this.getUpdatedProperties().add("flgAnn");
	}
	public String getIdRecDizRegistroFmt() {
		return idRecDizRegistroFmt;
	}
	public void setIdRecDizRegistroFmt(String idRecDizRegistroFmt) {
		this.idRecDizRegistroFmt = idRecDizRegistroFmt;
		this.getUpdatedProperties().add("idRecDizRegistroFmt");
	}
	public String getIdRecDizTipoFmt() {
		return idRecDizTipoFmt;
	}
	public void setIdRecDizTipoFmt(String idRecDizTipoFmt) {
		this.idRecDizTipoFmt = idRecDizTipoFmt;
		this.getUpdatedProperties().add("idRecDizTipoFmt");
	}
	public Set<Mimetype> getMimetypes() {
		return mimetypes;
	}
	public void setMimetypes(Set<Mimetype> mimetypes) {
		this.mimetypes = mimetypes;
		this.getUpdatedProperties().add("mimetypes");
	}
	public Set<String> getEstensioni() {
		return estensioni;
	}
	public void setEstensioni(Set<String> estensioni) {
		this.estensioni = estensioni;
		this.getUpdatedProperties().add("estensioni");
	}
}
