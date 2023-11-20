/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.MultiToolStripButton;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

/**
 * 
 * @author cristiano
 *
 */

public class TipologieDocumentaliLayout extends CustomLayout {

	private MultiToolStripButton addTipologieDocumentaliAUOButton;
	private MultiToolStripButton removeTipologieDocumentaliDaUOButton;
	private ToolStripButton addTipologieDocumentaliNewButton;
	protected Record record;

	public TipologieDocumentaliLayout() {
		this(null, null, null);
	}

	public TipologieDocumentaliLayout(String finalita, Boolean flgSelezioneSingola) {
		this(finalita, flgSelezioneSingola, null);
	}

	public TipologieDocumentaliLayout(String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		super("tipologiedocumentali", 
			  new GWTRestDataSource("TipologieDocumentaliDataSource", "idTipoDoc", FieldType.TEXT), 
			  new ConfigurableFilter("tipologiedocumentali"), 
			  new TipologieDocumentaliList("tipologiedocumentali", null, null), 
			  new TipologieDocumentaliDetail("tipologiedocumentali"),
			  finalita, 
			  flgSelezioneSingola, 
			  showOnlyDetail);

		multiselectButton.hide();

		if (!isAbilToIns()) {
			newButton.hide();
		}
		
		if (isAbilToIns() && showNewButton()) {
			newButton.show();
		}
		else{
			newButton.hide();
		}
		
		setMultiselect(false);
		
	}

	public TipologieDocumentaliLayout(String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail, Record record) {
		super("tipologiedocumentali", 
			  getGWTRestDataSource(record), 
			  new ConfigurableFilter("tipologiedocumentali"), 
			  new TipologieDocumentaliList("tipologiedocumentali", record.getAttribute("flgStatoAbilitazioneIn"), record.getAttribute("opzioniAbil")), 
			  new TipologieDocumentaliDetail("tipologiedocumentali"), 
			  finalita,
			  flgSelezioneSingola, 
			  showOnlyDetail);

		this.record = record;

		multiselectButton.hide();
		
		newButton.hide();

		setMultiselect(true);
	}

	private static GWTRestDataSource getGWTRestDataSource(Record record) {
		GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("TipologieDocumentaliDataSource", "idTipoDoc", FieldType.TEXT);
		lGWTRestDataSource.addParam("viewTipologiaDaUO", record.getAttribute("viewTipologiaDaUO"));
		lGWTRestDataSource.addParam("flgStatoAbilitazioneIn", record.getAttribute("flgStatoAbilitazioneIn"));
		lGWTRestDataSource.addParam("flgTipologiaDestAbilIn", record.getAttribute("flgTipologiaDestAbilIn"));
		lGWTRestDataSource.addParam("idUo", record.getAttribute("idUo"));
		lGWTRestDataSource.addParam("opzioniAbil", record.getAttribute("opzioniAbil"));		
		return lGWTRestDataSource;
	}

	public static boolean isAbilToIns() {
		return Layout.isPrivilegioAttivo("DC/TD;I");
	}

	public static boolean isAbilToMod() {
		return Layout.isPrivilegioAttivo("DC/ATT;M");
	}

	public static boolean isAbilToDel() {
		return Layout.isPrivilegioAttivo("DC/ATT;FC");
	}

	public static boolean isRecordAbilToMod(boolean flgDiSistema) {
		return !flgDiSistema && isAbilToMod();
	}

	public static boolean isRecordAbilToDel(boolean flgValido, boolean flgDiSistema) {
		return flgValido && !flgDiSistema && isAbilToDel();
	}

	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().tipologieDocumentali_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().tipologieDocumentali_edit_title(getTipoEstremiRecord(record));
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().tipologieDocumentali_view_title(getTipoEstremiRecord(record));
	}

	public String getTipoEstremiRecord(Record record) {
		return record.getAttribute("nome");
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

	@Override
	public boolean getDefaultMultiselect() {
		return false;
	}

	@Override
	protected ToolStripButton[] getCustomNewButtons() {
		
		final String opzioniAbil = ((TipologieDocumentaliList) getList()).getOpzioniAbil();
		
		if (((TipologieDocumentaliList) getList()).isListaTipologieDocAbilitate()) {
			
			// Se opzioniAbil = "P", mostro la lista delle tipologie pubblicabili
			if(opzioniAbil!=null && opzioniAbil.equalsIgnoreCase("P")){
				if (addTipologieDocumentaliNewButton == null) {
					addTipologieDocumentaliNewButton = new ToolStripButton();
					addTipologieDocumentaliNewButton.setIcon("lookup/tipologiamulti.png");
					addTipologieDocumentaliNewButton.setIconSize(16);
					addTipologieDocumentaliNewButton.setPrompt("Aggiungi tipologie documentali pubblicabili");
					addTipologieDocumentaliNewButton.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							final Record record = getRecord();
							record.setAttribute("flgStatoAbilitazioneIn", "0");
							record.setAttribute("opzioniAbil", opzioniAbil);
							TipologieDocumentaliWindow classifiche = new TipologieDocumentaliWindow("Tipologie non ancora abilitate per la pubblicazione,", record) {

								@Override
								public void manageOnCloseClick() {
									super.manageOnCloseClick();
									doSearch();
								}
							};
							classifiche.show();
						}
					});
				}
				
			}
			// Altrimenti mostro la lista delle tipologie
			else{
				
				if (addTipologieDocumentaliNewButton == null) {
					addTipologieDocumentaliNewButton = new ToolStripButton();
					addTipologieDocumentaliNewButton.setIcon("lookup/tipologiamulti.png");
					addTipologieDocumentaliNewButton.setIconSize(16);
					addTipologieDocumentaliNewButton.setPrompt("Aggiungi tipologie documentali");
					addTipologieDocumentaliNewButton.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							final Record record = getRecord();
							record.setAttribute("flgStatoAbilitazioneIn", "0");
							record.setAttribute("opzioniAbil", opzioniAbil);							
							TipologieDocumentaliWindow classifiche = new TipologieDocumentaliWindow("Tipologie documentali non abilitate alla U.0,", record) {

								@Override
								public void manageOnCloseClick() {
									super.manageOnCloseClick();
									doSearch();
								}
							};
							classifiche.show();
						}
					});
				}
			}
			
			return new ToolStripButton[] { addTipologieDocumentaliNewButton };

		} else
			return new ToolStripButton[] {};
	}

	@Override
	protected MultiToolStripButton[] getMultiselectButtons() {
		
		final String opzioniAbil = ((TipologieDocumentaliList) getList()).getOpzioniAbil();
		
		if (((TipologieDocumentaliList) getList()).isListaTipologieDocAbilitate()) {
			
			// Se opzioniAbil = "P", mostro la lista delle tipologie pubblicabili
			if(opzioniAbil!=null && opzioniAbil.equalsIgnoreCase("P")){
				if (removeTipologieDocumentaliDaUOButton == null) {
					removeTipologieDocumentaliDaUOButton = new MultiToolStripButton("buttons/revoke.png", this, "Rimuovi dalla U.O.", false) {

						@Override
						public void doSomething() {
							final RecordList listaTipologieDoc = new RecordList();

							for (int i = 0; i < list.getSelectedRecords().length; i++) {
								listaTipologieDoc.add(list.getSelectedRecords()[i]);
							}
							final Record lRecord = getRecord();
							lRecord.setAttribute("listaTipologieDoc", listaTipologieDoc);

							final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("TipologieDocumentaliDataSource");
							lGWTRestDataSource.performCustomOperation("rimuoviTipologieDocPubblicabiliDaUO", lRecord, new DSCallback() {

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
				
			}
			// Altrimenti mostro la lista delle tipologie
			else{
				if (removeTipologieDocumentaliDaUOButton == null) {
					removeTipologieDocumentaliDaUOButton = new MultiToolStripButton("buttons/revoke.png", this, "Rimuovi dalla U.O.", false) {

						@Override
						public void doSomething() {
							final RecordList listaTipologieDoc = new RecordList();

							for (int i = 0; i < list.getSelectedRecords().length; i++) {
								listaTipologieDoc.add(list.getSelectedRecords()[i]);
							}
							final Record lRecord = getRecord();
							lRecord.setAttribute("listaTipologieDoc", listaTipologieDoc);

							final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("TipologieDocumentaliDataSource");
							lGWTRestDataSource.performCustomOperation("rimuoviTipologieDocDaUO", lRecord, new DSCallback() {

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
				
			}
							
			return new MultiToolStripButton[] { removeTipologieDocumentaliDaUOButton };
			
		} else {
			
			// Se opzioniAbil = "P", mostro la lista delle tipologie pubblicabili
			if(opzioniAbil!=null && opzioniAbil.equalsIgnoreCase("P")){
				if (addTipologieDocumentaliAUOButton == null) {
					addTipologieDocumentaliAUOButton = new MultiToolStripButton("buttons/seleziona.png", this, "Aggiungi alla U.O.", false) {

						@Override
						public void doSomething() {
							final RecordList listaClassificazioni = new RecordList();

							for (int i = 0; i < list.getSelectedRecords().length; i++) {
								listaClassificazioni.add(list.getSelectedRecords()[i]);
							}
							final Record lRecord = getRecord();
							lRecord.setAttribute("listaTipologieDoc", listaClassificazioni);

							final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("TipologieDocumentaliDataSource");
							lGWTRestDataSource.performCustomOperation("aggiungiTipologieDocPubblicabiliAUO", lRecord, new DSCallback() {

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
			}
			// Altrimenti mostro la lista delle tipologie
			else{
				if (addTipologieDocumentaliAUOButton == null) {
					addTipologieDocumentaliAUOButton = new MultiToolStripButton("buttons/seleziona.png", this, "Aggiungi alla U.O.", false) {

						@Override
						public void doSomething() {
							final RecordList listaClassificazioni = new RecordList();

							for (int i = 0; i < list.getSelectedRecords().length; i++) {
								listaClassificazioni.add(list.getSelectedRecords()[i]);
							}
							final Record lRecord = getRecord();
							lRecord.setAttribute("listaTipologieDoc", listaClassificazioni);

							final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("TipologieDocumentaliDataSource");
							lGWTRestDataSource.performCustomOperation("aggiungiTipologieDocAUO", lRecord, new DSCallback() {

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
			}
			
			return new MultiToolStripButton[] { addTipologieDocumentaliAUOButton };
		}
	}

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}
	
	public static boolean isAbilTipologieDocPubblicabili() {
		return (( AurigaLayout.getParametroDB("SIST_ALBO")!=null && AurigaLayout.getParametroDB("SIST_ALBO").equalsIgnoreCase("AURIGA")));		
	}
	
	public boolean showNewButton(){
		return true;
	}
	
	@Override
	protected Record createMultiLookupRecord(Record record) {
		final Record newRecord = new Record();		
		newRecord.setAttribute("id", record.getAttributeAsString("idTipoDoc"));
		newRecord.setAttribute("nome", record.getAttributeAsString("nome"));
		newRecord.setAttribute("icona", "menu/tipologieDocumentali.png");
		return newRecord;
	}

	public int getMultiLookupGridSize(){
		int size = multiLookupGrid.getRecordList().getLength();
		return size;
	}
}
