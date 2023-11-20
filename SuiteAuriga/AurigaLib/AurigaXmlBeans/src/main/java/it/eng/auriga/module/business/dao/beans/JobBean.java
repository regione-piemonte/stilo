/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JobBean extends AbstractBean implements java.io.Serializable {

	private static final long serialVersionUID = 2218190035306283594L;
	
	private BigDecimal idJob;
	private BigDecimal idSpAoo;
	private String codApplOwner;
	private String codIstApplOwner;
	private String idUser;
	private String parametri;
	private String tipo;
	private String formato;
	private Date submitTime;
	private Date scheduleTime;
	private Date startTime;
	private Date endTime;
	private String status;
	private String connString;
	private String connDriver;
	private BigDecimal idDoc;
	private String message;
	private String exportFilename;
	private Boolean priorita;
	private Boolean flgXBassaOper;
	private Date tsInvioInCons;
	
	public BigDecimal getIdJob() {
		return idJob;
	}
	public void setIdJob(BigDecimal idJob) {
		this.idJob = idJob;
		this.getUpdatedProperties().add("idJob");
	}
	public BigDecimal getIdSpAoo() {
		return idSpAoo;
	}
	public void setIdSpAoo(BigDecimal idSpAoo) {
		this.idSpAoo = idSpAoo;
		this.getUpdatedProperties().add("idSpAoo");
	}
	public String getCodApplOwner() {
		return codApplOwner;
	}
	public void setCodApplOwner(String codApplOwner) {
		this.codApplOwner = codApplOwner;
		this.getUpdatedProperties().add("codApplOwner");
	}
	public String getCodIstApplOwner() {
		return codIstApplOwner;
	}
	public void setCodIstApplOwner(String codIstApplOwner) {
		this.codIstApplOwner = codIstApplOwner;
		this.getUpdatedProperties().add("codIstApplOwner");
	}
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
		this.getUpdatedProperties().add("idUser");
	}
	public String getParametri() {
		return parametri;
	}
	public void setParametri(String parametri) {
		this.parametri = parametri;
		this.getUpdatedProperties().add("parametri");
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
		this.getUpdatedProperties().add("tipo");
	}
	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
		this.getUpdatedProperties().add("formato");
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
		this.getUpdatedProperties().add("submitTime");
	}
	public Date getScheduleTime() {
		return scheduleTime;
	}
	public void setScheduleTime(Date scheduleTime) {
		this.scheduleTime = scheduleTime;
		this.getUpdatedProperties().add("scheduleTime");
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
		this.getUpdatedProperties().add("startTime");
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
		this.getUpdatedProperties().add("endTime");
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
		this.getUpdatedProperties().add("status");
	}
	public String getConnString() {
		return connString;
	}
	public void setConnString(String connString) {
		this.connString = connString;
		this.getUpdatedProperties().add("connString");
	}
	public String getConnDriver() {
		return connDriver;
	}
	public void setConnDriver(String connDriver) {
		this.connDriver = connDriver;
		this.getUpdatedProperties().add("connDriver");
	}
	public BigDecimal getIdDoc() {
		return idDoc;
	}
	public void setIdDoc(BigDecimal idDoc) {
		this.idDoc = idDoc;
		this.getUpdatedProperties().add("idDoc");
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
		this.getUpdatedProperties().add("message");
	}
	public String getExportFilename() {
		return exportFilename;
	}
	public void setExportFilename(String exportFilename) {
		this.exportFilename = exportFilename;
		this.getUpdatedProperties().add("exportFilename");
	}
	public Boolean getPriorita() {
		return priorita;
	}
	public void setPriorita(Boolean priorita) {
		this.priorita = priorita;
		this.getUpdatedProperties().add("priorita");
	}
	public Boolean getFlgXBassaOper() {
		return flgXBassaOper;
	}
	public void setFlgXBassaOper(Boolean flgXBassaOper) {
		this.flgXBassaOper = flgXBassaOper;
		this.getUpdatedProperties().add("flgXBassaOper");
	}
	public Date getTsInvioInCons() {
		return tsInvioInCons;
	}
	public void setTsInvioInCons(Date tsInvioInCons) {
		this.tsInvioInCons = tsInvioInCons;
		this.getUpdatedProperties().add("tsInvioInCons");
	}

}
