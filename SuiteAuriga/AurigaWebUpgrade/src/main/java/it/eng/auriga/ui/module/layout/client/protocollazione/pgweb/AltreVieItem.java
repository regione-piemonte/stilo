/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class AltreVieItem extends ReplicableItem {
	
	@Override
	public ReplicableCanvas getCanvasToReply() {
		AltreVieCanvas lAltreVieCanvas = new AltreVieCanvas(this){
			@Override
			public boolean isIndirizzoObbligatorio() {
				return isIndirizzoObbligatorioInCanvas();
			}
			
			@Override
			public boolean isCivicoObbligatorio() {
				return isCivicoObbligatorioInCanvas();
			}
			
			@Override
			public boolean isForceCapNonObbligatorio() {
				return isForceCapNonObbligatorioInCanvas();
			}
			
			@Override
			protected boolean isControllaIndirizzoAfterEditRecord() {
				return isControllaIndirizzoAfterEditRecordInCanvas();
			}
		};
		return lAltreVieCanvas;
	}
	
	public boolean showFlgFuoriComune() {
		return true;
	}
	
	public boolean getFlgFuoriComune() {
		return true;
	}
	
	public boolean isIndirizzoObbligatorioInCanvas() {
		return false;
	}
	
	public boolean isCivicoObbligatorioInCanvas() {
		return false;
	}
	
	public boolean isForceCapNonObbligatorioInCanvas() {
		return true;
	}
	
	public boolean isControllaIndirizzoAfterEditRecordInCanvas() {
		return false;
	}
	
	public boolean getShowStato() {
		return true;
	}
	
	@Override
	public void setUpClickRemoveHandler(VLayout lVLayout, HLayout lHLayout) {
		super.setUpClickRemoveHandler(lVLayout, lHLayout);
		manageOnChanged();
	}
	
	public void manageOnChanged() {
		
	}
		
}