/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import it.eng.auriga.ui.module.layout.server.invioMail.datasource.bean.AttachmentBean;

public class AttestatoConformitaBean extends AttachmentBean{

	private String siglaProtocollo;
	private String nroProtocollo;
	private String desUserProtocollo;
	private String desUOProtocollo;
	private String dataProtocollo;
	private BigDecimal idUd;
	private BigDecimal idDoc;
	private Boolean attestatoFirmatoDigitalmente;
	
	public String getSiglaProtocollo() {
		return siglaProtocollo;
	}
	public void setSiglaProtocollo(String siglaProtocollo) {
		this.siglaProtocollo = siglaProtocollo;
	}
	public String getNroProtocollo() {
		return nroProtocollo;
	}
	public void setNroProtocollo(String nroProtocollo) {
		this.nroProtocollo = nroProtocollo;
	}
	public String getDesUserProtocollo() {
		return desUserProtocollo;
	}
	public void setDesUserProtocollo(String desUserProtocollo) {
		this.desUserProtocollo = desUserProtocollo;
	}
	public String getDesUOProtocollo() {
		return desUOProtocollo;
	}
	public void setDesUOProtocollo(String desUOProtocollo) {
		this.desUOProtocollo = desUOProtocollo;
	}
	public String getDataProtocollo() {
		return dataProtocollo;
	}
	public void setDataProtocollo(String dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}
	
	public BigDecimal getIdUd() {
		return idUd;
	}
	
	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}
	
	public BigDecimal getIdDoc() {
		return idDoc;
	}
	
	public void setIdDoc(BigDecimal idDoc) {
		this.idDoc = idDoc;
	}
	
	public Boolean getAttestatoFirmatoDigitalmente() {
		return attestatoFirmatoDigitalmente;
	}
	
	public void setAttestatoFirmatoDigitalmente(Boolean attestatoFirmatoDigitalmente) {
		this.attestatoFirmatoDigitalmente = attestatoFirmatoDigitalmente;
	}

}
