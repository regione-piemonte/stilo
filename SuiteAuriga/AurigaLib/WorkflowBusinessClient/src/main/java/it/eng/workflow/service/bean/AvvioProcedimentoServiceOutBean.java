/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AvvioProcedimentoServiceOutBean implements Serializable {

	private static final long serialVersionUID = -2642727322624178479L;
	
	private BigDecimal idProcesso;
	private BigDecimal idFolder;
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
