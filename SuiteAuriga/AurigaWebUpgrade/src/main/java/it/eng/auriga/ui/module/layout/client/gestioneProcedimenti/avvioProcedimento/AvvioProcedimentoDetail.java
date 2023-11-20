/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.ResultSet;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;

public class AvvioProcedimentoDetail extends CustomDetail{

	private DynamicForm avvioProcedimentoForm;
	private FilteredSelectItemWithDisplay selezionaProcedimentoItem;
	private SelectItem selectItemFlussoDiLavoro; 
	private GWTRestDataSource gwtRestDataSourceFlusso; 

	public AvvioProcedimentoDetail(String nomeEntita) {
		super(nomeEntita);

		avvioProcedimentoForm = new DynamicForm();
		avvioProcedimentoForm.setColWidths(130, 500);
		avvioProcedimentoForm.setValuesManager(vm);
		
		creaSelectSelezionaProcedimento();
		
		selectItemFlussoDiLavoro = new SelectItem("flowTypeId", "Seleziona flusso di lavoro");
		selectItemFlussoDiLavoro.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return selectItemFlussoDiLavoro.isVisible();
			}
		}){
		});
		selectItemFlussoDiLavoro.setVisible(false);
		selectItemFlussoDiLavoro.setWidth(500);
		gwtRestDataSourceFlusso = new GWTRestDataSource("LoadComboSelezionaFlussoDiLavoroDataSource");
		selectItemFlussoDiLavoro.setOptionDataSource(gwtRestDataSourceFlusso);
		selectItemFlussoDiLavoro.addDataArrivedHandler(new DataArrivedHandler() {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				manageDataArrived(event);
			}
		});
		selectItemFlussoDiLavoro.setDisplayField("value");
		selectItemFlussoDiLavoro.setValueField("key");
		selectItemFlussoDiLavoro.setAutoFetchData(false);
		
		TextAreaItem lTextAreaItemOggetto = new TextAreaItem("oggettoProc", "Oggetto");
		lTextAreaItemOggetto.setRequired(true);
		lTextAreaItemOggetto.setWidth(500);
		lTextAreaItemOggetto.setHeight(60);
		TextAreaItem lTextAreaItemAnnotazioni = new TextAreaItem("noteProc", "Annotazioni");
		lTextAreaItemAnnotazioni.setWidth(500);
		lTextAreaItemAnnotazioni.setHeight(60);
		
		avvioProcedimentoForm.setFields(selezionaProcedimentoItem,selectItemFlussoDiLavoro, lTextAreaItemOggetto, lTextAreaItemAnnotazioni);
		
		VLayout lVLayout = new VLayout();  
		lVLayout.setWidth100();
		lVLayout.setHeight(50);			
		
		lVLayout.addMember(avvioProcedimentoForm);			

		addMember(lVLayout);
	}

	protected void manageDataArrived(DataArrivedEvent event) {
		ResultSet lResultSet = event.getData();
		if (lResultSet == null || lResultSet.getLength() == 0) {
			Layout.addMessage(new MessageBean("Procedimento senza flusso modellato: avvio non consentito", "", MessageType.ERROR));
		} else if (lResultSet.getLength() > 1 ){
			selectItemFlussoDiLavoro.show();
		} else if (lResultSet.getLength() == 1 ){
			vm.setValue("flowTypeId", lResultSet.get(0).getAttribute("key"));
		}
	}

	private void creaSelectSelezionaProcedimento() {
		SelectGWTRestDataSource lGwtRestDataSourceSelezionaProcedimento = new SelectGWTRestDataSource("LoadComboSelezionaProcedimentoDataSource", "idProcessType", FieldType.INTEGER, new String[]{"nome"}, true);
		selezionaProcedimentoItem = new FilteredSelectItemWithDisplay("idProcessType", lGwtRestDataSourceSelezionaProcedimento){
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				manageProcedimentoSelezionato(record);
			}			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				avvioProcedimentoForm.setValue("idProcessType", "");
			};		
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					avvioProcedimentoForm.setValue("idProcessType", "");
				}
			}
		};	
		selezionaProcedimentoItem.setColSpan(3);
		selezionaProcedimentoItem.setWidth("*");
		ListGridField nomeField = new ListGridField("nome", "Nome");
		nomeField.setWidth(200);		
		selezionaProcedimentoItem.setPickListFields(nomeField);		
		selezionaProcedimentoItem.setFilterLocally(true);
		selezionaProcedimentoItem.setValueField("idProcessType");
		selezionaProcedimentoItem.setDisplayField("nome");
		selezionaProcedimentoItem.setTitle("Seleziona procedimento");
		selezionaProcedimentoItem.setOptionDataSource(lGwtRestDataSourceSelezionaProcedimento); 
		selezionaProcedimentoItem.setWidth(500);
		selezionaProcedimentoItem.setRequired(true);
		selezionaProcedimentoItem.setClearable(true);
		selezionaProcedimentoItem.setShowIcons(true);
		selezionaProcedimentoItem.setItemHoverFormatter(new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("nome") : null;
			}
		});		
	}

	protected void manageProcedimentoSelezionato(Record record) {
		selectItemFlussoDiLavoro.clearValue();
		String idProcessType = record.getAttribute("idProcessType");
		GWTRestDataSource lGWTRestDataSource = (GWTRestDataSource)selectItemFlussoDiLavoro.getOptionDataSource();
		lGWTRestDataSource.addParam("idProcessType", idProcessType);
		selectItemFlussoDiLavoro.fetchData();
	} 

}
