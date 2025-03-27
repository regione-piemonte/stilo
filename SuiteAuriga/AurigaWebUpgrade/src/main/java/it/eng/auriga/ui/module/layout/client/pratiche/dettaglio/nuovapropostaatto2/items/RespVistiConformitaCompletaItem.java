/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

public class RespVistiConformitaCompletaItem extends RuoliScrivaniaAttiCompletaItem {

	
	public RespVistiConformitaCompletaItem() {
		super("respVistiConformita", "respVistiConformitaFromLoadDett", "codUoRespVistiConformita", "desRespVistiConformita", "flgRespVistiConformitaFirmatario", "motiviRespVistiConformita", "flgRiacqVistoInRitornoIterRespVistiConformita");		
	}

	public String getAltriParamLoadCombo() {
		return null;
	}

	public boolean showFlgFirmatario() {
		return false;
	}
	
	public boolean showMotivi() {
		return false;
	}

	public boolean isRequiredMotivi() {
		return false;
	}
	
	public boolean showFlgRiacqVistoInRitornoIter() {
		return false;
	}
	
}
