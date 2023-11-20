/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;

public class UfficioGareAcquistiItem extends GroupReplicableItem {

	public UfficioGareAcquistiItem(String title) {
		super(title);
	}

	@Override
	public ReplicableCanvas getCanvasToReply() {
		UfficioGareAcquistiCanvas lUfficioGareAcquistiCanvas = new UfficioGareAcquistiCanvas(this);
		return lUfficioGareAcquistiCanvas;
	}
	
	public boolean getFlgAbilitaAutoFetchDataSelectOrganigramma() {
		return false;
	}
	
	public boolean selectUniqueValueAfterChangedParams() {
		return false;
	}
	
	public void resetAfterChangedUoProponente() {
		if(getAltriParamLoadCombo() != null && getAltriParamLoadCombo().indexOf("$ID_UO_PROPONENTE$") != -1) {	
			resetAfterChangedParams();
		}
	}
	
	public void resetAfterChangedParams() {
		for(ReplicableCanvas lReplicableCanvas : getAllCanvas()) {
			((UfficioGareAcquistiCanvas)lReplicableCanvas).resetAfterChangedParams();
		}	
	}
	
	public String getUoProponenteCorrente() {
		return null;
	}	
	
	public String getDefaultValue() {
		return null;
	}
	
	public String getTipoLoadCombo() {
		return null;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}
	
	public GWTRestDataSource getUfficioGareAcquistiDS() {
		GWTRestDataSource ufficioGareAcquistiDS = new GWTRestDataSource("LoadComboUfficioGareAcquistiDataSource", "key", FieldType.TEXT);
		if(getTipoLoadCombo() != null) {
			ufficioGareAcquistiDS.addParam("tipoLoadCombo", getTipoLoadCombo());
		}		
		if(getAltriParamLoadCombo() != null) {
			ufficioGareAcquistiDS.addParam("altriParamLoadCombo", getAltriParamLoadCombo());
		}
		return ufficioGareAcquistiDS;
	}
	
	public void getUoDaPreimpostare(final ServiceCallback<Record> callback) {			
		final String defaultValue = getDefaultValue();
		final String idUoLavoro = AurigaLayout.getIdUoLavoro();				
		getUfficioGareAcquistiDS().fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				RecordList data = response.getDataAsRecordList();
				// Se è impostata una UO di lavoro ed è nella lista seleziono quella
				if(idUoLavoro != null && !"".equals(idUoLavoro)) {
					if (data.getLength() > 0) {					
						for (int i = 0; i < data.getLength(); i++) {
							String key = data.get(i).getAttribute("key");
							String display = data.get(i).getAttribute("value");
							if (idUoLavoro.equals(key)) {
								Record record = new Record();
								record.setAttribute("ufficioGareAcquisti", key);
								record.setAttribute("desUfficioGareAcquisti", display);
								if(callback != null) {
									callback.execute(record);
								}
								return;
							}
						}
					}
				}						
				// Altrimenti se è configurato un default ed è nella lista seleziono quello
				if(defaultValue != null && !"".equals(defaultValue)) {
					if (data.getLength() > 0) {						
						for (int i = 0; i < data.getLength(); i++) {
							String key = data.get(i).getAttribute("key");
							String display = data.get(i).getAttribute("value");
							if (defaultValue.equals(key)) {
								Record record = new Record();
								record.setAttribute("ufficioGareAcquisti", key);
								record.setAttribute("desUfficioGareAcquisti", display);
								if(callback != null) {
									callback.execute(record);
								}
								return;
							}
						}
					}																							
				}						
			}
		});	
	}
	
}
