/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.util.StringUtil;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.BlurEvent;
import com.smartgwt.client.widgets.form.fields.events.BlurHandler;
import com.smartgwt.client.widgets.form.validator.Validator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class SelezionaValoriDizionarioCanvas extends ReplicableCanvas {
	
	private static final int COMPONENT_WIDTH = 850;

	private ComboBoxItemValoriDizionario valueItem;

	private ReplicableCanvasForm mDynamicForm;
	
	public SelezionaValoriDizionarioCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		String dictionaryEntry = ((SelezionaValoriDizionarioItem) getItem()).getDictionaryEntry();
		boolean requiredStrInDes = ((SelezionaValoriDizionarioItem) getItem()).isRequiredStrInDes();
		
		valueItem = new ComboBoxItemValoriDizionario("value", null, dictionaryEntry, false, requiredStrInDes);
		valueItem.setValueField("value");
		valueItem.setDisplayField("value");
		valueItem.setShowPickerIcon(false);
		valueItem.setWidth(COMPONENT_WIDTH + 2);
		valueItem.setTextBoxStyle(it.eng.utility.Styles.textItem);
		valueItem.setRequired(true);
		valueItem.setCanEdit(true);
		valueItem.setColSpan(10);
		valueItem.setEndRow(false);
		valueItem.setStartRow(true);
		valueItem.setAutoFetchData(false);
		valueItem.setAlwaysFetchMissingValues(true);
		valueItem.setAddUnknownValues(((SelezionaValoriDizionarioItem) getItem()).getAddUnknownValues());
		valueItem.setFetchDelay(500);
		valueItem.setValidateOnChange(false);
		valueItem.setItemHoverFormatter(true, new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return StringUtil.asHTML((String) valueItem.getValue());
			}
		});
		
		Validator[] validators = ((SelezionaValoriDizionarioItem) getItem()).getValidators();
		if(validators != null && validators.length > 0) {
			valueItem.setValidators(validators);
			valueItem.addBlurHandler(new BlurHandler() {
				
				@Override
				public void onBlur(BlurEvent event) {
					
					valueItem.validate();
				}
			});
		}
		
		ListGrid proposteValoriPickListProperties = new ListGrid();
		proposteValoriPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		proposteValoriPickListProperties.setShowHeader(false);
		proposteValoriPickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {
				event.cancel();
				valueItem.setValue(event.getRecord().getAttribute("value"));				
			}
		});
		proposteValoriPickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				
				String value = valueItem != null && valueItem.getValue() != null ? (String) valueItem.getValue() : null;
				GWTRestDataSource proposteValoriDS = (GWTRestDataSource) valueItem.getOptionDataSource();
				proposteValoriDS.addParam("strInDes", value);				
				valueItem.setOptionDataSource(proposteValoriDS);
				valueItem.invalidateDisplayValueCache();
			}
		});
		valueItem.setPickListProperties(proposteValoriPickListProperties);
		
		mDynamicForm.setFields(valueItem);	
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
		
}
