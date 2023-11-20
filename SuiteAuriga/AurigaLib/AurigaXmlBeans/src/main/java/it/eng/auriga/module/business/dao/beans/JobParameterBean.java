/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import it.eng.core.business.beans.AbstractBean;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JobParameterBean extends AbstractBean implements java.io.Serializable {

	private static final long serialVersionUID = -5976876286460761265L;
	
	private BigDecimal idJob;
	private BigDecimal parameterId;
	private String parameterValue;	
	private String parameterDir;
	private String parameterType;
	private String parameterSubtype;

	public BigDecimal getIdJob() {
		return idJob;
	}
	public void setIdJob(BigDecimal idJob) {
		this.idJob = idJob;
		this.getUpdatedProperties().add("idJob");
	}
	public BigDecimal getParameterId() {
		return parameterId;
	}
	public void setParameterId(BigDecimal parameterId) {
		this.parameterId = parameterId;
		this.getUpdatedProperties().add("parameterId");
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
		this.getUpdatedProperties().add("parameterValue");
	}
	public String getParameterDir() {
		return parameterDir;
	}
	public void setParameterDir(String parameterDir) {
		this.parameterDir = parameterDir;
		this.getUpdatedProperties().add("parameterDir");
	}
	public String getParameterType() {
		return parameterType;
	}
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
		this.getUpdatedProperties().add("parameterType");
	}
	public String getParameterSubtype() {
		return parameterSubtype;
	}
	public void setParameterSubtype(String parameterSubtype) {
		this.parameterSubtype = parameterSubtype;
		this.getUpdatedProperties().add("parameterSubtype");
	}
	
}
