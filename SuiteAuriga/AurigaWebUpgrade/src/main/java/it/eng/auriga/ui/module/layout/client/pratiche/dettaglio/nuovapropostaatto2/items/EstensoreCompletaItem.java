/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class EstensoreCompletaItem extends RuoliScrivaniaAttiCompletaItem {

	public EstensoreCompletaItem() {
		super("estensore", "estensoreFromLoadDett", "codUoEstensore", "desEstensore");
	}
	
	public String getUoProponenteCorrente() {
		return null;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}

}