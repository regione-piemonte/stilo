/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBPubblicaAttoByEnteResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlboAVBPubblicaAttoByEnteResponseReturn alboAVBPubblicaAttoByEnteResponseReturn;

	public AlboAVBPubblicaAttoByEnteResponse() {
	}

	public AlboAVBPubblicaAttoByEnteResponse(
			AlboAVBPubblicaAttoByEnteResponseReturn alboAVBPubblicaAttoByEnteResponseReturn) {
		this.alboAVBPubblicaAttoByEnteResponseReturn = alboAVBPubblicaAttoByEnteResponseReturn;
	}

	public AlboAVBPubblicaAttoByEnteResponseReturn getAlboAVBPubblicaAttoByEnteResponseReturn() {
		return alboAVBPubblicaAttoByEnteResponseReturn;
	}

	public void setAlboAVBPubblicaAttoByEnteResponseReturn(
			AlboAVBPubblicaAttoByEnteResponseReturn alboAVBPubblicaAttoByEnteResponseReturn) {
		this.alboAVBPubblicaAttoByEnteResponseReturn = alboAVBPubblicaAttoByEnteResponseReturn;
	}
}
