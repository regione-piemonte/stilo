/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBSalvaAllegato implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlboAVBSalvaAllegatoIn alboAvbSalvaAllegatoIn;

	public AlboAVBSalvaAllegato() {
	}

	public AlboAVBSalvaAllegato(AlboAVBSalvaAllegatoIn alboAvbSalvaAllegatoIn) {
		this.alboAvbSalvaAllegatoIn = alboAvbSalvaAllegatoIn;
	}

	public AlboAVBSalvaAllegatoIn getAlboAvbSalvaAllegatoIn() {
		return alboAvbSalvaAllegatoIn;
	}

	public void setAlboAvbSalvaAllegatoIn(AlboAVBSalvaAllegatoIn alboAvbSalvaAllegatoIn) {
		this.alboAvbSalvaAllegatoIn = alboAvbSalvaAllegatoIn;
	}
}
