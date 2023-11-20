/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.BackDetailInterface;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.SaveDetailInterface;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.frontoffice.items.AllegatiProcItem;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;

public abstract class AllegatoProcDetail extends CustomDetail implements SaveDetailInterface, BackDetailInterface {
	
	private DynamicForm allegatiProcForm;
//	private AttachmentMultipleReplicableItem listaAllegatiProcItem;
	private AllegatiProcItem listaAllegatiProcItem;
	
	private String idProcess;
	private String idTipoAllegato;
	private String idEvento;
	private String idTipoEvento;
	private String rowId;
	
	private String flgAcqProd;
	private String codRuoloDocInProd;
	private String codStatoUdInProc;
	
	private RecordList listaAllegatiProcSaved;
	
	private DettaglioPraticaLayout dettaglioPraticaLayout;
	
	public AllegatoProcDetail(String nomeEntita, String idProcess, String idTipoAllegato, String flgAcqProd, String codRuoloDocInProd, String codStatoUdInProc, Record lRecordEvento, final RecordList listaAllegatiProcSaved, DettaglioPraticaLayout dettaglioPraticaLayout)
	{
		super(nomeEntita);
		
		this.idProcess = idProcess;
		this.idTipoAllegato = idTipoAllegato;
		this.idTipoEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoEvento") : null;
		this.idEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idEvento") : null;
		this.rowId = lRecordEvento != null ? lRecordEvento.getAttribute("rowId") : null;
		this.listaAllegatiProcSaved = listaAllegatiProcSaved;
		this.dettaglioPraticaLayout = dettaglioPraticaLayout;
		
		this.flgAcqProd = flgAcqProd;
		this.codRuoloDocInProd = codRuoloDocInProd;
		this.codStatoUdInProc = codStatoUdInProc;
		
		build();
	}
	
	private void build() {
		
		VLayout lVLayout = new VLayout();
		
		allegatiProcForm = new DynamicForm();
		allegatiProcForm.setWidth100();
		allegatiProcForm.setHeight100();
		allegatiProcForm.setValuesManager(vm);
		allegatiProcForm.setBorder("1px solid #5c7c9d");	
		allegatiProcForm.setPadding(5);
		
//		listaAllegatiProcItem = new AttachmentMultipleReplicableItem();
		listaAllegatiProcItem = new AllegatiProcItem();
		listaAllegatiProcItem.setName("listaAllegatiProc");
		listaAllegatiProcItem.setShowTitle(false);

		allegatiProcForm.setFields(listaAllegatiProcItem);
				
		lVLayout.addMember(allegatiProcForm);
		
		addMember(lVLayout);	
		
		loadDati();
		
	}

	@Override
	public void loadDati() {
		if(listaAllegatiProcSaved != null) {
			Record lDetailRecord = new Record();
			RecordList listaAllegatiProc = new RecordList();
			for(int i = 0; i < listaAllegatiProcSaved.getLength(); i++) {
				listaAllegatiProc.add(listaAllegatiProcSaved.get(i));
			}
			lDetailRecord.setAttribute("listaAllegatiProc", listaAllegatiProc);
			allegatiProcForm.editRecord(lDetailRecord);
		}
	}

	@Override
	public void save() {
		Record lRecord = new Record();
		lRecord.setAttribute("listaAllegatiProcSaved", listaAllegatiProcSaved);
		ArrayList<Map> listaAllegatiProc = (ArrayList<Map>) allegatiProcForm.getValues().get("listaAllegatiProc");
		RecordList listaAllegatiProcToSave = new RecordList();
		if(listaAllegatiProc != null) {
			for(int i = 0; i < listaAllegatiProc.size(); i++) {
				listaAllegatiProcToSave.add(new Record(listaAllegatiProc.get(i)));
			}
		}
		lRecord.setAttribute("listaAllegatiProcToSave", listaAllegatiProcToSave);
		lRecord.setAttribute("idTipoDocumento", idTipoAllegato);				
		lRecord.setAttribute("idProcess", idProcess);
		lRecord.setAttribute("idEvento", idEvento);
		lRecord.setAttribute("idTipoEvento", idTipoEvento);
		lRecord.setAttribute("rowId", rowId);			
		Layout.showWaitPopup("Salvataggio in corso...");		
		
		GWTRestService<Record, Record> allegatiPraticaDS = new GWTRestService<Record, Record>("AllegatiPraticaDataSource");
		allegatiPraticaDS.addParam("flgAcqProd", flgAcqProd);
		allegatiPraticaDS.addParam("codRuoloDocInProd", codRuoloDocInProd);		
		allegatiPraticaDS.addParam("codStatoUdInProc", codStatoUdInProc);		
		
		allegatiPraticaDS.call(lRecord, new ServiceCallback<Record>() {			
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
