/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class ResponsabiliPEGCompletaItem extends RuoliScrivaniaAttiCompletaItem {

	public ResponsabiliPEGCompletaItem() {
		super("responsabilePEG", "responsabilePEGFromLoadDett", "codUoResponsabilePEG", "desResponsabilePEG");
	}

	public String getAltriParamLoadCombo() {
		return null;
	}
	
}
