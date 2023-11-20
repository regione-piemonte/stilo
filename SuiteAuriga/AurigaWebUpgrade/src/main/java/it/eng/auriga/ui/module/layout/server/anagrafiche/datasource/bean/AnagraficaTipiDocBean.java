/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

public class AnagraficaTipiDocBean {

	private String idTipoDoc;	
	private String nomeTipoDoc;
	private String descrizioneTipoDoc;	
	private Integer flgAnn;	
	private String codTipoDoc;
	private String score;
	private Integer flgDiSistema;
	private Date tsIns;	
	private String uteIns;	
	private Date tsLastUpd;
	private String uteLastUpd;		
	private Boolean flgSelXFinalita;
	
	public String getIdTipoDoc() {
		return idTipoDoc;
	}
	public String getNomeTipoDoc() {
		return nomeTipoDoc;
	}
	public String getDescrizioneTipoDoc() {
		return descrizioneTipoDoc;
	}
	public String getScore() {
		return score;
	}
	public Integer getFlgDiSistema() {
		return flgDiSistema;
	}
	
	public Date getTsIns() {
		return tsIns;
	}
	public String getUteIns() {
		return uteIns;
	}
	public Date getTsLastUpd() {
		return tsLastUpd;
	}
	public String getUteLastUpd() {
		return uteLastUpd;
	}
	public Boolean getFlgSelXFinalita() {
		return flgSelXFinalita;
	}
	public void setIdTipoDoc(String idTipoDoc) {
		this.idTipoDoc = idTipoDoc;
	}
	public void setNomeTipoDoc(String nomeTipoDoc) {
		this.nomeTipoDoc = nomeTipoDoc;
	}
	public void setDescrizioneTipoDoc(String descrizioneTipoDoc) {
		this.descrizioneTipoDoc = descrizioneTipoDoc;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public void setFlgDiSistema(Integer flgDiSistema) {
		this.flgDiSistema = flgDiSistema;
	}
	
	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}
	public void setUteIns(String uteIns) {
		this.uteIns = uteIns;
	}
	public void setTsLastUpd(Date tsLastUpd) {
		this.tsLastUpd = tsLastUpd;
	}
	public void setUteLastUpd(String uteLastUpd) {
		this.uteLastUpd = uteLastUpd;
	}
	public void setFlgSelXFinalita(Boolean flgSelXFinalita) {
		this.flgSelXFinalita = flgSelXFinalita;
	}
	public String getCodTipoDoc() {
		return codTipoDoc;
	}
	public void setCodTipoDoc(String codTipoDoc) {
		this.codTipoDoc = codTipoDoc;
	}
	public Integer getFlgAnn() {
		return flgAnn;
	}
	public void setFlgAnn(Integer flgAnn) {
		this.flgAnn = flgAnn;
	}

}
