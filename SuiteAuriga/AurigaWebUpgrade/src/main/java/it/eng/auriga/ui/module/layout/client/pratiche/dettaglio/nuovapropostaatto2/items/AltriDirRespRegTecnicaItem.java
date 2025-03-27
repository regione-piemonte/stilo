/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class AltriDirRespRegTecnicaItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		AltriDirRespRegTecnicaCanvas lAltriDirRespRegTecnicaCanvas = new AltriDirRespRegTecnicaCanvas(this);
		return lAltriDirRespRegTecnicaCanvas;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
	public boolean showFlgFirmatario() {
		return true;
	}

}
