/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraOutputAggiornaRifAttoLiquidazione implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private EsitoChiamata esitoChiamata;
	private BigInteger totLiquidazioniAggiornate;
	
	public EsitoChiamata getEsitoChiamata() {
		return esitoChiamata;
	}
	
	public void setEsitoChiamata(EsitoChiamata esitoChiamata) {
		this.esitoChiamata = esitoChiamata;
	}
	
	public BigInteger getTotLiquidazioniAggiornate() {
		return totLiquidazioniAggiornate;
	}
	
	public void setTotLiquidazioniAggiornate(BigInteger totLiquidazioniAggiornate) {
		this.totLiquidazioniAggiornate = totLiquidazioniAggiornate;
	}
	
}
