/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * bean che rappresenta la voce di rubrica da inserire o updatare e su cui effettuare i controlli
 * 
 * @author jravagnan
 * 
 */
@XmlRootElement
public class VoceRubricaBeanIn  extends AbstractBean implements Serializable{

	private static final long serialVersionUID = -8300857589131343413L;

	private TRubricaEmailBean voceRubrica;

	private String idFruitore;

	public TRubricaEmailBean getVoceRubrica() {
		return voceRubrica;
	}

	public void setVoceRubrica(TRubricaEmailBean voceRubrica) {
		this.voceRubrica = voceRubrica;
	}

	public String getIdFruitore() {
		return idFruitore;
	}

	public void setIdFruitore(String idFruitore) {
		this.idFruitore = idFruitore;
	}

}
