/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

/**
 * classe che contiene tutte le voci rubrica
 * rispondenti a determinati criteri di ricerca/aggiornamento
 * @author jravagnan
 *
 */
@XmlRootElement
public class RubricaOutBean extends AbstractBean {

	private static final long serialVersionUID = -2138186163284080953L;
	
	private List<TRubricaEmailBean> vociRubrica;

	public List<TRubricaEmailBean> getVociRubrica() {
		return vociRubrica;
	}

	public void setVociRubrica(List<TRubricaEmailBean> vociRubrica) {
		this.vociRubrica = vociRubrica;
	}

}
