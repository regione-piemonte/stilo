/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBElencoTipiAttoResponseReturn implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlboAVBTipoAtto[] alboAVBTipoAtto;
	
	private AlboAVBAttoError alboAvbAttoError;

	public AlboAVBElencoTipiAttoResponseReturn() {
	}

	public AlboAVBElencoTipiAttoResponseReturn(AlboAVBTipoAtto[] alboAVBTipoAtto, AlboAVBAttoError alboAvbAttoError) {
		this.alboAVBTipoAtto = alboAVBTipoAtto;
		this.alboAvbAttoError = alboAvbAttoError;
	}

	public AlboAVBTipoAtto[] getAlboAVBTipoAtto() {
		return alboAVBTipoAtto;
	}

	public void setAlboAVBTipoAtto(AlboAVBTipoAtto[] alboAVBTipoAtto) {
		this.alboAVBTipoAtto = alboAVBTipoAtto;
	}

	public AlboAVBAttoError getAlboAvbAttoError() {
		return alboAvbAttoError;
	}

	public void setAlboAvbAttoError(AlboAVBAttoError alboAvbAttoError) {
		this.alboAvbAttoError = alboAvbAttoError;
	}
	
	 public AlboAVBTipoAtto getAlboAVBTipoAtto(int i) {
	        return this.alboAVBTipoAtto[i];
	    }

	    public void setElencoTipiAtto(int i, AlboAVBTipoAtto value) {
	        this.alboAVBTipoAtto[i] = value;
	    }
}
