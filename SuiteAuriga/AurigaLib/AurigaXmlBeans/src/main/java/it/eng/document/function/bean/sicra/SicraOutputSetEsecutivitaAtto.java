/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraOutputSetEsecutivitaAtto implements Serializable {

	private static final long serialVersionUID = 1L;

	private EsitoChiamata esitoChiamata;
	private SicraMessaggio riscontro;
	private List<SicraImpegnoSintetico> impegno;

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

	public List<SicraImpegnoSintetico> getImpegno() {
		return impegno;
	}

	public void setImpegno(List<SicraImpegnoSintetico> impegno) {
		this.impegno = impegno;
	}

}// SicraOutputSetEsecutivitaAtto
