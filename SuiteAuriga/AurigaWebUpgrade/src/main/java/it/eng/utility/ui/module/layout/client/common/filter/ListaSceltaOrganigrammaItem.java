/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class ListaSceltaOrganigrammaItem extends CustomItem {
			
	public ListaSceltaOrganigrammaItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public ListaSceltaOrganigrammaItem(){
		super();
	}
		
	public ListaSceltaOrganigrammaItem(final Map<String, String> property){
		super(property);
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new ListaSceltaOrganigrammaItem(jsObj);
		return lCustomItem;
	}
		
	@Override
	public CustomItemFormField[] getFormFields() {
				
		CustomItemFormField listaOrganigramma = new CustomItemFormField("listaOrganigramma", this);
		HiddenItem listaOrganigrammaEditorType = new HiddenItem("listaOrganigramma");
		listaOrganigramma.setEditorType(listaOrganigrammaEditorType);
		listaOrganigramma.setEndRow(false);
		
		CustomItemFormField listaDescrizioni = new CustomItemFormField("listaDescrizioni", this);
		TextItem listaDescrizioniEditorType = new TextItem("listaDescrizioni");
		listaDescrizioniEditorType.setCanEdit(false);
		listaDescrizioniEditorType.setWidth(400);	
		listaDescrizioni.setEditorType(listaDescrizioniEditorType);
		listaDescrizioni.setEndRow(false);		
		listaDescrizioni.setItemHoverFormatter(new FormItemHoverFormatter() {
			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				
				return (String) form.getValue("listaDescrizioniHtml");
			}
		});	
		listaDescrizioni.setHoverWidth(600);
		
		CustomItemFormField editButton = new CustomItemFormField("edit", this);		
		ImgButtonItem editButtonEditorType = new ImgButtonItem("editButton", "buttons/modify.png", I18NUtil.getMessages().modifyButton_prompt());   
		editButtonEditorType.setAlign(Alignment.CENTER);   
		editButtonEditorType.setAlwaysEnabled(true);     
		editButtonEditorType.setIconWidth(16);
		editButtonEditorType.setIconHeight(16);	
		editButtonEditorType.addIconClickHandler(new IconClickHandler() {		
			
			@Override
			public void onIconClick(IconClickEvent event) {
				Record lRecord = new Record(_form.getValues());
				OrganigrammaPopup lOrganigrammaPopup = new OrganigrammaPopup("Modifica filtro su organigramma", property, lRecord) {
					@Override
					public void onClickOkButton(Record values) {
						
						editRecord(values);						
					}					
				};
				lOrganigrammaPopup.show();
			}
		});
		editButton.setEditorType(editButtonEditorType);
		
		return new CustomItemFormField[]{listaOrganigramma, listaDescrizioni, editButton};
			
	}
	
	@Override
	protected void editRecord(Record record) {
	
		if (record.getAttribute("listaOrganigramma")!=null){
			RecordList listaOrganigramma = new RecordList();
			String listaDescrizioni = null;
			String listaDescrizioniHtml = null;
			RecordList lRecordListOrganigramma = new RecordList(record.getAttributeAsJavaScriptObject("listaOrganigramma"));
			for (int i = 0; i < lRecordListOrganigramma.getLength(); i++){	
				Record lRecordOrganigramma = lRecordListOrganigramma.get(i);
				if(lRecordOrganigramma.getAttribute("organigramma") != null && !"".equals(lRecordOrganigramma.getAttribute("organigramma"))) {
					listaOrganigramma.add(lRecordOrganigramma);
					String descrizione = lRecordOrganigramma.getAttribute("codRapido") + " ** " + lRecordOrganigramma.getAttribute("descrizione");
					boolean flgIncludiSottoUO = lRecordOrganigramma.getAttributeAsBoolean("flgIncludiSottoUO") != null && lRecordOrganigramma.getAttributeAsBoolean("flgIncludiSottoUO");
					boolean flgIncludiScrivanie = lRecordOrganigramma.getAttributeAsBoolean("flgIncludiScrivanie") != null && lRecordOrganigramma.getAttributeAsBoolean("flgIncludiScrivanie");
					if(flgIncludiSottoUO && flgIncludiScrivanie) {
						descrizione += " (sotto-UO e scrivanie)";
					} else if(flgIncludiSottoUO) {
						descrizione += " (sotto-UO)";
					} else if(flgIncludiScrivanie) {
						descrizione += " (scrivanie)";
					}
					if(listaDescrizioni == null) {
						listaDescrizioni = descrizione;
					} else {
						listaDescrizioni += " , " + descrizione;
					}			
					String descrizioneHtml = lRecordOrganigramma.getAttribute("descrizione");			
					if(lRecordOrganigramma.getAttribute("typeNodo") != null && "UO".equals(lRecordOrganigramma.getAttribute("typeNodo"))) {
						descrizioneHtml = "<b>" + descrizioneHtml + "</b>";
					}
					descrizioneHtml = lRecordOrganigramma.getAttribute("codRapido") + " ** " + descrizioneHtml;
					if(flgIncludiSottoUO && flgIncludiScrivanie) {
						descrizioneHtml += " (sotto-UO e scrivanie)";
					} else if(flgIncludiSottoUO) {
						descrizioneHtml += " (sotto-UO)";
					} else if(flgIncludiScrivanie) {
						descrizioneHtml += " (scrivanie)";
					}
					if(listaDescrizioniHtml == null) {
						listaDescrizioniHtml = descrizioneHtml;
					} else {
						listaDescrizioniHtml += "<br/>" + descrizioneHtml;
					}	
				}				
			}
			record.setAttribute("listaOrganigramma", listaOrganigramma);
			record.setAttribute("listaDescrizioni", listaDescrizioni);
			record.setAttribute("listaDescrizioniHtml", listaDescrizioniHtml);
		}
		super.editRecord(record);
		_instance.storeValue(record);
	}
		
}