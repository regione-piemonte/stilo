/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabilitaAdspUpdateStatoDocumentoResponse extends ContabilitaAdspResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer kStato;

	public Integer getkStato() {
		return kStato;
	}

	public void setkStato(Integer kStato) {
		this.kStato = kStato;
	}

	@Override
	public String toString() {
		return "ContabilitaAdspUpdateStatoDocumentoResponse [kStato=" + kStato + "]";
	}
	
}
