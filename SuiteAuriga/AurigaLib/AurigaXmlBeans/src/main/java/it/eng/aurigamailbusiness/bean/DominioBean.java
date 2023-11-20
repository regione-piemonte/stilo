/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * classe di ingresso ai metodi di riconoscimento fruitore casella
 * @author jravagnan
 *
 */
@XmlRootElement
public class DominioBean extends AbstractBean implements Serializable{

	private static final long serialVersionUID = 2219977861218598219L;

	private String idDominio;

	public String getIdDominio() {
		return idDominio;
	}

	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}


	
}
