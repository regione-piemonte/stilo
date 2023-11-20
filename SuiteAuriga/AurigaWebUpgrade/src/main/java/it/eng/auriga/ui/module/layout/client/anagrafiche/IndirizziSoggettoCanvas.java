/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.IndirizzoCanvas;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class IndirizziSoggettoCanvas extends IndirizzoCanvas {

	private HiddenItem rowIdItem;
	private SelectItem tipoItem;
	protected DateItem dataValidoDalItem;
	protected DateItem dataValidoFinoAlItem;

	public IndirizziSoggettoCanvas(IndirizziSoggettoItem item) {
		super(item);
	}

	public IndirizziSoggettoCanvas(IndirizziSoggettoItem item, HashMap<String, String> parameters) {
		super(item, parameters);
	}

	@Override
	public void buildMainForm() {

		final GWTRestDataSource tipoDS = new GWTRestDataSource("TipoIndirizzoDataSource", "key", FieldType.TEXT);

		mDynamicForm = new ReplicableCanvasForm() {
			
			@Override
			public boolean hasValue(Record defaultRecord) {
			
				Map<String,Object> values = getValuesManager() != null ? getValuesManager().getValues() : getValues();
				
				if (values != null && values.size() > 0) {
					if (checkIndirizzoValorizzato(values)) {
						return true;
					}
				}
				return false;
			}
			
			
		};
		
		mDynamicForm.setWrapItemTitles(false);

		rowIdItem = new HiddenItem("rowId");

		tipoItem = new SelectItem("tipo");
		tipoItem.setShowTitle(false);
		tipoItem.setValueField("key");
		tipoItem.setDisplayField("value");
		tipoItem.setOptionDataSource(tipoDS);
		tipoItem.setWidth(150);
		tipoItem.setRequired(true);
		tipoItem.setCachePickListResults(false);
		// tipoItem.setAllowEmptyValue(true);
		tipoItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {

			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				Criterion[] criterias = new Criterion[1];
				criterias[0] = new Criterion("flgPersonaFisica", OperatorId.EQUALS, ((IndirizziSoggettoItem) getItem()).getFlgPersonaFisica());
				return new AdvancedCriteria(OperatorId.AND, criterias);
			}
		});
		// tipoItem.setDefaultToFirstOption(true);
		String flgPersonaFisica = ((IndirizziSoggettoItem) getItem()).getFlgPersonaFisica();
		if (flgPersonaFisica != null && "1".equals(flgPersonaFisica)) {
			tipoItem.setDefaultValue("RS"); // Residenza
		} else {
			tipoItem.setDefaultValue("SL"); // Sede legale
		} 

		dataValidoDalItem = new DateItem("dataValidoDal", I18NUtil.getMessages().soggetti_detail_indirizzi_dataValidoDalItem_title());
		dataValidoDalItem.setColSpan(1);
//		dataValidoDalItem.setDefaultValue(DateUtil.format(new Date()));
		dataValidoDalItem.setRequired(true);
		dataValidoDalItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				GWTRestDataSource statoDS = (GWTRestDataSource) statoItem.getOptionDataSource();
				statoDS.addParam("tsVld", DateUtil.format((Date) event.getValue()));
				statoItem.setOptionDataSource(statoDS);

				GWTRestDataSource comuniDS = (GWTRestDataSource) comuneItem.getOptionDataSource();
				comuniDS.addParam("tsVld", DateUtil.format((Date) event.getValue()));
				comuneItem.setOptionDataSource(comuniDS);
			}
		});

		dataValidoFinoAlItem = new DateItem("dataValidoFinoAl", I18NUtil.getMessages().soggetti_detail_indirizzi_dataValidoFinoAlItem_title());
		dataValidoFinoAlItem.setColSpan(5);

		mDynamicForm.setFields(rowIdItem, tipoItem, dataValidoDalItem, dataValidoFinoAlItem);

		mDynamicForm.setNumCols(20);
		mDynamicForm.setColWidths("50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50",
				"100");

	}

	public void validateTipo(final String flgPersonaFisica) {
		mDynamicForm.redraw();
		Criterion[] criterias = new Criterion[1];
		criterias[0] = new Criterion("flgPersonaFisica", OperatorId.IEQUALS, flgPersonaFisica);
		tipoItem.getOptionDataSource().fetchData(new AdvancedCriteria(OperatorId.AND, criterias), new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {				
				LinkedHashMap<String, String> tipoValueMap = (LinkedHashMap<String, String>) response.getDataAsRecordList().getValueMap(tipoItem.getValueField(), tipoItem.getDisplayField());				
				tipoItem.setValueMap(tipoValueMap);
				if(!mDynamicForm.hasValue(((ReplicableItem) getItem()).getCanvasDefaultRecord())) {					
					if (flgPersonaFisica != null && "1".equals(flgPersonaFisica)) {
						mDynamicForm.setValue("tipo", "RS"); // Residenza
					} else {
						mDynamicForm.setValue("tipo", "SL"); // Sede legale
					}
				} else if (tipoItem.getValue() != null && !"".equals(tipoItem.getValueAsString()) && !tipoValueMap.containsKey(tipoItem.getValueAsString())) {
					mDynamicForm.setValue("tipo", "");
				}
				if (flgPersonaFisica != null && "1".equals(flgPersonaFisica)) {
					tipoItem.setDefaultValue("RS"); // Residenza
				} else {
					tipoItem.setDefaultValue("SL"); // Sede legale
				}
			}
		});
	}

	@Override
	public void editRecord(Record record) {
		
		// GWTRestDataSource tipoDS = (GWTRestDataSource) tipoItem.getOptionDataSource();
		// if(record.getAttribute("rowId") != null && !"".equals(record.getAttributeAsString("rowId"))) {
		// tipoDS.addParam("rowId", record.getAttributeAsString("rowId"));
		// } else {
		// tipoDS.addParam("rowId", null);
		// }
		// String flgPersonaFisica = getParams() != null ? getParams().get("flgPersonaFisica") : "";
		// tipoDS.addParam("flgPersonaFisica", flgPersonaFisica);
		// tipoItem.setOptionDataSource(tipoDS);

		GWTRestDataSource statoDS = (GWTRestDataSource) statoItem.getOptionDataSource();
		if (record.getAttribute("stato") != null && !"".equals(record.getAttributeAsString("stato"))) {
			statoDS.addParam("codIstatStato", record.getAttributeAsString("stato"));
		} else {
			statoDS.addParam("codIstatStato", null);
		}
		try {
			statoDS.addParam("tsVld", record.getAttributeAsDate("dataValidoDal") != null ? DateUtil.format(record.getAttributeAsDate("dataValidoDal")) : null);
		} catch(Exception e) {
			statoDS.addParam("tsVld", record.getAttribute("dataValidoDal"));
		}
		statoItem.setOptionDataSource(statoDS);

		/*
		GWTRestDataSource tipoToponimoDS = (GWTRestDataSource) tipoToponimoItem.getOptionDataSource();
		if (record.getAttribute("rowId") != null && !"".equals(record.getAttributeAsString("rowId"))) {
			tipoToponimoDS.addParam("rowId", record.getAttributeAsString("rowId"));
		} else {
			tipoToponimoDS.addParam("rowId", null);
		}
		tipoToponimoItem.setOptionDataSource(tipoToponimoDS);

		*/
		
		GWTRestDataSource comuniDS = (GWTRestDataSource) comuneItem.getOptionDataSource();
		if (record.getAttribute("comune") != null && !"".equals(record.getAttributeAsString("comune"))) {
			comuniDS.addParam("codIstatComune", record.getAttributeAsString("comune"));
			comuniDS.addParam("nomeComune", record.getAttributeAsString("nomeComune"));
		} else {
			comuniDS.addParam("codIstatComune", null);
			comuniDS.addParam("nomeComune", null);
		}
		try {
			comuniDS.addParam("tsVld", record.getAttributeAsDate("dataValidoDal") != null ? DateUtil.format(record.getAttributeAsDate("dataValidoDal")) : null);
		} catch(Exception e) {
			comuniDS.addParam("tsVld", record.getAttribute("dataValidoDal"));
		}
		comuneItem.setOptionDataSource(comuniDS);

		super.editRecord(record);
	}

	public boolean isIndirizzoObbligatorio() {
		return true;
	}

	@Override
	public boolean showFlgFuoriComune() {
		return ((IndirizziSoggettoItem) getItem()).isRubricaSoggetti() && super.showFlgFuoriComune();
	}

	public boolean showItemsIndirizzo() {
		return true;
	}

	public boolean showItemsIndirizzoWithBorder() {
		return false;
	}

}
