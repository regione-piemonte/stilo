/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBDettaglioAttoResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlboAVBDettaglioAttoResponseReturn alboAVBDettaglioAttoResponseReturn;

	public AlboAVBDettaglioAttoResponse() {
	}

	public AlboAVBDettaglioAttoResponse(AlboAVBDettaglioAttoResponseReturn alboAVBDettaglioAttoResponseReturn) {
		this.alboAVBDettaglioAttoResponseReturn = alboAVBDettaglioAttoResponseReturn;
	}

	public AlboAVBDettaglioAttoResponseReturn getAlboAVBDettaglioAttoResponseReturn() {
		return alboAVBDettaglioAttoResponseReturn;
	}

	public void setAlboAVBDettaglioAttoResponseReturn(
			AlboAVBDettaglioAttoResponseReturn alboAVBDettaglioAttoResponseReturn) {
		this.alboAVBDettaglioAttoResponseReturn = alboAVBDettaglioAttoResponseReturn;
	}
}
