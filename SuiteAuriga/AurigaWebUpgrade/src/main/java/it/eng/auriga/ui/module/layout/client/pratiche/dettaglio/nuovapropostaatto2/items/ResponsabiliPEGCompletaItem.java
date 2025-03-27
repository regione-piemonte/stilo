/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

public class ResponsabiliPEGCompletaItem extends RuoliScrivaniaAttiCompletaItem {

	public ResponsabiliPEGCompletaItem() {
		super("responsabilePEG", "responsabilePEGFromLoadDett", "codUoResponsabilePEG", "desResponsabilePEG");
	}

	public String getAltriParamLoadCombo() {
		return null;
	}
	
}
