/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraOutputArchiviaAtto implements Serializable {

	private static final long serialVersionUID = 1L;

	private EsitoChiamata esitoChiamata;
	private SicraMessaggio riscontro;

	public EsitoChiamata getEsitoChiamata() {
		return esitoChiamata;
	}

	public void setEsitoChiamata(EsitoChiamata esitoChiamata) {
		this.esitoChiamata = esitoChiamata;
	}

	public SicraMessaggio getRiscontro() {
		return riscontro;
	}

	public void setRiscontro(SicraMessaggio riscontro) {
		this.riscontro = riscontro;
	}

}// SicraOutputArchiviaAtto
