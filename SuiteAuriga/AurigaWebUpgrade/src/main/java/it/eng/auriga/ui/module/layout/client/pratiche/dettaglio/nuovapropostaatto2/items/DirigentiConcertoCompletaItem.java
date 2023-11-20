/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class DirigentiConcertoCompletaItem extends RuoliScrivaniaAttiCompletaItem {

	public DirigentiConcertoCompletaItem() {
		super("dirigenteConcerto", "dirigenteConcertoFromLoadDett", "codUoDirigenteConcerto", "desDirigenteConcerto", "flgDirigenteConcertoFirmatario");
	}

	public String getAltriParamLoadCombo() {
		return null;
	}
	
	public boolean showFlgFirmatario() {
		return false;
	}
	
}
