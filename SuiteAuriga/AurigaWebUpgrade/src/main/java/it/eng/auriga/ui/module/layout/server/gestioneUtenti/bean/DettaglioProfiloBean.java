/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class DettaglioProfiloBean {
	
	private String idProfilo;
	private String nomeProfilo;
	private Boolean livMaxRiservatezza;
	private Boolean presentiPrivSuFunzioni;
	private String accessoDocIndipACL;
	private String accessoFolderIndipACL;
	private String accessoWorkspaceIndipACL;
	private String accessoDocIndipUserAbil;
	private String accessoFolderIndipUserAbil;
	private Boolean flgVisibTuttiRiservati;
	private String warning;
	
	public Boolean getLivMaxRiservatezza() {
		return livMaxRiservatezza;
	}
	public void setLivMaxRiservatezza(Boolean livMaxRiservatezza) {
		this.livMaxRiservatezza = livMaxRiservatezza;
	}
	public String getNomeProfilo() {
		return nomeProfilo;
	}
	public void setNomeProfilo(String nomeProfilo) {
		this.nomeProfilo = nomeProfilo;
	}	
	public String getIdProfilo() {
		return idProfilo;
	}
	public void setIdProfilo(String idProfilo) {
		this.idProfilo = idProfilo;
	}
	public Boolean getPresentiPrivSuFunzioni() {
		return presentiPrivSuFunzioni;
	}
	public void setPresentiPrivSuFunzioni(Boolean presentiPrivSuFunzioni) {
		this.presentiPrivSuFunzioni = presentiPrivSuFunzioni;
	}
	public String getAccessoDocIndipACL() {
		return accessoDocIndipACL;
	}
	public void setAccessoDocIndipACL(String accessoDocIndipACL) {
		this.accessoDocIndipACL = accessoDocIndipACL;
	}
	public String getAccessoFolderIndipACL() {
		return accessoFolderIndipACL;
	}
	public void setAccessoFolderIndipACL(String accessoFolderIndipACL) {
		this.accessoFolderIndipACL = accessoFolderIndipACL;
	}
	public String getAccessoWorkspaceIndipACL() {
		return accessoWorkspaceIndipACL;
	}
	public void setAccessoWorkspaceIndipACL(String accessoWorkspaceIndipACL) {
		this.accessoWorkspaceIndipACL = accessoWorkspaceIndipACL;
	}
	public String getAccessoDocIndipUserAbil() {
		return accessoDocIndipUserAbil;
	}
	public void setAccessoDocIndipUserAbil(String accessoDocIndipUserAbil) {
		this.accessoDocIndipUserAbil = accessoDocIndipUserAbil;
	}
	public String getAccessoFolderIndipUserAbil() {
		return accessoFolderIndipUserAbil;
	}
	public void setAccessoFolderIndipUserAbil(String accessoFolderIndipUserAbil) {
		this.accessoFolderIndipUserAbil = accessoFolderIndipUserAbil;
	}
	public Boolean getFlgVisibTuttiRiservati() {
		return flgVisibTuttiRiservati;
	}
	public void setFlgVisibTuttiRiservati(Boolean flgVisibTuttiRiservati) {
		this.flgVisibTuttiRiservati = flgVisibTuttiRiservati;
	}
	public String getWarning() {
		return warning;
	}
	public void setWarning(String warning) {
		this.warning = warning;
	}	

}
