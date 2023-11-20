/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.tree.TreeGrid;

import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class SelectTreeInoltroRagioneriaItem extends SelectItem {
	
	public SelectTreeInoltroRagioneriaItem(String datasourceName){	
		super();
		setAttribute(datasourceName);
	}
	
	public SelectTreeInoltroRagioneriaItem(String datasourceName, String name) {
		super(name);
		setAttribute(datasourceName);
    }  

	public SelectTreeInoltroRagioneriaItem(String datasourceName, String name, String title) {
		super(name, title);
		setAttribute(datasourceName);
    }  
	
	@Override
	protected ListGrid builPickListProperties() {
		final TreeGrid pickListProperties = new TreeGrid();  
		pickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		pickListProperties.setSelectionType(SelectionStyle.NONE);
		pickListProperties.setShowSelectedStyle(false);
		pickListProperties.addCellClickHandler(new CellClickHandler() {	
			
			@Override
			public void onCellClick(CellClickEvent event) {
				onOptionClick(event.getRecord());
			}
		});
		pickListProperties.setShowHeader(false);
        pickListProperties.setAutoFitFieldWidths(true); 
        pickListProperties.setShowAllRecords(true);
        pickListProperties.setBodyOverflow(Overflow.VISIBLE);
        pickListProperties.setOverflow(Overflow.VISIBLE);
        pickListProperties.setLeaveScrollbarGap(false);
        /*
         * Impedisce il ricaricamento generale dell'albero ad ogni esplosione dei nodi anche 
         * se nodi foglia
         */
        pickListProperties.setLoadDataOnDemand(false);
        pickListProperties.setNodeIcon("blank.png");
        pickListProperties.setFolderIcon("blank.png");
        return pickListProperties;
	}
	
	protected void setAttribute(String datasourceName) {

		setPickListWidth(600);
		
		ListGridField nomeField = new ListGridField("nome");
		
		ListGridField idNodeField = new ListGridField("idNode");  
		idNodeField.setHidden(true);
		
		ListGridField conteggiNodoField = new ListGridField("conteggiNodo");
		conteggiNodoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		conteggiNodoField.setWidth(50);		
		
		ListGridField idProcessTypeField = new ListGridField("idProcessType");  
		idProcessTypeField.setHidden(true);
	
		ListGridField assegnatarioField = new ListGridField("assegnatario");  
		assegnatarioField.setHidden(true);
		
		ListGridField dtInoltroRagioneriaField = new ListGridField("dtInoltroRagioneria");  
		dtInoltroRagioneriaField.setHidden(true);
		
		/**
		 * Con la proprietà setDataSetType("tree"); nel setPickListFields va settato per primo un campo NON hidden
		 * perchè abbiamo riscontrato problemi di creazione del componente grafico
		 */
		setPickListFields(nomeField, idNodeField, conteggiNodoField, idProcessTypeField, assegnatarioField, dtInoltroRagioneriaField);  
	       
		setDataSetType("tree"); 
        setDisplayField("nome");
        setValueField("nome"); 
        setWrapTitle(false);
      
		SelectGWTRestDataSource lSelectGWTRestDataSource = new SelectGWTRestDataSource(datasourceName, true, "idNode", FieldType.TEXT);
		setOptionDataSource(lSelectGWTRestDataSource);	
	}
	
}