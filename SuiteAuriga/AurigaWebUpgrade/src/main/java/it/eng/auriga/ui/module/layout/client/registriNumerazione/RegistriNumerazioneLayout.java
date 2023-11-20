/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.MultiToolStripButton;

public class RegistriNumerazioneLayout extends CustomLayout {
	
	private MultiToolStripButton addRegistroAUO;
	private MultiToolStripButton removeRegistroDaUO;
	
	private ToolStripButton addRegistri;
	protected Record record;
	
	public RegistriNumerazioneLayout() {
		this(null, null, null);
	}

	public RegistriNumerazioneLayout(String finalita, Boolean flgSelezioneSingola) {
		this(finalita, flgSelezioneSingola, null);
	}

	public RegistriNumerazioneLayout(String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		super("registri_numerazione", 
				 new GWTRestDataSource("RegistriNumerazioneDataSource", "idTipoRegNum", FieldType.TEXT),  
				new ConfigurableFilter("registri_numerazione"),
				new RegistriNumerazioneList("registri_numerazione", null), 
				new RegistriNumerazioneDetail("registri_numerazione"), 
				finalita, 
				flgSelezioneSingola, 
				showOnlyDetail);

		multiselectButton.hide();

		if (isAbilToIns() && showNewButton()) {
			newButton.show();
		}
		else{
			newButton.hide();
		}
		
		setMultiselect(false);
	}
	
	public RegistriNumerazioneLayout(String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail, Record record) {
		super("registri_numerazione", 
				getGWTRestDataSource(record), 
				new ConfigurableFilter("registri_numerazione"),
				new RegistriNumerazioneList("registri_numerazione", record.getAttribute("flgStatoAbilitazioneIn")), 
				new RegistriNumerazioneDetail("registri_numerazione"), 
				finalita, 
				flgSelezioneSingola, 
				showOnlyDetail);

		this.record = record;
		
		multiselectButton.hide();
		
		if (isAbilToIns()) {
			newButton.show();
		}
		else{
			newButton.hide();
		}

		setMultiselect(true);
	}
	
	public static boolean isAbilToIns() {
		return Layout.isPrivilegioAttivo("DC/TR;I");
	}

	public static boolean isAbilToMod() {
		return Layout.isPrivilegioAttivo("DC/TR;M");
	}

	public static boolean isAbilToDel() {
		return Layout.isPrivilegioAttivo("DC/TR;FC");
	}	
	
	public static boolean isRecordAbilToMod(boolean flgDiSistema) {
		return !flgDiSistema && isAbilToMod();
	}
	
	public static boolean isRecordAbilToDel(boolean flgValido, boolean flgDiSistema) {
		return flgValido && !flgDiSistema && isAbilToDel();
	}
	
	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().registri_numerazione_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().registri_numerazione_edit_title(getTipoEstremiRecord(record));
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().registri_numerazione_view_title(getTipoEstremiRecord(record));
	}

	public String getTipoEstremiRecord(Record record) {
		return record.getAttribute("descrizione");
	}

	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		super.viewMode();
		if (isAbilToDel()) {
			deleteButton.show();
		} else {
			deleteButton.hide();
		}
		if (isAbilToMod()) {
			editButton.show();
		} else {
			editButton.hide();
		}
		altreOpButton.hide();
	}

	@Override
	public void editMode(boolean fromViewMode) {
		super.editMode(fromViewMode);
		altreOpButton.hide();
	}

	private static GWTRestDataSource getGWTRestDataSource(Record record) {
		GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("RegistriNumerazioneDataSource", "idTipoRegNum", FieldType.TEXT);
		
		if(record!=null){
			lGWTRestDataSource.addParam("callFrom", record.getAttribute("callFrom"));
			lGWTRestDataSource.addParam("flgStatoAbilitazioneIn", record.getAttribute("flgStatoAbilitazioneIn"));
			lGWTRestDataSource.addParam("flgTipologiaDestAbilIn", record.getAttribute("flgTipologiaDestAbilIn"));
			lGWTRestDataSource.addParam("idUo", record.getAttribute("idUo"));
			lGWTRestDataSource.addParam("opzioniAbil", record.getAttribute("opzioniAbil"));	
		}
		
		return lGWTRestDataSource;
	}
	
	@Override
	public boolean getDefaultMultiselect() {
		return false;
	}

	@Override
	protected ToolStripButton[] getCustomNewButtons() {
		if (  isAbilToAddRegistroAUO() && ((RegistriNumerazioneList) getList()).isListaRegistriAbilitati()) {
			if (addRegistri == null) {
				addRegistri = new ToolStripButton();
				addRegistri.setIcon("lookup/tipologiamulti.png");
				addRegistri.setIconSize(16);
				addRegistri.setPrompt("Aggiungi registri");
				addRegistri.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						final Record record = getRecord();
						record.setAttribute("flgStatoAbilitazioneIn", "0");
						RegistriNumerazioneWindow registri = new RegistriNumerazioneWindow("Registri non abilitati alla U.0,", record) {

							@Override
							public void manageOnCloseClick() {
								super.manageOnCloseClick();
								doSearch();
							}
						};
						registri.show();
					}
				});
			}
			return new ToolStripButton[] { addRegistri };

		} else
			return new ToolStripButton[] {};
	}
	
	@Override
	protected MultiToolStripButton[] getMultiselectButtons() {
		if (((RegistriNumerazioneList) getList()).isListaRegistriAbilitati()) {
			if (removeRegistroDaUO == null) {
				removeRegistroDaUO = new MultiToolStripButton("buttons/revoke.png", this, "Rimuovi dalla U.O.", false) {

					@Override
					public boolean toShow() {
						return isAbilToRemoveRegistroDaUO();
					}
					
					@Override
					public void doSomething() {
						final RecordList listaRecordSelezionati = new RecordList();

						for (int i = 0; i < list.getSelectedRecords().length; i++) {
							listaRecordSelezionati.add(list.getSelectedRecords()[i]);
						}
						final Record lRecord = getRecord();
						lRecord.setAttribute("listaRegistriNumerazione", listaRecordSelezionati);

						final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("RegistriNumerazioneDataSource");
						lGWTRestDataSource.performCustomOperation("rimuoviRegistroDaUO", lRecord, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									doSearch();
								}
							}
						}, new DSRequest());
					}
				};
			}
			return new MultiToolStripButton[] { removeRegistroDaUO };
		} else {
			if (addRegistroAUO == null) {
				addRegistroAUO = new MultiToolStripButton("buttons/seleziona.png", this, "Aggiungi alla U.O.", false) {

					@Override
					public boolean toShow() {
						return isAbilToAddRegistroAUO();
					}

					@Override
					public void doSomething() {
						final RecordList listaRecordSelezionati = new RecordList();

						for (int i = 0; i < list.getSelectedRecords().length; i++) {
							listaRecordSelezionati.add(list.getSelectedRecords()[i]);
						}
						final Record lRecord = getRecord();
						lRecord.setAttribute("listaRegistriNumerazione", listaRecordSelezionati);

						final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("RegistriNumerazioneDataSource");
						lGWTRestDataSource.performCustomOperation("aggiungiRegistroAUO", lRecord, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									doSearch();
								}
							}
						}, new DSRequest());
					}
				};
			}
			return new MultiToolStripButton[] {addRegistroAUO};
		}
	}
	
	public Record getRecord() {
		
		return record;
	}
	
	public void setRecord(Record record) {
		this.record = record;
	}
	
	public boolean showNewButton(){
		return true;
	}
	
	@Override
	protected Record createMultiLookupRecord(Record record) {
		final Record newRecord = new Record();		
		newRecord.setAttribute("id", record.getAttributeAsString("idTipoRegNum"));
		newRecord.setAttribute("nome", record.getAttributeAsString("descrizione"));
		newRecord.setAttribute("icona", "menu/registri_numerazione.png");
		return newRecord;
	}
	
	public int getMultiLookupGridSize(){
		int size = multiLookupGrid.getRecordList().getLength();
		return size;
	}
	
	public static boolean isAbilToAddRegistroAUO() {
		return Layout.isPrivilegioAttivo("SIC/SO;M");
	}

	public static boolean isAbilToRemoveRegistroDaUO() {
		return Layout.isPrivilegioAttivo("SIC/SO;M");
	}
}