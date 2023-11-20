/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.jaxws.webservices.deletedoc;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSDeleteUdDocBean implements Serializable {

	
	private String idUdDoc;
	private String flgTipoDel;
	private String flgTipoTarget;
	


	public String getFlgTipoDel() {
		return flgTipoDel;
	}

	public void setFlgTipoDel(String flgTipoDel) {
		this.flgTipoDel = flgTipoDel;
	}

	public String getIdUdDoc() {
		return idUdDoc;
	}

	public void setIdUdDoc(String idUdDoc) {
		this.idUdDoc = idUdDoc;
	}

	public String getFlgTipoTarget() {
		return flgTipoTarget;
	}

	public void setFlgTipoTarget(String flgTipoTarget) {
		this.flgTipoTarget = flgTipoTarget;
	}
	

	}
