/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.BackDetailInterface;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.SaveDetailInterface;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;

import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.layout.VLayout;

public abstract class EventoLayoutDinamico extends CustomDetail implements SaveDetailInterface, BackDetailInterface {

	protected AttributiDinamiciDetail detail;
	protected DettaglioPraticaLayout dettaglioPraticaLayout;

	protected String idProcess;
	protected String idEvento;
	protected String idTipoEvento;
	protected String rowId;
	protected String nome;
	protected VLayout lVLayoutDinamico;

	private RecordList attributiAdd;
	protected Map mappaDettAttrLista;
	protected Map mappaValoriAttrLista;
	protected Map mappaVariazioniAttrLista;
	protected Map mappaDocumenti;

	public EventoLayoutDinamico(final String nomeEntita, String idProcess, Record lRecordEvento, DettaglioPraticaLayout dettaglioPraticaLayout) {

		super(nomeEntita);

		this.idProcess = idProcess;
		this.idTipoEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoEvento") : null;
		this.idEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idEvento") : null;
		this.rowId = lRecordEvento != null ? lRecordEvento.getAttribute("rowId") : null;
		this.nome = lRecordEvento != null ? lRecordEvento.getAttribute("nome") : null;

		this.dettaglioPraticaLayout = dettaglioPraticaLayout;

		setHeight100();
		setWidth100();

		lVLayoutDinamico = new VLayout();
		lVLayoutDinamico.setHeight100();
		lVLayoutDinamico.setWidth100();

		addMember(lVLayoutDinamico);

		GWTRestService<Record, Record> lAttributiDinamiciDatasource = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
		Record lAttributiDinamiciRecord = new Record();
		lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_PROCESS_HISTORY");
		lAttributiDinamiciRecord.setAttribute("rowId", rowId);
		lAttributiDinamiciRecord.setAttribute("tipoEntita", idTipoEvento);
		lAttributiDinamiciDatasource.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {

			@Override
			public void execute(final Record object) {
				attributiAdd = object.getAttributeAsRecordList("attributiAdd");
				mappaDettAttrLista = object.getAttributeAsMap("mappaDettAttrLista");
				mappaValoriAttrLista = object.getAttributeAsMap("mappaValoriAttrLista");
				mappaVariazioniAttrLista = object.getAttributeAsMap("mappaVariazioniAttrLista");
				mappaDocumenti = object.getAttributeAsMap("mappaDocumenti");
				detail = new AttributiDinamiciDetail(nomeEntita, attributiAdd, mappaDettAttrLista, mappaValoriAttrLista, mappaVariazioniAttrLista,
						mappaDocumenti, null, null, null);
				detail.setCanEdit(editing);
				lVLayoutDinamico.setMembers(detail);
				loadDati();
			}
		});
	}

	@Override
	public void loadDati() {
		// if(idEvento != null && !"".equals(idEvento)) {
		// Record lRecord = new Record();
		// lRecord.setAttribute("idProcess", idProcess);
		// lRecord.setAttribute("idTipoEvento", idTipoEvento);
		// lRecord.setAttribute("idEvento", idEvento);
		// lRecord.setAttribute("rowId", rowId);
		// Layout.showWaitPopup("Caricamento dati in corso...");
		// new GWTRestService<Record, Record>("CustomEventDatasource").executecustom("loadDati", lRecord, new DSCallback() {
		// @Override
		// public void execute(DSResponse response, Object rawData, DSRequest request) {
		// Layout.hideWaitPopup();
		// if (response.getStatus() == DSResponse.STATUS_SUCCESS){
		// Record lRecordValori = response.getData()[0];
		// Record detailRecord = new Record(lRecordValori.getAttributeAsMap("valori"));
		// detailRecord.setAttribute("idProcess", idProcess);
		// detailRecord.setAttribute("idTipoEvento", idTipoEvento);
		// detailRecord.setAttribute("idEvento", idEvento);
		// detailRecord.setAttribute("rowId", rowId);
		// if(detail != null) detail.getValuesManager().editRecord(detailRecord);
		// }
		// }
		// });
		// }
	}

	@Override
	public void save() {
		if (detail.getValuesManager().validate()) {
			// ATTENZIONE: se provo a prendere i valori direttamente dal vm, i valori degli attributi lista non li prende correttamente
			// final Record detailRecord = new Record(detail.getValuesManager().getValues());
			final Record detailRecord = detail.getRecordToSave();
			detailRecord.setAttribute("valori", detail.getMappaValori(detailRecord));
			detailRecord.setAttribute("tipiValori", detail.getMappaTipiValori(detailRecord));
			detailRecord.setAttribute("idProcess", idProcess);
			detailRecord.setAttribute("idEvento", idEvento);
			detailRecord.setAttribute("idTipoEvento", idTipoEvento);
			detailRecord.setAttribute("desEvento", dettaglioPraticaLayout.getDisplayNameEvento(nome));
			Layout.showWaitPopup("Salvataggio in corso...");
			new GWTRestService<Record, Record>("CustomEventDatasource").call(detailRecord, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					dettaglioPraticaLayout.creaMenuGestisciIter(new ServiceCallback<Record>() {

						@Override
						public void execute(Record record) {
							Layout.hideWaitPopup();
							back();
						}
					});
				}
			});
		}
	}

}
