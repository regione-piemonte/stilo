/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.form.validator.Validator;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public abstract class SelezionaValoriDizionarioItem extends ReplicableItem {
	
	public abstract String getDictionaryEntry();
	
	public boolean isRequiredStrInDes() {
		return true;
	}
	
	public boolean getAddUnknownValues() {
		return false;
	}
	
	public Validator[] getValidators() {
		return null;
	}

	public ReplicableCanvas getCanvasToReply() {
		SelezionaValoriDizionarioCanvas lSelezionaValoriDizionarioCanvas = new SelezionaValoriDizionarioCanvas(this);
		return lSelezionaValoriDizionarioCanvas;
	}
	
	
}
