/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

@XmlRootElement
public class TRegEstVsEmailBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -7341833444762147789L;

	private Short annoReg;
	private String codAmministrazione;
	private String codAoo;
	private String idRegEstEmail;
	private String idUteIns;
	private String idUteUltimoAgg;
	private Integer numReg;
	private String siglaRegistro;
	private Date tsIns;
	private Date tsReg;
	private Date tsUltimoAgg;
	private String idEmailRicevuta;
	private String idEmailInviata;
	private String idDestinatarioEmail;

	public Short getAnnoReg() {
		return annoReg;
	}

	@Override
	public String toString() {
		return "TRegEstVsEmailBean [idRegEstEmail=" + idRegEstEmail + ", idEmailRicevuta=" + idEmailRicevuta + ", idEmailInviata=" + idEmailInviata
				+ ", idDestinatarioEmail=" + idDestinatarioEmail + "]";
	}

	public void setAnnoReg(Short annoReg) {
		this.annoReg = annoReg;
	}

	public String getCodAmministrazione() {
		return codAmministrazione;
	}

	public void setCodAmministrazione(String codAmministrazione) {
		this.codAmministrazione = codAmministrazione;
	}

	public String getCodAoo() {
		return codAoo;
	}

	public void setCodAoo(String codAoo) {
		this.codAoo = codAoo;
	}

	public String getIdRegEstEmail() {
		return idRegEstEmail;
	}

	public void setIdRegEstEmail(String idRegEstEmail) {
		this.idRegEstEmail = idRegEstEmail;
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

	public Integer getNumReg() {
		return numReg;
	}

	public void setNumReg(Integer numReg) {
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

	public String getIdEmailRicevuta() {
		return idEmailRicevuta;
	}

	public void setIdEmailRicevuta(String idEmailRicevuta) {
		this.idEmailRicevuta = idEmailRicevuta;
	}

	public String getIdEmailInviata() {
		return idEmailInviata;
	}

	public void setIdEmailInviata(String idEmailInviata) {
		this.idEmailInviata = idEmailInviata;
	}

	public String getIdDestinatarioEmail() {
		return idDestinatarioEmail;
	}

	public void setIdDestinatarioEmail(String idDestinatarioEmail) {
		this.idDestinatarioEmail = idDestinatarioEmail;
	}

}