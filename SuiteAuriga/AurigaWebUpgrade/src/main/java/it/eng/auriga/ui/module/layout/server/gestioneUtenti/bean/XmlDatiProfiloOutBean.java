/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class XmlDatiProfiloOutBean {
	
	@XmlVariabile(nome="LivMaxRiservatezza", tipo=TipoVariabile.SEMPLICE)
	private String livMaxRiservatezza;
	@XmlVariabile(nome="FlgVisibTuttiRiservati", tipo=TipoVariabile.SEMPLICE)
	private String flgVisibTuttiRiservati;	
	@XmlVariabile(nome="AccessoDocIndipACL", tipo=TipoVariabile.SEMPLICE)
	private String accessoDocIndipACL;
	@XmlVariabile(nome="AccessoFolderIndipACL", tipo=TipoVariabile.SEMPLICE)
	private String accessoFolderIndipACL;
	@XmlVariabile(nome="AccessoWorkspaceIndipACL", tipo=TipoVariabile.SEMPLICE)
	private String accessoWorkspaceIndipACL;
	@XmlVariabile(nome="AccessoDocIndipUserAbil", tipo=TipoVariabile.SEMPLICE)
	private String accessoDocIndipUserAbil;
	@XmlVariabile(nome="AccessoFolderIndipUserAbil", tipo=TipoVariabile.SEMPLICE)
	private String accessoFolderIndipUserAbil;
	@XmlVariabile(nome="PresentiPrivSuFunzioni", tipo=TipoVariabile.SEMPLICE)
	private String presentiPrivSuFunzioni;
	
	public String getLivMaxRiservatezza() {
		return livMaxRiservatezza;
	}
	public void setLivMaxRiservatezza(String livMaxRiservatezza) {
		this.livMaxRiservatezza = livMaxRiservatezza;
	}
	public String getFlgVisibTuttiRiservati() {
		return flgVisibTuttiRiservati;
	}
	public void setFlgVisibTuttiRiservati(String flgVisibTuttiRiservati) {
		this.flgVisibTuttiRiservati = flgVisibTuttiRiservati;
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
	public String getPresentiPrivSuFunzioni() {
		return presentiPrivSuFunzioni;
	}
	public void setPresentiPrivSuFunzioni(String presentiPrivSuFunzioni) {
		this.presentiPrivSuFunzioni = presentiPrivSuFunzioni;
	}
	 	
}
