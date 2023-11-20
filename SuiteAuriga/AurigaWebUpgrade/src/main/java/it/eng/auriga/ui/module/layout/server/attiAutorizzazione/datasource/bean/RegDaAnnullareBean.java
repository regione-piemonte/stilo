/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class RegDaAnnullareBean {
	
	@NumeroColonna(numero = "1")
	private String codCategoriaReg;
	@NumeroColonna(numero = "2")
	private String siglaReg;
	@NumeroColonna(numero = "3")
	private String annoReg;
	@NumeroColonna(numero = "4")
	private String nroReg;
	@NumeroColonna(numero = "5")
	private String motiviAnn;	
	@NumeroColonna(numero = "7")
	private String idUd;	
	@NumeroColonna(numero = "8")
	private String tsReg;
	@NumeroColonna(numero = "9")
	private String segnatura;
	@NumeroColonna(numero = "10")
	private String tsRichAnn;
	@NumeroColonna(numero = "11")
	private String uteRichAnn;
	@NumeroColonna(numero = "12")
	private String uteReg;
	
	public String getCodCategoriaReg() {
		return codCategoriaReg;
	}
	public void setCodCategoriaReg(String codCategoriaReg) {
		this.codCategoriaReg = codCategoriaReg;
	}
	public String getSiglaReg() {
		return siglaReg;
	}
	public void setSiglaReg(String siglaReg) {
		this.siglaReg = siglaReg;
	}
	public String getAnnoReg() {
		return annoReg;
	}
	public void setAnnoReg(String annoReg) {
		this.annoReg = annoReg;
	}
	public String getNroReg() {
		return nroReg;
	}
	public void setNroReg(String nroReg) {
		this.nroReg = nroReg;
	}
	public String getMotiviAnn() {
		return motiviAnn;
	}
	public void setMotiviAnn(String motiviAnn) {
		this.motiviAnn = motiviAnn;
	}
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public String getTsReg() {
		return tsReg;
	}
	public void setTsReg(String tsReg) {
		this.tsReg = tsReg;
	}
	public String getSegnatura() {
		return segnatura;
	}
	public void setSegnatura(String segnatura) {
		this.segnatura = segnatura;
	}
	public String getTsRichAnn() {
		return tsRichAnn;
	}
	public void setTsRichAnn(String tsRichAnn) {
		this.tsRichAnn = tsRichAnn;
	}
	public String getUteRichAnn() {
		return uteRichAnn;
	}
	public void setUteRichAnn(String uteRichAnn) {
		this.uteRichAnn = uteRichAnn;
	}
	public String getUteReg() {
		return uteReg;
	}
	public void setUteReg(String uteReg) {
		this.uteReg = uteReg;
	}	
	
}
