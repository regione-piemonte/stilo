/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;

public class UfficioLiquidatoreCanvas extends ReplicableCanvas {

	protected HiddenItem nomeUfficioLiquidatoreItem;
	protected ExtendedTextItem codRapidoUfficioLiquidatoreItem;
	protected FilteredSelectItemWithDisplay ufficioLiquidatoreItem;
	private ReplicableCanvasForm mDynamicForm;

	public UfficioLiquidatoreCanvas(UfficioLiquidatoreItem item) {
		super(item);
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);

		nomeUfficioLiquidatoreItem = new HiddenItem("nomeUfficioLiquidatore");
		
		codRapidoUfficioLiquidatoreItem = new ExtendedTextItem("codRapidoUfficioLiquidatore", "Cod. rapido");
		codRapidoUfficioLiquidatoreItem.setWidth(120);
		codRapidoUfficioLiquidatoreItem.setColSpan(1);
		codRapidoUfficioLiquidatoreItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (event.getValue() != null && !"".equals(event.getValue())) {
					cercaSoggettoLD();
				}
			}
		});
		
		SelectGWTRestDataSource ufficioLiquidatoreDS = new SelectGWTRestDataSource("LoadComboGruppoUfficioLiquidatoreDataSource", "idGruppo", FieldType.TEXT, new String[] { "nomeGruppo" }, true);		
		
		ufficioLiquidatoreItem = new FilteredSelectItemWithDisplay("ufficioLiquidatore", ufficioLiquidatoreDS) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("nomeUfficioLiquidatore", record.getAttributeAsString("nomeGruppo"));
				mDynamicForm.setValue("codRapidoUfficioLiquidatore", record.getAttributeAsString("codiceRapidoGruppo"));
				
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("ufficioLiquidatore", "");
				mDynamicForm.setValue("nomeUfficioLiquidatore", "");
				mDynamicForm.setValue("codRapidoUfficioLiquidatore", "");
			}
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("ufficioLiquidatore", "");
					mDynamicForm.setValue("nomeUfficioLiquidatore", "");
					mDynamicForm.setValue("codRapidoUfficioLiquidatore", "");
				}
            }
		};
		ufficioLiquidatoreItem.setRequired(true);
		ufficioLiquidatoreItem.setShowTitle(false);
		ufficioLiquidatoreItem.setAutoFetchData(false);
		ufficioLiquidatoreItem.setFetchMissingValues(true);			
		ListGridField codiceRapidoGruppoField = new ListGridField("codiceRapidoGruppo", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codiceRapidoGruppoField.setWidth(70);
		ListGridField nomeGruppoField = new ListGridField("nomeGruppo", I18NUtil.getMessages().protocollazione_detail_nomeItem_title());
		nomeGruppoField.setWidth("*");
		ufficioLiquidatoreItem.setPickListFields(codiceRapidoGruppoField, nomeGruppoField);
		ufficioLiquidatoreItem.setFilterLocally(true);
		ufficioLiquidatoreItem.setValueField("idGruppo");
		ufficioLiquidatoreItem.setOptionDataSource(ufficioLiquidatoreDS);
		ufficioLiquidatoreItem.setShowTitle(false);
		ufficioLiquidatoreItem.setWidth(400);
		ufficioLiquidatoreItem.setClearable(true);
		ufficioLiquidatoreItem.setShowIcons(true);

		SpacerItem spacer = new SpacerItem();
		spacer.setWidth(20);
		spacer.setColSpan(1);

		mDynamicForm.setFields(nomeUfficioLiquidatoreItem, codRapidoUfficioLiquidatoreItem, spacer, spacer, ufficioLiquidatoreItem);

		mDynamicForm.setNumCols(16);
		mDynamicForm.setColWidths("50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100");

		addChild(mDynamicForm);

	}
	
	protected void cercaSoggettoLD() {
		mDynamicForm.setValue("ufficioLiquidatore", (String) null);
		mDynamicForm.clearErrors(true);
		final String value = codRapidoUfficioLiquidatoreItem.getValueAsString().toUpperCase();
		if (value != null && !"".equals(value)) {
			SelectGWTRestDataSource ufficioLiquidatoreDS = new SelectGWTRestDataSource("LoadComboGruppoUfficioLiquidatoreDataSource", "idGruppo", FieldType.TEXT, new String[] { "nomeGruppo" }, true);	
			ufficioLiquidatoreDS.extraparam.put("codiceRapidoGruppo", value);
			ufficioLiquidatoreDS.fetchData(null, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					RecordList data = response.getDataAsRecordList();
					boolean trovato = false;
					if (data.getLength() > 0) {
						for (int i = 0; i < data.getLength(); i++) {
							String codiceRapido = data.get(i).getAttribute("codiceRapidoGruppo");
							if (codiceRapido != null && value.equalsIgnoreCase(codiceRapido)) {
								mDynamicForm.setValue("ufficioLiquidatore", data.get(i).getAttribute("idGruppo"));
								mDynamicForm.setValue("nomeUfficioLiquidatore", data.get(i).getAttribute("nomeGruppo"));
								mDynamicForm.setValue("codRapidoUfficioLiquidatore", data.get(i).getAttribute("codiceRapidoGruppo"));
								ufficioLiquidatoreItem.fireEvent(new ChangedEvent(ufficioLiquidatoreItem.getJsObj()));
								trovato = true;
								break;
							}
						}
					}
					if (!trovato) {
						mDynamicForm.setFieldErrors("codRapidoUfficioLiquidatore", "Gruppo inesistente");						
					}
				}
			});
		}
	}

	@Override
	public void editRecord(Record record) {
		manageLoadSelectInEditRecord(record, ufficioLiquidatoreItem, "ufficioLiquidatore", "nomeUfficioLiquidatore", "ufficioLiquidatore");
		super.editRecord(record);
	}

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}

}
