/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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