/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

public class ScrivaniaDECItem extends RuoliScrivaniaAttiCompletaItem {

	public ScrivaniaDECItem() {
		super("scrivaniaDEC", "scrivaniaDECFromLoadDett", "codUoScrivaniaDEC", "desScrivaniaDEC");		
	}
	
	@Override
	public void manageChangedScrivaniaSelezionata() {
		
	}
	
	public boolean getFlgAbilitaAutoFetchDataSelectOrganigramma() {
		return false;
	}
	
	public boolean selectUniqueValueAfterChangedParams() {
		return false;
	}
	
	@Override
	public Boolean getShowRemoveButton() {
		return false;
	};
	
	public String getAltriParamLoadCombo() {
		return null;
	}

}
