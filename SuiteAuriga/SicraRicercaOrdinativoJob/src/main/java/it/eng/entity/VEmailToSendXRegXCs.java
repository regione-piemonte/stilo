/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

// Generated 5-ott-2015 10.18.12 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VEmailToSendXRegXCs generated by hbm2java
 */
@Entity
@Table(name = "V_EMAIL_TO_SEND_X_REG_X_CS")
public class VEmailToSendXRegXCs implements java.io.Serializable {

	private static final long serialVersionUID = -6858410989454367821L;

	private VEmailToSendXRegXCsId id;

	private String codAmmIpa;

	private String idServizio;

	private String idRegistro;

	private String codAooIpa;

	private String denominazione;

	private String tipoRegistro;

	private String siglaRegistro;

	private String indirizziDest;

	private String periodoReg;

	private String errTyCtrlFile;

	private String errMsgCtrlFile;

	private String xmlRdv;

	private Date tsRicezione;

	public VEmailToSendXRegXCs() {
	}

	public VEmailToSendXRegXCs(VEmailToSendXRegXCsId id) {
		this.id = id;
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "tipoEmail", column = @Column(name = "TIPO_EMAIL", length = 25)),
			@AttributeOverride(name = "idServizio", column = @Column(name = "ID_SERVIZIO", length = 64)) })
	public VEmailToSendXRegXCsId getId() {
		return this.id;
	}
	
	public void setId(VEmailToSendXRegXCsId id) {
		this.id = id;
	}

	@Column(name = "COD_AMM_IPA", length = 150)
	public String getCodAmmIpa() {
		return this.codAmmIpa;
	}

	public void setCodAmmIpa(String codAmmIpa) {
		this.codAmmIpa = codAmmIpa;
	}

	@Column(name = "ID_REGISTRO", length = 64)
	public String getIdRegistro() {
		return this.idRegistro;
	}

	public void setIdRegistro(String idRegistro) {
		this.idRegistro = idRegistro;
	}

	@Column(name = "COD_AOO_IPA", length = 4000)
	public String getCodAooIpa() {
		return this.codAooIpa;
	}

	public void setCodAooIpa(String codAooIpa) {
		this.codAooIpa = codAooIpa;
	}

	@Column(name = "DENOMINAZIONE", length = 500)
	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	@Column(name = "TIPO_REGISTRO", length = 4000)
	public String getTipoRegistro() {
		return this.tipoRegistro;
	}

	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	@Column(name = "SIGLA_REGISTRO", length = 4000)
	public String getSiglaRegistro() {
		return this.siglaRegistro;
	}

	public void setSiglaRegistro(String siglaRegistro) {
		this.siglaRegistro = siglaRegistro;
	}

	@Column(name = "INDIRIZZI_DEST", length = 4000)
	public String getIndirizziDest() {
		return this.indirizziDest;
	}

	public void setIndirizziDest(String indirizziDest) {
		this.indirizziDest = indirizziDest;
	}

	@Column(name = "PERIODO_REG", length = 38)
	public String getPeriodoReg() {
		return this.periodoReg;
	}

	public void setPeriodoReg(String periodoReg) {
		this.periodoReg = periodoReg;
	}

	@Column(name = "ERR_TY_CTRL_FILE", length = 30)
	public String getErrTyCtrlFile() {
		return this.errTyCtrlFile;
	}

	public void setErrTyCtrlFile(String errTyCtrlFile) {
		this.errTyCtrlFile = errTyCtrlFile;
	}

	@Column(name = "ERR_MSG_CTRL_FILE")
	public String getErrMsgCtrlFile() {
		return this.errMsgCtrlFile;
	}

	public void setErrMsgCtrlFile(String errMsgCtrlFile) {
		this.errMsgCtrlFile = errMsgCtrlFile;
	}

	@Column(name = "XML_RDV")
	public String getXmlRdv() {
		return this.xmlRdv;
	}

	public void setXmlRdv(String xmlRdv) {
		this.xmlRdv = xmlRdv;
	}

	@Column(name = "TS_RICEZIONE", length = 7)
	public Date getTsRicezione() {
		return this.tsRicezione;
	}

	public void setTsRicezione(Date tsRicezione) {
		this.tsRicezione = tsRicezione;
	}
	

	@Column(name = "ID_SERVIZIO", length = 64)
	public String getIdServizio() {
		return this.idServizio;
	}

	public void setIdServizio(String idServizio) {
		this.idServizio = idServizio;
	}
}
