/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.DateUtil;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

public class PubblicazioneAlboConsultazioneRichiesteLayout extends CustomLayout {	
	
	protected Record record;
	
	public PubblicazioneAlboConsultazioneRichiesteLayout() {
		this(null, null);
	}
	
	public PubblicazioneAlboConsultazioneRichiesteLayout(Boolean flgSelezioneSingola) {
		this(flgSelezioneSingola, null);
	}

	public PubblicazioneAlboConsultazioneRichiesteLayout(Boolean flgSelezioneSingola, Boolean showOnlyDetail) {			
		super("pubblicazione_albo_consultazione_richieste", 
				new GWTRestDataSource("PubblicazioneAlboConsultazioneRichiesteDataSource", "idUdFolder", FieldType.TEXT),  
				null,
				new PubblicazioneAlboConsultazioneRichiesteList("pubblicazione_albo_consultazione_richieste") ,
				new PubblicazioneAlboConsultazioneRichiesteDetail("pubblicazione_albo_consultazione_richieste"),
				null,
				flgSelezioneSingola,
				showOnlyDetail
		);
		
		setMultiselect(false);
		
		this.setLookup(false);
		
		if (!isAbilToIns()) {
			newButton.hide();
		}
		deleteButton.setTitle("Elimina richiesta pubbl.");
	}	
	
	@Override
	public boolean isForcedToAutoSearch() {
		return true;
	}
	
	public static boolean isAbilToMod() {		
		return (Layout.isPrivilegioAttivo("PUB/RIC/INT;M") || Layout.isPrivilegioAttivo("PUB/RIC/EST;M"));
	}
	
	public static boolean isAbilToIns() {
		return (Layout.isPrivilegioAttivo("PUB/RIC/INT;I") || Layout.isPrivilegioAttivo("PUB/RIC/EST;I"));
	}
		
	
	public static boolean isAbilToView() {
		return true;
	}	
	
	public static boolean isRecordAbilToView(boolean flgLocked) {
		return !flgLocked && isAbilToView();
	}

	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_edit_title(getTipoEstremiRecord(record), DateUtil.format(record.getAttributeAsDate("dataInizioPubblicazione")));		
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().pubblicazione_albo_consultazione_richieste_detail_view_title(getTipoEstremiRecord(record), DateUtil.format(record.getAttributeAsDate("dataInizioPubblicazione")));		
	}
	
	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		super.viewMode();
		Record record = new Record(detail.getValuesManager().getValues());
		if(record.getAttributeAsBoolean("abilModificabile") && isAbilToMod()) {
			editButton.show();
			deleteButton.show();
		} else {
			editButton.hide();
			deleteButton.hide();
		}
		altreOpButton.hide();
	}

	@Override
	public void editMode(boolean fromViewMode) {
		super.editMode(fromViewMode);
		altreOpButton.hide();
	}

	@Override
	protected Record createMultiLookupRecord(Record record) {
		return new Record();
	}
	
	
	public void reloadList() {
		doSearch();
	}
	
	@Override
	protected GWTRestDataSource createNroRecordDatasource() {
		GWTRestDataSource datasource = new GWTRestDataSource("PubblicazioneAlboConsultazioneRichiesteDataSource", "idUdFolder", FieldType.TEXT);
		return datasource;
	}
	
	@Override
	protected Record[] extractRecords(String[] fields) {
		// Se sono in overflow i dati verranno recuperati con il metodo asincrono,
		// altrimenti utilizzo quelli nella lista a GUI
		if (overflow){
			return new Record[0];
		}else{
			return super.extractRecords(fields);
		}
	}
	
	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}
	
	@Override
	public void onSaveButtonClick() {
		final Record record = detail.getRecordToSave();
		if(detail.validate()) {
			realSave(record);
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}
	
	protected void realSave(final Record record) {		
		Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
		DSCallback callback = new DSCallback() {								
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {				
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record savedRecord = response.getData()[0];
					// dopo il salvataggio idRichPubbl viene ricalcolato con la nuova dataInizioPubblicazione salvata 
					try {
						list.manageLoadDetail(savedRecord, detail.getRecordNum(), new DSCallback() {							
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								viewMode();		
								Layout.hideWaitPopup();
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterSave_message(getTipoEstremiRecord(response.getData()[0])), "", MessageType.INFO));								
							}
						});						
					} catch(Exception e) {
						Layout.hideWaitPopup();
					}					
				} else {
					Layout.hideWaitPopup();
				}
			}
		};
		try {
			if(record.getAttribute("idUdFolder")==null || record.getAttribute("idUdFolder").equals("")) {			
				detail.getDataSource().addData(record, callback);
			} else {
				detail.getDataSource().updateData(record, callback);
			}
		} catch(Exception e) {
			Layout.hideWaitPopup();
		}
	}
	
	@Override
	public String getTipoEstremiRecord(Record record) {		
		String tipoReg = record.getAttribute("tipoRegNum") != null ? record.getAttribute("tipoRegNum") : "";
		String sigla = "";
		if (tipoReg.equals("R") || tipoReg.equals("PP")) {
			sigla = record.getAttribute("siglaRegNum") != null ? record.getAttribute("siglaRegNum") : "";
		} else {
			sigla = tipoReg;
		}
		String numero = record.getAttribute("nroRegNum") != null ? record.getAttribute("nroRegNum") : "";;
		String anno = record.getAttribute("annoRegNum") != null ? record.getAttribute("annoRegNum") : "";;										
		return sigla + " " + numero + "/" + anno;
	}
	
}