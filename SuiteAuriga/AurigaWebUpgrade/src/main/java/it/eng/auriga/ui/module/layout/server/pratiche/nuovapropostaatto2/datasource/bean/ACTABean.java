/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class ACTABean {
	
	//INPUT
	private String indiceClassificazioneEstesa;
	private String descrizioneVoceTitolario;
	private String codiceFascicoloDossier;
	private String suffissoCodiceFascicoloDossier;
	private String codiceSottofascicoloDossier;
	
	//OUTPUT
	private Boolean presenzaIndiceClassificazione;
	private String idFascicoloDossier;
	
	
	public String getIndiceClassificazioneEstesa() {
		return indiceClassificazioneEstesa;
	}
	public void setIndiceClassificazioneEstesa(String indiceClassificazioneEstesa) {
		this.indiceClassificazioneEstesa = indiceClassificazioneEstesa;
	}
	public String getDescrizioneVoceTitolario() {
		return descrizioneVoceTitolario;
	}
	public void setDescrizioneVoceTitolario(String descrizioneVoceTitolario) {
		this.descrizioneVoceTitolario = descrizioneVoceTitolario;
	}
	public String getCodiceFascicoloDossier() {
		return codiceFascicoloDossier;
	}
	public void setCodiceFascicoloDossier(String codiceFascicoloDossier) {
		this.codiceFascicoloDossier = codiceFascicoloDossier;
	}
	public String getSuffissoCodiceFascicoloDossier() {
		return suffissoCodiceFascicoloDossier;
	}
	public void setSuffissoCodiceFascicoloDossier(String suffissoCodiceFascicoloDossier) {
		this.suffissoCodiceFascicoloDossier = suffissoCodiceFascicoloDossier;
	}	
	public String getCodiceSottofascicoloDossier() {
		return codiceSottofascicoloDossier;
	}
	public void setCodiceSottofascicoloDossier(String codiceSottofascicoloDossier) {
		this.codiceSottofascicoloDossier = codiceSottofascicoloDossier;
	}
	public Boolean getPresenzaIndiceClassificazione() {
		return presenzaIndiceClassificazione;
	}
	public void setPresenzaIndiceClassificazione(Boolean presenzaIndiceClassificazione) {
		this.presenzaIndiceClassificazione = presenzaIndiceClassificazione;
	}
	public String getIdFascicoloDossier() {
		return idFascicoloDossier;
	}
	public void setIdFascicoloDossier(String idFascicoloDossier) {
		this.idFascicoloDossier = idFascicoloDossier;
	}
	
}