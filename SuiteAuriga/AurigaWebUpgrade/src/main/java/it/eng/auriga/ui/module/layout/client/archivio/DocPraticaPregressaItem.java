/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetail;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;

public class DocPraticaPregressaItem extends AllegatiItem {
	
	@Override
	public ReplicableCanvas getCanvasToReply() {
		HashMap<String, String> lMap = new HashMap<String, String>();
		lMap.put("nomeFileWidth", getNomeFileWidth() != null ? String.valueOf(getNomeFileWidth()) : null);
		DocPraticaPregressaCanvas lDocPraticaPregressaCanvas = new DocPraticaPregressaCanvas(this, lMap);
		return lDocPraticaPregressaCanvas;
	}	
	
	@Override
	public boolean isFromFolderPraticaPregressa() {
		return true;
	}
	
	@Override
	public boolean showNumeroAllegato() {
		return false;
	}
	
	@Override
	public boolean isObbligatorioFile() {
		return true;
	}
	
	@Override
	public String getTitleFlgDatiSensibili() {
		return "vers. con omissis";
	}
	
	@Override
	public String getTitleNomeFileOmissis() {
		return "File vers. omissis";
	}
	
	public String getIdFolderType() {
		return null;
	}
	
	@Override
	public void visualizzaVersioni(Record allegatoRecord) {
		final String nroProgr = allegatoRecord.getAttributeAsString("numeroProgrAllegato");
		final String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");						
		final GWTRestDataSource lProtocolloDataSource = new GWTRestDataSource("ProtocolloDataSource");
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", allegatoRecord.getAttributeAsString("idUdAppartenenza"));
		lProtocolloDataSource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record recordProtocollo = response.getData()[0];
					ProtocollazioneDetail.visualizzaVersioni(idDoc, "DI", nroProgr, "", recordProtocollo);
				}
			}
		});
	}
	
}
