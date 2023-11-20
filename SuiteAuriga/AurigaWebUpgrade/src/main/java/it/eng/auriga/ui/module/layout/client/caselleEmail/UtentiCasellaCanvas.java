/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.ListGridField;

public class UtentiCasellaCanvas extends ReplicableCanvas{	
	
	private SelectItem utenteItem;
	
	private CheckboxItem flgSmistatoreItem;	
	private CheckboxItem flgMittenteItem;	
	private CheckboxItem flgAmministratoreItem;	
	
	private ReplicableCanvasForm mDynamicForm;
	
	public UtentiCasellaCanvas(UtentiCasellaItem item) {
		// TODO Auto-generated constructor stub
		super(item);			
	}
	
	@Override
	public void disegna() {		
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		SelectGWTRestDataSource utenteDS = new SelectGWTRestDataSource("LoadComboUtentiCasellaDataSource", "idUser", FieldType.TEXT, new String[]{"cognomeNome", "username"}, true);
		utenteDS.addParam("dominio", getItem().getForm().getValuesManager().getValueAsString("idSpAoo"));
		
		utenteItem = new FilteredSelectItemWithDisplay("idUser", utenteDS);							
		ListGridField codiceField = new ListGridField("codice", "Cod.");
		codiceField.setWidth(100);		
		ListGridField cognomeNomeField = new ListGridField("cognomeNome", "Cognome e nome");		
		ListGridField usernameField = new ListGridField("username", "Username");				
		usernameField.setWidth(150);		
		utenteItem.setPickListFields(codiceField, cognomeNomeField, usernameField);		
		utenteItem.setFilterLocally(true);
		utenteItem.setValueField("idUser");  		
		utenteItem.setShowTitle(false);
		utenteItem.setRequired(true);
		utenteItem.setClearable(true);
		utenteItem.setShowIcons(true);
		utenteItem.setWidth(440);
		utenteItem.setAutoFetchData(false);
		utenteItem.setAlwaysFetchMissingValues(true);
		utenteItem.setFetchMissingValues(true);		
		if(AurigaLayout.getParametroDBAsBoolean("NRO_UTENTI_NONSTD")) {
			utenteItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimNonStdMessage());
		}
		utenteItem.setItemHoverFormatter(new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("cognomeNome") : null;
			}
		});		
		
		flgSmistatoreItem = new CheckboxItem("flgSmistatore", "smistatore");
		flgSmistatoreItem.setValue(false);
		flgSmistatoreItem.setColSpan(1);
		flgSmistatoreItem.setWidth("*");	
		
		flgMittenteItem = new CheckboxItem("flgMittente", "mittente");
		flgMittenteItem.setValue(false);
		flgMittenteItem.setColSpan(1);
		flgMittenteItem.setWidth("*");	
		
		flgAmministratoreItem = new CheckboxItem("flgAmministratore", "amministratore");
		flgAmministratoreItem.setValue(false);
		flgAmministratoreItem.setColSpan(1);
		flgAmministratoreItem.setWidth("*");	
		
		mDynamicForm.setFields(
			utenteItem,
			flgSmistatoreItem,
			flgMittenteItem,
			flgAmministratoreItem
		);
		
		mDynamicForm.setNumCols(10);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		addChild(mDynamicForm);
	}
	
	@Override
	public void editRecord(Record record) {
		manageLoadSelectInEditRecord(record, utenteItem, "idUser", new String[]{"cognomeNome", "username"}, "idUser");
		super.editRecord(record);
	}
	
	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();	
	}
	
	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
		
}
