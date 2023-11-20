/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Federico Cacco
 */

@XmlRootElement
public class RichEmailToSendBean extends AbstractBean implements java.io.Serializable {

	private static final long serialVersionUID = -3845163073219544953L;

	private String idRichiesta;
	private String codSenderAppl;
	private String provIdRichiesta;
	private BigDecimal idSpAoo;
	private String doctype;
	private Boolean flgPec;
	private String xmlRequest;
	private String stato;
	private String idEmail;
	private String errMsg;
	private Date tsIns;
	private Date tsUltimoAgg;

	public String getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(String idRichiesta) {
		this.idRichiesta = idRichiesta;
		this.getUpdatedProperties().add("idRichiesta");
	}

	public String getCodSenderAppl() {
		return codSenderAppl;
	}

	public void setCodSenderAppl(String codSenderAppl) {
		this.codSenderAppl = codSenderAppl;
		this.getUpdatedProperties().add("codSenderAppl");
	}

	public String getProvIdRichiesta() {
		return provIdRichiesta;
	}

	public void setProvIdRichiesta(String provIdRichiesta) {
		this.provIdRichiesta = provIdRichiesta;
		this.getUpdatedProperties().add("provIdRichiesta");
	}

	public BigDecimal getIdSpAoo() {
		return idSpAoo;
	}

	public void setIdSpAoo(BigDecimal idSpAoo) {
		this.idSpAoo = idSpAoo;
		this.getUpdatedProperties().add("idSpAoo");
	}

	public String getDoctype() {
		return doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
		this.getUpdatedProperties().add("doctype");
	}

	public Boolean getFlgPec() {
		return flgPec;
	}

	public void setFlgPec(Boolean flgPec) {
		this.flgPec = flgPec;
		this.getUpdatedProperties().add("flgPec");
	}

	public String getXmlRequest() {
		return xmlRequest;
	}

	public void setXmlRequest(String xmlRequest) {
		this.xmlRequest = xmlRequest;
		this.getUpdatedProperties().add("xmlRequest");
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
		this.getUpdatedProperties().add("stato");
	}

	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
		this.getUpdatedProperties().add("idEmail");
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
		this.getUpdatedProperties().add("errMsg");
	}

	public Date getTsIns() {
		return tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
		this.getUpdatedProperties().add("tsIns");
	}

	public Date getTsUltimoAgg() {
		return tsUltimoAgg;
	}

	public void setTsUltimoAgg(Date tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
		this.getUpdatedProperties().add("tsUltimoAgg");
	}

}
