/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class ResponsabiliPEGItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		ResponsabiliPEGCanvas lResponsabiliPEGCanvas = new ResponsabiliPEGCanvas(this);
		return lResponsabiliPEGCanvas;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}

}
