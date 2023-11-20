/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

@XmlRootElement
public class InputModuliCompBean extends AbstractBean implements java.io.Serializable {

	private static final long serialVersionUID = -276900485342687053L;

	private BigDecimal idModello;
	private String nomeInput;
	private Boolean flgMultivalore;
	private String attrName;
	private BigDecimal idSpAooAttr;
	private Boolean flgObbligatorio;
	private Boolean flgCompilabile;
	private Date tsIns;
	private BigDecimal idUserIns;
	private Date tsLastUpd;
	private BigDecimal idUserLastUpd;
	private String attrType;
	private BigDecimal maxNumValues;
	private Boolean flgBarcode;
	private String tipoBarcode;
	private BigDecimal nroColonna;
	private Boolean flgAssConAttributoCustom;

	public BigDecimal getIdModello() {
		return idModello;
	}

	public void setIdModello(BigDecimal idModello) {
		this.idModello = idModello;
		this.getUpdatedProperties().add("idModello");
	}

	public String getNomeInput() {
		return nomeInput;
	}

	public void setNomeInput(String nomeInput) {
		this.nomeInput = nomeInput;
		this.getUpdatedProperties().add("nomeInput");
	}

	public Boolean getFlgMultivalore() {
		return flgMultivalore;
	}

	public void setFlgMultivalore(Boolean flgMultivalore) {
		this.flgMultivalore = flgMultivalore;
		this.getUpdatedProperties().add("flgMultivalore");
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
		this.getUpdatedProperties().add("attrName");
	}

	public BigDecimal getIdSpAooAttr() {
		return idSpAooAttr;
	}

	public void setIdSpAooAttr(BigDecimal idSpAooAttr) {
		this.idSpAooAttr = idSpAooAttr;
		this.getUpdatedProperties().add("idSpAooAttr");
	}

	public Boolean getFlgObbligatorio() {
		return flgObbligatorio;
	}

	public void setFlgObbligatorio(Boolean flgObbligatorio) {
		this.flgObbligatorio = flgObbligatorio;
		this.getUpdatedProperties().add("flgObbligatorio");
	}

	public Boolean getFlgCompilabile() {
		return flgCompilabile;
	}

	public void setFlgCompilabile(Boolean flgCompilabile) {
		this.flgCompilabile = flgCompilabile;
		this.getUpdatedProperties().add("flgCompilabile");
	}

	public Date getTsIns() {
		return tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
		this.getUpdatedProperties().add("tsIns");
	}

	public BigDecimal getIdUserIns() {
		return idUserIns;
	}

	public void setIdUserIns(BigDecimal idUserIns) {
		this.idUserIns = idUserIns;
		this.getUpdatedProperties().add("idUserIns");
	}

	public Date getTsLastUpd() {
		return tsLastUpd;
	}

	public void setTsLastUpd(Date tsLastUpd) {
		this.tsLastUpd = tsLastUpd;
		this.getUpdatedProperties().add("tsLastUpd");
	}

	public BigDecimal getIdUserLastUpd() {
		return idUserLastUpd;
	}

	public void setIdUserLastUpd(BigDecimal idUserLastUpd) {
		this.idUserLastUpd = idUserLastUpd;
		this.getUpdatedProperties().add("idUserLastUpd");
	}

	public String getAttrType() {
		return attrType;
	}

	public void setAttrType(String attrType) {
		this.attrType = attrType;
		this.getUpdatedProperties().add("attrType");
	}

	public BigDecimal getMaxNumValues() {
		return maxNumValues;
	}

	public void setMaxNumValues(BigDecimal maxNumValues) {
		this.maxNumValues = maxNumValues;
		this.getUpdatedProperties().add("maxNumValues");
	}

	public Boolean getFlgBarcode() {
		return flgBarcode;
	}

	public void setFlgBarcode(Boolean flgBarcode) {
		this.flgBarcode = flgBarcode;
		this.getUpdatedProperties().add("flgBarcode");
	}

	public String getTipoBarcode() {
		return tipoBarcode;
	}

	public void setTipoBarcode(String tipoBarcode) {
		this.tipoBarcode = tipoBarcode;
		this.getUpdatedProperties().add("tipoBarcode");
	}
	
	public BigDecimal getNroColonna() {
		return nroColonna;
	}

	public void setNroColonna(BigDecimal nroColonna) {
		this.nroColonna = nroColonna;
		this.getUpdatedProperties().add("nroColonna");
	}
	
	public Boolean getFlgAssConAttributoCustom() {
		return flgAssConAttributoCustom;
	}
	
	public void setFlgAssConAttributoCustom(Boolean flgAssConAttributoCustom) {
		this.flgAssConAttributoCustom = flgAssConAttributoCustom;
		this.getUpdatedProperties().add("flgAssConAttributoCustom");
	}	

}
