/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class GruppoClientiItem extends ReplicableItem {
	
	private String idGruppoClienti;
	
	
	@Override
	public ReplicableCanvas getCanvasToReply() {		
		GruppoClientiCanvas lGruppoClientiCanvas = new GruppoClientiCanvas();
		return lGruppoClientiCanvas;
	}


	public String getIdGruppoClienti() {
		return idGruppoClienti;
	}


	public void setIdGruppoClienti(String idGruppoClienti) {
		this.idGruppoClienti = idGruppoClienti;
	}


	
	
}
