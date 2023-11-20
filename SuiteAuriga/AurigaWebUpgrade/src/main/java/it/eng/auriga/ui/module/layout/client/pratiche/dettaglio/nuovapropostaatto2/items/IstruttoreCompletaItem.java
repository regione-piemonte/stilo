/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class IstruttoreCompletaItem extends RuoliScrivaniaAttiCompletaItem {

	public IstruttoreCompletaItem() {
		super("istruttore", "istruttoreFromLoadDett", "codUoIstruttore", "desIstruttore");
	}
	
	public String getUoProponenteCorrente() {
		return null;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
}