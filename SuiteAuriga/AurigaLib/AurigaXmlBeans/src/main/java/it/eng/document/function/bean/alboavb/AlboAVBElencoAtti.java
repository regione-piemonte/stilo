/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBElencoAtti implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlboAVBElencoAttiIn alboAVBElencoAttiIn;

	public AlboAVBElencoAtti() {
	}

	public AlboAVBElencoAtti(AlboAVBElencoAttiIn alboAVBElencoAttiIn) {
		this.alboAVBElencoAttiIn = alboAVBElencoAttiIn;
	}

	public AlboAVBElencoAttiIn getAlboAVBElencoAttiIn() {
		return alboAVBElencoAttiIn;
	}

	public void setAlboAVBElencoAttiIn(AlboAVBElencoAttiIn alboAVBElencoAttiIn) {
		this.alboAVBElencoAttiIn = alboAVBElencoAttiIn;
	}
}
