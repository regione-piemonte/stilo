/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.protocollazione.AssegnazioneItem;

public class AssegnazioneEmailItem extends AssegnazioneItem {

	public AssegnazioneEmailItem() {
		setFlgUdFolder(null); //per disabilitare select preferiti
		setFlgSenzaLD(true);
		setTipoAssegnatari(AurigaLayout.getParametroDB("TIPO_ASSEGNATARI_EMAIL"));
	}
	
	@Override
	public String getFinalitaLoadComboOrganigramma() {
		return "ASS_EMAIL";
	}
	
	@Override
	public String getFinalitaOrganigrammaLookup() {
		return "ASS_EMAIL";
	}
	
	@Override
	public boolean showOpzioniInvioAssegnazioneButton() {
		return false;
	}
	
}
