/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;

/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkModelliDocGetdatixgendamodelloBean")
public class DmpkModelliDocGetdatixgendamodelloBean extends StoreBean implements Serializable {

	private static final String storeName = "DMPK_MODELLI_DOC_GETDATIXGENDAMODELLO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String nomemodelloin;
	private java.lang.String flgtpobjrifin;
	private java.lang.String idobjrifin;
	private java.lang.String datixmodelloxmlout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String attributiaddin;

	public java.lang.Integer getParametro_1() {
		return parametro_1;
	}

	public java.lang.String getCodidconnectiontokenin() {
		return codidconnectiontokenin;
	}

	public java.math.BigDecimal getIduserlavoroin() {
		return iduserlavoroin;
	}

	public java.lang.String getNomemodelloin() {
		return nomemodelloin;
	}

	public java.lang.String getFlgtpobjrifin() {
		return flgtpobjrifin;
	}

	public java.lang.String getIdobjrifin() {
		return idobjrifin;
	}

	public java.lang.String getDatixmodelloxmlout() {
		return datixmodelloxmlout;
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

	public java.lang.String getAttributiaddin() {
		return attributiaddin;
	}

	public void setParametro_1(java.lang.Integer value) {
		this.parametro_1 = value;
	}

	public void setCodidconnectiontokenin(java.lang.String value) {
		this.codidconnectiontokenin = value;
	}

	public void setIduserlavoroin(java.math.BigDecimal value) {
		this.iduserlavoroin = value;
	}

	public void setNomemodelloin(java.lang.String value) {
		this.nomemodelloin = value;
	}

	public void setFlgtpobjrifin(java.lang.String value) {
		this.flgtpobjrifin = value;
	}

	public void setIdobjrifin(java.lang.String value) {
		this.idobjrifin = value;
	}

	public void setDatixmodelloxmlout(java.lang.String value) {
		this.datixmodelloxmlout = value;
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

	public void setAttributiaddin(java.lang.String value) {
		this.attributiaddin = value;
	}

	public String getStoreName() {
		return storeName;
	}

	public StoreType getType() {
		return StoreType.STORE;
	}

}