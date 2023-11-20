/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class OrganigrammaPopup extends ModalWindow {
	
	protected OrganigrammaPopup _window;
	protected DynamicForm _form;
	
	public DynamicForm getForm() {
		return _form;
	}

	protected OrganigrammaItem organigrammaItem;
	 
	protected ButtonItem confermaButton;
	protected ButtonItem annullaButton;
	
	public OrganigrammaPopup(String pTitle, final Map<String, String> pMapProperties, Record pRecord){
		
		super("lista_scelta_organigramma", true);
		
		_window = this;
		
		setTitle(pTitle);
		
		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);			
		
		_form = new DynamicForm();													
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(5);
		_form.setColWidths(120,"*","*","*","*");		
		_form.setCellPadding(5);		
		_form.setWrapItemTitles(false);	
		
		String flgNodoType = null;
		if(pMapProperties.get("flgNodoTypeParamDB") != null && !"".equals(pMapProperties.get("flgNodoTypeParamDB"))) {
			flgNodoType = AurigaLayout.getParametroDB(pMapProperties.get("flgNodoTypeParamDB"));
		} 
		if(flgNodoType == null || "".equals(flgNodoType)) {
			flgNodoType = pMapProperties.get("flgNodoType") != null && !"".equals(pMapProperties.get("flgNodoType")) ? pMapProperties.get("flgNodoType") : "UO;SV";
		}
		
		final String tipoAssegnatari = flgNodoType;
		
		final boolean showCheckIncludi = pMapProperties.get("showCheckIncludi") != null && Boolean.valueOf(pMapProperties.get("showCheckIncludi"));
		
		organigrammaItem = new OrganigrammaItem() {
			
			@Override
			public SelectGWTRestDataSource getOrganigrammaDataSource() {
				
				SelectGWTRestDataSource organigrammaDS = new SelectGWTRestDataSource("SelectOrganigrammaDatasource", "id", FieldType.TEXT, new String[]{"descrizione"}, true);				
				organigrammaDS.addParam("flgNodoTypeParamDB", pMapProperties.get("flgNodoTypeParamDB"));
				organigrammaDS.addParam("flgNodoType", pMapProperties.get("flgNodoType"));
				organigrammaDS.addParam("flgSoloVld", pMapProperties.get("flgSoloVld"));
				organigrammaDS.addParam("dtInizioVld", pMapProperties.get("dtInizioVld"));
				organigrammaDS.addParam("dtFineVld", pMapProperties.get("dtFineVld"));
				return organigrammaDS;			
			}			
			@Override
			public boolean getShowCheckIncludi() {
				return showCheckIncludi;
			}			
			@Override
			public String getTipoAssegnatari() {
				return tipoAssegnatari;
			}
		};	
		if(pMapProperties.get("multiple") == null || !Boolean.valueOf(pMapProperties.get("multiple"))) {
			organigrammaItem.setNotReplicable(true);
		}
		organigrammaItem.setName("listaOrganigramma");
		organigrammaItem.setShowTitle(false);
		
		_form.setFields(new FormItem[]{organigrammaItem});
				
		Button confermaButton = new Button("Ok");   
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {	
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				if(organigrammaItem.getTotalMembers() == 1 && !organigrammaItem.hasValue()) {						
					ReplicableCanvas canvas = organigrammaItem.getLastCanvas();
					organigrammaItem.setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
				if(organigrammaItem.validate()) {
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
		
		setIcon("lookup/organigramma.png");
		
		if(pRecord != null) {
			if(pRecord.getAttributeAsRecordList("listaOrganigramma") == null || pRecord.getAttributeAsRecordList("listaOrganigramma").isEmpty()) {					
				RecordList listaOrganigramma = new RecordList();
				Record lRecordDefault = new Record();
				lRecordDefault.setAttribute("codRapido", AurigaLayout.getCodRapidoOrganigramma());
				listaOrganigramma.add(lRecordDefault);
				pRecord.setAttribute("listaOrganigramma", listaOrganigramma);
			}
			_form.editRecord(pRecord);
		} 
		
		if(organigrammaItem.getTotalMembers() == 0 ) {						
			organigrammaItem.onClickNewButton();
		}
	}

	public abstract void onClickOkButton(Record record);
		
}