/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

public class TaskUnioneFileFascDetail extends TaskDettFascicoloDetail {

	protected TaskUnioneFileFascDetail instance;

	protected Boolean fileUnioneDaFirmare;
	protected Boolean fileUnioneDaTimbrare;

	public TaskUnioneFileFascDetail(String nomeEntita, String idProcess, String nomeFlussoWF, String processNameWF, String idFolder, Record lRecordEvento,
			DettaglioPraticaLayout dettaglioPraticaLayout) {

		super(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idFolder, lRecordEvento, dettaglioPraticaLayout);

		instance = this;

		fileUnioneDaFirmare = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("fileUnioneDaFirmare") : null;
		fileUnioneDaTimbrare = lRecordEvento != null ? lRecordEvento.getAttributeAsBoolean("fileUnioneDaTimbrare") : null;

	}

	@Override
	public void saveAndGo() {
		if (isEsitoTaskOk(attrEsito)) {
			salvaDati(true, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					idEvento = object.getAttribute("idEvento");
					unioneFileFasc(new ServiceCallback<Record>() {

						@Override
						public void execute(Record lRecordFinale) {
							Record lRecord = new Record();
							lRecord.setAttribute("instanceId", instanceId);
							lRecord.setAttribute("activityName", activityName);
							lRecord.setAttribute("idProcess", idProcess);
							lRecord.setAttribute("idEventType", idTipoEvento);
							lRecord.setAttribute("idEvento", idEvento);
							lRecord.setAttribute("note", messaggio);
							GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");
							lAurigaTaskDataSource.executecustom("salvaTask", lRecord, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {

											@Override
											public void execute(Record record) {
												Layout.hideWaitPopup();
												Layout.addMessage(new MessageBean("Procedimento avanzato al passo successivo", "", MessageType.INFO));
												next();
											}
										});
									}
								}
							});
						}
					});
				}
			});
		} else {
			super.saveAndGo();
		}
	}

	public void unioneFileFasc(final ServiceCallback<Record> callback) {
	
		Record lRecord = getRecordToSave(null);

		GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
		if (fileUnioneDaTimbrare != null && fileUnioneDaTimbrare) {
			lArchivioDatasource.addParam("fileUnioneDaTimbrare", "true");
		}
		lArchivioDatasource.executecustom("unioneFileFasc", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.hideWaitPopup();
					Record lRecordFinale = response.getData()[0];
					previewFileUnioneAndCallback(lRecordFinale, callback);
				}
			}
		});
	}

	public void previewFileUnioneAndCallback(final Record lRecordFinale, final ServiceCallback<Record> callback) {

		final String idUd = lRecordFinale.getAttribute("idUdAppartenenza");
		String idDocAllegato = lRecordFinale.getAttribute("idDocAllegato");
		addToRecent(idUd, idDocAllegato);
		final String display = lRecordFinale.getAttribute("nomeFileAllegato");
		final String uri = lRecordFinale.getAttribute("uriFileAllegato");
		final Boolean remoteUri = Boolean.valueOf(lRecordFinale.getAttribute("remoteUri"));
		final InfoFileRecord info = new InfoFileRecord(lRecordFinale.getAttributeAsObject("infoFile"));
		new PreviewWindow(uri, remoteUri, info, "FileToExtractBean", display) {

			@Override
			public void manageCloseClick() {
				
				super.manageCloseClick();
				if (callback != null) {
					callback.execute(lRecordFinale);
				}
			}
		};
	}

	public void addToRecent(String idUd, String idDoc) {
		if (idUd != null && !"".equals(idUd) && idDoc != null && !"".equals(idDoc)) {
			Record record = new Record();
			record.setAttribute("idUd", idUd);
			record.setAttribute("idDoc", idDoc);
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AddToRecentDataSource");
			lGwtRestDataSource.addData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

				}
			});
		}
	}

}
