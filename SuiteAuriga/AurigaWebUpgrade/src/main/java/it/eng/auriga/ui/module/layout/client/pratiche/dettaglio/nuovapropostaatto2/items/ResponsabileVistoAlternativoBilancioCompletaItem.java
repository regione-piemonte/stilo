/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

public class ResponsabileVistoAlternativoBilancioCompletaItem extends RuoliScrivaniaAttiCompletaItem {

	public ResponsabileVistoAlternativoBilancioCompletaItem() {
		super("responsabileVistoAlternativoBilancio", "responsabileVistoAlternativoBilancioFromLoadDett", "codUoResponsabileVistoAlternativoBilancio", "desResponsabileVistoAlternativoBilancio");
	}
	
	public String getUoProponenteCorrente() {
		return null;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}

}
