/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class AltriDirigentiProponentiCompletaItem extends RuoliScrivaniaAttiCompletaItem {

	public AltriDirigentiProponentiCompletaItem() {
		super("dirigenteProponente", "dirigenteProponenteFromLoadDett", "codUoDirigenteProponente", "desDirigenteProponente", "flgDirigenteProponenteFirmatario", "motiviDirigenteProponente");
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

}