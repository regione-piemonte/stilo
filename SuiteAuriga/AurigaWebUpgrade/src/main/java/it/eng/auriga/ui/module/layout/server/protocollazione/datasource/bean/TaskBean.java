/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */




public class TaskBean {

	private String instanceId;
	private String instanceLabel;
	private String activityName;	
	private String displayName;		
	private Boolean flgEseguibile;
	private String motiviNonEseg;
	private Boolean flgDatiVisibili;
	private Boolean flgFatta;
	private String idChildProcess;
	private String estremiChildProcess;	
	private String rowIdEvento;	
	private String idProcess;
	
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getInstanceLabel() {
		return instanceLabel;
	}
	public void setInstanceLabel(String instanceLabel) {
		this.instanceLabel = instanceLabel;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public Boolean getFlgEseguibile() {
		return flgEseguibile;
	}
	public void setFlgEseguibile(Boolean flgEseguibile) {
		this.flgEseguibile = flgEseguibile;
	}
	public String getMotiviNonEseg() {
		return motiviNonEseg;
	}
	public void setMotiviNonEseg(String motiviNonEseg) {
		this.motiviNonEseg = motiviNonEseg;
	}
	public Boolean getFlgDatiVisibili() {
		return flgDatiVisibili;
	}
	public void setFlgDatiVisibili(Boolean flgDatiVisibili) {
		this.flgDatiVisibili = flgDatiVisibili;
	}
	public Boolean getFlgFatta() {
		return flgFatta;
	}
	public void setFlgFatta(Boolean flgFatta) {
		this.flgFatta = flgFatta;
	}
	public String getIdChildProcess() {
		return idChildProcess;
	}
	public void setIdChildProcess(String idChildProcess) {
		this.idChildProcess = idChildProcess;
	}
	public String getEstremiChildProcess() {
		return estremiChildProcess;
	}
	public void setEstremiChildProcess(String estremiChildProcess) {
		this.estremiChildProcess = estremiChildProcess;
	}
	public String getRowIdEvento() {
		return rowIdEvento;
	}
	public void setRowIdEvento(String rowIdEvento) {
		this.rowIdEvento = rowIdEvento;
	}
	public String getIdProcess() {
		return idProcess;
	}
	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}
	
}
