/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

/**
 * 
 * @author Cristiano Daniele
 *
 */

public class CampoCaricaRubricaClientiItem extends ReplicableItem {

	public String codTipo;

	@Override
	public ReplicableCanvas getCanvasToReply() {
		CampoCaricaRubricaClientiCanvas _canvas = new CampoCaricaRubricaClientiCanvas(this);
		return _canvas;
	}

	public void setCompanyId(String value) {
		this.codTipo = value;
	}

	public String getCompanyId() {
		return this.codTipo;
	}

}
