/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

public class OperPrelievoSuFascArchivioBean {

	private boolean success;
	private String errorMessage;

	private String idUdFolder;
	private String codOperPrelievoSuFascArchivio;

	private String datiPrelievoDaArchivioCodUO;
	private String datiPrelievoDaArchivioIdUO;
	private String datiPrelievoDaArchivioDesUO;
	private Date datiPrelievoDaArchivioDataPrelievo;

	private String datiPrelievoDaArchivioIdUserResponsabile;
	private String datiPrelievoDaArchivioUsernameResponsabile;
	private String datiPrelievoDaArchivioCognomeResponsabile;
	private String datiPrelievoDaArchivioNomeResponsabile;
	private String datiPrelievoDaArchivioNoteRichiedente;
	private String datiPrelievoDaArchivioNoteArchivio;
	private String datiPrelievoDaArchivioclassifica;

	private Date datiPrelievoDaArchivioDataRestituzionePrelievo;

	private String motiviPrelievoSuFascArchivio;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getIdUdFolder() {
		return idUdFolder;
	}

	public void setIdUdFolder(String idUdFolder) {
		this.idUdFolder = idUdFolder;
	}

	public String getCodOperPrelievoSuFascArchivio() {
		return codOperPrelievoSuFascArchivio;
	}

	public void setCodOperPrelievoSuFascArchivio(String codOperPrelievoSuFascArchivio) {
		this.codOperPrelievoSuFascArchivio = codOperPrelievoSuFascArchivio;
	}

	public String getDatiPrelievoDaArchivioCodUO() {
		return datiPrelievoDaArchivioCodUO;
	}

	public void setDatiPrelievoDaArchivioCodUO(String datiPrelievoDaArchivioCodUO) {
		this.datiPrelievoDaArchivioCodUO = datiPrelievoDaArchivioCodUO;
	}

	public String getDatiPrelievoDaArchivioIdUO() {
		return datiPrelievoDaArchivioIdUO;
	}

	public void setDatiPrelievoDaArchivioIdUO(String datiPrelievoDaArchivioIdUO) {
		this.datiPrelievoDaArchivioIdUO = datiPrelievoDaArchivioIdUO;
	}

	public String getDatiPrelievoDaArchivioDesUO() {
		return datiPrelievoDaArchivioDesUO;
	}

	public void setDatiPrelievoDaArchivioDesUO(String datiPrelievoDaArchivioDesUO) {
		this.datiPrelievoDaArchivioDesUO = datiPrelievoDaArchivioDesUO;
	}

	public Date getDatiPrelievoDaArchivioDataPrelievo() {
		return datiPrelievoDaArchivioDataPrelievo;
	}

	public void setDatiPrelievoDaArchivioDataPrelievo(Date datiPrelievoDaArchivioDataPrelievo) {
		this.datiPrelievoDaArchivioDataPrelievo = datiPrelievoDaArchivioDataPrelievo;
	}

	public String getDatiPrelievoDaArchivioIdUserResponsabile() {
		return datiPrelievoDaArchivioIdUserResponsabile;
	}

	public void setDatiPrelievoDaArchivioIdUserResponsabile(String datiPrelievoDaArchivioIdUserResponsabile) {
		this.datiPrelievoDaArchivioIdUserResponsabile = datiPrelievoDaArchivioIdUserResponsabile;
	}
	
	public String getDatiPrelievoDaArchivioUsernameResponsabile() {
		return datiPrelievoDaArchivioUsernameResponsabile;
	}

	public void setDatiPrelievoDaArchivioUsernameResponsabile(String datiPrelievoDaArchivioUsernameResponsabile) {
		this.datiPrelievoDaArchivioUsernameResponsabile = datiPrelievoDaArchivioUsernameResponsabile;
	}

	public String getDatiPrelievoDaArchivioCognomeResponsabile() {
		return datiPrelievoDaArchivioCognomeResponsabile;
	}

	public void setDatiPrelievoDaArchivioCognomeResponsabile(String datiPrelievoDaArchivioCognomeResponsabile) {
		this.datiPrelievoDaArchivioCognomeResponsabile = datiPrelievoDaArchivioCognomeResponsabile;
	}

	public String getDatiPrelievoDaArchivioNomeResponsabile() {
		return datiPrelievoDaArchivioNomeResponsabile;
	}

	public void setDatiPrelievoDaArchivioNomeResponsabile(String datiPrelievoDaArchivioNomeResponsabile) {
		this.datiPrelievoDaArchivioNomeResponsabile = datiPrelievoDaArchivioNomeResponsabile;
	}

	public String getDatiPrelievoDaArchivioNoteRichiedente() {
		return datiPrelievoDaArchivioNoteRichiedente;
	}

	public void setDatiPrelievoDaArchivioNoteRichiedente(String datiPrelievoDaArchivioNoteRichiedente) {
		this.datiPrelievoDaArchivioNoteRichiedente = datiPrelievoDaArchivioNoteRichiedente;
	}

	public String getDatiPrelievoDaArchivioNoteArchivio() {
		return datiPrelievoDaArchivioNoteArchivio;
	}

	public void setDatiPrelievoDaArchivioNoteArchivio(String datiPrelievoDaArchivioNoteArchivio) {
		this.datiPrelievoDaArchivioNoteArchivio = datiPrelievoDaArchivioNoteArchivio;
	}

	public String getDatiPrelievoDaArchivioclassifica() {
		return datiPrelievoDaArchivioclassifica;
	}

	public void setDatiPrelievoDaArchivioclassifica(String datiPrelievoDaArchivioclassifica) {
		this.datiPrelievoDaArchivioclassifica = datiPrelievoDaArchivioclassifica;
	}

	public Date getDatiPrelievoDaArchivioDataRestituzionePrelievo() {
		return datiPrelievoDaArchivioDataRestituzionePrelievo;
	}

	public void setDatiPrelievoDaArchivioDataRestituzionePrelievo(Date datiPrelievoDaArchivioDataRestituzionePrelievo) {
		this.datiPrelievoDaArchivioDataRestituzionePrelievo = datiPrelievoDaArchivioDataRestituzionePrelievo;
	}

	public String getMotiviPrelievoSuFascArchivio() {
		return motiviPrelievoSuFascArchivio;
	}

	public void setMotiviPrelievoSuFascArchivio(String motiviPrelievoSuFascArchivio) {
		this.motiviPrelievoSuFascArchivio = motiviPrelievoSuFascArchivio;
	}

}
