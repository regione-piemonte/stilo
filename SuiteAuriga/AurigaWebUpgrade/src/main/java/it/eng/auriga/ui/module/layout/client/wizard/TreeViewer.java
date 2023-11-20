/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.protocollazione.AbstractPreviewCanvas;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class TreeViewer extends HLayout implements AbstractPreviewCanvas {

	@Override
	public void finishLoad(VLayout lVLayout) {
		setAlign(Alignment.CENTER);
		setMembers(lVLayout);
		markForRedraw();
	}

}
