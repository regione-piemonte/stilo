/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBPubblicaAtto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlboAVBPubblicaAttoIn alboAvbPubblicaAttoIn;

	public AlboAVBPubblicaAtto() {
	}

	public AlboAVBPubblicaAtto(AlboAVBPubblicaAttoIn alboAvbPubblicaAttoIn) {
		this.alboAvbPubblicaAttoIn = alboAvbPubblicaAttoIn;
	}

	public AlboAVBPubblicaAttoIn getAlboAvbPubblicaAttoIn() {
		return alboAvbPubblicaAttoIn;
	}

	public void setAlboAvbPubblicaAttoIn(AlboAVBPubblicaAttoIn alboAvbPubblicaAttoIn) {
		this.alboAvbPubblicaAttoIn = alboAvbPubblicaAttoIn;
	}
}
