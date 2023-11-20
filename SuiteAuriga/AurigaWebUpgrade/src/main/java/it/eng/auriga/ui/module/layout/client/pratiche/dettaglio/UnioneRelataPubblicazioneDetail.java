/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatoCanvas;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;

public class UnioneRelataPubblicazioneDetail extends FirmaPropostaAttoDetail {

	protected UnioneRelataPubblicazioneDetail instance;

	public UnioneRelataPubblicazioneDetail(String nomeEntita, String idProcess, String nomeFlussoWF, String processNameWF, String idUd, Record lRecordEvento,
			DettaglioPraticaLayout dettaglioPraticaLayout) {

		super(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idUd, lRecordEvento, dettaglioPraticaLayout);

		instance = this;
	}

	public void firmaAndReturn() {
		if(fileAllegatiItem != null && (fileAllegatiItem instanceof AllegatiItem)) {
			final AllegatoCanvas lAllegatoRelataCanvas = ((AllegatiItem)fileAllegatiItem).getAllegatoCanvasFromTipo(idTipoTaskDoc);
		if (lAllegatoRelataCanvas != null) {
			lAllegatoRelataCanvas.firma(new ServiceCallback<Record>() {

				@Override
				public void execute(Record lRecordAllegatoRelata) {
					Record lRecord = new Record();
					Record lRecordProtocolloOriginale = getRecordToSave(null);
					lRecord.setAttribute("protocolloOriginale", lRecordProtocolloOriginale);
					Record lRecordFileFirmati = new Record();
					Record[] lRecordFiles = new Record[] { lRecordAllegatoRelata };
					lRecordFileFirmati.setAttribute("files", lRecordFiles);
					lRecord.setAttribute("fileFirmati", lRecordFileFirmati);
					GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("FirmaDirigenteDatasource");
					lGwtRestService.executecustom("unisciAllegatoAPrimario", lRecordFileFirmati, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							salvaDati(false, new ServiceCallback<Record>() {

								@Override
								public void execute(Record object) {
									callbackSalvaDati(object);
								}
							});
						}
					});
				}
			});
			}
		}
	}

}
