/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBSalvaAllegatoResponseReturn implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlboAVBAttoError alboAvbAttoError;

	public AlboAVBSalvaAllegatoResponseReturn() {
	}

	public AlboAVBSalvaAllegatoResponseReturn(AlboAVBAttoError alboAvbAttoError) {
		this.alboAvbAttoError = alboAvbAttoError;
	}

	public AlboAVBAttoError getAlboAvbAttoError() {
		return alboAvbAttoError;
	}

	public void setAlboAvbAttoError(AlboAVBAttoError alboAvbAttoError) {
		this.alboAvbAttoError = alboAvbAttoError;
	}
}
