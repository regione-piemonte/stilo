/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
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
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

/**
 * 
 * @author dbe4235
 *
 */

public class SelectTreeSmistamentoAttiRagioneriaItem extends SelectItem {
	
	public SelectTreeSmistamentoAttiRagioneriaItem(String datasourceName){	
		super();
		setAttribute(datasourceName);
	}
	
	public SelectTreeSmistamentoAttiRagioneriaItem(String datasourceName, String name) {
		super(name);
		setAttribute(datasourceName);
    }  

	public SelectTreeSmistamentoAttiRagioneriaItem(String datasourceName, String name, String title) {
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
				String nroLivello = event.getRecord().getAttributeAsString("nroLivello");
				if (nroLivello != null && "1".equals(nroLivello)) {
					onOptionClick(event.getRecord());
				} else {
					event.cancel();
					Layout.addMessage(new MessageBean("Valore non selezionabile", "", MessageType.ERROR));
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							clearSelect();
						}
					});
				}
			}
		});
		pickListProperties.setShowHeader(false);
        pickListProperties.setAutoFitFieldWidths(true); 
        pickListProperties.setShowAllRecords(true);
        // con overflow a VISIBLE quando ci sono molti record si vede solo una porzione della pickList e non si può scrollare
        pickListProperties.setBodyOverflow(Overflow.AUTO);
        pickListProperties.setOverflow(Overflow.AUTO);
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