/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBPubblicaAttoResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlboAVBPubblicaAttoResponseReturn alboAvbPubblicaAttoResponseReturn;

	public AlboAVBPubblicaAttoResponse() {
	}

	public AlboAVBPubblicaAttoResponse(AlboAVBPubblicaAttoResponseReturn alboAvbPubblicaAttoResponseReturn) {
		this.alboAvbPubblicaAttoResponseReturn = alboAvbPubblicaAttoResponseReturn;
	}

	public AlboAVBPubblicaAttoResponseReturn getAlboAvbPubblicaAttoResponseReturn() {
		return alboAvbPubblicaAttoResponseReturn;
	}

	public void setAlboAvbPubblicaAttoResponseReturn(AlboAVBPubblicaAttoResponseReturn alboAvbPubblicaAttoResponseReturn) {
		this.alboAvbPubblicaAttoResponseReturn = alboAvbPubblicaAttoResponseReturn;
	}
}
