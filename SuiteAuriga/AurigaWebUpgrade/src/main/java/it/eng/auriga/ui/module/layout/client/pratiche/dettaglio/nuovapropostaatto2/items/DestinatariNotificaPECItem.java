/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class DestinatariNotificaPECItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		DestinatariNotificaPECCanvas lDestinatariNotificaPECCanvas = new DestinatariNotificaPECCanvas(this);
		return lDestinatariNotificaPECCanvas;
	}
	
	public boolean showDescrizioneDestinatariNotificaPEC() {
		return false;
	}
		
	public String getTitleDescrizioneDestinatariNotificaPEC() {
		return null;
	}
	
	public boolean isRequiredDescrizioneDestinatariNotificaPEC() {
		return false;
	}
	
	public boolean isEditableDescrizioneDestinatariNotificaPEC() {
		return false;
	}
	
	public boolean showIndirizzoPECDestinatariNotificaPEC() {
		return false;
	}
	
	public String getTitleIndirizzoPECDestinatariNotificaPEC() {
		return null;
	}
	
	public boolean isRequiredIndirizzoPECDestinatariNotificaPEC() {
		return false;
	}				
	
	public boolean isEditableIndirizzoPECDestinatariNotificaPEC() {
		return false;
	}
	
	public boolean showNotaDestinatariNotificaPEC() {
		return false;
	}
	
	public String getTitleNotaDestinatariNotificaPEC() {
		return null;
	}
	
	public boolean isRequiredNotaDestinatariNotificaPEC() {
		return false;
	}
	
	public String getDefaultValueNotaDestinatariNotificaPEC() {
		return null;
	}
	
	public boolean isEditableNotaDestinatariNotificaPEC() {
		return false;
	}
	
}
