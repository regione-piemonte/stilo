/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class FlussiAssociatiWorkflowCanvas extends ReplicableCanvas {

	private ReplicableCanvasForm mDynamicForm;

	private SelectItem codTipoFlussoItem;
	private DateItem dataInizioVldItem;
	private DateItem dataFineVldItem;

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setNumCols(10);
		
		GWTRestDataSource codTipoFlussoDS = new GWTRestDataSource("FlussiWorkflowDataSource", "key", FieldType.TEXT, true);
		codTipoFlussoDS.addParam("flgSoloValidi", "true");
		
		codTipoFlussoItem = new SelectItem("codTipoFlusso", "Iter workflow"); 
		codTipoFlussoItem.setWidth(300);
		codTipoFlussoItem.setStartRow(true);
		codTipoFlussoItem.setOptionDataSource(codTipoFlussoDS);		
		codTipoFlussoItem.setValueField("key");
		codTipoFlussoItem.setDisplayField("value");
		codTipoFlussoItem.setRequired(true);		

		dataInizioVldItem = new DateItem("dataInizioVld", "In uso dal");
		dataInizioVldItem.setColSpan(1);
		
		dataFineVldItem = new DateItem("dataFineVld", "al");
		dataFineVldItem.setColSpan(1);
		
		mDynamicForm.setFields(codTipoFlussoItem, dataInizioVldItem, dataFineVldItem);

		mDynamicForm.setColWidths("50", "100", "50", "100", "50", "100", "50", "100", "50", "100");
		addChild(mDynamicForm);

	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}
	
	@Override
	public void editRecord(Record record) {
		
		GWTRestDataSource codTipoFlussoDS = (GWTRestDataSource) codTipoFlussoItem.getOptionDataSource();
		if(record.getAttribute("codTipoFlusso") != null && !"".equals(record.getAttributeAsString("codTipoFlusso"))) {
			codTipoFlussoDS.addParam("codTipoFlusso", record.getAttributeAsString("codTipoFlusso"));										
		} else {
			codTipoFlussoDS.addParam("codTipoFlusso", null);										
		}
		codTipoFlussoItem.setOptionDataSource(codTipoFlussoDS);	
		
		super.editRecord(record);
	}

}
