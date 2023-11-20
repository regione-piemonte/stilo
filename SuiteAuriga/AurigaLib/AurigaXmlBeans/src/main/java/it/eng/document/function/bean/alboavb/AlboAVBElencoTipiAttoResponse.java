/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBElencoTipiAttoResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlboAVBElencoTipiAttoResponseReturn alboAVBElencoTipiAttoResponseReturn;

	public AlboAVBElencoTipiAttoResponse() {
	}

	public AlboAVBElencoTipiAttoResponse(AlboAVBElencoTipiAttoResponseReturn alboAVBElencoTipiAttoResponseReturn) {
		this.alboAVBElencoTipiAttoResponseReturn = alboAVBElencoTipiAttoResponseReturn;
	}

	public AlboAVBElencoTipiAttoResponseReturn getAlboAVBElencoTipiAttoResponseReturn() {
		return alboAVBElencoTipiAttoResponseReturn;
	}

	public void setAlboAVBElencoTipiAttoResponseReturn(
			AlboAVBElencoTipiAttoResponseReturn alboAVBElencoTipiAttoResponseReturn) {
		this.alboAVBElencoTipiAttoResponseReturn = alboAVBElencoTipiAttoResponseReturn;
	}
}
