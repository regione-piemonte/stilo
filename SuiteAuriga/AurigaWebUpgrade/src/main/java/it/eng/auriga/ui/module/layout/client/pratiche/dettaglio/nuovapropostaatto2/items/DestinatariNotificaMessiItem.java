/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class DestinatariNotificaMessiItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		DestinatariNotificaMessiCanvas lDestinatariNotificaMessiCanvas = new DestinatariNotificaMessiCanvas(this);
		return lDestinatariNotificaMessiCanvas;
	}
	
	public String getIdUdAtto() {
		return null;
	}
	
	public void afterGeneraModuloNotifica(Record recordModuloNotifica) {
		
	}
	
	public boolean showDescrizioneDestinatariNotificaMessi() {
		return false;
	}
		
	public String getTitleDescrizioneDestinatariNotificaMessi() {
		return null;
	}
	
	public boolean isRequiredDescrizioneDestinatariNotificaMessi() {
		return false;
	}
	
	public boolean isEditableDescrizioneDestinatariNotificaMessi() {
		return false;
	}	
	
	public boolean showEmailDestinatariNotificaMessi() {
		return false;
	}
	
	public String getTitleEmailDestinatariNotificaMessi() {
		return null;
	}
	
	public boolean isRequiredEmailDestinatariNotificaMessi() {
		return false;
	}
	
	public boolean isEditableEmailDestinatariNotificaMessi() {
		return false;
	}
		
	public boolean showIndirizzoDestinatariNotificaMessi() {
		return false;
	}
	
	public String getTitleIndirizzoDestinatariNotificaMessi() {
		return null;
	}
	
	public boolean isRequiredIndirizzoDestinatariNotificaMessi() {
		return false;
	}				
	
	public boolean isEditableIndirizzoDestinatariNotificaMessi() {
		return false;
	}	
	
	public boolean showAltriDatiDestinatariNotificaMessi() {
		return false;
	}
	
	public String getTitleAltriDatiDestinatariNotificaMessi() {
		return null;
	}
	
	public boolean isRequiredAltriDatiDestinatariNotificaMessi() {
		return false;
	}	
	
	public boolean isEditableAltriDatiDestinatariNotificaMessi() {
		return false;
	}	
	
	public boolean showNumeroNotificaDestinatariNotificaMessi() {
		return false;
	}
	
	public String getTitleNumeroNotificaDestinatariNotificaMessi() {
		return null;
	}
	
	public boolean isRequiredNumeroNotificaDestinatariNotificaMessi() {
		return false;
	}
	
	public boolean isEditableNumeroNotificaDestinatariNotificaMessi() {
		return false;
	}
	
	public boolean showDataNotificaDestinatariNotificaMessi() {
		return false;
	}
	
	public String getTitleDataNotificaDestinatariNotificaMessi() {		
		return null;
	}
	
	public boolean isRequiredDataNotificaDestinatariNotificaMessi() {
		return false;
	}	
	
	public String getDefaultValueDataNotificaDestinatariNotificaMessi() {
		return null;
	}
	
	public boolean isEditableDataNotificaDestinatariNotificaMessi() {
		return false;
	}
	
	public boolean showMezzoNotificaDestinatariNotificaMessi() {
		return false;
	}
	
	public String getTitleMezzoNotificaDestinatariNotificaMessi() {
		return null;
	}
	
	public boolean isRequiredMezzoNotificaDestinatariNotificaMessi() {
		return false;
	}	
	
	public HashMap<String, String> getValueMapMezzoNotificaDestinatariNotificaMessi() {
		return null;
	}
	
	public String getDefaultValueMezzoNotificaDestinatariNotificaMessi() {
		return null;
	}
	
	public boolean isEditableMezzoNotificaDestinatariNotificaMessi() {
		return false;
	}
	
	public void setCanEdit(boolean flgInibitaAggiuntaRighe, boolean flgInibitaCancellazioneRighe, boolean canEdit) {
		if(flgInibitaAggiuntaRighe && flgInibitaCancellazioneRighe) {
			setCanEdit(false);
			if (getCanvas() != null) {
				final VLayout lVLayout = (VLayout) getCanvas();
				for (int i = 0; i < lVLayout.getMembers().length; i++) {
					Canvas lVLayoutMember = lVLayout.getMember(i);
					if (lVLayoutMember instanceof HLayout) {
						for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
							if (lHLayoutMember instanceof ReplicableCanvas) {
								ReplicableCanvas lReplicableCanvas = (ReplicableCanvas) lHLayoutMember;
								lReplicableCanvas.setCanEdit(canEdit);		
							}
						}
					}
				}
			}
		} else {
			setCanEdit(canEdit);
			if(flgInibitaAggiuntaRighe || flgInibitaCancellazioneRighe) {
				if (getCanvas() != null) {
					final VLayout lVLayout = (VLayout) getCanvas();
					for (int i = 0; i < lVLayout.getMembers().length; i++) {
						Canvas lVLayoutMember = lVLayout.getMember(i);
						if (lVLayoutMember instanceof HLayout) {
							for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
								if (lHLayoutMember instanceof ImgButton) {
									if (i == (lVLayout.getMembers().length - 1)) {
										// se è il bottone di add lo nascondo
										if(flgInibitaAggiuntaRighe) {
											lHLayoutMember.hide();
										}
									} else if (lHLayoutMember instanceof RemoveButton) {
										// se è un bottone di remove lo disabilito
										if(flgInibitaCancellazioneRighe) {
											((RemoveButton) lHLayoutMember).setAlwaysDisabled(true);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
}
