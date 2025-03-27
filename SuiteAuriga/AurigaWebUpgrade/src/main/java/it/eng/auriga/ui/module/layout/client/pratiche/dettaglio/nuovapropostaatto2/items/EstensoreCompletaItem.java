/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

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
