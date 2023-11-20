/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public abstract class AttributiAddEditabiliItem extends ReplicableItem{
	
	public AttributiAddEditabiliItem() {
		super();		
	}
	
	@Override
	public ReplicableCanvas getCanvasToReply() {
		AttributiAddEditabiliCanvas lAttributiAddEditabiliCanvas = new AttributiAddEditabiliCanvas(this);
		return lAttributiAddEditabiliCanvas;
	}

	@Override
	public void setCanEdit(Boolean canEdit) {
		
		setEditing(canEdit);
		if(getCanvas() != null) {
			final VLayout lVLayout = (VLayout) getCanvas(); 		
			for (int i=0;i<lVLayout.getMembers().length; i++){
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if(lVLayoutMember instanceof HLayout) {				
					RemoveButton lRemoveButton = null;
					for(Canvas lHLayoutMember : ((HLayout)lVLayoutMember).getMembers()) {
						if(lHLayoutMember instanceof ImgButton) {
							if(i == (lVLayout.getMembers().length - 1)) {
								if(canEdit) {
									lHLayoutMember.show();
								} else {
									lHLayoutMember.hide();
								}									
							} else {
								lRemoveButton = ((RemoveButton)lHLayoutMember);	
								if(canEdit) {
									lRemoveButton.show();
								} else {
									lRemoveButton.hide();
								}	
							}																			
						} else if(lHLayoutMember instanceof ReplicableCanvas) {
							AttributiAddEditabiliCanvas lAttributiAddEditabiliCanvas = (AttributiAddEditabiliCanvas)lHLayoutMember;
							lAttributiAddEditabiliCanvas.setCanEdit(canEdit);													
						}							
					}	
				}			
			}
		}
	}
	
	public abstract String getCodTipoFlusso();
	
	public abstract String getNomeTask();

}
