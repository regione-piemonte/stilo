/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

import java.util.HashMap;

public class IstruttoreProcItem extends ReplicableItem {
	
	private String idProcedimento;
	private String listaIdProcessType;
	
	public IstruttoreProcItem(String idProcedimento, String listaIdProcessType) {
		this.idProcedimento = idProcedimento;
		this.listaIdProcessType = listaIdProcessType;
	}
	
	@Override
	public ReplicableCanvas getCanvasToReply() {		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("idProcedimento", idProcedimento);
		params.put("listaIdProcessType", listaIdProcessType);
		IstruttoreProcCanvas lIstruttoreProcCanvas = new IstruttoreProcCanvas(this, params);		
		return lIstruttoreProcCanvas;
	}
	
}
