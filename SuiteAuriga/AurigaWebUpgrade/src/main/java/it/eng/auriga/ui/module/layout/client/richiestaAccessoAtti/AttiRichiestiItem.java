/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class AttiRichiestiItem extends ReplicableItem{

	@Override
	public ReplicableCanvas getCanvasToReply() {
		AttiRichiestiCanvas lAttiRichiestiCanvas = new AttiRichiestiCanvas();
		return lAttiRichiestiCanvas;
	}
	
	public boolean isElencoAttiRichiestiInCanvasEditable(){
		return true;
	}
	
	public boolean isEstremiAttiRichiestiInCanvasEditable(){
		return true;
	}
	
	public boolean isStatoAttiInCanvasToShow(){
		return true;
	}
	
	public boolean isStatoAttiInCanvasEditable(){
		return true;
	}
	
	public boolean isNoteCittadellaInCanvasToEnable(){
		return true;
	}
	
	public boolean isNoteUffRichiedenteInCanvasToEnable(){
		return true;
	}
	
	public Record getIndirizzoImpostato() {
		return null;
	}
	
	public String getIdNodoRicerca() {
		return "/";
	}

}
