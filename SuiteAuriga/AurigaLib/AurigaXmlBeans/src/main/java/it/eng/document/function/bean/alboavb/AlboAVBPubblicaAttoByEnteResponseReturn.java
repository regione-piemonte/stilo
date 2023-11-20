/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBPubblicaAttoByEnteResponseReturn implements Serializable {

	private static final long serialVersionUID = 1L;

	private AlboAVBAtto alboAvbAtto;          

	private AlboAVBAttoError alboAvbAttoError;

	public AlboAVBPubblicaAttoByEnteResponseReturn() {
	}

	public AlboAVBPubblicaAttoByEnteResponseReturn(AlboAVBAtto alboAvbAtto, AlboAVBAttoError alboAvbAttoError) {
		this.alboAvbAtto = alboAvbAtto;
		this.alboAvbAttoError = alboAvbAttoError;
	}

	public AlboAVBAtto getAlboAvbAtto() {
		return alboAvbAtto;
	}

	public void setAlboAvbAtto(AlboAVBAtto alboAvbAtto) {
		this.alboAvbAtto = alboAvbAtto;
	}

	public AlboAVBAttoError getAlboAvbAttoError() {
		return alboAvbAttoError;
	}

	public void setAlboAvbAttoError(AlboAVBAttoError alboAvbAttoError) {
		this.alboAvbAttoError = alboAvbAttoError;
	}
}
