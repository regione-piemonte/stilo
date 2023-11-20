/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;

/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntPortaleCrmVerificarichiestaaccessoattiBean")
public class DmpkIntPortaleCrmVerificarichiestaaccessoattiBean extends StoreBean implements Serializable {

	private static final String storeName = "DMPK_INT_PORTALE_CRM_VERIFICARICHIESTAACCESSOATTI";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.Integer annoprotgenin;
	private java.lang.Integer nroprotgenin;
	private java.lang.Integer idrichiestaout;
	private java.lang.String oggettorichiestaout;
	private java.lang.String attirichiestilistaout;
	private java.lang.String sediappuntamentoout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;

	public java.lang.Integer getParametro_1() {
		return parametro_1;
	}

	public java.lang.String getCodidconnectiontokenin() {
		return codidconnectiontokenin;
	}

	public java.lang.Integer getAnnoprotgenin() {
		return annoprotgenin;
	}

	public java.lang.Integer getNroprotgenin() {
		return nroprotgenin;
	}

	public java.lang.Integer getIdrichiestaout() {
		return idrichiestaout;
	}

	public java.lang.String getOggettorichiestaout() {
		return oggettorichiestaout;
	}

	public java.lang.String getAttirichiestilistaout() {
		return attirichiestilistaout;
	}

	public java.lang.String getSediappuntamentoout() {
		return sediappuntamentoout;
	}

	public java.lang.String getErrcontextout() {
		return errcontextout;
	}

	public java.lang.Integer getErrcodeout() {
		return errcodeout;
	}

	public java.lang.String getErrmsgout() {
		return errmsgout;
	}

	public void setParametro_1(java.lang.Integer value) {
		this.parametro_1 = value;
	}

	public void setCodidconnectiontokenin(java.lang.String value) {
		this.codidconnectiontokenin = value;
	}

	public void setAnnoprotgenin(java.lang.Integer value) {
		this.annoprotgenin = value;
	}

	public void setNroprotgenin(java.lang.Integer value) {
		this.nroprotgenin = value;
	}

	public void setIdrichiestaout(java.lang.Integer value) {
		this.idrichiestaout = value;
	}

	public void setOggettorichiestaout(java.lang.String value) {
		this.oggettorichiestaout = value;
	}

	public void setAttirichiestilistaout(java.lang.String value) {
		this.attirichiestilistaout = value;
	}

	public void setSediappuntamentoout(java.lang.String value) {
		this.sediappuntamentoout = value;
	}

	public void setErrcontextout(java.lang.String value) {
		this.errcontextout = value;
	}

	public void setErrcodeout(java.lang.Integer value) {
		this.errcodeout = value;
	}

	public void setErrmsgout(java.lang.String value) {
		this.errmsgout = value;
	}

	public String getStoreName() {
		return storeName;
	}

	public StoreType getType() {
		return StoreType.STORE;
	}

}