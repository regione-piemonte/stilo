/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public abstract class ContattiSoggettoItem extends ReplicableItem{
		
	@Override
	public ReplicableCanvas getCanvasToReply() {
		ContattiSoggettoCanvas lContattiSoggettoCanvas = new ContattiSoggettoCanvas();
		return lContattiSoggettoCanvas;
	}	
	
	public abstract boolean isNewMode();
	public abstract String getFlgPersonaFisica();


	public void validateTipo(String flgPersonaFisica) {
		
		if (getCanvas() != null) {
			final VLayout lVLayout = (VLayout) getCanvas();
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if (lVLayoutMember instanceof HLayout) {
					for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
						if (lHLayoutMember instanceof ContattiSoggettoCanvas) {
							ContattiSoggettoCanvas lCanvas = (ContattiSoggettoCanvas) lHLayoutMember;
							lCanvas.validateTipo(flgPersonaFisica);
						}
					}
				}
			}
		}
	}

}