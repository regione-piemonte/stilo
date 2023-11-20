/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraLiquidazioneAtto extends SicraLiquidazione implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private SicraAttoLiqTecnica attoLiqTecnica;
	private SicraAttoLiqContabile attoLiqContabile;
	
	public SicraAttoLiqTecnica getAttoLiqTecnica() {
		return attoLiqTecnica;
	}
	
	public void setAttoLiqTecnica(SicraAttoLiqTecnica attoLiqTecnica) {
		this.attoLiqTecnica = attoLiqTecnica;
	}
	
	public SicraAttoLiqContabile getAttoLiqContabile() {
		return attoLiqContabile;
	}
	
	public void setAttoLiqContabile(SicraAttoLiqContabile attoLiqContabile) {
		this.attoLiqContabile = attoLiqContabile;
	}
	
}
