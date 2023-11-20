/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.core.business.beans.AbstractBean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * classe rappresentante una ML
 * 
 * @author jravagnan
 * 
 */
@XmlRootElement
public class MailingListBean extends AbstractBean {

	private static final long serialVersionUID = -2138186163284080953L;

	private TRubricaEmailBean voceMailingList;

	private List<String> idVoceRubricaMembri;

	public TRubricaEmailBean getVoceMailingList() {
		return voceMailingList;
	}

	public void setVoceMailingList(TRubricaEmailBean voceMailingList) {
		this.voceMailingList = voceMailingList;
	}

	public List<String> getIdVoceRubricaMembri() {
		return idVoceRubricaMembri;
	}

	public void setIdVoceRubricaMembri(List<String> idVoceRubricaMembri) {
		this.idVoceRubricaMembri = idVoceRubricaMembri;
	}

}
