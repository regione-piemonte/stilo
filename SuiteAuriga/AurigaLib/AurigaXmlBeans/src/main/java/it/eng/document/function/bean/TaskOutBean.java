/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.function.bean.Flag;

@XmlRootElement
public class TaskOutBean {
	
	@NumeroColonna(numero = "1")
	private String instanceId;
	@NumeroColonna(numero = "2")
	private String instanceLabel;
	@NumeroColonna(numero = "3")
	private String activityName;	
	@NumeroColonna(numero = "4")	
	private String displayName;		
	@NumeroColonna(numero = "5")
	private Flag flgEseguibile;
	@NumeroColonna(numero = "6")
	private String motiviNonEseg;
	@NumeroColonna(numero = "7")
	private Flag flgDatiVisibili;
	@NumeroColonna(numero = "8")
	private Flag flgFatta;
	@NumeroColonna(numero = "9")
	private String idChildProcess;
	@NumeroColonna(numero = "10")
	private String estremiChildProcess;
	@NumeroColonna(numero = "11")
	private String rowIdEvento;	
	
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
	public Flag getFlgEseguibile() {
		return flgEseguibile;
	}
	public void setFlgEseguibile(Flag flgEseguibile) {
		this.flgEseguibile = flgEseguibile;
	}
	public String getMotiviNonEseg() {
		return motiviNonEseg;
	}
	public void setMotiviNonEseg(String motiviNonEseg) {
		this.motiviNonEseg = motiviNonEseg;
	}
	public Flag getFlgDatiVisibili() {
		return flgDatiVisibili;
	}
	public void setFlgDatiVisibili(Flag flgDatiVisibili) {
		this.flgDatiVisibili = flgDatiVisibili;
	}
	public Flag getFlgFatta() {
		return flgFatta;
	}
	public void setFlgFatta(Flag flgFatta) {
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
	
}
