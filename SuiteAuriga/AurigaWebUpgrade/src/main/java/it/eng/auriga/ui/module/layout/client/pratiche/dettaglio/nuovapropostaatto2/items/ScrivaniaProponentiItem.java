/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class ScrivaniaProponentiItem extends ReplicableItem {
	
	@Override
	public ReplicableCanvas getCanvasToReply() {		
		ScrivaniaProponentiCanvas lScrivaniaProponentiCanvas = new ScrivaniaProponentiCanvas(this);		
		return lScrivaniaProponentiCanvas;
	}
	
	public String getIdUoProponenteIdScrivania() {
		return null;
	}
	
	public String getAltriParamLoadComboIdScrivania() {
		return null;
	}
	
	public int getSelectItemOrganigrammaWidth() {
		return 500;
	}
	
	public boolean showTipoVistoScrivania() {
		return false;
	}
	
	public boolean isRequiredTipoVistoScrivania() {
		return false;
	}
	
	public boolean showFlgForzaDispFirmaScrivania() {
		return false;
	}
	
	public void manageChangedUoSelezionata() {
		for(ReplicableCanvas lReplicableCanvas : getAllCanvas()) {
			((ScrivaniaProponentiCanvas)lReplicableCanvas).manageChangedUoSelezionata();
		}	
	}
	
}
