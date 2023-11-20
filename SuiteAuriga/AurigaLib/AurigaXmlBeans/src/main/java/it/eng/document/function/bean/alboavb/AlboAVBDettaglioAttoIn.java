/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboAVBDettaglioAttoIn implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long idAtto;

    private long idEnte;

	public AlboAVBDettaglioAttoIn() {
	}

	public AlboAVBDettaglioAttoIn(long idAtto, long idEnte) {
		this.idAtto = idAtto;
		this.idEnte = idEnte;
	}

	public long getIdAtto() {
		return idAtto;
	}

	public void setIdAtto(long idAtto) {
		this.idAtto = idAtto;
	}

	public long getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(long idEnte) {
		this.idEnte = idEnte;
	}
}
