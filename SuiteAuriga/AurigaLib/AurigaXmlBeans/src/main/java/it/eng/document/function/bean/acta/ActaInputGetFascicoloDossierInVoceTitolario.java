/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ActaInputGetFascicoloDossierInVoceTitolario implements Serializable {

	private static final long serialVersionUID = 1L;

	private String aooCode;
	private String structurCode;
	private String descrizioneVoceTitolario;
	private String codiceFascicoloDossier;
	private String suffissoCodiceFascicoloDossier;
	private String numeroSottofascicolo;

	public String getAooCode() {
		return aooCode;
	}

	public void setAooCode(String aooCode) {
		this.aooCode = aooCode;
	}

	public String getStructurCode() {
		return structurCode;
	}

	public void setStructurCode(String structurCode) {
		this.structurCode = structurCode;
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

	public String getNumeroSottofascicolo() {
		return numeroSottofascicolo;
	}

	public void setNumeroSottofascicolo(String numeroSottofascicolo) {
		this.numeroSottofascicolo = numeroSottofascicolo;
	}

}
