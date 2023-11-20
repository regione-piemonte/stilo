/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DocDaRifirmareBean extends AbstractBean implements java.io.Serializable {

	private static final long serialVersionUID = 2218190035306283594L;
	
	private BigDecimal idDoc;
	private String firmatario;
	private BigDecimal nroOrdineInverso;
	private BigDecimal nroVerLast;
	private Date tsFirmaApposta;
	
	public BigDecimal getIdDoc() {
		return idDoc;
	}
	public void setIdDoc(BigDecimal idDoc) {
		this.idDoc = idDoc;
		this.getUpdatedProperties().add("idDoc");
	}
	public String getFirmatario() {
		return firmatario;
	}
	public void setFirmatario(String firmatario) {
		this.firmatario = firmatario;
		this.getUpdatedProperties().add("firmatario");
	}
	public BigDecimal getNroOrdineInverso() {
		return nroOrdineInverso;
	}
	public void setNroOrdineInverso(BigDecimal nroOrdineInverso) {
		this.nroOrdineInverso = nroOrdineInverso;
		this.getUpdatedProperties().add("nroOrdineInverso");
	}
	public BigDecimal getNroVerLast() {
		return nroVerLast;
	}
	public void setNroVerLast(BigDecimal nroVerLast) {
		this.nroVerLast = nroVerLast;
		this.getUpdatedProperties().add("nroVerLast");
	}
	public Date getTsFirmaApposta() {
		return tsFirmaApposta;
	}
	public void setTsFirmaApposta(Date tsFirmaApposta) {
		this.tsFirmaApposta = tsFirmaApposta;
		this.getUpdatedProperties().add("tsFirmaApposta");
	}
	
}
