/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

/**
 * classe che raggruppa degli idmail
 * 
 * @author jravagnan
 * 
 */
@XmlRootElement
public class EmailGroupBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 7811255048616558080L;

	private String nameGroup;

	private List<String> idEmails;

	private String classificazioneFrom;

	private Classificazione classificazioneTo;

	public String getNameGroup() {
		return nameGroup;
	}

	public void setNameGroup(String nameGroup) {
		this.nameGroup = nameGroup;
	}

	public List<String> getIdEmails() {
		return idEmails;
	}

	public void setIdEmails(List<String> idEmails) {
		this.idEmails = idEmails;
	}

	public String getClassificazioneFrom() {
		return classificazioneFrom;
	}

	public void setClassificazioneFrom(String classificazioneFrom) {
		this.classificazioneFrom = classificazioneFrom;
	}

	public Classificazione getClassificazioneTo() {
		return classificazioneTo;
	}

	public void setClassificazioneTo(Classificazione classificazioneTo) {
		this.classificazioneTo = classificazioneTo;
	}

}
