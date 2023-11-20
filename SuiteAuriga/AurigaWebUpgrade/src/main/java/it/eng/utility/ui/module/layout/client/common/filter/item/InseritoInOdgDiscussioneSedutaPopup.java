/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.common.items.SelectItemValoriDizionario;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class InseritoInOdgDiscussioneSedutaPopup extends ModalWindow {
	
	protected InseritoInOdgDiscussioneSedutaPopup _window;
	protected DynamicForm _form;
	
	public DynamicForm getForm() {
		return _form;
	}

	protected SelectItemValoriDizionario organoCollegialeItem;
	protected HiddenItem desOrganoCollegiale;
	protected DateItem dataItem;
	protected SelectItem esitoItem;
	 
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
	
	public InseritoInOdgDiscussioneSedutaPopup(String pTitle, final Map<String, String> pMapProperties, Record pRecord){
		
		super("inserito_in_odg_discussione_seduta", true);
		
		_window = this;
		
		setTitle(pTitle);
		
		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);			
		
		_form = new DynamicForm();													
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(7);
		_form.setColWidths(1,1,1,1,1,1,"*");		
		_form.setCellPadding(5);		
		_form.setWrapItemTitles(false);	
		
		desOrganoCollegiale = new HiddenItem("desOrganoCollegiale");
		
		organoCollegialeItem = new SelectItemValoriDizionario("organoCollegiale", "Del", "ORGANI_COLLEGIALI") {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				desOrganoCollegiale.setValue(record.getAttribute("value"));
				markForRedraw();
			}

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					desOrganoCollegiale.setValue("");
				}
				markForRedraw();
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				desOrganoCollegiale.setValue("");
				markForRedraw();
			}
		};
		organoCollegialeItem.setRequired(true);
		organoCollegialeItem.setColSpan(1);
		organoCollegialeItem.setDefaultToFirstOption(false);
		organoCollegialeItem.addDataArrivedHandler(new DataArrivedHandler() {
			
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				if(organoCollegialeItem.getValue() == null &&
						event.getData() != null && event.getData().getLength() > 0) {
					if(desOrganoCollegiale != null) {
						desOrganoCollegiale.setValue(event.getData().get(0).getAttribute("value"));
					}
					organoCollegialeItem.setValue(event.getData().get(0).getAttribute("key"));
					organoCollegialeItem.fireEvent(new ChangedEvent(organoCollegialeItem.getJsObj()));
				}
			}
		});
		organoCollegialeItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if(esitoItem != null) {
					esitoItem.fetchData();
				}
				_form.markForRedraw();
			}
		});

		dataItem = new DateItem("data", "in data");
		dataItem.setColSpan(1);
		
		final GWTRestDataSource esitoOrganoCollegialeDS = new GWTRestDataSource("EsitoOrganoCollegialeDataSource", "key", FieldType.TEXT);
		
		esitoItem = new SelectItem("esito", "con esito") {
			
			@Override
			protected ListGrid builPickListProperties() {
				
				ListGrid esitoPickListProperties = super.builPickListProperties();	
				esitoPickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						GWTRestDataSource esitoDS = (GWTRestDataSource) esitoItem.getOptionDataSource();
						esitoDS.addParam("organoCollegiale", organoCollegialeItem.getValueAsString());												
						esitoItem.setOptionDataSource(esitoDS);
						esitoItem.invalidateDisplayValueCache();
					}
				});
				return esitoPickListProperties;
			}
		};
		esitoItem.setValueField("key");
		esitoItem.setDisplayField("value");
		esitoItem.setOptionDataSource(esitoOrganoCollegialeDS);
		esitoItem.setWidth(200);
		esitoItem.setColSpan(1);
		esitoItem.setClearable(true);
		
		_form.setFields(new FormItem[]{desOrganoCollegiale,organoCollegialeItem, dataItem, esitoItem});
				
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {	
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(_form.validate()) {
					onClickOkButton(_form.getValuesAsRecord());
					_window.markForDestroy();
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
		
		setIcon("blank.png");
		
		if(pRecord != null) {
			_form.editRecord(pRecord);
		} 
		
	}

	public abstract void onClickOkButton(Record record);
		
}