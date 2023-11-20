/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItem;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * 
 * @author Cristiano Daniele
 *
 */

public class RelEventTypeProcessDetail extends CustomDetail {

	protected DynamicForm form;
	protected DynamicForm formProcessi;

	private FilteredSelectItem idEventTypeInItem;
	private HiddenItem desEventTypeInItem;

	public RelEventTypeProcessDetail(String nomeEntita) {
		super(nomeEntita);
		// TODO Auto-generated constructor stub

		form = new DynamicForm();
		form.setValuesManager(vm);
		form.setWidth("100%");
		form.setHeight("5");
		form.setPadding(5);
		form.setWrapItemTitles(false);
		form.setNumCols(12);
		form.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		formProcessi = new DynamicForm();
		formProcessi.setValuesManager(vm);
		formProcessi.setWidth("100%");
		formProcessi.setHeight("5");
		formProcessi.setPadding(5);
		formProcessi.setWrapItemTitles(false);
		formProcessi.setNumCols(12);
		formProcessi.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		desEventTypeInItem = new HiddenItem("desEventTypeIn");

		GWTRestDataSource dipendenzeAltriAttributiDS = new GWTRestDataSource("LoadComboEventTypeDataSource", "key", FieldType.TEXT, true);
		ListGridField keyField = new ListGridField("key");
		keyField.setHidden(true);
		ListGridField nomeField = new ListGridField("value", "Nome");
		idEventTypeInItem = new FilteredSelectItem("idEventTypeIn", "Tipo evento") {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				desEventTypeInItem.setValue(record.getAttribute("value"));
				markForRedraw();
			}

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					desEventTypeInItem.setValue("");
				}
				markForRedraw();
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				desEventTypeInItem.setValue("");
				markForRedraw();
			};
		};
		idEventTypeInItem.setPickListFields(keyField, nomeField);
		idEventTypeInItem.setWidth(450);
		idEventTypeInItem.setClearable(true);
		idEventTypeInItem.setValueField("key");
		idEventTypeInItem.setDisplayField("value");
		idEventTypeInItem.setColSpan(10);
		idEventTypeInItem.setStartRow(true);
		idEventTypeInItem.setOptionDataSource(dipendenzeAltriAttributiDS);
		idEventTypeInItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});

		form.setItems(idEventTypeInItem, desEventTypeInItem);

		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(50);
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(50);

		lVLayout.addMember(form);

		addMember(lVLayout);
		addMember(lVLayoutSpacer);
	}

	@Override
	public void editRecord(Record record) {
		
		super.editRecord(record);
		loadEventType(record);
	}

	private void loadEventType(Record record) {
		GWTRestDataSource listaDataDS = (GWTRestDataSource) idEventTypeInItem.getOptionDataSource();
		if (record.getAttribute("idEventTypeIn") != null && !"".equals(record.getAttributeAsString("idEventTypeIn"))) {
			listaDataDS.addParam("idEventTypeIn", record.getAttributeAsString("idEventTypeIn"));
		} else {
			listaDataDS.addParam("idEventTypeIn", null);
		}
		if (record.getAttribute("desEventTypeIn") != null && !"".equals(record.getAttributeAsString("desEventTypeIn"))) {
			listaDataDS.addParam("desEventTypeIn", record.getAttributeAsString("desEventTypeIn"));
		} else {
			listaDataDS.addParam("desEventTypeIn", null);
		}
		idEventTypeInItem.setOptionDataSource(listaDataDS);
		idEventTypeInItem.fetchData();
	}

}
