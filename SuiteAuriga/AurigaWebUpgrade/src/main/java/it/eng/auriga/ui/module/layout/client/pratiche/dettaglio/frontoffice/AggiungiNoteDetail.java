/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.BackDetailInterface;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.SaveDetailInterface;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 * 
 * @author Antonio Magnocavallo
 *
 */

public abstract class AggiungiNoteDetail extends CustomDetail implements SaveDetailInterface, BackDetailInterface {
	
	private DynamicForm  form;
	private TextAreaItem aggiungiNoteItem;
	
	private String idProcess;
	private String idEvento;
	private String idTipoEvento;
		
	private DettaglioPraticaLayout dettaglioPraticaLayout;
	
	public AggiungiNoteDetail(String nomeEntita, String idProcess, Record lRecordEvento, DettaglioPraticaLayout dettaglioPraticaLayout) {
		
		super(nomeEntita);
		
		HLayout lHLayout = new HLayout();
		lHLayout.setAlign(Alignment.CENTER);
		
		this.idProcess = idProcess;
		this.idTipoEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idTipoEvento") : null;
		this.idEvento = lRecordEvento != null ? lRecordEvento.getAttribute("idEvento") : null;
		this.dettaglioPraticaLayout = dettaglioPraticaLayout;
		
		form = new DynamicForm();
		form.setValuesManager(vm);
		
		aggiungiNoteItem = new TextAreaItem("aggiungiNote") {
			@Override
			public void setCanEdit(Boolean canEdit) {
				
				super.setCanEdit(canEdit);
		    	setTextBoxStyle(it.eng.utility.Styles.stickyNoteCustom);				
			}
		}; 
		aggiungiNoteItem.setShowTitle(false);
		aggiungiNoteItem.setTextBoxStyle(it.eng.utility.Styles.stickyNoteCustom);
		
		form.setFields(aggiungiNoteItem);
		
		lHLayout.addMember(form);
		addMember(lHLayout);	
	}
	
	@Override
	public void loadDati() {
		Record lRecord = new Record();
		lRecord.setAttribute("idProcess", idProcess);
		lRecord.setAttribute("idEvento", idEvento);
		lRecord.setAttribute("idTipoEvento", idTipoEvento);
		Layout.showWaitPopup("Caricamento dati in corso...");
		new GWTRestService<Record, Record>("AggiungiNoteDataSource").executecustom("loadDati", lRecord, new DSCallback() {			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS){
					form.editRecord(response.getData()[0]);
				}
			}
		}); 
	}

	@Override
	public void save() {
		if(form.validate()) {
			Record lRecord = new Record();
			lRecord.setAttribute("aggiungiNote", aggiungiNoteItem.getValueAsString());
			lRecord.setAttribute("idProcess", idProcess);
			lRecord.setAttribute("idEvento", idEvento);
			lRecord.setAttribute("idTipoEvento", idTipoEvento);
			Layout.showWaitPopup("Salvataggio in corso...");		
			new GWTRestService<Record, Record>("AggiungiNoteDataSource").call(lRecord, new ServiceCallback<Record>() {
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