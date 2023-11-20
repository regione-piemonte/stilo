/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * classe di ricerca per le voci rubrica
 * 
 * @author jravagnan
 * 
 */
@XmlRootElement
public class RubricaSearchBean extends AbstractBean {

	private static final long serialVersionUID = -2138186163284080953L;

	private String descrizioneVoce;

	private String accountEmail;

	private Boolean soloPec=false;

	private Boolean ancheMailingList=false;
	
	private String idFruitore;
	
	private Boolean ancheAnnullate=false;

	public String getDescrizioneVoce() {
		return descrizioneVoce;
	}

	public void setDescrizioneVoce(String descrizioneVoce) {
		this.descrizioneVoce = descrizioneVoce;
	}

	public String getAccountEmail() {
		return accountEmail;
	}

	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
	}

	public Boolean getSoloPec() {
		return soloPec;
	}

	public void setSoloPec(Boolean soloPec) {
		this.soloPec = soloPec;
	}

	public Boolean getAncheMailingList() {
		return ancheMailingList;
	}

	public void setAncheMailingList(Boolean ancheMailingList) {
		this.ancheMailingList = ancheMailingList;
	}

	public String getIdFruitore() {
		return idFruitore;
	}

	public void setIdFruitore(String idFruitore) {
		this.idFruitore = idFruitore;
	}

	public Boolean getAncheAnnullate() {
		return ancheAnnullate;
	}

	public void setAncheAnnullate(Boolean ancheAnnullate) {
		this.ancheAnnullate = ancheAnnullate;
	}

}
