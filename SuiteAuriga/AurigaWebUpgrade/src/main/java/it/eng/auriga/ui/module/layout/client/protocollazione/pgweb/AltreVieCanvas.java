/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Visibility;

import it.eng.auriga.ui.module.layout.client.protocollazione.IndirizzoCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class AltreVieCanvas extends IndirizzoCanvas {

	public AltreVieCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void buildMainForm() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setVisibility(Visibility.HIDDEN);
		mDynamicForm.setNumCols(30);
		mDynamicForm.setColWidths("50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50",
				"100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100");

	}

	@Override
	public boolean showItemsIndirizzo() {
		return true;
	}

	@Override
	public boolean showItemsIndirizzoWithBorder() {
		return false;
	}

	@Override
	public boolean showFlgFuoriComune() {
		return ((AltreVieItem) getItem()).showFlgFuoriComune() && super.showFlgFuoriComune();
	}	
	
	@Override
	public boolean getFlgFuoriComune() {
		return ((AltreVieItem) getItem()).getFlgFuoriComune() && super.getFlgFuoriComune();
	}
	
	@Override
	public boolean getShowStato() {
		return ((AltreVieItem) getItem()).getShowStato() && super.getShowStato();
	}
	
	@Override
	public void manageOnChangedIndirizzo() {
		((AltreVieItem) getItem()).manageOnChanged();
	}

	
}
