/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AvvioProcedimentoServiceInBean implements Serializable {

	private static final long serialVersionUID = -321364154455285500L;
	
	private Integer idUd;
	private Integer idFolder;
	private Integer idProcessType;
	private String flowTypeId;
	private String oggettoProc;
	private String noteProc;
	private Integer idProcessIo;
	
	public Integer getIdUd() {
		return idUd;
	}
	public void setIdUd(Integer idUd) {
		this.idUd = idUd;
	}
	public Integer getIdFolder() {
		return idFolder;
	}
	public void setIdFolder(Integer idFolder) {
		this.idFolder = idFolder;
	}
	public Integer getIdProcessType() {
		return idProcessType;
	}
	public void setIdProcessType(Integer idProcessType) {
		this.idProcessType = idProcessType;
	}
	public String getFlowTypeId() {
		return flowTypeId;
	}
	public void setFlowTypeId(String flowTypeId) {
		this.flowTypeId = flowTypeId;
	}
	public String getOggettoProc() {
		return oggettoProc;
	}
	public void setOggettoProc(String oggettoProc) {
		this.oggettoProc = oggettoProc;
	}
	public String getNoteProc() {
		return noteProc;
	}
	public void setNoteProc(String noteProc) {
		this.noteProc = noteProc;
	}
	public Integer getIdProcessIo() {
		return idProcessIo;
	}
	public void setIdProcessIo(Integer idProcessIo) {
		this.idProcessIo = idProcessIo;
	}
	
	
}
