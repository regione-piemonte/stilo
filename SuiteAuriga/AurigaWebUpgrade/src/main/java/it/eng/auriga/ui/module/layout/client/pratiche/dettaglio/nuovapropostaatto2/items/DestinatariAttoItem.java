/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public abstract class DestinatariAttoItem extends ReplicableItem {

	// Flag per disabilitare la ricerca della denominazione all'onChanged
	protected boolean cercaInRubricaAfterChanged = true;

	@Override
	public ReplicableCanvas getCanvasToReply() {
		
		DestinatariAttoCanvas lDestinatariAttoCanvas = new DestinatariAttoCanvas(this);
		return lDestinatariAttoCanvas;
	}
	
	public abstract String getIdDocTypeAtto();
	
	public abstract boolean showPrefisso();
	
	public abstract String getTitlePrefisso();
	
	public abstract boolean isRequiredPrefisso();
	
	public abstract boolean isEditablePrefisso();
	
	public abstract String getTitleDenominazione();
	
	public abstract boolean isRequiredDenominazione();
	
	public abstract boolean isEditableDenominazione();
	
	public abstract boolean showIndirizzo();
	
	public abstract String getTitleIndirizzo();
	
	public abstract boolean isRequiredIndirizzo();
	
	public abstract boolean isEditableIndirizzo();
	
	public abstract boolean showCorteseAttenzione();
	
	public abstract String getTitleCorteseAttenzione();
	
	public abstract boolean isRequiredCorteseAttenzione();
	
	public abstract boolean isEditableCorteseAttenzione();
	
	public boolean isCercaInRubricaAfterChanged() {
		return cercaInRubricaAfterChanged;
	}

	public void setCercaInRubricaAfterChanged(boolean cercaInRubricaAfterChanged) {
		this.cercaInRubricaAfterChanged = cercaInRubricaAfterChanged;
	}
	
}