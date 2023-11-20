/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;

import java.util.LinkedHashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.grid.ListGridField;

public class AttivaDisattivaSessioneInDelegaForm extends DynamicForm {

	private AttivaDisattivaSessioneInDelegaWindow selectionWindow;
	private DynamicForm form;
	private FilteredSelectItem lSelectDelega;
	private TextAreaItem lTextAreaItem;
	private HiddenItem idUserSelezionato;
	
	public AttivaDisattivaSessioneInDelegaForm(AttivaDisattivaSessioneInDelegaWindow pSchemaSelectionWindow) {
		
		form = this;
		selectionWindow = pSchemaSelectionWindow;
		
		setKeepInParentRect(true);
		setWidth100();
		setHeight100();
		setNumCols(2);
		setColWidths(1, 300);
		setCellPadding(5);
		setWrapItemTitles(false);
		
		idUserSelezionato = new HiddenItem("idUserSelezionato");
		
		final GWTRestDataSource delegheDS = new GWTRestDataSource("AurigaLoadDelegheDataSource", "idUser", FieldType.TEXT, true);
		
		lSelectDelega = new FilteredSelectItem("delega", "A nome di") {
			@Override
			public void onOptionClick(Record record) {				
				super.onOptionClick(record);
				manageOnOptionClick(getName(), record);				
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				manageClearSelect(getName());
			};
		};		
		ListGridField idUserField      = new ListGridField("idUser", "Id.User");  
		idUserField.setHidden(true);		
		ListGridField descrizioneField = new ListGridField("descrizione", "Cognome e nome"); 
		descrizioneField.setWidth("100%");
		lSelectDelega.setPickListFields(idUserField, descrizioneField);		
		lSelectDelega.setEmptyPickListMessage(I18NUtil.getMessages().attiva_disattiva_sessione_in_delega_form_selectDelega_noSearchOrEmptyMessage());
		lSelectDelega.setValueField("idUser");
		lSelectDelega.setDisplayField("descrizione");
		lSelectDelega.setOptionDataSource(delegheDS);
		lSelectDelega.setWidth(300);
		lSelectDelega.setAutoFetchData(false);		
		lSelectDelega.setAlwaysFetchMissingValues(true);
		lSelectDelega.setFetchMissingValues(true);	
		lSelectDelega.setRequired(true);
		lSelectDelega.setClearable(true);
		lSelectDelega.setShowIcons(true);
		
		lTextAreaItem = new TextAreaItem("avvertimenti");
		lTextAreaItem.setWidth(300);
		lTextAreaItem.setHeight(100);
		lTextAreaItem.setTitle("&nbsp;&nbsp;&nbsp;<img src=\"images/warning.png\" style=\"vertical-align:bottom\"/>&nbsp;<font color=\"red\"><b>Attenzione<b/></font>");
		lTextAreaItem.setVisible(false);
		lTextAreaItem.setCanEdit(false);

		setFields(lSelectDelega, lTextAreaItem, idUserSelezionato);
		setAlign(Alignment.CENTER);
		setTop(50);
	}

	protected void manageChangedDelega() {
		selectionWindow.manageChangeDelega();
	}

	public void initCombo(String delegaIn, String delegaDenominazioneIn){
		
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put(delegaIn, delegaDenominazioneIn);
		lSelectDelega.setValueMap(valueMap);

		lSelectDelega.setValue(delegaIn);
		form.setValue("delega", delegaIn);	
		form.setValue("idUserSelezionato", delegaIn);		
	}
	
	public String getUtenteSelezionato() {
		return form.getValueAsString("idUserSelezionato");
	}

	public void settaAvvertimenti(String avvertimenti) {
		form.setValue("avvertimenti", avvertimenti);
		lTextAreaItem.show();
	}

	private void manageOnOptionClick(String name, Record record){	
		form.setValue("idUserSelezionato", record.getAttributeAsString("idUser"));	
		manageChangedDelega();
	}
	
	private void manageClearSelect(String name){
		lSelectDelega.clearValue();
		lSelectDelega.setValue((String)null);					
		form.setValue("delega",      (String)null);
		idUserSelezionato.setValue((String)null);
		form.setValue("idUserSelezionato",      (String)null);
		manageChangedDelega();
	}
	
}