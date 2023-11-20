/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.protocollazione.ClassificaFascicoloItem;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class ClassificazioneFascicolazionePopup extends ModalWindow {
		
	protected ClassificazioneFascicolazionePopup _window;
	protected DynamicForm _form;
	
	public DynamicForm getForm() {
		return _form;
	}

	protected HiddenItem livelloRiservatezzaHiddenItem;
	protected ClassificaFascicoloItem classificaFascicoloItem;
	
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
	
	public ClassificazioneFascicolazionePopup(boolean isFascicolazione, Record pListRecord){
		this(isFascicolazione, pListRecord, null);
	}
	
	public ClassificazioneFascicolazionePopup(boolean isFascicolazione, Record pListRecord, Record pDetailRecord){
		
		super("classificazione_fascicolazione", true);
		
		_window = this;

		if(isFascicolazione) {
			setTitle("Compila dati classificazione e fascicolazione");
		} else {
			setTitle("Compila dati classificazione ed eventuale fascicolazione");
		}
		
		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);			
		
		_form = new DynamicForm();													
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(8);
		_form.setColWidths(120,1,1,1,1,1,"*","*");		
		_form.setCellPadding(5);		
		_form.setWrapItemTitles(false);		
		
		livelloRiservatezzaHiddenItem = new HiddenItem("livelloRiservatezza");
		
		classificaFascicoloItem = new ClassificaFascicoloItem();
		if(isFascicolazione) {
			classificaFascicoloItem.setFascicoloObbligatorio(true);
		}
		if(pListRecord == null) {
			classificaFascicoloItem.setFascicolazioneMassiva(true);
			classificaFascicoloItem.setAttribute("obbligatorio", true);
		} else {
			classificaFascicoloItem.setAttribute("obbligatorio", isRequiredClassificazioneFascicolazione(pListRecord, pDetailRecord));
		} 
		
		classificaFascicoloItem.setName("listaClassFasc");
		classificaFascicoloItem.setShowTitle(false);
		classificaFascicoloItem.setCanEdit(true);
		
		_form.setFields(new FormItem[]{livelloRiservatezzaHiddenItem, classificaFascicoloItem});
		
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				if(classificaFascicoloItem.validate()) {	
					checkPropagaRiservatezzaFascicoliAContenuti(new DSCallback() {	
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							
							onClickOkButton(new DSCallback() {			
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									
									_window.markForDestroy();
								}
							});
						}																				
					});
				}
			}
		});
		
		Button annullaButton = new Button("Annulla");   
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16); 
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				_window.markForDestroy();	
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
        _buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);	
		 		
		setAlign(Alignment.CENTER);
		setTop(50);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET 
		VLayout portletLayout = new VLayout();  
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		layout.addMember(_form);
				
		portletLayout.addMember(layout);		
		portletLayout.addMember(_buttons);
						
		setBody(portletLayout);
				
		setIcon(isFascicolazione ? "archivio/fascicola.png" : "archivio/classfasc.png");
		
		if(pListRecord != null) {
			GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
			Record lRecordToLoad = new Record();
			lRecordToLoad.setAttribute("idUd", pListRecord.getAttribute("idUdFolder"));
			lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Record lDetailRecord = response.getData()[0];
					_form.editRecord(lDetailRecord);	
					if (classificaFascicoloItem != null && classificaFascicoloItem.getTotalMembers() == 0) {
						classificaFascicoloItem.onClickNewButton();
					}
					_window.show();
				}
			});
		} else {		 
			_form.editNewRecord();	
			_window.show();
		}
	}
		
	public void checkPropagaRiservatezzaFascicoliAContenuti(final DSCallback callback) {
		RecordList lRecordList = _form.getValueAsRecordList("listaClassFasc");
		String estremiFascicoliRiservati = "";
		boolean fascicoliRiservati = false;
		if(lRecordList != null && lRecordList.getLength() > 0) { 
			for(int i = 0; i < lRecordList.getLength(); i++) {
				Record lRecord = lRecordList.get(i);
				if(lRecord.getAttribute("livelloRiservatezza") != null && "1".equals(lRecord.getAttribute("livelloRiservatezza"))) {
					if(!"".equals(estremiFascicoliRiservati)) {
						estremiFascicoliRiservati += ", ";
					}
					estremiFascicoliRiservati += getEstremiFascicolo(lRecord);		
					fascicoliRiservati = true;
				}
			}
		}		
		if(fascicoliRiservati && (livelloRiservatezzaHiddenItem.getValue() == null || "".equals(livelloRiservatezzaHiddenItem.getValue()))) {
			SC.ask("Fascicolo/i " + estremiFascicoliRiservati + " riservati: vuoi estendere la riservatezza al documento?", new BooleanCallback() {								
				@Override
				public void execute(Boolean value) {
					
					if(value == null) return;
					if(value) {
						livelloRiservatezzaHiddenItem.setValue("1");
					}	
					callback.execute(new DSResponse(), null, new DSRequest());					
				}
			});
		} else {
			callback.execute(new DSResponse(), null, new DSRequest());				
		}
	}
	
	public String getEstremiFascicolo(Record record) {
		String estremi = "";
		if(record.getAttributeAsString("annoFascicolo") != null && !"".equals(record.getAttributeAsString("annoFascicolo"))) {
			estremi += record.getAttributeAsString("annoFascicolo") + " ";
		}	
		if(record.getAttributeAsString("indiceClassifica") != null && !"".equals(record.getAttributeAsString("indiceClassifica"))) {
			estremi += record.getAttributeAsString("indiceClassifica") + " ";
		}	
		if(record.getAttributeAsString("nroFascicolo") != null && !"".equals(record.getAttributeAsString("nroFascicolo"))) {						
			estremi += "N° " + record.getAttributeAsString("nroFascicolo");
			if(record.getAttributeAsString("nroSottofascicolo") != null && !"".equals(record.getAttributeAsString("nroSottofascicolo"))) {
				estremi += "/" + record.getAttributeAsString("nroSottofascicolo");
			}
			if (record.getAttributeAsString("nroInserto") != null && !"".equals(record.getAttributeAsString("nroInserto"))) {
				estremi += "/" + record.getAttributeAsString("nroInserto");
			}
			estremi += " ";
		}
		if(record.getAttributeAsString("nome") != null && !"".equals(record.getAttributeAsString("nome"))) {
			estremi += record.getAttributeAsString("nome");						
		}		
		return estremi;
	}	

	public abstract void onClickOkButton(DSCallback callback);
	
	public boolean isRequiredClassificazioneFascicolazione(Record pListRecord, Record pDetailRecord){
		String flgTipoProv = pListRecord.getAttributeAsString("flgTipoProv");

		boolean flgPropostaAtto = pDetailRecord != null && pDetailRecord.getAttributeAsBoolean("flgPropostaAtto") != null && pDetailRecord.getAttributeAsBoolean("flgPropostaAtto");
		
		switch (flgTipoProv) {
		case "U":
		case "E":
			return AurigaLayout.getParametroDBAsBoolean("OBBL_CLASSIF_PROT_" + flgTipoProv);
		case "I":
			return flgPropostaAtto ? AurigaLayout.getParametroDBAsBoolean("OBBL_CLASSIF_PROP_ATTO") : AurigaLayout.getParametroDBAsBoolean("OBBL_CLASSIF_PROT_I");
		default:
			return false;
		}
	}
}
