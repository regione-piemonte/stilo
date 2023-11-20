/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public abstract class AssociazioniAttributiCustomItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {		
		AssociazioniAttributiCustomCanvas lAssociazioniAttributiCustomCanvas = new AssociazioniAttributiCustomCanvas(this);
		return lAssociazioniAttributiCustomCanvas;
	}
	
	public abstract String getNomeTabella();
	
	public abstract String getIdEntitaAssociata();
	
	public abstract boolean isAssociazioneSottoAttributo();
		
	public String getNomeAttributoComplex() {
		return null;
	}
	
	public void reloadCombo() {
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			ReplicableCanvas lReplicableCanvas = getReplicableCanvas(lHLayout);			
			((AssociazioniAttributiCustomCanvas) lReplicableCanvas).reloadCombo();
		}
	}
	
}
