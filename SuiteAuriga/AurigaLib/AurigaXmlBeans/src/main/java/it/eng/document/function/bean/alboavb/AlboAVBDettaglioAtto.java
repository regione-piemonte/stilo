/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBDettaglioAtto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AlboAVBDettaglioAttoIn alboAVBDettaglioAttoIn;

	public AlboAVBDettaglioAtto() {
	}

	public AlboAVBDettaglioAtto(AlboAVBDettaglioAttoIn alboAVBDettaglioAttoIn) {
		this.alboAVBDettaglioAttoIn = alboAVBDettaglioAttoIn;
	}

	public AlboAVBDettaglioAttoIn getAlboAVBDettaglioAttoIn() {
		return alboAVBDettaglioAttoIn;
	}

	public void setAlboAVBDettaglioAttoIn(AlboAVBDettaglioAttoIn alboAVBDettaglioAttoIn) {
		this.alboAVBDettaglioAttoIn = alboAVBDettaglioAttoIn;
	}
}
