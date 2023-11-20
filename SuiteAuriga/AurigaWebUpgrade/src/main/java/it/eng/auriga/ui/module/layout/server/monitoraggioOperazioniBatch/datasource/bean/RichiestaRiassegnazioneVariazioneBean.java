/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

public class RichiestaRiassegnazioneVariazioneBean implements Serializable {

	private static final long serialVersionUID = -6719820631431117639L;

	
	private String tipoOggettoRiassegnare;
	private Date dataeOraOperazione;
	
	private String organigrammaUoPostazioneDa;	
	private String organigrammaUoPostazioneVs;
	
	private String motivazioneRichiesta;
	private String noteRichiesta;
	
	private String storeErrorMessage;
	private boolean storeInError;

	
	public String getTipoOggettoRiassegnare() {
		return tipoOggettoRiassegnare;
	}
	public void setTipoOggettoRiassegnare(String tipoOggettoRiassegnare) {
		this.tipoOggettoRiassegnare = tipoOggettoRiassegnare;
	}
	public Date getDataeOraOperazione() {
		return dataeOraOperazione;
	}
	public void setDataeOraOperazione(Date dataeOraOperazione) {
		this.dataeOraOperazione = dataeOraOperazione;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getOrganigrammaUoPostazioneDa() {
		return organigrammaUoPostazioneDa;
	}
	public void setOrganigrammaUoPostazioneDa(String organigrammaUoPostazioneDa) {
		this.organigrammaUoPostazioneDa = organigrammaUoPostazioneDa;
	}
	public String getOrganigrammaUoPostazioneVs() {
		return organigrammaUoPostazioneVs;
	}
	public void setOrganigrammaUoPostazioneVs(String organigrammaUoPostazioneVs) {
		this.organigrammaUoPostazioneVs = organigrammaUoPostazioneVs;
	}
	public String getMotivazioneRichiesta() {
		return motivazioneRichiesta;
	}
	public void setMotivazioneRichiesta(String motivazioneRichiesta) {
		this.motivazioneRichiesta = motivazioneRichiesta;
	}
	public String getNoteRichiesta() {
		return noteRichiesta;
	}
	public void setNoteRichiesta(String noteRichiesta) {
		this.noteRichiesta = noteRichiesta;
	}
	public String getStoreErrorMessage() {
		return storeErrorMessage;
	}
	public void setStoreErrorMessage(String storeErrorMessage) {
		this.storeErrorMessage = storeErrorMessage;
	}
	public boolean isStoreInError() {
		return storeInError;
	}
	public void setStoreInError(boolean storeInError) {
		this.storeInError = storeInError;
	}

}
