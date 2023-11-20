/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;

/**
 * 
 * @author Antonio Magnocavallo
 *
 */

public class ApriDettaglioPraticaLayout extends DettaglioPraticaLayout {
		
	public ApriDettaglioPraticaLayout(String idPratica, String estremi, Record lRecordDettaglioPratica) {
		
		super(null, null);
		
		this.idProcess = idPratica;
		this.estremi = estremi;
		
		if(lRecordDettaglioPratica != null) {
			caricaDettaglioPratica(lRecordDettaglioPratica);
		} else {
			loadDati(null);	
		}
	}
	
	public void loadDati(final DSCallback callback) {
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idPratica", idProcess);
		
		GWTRestDataSource praticheDS = new GWTRestDataSource("PraticheDataSource", "idPratica", FieldType.TEXT);
		praticheDS.addParam("isAperturaDettaglioPratica", "true");
		praticheDS.getData(lRecordToLoad, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData,
					DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
					Record lRecordDettaglioPratica = response.getData()[0];
					lRecordDettaglioPratica.getAttribute("warningConcorrenza");
					caricaDettaglioPratica(lRecordDettaglioPratica);
					if(callback != null) {
						callback.execute(new DSResponse(), null, new DSRequest());
					}
				}
			}
		});
	}
	
}