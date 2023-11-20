/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class SelezionaTipoAttoDetail extends CustomDetail {
	
	private SelezionaTipoAttoWindow window;
	private SelezionaTipoAttoDetail instance;
	
	private CheckboxItem flgNuovoComeCopiaItem;
	
	private DynamicForm scegliTipoForm;
	private SelectItem idTipoProc;
	private HiddenItem nomeTipoProc;
	private HiddenItem idTipoFlussoActiviti;
	private HiddenItem idTipoDocProposta;
	private HiddenItem nomeTipoDocProposta;
	private HiddenItem siglaProposta;
	private HiddenItem categoriaProposta;	
	private HiddenItem flgDelibera;
	private HiddenItem showDirigentiProponenti;
	private HiddenItem showAssessori;
	private HiddenItem showConsiglieri;
	
	private DynamicForm copiaComeForm; 
	private SelectItem tipoNumerazioneCopiaItem;
	private TextItem siglaCopiaItem;
	private NumericItem numeroCopiaItem;
	private NumericItem annoCopiaItem;
	private CheckboxItem flgCopiaAllegatiPareriItem;
	
	public SelezionaTipoAttoDetail(SelezionaTipoAttoWindow pWindow, String organoCollegiale){
		super(pWindow.getNomeEntita());
		
		instance = this;
		window = pWindow;
		
		setKeepInParentRect(true);
		setWidth100();
		setHeight100();
		setAlign(Alignment.CENTER);
		
		scegliTipoForm = new DynamicForm();
		scegliTipoForm.setNumCols(20);
		scegliTipoForm.setTop(80);
		scegliTipoForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		scegliTipoForm.setValuesManager(vm);
		
		GWTRestDataSource attoConFlussoWFDS = new GWTRestDataSource("LoadComboAttoConFlussoWFDataSource");
		attoConFlussoWFDS.addParam("organoCollegiale", organoCollegiale);
		idTipoProc = new SelectItem("idTipoProc", "Atto di tipo") {
			
			@Override
			public void onOptionClick(Record record) {
				nomeTipoProc.setValue(record.getAttribute("nomeTipoProc"));		
				idTipoFlussoActiviti.setValue(record.getAttribute("idTipoFlussoActiviti"));
				idTipoDocProposta.setValue(record.getAttribute("idTipoDocProposta"));
				nomeTipoDocProposta.setValue(record.getAttribute("nomeTipoDocProposta"));
				siglaProposta.setValue(record.getAttribute("siglaProposta"));
				categoriaProposta.setValue(record.getAttribute("categoriaProposta"));
				flgDelibera.setValue(record.getAttribute("flgDelibera"));
				showDirigentiProponenti.setValue(record.getAttribute("showDirigentiProponenti"));
				showAssessori.setValue(record.getAttribute("showAssessori"));
				showConsiglieri.setValue(record.getAttribute("showConsiglieri"));
			}			
		};
		idTipoProc.setShowTitle(true);
		idTipoProc.setWrapTitle(false);
		idTipoProc.setStartRow(true);
		idTipoProc.setRequired(true);
		idTipoProc.setWidth(613);
		idTipoProc.setValueField("idTipoProc");
		idTipoProc.setDisplayField("nomeTipoProc");		
		idTipoProc.setOptionDataSource(attoConFlussoWFDS);		
		idTipoProc.addDataArrivedHandler(new DataArrivedHandler() {	
			
			@Override
			public void onDataArrived(DataArrivedEvent event) {

				if (event.getData().getLength() > 0){
					Record record = event.getData().get(0);
					idTipoProc.setValue(record.getAttribute("idTipoProc"));
					nomeTipoProc.setValue(record.getAttribute("nomeTipoProc"));	
					idTipoFlussoActiviti.setValue(record.getAttribute("idTipoFlussoActiviti"));
					idTipoDocProposta.setValue(record.getAttribute("idTipoDocProposta"));
					nomeTipoDocProposta.setValue(record.getAttribute("nomeTipoDocProposta"));
					siglaProposta.setValue(record.getAttribute("siglaProposta"));	
					categoriaProposta.setValue(record.getAttribute("categoriaProposta"));
					flgDelibera.setValue(record.getAttribute("flgDelibera"));	
					showDirigentiProponenti.setValue(record.getAttribute("showDirigentiProponenti"));	
					showAssessori.setValue(record.getAttribute("showAssessori"));	
					showConsiglieri.setValue(record.getAttribute("showConsiglieri"));	
				}
				
//				//se c'è un solo tipo di atto avvio automaticamente il processo
//				if(event.getData().getLength() == 1) {
//					manageOnClick();
//				}
			}
		});
		
		
		nomeTipoProc = new HiddenItem("nomeTipoProc");
		idTipoFlussoActiviti = new HiddenItem("idTipoFlussoActiviti");
		idTipoDocProposta = new HiddenItem("idTipoDocProposta");
		nomeTipoDocProposta = new HiddenItem("nomeTipoDocProposta");
		siglaProposta = new HiddenItem("siglaProposta");
		categoriaProposta = new HiddenItem("categoriaProposta");
		flgDelibera = new HiddenItem("flgDelibera");
		showDirigentiProponenti = new HiddenItem("showDirigentiProponenti");
		showAssessori = new HiddenItem("showAssessori");
		showConsiglieri = new HiddenItem("showConsiglieri");
		
		scegliTipoForm.setFields(idTipoProc, nomeTipoProc, idTipoFlussoActiviti, idTipoDocProposta, nomeTipoDocProposta, siglaProposta, categoriaProposta, flgDelibera, showDirigentiProponenti, showAssessori, showConsiglieri);

		flgNuovoComeCopiaItem = new CheckboxItem("nuovoComeCopia", "Come copia dell'atto");
		flgNuovoComeCopiaItem.setWrapTitle(false);
		flgNuovoComeCopiaItem.setHeight(30);		
		flgNuovoComeCopiaItem.setWidth(150);
		flgNuovoComeCopiaItem.setStartRow(true);
		flgNuovoComeCopiaItem.setShowTitle(false);
		flgNuovoComeCopiaItem.setValue(false);
		flgNuovoComeCopiaItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				boolean flgAvvioComeCopia = event.getValue() != null ? (Boolean) event.getValue() : false;
			
				if(flgAvvioComeCopia) {
					tipoNumerazioneCopiaItem.show();
					siglaCopiaItem.show();
					numeroCopiaItem.setRequired(true);
					numeroCopiaItem.setAttribute("obbligatorio", true);
					numeroCopiaItem.show();
					annoCopiaItem.show();
					if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_COPIA_ALLEGATI_ATTO")) {
						flgCopiaAllegatiPareriItem.show();
					} else {
						flgCopiaAllegatiPareriItem.hide();
					}
				} else {
					tipoNumerazioneCopiaItem.hide();
					siglaCopiaItem.hide();
					numeroCopiaItem.setRequired(false);
					numeroCopiaItem.setAttribute("obbligatorio", false);
					numeroCopiaItem.hide();
					annoCopiaItem.hide();
					flgCopiaAllegatiPareriItem.hide();
				}
			}
		});

		tipoNumerazioneCopiaItem = new SelectItem("tipoNumerazioneCopia", "Numerazione");
		LinkedHashMap<String, String> numerazioneValueMap = new LinkedHashMap<>();
		numerazioneValueMap.put("I", "di proposta/iniziale");
		numerazioneValueMap.put("F", "finale");
		tipoNumerazioneCopiaItem.setValueMap(numerazioneValueMap);
		tipoNumerazioneCopiaItem.setRequired(false);
		tipoNumerazioneCopiaItem.setAllowEmptyValue(true);
		tipoNumerazioneCopiaItem.setWidth(150);
		tipoNumerazioneCopiaItem.setVisible(false);
		
		siglaCopiaItem = new TextItem("siglaCopia", "Sigla");
		siglaCopiaItem.setRequired(false);
		siglaCopiaItem.setWidth(70);
		siglaCopiaItem.setVisible(false);
		
		numeroCopiaItem = new NumericItem("numeroCopia", "N°");
		numeroCopiaItem.setRequired(false);
		numeroCopiaItem.setWidth(80);
		numeroCopiaItem.setVisible(false);
		numeroCopiaItem.setLength(7);
		
		annoCopiaItem = new NumericItem("annoCopia", "dell'anno");
		annoCopiaItem.setRequired(false);
		annoCopiaItem.setWidth(60);
		annoCopiaItem.setVisible(false);
		annoCopiaItem.setLength(4);				
		
		flgCopiaAllegatiPareriItem = new CheckboxItem("flgCopiaAllegatiPareri", "Inclusi allegati e pareri");
		flgCopiaAllegatiPareriItem.setWrapTitle(false);
		flgCopiaAllegatiPareriItem.setHeight(30);
		flgCopiaAllegatiPareriItem.setWidth(150);
		flgCopiaAllegatiPareriItem.setVisible(false);
		flgCopiaAllegatiPareriItem.setValue(false);
		
		copiaComeForm = new DynamicForm();
		copiaComeForm.setNumCols(20);
		copiaComeForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		copiaComeForm.setValuesManager(vm);
		copiaComeForm.setFields(flgNuovoComeCopiaItem, tipoNumerazioneCopiaItem, siglaCopiaItem, numeroCopiaItem, annoCopiaItem, flgCopiaAllegatiPareriItem);	
		
		VLayout lVLayout = new VLayout();  
		lVLayout.setWidth100();
		lVLayout.setHeight(50);					
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();

		lVLayout.addMember(scegliTipoForm);
		lVLayout.addMember(copiaComeForm);
		
		addMember(lVLayout);
		addMember(lVLayoutSpacer);	
	}

	
	public boolean isNuovoComeCopia() {
		return flgNuovoComeCopiaItem.getValue() != null ? flgNuovoComeCopiaItem.getValueAsBoolean() : false;
	}
	
}	