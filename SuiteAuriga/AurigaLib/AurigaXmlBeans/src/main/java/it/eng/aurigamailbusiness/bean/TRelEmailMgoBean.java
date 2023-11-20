/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

@XmlRootElement
public class TRelEmailMgoBean extends AbstractBean implements Serializable {

	@Override
	public String toString() {
		return "TRelEmailMgoBean [idRelEmail=" + idRelEmail + ", idDestinatario2=" + idDestinatario2 + ", idEmail1=" + idEmail1 + ", idEmail2=" + idEmail2
				+ "]";
	}

	private static final long serialVersionUID = -7341833444762147789L;

	private String dettRelazione;
	private boolean flgRelAutomatica;
	private String idRelEmail;
	private String idUteIns;
	private String idUteUltimoAgg;
	private Date tsIns;
	private Date tsUltimoAgg;
	private String idDestinatario2;
	private String idEmail1;
	private String idEmail2;
	private String idRecDizRuolo1Vs2;
	private String idRecDizCategRel;

	public String getDettRelazione() {
		return dettRelazione;
	}

	public void setDettRelazione(String dettRelazione) {
		this.dettRelazione = dettRelazione;
	}

	public boolean getFlgRelAutomatica() {
		return flgRelAutomatica;
	}

	public void setFlgRelAutomatica(boolean flgRelAutomatica) {
		this.flgRelAutomatica = flgRelAutomatica;
	}

	public String getIdRelEmail() {
		return idRelEmail;
	}

	public void setIdRelEmail(String idRelEmail) {
		this.idRelEmail = idRelEmail;
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

	public Date getTsIns() {
		return tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	public Date getTsUltimoAgg() {
		return tsUltimoAgg;
	}

	public void setTsUltimoAgg(Date tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
	}

	public String getIdDestinatario2() {
		return idDestinatario2;
	}

	public void setIdDestinatario2(String idDestinatario2) {
		this.idDestinatario2 = idDestinatario2;
	}

	public String getIdEmail1() {
		return idEmail1;
	}

	public void setIdEmail1(String idEmail1) {
		this.idEmail1 = idEmail1;
	}

	public String getIdEmail2() {
		return idEmail2;
	}

	public void setIdEmail2(String idEmail2) {
		this.idEmail2 = idEmail2;
	}

	public String getIdRecDizRuolo1Vs2() {
		return idRecDizRuolo1Vs2;
	}

	public void setIdRecDizRuolo1Vs2(String idRecDizRuolo1Vs2) {
		this.idRecDizRuolo1Vs2 = idRecDizRuolo1Vs2;
	}

	public String getIdRecDizCategRel() {
		return idRecDizCategRel;
	}

	public void setIdRecDizCategRel(String idRecDizCategRel) {
		this.idRecDizCategRel = idRecDizCategRel;
	}

}