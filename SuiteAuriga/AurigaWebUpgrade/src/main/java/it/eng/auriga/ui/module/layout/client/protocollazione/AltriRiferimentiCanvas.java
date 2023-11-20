/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.validator.CustomValidator;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;

public class AltriRiferimentiCanvas extends ReplicableCanvas {

	private SelectItem registroTipoRifItem;
	private ExtendedTextItem numeroItem;
	private AnnoItem annoItem;
	private DateItem dataItem;
	private TextAreaItem noteItem;

	private ReplicableCanvasForm mDynamicForm;

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);

		final GWTRestDataSource registroTipoRifDS = new GWTRestDataSource("LoadComboRegistroTipoRifDataSource", "key", FieldType.TEXT);

		registroTipoRifItem = new SelectItem("registroTipoRif", "Registro/tipo rif.");
		registroTipoRifItem.setValueField("key");
		registroTipoRifItem.setDisplayField("value");
		registroTipoRifItem.setOptionDataSource(registroTipoRifDS);
		registroTipoRifItem.setWidth(200);
		registroTipoRifItem.setRequired(true);
		registroTipoRifItem.setCachePickListResults(false);
		registroTipoRifItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {

			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				if (registroTipoRifItem.getValue() != null && !"".equals((String) registroTipoRifItem.getValue())) {
					Criterion[] criterias = new Criterion[1];
					criterias[0] = new Criterion("registroTipoRif", OperatorId.EQUALS, (String) registroTipoRifItem.getValue());
					return new AdvancedCriteria(OperatorId.AND, criterias);
				}
				return null;
			}
		});

		numeroItem = new ExtendedTextItem("numero", "N.ro");
		numeroItem.setRequired(true);
		numeroItem.setWidth(100);
		numeroItem.setColSpan(1);

		CustomValidator lAnnoDataValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				return false;
			}
		};
		lAnnoDataValidator.setErrorMessage("Uno tra anno e data deve essere valorizzato");

		annoItem = new AnnoItem("anno", "Anno");
		// annoItem.setValidators(lAnnoDataValidator);
		annoItem.setColSpan(1);

		dataItem = new DateItem("data", "Data");
		// dataItem.setValidators(lAnnoDataValidator);
		dataItem.setColSpan(1);

		noteItem = new TextAreaItem("note", "Annotazioni");
		noteItem.setHeight(40);
		noteItem.setWidth(650);
		noteItem.setStartRow(true);
		noteItem.setColSpan(20);

		mDynamicForm.setFields(registroTipoRifItem, numeroItem, annoItem, dataItem, noteItem);

		mDynamicForm.setNumCols(30);
		mDynamicForm.setColWidths("50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50",
				"100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100");

		addChild(mDynamicForm);

	}

	@Override
	public void editRecord(Record record) {
		GWTRestDataSource registroTipoRifDS = (GWTRestDataSource) registroTipoRifItem.getOptionDataSource();
		if (record.getAttribute("registroTipoRif") != null && !"".equals(record.getAttributeAsString("registroTipoRif"))) {
			registroTipoRifDS.addParam("registroTipoRif", record.getAttributeAsString("registroTipoRif"));
		} else {
			registroTipoRifDS.addParam("registroTipoRif", null);
		}
		registroTipoRifItem.setOptionDataSource(registroTipoRifDS);
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
