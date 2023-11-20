/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public abstract class IndirizziSoggettoItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		IndirizziSoggettoCanvas lIndirizziSoggettoCanvas = new IndirizziSoggettoCanvas(this);
		return lIndirizziSoggettoCanvas;
	}
	
	@Override
	public Record getCanvasDefaultRecord() {
		Record lRecord = new Record();
		lRecord.setAttribute("dataValidoDal", new Date());
		return lRecord; // lo metto per forzare l'editRecord sul canvas quando aggiungo una riga (in modo da passare tsVld alle combo di stato e comune)
	}

	public void validateTipo(String flgPersonaFisica) {
		
		if (getCanvas() != null) {
			final VLayout lVLayout = (VLayout) getCanvas();
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if (lVLayoutMember instanceof HLayout) {
					for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
						if (lHLayoutMember instanceof IndirizziSoggettoCanvas) {
							IndirizziSoggettoCanvas lCanvas = (IndirizziSoggettoCanvas) lHLayoutMember;
							lCanvas.validateTipo(flgPersonaFisica);
						}
					}
				}
			}
		}
	}

	public boolean isRubricaSoggetti() {
		
		return false;
	}

	public abstract String getFlgPersonaFisica();

}
