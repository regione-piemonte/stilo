/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItem;

public class AttributiAddEditabiliCanvas extends ReplicableCanvas {
	
	private FilteredSelectItem nomeAttributoItem;
	
	private ReplicableCanvasForm mDynamicForm;

	public AttributiAddEditabiliCanvas(AttributiAddEditabiliItem item) {
		super(item);
	}

	public AttributiAddEditabiliCanvas(AttributiAddEditabiliItem item, HashMap<String, String> parameters) {
		super(item, parameters);
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		GWTRestDataSource attributiAddEditabiliDS = new GWTRestDataSource("LoadComboAttributiAddEditabiliDataSource");
		attributiAddEditabiliDS.addParam("codTipoFlusso", ((AttributiAddEditabiliItem) getItem()).getCodTipoFlusso());
		attributiAddEditabiliDS.addParam("nomeTask", ((AttributiAddEditabiliItem) getItem()).getNomeTask());
		
		nomeAttributoItem = new FilteredSelectItem("nomeAttributo");		
		nomeAttributoItem.setWidth(450);
		nomeAttributoItem.setValueField("key");
		nomeAttributoItem.setDisplayField("value");
		nomeAttributoItem.setOptionDataSource(attributiAddEditabiliDS);
		nomeAttributoItem.setAutoFetchData(false);
		nomeAttributoItem.setAlwaysFetchMissingValues(true);
		nomeAttributoItem.setFetchMissingValues(true);
		nomeAttributoItem.setShowTitle(false);
		nomeAttributoItem.setRequired(true);
		nomeAttributoItem.setClearable(true);			
		ListGridField nomeField = new ListGridField("key", I18NUtil.getMessages().attributi_custom_nome());
		ListGridField labelField = new ListGridField("value", I18NUtil.getMessages().attributi_custom_etichetta());
		nomeAttributoItem.setPickListFields(nomeField, labelField);			
		ListGrid pickListProperties = nomeAttributoItem.getPickListProperties();
		pickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				GWTRestDataSource attributiAddEditabiliDS = (GWTRestDataSource) nomeAttributoItem.getOptionDataSource();
				attributiAddEditabiliDS.addParam("codTipoFlusso", ((AttributiAddEditabiliItem) getItem()).getCodTipoFlusso());
				attributiAddEditabiliDS.addParam("nomeTask", ((AttributiAddEditabiliItem) getItem()).getNomeTask());
				nomeAttributoItem.setOptionDataSource(attributiAddEditabiliDS);
				nomeAttributoItem.invalidateDisplayValueCache();
			}
		});
		nomeAttributoItem.setPickListProperties(pickListProperties);

		mDynamicForm.setFields(nomeAttributoItem);

		mDynamicForm.setNumCols(16);
		mDynamicForm.setColWidths("50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100");

		addChild(mDynamicForm);

	}

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}
	
	@Override
	public void editRecord(final Record record) {
		super.editRecord(record);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				comboNomeAttributoEditRecord(record);
			}
		});
	}

	private void comboNomeAttributoEditRecord(Record record) {
		GWTRestDataSource attributiAddEditabiliDS = (GWTRestDataSource) nomeAttributoItem.getOptionDataSource();
		if (record.getAttribute("nomeAttributo") != null && !"".equals(record.getAttributeAsString("nomeAttributo"))) {
			attributiAddEditabiliDS.addParam("nomeAttributo", record.getAttributeAsString("nomeAttributo"));
		} else {
			attributiAddEditabiliDS.addParam("nomeAttributo", null);
		}
		nomeAttributoItem.setOptionDataSource(attributiAddEditabiliDS);
		nomeAttributoItem.fetchData();
	}

}
