/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class CaricaDocumentoBean {
	
	String wsEndpoint = "";
	String serviceName = "";
	String operationName = "";
	String actionURI = "";
	
	public String getWsEndpoint() {
		return wsEndpoint;
	}
	public void setWsEndpoint(String wsEndpoint) {
		this.wsEndpoint = wsEndpoint;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getActionURI() {
		return actionURI;
	}
	public void setActionURI(String actionURI) {
		this.actionURI = actionURI;
	}
	
}
