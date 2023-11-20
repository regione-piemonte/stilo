/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBElencoAttiResponseReturn implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlboAVBElencoAttiDto alboAVBElencoAttiDto;
	
	private AlboAVBAttoError alboAvbAttoError;

	public AlboAVBElencoAttiResponseReturn() {
	}

	public AlboAVBElencoAttiResponseReturn(AlboAVBElencoAttiDto alboAVBElencoAttiDto,
			AlboAVBAttoError alboAvbAttoError) {
		this.alboAVBElencoAttiDto = alboAVBElencoAttiDto;
		this.alboAvbAttoError = alboAvbAttoError;
	}

	public AlboAVBElencoAttiDto getAlboAVBElencoAttiDto() {
		return alboAVBElencoAttiDto;
	}

	public void setAlboAVBElencoAttiDto(AlboAVBElencoAttiDto alboAVBElencoAttiDto) {
		this.alboAVBElencoAttiDto = alboAVBElencoAttiDto;
	}

	public AlboAVBAttoError getAlboAvbAttoError() {
		return alboAvbAttoError;
	}

	public void setAlboAvbAttoError(AlboAVBAttoError alboAvbAttoError) {
		this.alboAvbAttoError = alboAvbAttoError;
	}
}
