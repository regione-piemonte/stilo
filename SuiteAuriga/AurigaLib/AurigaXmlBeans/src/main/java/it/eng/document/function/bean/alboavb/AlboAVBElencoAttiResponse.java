/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBElencoAttiResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlboAVBElencoAttiResponseReturn alboAVBElencoAttiResponseReturn;

	public AlboAVBElencoAttiResponse() {
	}

	public AlboAVBElencoAttiResponse(AlboAVBElencoAttiResponseReturn alboAVBElencoAttiResponseReturn) {
		this.alboAVBElencoAttiResponseReturn = alboAVBElencoAttiResponseReturn;
	}

	public AlboAVBElencoAttiResponseReturn getAlboAVBElencoAttiResponseReturn() {
		return alboAVBElencoAttiResponseReturn;
	}

	public void setAlboAVBElencoAttiResponseReturn(AlboAVBElencoAttiResponseReturn alboAVBElencoAttiResponseReturn) {
		this.alboAVBElencoAttiResponseReturn = alboAVBElencoAttiResponseReturn;
	}
}
