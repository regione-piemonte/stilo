/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.StampaEtichettaUtility;
import it.eng.auriga.ui.module.layout.client.StampaEtichettaUtility.StampaEtichettaCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;

/**
 * 
 * @author DANCRIST
 * 
 * Classe relativa alla stampa dei barcode su etichetta con Segnatura o Tipologia
 *
 */

public class CopertinaEtichettaUtil {
	
	/**
	 * 
	 * Generazione etichetta con segnatura singola o multipla e con posizione o senza.
	 * 
	 * - Pratica pregressa: DmpkRegistrazionedocGetbarcodedacapofilapratica
	 * - Protocollazione:   DmpkRegistrazionedocGettimbrodigreg
	 */
	public static void stampaEtichettaDatiSegnatura(final Record record) {

		Layout.showWaitPopup("Stampa etichetta in corso...");
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", record.getAttribute("idUd"));
		lRecord.setAttribute("nrAllegato", record.getAttribute("numeroAllegato"));
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("PreparaValoriEtichettaDatasource");
		if(record.getAttribute("numeroAllegato") != null && !"".equals(record.getAttribute("numeroAllegato"))){
			lGwtRestService.addParam("provenienza", "A");
		}
		lRecord.setAttribute("barcodePraticaPregressa", record.getAttribute("barcodePraticaPregressa"));
		lRecord.setAttribute("idFolder", record.getAttribute("idFolder"));
		lRecord.setAttribute("sezionePratica", record.getAttribute("sezionePratica"));
		lGwtRestService.performCustomOperation("getEtichettaDatiSegnatura",lRecord, new DSCallback() {
	
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {	
					Record object = response.getData()[0];
					String nomeStampante = record.getAttribute("nomeStampantePred");
					final Record[] etichette = object.getAttributeAsRecordArray("etichette");
					final String numCopie = "1";
					StampaEtichettaUtility.stampaEtichetta("", "", nomeStampante, "", etichette, numCopie, new StampaEtichettaCallback() {

						@Override
						public void execute() {
							
						}
					});
				}else{
					Layout.addMessage(new MessageBean("Impossibile stampare l'etichetta", "", MessageType.ERROR));
				}
			}

		},new DSRequest());
	}
	
	
	/**
	 * 
	 * Generazione etichetta con tipologia singola o multipla.
	 * 
	 * - Pratica pregressa: DmpkRegistrazionedocGettimbrospecxtipo ( Flgdocfolderin=F)
	 * - Protocollazione:   DmpkRegistrazionedocGettimbrodigreg	   ( Flgdocfolderin=D)
	 */
	public static void stampaEtichettaDatiTipologia(final Record record) {

		Layout.showWaitPopup("Stampa etichetta in corso...");
		Record lRecord = new Record();
		lRecord.setAttribute("idDoc", record.getAttribute("idDoc"));
		lRecord.setAttribute("idFolder", record.getAttribute("idFolder"));
		lRecord.setAttribute("nrAllegato", record.getAttribute("numeroAllegato"));
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("PreparaValoriEtichettaDatasource");
		lGwtRestService.performCustomOperation("getEtichettaDatiTipologia",lRecord, new DSCallback() {


			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {	
					Record object = response.getData()[0];
					String nomeStampante = record.getAttribute("nomeStampantePred");
					final Record[] etichette = object.getAttributeAsRecordArray("etichette");
					final String numCopie = "1";
					StampaEtichettaUtility.stampaEtichetta("", "", nomeStampante, "", etichette, numCopie, new StampaEtichettaCallback() {

						@Override
						public void execute() {
							
						}
					});
				}else{
					Layout.addMessage(new MessageBean("Impossibile stampare l'etichetta", "", MessageType.ERROR));
				}
			}

		},new DSRequest());
	}

}
