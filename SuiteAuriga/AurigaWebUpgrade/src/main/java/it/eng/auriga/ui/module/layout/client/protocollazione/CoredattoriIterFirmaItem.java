/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.protocollazione;

public class CoredattoriIterFirmaItem extends CondivisioneItem {
	
	public CoredattoriIterFirmaItem() {
		setFlgUdFolder(null); //per disabilitare select preferiti
		setFlgSenzaLD(true);
	}
	
	@Override
	public String getFinalitaOrganigrammaLookup() {
		return null; //TODO cosa devo passare come finalita?
	}
	
	@Override
	public String getFinalitaLoadComboOrganigramma() {
		return null; //TODO cosa devo passare come finalita?
	}
	
	@Override
	public boolean showOpzioniInvioCondivisioneButton() {
		return false;
	}

}
