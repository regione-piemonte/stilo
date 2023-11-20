/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBPubblicaAttoByEnte implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlboAVBPubblicaAttoByEnteIn alboAVBPubblicaAttoByEnteIn;

	public AlboAVBPubblicaAttoByEnte() {
	}

	public AlboAVBPubblicaAttoByEnte(AlboAVBPubblicaAttoByEnteIn alboAVBPubblicaAttoByEnteIn) {
		this.alboAVBPubblicaAttoByEnteIn = alboAVBPubblicaAttoByEnteIn;
	}

	public AlboAVBPubblicaAttoByEnteIn getAlboAVBPubblicaAttoByEnteIn() {
		return alboAVBPubblicaAttoByEnteIn;
	}

	public void setAlboAVBPubblicaAttoByEnteIn(AlboAVBPubblicaAttoByEnteIn alboAVBPubblicaAttoByEnteIn) {
		this.alboAVBPubblicaAttoByEnteIn = alboAVBPubblicaAttoByEnteIn;
	}
}
