/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

public class AvvioIterAttiOutBean {
	
	private BigDecimal idProcesso;
	private BigDecimal idFolder;
	private BigDecimal idUd;
	private String estremiRegNumUd;	
	private String processInstanceId;
	private String processDefinitionId;
	private Boolean esito;
	private String error;
	
	public BigDecimal getIdProcesso() {
		return idProcesso;
	}
	public void setIdProcesso(BigDecimal idProcesso) {
		this.idProcesso = idProcesso;
	}
	public Boolean getEsito() {
		return esito;
	}
	public void setEsito(Boolean esito) {
		this.esito = esito;
	}
	public BigDecimal getIdFolder() {
		return idFolder;
	}
	public void setIdFolder(BigDecimal idFolder) {
		this.idFolder = idFolder;
	}
	public BigDecimal getIdUd() {
		return idUd;
	}
	public void setIdUd(BigDecimal idUd) {
		this.idUd = idUd;
	}
	public String getEstremiRegNumUd() {
		return estremiRegNumUd;
	}
	public void setEstremiRegNumUd(String estremiRegNumUd) {
		this.estremiRegNumUd = estremiRegNumUd;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
}
