/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

@XmlRootElement
public class TRegProtVsEmailBean extends AbstractBean implements Serializable {

	@Override
	public String toString() {
		return "TRegProtVsEmailBean [idRegProtEmail=" + idRegProtEmail + ", idEmail=" + idEmail + "]";
	}

	private static final long serialVersionUID = -7341833444762147789L;

	private Short annoReg;

	private String categoriaReg;

	private String idProvReg;

	private String idRegProtEmail;

	private String idUteIns;

	private String idUteUltimoAgg;

	private BigDecimal numReg;

	private String siglaRegistro;

	private Date tsIns;

	private Date tsReg;

	private Date tsUltimoAgg;

	private String idEmail;

	public Short getAnnoReg() {
		return annoReg;
	}

	public void setAnnoReg(Short annoReg) {
		this.annoReg = annoReg;
	}

	public String getCategoriaReg() {
		return categoriaReg;
	}

	public void setCategoriaReg(String categoriaReg) {
		this.categoriaReg = categoriaReg;
	}

	public String getIdProvReg() {
		return idProvReg;
	}

	public void setIdProvReg(String idProvReg) {
		this.idProvReg = idProvReg;
	}

	public String getIdRegProtEmail() {
		return idRegProtEmail;
	}

	public void setIdRegProtEmail(String idRegProtEmail) {
		this.idRegProtEmail = idRegProtEmail;
	}

	public String getIdUteIns() {
		return idUteIns;
	}

	public void setIdUteIns(String idUteIns) {
		this.idUteIns = idUteIns;
	}

	public String getIdUteUltimoAgg() {
		return idUteUltimoAgg;
	}

	public void setIdUteUltimoAgg(String idUteUltimoAgg) {
		this.idUteUltimoAgg = idUteUltimoAgg;
	}

	public BigDecimal getNumReg() {
		return numReg;
	}

	public void setNumReg(BigDecimal numReg) {
		this.numReg = numReg;
	}

	public String getSiglaRegistro() {
		return siglaRegistro;
	}

	public void setSiglaRegistro(String siglaRegistro) {
		this.siglaRegistro = siglaRegistro;
	}

	public Date getTsIns() {
		return tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	public Date getTsReg() {
		return tsReg;
	}

	public void setTsReg(Date tsReg) {
		this.tsReg = tsReg;
	}

	public Date getTsUltimoAgg() {
		return tsUltimoAgg;
	}

	public void setTsUltimoAgg(Date tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
	}

	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

}