/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public abstract class IndirizziRichiedenteItem extends ReplicableItem {
	
	@Override
	public ReplicableCanvas getCanvasToReply() {
		IndirizziRichiedenteCanvas lIndirizziRichiedenteCanvas = new IndirizziRichiedenteCanvas(this);		
		return lIndirizziRichiedenteCanvas;
	}
	
	public void validateTipo(String flgPersonaFisica) {
		
		if(getCanvas() != null) {
			final VLayout lVLayout = (VLayout) getCanvas(); 		
			for (int i=0;i<lVLayout.getMembers().length; i++){
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if(lVLayoutMember instanceof HLayout) {
					for(Canvas lHLayoutMember : ((HLayout)lVLayoutMember).getMembers()) {
						if(lHLayoutMember instanceof IndirizziRichiedenteCanvas) {
							IndirizziRichiedenteCanvas lCanvas = (IndirizziRichiedenteCanvas)lHLayoutMember;
							lCanvas.validateTipo(flgPersonaFisica);
						}
					}	
				}			
			}
		}
	}
	
	public abstract String getFlgPersonaFisica();

	public String getTipoIndirizzo() {
		return null;
	}
	
	public boolean isIndirizzoRichiedentePratica() {
		return false;
	}
	
}
