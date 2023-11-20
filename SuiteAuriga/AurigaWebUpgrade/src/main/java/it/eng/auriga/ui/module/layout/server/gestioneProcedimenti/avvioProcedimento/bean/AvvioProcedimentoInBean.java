/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class AvvioProcedimentoInBean {
	
	private Integer idUd;
	private Integer idFolder;
	private Integer idProcessType;
	private String nomeProcessType;
	private String flowTypeId;
	private String idDocTypeFinale;	
	private String nomeDocTypeFinale;
	private String oggettoProc;
	private String noteProc;
	
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
	public String getNomeProcessType() {
		return nomeProcessType;
	}
	public void setNomeProcessType(String nomeProcessType) {
		this.nomeProcessType = nomeProcessType;
	}
	public String getFlowTypeId() {
		return flowTypeId;
	}
	public void setFlowTypeId(String flowTypeId) {
		this.flowTypeId = flowTypeId;
	}
	public String getIdDocTypeFinale() {
		return idDocTypeFinale;
	}
	public void setIdDocTypeFinale(String idDocTypeFinale) {
		this.idDocTypeFinale = idDocTypeFinale;
	}
	public String getNomeDocTypeFinale() {
		return nomeDocTypeFinale;
	}
	public void setNomeDocTypeFinale(String nomeDocTypeFinale) {
		this.nomeDocTypeFinale = nomeDocTypeFinale;
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
	
}
