/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraOutputRicercaAttoLiquidazione implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private EsitoChiamata esitoChiamata;
	private List<SicraLiquidazioneAtto> liquidazione;
	
	public EsitoChiamata getEsitoChiamata() {
		return esitoChiamata;
	}
	
	public void setEsitoChiamata(EsitoChiamata esitoChiamata) {
		this.esitoChiamata = esitoChiamata;
	}

	public List<SicraLiquidazioneAtto> getLiquidazione() {
		return liquidazione;
	}

	public void setLiquidazione(List<SicraLiquidazioneAtto> liquidazione) {
		this.liquidazione = liquidazione;
	}
	
}
