/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class DmtProcess {
	
	private String idProcess;
	
	private String deploymentName;
	
	private String instanceId;

	public String getDeploymentName() {
		return deploymentName;
	}

	public void setDeploymentName(String deploymentName) {
		this.deploymentName = deploymentName;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getIdProcess() {
		return idProcess;
	}

	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}

}
