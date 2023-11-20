/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

/**
 * riferimento degli attachments
 * 
 * @author jravagnan
 * 
 */
@XmlRootElement
public class InteroperabilitaAttachmentBeanIn extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -6938539831907003794L;

	private File eml;

	private String idEmail;

	private TipoInteroperabilita tipoInteroperabilita;

	public File getEml() {
		return eml;
	}

	public void setEml(File eml) {
		this.eml = eml;
	}

	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public TipoInteroperabilita getTipoInteroperabilita() {
		return tipoInteroperabilita;
	}

	public void setTipoInteroperabilita(TipoInteroperabilita tipoInteroperabilita) {
		this.tipoInteroperabilita = tipoInteroperabilita;
	}

}
